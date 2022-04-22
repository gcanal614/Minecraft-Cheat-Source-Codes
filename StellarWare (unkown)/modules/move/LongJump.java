package stellar.skid.modules.move;

import net.minecraft.util.Timer;
import stellar.skid.events.EventTarget;
import stellar.skid.events.events.*;
import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.gui.screen.setting.SettingType;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.configurations.annotation.Property;
import stellar.skid.modules.configurations.property.object.PropertyFactory;
import stellar.skid.modules.configurations.property.object.StringProperty;
import stellar.skid.utils.notifications.NotificationType;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class    LongJump extends AbstractModule {

    /*    @Property("speed")
        private DoubleProperty speed = PropertyFactory.createDouble(5.0).minimum(2.0).maximum(6.0);
        @Property("jump-boost")
        private IntProperty jump_boost = PropertyFactory.createInt(2).minimum(0).maximum(4);*/
    @Property("Longjump")
    private final StringProperty LongjumpModes = PropertyFactory.createString("LongjumpBow").acceptableValues("LongjumpBow", "RedeSkyTest");

    public LongJump(@NotNull ModuleManager novoline) {
        super(novoline, EnumModuleType.MOVEMENT, "LongJump", "Long Jump");
/*        Manager.put(new Setting("LJ_SPEED", "Speed", SettingType.SLIDER, this, speed, 0.2));
        Manager.put(new Setting("LJ_JUMP_BOOST", "Jump Boost", SettingType.SLIDER, this, jump_boost, 1));*/
        Manager.put(new Setting("LongJumpModes", "Modes ", SettingType.COMBOBOX, this, LongjumpModes));

    }

    private int tick, stage;
    private boolean shouldBoost, wait;
    private double lastDist, baseSpeed, moveSpeed;
    private List<Packet> packetList = new CopyOnWriteArrayList();

    private int bowSlot() {
        return mc.player.getSlotByItem(Items.bow);
    }

    private double z, O;
    private boolean J;

    @Override
    public void onEnable() {
        if (LongjumpModes.get().equalsIgnoreCase("LongjumpBow")) {
            if (bowSlot() == -1 || !mc.player.inventory.hasItem(Items.arrow)) {
                stellarWare.getNotificationManager().pop(getDisplayName(), "You Need Bow and Arrows!", NotificationType.WARNING);
                toggle();
                return;
            }

            wait = true;
            mc.timer.timerSpeed = 0.8F;
            setSuffix("Bow");
            checkModule(Speed.class, Scaffold.class, Flight.class);
        }
    }

    @Override
    public void onDisable() {
        tick = 0;
        mc.timer.timerSpeed = 1;
    }

    @EventTarget
    public void onTick(TickUpdateEvent event) {
        if (LongjumpModes.get().equalsIgnoreCase("LongjumpBow")) {

            setSuffix("Bow");
            tick++;
        }
    }

    @EventTarget
    public void EventMotion(EventMotion EventMotion) {

        if (EventMotion instanceof EventMotion) {
            EventMotion ads = (EventMotion) EventMotion;
            if (LongjumpModes.get().equalsIgnoreCase("RedeSkyTest")) {
                
                stellarWare.notificationManager.pop("tets", NotificationType.WARNING);
            }
        }
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent event) {


        if (LongjumpModes.get().equalsIgnoreCase("LongjumpBow")) {

            lastDist = mc.player.getLastTickDistance();
            baseSpeed = mc.player.getBaseMoveSpeed(0.16);

            if (mc.player.hurtResistantTime == 19) {
                wait = false;
                stage = 1;
            }
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        if (LongjumpModes.get().equalsIgnoreCase("LongjumpBow")) {

            if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                checkModule(getClass());
            }

            if (wait) {
                if (event.getPacket() instanceof C03PacketPlayer || event.getPacket() instanceof C08PacketPlayerBlockPlacement
                        || event.getPacket() instanceof C07PacketPlayerDigging || event.getPacket() instanceof C09PacketHeldItemChange) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventTarget
    public void onMotion(MotionUpdateEvent event) {


        if (LongjumpModes.get().equalsIgnoreCase("LongjumpBow")) {

            if (event.getState().equals(MotionUpdateEvent.State.PRE)) {
                if (tick == 1) {
                    sendPacketNoEvent(new C03PacketPlayer());
                    sendPacketNoEvent(new C09PacketHeldItemChange(bowSlot()));
                    sendPacketNoEvent(new C08PacketPlayerBlockPlacement(mc.player.inventory.getStackInSlot(bowSlot())));
                } else if (tick == 2) {
                    sendPacketNoEvent(new C03PacketPlayer());
                } else if (tick == 3) {
                    sendPacketNoEvent(new C03PacketPlayer());
                } else if (tick == 4) {
                    sendPacketNoEvent(new C06PacketPlayerPosLook(event.getX(), event.getY(), event.getZ(), event.getYaw(), -90, event.isOnGround()));
                    sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    sendPacketNoEvent(new C09PacketHeldItemChange(mc.player.inventory.currentItem));
                }
            }


            if (tick > 25) {
                if (mc.player.onGround) {
                    wait = true;
                    mc.timer.timerSpeed = 1;
                    checkModule(getClass());
                }
            }
        }
    }

    @EventTarget
    public void onMove(MoveEvent event) {


        if (LongjumpModes.get().equalsIgnoreCase("LongjumpBow")) {

            double baseAmplifier = mc.player.isPotionActive(Potion.moveSpeed) ? mc.player.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 1 ? 1.86 : 1.34 : 1.34;
            double jumpAmplifier = mc.player.isPotionActive(Potion.moveSpeed) ? mc.player.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 1 ? 1.69 : 2.35 : 2.2;
            if (wait) {
                event.setMoveSpeed(0);
            } else {
                if (mc.player.onGround) {
                    mc.timer.timerSpeed = 0.8F;
                    event.setY(this.mc.player.motionY = this.mc.player.getBaseMotionY());
                    mc.player.setMotion(5);
                    if (!this.mc.player.isPotionActive(Potion.jump) && this.mc.player.motionY < 0.0D) {
                        this.moveSpeed = this.z;
                        if (this.mc.player.ticksExisted % 2 == 0 && (double) this.mc.player.fallDistance < 0.45D) {
                            this.moveSpeed = this.z * 1.2D;
                            this.mc.player.motionY = 0.0D;
                        }
                    }


                    this.moveSpeed = Math.max(this.moveSpeed, this.z);
                    event.setMoveSpeed(Math.max(moveSpeed, baseSpeed));
                }
            }
        }
    }


}

