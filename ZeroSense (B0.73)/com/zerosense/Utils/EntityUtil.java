package com.zerosense.Utils;

import com.zerosense.Events.impl.EventMove;
import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public class EntityUtil
{
    private static Minecraft mc = Minecraft.getMinecraft();


    /*
    public static ArrayList<EntityLivingBase> getEntitiesWithAntiBot() {
        final ArrayList<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        final Collection<NetworkPlayerInfo> playerlist = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
        final ArrayList<String> playerlists = new ArrayList<String>();
        for (final NetworkPlayerInfo info : playerlist) {
            if (info != null && info.func_178845_a() != null) {
                if (info.func_178845_a().getName() == null) {
                    continue;
                }
                playerlists.add(info.func_178845_a().getName());
            }
        }
        for (final Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (o instanceof EntityLivingBase) {
                for (final String name : playerlists) {
                    if (name.equals(((EntityLivingBase)o).getName())) {
                        final Entity e = (Entity)o;
                        final EntityLivingBase elb = (EntityLivingBase)e;
                        if (elb != Minecraft.getMinecraft().thePlayer) {
                            targets.add(elb);
                            break;
                        }
                        break;
                    }
                }
            }
            if (o instanceof EntityZombie) {
                final Entity e2 = (Entity)o;
                final EntityLivingBase elb2 = (EntityLivingBase)e2;
                targets.add(elb2);
                break;
            }
        }
        return targets;
    }

     */

    public static void GetEnityDamage(double damage) {

        Minecraft mc = Minecraft.getMinecraft();

        if (damage > MathHelper.floor_double(mc.thePlayer.getMaxHealth()))
            damage = MathHelper.floor_double(mc.thePlayer.getMaxHealth());

        double offset = 0.0625;
        //offset = 0.015625;
        if (mc.thePlayer != null && mc.getNetHandler() != null) {
            for (short i = 0; i <= ((3 + damage) / offset); i++) {
                mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY + ((offset / 2) * 1), mc.thePlayer.posZ, false));
                mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY + ((offset / 2) * 2), mc.thePlayer.posZ, false));
                mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY, mc.thePlayer.posZ, (i == ((3 + damage) / offset))));
                //mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX,
                //mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, (i == ((3 + damage) / offset))));
            }
        }

    }
/*
    public static ArrayList<EntityLivingBase> getEntities() {
        final ArrayList<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        final Collection<NetworkPlayerInfo> playerlist = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
        final ArrayList<String> playerlists = new ArrayList<String>();
        for (final NetworkPlayerInfo info : playerlist) {
            if (info != null && info.func_178845_a() != null) {
                if (info.func_178845_a().getName() == null) {
                    continue;
                }
                playerlists.add(info.func_178845_a().getName());
            }
        }
        for (final Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (o instanceof EntityLivingBase) {
                if (ZeroSense.antiBot.toggled == true) {
                    final Entity e = (Entity)o;
                    final EntityLivingBase elb = (EntityLivingBase)e;
                    if (elb != Minecraft.getMinecraft().thePlayer && !elb.isOnSameTeam(Minecraft.getMinecraft().thePlayer)) {
                        targets.add(elb);
                        continue;
                    }
                    continue;
                }
                else {
                    for (final String name : playerlists) {
                        if (name.equals(((EntityLivingBase)o).getName()) || ZeroSense.antiBot.toggled == true) {
                            final Entity e2 = (Entity)o;
                            final EntityLivingBase elb2 = (EntityLivingBase)e2;
                            if (elb2 != Minecraft.getMinecraft().thePlayer && !elb2.isOnSameTeam(Minecraft.getMinecraft().thePlayer)) {
                                targets.add(elb2);
                                break;
                            }
                            break;
                        }
                    }
                }
            }
            if (o instanceof EntityZombie) {
                final Entity e = (Entity)o;
                final EntityLivingBase elb = (EntityLivingBase)e;
                targets.add(elb);
                break;
            }
        }
        return targets;
    }

 */

    public static EntityLivingBase getClosest(final ArrayList<EntityLivingBase> entities, double distance) {
        EntityLivingBase target = null;
        for (final Object object : entities) {
            final Entity entity = (Entity)object;
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase player = (EntityLivingBase)entity;
                if (player instanceof EntityArmorStand || player instanceof EntitySlime || player == Minecraft.getMinecraft().thePlayer) {
                    continue;
                }
                final double currentDist = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(player);
                if (currentDist > distance) {
                    continue;
                }
                distance = currentDist;
                target = player;
            }
        }
        return target;
    }
    public static void setMoveSpeed(EventMove event, double speed) {
    double forward = mc.thePlayer.movementInput.moveForward;
    double strafe = mc.thePlayer.movementInput.moveStrafe;
    float yaw = mc.thePlayer.rotationYaw;
    if (forward == 0.0D && strafe == 0.0D) {
        event.setX(0.0D);
        event.setZ(0.0D);
    } else {
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += (float)(forward > 0.0D ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += (float)(forward > 0.0D ? 45 : -45);
            }

            strafe = 0.0D;
            if (forward > 0.0D) {
                forward = 1.0D;
            } else if (forward < 0.0D) {
                forward = -1.0D;
            }
        }

        event.setX(forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw)));
        event.setZ(forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw)));
    }
}
}
