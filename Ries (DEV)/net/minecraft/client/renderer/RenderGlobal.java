/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonSyntaxException
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.vector.Matrix4f
 *  org.lwjgl.util.vector.Vector3f
 *  org.lwjgl.util.vector.Vector4f
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
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
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemRecord;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vector3d;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.optifine.CustomColors;
import net.optifine.CustomSky;
import net.optifine.DynamicLights;
import net.optifine.Lagometer;
import net.optifine.RandomEntities;
import net.optifine.SmartAnimations;
import net.optifine.model.BlockModelUtils;
import net.optifine.reflect.Reflector;
import net.optifine.render.ChunkVisibility;
import net.optifine.render.CloudRenderer;
import net.optifine.render.RenderEnv;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;
import net.optifine.shaders.ShadowUtils;
import net.optifine.util.ChunkUtils;
import net.optifine.util.RenderChunkUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class RenderGlobal
implements IWorldAccess,
IResourceManagerReloadListener {
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
    private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
    private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
    private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
    private static final ResourceLocation locationForcefieldPng = new ResourceLocation("textures/misc/forcefield.png");
    public final Minecraft mc;
    private final TextureManager renderEngine;
    private final RenderManager renderManager;
    private WorldClient theWorld;
    private Set<RenderChunk> chunksToUpdate = Sets.newLinkedHashSet();
    private List<ContainerLocalRenderInformation> renderInfos = Lists.newArrayListWithCapacity((int)69696);
    private final Set<TileEntity> field_181024_n = Sets.newHashSet();
    private ViewFrustum viewFrustum;
    private int starGLCallList = -1;
    private int glSkyList = -1;
    private int glSkyList2 = -1;
    private final VertexFormat vertexBufferFormat;
    private VertexBuffer starVBO;
    private VertexBuffer skyVBO;
    private VertexBuffer sky2VBO;
    private int cloudTickCounter;
    public final Map<Integer, DestroyBlockProgress> damagedBlocks = Maps.newHashMap();
    private final Map<BlockPos, ISound> mapSoundPositions = Maps.newHashMap();
    private final TextureAtlasSprite[] destroyBlockIcons = new TextureAtlasSprite[10];
    private Framebuffer entityOutlineFramebuffer;
    private ShaderGroup entityOutlineShader;
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
    private boolean vboEnabled;
    IRenderChunkFactory renderChunkFactory;
    private double prevRenderSortX;
    private double prevRenderSortY;
    private double prevRenderSortZ;
    public boolean displayListEntitiesDirty = true;
    private final CloudRenderer cloudRenderer;
    public Entity renderedEntity;
    public final Set chunksToResortTransparency = new LinkedHashSet();
    public final Set chunksToUpdateForced = new LinkedHashSet();
    private final Deque visibilityDeque = new ArrayDeque();
    private List renderInfosEntities = new ArrayList(1024);
    private List renderInfosTileEntities = new ArrayList(1024);
    private final List renderInfosNormal = new ArrayList(1024);
    private final List renderInfosEntitiesNormal = new ArrayList(1024);
    private final List renderInfosTileEntitiesNormal = new ArrayList(1024);
    private final List renderInfosShadow = new ArrayList(1024);
    private final List renderInfosEntitiesShadow = new ArrayList(1024);
    private final List renderInfosTileEntitiesShadow = new ArrayList(1024);
    private int renderDistance = 0;
    private int renderDistanceSq = 0;
    private static final Set SET_ALL_FACINGS = Collections.unmodifiableSet(new HashSet<EnumFacing>(Arrays.asList(EnumFacing.VALUES)));
    private int countTileEntitiesRendered;
    private IChunkProvider worldChunkProvider = null;
    private LongHashMap worldChunkProviderMap = null;
    private int countLoadedChunksPrev = 0;
    private final RenderEnv renderEnv = new RenderEnv(Blocks.air.getDefaultState(), new BlockPos(0, 0, 0));
    public boolean renderOverlayDamaged = false;
    public boolean renderOverlayEyes = false;
    private boolean firstWorldLoad = false;
    private static int renderEntitiesCounter = 0;

    public RenderGlobal(Minecraft mcIn) {
        this.cloudRenderer = new CloudRenderer(mcIn);
        this.mc = mcIn;
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
        TextureMap texturemap = this.mc.getTextureMapBlocks();
        for (int i = 0; i < this.destroyBlockIcons.length; ++i) {
            this.destroyBlockIcons[i] = texturemap.getAtlasSprite("minecraft:blocks/destroy_stage_" + i);
        }
    }

    public void makeEntityOutlineShader() {
        if (OpenGlHelper.shadersSupported) {
            if (ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
                ShaderLinkHelper.setNewStaticShaderLinkHelper();
            }
            ResourceLocation resourcelocation = new ResourceLocation("shaders/post/entity_outline.json");
            try {
                this.entityOutlineShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), resourcelocation);
                this.entityOutlineShader.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
                this.entityOutlineFramebuffer = this.entityOutlineShader.getFramebufferRaw("final");
            }
            catch (JsonSyntaxException | IOException ioexception) {
                logger.warn("Failed to load shader: " + resourcelocation, ioexception);
                this.entityOutlineShader = null;
                this.entityOutlineFramebuffer = null;
            }
        } else {
            this.entityOutlineShader = null;
            this.entityOutlineFramebuffer = null;
        }
    }

    public void renderEntityOutlineFramebuffer() {
        if (this.isRenderEntityOutlines()) {
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            this.entityOutlineFramebuffer.framebufferRenderExt(this.mc.displayWidth, this.mc.displayHeight, false);
            GlStateManager.disableBlend();
        }
    }

    protected boolean isRenderEntityOutlines() {
        return !Config.isFastRender() && !Config.isShaders() && !Config.isAntialiasing() && this.entityOutlineFramebuffer != null && this.entityOutlineShader != null && this.mc.thePlayer != null && this.mc.thePlayer.isSpectator() && this.mc.gameSettings.keyBindSpectatorOutlines.isKeyDown();
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
        int i = 64;
        int j = 6;
        worldRendererIn.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        int k = (this.renderDistance / 64 + 1) * 64 + 64;
        for (int l = -k; l <= k; l += 64) {
            for (int i1 = -k; i1 <= k; i1 += 64) {
                float f = l;
                float f1 = l + 64;
                if (p_174968_3_) {
                    f1 = l;
                    f = l + 64;
                }
                worldRendererIn.func_181662_b(f, p_174968_2_, i1).func_181675_d();
                worldRendererIn.func_181662_b(f1, p_174968_2_, i1).func_181675_d();
                worldRendererIn.func_181662_b(f1, p_174968_2_, i1 + 64).func_181675_d();
                worldRendererIn.func_181662_b(f, p_174968_2_, i1 + 64).func_181675_d();
            }
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
        worldRendererIn.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        for (int i = 0; i < 1500; ++i) {
            double d0 = random.nextFloat() * 2.0f - 1.0f;
            double d1 = random.nextFloat() * 2.0f - 1.0f;
            double d2 = random.nextFloat() * 2.0f - 1.0f;
            double d3 = 0.15f + random.nextFloat() * 0.1f;
            double d4 = d0 * d0 + d1 * d1 + d2 * d2;
            if (!(d4 < 1.0) || !(d4 > 0.01)) continue;
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
            for (int j = 0; j < 4; ++j) {
                double d18 = (double)((j & 2) - 1) * d3;
                double d19 = (double)((j + 1 & 2) - 1) * d3;
                double d21 = d18 * d16 - d19 * d15;
                double d22 = d19 * d16 + d18 * d15;
                double d23 = d21 * d12 + 0.0 * d13;
                double d24 = 0.0 * d12 - d21 * d13;
                double d25 = d24 * d9 - d22 * d10;
                double d26 = d22 * d9 + d24 * d10;
                worldRendererIn.func_181662_b(d5 + d25, d6 + d23, d7 + d26).func_181675_d();
            }
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
        ChunkVisibility.reset();
        this.worldChunkProvider = null;
        this.worldChunkProviderMap = null;
        this.renderEnv.reset(null, null);
        Shaders.checkWorldChanged(this.theWorld);
        if (worldClientIn != null) {
            worldClientIn.addWorldAccess(this);
            this.loadRenderers();
        } else {
            this.chunksToUpdate.clear();
            this.clearRenderInfos();
            if (this.viewFrustum != null) {
                this.viewFrustum.deleteGlResources();
            }
            this.viewFrustum = null;
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
            SmartAnimations.update();
            this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
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
            this.generateStars();
            this.generateSky();
            this.generateSky2();
            if (this.viewFrustum != null) {
                this.viewFrustum.deleteGlResources();
            }
            this.stopChunkUpdates();
            Set<TileEntity> set = this.field_181024_n;
            synchronized (set) {
                this.field_181024_n.clear();
            }
            this.viewFrustum = new ViewFrustum(this.theWorld, this.mc.gameSettings.renderDistanceChunks, this, this.renderChunkFactory);
            if (this.theWorld != null && (entity = this.mc.getRenderViewEntity()) != null) {
                this.viewFrustum.updateChunkPositions(entity.posX, entity.posZ);
            }
            this.renderEntitiesStartupCounter = 2;
        }
        if (this.mc.thePlayer == null) {
            this.firstWorldLoad = true;
        }
    }

    protected void stopChunkUpdates() {
        this.chunksToUpdate.clear();
        this.renderDispatcher.stopChunkUpdates();
    }

    public void createBindEntityOutlineFbs(int p_72720_1_, int p_72720_2_) {
        if (OpenGlHelper.shadersSupported && this.entityOutlineShader != null) {
            this.entityOutlineShader.createBindFramebuffers(p_72720_1_, p_72720_2_);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void renderEntities(Entity renderViewEntity, ICamera camera, float partialTicks) {
        int i = 0;
        if (Reflector.MinecraftForgeClient_getRenderPass.exists()) {
            i = Reflector.callInt(Reflector.MinecraftForgeClient_getRenderPass, new Object[0]);
        }
        if (this.renderEntitiesStartupCounter > 0) {
            if (i > 0) {
                return;
            }
            --this.renderEntitiesStartupCounter;
        } else {
            double d0 = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * (double)partialTicks;
            double d1 = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * (double)partialTicks;
            double d2 = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * (double)partialTicks;
            this.theWorld.theProfiler.startSection("prepare");
            TileEntityRendererDispatcher.instance.cacheActiveRenderInfo(this.theWorld, this.mc.getTextureManager(), this.mc.fontRendererObj, this.mc.getRenderViewEntity(), partialTicks);
            this.renderManager.cacheActiveRenderInfo(this.theWorld, this.mc.fontRendererObj, this.mc.getRenderViewEntity(), this.mc.pointedEntity, this.mc.gameSettings, partialTicks);
            ++renderEntitiesCounter;
            if (i == 0) {
                this.countEntitiesTotal = 0;
                this.countEntitiesRendered = 0;
                this.countEntitiesHidden = 0;
                this.countTileEntitiesRendered = 0;
            }
            Entity entity = this.mc.getRenderViewEntity();
            double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
            double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
            double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;
            TileEntityRendererDispatcher.staticPlayerX = d3;
            TileEntityRendererDispatcher.staticPlayerY = d4;
            TileEntityRendererDispatcher.staticPlayerZ = d5;
            this.renderManager.setRenderPosition(d3, d4, d5);
            this.mc.entityRenderer.enableLightmap();
            this.theWorld.theProfiler.endStartSection("global");
            List<Entity> list = this.theWorld.getLoadedEntityList();
            if (i == 0) {
                this.countEntitiesTotal = list.size();
            }
            if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
                GlStateManager.disableFog();
            }
            boolean flag = Reflector.ForgeEntity_shouldRenderInPass.exists();
            boolean flag1 = Reflector.ForgeTileEntity_shouldRenderInPass.exists();
            for (int j = 0; j < this.theWorld.weatherEffects.size(); ++j) {
                Entity entity1 = (Entity)this.theWorld.weatherEffects.get(j);
                if (flag && !Reflector.callBoolean(entity1, Reflector.ForgeEntity_shouldRenderInPass, i)) continue;
                ++this.countEntitiesRendered;
                if (!entity1.isInRangeToRender3d(d0, d1, d2)) continue;
                this.renderManager.renderEntitySimple(entity1, partialTicks);
            }
            if (this.isRenderEntityOutlines()) {
                GlStateManager.depthFunc(519);
                GlStateManager.disableFog();
                this.entityOutlineFramebuffer.framebufferClear();
                this.entityOutlineFramebuffer.bindFramebuffer(false);
                this.theWorld.theProfiler.endStartSection("entityOutlines");
                RenderHelper.disableStandardItemLighting();
                this.renderManager.setRenderOutlines(true);
                for (Entity entity3 : list) {
                    boolean flag3;
                    boolean flag2 = this.mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping();
                    boolean bl = flag3 = entity3.isInRangeToRender3d(d0, d1, d2) && (entity3.ignoreFrustumCheck || camera.isBoundingBoxInFrustum(entity3.getEntityBoundingBox()) || entity3.riddenByEntity == this.mc.thePlayer) && entity3 instanceof EntityPlayer;
                    if (entity3 == this.mc.getRenderViewEntity() && this.mc.gameSettings.thirdPersonView == 0 && !flag2 || !flag3) continue;
                    this.renderManager.renderEntitySimple(entity3, partialTicks);
                }
                this.renderManager.setRenderOutlines(false);
                RenderHelper.enableStandardItemLighting();
                GlStateManager.depthMask(false);
                this.entityOutlineShader.loadShaderGroup(partialTicks);
                GlStateManager.enableLighting();
                GlStateManager.depthMask(true);
                this.mc.getFramebuffer().bindFramebuffer(false);
                GlStateManager.enableFog();
                GlStateManager.enableBlend();
                GlStateManager.enableColorMaterial();
                GlStateManager.depthFunc(515);
                GlStateManager.enableDepth();
                GlStateManager.enableAlpha();
            }
            this.theWorld.theProfiler.endStartSection("entities");
            boolean flag6 = Config.isShaders();
            if (flag6) {
                Shaders.beginEntities();
            }
            RenderItemFrame.updateItemRenderDistance();
            boolean flag7 = this.mc.gameSettings.fancyGraphics;
            this.mc.gameSettings.fancyGraphics = Config.isDroppedItemsFancy();
            for (Object e : this.renderInfosEntities) {
                ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = (ContainerLocalRenderInformation)e;
                Chunk chunk = renderglobal$containerlocalrenderinformation.renderChunk.getChunk();
                ClassInheritanceMultiMap<Entity> classinheritancemultimap = chunk.getEntityLists()[renderglobal$containerlocalrenderinformation.renderChunk.getPosition().getY() / 16];
                if (classinheritancemultimap.isEmpty()) continue;
                for (Entity entity2 : classinheritancemultimap) {
                    boolean flag4;
                    if (flag && !Reflector.callBoolean(entity2, Reflector.ForgeEntity_shouldRenderInPass, i)) continue;
                    boolean bl = flag4 = this.renderManager.shouldRender(entity2, camera, d0, d1, d2) || entity2.riddenByEntity == this.mc.thePlayer;
                    if (flag4) {
                        boolean flag5;
                        boolean bl2 = flag5 = this.mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping();
                        if (entity2 == this.mc.getRenderViewEntity() && this.mc.gameSettings.thirdPersonView == 0 && !flag5 || !(entity2.posY < 0.0) && !(entity2.posY >= 256.0) && !this.theWorld.isBlockLoaded(new BlockPos(entity2))) continue;
                        ++this.countEntitiesRendered;
                        this.renderedEntity = entity2;
                        if (flag6) {
                            Shaders.nextEntity(entity2);
                        }
                        this.renderManager.renderEntitySimple(entity2, partialTicks);
                        this.renderedEntity = null;
                    }
                    if (flag4 || !(entity2 instanceof EntityWitherSkull) || flag && !Reflector.callBoolean(entity2, Reflector.ForgeEntity_shouldRenderInPass, i)) continue;
                    this.renderedEntity = entity2;
                    if (flag6) {
                        Shaders.nextEntity(entity2);
                    }
                    this.mc.getRenderManager().renderWitherSkull(entity2, partialTicks);
                    this.renderedEntity = null;
                }
            }
            this.mc.gameSettings.fancyGraphics = flag7;
            if (flag6) {
                Shaders.endEntities();
                Shaders.beginBlockEntities();
            }
            this.theWorld.theProfiler.endStartSection("blockentities");
            RenderHelper.enableStandardItemLighting();
            if (Reflector.ForgeTileEntity_hasFastRenderer.exists()) {
                TileEntityRendererDispatcher.instance.preDrawBatch();
            }
            TileEntitySignRenderer.updateTextRenderDistance();
            for (Object e : this.renderInfosTileEntities) {
                ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation1 = (ContainerLocalRenderInformation)e;
                List<TileEntity> list1 = renderglobal$containerlocalrenderinformation1.renderChunk.getCompiledChunk().getTileEntities();
                if (list1.isEmpty()) continue;
                for (TileEntity tileentity1 : list1) {
                    AxisAlignedBB axisalignedbb1;
                    if (flag1 && (!Reflector.callBoolean(tileentity1, Reflector.ForgeTileEntity_shouldRenderInPass, i) || (axisalignedbb1 = (AxisAlignedBB)Reflector.call(tileentity1, Reflector.ForgeTileEntity_getRenderBoundingBox, new Object[0])) != null && !camera.isBoundingBoxInFrustum(axisalignedbb1))) continue;
                    if (flag6) {
                        Shaders.nextBlockEntity(tileentity1);
                    }
                    TileEntityRendererDispatcher.instance.renderTileEntity(tileentity1, partialTicks, -1);
                    ++this.countTileEntitiesRendered;
                }
            }
            Iterator<DestroyBlockProgress> iterator = this.field_181024_n;
            synchronized (iterator) {
                for (TileEntity tileentity : this.field_181024_n) {
                    if (flag1 && !Reflector.callBoolean(tileentity, Reflector.ForgeTileEntity_shouldRenderInPass, i)) continue;
                    if (flag6) {
                        Shaders.nextBlockEntity(tileentity);
                    }
                    TileEntityRendererDispatcher.instance.renderTileEntity(tileentity, partialTicks, -1);
                }
            }
            if (Reflector.ForgeTileEntity_hasFastRenderer.exists()) {
                TileEntityRendererDispatcher.instance.drawBatch(i);
            }
            this.renderOverlayDamaged = true;
            this.preRenderDamagedBlocks();
            for (DestroyBlockProgress destroyblockprogress : this.damagedBlocks.values()) {
                boolean flag8;
                BlockPos blockpos = destroyblockprogress.getPosition();
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
                    AxisAlignedBB axisalignedbb;
                    flag8 = false;
                    if (tileentity2 != null && Reflector.callBoolean(tileentity2, Reflector.ForgeTileEntity_shouldRenderInPass, i) && Reflector.callBoolean(tileentity2, Reflector.ForgeTileEntity_canRenderBreaking, new Object[0]) && (axisalignedbb = (AxisAlignedBB)Reflector.call(tileentity2, Reflector.ForgeTileEntity_getRenderBoundingBox, new Object[0])) != null) {
                        flag8 = camera.isBoundingBoxInFrustum(axisalignedbb);
                    }
                } else {
                    boolean bl = flag8 = tileentity2 != null && (block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockSign || block instanceof BlockSkull);
                }
                if (!flag8) continue;
                if (flag6) {
                    Shaders.nextBlockEntity(tileentity2);
                }
                TileEntityRendererDispatcher.instance.renderTileEntity(tileentity2, partialTicks, destroyblockprogress.getPartialBlockDamage());
            }
            this.postRenderDamagedBlocks();
            this.renderOverlayDamaged = false;
            if (flag6) {
                Shaders.endBlockEntities();
            }
            --renderEntitiesCounter;
            this.mc.entityRenderer.disableLightmap();
            this.mc.mcProfiler.endSection();
        }
    }

    public String getDebugInfoRenders() {
        int i = this.viewFrustum.renderChunks.length;
        int j = 0;
        for (ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
            CompiledChunk compiledchunk = renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk;
            if (compiledchunk == CompiledChunk.DUMMY || !compiledchunk.isEmpty()) continue;
            ++j;
        }
        Object[] objectArray = new Object[5];
        objectArray[0] = j;
        objectArray[1] = i;
        this.mc.getClass();
        objectArray[2] = "(s) ";
        objectArray[3] = this.renderDistanceChunks;
        objectArray[4] = this.renderDispatcher.getDebugInfo();
        return String.format("C: %d/%d %sD: %d, %s", objectArray);
    }

    public String getDebugInfoEntities() {
        return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ", B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered) + ", " + Config.getVersionDebug();
    }

    public void setupTerrain(Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator) {
        if (this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks) {
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
            DynamicLights.update(this);
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
        this.mc.mcProfiler.endStartSection("culling");
        BlockPos blockpos = new BlockPos(d3, d4 + (double)viewEntity.getEyeHeight(), d5);
        RenderChunk renderchunk = this.viewFrustum.getRenderChunk(blockpos);
        new BlockPos(MathHelper.floor_double(d3 / 16.0) * 16, MathHelper.floor_double(d4 / 16.0) * 16, MathHelper.floor_double(d5 / 16.0) * 16);
        this.displayListEntitiesDirty = this.displayListEntitiesDirty || !this.chunksToUpdate.isEmpty() || viewEntity.posX != this.lastViewEntityX || viewEntity.posY != this.lastViewEntityY || viewEntity.posZ != this.lastViewEntityZ || (double)viewEntity.rotationPitch != this.lastViewEntityPitch || (double)viewEntity.rotationYaw != this.lastViewEntityYaw;
        this.lastViewEntityX = viewEntity.posX;
        this.lastViewEntityY = viewEntity.posY;
        this.lastViewEntityZ = viewEntity.posZ;
        this.lastViewEntityPitch = viewEntity.rotationPitch;
        this.lastViewEntityYaw = viewEntity.rotationYaw;
        boolean flag = this.debugFixedClippingHelper != null;
        this.mc.mcProfiler.endStartSection("update");
        Lagometer.timerVisibility.start();
        int i = this.getCountLoadedChunks();
        if (i != this.countLoadedChunksPrev) {
            this.countLoadedChunksPrev = i;
            this.displayListEntitiesDirty = true;
        }
        int j = 256;
        if (!ChunkVisibility.isFinished()) {
            this.displayListEntitiesDirty = true;
        }
        if (!flag && this.displayListEntitiesDirty && Config.isIntegratedServerRunning()) {
            j = ChunkVisibility.getMaxChunkY(this.theWorld, viewEntity, this.renderDistanceChunks);
        }
        RenderChunk renderchunk1 = this.viewFrustum.getRenderChunk(new BlockPos(viewEntity.posX, viewEntity.posY, viewEntity.posZ));
        if (Shaders.isShadowPass) {
            this.renderInfos = this.renderInfosShadow;
            this.renderInfosEntities = this.renderInfosEntitiesShadow;
            this.renderInfosTileEntities = this.renderInfosTileEntitiesShadow;
            if (!flag && this.displayListEntitiesDirty) {
                this.clearRenderInfos();
                if (renderchunk1 != null && renderchunk1.getPosition().getY() > j) {
                    this.renderInfosEntities.add(renderchunk1.getRenderInfo());
                }
                Iterator<RenderChunk> iterator = ShadowUtils.makeShadowChunkIterator(this.theWorld, partialTicks, viewEntity, this.renderDistanceChunks, this.viewFrustum);
                while (iterator.hasNext()) {
                    RenderChunk renderchunk2 = iterator.next();
                    if (renderchunk2 == null || renderchunk2.getPosition().getY() > j) continue;
                    ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = renderchunk2.getRenderInfo();
                    if (renderchunk2.compiledChunk.isEmpty() || renderchunk2.isNeedsUpdate()) {
                        this.renderInfos.add(renderglobal$containerlocalrenderinformation);
                    }
                    if (ChunkUtils.hasEntities(renderchunk2.getChunk())) {
                        this.renderInfosEntities.add(renderglobal$containerlocalrenderinformation);
                    }
                    if (renderchunk2.getCompiledChunk().getTileEntities().size() <= 0) continue;
                    this.renderInfosTileEntities.add(renderglobal$containerlocalrenderinformation);
                }
            }
        } else {
            this.renderInfos = this.renderInfosNormal;
            this.renderInfosEntities = this.renderInfosEntitiesNormal;
            this.renderInfosTileEntities = this.renderInfosTileEntitiesNormal;
        }
        if (!flag && this.displayListEntitiesDirty && !Shaders.isShadowPass) {
            this.displayListEntitiesDirty = false;
            this.clearRenderInfos();
            this.visibilityDeque.clear();
            Deque deque = this.visibilityDeque;
            boolean flag1 = this.mc.renderChunksMany;
            if (renderchunk != null && renderchunk.getPosition().getY() <= j) {
                boolean flag2 = false;
                ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation4 = new ContainerLocalRenderInformation(renderchunk, null, 0);
                HashSet set1 = new HashSet(SET_ALL_FACINGS);
                if (set1.size() == 1) {
                    Vector3f vector3f = this.getViewVector(viewEntity, partialTicks);
                    EnumFacing enumfacing2 = EnumFacing.getFacingFromVector(vector3f.x, vector3f.y, vector3f.z).getOpposite();
                    set1.remove(enumfacing2);
                }
                if (set1.isEmpty()) {
                    flag2 = true;
                }
                if (flag2 && !playerSpectator) {
                    this.renderInfos.add(renderglobal$containerlocalrenderinformation4);
                } else {
                    if (playerSpectator && this.theWorld.getBlockState(blockpos).getBlock().isOpaqueCube()) {
                        flag1 = false;
                    }
                    renderchunk.setFrameIndex(frameCount);
                    deque.add(renderglobal$containerlocalrenderinformation4);
                }
            } else {
                int j1;
                int n = j1 = blockpos.getY() > 0 ? Math.min(j, 248) : 8;
                if (renderchunk1 != null) {
                    this.renderInfosEntities.add(renderchunk1.getRenderInfo());
                }
                for (int k = -this.renderDistanceChunks; k <= this.renderDistanceChunks; ++k) {
                    for (int l = -this.renderDistanceChunks; l <= this.renderDistanceChunks; ++l) {
                        RenderChunk renderchunk3 = this.viewFrustum.getRenderChunk(new BlockPos((k << 4) + 8, j1, (l << 4) + 8));
                        if (renderchunk3 == null || !renderchunk3.isBoundingBoxInFrustum(camera, frameCount)) continue;
                        renderchunk3.setFrameIndex(frameCount);
                        ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation1 = renderchunk3.getRenderInfo();
                        renderglobal$containerlocalrenderinformation1.initialize(null, 0);
                        deque.add(renderglobal$containerlocalrenderinformation1);
                    }
                }
            }
            this.mc.mcProfiler.startSection("iteration");
            boolean flag3 = Config.isFogOn();
            while (!deque.isEmpty()) {
                ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation5 = (ContainerLocalRenderInformation)deque.poll();
                RenderChunk renderchunk6 = renderglobal$containerlocalrenderinformation5.renderChunk;
                EnumFacing enumfacing1 = renderglobal$containerlocalrenderinformation5.facing;
                CompiledChunk compiledchunk = renderchunk6.compiledChunk;
                if (compiledchunk.isEmpty() || renderchunk6.isNeedsUpdate()) {
                    this.renderInfos.add(renderglobal$containerlocalrenderinformation5);
                }
                if (ChunkUtils.hasEntities(renderchunk6.getChunk())) {
                    this.renderInfosEntities.add(renderglobal$containerlocalrenderinformation5);
                }
                if (compiledchunk.getTileEntities().size() > 0) {
                    this.renderInfosTileEntities.add(renderglobal$containerlocalrenderinformation5);
                }
                for (EnumFacing enumfacing : flag1 ? ChunkVisibility.getFacingsNotOpposite(renderglobal$containerlocalrenderinformation5.setFacing) : EnumFacing.VALUES) {
                    RenderChunk renderchunk4;
                    if (flag1 && enumfacing1 != null && !compiledchunk.isVisible(enumfacing1.getOpposite(), enumfacing) || (renderchunk4 = this.getRenderChunkOffset(blockpos, renderchunk6, enumfacing, flag3, j)) == null || !renderchunk4.setFrameIndex(frameCount) || !renderchunk4.isBoundingBoxInFrustum(camera, frameCount)) continue;
                    int i1 = renderglobal$containerlocalrenderinformation5.setFacing | 1 << enumfacing.ordinal();
                    ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation2 = renderchunk4.getRenderInfo();
                    renderglobal$containerlocalrenderinformation2.initialize(enumfacing, i1);
                    deque.add(renderglobal$containerlocalrenderinformation2);
                }
            }
            this.mc.mcProfiler.endSection();
        }
        this.mc.mcProfiler.endStartSection("captureFrustum");
        if (this.debugFixTerrainFrustum) {
            this.fixTerrainFrustum(d3, d4, d5);
            this.debugFixTerrainFrustum = false;
        }
        Lagometer.timerVisibility.end();
        if (Shaders.isShadowPass) {
            Shaders.mcProfilerEndSection();
        } else {
            this.mc.mcProfiler.endStartSection("rebuildNear");
            this.renderDispatcher.clearChunkUpdates();
            Set<RenderChunk> set = this.chunksToUpdate;
            this.chunksToUpdate = Sets.newLinkedHashSet();
            Lagometer.timerChunkUpdate.start();
            for (ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation3 : this.renderInfos) {
                boolean flag4;
                RenderChunk renderchunk5 = renderglobal$containerlocalrenderinformation3.renderChunk;
                if (!renderchunk5.isNeedsUpdate() && !set.contains(renderchunk5)) continue;
                this.displayListEntitiesDirty = true;
                BlockPos blockpos1 = renderchunk5.getPosition();
                boolean bl = flag4 = blockpos.distanceSq(blockpos1.getX() + 8, blockpos1.getY() + 8, blockpos1.getZ() + 8) < 768.0;
                if (!flag4) {
                    this.chunksToUpdate.add(renderchunk5);
                    continue;
                }
                if (!renderchunk5.isPlayerUpdate()) {
                    this.chunksToUpdateForced.add(renderchunk5);
                    continue;
                }
                this.mc.mcProfiler.startSection("build near");
                this.renderDispatcher.updateChunkNow(renderchunk5);
                renderchunk5.setNeedsUpdate(false);
                this.mc.mcProfiler.endSection();
            }
            Lagometer.timerChunkUpdate.end();
            this.chunksToUpdate.addAll(set);
            this.mc.mcProfiler.endSection();
        }
    }

    private RenderChunk getRenderChunkOffset(BlockPos p_getRenderChunkOffset_1_, RenderChunk p_getRenderChunkOffset_2_, EnumFacing p_getRenderChunkOffset_3_, boolean p_getRenderChunkOffset_4_, int p_getRenderChunkOffset_5_) {
        RenderChunk renderchunk = p_getRenderChunkOffset_2_.getRenderChunkNeighbour(p_getRenderChunkOffset_3_);
        if (renderchunk == null) {
            return null;
        }
        if (renderchunk.getPosition().getY() > p_getRenderChunkOffset_5_) {
            return null;
        }
        if (p_getRenderChunkOffset_4_) {
            int j;
            BlockPos blockpos = renderchunk.getPosition();
            int i = p_getRenderChunkOffset_1_.getX() - blockpos.getX();
            int k = i * i + (j = p_getRenderChunkOffset_1_.getZ() - blockpos.getZ()) * j;
            if (k > this.renderDistanceSq) {
                return null;
            }
        }
        return renderchunk;
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
        for (int i = 0; i < 8; ++i) {
            net.minecraft.util.Matrix4f.transform((Matrix4f)matrix4f2, (Vector4f)this.debugTerrainMatrix[i], (Vector4f)this.debugTerrainMatrix[i]);
            this.debugTerrainMatrix[i].x /= this.debugTerrainMatrix[i].w;
            this.debugTerrainMatrix[i].y /= this.debugTerrainMatrix[i].w;
            this.debugTerrainMatrix[i].z /= this.debugTerrainMatrix[i].w;
            this.debugTerrainMatrix[i].w = 1.0f;
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

    public void renderBlockLayer(EnumWorldBlockLayer blockLayerIn, double partialTicks, int pass, Entity entityIn) {
        RenderHelper.disableStandardItemLighting();
        if (blockLayerIn == EnumWorldBlockLayer.TRANSLUCENT && !Shaders.isShadowPass) {
            this.mc.mcProfiler.startSection("translucent_sort");
            double d0 = entityIn.posX - this.prevRenderSortX;
            double d1 = entityIn.posY - this.prevRenderSortY;
            double d2 = entityIn.posZ - this.prevRenderSortZ;
            if (d0 * d0 + d1 * d1 + d2 * d2 > 1.0) {
                this.prevRenderSortX = entityIn.posX;
                this.prevRenderSortY = entityIn.posY;
                this.prevRenderSortZ = entityIn.posZ;
                int k = 0;
                this.chunksToResortTransparency.clear();
                for (ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
                    if (!renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk.isLayerStarted(blockLayerIn) || k++ >= 15) continue;
                    this.chunksToResortTransparency.add(renderglobal$containerlocalrenderinformation.renderChunk);
                }
            }
            this.mc.mcProfiler.endSection();
        }
        this.mc.mcProfiler.startSection("filterempty");
        int l = 0;
        boolean flag = blockLayerIn == EnumWorldBlockLayer.TRANSLUCENT;
        int i1 = flag ? this.renderInfos.size() - 1 : 0;
        int i = flag ? -1 : this.renderInfos.size();
        int j1 = flag ? -1 : 1;
        for (int j = i1; j != i; j += j1) {
            RenderChunk renderchunk = this.renderInfos.get((int)j).renderChunk;
            if (!renderchunk.getCompiledChunk().isLayerEmpty(blockLayerIn)) continue;
            ++l;
            this.renderContainer.addRenderChunk(renderchunk, blockLayerIn);
        }
        if (l != 0) {
            if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
                GlStateManager.disableFog();
            }
            this.mc.mcProfiler.endStartSection("render_" + (Object)((Object)blockLayerIn));
            this.renderBlockLayer(blockLayerIn);
        }
        this.mc.mcProfiler.endSection();
    }

    private void renderBlockLayer(EnumWorldBlockLayer blockLayerIn) {
        this.mc.entityRenderer.enableLightmap();
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
            ShadersRender.preRenderChunkLayer(blockLayerIn);
        }
        this.renderContainer.renderChunkLayer(blockLayerIn);
        if (Config.isShaders()) {
            ShadersRender.postRenderChunkLayer(blockLayerIn);
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
        this.mc.entityRenderer.disableLightmap();
    }

    private void cleanupDamagedBlocks(Iterator<DestroyBlockProgress> iteratorIn) {
        while (iteratorIn.hasNext()) {
            DestroyBlockProgress destroyblockprogress = iteratorIn.next();
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
            for (int i = 0; i < 6; ++i) {
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
                worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
                int j = 40;
                int k = 40;
                int l = 40;
                if (Config.isCustomColors()) {
                    Vec3 vec3 = new Vec3((double)j / 255.0, (double)k / 255.0, (double)l / 255.0);
                    vec3 = CustomColors.getWorldSkyColor(vec3, this.theWorld, this.mc.getRenderViewEntity(), 0.0f);
                    j = (int)(vec3.xCoord * 255.0);
                    k = (int)(vec3.yCoord * 255.0);
                    l = (int)(vec3.zCoord * 255.0);
                }
                worldrenderer.func_181662_b(-100.0, -100.0, -100.0).func_181673_a(0.0, 0.0).func_181669_b(j, k, l, 255).func_181675_d();
                worldrenderer.func_181662_b(-100.0, -100.0, 100.0).func_181673_a(0.0, 16.0).func_181669_b(j, k, l, 255).func_181675_d();
                worldrenderer.func_181662_b(100.0, -100.0, 100.0).func_181673_a(16.0, 16.0).func_181669_b(j, k, l, 255).func_181675_d();
                worldrenderer.func_181662_b(100.0, -100.0, -100.0).func_181673_a(16.0, 0.0).func_181669_b(j, k, l, 255).func_181675_d();
                tessellator.draw();
                GlStateManager.popMatrix();
            }
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }
    }

    public void renderSky(float partialTicks, int pass) {
        WorldProvider worldprovider;
        Object object;
        if (Reflector.ForgeWorldProvider_getSkyRenderer.exists() && (object = Reflector.call(worldprovider = this.mc.theWorld.provider, Reflector.ForgeWorldProvider_getSkyRenderer, new Object[0])) != null) {
            Reflector.callVoid(object, Reflector.IRenderHandler_render, Float.valueOf(partialTicks), this.theWorld, this.mc);
            return;
        }
        if (this.mc.theWorld.provider.getDimensionId() == 1) {
            this.renderSkyEnd();
        } else if (this.mc.theWorld.provider.isSurfaceWorld()) {
            float f17;
            GlStateManager.disableTexture2D();
            boolean flag = Config.isShaders();
            if (flag) {
                Shaders.disableTexture2D();
            }
            Vec3 vec3 = this.theWorld.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
            vec3 = CustomColors.getSkyColor(vec3, this.mc.theWorld, this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY + 1.0, this.mc.getRenderViewEntity().posZ);
            if (flag) {
                Shaders.setSkyColor(vec3);
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
            if (flag) {
                Shaders.enableFog();
            }
            GlStateManager.color(f, f1, f2);
            if (flag) {
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
            if (flag) {
                Shaders.disableFog();
            }
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.disableStandardItemLighting();
            float[] afloat = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(partialTicks), partialTicks);
            if (afloat != null && Config.isSunMoonEnabled()) {
                GlStateManager.disableTexture2D();
                if (flag) {
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
                worldrenderer.func_181668_a(6, DefaultVertexFormats.field_181706_f);
                worldrenderer.func_181662_b(0.0, 100.0, 0.0).func_181666_a(f6, f7, f8, afloat[3]).func_181675_d();
                for (int l = 0; l <= 16; ++l) {
                    float f18 = (float)l * (float)Math.PI * 2.0f / 16.0f;
                    float f12 = MathHelper.sin(f18);
                    float f13 = MathHelper.cos(f18);
                    worldrenderer.func_181662_b(f12 * 120.0f, f13 * 120.0f, -f13 * 40.0f * afloat[3]).func_181666_a(afloat[0], afloat[1], afloat[2], 0.0f).func_181675_d();
                }
                tessellator.draw();
                GlStateManager.popMatrix();
                GlStateManager.shadeModel(7424);
            }
            GlStateManager.enableTexture2D();
            if (flag) {
                Shaders.enableTexture2D();
            }
            GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
            GlStateManager.pushMatrix();
            float f15 = 1.0f - this.theWorld.getRainStrength(partialTicks);
            GlStateManager.color(1.0f, 1.0f, 1.0f, f15);
            GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
            CustomSky.renderSky(this.theWorld, this.renderEngine, partialTicks);
            if (flag) {
                Shaders.preCelestialRotate();
            }
            GlStateManager.rotate(this.theWorld.getCelestialAngle(partialTicks) * 360.0f, 1.0f, 0.0f, 0.0f);
            if (flag) {
                Shaders.postCelestialRotate();
            }
            float f16 = 30.0f;
            if (Config.isSunTexture()) {
                this.renderEngine.bindTexture(locationSunPng);
                worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
                worldrenderer.func_181662_b(-f16, 100.0, -f16).func_181673_a(0.0, 0.0).func_181675_d();
                worldrenderer.func_181662_b(f16, 100.0, -f16).func_181673_a(1.0, 0.0).func_181675_d();
                worldrenderer.func_181662_b(f16, 100.0, f16).func_181673_a(1.0, 1.0).func_181675_d();
                worldrenderer.func_181662_b(-f16, 100.0, f16).func_181673_a(0.0, 1.0).func_181675_d();
                tessellator.draw();
            }
            f16 = 20.0f;
            if (Config.isMoonTexture()) {
                this.renderEngine.bindTexture(locationMoonPhasesPng);
                int i = this.theWorld.getMoonPhase();
                int k = i % 4;
                int i1 = i / 4 % 2;
                float f19 = (float)k / 4.0f;
                float f21 = (float)i1 / 2.0f;
                float f23 = (float)(k + 1) / 4.0f;
                float f14 = (float)(i1 + 1) / 2.0f;
                worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
                worldrenderer.func_181662_b(-f16, -100.0, f16).func_181673_a(f23, f14).func_181675_d();
                worldrenderer.func_181662_b(f16, -100.0, f16).func_181673_a(f19, f14).func_181675_d();
                worldrenderer.func_181662_b(f16, -100.0, -f16).func_181673_a(f19, f21).func_181675_d();
                worldrenderer.func_181662_b(-f16, -100.0, -f16).func_181673_a(f23, f21).func_181675_d();
                tessellator.draw();
            }
            GlStateManager.disableTexture2D();
            if (flag) {
                Shaders.disableTexture2D();
            }
            if ((f17 = this.theWorld.getStarBrightness(partialTicks) * f15) > 0.0f && Config.isStarsEnabled() && !CustomSky.hasSkyLayers(this.theWorld)) {
                GlStateManager.color(f17, f17, f17, f17);
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
            if (flag) {
                Shaders.enableFog();
            }
            GlStateManager.popMatrix();
            GlStateManager.disableTexture2D();
            if (flag) {
                Shaders.disableTexture2D();
            }
            GlStateManager.color(0.0f, 0.0f, 0.0f);
            double d0 = this.mc.thePlayer.getPositionEyes((float)partialTicks).yCoord - this.theWorld.getHorizon();
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
                float f22 = -((float)(d0 + 65.0));
                worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
                worldrenderer.func_181662_b(-1.0, f22, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(1.0, f22, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(1.0, -1.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(-1.0, -1.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(-1.0, -1.0, -1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(1.0, -1.0, -1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(1.0, f22, -1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(-1.0, f22, -1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(1.0, -1.0, -1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(1.0, -1.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(1.0, f22, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(1.0, f22, -1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(-1.0, f22, -1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(-1.0, f22, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(-1.0, -1.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(-1.0, -1.0, -1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(-1.0, -1.0, -1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(-1.0, -1.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(1.0, -1.0, 1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                worldrenderer.func_181662_b(1.0, -1.0, -1.0).func_181669_b(0, 0, 0, 255).func_181675_d();
                tessellator.draw();
            }
            if (this.theWorld.provider.isSkyColored()) {
                GlStateManager.color(f * 0.2f + 0.04f, f1 * 0.2f + 0.04f, f2 * 0.6f + 0.1f);
            } else {
                GlStateManager.color(f, f1, f2);
            }
            if (this.mc.gameSettings.renderDistanceChunks <= 4) {
                GlStateManager.color(this.mc.entityRenderer.fogColorRed, this.mc.entityRenderer.fogColorGreen, this.mc.entityRenderer.fogColorBlue);
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, -((float)(d0 - 16.0)), 0.0f);
            if (Config.isSkyEnabled()) {
                if (this.vboEnabled) {
                    this.sky2VBO.bindBuffer();
                    GlStateManager.glEnableClientState(32884);
                    GlStateManager.glVertexPointer(3, 5126, 12, 0);
                    this.sky2VBO.drawArrays(7);
                    this.sky2VBO.unbindBuffer();
                    GlStateManager.glDisableClientState(32884);
                } else {
                    GlStateManager.callList(this.glSkyList2);
                }
            }
            GlStateManager.popMatrix();
            GlStateManager.enableTexture2D();
            if (flag) {
                Shaders.enableTexture2D();
            }
            GlStateManager.depthMask(true);
        }
    }

    public void renderClouds(float partialTicks, int pass) {
        if (Config.isCloudsOff()) {
            WorldProvider worldprovider;
            Object object;
            if (Reflector.ForgeWorldProvider_getCloudRenderer.exists() && (object = Reflector.call(worldprovider = this.mc.theWorld.provider, Reflector.ForgeWorldProvider_getCloudRenderer, new Object[0])) != null) {
                Reflector.callVoid(object, Reflector.IRenderHandler_render, Float.valueOf(partialTicks), this.theWorld, this.mc);
                return;
            }
            if (this.mc.theWorld.provider.isSurfaceWorld()) {
                if (Config.isShaders()) {
                    Shaders.beginClouds();
                }
                if (Config.isCloudsFancy()) {
                    this.renderCloudsFancy(pass);
                } else {
                    float f9 = partialTicks;
                    partialTicks = 0.0f;
                    GlStateManager.disableCull();
                    float f10 = (float)(this.mc.getRenderViewEntity().lastTickPosY + (this.mc.getRenderViewEntity().posY - this.mc.getRenderViewEntity().lastTickPosY) * (double)partialTicks);
                    Tessellator tessellator = Tessellator.getInstance();
                    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                    this.renderEngine.bindTexture(locationCloudsPng);
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                    Vec3 vec3 = this.theWorld.getCloudColour(partialTicks);
                    float f = (float)vec3.xCoord;
                    float f1 = (float)vec3.yCoord;
                    float f2 = (float)vec3.zCoord;
                    this.cloudRenderer.prepareToRender(false, this.cloudTickCounter, f9, vec3);
                    if (this.cloudRenderer.shouldUpdateGlList()) {
                        this.cloudRenderer.startUpdateGlList();
                        if (pass != 2) {
                            float f3 = (f * 30.0f + f1 * 59.0f + f2 * 11.0f) / 100.0f;
                            float f4 = (f * 30.0f + f1 * 70.0f) / 100.0f;
                            float f5 = (f * 30.0f + f2 * 70.0f) / 100.0f;
                            f = f3;
                            f1 = f4;
                            f2 = f5;
                        }
                        float f11 = 4.8828125E-4f;
                        double d2 = (float)this.cloudTickCounter + partialTicks;
                        double d0 = this.mc.getRenderViewEntity().prevPosX + (this.mc.getRenderViewEntity().posX - this.mc.getRenderViewEntity().prevPosX) * (double)partialTicks + d2 * (double)0.03f;
                        double d1 = this.mc.getRenderViewEntity().prevPosZ + (this.mc.getRenderViewEntity().posZ - this.mc.getRenderViewEntity().prevPosZ) * (double)partialTicks;
                        int k = MathHelper.floor_double(d0 / 2048.0);
                        int l = MathHelper.floor_double(d1 / 2048.0);
                        float f6 = this.theWorld.provider.getCloudHeight() - f10 + 0.33f;
                        f6 += this.mc.gameSettings.ofCloudsHeight * 128.0f;
                        float f7 = (float)((d0 -= (double)(k * 2048)) * 4.8828125E-4);
                        float f8 = (float)((d1 -= (double)(l * 2048)) * 4.8828125E-4);
                        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
                        for (int i1 = -256; i1 < 256; i1 += 32) {
                            for (int j1 = -256; j1 < 256; j1 += 32) {
                                worldrenderer.func_181662_b(i1, f6, j1 + 32).func_181673_a((float)i1 * 4.8828125E-4f + f7, (float)(j1 + 32) * 4.8828125E-4f + f8).func_181666_a(f, f1, f2, 0.8f).func_181675_d();
                                worldrenderer.func_181662_b(i1 + 32, f6, j1 + 32).func_181673_a((float)(i1 + 32) * 4.8828125E-4f + f7, (float)(j1 + 32) * 4.8828125E-4f + f8).func_181666_a(f, f1, f2, 0.8f).func_181675_d();
                                worldrenderer.func_181662_b(i1 + 32, f6, j1).func_181673_a((float)(i1 + 32) * 4.8828125E-4f + f7, (float)j1 * 4.8828125E-4f + f8).func_181666_a(f, f1, f2, 0.8f).func_181675_d();
                                worldrenderer.func_181662_b(i1, f6, j1).func_181673_a((float)i1 * 4.8828125E-4f + f7, (float)j1 * 4.8828125E-4f + f8).func_181666_a(f, f1, f2, 0.8f).func_181675_d();
                            }
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

    public boolean hasCloudFog() {
        return false;
    }

    private void renderCloudsFancy(int pass) {
        float partialTicks = 0.0f;
        GlStateManager.disableCull();
        float f = (float)(this.mc.getRenderViewEntity().lastTickPosY + (this.mc.getRenderViewEntity().posY - this.mc.getRenderViewEntity().lastTickPosY) * (double)partialTicks);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        double d0 = (float)this.cloudTickCounter + partialTicks;
        double d1 = (this.mc.getRenderViewEntity().prevPosX + (this.mc.getRenderViewEntity().posX - this.mc.getRenderViewEntity().prevPosX) * (double)partialTicks + d0 * (double)0.03f) / 12.0;
        double d2 = (this.mc.getRenderViewEntity().prevPosZ + (this.mc.getRenderViewEntity().posZ - this.mc.getRenderViewEntity().prevPosZ) * (double)partialTicks) / 12.0 + (double)0.33f;
        float f3 = this.theWorld.provider.getCloudHeight() - f + 0.33f;
        f3 += this.mc.gameSettings.ofCloudsHeight * 128.0f;
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
        this.cloudRenderer.prepareToRender(true, this.cloudTickCounter, partialTicks, vec3);
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
        float f17 = (float)MathHelper.floor_double(d1) * 0.00390625f;
        float f18 = (float)MathHelper.floor_double(d2) * 0.00390625f;
        float f19 = (float)(d1 - (double)MathHelper.floor_double(d1));
        float f20 = (float)(d2 - (double)MathHelper.floor_double(d2));
        GlStateManager.scale(12.0f, 1.0f, 12.0f);
        for (int i1 = 0; i1 < 2; ++i1) {
            if (i1 == 0) {
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
        }
        if (this.cloudRenderer.shouldUpdateGlList()) {
            this.cloudRenderer.startUpdateGlList();
            for (int l1 = -3; l1 <= 4; ++l1) {
                for (int j1 = -3; j1 <= 4; ++j1) {
                    worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181712_l);
                    float f22 = l1 * 8;
                    float f23 = j1 * 8;
                    float f24 = f22 - f19;
                    float f25 = f23 - f20;
                    if (f3 > -5.0f) {
                        worldrenderer.func_181662_b(f24 + 0.0f, f3 + 0.0f, f25 + 8.0f).func_181673_a((f22 + 0.0f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).func_181666_a(f10, f11, f12, 0.8f).func_181663_c(0.0f, -1.0f, 0.0f).func_181675_d();
                        worldrenderer.func_181662_b(f24 + 8.0f, f3 + 0.0f, f25 + 8.0f).func_181673_a((f22 + 8.0f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).func_181666_a(f10, f11, f12, 0.8f).func_181663_c(0.0f, -1.0f, 0.0f).func_181675_d();
                        worldrenderer.func_181662_b(f24 + 8.0f, f3 + 0.0f, f25 + 0.0f).func_181673_a((f22 + 8.0f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).func_181666_a(f10, f11, f12, 0.8f).func_181663_c(0.0f, -1.0f, 0.0f).func_181675_d();
                        worldrenderer.func_181662_b(f24 + 0.0f, f3 + 0.0f, f25 + 0.0f).func_181673_a((f22 + 0.0f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).func_181666_a(f10, f11, f12, 0.8f).func_181663_c(0.0f, -1.0f, 0.0f).func_181675_d();
                    }
                    if (f3 <= 5.0f) {
                        worldrenderer.func_181662_b(f24 + 0.0f, f3 + 4.0f - 9.765625E-4f, f25 + 8.0f).func_181673_a((f22 + 0.0f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).func_181666_a(f4, f5, f6, 0.8f).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
                        worldrenderer.func_181662_b(f24 + 8.0f, f3 + 4.0f - 9.765625E-4f, f25 + 8.0f).func_181673_a((f22 + 8.0f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).func_181666_a(f4, f5, f6, 0.8f).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
                        worldrenderer.func_181662_b(f24 + 8.0f, f3 + 4.0f - 9.765625E-4f, f25 + 0.0f).func_181673_a((f22 + 8.0f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).func_181666_a(f4, f5, f6, 0.8f).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
                        worldrenderer.func_181662_b(f24 + 0.0f, f3 + 4.0f - 9.765625E-4f, f25 + 0.0f).func_181673_a((f22 + 0.0f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).func_181666_a(f4, f5, f6, 0.8f).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
                    }
                    if (l1 > -1) {
                        for (int k1 = 0; k1 < 8; ++k1) {
                            worldrenderer.func_181662_b(f24 + (float)k1 + 0.0f, f3 + 0.0f, f25 + 8.0f).func_181673_a((f22 + (float)k1 + 0.5f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).func_181666_a(f26, f27, f28, 0.8f).func_181663_c(-1.0f, 0.0f, 0.0f).func_181675_d();
                            worldrenderer.func_181662_b(f24 + (float)k1 + 0.0f, f3 + 4.0f, f25 + 8.0f).func_181673_a((f22 + (float)k1 + 0.5f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).func_181666_a(f26, f27, f28, 0.8f).func_181663_c(-1.0f, 0.0f, 0.0f).func_181675_d();
                            worldrenderer.func_181662_b(f24 + (float)k1 + 0.0f, f3 + 4.0f, f25 + 0.0f).func_181673_a((f22 + (float)k1 + 0.5f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).func_181666_a(f26, f27, f28, 0.8f).func_181663_c(-1.0f, 0.0f, 0.0f).func_181675_d();
                            worldrenderer.func_181662_b(f24 + (float)k1 + 0.0f, f3 + 0.0f, f25 + 0.0f).func_181673_a((f22 + (float)k1 + 0.5f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).func_181666_a(f26, f27, f28, 0.8f).func_181663_c(-1.0f, 0.0f, 0.0f).func_181675_d();
                        }
                    }
                    if (l1 <= 1) {
                        for (int i2 = 0; i2 < 8; ++i2) {
                            worldrenderer.func_181662_b(f24 + (float)i2 + 1.0f - 9.765625E-4f, f3 + 0.0f, f25 + 8.0f).func_181673_a((f22 + (float)i2 + 0.5f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).func_181666_a(f26, f27, f28, 0.8f).func_181663_c(1.0f, 0.0f, 0.0f).func_181675_d();
                            worldrenderer.func_181662_b(f24 + (float)i2 + 1.0f - 9.765625E-4f, f3 + 4.0f, f25 + 8.0f).func_181673_a((f22 + (float)i2 + 0.5f) * 0.00390625f + f17, (f23 + 8.0f) * 0.00390625f + f18).func_181666_a(f26, f27, f28, 0.8f).func_181663_c(1.0f, 0.0f, 0.0f).func_181675_d();
                            worldrenderer.func_181662_b(f24 + (float)i2 + 1.0f - 9.765625E-4f, f3 + 4.0f, f25 + 0.0f).func_181673_a((f22 + (float)i2 + 0.5f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).func_181666_a(f26, f27, f28, 0.8f).func_181663_c(1.0f, 0.0f, 0.0f).func_181675_d();
                            worldrenderer.func_181662_b(f24 + (float)i2 + 1.0f - 9.765625E-4f, f3 + 0.0f, f25 + 0.0f).func_181673_a((f22 + (float)i2 + 0.5f) * 0.00390625f + f17, (f23 + 0.0f) * 0.00390625f + f18).func_181666_a(f26, f27, f28, 0.8f).func_181663_c(1.0f, 0.0f, 0.0f).func_181675_d();
                        }
                    }
                    if (j1 > -1) {
                        for (int j2 = 0; j2 < 8; ++j2) {
                            worldrenderer.func_181662_b(f24 + 0.0f, f3 + 4.0f, f25 + (float)j2 + 0.0f).func_181673_a((f22 + 0.0f) * 0.00390625f + f17, (f23 + (float)j2 + 0.5f) * 0.00390625f + f18).func_181666_a(f13, f14, f15, 0.8f).func_181663_c(0.0f, 0.0f, -1.0f).func_181675_d();
                            worldrenderer.func_181662_b(f24 + 8.0f, f3 + 4.0f, f25 + (float)j2 + 0.0f).func_181673_a((f22 + 8.0f) * 0.00390625f + f17, (f23 + (float)j2 + 0.5f) * 0.00390625f + f18).func_181666_a(f13, f14, f15, 0.8f).func_181663_c(0.0f, 0.0f, -1.0f).func_181675_d();
                            worldrenderer.func_181662_b(f24 + 8.0f, f3 + 0.0f, f25 + (float)j2 + 0.0f).func_181673_a((f22 + 8.0f) * 0.00390625f + f17, (f23 + (float)j2 + 0.5f) * 0.00390625f + f18).func_181666_a(f13, f14, f15, 0.8f).func_181663_c(0.0f, 0.0f, -1.0f).func_181675_d();
                            worldrenderer.func_181662_b(f24 + 0.0f, f3 + 0.0f, f25 + (float)j2 + 0.0f).func_181673_a((f22 + 0.0f) * 0.00390625f + f17, (f23 + (float)j2 + 0.5f) * 0.00390625f + f18).func_181666_a(f13, f14, f15, 0.8f).func_181663_c(0.0f, 0.0f, -1.0f).func_181675_d();
                        }
                    }
                    if (j1 <= 1) {
                        for (int k2 = 0; k2 < 8; ++k2) {
                            worldrenderer.func_181662_b(f24 + 0.0f, f3 + 4.0f, f25 + (float)k2 + 1.0f - 9.765625E-4f).func_181673_a((f22 + 0.0f) * 0.00390625f + f17, (f23 + (float)k2 + 0.5f) * 0.00390625f + f18).func_181666_a(f13, f14, f15, 0.8f).func_181663_c(0.0f, 0.0f, 1.0f).func_181675_d();
                            worldrenderer.func_181662_b(f24 + 8.0f, f3 + 4.0f, f25 + (float)k2 + 1.0f - 9.765625E-4f).func_181673_a((f22 + 8.0f) * 0.00390625f + f17, (f23 + (float)k2 + 0.5f) * 0.00390625f + f18).func_181666_a(f13, f14, f15, 0.8f).func_181663_c(0.0f, 0.0f, 1.0f).func_181675_d();
                            worldrenderer.func_181662_b(f24 + 8.0f, f3 + 0.0f, f25 + (float)k2 + 1.0f - 9.765625E-4f).func_181673_a((f22 + 8.0f) * 0.00390625f + f17, (f23 + (float)k2 + 0.5f) * 0.00390625f + f18).func_181666_a(f13, f14, f15, 0.8f).func_181663_c(0.0f, 0.0f, 1.0f).func_181675_d();
                            worldrenderer.func_181662_b(f24 + 0.0f, f3 + 0.0f, f25 + (float)k2 + 1.0f - 9.765625E-4f).func_181673_a((f22 + 0.0f) * 0.00390625f + f17, (f23 + (float)k2 + 0.5f) * 0.00390625f + f18).func_181666_a(f13, f14, f15, 0.8f).func_181663_c(0.0f, 0.0f, 1.0f).func_181675_d();
                        }
                    }
                    tessellator.draw();
                }
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
            RenderChunk renderchunk;
            Iterator iterator = this.chunksToUpdateForced.iterator();
            while (iterator.hasNext() && this.renderDispatcher.updateChunkLater(renderchunk = (RenderChunk)iterator.next())) {
                renderchunk.setNeedsUpdate(false);
                iterator.remove();
                this.chunksToUpdate.remove(renderchunk);
                this.chunksToResortTransparency.remove(renderchunk);
            }
        }
        if (this.chunksToResortTransparency.size() > 0 && (iterator2 = this.chunksToResortTransparency.iterator()).hasNext() && this.renderDispatcher.updateTransparencyLater(renderchunk2 = (RenderChunk)iterator2.next())) {
            iterator2.remove();
        }
        double d1 = 0.0;
        int i = Config.getUpdatesPerFrame();
        if (!this.chunksToUpdate.isEmpty()) {
            RenderChunk renderchunk1;
            boolean flag;
            boolean flag1;
            Iterator<RenderChunk> iterator1 = this.chunksToUpdate.iterator();
            while (iterator1.hasNext() && (flag1 = (flag = (renderchunk1 = iterator1.next()).isChunkRegionEmpty()) ? this.renderDispatcher.updateChunkNow(renderchunk1) : this.renderDispatcher.updateChunkLater(renderchunk1))) {
                double d0;
                renderchunk1.setNeedsUpdate(false);
                iterator1.remove();
                if (flag || !((d1 += (d0 = 2.0 * RenderChunkUtils.getRelativeBufferSize(renderchunk1))) > (double)i)) continue;
                break;
            }
        }
    }

    public void renderWorldBorder(Entity p_180449_1_, float partialTicks) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        WorldBorder worldborder = this.theWorld.getWorldBorder();
        double d0 = this.mc.gameSettings.renderDistanceChunks * 16;
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
            worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
            worldrenderer.setTranslation(-d2, -d3, -d4);
            double d5 = Math.max((double)MathHelper.floor_double(d4 - d0), worldborder.minZ());
            double d6 = Math.min((double)MathHelper.ceiling_double_int(d4 + d0), worldborder.maxZ());
            if (d2 > worldborder.maxX() - d0) {
                float f7 = 0.0f;
                double d7 = d5;
                while (d7 < d6) {
                    double d8 = Math.min(1.0, d6 - d7);
                    float f8 = (float)d8 * 0.5f;
                    worldrenderer.func_181662_b(worldborder.maxX(), 256.0, d7).func_181673_a(f3 + f7, f3 + 0.0f).func_181675_d();
                    worldrenderer.func_181662_b(worldborder.maxX(), 256.0, d7 + d8).func_181673_a(f3 + f8 + f7, f3 + 0.0f).func_181675_d();
                    worldrenderer.func_181662_b(worldborder.maxX(), 0.0, d7 + d8).func_181673_a(f3 + f8 + f7, f3 + 128.0f).func_181675_d();
                    worldrenderer.func_181662_b(worldborder.maxX(), 0.0, d7).func_181673_a(f3 + f7, f3 + 128.0f).func_181675_d();
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
                    worldrenderer.func_181662_b(worldborder.minX(), 256.0, d9).func_181673_a(f3 + f9, f3 + 0.0f).func_181675_d();
                    worldrenderer.func_181662_b(worldborder.minX(), 256.0, d9 + d12).func_181673_a(f3 + f12 + f9, f3 + 0.0f).func_181675_d();
                    worldrenderer.func_181662_b(worldborder.minX(), 0.0, d9 + d12).func_181673_a(f3 + f12 + f9, f3 + 128.0f).func_181675_d();
                    worldrenderer.func_181662_b(worldborder.minX(), 0.0, d9).func_181673_a(f3 + f9, f3 + 128.0f).func_181675_d();
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
                    worldrenderer.func_181662_b(d10, 256.0, worldborder.maxZ()).func_181673_a(f3 + f10, f3 + 0.0f).func_181675_d();
                    worldrenderer.func_181662_b(d10 + d13, 256.0, worldborder.maxZ()).func_181673_a(f3 + f13 + f10, f3 + 0.0f).func_181675_d();
                    worldrenderer.func_181662_b(d10 + d13, 0.0, worldborder.maxZ()).func_181673_a(f3 + f13 + f10, f3 + 128.0f).func_181675_d();
                    worldrenderer.func_181662_b(d10, 0.0, worldborder.maxZ()).func_181673_a(f3 + f10, f3 + 128.0f).func_181675_d();
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
                    worldrenderer.func_181662_b(d11, 256.0, worldborder.minZ()).func_181673_a(f3 + f11, f3 + 0.0f).func_181675_d();
                    worldrenderer.func_181662_b(d11 + d14, 256.0, worldborder.minZ()).func_181673_a(f3 + f14 + f11, f3 + 0.0f).func_181675_d();
                    worldrenderer.func_181662_b(d11 + d14, 0.0, worldborder.minZ()).func_181673_a(f3 + f14 + f11, f3 + 128.0f).func_181675_d();
                    worldrenderer.func_181662_b(d11, 0.0, worldborder.minZ()).func_181673_a(f3 + f11, f3 + 128.0f).func_181675_d();
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
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
        }
    }

    private void preRenderDamagedBlocks() {
        GlStateManager.tryBlendFuncSeparate(774, 768, 1, 0);
        GlStateManager.enableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.5f);
        GlStateManager.doPolygonOffset(-1.0f, -10.0f);
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
            worldRendererIn.func_181668_a(7, DefaultVertexFormats.BLOCK);
            worldRendererIn.setTranslation(-d0, -d1, -d2);
            worldRendererIn.markDirty();
            Iterator<DestroyBlockProgress> iterator = this.damagedBlocks.values().iterator();
            while (iterator.hasNext()) {
                boolean flag;
                DestroyBlockProgress destroyblockprogress = iterator.next();
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
                        flag1 = Reflector.callBoolean(tileentity, Reflector.ForgeTileEntity_canRenderBreaking, new Object[0]);
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
                BlockRendererDispatcher blockrendererdispatcher = this.mc.getBlockRendererDispatcher();
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
            BlockPos blockpos = movingObjectPositionIn.getBlockPos();
            Block block = this.theWorld.getBlockState(blockpos).getBlock();
            if (block.getMaterial() != Material.air && this.theWorld.getWorldBorder().contains(blockpos)) {
                block.setBlockBoundsBasedOnState(this.theWorld, blockpos);
                double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
                double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
                double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;
                AxisAlignedBB axisalignedbb = block.getSelectedBoundingBox(this.theWorld, blockpos);
                Block.EnumOffsetType block$enumoffsettype = block.getOffsetType();
                if (block$enumoffsettype != Block.EnumOffsetType.NONE) {
                    axisalignedbb = BlockModelUtils.getOffsetBoundingBox(axisalignedbb, block$enumoffsettype, blockpos);
                }
                RenderGlobal.func_181561_a(axisalignedbb.expand(0.002f, 0.002f, 0.002f).offset(-d0, -d1, -d2));
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
        worldrenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).func_181675_d();
        worldrenderer.func_181662_b(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).func_181675_d();
        worldrenderer.func_181662_b(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).func_181675_d();
        worldrenderer.func_181662_b(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).func_181675_d();
        worldrenderer.func_181662_b(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).func_181675_d();
        tessellator.draw();
        worldrenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).func_181675_d();
        worldrenderer.func_181662_b(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).func_181675_d();
        worldrenderer.func_181662_b(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).func_181675_d();
        worldrenderer.func_181662_b(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).func_181675_d();
        worldrenderer.func_181662_b(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).func_181675_d();
        tessellator.draw();
        worldrenderer.func_181668_a(1, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).func_181675_d();
        worldrenderer.func_181662_b(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).func_181675_d();
        worldrenderer.func_181662_b(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).func_181675_d();
        worldrenderer.func_181662_b(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).func_181675_d();
        worldrenderer.func_181662_b(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).func_181675_d();
        worldrenderer.func_181662_b(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).func_181675_d();
        worldrenderer.func_181662_b(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).func_181675_d();
        worldrenderer.func_181662_b(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).func_181675_d();
        tessellator.draw();
    }

    public static void func_181563_a(AxisAlignedBB p_181563_0_, int p_181563_1_, int p_181563_2_, int p_181563_3_, int p_181563_4_) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        worldrenderer.func_181662_b(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        worldrenderer.func_181662_b(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        worldrenderer.func_181662_b(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        worldrenderer.func_181662_b(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        worldrenderer.func_181662_b(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        tessellator.draw();
        worldrenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        worldrenderer.func_181662_b(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        worldrenderer.func_181662_b(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        worldrenderer.func_181662_b(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        worldrenderer.func_181662_b(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        worldrenderer.func_181662_b(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        tessellator.draw();
        worldrenderer.func_181668_a(1, DefaultVertexFormats.field_181706_f);
        worldrenderer.func_181662_b(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        worldrenderer.func_181662_b(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        worldrenderer.func_181662_b(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        worldrenderer.func_181662_b(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        worldrenderer.func_181662_b(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        worldrenderer.func_181662_b(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        worldrenderer.func_181662_b(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
        worldrenderer.func_181662_b(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).func_181675_d();
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
        ISound isound = this.mapSoundPositions.get(blockPosIn);
        if (isound != null) {
            this.mc.getSoundHandler().stopSound(isound);
            this.mapSoundPositions.remove(blockPosIn);
        }
        if (recordName != null) {
            ItemRecord itemrecord = ItemRecord.getRecord(recordName);
            if (itemrecord != null) {
                this.mc.ingameGUI.setRecordPlayingMessage(itemrecord.getRecordNameLocal());
            }
            PositionedSoundRecord positionedsoundrecord = PositionedSoundRecord.create(new ResourceLocation(recordName), blockPosIn.getX(), blockPosIn.getY(), blockPosIn.getZ());
            this.mapSoundPositions.put(blockPosIn, positionedsoundrecord);
            this.mc.getSoundHandler().playSound(positionedsoundrecord);
        }
    }

    @Override
    public void playSound(String soundName, double x, double y, double z, float volume, float pitch) {
    }

    @Override
    public void playSoundToNearExcept(EntityPlayer except, String soundName, double x, double y, double z, float volume, float pitch) {
    }

    @Override
    public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int ... p_180442_15_) {
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
            crashreportcategory.addCrashSectionCallable("Position", () -> CrashReportCategory.getCoordinateInfo(xCoord, yCoord, zCoord));
            throw new ReportedException(crashreport);
        }
    }

    private void spawnParticle(EnumParticleTypes particleIn, double p_174972_2_, double p_174972_4_, double p_174972_6_, double p_174972_8_, double p_174972_10_, double p_174972_12_, int ... p_174972_14_) {
        this.spawnParticle(particleIn.getParticleID(), particleIn.getShouldIgnoreRange(), p_174972_2_, p_174972_4_, p_174972_6_, p_174972_8_, p_174972_10_, p_174972_12_, p_174972_14_);
    }

    private EntityFX spawnEntityFX(int p_174974_1_, boolean ignoreRange, double p_174974_3_, double p_174974_5_, double p_174974_7_, double p_174974_9_, double p_174974_11_, double p_174974_13_, int ... p_174974_15_) {
        if (this.mc != null && this.mc.getRenderViewEntity() != null && this.mc.effectRenderer != null) {
            int i = this.mc.gameSettings.particleSetting;
            if (i == 1 && this.theWorld.rand.nextInt(3) == 0) {
                i = 2;
            }
            double d0 = this.mc.getRenderViewEntity().posX - p_174974_3_;
            double d1 = this.mc.getRenderViewEntity().posY - p_174974_5_;
            double d2 = this.mc.getRenderViewEntity().posZ - p_174974_7_;
            if (p_174974_1_ == EnumParticleTypes.EXPLOSION_HUGE.getParticleID() && Config.isAnimatedExplosion()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.EXPLOSION_LARGE.getParticleID() && Config.isAnimatedExplosion()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.EXPLOSION_NORMAL.getParticleID() && Config.isAnimatedExplosion()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SUSPENDED.getParticleID() && !Config.isWaterParticles()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SUSPENDED_DEPTH.getParticleID() && !Config.isVoidParticles()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SMOKE_NORMAL.getParticleID() && Config.isAnimatedSmoke()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SMOKE_LARGE.getParticleID() && Config.isAnimatedSmoke()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SPELL_MOB.getParticleID() && Config.isPotionParticles()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID() && Config.isPotionParticles()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SPELL.getParticleID() && Config.isPotionParticles()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SPELL_INSTANT.getParticleID() && Config.isPotionParticles()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.SPELL_WITCH.getParticleID() && Config.isPotionParticles()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.PORTAL.getParticleID() && !Config.isPortalParticles()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.FLAME.getParticleID() && !Config.isAnimatedFlame()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.REDSTONE.getParticleID() && !Config.isAnimatedRedstone()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.DRIP_WATER.getParticleID() && Config.isDrippingWaterLava()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.DRIP_LAVA.getParticleID() && Config.isDrippingWaterLava()) {
                return null;
            }
            if (p_174974_1_ == EnumParticleTypes.FIREWORKS_SPARK.getParticleID() && !Config.isFireworkParticles()) {
                return null;
            }
            if (!ignoreRange) {
                double d3 = 1024.0;
                if (p_174974_1_ == EnumParticleTypes.CRIT.getParticleID()) {
                    d3 = 38416.0;
                }
                if (d0 * d0 + d1 * d1 + d2 * d2 > d3) {
                    return null;
                }
                if (i > 1) {
                    return null;
                }
            }
            EntityFX entityfx = this.mc.effectRenderer.spawnEffectParticle(p_174974_1_, p_174974_3_, p_174974_5_, p_174974_7_, p_174974_9_, p_174974_11_, p_174974_13_, p_174974_15_);
            if (p_174974_1_ == EnumParticleTypes.WATER_BUBBLE.getParticleID()) {
                CustomColors.updateWaterFX(entityfx, this.theWorld, p_174974_3_, p_174974_5_, p_174974_7_, this.renderEnv);
            }
            if (p_174974_1_ == EnumParticleTypes.WATER_SPLASH.getParticleID()) {
                CustomColors.updateWaterFX(entityfx, this.theWorld, p_174974_3_, p_174974_5_, p_174974_7_, this.renderEnv);
            }
            if (p_174974_1_ == EnumParticleTypes.WATER_DROP.getParticleID()) {
                CustomColors.updateWaterFX(entityfx, this.theWorld, p_174974_3_, p_174974_5_, p_174974_7_, this.renderEnv);
            }
            if (p_174974_1_ == EnumParticleTypes.TOWN_AURA.getParticleID()) {
                CustomColors.updateMyceliumFX(entityfx);
            }
            if (p_174974_1_ == EnumParticleTypes.PORTAL.getParticleID()) {
                CustomColors.updatePortalFX(entityfx);
            }
            if (p_174974_1_ == EnumParticleTypes.REDSTONE.getParticleID()) {
                CustomColors.updateReddustFX(entityfx, this.theWorld, p_174974_3_, p_174974_5_, p_174974_7_);
            }
            return entityfx;
        }
        return null;
    }

    @Override
    public void onEntityAdded(Entity entityIn) {
        RandomEntities.entityLoaded(entityIn, this.theWorld);
    }

    @Override
    public void onEntityRemoved(Entity entityIn) {
        if (Config.isDynamicLights()) {
            DynamicLights.entityRemoved(entityIn, this);
        }
    }

    @Override
    public void broadcastSound(int p_180440_1_, BlockPos p_180440_2_, int p_180440_3_) {
        switch (p_180440_1_) {
            case 1013: 
            case 1018: {
                if (this.mc.getRenderViewEntity() == null) break;
                double d0 = (double)p_180440_2_.getX() - this.mc.getRenderViewEntity().posX;
                double d1 = (double)p_180440_2_.getY() - this.mc.getRenderViewEntity().posY;
                double d2 = (double)p_180440_2_.getZ() - this.mc.getRenderViewEntity().posZ;
                double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                double d4 = this.mc.getRenderViewEntity().posX;
                double d5 = this.mc.getRenderViewEntity().posY;
                double d6 = this.mc.getRenderViewEntity().posZ;
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
                int i = p_180439_4_ % 3 - 1;
                int j = p_180439_4_ / 3 % 3 - 1;
                double d0 = (double)blockPosIn.getX() + (double)i * 0.6 + 0.5;
                double d1 = (double)blockPosIn.getY() + 0.5;
                double d2 = (double)blockPosIn.getZ() + (double)j * 0.6 + 0.5;
                for (int i1 = 0; i1 < 10; ++i1) {
                    double d15 = random.nextDouble() * 0.2 + 0.01;
                    double d16 = d0 + (double)i * 0.01 + (random.nextDouble() - 0.5) * (double)j * 0.5;
                    double d17 = d1 + (random.nextDouble() - 0.5) * 0.5;
                    double d18 = d2 + (double)j * 0.01 + (random.nextDouble() - 0.5) * (double)i * 0.5;
                    double d19 = (double)i * d15 + random.nextGaussian() * 0.01;
                    double d20 = -0.03 + random.nextGaussian() * 0.01;
                    double d21 = (double)j * d15 + random.nextGaussian() * 0.01;
                    this.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d16, d17, d18, d19, d20, d21, new int[0]);
                }
                return;
            }
            case 2001: {
                Block block = Block.getBlockById(p_180439_4_ & 0xFFF);
                if (block.getMaterial() != Material.air) {
                    this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(block.stepSound.getBreakSound()), (block.stepSound.getVolume() + 1.0f) / 2.0f, block.stepSound.getFrequency() * 0.8f, (float)blockPosIn.getX() + 0.5f, (float)blockPosIn.getY() + 0.5f, (float)blockPosIn.getZ() + 0.5f));
                }
                this.mc.effectRenderer.addBlockDestroyEffects(blockPosIn, block.getStateFromMeta(p_180439_4_ >> 12 & 0xFF));
                break;
            }
            case 2002: {
                double d3 = blockPosIn.getX();
                double d4 = blockPosIn.getY();
                double d5 = blockPosIn.getZ();
                for (int k = 0; k < 8; ++k) {
                    this.spawnParticle(EnumParticleTypes.ITEM_CRACK, d3, d4, d5, random.nextGaussian() * 0.15, random.nextDouble() * 0.2, random.nextGaussian() * 0.15, Item.getIdFromItem(Items.potionitem), p_180439_4_);
                }
                int j1 = Items.potionitem.getColorFromDamage(p_180439_4_);
                float f = (float)(j1 >> 16 & 0xFF) / 255.0f;
                float f1 = (float)(j1 >> 8 & 0xFF) / 255.0f;
                float f2 = (float)(j1 & 0xFF) / 255.0f;
                EnumParticleTypes enumparticletypes = EnumParticleTypes.SPELL;
                if (Items.potionitem.isEffectInstant(p_180439_4_)) {
                    enumparticletypes = EnumParticleTypes.SPELL_INSTANT;
                }
                for (int k1 = 0; k1 < 100; ++k1) {
                    double d7 = random.nextDouble() * 4.0;
                    double d9 = random.nextDouble() * Math.PI * 2.0;
                    double d11 = Math.cos(d9) * d7;
                    double d23 = 0.01 + random.nextDouble() * 0.5;
                    double d24 = Math.sin(d9) * d7;
                    EntityFX entityfx = this.spawnEntityFX(enumparticletypes.getParticleID(), enumparticletypes.getShouldIgnoreRange(), d3 + d11 * 0.1, d4 + 0.3, d5 + d24 * 0.1, d11, d23, d24, new int[0]);
                    if (entityfx == null) continue;
                    float f3 = 0.75f + random.nextFloat() * 0.25f;
                    entityfx.setRBGColorF(f * f3, f1 * f3, f2 * f3);
                    entityfx.multiplyVelocity((float)d7);
                }
                this.theWorld.playSoundAtPos(blockPosIn, "game.potion.smash", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2003: {
                double d6 = (double)blockPosIn.getX() + 0.5;
                double d8 = blockPosIn.getY();
                double d10 = (double)blockPosIn.getZ() + 0.5;
                for (int l1 = 0; l1 < 8; ++l1) {
                    this.spawnParticle(EnumParticleTypes.ITEM_CRACK, d6, d8, d10, random.nextGaussian() * 0.15, random.nextDouble() * 0.2, random.nextGaussian() * 0.15, Item.getIdFromItem(Items.ender_eye));
                }
                for (double d22 = 0.0; d22 < Math.PI * 2; d22 += 0.15707963267948966) {
                    this.spawnParticle(EnumParticleTypes.PORTAL, d6 + Math.cos(d22) * 5.0, d8 - 0.4, d10 + Math.sin(d22) * 5.0, Math.cos(d22) * -5.0, 0.0, Math.sin(d22) * -5.0, new int[0]);
                    this.spawnParticle(EnumParticleTypes.PORTAL, d6 + Math.cos(d22) * 5.0, d8 - 0.4, d10 + Math.sin(d22) * 5.0, Math.cos(d22) * -7.0, 0.0, Math.sin(d22) * -7.0, new int[0]);
                }
                return;
            }
            case 2004: {
                for (int l = 0; l < 20; ++l) {
                    double d12 = (double)blockPosIn.getX() + 0.5 + ((double)this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    double d13 = (double)blockPosIn.getY() + 0.5 + ((double)this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    double d14 = (double)blockPosIn.getZ() + 0.5 + ((double)this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    this.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d12, d13, d14, 0.0, 0.0, 0.0, new int[0]);
                    this.theWorld.spawnParticle(EnumParticleTypes.FLAME, d12, d13, d14, 0.0, 0.0, 0.0, new int[0]);
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
            DestroyBlockProgress destroyblockprogress = this.damagedBlocks.get(breakerId);
            if (destroyblockprogress == null || destroyblockprogress.getPosition().getX() != pos.getX() || destroyblockprogress.getPosition().getY() != pos.getY() || destroyblockprogress.getPosition().getZ() != pos.getZ()) {
                destroyblockprogress = new DestroyBlockProgress(pos);
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

    public boolean hasNoChunkUpdates() {
        return !this.chunksToUpdate.isEmpty() || !this.renderDispatcher.hasChunkUpdates();
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

    public int getCountLoadedChunks() {
        if (this.theWorld == null) {
            return 0;
        }
        IChunkProvider ichunkprovider = this.theWorld.getChunkProvider();
        if (ichunkprovider == null) {
            return 0;
        }
        if (ichunkprovider != this.worldChunkProvider) {
            this.worldChunkProvider = ichunkprovider;
            this.worldChunkProviderMap = (LongHashMap)Reflector.getFieldValue(ichunkprovider, Reflector.ChunkProviderClient_chunkMapping);
        }
        return this.worldChunkProviderMap == null ? 0 : this.worldChunkProviderMap.getNumHashElements();
    }

    public int getCountChunksToUpdate() {
        return this.chunksToUpdate.size();
    }

    public RenderChunk getRenderChunk(BlockPos p_getRenderChunk_1_) {
        return this.viewFrustum.getRenderChunk(p_getRenderChunk_1_);
    }

    public WorldClient getWorld() {
        return this.theWorld;
    }

    private void clearRenderInfos() {
        if (renderEntitiesCounter > 0) {
            this.renderInfos = new ArrayList<ContainerLocalRenderInformation>(this.renderInfos.size() + 16);
            this.renderInfosEntities = new ArrayList(this.renderInfosEntities.size() + 16);
            this.renderInfosTileEntities = new ArrayList(this.renderInfosTileEntities.size() + 16);
        } else {
            this.renderInfos.clear();
            this.renderInfosEntities.clear();
            this.renderInfosTileEntities.clear();
        }
    }

    public void onPlayerPositionSet() {
        if (this.firstWorldLoad) {
            this.loadRenderers();
            this.firstWorldLoad = false;
        }
    }

    public void pauseChunkUpdates() {
        this.renderDispatcher.pauseChunkUpdates();
    }

    public void resumeChunkUpdates() {
        this.renderDispatcher.resumeChunkUpdates();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void func_181023_a(Collection<TileEntity> p_181023_1_, Collection<TileEntity> p_181023_2_) {
        Set<TileEntity> set = this.field_181024_n;
        synchronized (set) {
            this.field_181024_n.removeAll(p_181023_1_);
            this.field_181024_n.addAll(p_181023_2_);
        }
    }

    public static class ContainerLocalRenderInformation {
        final RenderChunk renderChunk;
        EnumFacing facing;
        int setFacing;

        public ContainerLocalRenderInformation(RenderChunk p_i2_1_, EnumFacing p_i2_2_, int p_i2_3_) {
            this.renderChunk = p_i2_1_;
            this.facing = p_i2_2_;
            this.setFacing = p_i2_3_;
        }

        private void initialize(EnumFacing p_initialize_1_, int p_initialize_2_) {
            this.facing = p_initialize_1_;
            this.setFacing = p_initialize_2_;
        }
    }
}

