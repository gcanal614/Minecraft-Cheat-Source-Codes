package stellar.skid.modules.move;

import stellar.skid.events.EventTarget;
import stellar.skid.events.events.EventState;
import stellar.skid.events.events.MotionUpdateEvent;
import stellar.skid.events.events.MoveEvent;
import stellar.skid.events.events.Render3DEvent;
import stellar.skid.gui.screen.click.DiscordGUI;
import stellar.skid.gui.screen.dropdown.DropdownGUI;
import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.gui.screen.setting.SettingType;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.combat.KillAura;
import stellar.skid.modules.configurations.annotation.Property;
import stellar.skid.modules.configurations.property.object.BooleanProperty;
import stellar.skid.modules.configurations.property.object.DoubleProperty;
import stellar.skid.modules.configurations.property.object.PropertyFactory;
import stellar.skid.modules.configurations.property.object.StringProperty;
import stellar.skid.utils.RenderUtils;
import stellar.skid.utils.RotationUtil;
import stellar.skid.utils.Timer;
import stellar.skid.utils.notifications.NotificationType;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.configurations.annotation.Property;
import stellar.skid.modules.configurations.property.object.ColorProperty;
import stellar.skid.modules.configurations.property.object.StringProperty;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static stellar.skid.gui.screen.setting.SettingType.COLOR_PICKER;
import static stellar.skid.modules.configurations.property.object.PropertyFactory.createColor;
import static stellar.skid.modules.configurations.property.object.PropertyFactory.createString;


public class ClickTP extends AbstractModule {

    private int ticks = 0;
    private List<Vec3> v3s = new ArrayList<>();
    private boolean niga;

    @Property("user-mode")
    private final StringProperty useMode = createString("Button").acceptableValues("Button", "Shift","Toggle");
    @Property("button")
    private final StringProperty button = createString("Middle").acceptableValues("Middle", "Right");
    @Property("color")
    private final ColorProperty color = createColor(new Color(152, 217, 0).getRGB());


    private Timer timer = new Timer();

    public ClickTP(@NotNull ModuleManager moduleManager) {
        super(moduleManager, "ClickTeleport", "Click Teleport", EnumModuleType.EXPLOITS, "lol");
        Manager.put(new Setting("TPuseMode", "Use Mode", SettingType.COMBOBOX, this, useMode));
        Manager.put(new Setting("BUTTON", "Mouse Button", SettingType.COMBOBOX, this, button, () -> useMode.get().equalsIgnoreCase("button")));
        Manager.put(new Setting("BO_COLOR", "Outline Color", COLOR_PICKER, this, color, null));
    }

    public int button() {
        final String button = this.button.get();

        switch (button.toLowerCase()) {
            case "middle":
                return 2;
            case "right":
                return 1;
            default:
                return 0;
        }
    }

    public boolean shouldTP() {
        return useMode.equalsIgnoreCase("Shift") ? mc.player.isSneaking() : Mouse.isButtonDown(button()) && !mc.player.isSneaking();
    }

    public MovingObjectPosition getPosition() {
        float borderSize = mc.player.getCollisionBorderSize();
        float distance = 25;
        Vec3 positionEyes = RotationUtil.getPositionEyes(1);
        Vec3 startVec = RotationUtil.getVectorForRotation(mc.player.rotationPitch, mc.player.rotationYaw);
        Vec3 endVec = positionEyes.addVector(startVec.xCoord * distance, startVec.yCoord * distance, startVec.zCoord * distance);
        return mc.world.rayTraceBlocks(positionEyes, endVec, false, false, false);
    }

    @Override
    public void onEnable() {
        if(useMode.get().equals("Toggle")){
            teleport();
            toggle();
        }
    }

    @EventTarget
    public void onMotionUpdate(MotionUpdateEvent event){
        if (timer.delay(300) && shouldTP() && !(mc.currentScreen instanceof GuiInventory) && !(mc.currentScreen instanceof DiscordGUI) && !(mc.currentScreen instanceof DropdownGUI) && mc.objectMouseOver.entityHit == null) {
            teleport();
            timer.reset();
        }
    }


    private void teleport(){
        double maximumStepDistance = 0.280061663902;
        if (getPosition() != null) {
            if (getPosition().typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                BlockPos pos = getPosition().getBlockPos();
                double x = pos.getX() + 0.5, z = pos.getZ() + 0.5;
                float angleYaw = RotationUtil.getYawToPoint(x, z);
                double distance = Math.abs(mc.player.getDistance(x, pos.getY(), z));

                double height = mc.player.posY;

                for (double step = maximumStepDistance; step < distance; step += maximumStepDistance) {

                    double xAxis = -Math.sin(Math.toRadians(angleYaw)) * step;
                    double zAxis = Math.cos(Math.toRadians(angleYaw)) * step;

                    double distanceToBlock = 0;

                    Vec3 position = getPositionForVector(mc.player.posX + xAxis, height, mc.player.posZ + zAxis);

                    Vec3 sv = RotationUtil.getVectorForRotation(90, 0);
                    Vec3 ed = position.addVector(sv.xCoord * 99, sv.yCoord * 99, sv.zCoord * 99);
                    MovingObjectPosition bh = mc.world.rayTraceBlocks(position, ed,
                            false,
                            false,
                            false);
                    if (bh == null) {
                        stellarWare.getNotificationManager().pop("Can't teleport there!",2000, NotificationType.ERROR);
                        return;
                    }

                    for (double ahead = 0; ahead < maximumStepDistance; ahead += maximumStepDistance / 100) {
                        Vec3 startVec = RotationUtil.getVectorForRotation(0, angleYaw);
                        Vec3 endVec = position.addVector(startVec.xCoord * ahead, startVec.yCoord * ahead, startVec.zCoord * ahead);
                        MovingObjectPosition blockAhead = mc.world.rayTraceBlocks(position, endVec,
                                false,
                                false,
                                false);
                        if (blockAhead != null) {
                            if (blockAhead.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                                distanceToBlock = ahead;
                                break;
                            }
                        }
                    }

                    Block block = mc.world.getBlockState(
                            new BlockPos(
                                    mc.player.posX - Math.sin(Math.toRadians(angleYaw)) * (step + maximumStepDistance),
                                    height,
                                    mc.player.posZ + Math.cos(Math.toRadians(angleYaw)) * (step + maximumStepDistance)))
                            .getBlock();


                    if (distanceToBlock > 0 && block != Blocks.tallgrass) {


                        double dOffsetX = -Math.sin(Math.toRadians(angleYaw)) * (distanceToBlock - 0.3);
                        double dOffsetZ = Math.cos(Math.toRadians(angleYaw)) * (distanceToBlock - 0.3);

                        sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
                                mc.player.posX + xAxis + dOffsetX,
                                height,
                                mc.player.posZ + zAxis + dOffsetZ,
                                true
                        ));

                        double blockHeight = block == Blocks.air ? 0.5 : block.getBlockBoundsMaxY() - block.getBlockBoundsMinY();
                        print(blockHeight,block);
                        double stepHeight = blockHeight - height % 1;

                        if (stepHeight < 0.625) {
                            sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
                                    mc.player.posX + xAxis + dOffsetX,
                                    height + stepHeight,
                                    mc.player.posZ + zAxis + dOffsetZ,
                                    false
                            ));
                        } else {
                            sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
                                    mc.player.posX + xAxis + dOffsetX,
                                    height + 0.41999998688698,
                                    mc.player.posZ + zAxis + dOffsetZ,
                                    false
                            ));
                            sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
                                    mc.player.posX + xAxis + dOffsetX,
                                    height + 0.7531999805212,
                                    mc.player.posZ + zAxis + dOffsetZ,
                                    false
                            ));
                        }
                        height += stepHeight;
                    } else {
                        double fallDistance = 0;
                        Vec3 newPos = getPositionForVector(mc.player.posX + xAxis, height, mc.player.posZ + zAxis);
                        for (double downCast = 0; downCast < 99; downCast += 0.0001) {
                            Vec3 startVec = RotationUtil.getVectorForRotation(90, 0);
                            Vec3 endVec = newPos.addVector(startVec.xCoord * downCast, startVec.yCoord * downCast, startVec.zCoord * downCast);
                            MovingObjectPosition blockBelow = mc.world.rayTraceBlocks(newPos, endVec,
                                    false,
                                    false,
                                    false);
                            if (blockBelow != null) {
                                if (blockBelow.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                                    Block block1 = mc.world.getBlockState(blockBelow.getBlockPos()).getBlock();
                                    if (block1 != Blocks.tallgrass) {
                                        fallDistance = MathHelper.incValue(MathHelper.round(downCast, 3), 0.125);
                                        if (fallDistance != 0) {
                                            print(fallDistance + " " + block1);
                                        }
                                        break;
                                    }
                                }
                            }
                        }

                        if (fallDistance > 0) {
                            double forwardMotion = 0;
                            Vec3 trollPosition = getPositionForVector(mc.player.posX + xAxis, height - 0.01, mc.player.posZ + zAxis);
                            for (double forwardCast = 0; forwardCast < maximumStepDistance; forwardCast += 0.001) {
                                Vec3 startVec = RotationUtil.getVectorForRotation(0, angleYaw);
                                Vec3 endVec = trollPosition.addVector(startVec.xCoord * forwardCast, startVec.yCoord * forwardCast, startVec.zCoord * forwardCast);
                                MovingObjectPosition blockBelow = mc.world.rayTraceBlocks(trollPosition, endVec,
                                        false,
                                        false,
                                        false);
                                if (blockBelow != null) {
                                    if (blockBelow.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
                                        forwardMotion = forwardCast;
                                    }
                                }
                            }

                            double fwX = -Math.sin(Math.toRadians(angleYaw)) * (forwardMotion + 0.5);
                            double fwZ = Math.cos(Math.toRadians(angleYaw)) * (forwardMotion + 0.5);

                            sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
                                    mc.player.posX + xAxis + fwX,
                                    height,
                                    mc.player.posZ + zAxis + fwZ,
                                    true
                            ));


                            double fell = 0;
                            double downMotion = 0;
                            double midAirPosition = height;

                            for (int i = 0; i < 99; i++) {
                                if (fell < fallDistance) {
                                    downMotion += 0.08;
                                    downMotion *= 0.980000019073486;
                                    fell += downMotion;
                                    midAirPosition = height - fell;

                                    if (fell < fallDistance) {
                                        sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
                                                mc.player.posX + xAxis + fwX,
                                                midAirPosition,
                                                mc.player.posZ + zAxis + fwZ,
                                                false
                                        ));
                                    }
                                } else {
                                    height -= fallDistance;
                                    break;
                                }
                            }
                        } else {
                            sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
                                    mc.player.posX + xAxis,
                                    height,
                                    mc.player.posZ + zAxis,
                                    true
                            ));
                        }
                    }

                }
                mc.player.setPosition(x, height, z);
            }
        }
    }



    @EventTarget
    public void onRender3D(Render3DEvent event) {
        if (getPosition() != null) {
            if (getPosition() != null && getPosition().typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                BlockPos pos = getPosition().getBlockPos();
                RenderUtils.pre3D();
                RenderUtils.setColor(color.getAwtColor());


                double x = pos.getX() - mc.getRenderManager().renderPosX;
                double y = pos.getY() - mc.getRenderManager().renderPosY;
                double z = pos.getZ() - mc.getRenderManager().renderPosZ;
                double height = mc.world.getBlockState(pos).getBlock().getBlockBoundsMaxY() - mc.world.getBlockState(pos).getBlock().getBlockBoundsMinY();



                GL11.glLineWidth(1);
                GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex3d(x, y, z);
                GL11.glVertex3d(x, y + height, z);
                GL11.glEnd();
                GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex3d(x + 1, y, z);
                GL11.glVertex3d(x + 1, y + height, z);
                GL11.glEnd();
                GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex3d(x + 1, y, z + 1);
                GL11.glVertex3d(x + 1, y + height, z + 1);
                GL11.glEnd();
                GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex3d(x, y, z + 1);
                GL11.glVertex3d(x, y + height, z + 1);
                GL11.glEnd();
                GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex3d(x, y, z);
                GL11.glVertex3d(x + 1, y, z);
                GL11.glEnd();
                GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex3d(x, y + height, z);
                GL11.glVertex3d(x + 1, y + height, z);
                GL11.glEnd();
                GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex3d(x, y, z);
                GL11.glVertex3d(x, y, z + 1);
                GL11.glEnd();
                GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex3d(x, y + height, z);
                GL11.glVertex3d(x, y + height, z + 1);
                GL11.glEnd();
                GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex3d(x + 1, y, z + 1);
                GL11.glVertex3d(x + 1, y, z + 1);
                GL11.glEnd();
                GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex3d(x + 1, y + height, z + 1);
                GL11.glVertex3d(x + 1, y + height, z + 1);
                GL11.glEnd();
                GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex3d(x + 1, y, z + 1);
                GL11.glVertex3d(x + 1, y, z);
                GL11.glEnd();
                GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex3d(x + 1, y + height, z + 1);
                GL11.glVertex3d(x + 1, y + height, z);
                GL11.glEnd();
                GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex3d(x, y, z + 1);
                GL11.glVertex3d(x + 1, y, z + 1);
                GL11.glEnd();
                GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex3d(x, y + height, z + 1);
                GL11.glVertex3d(x + 1, y + height, z + 1);
                GL11.glEnd();
                RenderUtils.post3D();
            }
        }
    }

    public Vec3 getPositionForVector(double x, double y, double z) {
        return new Vec3(x, y, z);
    }
}
