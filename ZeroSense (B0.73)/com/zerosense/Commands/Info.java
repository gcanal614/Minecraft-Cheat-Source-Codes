package com.zerosense.Commands;

import com.zerosense.Commands.Manager.Command;
import com.zerosense.ZeroSense;


public class Info extends Command {
    public Info() {
        super("help", "get info", "getting info", "help");
    }

    @Override
    public void onCommand(String[] args, String command) {
        ZeroSense.addChatMessage("-------------------------");
        ZeroSense.addChatMessage("+help getting commands");
        ZeroSense.addChatMessage("+t <name> toggle module");
        ZeroSense.addChatMessage("+bind <name> bind module");
        ZeroSense.addChatMessage("More soon...");
        ZeroSense.addChatMessage("-------------------------");
    }
}
