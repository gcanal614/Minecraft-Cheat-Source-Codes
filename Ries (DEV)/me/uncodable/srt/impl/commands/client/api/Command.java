/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.commands.client.api;

import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.commands.client.api.CommandInfo;
import net.minecraft.client.Minecraft;

public abstract class Command {
    protected static final Minecraft MC = Minecraft.getMinecraft();
    private final CommandInfo info = this.getClass().getAnnotation(CommandInfo.class);

    public abstract void exec(String[] var1);

    protected void printUsage() {
        Ries.INSTANCE.msg(String.format("Invalid parameters/usage. Proper usage: %s", this.info.usage()));
    }

    public CommandInfo getInfo() {
        return this.info;
    }
}

