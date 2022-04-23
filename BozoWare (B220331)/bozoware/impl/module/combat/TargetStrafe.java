// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.combat;

import bozoware.impl.module.movement.Speed;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import bozoware.impl.module.movement.Flight;
import bozoware.base.BozoWare;
import bozoware.base.util.player.RotationUtils;
import net.minecraft.client.renderer.GlStateManager;
import java.util.Objects;
import bozoware.base.util.visual.GLUtil;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import bozoware.base.util.player.MovementUtil;
import java.awt.Color;
import bozoware.impl.property.ColorProperty;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.event.visual.EventRender3D;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.PlayerMoveEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Target Strafe", moduleCategory = ModuleCategory.COMBAT)
public class TargetStrafe extends Module
{
    @EventListener
    EventConsumer<PlayerMoveEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<EventRender3D> onEventRender3D;
    private static int direction;
    public final ValueProperty<Double> radius;
    public final BooleanProperty render;
    public final ColorProperty renderColor;
    public final BooleanProperty space;
    public final BooleanProperty onSpeed;
    private Aura aura;
    
    public TargetStrafe() {
        this.radius = new ValueProperty<Double>("Radius", 2.0, 0.1, 4.0, this);
        this.render = new BooleanProperty("Render Circle", true, this);
        this.renderColor = new ColorProperty("Circle Color", new Color(-65536), this);
        this.space = new BooleanProperty("On Space Held", true, this);
        this.onSpeed = new BooleanProperty("Speed", true, this);
        this.renderColor.setHidden(false);
        this.render.onValueChange = (() -> {
            if (this.render.getPropertyValue()) {
                this.renderColor.setHidden(false);
            }
            else {
                this.renderColor.setHidden(true);
            }
            return;
        });
        this.onUpdatePositionEvent = (e -> {
            if (TargetStrafe.mc.thePlayer.isCollidedHorizontally) {
                this.switchDirection();
            }
            if (TargetStrafe.mc.gameSettings.keyBindLeft.isPressed()) {
                TargetStrafe.direction = 1;
            }
            if (TargetStrafe.mc.gameSettings.keyBindRight.isPressed()) {
                TargetStrafe.direction = -1;
            }
            this.doStrafeAtSpeed(e, MovementUtil.getMoveSpeed());
            return;
        });
        this.onEventRender3D = (e -> {
            if (this.canStrafe() && this.render.getPropertyValue()) {
                this.drawCircle(Aura.target, e.partialTicks, this.radius.getPropertyValue(), this.renderColor.getPropertyValue());
            }
        });
    }
    
    private void drawCircle(final Entity entity, final float partialTicks, final double rad, final Color color) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GLUtil.startSmooth();
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(3);
        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - TargetStrafe.mc.getRenderManager().viewerPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - TargetStrafe.mc.getRenderManager().viewerPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - TargetStrafe.mc.getRenderManager().viewerPosZ;
        final float r = 0.003921569f * Color.WHITE.getRed();
        final float g = 0.003921569f * Color.WHITE.getGreen();
        final float b = 0.003921569f * Color.WHITE.getBlue();
        for (int i = 0; i <= 90; ++i) {
            GlStateManager.color(Objects.requireNonNull(color).getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
            GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586 / 45.0), y, z + rad * Math.sin(i * 6.283185307179586 / 45.0));
        }
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GLUtil.endSmooth();
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
    
    private void switchDirection() {
        if (TargetStrafe.direction == 1) {
            TargetStrafe.direction = -1;
        }
        else {
            TargetStrafe.direction = 1;
        }
    }
    
    public final boolean doStrafeAtSpeed(final PlayerMoveEvent event, final double moveSpeed) {
        final boolean strafe = this.canStrafe();
        if (strafe) {
            final float[] rotations = RotationUtils.getNeededRotations(Aura.target);
            if (BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Flight.class).isModuleToggled()) {
                TargetStrafe.mc.gameSettings.keyBindJump.pressed = false;
            }
            if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(Aura.target) <= this.radius.getPropertyValue()) {
                MovementUtil.setSpeed(event, moveSpeed, rotations[0], TargetStrafe.direction, 0.0);
            }
            else {
                MovementUtil.setSpeed(event, moveSpeed, rotations[0], TargetStrafe.direction, 1.0);
            }
        }
        return strafe;
    }
    
    public static void setSpeed(final PlayerMoveEvent updatePositionEvent, final double moveSpeed) {
        setSpeed(updatePositionEvent, moveSpeed, TargetStrafe.mc.thePlayer.rotationYaw, TargetStrafe.mc.thePlayer.movementInput.moveStrafe, TargetStrafe.mc.thePlayer.movementInput.moveForward);
    }
    
    public static void setSpeed(final PlayerMoveEvent updatePositionEvent, final double moveSpeed, final float pseudoYaw, final double pseudoStrafe, final double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (pseudoForward != 0.0) {
            if (pseudoStrafe > 0.0) {
                yaw = pseudoYaw + ((pseudoForward > 0.0) ? -45 : 45);
            }
            else if (pseudoStrafe < 0.0) {
                yaw = pseudoYaw + ((pseudoForward > 0.0) ? 45 : -45);
            }
            strafe = 0.0;
            if (pseudoForward > 0.0) {
                forward = 1.0;
            }
            else if (pseudoForward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        }
        else if (strafe < 0.0) {
            strafe = -1.0;
        }
        final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        updatePositionEvent.setMotionX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        updatePositionEvent.setMotionZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }
    
    public boolean canStrafe() {
        if (this.space.getPropertyValue() && !Keyboard.isKeyDown(TargetStrafe.mc.gameSettings.keyBindJump.getKeyCode())) {
            return false;
        }
        if (this.onSpeed.getPropertyValue() && !BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Speed.class).isModuleToggled()) {
            return false;
        }
        if (BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Aura.class).isModuleToggled()) {
            final Aura aura = this.aura;
            if (Aura.target != null) {
                return true;
            }
        }
        return false;
    }
    
    static {
        TargetStrafe.direction = -1;
    }
}
