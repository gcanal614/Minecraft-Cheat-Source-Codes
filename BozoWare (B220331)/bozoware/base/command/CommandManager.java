// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.command;

import bozoware.impl.command.WatermarkCommand;
import bozoware.impl.command.SpamCommand;
import bozoware.impl.command.HideCommand;
import bozoware.impl.command.BanCommand;
import bozoware.impl.command.SusCommand;
import bozoware.impl.command.ConfigCommand;
import bozoware.impl.command.TPCommand;
import bozoware.impl.command.HelpCommand;
import bozoware.impl.command.BozoCommand;
import bozoware.impl.command.BindCommand;
import bozoware.impl.command.CopyNameCommand;
import bozoware.impl.command.ToggleCommand;
import bozoware.impl.command.VClipCommand;
import java.util.Iterator;
import bozoware.base.BozoWare;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import bozoware.base.util.Wrapper;
import java.util.ArrayList;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.SendChatMessageEvent;
import bozoware.base.event.EventConsumer;

public class CommandManager
{
    private final String commandPrefix = ".";
    @EventListener
    EventConsumer<SendChatMessageEvent> onSendChatMessageEvent;
    private final ArrayList<Command> commands;
    
    public CommandManager() {
        this.commands = new ArrayList<Command>();
        String afterPrefix;
        String[] afterPrefixSplit;
        final Iterator<Command> iterator;
        Command command;
        final String[] array;
        int length;
        int i = 0;
        String alias;
        this.onSendChatMessageEvent = (chatEvent -> {
            if (chatEvent.getMessage().startsWith(".")) {
                chatEvent.setCancelled(true);
                afterPrefix = chatEvent.getMessage().substring(1);
                afterPrefixSplit = afterPrefix.split(" ");
                this.commands.iterator();
                while (iterator.hasNext()) {
                    command = iterator.next();
                    command.getAliases();
                    length = array.length;
                    while (i < length) {
                        alias = array[i];
                        if (afterPrefixSplit[0].equalsIgnoreCase(alias)) {
                            try {
                                command.execute(afterPrefixSplit);
                            }
                            catch (CommandArgumentException e) {
                                Wrapper.getPlayer().addChatMessage(new ChatComponentText(e.getMessage()));
                            }
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                }
            }
            return;
        });
        this.registerCommands();
        BozoWare.getInstance().getEventManager().subscribe(this);
    }
    
    private void registerCommands() {
        this.commands.add(new VClipCommand());
        this.commands.add(new ToggleCommand());
        this.commands.add(new CopyNameCommand());
        this.commands.add(new BindCommand());
        this.commands.add(new BozoCommand());
        this.commands.add(new HelpCommand());
        this.commands.add(new TPCommand());
        this.commands.add(new ConfigCommand());
        this.commands.add(new SusCommand());
        this.commands.add(new BanCommand());
        this.commands.add(new HideCommand());
        this.commands.add(new SpamCommand());
        this.commands.add(new WatermarkCommand());
    }
}
