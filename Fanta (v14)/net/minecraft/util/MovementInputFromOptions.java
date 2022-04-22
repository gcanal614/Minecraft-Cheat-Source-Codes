/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import de.fanta.Client;
import de.fanta.module.impl.combat.Killaura;
import de.fanta.module.impl.combat.TestAura;
import de.fanta.module.impl.combat.TriggerBot;
import de.fanta.setting.settings.CheckBox;
import de.fanta.utils.Rotations;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

public class MovementInputFromOptions
extends MovementInput {
    private final GameSettings gameSettings;
    private float lastForward;
    private float lastStrafe;
    Minecraft mc = Minecraft.getMinecraft();

    public MovementInputFromOptions(GameSettings gameSettingsIn) {
        this.gameSettings = gameSettingsIn;
    }

    @Override
    public void updatePlayerMoveState() {
        this.moveStrafe = 0.0f;
        this.moveForward = 0.0f;
        if (this.gameSettings.keyBindForward.isKeyDown()) {
            this.moveForward += 1.0f;
        }
        if (this.gameSettings.keyBindBack.isKeyDown()) {
            this.moveForward -= 1.0f;
        }
        if (this.gameSettings.keyBindLeft.isKeyDown()) {
            this.moveStrafe += 1.0f;
        }
        if (this.gameSettings.keyBindRight.isKeyDown()) {
            this.moveStrafe -= 1.0f;
        }
        this.jump = this.gameSettings.keyBindJump.isKeyDown();
        this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
        if (TestAura.target != null && Client.INSTANCE.moduleManager.getModule((String)"TestAura").state && Minecraft.getMinecraft().thePlayer.isMoving() && ((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"TestAura").getSetting((String)"SilentMoveFix").getSetting()).state && ((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"TestAura").getSetting((String)"MoveFix").getSetting()).state) {
            this.testFix(this.moveForward, this.moveStrafe);
        }
        if (this.sneak) {
            if (!((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Scaffold").getSetting((String)"Legit").getSetting()).state && !Minecraft.getMinecraft().gameSettings.keyBindJump.pressed) {
                float RandomSneak = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.8, 0.88);
                this.moveStrafe = (float)((double)this.moveStrafe * 0.3);
                this.moveForward = (float)((double)this.moveForward * 0.3);
            } else {
                this.moveStrafe = (float)((double)this.moveStrafe * 0.3);
                this.moveForward = (float)((double)this.moveForward * 0.3);
            }
        }
        if (Minecraft.getMinecraft().gameSettings.keyBindSneak.pressed && !Client.INSTANCE.moduleManager.getModule("Scaffold").isState()) {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3);
            this.moveForward = (float)((double)this.moveForward * 0.3);
        }
    }

    private void testFix(float forward, float strafe) {
        if (!Minecraft.getMinecraft().thePlayer.isMoving() && Killaura.kTarget == null) {
            return;
        }
        float slipperiness = 0.91f;
        if (this.mc.thePlayer.onGround) {
            slipperiness = this.mc.theWorld.getBlockState((BlockPos)new BlockPos((int)MathHelper.floor_double((double)this.mc.thePlayer.posX), (int)(MathHelper.floor_double((double)this.mc.thePlayer.getEntityBoundingBox().minY) - 1), (int)MathHelper.floor_double((double)this.mc.thePlayer.posZ))).getBlock().slipperiness * 0.91f;
        }
        float moveSpeedOffset = 0.16277136f / (slipperiness * slipperiness * slipperiness);
        float friction = this.mc.thePlayer.onGround ? this.mc.thePlayer.getAIMoveSpeed() * moveSpeedOffset : this.mc.thePlayer.jumpMovementFactor;
        float f = strafe * strafe + forward * forward;
        if ((f = MathHelper.sqrt_float(f)) < 1.0f) {
            f = 1.0f;
        }
        f = friction / f;
        float clientStrafe = strafe * f;
        float clientForward = forward * f;
        float clientRotationSin = MathHelper.sin(this.mc.thePlayer.rotationYaw * (float)Math.PI / 180.0f);
        float clientRotationCos = MathHelper.cos(this.mc.thePlayer.rotationYaw * (float)Math.PI / 180.0f);
        float clientMotionX = clientStrafe * clientRotationCos - clientForward * clientRotationSin;
        float clientMotionZ = clientForward * clientRotationCos + clientStrafe * clientRotationSin;
        float serverRotationSin = MathHelper.sin(Rotations.yaw * (float)Math.PI / 180.0f);
        float serverRotationCos = MathHelper.cos(Rotations.yaw * (float)Math.PI / 180.0f);
        float smalestDistance = Float.NaN;
        float posibleForward = 0.0f;
        float posibleStrafe = 0.0f;
        int strafevalue = -1;
        while (strafevalue <= 1) {
            int forwardvalue = -1;
            while (forwardvalue <= 1) {
                if (forwardvalue != 0 || strafevalue != 0) {
                    float f2 = strafevalue * strafevalue + forwardvalue * forwardvalue;
                    f2 = MathHelper.sqrt_float(f2);
                    float calcStrafe = (float)strafevalue * f;
                    float calcForward = (float)forwardvalue * f;
                    float calcMotionX = calcStrafe * serverRotationCos - calcForward * serverRotationSin;
                    float calcMotionZ = calcForward * serverRotationCos + calcStrafe * serverRotationSin;
                    float diffMotionX = calcMotionX - clientMotionX;
                    float diffMotionZ = calcMotionZ - clientMotionZ;
                    float distance = this.normalize(MathHelper.sqrt_float(diffMotionX * diffMotionX + diffMotionZ * diffMotionZ));
                    System.out.println("Distance: " + distance + " Smalest: " + smalestDistance + " Forward: " + forwardvalue + " Strafe: " + strafevalue);
                    if (Float.isNaN(smalestDistance) || distance < smalestDistance) {
                        posibleForward = forwardvalue;
                        posibleStrafe = strafevalue;
                        smalestDistance = distance;
                    }
                }
                ++forwardvalue;
            }
            ++strafevalue;
        }
        this.moveForward = posibleForward;
        this.moveStrafe = posibleStrafe;
        this.lastForward = posibleForward;
        this.lastStrafe = posibleStrafe;
    }

    private void testFix2(float forward, float strafe) {
        if (!Minecraft.getMinecraft().thePlayer.isMoving() && TriggerBot.Target == null) {
            return;
        }
        float slipperiness = 0.91f;
        if (this.mc.thePlayer.onGround) {
            slipperiness = this.mc.theWorld.getBlockState((BlockPos)new BlockPos((int)MathHelper.floor_double((double)this.mc.thePlayer.posX), (int)(MathHelper.floor_double((double)this.mc.thePlayer.getEntityBoundingBox().minY) - 1), (int)MathHelper.floor_double((double)this.mc.thePlayer.posZ))).getBlock().slipperiness * 0.91f;
        }
        float moveSpeedOffset = 0.16277136f / (slipperiness * slipperiness * slipperiness);
        float friction = this.mc.thePlayer.onGround ? this.mc.thePlayer.getAIMoveSpeed() * moveSpeedOffset : this.mc.thePlayer.jumpMovementFactor;
        float f = strafe * strafe + forward * forward;
        if ((f = MathHelper.sqrt_float(f)) < 1.0f) {
            f = 1.0f;
        }
        f = friction / f;
        float clientStrafe = strafe * f;
        float clientForward = forward * f;
        float clientRotationSin = MathHelper.sin(this.mc.thePlayer.rotationYaw * (float)Math.PI / 180.0f);
        float clientRotationCos = MathHelper.cos(this.mc.thePlayer.rotationYaw * (float)Math.PI / 180.0f);
        float clientMotionX = clientStrafe * clientRotationCos - clientForward * clientRotationSin;
        float clientMotionZ = clientForward * clientRotationCos + clientStrafe * clientRotationSin;
        System.out.println("MotionXDif: " + ((double)clientMotionX - this.mc.thePlayer.motionX));
        System.out.println("MotionZDif: " + ((double)clientMotionZ - this.mc.thePlayer.motionZ));
        float serverRotationSin = MathHelper.sin(Rotations.yaw * (float)Math.PI / 180.0f);
        float serverRotationCos = MathHelper.cos(Rotations.yaw * (float)Math.PI / 180.0f);
        float smalestDistance = Float.NaN;
        float posibleForward = 0.0f;
        float posibleStrafe = 0.0f;
        int strafevalue = -1;
        while (strafevalue <= 1) {
            int forwardvalue = -1;
            while (forwardvalue <= 1) {
                if (forwardvalue != 0 || strafevalue != 0) {
                    float f2 = strafevalue * strafevalue + forwardvalue * forwardvalue;
                    f2 = MathHelper.sqrt_float(f2);
                    float calcStrafe = (float)strafevalue * f;
                    float calcForward = (float)forwardvalue * f;
                    float calcMotionX = calcStrafe * serverRotationCos - calcForward * serverRotationSin;
                    float calcMotionZ = calcForward * serverRotationCos + calcStrafe * serverRotationSin;
                    float diffMotionX = calcMotionX - clientMotionX;
                    float diffMotionZ = calcMotionZ - clientMotionZ;
                    float distance = this.normalize(MathHelper.sqrt_float(diffMotionX * diffMotionX + diffMotionZ * diffMotionZ));
                    System.out.println("Distance: " + distance + " Smalest: " + smalestDistance + " Forward: " + forwardvalue + " Strafe: " + strafevalue);
                    if (Float.isNaN(smalestDistance) || distance < smalestDistance) {
                        posibleForward = forwardvalue;
                        posibleStrafe = strafevalue;
                        smalestDistance = distance;
                    }
                }
                ++forwardvalue;
            }
            ++strafevalue;
        }
        System.out.println("Forward: " + posibleForward + " Strafe: " + posibleStrafe);
        this.moveForward = posibleForward;
        this.moveStrafe = posibleStrafe;
        this.lastForward = posibleForward;
        this.lastStrafe = posibleStrafe;
    }

    public float normalize(float value) {
        if (value < 0.0f) {
            return value / -1.0f;
        }
        return value;
    }
}

