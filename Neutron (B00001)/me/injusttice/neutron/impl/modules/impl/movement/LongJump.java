package me.injusttice.neutron.impl.modules.impl.movement;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventNigger;
import me.injusttice.neutron.api.events.impl.UpdateEvent;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.movement.MovementUtils;
import me.injusttice.neutron.utils.network.PacketUtil;
import me.injusttice.neutron.utils.player.Timer;
import me.injusttice.neutron.api.events.impl.EventMotion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class LongJump extends Module {

    public ModeSet mode = new ModeSet("Mode", "BlocksMC", "Vanilla", "BlocksMC", "Redesky", "Hypixel Bow");
    public DoubleSet multiplier = new DoubleSet("Multiplier", 1.0, 0.8, 1.5, 0.1);
    public boolean damaged;
    public Timer timer;
    int ticks;
    int slot;

    public LongJump() {
        super("LongJump", 0, Category.MOVEMENT);
        slot = 0;
        timer = new Timer();
        damaged = false;
        addSettings(mode, multiplier);
    }

    @EventTarget
    public void onEvent(EventNigger e) {
        if (e instanceof UpdateEvent){
            if (e.isPre()){
                setDisplayName("Long Jump §7" + mode.getMode());
                switch (mode.getMode()) {
                    case "BlocksMC":
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                            MovementUtils.strafe();
                            mc.thePlayer.motionX *= 1.153999988079071D;
                            mc.thePlayer.motionY += 0.2099999997764825382D;
                            mc.thePlayer.motionZ *= 1.153999988079071D;
                            mc.thePlayer.speedInAir = 0.1F;
                        }
                        break;
                }
            }
        }
    }

    @EventTarget
    public void onMotion(EventMotion e) {
        switch (mode.getMode()) {
            case "Hypixel Bow":
                int oldPitch = (int)mc.thePlayer.rotationPitch;
                if (mc.thePlayer.hurtTime > 0) {
                    damaged = true;
                }
                if (mc.thePlayer.onGround && !damaged) {
                    for (int i = 0; i < 9; ++i) {
                        ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
                        if (itemStack != null && mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemFireball) {
                            PacketUtil.sendPacketSilent(new C09PacketHeldItemChange(i));
                            PacketUtil.sendPacketSilent(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, 90.0f, mc.thePlayer.onGround));
                            PacketUtil.sendPacketSilent(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                            PacketUtil.sendPacketSilent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                            PacketUtil.sendPacketSilent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, (float)oldPitch, mc.thePlayer.onGround));
                        }
                    }
                }
                if (damaged && mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = 2.5;
                    MovementUtils.setMotion(MovementUtils.getBaseSpeedHypixelApplied() * 6.65);
                    toggle();
                } else {
                    damaged = false;
                }
                break;
            case "Redesky":
                if (e.isPre() && mode.is("Redesky") && mc.thePlayer.onGround) {
                    toggle();
                }
                break;
        }
    }

    @Override
    public void onEnable(){
        super.onEnable();
        ticks = 0;
        if (mode.is("Redesky") && mc.thePlayer.onGround) {
            mc.thePlayer.motionY = multiplier.getValue();
            EntityPlayerSP thePlayer = mc.thePlayer;
            thePlayer.motionX *= 1.5;
            EntityPlayerSP thePlayer2 = mc.thePlayer;
            thePlayer2.motionZ *= 1.5;
        }
        timer.reset();
        damaged = false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 1.0F;
        mc.thePlayer.speedInAir = 0.02f;

        resetCapabilities();
    }

    public static void resetCapabilities() {
        (Minecraft.getMinecraft()).gameSettings.keyBindJump.pressed = GameSettings.isKeyDown((Minecraft.getMinecraft()).gameSettings.keyBindJump);
        (Minecraft.getMinecraft()).thePlayer.stepHeight = 0.625F;
        (Minecraft.getMinecraft()).timer.timerSpeed = 1.0F;
        (Minecraft.getMinecraft()).thePlayer.isCollided = false;
        if(Minecraft.getMinecraft().thePlayer.isSpectator()) return;
        (Minecraft.getMinecraft()).thePlayer.capabilities.isFlying = false;
        (Minecraft.getMinecraft()).thePlayer.capabilities.allowFlying = (Minecraft.getMinecraft()).playerController.isInCreativeMode();
        (Minecraft.getMinecraft()).thePlayer.capabilities.isCreativeMode = (Minecraft.getMinecraft()).playerController.isInCreativeMode();
        (Minecraft.getMinecraft()).thePlayer.speedInAir = 0.02f;
    }
}
