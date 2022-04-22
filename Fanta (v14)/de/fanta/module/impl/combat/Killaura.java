/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.lavache.anime.Animate
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.module.impl.combat;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventPacket;
import de.fanta.events.listeners.EventPreMotion;
import de.fanta.events.listeners.EventReceivedPacket;
import de.fanta.events.listeners.EventRender2D;
import de.fanta.events.listeners.EventRender3D;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.ColorValue;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.setting.settings.Slider;
import de.fanta.utils.Colors;
import de.fanta.utils.FriendSystem;
import de.fanta.utils.RenderUtil;
import de.fanta.utils.Rotations;
import de.fanta.utils.TimeUtil;
import fr.lavache.anime.Animate;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.particle.EntityParticleEmitter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class Killaura
extends Module {
    public static EntityLivingBase kTarget;
    public static double range;
    public static double preAimRange;
    public static double cracks;
    float yaw;
    float pitch;
    private float lastYaw;
    private float lastPitch;
    double minCPS;
    double maxCPS;
    public static float yaww;
    public static float pitchh;
    public static float[] facing;
    Animate anim = new Animate();
    private final CopyOnWriteArrayList<Entity> copyEntities = new CopyOnWriteArrayList();
    private final ArrayList<Entity> madeSound = new ArrayList();
    TimeUtil time = new TimeUtil();
    TimeUtil timeAB = new TimeUtil();
    Random random = new Random();
    public static ArrayList<Entity> bots;
    float lostHealthPercentage = 0.0f;
    float lastHealthPercentage = 0.0f;

    static {
        bots = new ArrayList();
    }

    public Killaura() {
        super("Killaura", 19, Module.Type.Combat, Color.green);
        this.settings.add(new Setting("RedColoring", new CheckBox(false)));
        this.settings.add(new Setting("Raytrace", new CheckBox(false)));
        this.settings.add(new Setting("Antibot", new CheckBox(false)));
        this.settings.add(new Setting("SoundCheck", new CheckBox(false)));
        this.settings.add(new Setting("AutoBlock", new CheckBox(false)));
        this.settings.add(new Setting("Swing", new CheckBox(false)));
        this.settings.add(new Setting("TargetHUD", new CheckBox(false)));
        this.settings.add(new Setting("LegitRange", new CheckBox(false)));
        this.settings.add(new Setting("Multy", new CheckBox(false)));
        this.settings.add(new Setting("KeepSprint", new CheckBox(false)));
        this.settings.add(new Setting("1.12 Hit", new CheckBox(false)));
        this.settings.add(new Setting("TargetESP", new CheckBox(false)));
        this.settings.add(new Setting("Range", new Slider(1.0, 8.0, 0.1, 4.0)));
        this.settings.add(new Setting("PreAimRange", new Slider(0.0, 5.0, 0.1, 2.0)));
        this.settings.add(new Setting("CPS-Max", new Slider(0.0, 20.0, 1.0, 10.0)));
        this.settings.add(new Setting("CPS-Min", new Slider(0.0, 20.0, 1.0, 2.0)));
        this.settings.add(new Setting("Cracks", new Slider(0.0, 1000.0, 1.0, 50.0)));
        this.settings.add(new Setting("Color", new ColorValue(Color.RED.getRGB())));
        this.settings.add(new Setting("AutoBlockMode", new DropdownBox("Intave", new String[]{"Intave", "SmartIntave", "NCP", "Hypixel", "Other"})));
        this.settings.add(new Setting("Modes", new DropdownBox("Nearest", new String[]{"Single", "Nearest", "Multy", "Switch"})));
        this.settings.add(new Setting("RotationMode", new DropdownBox("Instant", new String[]{"Instant", "Smooth", "Intave", "AAC"})));
        this.settings.add(new Setting("TargetHUDMode", new DropdownBox("Novoline", new String[]{"Fanta", "OldAstolfo", "Novoline"})));
        this.settings.add(new Setting("TargetHUDColor", new DropdownBox("DARK", new String[]{"DARK", "WHITE"})));
    }

    @Override
    public void onEnable() {
        Killaura.mc.gameSettings.keyBindUseItem.pressed = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        bots.clear();
        Killaura.mc.gameSettings.keyBindSprint.pressed = false;
        super.onDisable();
    }

    @Override
    public void onEvent(Event event) {
        EventReceivedPacket eventReceivedPacket;
        Object packet;
        if (event instanceof EventPacket) {
            EventPacket cfr_ignored_0 = (EventPacket)event;
            boolean cfr_ignored_1 = EventPacket.getPacket() instanceof C03PacketPlayer;
        }
        if (event instanceof EventReceivedPacket && ((CheckBox)this.getSetting((String)"SoundCheck").getSetting()).state && (packet = (eventReceivedPacket = (EventReceivedPacket)event).getPacket()) instanceof S29PacketSoundEffect) {
            S29PacketSoundEffect soundEffect = (S29PacketSoundEffect)packet;
            this.copyEntities.addAll(Killaura.mc.theWorld.loadedEntityList);
            this.copyEntities.forEach(entity -> {
                if (entity != Killaura.mc.thePlayer && entity.getDistance(soundEffect.getX(), soundEffect.getY(), soundEffect.getZ()) <= 0.8) {
                    this.madeSound.add((Entity)entity);
                }
            });
            this.copyEntities.clear();
        }
        if (event instanceof EventPreMotion) {
            if (((CheckBox)this.getSetting((String)"Antibot").getSetting()).state) {
                for (Object e : Killaura.mc.theWorld.getLoadedEntityList()) {
                    if (!(e instanceof EntityPlayer) || !this.isBot((EntityPlayer)e) && !((EntityPlayer)e).isInvisible() || e == Killaura.mc.thePlayer) continue;
                    bots.add((EntityPlayer)e);
                    Killaura.mc.theWorld.removeEntity((Entity)e);
                }
            }
            if (((CheckBox)this.getSetting((String)"AutoBlock").getSetting()).state && ((CheckBox)this.getSetting((String)"AutoBlock").getSetting()).state && !Killaura.mc.thePlayer.isSwingInProgress && Killaura.mc.thePlayer.getHeldItem() != null && Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && !Killaura.hasTarget()) {
                Killaura.mc.gameSettings.keyBindUseItem.pressed = false;
            }
            if (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption != null || kTarget == null) {
                kTarget = (EntityLivingBase)this.modes();
            }
            range = ((Slider)this.getSetting((String)"Range").getSetting()).curValue;
            preAimRange = ((Slider)this.getSetting((String)"PreAimRange").getSetting()).curValue;
            this.maxCPS = ((Slider)this.getSetting((String)"CPS-Max").getSetting()).curValue;
            this.minCPS = ((Slider)this.getSetting((String)"CPS-Min").getSetting()).curValue;
            int n = Killaura.randomNumber((int)this.maxCPS, (int)this.minCPS);
            if (kTarget != null && kTarget != Killaura.mc.thePlayer && (double)Killaura.mc.thePlayer.getDistanceToEntity(kTarget) < range + preAimRange && ((EntityPlayer)kTarget).getHealth() != 0.0f) {
                if (bots.contains(kTarget) || FriendSystem.isFriendString(kTarget.getName()) || kTarget.getName().equalsIgnoreCase("\u00a76Shop")) {
                    return;
                }
                if (!((CheckBox)this.getSetting((String)"Multy").getSetting()).state) {
                    ((EventPreMotion)event).setPitch(Rotations.pitch);
                    ((EventPreMotion)event).setYaw(Rotations.yaw);
                    this.RotationModes(kTarget);
                }
                if (((CheckBox)this.getSetting((String)"LegitRange").getSetting()).state) {
                    if (Killaura.mc.thePlayer != null && (double)Killaura.mc.thePlayer.getDistanceToEntity(kTarget) <= range && kTarget != null && Killaura.mc.objectMouseOver != null && Killaura.mc.objectMouseOver.entityHit != null) {
                        if (this.madeSound.contains(kTarget) || bots.contains(kTarget) || FriendSystem.isFriendString(kTarget.getName()) || kTarget.getName().equalsIgnoreCase("\u00a76Shop")) {
                            return;
                        }
                        if (((CheckBox)this.getSetting((String)"1.12 Hit").getSetting()).state) {
                            if (Killaura.mc.thePlayer.ticksExisted % 12 == 0) {
                                mc.clickMouse();
                                int i = 0;
                                while ((double)i < cracks) {
                                    double d2;
                                    double d1;
                                    double d0 = Entity.rand.nextFloat() * 2.0f - 1.0f;
                                    if (d0 * d0 + (d1 = (double)(Entity.rand.nextFloat() * 2.0f - 1.0f)) * d1 + (d2 = (double)(Entity.rand.nextFloat() * 2.0f - 1.0f)) * d2 <= 1.0) {
                                        double d3 = EntityParticleEmitter.attachedEntity.posX + d0 * (double)EntityParticleEmitter.attachedEntity.width / 4.0;
                                        double d4 = EntityParticleEmitter.attachedEntity.getEntityBoundingBox().minY + (double)(EntityParticleEmitter.attachedEntity.height / 2.0f) + d1 * (double)EntityParticleEmitter.attachedEntity.height / 4.0;
                                        double d5 = EntityParticleEmitter.attachedEntity.posZ + d2 * (double)EntityParticleEmitter.attachedEntity.width / 4.0;
                                        Entity.worldObj.spawnParticle(EntityParticleEmitter.particleTypes, false, d3, d4, d5, d0, d1 + 0.2, d2, new int[1]);
                                        this.time.reset();
                                    }
                                    ++i;
                                }
                            }
                        } else if (this.time.hasReached(1000 / n)) {
                            int i = 0;
                            while ((double)i < cracks) {
                                double d2;
                                double d1;
                                double d0 = Entity.rand.nextFloat() * 2.0f - 1.0f;
                                if (d0 * d0 + (d1 = (double)(Entity.rand.nextFloat() * 2.0f - 1.0f)) * d1 + (d2 = (double)(Entity.rand.nextFloat() * 2.0f - 1.0f)) * d2 <= 1.0) {
                                    double d3 = EntityParticleEmitter.attachedEntity.posX + d0 * (double)EntityParticleEmitter.attachedEntity.width / 4.0;
                                    double d4 = EntityParticleEmitter.attachedEntity.getEntityBoundingBox().minY + (double)(EntityParticleEmitter.attachedEntity.height / 2.0f) + d1 * (double)EntityParticleEmitter.attachedEntity.height / 4.0;
                                    double d5 = EntityParticleEmitter.attachedEntity.posZ + d2 * (double)EntityParticleEmitter.attachedEntity.width / 4.0;
                                    Entity.worldObj.spawnParticle(EntityParticleEmitter.particleTypes, false, d3, d4, d5, d0, d1 + 0.2, d2, new int[1]);
                                }
                                ++i;
                            }
                            this.time.reset();
                            mc.clickMouse();
                            this.time.reset();
                        }
                    }
                    this.AutoBlock();
                } else if (((CheckBox)this.getSetting((String)"1.12 Hit").getSetting()).state) {
                    if (Killaura.mc.thePlayer.ticksExisted % 12 == 0 && (double)Killaura.mc.thePlayer.getDistanceToEntity(kTarget) <= range && kTarget != null) {
                        if (this.madeSound.contains(kTarget) || bots.contains(kTarget) || FriendSystem.isFriendString(kTarget.getName()) || kTarget.getName().equalsIgnoreCase("\u00a76Shop")) {
                            return;
                        }
                        this.AutoBlock();
                        if (this.time.hasReached(1000 / n)) {
                            int i = 0;
                            while ((double)i < cracks) {
                                double d2;
                                double d1;
                                double d0 = Entity.rand.nextFloat() * 2.0f - 1.0f;
                                if (d0 * d0 + (d1 = (double)(Entity.rand.nextFloat() * 2.0f - 1.0f)) * d1 + (d2 = (double)(Entity.rand.nextFloat() * 2.0f - 1.0f)) * d2 <= 1.0) {
                                    double d3 = EntityParticleEmitter.attachedEntity.posX + d0 * (double)EntityParticleEmitter.attachedEntity.width / 4.0;
                                    double d4 = EntityParticleEmitter.attachedEntity.getEntityBoundingBox().minY + (double)(EntityParticleEmitter.attachedEntity.height / 2.0f) + d1 * (double)EntityParticleEmitter.attachedEntity.height / 4.0;
                                    double d5 = EntityParticleEmitter.attachedEntity.posZ + d2 * (double)EntityParticleEmitter.attachedEntity.width / 4.0;
                                    Entity.worldObj.spawnParticle(EntityParticleEmitter.particleTypes, false, d3, d4, d5, d0, d1 + 0.2, d2, new int[1]);
                                }
                                ++i;
                            }
                            this.time.reset();
                            Killaura.mc.playerController.attackEntity(Killaura.mc.thePlayer, kTarget);
                            Killaura.mc.thePlayer.swingItem();
                            this.time.reset();
                        }
                    }
                } else if ((double)Killaura.mc.thePlayer.getDistanceToEntity(kTarget) <= range && kTarget != null) {
                    if (bots.contains(kTarget) || FriendSystem.isFriendString(kTarget.getName()) || kTarget.getName().equalsIgnoreCase("\u00a76Shop")) {
                        return;
                    }
                    this.AutoBlock();
                    if (this.time.hasReached(1000 / n)) {
                        int i = 0;
                        while ((double)i < cracks) {
                            double d2;
                            double d1;
                            double d0 = Entity.rand.nextFloat() * 2.0f - 1.0f;
                            if (d0 * d0 + (d1 = (double)(Entity.rand.nextFloat() * 2.0f - 1.0f)) * d1 + (d2 = (double)(Entity.rand.nextFloat() * 2.0f - 1.0f)) * d2 <= 1.0) {
                                double d3 = EntityParticleEmitter.attachedEntity.posX + d0 * (double)EntityParticleEmitter.attachedEntity.width / 4.0;
                                double d4 = EntityParticleEmitter.attachedEntity.getEntityBoundingBox().minY + (double)(EntityParticleEmitter.attachedEntity.height / 2.0f) + d1 * (double)EntityParticleEmitter.attachedEntity.height / 4.0;
                                double d5 = EntityParticleEmitter.attachedEntity.posZ + d2 * (double)EntityParticleEmitter.attachedEntity.width / 4.0;
                                Entity.worldObj.spawnParticle(EntityParticleEmitter.particleTypes, false, d3, d4, d5, d0, d1 + 0.2, d2, new int[1]);
                            }
                            ++i;
                        }
                        this.time.reset();
                        Killaura.mc.thePlayer.swingItem();
                        Killaura.mc.playerController.attackEntity(Killaura.mc.thePlayer, kTarget);
                        this.time.reset();
                    }
                }
            } else {
                kTarget = null;
            }
        }
        if (event instanceof EventRender3D && ((CheckBox)this.getSetting((String)"TargetESP").getSetting()).state) {
            this.drawTargetESP((EntityPlayer)kTarget, Killaura.mc.timer.renderPartialTicks);
        }
        if (event instanceof EventRender2D && event.isPre()) {
            this.drawTargetHUD();
        }
    }

    public void mouseFix() {
        float f = Killaura.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f2 = f * f * f * 1.2f;
        Rotations.yaw -= Rotations.yaw % f2;
        Rotations.pitch -= Rotations.pitch % (f2 * f);
    }

    public Entity modes() {
        EntityPlayer target = null;
        switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
            case "Switch": {
                for (Object o : Killaura.mc.theWorld.loadedEntityList) {
                    Entity e = (Entity)o;
                    if (e.getName().equals(Killaura.mc.thePlayer.getName()) || !(e instanceof EntityPlayer) || e instanceof EntityArmorStand) continue;
                    EntityPlayer player = (EntityPlayer)e;
                    if (target == null || player.hurtTime < target.hurtTime) {
                        if (!((double)Killaura.mc.thePlayer.getDistanceToEntity(e) < range + preAimRange)) continue;
                        target = player;
                        continue;
                    }
                    if (player.hurtTime != target.hurtTime || !(Killaura.mc.thePlayer.getDistanceToEntity(player) < Killaura.mc.thePlayer.getDistanceToEntity(target)) || !((double)Killaura.mc.thePlayer.getDistanceToEntity(e) < range + preAimRange)) continue;
                    target = player;
                }
                return target;
            }
            case "Single": {
                for (Object o : Killaura.mc.theWorld.loadedEntityList) {
                    Entity e = (Entity)o;
                    if (e.getName().equals(Killaura.mc.thePlayer.getName()) || !(e instanceof EntityPlayer) || e instanceof EntityArmorStand || target != null && !((double)Killaura.mc.thePlayer.getDistanceToEntity(e) <= (double)Killaura.mc.thePlayer.getDistanceToEntity(target) + preAimRange) || !((double)Killaura.mc.thePlayer.getDistanceToEntity(e) < range + preAimRange)) continue;
                    target = (EntityPlayer)e;
                }
                return target;
            }
            case "Multy": {
                Entity e;
                for (Object o : Killaura.mc.theWorld.loadedEntityList) {
                    e = (Entity)o;
                    if (e.getName().equals(Killaura.mc.thePlayer.getName()) || !(e instanceof EntityPlayer) || e instanceof EntityArmorStand || target != null || !((double)Killaura.mc.thePlayer.getDistanceToEntity(e) < range + preAimRange)) continue;
                    target = (EntityPlayer)e;
                }
            }
            case "Nearest": {
                Entity e;
                for (Object o : Killaura.mc.theWorld.loadedEntityList) {
                    e = (Entity)o;
                    if (e.getName().equals(Killaura.mc.thePlayer.getName()) || !(e instanceof EntityPlayer) || e instanceof EntityArmorStand || target != null && !(Killaura.mc.thePlayer.getDistanceToEntity(e) < Killaura.mc.thePlayer.getDistanceToEntity(target)) || !((double)Killaura.mc.thePlayer.getDistanceToEntity(e) < range + preAimRange)) continue;
                    target = (EntityPlayer)e;
                }
                break;
            }
        }
        return target;
    }

    public static int randomNumber(int max, int min) {
        return Math.round((float)min + (float)Math.random() * (float)(max - min));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void RotationModes(Entity target) {
        if (target == null) {
            return;
        }
        Vec3 randomCenter = Rotations.getRandomCenter(target.getEntityBoundingBox());
        Vec3 Center = Rotations.getCenter(target.getEntityBoundingBox());
        Vec3 Center2 = Rotations.getCenter2(target.getEntityBoundingBox());
        float yaw1 = Rotations.getYawToPoint(Center);
        float pitch1 = Rotations.getPitchToPoint(Center);
        float pitch3 = Rotations.getPitchToPoint(Center2);
        float yaw2 = Rotations.getYawToPoint(randomCenter);
        float pitch2 = Rotations.getPitchToPoint(randomCenter);
        switch (((DropdownBox)this.getSetting((String)"RotationMode").getSetting()).curOption) {
            case "Instant": {
                Rotations.setRotation(yaw1, pitch3);
                return;
            }
            case "Smooth": {
                Rotations.setYaw(yaw1, Killaura.randomNumber(33, 14));
                Rotations.setPitch(pitch1, Killaura.randomNumber(20, 8));
                return;
            }
            case "AAC": {
                try {
                    if (Killaura.mc.objectMouseOver.entityHit != null) {
                        Rotations.setYaw(yaw1, 0.0f);
                        Rotations.setPitch(pitch1, 0.0f);
                        if (bots.contains(kTarget) || FriendSystem.isFriendString(kTarget.getName()) || kTarget.getName().equalsIgnoreCase("\u00a76Shop")) {
                            return;
                        }
                    }
                    if (Killaura.mc.objectMouseOver.entityHit != null) return;
                    Rotations.setYaw(yaw2, 90.0f);
                    Rotations.setPitch(pitch2, 180.0f);
                    if (!bots.contains(kTarget) && !FriendSystem.isFriendString(kTarget.getName()) && !kTarget.getName().equalsIgnoreCase("\u00a76Shop")) return;
                    return;
                }
                catch (NullPointerException nullPointerException) {}
                return;
            }
            case "Intave": {
                float[] rota = Rotations.getNewKillAuraRots(Killaura.mc.thePlayer, (EntityLivingBase)target, this.lastYaw, this.lastPitch);
                this.lastYaw = rota[0];
                this.lastPitch = rota[1];
                try {
                    if (Killaura.mc.objectMouseOver.entityHit != null) {
                        if (bots.contains(kTarget) || FriendSystem.isFriendString(kTarget.getName()) || kTarget.getName().equalsIgnoreCase("\u00a76Shop")) {
                            return;
                        }
                        Rotations.setYaw(rota[0], 180.0f);
                        Rotations.setPitch(rota[1], 180.0f);
                    }
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
                try {
                    if (Killaura.mc.objectMouseOver.entityHit != null) return;
                    Rotations.setYaw(rota[0], 180.0f);
                    Rotations.setPitch(rota[1], 180.0f);
                    return;
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
            }
        }
    }

    public static boolean hasTarget() {
        return kTarget != null;
    }

    public void AutoBlock() {
        block21: {
            if (!((CheckBox)this.getSetting((String)"AutoBlock").getSetting()).state || Killaura.mc.thePlayer.getHeldItem() == null || !(Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) break block21;
            switch (((DropdownBox)this.getSetting((String)"AutoBlockMode").getSetting()).curOption) {
                case "Intave": {
                    if (Killaura.mc.thePlayer.isSwingInProgress && Killaura.mc.thePlayer.getHeldItem() != null && Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                        Killaura.mc.gameSettings.keyBindUseItem.pressed = true;
                    }
                }
                case "SmartIntave": {
                    if (!Killaura.mc.thePlayer.isSwingInProgress || Killaura.mc.thePlayer.hurtTime == 0 || Killaura.mc.thePlayer.getHeldItem() == null || !(Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) break;
                    Killaura.mc.gameSettings.keyBindUseItem.pressed = true;
                    break;
                }
                case "NCP": {
                    if (Killaura.mc.thePlayer.getHeldItem() == null || !(Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) break;
                    Killaura.mc.playerController.sendUseItem(Killaura.mc.thePlayer, Killaura.mc.theWorld, Killaura.mc.thePlayer.getHeldItem());
                    break;
                }
                case "Hypixel": {
                    if (Killaura.mc.thePlayer.getHeldItem() == null || !(Killaura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) break;
                    Killaura.mc.thePlayer.setItemInUse(Killaura.mc.thePlayer.getHeldItem(), 100);
                    break;
                }
                case "Other": {
                    if (this.timeAB.hasReached(Killaura.randomNumber(63, 56))) {
                        Killaura.mc.gameSettings.keyBindUseItem.pressed = true;
                        Killaura.mc.gameSettings.keyBindSprint.pressed = false;
                        Killaura.mc.thePlayer.setItemInUse(Killaura.mc.thePlayer.getHeldItem(), Killaura.mc.thePlayer.getHeldItem().getMaxItemUseDuration());
                        Killaura.mc.playerController.sendUseItem(Killaura.mc.thePlayer, Killaura.mc.theWorld, Killaura.mc.thePlayer.getHeldItem());
                        Killaura.mc.gameSettings.keyBindSprint.pressed = false;
                        this.timeAB.reset();
                    }
                    Killaura.mc.gameSettings.keyBindUseItem.pressed = false;
                }
            }
        }
    }

    public boolean smartblock(Entity entity) {
        float yaw = entity.rotationYaw;
        while (yaw > 360.0f) {
            yaw -= 360.0f;
        }
        while (yaw < 0.0f) {
            yaw += 360.0f;
        }
        while (yaw > 360.0f) {
            yaw -= 360.0f;
        }
        while (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return Math.abs(yaw - Killaura.mc.thePlayer.rotationYawHead) >= 130.0f && Math.abs(yaw - Killaura.mc.thePlayer.rotationYawHead) <= 280.0f;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void drawTargetHUD() {
        switch (((DropdownBox)this.getSetting((String)"TargetHUDMode").getSetting()).curOption) {
            case "Novoline": {
                String message;
                double TargetStrength;
                if (!((CheckBox)this.getSetting((String)"TargetHUD").getSetting()).state || kTarget == null || !(kTarget instanceof EntityPlayer) || FriendSystem.isFriendString(kTarget.getName())) break;
                EntityLivingBase target = kTarget;
                if (target != null && target instanceof EntityPlayer || target instanceof EntityAnimal || target instanceof EntityVillager || target instanceof EntityMob) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate((float)ScaledResolution.INSTANCE.getScaledWidth() / 2.0f, (float)ScaledResolution.INSTANCE.getScaledHeight() / 1.8f, 0.0f);
                    switch (((DropdownBox)this.getSetting((String)"TargetHUDColor").getSetting()).curOption) {
                        case "DARK": {
                            Killaura.drawRect(8.0, 4.0, 151.0, 42.0, Killaura.getColor(0, 0, 0, 255));
                            Killaura.drawRect(9.0, 5.0, 150.0, 41.0, Killaura.getColor(40, 40, 40, 255));
                            break;
                        }
                        case "WHITE": {
                            Killaura.drawRect(0.0, 0.0, 150.0, 50.0, Killaura.getColor(255, 255, 255, 32));
                            break;
                        }
                    }
                    Killaura.mc.fontRendererObj.drawStringWithShadow(target.getName(), 46.5f, 7.0f, Killaura.getColor(255, 255, 255, 255));
                    float healthProcent = target.getHealth() / target.getMaxHealth();
                    if (this.lastHealthPercentage != healthProcent) {
                        this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
                    }
                    this.lastHealthPercentage = healthProcent;
                    float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
                    this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
                    Killaura.drawRect(47.0, 18.0, (double)(55.0f + 90.0f * finalHealthPercentage), 28.0, Color.white.getRGB());
                    Killaura.mc.fontRendererObj.drawStringWithShadow(String.valueOf(String.valueOf(Math.round(target.getHealth())) + ".0" + " \u2764"), 47.0f, 31.0f, Color.white.getRGB());
                    double winChance = 0.0;
                    TargetStrength = Killaura.getWeaponStrength(target.getHeldItem());
                    winChance = Killaura.getWeaponStrength(Killaura.mc.thePlayer.getHeldItem()) - TargetStrength;
                    winChance += Killaura.getProtection(Killaura.mc.thePlayer) - Killaura.getProtection(target);
                    message = (winChance += (double)(Killaura.mc.thePlayer.getHealth() - target.getHealth())) == 0.0 ? "You could win" : (winChance < 0.0 ? "You could lose" : "You are going to win");
                    Client.INSTANCE.unicodeBasicFontRenderer.drawString(message, 46.5f - (float)Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(message) + (float)Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(message) / 1.0f, 30.0f, Killaura.getColor(255, 240, 0, 1));
                    GlStateManager.popMatrix();
                }
                Object NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(Killaura.mc.thePlayer.sendQueue.getPlayerInfoMap());
                Iterator itarlor = NetworkMoment.iterator();
                while (itarlor.hasNext()) {
                    Object nextObject = itarlor.next();
                    NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)nextObject;
                    if (Killaura.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) != target) continue;
                    GlStateManager.enableCull();
                    mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                    Gui.drawScaledCustomSizeModalRect(ScaledResolution.INSTANCE.getScaledWidth() / 2 + 10, ScaledResolution.INSTANCE.getScaledHeight() / 2 + 35, 8.0f, 8.0f, 8, 8, 32, 32, 64.0f, 66.0f);
                }
                break;
            }
            case "OldAstolfo": {
                String message;
                double TargetStrength;
                if (!((CheckBox)this.getSetting((String)"TargetHUD").getSetting()).state || kTarget == null || !(kTarget instanceof EntityPlayer) || FriendSystem.isFriendString(kTarget.getName())) break;
                EntityLivingBase target = kTarget;
                if (target != null && target instanceof EntityPlayer || target instanceof EntityAnimal || target instanceof EntityVillager || target instanceof EntityMob) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate((float)ScaledResolution.INSTANCE.getScaledWidth() / 2.0f, (float)ScaledResolution.INSTANCE.getScaledHeight() / 1.8f, 0.0f);
                    switch (((DropdownBox)this.getSetting((String)"TargetHUDColor").getSetting()).curOption) {
                        case "DARK": {
                            Killaura.drawRect(0.0, 0.0, 150.0, 50.0, Killaura.getColor(0, 0, 0, 160));
                            break;
                        }
                        case "WHITE": {
                            Killaura.drawRect(0.0, 0.0, 150.0, 50.0, Killaura.getColor(255, 255, 255, 32));
                            break;
                        }
                    }
                    Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(target.getName(), 46.5f, 3.0f, Killaura.getColor(255, 240, 0, 255));
                    float healthProcent = target.getHealth() / target.getMaxHealth();
                    if (this.lastHealthPercentage != healthProcent) {
                        this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
                    }
                    this.lastHealthPercentage = healthProcent;
                    float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
                    this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
                    Killaura.drawRect(47.0, 18.0, (double)(55.0f + 90.0f * finalHealthPercentage), 28.0, Color.HSBtoRGB(Math.min(-finalHealthPercentage + 0.3f, 0.0f), 1.0f, 1.0f));
                    double winChance = 0.0;
                    TargetStrength = Killaura.getWeaponStrength(target.getHeldItem());
                    winChance = Killaura.getWeaponStrength(Killaura.mc.thePlayer.getHeldItem()) - TargetStrength;
                    winChance += Killaura.getProtection(Killaura.mc.thePlayer) - Killaura.getProtection(target);
                    message = (winChance += (double)(Killaura.mc.thePlayer.getHealth() - target.getHealth())) == 0.0 ? "You could win" : (winChance < 0.0 ? "You could lose" : "You are going to win");
                    Client.INSTANCE.unicodeBasicFontRenderer.drawString(message, 46.5f - (float)Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(message) + (float)Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(message) / 1.0f, 30.0f, Killaura.getColor(255, 240, 0, 1));
                    GlStateManager.popMatrix();
                }
                if (((CheckBox)this.getSetting((String)"RedColoring").getSetting()).state && Killaura.kTarget.hurtTime != 0) {
                    GL11.glColor4d((double)255.0, (double)0.0, (double)0.0, (double)255.0);
                }
                Object NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(Killaura.mc.thePlayer.sendQueue.getPlayerInfoMap());
                Iterator itarlor = NetworkMoment.iterator();
                while (itarlor.hasNext()) {
                    Object nextObject = itarlor.next();
                    NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)nextObject;
                    if (Killaura.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) != target) continue;
                    GlStateManager.enableCull();
                    mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                    Gui.drawScaledCustomSizeModalRect(ScaledResolution.INSTANCE.getScaledWidth() / 2 + 10, ScaledResolution.INSTANCE.getScaledHeight() / 2 + 35, 8.0f, 8.0f, 8, 8, 32, 32, 64.0f, 66.0f);
                }
                break;
            }
        }
        switch (((DropdownBox)this.getSetting((String)"TargetHUDMode").getSetting()).curOption) {
            case "Fanta": {
                if (!((CheckBox)this.getSetting((String)"TargetHUD").getSetting()).state || kTarget == null || !(kTarget instanceof EntityPlayer) || FriendSystem.isFriendString(kTarget.getName())) return;
                EntityLivingBase target = kTarget;
                ScaledResolution s = new ScaledResolution(mc);
                Color backgroundColor = new Color(0, 0, 0, 120);
                Color emptyBarColor = new Color(59, 59, 59, 160);
                Color healthBarColor = Color.green;
                Color distBarColor = new Color(20, 81, 208);
                int left = s.getScaledWidth() / 2 + 5;
                int right2 = 80;
                int right = s.getScaledWidth() / 2 + right2;
                int right3 = 80 + Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(target.getName()) / 2 - 15;
                int top = s.getScaledHeight() / 2 - 25;
                int bottom = s.getScaledHeight() / 2 + 25;
                float curTargetHealth = ((EntityPlayer)target).getHealth();
                float maxTargetHealth = ((EntityPlayer)target).getMaxHealth();
                float healthProcent = kTarget.getHealth() / kTarget.getMaxHealth();
                if (this.lastHealthPercentage != healthProcent) {
                    this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
                }
                this.lastHealthPercentage = healthProcent;
                float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
                this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
                float calculatedHealth = finalHealthPercentage;
                int rectRight = right + Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(target.getName()) / 2 - 5;
                float healthPos = calculatedHealth * (float)right3;
                Client.blurHelper.blur2(left, top, right + Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(target.getName()) - 5, bottom, 10.0f);
                Killaura.drawRect(left, top, right + Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(target.getName()) - 5, bottom, new Color(25, 25, 25, 205));
                if (this.lastHealthPercentage != healthProcent) {
                    this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
                }
                this.lastHealthPercentage = healthProcent;
                this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                List NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(Killaura.mc.thePlayer.sendQueue.getPlayerInfoMap());
                Iterator itarlor = NetworkMoment.iterator();
                while (true) {
                    if (!itarlor.hasNext()) {
                        Killaura.drawRect(left + 5, bottom - 13, (float)left + healthPos + (float)(Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(target.getName()) / 2), bottom - 5, healthBarColor);
                        Client.INSTANCE.unicodeBasicFontRenderer5.drawStringWithShadow(target.getName(), left + 40, top + 4, Color.WHITE.getRGB());
                        Client.INSTANCE.unicodeBasicFontRenderer5.drawStringWithShadow("Health: " + Math.round(curTargetHealth), left + 40, top + 20, Color.WHITE.getRGB());
                        return;
                    }
                    Object nextObject = itarlor.next();
                    NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)nextObject;
                    if (Killaura.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) != target) continue;
                    GlStateManager.enableCull();
                    mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                    Gui.drawScaledCustomSizeModalRect(s.getScaledWidth() / 2 + 10, s.getScaledHeight() / 2 - 22, 8.0f, 8.0f, 8, 8, 32, 32, 64.0f, 66.0f);
                }
            }
        }
    }

    public void drawFace(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer e) {
        mc.getTextureManager().bindTexture(e.getLocationSkin());
        GL11.glEnable((int)3042);
        Gui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
        GL11.glDisable((int)3042);
    }

    public int Color(int health) {
        if (health == 20) {
            return -13637079;
        }
        if (health == 19) {
            return -12522967;
        }
        if (health == 18) {
            return -9246167;
        }
        if (health == 17) {
            return -7673303;
        }
        if (health == 16) {
            return -6165975;
        }
        if (health == 15) {
            return -4658647;
        }
        if (health == 14) {
            return -3675607;
        }
        if (health == 13) {
            return -2889175;
        }
        if (health == 12) {
            return -1775063;
        }
        if (health == 11) {
            return -1775063;
        }
        if (health == 10) {
            return -1382615;
        }
        if (health == 9) {
            return -1386967;
        }
        if (health == 8) {
            return -1392343;
        }
        if (health == 7) {
            return -1394647;
        }
        if (health == 6) {
            return -1398999;
        }
        if (health == 5) {
            return -1405143;
        }
        if (health == 4) {
            return -1410263;
        }
        if (health == 3) {
            return -1415639;
        }
        if (health == 2) {
            return -1417687;
        }
        if (health == 1) {
            return -1425367;
        }
        if (health < 1) {
            return -261373;
        }
        return -16777216;
    }

    private static double getProtection(EntityLivingBase target) {
        double protection = 0.0;
        int i = 0;
        while (i <= 3) {
            ItemStack stack = target.getCurrentArmor(i);
            if (stack != null) {
                if (stack.getItem() instanceof ItemArmor) {
                    ItemArmor armor = (ItemArmor)stack.getItem();
                    protection += (double)armor.damageReduceAmount;
                }
                protection += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.25;
            }
            ++i;
        }
        return protection;
    }

    private static double getWeaponStrength(ItemStack stack) {
        double damage = 0.0;
        if (stack != null) {
            if (stack.getItem() instanceof ItemSword) {
                ItemSword sword = (ItemSword)stack.getItem();
                damage += (double)sword.getDamageVsEntity();
            }
            if (stack.getItem() instanceof ItemTool) {
                ItemTool tool = (ItemTool)stack.getItem();
                damage += (double)tool.getToolMaterial().getDamageVsEntity();
            }
            damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25;
        }
        return damage;
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0).endVertex();
        worldrenderer.pos(right, bottom, 0.0).endVertex();
        worldrenderer.pos(right, top, 0.0).endVertex();
        worldrenderer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static int getColor(int red, int green, int blue, int opacity) {
        int color = MathHelper.clamp_int(opacity, 0, 255) << 24;
        color |= MathHelper.clamp_int(red, 0, 255) << 16;
        color |= MathHelper.clamp_int(green, 0, 255) << 8;
        return color |= MathHelper.clamp_int(blue, 0, 255);
    }

    private static void renderPlayer(int posX, int posY, int scale, EntityLivingBase player) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.translate(posX, posY, 50.0f);
        GlStateManager.scale(-scale, scale, scale);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        RenderHelper.enableStandardItemLighting();
        player.rotationYawHead = player.rotationYaw;
        player.prevRotationYawHead = player.rotationYaw;
        GlStateManager.translate(0.0f, 0.0f, 0.0f);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0f);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntityWithPosYaw(player, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        rendermanager.setRenderShadow(true);
        player.renderYawOffset = player.rotationYaw;
        player.prevRotationYawHead = player.rotationYaw;
        player.rotationYawHead = player.rotationYaw;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public static void fillRect(double x, double y, double width, double height, Color color) {
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glVertex2d((double)x, (double)y);
    }

    public static void drawRectRound(int x, int y, int width, int height, int diameter, Color color) {
        Killaura.fillRect(x, y + diameter, width, height - diameter * 2, color);
        Killaura.fillRect(x + 5, y, width - diameter * 2, height, color);
        GL11.glBegin((int)6);
        Killaura.drawCircle(x + diameter, y + diameter, diameter, 0, 360, color);
        Killaura.drawCircle(x + width - diameter, y + diameter, diameter, 0, 360, color);
        Killaura.drawCircle(x + diameter, y + height - diameter, diameter, 0, 360, color);
        Killaura.drawCircle(x + width - diameter, y + height - diameter, diameter, 0, 360, color);
    }

    private static void drawCircle(float x, float y, float diameter, int start, int stop, Color color) {
        double twicePi = Math.PI * 2;
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        int i = start;
        while (i <= stop) {
            GL11.glVertex2d((double)((double)x + (double)diameter * Math.sin((double)i * (Math.PI * 2) / (double)stop)), (double)((double)y + (double)diameter * Math.cos((double)i * (Math.PI * 2) / (double)stop)));
            ++i;
        }
    }

    boolean isBot(EntityPlayer player) {
        if (!this.isInTablist(player)) {
            return true;
        }
        if (!this.madeSound.contains(player) && ((CheckBox)this.getSetting((String)"SoundCheck").getSetting()).state) {
            return true;
        }
        return this.invalidName(player);
    }

    boolean isInTablist(EntityPlayer player) {
        if (Minecraft.getMinecraft().isSingleplayer()) {
            return false;
        }
        for (NetworkPlayerInfo playerInfo : Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()) {
            if (!playerInfo.getGameProfile().getName().equalsIgnoreCase(player.getName())) continue;
            return true;
        }
        return false;
    }

    boolean invalidName(Entity e) {
        if (e.getName().contains("-")) {
            return true;
        }
        if (e.getName().contains("/")) {
            return true;
        }
        if (e.getName().contains("|")) {
            return true;
        }
        if (e.getName().contains("<")) {
            return true;
        }
        if (e.getName().contains(">")) {
            return true;
        }
        return e.getName().contains("\u0e22\u0e07");
    }

    public static void drawRect(float g, float h, float i, float j, Color col2) {
        GL11.glColor4f((float)((float)col2.getRed() / 255.0f), (float)((float)col2.getGreen() / 255.0f), (float)((float)col2.getBlue() / 255.0f), (float)((float)col2.getAlpha() / 255.0f));
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)i, (double)h);
        GL11.glVertex2d((double)g, (double)h);
        GL11.glVertex2d((double)g, (double)j);
        GL11.glVertex2d((double)i, (double)j);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }

    public void onPre(EventPreMotion pre) {
        if (kTarget != null) {
            facing = Killaura.getEntityRotations(Killaura.mc.thePlayer, kTarget);
            pre.setYaw(yaww);
            pre.setPitch(pitchh);
            yaww = Killaura.interpolateRotation(yaww, facing[0], 180.0f);
            pitchh = Killaura.interpolateRotation(pitchh, facing[1], 180.0f);
        }
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

    public static float[] getEntityRotations(EntityPlayerSP player, EntityLivingBase target) {
        double posX = target.posX - player.posX;
        double posY = target.posY + (double)target.getEyeHeight() - (player.posY + (double)player.getEyeHeight());
        float RotationY2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 150.0, 180.0);
        double posZ = target.posZ - player.posZ;
        double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
        float yaw = (float)(Math.atan2(posZ, posX) * (double)RotationY2 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(posY, var14) * (double)RotationY2 / Math.PI));
        float f2 = Killaura.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        return new float[]{yaw, pitch};
    }

    public void drawTargetESP(EntityPlayer target, float pt) {
        double x = target.lastTickPosX + (target.posX - target.lastTickPosX) * (double)pt - Killaura.mc.getRenderManager().viewerPosX;
        double y = target.lastTickPosY + (target.posY - target.lastTickPosY) * (double)pt - Killaura.mc.getRenderManager().viewerPosY;
        double z = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * (double)pt - Killaura.mc.getRenderManager().viewerPosZ;
        int[] rgb = Colors.getRGB(this.getColor2());
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GL11.glLineWidth((float)3.0f);
        GL11.glShadeModel((int)7425);
        GL11.glDisable((int)2884);
        double size = (double)target.width * 1.2;
        float factor = (float)Math.sin((float)System.nanoTime() / 3.0E8f);
        GL11.glTranslatef((float)0.0f, (float)factor, (float)0.0f);
        GL11.glBegin((int)5);
        int j = 0;
        while (j < 361) {
            RenderUtil.color(Colors.getColor(rgb[0], rgb[1], rgb[2], 200));
            double x1 = x + Math.cos(Math.toRadians(j)) * size;
            double z1 = z - Math.sin(Math.toRadians(j)) * size;
            GL11.glVertex3d((double)x1, (double)(y + 1.0), (double)z1);
            RenderUtil.color(Colors.getColor(rgb[0], rgb[1], rgb[2], 0));
            GL11.glVertex3d((double)x1, (double)(y + 1.0 + (double)(factor * 0.4f)), (double)z1);
            ++j;
        }
        GL11.glEnd();
        GL11.glBegin((int)2);
        j = 0;
        while (j < 361) {
            RenderUtil.color(Colors.getColor(rgb[0], rgb[1], rgb[2], 1));
            GL11.glVertex3d((double)(x + Math.cos(Math.toRadians(j)) * size), (double)(y + 1.0), (double)(z - Math.sin(Math.toRadians(j)) * size));
            ++j;
        }
        GL11.glEnd();
        GlStateManager.enableAlpha();
        GL11.glShadeModel((int)7424);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)2884);
        GlStateManager.enableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
        GlStateManager.popMatrix();
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

