package non.asset.command.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSign;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import non.asset.Clarinet;
import non.asset.command.Command;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.game.TickEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.utils.OFC.Printer;
import non.asset.utils.OFC.TimerUtil;

public class Teleport extends Command {
    private int x, y, z;
    private boolean fucking;
    private boolean finger;
    private final Minecraft mc = Minecraft.getMinecraft();
    private boolean packet = true;
    private int moveUnder;
    private TimerUtil timerUtil = new TimerUtil();

    public Teleport() {
        super("Teleport", new String[]{"teleport", "tp"});
    }

    @Override
    public void onRun(final String[] args) {
        switch (args.length) {
            case 2:
                if (args[1].toLowerCase().equals("stop")) {
                    if (fucking) {
                        stopTP();
                        Printer.print("Stopped.");
                    } else {
                        Printer.print("Not running.");
                    }
                    break;
                }
                if (args[1].toLowerCase().equals("help")) {
                    Printer.print(".teleport <args>");
                    break;
                }
                if (args[1].toLowerCase().equals("packet")) {
                    packet ^= true;
                    Printer.print("Packet set to " + packet);
                    break;
                }
                if (fucking) {
                    Printer.print("Already going.");
                    break;
                }
                for (EntityPlayer e : mc.theWorld.playerEntities) {
                    if (e.getName().toLowerCase().equals(args[1].toLowerCase())) {
                        startTP(MathHelper.floor_double(e.posX), MathHelper.floor_double(e.posY), MathHelper.floor_double(e.posZ), true);
                        return;
                    }
                }
                Clarinet.INSTANCE.getEventBus().bind(this);
                mc.thePlayer.sendQueue.addToSendQueueNoEvents(new C01PacketChatMessage("/f who " + args[1]));
                break;
            case 3:
                if (fucking) {
                    Printer.print("Already going.");
                    break;
                }
                if (NumberUtils.isNumber(args[1]) && NumberUtils.isNumber(args[2])) {
                    if (!isUnderBlock() || packet) {
                        startTP(Integer.parseInt(args[1]), 255, Integer.parseInt(args[2]), true);
                    } else {
                        Printer.print("You are under a block!");
                    }
                } else {
                    Printer.print("Invalid arguments.");
                    Printer.print("try .teleport <args>");
                }
                break;
            case 4:
                if (fucking) {
                    Printer.print("Already going.");
                    break;
                }
                if (NumberUtils.isNumber(args[1]) && NumberUtils.isNumber(args[2]) && NumberUtils.isNumber(args[3])) {
                    if (!isUnderBlock() || packet) {
                        startTP(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), true);
                    } else {
                        Printer.print("You are under a block!");
                    }
                } else {
                    Printer.print("Invalid arguments.");
                    Printer.print("try .teleport <args>");
                }
                break;
            default:
                Printer.print("Invalid arguments.");
                Printer.print("try .teleport <args>");
                break;
        }
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
        if (fucking && !packet) {
            final float storedangles = getRotationFromPosition(x, z);
            final double distancex = -3 * Math.sin(storedangles);
            final double distancez = 3 * Math.cos(storedangles);
            if (mc.thePlayer.ticksExisted % 3 == 0) {
                if (mc.thePlayer.posY < 250) {
                    mc.thePlayer.motionY = 5;
                } else {
                    mc.thePlayer.motionY = 0;
                    finger = true;
                }
                if (mc.thePlayer.getDistanceSq(x, mc.thePlayer.posY, z) >= 32) {
                    if (finger) {
                        mc.thePlayer.motionX = distancex;
                        mc.thePlayer.motionZ = distancez;
                    }
                } else {
                    mc.thePlayer.motionX = 0;
                    mc.thePlayer.motionZ = 0;
                    Printer.print("Finished you have arrived at x:" + (int) mc.thePlayer.posX + " z:" + (int) mc.thePlayer.posZ);
                    fucking = false;
                    finger = false;
                    mc.renderGlobal.loadRenderers();
                    Clarinet.INSTANCE.getEventBus().unbind(this);
                }
            }
        }
    }

    @Handler
    public void onPacket(PacketEvent event) {
        if (packet && event.isSending()) {
            if (fucking) {
                if (event.getPacket() instanceof C03PacketPlayer) {
                    C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();
                    if (!finger) {
                        packet.setY(y);
                        packet.setX(x);
                        packet.setZ(z);
                        mc.thePlayer.sendQueue.addToSendQueueNoEvents(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
                        mc.thePlayer.setPosition(x,y,z);
                        finger = true;
                        moveUnder = 2;
                    }
                }
                if (timerUtil.reach(500)) {
                    Printer.print("Finished you have arrived at x:" + x + " z:" + z);
                    fucking = false;
                    finger = false;
                    mc.renderGlobal.loadRenderers();
                    Clarinet.INSTANCE.getEventBus().unbind(this);
                    timerUtil.reset();
                }
            }
        } else {
            if (event.getPacket() instanceof S08PacketPlayerPosLook && moveUnder == 2) {
                moveUnder = 1;
            }
            if (event.getPacket() instanceof S02PacketChat) {
                S02PacketChat packet = (S02PacketChat) event.getPacket();
                String text = packet.getChatComponent().getUnformattedText();
                if (text.contains("You cannot go past the border.")) {
                    event.setCanceled(true);
                }
                if (text.contains("Home: ")) {
                    if (text.contains("Not set")) {
                        stopTP();
                        Printer.print("Player or faction found but f home was not set.");
                        return;
                    }
                    try {
                        int x = Integer.parseInt(StringUtils.substringBetween(text, "Home: ", ", "));
                        int z = Integer.parseInt(text.split(", ")[1]);
                        startTP(x, 255, z, false);
                    } catch (Exception e) {
                        stopTP();
                    }
                } else {
                    if (text.contains(" not found.")) {
                        stopTP();
                        Printer.print("Player or faction not found.");
                    }
                }
            }
        }
    }

    @Handler
    public void onTick(TickEvent event) {
        if (mc.thePlayer != null && moveUnder == 1 && packet) {
            if (mc.thePlayer.getDistanceSq(x, mc.thePlayer.posY, z) > 1) {
                mc.thePlayer.sendQueue.addToSendQueueNoEvents(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
                mc.thePlayer.sendQueue.addToSendQueueNoEvents(new C03PacketPlayer.C04PacketPlayerPosition(Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY, true));
                moveUnder = 0;
            }
        }
    }

    private void startTP(final int x, final int y, final int z, boolean register) {
        if (fucking) {
            Printer.print("Already active!");
            return;
        }
        this.x = x;
        this.y = y;
        this.z = z;
        fucking = true;
        Printer.print("Teleporting to x:" + x + " y:" + y + " z:" + z + ".");
        if (register) {
            Clarinet.INSTANCE.getEventBus().bind(this);
        }
        timerUtil.reset();
    }

    private void stopTP() {
        x = y = z = 0;
        fucking = false;
        finger = false;
        Clarinet.INSTANCE.getEventBus().unbind(this);
    }

    private boolean isUnderBlock() {
        for (int i = (int) (Minecraft.getMinecraft().thePlayer.posY + 2); i < 255; ++i) {
            BlockPos pos = new BlockPos(Minecraft.getMinecraft().thePlayer.posX, i, Minecraft.getMinecraft().thePlayer.posZ);
            if (Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock() instanceof BlockAir || Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock() instanceof BlockFenceGate || Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock() instanceof BlockSign || Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock() instanceof BlockButton)
                continue;
            return true;
        }
        return false;
    }

    private float getRotationFromPosition(final double x, final double z) {
        final double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        final double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        final float yaw = (float) Math.atan2(zDiff, xDiff) - 1;
        return yaw;
    }
}
