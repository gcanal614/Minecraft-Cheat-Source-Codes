/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.combat;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventPreMotion;
import de.fanta.events.listeners.EventReceivedPacket;
import de.fanta.events.listeners.EventRender3D;
import de.fanta.events.listeners.EventUpdate;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.utils.FriendSystem;
import de.fanta.utils.Rotations;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import java.util.ConcurrentModificationException;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class TriggerBot
extends Module {
    TimeUtil time = new TimeUtil();
    private float[] facing;
    public static Entity Target;
    public static float yaw;
    public static float pitch;
    private float lastYaw;
    private float lastPitch;

    public TriggerBot() {
        super("TriggerBot", 0, Module.Type.Combat, Color.WHITE);
        this.settings.add(new Setting("AutoBlock", new CheckBox(true)));
        this.settings.add(new Setting("RotationMode", new DropdownBox("Instant", new String[]{"Instant", "Smooth", "Intave", "AAC", "Matrix"})));
        this.settings.add(new Setting("Modes", new DropdownBox("Switch", new String[]{"Switch", "Single", "Nearest"})));
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventReceivedPacket) {
            Packet p = EventReceivedPacket.INSTANCE.getPacket();
            boolean cfr_ignored_0 = p instanceof S08PacketPlayerPosLook;
        }
        try {
            if (((CheckBox)this.getSetting((String)"AutoBlock").getSetting()).state) {
                if (TriggerBot.mc.thePlayer.getHeldItem() != null && TriggerBot.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                    TriggerBot.mc.gameSettings.keyBindUseItem.pressed = false;
                }
                if (!TriggerBot.mc.thePlayer.isSwingInProgress && TriggerBot.mc.thePlayer.getHeldItem() != null && TriggerBot.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                    TriggerBot.mc.gameSettings.keyBindUseItem.pressed = false;
                }
            }
            if (((CheckBox)this.getSetting((String)"AutoBlock").getSetting()).state && TriggerBot.mc.thePlayer.isSwingInProgress && TriggerBot.mc.thePlayer.getHeldItem() != null && TriggerBot.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                TriggerBot.mc.gameSettings.keyBindUseItem.pressed = true;
            }
            if (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption != null || Target == null) {
                Target = this.modes();
            }
            try {
                int CPS;
                if (event instanceof EventUpdate && TriggerBot.mc.objectMouseOver.entityHit != null && this.time.hasReached(1000 / (CPS = TriggerBot.randomNumber(20, 14)))) {
                    try {
                        mc.clickMouse();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    this.time.reset();
                }
                if (event instanceof EventRender3D) {
                    if (Target != null) {
                        float[] rota = Rotations.Intavee(TriggerBot.mc.thePlayer, (EntityLivingBase)Target);
                        if (TriggerBot.mc.thePlayer.getDistanceToEntity(Target) >= 3.0f || FriendSystem.isFriendString(Target.getName())) {
                            return;
                        }
                    }
                    this.RotationModes(Target);
                }
            }
            catch (NullPointerException nullPointerException) {}
        }
        catch (ConcurrentModificationException concurrentModificationException) {
            // empty catch block
        }
        if (event instanceof EventPreMotion) {
            ((EventPreMotion)event).setPitch(Rotations.pitch);
            ((EventPreMotion)event).setYaw(Rotations.yaw);
        }
    }

    public static int randomNumber(int max, int min) {
        return Math.round((float)min + (float)Math.random() * (float)(max - min));
    }

    /*
     * Exception decompiling
     */
    public Entity modes() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Can't sort instructions [@NONE, blocks:[5] lbl50 : CaseStatement: default:\u000a, @NONE, blocks:[5] lbl50 : CaseStatement: default:\u000a]
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

    public static float[] Intavee(EntityPlayerSP player, EntityLivingBase target) {
        float RotationPitch = (float)MathHelper.getRandomDoubleInRange(new Random(), 90.0, 92.0);
        float RotationYaw = (float)MathHelper.getRandomDoubleInRange(new Random(), RotationPitch, 94.0);
        float RotationYaw3 = (float)MathHelper.getRandomDoubleInRange(new Random(), 3.12, 3.13);
        float RotationYaw2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 175.0, 180.0);
        double posX = target.posX - player.posX;
        float RotationY2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 178.0, 180.0);
        float RotationY4 = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.2, 0.3);
        float RotationY3 = (float)MathHelper.getRandomDoubleInRange(new Random(), RotationY4, 0.1);
        double posY = target.posY + (double)target.getEyeHeight() - (player.posY + (double)player.getAge() + (double)player.getEyeHeight());
        double posZ = target.posZ - player.posZ;
        double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
        float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(posY, var14) * (double)RotationYaw2 / 90.0));
        float f2 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        return new float[]{yaw, MathHelper.clamp_float(pitch, -90.0f, 90.0f)};
    }

    public static float interpolateRotation(float par1, float par2, float par3) {
        float f = MathHelper.wrapAngleTo180_float(par2 - par1);
        if (f > par3) {
            f = par3;
        }
        if (f < -par3) {
            f = -par3;
        }
        return par1 + f;
    }

    public void RotationModes(Entity target) {
        Vec3 randomCenter = Rotations.getRandomCenter(target.getEntityBoundingBox());
        Vec3 Center = Rotations.getCenter(target.getEntityBoundingBox());
        float yaw1 = Rotations.getYawToPoint(Center);
        float pitch1 = Rotations.getPitchToPoint(Center);
        float yaw2 = Rotations.getYawToPoint(randomCenter);
        float pitch2 = Rotations.getPitchToPoint(randomCenter);
        switch (((DropdownBox)this.getSetting((String)"RotationMode").getSetting()).curOption) {
            case "Instant": {
                Rotations.setRotation(yaw1, pitch1);
                break;
            }
            case "Smooth": {
                Rotations.setYaw(yaw1, TriggerBot.randomNumber(33, 14));
                Rotations.setPitch(pitch1, TriggerBot.randomNumber(20, 8));
                break;
            }
            case "AAC": {
                try {
                    if (TriggerBot.mc.objectMouseOver.entityHit != null) {
                        Rotations.setYaw(yaw1, 0.0f);
                        Rotations.setPitch(pitch1, 0.0f);
                    }
                    if (TriggerBot.mc.objectMouseOver.entityHit != null) break;
                    Rotations.setYaw(yaw2, 90.0f);
                    Rotations.setPitch(pitch2, 180.0f);
                }
                catch (NullPointerException nullPointerException) {}
                break;
            }
            case "Intave": {
                float[] rota = Rotations.Intavee(TriggerBot.mc.thePlayer, (EntityLivingBase)target);
                try {
                    if (TriggerBot.mc.objectMouseOver.entityHit != null) {
                        TriggerBot.mc.thePlayer.rotationYawHead = rota[0];
                        TriggerBot.mc.thePlayer.rotationPitchHead = rota[1];
                    }
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
                try {
                    if (TriggerBot.mc.objectMouseOver.entityHit != null) break;
                    Rotations.setYaw(rota[0], 180.0f);
                    Rotations.setPitch(rota[1], 180.0f);
                }
                catch (NullPointerException nullPointerException) {}
                break;
            }
            case "Matrix": {
                float[] rota2 = Rotations.Intaveee(TriggerBot.mc.thePlayer, (EntityLivingBase)target);
                try {
                    if (TriggerBot.mc.objectMouseOver.entityHit != null) {
                        Rotations.setYaw(rota2[0], 180.0f);
                        Rotations.setPitch(rota2[1], 180.0f);
                    }
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
                try {
                    if (TriggerBot.mc.objectMouseOver.entityHit != null) break;
                    Rotations.setYaw(rota2[0], 180.0f);
                    Rotations.setPitch(rota2[1], 180.0f);
                    break;
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
            }
        }
    }
}

