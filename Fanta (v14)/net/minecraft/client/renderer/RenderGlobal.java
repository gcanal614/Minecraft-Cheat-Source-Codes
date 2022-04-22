/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonSyntaxException
 *  optifine.ChunkUtils
 *  optifine.CloudRenderer
 *  optifine.Config
 *  optifine.CustomColors
 *  optifine.CustomSky
 *  optifine.DynamicLights
 *  optifine.Lagometer
 *  optifine.RandomMobs
 *  optifine.Reflector
 *  optifine.ReflectorMethod
 *  optifine.RenderInfoLazy
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.vector.Matrix4f
 *  org.lwjgl.util.vector.Vector3f
 *  org.lwjgl.util.vector.Vector4f
 *  shadersmod.client.Shaders
 *  shadersmod.client.ShadersRender
 *  shadersmod.client.ShadowUtils
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import de.fanta.Client;
import de.fanta.gui.font.BasicFontRenderer;
import de.fanta.module.impl.visual.ESP;
import de.fanta.setting.settings.DropdownBox;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.ChunkRenderContainer;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VboRenderList;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.ListChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VboChunkFactory;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vector3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import optifine.ChunkUtils;
import optifine.CloudRenderer;
import optifine.Config;
import optifine.CustomColors;
import optifine.CustomSky;
import optifine.DynamicLights;
import optifine.Lagometer;
import optifine.RandomMobs;
import optifine.Reflector;
import optifine.ReflectorMethod;
import optifine.RenderInfoLazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;
import shadersmod.client.ShadowUtils;

public class RenderGlobal
implements IWorldAccess,
IResourceManagerReloadListener {
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
    private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
    private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
    private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
    private static final ResourceLocation locationForcefieldPng = new ResourceLocation("textures/misc/forcefield.png");
    public static Minecraft mc;
    private final TextureManager renderEngine;
    private final RenderManager renderManager;
    private WorldClient theWorld;
    private Set chunksToUpdate = Sets.newLinkedHashSet();
    private List renderInfos = Lists.newArrayListWithCapacity((int)69696);
    private final Set field_181024_n = Sets.newHashSet();
    private ViewFrustum viewFrustum;
    private int starGLCallList = -1;
    private int glSkyList = -1;
    private int glSkyList2 = -1;
    private VertexFormat vertexBufferFormat;
    private VertexBuffer starVBO;
    private VertexBuffer skyVBO;
    private VertexBuffer sky2VBO;
    private int cloudTickCounter;
    public final Map damagedBlocks = Maps.newHashMap();
    private final Map mapSoundPositions = Maps.newHashMap();
    private final TextureAtlasSprite[] destroyBlockIcons = new TextureAtlasSprite[10];
    private static Framebuffer entityOutlineFramebuffer;
    private static ShaderGroup entityOutlineShader;
    private double frustumUpdatePosX = Double.MIN_VALUE;
    private double frustumUpdatePosY = Double.MIN_VALUE;
    private double frustumUpdatePosZ = Double.MIN_VALUE;
    private int frustumUpdatePosChunkX = Integer.MIN_VALUE;
    private int frustumUpdatePosChunkY = Integer.MIN_VALUE;
    private int frustumUpdatePosChunkZ = Integer.MIN_VALUE;
    private double lastViewEntityX = Double.MIN_VALUE;
    private double lastViewEntityY = Double.MIN_VALUE;
    private double lastViewEntityZ = Double.MIN_VALUE;
    private double lastViewEntityPitch = Double.MIN_VALUE;
    private double lastViewEntityYaw = Double.MIN_VALUE;
    private final ChunkRenderDispatcher renderDispatcher = new ChunkRenderDispatcher();
    private ChunkRenderContainer renderContainer;
    private int renderDistanceChunks = -1;
    private int renderEntitiesStartupCounter = 2;
    private int countEntitiesTotal;
    private int countEntitiesRendered;
    private int countEntitiesHidden;
    private boolean debugFixTerrainFrustum = false;
    private ClippingHelper debugFixedClippingHelper;
    private final Vector4f[] debugTerrainMatrix = new Vector4f[8];
    private final Vector3d debugTerrainFrustumPosition = new Vector3d();
    private boolean vboEnabled = false;
    IRenderChunkFactory renderChunkFactory;
    private double prevRenderSortX;
    private double prevRenderSortY;
    private double prevRenderSortZ;
    public boolean displayListEntitiesDirty = true;
    private static final String __OBFID = "CL_00000954";
    private CloudRenderer cloudRenderer;
    public Entity renderedEntity;
    public Set chunksToResortTransparency = new LinkedHashSet();
    public Set chunksToUpdateForced = new LinkedHashSet();
    private Deque visibilityDeque = new ArrayDeque();
    private List renderInfosEntities = new ArrayList(1024);
    private List renderInfosTileEntities = new ArrayList(1024);
    private List renderInfosNormal = new ArrayList(1024);
    private List renderInfosEntitiesNormal = new ArrayList(1024);
    private List renderInfosTileEntitiesNormal = new ArrayList(1024);
    private List renderInfosShadow = new ArrayList(1024);
    private List renderInfosEntitiesShadow = new ArrayList(1024);
    private List renderInfosTileEntitiesShadow = new ArrayList(1024);
    private int renderDistance = 0;
    private int renderDistanceSq = 0;
    private static final Set SET_ALL_FACINGS;
    private int countTileEntitiesRendered;

    static {
        SET_ALL_FACINGS = Collections.unmodifiableSet(new HashSet<EnumFacing>(Arrays.asList(EnumFacing.VALUES)));
    }

    public RenderGlobal(Minecraft mcIn) {
        this.cloudRenderer = new CloudRenderer(mcIn);
        mc = mcIn;
        this.renderManager = mcIn.getRenderManager();
        this.renderEngine = mcIn.getTextureManager();
        this.renderEngine.bindTexture(locationForcefieldPng);
        GL11.glTexParameteri((int)3553, (int)10242, (int)10497);
        GL11.glTexParameteri((int)3553, (int)10243, (int)10497);
        GlStateManager.bindTexture(0);
        this.updateDestroyBlockIcons();
        this.vboEnabled = OpenGlHelper.useVbo();
        if (this.vboEnabled) {
            this.renderContainer = new VboRenderList();
            this.renderChunkFactory = new VboChunkFactory();
        } else {
            this.renderContainer = new RenderList();
            this.renderChunkFactory = new ListChunkFactory();
        }
        this.vertexBufferFormat = new VertexFormat();
        this.vertexBufferFormat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3));
        this.generateStars();
        this.generateSky();
        this.generateSky2();
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        this.updateDestroyBlockIcons();
    }

    private void updateDestroyBlockIcons() {
        TextureMap texturemap = mc.getTextureMapBlocks();
        int i = 0;
        while (i < this.destroyBlockIcons.length) {
            this.destroyBlockIcons[i] = texturemap.getAtlasSprite("minecraft:blocks/destroy_stage_" + i);
            ++i;
        }
    }

    public static void makeEntityOutlineShader() {
        if (OpenGlHelper.shadersSupported) {
            if (ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
                ShaderLinkHelper.setNewStaticShaderLinkHelper();
            }
            ResourceLocation resourcelocation = new ResourceLocation("shaders/post/entity_outline.json");
            try {
                entityOutlineShader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), resourcelocation);
                entityOutlineShader.createBindFramebuffers(RenderGlobal.mc.displayWidth, RenderGlobal.mc.displayHeight);
                entityOutlineFramebuffer = entityOutlineShader.getFramebufferRaw("final");
            }
            catch (IOException ioexception) {
                logger.warn("Failed to load shader: " + resourcelocation, (Throwable)ioexception);
                entityOutlineShader = null;
                entityOutlineFramebuffer = null;
            }
            catch (JsonSyntaxException jsonsyntaxexception) {
                logger.warn("Failed to load shader: " + resourcelocation, (Throwable)jsonsyntaxexception);
                entityOutlineShader = null;
                entityOutlineFramebuffer = null;
            }
        } else {
            entityOutlineShader = null;
            entityOutlineFramebuffer = null;
        }
    }

    public void renderEntityOutlineFramebuffer() {
        if (this.isRenderEntityOutlines()) {
            GlStateManager.enableBlend();
            if (Client.INSTANCE.moduleManager.getModule("ESP").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"ESP").getSetting((String)"ESPModes").getSetting()).curOption == "Shader") {
                GlStateManager.tryBlendFuncSeparate(770, 1, 0, 1);
            } else if (Client.INSTANCE.moduleManager.getModule("ESP").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"ESP").getSetting((String)"ESPModes").getSetting()).curOption == "Outline") {
                GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            }
            entityOutlineFramebuffer.framebufferRenderExt(RenderGlobal.mc.displayWidth, RenderGlobal.mc.displayHeight, false);
            GlStateManager.disableBlend();
        }
    }

    protected boolean isRenderEntityOutlines() {
        return Client.INSTANCE.moduleManager.getModule("ESP").isState() && ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"ESP").getSetting((String)"ESPModes").getSetting()).curOption == "Shader" || ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"ESP").getSetting((String)"ESPModes").getSetting()).curOption == "Outline";
    }

    private void generateSky2() {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        if (this.sky2VBO != null) {
            this.sky2VBO.deleteGlBuffers();
        }
        if (this.glSkyList2 >= 0) {
            GLAllocation.deleteDisplayLists(this.glSkyList2);
            this.glSkyList2 = -1;
        }
        if (this.vboEnabled) {
            this.sky2VBO = new VertexBuffer(this.vertexBufferFormat);
            this.renderSky(worldrenderer, -16.0f, true);
            worldrenderer.finishDrawing();
            worldrenderer.reset();
            this.sky2VBO.func_181722_a(worldrenderer.getByteBuffer());
        } else {
            this.glSkyList2 = GLAllocation.generateDisplayLists(1);
            GL11.glNewList((int)this.glSkyList2, (int)4864);
            this.renderSky(worldrenderer, -16.0f, true);
            tessellator.draw();
            GL11.glEndList();
        }
    }

    private void generateSky() {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        if (this.skyVBO != null) {
            this.skyVBO.deleteGlBuffers();
        }
        if (this.glSkyList >= 0) {
            GLAllocation.deleteDisplayLists(this.glSkyList);
            this.glSkyList = -1;
        }
        if (this.vboEnabled) {
            this.skyVBO = new VertexBuffer(this.vertexBufferFormat);
            this.renderSky(worldrenderer, 16.0f, false);
            worldrenderer.finishDrawing();
            worldrenderer.reset();
            this.skyVBO.func_181722_a(worldrenderer.getByteBuffer());
        } else {
            this.glSkyList = GLAllocation.generateDisplayLists(1);
            GL11.glNewList((int)this.glSkyList, (int)4864);
            this.renderSky(worldrenderer, 16.0f, false);
            tessellator.draw();
            GL11.glEndList();
        }
    }

    private void renderSky(WorldRenderer worldRendererIn, float p_174968_2_, boolean p_174968_3_) {
        boolean flag = true;
        boolean flag1 = true;
        worldRendererIn.begin(7, DefaultVertexFormats.POSITION);
        int i = -384;
        while (i <= 384) {
            int j = -384;
            while (j <= 384) {
                float f = i;
                float f1 = i + 64;
                if (p_174968_3_) {
                    f1 = i;
                    f = i + 64;
                }
                worldRendererIn.pos(f, p_174968_2_, j).endVertex();
                worldRendererIn.pos(f1, p_174968_2_, j).endVertex();
                worldRendererIn.pos(f1, p_174968_2_, j + 64).endVertex();
                worldRendererIn.pos(f, p_174968_2_, j + 64).endVertex();
                j += 64;
            }
            i += 64;
        }
    }

    private void generateStars() {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        if (this.starVBO != null) {
            this.starVBO.deleteGlBuffers();
        }
        if (this.starGLCallList >= 0) {
            GLAllocation.deleteDisplayLists(this.starGLCallList);
            this.starGLCallList = -1;
        }
        if (this.vboEnabled) {
            this.starVBO = new VertexBuffer(this.vertexBufferFormat);
            this.renderStars(worldrenderer);
            worldrenderer.finishDrawing();
            worldrenderer.reset();
            this.starVBO.func_181722_a(worldrenderer.getByteBuffer());
        } else {
            this.starGLCallList = GLAllocation.generateDisplayLists(1);
            GlStateManager.pushMatrix();
            GL11.glNewList((int)this.starGLCallList, (int)4864);
            this.renderStars(worldrenderer);
            tessellator.draw();
            GL11.glEndList();
            GlStateManager.popMatrix();
        }
    }

    private void renderStars(WorldRenderer worldRendererIn) {
        Random random = new Random(10842L);
        worldRendererIn.begin(7, DefaultVertexFormats.POSITION);
        int i = 0;
        while (i < 1500) {
            double d0 = random.nextFloat() * 2.0f - 1.0f;
            double d1 = random.nextFloat() * 2.0f - 1.0f;
            double d2 = random.nextFloat() * 2.0f - 1.0f;
            double d3 = 0.15f + random.nextFloat() * 0.1f;
            double d4 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d4 < 1.0 && d4 > 0.01) {
                d4 = 1.0 / Math.sqrt(d4);
                double d5 = (d0 *= d4) * 100.0;
                double d6 = (d1 *= d4) * 100.0;
                double d7 = (d2 *= d4) * 100.0;
                double d8 = Math.atan2(d0, d2);
                double d9 = Math.sin(d8);
                double d10 = Math.cos(d8);
                double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
                double d12 = Math.sin(d11);
                double d13 = Math.cos(d11);
                double d14 = random.nextDouble() * Math.PI * 2.0;
                double d15 = Math.sin(d14);
                double d16 = Math.cos(d14);
                int j = 0;
                while (j < 4) {
                    double d17 = 0.0;
                    double d18 = (double)((j & 2) - 1) * d3;
                    double d19 = (double)((j + 1 & 2) - 1) * d3;
                    double d20 = 0.0;
                    double d21 = d18 * d16 - d19 * d15;
                    double d22 = d19 * d16 + d18 * d15;
                    double d23 = d21 * d12 + 0.0 * d13;
                    double d24 = 0.0 * d12 - d21 * d13;
                    double d25 = d24 * d9 - d22 * d10;
                    double d26 = d22 * d9 + d24 * d10;
                    worldRendererIn.pos(d5 + d25, d6 + d23, d7 + d26).endVertex();
                    ++j;
                }
            }
            ++i;
        }
    }

    public void setWorldAndLoadRenderers(WorldClient worldClientIn) {
        if (this.theWorld != null) {
            this.theWorld.removeWorldAccess(this);
        }
        this.frustumUpdatePosX = Double.MIN_VALUE;
        this.frustumUpdatePosY = Double.MIN_VALUE;
        this.frustumUpdatePosZ = Double.MIN_VALUE;
        this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
        this.renderManager.set(worldClientIn);
        this.theWorld = worldClientIn;
        if (Config.isDynamicLights()) {
            DynamicLights.clear();
        }
        if (worldClientIn != null) {
            worldClientIn.addWorldAccess(this);
            this.loadRenderers();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void loadRenderers() {
        if (this.theWorld != null) {
            Entity entity;
            this.displayListEntitiesDirty = true;
            Blocks.leaves.setGraphicsLevel(Config.isTreesFancy());
            Blocks.leaves2.setGraphicsLevel(Config.isTreesFancy());
            BlockModelRenderer.updateAoLightValue();
            if (Config.isDynamicLights()) {
                DynamicLights.clear();
            }
            this.renderDistanceChunks = RenderGlobal.mc.gameSettings.renderDistanceChunks;
            this.renderDistance = this.renderDistanceChunks * 16;
            this.renderDistanceSq = this.renderDistance * this.renderDistance;
            boolean flag = this.vboEnabled;
            this.vboEnabled = OpenGlHelper.useVbo();
            if (flag && !this.vboEnabled) {
                this.renderContainer = new RenderList();
                this.renderChunkFactory = new ListChunkFactory();
            } else if (!flag && this.vboEnabled) {
                this.renderContainer = new VboRenderList();
                this.renderChunkFactory = new VboChunkFactory();
            }
            if (flag != this.vboEnabled) {
                this.generateStars();
                this.generateSky();
                this.generateSky2();
            }
            if (this.viewFrustum != null) {
                this.viewFrustum.deleteGlResources();
            }
            this.stopChunkUpdates();
            Set var5 = this.field_181024_n;
            Set set = this.field_181024_n;
            synchronized (set) {
                this.field_181024_n.clear();
            }
            this.viewFrustum = new ViewFrustum(this.theWorld, RenderGlobal.mc.gameSettings.renderDistanceChunks, this, this.renderChunkFactory);
            if (this.theWorld != null && (entity = mc.getRenderViewEntity()) != null) {
                this.viewFrustum.updateChunkPositions(entity.posX, entity.posZ);
            }
            this.renderEntitiesStartupCounter = 2;
        }
    }

    protected void stopChunkUpdates() {
        this.chunksToUpdate.clear();
        this.renderDispatcher.stopChunkUpdates();
    }

    public void createBindEntityOutlineFbs(int p_72720_1_, int p_72720_2_) {
        if (OpenGlHelper.shadersSupported && entityOutlineShader != null) {
            entityOutlineShader.createBindFramebuffers(p_72720_1_, p_72720_2_);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void renderEntities(Entity renderViewEntity, ICamera camera, float partialTicks) {
        int i = 0;
        if (Reflector.MinecraftForgeClient_getRenderPass.exists()) {
            i = Reflector.callInt((ReflectorMethod)Reflector.MinecraftForgeClient_getRenderPass, (Object[])new Object[0]);
        }
        if (this.renderEntitiesStartupCounter > 0) {
            if (i > 0) {
                return;
            }
            --this.renderEntitiesStartupCounter;
        } else {
            ClassInheritanceMultiMap<Entity> classinheritancemultimap;
            double d0 = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * (double)partialTicks;
            double d1 = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * (double)partialTicks;
            double d2 = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * (double)partialTicks;
            this.theWorld.theProfiler.startSection("prepare");
            TileEntityRendererDispatcher.instance.cacheActiveRenderInfo(this.theWorld, mc.getTextureManager(), RenderGlobal.mc.fontRendererObj, mc.getRenderViewEntity(), partialTicks);
            this.renderManager.cacheActiveRenderInfo(this.theWorld, RenderGlobal.mc.fontRendererObj, mc.getRenderViewEntity(), RenderGlobal.mc.pointedEntity, RenderGlobal.mc.gameSettings, partialTicks);
            if (i == 0) {
                this.countEntitiesTotal = 0;
                this.countEntitiesRendered = 0;
                this.countEntitiesHidden = 0;
                this.countTileEntitiesRendered = 0;
            }
            Entity entity = mc.getRenderViewEntity();
            double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
            double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
            double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;
            TileEntityRendererDispatcher.staticPlayerX = d3;
            TileEntityRendererDispatcher.staticPlayerY = d4;
            TileEntityRendererDispatcher.staticPlayerZ = d5;
            this.renderManager.setRenderPosition(d3, d4, d5);
            RenderGlobal.mc.entityRenderer.enableLightmap();
            this.theWorld.theProfiler.endStartSection("global");
            List<Entity> list = this.theWorld.getLoadedEntityList();
            if (i == 0) {
                this.countEntitiesTotal = list.size();
            }
            if (Config.isFogOff() && RenderGlobal.mc.entityRenderer.fogStandard) {
                GlStateManager.disableFog();
            }
            boolean flag = Reflector.ForgeEntity_shouldRenderInPass.exists();
            boolean flag1 = Reflector.ForgeTileEntity_shouldRenderInPass.exists();
            int j = 0;
            while (j < this.theWorld.weatherEffects.size()) {
                Entity entity1 = (Entity)this.theWorld.weatherEffects.get(j);
                if (!flag || Reflector.callBoolean((Object)entity1, (ReflectorMethod)Reflector.ForgeEntity_shouldRenderInPass, (Object[])new Object[]{i})) {
                    ++this.countEntitiesRendered;
                    if (entity1.isInRangeToRender3d(d0, d1, d2)) {
                        this.renderManager.renderEntitySimple(entity1, partialTicks);
                    }
                }
                ++j;
            }
            if (this.isRenderEntityOutlines()) {
                GlStateManager.depthFunc(519);
                GlStateManager.disableFog();
                entityOutlineFramebuffer.framebufferClear();
                entityOutlineFramebuffer.bindFramebuffer(false);
                this.theWorld.theProfiler.endStartSection("entityOutlines");
                RenderHelper.disableStandardItemLighting();
                this.renderManager.setRenderOutlines(true);
                int k = 0;
                while (k < list.size()) {
                    Entity entity3 = list.get(k);
                    if (!flag || Reflector.callBoolean((Object)entity3, (ReflectorMethod)Reflector.ForgeEntity_shouldRenderInPass, (Object[])new Object[]{i})) {
                        boolean flag3;
                        boolean flag2 = mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)mc.getRenderViewEntity()).isPlayerSleeping();
                        boolean bl = flag3 = entity3.isInRangeToRender3d(d0, d1, d2) && (entity3.ignoreFrustumCheck || camera.isBoundingBoxInFrustum(entity3.getEntityBoundingBox()) || entity3.riddenByEntity == RenderGlobal.mc.thePlayer) && entity3 instanceof EntityPlayer;
                        if ((entity3 != mc.getRenderViewEntity() || RenderGlobal.mc.gameSettings.thirdPersonView != 0 || flag2) && flag3) {
                            this.renderManager.renderEntitySimple(entity3, partialTicks);
                        }
                    }
                    ++k;
                }
                if (Client.INSTANCE.moduleManager.getModule("ChestESP").isState()) {
                    ESP.drawChestESP();
                }
                this.renderManager.setRenderOutlines(false);
                RenderHelper.enableStandardItemLighting();
                GlStateManager.depthMask(false);
                entityOutlineShader.loadShaderGroup(partialTicks);
                GlStateManager.enableLighting();
                GlStateManager.depthMask(true);
                mc.getFramebuffer().bindFramebuffer(false);
                GlStateManager.enableFog();
                GlStateManager.enableBlend();
                GlStateManager.enableColorMaterial();
                GlStateManager.depthFunc(515);
                GlStateManager.enableDepth();
                GlStateManager.enableAlpha();
            }
            this.theWorld.theProfiler.endStartSection("entities");
            boolean flag7 = Config.isShaders();
            if (flag7) {
                Shaders.beginEntities();
            }
            Iterator iterator1 = this.renderInfosEntities.iterator();
            boolean flag4 = RenderGlobal.mc.gameSettings.fancyGraphics;
            RenderGlobal.mc.gameSettings.fancyGraphics = Config.isDroppedItemsFancy();
            while (iterator1.hasNext()) {
                ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = (ContainerLocalRenderInformation)iterator1.next();
                Chunk chunk = this.theWorld.getChunkFromBlockCoords(renderglobal$containerlocalrenderinformation.renderChunk.getPosition());
                classinheritancemultimap = chunk.getEntityLists()[renderglobal$containerlocalrenderinformation.renderChunk.getPosition().getY() / 16];
                if (classinheritancemultimap.isEmpty()) continue;
                for (Entity entity2 : classinheritancemultimap) {
                    boolean flag5;
                    if (flag && !Reflector.callBoolean((Object)entity2, (ReflectorMethod)Reflector.ForgeEntity_shouldRenderInPass, (Object[])new Object[]{i}) && !(entity2 instanceof EntityArrow) && !(entity2 instanceof EntitySnowball)) continue;
                    boolean bl = flag5 = this.renderManager.shouldRender(entity2, camera, d0, d1, d2) || entity2.riddenByEntity == RenderGlobal.mc.thePlayer || entity2 instanceof EntityArrow || entity2 instanceof EntitySnowball;
                    if (flag5) {
                        boolean flag6;
                        boolean bl2 = flag6 = mc.getRenderViewEntity() instanceof EntityLivingBase ? ((EntityLivingBase)mc.getRenderViewEntity()).isPlayerSleeping() : false;
                        if (entity2 == mc.getRenderViewEntity() && RenderGlobal.mc.gameSettings.thirdPersonView == 0 && !flag6 || !(entity2.posY < 0.0) && !(entity2.posY >= 256.0) && !this.theWorld.isBlockLoaded(new BlockPos(entity2))) continue;
                        ++this.countEntitiesRendered;
                        if (entity2.getClass() == EntityItemFrame.class) {
                            entity2.renderDistanceWeight = 0.06;
                        }
                        this.renderedEntity = entity2;
                        if (flag7) {
                            Shaders.nextEntity((Entity)entity2);
                        }
                        this.renderManager.renderEntitySimple(entity2, partialTicks);
                        this.renderedEntity = null;
                    }
                    if (flag5 || !(entity2 instanceof EntityWitherSkull)) continue;
                    if (flag7) {
                        Shaders.nextEntity((Entity)entity2);
                    }
                    mc.getRenderManager().renderWitherSkull(entity2, partialTicks);
                }
            }
            RenderGlobal.mc.gameSettings.fancyGraphics = flag4;
            BasicFontRenderer fontrenderer = TileEntityRendererDispatcher.instance.getBasicFontRenderer();
            if (flag7) {
                Shaders.endEntities();
                Shaders.beginBlockEntities();
            }
            this.theWorld.theProfiler.endStartSection("blockentities");
            RenderHelper.enableStandardItemLighting();
            if (Reflector.ForgeTileEntityRendererDispatcher_preDrawBatch.exists()) {
                Reflector.call((Object)TileEntityRendererDispatcher.instance, (ReflectorMethod)Reflector.ForgeTileEntityRendererDispatcher_preDrawBatch, (Object[])new Object[0]);
            }
            for (Object renderglobal$containerlocalrenderinformation10 : this.renderInfosTileEntities) {
                ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation1 = (ContainerLocalRenderInformation)renderglobal$containerlocalrenderinformation10;
                List<TileEntity> list1 = renderglobal$containerlocalrenderinformation1.renderChunk.getCompiledChunk().getTileEntities();
                if (list1.isEmpty()) continue;
                for (TileEntity tileentity : list1) {
                    AxisAlignedBB axisalignedbb;
                    if (flag1 && (!Reflector.callBoolean((Object)tileentity, (ReflectorMethod)Reflector.ForgeTileEntity_shouldRenderInPass, (Object[])new Object[]{i}) || (axisalignedbb = (AxisAlignedBB)Reflector.call((Object)tileentity, (ReflectorMethod)Reflector.ForgeTileEntity_getRenderBoundingBox, (Object[])new Object[0])) != null && !camera.isBoundingBoxInFrustum(axisalignedbb))) continue;
                    Class<?> oclass = tileentity.getClass();
                    if (oclass == TileEntitySign.class && !Config.zoomMode) {
                        EntityPlayerSP entityplayer = RenderGlobal.mc.thePlayer;
                        double d6 = tileentity.getDistanceSq(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
                        if (d6 > 256.0) {
                            fontrenderer.setEnabled(false);
                        }
                    }
                    if (flag7) {
                        Shaders.nextBlockEntity((TileEntity)tileentity);
                    }
                    TileEntityRendererDispatcher.instance.renderTileEntity(tileentity, partialTicks, -1);
                    ++this.countTileEntitiesRendered;
                    fontrenderer.setEnabled(true);
                }
            }
            Set var32 = this.field_181024_n;
            classinheritancemultimap = this.field_181024_n;
            synchronized (classinheritancemultimap) {
                for (Object tileentity1 : this.field_181024_n) {
                    AxisAlignedBB axisalignedbb1;
                    if (flag1 && (!Reflector.callBoolean(tileentity1, (ReflectorMethod)Reflector.ForgeTileEntity_shouldRenderInPass, (Object[])new Object[]{i}) || (axisalignedbb1 = (AxisAlignedBB)Reflector.call(tileentity1, (ReflectorMethod)Reflector.ForgeTileEntity_getRenderBoundingBox, (Object[])new Object[0])) != null && !camera.isBoundingBoxInFrustum(axisalignedbb1))) continue;
                    Class<?> oclass1 = tileentity1.getClass();
                    if (oclass1 == TileEntitySign.class && !Config.zoomMode) {
                        EntityPlayerSP entityplayer1 = RenderGlobal.mc.thePlayer;
                        double d7 = ((TileEntity)tileentity1).getDistanceSq(entityplayer1.posX, entityplayer1.posY, entityplayer1.posZ);
                        if (d7 > 256.0) {
                            fontrenderer.setEnabled(false);
                        }
                    }
                    if (flag7) {
                        Shaders.nextBlockEntity((TileEntity)((TileEntity)tileentity1));
                    }
                    TileEntityRendererDispatcher.instance.renderTileEntity((TileEntity)tileentity1, partialTicks, -1);
                    fontrenderer.setEnabled(true);
                }
            }
            if (Reflector.ForgeTileEntityRendererDispatcher_drawBatch.exists()) {
                Reflector.call((Object)TileEntityRendererDispatcher.instance, (ReflectorMethod)Reflector.ForgeTileEntityRendererDispatcher_drawBatch, (Object[])new Object[]{i});
            }
            this.preRenderDamagedBlocks();
            for (Object destroyblockprogress : this.damagedBlocks.values()) {
                boolean flag8;
                BlockPos blockpos = ((DestroyBlockProgress)destroyblockprogress).getPosition();
                TileEntity tileentity2 = this.theWorld.getTileEntity(blockpos);
                if (tileentity2 instanceof TileEntityChest) {
                    TileEntityChest tileentitychest = (TileEntityChest)tileentity2;
                    if (tileentitychest.adjacentChestXNeg != null) {
                        blockpos = blockpos.offset(EnumFacing.WEST);
                        tileentity2 = this.theWorld.getTileEntity(blockpos);
                    } else if (tileentitychest.adjacentChestZNeg != null) {
                        blockpos = blockpos.offset(EnumFacing.NORTH);
                        tileentity2 = this.theWorld.getTileEntity(blockpos);
                    }
                }
                Block block = this.theWorld.getBlockState(blockpos).getBlock();
                if (flag1) {
                    AxisAlignedBB axisalignedbb2;
                    flag8 = false;
                    if (tileentity2 != null && Reflector.callBoolean((Object)tileentity2, (ReflectorMethod)Reflector.ForgeTileEntity_shouldRenderInPass, (Object[])new Object[]{i}) && Reflector.callBoolean((Object)tileentity2, (ReflectorMethod)Reflector.ForgeTileEntity_canRenderBreaking, (Object[])new Object[0]) && (axisalignedbb2 = (AxisAlignedBB)Reflector.call((Object)tileentity2, (ReflectorMethod)Reflector.ForgeTileEntity_getRenderBoundingBox, (Object[])new Object[0])) != null) {
                        flag8 = camera.isBoundingBoxInFrustum(axisalignedbb2);
                    }
                } else {
                    boolean bl = flag8 = tileentity2 != null && (block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockSign || block instanceof BlockSkull);
                }
                if (!flag8) continue;
                if (flag7) {
                    Shaders.nextBlockEntity((TileEntity)tileentity2);
                }
                TileEntityRendererDispatcher.instance.renderTileEntity(tileentity2, partialTicks, ((DestroyBlockProgress)destroyblockprogress).getPartialBlockDamage());
            }
            this.postRenderDamagedBlocks();
            RenderGlobal.mc.entityRenderer.disableLightmap();
            RenderGlobal.mc.mcProfiler.endSection();
        }
    }

    public String getDebugInfoRenders() {
        int i = this.viewFrustum.renderChunks.length;
        int j = 0;
        for (Object renderglobal$containerlocalrenderinformation0 : this.renderInfos) {
            ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = (ContainerLocalRenderInformation)renderglobal$containerlocalrenderinformation0;
            CompiledChunk compiledchunk = renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk;
            if (compiledchunk == CompiledChunk.DUMMY || compiledchunk.isEmpty()) continue;
            ++j;
        }
        return String.format("C: %d/%d %sD: %d, %s", j, i, RenderGlobal.mc.renderChunksMany ? "(s) " : "", this.renderDistanceChunks, this.renderDispatcher.getDebugInfo());
    }

    public String getDebugInfoEntities() {
        return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ", B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered) + ", " + Config.getVersionDebug();
    }

    public void setupTerrain(Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator) {
        if (RenderGlobal.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks) {
            this.loadRenderers();
        }
        this.theWorld.theProfiler.startSection("camera");
        double d0 = viewEntity.posX - this.frustumUpdatePosX;
        double d1 = viewEntity.posY - this.frustumUpdatePosY;
        double d2 = viewEntity.posZ - this.frustumUpdatePosZ;
        if (this.frustumUpdatePosChunkX != viewEntity.chunkCoordX || this.frustumUpdatePosChunkY != viewEntity.chunkCoordY || this.frustumUpdatePosChunkZ != viewEntity.chunkCoordZ || d0 * d0 + d1 * d1 + d2 * d2 > 16.0) {
            this.frustumUpdatePosX = viewEntity.posX;
            this.frustumUpdatePosY = viewEntity.posY;
            this.frustumUpdatePosZ = viewEntity.posZ;
            this.frustumUpdatePosChunkX = viewEntity.chunkCoordX;
            this.frustumUpdatePosChunkY = viewEntity.chunkCoordY;
            this.frustumUpdatePosChunkZ = viewEntity.chunkCoordZ;
            this.viewFrustum.updateChunkPositions(viewEntity.posX, viewEntity.posZ);
        }
        if (Config.isDynamicLights()) {
            DynamicLights.update((RenderGlobal)this);
        }
        this.theWorld.theProfiler.endStartSection("renderlistcamera");
        double d3 = viewEntity.lastTickPosX + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
        double d4 = viewEntity.lastTickPosY + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
        double d5 = viewEntity.lastTickPosZ + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;
        this.renderContainer.initialize(d3, d4, d5);
        this.theWorld.theProfiler.endStartSection("cull");
        if (this.debugFixedClippingHelper != null) {
            Frustum frustum = new Frustum(this.debugFixedClippingHelper);
            frustum.setPosition(this.debugTerrainFrustumPosition.field_181059_a, this.debugTerrainFrustumPosition.field_181060_b, this.debugTerrainFrustumPosition.field_181061_c);
            camera = frustum;
        }
        RenderGlobal.mc.mcProfiler.endStartSection("culling");
        BlockPos blockpos2 = new BlockPos(d3, d4 + (double)viewEntity.getEyeHeight(), d5);
        RenderChunk renderchunk = this.viewFrustum.getRenderChunk(blockpos2);
        BlockPos blockpos = new BlockPos(MathHelper.floor_double(d3 / 16.0) * 16, MathHelper.floor_double(d4 / 16.0) * 16, MathHelper.floor_double(d5 / 16.0) * 16);
        this.displayListEntitiesDirty = this.displayListEntitiesDirty || !this.chunksToUpdate.isEmpty() || viewEntity.posX != this.lastViewEntityX || viewEntity.posY != this.lastViewEntityY || viewEntity.posZ != this.lastViewEntityZ || (double)viewEntity.rotationPitch != this.lastViewEntityPitch || (double)viewEntity.rotationYaw != this.lastViewEntityYaw;
        this.lastViewEntityX = viewEntity.posX;
        this.lastViewEntityY = viewEntity.posY;
        this.lastViewEntityZ = viewEntity.posZ;
        this.lastViewEntityPitch = viewEntity.rotationPitch;
        this.lastViewEntityYaw = viewEntity.rotationYaw;
        boolean flag = this.debugFixedClippingHelper != null;
        Lagometer.timerVisibility.start();
        if (Shaders.isShadowPass) {
            this.renderInfos = this.renderInfosShadow;
            this.renderInfosEntities = this.renderInfosEntitiesShadow;
            this.renderInfosTileEntities = this.renderInfosTileEntitiesShadow;
            if (!flag && this.displayListEntitiesDirty) {
                this.renderInfos.clear();
                this.renderInfosEntities.clear();
                this.renderInfosTileEntities.clear();
                RenderInfoLazy renderinfolazy = new RenderInfoLazy();
                Iterator iterator = ShadowUtils.makeShadowChunkIterator((WorldClient)this.theWorld, (double)partialTicks, (Entity)viewEntity, (int)this.renderDistanceChunks, (ViewFrustum)this.viewFrustum);
                while (iterator.hasNext()) {
                    BlockPos blockpos1;
                    RenderChunk renderchunk1 = (RenderChunk)iterator.next();
                    if (renderchunk1 == null) continue;
                    renderinfolazy.setRenderChunk(renderchunk1);
                    if (!renderchunk1.compiledChunk.isEmpty() || renderchunk1.isNeedsUpdate()) {
                        this.renderInfos.add(renderinfolazy.getRenderInfo());
                    }
                    if (ChunkUtils.hasEntities((Chunk)this.theWorld.getChunkFromBlockCoords(blockpos1 = renderchunk1.getPosition()))) {
                        this.renderInfosEntities.add(renderinfolazy.getRenderInfo());
                    }
                    if (renderchunk1.getCompiledChunk().getTileEntities().size() <= 0) continue;
                    this.renderInfosTileEntities.add(renderinfolazy.getRenderInfo());
                }
            }
        } else {
            this.renderInfos = this.renderInfosNormal;
            this.renderInfosEntities = this.renderInfosEntitiesNormal;
            this.renderInfosTileEntities = this.renderInfosTileEntitiesNormal;
        }
        if (!flag && this.displayListEntitiesDirty && !Shaders.isShadowPass) {
            this.displayListEntitiesDirty = false;
            this.renderInfos.clear();
            this.renderInfosEntities.clear();
            this.renderInfosTileEntities.clear();
            this.visibilityDeque.clear();
            Deque deque = this.visibilityDeque;
            boolean flag1 = RenderGlobal.mc.renderChunksMany;
            if (renderchunk != null) {
                boolean flag2 = false;
                ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation3 = new ContainerLocalRenderInformation(renderchunk, null, 0, null);
                Set set1 = SET_ALL_FACINGS;
                if (set1.size() == 1) {
                    Vector3f vector3f = this.getViewVector(viewEntity, partialTicks);
                    EnumFacing enumfacing = EnumFacing.getFacingFromVector(vector3f.x, vector3f.y, vector3f.z).getOpposite();
                    set1.remove(enumfacing);
                }
                if (set1.isEmpty()) {
                    flag2 = true;
                }
                if (flag2 && !playerSpectator) {
                    this.renderInfos.add(renderglobal$containerlocalrenderinformation3);
                } else {
                    if (playerSpectator && this.theWorld.getBlockState(blockpos2).getBlock().isOpaqueCube()) {
                        flag1 = false;
                    }
                    renderchunk.setFrameIndex(frameCount);
                    deque.add(renderglobal$containerlocalrenderinformation3);
                }
            } else {
                int i = blockpos2.getY() > 0 ? 248 : 8;
                int j = -this.renderDistanceChunks;
                while (j <= this.renderDistanceChunks) {
                    int k = -this.renderDistanceChunks;
                    while (k <= this.renderDistanceChunks) {
                        RenderChunk renderchunk2 = this.viewFrustum.getRenderChunk(new BlockPos((j << 4) + 8, i, (k << 4) + 8));
                        if (renderchunk2 != null && camera.isBoundingBoxInFrustum(renderchunk2.boundingBox)) {
                            renderchunk2.setFrameIndex(frameCount);
                            deque.add(new ContainerLocalRenderInformation(renderchunk2, null, 0, null));
                        }
                        ++k;
                    }
                    ++j;
                }
            }
            EnumFacing[] aenumfacing = EnumFacing.VALUES;
            int l = aenumfacing.length;
            while (!deque.isEmpty()) {
                ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation1 = (ContainerLocalRenderInformation)deque.poll();
                RenderChunk renderchunk4 = renderglobal$containerlocalrenderinformation1.renderChunk;
                EnumFacing enumfacing2 = renderglobal$containerlocalrenderinformation1.facing;
                BlockPos blockpos3 = renderchunk4.getPosition();
                if (!renderchunk4.compiledChunk.isEmpty() || renderchunk4.isNeedsUpdate()) {
                    this.renderInfos.add(renderglobal$containerlocalrenderinformation1);
                }
                if (ChunkUtils.hasEntities((Chunk)this.theWorld.getChunkFromBlockCoords(blockpos3))) {
                    this.renderInfosEntities.add(renderglobal$containerlocalrenderinformation1);
                }
                if (renderchunk4.getCompiledChunk().getTileEntities().size() > 0) {
                    this.renderInfosTileEntities.add(renderglobal$containerlocalrenderinformation1);
                }
                int i1 = 0;
                while (i1 < l) {
                    RenderChunk renderchunk3;
                    EnumFacing enumfacing1 = aenumfacing[i1];
                    if (!(flag1 && renderglobal$containerlocalrenderinformation1.setFacing.contains(enumfacing1.getOpposite()) || flag1 && enumfacing2 != null && !renderchunk4.getCompiledChunk().isVisible(enumfacing2.getOpposite(), enumfacing1) || (renderchunk3 = this.func_181562_a(blockpos2, renderchunk4, enumfacing1)) == null || !renderchunk3.setFrameIndex(frameCount) || !camera.isBoundingBoxInFrustum(renderchunk3.boundingBox))) {
                        ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = new ContainerLocalRenderInformation(renderchunk3, enumfacing1, renderglobal$containerlocalrenderinformation1.counter + 1, null);
                        renderglobal$containerlocalrenderinformation.setFacing.addAll(renderglobal$containerlocalrenderinformation1.setFacing);
                        renderglobal$containerlocalrenderinformation.setFacing.add(enumfacing1);
                        deque.add(renderglobal$containerlocalrenderinformation);
                    }
                    ++i1;
                }
            }
        }
        if (this.debugFixTerrainFrustum) {
            this.fixTerrainFrustum(d3, d4, d5);
            this.debugFixTerrainFrustum = false;
        }
        Lagometer.timerVisibility.end();
        if (Shaders.isShadowPass) {
            Shaders.mcProfilerEndSection();
        } else {
            this.renderDispatcher.clearChunkUpdates();
            Set set = this.chunksToUpdate;
            this.chunksToUpdate = Sets.newLinkedHashSet();
            Iterator iterator1 = this.renderInfos.iterator();
            Lagometer.timerChunkUpdate.start();
            while (iterator1.hasNext()) {
                ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation2 = (ContainerLocalRenderInformation)iterator1.next();
                RenderChunk renderchunk5 = renderglobal$containerlocalrenderinformation2.renderChunk;
                if (!renderchunk5.isNeedsUpdate() && !set.contains(renderchunk5)) continue;
                this.displayListEntitiesDirty = true;
                if (this.isPositionInRenderChunk(blockpos, renderglobal$containerlocalrenderinformation2.renderChunk)) {
                    if (!renderchunk5.isPlayerUpdate()) {
                        this.chunksToUpdateForced.add(renderchunk5);
                        continue;
                    }
                    RenderGlobal.mc.mcProfiler.startSection("build near");
                    this.renderDispatcher.updateChunkNow(renderchunk5);
                    renderchunk5.setNeedsUpdate(false);
                    RenderGlobal.mc.mcProfiler.endSection();
                    continue;
                }
                this.chunksToUpdate.add(renderchunk5);
            }
            Lagometer.timerChunkUpdate.end();
            this.chunksToUpdate.addAll(set);
            RenderGlobal.mc.mcProfiler.endSection();
        }
    }

    private boolean isPositionInRenderChunk(BlockPos pos, RenderChunk renderChunkIn) {
        BlockPos blockpos = renderChunkIn.getPosition();
        return MathHelper.abs_int(pos.getX() - blockpos.getX()) > 16 ? false : (MathHelper.abs_int(pos.getY() - blockpos.getY()) > 16 ? false : MathHelper.abs_int(pos.getZ() - blockpos.getZ()) <= 16);
    }

    private Set getVisibleFacings(BlockPos pos) {
        VisGraph visgraph = new VisGraph();
        BlockPos blockpos = new BlockPos(pos.getX() >> 4 << 4, pos.getY() >> 4 << 4, pos.getZ() >> 4 << 4);
        Chunk chunk = this.theWorld.getChunkFromBlockCoords(blockpos);
        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(blockpos, blockpos.add(15, 15, 15))) {
            if (!chunk.getBlock(blockpos$mutableblockpos).isOpaqueCube()) continue;
            visgraph.func_178606_a(blockpos$mutableblockpos);
        }
        return visgraph.func_178609_b(pos);
    }

    private RenderChunk func_181562_a(BlockPos p_181562_1_, RenderChunk p_181562_2_, EnumFacing p_181562_3_) {
        BlockPos blockpos = p_181562_2_.getPositionOffset16(p_181562_3_);
        if (blockpos.getY() >= 0 && blockpos.getY() < 256) {
            int k;
            int i = MathHelper.abs_int(p_181562_1_.getX() - blockpos.getX());
            int j = MathHelper.abs_int(p_181562_1_.getZ() - blockpos.getZ());
            if (Config.isFogOff() ? i > this.renderDistance || j > this.renderDistance : (k = i * i + j * j) > this.renderDistanceSq) {
                return null;
            }
            return this.viewFrustum.getRenderChunk(blockpos);
        }
        return null;
    }

    private void fixTerrainFrustum(double x, double y, double z) {
        this.debugFixedClippingHelper = new ClippingHelperImpl();
        ((ClippingHelperImpl)this.debugFixedClippingHelper).init();
        net.minecraft.util.Matrix4f matrix4f = new net.minecraft.util.Matrix4f(this.debugFixedClippingHelper.modelviewMatrix);
        matrix4f.transpose();
        net.minecraft.util.Matrix4f matrix4f1 = new net.minecraft.util.Matrix4f(this.debugFixedClippingHelper.projectionMatrix);
        matrix4f1.transpose();
        net.minecraft.util.Matrix4f matrix4f2 = new net.minecraft.util.Matrix4f();
        net.minecraft.util.Matrix4f.mul((Matrix4f)matrix4f1, (Matrix4f)matrix4f, (Matrix4f)matrix4f2);
        matrix4f2.invert();
        this.debugTerrainFrustumPosition.field_181059_a = x;
        this.debugTerrainFrustumPosition.field_181060_b = y;
        this.debugTerrainFrustumPosition.field_181061_c = z;
        this.debugTerrainMatrix[0] = new Vector4f(-1.0f, -1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix[1] = new Vector4f(1.0f, -1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix[2] = new Vector4f(1.0f, 1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix[3] = new Vector4f(-1.0f, 1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix[4] = new Vector4f(-1.0f, -1.0f, 1.0f, 1.0f);
        this.debugTerrainMatrix[5] = new Vector4f(1.0f, -1.0f, 1.0f, 1.0f);
        this.debugTerrainMatrix[6] = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.debugTerrainMatrix[7] = new Vector4f(-1.0f, 1.0f, 1.0f, 1.0f);
        int i = 0;
        while (i < 8) {
            net.minecraft.util.Matrix4f.transform((Matrix4f)matrix4f2, (Vector4f)this.debugTerrainMatrix[i], (Vector4f)this.debugTerrainMatrix[i]);
            this.debugTerrainMatrix[i].x /= this.debugTerrainMatrix[i].w;
            this.debugTerrainMatrix[i].y /= this.debugTerrainMatrix[i].w;
            this.debugTerrainMatrix[i].z /= this.debugTerrainMatrix[i].w;
            this.debugTerrainMatrix[i].w = 1.0f;
            ++i;
        }
    }

    protected Vector3f getViewVector(Entity entityIn, double partialTicks) {
        float f = (float)((double)entityIn.prevRotationPitch + (double)(entityIn.rotationPitch - entityIn.prevRotationPitch) * partialTicks);
        float f1 = (float)((double)entityIn.prevRotationYaw + (double)(entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks);
        if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
            f += 180.0f;
        }
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180));
        return new Vector3f(f3 * f4, f5, f2 * f4);
    }

    public int renderBlockLayer(EnumWorldBlockLayer blockLayerIn, double partialTicks, int pass, Entity entityIn) {
        RenderHelper.disableStandardItemLighting();
        if (blockLayerIn == EnumWorldBlockLayer.TRANSLUCENT) {
            RenderGlobal.mc.mcProfiler.startSection("translucent_sort");
            double d0 = entityIn.posX - this.prevRenderSortX;
            double d1 = entityIn.posY - this.prevRenderSortY;
            double d2 = entityIn.posZ - this.prevRenderSortZ;
            if (d0 * d0 + d1 * d1 + d2 * d2 > 1.0) {
                this.prevRenderSortX = entityIn.posX;
                this.prevRenderSortY = entityIn.posY;
                this.prevRenderSortZ = entityIn.posZ;
                int k = 0;
                Iterator iterator = this.renderInfos.iterator();
                this.chunksToResortTransparency.clear();
                while (iterator.hasNext()) {
                    ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = (ContainerLocalRenderInformation)iterator.next();
                    if (!renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk.isLayerStarted(blockLayerIn) || k++ >= 15) continue;
                    this.chunksToResortTransparency.add(renderglobal$containerlocalrenderinformation.renderChunk);
                }
            }
            RenderGlobal.mc.mcProfiler.endSection();
        }
        RenderGlobal.mc.mcProfiler.startSection("filterempty");
        int l = 0;
        boolean flag = blockLayerIn == EnumWorldBlockLayer.TRANSLUCENT;
        int i1 = flag ? this.renderInfos.size() - 1 : 0;
        int i = flag ? -1 : this.renderInfos.size();
        int j1 = flag ? -1 : 1;
        int j = i1;
        while (j != i) {
            RenderChunk renderchunk = ((ContainerLocalRenderInformation)this.renderInfos.get((int)j)).renderChunk;
            if (!renderchunk.getCompiledChunk().isLayerEmpty(blockLayerIn)) {
                ++l;
                this.renderContainer.addRenderChunk(renderchunk, blockLayerIn);
            }
            j += j1;
        }
        if (l == 0) {
            RenderGlobal.mc.mcProfiler.endSection();
            return l;
        }
        if (Config.isFogOff() && RenderGlobal.mc.entityRenderer.fogStandard) {
            GlStateManager.disableFog();
        }
        RenderGlobal.mc.mcProfiler.endStartSection("render_" + (Object)((Object)blockLayerIn));
        this.renderBlockLayer(blockLayerIn);
        RenderGlobal.mc.mcProfiler.endSection();
        return l;
    }

    private void renderBlockLayer(EnumWorldBlockLayer blockLayerIn) {
        RenderGlobal.mc.entityRenderer.enableLightmap();
        if (OpenGlHelper.useVbo()) {
            GL11.glEnableClientState((int)32884);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            GL11.glEnableClientState((int)32888);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glEnableClientState((int)32888);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            GL11.glEnableClientState((int)32886);
        }
        if (Config.isShaders()) {
            ShadersRender.preRenderChunkLayer((EnumWorldBlockLayer)blockLayerIn);
        }
        this.renderContainer.renderChunkLayer(blockLayerIn);
        if (Config.isShaders()) {
            ShadersRender.postRenderChunkLayer((EnumWorldBlockLayer)blockLayerIn);
        }
        if (OpenGlHelper.useVbo()) {
            for (VertexFormatElement vertexformatelement : DefaultVertexFormats.BLOCK.getElements()) {
                VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();
                int i = vertexformatelement.getIndex();
                switch (vertexformatelement$enumusage) {
                    case POSITION: {
                        GL11.glDisableClientState((int)32884);
                        break;
                    }
                    case UV: {
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + i);
                        GL11.glDisableClientState((int)32888);
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                        break;
                    }
                    case COLOR: {
                        GL11.glDisableClientState((int)32886);
                        GlStateManager.resetColor();
                    }
                }
            }
        }
        RenderGlobal.mc.entityRenderer.disableLightmap();
    }

    private void cleanupDamagedBlocks(Iterator iteratorIn) {
        while (iteratorIn.hasNext()) {
            DestroyBlockProgress destroyblockprogress = (DestroyBlockProgress)iteratorIn.next();
            int i = destroyblockprogress.getCreationCloudUpdateTick();
            if (this.cloudTickCounter - i <= 400) continue;
            iteratorIn.remove();
        }
    }

    public void updateClouds() {
        if (Config.isShaders() && Keyboard.isKeyDown((int)61) && Keyboard.isKeyDown((int)19)) {
            Shaders.uninit();
            Shaders.loadShaderPack();
        }
        ++this.cloudTickCounter;
        if (this.cloudTickCounter % 20 == 0) {
            this.cleanupDamagedBlocks(this.damagedBlocks.values().iterator());
        }
    }

    private void renderSkyEnd() {
        if (Config.isSkyEnabled()) {
            GlStateManager.disableFog();
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.depthMask(false);
            this.renderEngine.bindTexture(locationEndSkyPng);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            int i = 0;
            while (i < 6) {
                GlStateManager.pushMatrix();
                if (i == 1) {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (i == 2) {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (i == 3) {
                    GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
                }
                if (i == 4) {
                    GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                }
                if (i == 5) {
                    GlStateManager.rotate(-90.0f, 0.0f, 0.0f, 1.0f);
                }
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldrenderer.pos(-100.0, -100.0, -100.0).tex(0.0, 0.0).color(40, 40, 40, 255).endVertex();
                worldrenderer.pos(-100.0, -100.0, 100.0).tex(0.0, 16.0).color(40, 40, 40, 255).endVertex();
                worldrenderer.pos(100.0, -100.0, 100.0).tex(16.0, 16.0).color(40, 40, 40, 255).endVertex();
                worldrenderer.pos(100.0, -100.0, -100.0).tex(16.0, 0.0).color(40, 40, 40, 255).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
                ++i;
            }
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
        }
    }

    public void renderSky(float partialTicks, int pass) {
        WorldProvider worldprovider;
        Object object;
        if (Reflector.ForgeWorldProvider_getSkyRenderer.exists() && (object = Reflector.call((Object)(worldprovider = RenderGlobal.mc.theWorld.provider), (ReflectorMethod)Reflector.ForgeWorldProvider_getSkyRenderer, (Object[])new Object[0])) != null) {
            Reflector.callVoid((Object)object, (ReflectorMethod)Reflector.IRenderHandler_render, (Object[])new Object[]{Float.valueOf(partialTicks), this.theWorld, mc});
            return;
        }
        if (RenderGlobal.mc.theWorld.provider.getDimensionId() == 1) {
            this.renderSkyEnd();
        } else if (RenderGlobal.mc.theWorld.provider.isSurfaceWorld()) {
            float f24;
            GlStateManager.disableTexture2D();
            boolean flag1 = Config.isShaders();
            if (flag1) {
                Shaders.disableTexture2D();
            }
            Vec3 vec3 = this.theWorld.getSkyColor(mc.getRenderViewEntity(), partialTicks);
            vec3 = CustomColors.getSkyColor((Vec3)vec3, (IBlockAccess)RenderGlobal.mc.theWorld, (double)RenderGlobal.mc.getRenderViewEntity().posX, (double)(RenderGlobal.mc.getRenderViewEntity().posY + 1.0), (double)RenderGlobal.mc.getRenderViewEntity().posZ);
            if (flag1) {
                Shaders.setSkyColor((Vec3)vec3);
            }
            float f = (float)vec3.xCoord;
            float f1 = (float)vec3.yCoord;
            float f2 = (float)vec3.zCoord;
            if (pass != 2) {
                float f3 = (f * 30.0f + f1 * 59.0f + f2 * 11.0f) / 100.0f;
                float f4 = (f * 30.0f + f1 * 70.0f) / 100.0f;
                float f5 = (f * 30.0f + f2 * 70.0f) / 100.0f;
                f = f3;
                f1 = f4;
                f2 = f5;
            }
            GlStateManager.color(f, f1, f2);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            GlStateManager.depthMask(false);
            GlStateManager.enableFog();
            if (flag1) {
                Shaders.enableFog();
            }
            GlStateManager.color(f, f1, f2);
            if (flag1) {
                Shaders.preSkyList();
            }
            if (Config.isSkyEnabled()) {
                if (this.vboEnabled) {
                    this.skyVBO.bindBuffer();
                    GL11.glEnableClientState((int)32884);
                    GL11.glVertexPointer((int)3, (int)5126, (int)12, (long)0L);
                    this.skyVBO.drawArrays(7);
                    this.skyVBO.unbindBuffer();
                    GL11.glDisableClientState((int)32884);
                } else {
                    GlStateManager.callList(this.glSkyList);
                }
            }
            GlStateManager.disableFog();
            if (flag1) {
                Shaders.disableFog();
            }
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.disableStandardItemLighting();
            float[] afloat = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(partialTicks), partialTicks);
            if (afloat != null && Config.isSunMoonEnabled()) {
                GlStateManager.disableTexture2D();
                if (flag1) {
                    Shaders.disableTexture2D();
                }
                GlStateManager.shadeModel(7425);
                GlStateManager.pushMatrix();
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(MathHelper.sin(this.theWorld.getCelestialAngleRadians(partialTicks)) < 0.0f ? 180.0f : 0.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                float f6 = afloat[0];
                float f7 = afloat[1];
                float f8 = afloat[2];
                if (pass != 2) {
                    float f9 = (f6 * 30.0f + f7 * 59.0f + f8 * 11.0f) / 100.0f;
                    float f10 = (f6 * 30.0f + f7 * 70.0f) / 100.0f;
                    float f11 = (f6 * 30.0f + f8 * 70.0f) / 100.0f;
                    f6 = f9;
                    f7 = f10;
                    f8 = f11;
                }
                worldrenderer.begin(6, DefaultVertexFormats.POSITION_COLOR);
                worldrenderer.pos(0.0, 100.0, 0.0).color(f6, f7, f8, afloat[3]).endVertex();
                boolean flag = true;
                int i = 0;
                while (i <= 16) {
                    float f20 = (float)i * (float)Math.PI * 2.0f / 16.0f;
                    float f12 = MathHelper.sin(f20);
                    float f13 = MathHelper.cos(f20);
                    worldrenderer.pos(f12 * 120.0f, f13 * 120.0f, -f13 * 40.0f * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0.0f).endVertex();
                    ++i;
                }
                tessellator.draw();
                GlStateManager.popMatrix();
                GlStateManager.shadeModel(7424);
            }
            GlStateManager.enableTexture2D();
            if (flag1) {
                Shaders.enableTexture2D();
            }
            GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
            GlStateManager.pushMatrix();
            float f15 = 1.0f - this.theWorld.getRainStrength(partialTicks);
            GlStateManager.color(1.0f, 1.0f, 1.0f, f15);
            GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
            CustomSky.renderSky((World)this.theWorld, (TextureManager)this.renderEngine, (float)this.theWorld.getCelestialAngle(partialTicks), (float)f15);
            if (flag1) {
                Shaders.preCelestialRotate();
            }
            GlStateManager.rotate(this.theWorld.getCelestialAngle(partialTicks) * 360.0f, 1.0f, 0.0f, 0.0f);
            if (flag1) {
                Shaders.postCelestialRotate();
            }
            float f16 = 30.0f;
            if (Config.isSunTexture()) {
                this.renderEngine.bindTexture(locationSunPng);
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
                worldrenderer.pos(-f16, 100.0, -f16).tex(0.0, 0.0).endVertex();
                worldrenderer.pos(f16, 100.0, -f16).tex(1.0, 0.0).endVertex();
                worldrenderer.pos(f16, 100.0, f16).tex(1.0, 1.0).endVertex();
                worldrenderer.pos(-f16, 100.0, f16).tex(0.0, 1.0).endVertex();
                tessellator.draw();
            }
            f16 = 20.0f;
            if (Config.isMoonTexture()) {
                this.renderEngine.bindTexture(locationMoonPhasesPng);
                int l = this.theWorld.getMoonPhase();
                int j = l % 4;
                int k = l / 4 % 2;
                float f21 = (float)(j + 0) / 4.0f;
                float f22 = (float)(k + 0) / 2.0f;
                float f23 = (float)(j + 1) / 4.0f;
                float f14 = (float)(k + 1) / 2.0f;
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
                worldrenderer.pos(-f16, -100.0, f16).tex(f23, f14).endVertex();
                worldrenderer.pos(f16, -100.0, f16).tex(f21, f14).endVertex();
                worldrenderer.pos(f16, -100.0, -f16).tex(f21, f22).endVertex();
                worldrenderer.pos(-f16, -100.0, -f16).tex(f23, f22).endVertex();
                tessellator.draw();
            }
            GlStateManager.disableTexture2D();
            if (flag1) {
                Shaders.disableTexture2D();
            }
            if ((f24 = this.theWorld.getStarBrightness(partialTicks) * f15) > 0.0f && Config.isStarsEnabled() && !CustomSky.hasSkyLayers((World)this.theWorld)) {
                GlStateManager.color(f24, f24, f24, f24);
                if (this.vboEnabled) {
                    this.starVBO.bindBuffer();
                    GL11.glEnableClientState((int)32884);
                    GL11.glVertexPointer((int)3, (int)5126, (int)12, (long)0L);
                    this.starVBO.drawArrays(7);
                    this.starVBO.unbindBuffer();
                    GL11.glDisableClientState((int)32884);
                } else {
                    GlStateManager.callList(this.starGLCallList);
                }
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableFog();
            if (flag1) {
                Shaders.enableFog();
            }
            GlStateManager.popMatrix();
            GlStateManager.disableTexture2D();
            if (flag1) {
                Shaders.disableTexture2D();
            }
            GlStateManager.color(0.0f, 0.0f, 0.0f);
            double d0 = RenderGlobal.mc.thePlayer.getPositionEyes((float)partialTicks).yCoord - this.theWorld.getHorizon();
            if (d0 < 0.0) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0f, 12.0f, 0.0f);
                if (this.vboEnabled) {
                    this.sky2VBO.bindBuffer();
                    GL11.glEnableClientState((int)32884);
                    GL11.glVertexPointer((int)3, (int)5126, (int)12, (long)0L);
                    this.sky2VBO.drawArrays(7);
                    this.sky2VBO.unbindBuffer();
                    GL11.glDisableClientState((int)32884);
                } else {
                    GlStateManager.callList(this.glSkyList2);
                }
                GlStateManager.popMatrix();
                float f17 = 1.0f;
                float f18 = -((float)(d0 + 65.0));
                float f19 = -1.0f;
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                worldrenderer.pos(-1.0, f18, 1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(1.0, f18, 1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(-1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(-1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(1.0, f18, -1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(-1.0, f18, -1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(1.0, f18, 1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(1.0, f18, -1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(-1.0, f18, -1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(-1.0, f18, 1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(-1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(-1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(-1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(-1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos(1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
                tessellator.draw();
            }
            if (this.theWorld.provider.isSkyColored()) {
                GlStateManager.color(f * 0.2f + 0.04f, f1 * 0.2f + 0.04f, f2 * 0.6f + 0.1f);
            } else {
                GlStateManager.color(f, f1, f2);
            }
            if (RenderGlobal.mc.gameSettings.renderDistanceChunks <= 4) {
                GlStateManager.color(RenderGlobal.mc.entityRenderer.fogColorRed, RenderGlobal.mc.entityRenderer.fogColorGreen, RenderGlobal.mc.entityRenderer.fogColorBlue);
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, -((float)(d0 - 16.0)), 0.0f);
            if (Config.isSkyEnabled()) {
                GlStateManager.callList(this.glSkyList2);
            }
            GlStateManager.popMatrix();
            GlStateManager.enableTexture2D();
            if (flag1) {
                Shaders.enableTexture2D();
            }
            GlStateManager.depthMask(true);
        }
    }

    public void renderClouds(float partialTicks, int pass) {
        if (!Config.isCloudsOff()) {
            WorldProvider worldprovider;
            Object object;
            if (Reflector.ForgeWorldProvider_getCloudRenderer.exists() && (object = Reflector.call((Object)(worldprovider = RenderGlobal.mc.theWorld.provider), (ReflectorMethod)Reflector.ForgeWorldProvider_getCloudRenderer, (Object[])new Object[0])) != null) {
                Reflector.callVoid((Object)object, (ReflectorMethod)Reflector.IRenderHandler_render, (Object[])new Object[]{Float.valueOf(partialTicks), this.theWorld, mc});
                return;
            }
            if (RenderGlobal.mc.theWorld.provider.isSurfaceWorld()) {
                if (Config.isShaders()) {
                    Shaders.beginClouds();
                }
                if (Config.isCloudsFancy()) {
                    this.renderCloudsFancy(partialTicks, pass);
                } else {
                    this.cloudRenderer.prepareToRender(false, this.cloudTickCounter, partialTicks);
                    partialTicks = 0.0f;
                    GlStateManager.disableCull();
                    float f9 = (float)(RenderGlobal.mc.getRenderViewEntity().lastTickPosY + (RenderGlobal.mc.getRenderViewEntity().posY - RenderGlobal.mc.getRenderViewEntity().lastTickPosY) * (double)partialTicks);
                    boolean flag = true;
                    boolean flag1 = true;
                    Tessellator tessellator = Tessellator.getInstance();
                    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                    this.renderEngine.bindTexture(locationCloudsPng);
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                    if (this.cloudRenderer.shouldUpdateGlList()) {
                        this.cloudRenderer.startUpdateGlList();
                        Vec3 vec3 = this.theWorld.getCloudColour(partialTicks);
                        float f = (float)vec3.xCoord;
                        float f1 = (float)vec3.yCoord;
                        float f2 = (float)vec3.zCoord;
                        if (pass != 2) {
                            float f3 = (f * 30.0f + f1 * 59.0f + f2 * 11.0f) / 100.0f;
                            float f4 = (f * 30.0f + f1 * 70.0f) / 100.0f;
                            float f5 = (f * 30.0f + f2 * 70.0f) / 100.0f;
                            f = f3;
                            f1 = f4;
                            f2 = f5;
                        }
                        float f10 = 4.8828125E-4f;
                        double d2 = (float)this.cloudTickCounter + partialTicks;
                        double d0 = RenderGlobal.mc.getRenderViewEntity().prevPosX + (RenderGlobal.mc.getRenderViewEntity().posX - RenderGlobal.mc.getRenderViewEntity().prevPosX) * (double)partialTicks + d2 * (double)0.03f;
                        double d1 = RenderGlobal.mc.getRenderViewEntity().prevPosZ + (RenderGlobal.mc.getRenderViewEntity().posZ - RenderGlobal.mc.getRenderViewEntity().prevPosZ) * (double)partialTicks;
                        int i = MathHelper.floor_double(d0 / 2048.0);
                        int j = MathHelper.floor_double(d1 / 2048.0);
                        float f6 = this.theWorld.provider.getCloudHeight() - f9 + 0.33f;
                        f6 += RenderGlobal.mc.gameSettings.ofCloudsHeight * 128.0f;
                        float f7 = (float)((d0 -= (double)(i * 2048)) * 4.8828125E-4);
                        float f8 = (float)((d1 -= (double)(j * 2048)) * 4.8828125E-4);
                        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                        int k = -256;
                        while (k < 256) {
                            int l = -256;
                            while (l < 256) {
                                worldrenderer.pos(k + 0, f6, l + 32).tex((float)(k + 0) * 4.8828125E-4f + f7, (float)(l + 32) * 4.8828125E-4f + f8).color(f, f1, f2, 0.8f).endVertex();
                                worldrenderer.pos(k + 32, f6, l + 32).tex((float)(k + 32) * 4.8828125E-4f + f7, (float)(l + 32) * 4.8828125E-4f + f8).color(f, f1, f2, 0.8f).endVertex();
                                worldrenderer.pos(k + 32, f6, l + 0).tex((float)(k + 32) * 4.8828125E-4f + f7, (float)(l + 0) * 4.8828125E-4f + f8).color(f, f1, f2, 0.8f).endVertex();
                                worldrenderer.pos(k + 0, f6, l + 0).tex((float)(k + 0) * 4.8828125E-4f + f7, (float)(l + 0) * 4.8828125E-4f + f8).color(f, f1, f2, 0.8f).endVertex();
                                l += 32;
                            }
                            k += 32;
                        }
                        tessellator.draw();
                        this.cloudRenderer.endUpdateGlList();
                    }
                    this.cloudRenderer.renderGlList();
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.disableBlend();
                    GlStateManager.enableCull();
                }
                if (Config.isShaders()) {
                    Shaders.endClouds();
                }
            }
        }
    }

    public boolean hasCloudFog(double x, double y, double z, float partialTicks) {
        return false;
    }

    private void renderCloudsFancy(float partialTicks, int pass) {
        this.cloudRenderer.prepareToRender(true, this.cloudTickCounter, partialTicks);
        partialTicks = 0.0f;
        GlStateManager.disableCull();
        float f = (float)(RenderGlobal.mc.getRenderViewEntity().lastTickPosY + (RenderGlobal.mc.getRenderViewEntity().posY - RenderGlobal.mc.getRenderViewEntity().lastTickPosY) * (double)partialTicks);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        float f1 = 12.0f;
        float f2 = 4.0f;
        double d0 = (float)this.cloudTickCounter + partialTicks;
        double d1 = (RenderGlobal.mc.getRenderViewEntity().prevPosX + (RenderGlobal.mc.getRenderViewEntity().posX - RenderGlobal.mc.getRenderViewEntity().prevPosX) * (double)partialTicks + d0 * (double)0.03f) / 12.0;
        double d2 = (RenderGlobal.mc.getRenderViewEntity().prevPosZ + (RenderGlobal.mc.getRenderViewEntity().posZ - RenderGlobal.mc.getRenderViewEntity().prevPosZ) * (double)partialTicks) / 12.0 + (double)0.33f;
        float f3 = this.theWorld.provider.getCloudHeight() - f + 0.33f;
        f3 += RenderGlobal.mc.gameSettings.ofCloudsHeight * 128.0f;
        int i = MathHelper.floor_double(d1 / 2048.0);
        int j = MathHelper.floor_double(d2 / 2048.0);
        d1 -= (double)(i * 2048);
        d2 -= (double)(j * 2048);
        this.renderEngine.bindTexture(locationCloudsPng);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Vec3 vec3 = this.theWorld.getCloudColour(partialTicks);
        float f4 = (float)vec3.xCoord;
        float f5 = (float)vec3.yCoord;
        float f6 = (float)vec3.zCoord;
        if (pass != 2) {
            float f7 = (f4 * 30.0f + f5 * 59.0f + f6 * 11.0f) / 100.0f;
            float f8 = (f4 * 30.0f + f5 * 70.0f) / 100.0f;
            float f9 = (f4 * 30.0f + f6 * 70.0f) / 100.0f;
            f4 = f7;
            f5 = f8;
            f6 = f9;
        }
        float f26 = f4 * 0.9f;
        float f27 = f5 * 0.9f;
        float f28 = f6 * 0.9f;
        float f10 = f4 * 0.7f;
        float f11 = f5 * 0.7f;
        float f12 = f6 * 0.7f;
        float f13 = f4 * 0.8f;
        float f14 = f5 * 0.8f;
        float f15 = f6 * 0.8f;
        float f16 = 0.00390625f;
        float f17 = (float)MathHelper.floor_double(d1) * 0.00390625f;
        float f18 = (float)MathHelper.floor_double(d2) * 0.00390625f;
        float f19 = (float)(d1 - (double)MathHelper.floor_double(d1));
        float f20 = (float)(d2 - (double)MathHelper.floor_double(d2));
        boolean flag = true;
        boolean flag1 = true;
        float f21 = 9.765625E-4f;
        GlStateManager.scale(12.0f, 1.0f, 12.0f);
        int k = 0;
        while (k < 2) {
            if (k == 0) {
                GlStateManager.colorMask(false, false, false, false);
            } else {
                switch (pass) {
                    case 0: {
                        GlStateManager.colorMask(false, true, true, true);
                        break;
                    }
                    case 1: {
                        GlStateManager.colorMask(true, false, false, true);
                        break;
                    }
                    case 2: {
                        GlStateManager.colorMask(true, true, true, true);
                    }
                }
            }
            this.cloudRenderer.renderGlList();
            ++k;
        }
        if (this.cloudRenderer.shouldUpdateGlList()) {
            this.cloudRenderer.startUpdateGlList();
            int j1 = -3;
            while (j1 <= 4) {
                int l = -3;
                while (l <= 4) {
                    worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
                    float f22 = j1 * 8;
                    float f23 = l * 8;
                    float f24 = f22 - f19;
                    float f25 = f23 - f20;
                    if (f3 > -5.0f) {
                        worldrenderer.pos(f24 + 0.0f, f3 + 0.0f, f25 + 8.0f).tex((f22 + 0.0f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).color(f10, f11, f12, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                        worldrenderer.pos(f24 + 8.0f, f3 + 0.0f, f25 + 8.0f).tex((f22 + 8.0f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).color(f10, f11, f12, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                        worldrenderer.pos(f24 + 8.0f, f3 + 0.0f, f25 + 0.0f).tex((f22 + 8.0f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).color(f10, f11, f12, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                        worldrenderer.pos(f24 + 0.0f, f3 + 0.0f, f25 + 0.0f).tex((f22 + 0.0f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).color(f10, f11, f12, 0.8f).normal(0.0f, -1.0f, 0.0f).endVertex();
                    }
                    if (f3 <= 5.0f) {
                        worldrenderer.pos(f24 + 0.0f, f3 + 4.0f - 9.765625E-4f, f25 + 8.0f).tex((f22 + 0.0f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).color(f4, f5, f6, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                        worldrenderer.pos(f24 + 8.0f, f3 + 4.0f - 9.765625E-4f, f25 + 8.0f).tex((f22 + 8.0f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).color(f4, f5, f6, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                        worldrenderer.pos(f24 + 8.0f, f3 + 4.0f - 9.765625E-4f, f25 + 0.0f).tex((f22 + 8.0f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).color(f4, f5, f6, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                        worldrenderer.pos(f24 + 0.0f, f3 + 4.0f - 9.765625E-4f, f25 + 0.0f).tex((f22 + 0.0f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).color(f4, f5, f6, 0.8f).normal(0.0f, 1.0f, 0.0f).endVertex();
                    }
                    if (j1 > -1) {
                        int i1 = 0;
                        while (i1 < 8) {
                            worldrenderer.pos(f24 + (float)i1 + 0.0f, f3 + 0.0f, f25 + 8.0f).tex((f22 + (float)i1 + 0.5f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).color(f26, f27, f28, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                            worldrenderer.pos(f24 + (float)i1 + 0.0f, f3 + 4.0f, f25 + 8.0f).tex((f22 + (float)i1 + 0.5f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).color(f26, f27, f28, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                            worldrenderer.pos(f24 + (float)i1 + 0.0f, f3 + 4.0f, f25 + 0.0f).tex((f22 + (float)i1 + 0.5f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).color(f26, f27, f28, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                            worldrenderer.pos(f24 + (float)i1 + 0.0f, f3 + 0.0f, f25 + 0.0f).tex((f22 + (float)i1 + 0.5f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).color(f26, f27, f28, 0.8f).normal(-1.0f, 0.0f, 0.0f).endVertex();
                            ++i1;
                        }
                    }
                    if (j1 <= 1) {
                        int k1 = 0;
                        while (k1 < 8) {
                            worldrenderer.pos(f24 + (float)k1 + 1.0f - 9.765625E-4f, f3 + 0.0f, f25 + 8.0f).tex((f22 + (float)k1 + 0.5f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).color(f26, f27, f28, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                            worldrenderer.pos(f24 + (float)k1 + 1.0f - 9.765625E-4f, f3 + 4.0f, f25 + 8.0f).tex((f22 + (float)k1 + 0.5f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).color(f26, f27, f28, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                            worldrenderer.pos(f24 + (float)k1 + 1.0f - 9.765625E-4f, f3 + 4.0f, f25 + 0.0f).tex((f22 + (float)k1 + 0.5f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).color(f26, f27, f28, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                            worldrenderer.pos(f24 + (float)k1 + 1.0f - 9.765625E-4f, f3 + 0.0f, f25 + 0.0f).tex((f22 + (float)k1 + 0.5f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).color(f26, f27, f28, 0.8f).normal(1.0f, 0.0f, 0.0f).endVertex();
                            ++k1;
                        }
                    }
                    if (l > -1) {
                        int l1 = 0;
                        while (l1 < 8) {
                            worldrenderer.pos(f24 + 0.0f, f3 + 4.0f, f25 + (float)l1 + 0.0f).tex((f22 + 0.0f) * 0.00390625f + f17, (f23 + (float)l1 + 0.5f) * 0.00390625f + f18).color(f13, f14, f15, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                            worldrenderer.pos(f24 + 8.0f, f3 + 4.0f, f25 + (float)l1 + 0.0f).tex((f22 + 8.0f) * 0.00390625f + f17, (f23 + (float)l1 + 0.5f) * 0.00390625f + f18).color(f13, f14, f15, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                            worldrenderer.pos(f24 + 8.0f, f3 + 0.0f, f25 + (float)l1 + 0.0f).tex((f22 + 8.0f) * 0.00390625f + f17, (f23 + (float)l1 + 0.5f) * 0.00390625f + f18).color(f13, f14, f15, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                            worldrenderer.pos(f24 + 0.0f, f3 + 0.0f, f25 + (float)l1 + 0.0f).tex((f22 + 0.0f) * 0.00390625f + f17, (f23 + (float)l1 + 0.5f) * 0.00390625f + f18).color(f13, f14, f15, 0.8f).normal(0.0f, 0.0f, -1.0f).endVertex();
                            ++l1;
                        }
                    }
                    if (l <= 1) {
                        int i2 = 0;
                        while (i2 < 8) {
                            worldrenderer.pos(f24 + 0.0f, f3 + 4.0f, f25 + (float)i2 + 1.0f - 9.765625E-4f).tex((f22 + 0.0f) * 0.00390625f + f17, (f23 + (float)i2 + 0.5f) * 0.00390625f + f18).color(f13, f14, f15, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                            worldrenderer.pos(f24 + 8.0f, f3 + 4.0f, f25 + (float)i2 + 1.0f - 9.765625E-4f).tex((f22 + 8.0f) * 0.00390625f + f17, (f23 + (float)i2 + 0.5f) * 0.00390625f + f18).color(f13, f14, f15, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                            worldrenderer.pos(f24 + 8.0f, f3 + 0.0f, f25 + (float)i2 + 1.0f - 9.765625E-4f).tex((f22 + 8.0f) * 0.00390625f + f17, (f23 + (float)i2 + 0.5f) * 0.00390625f + f18).color(f13, f14, f15, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                            worldrenderer.pos(f24 + 0.0f, f3 + 0.0f, f25 + (float)i2 + 1.0f - 9.765625E-4f).tex((f22 + 0.0f) * 0.00390625f + f17, (f23 + (float)i2 + 0.5f) * 0.00390625f + f18).color(f13, f14, f15, 0.8f).normal(0.0f, 0.0f, 1.0f).endVertex();
                            ++i2;
                        }
                    }
                    tessellator.draw();
                    ++l;
                }
                ++j1;
            }
            this.cloudRenderer.endUpdateGlList();
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
    }

    public void updateChunks(long finishTimeNano) {
        RenderChunk renderchunk2;
        Iterator iterator2;
        finishTimeNano = (long)((double)finishTimeNano + 1.0E8);
        this.displayListEntitiesDirty |= this.renderDispatcher.runChunkUploads(finishTimeNano);
        if (this.chunksToUpdateForced.size() > 0) {
            Iterator iterator = this.chunksToUpdateForced.iterator();
            while (iterator.hasNext()) {
                RenderChunk renderchunk = (RenderChunk)iterator.next();
                if (!this.renderDispatcher.updateChunkLater(renderchunk)) break;
                renderchunk.setNeedsUpdate(false);
                iterator.remove();
                this.chunksToUpdate.remove(renderchunk);
                this.chunksToResortTransparency.remove(renderchunk);
            }
        }
        if (this.chunksToResortTransparency.size() > 0 && (iterator2 = this.chunksToResortTransparency.iterator()).hasNext() && this.renderDispatcher.updateTransparencyLater(renderchunk2 = (RenderChunk)iterator2.next())) {
            iterator2.remove();
        }
        int j = 0;
        int k = Config.getUpdatesPerFrame();
        int i = k * 2;
        Iterator iterator1 = this.chunksToUpdate.iterator();
        while (iterator1.hasNext()) {
            RenderChunk renderchunk1 = (RenderChunk)iterator1.next();
            if (!this.renderDispatcher.updateChunkLater(renderchunk1)) break;
            renderchunk1.setNeedsUpdate(false);
            iterator1.remove();
            if (renderchunk1.getCompiledChunk().isEmpty() && k < i) {
                ++k;
            }
            if (++j >= k) break;
        }
    }

    public void renderWorldBorder(Entity p_180449_1_, float partialTicks) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        WorldBorder worldborder = this.theWorld.getWorldBorder();
        double d0 = RenderGlobal.mc.gameSettings.renderDistanceChunks * 16;
        if (p_180449_1_.posX >= worldborder.maxX() - d0 || p_180449_1_.posX <= worldborder.minX() + d0 || p_180449_1_.posZ >= worldborder.maxZ() - d0 || p_180449_1_.posZ <= worldborder.minZ() + d0) {
            double d1 = 1.0 - worldborder.getClosestDistance(p_180449_1_) / d0;
            d1 = Math.pow(d1, 4.0);
            double d2 = p_180449_1_.lastTickPosX + (p_180449_1_.posX - p_180449_1_.lastTickPosX) * (double)partialTicks;
            double d3 = p_180449_1_.lastTickPosY + (p_180449_1_.posY - p_180449_1_.lastTickPosY) * (double)partialTicks;
            double d4 = p_180449_1_.lastTickPosZ + (p_180449_1_.posZ - p_180449_1_.lastTickPosZ) * (double)partialTicks;
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
            this.renderEngine.bindTexture(locationForcefieldPng);
            GlStateManager.depthMask(false);
            GlStateManager.pushMatrix();
            int i = worldborder.getStatus().getID();
            float f = (float)(i >> 16 & 0xFF) / 255.0f;
            float f1 = (float)(i >> 8 & 0xFF) / 255.0f;
            float f2 = (float)(i & 0xFF) / 255.0f;
            GlStateManager.color(f, f1, f2, (float)d1);
            GlStateManager.doPolygonOffset(-3.0f, -3.0f);
            GlStateManager.enablePolygonOffset();
            GlStateManager.alphaFunc(516, 0.1f);
            GlStateManager.enableAlpha();
            GlStateManager.disableCull();
            float f3 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0f;
            float f4 = 0.0f;
            float f5 = 0.0f;
            float f6 = 128.0f;
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.setTranslation(-d2, -d3, -d4);
            double d5 = Math.max((double)MathHelper.floor_double(d4 - d0), worldborder.minZ());
            double d6 = Math.min((double)MathHelper.ceiling_double_int(d4 + d0), worldborder.maxZ());
            if (d2 > worldborder.maxX() - d0) {
                float f7 = 0.0f;
                double d7 = d5;
                while (d7 < d6) {
                    double d8 = Math.min(1.0, d6 - d7);
                    float f8 = (float)d8 * 0.5f;
                    worldrenderer.pos(worldborder.maxX(), 256.0, d7).tex(f3 + f7, f3 + 0.0f).endVertex();
                    worldrenderer.pos(worldborder.maxX(), 256.0, d7 + d8).tex(f3 + f8 + f7, f3 + 0.0f).endVertex();
                    worldrenderer.pos(worldborder.maxX(), 0.0, d7 + d8).tex(f3 + f8 + f7, f3 + 128.0f).endVertex();
                    worldrenderer.pos(worldborder.maxX(), 0.0, d7).tex(f3 + f7, f3 + 128.0f).endVertex();
                    d7 += 1.0;
                    f7 += 0.5f;
                }
            }
            if (d2 < worldborder.minX() + d0) {
                float f9 = 0.0f;
                double d9 = d5;
                while (d9 < d6) {
                    double d12 = Math.min(1.0, d6 - d9);
                    float f12 = (float)d12 * 0.5f;
                    worldrenderer.pos(worldborder.minX(), 256.0, d9).tex(f3 + f9, f3 + 0.0f).endVertex();
                    worldrenderer.pos(worldborder.minX(), 256.0, d9 + d12).tex(f3 + f12 + f9, f3 + 0.0f).endVertex();
                    worldrenderer.pos(worldborder.minX(), 0.0, d9 + d12).tex(f3 + f12 + f9, f3 + 128.0f).endVertex();
                    worldrenderer.pos(worldborder.minX(), 0.0, d9).tex(f3 + f9, f3 + 128.0f).endVertex();
                    d9 += 1.0;
                    f9 += 0.5f;
                }
            }
            d5 = Math.max((double)MathHelper.floor_double(d2 - d0), worldborder.minX());
            d6 = Math.min((double)MathHelper.ceiling_double_int(d2 + d0), worldborder.maxX());
            if (d4 > worldborder.maxZ() - d0) {
                float f10 = 0.0f;
                double d10 = d5;
                while (d10 < d6) {
                    double d13 = Math.min(1.0, d6 - d10);
                    float f13 = (float)d13 * 0.5f;
                    worldrenderer.pos(d10, 256.0, worldborder.maxZ()).tex(f3 + f10, f3 + 0.0f).endVertex();
                    worldrenderer.pos(d10 + d13, 256.0, worldborder.maxZ()).tex(f3 + f13 + f10, f3 + 0.0f).endVertex();
                    worldrenderer.pos(d10 + d13, 0.0, worldborder.maxZ()).tex(f3 + f13 + f10, f3 + 128.0f).endVertex();
                    worldrenderer.pos(d10, 0.0, worldborder.maxZ()).tex(f3 + f10, f3 + 128.0f).endVertex();
                    d10 += 1.0;
                    f10 += 0.5f;
                }
            }
            if (d4 < worldborder.minZ() + d0) {
                float f11 = 0.0f;
                double d11 = d5;
                while (d11 < d6) {
                    double d14 = Math.min(1.0, d6 - d11);
                    float f14 = (float)d14 * 0.5f;
                    worldrenderer.pos(d11, 256.0, worldborder.minZ()).tex(f3 + f11, f3 + 0.0f).endVertex();
                    worldrenderer.pos(d11 + d14, 256.0, worldborder.minZ()).tex(f3 + f14 + f11, f3 + 0.0f).endVertex();
                    worldrenderer.pos(d11 + d14, 0.0, worldborder.minZ()).tex(f3 + f14 + f11, f3 + 128.0f).endVertex();
                    worldrenderer.pos(d11, 0.0, worldborder.minZ()).tex(f3 + f11, f3 + 128.0f).endVertex();
                    d11 += 1.0;
                    f11 += 0.5f;
                }
            }
            tessellator.draw();
            worldrenderer.setTranslation(0.0, 0.0, 0.0);
            GlStateManager.enableCull();
            GlStateManager.disableAlpha();
            GlStateManager.doPolygonOffset(0.0f, 0.0f);
            GlStateManager.disablePolygonOffset();
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
        }
    }

    private void preRenderDamagedBlocks() {
        GlStateManager.tryBlendFuncSeparate(774, 768, 1, 0);
        GlStateManager.enableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.5f);
        GlStateManager.doPolygonOffset(-3.0f, -3.0f);
        GlStateManager.enablePolygonOffset();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableAlpha();
        GlStateManager.pushMatrix();
        if (Config.isShaders()) {
            ShadersRender.beginBlockDamage();
        }
    }

    private void postRenderDamagedBlocks() {
        GlStateManager.disableAlpha();
        GlStateManager.doPolygonOffset(0.0f, 0.0f);
        GlStateManager.disablePolygonOffset();
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
        if (Config.isShaders()) {
            ShadersRender.endBlockDamage();
        }
    }

    public void drawBlockDamageTexture(Tessellator tessellatorIn, WorldRenderer worldRendererIn, Entity entityIn, float partialTicks) {
        double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double)partialTicks;
        double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double)partialTicks;
        double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double)partialTicks;
        if (!this.damagedBlocks.isEmpty()) {
            this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            this.preRenderDamagedBlocks();
            worldRendererIn.begin(7, DefaultVertexFormats.BLOCK);
            worldRendererIn.setTranslation(-d0, -d1, -d2);
            worldRendererIn.markDirty();
            Iterator iterator = this.damagedBlocks.values().iterator();
            while (iterator.hasNext()) {
                boolean flag;
                DestroyBlockProgress destroyblockprogress = (DestroyBlockProgress)iterator.next();
                BlockPos blockpos = destroyblockprogress.getPosition();
                double d3 = (double)blockpos.getX() - d0;
                double d4 = (double)blockpos.getY() - d1;
                double d5 = (double)blockpos.getZ() - d2;
                Block block = this.theWorld.getBlockState(blockpos).getBlock();
                if (Reflector.ForgeTileEntity_canRenderBreaking.exists()) {
                    TileEntity tileentity;
                    boolean flag1;
                    boolean bl = flag1 = block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockSign || block instanceof BlockSkull;
                    if (!flag1 && (tileentity = this.theWorld.getTileEntity(blockpos)) != null) {
                        flag1 = Reflector.callBoolean((Object)tileentity, (ReflectorMethod)Reflector.ForgeTileEntity_canRenderBreaking, (Object[])new Object[0]);
                    }
                    flag = !flag1;
                } else {
                    boolean bl = flag = !(block instanceof BlockChest) && !(block instanceof BlockEnderChest) && !(block instanceof BlockSign) && !(block instanceof BlockSkull);
                }
                if (!flag) continue;
                if (d3 * d3 + d4 * d4 + d5 * d5 > 1024.0) {
                    iterator.remove();
                    continue;
                }
                IBlockState iblockstate = this.theWorld.getBlockState(blockpos);
                if (iblockstate.getBlock().getMaterial() == Material.air) continue;
                int i = destroyblockprogress.getPartialBlockDamage();
                TextureAtlasSprite textureatlassprite = this.destroyBlockIcons[i];
                BlockRendererDispatcher blockrendererdispatcher = mc.getBlockRendererDispatcher();
                blockrendererdispatcher.renderBlockDamage(iblockstate, blockpos, textureatlassprite, this.theWorld);
            }
            tessellatorIn.draw();
            worldRendererIn.setTranslation(0.0, 0.0, 0.0);
            this.postRenderDamagedBlocks();
        }
    }

    public void drawSelectionBox(EntityPlayer player, MovingObjectPosition movingObjectPositionIn, int p_72731_3_, float partialTicks) {
        if (p_72731_3_ == 0 && movingObjectPositionIn.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.color(0.0f, 0.0f, 0.0f, 0.4f);
            GL11.glLineWidth((float)2.0f);
            GlStateManager.disableTexture2D();
            if (Config.isShaders()) {
                Shaders.disableTexture2D();
            }
            GlStateManager.depthMask(false);
            float f = 0.002f;
            BlockPos blockpos = movingObjectPositionIn.getBlockPos();
            Block block = this.theWorld.getBlockState(blockpos).getBlock();
            if (block.getMaterial() != Material.air && this.theWorld.getWorldBorder().contains(blockpos)) {
                block.setBlockBoundsBasedOnState(this.theWorld, blockpos);
                double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
                double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
                double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;
                RenderGlobal.func_181561_a(block.getSelectedBoundingBox(this.theWorld, blockpos).expand(0.002f, 0.002f, 0.002f).offset(-d0, -d1, -d2));
            }
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            if (Config.isShaders()) {
                Shaders.enableTexture2D();
            }
            GlStateManager.disableBlend();
        }
    }

    public static void func_181561_a(AxisAlignedBB p_181561_0_) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION);
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
        tessellator.draw();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION);
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
        tessellator.draw();
        worldrenderer.begin(1, DefaultVertexFormats.POSITION);
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
        worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
        worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
        tessellator.draw();
    }

    public static void func_181563_a(AxisAlignedBB p_181563_0_, int p_181563_1_, int p_181563_2_, int p_181563_3_, int p_181563_4_) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        worldrenderer.pos(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        worldrenderer.pos(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        tessellator.draw();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        worldrenderer.pos(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        worldrenderer.pos(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        tessellator.draw();
        worldrenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        worldrenderer.pos(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        worldrenderer.pos(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        worldrenderer.pos(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
        tessellator.draw();
    }

    private void markBlocksForUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.viewFrustum.markBlocksForUpdate(x1, y1, z1, x2, y2, z2);
    }

    @Override
    public void markBlockForUpdate(BlockPos pos) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        this.markBlocksForUpdate(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1);
    }

    @Override
    public void notifyLightSet(BlockPos pos) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        this.markBlocksForUpdate(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1);
    }

    @Override
    public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.markBlocksForUpdate(x1 - 1, y1 - 1, z1 - 1, x2 + 1, y2 + 1, z2 + 1);
    }

    @Override
    public void playRecord(String recordName, BlockPos blockPosIn) {
        ISound isound = (ISound)this.mapSoundPositions.get(blockPosIn);
        if (isound != null) {
            mc.getSoundHandler().stopSound(isound);
            this.mapSoundPositions.remove(blockPosIn);
        }
        if (recordName != null) {
            ItemRecord itemrecord = ItemRecord.getRecord(recordName);
            if (itemrecord != null) {
                RenderGlobal.mc.ingameGUI.setRecordPlayingMessage(itemrecord.getRecordNameLocal());
            }
            ResourceLocation resourcelocation = null;
            if (Reflector.ForgeItemRecord_getRecordResource.exists() && itemrecord != null) {
                resourcelocation = (ResourceLocation)Reflector.call((Object)itemrecord, (ReflectorMethod)Reflector.ForgeItemRecord_getRecordResource, (Object[])new Object[]{recordName});
            }
            if (resourcelocation == null) {
                resourcelocation = new ResourceLocation(recordName);
            }
            PositionedSoundRecord positionedsoundrecord = PositionedSoundRecord.create(resourcelocation, blockPosIn.getX(), blockPosIn.getY(), blockPosIn.getZ());
            this.mapSoundPositions.put(blockPosIn, positionedsoundrecord);
            mc.getSoundHandler().playSound(positionedsoundrecord);
        }
    }

    @Override
    public void playSound(String soundName, double x, double y, double z, float volume, float pitch) {
    }

    @Override
    public void playSoundToNearExcept(EntityPlayer except, String soundName, double x, double y, double z, float volume, float pitch) {
    }

    @Override
    public void spawnParticle(int particleID, boolean ignoreRange, final double xCoord, final double yCoord, final double zCoord, double xOffset, double yOffset, double zOffset, int ... p_180442_15_) {
        try {
            this.spawnEntityFX(particleID, ignoreRange, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, p_180442_15_);
        }
        catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while adding particle");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being added");
            crashreportcategory.addCrashSection("ID", particleID);
            if (p_180442_15_ != null) {
                crashreportcategory.addCrashSection("Parameters", p_180442_15_);
            }
            crashreportcategory.addCrashSectionCallable("Position", new Callable(){
                private static final String __OBFID = "CL_00000955";

                public String call() throws Exception {
                    return CrashReportCategory.getCoordinateInfo(xCoord, yCoord, zCoord);
                }
            });
            throw new ReportedException(crashreport);
        }
    }

    private void spawnParticle(EnumParticleTypes particleIn, double p_174972_2_, double p_174972_4_, double p_174972_6_, double p_174972_8_, double p_174972_10_, double p_174972_12_, int ... p_174972_14_) {
        this.spawnParticle(particleIn.getParticleID(), particleIn.getShouldIgnoreRange(), p_174972_2_, p_174972_4_, p_174972_6_, p_174972_8_, p_174972_10_, p_174972_12_, p_174972_14_);
    }

    private EntityFX spawnEntityFX(int p_174974_1_, boolean ignoreRange, double p_174974_3_, double p_174974_5_, double p_174974_7_, double p_174974_9_, double p_174974_11_, double p_174974_13_, int ... p_174974_15_) {
        if (mc != null && mc.getRenderViewEntity() != null && RenderGlobal.mc.effectRenderer != null) {
            int i = RenderGlobal.mc.gameSettings.particleSetting;
            if (i == 1 && this.theWorld.rand.nextInt(3) == 0) {
                i = 2;
            }
            double d0 = RenderGlobal.mc.getRenderViewEntity().posX - p_174974_3_;
            double d1 = RenderGlobal.mc.getRenderViewEntity().posY - p_174974_5_;
            double d2 = RenderGlobal.mc.getRenderViewEntity().posZ - p_174974_7_;
            if (p_174974_1_ == EnumParticleTypes.EXPLOSION_HUGE.getParticleID() && !Config.isAnimatedExplosion()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.EXPLOSION_LARGE.getParticleID() && !Config.isAnimatedExplosion()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.EXPLOSION_NORMAL.getParticleID() && !Config.isAnimatedExplosion()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SUSPENDED.getParticleID() && !Config.isWaterParticles()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SUSPENDED_DEPTH.getParticleID() && !Config.isVoidParticles()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SMOKE_NORMAL.getParticleID() && !Config.isAnimatedSmoke()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SMOKE_LARGE.getParticleID() && !Config.isAnimatedSmoke()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SPELL_MOB.getParticleID() && !Config.isPotionParticles()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID() && !Config.isPotionParticles()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SPELL.getParticleID() && !Config.isPotionParticles()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SPELL_INSTANT.getParticleID() && !Config.isPotionParticles()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SPELL_WITCH.getParticleID() && !Config.isPotionParticles()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.PORTAL.getParticleID() && !Config.isAnimatedPortal()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.FLAME.getParticleID() && !Config.isAnimatedFlame()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.REDSTONE.getParticleID() && !Config.isAnimatedRedstone()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.DRIP_WATER.getParticleID() && !Config.isDrippingWaterLava()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.DRIP_LAVA.getParticleID() && !Config.isDrippingWaterLava()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.FIREWORKS_SPARK.getParticleID() && !Config.isFireworkParticles()) {
                return null;
            }
            if (ignoreRange) {
                return RenderGlobal.mc.effectRenderer.spawnEffectParticle(p_174974_1_, p_174974_3_, p_174974_5_, p_174974_7_, p_174974_9_, p_174974_11_, p_174974_13_, p_174974_15_);
            }
            double d3 = 16.0;
            double d4 = 256.0;
            if (p_174974_1_ == EnumParticleTypes.CRIT.getParticleID()) {
                d4 = 38416.0;
            }
            if (d0 * d0 + d1 * d1 + d2 * d2 > d4) {
                return null;
            }
            if (i > 1) {
                return null;
            }
            EntityFX entityfx = RenderGlobal.mc.effectRenderer.spawnEffectParticle(p_174974_1_, p_174974_3_, p_174974_5_, p_174974_7_, p_174974_9_, p_174974_11_, p_174974_13_, p_174974_15_);
            if (p_174974_1_ == EnumParticleTypes.WATER_BUBBLE.getParticleID()) {
                CustomColors.updateWaterFX((EntityFX)entityfx, (IBlockAccess)this.theWorld, (double)p_174974_3_, (double)p_174974_5_, (double)p_174974_7_);
            }
            if (p_174974_1_ == EnumParticleTypes.WATER_SPLASH.getParticleID()) {
                CustomColors.updateWaterFX((EntityFX)entityfx, (IBlockAccess)this.theWorld, (double)p_174974_3_, (double)p_174974_5_, (double)p_174974_7_);
            }
            if (p_174974_1_ == EnumParticleTypes.WATER_DROP.getParticleID()) {
                CustomColors.updateWaterFX((EntityFX)entityfx, (IBlockAccess)this.theWorld, (double)p_174974_3_, (double)p_174974_5_, (double)p_174974_7_);
            }
            if (p_174974_1_ == EnumParticleTypes.TOWN_AURA.getParticleID()) {
                CustomColors.updateMyceliumFX((EntityFX)entityfx);
            }
            if (p_174974_1_ == EnumParticleTypes.PORTAL.getParticleID()) {
                CustomColors.updatePortalFX((EntityFX)entityfx);
            }
            if (p_174974_1_ == EnumParticleTypes.REDSTONE.getParticleID()) {
                CustomColors.updateReddustFX((EntityFX)entityfx, (IBlockAccess)this.theWorld, (double)p_174974_3_, (double)p_174974_5_, (double)p_174974_7_);
            }
            return entityfx;
        }
        return null;
    }

    @Override
    public void onEntityAdded(Entity entityIn) {
        RandomMobs.entityLoaded((Entity)entityIn, (World)this.theWorld);
        if (Config.isDynamicLights()) {
            DynamicLights.entityAdded((Entity)entityIn, (RenderGlobal)this);
        }
    }

    @Override
    public void onEntityRemoved(Entity entityIn) {
        if (Config.isDynamicLights()) {
            DynamicLights.entityRemoved((Entity)entityIn, (RenderGlobal)this);
        }
    }

    public void deleteAllDisplayLists() {
    }

    @Override
    public void broadcastSound(int p_180440_1_, BlockPos p_180440_2_, int p_180440_3_) {
        switch (p_180440_1_) {
            case 1013: 
            case 1018: {
                if (mc.getRenderViewEntity() == null) break;
                double d0 = (double)p_180440_2_.getX() - RenderGlobal.mc.getRenderViewEntity().posX;
                double d1 = (double)p_180440_2_.getY() - RenderGlobal.mc.getRenderViewEntity().posY;
                double d2 = (double)p_180440_2_.getZ() - RenderGlobal.mc.getRenderViewEntity().posZ;
                double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                double d4 = RenderGlobal.mc.getRenderViewEntity().posX;
                double d5 = RenderGlobal.mc.getRenderViewEntity().posY;
                double d6 = RenderGlobal.mc.getRenderViewEntity().posZ;
                if (d3 > 0.0) {
                    d4 += d0 / d3 * 2.0;
                    d5 += d1 / d3 * 2.0;
                    d6 += d2 / d3 * 2.0;
                }
                if (p_180440_1_ == 1013) {
                    this.theWorld.playSound(d4, d5, d6, "mob.wither.spawn", 1.0f, 1.0f, false);
                    break;
                }
                this.theWorld.playSound(d4, d5, d6, "mob.enderdragon.end", 5.0f, 1.0f, false);
            }
        }
    }

    @Override
    public void playAuxSFX(EntityPlayer player, int sfxType, BlockPos blockPosIn, int p_180439_4_) {
        Random random = this.theWorld.rand;
        switch (sfxType) {
            case 1000: {
                this.theWorld.playSoundAtPos(blockPosIn, "random.click", 1.0f, 1.0f, false);
                break;
            }
            case 1001: {
                this.theWorld.playSoundAtPos(blockPosIn, "random.click", 1.0f, 1.2f, false);
                break;
            }
            case 1002: {
                this.theWorld.playSoundAtPos(blockPosIn, "random.bow", 1.0f, 1.2f, false);
                break;
            }
            case 1003: {
                this.theWorld.playSoundAtPos(blockPosIn, "random.door_open", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1004: {
                this.theWorld.playSoundAtPos(blockPosIn, "random.fizz", 0.5f, 2.6f + (random.nextFloat() - random.nextFloat()) * 0.8f, false);
                break;
            }
            case 1005: {
                if (Item.getItemById(p_180439_4_) instanceof ItemRecord) {
                    this.theWorld.playRecord(blockPosIn, "records." + ((ItemRecord)Item.getItemById((int)p_180439_4_)).recordName);
                    break;
                }
                this.theWorld.playRecord(blockPosIn, null);
                break;
            }
            case 1006: {
                this.theWorld.playSoundAtPos(blockPosIn, "random.door_close", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1007: {
                this.theWorld.playSoundAtPos(blockPosIn, "mob.ghast.charge", 10.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1008: {
                this.theWorld.playSoundAtPos(blockPosIn, "mob.ghast.fireball", 10.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1009: {
                this.theWorld.playSoundAtPos(blockPosIn, "mob.ghast.fireball", 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1010: {
                this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.wood", 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1011: {
                this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.metal", 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1012: {
                this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.woodbreak", 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1014: {
                this.theWorld.playSoundAtPos(blockPosIn, "mob.wither.shoot", 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1015: {
                this.theWorld.playSoundAtPos(blockPosIn, "mob.bat.takeoff", 0.05f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1016: {
                this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.infect", 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1017: {
                this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.unfect", 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1020: {
                this.theWorld.playSoundAtPos(blockPosIn, "random.anvil_break", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1021: {
                this.theWorld.playSoundAtPos(blockPosIn, "random.anvil_use", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1022: {
                this.theWorld.playSoundAtPos(blockPosIn, "random.anvil_land", 0.3f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2000: {
                int k = p_180439_4_ % 3 - 1;
                int l = p_180439_4_ / 3 % 3 - 1;
                double d13 = (double)blockPosIn.getX() + (double)k * 0.6 + 0.5;
                double d15 = (double)blockPosIn.getY() + 0.5;
                double d19 = (double)blockPosIn.getZ() + (double)l * 0.6 + 0.5;
                int l1 = 0;
                while (l1 < 10) {
                    double d20 = random.nextDouble() * 0.2 + 0.01;
                    double d21 = d13 + (double)k * 0.01 + (random.nextDouble() - 0.5) * (double)l * 0.5;
                    double d22 = d15 + (random.nextDouble() - 0.5) * 0.5;
                    double d23 = d19 + (double)l * 0.01 + (random.nextDouble() - 0.5) * (double)k * 0.5;
                    double d24 = (double)k * d20 + random.nextGaussian() * 0.01;
                    double d9 = -0.03 + random.nextGaussian() * 0.01;
                    double d10 = (double)l * d20 + random.nextGaussian() * 0.01;
                    this.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d21, d22, d23, d24, d9, d10, new int[0]);
                    ++l1;
                }
                return;
            }
            case 2001: {
                Block block = Block.getBlockById(p_180439_4_ & 0xFFF);
                if (block.getMaterial() != Material.air) {
                    mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(block.stepSound.getBreakSound()), (block.stepSound.getVolume() + 1.0f) / 2.0f, block.stepSound.getFrequency() * 0.8f, (float)blockPosIn.getX() + 0.5f, (float)blockPosIn.getY() + 0.5f, (float)blockPosIn.getZ() + 0.5f));
                }
                RenderGlobal.mc.effectRenderer.addBlockDestroyEffects(blockPosIn, block.getStateFromMeta(p_180439_4_ >> 12 & 0xFF));
                break;
            }
            case 2002: {
                double d11 = blockPosIn.getX();
                double d12 = blockPosIn.getY();
                double d14 = blockPosIn.getZ();
                int i1 = 0;
                while (i1 < 8) {
                    this.spawnParticle(EnumParticleTypes.ITEM_CRACK, d11, d12, d14, random.nextGaussian() * 0.15, random.nextDouble() * 0.2, random.nextGaussian() * 0.15, Item.getIdFromItem(Items.potionitem), p_180439_4_);
                    ++i1;
                }
                int j1 = Items.potionitem.getColorFromDamage(p_180439_4_);
                float f = (float)(j1 >> 16 & 0xFF) / 255.0f;
                float f1 = (float)(j1 >> 8 & 0xFF) / 255.0f;
                float f2 = (float)(j1 >> 0 & 0xFF) / 255.0f;
                EnumParticleTypes enumparticletypes = EnumParticleTypes.SPELL;
                if (Items.potionitem.isEffectInstant(p_180439_4_)) {
                    enumparticletypes = EnumParticleTypes.SPELL_INSTANT;
                }
                int k1 = 0;
                while (k1 < 100) {
                    double d16 = random.nextDouble() * 4.0;
                    double d17 = random.nextDouble() * Math.PI * 2.0;
                    double d18 = Math.cos(d17) * d16;
                    double d7 = 0.01 + random.nextDouble() * 0.5;
                    double d8 = Math.sin(d17) * d16;
                    EntityFX entityfx = this.spawnEntityFX(enumparticletypes.getParticleID(), enumparticletypes.getShouldIgnoreRange(), d11 + d18 * 0.1, d12 + 0.3, d14 + d8 * 0.1, d18, d7, d8, new int[0]);
                    if (entityfx != null) {
                        float f3 = 0.75f + random.nextFloat() * 0.25f;
                        entityfx.setRBGColorF(f * f3, f1 * f3, f2 * f3);
                        entityfx.multiplyVelocity((float)d16);
                    }
                    ++k1;
                }
                this.theWorld.playSoundAtPos(blockPosIn, "game.potion.smash", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2003: {
                double var7 = (double)blockPosIn.getX() + 0.5;
                double var9 = blockPosIn.getY();
                double var11 = (double)blockPosIn.getZ() + 0.5;
                int var13 = 0;
                while (var13 < 8) {
                    this.spawnParticle(EnumParticleTypes.ITEM_CRACK, var7, var9, var11, random.nextGaussian() * 0.15, random.nextDouble() * 0.2, random.nextGaussian() * 0.15, Item.getIdFromItem(Items.ender_eye));
                    ++var13;
                }
                double var32 = 0.0;
                while (var32 < Math.PI * 2) {
                    this.spawnParticle(EnumParticleTypes.PORTAL, var7 + Math.cos(var32) * 5.0, var9 - 0.4, var11 + Math.sin(var32) * 5.0, Math.cos(var32) * -5.0, 0.0, Math.sin(var32) * -5.0, new int[0]);
                    this.spawnParticle(EnumParticleTypes.PORTAL, var7 + Math.cos(var32) * 5.0, var9 - 0.4, var11 + Math.sin(var32) * 5.0, Math.cos(var32) * -7.0, 0.0, Math.sin(var32) * -7.0, new int[0]);
                    var32 += 0.15707963267948966;
                }
                return;
            }
            case 2004: {
                int var18 = 0;
                while (var18 < 20) {
                    double d3 = (double)blockPosIn.getX() + 0.5 + ((double)this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    double d4 = (double)blockPosIn.getY() + 0.5 + ((double)this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    double d5 = (double)blockPosIn.getZ() + 0.5 + ((double)this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    this.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d3, d4, d5, 0.0, 0.0, 0.0, new int[0]);
                    this.theWorld.spawnParticle(EnumParticleTypes.FLAME, d3, d4, d5, 0.0, 0.0, 0.0, new int[0]);
                    ++var18;
                }
                return;
            }
            case 2005: {
                ItemDye.spawnBonemealParticles(this.theWorld, blockPosIn, p_180439_4_);
            }
        }
    }

    @Override
    public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
        if (progress >= 0 && progress < 10) {
            DestroyBlockProgress destroyblockprogress = (DestroyBlockProgress)this.damagedBlocks.get(breakerId);
            if (destroyblockprogress == null || destroyblockprogress.getPosition().getX() != pos.getX() || destroyblockprogress.getPosition().getY() != pos.getY() || destroyblockprogress.getPosition().getZ() != pos.getZ()) {
                destroyblockprogress = new DestroyBlockProgress(breakerId, pos);
                this.damagedBlocks.put(breakerId, destroyblockprogress);
            }
            destroyblockprogress.setPartialBlockDamage(progress);
            destroyblockprogress.setCloudUpdateTick(this.cloudTickCounter);
        } else {
            this.damagedBlocks.remove(breakerId);
        }
    }

    public void setDisplayListEntitiesDirty() {
        this.displayListEntitiesDirty = true;
    }

    public void resetClouds() {
        this.cloudRenderer.reset();
    }

    public int getCountRenderers() {
        return this.viewFrustum.renderChunks.length;
    }

    public int getCountActiveRenderers() {
        return this.renderInfos.size();
    }

    public int getCountEntitiesRendered() {
        return this.countEntitiesRendered;
    }

    public int getCountTileEntitiesRendered() {
        return this.countTileEntitiesRendered;
    }

    public RenderChunk getRenderChunk(BlockPos p_getRenderChunk_1_) {
        return this.viewFrustum.getRenderChunk(p_getRenderChunk_1_);
    }

    public RenderChunk getRenderChunk(RenderChunk p_getRenderChunk_1_, EnumFacing p_getRenderChunk_2_) {
        if (p_getRenderChunk_1_ == null) {
            return null;
        }
        BlockPos blockpos = p_getRenderChunk_1_.func_181701_a(p_getRenderChunk_2_);
        return this.viewFrustum.getRenderChunk(blockpos);
    }

    public WorldClient getWorld() {
        return this.theWorld;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void func_181023_a(Collection p_181023_1_, Collection p_181023_2_) {
        Set set = this.field_181024_n;
        Set set2 = this.field_181024_n;
        synchronized (set2) {
            this.field_181024_n.removeAll(p_181023_1_);
            this.field_181024_n.addAll(p_181023_2_);
        }
    }

    public Framebuffer getEntityOutlineFramebuffer() {
        return entityOutlineFramebuffer;
    }

    public ShaderGroup getEntityOutlineShader() {
        return entityOutlineShader;
    }

    public static class ContainerLocalRenderInformation {
        final RenderChunk renderChunk;
        final EnumFacing facing;
        final Set setFacing = EnumSet.noneOf(EnumFacing.class);
        final int counter;
        private static final String __OBFID = "CL_00002534";

        public ContainerLocalRenderInformation(RenderChunk p_i4_1_, EnumFacing p_i4_2_, int p_i4_3_) {
            this.renderChunk = p_i4_1_;
            this.facing = p_i4_2_;
            this.counter = p_i4_3_;
        }

        ContainerLocalRenderInformation(RenderChunk p_i5_1_, EnumFacing p_i5_2_, int p_i5_3_, Object p_i5_4_) {
            this(p_i5_1_, p_i5_2_, p_i5_3_);
        }
    }
}

