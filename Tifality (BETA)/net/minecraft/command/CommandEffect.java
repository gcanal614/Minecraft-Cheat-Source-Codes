/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

public class CommandEffect
extends CommandBase {
    @Override
    public String getCommandName() {
        return "effect";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.effect.usage";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        int i;
        if (args2.length < 2) {
            throw new WrongUsageException("commands.effect.usage", new Object[0]);
        }
        EntityLivingBase entitylivingbase = CommandEffect.getEntity(sender, args2[0], EntityLivingBase.class);
        if (args2[1].equals("clear")) {
            if (entitylivingbase.getActivePotionEffects().isEmpty()) {
                throw new CommandException("commands.effect.failure.notActive.all", entitylivingbase.getCommandSenderName());
            }
            entitylivingbase.clearActivePotions();
            CommandEffect.notifyOperators(sender, (ICommand)this, "commands.effect.success.removed.all", entitylivingbase.getCommandSenderName());
            return;
        }
        try {
            i = CommandEffect.parseInt(args2[1], 1);
        }
        catch (NumberInvalidException numberinvalidexception) {
            Potion potion = Potion.getPotionFromResourceLocation(args2[1]);
            if (potion == null) {
                throw numberinvalidexception;
            }
            i = potion.id;
        }
        int j = 600;
        int l = 30;
        int k = 0;
        if (i < 0 || i >= Potion.potionTypes.length || Potion.potionTypes[i] == null) throw new NumberInvalidException("commands.effect.notFound", i);
        Potion potion1 = Potion.potionTypes[i];
        if (args2.length >= 3) {
            l = CommandEffect.parseInt(args2[2], 0, 1000000);
            j = potion1.isInstant() ? l : l * 20;
        } else if (potion1.isInstant()) {
            j = 1;
        }
        if (args2.length >= 4) {
            k = CommandEffect.parseInt(args2[3], 0, 255);
        }
        boolean flag = true;
        if (args2.length >= 5 && "true".equalsIgnoreCase(args2[4])) {
            flag = false;
        }
        if (l > 0) {
            PotionEffect potioneffect = new PotionEffect(i, j, k, false, flag);
            entitylivingbase.addPotionEffect(potioneffect);
            CommandEffect.notifyOperators(sender, (ICommand)this, "commands.effect.success", new ChatComponentTranslation(potioneffect.getEffectName(), new Object[0]), i, k, entitylivingbase.getCommandSenderName(), l);
            return;
        } else {
            if (!entitylivingbase.isPotionActive(i)) throw new CommandException("commands.effect.failure.notActive", new ChatComponentTranslation(potion1.getName(), new Object[0]), entitylivingbase.getCommandSenderName());
            entitylivingbase.removePotionEffect(i);
            CommandEffect.notifyOperators(sender, (ICommand)this, "commands.effect.success.removed", new ChatComponentTranslation(potion1.getName(), new Object[0]), entitylivingbase.getCommandSenderName());
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        return args2.length == 1 ? CommandEffect.getListOfStringsMatchingLastWord(args2, this.getAllUsernames()) : (args2.length == 2 ? CommandEffect.getListOfStringsMatchingLastWord(args2, Potion.func_181168_c()) : (args2.length == 5 ? CommandEffect.getListOfStringsMatchingLastWord(args2, "true", "false") : null));
    }

    protected String[] getAllUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    @Override
    public boolean isUsernameIndex(String[] args2, int index) {
        return index == 0;
    }
}

