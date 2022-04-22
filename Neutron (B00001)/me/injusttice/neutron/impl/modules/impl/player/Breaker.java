package me.injusttice.neutron.impl.modules.impl.player;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.movement.RotationUtils;
import me.injusttice.neutron.utils.network.PacketUtil;
import me.injusttice.neutron.utils.player.RaytraceUtils;
import me.injusttice.neutron.utils.player.Timer;
import me.injusttice.neutron.utils.world.BlockUtils;
import net.minecraft.init.Blocks;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

public class Breaker extends Module {
    
    private int h;
    private int i;
    private int j;
    private static int k;
    protected Timer time;
    protected long lastFail;
    protected boolean failed;
    protected BlockPos failedBlock;
    protected BlockPos lastArround;
    protected Block lastArroundBlock;
    protected int ftrys;
    protected int blockID;
    protected float yaw;
    protected float pitch;
    public ModeSet mode = new ModeSet("Mode", "Bed", "Bed", "Cake");
    
    public Breaker() {
        super("Breaker", 0, Category.PLAYER);
        addSettings(mode);
        time = new Timer();
        blockID = 92;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        time.reset();
        lastFail = 0L;
        ftrys = 0;
        failed = false;
        lastArround = null;
        lastArroundBlock = null;
        failedBlock = null;
    }
    
    @EventTarget
    public void onMotion(EventMotion e) {
        if(e.isPre()) {
            switch (mode.getMode()) {
                case "Bed":
                    for (int x2 = -k; x2 < k; ++x2) {
                        for (int y = k; y > -k; --y) {
                            for (int z2 = -k; z2 < k; ++z2) {
                                h = (int)mc.thePlayer.posX + x2;
                                i = (int)mc.thePlayer.posY + y;
                                j = (int)mc.thePlayer.posZ + z2;
                                BlockPos blockPos2 = new BlockPos(h, i, j);
                                Block block2 = mc.theWorld.getBlockState(blockPos2).getBlock();
                                float[] rotations = RotationUtils.getRotationFromPosition(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
                                if (block2.getBlockState().getBlock() == Block.getBlockById(26)) {
                                    e.setYaw(rotations[0]);
                                    e.setPitch(rotations[1]);
                                    PacketUtil.sendPacketSilent(new C0APacketAnimation());
                                    mc.playerController.curBlockDamageMP = 1.0f;
                                    mc.playerController.onPlayerDamageBlock(blockPos2, EnumFacing.NORTH);
                                    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos2, EnumFacing.NORTH));
                                    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos2, EnumFacing.NORTH));
                                }
                            }
                        }
                    }
                    break;
                case "Cake":
                    if (mc.theWorld != null && mc.thePlayer != null) {
                        if (failedBlock != null && getBlocksArround(failedBlock).isEmpty()) {
                            lastArround = null;
                            lastArroundBlock = null;
                            failed = false;
                        }
                        try {
                            if (failed && !getBlocksArround(failedBlock).isEmpty()) {
                                checkFailed();
                            }
                            for (int y = -5; y < 5; ++y) {
                                for (int x = -5; x < 5; ++x) {
                                    for (int z = -5; z < 5; ++z) {
                                        BlockPos blockPos = mc.thePlayer.getPosition().add(x, y, (double)z);
                                        Block block = mc.theWorld.getBlockState(blockPos).getBlock();
                                        if (block == Block.getBlockById(blockID)) {
                                            setRotation(blockPos);
                                            if (time.hasTimeElapsed(4L, true)) {
                                                destroyBlock(blockPos);
                                                ++ftrys;
                                                time.reset();
                                                if (failedDestroy(blockPos, block) && !getBlocksArround(blockPos).isEmpty() && ftrys > 5.0) {
                                                    lastFail = System.currentTimeMillis();
                                                    failed = true;
                                                    failedBlock = blockPos;
                                                    ftrys = 0;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }
    
    protected void checkFailed() {
        if (lastArround != null) {
            lastArround = null;
            lastArroundBlock = null;
            failed = false;
        }
    }
    
    protected List getBlocksArround(BlockPos mainPos) {
        ArrayList arroundBlocks = new ArrayList();
        Block mainBlock = mc.theWorld.getBlockState(mainPos).getBlock();
        for (int y = 0; y < 2; ++y) {
            for (int x = -5; x < 5; ++x) {
                for (int z = -5; z < 5; ++z) {
                    BlockPos blockPos = mainPos.add(x, y, z);
                    Block block = mc.theWorld.getBlockState(blockPos).getBlock();
                    if (block != Blocks.air) {
                        arroundBlocks.add(blockPos);
                    }
                }
            }
        }
        return arroundBlocks;
    }
    
    protected boolean failedDestroy(BlockPos blockPos, Block block) {
        return mc.theWorld.getBlockState(blockPos).getBlock() == block;
    }
    
    protected void setRotation(BlockPos blockPos) {
        BlockUtils sLoc = new BlockUtils(mc.thePlayer.posX, mc.thePlayer.posY + 1.6, mc.thePlayer.posZ);
        BlockUtils eLoc = new BlockUtils(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        RaytraceUtils rayTrace = new RaytraceUtils(sLoc, eLoc);
        pitch = (float)rayTrace.getPitch();
        yaw = (float)rayTrace.getYaw();
    }
    
    protected void destroyBlock(BlockPos blockPos) {
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
        PacketUtil.sendPacketSilent(new C0APacketAnimation());
    }
    
    static {
        k = 5;
    }
}
