package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;

public class BlockJukebox extends BlockContainer
{
    public static final PropertyBool HAS_RECORD;
    
    protected BlockJukebox() {
        super(Material.wood);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockJukebox.HAS_RECORD, false));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onBlockActivated(final World worldIn, final BlockPos pos, IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (state.getValue(BlockJukebox.HAS_RECORD)) {
            this.dropRecord(worldIn, pos, state);
            state = state.withProperty(BlockJukebox.HAS_RECORD, false);
            worldIn.setBlockState(pos, state, 2);
            return true;
        }
        return false;
    }
    
    public void insertRecord(final World worldIn, final BlockPos pos, final IBlockState state, final ItemStack recordStack) {
        if (!worldIn.isRemote) {
            final TileEntity var5 = worldIn.getTileEntity(pos);
            if (var5 instanceof TileEntityJukebox) {
                ((TileEntityJukebox)var5).setRecord(new ItemStack(recordStack.getItem(), 1, recordStack.getMetadata()));
                worldIn.setBlockState(pos, state.withProperty(BlockJukebox.HAS_RECORD, true), 2);
            }
        }
    }
    
    private void dropRecord(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!worldIn.isRemote) {
            final TileEntity var4 = worldIn.getTileEntity(pos);
            if (var4 instanceof TileEntityJukebox) {
                final TileEntityJukebox var5 = (TileEntityJukebox)var4;
                final ItemStack var6 = var5.getRecord();
                if (var6 != null) {
                    worldIn.playAuxSFX(1005, pos, 0);
                    worldIn.func_175717_a(pos, null);
                    var5.setRecord(null);
                    final float var7 = 0.7f;
                    final double var8 = worldIn.rand.nextFloat() * var7 + (1.0f - var7) * 0.5;
                    final double var9 = worldIn.rand.nextFloat() * var7 + (1.0f - var7) * 0.2 + 0.6;
                    final double var10 = worldIn.rand.nextFloat() * var7 + (1.0f - var7) * 0.5;
                    final ItemStack var11 = var6.copy();
                    final EntityItem var12 = new EntityItem(worldIn, pos.getX() + var8, pos.getY() + var9, pos.getZ() + var10, var11);
                    var12.setDefaultPickupDelay();
                    worldIn.spawnEntityInWorld(var12);
                }
            }
        }
    }
    
    @Override
    public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.dropRecord(worldIn, pos, state);
        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        if (!worldIn.isRemote) {
            super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World worldIn, final int meta) {
        return new TileEntityJukebox();
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World worldIn, final BlockPos pos) {
        final TileEntity var3 = worldIn.getTileEntity(pos);
        if (var3 instanceof TileEntityJukebox) {
            final ItemStack var4 = ((TileEntityJukebox)var3).getRecord();
            if (var4 != null) {
                return Item.getIdFromItem(var4.getItem()) + 1 - Item.getIdFromItem(Items.record_13);
            }
        }
        return 0;
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(BlockJukebox.HAS_RECORD, meta > 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        return ((boolean)state.getValue(BlockJukebox.HAS_RECORD)) ? 1 : 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockJukebox.HAS_RECORD });
    }
    
    static {
        HAS_RECORD = PropertyBool.create("has_record");
    }
    
    public static class TileEntityJukebox extends TileEntity
    {
        private ItemStack record;
        
        @Override
        public void readFromNBT(final NBTTagCompound compound) {
            super.readFromNBT(compound);
            if (compound.hasKey("RecordItem", 10)) {
                this.setRecord(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("RecordItem")));
            }
            else if (compound.getInteger("Record") > 0) {
                this.setRecord(new ItemStack(Item.getItemById(compound.getInteger("Record")), 1, 0));
            }
        }
        
        @Override
        public void writeToNBT(final NBTTagCompound compound) {
            super.writeToNBT(compound);
            if (this.getRecord() != null) {
                compound.setTag("RecordItem", this.getRecord().writeToNBT(new NBTTagCompound()));
            }
        }
        
        public ItemStack getRecord() {
            return this.record;
        }
        
        public void setRecord(final ItemStack recordStack) {
            this.record = recordStack;
            this.markDirty();
        }
    }
}
