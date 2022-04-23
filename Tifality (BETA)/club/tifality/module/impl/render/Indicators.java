/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.render;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.render.Render2DEvent;
import club.tifality.manager.event.impl.render.Render3DEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.utils.render.RenderingUtils;
import com.google.common.collect.Maps;
import java.awt.Color;
import java.util.Map;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

@ModuleInfo(label="Indicators", category=ModuleCategory.RENDER)
public class Indicators
extends Module {
    private final DoubleProperty size = new DoubleProperty("Size", 10.0, 5.0, 25.0, 1.0);
    private final DoubleProperty radius = new DoubleProperty("Radius", 45.0, 10.0, 200.0, 1.0);
    private final Property<Boolean> fade = new Property<Boolean>("Fade", true);
    private int alpha;
    private boolean plus_or_minus;
    private final EntityListener entityListener = new EntityListener();

    @Override
    public void onEnable() {
        this.alpha = 0;
        this.plus_or_minus = false;
    }

    @Listener
    public void onRender3D(Render3DEvent event) {
        this.entityListener.render3d(event);
    }

    @Listener
    public void onRender2D(Render2DEvent event) {
        if (this.fade.get().booleanValue()) {
            float speed = 0.0025f;
            if ((float)this.alpha <= 60.0f || (float)this.alpha >= 255.0f) {
                this.plus_or_minus = !this.plus_or_minus;
            }
            this.alpha = this.plus_or_minus ? (int)((float)this.alpha + speed) : (int)((float)this.alpha - speed);
            this.alpha = (int)Indicators.clamp(this.alpha, 60.0, 255.0);
        } else {
            this.alpha = 255;
        }
        Indicators.mc.theWorld.loadedEntityList.forEach(o -> {
            if (o instanceof EntityPlayer) {
                EntityPlayer entity = (EntityPlayer)o;
                Vec3 pos = this.entityListener.getEntityLowerBounds().get(entity);
                if (pos != null && !this.isOnScreen(pos)) {
                    int x = Display.getWidth() / 2 / (Indicators.mc.gameSettings.guiScale == 0 ? 1 : Indicators.mc.gameSettings.guiScale);
                    int y = Display.getHeight() / 2 / (Indicators.mc.gameSettings.guiScale == 0 ? 1 : Indicators.mc.gameSettings.guiScale);
                    float yaw = this.getRotations(entity) - Indicators.mc.thePlayer.rotationYaw;
                    GL11.glTranslatef(x, y, 0.0f);
                    GL11.glRotatef(yaw, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(-x, -y, 0.0f);
                    RenderingUtils.drawTracerPointer(x, (float)y - ((Double)this.radius.getValue()).floatValue(), ((Double)this.size.getValue()).floatValue(), 2.0f, 1.0f, this.getColor(entity, this.alpha).getRGB());
                    GL11.glTranslatef(x, y, 0.0f);
                    GL11.glRotatef(-yaw, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(-x, -y, 0.0f);
                }
            }
        });
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isOnScreen(Vec3 pos) {
        if (!(pos.xCoord > -1.0)) return false;
        if (!(pos.zCoord < 1.0)) return false;
        double d = pos.xCoord;
        int n = Indicators.mc.gameSettings.guiScale == 0 ? 1 : Indicators.mc.gameSettings.guiScale;
        if (!(d / (double)n >= 0.0)) return false;
        double d2 = pos.xCoord;
        int n2 = Indicators.mc.gameSettings.guiScale == 0 ? 1 : Indicators.mc.gameSettings.guiScale;
        if (!(d2 / (double)n2 <= (double)Display.getWidth())) return false;
        double d3 = pos.yCoord;
        int n3 = Indicators.mc.gameSettings.guiScale == 0 ? 1 : Indicators.mc.gameSettings.guiScale;
        if (!(d3 / (double)n3 >= 0.0)) return false;
        double d4 = pos.yCoord;
        int n4 = Indicators.mc.gameSettings.guiScale == 0 ? 1 : Indicators.mc.gameSettings.guiScale;
        if (!(d4 / (double)n4 <= (double)Display.getHeight())) return false;
        return true;
    }

    private float getRotations(EntityLivingBase ent) {
        double x = ent.posX - Indicators.mc.thePlayer.posX;
        double z = ent.posZ - Indicators.mc.thePlayer.posZ;
        float yaw = (float)(-(Math.atan2(x, z) * 57.29577951308232));
        return yaw;
    }

    private Color getColor(EntityLivingBase player, int alpha) {
        float f = Indicators.mc.thePlayer.getDistanceToEntity(player);
        float f1 = 40.0f;
        float f2 = Math.max(0.0f, Math.min(f, f1) / f1);
        Color clr = new Color(Color.HSBtoRGB(f2 / 3.0f, 1.0f, 1.0f) | 0xFF000000);
        return new Color(clr.getRed(), clr.getGreen(), clr.getBlue(), alpha);
    }

    public static double clamp(double value, double minimum, double maximum) {
        return value > maximum ? maximum : Math.max(value, minimum);
    }

    public static class EntityListener {
        private final Map<Entity, Vec3> entityUpperBounds = Maps.newHashMap();
        private final Map<Entity, Vec3> entityLowerBounds = Maps.newHashMap();

        private void render3d(Render3DEvent event) {
            if (!this.entityUpperBounds.isEmpty()) {
                this.entityUpperBounds.clear();
            }
            if (!this.entityLowerBounds.isEmpty()) {
                this.entityLowerBounds.clear();
            }
            for (Entity e : Module.mc.theWorld.loadedEntityList) {
                Vec3 bound = this.getEntityRenderPosition(e);
                bound.add(new Vec3(0.0, (double)e.height + 0.2, 0.0));
                Vec3 upperBounds2 = RenderingUtils.to2D(bound.xCoord, bound.yCoord, bound.zCoord);
                Vec3 lowerBounds = RenderingUtils.to2D(bound.xCoord, bound.yCoord - 2.0, bound.zCoord);
                if (upperBounds2 == null || lowerBounds == null) continue;
                this.entityUpperBounds.put(e, upperBounds2);
                this.entityLowerBounds.put(e, lowerBounds);
            }
        }

        private Vec3 getEntityRenderPosition(Entity entity) {
            double partial = Module.mc.timer.renderPartialTicks;
            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partial - RenderManager.viewerPosX;
            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partial - RenderManager.viewerPosY;
            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partial - RenderManager.viewerPosZ;
            return new Vec3(x, y, z);
        }

        public Map<Entity, Vec3> getEntityLowerBounds() {
            return this.entityLowerBounds;
        }
    }
}

