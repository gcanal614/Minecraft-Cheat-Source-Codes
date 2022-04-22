/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.module.impl.movement;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventRender3D;
import de.fanta.events.listeners.EventTick;
import de.fanta.events.listeners.PlayerMoveEvent;
import de.fanta.module.Module;
import de.fanta.module.impl.combat.Killaura;
import de.fanta.module.impl.combat.TestAura;
import de.fanta.module.impl.movement.Speed;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.ColorValue;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.setting.settings.Slider;
import de.fanta.utils.Colors;
import de.fanta.utils.FriendSystem;
import de.fanta.utils.RenderUtil;
import de.fanta.utils.RotationUtil;
import java.awt.Color;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class TargetStrafe
extends Module {
    public static boolean space;
    private int direction = -1;
    public static double range;

    public TargetStrafe() {
        super("TargetStrafe", 0, Module.Type.Movement, Color.cyan);
        this.settings.add(new Setting("Watchdog", new CheckBox(false)));
        this.settings.add(new Setting("Circle", new CheckBox(false)));
        this.settings.add(new Setting("NoVoid", new CheckBox(false)));
        this.settings.add(new Setting("StrafeMode", new DropdownBox("JumpOnly", new String[]{"JumpOnly", "Normal"})));
        this.settings.add(new Setting("Range", new Slider(0.1, 6.0, 0.1, 4.0)));
        this.settings.add(new Setting("Color", new ColorValue(Color.RED.getRGB())));
    }

    @Override
    public void onEvent(Event event) {
        range = ((Slider)this.getSetting((String)"Range").getSetting()).curValue;
        if (((CheckBox)this.getSetting((String)"Watchdog").getSetting()).state && TargetStrafe.mc.thePlayer.fallDistance < 2.0f && event instanceof EventTick && !Client.INSTANCE.moduleManager.getModule("Speed").isState()) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(TargetStrafe.mc.thePlayer.posX, TargetStrafe.mc.thePlayer.posY, TargetStrafe.mc.thePlayer.posZ, false));
        }
        float cfr_ignored_0 = TargetStrafe.mc.thePlayer.moveForward;
        if (event instanceof EventRender3D) {
            Iterator<Entity> iterator = TargetStrafe.mc.theWorld.getLoadedEntityList().iterator();
            while (iterator.hasNext()) {
                Entity e2;
                Entity entity = e2 = iterator.next();
                if (TestAura.target == null || !Client.INSTANCE.moduleManager.getModule("TestAura").isState() || !((CheckBox)this.getSetting((String)"Circle").getSetting()).state) continue;
                this.drawCircle(TargetStrafe.mc.timer.renderPartialTicks, range);
            }
        }
        if (event instanceof PlayerMoveEvent) {
            this.doStrafeAtSpeed(PlayerMoveEvent.INSTANCE, Speed.getSpeed());
        }
        if (TargetStrafe.mc.thePlayer.isCollidedHorizontally) {
            this.SD();
        }
        if (!this.isBlockUnder()) {
            this.SD();
        }
        if (TargetStrafe.mc.getMinecraft().gameSettings.keyBindRight.pressed) {
            this.direction = -1;
        } else if (TargetStrafe.mc.getMinecraft().gameSettings.keyBindLeft.pressed) {
            this.direction = 1;
        }
    }

    private void SD() {
        this.direction = this.direction == 1 ? -1 : 1;
    }

    public boolean doStrafeAtSpeed(PlayerMoveEvent event, double moveSpeed) {
        boolean strafe;
        block16: {
            strafe = this.canStrafe();
            if (TestAura.target == null || !strafe) break block16;
            float[] rotations = RotationUtil.getRotations(TestAura.target);
            float Strafe = (float)MathHelper.getRandomDoubleInRange(new Random(), 1.5, 2.0);
            switch (((DropdownBox)this.getSetting((String)"StrafeMode").getSetting()).curOption) {
                case "JumpOnly": {
                    if (!TargetStrafe.mc.gameSettings.keyBindJump.pressed) break;
                    if (!this.isBlockUnder()) {
                        if (TargetStrafe.mc.thePlayer.getDistanceToEntity(TestAura.target) <= 0.0f) {
                            Speed.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                            break;
                        }
                        Speed.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
                        break;
                    }
                    if (((CheckBox)this.getSetting((String)"NoVoid").getSetting()).state) break;
                    if ((double)TargetStrafe.mc.thePlayer.getDistanceToEntity(TestAura.target) <= range) {
                        Speed.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                        break;
                    }
                    Speed.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
                    break;
                }
                case "Normal": {
                    if (!this.isBlockUnder()) {
                        if (TargetStrafe.mc.thePlayer.getDistanceToEntity(TestAura.target) <= 0.0f) {
                            Speed.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                            break;
                        }
                        Speed.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
                        break;
                    }
                    if (((CheckBox)this.getSetting((String)"NoVoid").getSetting()).state) break;
                    if ((double)TargetStrafe.mc.thePlayer.getDistanceToEntity(TestAura.target) <= range) {
                        Speed.setSpeed1(event, moveSpeed, rotations[0], this.direction, 0.0);
                        break;
                    }
                    Speed.setSpeed1(event, moveSpeed, rotations[0], this.direction, 1.0);
                }
            }
        }
        return strafe;
    }

    public boolean canStrafe() {
        return Client.INSTANCE.moduleManager.getModule("TestAura").isState() && TestAura.target != null && !FriendSystem.isFriendString(TestAura.target.getName());
    }

    public boolean isBlockUnder() {
        int i = (int)TargetStrafe.mc.thePlayer.posY;
        while (i >= 0) {
            BlockPos position = new BlockPos(TargetStrafe.mc.thePlayer.posX, (double)i, TargetStrafe.mc.thePlayer.posZ);
            if (!(TargetStrafe.mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir)) {
                return true;
            }
            --i;
        }
        return false;
    }

    private boolean Check(Entity e2) {
        if (!e2.isEntityAlive()) {
            return false;
        }
        if (e2 == TargetStrafe.mc.thePlayer) {
            return false;
        }
        return e2 instanceof EntityPlayer;
    }

    private void drawCircle(float partialTicks, double rad) {
        if (Killaura.hasTarget()) {
            GL11.glPushMatrix();
            GL11.glDisable((int)3553);
            RenderUtil.startDrawing();
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glLineWidth((float)1.0f);
            GL11.glBegin((int)3);
            double x = TestAura.target.lastTickPosX + (TestAura.target.posX - TestAura.target.lastTickPosX) * (double)partialTicks - TargetStrafe.mc.getRenderManager().viewerPosX;
            double y = Killaura.kTarget.lastTickPosY + (TestAura.target.posY - TestAura.target.lastTickPosY) * (double)partialTicks - TargetStrafe.mc.getRenderManager().viewerPosY;
            double z = TestAura.target.lastTickPosZ + (TestAura.target.posZ - TestAura.target.lastTickPosZ) * (double)partialTicks - TargetStrafe.mc.getRenderManager().viewerPosZ;
            int[] rgb = Colors.getRGB(this.getColor2());
            float r = 0.003921569f * (float)rgb[0];
            float g = 0.003921569f * (float)rgb[1];
            float b = 0.003921569f * (float)rgb[2];
            double pix2 = Math.PI * 2;
            int i = 0;
            while (i <= 90) {
                GL11.glColor3f((float)r, (float)g, (float)b);
                GL11.glVertex3d((double)(x + rad * Math.cos((double)i * pix2 / 45.0)), (double)y, (double)(z + rad * Math.sin((double)i * pix2 / 45.0)));
                ++i;
            }
            GL11.glEnd();
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
            RenderUtil.stopDrawing();
            GL11.glEnable((int)3553);
            GL11.glPopMatrix();
        }
    }

    public int getColor2() {
        try {
            return ((ColorValue)this.getSetting((String)"Color").getSetting()).color;
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }
}

