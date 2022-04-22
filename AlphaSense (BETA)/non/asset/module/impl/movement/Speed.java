package non.asset.module.impl.movement;

import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import non.asset.Clarinet;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.player.MotionEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.module.impl.SpeedModifier;
import non.asset.module.impl.Combat.Aura;
import non.asset.utils.OFC.MathUtils;
import non.asset.utils.OFC.MoveUtil;
import non.asset.utils.OFC.TimerUtil;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.EnumValue;

public class Speed extends Module {
    private int stage = 1, stageOG = 1;
    private double moveSpeed, lastDist, moveSpeedOG, lastDistOG;
    private EnumValue<Mode> mode = new EnumValue<>("Mode", Mode.WATCHDOG);
    private boolean callGhostBusters = false;
    
    private BooleanValue sprintcheck = new BooleanValue("Sprint Check", false);
    private BooleanValue intell = new BooleanValue("Intelligent", false);
    
    
    public static boolean strafeDirection;
    private int voidTicks;

    public Speed() {
        super("Speed", Category.MOVEMENT);
        setDescription("Make you jump more faster");
    }

    public enum Mode {
        WATCHDOG, YPORT, NCP, REDESKY, VERUS, WATCHDUCK
    }

    @Override
    public void onEnable() {
        if (getMc().thePlayer == null) return;
        lastDist = 0;
        callGhostBusters = false;
        moveSpeed = 0;
    }

    @Override
    public void onDisable() {
        if (getMc().thePlayer == null) return;
        getMc().timer.timerSpeed = 1f;
    }


    @Handler
    public void onPacket(PacketEvent event) {
    	if(event.isSending()) return;
    	
    	if(sprintcheck.isEnabled()) {
	    	if(mc.thePlayer.isSprinting()) {
		    	if(event.getPacket() instanceof S08PacketPlayerPosLook) {
					mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, Action.STOP_SPRINTING));
		    	}
		    	if(event.getPacket() instanceof C0BPacketEntityAction) {
		    		C0BPacketEntityAction wtf = new C0BPacketEntityAction();
		    		if(wtf.getAction() == wtf.getAction().STOP_SPRINTING) {
			    		TimerUtil timeridk = new TimerUtil();	
						mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, Action.START_SPRINTING));
		    		}
		    	}
	    	}
    	}
    	
    	switch (mode.getValue()) {
		case NCP:
			break;
		case REDESKY:
			break;
		case VERUS:
			break;
		case WATCHDOG:
			break;
		case WATCHDUCK:
			break;
		case YPORT:
			break;
		default:
			break;
    	
    	}
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
		setSuffix(mode.getValue().name().toLowerCase());
        final boolean tick = getMc().thePlayer.ticksExisted % 2 == 0;
        lastDist = Math.sqrt(((getMc().thePlayer.posX - getMc().thePlayer.prevPosX) * (getMc().thePlayer.posX - getMc().thePlayer.prevPosX)) + ((getMc().thePlayer.posZ - getMc().thePlayer.prevPosZ) * (getMc().thePlayer.posZ - getMc().thePlayer.prevPosZ)));
        
        if(sprintcheck.isEnabled()) {
        	if(mc.gameSettings.isKeyDown(mc.gameSettings.keyBindSprint)) {
        		if(!mc.thePlayer.isCollidedHorizontally) {
        			if(!mc.thePlayer.isUsingItem()) {
        				if(!mc.thePlayer.isSprinting()) {
        					mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, Action.START_SPRINTING));
        				}
        			}
        		}
        	}
        }

        
        if (lastDist > 5) lastDist = 0.0D;
        switch (mode.getValue()) {
        
        	case REDESKY:
        		if (event.isPre()) {
                    if(mc.thePlayer.isMoving()) {
                    	if(mc.thePlayer.onGround) {
                    		mc.thePlayer.isAirBorne = true;
                    		SpeedModifier.setSpeed((float) MoveUtil.getSpeed());
                    		mc.thePlayer.jump();
                    		mc.timer.timerSpeed = mc.thePlayer.ticksExisted % 3 == 0 ? 0.9f : 1.1f;
                    	}
                    }
        		}
				
        		break;
        	
        	case VERUS:
                moveSpeed *= 1.4F;
                moveSpeed = lastDist - lastDist / 159D;
                ++stage;
                lastDist = 0.0D;
                
        		if (event.isPre()) {
                    if(mc.thePlayer.isMoving()) {
                    	if(mc.thePlayer.onGround) {
                    		mc.thePlayer.jump();
                    	}
                    	SpeedModifier.setSpeed(0.3347f);
                    }
        		}
        		break;
            case WATCHDOG:
                if (event.isPre()) {
                    if (event.getY() % 0.015625 == 0.0) {
                        event.setY(event.getY() + 5.3424E-4);
                        event.setOnGround(false);
                    }
                    if (getMc().thePlayer.motionY > 0.3) {
                        event.setOnGround(true);
                    }
                }
                break;
            
            default:break;
        }

        
        if(callGhostBusters == true) {
        	event.setCanceled(true);
        }
        
    }

    @Handler
    public void onMotion(MotionEvent event) {
        double forward = getMc().thePlayer.movementInput.moveForward, strafe = getMc().thePlayer.movementInput.moveStrafe, yaw = getMc().thePlayer.rotationYaw;
        if (getMc().thePlayer.isOnLiquid()) return;
        
        if(intell.isEnabled()) {
        	if(callGhostBusters == true) {
        		return;
        	}
        }
        
        switch (mode.getValue()) {
        	case WATCHDUCK:
				if ((getMc().thePlayer.moveForward != 0.0F || getMc().thePlayer.moveStrafing != 0.0F)) {
					if(getMc().thePlayer.onGround) {
                        float motionY = 0.4201f;
						event.setY(getMc().thePlayer.motionY = motionY);
					}else {
						SpeedModifier.setSpeed(0.44599f);
					}
				}
        		break;
            case WATCHDOG:
            case YPORT:
                if (mode.getValue() == Mode.YPORT && !getMc().thePlayer.isCollidedHorizontally) {
                    if (MathUtils.roundToPlace(getMc().thePlayer.posY - (int) getMc().thePlayer.posY, 3) == MathUtils.roundToPlace(0.4, 3)) {
                        event.setY((getMc().thePlayer.motionY = 0.2));
                    }
                    if (MathUtils.roundToPlace(getMc().thePlayer.posY - (int) getMc().thePlayer.posY, 3) == MathUtils.roundToPlace(0.6, 3)) {
                        event.setY((getMc().thePlayer.motionY = -0.2));
                    }
                    if (MathUtils.roundToPlace(getMc().thePlayer.posY - (int) getMc().thePlayer.posY, 3) == MathUtils.roundToPlace(0.4, 3)) {
                        event.setY((getMc().thePlayer.motionY = -0.3));
                    }
                }
                switch (stage) {
                    case 0:
                        ++stage;
                        lastDist = 0.0D;
                        break;
                    case 2:
                        lastDist = 0.0D;
                        float motionY = 0.4001f;
                        if ((getMc().thePlayer.moveForward != 0.0F || getMc().thePlayer.moveStrafing != 0.0F) && getMc().thePlayer.onGround) {
                            if (getMc().thePlayer.isPotionActive(Potion.jump))
                                motionY += ((getMc().thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.099F);
                            event.setY(getMc().thePlayer.motionY = motionY);
                            if (!Clarinet.INSTANCE.getModuleManager().getModule("scaffold").isEnabled()) {
                                moveSpeed *= getMc().thePlayer.isPotionActive(Potion.moveSpeed) ? (getMc().thePlayer.isPotionActive(Potion.jump) ? 1.95F : 2.05F) : 1.895F;
                            } else {
                                moveSpeed *= 1.4F;
                            }
                        }
                        break;
                    case 3:
                        double boost = Clarinet.INSTANCE.getModuleManager().getModule("scaffold").isEnabled() ? 0.725 : (getMc().thePlayer.isPotionActive(Potion.moveSpeed) ? (getMc().thePlayer.isPotionActive(Potion.jump) ? 0.915f : 0.725f) :
                                0.71625f);
                        moveSpeed = lastDist - boost * (lastDist - getBaseMoveSpeed());
                        break;
                    default:
                        ++stage;
                        if ((getMc().theWorld.getCollidingBoundingBoxes(getMc().thePlayer, getMc().thePlayer.getEntityBoundingBox().offset(0.0D, getMc().thePlayer.motionY, 0.0D)).size() > 0 || getMc().thePlayer.isCollidedVertically) && stage > 0) {
                            stage = getMc().thePlayer.moveForward == 0.0F && getMc().thePlayer.moveStrafing == 0.0F ? 0 : 1;
                        }
                        moveSpeed = lastDist - lastDist / 159D;
                        break;
                }
                moveSpeed = Math.max(moveSpeed, getBaseMoveSpeed());
                setMoveSpeed(event, moveSpeed);
                ++stage;
                break;
            case NCP:
            	if(stage == 0) {
                    ++stage;
                    lastDist = 0.0D;
            	}

            	if(stage == 2) {
                    double motionY = 0.4025;
                    if ((getMc().thePlayer.moveForward != 0.0F || getMc().thePlayer.moveStrafing != 0.0F) && getMc().thePlayer.onGround) {
                        if (getMc().thePlayer.isPotionActive(Potion.jump))
                            motionY += ((getMc().thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
                        event.setY(getMc().thePlayer.motionY = motionY);
                        moveSpeed *= 1.4;
                    }
            	}
            	if(stage == 3) {
                    moveSpeed = lastDist - (0.8 * (lastDist - getBaseMoveSpeed()));
            	}
            	if(stage != 0 && stage != 2 && stage != 3) {
                    if ((getMc().theWorld.getCollidingBoundingBoxes(getMc().thePlayer, getMc().thePlayer.getEntityBoundingBox().offset(0.0D, getMc().thePlayer.motionY, 0.0D)).size() > 0 || getMc().thePlayer.isCollidedVertically) && stage > 0) {
                        stage = getMc().thePlayer.moveForward == 0.0F && getMc().thePlayer.moveStrafing == 0.0F ? 0 : 1;
                    }
                    moveSpeed = lastDist - lastDist / 167.0D;
            	}
            moveSpeed = Math.max(moveSpeed, getBaseMoveSpeed());
            setMoveSpeed(event, moveSpeed);
            ++stage;
            break;
			case REDESKY:
				break;
			case VERUS:
				break;
			default:
				break;
        }
    }
    
    public float getRotationFromPosition(final double x, final double z) {
        final double xDiff = x - getMc().thePlayer.posX;
        final double zDiff = z - getMc().thePlayer.posZ;
        return (float) (Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0f;
    }

    public boolean inVoid() {
        for (int i = (int) Math.ceil(getMc().thePlayer.posY); i >= 0; i--) {
            if (getMc().theWorld.getBlockState(new BlockPos(getMc().thePlayer.posX, i, getMc().thePlayer.posZ)).getBlock() != Blocks.air) {
                return false;
            }
        }
        return true;
    }

    private void setMoveSpeed(final MotionEvent event, final double speed) {
        voidTicks++;
        if (Aura.target != null) {
            if (inVoid() && voidTicks > 4) {
                voidTicks = 0;
                strafeDirection = !strafeDirection;
            }
        }
        boolean shouldStrafe = Clarinet.INSTANCE.getModuleManager().getModule("targetstrafe").isEnabled() && TargetStrafe.indexPos != null && TargetStrafe.target != null && !(!getMc().gameSettings.keyBindJump.isKeyDown() && TargetStrafe.spaceOnly.isEnabled());
        double forward = shouldStrafe ? ((Math.abs(getMc().thePlayer.movementInput.moveForward) > 0 || Math.abs(getMc().thePlayer.movementInput.moveStrafe) > 0) ? 1 : 0) : getMc().thePlayer.movementInput.moveForward;
        double strafe = shouldStrafe ? 0 : getMc().thePlayer.movementInput.moveStrafe;
        float yaw = shouldStrafe ? getRotationFromPosition(TargetStrafe.indexPos.xCoord, TargetStrafe.indexPos.zCoord) : getMc().thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            event.setX(forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw)));
            event.setZ(forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw)));
        }
    }

    private double[] getDirectionalSpeed(final double speed) {
        float forward = getMc().thePlayer.movementInput.moveForward;
        float side = getMc().thePlayer.movementInput.moveStrafe;
        float yaw = getMc().thePlayer.prevRotationYaw + (getMc().thePlayer.rotationYaw - getMc().thePlayer.prevRotationYaw) * getMc().timer.renderPartialTicks;
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            } else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[]{posX, posZ};
    }

    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (getMc().thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = getMc().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + (0.2 * amplifier);
        }
        return baseSpeed;
    }
}

