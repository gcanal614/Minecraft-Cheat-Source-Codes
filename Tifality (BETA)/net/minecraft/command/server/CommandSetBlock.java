/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.command.server;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandSetBlock
extends CommandBase {
    @Override
    public String getCommandName() {
        return "setblock";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.setblock.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args2) throws CommandException {
        TileEntity tileentity;
        IBlockState iblockstate;
        TileEntity tileentity1;
        World world;
        if (args2.length < 4) {
            throw new WrongUsageException("commands.setblock.usage", new Object[0]);
        }
        sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
        BlockPos blockpos = CommandSetBlock.parseBlockPos(sender, args2, 0, false);
        Block block = CommandBase.getBlockByText(sender, args2[3]);
        int i = 0;
        if (args2.length >= 5) {
            i = CommandSetBlock.parseInt(args2[4], 0, 15);
        }
        if (!(world = sender.getEntityWorld()).isBlockLoaded(blockpos)) {
            throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
        }
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        boolean flag = false;
        if (args2.length >= 7 && block.hasTileEntity()) {
            String s = CommandSetBlock.getChatComponentFromNthArg(sender, args2, 6).getUnformattedText();
            try {
                nbttagcompound = JsonToNBT.getTagFromJson(s);
                flag = true;
            }
            catch (NBTException nbtexception) {
                throw new CommandException("commands.setblock.tagError", nbtexception.getMessage());
            }
        }
        if (args2.length >= 6) {
            if (args2[5].equals("destroy")) {
                world.destroyBlock(blockpos, true);
                if (block == Blocks.air) {
                    CommandSetBlock.notifyOperators(sender, (ICommand)this, "commands.setblock.success", new Object[0]);
                    return;
                }
            } else if (args2[5].equals("keep") && !world.isAirBlock(blockpos)) {
                throw new CommandException("commands.setblock.noChange", new Object[0]);
            }
        }
        if ((tileentity1 = world.getTileEntity(blockpos)) != null) {
            if (tileentity1 instanceof IInventory) {
                ((IInventory)((Object)tileentity1)).clear();
            }
            world.setBlockState(blockpos, Blocks.air.getDefaultState(), block == Blocks.air ? 2 : 4);
        }
        if (!world.setBlockState(blockpos, iblockstate = block.getStateFromMeta(i), 2)) {
            throw new CommandException("commands.setblock.noChange", new Object[0]);
        }
        if (flag && (tileentity = world.getTileEntity(blockpos)) != null) {
            nbttagcompound.setInteger("x", blockpos.getX());
            nbttagcompound.setInteger("y", blockpos.getY());
            nbttagcompound.setInteger("z", blockpos.getZ());
            tileentity.readFromNBT(nbttagcompound);
        }
        world.notifyNeighborsRespectDebug(blockpos, iblockstate.getBlock());
        sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
        CommandSetBlock.notifyOperators(sender, (ICommand)this, "commands.setblock.success", new Object[0]);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args2, BlockPos pos) {
        return args2.length > 0 && args2.length <= 3 ? CommandSetBlock.func_175771_a(args2, 0, pos) : (args2.length == 4 ? CommandSetBlock.getListOfStringsMatchingLastWord(args2, Block.blockRegistry.getKeys()) : (args2.length == 6 ? CommandSetBlock.getListOfStringsMatchingLastWord(args2, "replace", "destroy", "keep") : null));
    }
}

