// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.AxisAlignedBB;
import java.util.Objects;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import bozoware.base.util.visual.RenderUtil;
import org.lwjgl.opengl.GL11;
import bozoware.base.BozoWare;
import java.util.Iterator;
import bozoware.base.util.player.PlayerUtils;
import bozoware.impl.module.combat.AntiBot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import java.awt.Color;
import java.util.HashMap;
import net.minecraft.client.renderer.GLAllocation;
import org.lwjgl.BufferUtils;
import bozoware.impl.property.ColorProperty;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.EnumProperty;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Map;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import bozoware.impl.event.visual.Render2DEvent;
import bozoware.impl.event.visual.EventRender3D;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import net.minecraft.client.renderer.culling.Frustum;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "ESP", moduleCategory = ModuleCategory.VISUAL)
public class ESP extends Module
{
    private static final Frustum frustrum;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<EventRender3D> onRender3D;
    @EventListener
    EventConsumer<Render2DEvent> onRender2D;
    private final FloatBuffer windowPosition;
    private final IntBuffer viewport;
    private final FloatBuffer modelMatrix;
    private final FloatBuffer projectionMatrix;
    private final Map<EntityPlayer, float[]> entityPosMap;
    public final EnumProperty<ESPModes> mode;
    private final BooleanProperty renderonSelf;
    private final ColorProperty color;
    String location;
    
    public ESP() {
        this.windowPosition = BufferUtils.createFloatBuffer(4);
        this.viewport = GLAllocation.createDirectIntBuffer(16);
        this.modelMatrix = GLAllocation.createDirectFloatBuffer(16);
        this.projectionMatrix = GLAllocation.createDirectFloatBuffer(16);
        this.entityPosMap = new HashMap<EntityPlayer, float[]>();
        this.mode = new EnumProperty<ESPModes>("Mode", ESPModes.Shader, this);
        this.renderonSelf = new BooleanProperty("Render on Self", true, this);
        this.color = new ColorProperty("Color", new Color(-65536), this);
        this.onUpdatePositionEvent = (event -> {});
        this.onRender2D = (event -> {});
        final Iterator<Entity> iterator;
        Entity e;
        final Iterator<Entity> iterator2;
        Entity e2;
        this.onRender3D = (event -> {
            switch (this.mode.getPropertyValue()) {
                case Box: {
                    ESP.mc.theWorld.loadedEntityList.iterator();
                    while (iterator.hasNext()) {
                        e = iterator.next();
                        if (e != null && e instanceof EntityLivingBase && e instanceof EntityPlayer && !AntiBot.botList.contains(e.getEntityId()) && !e.isInvisible() && e != ESP.mc.thePlayer) {
                            PlayerUtils.draw2DESP(this.color.getColorRGB(), e);
                            PlayerUtils.get2DVector(e.getEntityBoundingBox());
                        }
                    }
                    break;
                }
                case Tracer: {
                    ESP.mc.theWorld.loadedEntityList.iterator();
                    while (iterator2.hasNext()) {
                        e2 = iterator2.next();
                        if (e2 != null && e2 instanceof EntityLivingBase) {
                            if (e2 instanceof EntityPlayer && !AntiBot.botList.contains(e2.getEntityId())) {
                                if (e2 != ESP.mc.thePlayer) {
                                    this.line(e2, false, 0);
                                }
                                else {
                                    continue;
                                }
                            }
                            else if (e2 instanceof EntityPlayer && AntiBot.botList.contains(e2.getEntityId()) && e2 != ESP.mc.thePlayer) {
                                this.line(e2, true, -65536);
                            }
                            else {
                                continue;
                            }
                        }
                    }
                    break;
                }
            }
        });
    }
    
    public static ESP getInstance() {
        return BozoWare.getInstance().getModuleManager().getModuleByClass.apply(ESP.class);
    }
    
    private void line(final Entity target, final boolean CustomColor, final int Color) {
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        ESP.mc.entityRenderer.orientCamera(ESP.mc.timer.renderPartialTicks);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        double x = RenderUtil.animate(target.posX, target.lastTickPosX, 10.0);
        double y = RenderUtil.animate(target.posY, target.lastTickPosY, 10.0);
        double z = RenderUtil.animate(target.posZ, target.lastTickPosZ, 10.0);
        x -= RenderManager.renderPosX;
        y -= RenderManager.renderPosY;
        z -= RenderManager.renderPosZ;
        if (!CustomColor) {
            RenderUtil.setColorWithAlpha(this.color.getPropertyValue().getRGB(), 255);
        }
        else {
            RenderUtil.setColorWithAlpha(Color, 255);
        }
        GL11.glLineWidth(1.5f);
        GL11.glBegin(3);
        GL11.glVertex3d(0.0, (double)ESP.mc.thePlayer.getEyeHeight(), 0.0);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glColor3d(1.0, 1.0, 1.0);
        GL11.glPopMatrix();
    }
    
    public static void entityESPBox(final Entity entity, final Color color) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ESP.mc.timer.renderPartialTicks;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ESP.mc.timer.renderPartialTicks;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ESP.mc.timer.renderPartialTicks;
        final float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * ESP.mc.timer.renderPartialTicks;
        final double n = x;
        ESP.mc.getRenderManager();
        final double x2 = n - RenderManager.renderPosX;
        final double n2 = y;
        ESP.mc.getRenderManager();
        final double y2 = n2 - RenderManager.renderPosY;
        final double n3 = z;
        ESP.mc.getRenderManager();
        GlStateManager.translate(x2, y2, n3 - RenderManager.renderPosZ);
        GlStateManager.rotate(-yaw, 0.0f, 1.0f, 0.0f);
        final double n4 = x;
        ESP.mc.getRenderManager();
        final double x3 = -(n4 - RenderManager.renderPosX);
        final double n5 = y;
        ESP.mc.getRenderManager();
        final double y3 = -(n5 - RenderManager.renderPosY);
        final double n6 = z;
        ESP.mc.getRenderManager();
        GlStateManager.translate(x3, y3, -(n6 - RenderManager.renderPosZ));
        GL11.glEnable(2848);
        GlStateManager.color(Objects.requireNonNull(color).getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
        final double n7 = x - entity.width / 2.0f - 0.05 - x;
        final double n8 = x;
        ESP.mc.getRenderManager();
        final double x4 = n7 + (n8 - RenderManager.renderPosX);
        final double n9 = y - y;
        final double n10 = y;
        ESP.mc.getRenderManager();
        final double y4 = n9 + (n10 - RenderManager.renderPosY);
        final double n11 = z - entity.width / 2.0f - 0.05 - z;
        final double n12 = z;
        ESP.mc.getRenderManager();
        final double z2 = n11 + (n12 - RenderManager.renderPosZ);
        final double n13 = x + entity.width / 2.0f + 0.05 - x;
        final double n14 = x;
        ESP.mc.getRenderManager();
        final double x5 = n13 + (n14 - RenderManager.renderPosX);
        final double n15 = y + entity.height + 0.1 - y;
        final double n16 = y;
        ESP.mc.getRenderManager();
        final double y5 = n15 + (n16 - RenderManager.renderPosY);
        final double n17 = z + entity.width / 2.0f + 0.05 - z;
        final double n18 = z;
        ESP.mc.getRenderManager();
        RenderGlobal.func_181561_a(new AxisAlignedBB(x4, y4, z2, x5, y5, n17 + (n18 - RenderManager.renderPosZ)));
        final double n19 = x;
        ESP.mc.getRenderManager();
        final double x6 = n19 - RenderManager.renderPosX;
        final double n20 = y;
        ESP.mc.getRenderManager();
        final double y6 = n20 - RenderManager.renderPosY;
        final double n21 = z;
        ESP.mc.getRenderManager();
        GlStateManager.translate(x6, y6, n21 - RenderManager.renderPosZ);
        GlStateManager.rotate(yaw, 0.0f, 1.0f, 0.0f);
        final double n22 = x;
        ESP.mc.getRenderManager();
        final double x7 = -(n22 - RenderManager.renderPosX);
        final double n23 = y;
        ESP.mc.getRenderManager();
        final double y7 = -(n23 - RenderManager.renderPosY);
        final double n24 = z;
        ESP.mc.getRenderManager();
        GlStateManager.translate(x7, y7, -(n24 - RenderManager.renderPosZ));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    static {
        frustrum = new Frustum();
    }
    
    public enum ESPModes
    {
        Shader, 
        Box, 
        Tracer;
    }
}
