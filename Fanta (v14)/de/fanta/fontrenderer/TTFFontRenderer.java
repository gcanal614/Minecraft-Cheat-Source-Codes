/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.vector.Vector2f
 */
package de.fanta.fontrenderer;

import de.fanta.fontrenderer.TextureData;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class TTFFontRenderer {
    private final boolean antiAlias;
    private final Font font;
    private boolean fractionalMetrics = false;
    private CharacterData[] regularData;
    private CharacterData[] boldData;
    private CharacterData[] italicsData;
    private final int[] colorCodes = new int[32];
    private static final int MARGIN = 4;
    private static final char COLOR_INVOKER = '\u00a7';
    private static int RANDOM_OFFSET = 1;

    public TTFFontRenderer(ExecutorService executorService, ConcurrentLinkedQueue<TextureData> textureQueue, Font font) {
        this(executorService, textureQueue, font, 256);
    }

    public TTFFontRenderer(ExecutorService executorService, ConcurrentLinkedQueue<TextureData> textureQueue, Font font, int characterCount) {
        this(executorService, textureQueue, font, characterCount, true);
    }

    public TTFFontRenderer(ExecutorService executorService, ConcurrentLinkedQueue<TextureData> textureQueue, Font font, boolean antiAlias) {
        this(executorService, textureQueue, font, 256, antiAlias);
    }

    public TTFFontRenderer(ExecutorService executorService, ConcurrentLinkedQueue<TextureData> textureQueue, Font font, int characterCount, boolean antiAlias) {
        this.font = font;
        this.fractionalMetrics = true;
        this.antiAlias = antiAlias;
        int[] regularTexturesIds = new int[characterCount];
        int[] boldTexturesIds = new int[characterCount];
        int[] italicTexturesIds = new int[characterCount];
        int i = 0;
        while (i < characterCount) {
            regularTexturesIds[i] = GL11.glGenTextures();
            boldTexturesIds[i] = GL11.glGenTextures();
            italicTexturesIds[i] = GL11.glGenTextures();
            ++i;
        }
        executorService.execute(() -> {
            this.regularData = this.setup(new CharacterData[characterCount], regularTexturesIds, textureQueue, 0);
        });
        executorService.execute(() -> {
            this.boldData = this.setup(new CharacterData[characterCount], boldTexturesIds, textureQueue, 1);
        });
        executorService.execute(() -> {
            this.italicsData = this.setup(new CharacterData[characterCount], italicTexturesIds, textureQueue, 2);
        });
    }

    private CharacterData[] setup(CharacterData[] characterData, int[] texturesIds, ConcurrentLinkedQueue<TextureData> textureQueue, int type) {
        this.generateColors();
        Font font = this.font.deriveFont(type);
        BufferedImage utilityImage = new BufferedImage(1, 1, 2);
        Graphics2D utilityGraphics = (Graphics2D)utilityImage.getGraphics();
        utilityGraphics.setFont(font);
        FontMetrics fontMetrics = utilityGraphics.getFontMetrics();
        int index = 0;
        while (index < characterData.length) {
            char character = (char)index;
            Rectangle2D characterBounds = fontMetrics.getStringBounds(String.valueOf(character), utilityGraphics);
            float width = (float)characterBounds.getWidth() + 8.0f;
            float height = (float)characterBounds.getHeight();
            BufferedImage characterImage = new BufferedImage(MathHelper.ceiling_double_int(width), MathHelper.ceiling_double_int(height), 2);
            Graphics2D graphics = (Graphics2D)characterImage.getGraphics();
            graphics.setFont(font);
            graphics.setColor(new Color(255, 255, 255, 0));
            graphics.fillRect(0, 0, characterImage.getWidth(), characterImage.getHeight());
            graphics.setColor(Color.WHITE);
            if (this.antiAlias) {
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            }
            graphics.drawString(String.valueOf(character), 4, fontMetrics.getAscent());
            int textureId = texturesIds[index];
            this.createTexture(textureId, characterImage, textureQueue);
            characterData[index] = new CharacterData(character, characterImage.getWidth(), characterImage.getHeight(), textureId);
            ++index;
        }
        return characterData;
    }

    private void createTexture(int textureId, BufferedImage image, ConcurrentLinkedQueue<TextureData> textureQueue) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer((int)(image.getWidth() * image.getHeight() * 4));
        int y = 0;
        while (y < image.getHeight()) {
            int x = 0;
            while (x < image.getWidth()) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte)(pixel >> 16 & 0xFF));
                buffer.put((byte)(pixel >> 8 & 0xFF));
                buffer.put((byte)(pixel & 0xFF));
                buffer.put((byte)(pixel >> 24 & 0xFF));
                ++x;
            }
            ++y;
        }
        buffer.flip();
        textureQueue.add(new TextureData(textureId, image.getWidth(), image.getHeight(), buffer));
    }

    public final int drawString(String text, double x, double y, int color) {
        return this.renderString(text, (float)x, (float)y, color, false);
    }

    public final int drawString(String text, double x, double y, Color color) {
        return this.drawString(text, x, y, color.getRGB());
    }

    public final int drawCenteredString(String text, double x, double y, int color) {
        float width = this.getWidth(text) / 2.0f;
        return this.renderString(text, (float)x - this.getWidth(text) / 2.0f, (float)y, color, false);
    }

    public final int drawCenteredString(String text, double x, double y, Color color) {
        return this.drawCenteredString(text, x, y, color.getRGB());
    }

    public final void drawStringWithShadow(String text, float x, float y, int color) {
        GL11.glTranslated((double)0.5, (double)0.5, (double)0.0);
        this.renderString(text, x, y, color, true);
        GL11.glTranslated((double)-0.5, (double)-0.5, (double)0.0);
        this.renderString(text, x, y, color, false);
    }

    private int renderString(String text, float x, float y, int color, boolean shadow) {
        if (text == "" || text.length() == 0) {
            return 0;
        }
        GL11.glPushMatrix();
        GlStateManager.scale(0.5, 0.5, 1.0);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        x -= 2.0f;
        y -= 2.0f;
        x += 0.5f;
        y += 0.5f;
        x *= 2.0f;
        y *= 2.0f;
        CharacterData[] characterData = this.regularData;
        boolean underlined = false;
        boolean strikethrough = false;
        boolean obfuscated = false;
        int length = text.length();
        double multiplier = 255.0 * (double)(shadow ? 4 : 1);
        Color c = new Color(color);
        GL11.glColor4d((double)((double)c.getRed() / multiplier), (double)((double)c.getGreen() / multiplier), (double)((double)c.getBlue() / multiplier), (double)((double)(color >> 24 & 0xFF) / 255.0));
        int i = 0;
        while (i < length) {
            int previous;
            char character = text.charAt(i);
            int n = previous = i > 0 ? (int)text.charAt(i - 1) : 46;
            if (previous != 167) {
                if (character == '\u00a7' && i < length) {
                    int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
                    if (index < 16) {
                        obfuscated = false;
                        strikethrough = false;
                        underlined = false;
                        characterData = this.regularData;
                        if (index < 0) {
                            index = 15;
                        }
                        if (shadow) {
                            index += 16;
                        }
                        int textColor = this.colorCodes[index];
                        GL11.glColor4d((double)((double)(textColor >> 16) / 255.0), (double)((double)(textColor >> 8 & 0xFF) / 255.0), (double)((double)(textColor & 0xFF) / 255.0), (double)((double)(color >> 24 & 0xFF) / 255.0));
                    } else if (index == 16) {
                        obfuscated = true;
                    } else if (index == 17) {
                        characterData = this.boldData;
                    } else if (index == 18) {
                        strikethrough = true;
                    } else if (index == 19) {
                        underlined = true;
                    } else if (index == 20) {
                        characterData = this.italicsData;
                    } else {
                        obfuscated = false;
                        strikethrough = false;
                        underlined = false;
                        characterData = this.regularData;
                        GL11.glColor4d((double)(1.0 * (shadow ? 0.25 : 1.0)), (double)(1.0 * (shadow ? 0.25 : 1.0)), (double)(1.0 * (shadow ? 0.25 : 1.0)), (double)((double)(color >> 24 & 0xFF) / 255.0));
                    }
                } else if (character <= '\u00ff') {
                    if (obfuscated) {
                        character = (char)(character + RANDOM_OFFSET);
                    }
                    this.drawChar(character, characterData, x, y);
                    CharacterData charData = characterData[character];
                    if (strikethrough) {
                        this.drawLine(new Vector2f(0.0f, charData.height / 2.0f), new Vector2f(charData.width, charData.height / 2.0f), 3.0f);
                    }
                    if (underlined) {
                        this.drawLine(new Vector2f(0.0f, charData.height - 15.0f), new Vector2f(charData.width, charData.height - 15.0f), 3.0f);
                    }
                    x += charData.width - 8.0f;
                }
            }
            ++i;
        }
        GL11.glPopMatrix();
        GlStateManager.disableBlend();
        GlStateManager.bindTexture(0);
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        return (int)x;
    }

    public float getWidth(String text) {
        float width = 0.0f;
        CharacterData[] characterData = this.regularData;
        int length = text.length();
        int i = 0;
        while (i < length) {
            int previous;
            char character = text.charAt(i);
            int n = previous = i > 0 ? (int)text.charAt(i - 1) : 46;
            if (previous != 167) {
                if (character == '\u00a7') {
                    int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
                    if (index == 17) {
                        characterData = this.boldData;
                    } else if (index == 20) {
                        characterData = this.italicsData;
                    } else if (index == 21) {
                        characterData = this.regularData;
                    }
                } else if (character <= '\u00ff') {
                    CharacterData charData = characterData[character];
                    width += (charData.width - 8.0f) / 2.0f;
                }
            }
            ++i;
        }
        return width + 2.0f;
    }

    public float getHeight(String text) {
        float height = 0.0f;
        CharacterData[] characterData = this.regularData;
        int length = text.length();
        int i = 0;
        while (i < length) {
            int previous;
            char character = text.charAt(i);
            int n = previous = i > 0 ? (int)text.charAt(i - 1) : 46;
            if (previous != 167) {
                if (character == '\u00a7') {
                    int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
                    if (index == 17) {
                        characterData = this.boldData;
                    } else if (index == 20) {
                        characterData = this.italicsData;
                    } else if (index == 21) {
                        characterData = this.regularData;
                    }
                } else if (character <= '\u00ff') {
                    CharacterData charData = characterData[character];
                    height = Math.max(height, charData.height);
                }
            }
            ++i;
        }
        return height / 2.0f - 2.0f;
    }

    private void drawChar(char character, CharacterData[] characterData, float x, float y) {
        CharacterData charData = characterData[character];
        charData.bind();
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)0.0f, (float)0.0f);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glTexCoord2f((float)0.0f, (float)1.0f);
        GL11.glVertex2d((double)x, (double)(y + charData.height));
        GL11.glTexCoord2f((float)1.0f, (float)1.0f);
        GL11.glVertex2d((double)(x + charData.width), (double)(y + charData.height));
        GL11.glTexCoord2f((float)1.0f, (float)0.0f);
        GL11.glVertex2d((double)(x + charData.width), (double)y);
        GL11.glEnd();
    }

    private void drawLine(Vector2f start, Vector2f end, float width) {
        GL11.glDisable((int)3553);
        GL11.glLineWidth((float)width);
        GL11.glBegin((int)1);
        GL11.glVertex2f((float)start.x, (float)start.y);
        GL11.glVertex2f((float)end.x, (float)end.y);
        GL11.glEnd();
        GL11.glEnable((int)3553);
    }

    private void generateColors() {
        int i = 0;
        while (i < 32) {
            int thingy = (i >> 3 & 1) * 85;
            int red = (i >> 2 & 1) * 170 + thingy;
            int green = (i >> 1 & 1) * 170 + thingy;
            int blue = (i & 1) * 170 + thingy;
            if (i == 6) {
                red += 85;
            }
            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCodes[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
            ++i;
        }
    }

    static class CharacterData {
        public char character;
        public float width;
        public float height;
        private final int textureId;

        public CharacterData(char character, float width, float height, int textureId) {
            this.character = character;
            this.width = width;
            this.height = height;
            this.textureId = textureId;
        }

        public void bind() {
            GL11.glBindTexture((int)3553, (int)this.textureId);
        }
    }
}

