/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command.server;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.IPBanEntry;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;

public class CommandBanIp
extends CommandBase {
    public static final Pattern field_147211_a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    @Override
    public String getCommandName() {
        return "ban-ip";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() && super.canCommandSenderUseCommand(sender);
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.banip.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        if (args2.length >= 1 && args2[0].length() > 1) {
            IChatComponent ichatcomponent = args2.length >= 2 ? CommandBanIp.getChatComponentFromNthArg(sender, args2, 1) : null;
            Matcher matcher = field_147211_a.matcher(args2[0]);
            if (matcher.matches()) {
                this.func_147210_a(sender, args2[0], ichatcomponent == null ? null : ichatcomponent.getUnformattedText());
            } else {
                EntityPlayerMP entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(args2[0]);
                if (entityplayermp == null) {
                    throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
                }
                this.func_147210_a(sender, entityplayermp.getPlayerIP(), ichatcomponent == null ? null : ichatcomponent.getUnformattedText());
            }
        } else {
            throw new WrongUsageException("commands.banip.usage", new Object[0]);
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        return args2.length == 1 ? CommandBanIp.getListOfStringsMatchingLastWord(args2, MinecraftServer.getServer().getAllUsernames()) : null;
    }

    protected void func_147210_a(ICommandSender p_147210_1_, String p_147210_2_, String p_147210_3_) {
        IPBanEntry ipbanentry = new IPBanEntry(p_147210_2_, (Date)null, p_147210_1_.getCommandSenderName(), (Date)null, p_147210_3_);
        MinecraftServer.getServer().getConfigurationManager().getBannedIPs().addEntry(ipbanentry);
        List<EntityPlayerMP> list = MinecraftServer.getServer().getConfigurationManager().getPlayersMatchingAddress(p_147210_2_);
        Object[] astring = new String[list.size()];
        int i = 0;
        for (EntityPlayerMP entityplayermp : list) {
            entityplayermp.playerNetServerHandler.kickPlayerFromServer("You have been IP banned.");
            astring[i++] = entityplayermp.getCommandSenderName();
        }
        if (list.isEmpty()) {
            CommandBanIp.notifyOperators(p_147210_1_, (ICommand)this, "commands.banip.success", p_147210_2_);
        } else {
            CommandBanIp.notifyOperators(p_147210_1_, (ICommand)this, "commands.banip.success.players", p_147210_2_, CommandBanIp.joinNiceString(astring));
        }
    }
}

