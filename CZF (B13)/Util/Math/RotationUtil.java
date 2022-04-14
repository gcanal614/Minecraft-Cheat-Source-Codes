/*
 * Decompiled with CFR 0_132.
 */
package gq.vapu.czfclient.Util.Math;

import gq.vapu.czfclient.Util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.Random;

public class RotationUtil {
    static Minecraft mc = Helper.mc;

    public static float[] rotate(EntityLivingBase ent) {

        final double x = ent.posX - mc.thePlayer.posX;
        double y = ent.posY - mc.thePlayer.posY;
        final double z = ent.posZ - mc.thePlayer.posZ;
        y /= mc.thePlayer.getDistanceToEntity(ent);
        final float yaw = (float) (-(Math.atan2(x, z) * 57.29577951308232));
        final float pitch = (float) (-(Math.asin(y) * 57.29577951308232));
        return new float[]{yaw, pitch};
    }

    public static boolean canEntityBeSeen(Entity e) {
        Vec3 vec1 = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);

        AxisAlignedBB box = e.getEntityBoundingBox();
        Vec3 vec2 = new Vec3(e.posX, e.posY + (e.getEyeHeight() / 1.32F), e.posZ);
        double minx = e.posX - 0.25;
        double maxx = e.posX + 0.25;
        double miny = e.posY;
        double maxy = e.posY + Math.abs(e.posY - box.maxY);
        double minz = e.posZ - 0.25;
        double maxz = e.posZ + 0.25;
        boolean see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(maxx, miny, minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(minx, miny, minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;

        if (see)
            return true;
        vec2 = new Vec3(minx, miny, maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(maxx, miny, maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;

        vec2 = new Vec3(maxx, maxy, minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;

        if (see)
            return true;
        vec2 = new Vec3(minx, maxy, minz);

        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(minx, maxy, maxz - 0.1);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(maxx, maxy, maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        return see;
    }

    public static float ���᳡��绰˵����ճ���˵����ǵ�������̱���β���ľ������ֱ���������˸���跹�����滹��ʮ�߸��еĶ�˵����Ұ����Ͻ�ȥ���װ�() {
        return Helper.mc.thePlayer.rotationPitch;
    }

    public static void ���᳡��绰˵����ճ���˵����ǵ�������̱���β���ľ������ֱ���������˸���跹�����滹��ʮ�߸��еĶ�˵����Ұ����Ͻ�ȥ���װ�(float ���᳡��绰˵����ճ���˵����ǵ�������̱���β���ľ������ֱ���������˸���跹�����滹��ʮ�߸��еĶ�˵����Ұ����Ͻ�ȥ���װ�) {
        Helper.mc.thePlayer.rotationPitch = ���᳡��绰˵����ճ���˵����ǵ�������̱���β���ľ������ֱ���������˸���跹�����滹��ʮ�߸��еĶ�˵����Ұ����Ͻ�ȥ���װ�;
    }

    public static float Ұ���Ұ�����״��������������ֹ��ٵı������һ����Ͱ�һ����Ե�shi���˳���() {
        return Helper.mc.thePlayer.rotationYaw;
    }

    public static void Ұ���Ұ�����״��������������ֹ��ٵı������һ����Ͱ�һ����Ե�shi���˳���(float Ұ���Ұ�����״��������������ֹ��ٵı������һ����Ͱ�һ����Ե�shi���˳���) {
        Helper.mc.thePlayer.rotationYaw = Ұ���Ұ�����״��������������ֹ��ٵı������һ����Ͱ�һ����Ե�shi���˳���;
    }

    public static float[] ɵ��ף��ȫ�ұ���(EntityLivingBase ɵ��ף��ȫ�ұ���) {
        double x = ɵ��ף��ȫ�ұ���.posX + (ɵ��ף��ȫ�ұ���.posX - ɵ��ף��ȫ�ұ���.lastTickPosX);
        double z = ɵ��ף��ȫ�ұ���.posZ + (ɵ��ף��ȫ�ұ���.posZ - ɵ��ף��ȫ�ұ���.lastTickPosZ);
        double y = ɵ��ף��ȫ�ұ���.posY + ɵ��ף��ȫ�ұ���.getEyeHeight() / 2.0F;
        return Ҫô�ܺ��Լ�Ҫô�غ������(x, z, y);
    }

    public static float[] Ҫô�ܺ��Լ�Ҫô�غ������(double ��ȫ��������Сȫ��ɵ��������������jb����һ����һ���γ�����ѭ�����Ƶ���һȦ, double ����������΢����ϸ��̫�����Ұѿ�ԭ����ĸ��B���ҿ����ܲ������ϸ��, double �ҵ������н���1��t��������ɱ��Ƶ) {
        double xDiff = ��ȫ��������Сȫ��ɵ��������������jb����һ����һ���γ�����ѭ�����Ƶ���һȦ - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = ����������΢����ϸ��̫�����Ұѿ�ԭ����ĸ��B���ҿ����ܲ������ϸ�� - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = �ҵ������н���1��t��������ɱ��Ƶ - Minecraft.getMinecraft().thePlayer.posY - 1.2;

        double ��������绰��һ��Ҫ�ȹҲ�Ȼ�����Ҫ���� = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float ������ʮ��������յľ�������Ʈ��Ĺǻ� = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
        float ��ʱ����ĸ�������ʱ�������ǽ�Ͽ۶��۲����� = (float) -(Math.atan2(yDiff, ��������绰��һ��Ҫ�ȹҲ�Ȼ�����Ҫ����) * 180.0D / 3.141592653589793D);
        return new float[]{������ʮ��������յľ�������Ʈ��Ĺǻ�, ��ʱ����ĸ�������ʱ�������ǽ�Ͽ۶��۲�����};
    }

    public static boolean ���������ȥ�����������֪ͨ��(Entity ���������ȥ�����������֪ͨ��) {
        Minecraft mc = Minecraft.getMinecraft();
        Vec3 vec1 = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);

        AxisAlignedBB box = ���������ȥ�����������֪ͨ��.getEntityBoundingBox();
        Vec3 vec2 = new Vec3(���������ȥ�����������֪ͨ��.posX, ���������ȥ�����������֪ͨ��.posY + (���������ȥ�����������֪ͨ��.getEyeHeight() / 1.32F), ���������ȥ�����������֪ͨ��.posZ);
        double minx = ���������ȥ�����������֪ͨ��.posX - 0.25;
        double maxx = ���������ȥ�����������֪ͨ��.posX + 0.25;
        double miny = ���������ȥ�����������֪ͨ��.posY;
        double maxy = ���������ȥ�����������֪ͨ��.posY + Math.abs(���������ȥ�����������֪ͨ��.posY - box.maxY);
        double minz = ���������ȥ�����������֪ͨ��.posZ - 0.25;
        double maxz = ���������ȥ�����������֪ͨ��.posZ + 0.25;
        boolean see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(maxx, miny, minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(minx, miny, minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;

        if (see)
            return true;
        vec2 = new Vec3(minx, miny, maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(maxx, miny, maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;

        vec2 = new Vec3(maxx, maxy, minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;

        if (see)
            return true;
        vec2 = new Vec3(minx, maxy, minz);

        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(minx, maxy, maxz - 0.1);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        if (see)
            return true;
        vec2 = new Vec3(maxx, maxy, maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null;
        return see;
    }

    public static float[] Ҫ��Ҫ���������ӿ۳�����������˾��(Entity target, float p_706252, float p_706253, boolean miss) {
        double var6;
        double var4 = target.posX - Helper.mc.thePlayer.posX;
        double var8 = target.posZ - Helper.mc.thePlayer.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var10 = (EntityLivingBase) target;
            var6 = var10.posY + (double) var10.getEyeHeight()
                    - (Helper.mc.thePlayer.posY + (double) Helper.mc.thePlayer.getEyeHeight());
        } else {
            var6 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0
                    - (Helper.mc.thePlayer.posY + (double) Helper.mc.thePlayer.getEyeHeight());
        }
        Random rnd = new Random();
        double var14 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float) (Math.atan2(var8, var4) * 180.0 / 3.141592653589793) - 90.0f;
        float var13 = (float) (-Math.atan2(var6 - (target instanceof EntityPlayer ? 0.25 : 0.0), var14) * 180.0
                / 3.141592653589793);
        float pitch = RotationUtil.����С���͹���Ŀ�����Ŀ����ֻ���Ļ��ͣ��Skid��Ҫ�ǲ������������趼�ܱ���������(Helper.mc.thePlayer.rotationPitch, var13, p_706253);
        float yaw = RotationUtil.����С���͹���Ŀ�����Ŀ����ֻ���Ļ��ͣ��Skid��Ҫ�ǲ������������趼�ܱ���������(Helper.mc.thePlayer.rotationYaw, var12, p_706252);
        return new float[]{yaw, pitch};
    }

    public static float ����С���͹���Ŀ�����Ŀ����ֻ���Ļ��ͣ��Skid��Ҫ�ǲ������������趼�ܱ���������(float p_706631, float p_706632, float p_706633) {
        float var4 = MathHelper.wrapAngleTo180_float(p_706632 - p_706631);
        if (var4 > p_706633) {
            var4 = p_706633;
        }
        if (var4 < -p_706633) {
            var4 = -p_706633;
        }
        return p_706631 + var4;
    }

    public static float ����û�м����������������������ú�����������û����(float angle1, float angle2) {
        float angle3 = Math.abs(angle1 - angle2) % 360.0f;
        if (angle3 > 180.0f) {
            angle3 = 0.0f;
        }
        return angle3;
    }

    public static float[] �ý���������������һ��(BlockPos �ý���������������һ��) {
        return RotationUtil.��������һ��Ҫ�����Ҹ��ҷ������Һ�ȥ��ϲ��(Helper.mc.thePlayer.getPositionVector().addVector(0.0, Helper.mc.thePlayer.getEyeHeight(), 1.0), new Vec3(�ý���������������һ��.getX(), �ý���������������һ��.getY(), �ý���������������һ��.getZ()));
    }

    public static float[] �ٹ�����Ĺ��ۼƿ��Ƶ���36Ȧ(Vec3 �ٹ�����Ĺ��ۼƿ��Ƶ���36Ȧ) {
        return RotationUtil.��������һ��Ҫ�����Ҹ��ҷ������Һ�ȥ��ϲ��(
                Helper.mc.thePlayer.getPositionVector().addVector(0.5, Helper.mc.thePlayer.getEyeHeight(), 0.5),
                �ٹ�����Ĺ��ۼƿ��Ƶ���36Ȧ);
    }

    public static float[] ��������һ��Ҫ�����Ҹ��ҷ������Һ�ȥ��ϲ��(Vec3 origin, Vec3 position) {
        Vec3 difference = position.subtract(origin);
        double distance = difference.flat().lengthVector();
        float yaw = (float) Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0f;
        float pitch = (float) (-Math.toDegrees(Math.atan2(difference.yCoord, distance)));
        return new float[]{yaw, pitch};
    }

    public static float ����av������������ȥ����(float ����av������������ȥ����, double posX, double posZ) {
        double deltaX = posX - Minecraft.getMinecraft().thePlayer.posX;
        double deltaZ = posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double yawToEntity = 0;
        if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
            if (deltaX != 0)
                yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
            if (deltaX != 0)
                yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else {
            if (deltaZ != 0)
                yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }

        return MathHelper.wrapAngleTo180_float(-(����av������������ȥ���� - (float) yawToEntity));
    }
}
