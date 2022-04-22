package wtf.astronicy.IMPL.module.impl.world.Scaffold;

import wtf.astronicy.IMPL.utils.RotationUtils;
import wtf.astronicy.Wrapper;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class ScaffoldUtils {

	public Minecraft mc = Minecraft.getMinecraft();

    public static class BlockCache {
        public BlockPos position;
        public EnumFacing facing;

        public BlockCache(final BlockPos position, final EnumFacing facing) {
            this.position = position;
            this.facing = facing;
        }

        public BlockPos getPosition() {
            return this.position;
        }

        private EnumFacing getFacing() {
            return this.facing;
        }

    }

    public static BlockCache grab() {
         final Minecraft mc = Minecraft.getMinecraft();
        final EnumFacing[] invert = {EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH,
                EnumFacing.EAST, EnumFacing.WEST};
        BlockPos position = new BlockPos(0, 0, 0);
        if (mc.thePlayer.isMoving() && !mc.gameSettings.keyBindJump.isKeyDown()) {
            for (double n2 = (double)((Double)Scaffold.extend.getValue()).intValue()
                    + 0.0001, n3 = 0.0; n3 <= n2; n3 += n2 / (Math.floor(n2))) {
                final BlockPos blockPos2 = new BlockPos(
                        mc.thePlayer.posX - MathHelper.sin(RotationUtils.clampRotation()) * n3,
                        mc.thePlayer.posY - 1.0,
                        mc.thePlayer.posZ + MathHelper.cos(RotationUtils.clampRotation()) * n3);
                final IBlockState blockState = mc.theWorld.getBlockState(blockPos2);
                if (blockState != null && blockState.getBlock() == Blocks.air) {
                    position = blockPos2;
                    break;
                }
            }

        } else {
            position = new BlockPos(new BlockPos(mc.thePlayer.getPositionVector().xCoord,
                    mc.thePlayer.getPositionVector().yCoord, mc.thePlayer.getPositionVector().zCoord))
                    .offset(EnumFacing.DOWN);
        }

        if (!(mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir)
                && !(mc.theWorld.getBlockState(position).getBlock() instanceof BlockLiquid)) {
            return null;
        }
        EnumFacing[] values;
        for (int length = (values = EnumFacing.values()).length, i = 0; i < length; ++i) {
            final EnumFacing facing = values[i];
            final BlockPos offset = position.offset(facing);
            if (!(mc.theWorld.getBlockState(offset).getBlock() instanceof BlockAir)
                    && !(mc.theWorld.getBlockState(position).getBlock() instanceof BlockLiquid)) {
                return new BlockCache(offset, invert[facing.ordinal()]);
            }
        }
        final BlockPos[] offsets = {new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1),
                new BlockPos(0, 0, 1)};
        BlockPos[] array;
        for (int length2 = (array = offsets).length, j = 0; j < length2; ++j) {
            final BlockPos offset2 = array[j];
            final BlockPos offsetPos = position.add(offset2.getX(), 0, offset2.getZ());
            if (mc.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
                EnumFacing[] values2;
                for (int length3 = (values2 = EnumFacing.values()).length, k = 0; k < length3; ++k) {
                    final EnumFacing facing2 = values2[k];
                    final BlockPos offset3 = offsetPos.offset(facing2);
                    if (!(mc.theWorld.getBlockState(offset3).getBlock() instanceof BlockAir)) {
                        return new BlockCache(offset3, invert[facing2.ordinal()]);
                    }
                }
            }

        }
        return null;
    }

	public static final double VANILLA_JUMP_HEIGHT = 0.42F;
    public static final int END = 45;
    public static final double WALK_SPEED = 0.221;
    private static final double SWIM_MOD = 0.115D / WALK_SPEED;
    public static final int INCLUDE_ARMOR_BEGIN = 5;
    public static final int EXCLUDE_ARMOR_BEGIN = 9;
    public static final int ONLY_HOT_BAR_BEGIN = 36;

    public static boolean isMoving() {
        return Wrapper.getPlayer().movementInput.moveForward != 0.0F || Wrapper.getPlayer().movementInput.moveStrafe != 0.0F;
    }

    public static void windowClick(int windowId, int slotId, int mouseButtonClicked, ClickType mode) {
        Minecraft.getMinecraft().playerController.windowClick(windowId, slotId, mouseButtonClicked, mode.ordinal(), Wrapper.getPlayer());
    }

    public static void windowClick(int slotId, int mouseButtonClicked, ClickType mode) {
        Minecraft.getMinecraft().playerController.windowClick(Wrapper.getPlayer().inventoryContainer.windowId, slotId,
                mouseButtonClicked, mode.ordinal(), Wrapper.getPlayer());
    }

    public static ItemStack getStackInSlot(int index) {
        return Wrapper.getPlayer().inventoryContainer.getSlot(index).getStack();
    }

    public static boolean isValid(ItemStack stack) {
        if (stack == null) return false;
        if (stack.getItem() instanceof ItemBlock) {
            return isGoodBlockStack(stack);
        }
        return false;
    }
    public static boolean isGoodBlockStack(ItemStack stack) {
        if (stack.stackSize < 1)
            return false;
        return isValidBlock(Block.getBlockFromItem(stack.getItem()), true);
    }

    public static boolean isValidBlock(Block block, boolean toPlace) {
        if (block instanceof BlockContainer || !block.isFullBlock() || !block.isFullCube() || (toPlace && block instanceof BlockFalling))
            return false;
        Material material = block.getMaterial();
        return !material.isLiquid() && material.isSolid();
    }

    public static double getJumpHeight(double baseJumpHeight) {
        if (isInLiquid()) {
            return WALK_SPEED * SWIM_MOD + 0.02F;
        } else if (Wrapper.getPlayer().isPotionActive(Potion.jump)) {
            return baseJumpHeight + (Wrapper.getPlayer().getActivePotionEffect(Potion.jump).getAmplifier() + 1.0F) * 0.1F;
        }
        return baseJumpHeight;
    }

    public static boolean isInLiquid() {
        return Wrapper.getPlayer().isInWater() || Wrapper.getPlayer().isInLava();
    }

    public enum ClickType {
        // if mouseButtonClicked is 0 `DROP_ITEM` will drop 1
        // item from the stack else if it is 1 it will drop the entire stack
        CLICK, SHIFT_CLICK, SWAP_WITH_HOT_BAR_SLOT, PLACEHOLDER, DROP_ITEM
    }

    public static int grabBlockSlot() {
        int slot = -1;
        int highestStack = -1;
        boolean didGetHotbar = false;
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && canItemBePlaced(itemStack) && itemStack.stackSize > 0) {
                if (Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize > highestStack && i < 9) {
                    highestStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize;
                    slot = i;
                    if (slot == getLastHotbarSlot()) {
                        didGetHotbar = true;
                    }
                }
                if (i > 8 && !didGetHotbar) {
                    int hotbarNum = getFreeHotbarSlot();
                    if (hotbarNum != -1 && Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize > highestStack) {
                        highestStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize;
                        slot = i;
                    }
                }
            }
        }
        if (slot > 8) {
            int hotbarNum = getFreeHotbarSlot();
            if (hotbarNum != -1) {
                Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, Minecraft.getMinecraft().thePlayer);
            } else {
                return -1;
            }
        }
        return slot;
    }

    public static int getFreeHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; k++) {
            if (Minecraft.getMinecraft().thePlayer.inventory.mainInventory[k] == null) {
                hotbarNum = k;
                continue;
            } else {
                hotbarNum = 7;
            }
        }
        return hotbarNum;
    }

    public static int getLastHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; k++) {
            final ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[k];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && canItemBePlaced(itemStack) && itemStack.stackSize > 1) {
                hotbarNum = k;
                continue;
            }
        }
        return hotbarNum;
    }

    public static boolean canItemBePlaced(ItemStack item) {
        if (item.getItem().getIdFromItem(item.getItem()) == 116)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 30)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 31)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 175)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 28)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 27)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 66)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 157)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 31)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 6)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 31)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 32)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 140)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 390)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 37)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 38)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 39)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 40)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 69)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 50)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 75)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 76)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 54)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 130)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 146)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 342)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 12)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 77)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 143)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 46)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 145)
            return false;

        return true;
    }

    public static Vec3 getHypixelVec3(BlockCache data) {
        BlockPos pos = data.position;
        EnumFacing face = data.facing;
        double x = (double) pos.getX() + 0.5, y = (double) pos.getY() + 0.5, z = (double) pos.getZ() + 0.5;
        if (face != EnumFacing.UP && face != EnumFacing.DOWN) {
            y += 0.5;
        } else {
            x += 0.3;
            z += 0.3;
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += 0.15;
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += 0.15;
        }
        return new Vec3(x, y, z);
    }





}
