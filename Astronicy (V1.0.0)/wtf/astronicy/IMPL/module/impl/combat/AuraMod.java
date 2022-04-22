package wtf.astronicy.IMPL.module.impl.combat;

import wtf.astronicy.Astronicy;
import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.player.MotionUpdateEvent;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.world.Scaffold.Scaffold;
import wtf.astronicy.IMPL.module.ModuleManager;
import wtf.astronicy.IMPL.module.impl.Module;

import wtf.astronicy.IMPL.module.registery.Aliases;
import wtf.astronicy.IMPL.module.registery.Bind;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import wtf.astronicy.IMPL.module.options.Option;
import wtf.astronicy.IMPL.module.options.impl.BoolOption;
import wtf.astronicy.IMPL.module.options.impl.DoubleOption;
import wtf.astronicy.IMPL.module.options.impl.EnumOption;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import wtf.astronicy.IMPL.utils.RotationUtils;
import wtf.astronicy.IMPL.utils.TimerUtility;

@ModName("Aura")
@Bind("R")
@Category(ModuleCategory.COMBAT)
@Aliases({"aura", "killaura", "ka"})
public final class AuraMod extends Module {

   float[] serversideRotations;
   private final TimerUtility attackStopwatch;

   public static boolean isBlocking;
   public Entity target;

   public DoubleOption aps = new DoubleOption("APS", 10D, 1D, 20D, 1.0D);
   public DoubleOption range = new DoubleOption("Range", 4.2D, 3.0D, 7.0D, 0.1D);
   public BoolOption autoBlock = new BoolOption("AutoBlock", true);
   public EnumOption blockMode = new EnumOption("Block Mode", BlockMode.FAKE, () -> autoBlock.getValue());
   public EnumOption atackMode = new EnumOption("Atack Mode", AtackMode.PLAYER);

   public long finalAPS = ((Double)this.range.getValue()).longValue() * 1000;

   @Listener(MotionUpdateEvent.class)
   public void nigerian(MotionUpdateEvent event){
      target = getClosest();

      if(target != null){

         double jitter = (Math.signum(Math.random() - 0.5f)) * 4.66f;

         float yaw = (float) (RotationUtils.getRotations((EntityLivingBase) target)[0] + jitter);
         float pitch = (float) (RotationUtils.getRotations((EntityLivingBase) target)[1] + jitter);
         event.setYaw(yaw);
         event.setPitch(pitch);

         mc.thePlayer.renderYawOffset = yaw;
         mc.thePlayer.rotationYawHead = yaw;
         mc.thePlayer.prevRenderYawOffset = yaw;

         if (this.canAttack()) {
            if (this.attackStopwatch.elapsed((long)(1000 / ((Double)this.aps.getValue()).intValue())) && this.canAttack()) {
               this.attack((EntityLivingBase) target);
               if (autoBlock.getValue()) {
                  executeAutoBlock();
               }
               this.attackStopwatch.reset();
            }
         } else {
            isBlocking = false;
         }
      } else {
         isBlocking = false;
      }
   }

   private void attack(EntityLivingBase entity) {

      EntityPlayerSP player = mc.thePlayer;
      NetHandlerPlayClient netHandler = mc.getNetHandler();
      ModuleManager mm = Astronicy.MANAGER_REGISTRY.moduleManager;

      player.swingItem();

      switch ((AtackMode) atackMode.getValue()) {
         case PACKET:
            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
            break;
         case PLAYER:
            mc.playerController.attackEntity(mc.thePlayer, entity);
            break;
      }
   }

   @Override
   public void onEnabled(){
      super.onEnabled();
      isBlocking = false;
      target = null;
   }

   @Override
   public void onDisabled(){
      super.onEnabled();
      isBlocking = false;
      target = null;
   }

   private Entity getClosest(){
      Entity finalTarget = null;
      for(Entity e : mc.theWorld.getLoadedEntityList()){
         if(checkEntity(e)){
            if(finalTarget == null){
               finalTarget = e;
            } else {
               if(mc.thePlayer.getDistanceToEntity(e) < mc.thePlayer.getDistanceToEntity(finalTarget)){
                  finalTarget = e;
               }
            }
         }
      }
      return finalTarget;
   }

   private void executeAutoBlock(){
      switch ((BlockMode)blockMode.getValue()) {
         case FAKE:
            isBlocking = true;
            break;
         case WATCHDOG:
            isBlocking = true;
            break;
      }
   }

   private void executeAutoUnBlock(){
      switch ((BlockMode)blockMode.getValue()) {
         case FAKE:
            isBlocking = false;
            break;
         case WATCHDOG:
            isBlocking = false;
            break;
      }
   }

   private boolean checkEntity(Entity e){
      if(e != mc.thePlayer){
         if(e instanceof EntityLivingBase){
            if(mc.thePlayer.getDistanceToEntity(e) <= (double)((Double)this.range.getValue()).intValue()) {
               if (e instanceof EntityPlayer) {
                  return true;
               }
               if (e instanceof EntityAnimal) {
                  return false;
               }
               if (e instanceof EntityMob) {
                  return false;
               }
            }
         }
      }
      return false;
   }

   public AuraMod(){
      this.addOptions(new Option[]{aps, range, autoBlock, atackMode, blockMode});
      this.attackStopwatch = new TimerUtility();

   }

   public static enum BlockMode{
      WATCHDOG,
      FAKE;
   }
   public static enum AtackMode{
      PLAYER,
      PACKET;
   }

   private boolean canAttack() {
      Scaffold scaffoldMod = new Scaffold();
      return !scaffoldMod.isEnabled();
   }

}