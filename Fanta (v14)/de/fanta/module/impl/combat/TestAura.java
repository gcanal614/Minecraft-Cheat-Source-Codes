/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.module.impl.combat;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventClickMouse;
import de.fanta.events.listeners.EventPreMotion;
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
import de.fanta.utils.RandomUtil;
import de.fanta.utils.RenderUtil;
import de.fanta.utils.Rotations;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.particle.EntityParticleEmitter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class TestAura
extends Module {
    TimeUtil timeUtil = new TimeUtil();
    public static EntityLivingBase target;
    private float lastYaw;
    private float lastPitch;
    public static double range;
    public static double preaimrange;
    public static double minCPS;
    public static double maxCPS;
    public static double cracks;
    public static double heursticsyaw;
    public static double heursticsyaw2;
    public static double heursticspitch;
    public static double heursticspitch2;
    public static double rotSpeed;
    public static double randomrotSpeed;
    public static double randomrotSpeed2;
    private boolean hasRotation = false;
    public static ArrayList<Entity> bots;
    public double current;
    public double x;
    public double y;
    public double dragX;
    public double dragY;
    public boolean dragging;
    float lostHealthPercentage = 0.0f;
    float lastHealthPercentage = 0.0f;

    static {
        bots = new ArrayList();
    }

    public TestAura() {
        super("TestAura", 0, Module.Type.Combat, Color.green);
        this.settings.add(new Setting("Range", new Slider(1.0, 8.0, 0.1, 4.0)));
        this.settings.add(new Setting("PreAimRange", new Slider(0.0, 5.0, 0.1, 2.0)));
        this.settings.add(new Setting("CPS-Max", new Slider(0.0, 20.0, 1.0, 10.0)));
        this.settings.add(new Setting("CPS-Min", new Slider(0.0, 20.0, 1.0, 2.0)));
        this.settings.add(new Setting("Cracks", new Slider(0.0, 1000.0, 1.0, 15.0)));
        this.settings.add(new Setting("LockView", new CheckBox(false)));
        this.settings.add(new Setting("TargetESP", new CheckBox(false)));
        this.settings.add(new Setting("Heuristics", new CheckBox(false)));
        this.settings.add(new Setting("HeuristicsYaw", new Slider(85.0, 95.0, 1.0, 90.0)));
        this.settings.add(new Setting("HeuristicsYaw2", new Slider(85.0, 95.0, 1.0, 90.0)));
        this.settings.add(new Setting("HeuristicsPitch", new Slider(170.0, 190.0, 1.0, 180.0)));
        this.settings.add(new Setting("HeuristicsPitch2", new Slider(170.0, 190.0, 1.0, 180.0)));
        this.settings.add(new Setting("RandomRotSpeed", new CheckBox(false)));
        this.settings.add(new Setting("Speed", new Slider(0.0, 180.0, 1.0, 75.0)));
        this.settings.add(new Setting("Speed1", new Slider(0.0, 180.0, 1.0, 80.0)));
        this.settings.add(new Setting("MoveFix", new CheckBox(false)));
        this.settings.add(new Setting("SilentMoveFix", new CheckBox(false)));
        this.settings.add(new Setting("GCD-Fix", new CheckBox(false)));
        this.settings.add(new Setting("ABS-GCD-Fix", new CheckBox(false)));
        this.settings.add(new Setting("JumpFix", new CheckBox(false)));
        this.settings.add(new Setting("KeepSprint", new CheckBox(false)));
        this.settings.add(new Setting("Autoblock", new CheckBox(false)));
        this.settings.add(new Setting("NewPVPSystem", new CheckBox(false)));
        this.settings.add(new Setting("Antibot", new CheckBox(false)));
        this.settings.add(new Setting("Raytrace", new CheckBox(false)));
        this.settings.add(new Setting("Intave14Fix", new CheckBox(false)));
        this.settings.add(new Setting("TargetHUD", new CheckBox(false)));
        this.settings.add(new Setting("TargetESP", new CheckBox(false)));
        this.settings.add(new Setting("Rotations", new CheckBox(true)));
        this.settings.add(new Setting("ThroughWalls", new CheckBox(true)));
        this.settings.add(new Setting("AttackType", new DropdownBox("Legit", new String[]{"Legit", "Packet"})));
        this.settings.add(new Setting("AutoBlock", new DropdownBox("Intave", new String[]{"Intave", "SmartIntave"})));
        this.settings.add(new Setting("Modes", new DropdownBox("Nearest", new String[]{"Single", "Nearest", "Switch"})));
        this.settings.add(new Setting("TargetHUDM", new DropdownBox("Novoline", new String[]{"Fanta", "OldAstolfo", "Novoline", "WindowsXP"})));
        this.settings.add(new Setting("Color", new ColorValue(Color.white.getRGB())));
    }

    @Override
    public void onEvent(Event e) {
        block30: {
            block33: {
                block31: {
                    range = ((Slider)this.getSetting((String)"Range").getSetting()).curValue;
                    preaimrange = ((Slider)this.getSetting((String)"PreAimRange").getSetting()).curValue;
                    maxCPS = ((Slider)this.getSetting((String)"CPS-Max").getSetting()).curValue;
                    minCPS = ((Slider)this.getSetting((String)"CPS-Min").getSetting()).curValue;
                    cracks = ((Slider)this.getSetting((String)"CPS-Min").getSetting()).curValue;
                    heursticspitch = ((Slider)this.getSetting((String)"HeuristicsPitch").getSetting()).curValue;
                    heursticspitch2 = ((Slider)this.getSetting((String)"HeuristicsPitch2").getSetting()).curValue;
                    heursticsyaw = ((Slider)this.getSetting((String)"HeuristicsYaw").getSetting()).curValue;
                    heursticsyaw2 = ((Slider)this.getSetting((String)"HeuristicsYaw2").getSetting()).curValue;
                    randomrotSpeed = ((Slider)this.getSetting((String)"Speed").getSetting()).curValue;
                    randomrotSpeed2 = ((Slider)this.getSetting((String)"Speed1").getSetting()).curValue;
                    if (e instanceof EventPreMotion) {
                        if (target == null) {
                            this.current = 0.7;
                        }
                        if (((CheckBox)this.getSetting((String)"Antibot").getSetting()).state) {
                            for (Entity entity : TestAura.mc.theWorld.getLoadedEntityList()) {
                                if (!(entity instanceof EntityPlayer) || !this.isBot((EntityPlayer)entity) && !((EntityPlayer)entity).isInvisible() || entity == TestAura.mc.thePlayer) continue;
                                bots.add((EntityPlayer)entity);
                                TestAura.mc.theWorld.removeEntity(entity);
                            }
                        }
                        if (((CheckBox)this.getSetting((String)"Autoblock").getSetting()).state && ((CheckBox)this.getSetting((String)"Autoblock").getSetting()).state && !TestAura.mc.thePlayer.isSwingInProgress && TestAura.mc.thePlayer.getHeldItem() != null && TestAura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && target == null) {
                            TestAura.mc.gameSettings.keyBindUseItem.pressed = false;
                        }
                        target = (EntityLivingBase)this.modes();
                        if (TestAura.mc.objectMouseOver != null && target != null) {
                            this.hasRotation = true;
                            if (((CheckBox)this.getSetting((String)"Rotations").getSetting()).state) {
                                if (!((CheckBox)this.getSetting((String)"ThroughWalls").getSetting()).state && !TestAura.mc.thePlayer.canEntityBeSeen(target) || bots.contains(target) || FriendSystem.isFriendString(target.getName()) || target.getName().equalsIgnoreCase("\u00a76Shop")) {
                                    return;
                                }
                                if (((CheckBox)this.getSetting((String)"LockView").getSetting()).state) {
                                    TestAura.mc.thePlayer.rotationYaw = Rotations.yaw;
                                    TestAura.mc.thePlayer.rotationPitch = Rotations.pitch;
                                } else {
                                    ((EventPreMotion)e).setPitch(Rotations.pitch);
                                    ((EventPreMotion)e).setYaw(Rotations.yaw);
                                }
                            }
                            this.rotationModes(target);
                        } else if (((CheckBox)this.getSetting((String)"Intave14Fix").getSetting()).state && this.hasRotation) {
                            this.resetRotation(Rotations.yaw);
                            this.hasRotation = false;
                        }
                    }
                    if (e instanceof EventRender3D && ((CheckBox)this.getSetting((String)"TargetESP").getSetting()).state) {
                        this.drawTargetESP((EntityPlayer)target, TestAura.mc.timer.renderPartialTicks);
                    }
                    if (e instanceof EventRender2D && e.isPre() && target != null && ((CheckBox)this.getSetting((String)"TargetHUD").getSetting()).state) {
                        this.drawTargetHud(target);
                    }
                    if (!(e instanceof EventClickMouse)) break block30;
                    float CPS = (float)MathHelper.getRandomDoubleInRange(new Random(), minCPS, maxCPS);
                    if (target == null) break block30;
                    if (((CheckBox)this.getSetting((String)"NewPVPSystem").getSetting()).state) break block31;
                    switch (((DropdownBox)this.getSetting((String)"AttackType").getSetting()).curOption) {
                        case "Legit": {
                            if (!((CheckBox)this.getSetting((String)"ThroughWalls").getSetting()).state && !TestAura.mc.thePlayer.canEntityBeSeen(target) || bots.contains(target) || FriendSystem.isFriendString(target.getName()) || target.getName().equalsIgnoreCase("\u00a76Shop")) {
                                return;
                            }
                            if (!this.timeUtil.hasReached((long)(1000.0f / CPS))) break;
                            mc.clickMouse();
                            this.timeUtil.reset();
                            break;
                        }
                        case "Packet": {
                            if (!((CheckBox)this.getSetting((String)"ThroughWalls").getSetting()).state && !TestAura.mc.thePlayer.canEntityBeSeen(target) || bots.contains(this.getPlayer()) || FriendSystem.isFriendString(target.getName()) || target.getName().equalsIgnoreCase("\u00a76Shop")) {
                                return;
                            }
                            if (!this.timeUtil.hasReached((long)(1000.0f / CPS))) break;
                            TestAura.mc.thePlayer.swingItem();
                            TestAura.mc.playerController.attackEntity(TestAura.mc.thePlayer, target);
                            this.timeUtil.reset();
                        }
                    }
                    if (this.timeUtil.hasReached((long)(1000.0f / CPS))) {
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
                        this.timeUtil.reset();
                    }
                    break block33;
                }
                if (TestAura.mc.thePlayer.ticksExisted % 12 == 0) {
                    TestAura.mc.playerController.attackEntity(TestAura.mc.thePlayer, target);
                    TestAura.mc.thePlayer.swingItem();
                }
            }
            this.AutoBlock();
        }
    }

    public static int randomNumber(int max, int min) {
        return Math.round((float)min + (float)Math.random() * (float)(max - min));
    }

    public Entity modes() {
        EntityPlayer target = null;
        switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
            case "Switch": {
                for (Object o : TestAura.mc.theWorld.loadedEntityList) {
                    Entity e = (Entity)o;
                    if (e.getName().equals(TestAura.mc.thePlayer.getName()) || !(e instanceof EntityPlayer) || e instanceof EntityArmorStand) continue;
                    EntityPlayer player = (EntityPlayer)e;
                    if (target == null || player.hurtTime < target.hurtTime) {
                        if (!((double)TestAura.mc.thePlayer.getDistanceToEntity(e) < range + preaimrange)) continue;
                        target = player;
                        continue;
                    }
                    if (player.hurtTime != target.hurtTime || !(TestAura.mc.thePlayer.getDistanceToEntity(player) < TestAura.mc.thePlayer.getDistanceToEntity(target)) || !((double)TestAura.mc.thePlayer.getDistanceToEntity(e) < range + preaimrange)) continue;
                    target = player;
                }
                return target;
            }
            case "Single": {
                for (Object o : TestAura.mc.theWorld.loadedEntityList) {
                    Entity e = (Entity)o;
                    if (e.getName().equals(TestAura.mc.thePlayer.getName()) || !(e instanceof EntityPlayer) || e instanceof EntityArmorStand || target != null && !(TestAura.mc.thePlayer.getDistanceToEntity(e) <= TestAura.mc.thePlayer.getDistanceToEntity(target) + 0.0f) || !((double)TestAura.mc.thePlayer.getDistanceToEntity(e) < range + preaimrange)) continue;
                    target = (EntityPlayer)e;
                }
                return target;
            }
            case "Nearest": {
                for (Object o : TestAura.mc.theWorld.loadedEntityList) {
                    Entity e = (Entity)o;
                    if (e.getName().equals(TestAura.mc.thePlayer.getName()) || !(e instanceof EntityPlayer) || e instanceof EntityArmorStand || target != null && !(TestAura.mc.thePlayer.getDistanceToEntity(e) < TestAura.mc.thePlayer.getDistanceToEntity(target)) || !((double)TestAura.mc.thePlayer.getDistanceToEntity(e) < range + preaimrange)) continue;
                    target = (EntityPlayer)e;
                }
                break;
            }
        }
        return target;
    }

    public void rotationModes(Entity target) {
        if (((CheckBox)this.getSetting((String)"GCD-Fix").getSetting()).state) {
            if (((CheckBox)this.getSetting((String)"Heuristics").getSetting()).state) {
                float rotationSpeed;
                float[] rota = TestAura.getNewKillAuraRotsHeuristicsGCD(TestAura.mc.thePlayer, (EntityLivingBase)target, this.lastYaw, this.lastPitch);
                this.lastYaw = rota[0];
                this.lastPitch = rota[1];
                if (TestAura.mc.objectMouseOver.entityHit != null) {
                    if (((CheckBox)this.getSetting((String)"RandomRotSpeed").getSetting()).state) {
                        rotationSpeed = (float)MathHelper.getRandomDoubleInRange(new Random(), randomrotSpeed, randomrotSpeed2);
                        Rotations.setYaw(rota[0], rotationSpeed);
                        Rotations.setPitch(rota[1], rotationSpeed);
                    } else {
                        Rotations.setYaw(rota[0], 180.0f);
                        Rotations.setPitch(rota[1], 180.0f);
                    }
                }
                if (TestAura.mc.objectMouseOver.entityHit == null) {
                    rotationSpeed = (float)MathHelper.getRandomDoubleInRange(new Random(), randomrotSpeed, randomrotSpeed2);
                    Rotations.setYaw(rota[0], rotationSpeed);
                    Rotations.setPitch(rota[1], rotationSpeed);
                }
            } else {
                float[] rota = TestAura.getNewKillAuraRotsGCD(TestAura.mc.thePlayer, (EntityLivingBase)target, this.lastYaw, this.lastPitch);
                this.lastYaw = rota[0];
                this.lastPitch = rota[1];
                if (TestAura.mc.objectMouseOver.entityHit != null) {
                    if (((CheckBox)this.getSetting((String)"RotSpeed").getSetting()).state) {
                        Rotations.setYaw(rota[0], (float)rotSpeed);
                        Rotations.setPitch(rota[1], (float)rotSpeed);
                    } else if (((CheckBox)this.getSetting((String)"RandomRotSpeed").getSetting()).state) {
                        float rotationSpeed = (float)MathHelper.getRandomDoubleInRange(new Random(), randomrotSpeed, randomrotSpeed2);
                        Rotations.setYaw(rota[0], rotationSpeed);
                        Rotations.setPitch(rota[1], rotationSpeed);
                    } else {
                        Rotations.setYaw(rota[0], 180.0f);
                        Rotations.setPitch(rota[1], 180.0f);
                    }
                }
                if (TestAura.mc.objectMouseOver.entityHit == null) {
                    Rotations.setYaw(rota[0], 180.0f);
                    Rotations.setPitch(rota[1], 180.0f);
                }
            }
        } else if (((CheckBox)this.getSetting((String)"ABS-GCD-Fix").getSetting()).state) {
            if (((CheckBox)this.getSetting((String)"Heuristics").getSetting()).state) {
                float rotationSpeed;
                float[] rota = TestAura.getNewKillAuraRotsHeuristicsGCDABS(TestAura.mc.thePlayer, (EntityLivingBase)target, this.lastYaw, this.lastPitch);
                this.lastYaw = rota[0];
                this.lastPitch = rota[1];
                if (TestAura.mc.objectMouseOver.entityHit != null) {
                    if (((CheckBox)this.getSetting((String)"RandomRotSpeed").getSetting()).state) {
                        rotationSpeed = (float)MathHelper.getRandomDoubleInRange(new Random(), randomrotSpeed, randomrotSpeed2);
                        Rotations.setYaw(rota[0], rotationSpeed);
                        Rotations.setPitch(rota[1], rotationSpeed);
                    } else {
                        Rotations.setYaw(rota[0], 180.0f);
                        Rotations.setPitch(rota[1], 180.0f);
                    }
                }
                if (TestAura.mc.objectMouseOver.entityHit == null) {
                    rotationSpeed = (float)MathHelper.getRandomDoubleInRange(new Random(), randomrotSpeed, randomrotSpeed2);
                    Rotations.setYaw(rota[0], rotationSpeed);
                    Rotations.setPitch(rota[1], rotationSpeed);
                }
            } else {
                float[] rota = TestAura.getNewKillAuraRotsGCD(TestAura.mc.thePlayer, (EntityLivingBase)target, this.lastYaw, this.lastPitch);
                this.lastYaw = rota[0];
                this.lastPitch = rota[1];
                if (TestAura.mc.objectMouseOver.entityHit != null) {
                    if (((CheckBox)this.getSetting((String)"RotSpeed").getSetting()).state) {
                        Rotations.setYaw(rota[0], (float)rotSpeed);
                        Rotations.setPitch(rota[1], (float)rotSpeed);
                    } else if (((CheckBox)this.getSetting((String)"RandomRotSpeed").getSetting()).state) {
                        float rotationSpeed = (float)MathHelper.getRandomDoubleInRange(new Random(), randomrotSpeed, randomrotSpeed2);
                        Rotations.setYaw(rota[0], rotationSpeed);
                        Rotations.setPitch(rota[1], rotationSpeed);
                    } else {
                        Rotations.setYaw(rota[0], 180.0f);
                        Rotations.setPitch(rota[1], 180.0f);
                    }
                }
                if (TestAura.mc.objectMouseOver.entityHit == null) {
                    Rotations.setYaw(rota[0], 180.0f);
                    Rotations.setPitch(rota[1], 180.0f);
                }
            }
        } else if (((CheckBox)this.getSetting((String)"Heuristics").getSetting()).state) {
            float rotationSpeed;
            float[] rota = TestAura.getNewKillAuraRotsHeuristics(TestAura.mc.thePlayer, (EntityLivingBase)target, this.lastYaw, this.lastPitch);
            this.lastYaw = rota[0];
            this.lastPitch = rota[1];
            if (TestAura.mc.objectMouseOver.entityHit != null) {
                if (((CheckBox)this.getSetting((String)"RandomRotSpeed").getSetting()).state) {
                    rotationSpeed = (float)MathHelper.getRandomDoubleInRange(new Random(), randomrotSpeed, randomrotSpeed2);
                    Rotations.setYaw(rota[0], rotationSpeed);
                    Rotations.setPitch(rota[1], rotationSpeed);
                } else {
                    Rotations.setYaw(rota[0], 180.0f);
                    Rotations.setPitch(rota[1], 180.0f);
                }
            }
            if (TestAura.mc.objectMouseOver.entityHit == null) {
                rotationSpeed = (float)MathHelper.getRandomDoubleInRange(new Random(), randomrotSpeed, randomrotSpeed2);
                Rotations.setYaw(rota[0], rotationSpeed);
                Rotations.setPitch(rota[1], rotationSpeed);
            }
        } else {
            float[] rota = TestAura.getNewKillAuraRots(TestAura.mc.thePlayer, (EntityLivingBase)target, this.lastYaw, this.lastPitch);
            this.lastYaw = rota[0];
            this.lastPitch = rota[1];
            if (TestAura.mc.objectMouseOver.entityHit != null) {
                if (((CheckBox)this.getSetting((String)"RotSpeed").getSetting()).state) {
                    Rotations.setYaw(rota[0], (float)rotSpeed);
                    Rotations.setPitch(rota[1], (float)rotSpeed);
                } else if (((CheckBox)this.getSetting((String)"RandomRotSpeed").getSetting()).state) {
                    float rotationSpeed = (float)MathHelper.getRandomDoubleInRange(new Random(), randomrotSpeed, randomrotSpeed2);
                    Rotations.setYaw(rota[0], rotationSpeed);
                    Rotations.setPitch(rota[1], rotationSpeed);
                } else {
                    Rotations.setYaw(rota[0], 180.0f);
                    Rotations.setPitch(rota[1], 180.0f);
                }
            }
            if (TestAura.mc.objectMouseOver.entityHit == null) {
                Rotations.setYaw(rota[0], 180.0f);
                Rotations.setPitch(rota[1], 180.0f);
            }
        }
    }

    public void drawTargetESP(EntityPlayer target, float pt) {
        double x = target.lastTickPosX + (target.posX - target.lastTickPosX) * (double)pt - TestAura.mc.getRenderManager().viewerPosX;
        double y = target.lastTickPosY + (target.posY - target.lastTickPosY) * (double)pt - TestAura.mc.getRenderManager().viewerPosY;
        double z = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * (double)pt - TestAura.mc.getRenderManager().viewerPosZ;
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

    @Override
    public void onEnable() {
        this.hasRotation = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        bots.clear();
        if (this.hasRotation) {
            this.resetRotation(Rotations.yaw);
        }
        super.onDisable();
    }

    public int getColor2() {
        try {
            return ((ColorValue)this.getSetting((String)"Color").getSetting()).color;
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }

    public static float[] getNewKillAuraRots(EntityPlayerSP player, EntityLivingBase target, float currentYaw, float currentPitch) {
        Vec3 positionEyes = player.getPositionEyes(1.0f);
        float f11 = target.getCollisionBorderSize();
        double ex = MathHelper.clamp_double(positionEyes.xCoord, target.getEntityBoundingBox().minX - (double)f11, target.getEntityBoundingBox().maxX + (double)f11);
        double ey = MathHelper.clamp_double(positionEyes.yCoord, target.getEntityBoundingBox().minY - (double)f11, target.getEntityBoundingBox().maxY + (double)f11);
        double ez = MathHelper.clamp_double(positionEyes.zCoord, target.getEntityBoundingBox().minZ - (double)f11, target.getEntityBoundingBox().maxZ + (double)f11);
        double x = ex - player.posX;
        double y = ey - (player.posY + (double)player.getEyeHeight());
        double z = ez - player.posZ;
        float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - 90.0);
        float calcPitch = (float)(-(MathHelper.func_181159_b(y, MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
        float yawSpeed = 180.0f;
        float pitchSpeed = 180.0f;
        float yaw = TestAura.updateRotation(currentYaw, calcYaw, yawSpeed);
        float pitch = TestAura.updateRotation(currentPitch, calcPitch, pitchSpeed);
        double diffYaw = MathHelper.wrapAngleTo180_float(calcYaw - currentYaw);
        double diffPitch = MathHelper.wrapAngleTo180_float(calcPitch - currentPitch);
        if (!((double)(-yawSpeed) <= diffYaw && diffYaw <= (double)yawSpeed && (double)(-pitchSpeed) <= diffPitch && diffPitch <= (double)pitchSpeed)) {
            yaw = (float)((double)yaw + (double)RandomUtil.nextFloat(1.0f, 2.0f) * Math.sin((double)pitch * Math.PI));
            pitch = (float)((double)pitch + (double)RandomUtil.nextFloat(1.0f, 2.0f) * Math.sin((double)yaw * Math.PI));
        }
        return new float[]{yaw, pitch};
    }

    public static float[] getNewKillAuraRotsHeuristics(EntityPlayerSP player, EntityLivingBase target, float currentYaw, float currentPitch) {
        Vec3 positionEyes = player.getPositionEyes(1.0f);
        float f11 = target.getCollisionBorderSize();
        double ex = MathHelper.clamp_double(positionEyes.xCoord, target.getEntityBoundingBox().minX - (double)f11, target.getEntityBoundingBox().maxX + (double)f11);
        double ey = MathHelper.clamp_double(positionEyes.yCoord, target.getEntityBoundingBox().minY - (double)f11, target.getEntityBoundingBox().maxY + (double)f11);
        double ez = MathHelper.clamp_double(positionEyes.zCoord, target.getEntityBoundingBox().minZ - (double)f11, target.getEntityBoundingBox().maxZ + (double)f11);
        double x = ex - player.posX;
        double y = ey - (player.posY + (double)player.getEyeHeight());
        double z = ez - player.posZ;
        float heursticsyawXD = (float)MathHelper.getRandomDoubleInRange(new Random(), heursticsyaw, heursticsyaw2);
        float heursticspitchXD = (float)MathHelper.getRandomDoubleInRange(new Random(), heursticspitch, heursticspitch2);
        float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - (double)heursticsyawXD);
        float calcPitch = (float)(-(MathHelper.func_181159_b(y, MathHelper.sqrt_double(x * x + z * z)) * (double)heursticspitchXD / Math.PI));
        float yawSpeed = 180.0f;
        float pitchSpeed = 180.0f;
        float yaw = TestAura.updateRotation(currentYaw, calcYaw, yawSpeed);
        float pitch = TestAura.updateRotation(currentPitch, calcPitch, pitchSpeed);
        double diffYaw = MathHelper.wrapAngleTo180_float(calcYaw - currentYaw);
        double diffPitch = MathHelper.wrapAngleTo180_float(calcPitch - currentPitch);
        if (!((double)(-yawSpeed) <= diffYaw && diffYaw <= (double)yawSpeed && (double)(-pitchSpeed) <= diffPitch && diffPitch <= (double)pitchSpeed)) {
            yaw = (float)((double)yaw + (double)RandomUtil.nextFloat(1.0f, 2.0f) * Math.sin((double)pitch * Math.PI));
            pitch = (float)((double)pitch + (double)RandomUtil.nextFloat(1.0f, 2.0f) * Math.sin((double)yaw * Math.PI));
        }
        return new float[]{yaw, pitch};
    }

    public static float[] getNewKillAuraRotsHeuristicsGCD(EntityPlayerSP player, EntityLivingBase target, float currentYaw, float currentPitch) {
        Vec3 positionEyes = player.getPositionEyes(1.0f);
        float f11 = target.getCollisionBorderSize();
        double ex = MathHelper.clamp_double(positionEyes.xCoord, target.getEntityBoundingBox().minX - (double)f11, target.getEntityBoundingBox().maxX + (double)f11);
        double ey = MathHelper.clamp_double(positionEyes.yCoord, target.getEntityBoundingBox().minY - (double)f11, target.getEntityBoundingBox().maxY + (double)f11);
        double ez = MathHelper.clamp_double(positionEyes.zCoord, target.getEntityBoundingBox().minZ - (double)f11, target.getEntityBoundingBox().maxZ + (double)f11);
        double x = ex - player.posX;
        double y = ey - (player.posY + (double)player.getEyeHeight());
        double z = ez - player.posZ;
        float heursticsyawXD = (float)MathHelper.getRandomDoubleInRange(new Random(), heursticsyaw, heursticsyaw2);
        float heursticspitchXD = (float)MathHelper.getRandomDoubleInRange(new Random(), heursticspitch, heursticspitch2);
        float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - (double)heursticsyawXD);
        float calcPitch = (float)(-(MathHelper.func_181159_b(y, MathHelper.sqrt_double(x * x + z * z)) * (double)heursticspitchXD / Math.PI));
        float yawSpeed = 180.0f;
        float pitchSpeed = 180.0f;
        float yaw = TestAura.updateRotation(currentYaw, calcYaw, yawSpeed);
        float pitch = TestAura.updateRotation(currentPitch, calcPitch, pitchSpeed);
        double diffYaw = MathHelper.wrapAngleTo180_float(calcYaw - currentYaw);
        double diffPitch = MathHelper.wrapAngleTo180_float(calcPitch - currentPitch);
        if (!((double)(-yawSpeed) <= diffYaw && diffYaw <= (double)yawSpeed && (double)(-pitchSpeed) <= diffPitch && diffPitch <= (double)pitchSpeed)) {
            yaw = (float)((double)yaw + (double)RandomUtil.nextFloat(1.0f, 2.0f) * Math.sin((double)pitch * Math.PI));
            pitch = (float)((double)pitch + (double)RandomUtil.nextFloat(1.0f, 2.0f) * Math.sin((double)yaw * Math.PI));
        }
        float f2 = TestAura.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        return new float[]{yaw, pitch};
    }

    public static float[] getNewKillAuraRotsGCD(EntityPlayerSP player, EntityLivingBase target, float currentYaw, float currentPitch) {
        Vec3 positionEyes = player.getPositionEyes(1.0f);
        float f11 = target.getCollisionBorderSize();
        double ex = MathHelper.clamp_double(positionEyes.xCoord, target.getEntityBoundingBox().minX - (double)f11, target.getEntityBoundingBox().maxX + (double)f11);
        double ey = MathHelper.clamp_double(positionEyes.yCoord, target.getEntityBoundingBox().minY - (double)f11, target.getEntityBoundingBox().maxY + (double)f11);
        double ez = MathHelper.clamp_double(positionEyes.zCoord, target.getEntityBoundingBox().minZ - (double)f11, target.getEntityBoundingBox().maxZ + (double)f11);
        double x = ex - player.posX;
        double y = ey - (player.posY + (double)player.getEyeHeight());
        double z = ez - player.posZ;
        float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - 90.0);
        float calcPitch = (float)(-(MathHelper.func_181159_b(y, MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
        float yawSpeed = 180.0f;
        float pitchSpeed = 180.0f;
        float yaw = TestAura.updateRotation(currentYaw, calcYaw, yawSpeed);
        float pitch = TestAura.updateRotation(currentPitch, calcPitch, pitchSpeed);
        double diffYaw = MathHelper.wrapAngleTo180_float(calcYaw - currentYaw);
        double diffPitch = MathHelper.wrapAngleTo180_float(calcPitch - currentPitch);
        if (!((double)(-yawSpeed) <= diffYaw && diffYaw <= (double)yawSpeed && (double)(-pitchSpeed) <= diffPitch && diffPitch <= (double)pitchSpeed)) {
            yaw = (float)((double)yaw + (double)RandomUtil.nextFloat(1.0f, 2.0f) * Math.sin((double)pitch * Math.PI));
            pitch = (float)((double)pitch + (double)RandomUtil.nextFloat(1.0f, 2.0f) * Math.sin((double)yaw * Math.PI));
        }
        float f2 = TestAura.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        return new float[]{yaw, pitch};
    }

    public static float[] getNewKillAuraRotsHeuristicsGCDABS(EntityPlayerSP player, EntityLivingBase target, float currentYaw, float currentPitch) {
        Vec3 positionEyes = player.getPositionEyes(1.0f);
        float f11 = target.getCollisionBorderSize();
        double ex = MathHelper.clamp_double(positionEyes.xCoord, target.getEntityBoundingBox().minX - (double)f11, target.getEntityBoundingBox().maxX + (double)f11);
        double ey = MathHelper.clamp_double(positionEyes.yCoord, target.getEntityBoundingBox().minY - (double)f11, target.getEntityBoundingBox().maxY + (double)f11);
        double ez = MathHelper.clamp_double(positionEyes.zCoord, target.getEntityBoundingBox().minZ - (double)f11, target.getEntityBoundingBox().maxZ + (double)f11);
        double x = ex - player.posX;
        double y = ey - (player.posY + (double)player.getEyeHeight());
        double z = ez - player.posZ;
        float heursticsyawXD = (float)MathHelper.getRandomDoubleInRange(new Random(), heursticsyaw, heursticsyaw2);
        float heursticspitchXD = (float)MathHelper.getRandomDoubleInRange(new Random(), heursticspitch, heursticspitch2);
        float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - (double)heursticsyawXD);
        float calcPitch = (float)(-(MathHelper.func_181159_b(y, MathHelper.sqrt_double(x * x + z * z)) * (double)heursticspitchXD / Math.PI));
        float yawSpeed = 180.0f;
        float pitchSpeed = 180.0f;
        float yaw = TestAura.updateRotation(currentYaw, calcYaw, yawSpeed);
        float pitch = TestAura.updateRotation(currentPitch, calcPitch, pitchSpeed);
        double diffYaw = MathHelper.wrapAngleTo180_float(calcYaw - currentYaw);
        double diffPitch = MathHelper.wrapAngleTo180_float(calcPitch - currentPitch);
        if (!((double)(-yawSpeed) <= diffYaw && diffYaw <= (double)yawSpeed && (double)(-pitchSpeed) <= diffPitch && diffPitch <= (double)pitchSpeed)) {
            yaw = (float)((double)yaw + (double)RandomUtil.nextFloat(1.0f, 2.0f) * Math.sin((double)pitch * Math.PI));
            pitch = (float)((double)pitch + (double)RandomUtil.nextFloat(1.0f, 2.0f) * Math.sin((double)yaw * Math.PI));
        }
        float f2 = TestAura.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= Math.abs(yaw % f3);
        pitch -= Math.abs(pitch % (f3 * f2));
        return new float[]{yaw, pitch};
    }

    public static float[] getNewKillAuraRotsGCDABS(EntityPlayerSP player, EntityLivingBase target, float currentYaw, float currentPitch) {
        Vec3 positionEyes = player.getPositionEyes(1.0f);
        float f11 = target.getCollisionBorderSize();
        double ex = MathHelper.clamp_double(positionEyes.xCoord, target.getEntityBoundingBox().minX - (double)f11, target.getEntityBoundingBox().maxX + (double)f11);
        double ey = MathHelper.clamp_double(positionEyes.yCoord, target.getEntityBoundingBox().minY - (double)f11, target.getEntityBoundingBox().maxY + (double)f11);
        double ez = MathHelper.clamp_double(positionEyes.zCoord, target.getEntityBoundingBox().minZ - (double)f11, target.getEntityBoundingBox().maxZ + (double)f11);
        double x = ex - player.posX;
        double y = ey - (player.posY + (double)player.getEyeHeight());
        double z = ez - player.posZ;
        float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - 90.0);
        float calcPitch = (float)(-(MathHelper.func_181159_b(y, MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
        float yawSpeed = 180.0f;
        float pitchSpeed = 180.0f;
        float yaw = TestAura.updateRotation(currentYaw, calcYaw, yawSpeed);
        float pitch = TestAura.updateRotation(currentPitch, calcPitch, pitchSpeed);
        double diffYaw = MathHelper.wrapAngleTo180_float(calcYaw - currentYaw);
        double diffPitch = MathHelper.wrapAngleTo180_float(calcPitch - currentPitch);
        if (!((double)(-yawSpeed) <= diffYaw && diffYaw <= (double)yawSpeed && (double)(-pitchSpeed) <= diffPitch && diffPitch <= (double)pitchSpeed)) {
            yaw = (float)((double)yaw + (double)RandomUtil.nextFloat(1.0f, 2.0f) * Math.sin((double)pitch * Math.PI));
            pitch = (float)((double)pitch + (double)RandomUtil.nextFloat(1.0f, 2.0f) * Math.sin((double)yaw * Math.PI));
        }
        float f2 = TestAura.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= Math.abs(yaw % f3);
        pitch -= Math.abs(pitch % (f3 * f2));
        return new float[]{yaw, pitch};
    }

    public static float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
        float f = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
        if (f > p_70663_3_) {
            f = p_70663_3_;
        }
        if (f < -p_70663_3_) {
            f = -p_70663_3_;
        }
        return p_70663_1_ + f;
    }

    public void AutoBlock() {
        block11: {
            if (!((CheckBox)this.getSetting((String)"Autoblock").getSetting()).state || TestAura.mc.thePlayer.getHeldItem() == null || !(TestAura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) break block11;
            switch (((DropdownBox)this.getSetting((String)"AutoBlock").getSetting()).curOption) {
                case "Intave": {
                    if (TestAura.mc.thePlayer.isSwingInProgress && TestAura.mc.thePlayer.getHeldItem() != null && TestAura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                        TestAura.mc.gameSettings.keyBindUseItem.pressed = true;
                    }
                }
                case "SmartIntave": {
                    if (!TestAura.mc.thePlayer.isSwingInProgress || TestAura.mc.thePlayer.hurtTime == 0 || TestAura.mc.thePlayer.getHeldItem() == null || !(TestAura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) break;
                    TestAura.mc.gameSettings.keyBindUseItem.pressed = true;
                }
            }
        }
    }

    boolean isBot(EntityPlayer player) {
        if (!this.isInTablist(player)) {
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

    public void drawTargetHud(Entity target1) {
        switch (((DropdownBox)this.getSetting((String)"TargetHUDM").getSetting()).curOption) {
            case "Fanta": {
                try {
                    ScaledResolution s = new ScaledResolution(mc);
                    Color backgroundColor = new Color(0, 0, 0, 120);
                    Color emptyBarColor = new Color(59, 59, 59, 160);
                    Color healthBarColor = Color.green;
                    Color distBarColor = new Color(20, 81, 208);
                    int left = (int)this.x + s.getScaledWidth() / 2 + 5;
                    int right2 = 80;
                    int right = s.getScaledWidth() / 900 + right2 - 5;
                    int right3 = 80 + Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(target1.getName()) / 2 - 15;
                    int top = (int)this.y + s.getScaledHeight() / 2 - 25;
                    int bottom = (int)this.y + s.getScaledHeight() / 2 + 25;
                    float curTargetHealth = ((EntityPlayer)target1).getHealth();
                    float maxTargetHealth = ((EntityPlayer)target1).getMaxHealth();
                    float healthProcent = target.getHealth() / target.getMaxHealth();
                    if (this.lastHealthPercentage != healthProcent) {
                        this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
                    }
                    this.lastHealthPercentage = healthProcent;
                    float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
                    this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
                    float calculatedHealth = finalHealthPercentage;
                    int rectRight = right + Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(target1.getName()) / 2 - 5;
                    float healthPos = calculatedHealth * (float)right3;
                    RenderUtil.drawRoundedRect2(left, top - 2, right + Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(target1.getName()) - 5, 52.0, 10.0, new Color(25, 25, 25, 150));
                    if (this.lastHealthPercentage != healthProcent) {
                        this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
                    }
                    this.lastHealthPercentage = healthProcent;
                    this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    int cfr_ignored_0 = TestAura.target.hurtTime;
                    List NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(TestAura.mc.thePlayer.sendQueue.getPlayerInfoMap());
                    for (Object nextObject : NetworkMoment) {
                        NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)nextObject;
                        if (TestAura.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) != target1) continue;
                        GlStateManager.enableCull();
                        mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                        Gui.drawScaledCustomSizeModalRect(left / 2 * 2 + 6, top / 2 * 2 + 3, 8.0f, 8.0f, 8, 8, 32, 32, 64.0f, 64.0f);
                    }
                    TestAura.drawRect(left + 5, bottom - 13, (float)left + healthPos + (float)(Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(target.getName()) / 2), bottom - 5, Color.cyan);
                    Client.INSTANCE.fluxTabGuiFont.drawStringWithShadow(target.getName(), left + 48, top + 4, Color.WHITE.getRGB());
                    Client.INSTANCE.fluxTabGuiFont.drawStringWithShadow("Health: " + Math.round(curTargetHealth), left + 48, top + 15, Color.WHITE.getRGB());
                }
                catch (NullPointerException s) {}
                break;
            }
            case "WindowsXP": {
                try {
                    ScaledResolution s = new ScaledResolution(mc);
                    Color backgroundColor = new Color(0, 0, 0, 120);
                    Color emptyBarColor = new Color(59, 59, 59, 160);
                    Color healthBarColor = Color.green;
                    Color distBarColor = new Color(20, 81, 208);
                    int left = (int)this.x + s.getScaledWidth() / 2 + 5;
                    int right2 = 80;
                    int right = s.getScaledWidth() / 900 + right2 - 5;
                    int right3 = 80 + Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(target1.getName()) / 2 - 15;
                    int top = (int)this.y + s.getScaledHeight() / 2 - 25;
                    int bottom = (int)this.y + s.getScaledHeight() / 2 + 25;
                    float curTargetHealth = ((EntityPlayer)target1).getHealth();
                    float maxTargetHealth = ((EntityPlayer)target1).getMaxHealth();
                    float healthProcent = target.getHealth() / target.getMaxHealth();
                    if (this.lastHealthPercentage != healthProcent) {
                        this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
                    }
                    this.lastHealthPercentage = healthProcent;
                    float finalHealthPercentage = healthProcent + this.lostHealthPercentage;
                    this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
                    float calculatedHealth = finalHealthPercentage;
                    int rectRight = right + Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(target1.getName()) / 2 - 5;
                    float healthPos = calculatedHealth * (float)right3;
                    RenderUtil.drawRoundedRect2(left - 3, top - 19, right + Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(target1.getName()) + 76, 118.0, 5.0, new Color(0, 84, 232));
                    RenderUtil.drawRoundedRect2(left, top - 5, right + Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(target1.getName()) + 70, 100.0, 0.0, new Color(220, 220, 220, 255));
                    TestAura.drawImage(left + 110 + Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(target1.getName()), top - 18, 35, 13, new ResourceLocation("Fanta/gui/windows.png"));
                    if (this.lastHealthPercentage != healthProcent) {
                        this.lostHealthPercentage += this.lastHealthPercentage - healthProcent;
                    }
                    this.lastHealthPercentage = healthProcent;
                    this.lostHealthPercentage = Math.max(0.0f, this.lostHealthPercentage - this.lostHealthPercentage / 20.0f);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    int cfr_ignored_1 = TestAura.target.hurtTime;
                    List NetworkMoment = GuiPlayerTabOverlay.field_175252_a.sortedCopy(TestAura.mc.thePlayer.sendQueue.getPlayerInfoMap());
                    for (Object nextObject : NetworkMoment) {
                        NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)nextObject;
                        if (TestAura.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfo.getGameProfile().getId()) != target1) continue;
                        GlStateManager.enableCull();
                        mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                        Gui.drawScaledCustomSizeModalRect(left / 2 * 2 + 8, top / 2 * 2 + 21, 8.0f, 8.0f, 8, 8, 45, 45, 64.0f, 64.0f);
                    }
                    TestAura.drawRect(left + 60, bottom - 10, (float)left + healthPos + (float)(Client.INSTANCE.unicodeBasicFontRenderer5.getStringWidth(target.getName()) / 2) + 45.0f, bottom + 15, new Color(5, 135, 8));
                    Client.INSTANCE.fluxTabGuiFont.drawStringWithShadow("Explorer", left + 2, top - 19, -1);
                    Client.INSTANCE.fluxTabGuiFont.drawString(target.getName(), left + 60, top + 15, Color.black.getRGB());
                    Client.INSTANCE.fluxTabGuiFont.drawString("Health: " + Math.round(curTargetHealth), left + 60, top + 25, Color.black.getRGB());
                    break;
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
            }
        }
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

    public static void drawImage(int x, int y, int width, int height, ResourceLocation resourceLocation) {
        mc.getTextureManager().bindTexture(resourceLocation);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }
}

