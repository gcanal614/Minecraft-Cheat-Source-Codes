/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.command.impl;

import de.fanta.command.Command;
import de.fanta.utils.ChatUtil;
import de.fanta.utils.FileUtil;
import de.fanta.utils.FriendSystem;

public class FriendCommand
extends Command {
    public FriendCommand() {
        super("friend");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            ChatUtil.sendChatError("Please use: \u00a7e.friend <add | remove> <Player> \u00a77| \u00a7e.friend <list>");
            return;
        }
        if (args[0].equalsIgnoreCase("add")) {
            if (!FriendSystem.getFriends().contains(args[1])) {
                FriendSystem.addFriend(args[1]);
                FriendCommand.messageWithPrefix("\u00a7aSuccessfully added " + args[1]);
                ChatUtil.messageWithoutPrefix(FriendSystem.getFriends().toString());
                FileUtil.saveFriends();
            } else {
                ChatUtil.sendChatError("Player already added.");
            }
        } else if (args[0].equalsIgnoreCase("remove")) {
            if (FriendSystem.getFriends().contains(args[1])) {
                FriendSystem.removeFriend(args[1]);
                FriendCommand.messageWithPrefix("\u00a7aSuccessfully removed " + args[1]);
                FileUtil.saveFriends();
            } else {
                ChatUtil.sendChatError("Player not added");
            }
        } else if (args[0].equalsIgnoreCase("list")) {
            FriendCommand.messageWithPrefix("\u00a75" + FriendSystem.getFriends().toString());
        } else {
            ChatUtil.sendChatError("Please use: \u00a7e.friend <add | remove> <Player> \u00a77| \u00a7e.friend <list>");
        }
    }
}

