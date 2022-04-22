/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.command.impl;

import de.fanta.command.Command;

public class Spec
extends Command {
    public static String name = " ";

    public Spec() {
        super("spec");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            Spec.messageWithPrefix("User Name");
            return;
        }
        name = args[0];
    }
}

