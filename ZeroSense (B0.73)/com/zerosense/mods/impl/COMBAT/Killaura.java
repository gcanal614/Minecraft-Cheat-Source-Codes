package com.zerosense.mods.impl.COMBAT;

import com.zerosense.Events.Event;
import com.zerosense.Events.impl.EventMotion;
import com.zerosense.Events.impl.EventUpdate;
import com.zerosense.Settings.BooleanSetting;
import com.zerosense.Settings.ModeSetting;
import com.zerosense.Settings.NumberSetting;
import com.zerosense.Utils.RenderUtils;
import com.zerosense.Utils.RotationUtils;
import com.zerosense.Utils.Timer;
import com.zerosense.mods.Module;
import me.tojatta.api.utilities.angle.Angle;
import me.tojatta.api.utilities.angle.AngleUtility;
import me.tojatta.api.utilities.vector.impl.Vector3;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import org.apache.commons.lang3.RandomUtils;


import javax.vecmath.Vector3d;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomUtils;

import static com.zerosense.Utils.MathUtils.randomNumber;

public class Killaura extends Module {

    public static EntityLivingBase target;

    public static ModeSetting rota = new ModeSetting("Mode", "Rotations", "Basic", "None", "Test","Smooth Dev","Dev", "Smooth2");
    public static BooleanSetting onlyPlayers = new BooleanSetting("Only players", false);
    public static BooleanSetting nonsilent = new BooleanSetting("Lock view", false);
    public static ModeSetting autoblockmode = new ModeSetting("AutoBlock Mode", "Vanilla"); //i will give you more modes later <3 ty <3
    public static BooleanSetting autoBlock = new BooleanSetting("AutoBlock", false);

    //public static BooleanSetting headrotatorio = new BooleanSetting("Head rotations", false);


    public NumberSetting reach = new NumberSetting("Reach", 1.0D, 7.0D, 0.1D, 3.5D);
    public NumberSetting aps = new NumberSetting("Aps", 1.0D, 20.0D, 0.1D, 13.0D);
   // public NumberSetting turn = new NumberSetting("Turn Head", 15.0, 120.0, 5.0, 1.0);

    Timer timer = new Timer();
    private float yaw;
    private float pitch;
    float[] rot = new float[]{0, 0};

    private AngleUtility angleUtility = new AngleUtility(110, 120, 30, 40);// This is the angle utility
    AxisAlignedBB axisAlignedBB;
    float shouldAddYaw;
    private static Minecraft mc = Minecraft.getMinecraft();
    float[] lastRotation = new float[] { 0f, 0f };
    private float rotationYawHead;


    private float[] lastRotations;
    public boolean Candefblock;
    public static float sYaw, sPitch, aacB;
    public Killaura() {
        super("Aura", Keyboard.KEY_R, Category.COMBAT);
        this.addSettings(onlyPlayers, nonsilent, rota, reach, aps,autoBlock, autoblockmode );// end :) ty <3 ily ily too type me on discord if you need help ok
    }


    @Override
    public void onEnable() {

        Candefblock = true;
        if(rota.is("Smooth2")){
            shouldAddYaw = 0;
            axisAlignedBB = null;
            if (mc.thePlayer != null) {
                lastRotation[0] = mc.thePlayer.rotationYaw;
                lastRotations = new float[] { mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch };
            }
            index = 0;
        }
    }

    @Override
    public void onDisable() {
        Candefblock = false;
        if(this.canBlock())
            unblock();

    }


    @Override
    public void onEvent(Event e) {
        if (e instanceof EventMotion) {

            rotationYawHead = mc.thePlayer.rotationYawHead;
            EventMotion event = (EventMotion) e;
            List<EntityLivingBase> list = (List<EntityLivingBase>) mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());


            list = list.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < this.reach.getValue() && entity != mc.thePlayer).collect(Collectors.toList());

            list.sort(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer)));

            if (onlyPlayers.isToggled()) {
                list = list.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());
            }




            if (rota.is("DEV")) {

            }
            if(rota.is("Smooth2")){

            }

            if (!list.isEmpty()) {
                target = list.get(0);
                if (nonsilent.isToggled()) {
                    mc.thePlayer.rotationYaw = getRotations(target)[0];
                    mc.thePlayer.rotationPitch = getRotations(target)[1];

                } else if (!nonsilent.isToggled()) {
                    e.setYaw(getRotations(target)[0]);
                    e.setPitch(getRotations(target)[1]);

                }
                if(autoBlock.isToggled()){
                    if(canBlock())
                        block();

                }

                //if (headrotatorio.isToggled()) {

                //    float[] rotation = getEntityRotations(target, lastRotations, false,
              //              (int)  turn.getValue());
           //         lastRotations = new float[] { rotation[0], rotation[1] };

        //            event.setYaw(rotation[0]);
         //           mc.thePlayer.renderYawOffset = event.getYaw();
              //      mc.thePlayer.rotationYawHead = event.getYaw();


            //        event.setPitch(rotation[1]);
                //    mc.thePlayer.renderArmPitch = event.getPitch();
           //         rotationYawHead = event.getYaw();
//
              //  }



                    if (timer.hasTimeElapsed((long) this.aps.getValue() / 1000, true)) {
                        mc.thePlayer.swingItem();
                        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                    }




                if (rota.is("Smooth Dev")) {

                    double randomYaw = 0.05;
                    double randomPitch = 0.05;
                    float targetYaw = RotationUtils.getYawChange(mc.thePlayer.rotationYaw, target.posX + randomNumber(1,-1) * randomYaw, target.posZ + randomNumber(1,-1) * randomYaw);
                    float yawFactor = targetYaw / 1.7F;
                    e.setYaw(mc.thePlayer.rotationYaw + yawFactor);
                    mc.thePlayer.rotationYaw += yawFactor;
                    float targetPitch = RotationUtils.getPitchChange(mc.thePlayer.rotationPitch, target, target.posY + randomNumber(1,-1) * randomPitch);
                    float pitchFactor = targetPitch / 1.7F;
                    e.setPitch(mc.thePlayer.rotationPitch + pitchFactor);
                    mc.thePlayer.rotationPitch += pitchFactor;                }
                if(rota.is("Smooth2")){
                    System.out.println("SMOOTH 2 turn on");


                    rot = RotationUtils.getRotations(target);
                    yaw = rot[0] + (float)RandomUtils.nextInt(1,26) - 13.0f;
                    pitch = rot[1] + (float)RandomUtils.nextInt(1,26) - 13.0f;




                }
            }
        }
    }
    private void block() {
        if(autoblockmode.is("Vanilla")){
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
        }
    }
    private void unblock() {
        if(autoblockmode.is("Vanilla")){
            mc.playerController.onStoppedUsingItem(mc.thePlayer); //that's best code for me
        }
    }

    public float[] getRotations(Entity e) {
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
                deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
                distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
                pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));
        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        } else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }
        return new float[]{
                yaw, pitch
        };
    }
    public float[] getNeededRotations(Vec3 vec) {
        Vec3 playerVector = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
        double y = vec.yCoord - playerVector.yCoord;
        double x = vec.xCoord - playerVector.xCoord;
        double z = vec.zCoord - playerVector.zCoord;
        double dff = Math.sqrt(x * x + z * z);
        float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0F;
        float pitch = (float)(-Math.toDegrees(Math.atan2(y, dff)));
        return new float[]{MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch)};
    }

    public float[] mouseFix(float yaw, float pitch) {
        float k = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        float k1 = k * k * k * 8.0F;
        yaw -= yaw % k1;
        pitch -= pitch % k1;
        return new float[]{yaw, pitch};
    }

    public Vec3 getCenter(AxisAlignedBB bb) {
        return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.5D, bb.minY + (bb.maxY - bb.minY) * 0.5D, bb.minZ + (bb.maxZ - bb.minZ) * 0.5D);
    }

    public boolean canBlock(){
        return mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
    }

    public static float[] getEntityRotations(EntityLivingBase target, float[] lastrotation, boolean aac, int smooth) {
        myAngleUtility angleUtility = new myAngleUtility(aac, smooth);
        Vector3d enemyCoords = new Vector3d(target.posX, target.posY + target.getEyeHeight(), target.posZ);
        Vector3d myCoords = new Vector3d(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                mc.thePlayer.posZ);
        myAngle dstAngle = angleUtility.calculateAngle(enemyCoords, myCoords);
        myAngle srcAngle = new myAngle(lastrotation[0], lastrotation[1]);
        myAngle smoothedAngle = angleUtility.smoothAngle(dstAngle, srcAngle);
        float yaw = smoothedAngle.getYaw();
        float pitch = smoothedAngle.getPitch();
        float yaw2 = MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw);
        yaw = mc.thePlayer.rotationYaw + yaw2;
        return new float[] { yaw, pitch };
    }





}

class myAngle {
    private static Minecraft mc = Minecraft.getMinecraft();
    private float yaw;
    private float pitch;

    public myAngle(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public myAngle() {
        this(0.0f, 0.0f);
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public myAngle constrantAngle() {
        this.setYaw(this.getYaw() % 360F);
        this.setPitch(this.getPitch() % 360F);

        while (this.getYaw() <= -180F) {
            this.setYaw(this.getYaw() + 360F);
        }

        while (this.getPitch() <= -180F) {
            this.setPitch(this.getPitch() + 360F);
        }

        while (this.getYaw() > 180F) {
            this.setYaw(this.getYaw() - 360F);
        }

        while (this.getPitch() > 180F) {
            this.setPitch(this.getPitch() - 360F);
        }

        return this;
    }
}
class myAngleUtility {
    private static Minecraft mc = Minecraft.getMinecraft();
    private boolean aac;
    private float smooth;
    private Random random;

    public myAngleUtility(boolean aac, float smooth) {
        this.aac = aac;
        this.smooth = smooth;
        this.random = new Random();
    }
    public myAngle calculateAngle(Vector3d destination, Vector3d source) {
        myAngle angles = new myAngle();
        destination.x += (aac ? randomFloat(-0.75F, 0.75F) : 0.0F) - source.x;
        destination.y += (aac ? randomFloat(-0.25F, 0.5F) : 0.0F) - source.y;
        destination.z += (aac ? randomFloat(-0.75F, 0.75F) : 0.0F) - source.z;
        double hypotenuse = Math.hypot(destination.x, destination.z);
        angles.setYaw((float) (Math.atan2(destination.z, destination.x) * 57.29577951308232D) - 90.0F);
        angles.setPitch(-(float) ((Math.atan2(destination.y, hypotenuse) * 57.29577951308232D)));
        return angles.constrantAngle();
    }

    public myAngle smoothAngle(myAngle destination, myAngle source) {
        myAngle angles = (new myAngle(source.getYaw() - destination.getYaw(),
                source.getPitch() - destination.getPitch())).constrantAngle();
        angles.setYaw(source.getYaw() - angles.getYaw() / 100.0F * smooth);
        angles.setPitch(source.getPitch() - angles.getPitch() / 100.0F * smooth);
        return angles.constrantAngle();
    }

    public float randomFloat(float min, float max) {
        return min + (this.random.nextFloat() * (max - min));
    }


    public static double isInFov(float var0, float var1, double var2, double var4, double var6) {
        Vec3 var8 = new Vec3(var0, var1, 0.0D);
        float[] var9 = getAngleBetweenVecs(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ),
                new Vec3(var2, var4, var6));
        double var10 = MathHelper.wrapAngleTo180_double(var8.xCoord - (double) var9[0]);
        return Math.abs(var10) * 2.0D;
    }

    public static float[] getAngleBetweenVecs(Vec3 var0, Vec3 var1) {
        double var2 = var1.xCoord - var0.xCoord;
        double var4 = var1.yCoord - var0.yCoord;
        double var6 = var1.zCoord - var0.zCoord;
        double var8 = Math.sqrt(var2 * var2 + var6 * var6);
        float var10 = (float) (Math.atan2(var6, var2) * 180.0D / 3.141592653589793D) - 90.0F;
        float var11 = (float) (-(Math.atan2(var4, var8) * 180.0D / 3.141592653589793D));
        return new float[] { var10, var11 };
    }

    public static float[] getAnglesIgnoringNull(Entity var0, float var1, float var2) {
        float[] var3 = getAngles(var0);
        if (var3 == null) {
            return new float[] { 0.0F, 0.0F };
        } else {
            float var4 = var3[0];
            float var5 = var3[1];
            return new float[] { var1 + MathHelper.wrapAngleTo180_float(var4 - var1),
                    var2 + MathHelper.wrapAngleTo180_float(var5 - var2) + 5.0F };
        }
    }
    public static float[] getAngles(Entity entity) {
        if (entity == null) {
            return null;
        } else {
            double var1 = entity.posX - mc.thePlayer.posX;
            double var3 = entity.posZ - mc.thePlayer.posZ;
            double var5;
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase var7 = (EntityLivingBase) entity;
                var5 = var7.posY + ((double) var7.getEyeHeight() - 0.4D)
                        - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
            } else {
                var5 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0D
                        - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
            }

            double var11 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
            float var9 = (float) (Math.atan2(var3, var1) * 180.0D / 3.141592653589793D) - 90.0F;
            float var10 = (float) (-(Math.atan2(var5, var11) * 180.0D / 3.141592653589793D));
            return new float[] { var9, var10 };
        }
    }




}


