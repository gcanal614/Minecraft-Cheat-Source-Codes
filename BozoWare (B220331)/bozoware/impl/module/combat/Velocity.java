// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.combat;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Velocity", moduleCategory = ModuleCategory.COMBAT)
public class Velocity extends Module
{
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    private final EnumProperty<Modes> mode;
    private final ValueProperty<Double> stackSize;
    private final ValueProperty<Double> horizontalPercentageProperty;
    private final ValueProperty<Double> verticalPercentageProperty;
    public double stack;
    
    public Velocity() {
        this.mode = new EnumProperty<Modes>("Mode", Modes.Cancel, this);
        this.stackSize = new ValueProperty<Double>("Stack Size", 3.0, 2.0, 6.0, this);
        this.horizontalPercentageProperty = new ValueProperty<Double>("Horizontal", 0.0, 0.0, 100.0, this);
        this.verticalPercentageProperty = new ValueProperty<Double>("Vertical", 0.0, 0.0, 100.0, this);
        S27PacketExplosion s27;
        S12PacketEntityVelocity s28;
        S12PacketEntityVelocity s29;
        S27PacketExplosion s30;
        double x;
        double y;
        double z;
        this.onPacketReceiveEvent = (e -> {
            switch (this.mode.getPropertyValue()) {
                case Custom: {
                    if (e.getPacket() instanceof S27PacketExplosion) {
                        s27 = (S27PacketExplosion)e.getPacket();
                        s27.motionX *= (float)(this.horizontalPercentageProperty.getPropertyValue() / 100.0);
                        s27.motionY *= (float)(this.verticalPercentageProperty.getPropertyValue() / 100.0);
                        s27.motionZ *= (float)(this.horizontalPercentageProperty.getPropertyValue() / 100.0);
                    }
                    if (e.getPacket() instanceof S12PacketEntityVelocity) {
                        s28 = (S12PacketEntityVelocity)e.getPacket();
                        s28.motionX *= (int)(this.horizontalPercentageProperty.getPropertyValue() / 100.0);
                        s28.motionY *= (int)(this.verticalPercentageProperty.getPropertyValue() / 100.0);
                        s28.motionZ *= (int)(this.horizontalPercentageProperty.getPropertyValue() / 100.0);
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case Cancel: {
                    if (e.getPacket() instanceof S27PacketExplosion) {
                        e.setCancelled(true);
                    }
                    if (e.getPacket() instanceof S12PacketEntityVelocity) {
                        e.setCancelled(true);
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case Stack: {
                    if (e.getPacket() instanceof S12PacketEntityVelocity) {
                        if (this.stack < this.stackSize.getPropertyValue()) {
                            s29 = (S12PacketEntityVelocity)e.getPacket();
                            s29.motionX *= (int)(this.horizontalPercentageProperty.getPropertyValue() / 100.0);
                            s29.motionY *= (int)(this.verticalPercentageProperty.getPropertyValue() / 100.0);
                            s29.motionZ *= (int)(this.horizontalPercentageProperty.getPropertyValue() / 100.0);
                        }
                        if (this.stack >= this.stackSize.getPropertyValue()) {
                            e.setCancelled(false);
                            this.stack = 0.0;
                        }
                    }
                    if (e.getPacket() instanceof S27PacketExplosion && this.stack < this.stackSize.getPropertyValue()) {
                        s30 = (S27PacketExplosion)e.getPacket();
                        s30.motionX *= (float)(this.horizontalPercentageProperty.getPropertyValue() / 100.0);
                        s30.motionY *= (float)(this.verticalPercentageProperty.getPropertyValue() / 100.0);
                        s30.motionZ *= (float)(this.horizontalPercentageProperty.getPropertyValue() / 100.0);
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case RedeskyPhase: {
                    if (e.getPacket() instanceof S27PacketExplosion) {
                        e.setCancelled(true);
                    }
                    if (e.getPacket() instanceof S12PacketEntityVelocity) {
                        x = Velocity.mc.thePlayer.posX;
                        y = Velocity.mc.thePlayer.posY;
                        z = Velocity.mc.thePlayer.posZ;
                        if (Velocity.mc.thePlayer.hurtTime >= 8) {
                            Velocity.mc.thePlayer.setPositionAndUpdate(x, y - 0.10000000149011612, z);
                            break;
                        }
                        else {
                            e.setCancelled(true);
                            break;
                        }
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        this.onUpdatePositionEvent = (e -> {
            if (this.mode.getPropertyValue().equals(Modes.Stack)) {
                System.out.println(this.stack);
                if (Velocity.mc.thePlayer.hurtTime == 9) {
                    ++this.stack;
                }
            }
        });
    }
    
    public enum Modes
    {
        Cancel, 
        Custom, 
        Stack, 
        RedeskyPhase;
    }
}
