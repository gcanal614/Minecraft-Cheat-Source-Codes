package wtf.astronicy.IMPL.module.impl.movement;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.player.MotionUpdateEvent;
import wtf.astronicy.API.events.player.MoveEvent;
import wtf.astronicy.API.events.player.UpdateActionEvent;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.registery.Bind;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import wtf.astronicy.IMPL.module.options.Option;
import wtf.astronicy.IMPL.module.options.impl.DoubleOption;
import wtf.astronicy.IMPL.module.options.impl.EnumOption;
import wtf.astronicy.IMPL.utils.MathUtils;
import wtf.astronicy.IMPL.utils.MovementUtils;
import wtf.astronicy.IMPL.utils.PlayerUtils;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import wtf.astronicy.IMPL.utils.TimerUtility;
import wtf.astronicy.Wrapper;

@ModName("SpeedIsOld")
@Category(ModuleCategory.MOVEMENT)
public final class SpeedMod extends Module {
   public final EnumOption mode;
   boolean collided;
   public final DoubleOption bhopSpeed;
   private float speed;
   private double moveSpeed;
   private double lastDist;
   private double y;
   public boolean shouldslow;
   private int stage;
   private int hops;
   boolean lessSlow;
   private int stageOG;
   double less;
   double stair;
   public double slow;
   TimerUtility timer = new TimerUtility();
   TimerUtility lastCheck = new TimerUtility();

   public SpeedMod() {
      this.mode = new EnumOption("Mode", SpeedMod.Mode.HYPIXEL);
      this.bhopSpeed = new DoubleOption("Bhop Speed", 0.5D, () -> {
         return this.mode.getValue() == SpeedMod.Mode.BHOP;
      }, 0.1D, 3.0D, 0.05D);
      this.setMode(this.mode);
      this.addOptions(new Option[]{this.mode, this.bhopSpeed});
   }

   public void onEnabled() {
      this.y = 0.0D;
      this.hops = 1;
      this.moveSpeed = MovementUtils.getBaseMoveSpeed();
      this.lastDist = 0.0D;
      this.stage = 0;

   }

   public void onDisabled() {
      mc.thePlayer.stepHeight = 0.625F;
      mc.timer.timerSpeed = 1.0F;
   }

   @Listener(UpdateActionEvent.class)
   public final void onActionUpdate(UpdateActionEvent event) {
      if (event.isSneakState()) {
         event.setSneakState(false);
      }

   }

   @Listener(MoveEvent.class)
   public final void onMove(MoveEvent event) {
      EntityPlayerSP player = mc.thePlayer;
      if (!PlayerUtils.isInLiquid()) {
         if (player.isMoving()) {
            double rounded;
            double difference;
            label184:
            switch((SpeedMod.Mode)this.mode.getValue()) {
            case NCP:
               double xDist = Wrapper.getPlayer().posX - Wrapper.getPlayer().lastTickPosX;
               double zDist = Wrapper.getPlayer().posZ - Wrapper.getPlayer().lastTickPosZ;

               lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
               break;
            case HYPIXEL:
               break;
            case PACKETHOP:
               switch(this.stage) {
               case 2:
                  mc.timer.timerSpeed = 2.0F;
                  this.moveSpeed = 0.0D;
                  break label184;
               case 3:
                  mc.timer.timerSpeed = 1.0F;
                  if (player.onGround && player.isCollidedVertically) {
                     event.y = player.motionY = MovementUtils.getJumpBoostModifier(0.41999998688697815D);
                     this.moveSpeed = MovementUtils.getBaseMoveSpeed() * 2.149D;
                  }
                  break label184;
               case 4:
                  rounded = 0.5600000023841858D * (this.lastDist - MovementUtils.getBaseMoveSpeed());
                  this.moveSpeed = this.lastDist - rounded;
                  break label184;
               }

               if (mc.theWorld.getCollidingBoundingBoxes(player, player.getEntityBoundingBox().offset(0.0D, player.motionY, 0.0D)).size() > 0 || player.isCollidedVertically && player.onGround) {
                  this.stage = 1;
                  mc.timer.timerSpeed = 1.085F;
               }

               if (player.motionY < 0.0D) {
                  player.motionY *= 1.1D;
               }

               this.moveSpeed = this.lastDist - this.lastDist / 50.0D;
               break;
            case MINEPLEX:
               switch(this.stage) {
               case 2:
                  if (player.onGround && player.isCollidedVertically) {
                     this.moveSpeed = 0.0D;
                  }

                  mc.timer.timerSpeed = 2.5F;
                  break;
               case 3:
                  mc.timer.timerSpeed = 1.0F;
                  this.moveSpeed = Math.min(0.3D * (double)this.hops, 0.97D);
                  break;
               default:
                  if (mc.theWorld.getCollidingBoundingBoxes(player, player.getEntityBoundingBox().offset(0.0D, player.motionY, 0.0D)).size() > 0 || player.isCollidedVertically && player.onGround) {
                     this.stage = 1;
                     ++this.hops;
                  }

                  this.moveSpeed -= 0.01D;
               }

               if (this.stage != 2) {
                  this.moveSpeed = Math.max(this.moveSpeed, MovementUtils.getBaseMoveSpeed());
               }
               break;
            case VHOP:
                rounded = MathUtils.round(player.posY - (double)((int)player.posY), 3.0D);
                if (rounded == MathUtils.round(0.4D, 3.0D)) {
                   event.y = player.motionY = 0.31D;
                } else if (rounded == MathUtils.round(0.71D, 3.0D)) {
                   event.y = player.motionY = 0.04D;
                } else if (rounded == MathUtils.round(0.75D, 3.0D)) {
                   event.y = player.motionY = -0.2D;
                } else if (rounded == MathUtils.round(0.55D, 3.0D)) {
                   event.y = player.motionY = -0.14D;
                } else if (rounded == MathUtils.round(0.41D, 3.0D)) {
                   event.y = player.motionY = -0.2D;
                }

                switch(this.stage) {
                case 0:
                   if (player.onGround && player.isCollidedVertically) {
                      this.moveSpeed = MovementUtils.getBaseMoveSpeed() * 1.3D;
                   }
                   break;
                case 1:
                default:
                   if (mc.theWorld.getCollidingBoundingBoxes(player, player.getEntityBoundingBox().offset(0.0D, player.motionY, 0.0D)).size() > 0 || player.isCollidedVertically && player.onGround) {
                      this.stage = 1;
                   }

                   this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
                   break;
                case 2:
                   if (player.onGround && player.isCollidedVertically) {
                      event.y = player.motionY = MovementUtils.getJumpBoostModifier(0.40001D);
                      this.moveSpeed *= 1.8D;
                   }

                   //mc.timer.timerSpeed = 1.3F;
                   break;
                case 3:
                   //mc.timer.timerSpeed = 1.15F;
                   difference = 0.72D * (this.lastDist - MovementUtils.getBaseMoveSpeed());
                   this.moveSpeed = this.lastDist - difference - 0.25f;
                }

                mc.timer.timerSpeed = 1.1F;
                this.moveSpeed = Math.max(this.moveSpeed, MovementUtils.getBaseMoveSpeed());
               break;
            case GROUND:
               mc.timer.timerSpeed = 1.25f;
               break;
            case BHOP:
               if (player.onGround && player.isCollidedVertically) {
                  event.y = player.motionY = MovementUtils.getJumpBoostModifier(0.41999998688697815D);
                  this.moveSpeed = (Double)this.bhopSpeed.getValue() * MovementUtils.getBaseMoveSpeed();
               } else {
                  this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
               }

               this.moveSpeed = Math.max(this.moveSpeed, MovementUtils.getBaseMoveSpeed());
            }

            MovementUtils.setSpeed(event, this.moveSpeed);

            ++this.stage;
         }

      }
   }

   @Listener(MotionUpdateEvent.class)
   public final void onMotionUpdate(MotionUpdateEvent event) {
      if (event.isPre()) {
         if (PlayerUtils.isInLiquid()) {
            return;
         }

         EntityPlayerSP player = mc.thePlayer;
         if (player.isMoving()) {
            if (this.mode.getValue() == SpeedMod.Mode.HYPIXEL || this.mode.getValue() == Mode.NCP) {
               mc.thePlayer.setSprinting(true);
                if (mc.thePlayer.onGround) {
                    if (mc.thePlayer.isMoving()) {
                        mc.thePlayer.motionY = 0.41f;
                        stage = 0;
                        speed = 25f;
                    } else {
                    	MovementUtils.setSpeed(0);
                    }
                } else {
                    MovementUtils.setSpeed(speed * MovementUtils.getBaseMoveSpeed());
                }
            } else if (this.mode.getValue() == SpeedMod.Mode.MINEPLEX) {
               mc.thePlayer.stepHeight = 0.0F;
               if (mc.thePlayer.isCollidedHorizontally) {
                  this.moveSpeed = 0.0D;
                  this.hops = 1;
               }

               if (mc.thePlayer.fallDistance < 8.0F) {
                  mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.4D, mc.thePlayer.posZ);
                  EntityPlayerSP var10000 = mc.thePlayer;
                  var10000.motionY += 0.02D;
               } else {
                  this.moveSpeed = 0.0D;
                  this.hops = 1;
                  mc.thePlayer.motionY = -1.0D;
               }
            } else if (this.mode.getValue() == SpeedMod.Mode.GROUND && this.canSpeed()) {
               //event.setOnGround(this.stage == 5);
               //event.setPosY(mc.thePlayer.posY + this.y + 7.435E-4D);
            }
         }

         double xDist = player.posX - player.prevPosX;
         double zDist = player.posZ - player.prevPosZ;
         this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
      }

   }

   private boolean canSpeed() {
      Block blockBelow = mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock();
      return mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isPressed() && blockBelow != Blocks.stone_stairs && blockBelow != Blocks.oak_stairs && blockBelow != Blocks.sandstone_stairs && blockBelow != Blocks.nether_brick_stairs && blockBelow != Blocks.spruce_stairs && blockBelow != Blocks.stone_brick_stairs && blockBelow != Blocks.birch_stairs && blockBelow != Blocks.jungle_stairs && blockBelow != Blocks.acacia_stairs && blockBelow != Blocks.brick_stairs && blockBelow != Blocks.dark_oak_stairs && blockBelow != Blocks.quartz_stairs && blockBelow != Blocks.red_sandstone_stairs && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 2.0D, mc.thePlayer.posZ)).getBlock() == Blocks.air;
   }

   public boolean isOnGround(final double n) {
      return !this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.getEntityBoundingBox().offset(0.0, -n, 0.0)).isEmpty();
   }

   private static enum Mode {
      HYPIXEL,
      MINEPLEX,
      VHOP,
      NCP,
      BHOP,
      PACKETHOP,
      GROUND;
   }
}
