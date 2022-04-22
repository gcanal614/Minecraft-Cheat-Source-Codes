package me.injusttice.neutron.utils.player;

import net.minecraft.util.AxisAlignedBB;
import java.util.List;
import net.minecraft.util.BlockPos;
import optifine.Reflector;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.client.Minecraft;

public class RayCastUtil {

    private static Minecraft mc;
    
    public static Vec3 getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
        float f2 = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
        float f3 = -MathHelper.cos(-pitch * 0.017453292f);
        float f4 = MathHelper.sin(-pitch * 0.017453292f);
        return new Vec3(f2 * f3, f4, f * f3);
    }
    
    public static MovingObjectPosition rayTrace(Entity view, double blockReachDistance, float partialTick, float yaw, float pitch) {
        Vec3 vec3 = view.getPositionEyes(1.0f);
        Vec3 vec4 = getVectorForRotation(pitch, yaw);
        Vec3 vec5 = vec3.addVector(vec4.xCoord * blockReachDistance, vec4.yCoord * blockReachDistance, vec4.zCoord * blockReachDistance);
        return view.worldObj.rayTraceBlocks(vec3, vec5, false, false, true);
    }
    
    public static MovingObjectPosition getMouseOver(Entity entity, float yaw, float pitch, double range) {
        if (entity != null && RayCastUtil.mc.theWorld != null) {
            Entity pointedEntity = null;
            RayCastUtil.mc.pointedEntity = null;
            MovingObjectPosition objectMouseOver = rayTrace(entity, range, 1.0f, yaw, pitch);
            double d1 = range;
            Vec3 vec3 = entity.getPositionEyes(1.0f);
            boolean flag = false;
            boolean flag2 = true;
            if (objectMouseOver != null && objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                d1 = objectMouseOver.hitVec.distanceTo(vec3);
            }
            Vec3 vec4 = getVectorForRotation(pitch, yaw);
            Vec3 vec5 = vec3.addVector(vec4.xCoord * range, vec4.yCoord * range, vec4.zCoord * range);
            Vec3 vec6 = null;
            float f = 1.0f;
            List list = RayCastUtil.mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec4.xCoord * range, vec4.yCoord * range, vec4.zCoord * range).expand(f, f, f), (Predicate<? super Entity>)Predicates.and((Predicate)EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
            double d2 = d1;
            for (int i = 0; i < list.size(); ++i) {
                Entity entity2 = (Entity) list.get(i);
                float f2 = entity2.getCollisionBorderSize();
                AxisAlignedBB axisalignedbb = entity2.getEntityBoundingBox().expand(f2, f2, f2);
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec5);
                if (axisalignedbb.isVecInside(vec3)) {
                    if (d2 >= 0.0) {
                        pointedEntity = entity2;
                        vec6 = ((movingobjectposition == null) ? vec3 : movingobjectposition.hitVec);
                        d2 = 0.0;
                    }
                }
                else if (movingobjectposition != null) {
                    double d3 = vec3.distanceTo(movingobjectposition.hitVec);
                    if (d3 < d2 || d2 == 0.0) {
                        boolean flag3 = false;
                        if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                            flag3 = Reflector.callBoolean(entity2, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                        }
                        if (entity2 == entity.ridingEntity && !flag3) {
                            if (d2 == 0.0) {
                                pointedEntity = entity2;
                                vec6 = movingobjectposition.hitVec;
                            }
                        }
                        else {
                            pointedEntity = entity2;
                            vec6 = movingobjectposition.hitVec;
                            d2 = d3;
                        }
                    }
                }
            }
            if (pointedEntity != null && flag && vec3.distanceTo(vec6) > range) {
                objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec6, null, new BlockPos(vec6));
            }
            if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
                objectMouseOver = new MovingObjectPosition(pointedEntity, vec6);
            }
            return objectMouseOver;
        }
        return null;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
