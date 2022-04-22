/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package de.fanta.command.impl;

import de.fanta.Client;
import de.fanta.command.Command;
import de.fanta.module.Module;
import de.fanta.utils.FileUtil;
import org.lwjgl.input.Keyboard;

public class Bind
extends Command {
    public Bind() {
        super("bind");
    }

    @Override
    public void execute(String[] args) {
        try {
            Module mod = Client.INSTANCE.moduleManager.getModule(args[0]);
            if (args.length != 2) {
                Bind.messageWithPrefix("\u00a77Bind \u00a78<\u00a7bModule\u00a78> \u00a78<\u00a7bButton\u00a78>\u00a7f");
                return;
            }
            String key = args[1];
            if (mod != null) {
                mod.setKeyBind(Keyboard.getKeyIndex((String)args[1].toUpperCase()));
                Bind.messageWithPrefix(" \u00a73The Module \u00a75\u00a7l" + mod.name + "\u00a7r\u00a73 was set on \u00a79\u00a7l" + key.toUpperCase() + "\u00a7r.");
                FileUtil.saveKeys();
            } else {
                Bind.messageWithPrefix(" \u00a7cThe Module \u00a75\u00a7l" + args[0] + "\u00a7r\u00a7c was not found :( .");
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            Bind.messageWithPrefix(" \u00a77Bind \u00a78<\u00a7bModule\u00a78> \u00a78<\u00a7bButton\u00a78>\u00a7f");
            e.printStackTrace();
        }
    }
}

