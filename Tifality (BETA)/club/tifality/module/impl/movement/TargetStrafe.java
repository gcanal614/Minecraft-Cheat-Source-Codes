/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.movement;

import club.tifality.Tifality;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.MoveEntityEvent;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.manager.event.impl.render.Render3DEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.ModuleManager;
import club.tifality.module.impl.combat.KillAura;
import club.tifality.module.impl.movement.Flight;
import club.tifality.module.impl.movement.Speed;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.property.impl.Representation;
import club.tifality.utils.RotationUtils;
import club.tifality.utils.Wrapper;
import club.tifality.utils.movement.MovementUtils;
import club.tifality.utils.render.OGLUtils;
import club.tifality.utils.render.RenderingUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

@ModuleInfo(label="TargetStrafe", category=ModuleCategory.MOVEMENT)
public final class TargetStrafe
extends Module {
    public final DoubleProperty radiusProperty = new DoubleProperty("Radius", 2.0, 0.1, 4.0, 0.1, Representation.DISTANCE);
    public final Property<Boolean> holdSpaceProperty = new Property<Boolean>("Hold Space", true);
    public final Property<Boolean> behindProperty = new Property<Boolean>("Behind", true);
    public final Property<Boolean> keepRangeValue = new Property<Boolean>("Keep Range", false, () -> this.behindProperty.get() == false);
    public final Property<Boolean> adaptiveSpeedProperty = new Property<Boolean>("Adaptive", true);
    public final EnumProperty<RenderMode> renderModeValue = new EnumProperty<RenderMode>("Mode", RenderMode.NORMAL);
    private final Property<Integer> renderColorValue = new Property<Integer>("Color", new Color(120, 255, 120).getRGB(), () -> this.renderModeValue.get() == RenderMode.NORMAL);
    public final DoubleProperty shapeValue = new DoubleProperty("Shape", 12.0, () -> this.renderModeValue.get() == RenderMode.NORMAL, 0.0, 30.0, 1.0);
    private final DoubleProperty pointsProperty = new DoubleProperty("Points", 12.0, () -> this.renderModeValue.get() == RenderMode.POINT, 1.0, 90.0, 1.0);
    private final Property<Integer> activePointColorProperty = new Property<Integer>("Active Color", -2147418368, () -> this.renderModeValue.get() == RenderMode.POINT);
    private final Property<Integer> dormantPointColorProperty = new Property<Integer>("Dormant Color", 0x20FFFFFF, () -> this.renderModeValue.get() == RenderMode.POINT);
    private final Property<Integer> invalidPointColorProperty = new Property<Integer>("Invalid Color", 0x20FF0000, () -> this.renderModeValue.get() == RenderMode.POINT);
    private final List<Point3D> currentPoints = new ArrayList<Point3D>();
    private Point3D currentPoint;
    private EntityLivingBase currentTarget;
    int consts;
    double lastDist;

    public TargetStrafe() {
        this.setSuffix(() -> this.adaptiveSpeedProperty.get() != false ? "Adaptive" : "Normal");
    }

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (event.isPre()) {
            EntityLivingBase target = KillAura.getInstance().getTarget();
            if (target != null) {
                this.currentTarget = target;
                this.collectPoints(target);
                this.currentPoint = this.findOptimalPoint(target, this.currentPoints);
            } else {
                this.currentTarget = null;
                this.currentPoint = null;
            }
        }
    }

    @Listener
    public void onRender3DEvent(Render3DEvent event) {
        Speed speed = Tifality.INSTANCE.getModuleManager().getModule(Speed.class);
        Flight fly = Tifality.INSTANCE.getModuleManager().getModule(Flight.class);
        KillAura killAura = ModuleManager.getInstance(KillAura.class);
        if (this.renderModeValue.get() == RenderMode.POINT && killAura.getTarget() != null) {
            float partialTicks = event.getPartialTicks();
            for (Point3D point : this.currentPoints) {
                int color = this.currentPoint == point ? this.activePointColorProperty.get() : (point.valid ? this.dormantPointColorProperty.get().intValue() : this.invalidPointColorProperty.get().intValue());
                double x = RenderingUtils.interpolate(point.prevX, point.x, partialTicks);
                double y = RenderingUtils.interpolate(point.prevY, point.y, partialTicks);
                double z = RenderingUtils.interpolate(point.prevZ, point.z, partialTicks);
                double pointSize = 0.03;
                AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + pointSize, y + pointSize, z + pointSize);
                OGLUtils.enableBlending();
                OGLUtils.disableDepth();
                OGLUtils.disableTexture2D();
                OGLUtils.color(color);
                double renderX = RenderManager.renderPosX;
                double renderY = RenderManager.renderPosY;
                double renderZ = RenderManager.renderPosZ;
                GL11.glTranslated(-renderX, -renderY, -renderZ);
                RenderGlobal.func_181561_a(bb, false, true);
                GL11.glTranslated(renderX, renderY, renderZ);
                OGLUtils.disableBlending();
                OGLUtils.enableDepth();
                OGLUtils.enableTexture2D();
            }
        }
        if (this.renderModeValue.get() == RenderMode.NORMAL) {
            double x = killAura.getTarget().lastTickPosX + (killAura.getTarget().posX - killAura.getTarget().lastTickPosX) * (double)TargetStrafe.mc.timer.renderPartialTicks - RenderManager.viewerPosX;
            double y = killAura.getTarget().lastTickPosY + (killAura.getTarget().posY - killAura.getTarget().lastTickPosY) * (double)TargetStrafe.mc.timer.renderPartialTicks - RenderManager.viewerPosY;
            double z = killAura.getTarget().lastTickPosZ + (killAura.getTarget().posZ - killAura.getTarget().lastTickPosZ) * (double)TargetStrafe.mc.timer.renderPartialTicks - RenderManager.viewerPosZ;
            RenderingUtils.TScylinder2(killAura.getTarget(), x, y, z, (Double)this.radiusProperty.get() - 0.00625, 6.0f, ((Double)this.shapeValue.get()).intValue(), new Color(0, 0, 0).getRGB());
            RenderingUtils.TScylinder2(killAura.getTarget(), x, y, z, (Double)this.radiusProperty.get() + 0.00625, 6.0f, ((Double)this.shapeValue.get()).intValue(), new Color(0, 0, 0).getRGB());
            if (speed.isEnabled() || fly.isEnabled()) {
                RenderingUtils.TScylinder1(killAura.getTarget(), x, y, z, (Double)this.radiusProperty.get(), ((Double)this.shapeValue.get()).intValue(), 5.0f, this.renderColorValue.get());
            } else {
                RenderingUtils.TScylinder1(killAura.getTarget(), x, y, z, (Double)this.radiusProperty.get(), ((Double)this.shapeValue.get()).intValue(), 5.0f, new Color(255, 255, 255).getRGB());
            }
        }
    }

    public boolean shouldAdaptSpeed() {
        double zDist;
        if (!this.adaptiveSpeedProperty.get().booleanValue()) {
            return false;
        }
        EntityPlayerSP player = Wrapper.getPlayer();
        double xDist = this.currentPoint.x - player.posX;
        return StrictMath.sqrt(xDist * xDist + (zDist = this.currentPoint.z - player.posZ) * zDist) < 0.2;
    }

    public double getAdaptedSpeed() {
        if (this.currentTarget == null) {
            return 0.0;
        }
        double xDist = this.currentTarget.posX - this.currentTarget.prevPosX;
        double zDist = this.currentTarget.posZ - this.currentTarget.prevPosZ;
        return StrictMath.sqrt(xDist * xDist + zDist * zDist);
    }

    public static TargetStrafe getInstance() {
        return ModuleManager.getInstance(TargetStrafe.class);
    }

    public boolean shouldStrafe() {
        return this.currentPoint != null;
    }

    public void setSpeed(MoveEntityEvent event, double speed) {
        MovementUtils.setSpeed(event, speed, 1.0f, 0.0f, this.getYawToPoint(this.currentPoint));
    }

    private float getYawToPoint(Point3D point) {
        EntityPlayerSP player = Wrapper.getPlayer();
        double xDist = point.x - player.posX;
        double zDist = point.z - player.posZ;
        float rotationYaw = player.rotationYaw;
        float var1 = (float)(StrictMath.atan2(zDist, xDist) * 180.0 / Math.PI) - 90.0f;
        return rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
    }

    private Point3D findOptimalPoint(EntityLivingBase target, List<Point3D> points) {
        if (this.behindProperty.get().booleanValue()) {
            Point3D bestPoint = null;
            float biggestDif = -1.0f;
            for (Point3D point : points) {
                float yawChange;
                if (!point.valid || !((yawChange = Math.abs(this.getYawChangeToPoint(target, point))) > biggestDif)) continue;
                biggestDif = yawChange;
                bestPoint = point;
            }
            return bestPoint;
        }
        return null;
    }

    private float getYawChangeToPoint(EntityLivingBase target, Point3D point) {
        double xDist = point.x - target.posX;
        double zDist = point.z - target.posZ;
        float rotationYaw = target.rotationYaw;
        float var1 = (float)(StrictMath.atan2(zDist, xDist) * 180.0 / Math.PI) - 90.0f;
        return rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
    }

    private void collectPoints(EntityLivingBase entity) {
        int size = ((Double)this.pointsProperty.get()).intValue();
        double radius = (Double)this.radiusProperty.get();
        this.currentPoints.clear();
        double x = entity.posX;
        double y = entity.posY;
        double z = entity.posZ;
        double prevX = entity.prevPosX;
        double prevY = entity.prevPosY;
        double prevZ = entity.prevPosZ;
        for (int i = 0; i < size; ++i) {
            double cos = radius * StrictMath.cos((float)i * ((float)Math.PI * 2) / (float)size);
            double sin = radius * StrictMath.sin((float)i * ((float)Math.PI * 2) / (float)size);
            double pointX = x + cos;
            double pointZ = z + sin;
            Point3D point = new Point3D(pointX, y, pointZ, prevX + cos, prevY, prevZ + sin, this.validatePoint(pointX, pointZ));
            this.currentPoints.add(point);
        }
    }

    private boolean validatePoint(double x, double z) {
        Vec3 pointVec = new Vec3(x, Wrapper.getPlayer().posY, z);
        IBlockState blockState = Wrapper.getWorld().getBlockState(new BlockPos(pointVec));
        boolean canBeSeen = Wrapper.getWorld().rayTraceBlocks(Wrapper.getPlayer().getPositionVector(), pointVec, false, false, false) == null;
        return !this.isOverVoid(x, z) && !blockState.getBlock().canCollideCheck(blockState, false) && canBeSeen;
    }

    private boolean isOverVoid(double x, double z) {
        for (double posY = Wrapper.getPlayer().posY; posY > 0.0; posY -= 1.0) {
            if (Wrapper.getWorld().getBlockState(new BlockPos(x, posY, z)).getBlock() instanceof BlockAir) continue;
            return false;
        }
        return true;
    }

    @Listener
    public void moveStrafe(MoveEntityEvent event) {
        EntityLivingBase target = KillAura.getInstance().getTarget();
        double xDist = event.getX();
        double zDist = event.getZ();
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
        if (this.behindProperty.get().booleanValue()) {
            if (this.keyMode() && target != null && this.shouldStrafe()) {
                if (this.shouldAdaptSpeed()) {
                    this.lastDist = Math.min(this.lastDist, this.getAdaptedSpeed());
                }
                if (this.canStrafe()) {
                    this.setSpeed(event, this.lastDist);
                }
                return;
            }
            if (this.canStrafe()) {
                MovementUtils.setSpeed(event, this.lastDist, TargetStrafe.mc.thePlayer.moveForward, TargetStrafe.mc.thePlayer.moveStrafing, TargetStrafe.mc.thePlayer.rotationYaw);
            }
        } else if (!this.isVoid(0, 0) && this.canStrafe()) {
            float rotations = RotationUtils.getYawToEntity(target, false);
            this.setSpeed(event, this.lastDist, rotations, (Double)this.radiusProperty.get(), 1.0);
        }
    }

    public boolean keyMode() {
        boolean strafe;
        boolean bl = strafe = TargetStrafe.mc.thePlayer.movementInput.moveStrafe != 0.0f || TargetStrafe.mc.thePlayer.movementInput.moveForward != 0.0f;
        if (this.holdSpaceProperty.get().booleanValue()) {
            return TargetStrafe.mc.gameSettings.keyBindJump.isKeyDown() && strafe;
        }
        return strafe;
    }

    public boolean canStrafe() {
        Speed speed = Tifality.INSTANCE.getModuleManager().getModule(Speed.class);
        Flight fly = Tifality.INSTANCE.getModuleManager().getModule(Flight.class);
        return (speed.isEnabled() || fly.isEnabled()) && KillAura.getInstance().getTarget() != null && !TargetStrafe.mc.thePlayer.isSneaking() && this.keyMode();
    }

    public Float canSize() {
        return Float.valueOf(this.adaptiveSpeedProperty.get() != false ? 30.0f : 45.0f / (float)this.getEnemyDistance());
    }

    private float getAlgorithm() {
        return (float)Math.max(this.getEnemyDistance() - (Double)this.radiusProperty.get(), this.getEnemyDistance() - this.getEnemyDistance() - (Double)this.radiusProperty.get() / (Double)this.radiusProperty.get() * 2.0);
    }

    private double getEnemyDistance() {
        return TargetStrafe.mc.thePlayer.getDistance(KillAura.getInstance().getTarget().posX, TargetStrafe.mc.thePlayer.posY, KillAura.getInstance().getTarget().posZ) + (double)0.4f - 0.1;
    }

    public void setSpeed(MoveEntityEvent moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        float yaw = pseudoYaw;
        float strafe2 = 0.0f;
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        if (TargetStrafe.mc.thePlayer.isCollidedHorizontally || this.checkVoid()) {
            this.consts = this.consts < 2 ? ++this.consts : -1;
        }
        switch (this.consts) {
            case 0: {
                this.consts = 1;
                break;
            }
            case 2: {
                this.consts = -1;
            }
        }
        strafe = this.holdSpaceProperty.get() != false ? pseudoStrafe * 0.98 * (double)this.consts : (double)this.consts;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                if (this.keepRangeValue.get().booleanValue() && this.getEnemyDistance() < (Double)this.radiusProperty.get() && !TargetStrafe.mc.thePlayer.isCollidedHorizontally && !this.checkVoid()) {
                    yaw += forward > 0.0 ? -this.canSize().floatValue() : this.canSize().floatValue();
                }
                strafe2 += forward > 0.0 ? -60.0f / this.getAlgorithm() : 60.0f / this.getAlgorithm();
            } else if (strafe < 0.0) {
                if (this.keepRangeValue.get().booleanValue() && this.getEnemyDistance() < (Double)this.radiusProperty.get() && !TargetStrafe.mc.thePlayer.isCollidedHorizontally && !this.checkVoid()) {
                    yaw += forward > 0.0 ? -this.canSize().floatValue() : this.canSize().floatValue();
                }
                strafe2 += forward > 0.0 ? 60.0f / this.getAlgorithm() : -60.0f / this.getAlgorithm();
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            } else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        } else if (strafe < 0.0) {
            strafe = -1.0;
        }
        double mx = Math.cos(Math.toRadians(yaw + 90.0f + strafe2));
        double mz = Math.sin(Math.toRadians(yaw + 90.0f + strafe2));
        moveEvent.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }

    private boolean checkVoid() {
        for (int x = -1; x < 1; ++x) {
            for (int z = -1; z < 1; ++z) {
                if (!this.isVoid(x, z)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean isVoid(int X, int Z) {
        Flight fly = Tifality.INSTANCE.getModuleManager().getModule(Flight.class);
        if (fly.isEnabled()) {
            return false;
        }
        if (TargetStrafe.mc.thePlayer.posY < 0.0) {
            return true;
        }
        for (int off = 0; off < (int)TargetStrafe.mc.thePlayer.posY + 2; off += 2) {
            AxisAlignedBB bb = TargetStrafe.mc.thePlayer.getEntityBoundingBox().offset(X, -off, Z);
            if (TargetStrafe.mc.theWorld.getCollidingBoundingBoxes(TargetStrafe.mc.thePlayer, bb).isEmpty()) {
                continue;
            }
            return false;
        }
        return true;
    }

    public static enum RenderMode {
        NORMAL,
        POINT,
        OFF;

    }

    private static final class Point3D {
        private final double x;
        private final double y;
        private final double z;
        private final double prevX;
        private final double prevY;
        private final double prevZ;
        private final boolean valid;

        public Point3D(double x, double y, double z, double prevX, double prevY, double prevZ, boolean valid) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.prevX = prevX;
            this.prevY = prevY;
            this.prevZ = prevZ;
            this.valid = valid;
        }
    }
}

