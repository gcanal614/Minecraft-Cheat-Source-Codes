/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  viamcp.ViaMCP
 */
package de.fanta.module.impl.world;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventPreMotion;
import de.fanta.events.listeners.EventRender2D;
import de.fanta.events.listeners.EventSycItem;
import de.fanta.events.listeners.EventUpdate;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.utils.Rotations;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import java.util.Arrays;
import java.util.Random;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.CAnimateHandPacket;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import viamcp.ViaMCP;

public class Scaffold
extends Module {
    int add = 0;
    public int slot;
    public static BlockData data;
    public static float[] lastRot;
    private final int[] forbiddenBlocks = new int[]{5};
    public static float lastYaw;
    public static float lastPitch;
    public boolean turn;
    public boolean downwards;
    private double posY;
    TimeUtil time = new TimeUtil();
    TimeUtil time2 = new TimeUtil();

    public Scaffold() {
        super("Scaffold", 50, Module.Type.World, Color.magenta);
        this.settings.add(new Setting("Legit", new CheckBox(false)));
        this.settings.add(new Setting("NoBob", new CheckBox(false)));
        this.settings.add(new Setting("Swing", new CheckBox(false)));
        this.settings.add(new Setting("Sprint", new CheckBox(false)));
        this.settings.add(new Setting("Matrix", new CheckBox(false)));
        this.settings.add(new Setting("NCP", new CheckBox(false)));
        this.settings.add(new Setting("Cubecraft", new CheckBox(false)));
        this.settings.add(new Setting("AAC", new CheckBox(false)));
        this.settings.add(new Setting("TP", new CheckBox(false)));
        this.settings.add(new Setting("NCPTower", new CheckBox(false)));
        this.settings.add(new Setting("CubecraftTower", new CheckBox(false)));
        this.settings.add(new Setting("NoAttack", new CheckBox(false)));
        this.settings.add(new Setting("OnlyAirPlace", new CheckBox(false)));
        this.settings.add(new Setting("MoveAbleTower", new CheckBox(false)));
    }

    @Override
    public void onEnable() {
        this.posY = Scaffold.mc.thePlayer.posY;
        Client.INSTANCE.moduleManager.getModule("Sprint").setState(true);
        if (((CheckBox)this.getSetting((String)"TP").getSetting()).state) {
            Minecraft.getMinecraft().thePlayer.motionY = 0.11f;
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Scaffold.mc.timer.timerSpeed = 1.0f;
        if (((CheckBox)this.getSetting((String)"NoAttack").getSetting()).state) {
            Client.INSTANCE.moduleManager.getModule("Killaura").setState(true);
        }
        Client.INSTANCE.moduleManager.getModule("Sprint").setState(true);
        Scaffold.mc.gameSettings.keyBindRight.pressed = false;
        Scaffold.mc.gameSettings.keyBindLeft.pressed = false;
        if (Scaffold.mc.entityRenderer.theShaderGroup != null) {
            Scaffold.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            Scaffold.mc.entityRenderer.theShaderGroup = null;
        }
        Scaffold.mc.gameSettings.keyBindSneak.pressed = false;
        super.onDisable();
    }

    @Override
    public void onEvent(Event event) {
        if (((CheckBox)this.getSetting((String)"NoAttack").getSetting()).state) {
            Client.INSTANCE.moduleManager.getModule("Killaura").setState(false);
        }
        if (((CheckBox)this.getSetting((String)"NCPTower").getSetting()).state) {
            boolean cfr_ignored_0 = Scaffold.mc.gameSettings.keyBindJump.pressed;
        }
        if (Keyboard.isKeyDown((int)Scaffold.mc.gameSettings.keyBindSneak.getKeyCode()) && !Scaffold.mc.gameSettings.keyBindJump.isKeyDown()) {
            KeyBinding.setKeyBindState(Scaffold.mc.gameSettings.keyBindSneak.getKeyCode(), false);
            this.downwards = true;
        } else {
            this.downwards = false;
        }
        if (event instanceof EventSycItem && this.getBlockSlot() != -1) {
            EventSycItem.INSTANCE.slot = this.slot = this.getBlockSlot();
        }
        if (event instanceof Event) {
            if (!((CheckBox)this.getSetting((String)"Sprint").getSetting()).state) {
                Scaffold.mc.thePlayer.setSprinting(false);
            } else {
                Scaffold.mc.thePlayer.isMoving();
            }
        }
        if (event instanceof EventPreMotion) {
            if (((CheckBox)this.getSetting((String)"NoBob").getSetting()).state) {
                Scaffold.mc.gameSettings.viewBobbing = true;
                Scaffold.mc.thePlayer.distanceWalkedModified = 0.0f;
            }
            if (((CheckBox)this.getSetting((String)"TP").getSetting()).state && Scaffold.mc.gameSettings.keyBindForward.pressed) {
                Scaffold.mc.thePlayer.motionY = 0.0;
                double x = Scaffold.mc.thePlayer.posX;
                double y = Scaffold.mc.thePlayer.posY;
                double z = Scaffold.mc.thePlayer.posZ;
                Scaffold.mc.gameSettings.keyBindSprint.pressed = false;
                if (Scaffold.mc.thePlayer.ticksExisted % 2 == 0) {
                    double yaw = Math.toRadians(Scaffold.mc.thePlayer.rotationYaw);
                    double speed1 = 1.2;
                    double xm = -Math.sin(yaw) * speed1;
                    double zm = Math.cos(yaw) * speed1;
                    Scaffold.mc.thePlayer.setPosition(x + xm, y, z + zm);
                }
            }
            Scaffold.mc.gameSettings.keyBindSprint.pressed = false;
            BlockPos blockPos = new BlockPos(Scaffold.mc.getMinecraft().thePlayer.posX, Scaffold.mc.getMinecraft().thePlayer.posY - 1.0, Scaffold.mc.getMinecraft().thePlayer.posZ);
            if (Scaffold.mc.theWorld.getBlockState(blockPos).getBlock() != Blocks.air) {
                Scaffold.mc.gameSettings.keyBindSneak.pressed = false;
                this.time.reset();
            }
            if (Scaffold.mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.air) {
                Scaffold.mc.gameSettings.keyBindSneak.pressed = true;
            }
            if (Scaffold.mc.theWorld.getBlockState(blockPos).getBlock() != Blocks.air) {
                Scaffold.mc.gameSettings.keyBindSneak.pressed = false;
            }
            int cfr_ignored_1 = Scaffold.mc.thePlayer.ticksExisted % 2;
            if (((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Scaffold").getSetting((String)"Matrix").getSetting()).state) {
                Scaffold.mc.gameSettings.keyBindSneak.pressed = false;
            }
            Rotations.yaw = Scaffold.mc.thePlayer.rotationYawHead;
            Rotations.pitch = Scaffold.mc.thePlayer.rotationPitchHead;
            if (data == null) {
                return;
            }
            float[] rotation = Rotations.rotationrecode7(data);
            float[] rotation2 = Rotations.rotationrecode2(data);
            ((EventPreMotion)event).setYaw(Scaffold.mc.thePlayer.rotationYaw + 180.0f);
            ((EventPreMotion)event).setPitch(82.0f);
            if (Scaffold.mc.theWorld.getBlockState(blockPos).getBlock() != Blocks.air && Minecraft.getMinecraft().thePlayer.isMoving()) {
                float f = (float)MathHelper.getRandomDoubleInRange(new Random(), 90.0, 90.0);
            }
            if (((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Scaffold").getSetting((String)"Cubecraft").getSetting()).state) {
                ((EventPreMotion)event).setYaw(Scaffold.mc.thePlayer.rotationYaw + 180.0f);
                ((EventPreMotion)event).setPitch(rotation[1]);
                Scaffold.mc.gameSettings.keyBindSneak.pressed = false;
            }
            if (((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Scaffold").getSetting((String)"Matrix").getSetting()).state) {
                float RotationPitch2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 80.009f, 80.0049f);
                ((EventPreMotion)event).setPitch(RotationPitch2);
                if (Scaffold.mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.air) {
                    float RotationPitch = (float)MathHelper.getRandomDoubleInRange(new Random(), 79.121, 79.265);
                    float RotationPitch3 = (float)MathHelper.getRandomDoubleInRange(new Random(), 82.5, 82.4);
                    float RotationYAW = (float)MathHelper.getRandomDoubleInRange(new Random(), 173.5, 173.6f);
                    ((EventPreMotion)event).setPitch(RotationPitch);
                    ((EventPreMotion)event).setYaw(Scaffold.mc.thePlayer.rotationYaw + RotationYAW);
                }
            }
            Rotations.setYaw(Scaffold.mc.thePlayer.rotationYaw + 180.0f, 180.0f);
            Rotations.setPitch(90.0f, 90.0f);
            if (((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Scaffold").getSetting((String)"NCP").getSetting()).state) {
                float yaw_ = this.updateRotation(Scaffold.mc.thePlayer.rotationYawHead, rotation2[0], 180.0f);
                ((EventPreMotion)event).setYaw(rotation2[0]);
                ((EventPreMotion)event).setPitch(rotation2[1]);
                Scaffold.mc.gameSettings.keyBindSneak.pressed = false;
                if (((CheckBox)this.getSetting((String)"NCPTower").getSetting()).state) {
                    if (((CheckBox)this.getSetting((String)"MoveAbleTower").getSetting()).state) {
                        if (Scaffold.mc.gameSettings.keyBindJump.pressed) {
                            if (this.time.hasReached(2000L)) {
                                this.time.reset();
                            } else if (Scaffold.mc.thePlayer.ticksExisted % 3 == 0) {
                                Scaffold.mc.thePlayer.motionY = 0.4196;
                            }
                        }
                        this.time.reset();
                    } else if (!Scaffold.mc.thePlayer.isMoving()) {
                        if (Scaffold.mc.gameSettings.keyBindJump.pressed) {
                            if (this.time.hasReached(2000L)) {
                                this.time.reset();
                            } else if (Scaffold.mc.thePlayer.ticksExisted % 3 == 0) {
                                Scaffold.mc.thePlayer.motionY = 0.4196;
                            }
                        }
                        this.time.reset();
                    }
                }
            }
            if (((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Scaffold").getSetting((String)"AAC").getSetting()).state) {
                ((EventPreMotion)event).setYaw(Scaffold.mc.thePlayer.rotationYaw + 180.0f);
                ((EventPreMotion)event).setPitch(82.5f);
                Scaffold.mc.gameSettings.keyBindSneak.pressed = false;
                if (((CheckBox)this.getSetting((String)"CubecraftTower").getSetting()).state) {
                    if (!Scaffold.mc.thePlayer.isMoving()) {
                        if (Scaffold.mc.gameSettings.keyBindJump.pressed) {
                            if (Scaffold.mc.thePlayer.ticksExisted % 2 == 0) {
                                Scaffold.mc.thePlayer.motionX *= (double)0.03f;
                                Scaffold.mc.thePlayer.motionZ *= (double)0.03f;
                            }
                            if (this.time.hasReached(2000L)) {
                                this.time.reset();
                            } else if (Scaffold.mc.thePlayer.ticksExisted % 3 == 0) {
                                Scaffold.mc.thePlayer.motionY = 0.4196;
                            }
                            Scaffold.mc.timer.timerSpeed = Scaffold.mc.thePlayer.ticksExisted % 3 == 0 ? 1.0f : 2.0f;
                        }
                        this.time.reset();
                    } else {
                        Scaffold.mc.timer.timerSpeed = 1.0f;
                    }
                }
            }
            int cfr_ignored_2 = Scaffold.mc.thePlayer.ticksExisted % 100;
        }
        boolean cfr_ignored_3 = event instanceof EventRender2D;
        if (event instanceof EventUpdate) {
            BlockPos blockPos;
            Scaffold.mc.gameSettings.keyBindSprint.pressed = false;
            float tmm = (float)MathHelper.getRandomDoubleInRange(new Random(), 93.0, 95.0);
            if (this.time2.hasReached((long)tmm)) {
                if (this.time.hasReached(10L)) {
                    this.turn = !this.turn;
                    this.time.reset();
                }
                this.time2.reset();
            }
            if (((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Scaffold").getSetting((String)"Matrix").getSetting()).state && Scaffold.mc.theWorld.getBlockState(blockPos = new BlockPos(Scaffold.mc.thePlayer.posX, Scaffold.mc.thePlayer.posY - 1.0, Scaffold.mc.thePlayer.posZ)).getBlock() == Blocks.air) {
                Scaffold.mc.thePlayer.motionX *= (double)0.508f;
                Scaffold.mc.thePlayer.motionZ *= (double)0.508f;
            }
            if ((data = this.find(new Vec3(0.0, 0.0, 0.0))) != null && this.getBlockSlot() != -1) {
                Scaffold.mc.playerController.updateController();
                Vec3 hitVec = new Vec3(BlockData.getPos()).addVector(0.5, 0.5, 0.5).add(new Vec3(BlockData.getFacing().getDirectionVec()).multi(0.5));
                if (this.slot != -1) {
                    if (!((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Scaffold").getSetting((String)"AAC").getSetting()).state || !((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Scaffold").getSetting((String)"NCP").getSetting()).state) {
                        this.rightClickMouse(Scaffold.mc.thePlayer.inventory.getStackInSlot(this.slot), this.slot);
                    }
                    if ((((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Scaffold").getSetting((String)"AAC").getSetting()).state || ((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Scaffold").getSetting((String)"NCP").getSetting()).state) && Scaffold.mc.playerController.onPlayerRightClick(Scaffold.mc.thePlayer, Scaffold.mc.theWorld, Scaffold.mc.thePlayer.inventory.getStackInSlot(this.slot), BlockData.getPos(), BlockData.getFacing(), hitVec)) {
                        Scaffold.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    }
                }
            }
        }
    }

    public BlockData grabBlock() {
        BlockPos[] offsets;
        BlockPos pos;
        EnumFacing[] invert = new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST};
        if (this.downwards) {
            this.posY = Scaffold.mc.thePlayer.getPositionVector().yCoord - 1.0;
        }
        if (!(Scaffold.mc.theWorld.getBlockState(pos = new BlockPos(Scaffold.mc.thePlayer.getPositionVector().xCoord, this.posY, Scaffold.mc.thePlayer.getPositionVector().zCoord).offset(EnumFacing.DOWN)).getBlock() instanceof BlockAir)) {
            return null;
        }
        EnumFacing[] enumFacingArray = EnumFacing.values();
        int n = enumFacingArray.length;
        int n2 = 0;
        while (n2 < n) {
            EnumFacing facing = enumFacingArray[n2];
            BlockPos offset = pos.offset(facing);
            if (!(Scaffold.mc.theWorld.getBlockState(offset).getBlock() instanceof BlockAir)) {
                return new BlockData(invert[facing.ordinal()], offset);
            }
            ++n2;
        }
        BlockPos[] blockPosArray = offsets = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1)};
        int n3 = offsets.length;
        n = 0;
        while (n < n3) {
            BlockPos offset = blockPosArray[n];
            BlockPos offsetPos = pos.add(offset.getX(), 0, offset.getZ());
            if (Scaffold.mc.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
                EnumFacing[] enumFacingArray2 = EnumFacing.values();
                int n4 = enumFacingArray2.length;
                int n5 = 0;
                while (n5 < n4) {
                    EnumFacing facing = enumFacingArray2[n5];
                    BlockPos blockOffset = offsetPos.offset(facing);
                    if (!(Scaffold.mc.theWorld.getBlockState(blockOffset).getBlock() instanceof BlockAir)) {
                        return new BlockData(invert[facing.ordinal()], blockOffset);
                    }
                    ++n5;
                }
            }
            ++n;
        }
        return null;
    }

    public void rightClickMouse(ItemStack itemstack, int slot) {
        if (!Scaffold.mc.playerController.func_181040_m()) {
            Scaffold.mc.rightClickDelayTimer = 4;
            try {
                switch (Scaffold.mc.objectMouseOver.typeOfHit) {
                    case ENTITY: {
                        if (!Scaffold.mc.playerController.func_178894_a(Scaffold.mc.thePlayer, Scaffold.mc.objectMouseOver.entityHit, Scaffold.mc.objectMouseOver)) {
                            Scaffold.mc.playerController.interactWithEntitySendPacket(Scaffold.mc.thePlayer, Scaffold.mc.objectMouseOver.entityHit);
                        }
                        break;
                    }
                    case BLOCK: {
                        int i;
                        BlockPos blockpos = Scaffold.mc.objectMouseOver.getBlockPos();
                        if (Scaffold.mc.theWorld.getBlockState(blockpos).getBlock().getMaterial() == Material.air) break;
                        int n = i = itemstack != null ? itemstack.stackSize : 0;
                        if (Scaffold.mc.playerController.onPlayerRightClick(Scaffold.mc.thePlayer, Scaffold.mc.theWorld, itemstack, blockpos, Scaffold.mc.objectMouseOver.sideHit, Scaffold.mc.objectMouseOver.hitVec) && !((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Scaffold").getSetting((String)"Swing").getSetting()).state) {
                            if (ViaMCP.getInstance().getVersion() == 47) {
                                Scaffold.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                            } else {
                                Scaffold.mc.thePlayer.sendQueue.addToSendQueue(new CAnimateHandPacket());
                            }
                        }
                        BlockPos blockPos = new BlockPos(Scaffold.mc.getMinecraft().thePlayer.posX, Scaffold.mc.getMinecraft().thePlayer.posY - 1.0, Scaffold.mc.getMinecraft().thePlayer.posZ);
                        if (((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Scaffold").getSetting((String)"Swing").getSetting()).state) {
                            if (Scaffold.mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.air) {
                                Scaffold.mc.thePlayer.swingItem();
                            }
                            if (Scaffold.mc.gameSettings.keyBindJump.pressed && Scaffold.mc.theWorld.getBlockState(blockPos).getBlock() != Blocks.air) {
                                Scaffold.mc.thePlayer.swingItem();
                            }
                        }
                        if (itemstack == null) {
                            return;
                        }
                        if (itemstack.stackSize == 0) {
                            Scaffold.mc.thePlayer.inventory.mainInventory[slot] = null;
                        }
                        if (!((CheckBox)this.getSetting((String)"Swing").getSetting()).state || itemstack.stackSize == i && !Scaffold.mc.playerController.isInCreativeMode()) break;
                        Scaffold.mc.entityRenderer.itemRenderer.resetEquippedProgress();
                    }
                    default: {
                        break;
                    }
                }
            }
            catch (NullPointerException nullPointerException) {
                // empty catch block
            }
        }
    }

    public int getBlockSlot() {
        int i = 0;
        while (i < 9) {
            ItemStack s = Scaffold.mc.thePlayer.inventory.getStackInSlot(i);
            if (s != null && s.getItem() instanceof ItemBlock && !Arrays.asList(new int[][]{this.forbiddenBlocks}).contains(s.getItem().getBlockId())) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    private Vec3 getPositionByFace(BlockPos position, EnumFacing facing) {
        Vec3 offset = new Vec3((double)facing.getDirectionVec().getX() / 2.0, (double)facing.getDirectionVec().getY() / 2.0, (double)facing.getDirectionVec().getZ() / 2.0);
        Vec3 point = new Vec3((double)position.getX() + 0.5, (double)position.getY() + 0.5, (double)position.getZ() + 0.5);
        return point.add(offset);
    }

    private boolean rayTrace(Vec3 origin, Vec3 position) {
        Vec3 difference = position.subtract(origin);
        int steps = 10;
        double x = difference.xCoord / (double)steps;
        double y = difference.yCoord / (double)steps;
        double z = difference.zCoord / (double)steps;
        Vec3 point = origin;
        int i = 0;
        while (i < steps) {
            point = point.addVector(x, y, z);
            BlockPos blockPosition = new BlockPos(point);
            IBlockState blockState = Scaffold.mc.getMinecraft().theWorld.getBlockState(blockPosition);
            if (!(blockState.getBlock() instanceof BlockLiquid) && !(blockState.getBlock() instanceof BlockAir)) {
                AxisAlignedBB boundingBox = blockState.getBlock().getCollisionBoundingBox(Scaffold.mc.getMinecraft().theWorld, blockPosition, blockState);
                if (boundingBox == null) {
                    boundingBox = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                }
                if (boundingBox.offset(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ()).isVecInside(point)) {
                    return true;
                }
            }
            ++i;
        }
        return false;
    }

    private BlockData find(Vec3 offset3) {
        BlockPos[] offsets;
        double x = Scaffold.mc.getMinecraft().thePlayer.posX;
        double y = Scaffold.mc.getMinecraft().thePlayer.posY;
        double z = Scaffold.mc.getMinecraft().thePlayer.posZ;
        EnumFacing[] invert = new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST};
        BlockPos position = new BlockPos(new Vec3(x, y, z).add(offset3)).offset(EnumFacing.DOWN);
        EnumFacing[] enumFacingArray = EnumFacing.values();
        int n = enumFacingArray.length;
        int n2 = 0;
        while (n2 < n) {
            EnumFacing facing = enumFacingArray[n2];
            BlockPos offset = position.offset(facing);
            if (!(Scaffold.mc.getMinecraft().theWorld.getBlockState(offset).getBlock() instanceof BlockAir)) {
                if (!this.rayTrace(Scaffold.mc.getMinecraft().thePlayer.getLook(0.0f), this.getPositionByFace(offset, invert[facing.ordinal()]))) {
                    return new BlockData(invert[facing.ordinal()], offset);
                }
            }
            ++n2;
        }
        BlockPos[] blockPosArray = offsets = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1), new BlockPos(0, 0, 2), new BlockPos(0, 0, -2), new BlockPos(2, 0, 0), new BlockPos(-2, 0, 0)};
        int n3 = offsets.length;
        n = 0;
        while (n < n3) {
            BlockPos offset = blockPosArray[n];
            BlockPos offsetPos = position.add(offset.getX(), 0, offset.getZ());
            if (Scaffold.mc.getMinecraft().theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
                EnumFacing[] enumFacingArray2 = EnumFacing.values();
                int n4 = enumFacingArray2.length;
                int n5 = 0;
                while (n5 < n4) {
                    EnumFacing facing = enumFacingArray2[n5];
                    BlockPos offset2 = offsetPos.offset(facing);
                    if (!(Scaffold.mc.getMinecraft().theWorld.getBlockState(offset2).getBlock() instanceof BlockAir)) {
                        if (!this.rayTrace(Scaffold.mc.getMinecraft().thePlayer.getLook(0.01f), this.getPositionByFace(offset, invert[facing.ordinal()]))) {
                            return new BlockData(invert[facing.ordinal()], offset2);
                        }
                    }
                    ++n5;
                }
            }
            ++n;
        }
        return null;
    }

    public static float[] rotationrecode7(BlockData blockData) {
        double x = (double)BlockData.getPos().getX() + 0.5 - Scaffold.mc.thePlayer.posX + (double)BlockData.getFacing().getFrontOffsetX() / 2.0;
        double z = (double)BlockData.getPos().getZ() + 0.5 - Scaffold.mc.thePlayer.posZ + (double)BlockData.getFacing().getFrontOffsetZ() / 2.0;
        double y = (double)BlockData.getPos().getY() + 0.5;
        double ymax = Scaffold.mc.thePlayer.posY + (double)Scaffold.mc.thePlayer.getEyeHeight() - y;
        double allmax = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(Math.atan2(ymax, allmax) * 180.0 / Math.PI);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return new float[]{yaw, pitch};
    }

    public static int getBlockCount() {
        int itemCount = 0;
        int i = 0;
        while (i < 36) {
            ItemStack stack = Scaffold.mc.thePlayer.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() != null && stack.getItem() instanceof ItemBlock) {
                itemCount += stack.stackSize;
            }
            ++i;
        }
        return itemCount;
    }

    public float updateRotation(float current, float needed, float speed) {
        float f = MathHelper.wrapAngleTo180_float(needed - current);
        if (f > speed) {
            f = speed;
        }
        if (f < -speed) {
            f = -speed;
        }
        return current + f;
    }

    public static class BlockData {
        private static EnumFacing facing;
        private static BlockPos pos;

        public BlockData(EnumFacing facing, BlockPos pos) {
            BlockData.facing = facing;
            BlockData.pos = pos;
        }

        public static EnumFacing getFacing() {
            return facing;
        }

        public static BlockPos getPos() {
            return pos;
        }
    }
}

