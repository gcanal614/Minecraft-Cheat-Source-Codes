/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.command;

import de.fanta.Client;
import de.fanta.utils.ChatUtil;

public class Command {
    private String name;

    public Command(String name) {
        this.name = name;
    }

    public void execute(String[] args) {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void messageWithPrefix(String msg) {
        ChatUtil.messageWithoutPrefix(String.valueOf(Client.INSTANCE.prefix) + msg);
    }
}

