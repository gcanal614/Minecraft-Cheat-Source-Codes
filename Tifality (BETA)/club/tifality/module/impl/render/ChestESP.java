/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.render;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.render.Render3DEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.ModuleManager;
import club.tifality.property.Property;
import club.tifality.property.impl.EnumProperty;
import club.tifality.utils.Wrapper;
import club.tifality.utils.render.OGLUtils;
import club.tifality.utils.render.RenderingUtils;
import java.awt.Color;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

@ModuleInfo(label="ChestESP", category=ModuleCategory.RENDER)
public final class ChestESP
extends Module {
    public final EnumProperty<Mode> mode = new EnumProperty<Mode>("Mode", Mode.BOX);
    public final Property<Boolean> flatValue = new Property<Boolean>("Flat", Boolean.FALSE, this::isChams);
    public static final Property<Integer> colorValue = new Property<Integer>("Visible Color", new Color(89, 128, 230, 77).getRGB());
    public final Property<Integer> colorInvisibleValue = new Property<Integer>("Invisible Color", new Color(89, 128, 230).getRGB(), () -> this.mode.get() == Mode.CSGO);
    private static ChestESP instance;

    @Listener
    public void onRender3DEvent(Render3DEvent event) {
        if (this.mode.get() == Mode.BOX) {
            for (TileEntity entity : Wrapper.getWorld().loadedTileEntityList) {
                Color color = null;
                if (entity instanceof TileEntityChest) {
                    color = new Color(colorValue.get());
                }
                if (entity instanceof TileEntityEnderChest) {
                    color = new Color(255, 0, 255, 77);
                }
                if (color == null) continue;
                BlockPos pos = entity.getPos();
                GL11.glPushMatrix();
                GL11.glDisable(2929);
                OGLUtils.startBlending();
                GL11.glDepthMask(false);
                GL11.glDisable(3553);
                RenderingUtils.glColor(color);
                GL11.glTranslated(-RenderManager.renderPosX, -RenderManager.renderPosY, -RenderManager.renderPosZ);
                RenderGlobal.func_181561_a(entity.getBlockType().getCollisionBoundingBox(Wrapper.getWorld(), pos, entity.getBlockType().getStateFromMeta(entity.getBlockMetadata())), false, true);
                GL11.glEnable(2929);
                OGLUtils.endBlending();
                GL11.glDepthMask(true);
                GL11.glEnable(3553);
                GL11.glPopMatrix();
            }
        }
    }

    public static ChestESP getInstance() {
        return instance != null ? instance : (instance = ModuleManager.getInstance(ChestESP.class));
    }

    public static void preOccludedRender(int occludedColor, boolean occludedFlat) {
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        if (occludedFlat) {
            GL11.glDisable(2896);
        }
        GL11.glEnable(32823);
        GL11.glPolygonOffset(0.0f, -1000000.0f);
        OpenGlHelper.setLightmapTextureCoords(1, 240.0f, 240.0f);
        GL11.glDepthMask(false);
        OGLUtils.color(occludedColor);
    }

    public static void preVisibleRender(int visibleColor, boolean visibleFlat, boolean occludedFlat) {
        GL11.glDepthMask(true);
        if (occludedFlat && !visibleFlat) {
            GL11.glEnable(2896);
        } else if (!occludedFlat && visibleFlat) {
            GL11.glDisable(2896);
        }
        OGLUtils.color(visibleColor);
        GL11.glDisable(32823);
    }

    public static void postRender(boolean visibleFlat) {
        if (visibleFlat) {
            GL11.glEnable(2896);
        }
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    public boolean isChams() {
        return this.mode.getValue() == Mode.CSGO;
    }

    public static enum Mode {
        BOX,
        CSGO;

    }
}

