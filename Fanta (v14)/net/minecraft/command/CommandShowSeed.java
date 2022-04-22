/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class CommandShowSeed
extends CommandBase {
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUseCommand(sender);
    }

    @Override
    public String getCommandName() {
        return "seed";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.seed.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        World world;
        if (sender instanceof EntityPlayer) {
            EntityPlayer cfr_ignored_0 = (EntityPlayer)sender;
            world = EntityPlayer.worldObj;
        } else {
            world = MinecraftServer.getServer().worldServerForDimension(0);
        }
        World world2 = world;
        sender.addChatMessage(new ChatComponentTranslation("commands.seed.success", world2.getSeed()));
    }
}

