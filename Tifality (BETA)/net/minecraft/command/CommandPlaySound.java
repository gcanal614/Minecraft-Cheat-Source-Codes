/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class CommandPlaySound
extends CommandBase {
    @Override
    public String getCommandName() {
        return "playsound";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.playsound.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        if (args2.length < 2) {
            throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
        }
        int i = 0;
        String s = args2[i++];
        EntityPlayerMP entityplayermp = CommandPlaySound.getPlayer(sender, args2[i++]);
        Vec3 vec3 = sender.getPositionVector();
        double d0 = vec3.xCoord;
        if (args2.length > i) {
            d0 = CommandPlaySound.parseDouble(d0, args2[i++], true);
        }
        double d1 = vec3.yCoord;
        if (args2.length > i) {
            d1 = CommandPlaySound.parseDouble(d1, args2[i++], 0, 0, false);
        }
        double d2 = vec3.zCoord;
        if (args2.length > i) {
            d2 = CommandPlaySound.parseDouble(d2, args2[i++], true);
        }
        double d3 = 1.0;
        if (args2.length > i) {
            d3 = CommandPlaySound.parseDouble(args2[i++], 0.0, 3.4028234663852886E38);
        }
        double d4 = 1.0;
        if (args2.length > i) {
            d4 = CommandPlaySound.parseDouble(args2[i++], 0.0, 2.0);
        }
        double d5 = 0.0;
        if (args2.length > i) {
            d5 = CommandPlaySound.parseDouble(args2[i], 0.0, 1.0);
        }
        double d6 = d3 > 1.0 ? d3 * 16.0 : 16.0;
        double d7 = entityplayermp.getDistance(d0, d1, d2);
        if (d7 > d6) {
            if (d5 <= 0.0) {
                throw new CommandException("commands.playsound.playerTooFar", entityplayermp.getCommandSenderName());
            }
            double d8 = d0 - entityplayermp.posX;
            double d9 = d1 - entityplayermp.posY;
            double d10 = d2 - entityplayermp.posZ;
            double d11 = Math.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
            if (d11 > 0.0) {
                d0 = entityplayermp.posX + d8 / d11 * 2.0;
                d1 = entityplayermp.posY + d9 / d11 * 2.0;
                d2 = entityplayermp.posZ + d10 / d11 * 2.0;
            }
            d3 = d5;
        }
        entityplayermp.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(s, d0, d1, d2, (float)d3, (float)d4));
        CommandPlaySound.notifyOperators(sender, (ICommand)this, "commands.playsound.success", s, entityplayermp.getCommandSenderName());
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        return args2.length == 2 ? CommandPlaySound.getListOfStringsMatchingLastWord(args2, MinecraftServer.getServer().getAllUsernames()) : (args2.length > 2 && args2.length <= 5 ? CommandPlaySound.func_175771_a(args2, 2, pos) : null);
    }

    @Override
    public boolean isUsernameIndex(String[] args2, int index) {
        return index == 1;
    }
}

