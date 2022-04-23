// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.world;

import java.util.ArrayList;
import net.minecraft.potion.Potion;
import java.util.Iterator;
import bozoware.base.BozoWare;
import bozoware.impl.module.combat.AntiBot;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import bozoware.base.util.misc.TimerUtil;
import java.util.List;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Hacker Detect (WIP)", moduleCategory = ModuleCategory.WORLD)
public class HackerDetector extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> event;
    public static List<Integer> flaggedPlayers;
    TimerUtil flyStopWatch;
    boolean possibleFlying;
    
    public HackerDetector() {
        this.flyStopWatch = new TimerUtil();
        this.possibleFlying = false;
        this.onModuleEnabled = (() -> {
            this.possibleFlying = false;
            this.flyStopWatch.reset();
            return;
        });
        final Iterator<Entity> iterator;
        Entity en;
        int i;
        this.event = (e -> {
            HackerDetector.mc.theWorld.loadedEntityList.iterator();
            while (iterator.hasNext()) {
                en = iterator.next();
                if (en instanceof EntityOtherPlayerMP && !HackerDetector.flaggedPlayers.contains(en.getEntityId()) && !AntiBot.botList.contains(en.getEntityId())) {
                    if (en.motionY == 0.0) {
                        for (i = 0; i <= 128; ++i) {
                            if (HackerDetector.mc.theWorld.getCollidingBoundingBoxes(en, en.getEntityBoundingBox().offset(0.0, -i, 0.0)).isEmpty()) {
                                this.possibleFlying = true;
                            }
                            else {
                                this.possibleFlying = false;
                            }
                            if (this.flyStopWatch.hasReached(2500L) && this.possibleFlying && HackerDetector.mc.thePlayer.isMoving()) {
                                if (!HackerDetector.flaggedPlayers.contains(en.getEntityId())) {
                                    BozoWare.getInstance().chat(en.getName() + " might be using fly");
                                }
                                HackerDetector.flaggedPlayers.add(en.getEntityId());
                            }
                        }
                    }
                    else {
                        this.flyStopWatch.reset();
                    }
                }
                if (en.rotationPitch > 90.0f || en.rotationPitch < -90.0f) {
                    HackerDetector.flaggedPlayers.add(en.getEntityId());
                    BozoWare.getInstance().chat(en.getName() + " might be using scaffold (bad rotations L)");
                }
            }
        });
    }
    
    public static double getBaseMoveSpeed(final EntityOtherPlayerMP en) {
        double baseSpeed = 0.2875;
        if (en != null && en.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = en.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    public static double getMoveSpeed(final EntityOtherPlayerMP en) {
        return Math.sqrt(en.motionX * en.motionX + en.motionZ * en.motionZ);
    }
    
    static {
        HackerDetector.flaggedPlayers = new ArrayList<Integer>();
    }
}
