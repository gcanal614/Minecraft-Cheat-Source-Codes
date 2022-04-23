// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.combat;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import bozoware.impl.property.EnumProperty;
import bozoware.base.util.misc.TimerUtil;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Criticals", moduleCategory = ModuleCategory.COMBAT)
public class Criticals extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    int ticksSinceSendCritical;
    double[] watchdogOffsets;
    public TimerUtil timer;
    Integer a;
    private final EnumProperty<critmodes> Mode;
    
    public Criticals() {
        this.watchdogOffsets = new double[] { 0.05999999865889549, 0.009999999776482582 };
        this.timer = new TimerUtil();
        this.Mode = new EnumProperty<critmodes>("Mode", critmodes.Packet, this);
        this.setModuleSuffix(this.Mode.getPropertyValue().toString());
        this.onModuleEnabled = (() -> this.timer.reset());
        this.onPacketReceiveEvent = (PacketReceiveEvent -> {});
        this.onUpdatePositionEvent = (e -> {
            if (e.isPre) {
                ++this.ticksSinceSendCritical;
            }
            switch (this.Mode.getPropertyValue()) {
                case Packet: {
                    if (Criticals.mc.thePlayer.isSwingInProgress && Criticals.mc.thePlayer.isCollidedVertically && Criticals.mc.thePlayer.onGround && Criticals.mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
                        Criticals.mc.thePlayer.yC04(0.0625, false);
                        Criticals.mc.thePlayer.yC04(0.0, false);
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case NCP: {
                    if (Criticals.mc.thePlayer.isSwingInProgress && Criticals.mc.thePlayer.isCollidedVertically && Criticals.mc.thePlayer.onGround) {
                        Label_0238_2: {
                            if (Aura.target == null) {
                                if (!(Criticals.mc.objectMouseOver.entityHit instanceof EntityLivingBase) || Criticals.mc.objectMouseOver.entityHit.hurtResistantTime == 20) {
                                    break Label_0238_2;
                                }
                            }
                            else if (Aura.target.hurtResistantTime == 20) {
                                break Label_0238_2;
                            }
                            e.setY(e.getY() + 0.003);
                        }
                        if (Criticals.mc.thePlayer.ticksExisted % 10 == 0) {
                            e.setY(e.getY() + 0.001);
                        }
                        e.setOnGround(false);
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case Verus: {
                    if (Criticals.mc.thePlayer.isSwingInProgress && Criticals.mc.thePlayer.isCollidedVertically && Criticals.mc.thePlayer.onGround && Criticals.mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
                        Criticals.mc.thePlayer.yC04(0.11, false);
                        Criticals.mc.thePlayer.yC04(0.1100013579, false);
                        Criticals.mc.thePlayer.yC04(1.3579E-6, false);
                        Criticals.mc.thePlayer.yC04(1.3579E-6, false);
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        this.Mode.onValueChange = (() -> this.setModuleSuffix(this.Mode.getPropertyValue().name()));
    }
    
    public int getPing() {
        final NetworkPlayerInfo info = Criticals.mc.getNetHandler().getPlayerInfo(Criticals.mc.thePlayer.getUniqueID());
        return (info == null) ? 0 : ((int)Math.ceil(info.getResponseTime() / 50.0));
    }
    
    private enum critmodes
    {
        Packet, 
        NCP, 
        Verus;
    }
}
