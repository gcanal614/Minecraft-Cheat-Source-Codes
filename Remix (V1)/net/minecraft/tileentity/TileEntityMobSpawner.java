package net.minecraft.tileentity;

import net.minecraft.server.gui.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;

public class TileEntityMobSpawner extends TileEntity implements IUpdatePlayerListBox
{
    private final MobSpawnerBaseLogic field_145882_a;
    
    public TileEntityMobSpawner() {
        this.field_145882_a = new MobSpawnerBaseLogic() {
            @Override
            public void func_98267_a(final int p_98267_1_) {
                TileEntityMobSpawner.this.worldObj.addBlockEvent(TileEntityMobSpawner.this.pos, Blocks.mob_spawner, p_98267_1_, 0);
            }
            
            @Override
            public World getSpawnerWorld() {
                return TileEntityMobSpawner.this.worldObj;
            }
            
            @Override
            public BlockPos func_177221_b() {
                return TileEntityMobSpawner.this.pos;
            }
            
            @Override
            public void setRandomEntity(final WeightedRandomMinecart p_98277_1_) {
                super.setRandomEntity(p_98277_1_);
                if (this.getSpawnerWorld() != null) {
                    this.getSpawnerWorld().markBlockForUpdate(TileEntityMobSpawner.this.pos);
                }
            }
        };
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.field_145882_a.readFromNBT(compound);
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound compound) {
        super.writeToNBT(compound);
        this.field_145882_a.writeToNBT(compound);
    }
    
    @Override
    public void update() {
        this.field_145882_a.updateSpawner();
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        var1.removeTag("SpawnPotentials");
        return new S35PacketUpdateTileEntity(this.pos, 1, var1);
    }
    
    @Override
    public boolean receiveClientEvent(final int id, final int type) {
        return this.field_145882_a.setDelayToMin(id) || super.receiveClientEvent(id, type);
    }
    
    public MobSpawnerBaseLogic getSpawnerBaseLogic() {
        return this.field_145882_a;
    }
}
