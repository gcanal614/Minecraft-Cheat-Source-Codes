package wtf.astronicy.IMPL.module.impl.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.packet.SendPacketEvent;
import wtf.astronicy.API.events.player.MotionUpdateEvent;
import wtf.astronicy.API.events.player.MoveEvent;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.options.Option;
import wtf.astronicy.IMPL.module.options.impl.DoubleOption;
import wtf.astronicy.IMPL.module.options.impl.EnumOption;
import wtf.astronicy.IMPL.module.registery.Bind;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import wtf.astronicy.IMPL.utils.Logger;
import wtf.astronicy.IMPL.utils.MovementUtils;
import wtf.astronicy.IMPL.utils.TimerUtility;

@ModName("Speed")
@Bind("V")
@Category(ModuleCategory.MOVEMENT)
public class Speed extends Module {
    public TimerUtility timer = new TimerUtility();


    public final EnumOption mode = new EnumOption("Speed Mode", SpeedModes.Hypixel); public enum SpeedModes{ Hypixel, HypixelLow, VanillaHop, Custom};
    public final DoubleOption speedSpeed = new DoubleOption("Bhop Speed", 0.5D, () -> { return this.mode.getValue() == SpeedModes.VanillaHop; }, 0.1D, 10D, .1D);
    public final DoubleOption customY = new DoubleOption("Custom Y", 0.5D, () -> { return this.mode.getValue() == SpeedModes.Custom; }, 0.1D, 10D, 0.05D);
    public final DoubleOption customSpeed = new DoubleOption("Custom Speed", 0.5D, () -> { return this.mode.getValue() == SpeedModes.Custom; }, 1D, 10D, .1D);

    public Speed(){
        this.addOptions(new Option[]{mode, speedSpeed, customY, customSpeed});
    }

    @Listener(MoveEvent.class)
    public final void onMovePlayer(MoveEvent event) {
        switch ((SpeedModes)this.mode.getValue()){

        }
    }
    @Override
    public void onDisabled() {
        mc.timer.timerSpeed = 1f;
        super.onDisabled();
    }

    @Listener(MotionUpdateEvent.class)
    public final void onMotionUpdate(MotionUpdateEvent event) {
        switch ((SpeedModes)this.mode.getValue()){
            case VanillaHop:
                if (mc.thePlayer.isMoving()) {
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.motionY = 0.41f;
                    }
                mc.thePlayer.setSpeed((double)(speedSpeed.getValue()) / 4);
            }
                break;
            case Custom:
                if(mc.thePlayer.isMoving()){
                    if(mc.thePlayer.onGround){
                        mc.thePlayer.motionY = (double) (customY.getValue());
                    }
                    mc.thePlayer.setSpeed((double)(customSpeed.getValue()) / 4);
                }
                break;

            case Hypixel:
                double al = 0;
                if(mc.thePlayer.isCollidedVertically)
                {
                    mc.thePlayer.motionY = 0.42F;
                }
                else
                {
                    if(mc.thePlayer.motionY <= 0F)
                    {
                        MovementUtils.setSpeed(0.1F);
                        mc.thePlayer.onGround = true;
                        al = mc.thePlayer.lastTickPosY;
                        mc.thePlayer.jump();
                    }
                    if (timer.elapsed(300)){
                        mc.thePlayer.fall(3,0);
                     //   mc.thePlayer.onGround = false;
                        timer.reset();
                    }
                }

              //  this.mc.thePlayer.motionY = - 0.1;


                break;
        }

    }

    @Listener(SendPacketEvent.class)
    public void onSendPacket(SendPacketEvent event){
        switch ((SpeedModes)this.mode.getValue()){
            case Hypixel:

                break;
        }
    }

}
