package optifine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;

public class ConnectedProperties {
   public static final int SYMMETRY_UNKNOWN = 128;
   public static final int METHOD_FIXED = 7;
   public MatchBlock[] matchBlocks = null;
   public static final int FACE_SIDES = 60;
   public static final int CONNECT_UNKNOWN = 128;
   public static final int METHOD_HORIZONTAL_VERTICAL = 8;
   public int faces = 63;
   public static final int SYMMETRY_NONE = 1;
   public static final int CONNECT_TILE = 2;
   public static final int METHOD_REPEAT = 5;
   public int maxHeight = 1024;
   public int[] metadatas = null;
   public static final int SYMMETRY_ALL = 6;
   public static final int METHOD_NONE = 0;
   public String[] tiles = null;
   public String basePath = null;
   public static final int FACE_UNKNOWN = 128;
   public String name = null;
   public static final int SYMMETRY_OPPOSITE = 2;
   public TextureAtlasSprite[] tileIcons = null;
   public static final int FACE_WEST = 16;
   public BiomeGenBase[] biomes = null;
   public static final int CONNECT_BLOCK = 1;
   public boolean innerSeams = false;
   public static final int METHOD_RANDOM = 4;
   public int connect = 0;
   public int[] sumWeights = null;
   public int height = 0;
   public int[] weights = null;
   public int width = 0;
   public static final int METHOD_HORIZONTAL = 2;
   public int method = 0;
   public String[] matchTiles = null;
   public static final int FACE_EAST = 32;
   public static final int FACE_SOUTH = 8;
   public int minHeight = 0;
   public static final int FACE_NORTH = 4;
   public static final int FACE_TOP = 2;
   public static final int CONNECT_MATERIAL = 3;
   public int sumAllWeights = 1;
   public int symmetry = 1;
   public static final int METHOD_VERTICAL_HORIZONTAL = 9;
   public static final int CONNECT_NONE = 0;
   public static final int METHOD_TOP = 3;
   public static final int METHOD_CTM = 1;
   public int renderPass = 0;
   public static final int METHOD_VERTICAL = 6;
   public static final int FACE_ALL = 63;
   public static final int FACE_BOTTOM = 1;
   public TextureAtlasSprite[] matchTileIcons = null;

   private boolean isValidRepeat(String var1) {
      if (this.tiles == null) {
         Config.warn(String.valueOf((new StringBuilder("Tiles not defined: ")).append(var1)));
         return false;
      } else if (this.width > 0 && this.width <= 16) {
         if (this.height > 0 && this.height <= 16) {
            if (this.tiles.length != this.width * this.height) {
               Config.warn(String.valueOf((new StringBuilder("Number of tiles does not equal width x height: ")).append(var1)));
               return false;
            } else {
               return true;
            }
         } else {
            Config.warn(String.valueOf((new StringBuilder("Invalid height: ")).append(var1)));
            return false;
         }
      } else {
         Config.warn(String.valueOf((new StringBuilder("Invalid width: ")).append(var1)));
         return false;
      }
   }

   private MatchBlock[] detectMatchBlocks() {
      int[] var1 = this.detectMatchBlockIds();
      if (var1 == null) {
         return null;
      } else {
         MatchBlock[] var2 = new MatchBlock[var1.length];

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3] = new MatchBlock(var1[var3]);
         }

         return var2;
      }
   }

   private boolean isValidCtm(String var1) {
      if (this.tiles == null) {
         this.tiles = this.parseTileNames("0-11 16-27 32-43 48-58");
      }

      if (this.tiles.length < 47) {
         Config.warn(String.valueOf((new StringBuilder("Invalid tiles, must be at least 47: ")).append(var1)));
         return false;
      } else {
         return true;
      }
   }

   private static int parseFaces(String var0) {
      if (var0 == null) {
         return 63;
      } else {
         String[] var1 = Config.tokenize(var0, " ,");
         int var2 = 0;

         for(int var3 = 0; var3 < var1.length; ++var3) {
            String var4 = var1[var3];
            int var5 = parseFace(var4);
            var2 |= var5;
         }

         return var2;
      }
   }

   public boolean matchesBiome(BiomeGenBase var1) {
      return Matches.biome(var1, this.biomes);
   }

   private static String parseName(String var0) {
      String var1 = var0;
      int var2 = var0.lastIndexOf(47);
      if (var2 >= 0) {
         var1 = var0.substring(var2 + 1);
      }

      int var3 = var1.lastIndexOf(46);
      if (var3 >= 0) {
         var1 = var1.substring(0, var3);
      }

      return var1;
   }

   private static int parseSymmetry(String var0) {
      if (var0 == null) {
         return 1;
      } else if (var0.equals("opposite")) {
         return 2;
      } else if (var0.equals("all")) {
         return 6;
      } else {
         Config.warn(String.valueOf((new StringBuilder("Unknown symmetry: ")).append(var0)));
         return 1;
      }
   }

   private boolean isValidRandom(String var1) {
      if (this.tiles != null && this.tiles.length > 0) {
         if (this.weights != null) {
            int[] var2;
            if (this.weights.length > this.tiles.length) {
               Config.warn(String.valueOf((new StringBuilder("More weights defined than tiles, trimming weights: ")).append(var1)));
               var2 = new int[this.tiles.length];
               System.arraycopy(this.weights, 0, var2, 0, var2.length);
               this.weights = var2;
            }

            int var3;
            int var4;
            if (this.weights.length < this.tiles.length) {
               Config.warn(String.valueOf((new StringBuilder("Less weights defined than tiles, expanding weights: ")).append(var1)));
               var2 = new int[this.tiles.length];
               System.arraycopy(this.weights, 0, var2, 0, this.weights.length);
               var3 = MathUtils.getAverage(this.weights);

               for(var4 = this.weights.length; var4 < var2.length; ++var4) {
                  var2[var4] = var3;
               }

               this.weights = var2;
            }

            this.sumWeights = new int[this.weights.length];
            var4 = 0;

            for(var3 = 0; var3 < this.weights.length; ++var3) {
               var4 += this.weights[var3];
               this.sumWeights[var3] = var4;
            }

            this.sumAllWeights = var4;
            if (this.sumAllWeights <= 0) {
               Config.warn(String.valueOf((new StringBuilder("Invalid sum of all weights: ")).append(var4)));
               this.sumAllWeights = 1;
            }
         }

         return true;
      } else {
         Config.warn(String.valueOf((new StringBuilder("Tiles not defined: ")).append(var1)));
         return false;
      }
   }

   public boolean matchesIcon(TextureAtlasSprite var1) {
      return Matches.sprite(var1, this.matchTileIcons);
   }

   private boolean isValidVertical(String var1) {
      if (this.tiles == null) {
         Config.warn(String.valueOf((new StringBuilder("No tiles defined for vertical: ")).append(var1)));
         return false;
      } else if (this.tiles.length != 4) {
         Config.warn(String.valueOf((new StringBuilder("Invalid tiles, must be exactly 4: ")).append(var1)));
         return false;
      } else {
         return true;
      }
   }

   public String toString() {
      return String.valueOf((new StringBuilder("CTM name: ")).append(this.name).append(", basePath: ").append(this.basePath).append(", matchBlocks: ").append(Config.arrayToString((Object[])this.matchBlocks)).append(", matchTiles: ").append(Config.arrayToString((Object[])this.matchTiles)));
   }

   private boolean isValidFixed(String var1) {
      if (this.tiles == null) {
         Config.warn(String.valueOf((new StringBuilder("Tiles not defined: ")).append(var1)));
         return false;
      } else if (this.tiles.length != 1) {
         Config.warn("Number of tiles should be 1 for method: fixed.");
         return false;
      } else {
         return true;
      }
   }

   private boolean isValidHorizontal(String var1) {
      if (this.tiles == null) {
         this.tiles = this.parseTileNames("12-15");
      }

      if (this.tiles.length != 4) {
         Config.warn(String.valueOf((new StringBuilder("Invalid tiles, must be exactly 4: ")).append(var1)));
         return false;
      } else {
         return true;
      }
   }

   public boolean matchesBlock(int var1, int var2) {
      return !Matches.block(var1, var2, this.matchBlocks) ? false : Matches.metadata(var2, this.metadatas);
   }

   private static int parseMethod(String var0) {
      if (var0 == null) {
         return 1;
      } else if (!var0.equals("ctm") && !var0.equals("glass")) {
         if (!var0.equals("horizontal") && !var0.equals("bookshelf")) {
            if (var0.equals("vertical")) {
               return 6;
            } else if (var0.equals("top")) {
               return 3;
            } else if (var0.equals("random")) {
               return 4;
            } else if (var0.equals("repeat")) {
               return 5;
            } else if (var0.equals("fixed")) {
               return 7;
            } else if (!var0.equals("horizontal+vertical") && !var0.equals("h+v")) {
               if (!var0.equals("vertical+horizontal") && !var0.equals("v+h")) {
                  Config.warn(String.valueOf((new StringBuilder("Unknown method: ")).append(var0)));
                  return 0;
               } else {
                  return 9;
               }
            } else {
               return 8;
            }
         } else {
            return 2;
         }
      } else {
         return 1;
      }
   }

   private int detectConnect() {
      return this.matchBlocks != null ? 1 : (this.matchTiles != null ? 2 : 128);
   }

   public boolean isValid(String var1) {
      if (this.name != null && this.name.length() > 0) {
         if (this.basePath == null) {
            Config.warn(String.valueOf((new StringBuilder("No base path found: ")).append(var1)));
            return false;
         } else {
            if (this.matchBlocks == null) {
               this.matchBlocks = this.detectMatchBlocks();
            }

            if (this.matchTiles == null && this.matchBlocks == null) {
               this.matchTiles = this.detectMatchTiles();
            }

            if (this.matchBlocks == null && this.matchTiles == null) {
               Config.warn(String.valueOf((new StringBuilder("No matchBlocks or matchTiles specified: ")).append(var1)));
               return false;
            } else if (this.method == 0) {
               Config.warn(String.valueOf((new StringBuilder("No method: ")).append(var1)));
               return false;
            } else if (this.tiles != null && this.tiles.length > 0) {
               if (this.connect == 0) {
                  this.connect = this.detectConnect();
               }

               if (this.connect == 128) {
                  Config.warn(String.valueOf((new StringBuilder("Invalid connect in: ")).append(var1)));
                  return false;
               } else if (this.renderPass > 0) {
                  Config.warn(String.valueOf((new StringBuilder("Render pass not supported: ")).append(this.renderPass)));
                  return false;
               } else if ((this.faces & 128) != 0) {
                  Config.warn(String.valueOf((new StringBuilder("Invalid faces in: ")).append(var1)));
                  return false;
               } else if ((this.symmetry & 128) != 0) {
                  Config.warn(String.valueOf((new StringBuilder("Invalid symmetry in: ")).append(var1)));
                  return false;
               } else {
                  switch(this.method) {
                  case 1:
                     return this.isValidCtm(var1);
                  case 2:
                     return this.isValidHorizontal(var1);
                  case 3:
                     return this.isValidTop(var1);
                  case 4:
                     return this.isValidRandom(var1);
                  case 5:
                     return this.isValidRepeat(var1);
                  case 6:
                     return this.isValidVertical(var1);
                  case 7:
                     return this.isValidFixed(var1);
                  case 8:
                     return this.isValidHorizontalVertical(var1);
                  case 9:
                     return this.isValidVerticalHorizontal(var1);
                  default:
                     Config.warn(String.valueOf((new StringBuilder("Unknown method: ")).append(var1)));
                     return false;
                  }
               }
            } else {
               Config.warn(String.valueOf((new StringBuilder("No tiles specified: ")).append(var1)));
               return false;
            }
         }
      } else {
         Config.warn(String.valueOf((new StringBuilder("No name found: ")).append(var1)));
         return false;
      }
   }

   public void updateIcons(TextureMap var1) {
      if (this.matchTiles != null) {
         this.matchTileIcons = registerIcons(this.matchTiles, var1);
      }

      if (this.tiles != null) {
         this.tileIcons = registerIcons(this.tiles, var1);
      }

   }

   private String[] parseMatchTiles(String var1) {
      if (var1 == null) {
         return null;
      } else {
         String[] var2 = Config.tokenize(var1, " ");

         for(int var3 = 0; var3 < var2.length; ++var3) {
            String var4 = var2[var3];
            if (var4.endsWith(".png")) {
               var4 = var4.substring(0, var4.length() - 4);
            }

            var4 = TextureUtils.fixResourcePath(var4, this.basePath);
            var2[var3] = var4;
         }

         return var2;
      }
   }

   public ConnectedProperties(Properties var1, String var2) {
      ConnectedParser var3 = new ConnectedParser("ConnectedTextures");
      this.name = var3.parseName(var2);
      this.basePath = var3.parseBasePath(var2);
      this.matchBlocks = var3.parseMatchBlocks(var1.getProperty("matchBlocks"));
      this.metadatas = var3.parseIntList(var1.getProperty("metadata"));
      this.matchTiles = this.parseMatchTiles(var1.getProperty("matchTiles"));
      this.method = parseMethod(var1.getProperty("method"));
      this.tiles = this.parseTileNames(var1.getProperty("tiles"));
      this.connect = parseConnect(var1.getProperty("connect"));
      this.faces = parseFaces(var1.getProperty("faces"));
      this.biomes = var3.parseBiomes(var1.getProperty("biomes"));
      this.minHeight = var3.parseInt(var1.getProperty("minHeight"), -1);
      this.maxHeight = var3.parseInt(var1.getProperty("maxHeight"), 1024);
      this.renderPass = var3.parseInt(var1.getProperty("renderPass"));
      this.innerSeams = ConnectedParser.parseBoolean(var1.getProperty("innerSeams"));
      this.width = var3.parseInt(var1.getProperty("width"));
      this.height = var3.parseInt(var1.getProperty("height"));
      this.weights = var3.parseIntList(var1.getProperty("weights"));
      this.symmetry = parseSymmetry(var1.getProperty("symmetry"));
   }

   private boolean isValidTop(String var1) {
      if (this.tiles == null) {
         this.tiles = this.parseTileNames("66");
      }

      if (this.tiles.length != 1) {
         Config.warn(String.valueOf((new StringBuilder("Invalid tiles, must be exactly 1: ")).append(var1)));
         return false;
      } else {
         return true;
      }
   }

   private static TextureAtlasSprite getIcon(String var0) {
      TextureMap var1 = Minecraft.getMinecraft().getTextureMapBlocks();
      TextureAtlasSprite var2 = var1.getSpriteSafe(var0);
      if (var2 != null) {
         return var2;
      } else {
         var2 = var1.getSpriteSafe(String.valueOf((new StringBuilder("blocks/")).append(var0)));
         return var2;
      }
   }

   public int getMetadataMax() {
      byte var1 = -1;
      int var2 = this.getMax(this.metadatas, var1);
      if (this.matchBlocks != null) {
         for(int var3 = 0; var3 < this.matchBlocks.length; ++var3) {
            MatchBlock var4 = this.matchBlocks[var3];
            var2 = this.getMax(var4.getMetadatas(), var2);
         }
      }

      return var2;
   }

   private boolean isValidVerticalHorizontal(String var1) {
      if (this.tiles == null) {
         Config.warn(String.valueOf((new StringBuilder("No tiles defined for vertical+horizontal: ")).append(var1)));
         return false;
      } else if (this.tiles.length != 7) {
         Config.warn(String.valueOf((new StringBuilder("Invalid tiles, must be exactly 7: ")).append(var1)));
         return false;
      } else {
         return true;
      }
   }

   private int[] detectMatchBlockIds() {
      if (!this.name.startsWith("block")) {
         return null;
      } else {
         int var1 = "block".length();

         int var2;
         for(var2 = var1; var2 < this.name.length(); ++var2) {
            char var3 = this.name.charAt(var2);
            if (var3 < '0' || var3 > '9') {
               break;
            }
         }

         if (var2 == var1) {
            return null;
         } else {
            String var5 = this.name.substring(var1, var2);
            int var4 = Config.parseInt(var5, -1);
            return var4 < 0 ? null : new int[]{var4};
         }
      }
   }

   private static int parseFace(String var0) {
      var0 = var0.toLowerCase();
      if (!var0.equals("bottom") && !var0.equals("down")) {
         if (!var0.equals("top") && !var0.equals("up")) {
            if (var0.equals("north")) {
               return 4;
            } else if (var0.equals("south")) {
               return 8;
            } else if (var0.equals("east")) {
               return 32;
            } else if (var0.equals("west")) {
               return 16;
            } else if (var0.equals("sides")) {
               return 60;
            } else if (var0.equals("all")) {
               return 63;
            } else {
               Config.warn(String.valueOf((new StringBuilder("Unknown face: ")).append(var0)));
               return 128;
            }
         } else {
            return 2;
         }
      } else {
         return 1;
      }
   }

   private static String parseBasePath(String var0) {
      int var1 = var0.lastIndexOf(47);
      return var1 < 0 ? "" : var0.substring(0, var1);
   }

   private String[] detectMatchTiles() {
      TextureAtlasSprite var1 = getIcon(this.name);
      return var1 == null ? null : new String[]{this.name};
   }

   public boolean matchesBlockId(int var1) {
      return Matches.blockId(var1, this.matchBlocks);
   }

   private static TextureAtlasSprite[] registerIcons(String[] var0, TextureMap var1) {
      if (var0 == null) {
         return null;
      } else {
         ArrayList var2 = new ArrayList();

         for(int var3 = 0; var3 < var0.length; ++var3) {
            String var4 = var0[var3];
            ResourceLocation var5 = new ResourceLocation(var4);
            String var6 = var5.getResourceDomain();
            String var7 = var5.getResourcePath();
            if (!var7.contains("/")) {
               var7 = String.valueOf((new StringBuilder("textures/blocks/")).append(var7));
            }

            String var8 = String.valueOf((new StringBuilder(String.valueOf(var7))).append(".png"));
            ResourceLocation var9 = new ResourceLocation(var6, var8);
            boolean var10 = Config.hasResource(var9);
            if (!var10) {
               Config.warn(String.valueOf((new StringBuilder("File not found: ")).append(var8)));
            }

            String var11 = "textures/";
            String var12 = var7;
            if (var7.startsWith(var11)) {
               var12 = var7.substring(var11.length());
            }

            ResourceLocation var13 = new ResourceLocation(var6, var12);
            TextureAtlasSprite var14 = var1.func_174942_a(var13);
            var2.add(var14);
         }

         TextureAtlasSprite[] var15 = (TextureAtlasSprite[])var2.toArray(new TextureAtlasSprite[var2.size()]);
         return var15;
      }
   }

   private boolean isValidHorizontalVertical(String var1) {
      if (this.tiles == null) {
         Config.warn(String.valueOf((new StringBuilder("No tiles defined for horizontal+vertical: ")).append(var1)));
         return false;
      } else if (this.tiles.length != 7) {
         Config.warn(String.valueOf((new StringBuilder("Invalid tiles, must be exactly 7: ")).append(var1)));
         return false;
      } else {
         return true;
      }
   }

   private int getMax(int[] var1, int var2) {
      if (var1 == null) {
         return var2;
      } else {
         for(int var3 = 0; var3 < var1.length; ++var3) {
            int var4 = var1[var3];
            if (var4 > var2) {
               var2 = var4;
            }
         }

         return var2;
      }
   }

   public static IProperty getProperty(String var0, Collection var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         IProperty var3 = (IProperty)var2.next();
         if (var0.equals(var3.getName())) {
            return var3;
         }
      }

      return null;
   }

   private static int parseConnect(String var0) {
      if (var0 == null) {
         return 0;
      } else if (var0.equals("block")) {
         return 1;
      } else if (var0.equals("tile")) {
         return 2;
      } else if (var0.equals("material")) {
         return 3;
      } else {
         Config.warn(String.valueOf((new StringBuilder("Unknown connect: ")).append(var0)));
         return 128;
      }
   }

   private String[] parseTileNames(String var1) {
      if (var1 == null) {
         return null;
      } else {
         ArrayList var2 = new ArrayList();
         String[] var3 = Config.tokenize(var1, " ,");

         label80:
         for(int var4 = 0; var4 < var3.length; ++var4) {
            String var5 = var3[var4];
            if (var5.contains("-")) {
               String[] var6 = Config.tokenize(var5, "-");
               if (var6.length == 2) {
                  int var7 = Config.parseInt(var6[0], -1);
                  int var8 = Config.parseInt(var6[1], -1);
                  if (var7 >= 0 && var8 >= 0) {
                     if (var7 <= var8) {
                        int var9 = var7;

                        while(true) {
                           if (var9 > var8) {
                              continue label80;
                           }

                           var2.add(String.valueOf(var9));
                           ++var9;
                        }
                     }

                     Config.warn(String.valueOf((new StringBuilder("Invalid interval: ")).append(var5).append(", when parsing: ").append(var1)));
                     continue;
                  }
               }
            }

            var2.add(var5);
         }

         String[] var10 = (String[])var2.toArray(new String[var2.size()]);

         for(int var11 = 0; var11 < var10.length; ++var11) {
            String var12 = var10[var11];
            var12 = TextureUtils.fixResourcePath(var12, this.basePath);
            if (!var12.startsWith(this.basePath) && !var12.startsWith("textures/") && !var12.startsWith("mcpatcher/")) {
               var12 = String.valueOf((new StringBuilder(String.valueOf(this.basePath))).append("/").append(var12));
            }

            if (var12.endsWith(".png")) {
               var12 = var12.substring(0, var12.length() - 4);
            }

            String var13 = "textures/blocks/";
            if (var12.startsWith(var13)) {
               var12 = var12.substring(var13.length());
            }

            if (var12.startsWith("/")) {
               var12 = var12.substring(1);
            }

            var10[var11] = var12;
         }

         return var10;
      }
   }
}
