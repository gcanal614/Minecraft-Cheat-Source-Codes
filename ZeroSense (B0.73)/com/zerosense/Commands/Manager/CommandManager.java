package com.zerosense.Commands.Manager;

import com.zerosense.Commands.Info;
import com.zerosense.Commands.Toggle;
import com.zerosense.Events.impl.EventChat;
import com.zerosense.ZeroSense;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    public List<Command> commands = new ArrayList<Command>();
    public String prefix = "+";

    public CommandManager(){
        setup();
    }

    public void setup(){
        commands.add(new Toggle());
        commands.add(new Info());
    }

    public void handleChat(EventChat event){
        String message = event.getMessage();

        if(!message.startsWith(prefix))
            return;
        
        event.setCancelled(true);

        message = message.substring(prefix.length());

        boolean foundCommand = false;

        if(message.split(" ").length > 0){
            String commandName = message.split(" ")[0];

            for (Command c : commands){
                if(c.aliases.contains(commandName) || c.name.equalsIgnoreCase(commandName)){
                    c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
                    foundCommand = true;
                    break;
                }
            }
        }

        if(!foundCommand){
            ZeroSense.addChatMessage("Could not find Command.");
        }
    }
}
