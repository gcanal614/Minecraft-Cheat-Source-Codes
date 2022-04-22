package stellar.skid.modules.player;

import stellar.skid.events.EventTarget;
import stellar.skid.events.events.CollideWithBlockEvent;
import stellar.skid.events.events.MotionUpdateEvent;
import stellar.skid.events.events.MoveEvent;
import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.gui.screen.setting.SettingType;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.configurations.annotation.Property;
import stellar.skid.modules.configurations.property.object.IntProperty;
import stellar.skid.modules.configurations.property.object.PropertyFactory;
import stellar.skid.modules.configurations.property.object.StringProperty;
import stellar.skid.modules.move.Step;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.MathHelper;
import org.checkerframework.checker.nullness.qual.NonNull;

import static stellar.skid.gui.screen.setting.SettingType.SLIDER;
import static stellar.skid.modules.EnumModuleType.EXPLOITS;
import static stellar.skid.modules.EnumModuleType.PLAYER;
import static stellar.skid.modules.configurations.property.object.PropertyFactory.createInt;
import static java.lang.Math.toRadians;

public final class Phase extends AbstractModule {

    @Property("mode")
    private StringProperty mode = PropertyFactory.createString("Downclip").acceptableValues("Collision","Downclip");
    @Property("downclip-value")
    private final IntProperty downClipValue = createInt(3).minimum(3).maximum(15);


    /* constructors */
    public Phase(@NonNull ModuleManager moduleManager) {
        super(moduleManager, "Phase", PLAYER, "Allows you to walks through blocks");
        Manager.put(new Setting("PHASE_MODE","Mode", SettingType.COMBOBOX,this,mode));
        Manager.put(new Setting("PHASE_DOWNCLIP_VALUE", "Height", SLIDER, this, downClipValue, 1, () -> mode.get().equals("Downclip")));
    }

    @Override
    public void onEnable() {
        if(mode.get().equals("Downclip")){
            mc.player.setPosition(mc.player.posX,mc.player.posY - downClipValue.get(),mc.player.posZ);
            toggle();
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (!isEnabled(Step.class)) {
            mc.player.stepHeight = 0.625F;
        }
    }

    /* events */
    @EventTarget
    public void onCollide(CollideWithBlockEvent collide) {
        if (mc.player.isInsideBlock()) {
            collide.setBoundingBox(null);
        }
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        if (mc.player.isInsideBlock()) {
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                event.setY(mc.player.motionY += 0.09f);
            } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                event.setY(mc.player.motionY -= 0.09f);
            } else {
                event.setY(mc.player.motionY = 0.0f);
            }

            event.setMoveSpeed(mc.player.getBaseMoveSpeed());
        }
    }

    @EventTarget
    public void onPost(MotionUpdateEvent event) {
        if (event.getState().equals(MotionUpdateEvent.State.POST)) {
            if (mc.player.stepHeight > 0) mc.player.stepHeight = 0;

            float moveStrafe = mc.player.movementInput().getMoveStrafe(), // @off
                    moveForward = mc.player.movementInput().getMoveForward(),
                    rotationYaw = mc.player.rotationYaw;

            double multiplier = 0.3,
                    mx = -MathHelper.sin(toRadians(rotationYaw)),
                    mz = MathHelper.cos(toRadians(rotationYaw)),
                    x = moveForward * multiplier * mx + moveStrafe * multiplier * mz,
                    z = moveForward * multiplier * mz - moveStrafe * multiplier * mx; // @on

            if (mc.player.isCollidedHorizontally && !mc.player.isOnLadder() && mc.player.onGround) {
                double posX = mc.player.posX, posY = mc.player.posY, posZ = mc.player.posZ;

                sendPacket(new C04PacketPlayerPosition(posX + x, posY, posZ + z, true));
                sendPacket(new C04PacketPlayerPosition(posX, posY + 3, posZ, true));
                mc.player.setPosition(posX + x, posY, posZ + z);
            }
        }
    }
}
