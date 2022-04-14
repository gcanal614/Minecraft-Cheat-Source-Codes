package net.minecraft.command.server;

import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import java.util.*;

public class CommandTestForBlock extends CommandBase
{
    public static boolean func_175775_a(final NBTBase p_175775_0_, final NBTBase p_175775_1_, final boolean p_175775_2_) {
        if (p_175775_0_ == p_175775_1_) {
            return true;
        }
        if (p_175775_0_ == null) {
            return true;
        }
        if (p_175775_1_ == null) {
            return false;
        }
        if (!p_175775_0_.getClass().equals(p_175775_1_.getClass())) {
            return false;
        }
        if (p_175775_0_ instanceof NBTTagCompound) {
            final NBTTagCompound var9 = (NBTTagCompound)p_175775_0_;
            final NBTTagCompound var10 = (NBTTagCompound)p_175775_1_;
            for (final String var12 : var9.getKeySet()) {
                final NBTBase var13 = var9.getTag(var12);
                if (!func_175775_a(var13, var10.getTag(var12), p_175775_2_)) {
                    return false;
                }
            }
            return true;
        }
        if (!(p_175775_0_ instanceof NBTTagList) || !p_175775_2_) {
            return p_175775_0_.equals(p_175775_1_);
        }
        final NBTTagList var14 = (NBTTagList)p_175775_0_;
        final NBTTagList var15 = (NBTTagList)p_175775_1_;
        if (var14.tagCount() == 0) {
            return var15.tagCount() == 0;
        }
        for (int var16 = 0; var16 < var14.tagCount(); ++var16) {
            final NBTBase var17 = var14.get(var16);
            boolean var18 = false;
            for (int var19 = 0; var19 < var15.tagCount(); ++var19) {
                if (func_175775_a(var17, var15.get(var19), p_175775_2_)) {
                    var18 = true;
                    break;
                }
            }
            if (!var18) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String getCommandName() {
        return "testforblock";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.testforblock.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 4) {
            throw new WrongUsageException("commands.testforblock.usage", new Object[0]);
        }
        sender.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
        final BlockPos var3 = CommandBase.func_175757_a(sender, args, 0, false);
        final Block var4 = Block.getBlockFromName(args[3]);
        if (var4 == null) {
            throw new NumberInvalidException("commands.setblock.notFound", new Object[] { args[3] });
        }
        int var5 = -1;
        if (args.length >= 5) {
            var5 = CommandBase.parseInt(args[4], -1, 15);
        }
        final World var6 = sender.getEntityWorld();
        if (!var6.isBlockLoaded(var3)) {
            throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
        }
        NBTTagCompound var7 = new NBTTagCompound();
        boolean var8 = false;
        if (args.length >= 6 && var4.hasTileEntity()) {
            final String var9 = CommandBase.getChatComponentFromNthArg(sender, args, 5).getUnformattedText();
            try {
                var7 = JsonToNBT.func_180713_a(var9);
                var8 = true;
            }
            catch (NBTException var10) {
                throw new CommandException("commands.setblock.tagError", new Object[] { var10.getMessage() });
            }
        }
        final IBlockState var11 = var6.getBlockState(var3);
        final Block var12 = var11.getBlock();
        if (var12 != var4) {
            throw new CommandException("commands.testforblock.failed.tile", new Object[] { var3.getX(), var3.getY(), var3.getZ(), var12.getLocalizedName(), var4.getLocalizedName() });
        }
        if (var5 > -1) {
            final int var13 = var11.getBlock().getMetaFromState(var11);
            if (var13 != var5) {
                throw new CommandException("commands.testforblock.failed.data", new Object[] { var3.getX(), var3.getY(), var3.getZ(), var13, var5 });
            }
        }
        if (var8) {
            final TileEntity var14 = var6.getTileEntity(var3);
            if (var14 == null) {
                throw new CommandException("commands.testforblock.failed.tileEntity", new Object[] { var3.getX(), var3.getY(), var3.getZ() });
            }
            final NBTTagCompound var15 = new NBTTagCompound();
            var14.writeToNBT(var15);
            if (!func_175775_a(var7, var15, true)) {
                throw new CommandException("commands.testforblock.failed.nbt", new Object[] { var3.getX(), var3.getY(), var3.getZ() });
            }
        }
        sender.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
        CommandBase.notifyOperators(sender, this, "commands.testforblock.success", var3.getX(), var3.getY(), var3.getZ());
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length > 0 && args.length <= 3) ? CommandBase.func_175771_a(args, 0, pos) : ((args.length == 4) ? CommandBase.func_175762_a(args, Block.blockRegistry.getKeys()) : null);
    }
}
