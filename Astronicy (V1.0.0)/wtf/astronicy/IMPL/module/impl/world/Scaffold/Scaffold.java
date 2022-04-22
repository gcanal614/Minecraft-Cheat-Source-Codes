package wtf.astronicy.IMPL.module.impl.world.Scaffold;

import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.player.MotionUpdateEvent;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.registery.Bind;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import wtf.astronicy.IMPL.module.options.Option;
import wtf.astronicy.IMPL.module.options.impl.BoolOption;
import wtf.astronicy.IMPL.module.options.impl.DoubleOption;
import wtf.astronicy.IMPL.module.options.impl.EnumOption;
import wtf.astronicy.IMPL.utils.RotationUtils;
import wtf.astronicy.IMPL.utils.TimerUtility;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;


@ModName("Scaffold")
@Bind("V")
@Category(ModuleCategory.WORLD)
public class Scaffold extends Module {

    private ScaffoldUtils.BlockCache blockCache, lastBlockCache;
    private EnumOption placetype = new EnumOption("Place Type", placeModes.PRE);
    public static DoubleOption extend = new DoubleOption("Extend", 0, 6, 0, 0.01);
    public static BoolOption sprint = new BoolOption("Sprint", false);
    private BoolOption tower = new BoolOption("Tower", false);
    private DoubleOption towerTimer = new DoubleOption("Tower Timer Boost", 1.2, 5, 0.1, 0.1);
    private BoolOption swing = new BoolOption("Swing", false);
    private float rotations[];
    private TimerUtility timer = new TimerUtility();


    public Scaffold(){
        placetype = new EnumOption("Modes", placeModes.PRE);
        this.addOptions(new Option[]{placetype,swing,tower,towerTimer,extend,sprint});

    }

    @Listener(MotionUpdateEvent.class)
    public void onMotion(MotionUpdateEvent e) {
        if(e.isPre()){
            // Rotations
            if(lastBlockCache != null) {
                rotations = RotationUtils.getFacingRotations2(lastBlockCache.getPosition().getX(), lastBlockCache.getPosition().getY(), lastBlockCache.getPosition().getZ());
                mc.thePlayer.renderYawOffset = rotations[0];
                mc.thePlayer.rotationYawHead = rotations[0];
                e.setYaw(rotations[0]);
                e.setPitch(81);
              //  mc.thePlayer.rotationPitchHead = 81;
            } else {
                e.setPitch(81);
                e.setYaw(mc.thePlayer.rotationYaw + 180);
               // mc.thePlayer.rotationPitchHead = 81;
                mc.thePlayer.renderYawOffset = mc.thePlayer.rotationYaw + 180;
                mc.thePlayer.rotationYawHead = mc.thePlayer.rotationYaw + 180;
            }

            // Speed 2 Slowdown
            if(mc.thePlayer.isPotionActive(Potion.moveSpeed.id)){
                mc.thePlayer.motionX *= 0.66;
                mc.thePlayer.motionZ *= 0.66;
            }

            // Setting Block Cache
            blockCache = ScaffoldUtils.grab();
            if (blockCache != null) {
                lastBlockCache = ScaffoldUtils.grab();
            }else{
                return;
            }

            // Setting Item Slot (Pre)
            int slot = ScaffoldUtils.grabBlockSlot();
            if(slot == -1) return;

            // Setting Slot
            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));

            // Placing Blocks (Pre)
            if(placetype.getValue() == placeModes.PRE){
                //Logger.log("Placing PRE");
                if (blockCache == null) return;
                mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(slot), lastBlockCache.position, lastBlockCache.facing, ScaffoldUtils.getHypixelVec3(lastBlockCache));
                if(swing.getValue()){
                    mc.thePlayer.swingItem();
                }
                mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                blockCache = null;
            }
        }else{

            // Tower
            if(tower.getValue()) {
                if(mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.timer.timerSpeed = (float) towerTimer.getValue();
                    if(mc.thePlayer.motionY < 0) {
                        mc.thePlayer.jump();
                    }
                }else{
                    mc.timer.timerSpeed = 1;
                }
            }

            // Setting Item Slot (Post)
            int slot = ScaffoldUtils.grabBlockSlot();
            if(slot == -1) return;

            // Placing Blocks (Post)
            if(placetype.getValue() == placeModes.POST){
                //Logger.log("Placing POST");

                if (blockCache == null) return;
                mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(slot), lastBlockCache.position, lastBlockCache.facing, ScaffoldUtils.getHypixelVec3(lastBlockCache));
                if(swing.getValue()){
                    mc.thePlayer.swingItem();
                }
                mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                blockCache = null;
            }
        }
        }
    enum placeModes {
        PRE,
        POST;

    }

    }



