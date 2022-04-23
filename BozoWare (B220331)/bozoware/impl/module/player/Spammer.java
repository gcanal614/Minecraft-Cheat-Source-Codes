// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.client.entity.EntityPlayerSP;
import bozoware.impl.command.SpamCommand;
import bozoware.base.util.misc.TimerUtil;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Spammer", moduleCategory = ModuleCategory.PLAYER)
public class Spammer extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> event;
    private final ValueProperty<Long> spamDelay;
    private final BooleanProperty randomCharacters;
    static TimerUtil spamTimer;
    public static String spamMessage;
    
    public Spammer() {
        this.spamDelay = new ValueProperty<Long>("Spamm Delay", 3000L, 1L, 10000L, this);
        this.randomCharacters = new BooleanProperty("Random Chars", true, this);
        this.onModuleEnabled = (() -> Spammer.spamTimer.reset());
        String bozo;
        String[] numba;
        int i;
        EntityPlayerSP thePlayer;
        String message;
        String string;
        final StringBuilder sb;
        this.event = (e -> {
            if (e.isPre) {
                Spammer.spamMessage = SpamCommand.spamMessage;
                if (Spammer.spamTimer.hasReached(this.spamDelay.getPropertyValue())) {
                    bozo = "";
                    numba = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
                    for (i = 0; i < 13; ++i) {
                        bozo += numba[(int)Math.floor(Math.random() * numba.length)];
                    }
                    thePlayer = Spammer.mc.thePlayer;
                    if (this.randomCharacters.getPropertyValue()) {
                        message = "[" + bozo + "] " + Spammer.spamMessage;
                    }
                    else {
                        new StringBuilder().append(Spammer.spamMessage);
                        if (this.randomCharacters.getPropertyValue()) {
                            string = " [" + bozo + "] ";
                        }
                        else {
                            string = "";
                        }
                        message = sb.append(string).toString();
                    }
                    thePlayer.sendChatMessage(message);
                    Spammer.spamTimer.reset();
                }
            }
        });
    }
    
    static {
        Spammer.spamTimer = new TimerUtil();
        Spammer.spamMessage = SpamCommand.spamMessage;
    }
}
