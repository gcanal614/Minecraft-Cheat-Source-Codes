/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.commands.metasploit.api;

import me.uncodable.srt.impl.commands.metasploit.api.MetasploitCommandInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public abstract class MetasploitCommand {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private final MetasploitCommandInfo info = this.getClass().getAnnotation(MetasploitCommandInfo.class);

    public abstract void exec(String[] var1);

    public void good(String msg) {
        MetasploitCommand.MC.thePlayer.addChatMessage(new ChatComponentText(String.format("\u00a75[\u00a7d\u00a7lMetasploit \u00a7d\u00a7lFramework\u00a75] %s", msg).replace(" ", " \u00a7a")));
    }

    public void bad(String msg) {
        MetasploitCommand.MC.thePlayer.addChatMessage(new ChatComponentText(String.format("\u00a75[\u00a7d\u00a7lMetasploit \u00a7d\u00a7lFramework\u00a75] %s", msg).replace(" ", " \u00a7c")));
    }

    public void info(String msg) {
        MetasploitCommand.MC.thePlayer.addChatMessage(new ChatComponentText(String.format("\u00a75[\u00a7d\u00a7lMetasploit \u00a7d\u00a7lFramework\u00a75] %s", msg).replace(" ", " \u00a79")));
    }

    public MetasploitCommandInfo getInfo() {
        return this.info;
    }
}

