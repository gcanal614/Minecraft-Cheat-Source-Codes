/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.vecmath.Vector3d
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.GLU
 */
package de.fanta.module.impl.visual;

import de.fanta.events.Event;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.ColorValue;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.setting.settings.Slider;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class ESP
extends Module {
    public static ESP INSTANCE;
    float ani = 0.0f;
    boolean max = false;
    private Frustum frustum = new Frustum();
    private static final Minecraft mc;
    private static final Frustum frustrum;
    private static final IntBuffer viewport;
    private static final FloatBuffer modelview;
    private static final FloatBuffer projection;
    private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
    private final ArrayList<Entity> collectedEntities = new ArrayList();
    public static double RGB;
    public static double LineWith;
    protected ModelBase mainModel;

    static {
        mc = Minecraft.getMinecraft();
        frustrum = new Frustum();
        viewport = GLAllocation.createDirectIntBuffer(16);
        modelview = GLAllocation.createDirectFloatBuffer(16);
        projection = GLAllocation.createDirectFloatBuffer(16);
    }

    public ESP() {
        super("ESP", 0, Module.Type.Visual, Color.green);
        this.settings.add(new Setting("RGBColors", new Slider(1.0, 20.0, 0.1, 4.0)));
        this.settings.add(new Setting("LineWith", new Slider(1.0, 3.3, 0.1, 1.0)));
        this.settings.add(new Setting("Rainbow", new CheckBox(false)));
        this.settings.add(new Setting("2DBlur", new CheckBox(false)));
        this.settings.add(new Setting("RGBReversed", new CheckBox(false)));
        this.settings.add(new Setting("ESPModes", new DropdownBox("Shader", new String[]{"Shader", "Box", "Outline", "NewOutline", "Circle", "Real2d"})));
        this.settings.add(new Setting("Color", new ColorValue(Color.RED.getRGB())));
    }

    public int getColor2() {
        try {
            return ((ColorValue)this.getSetting((String)"Color").getSetting()).color;
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }

    @Override
    public void onEnable() {
        RenderGlobal.makeEntityOutlineShader();
        super.onEnable();
    }

    /*
     * Exception decompiling
     */
    @Override
    public void onEvent(Event event) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Can't sort instructions [@NONE, blocks:[8] lbl250 : CaseStatement: default:\u000a, @NONE, blocks:[8] lbl250 : CaseStatement: default:\u000a]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.CompareByIndex.compare(CompareByIndex.java:25)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.CompareByIndex.compare(CompareByIndex.java:8)
         *     at java.util.TimSort.countRunAndMakeAscending(TimSort.java:360)
         *     at java.util.TimSort.sort(TimSort.java:220)
         *     at java.util.Arrays.sort(Arrays.java:1512)
         *     at java.util.ArrayList.sort(ArrayList.java:1464)
         *     at java.util.Collections.sort(Collections.java:177)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.buildSwitchCases(SwitchReplacer.java:271)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitch(SwitchReplacer.java:258)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:517)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private boolean isInViewFrustrum(Entity entity) {
        return this.isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }

    private boolean isInViewFrustrum(AxisAlignedBB bb) {
        Entity current = mc.getRenderViewEntity();
        this.frustum.setPosition(current.posX, current.posY, current.posZ);
        return this.frustum.isBoundingBoxInFrustum(bb);
    }

    private double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }

    private Vector3d project2D(int scaleFactor, double x, double y, double z) {
        GL11.glGetFloat((int)2982, (FloatBuffer)modelview);
        GL11.glGetFloat((int)2983, (FloatBuffer)projection);
        GL11.glGetInteger((int)2978, (IntBuffer)viewport);
        if (GLU.gluProject((float)((float)x), (float)((float)y), (float)((float)z), (FloatBuffer)modelview, (FloatBuffer)projection, (IntBuffer)viewport, (FloatBuffer)this.vector)) {
            return new Vector3d((double)(this.vector.get(0) / (float)scaleFactor), (double)(((float)Display.getHeight() - this.vector.get(1)) / (float)scaleFactor), (double)this.vector.get(2));
        }
        return null;
    }

    private void collectEntities() {
        this.collectedEntities.clear();
        List playerEntities = ESP.mc.theWorld.loadedEntityList;
        for (Entity entity : playerEntities) {
            if (!(entity instanceof EntityPlayer) || entity instanceof EntityPlayerSP || entity.isDead) continue;
            this.collectedEntities.add(entity);
        }
    }

    public static void drawRect(double x1, double y1, double x2, double y2, int argbColor) {
        if (x1 < x2) {
            double temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 < y2) {
            double temp1 = y1;
            y1 = y2;
            y2 = temp1;
        }
        float a = (float)(argbColor >> 24 & 0xFF) / 255.0f;
        float r = (float)(argbColor >> 16 & 0xFF) / 255.0f;
        float g = (float)(argbColor >> 8 & 0xFF) / 255.0f;
        float b = (float)(argbColor & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(r, g, b, a);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x1, y2, 0.0).endVertex();
        worldrenderer.pos(x2, y2, 0.0).endVertex();
        worldrenderer.pos(x2, y1, 0.0).endVertex();
        worldrenderer.pos(x1, y1, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawChestESP() {
        List loadedTileEntityList = Minecraft.getMinecraft().theWorld.loadedTileEntityList;
        int i = 0;
        int loadedTileEntityListSize = loadedTileEntityList.size();
        while (i < loadedTileEntityListSize) {
            TileEntity tileEntity = (TileEntity)loadedTileEntityList.get(i);
            if (tileEntity instanceof TileEntityChest) {
                GlStateManager.disableTexture2D();
                TileEntityRendererDispatcher.instance.renderTileEntity(tileEntity, Minecraft.getMinecraft().timer.renderPartialTicks, 1);
                GlStateManager.enableTexture2D();
            }
            ++i;
        }
    }
}

