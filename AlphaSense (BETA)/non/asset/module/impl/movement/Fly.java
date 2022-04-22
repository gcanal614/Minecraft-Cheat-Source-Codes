package non.asset.module.impl.movement;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.vecmath.Vector3d;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.potion.Potion;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.player.MotionEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.module.impl.SpeedModifier;
import non.asset.utils.PlayerManager;
import non.asset.utils.OFC.MoveUtil;
import non.asset.utils.OFC.TimerUtil;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.EnumValue;
import non.asset.utils.value.impl.NumberValue;

public class Fly extends Module {
	private TimerUtil timer = new TimerUtil();
	private TimerUtil bowfly = new TimerUtil();

	private TimerUtil functiontimer = new TimerUtil();

   private int aac5Status = 0;
   private double aac5LastPosX = 0.0D;
   private int aac5Same = 0;
   private C03PacketPlayer.C06PacketPlayerPosLook aac5QueuedPacket = null;
   private int aac5SameReach = 5;
   private boolean aac5FlyClip = false;
   private boolean aac5FlyStart = false;
   private boolean aac5nextFlag = false;
   private double aac5LastFlag = 0.0D;
   private LinkedList<Packet> packetQueue = new LinkedList();
	   
	private EnumValue<Modes> mode = new EnumValue<>("Mode", Modes.WATCHDOG);
	private EnumValue<BoostModes> boostModes = new EnumValue<>("Boost Mode", BoostModes.NORMAL, mode, "WATCHDOG");
	private BooleanValue boost = new BooleanValue("Boost", true, mode, "WATCHDOG");
	private BooleanValue extra = new BooleanValue("Extra Boost", true, boost, "true");
	private BooleanValue viewbob = new BooleanValue("ViewBob", true);

    private ArrayList<Packet> packets = new ArrayList<>();
    private Vector3d startVector;
	
	private NumberValue<Float> flyspeed = new NumberValue<>("Fly Speed", 2.0f, 0.1f, 3.0f, 0.1f, mode, "VANILLA");
	private double moveSpeed, lastDist, ascension;
	private int level, wait, momo0k, dggyuouiiu;

	public double posy;
	public double a = 0;
	public double antPitch = 0;
    private int count;
	public static float posCosX = 0;
	public static float posCosY = 0;
	public static float posCosZ = 0;
	public static float currentlyCosX = 0;
	public static float currentlyCosY = 0;
	public static float currentlyCosZ = 0;
	public static float flagPositionX = 0;
	public static float flagPositionY = 0;
	public static float flagPositionZ = 0;
	public static float lastYaw = 0;
	public static float lastPitch = 0;
	public double xxxYaw = 0;
	public double p1 = (float) 0;
	public double p2 = (float) 0;
	public double p3 = (float) 0;
	public boolean check1 = false;
	public boolean check2 = false;
	static Minecraft mc = Minecraft.getMinecraft();
	public Fly() {
		super("fly", Category.MOVEMENT);
		setDescription("Make you fly");
	}

	public enum Modes {
		VANILLA,  WATCHDOG, DUCK, VERUS, MINEMORA
	}

	public enum BoostModes {
		NORMAL, DAMAGE
	}

	@Override
	public void onEnable() {
		if (mc.thePlayer == null || mc.theWorld == null)
			return;
		
		reset();
	}
	
	@Override
	public void onDisable() {
		if (mc.thePlayer == null || mc.theWorld == null)
			return;
		disable();
	}

	@Handler
	public void onUpdate(UpdateEvent event) {
		if (mc.thePlayer == null)
			return;
		setSuffix(StringUtils.capitalize(mode.getValue().name().toLowerCase()));
		if (event.isPre()) {
			if (viewbob.getValue() && mc.thePlayer.isMoving()) {
				mc.thePlayer.cameraYaw = 0.089f;
			}
			switch (mode.getValue()) {
				case MINEMORA:
		            mc.thePlayer.motionY = 0;
					break;

				case DUCK:
					if (mc.gameSettings.keyBindJump.isKeyDown()) {
						mc.thePlayer.motionY = 0.5;
					}else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
						mc.thePlayer.motionY = -0.5;
					}else {
			            if (mc.thePlayer.ticksExisted % 2 == 0) {
			                mc.thePlayer.sendQueue.addToSendQueueNoEvents(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.26, mc.thePlayer.posZ, true));
			            } else {
			                mc.thePlayer.sendQueue.addToSendQueueNoEvents(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.26, mc.thePlayer.posZ, false));
			            }
			            mc.thePlayer.motionY = 0;
					}
					break;
		
				case VERUS:
					if(mc.thePlayer.ticksExisted % 2 == 0) {
			            mc.thePlayer.motionY = 0.015f;
					}else {
			            mc.thePlayer.motionY = -0.015f;
					}
					mc.thePlayer.onGround = true;
					break;
				
				case WATCHDOG:
					mc.thePlayer.onGround = true;
					if (mc.thePlayer.moveForward > 0) {
						if (boost.isEnabled()) {
							if(boostModes.getValue() != BoostModes.DAMAGE) {
								if (!timer.reach(135) && timer.reach(20)) {
									SpeedModifier.setSpeed(0.2f);
								} else {
									SpeedModifier.setSpeed(0.5f);
								}
								if (extra.isEnabled()) {
									SpeedModifier.setSpeed(1f);
								} else {
									SpeedModifier.setSpeed(0.5f);
								}
							}else {
								if(xxxYaw > 0.3) {
									xxxYaw -= 0.05;
								}
								SpeedModifier.setSpeed(extra.isEnabled() ? 0.3f + (float) xxxYaw : (float) xxxYaw);	
							}
						}
					}
					mc.thePlayer.motionY = 0;
					break;
				case VANILLA:
					if (mc.gameSettings.keyBindJump.isKeyDown())
						mc.thePlayer.motionY = flyspeed.getValue() / 2;
					else if (mc.gameSettings.keyBindSneak.isKeyDown())
						mc.thePlayer.motionY = -flyspeed.getValue() / 2;
					else
						mc.thePlayer.motionY = 0;
					break;
			}
		}
	}

	@Handler
	public void onMotion(MotionEvent event) {
		
		switch (mode.getValue()) {
			case VANILLA:
				setMoveSpeed(event, flyspeed.getValue());
				break;
			case WATCHDOG:
				if(boost.isEnabled()) {
					MoveUtil.setMoveSpeed(event, extra.isEnabled() ? 2 : 1);
				}
				break;
			case DUCK:
				MoveUtil.setMoveSpeed(event, 7);
				break;
			case VERUS:
	            setMoveSpeed(event, 5);
				break;
			case MINEMORA:
	            setMoveSpeed(event, 2);
	            break;
			default:
				break;
		}
	}

	@Handler
	public void onPacket(PacketEvent event) {
		if (getMc().thePlayer == null || getMc().theWorld == null)
			return;
		
		if (event.getPacket() instanceof S12PacketEntityVelocity) {
			event.setCanceled(true);
		}

		if ((event.getPacket()) instanceof S27PacketExplosion) {
			event.setCanceled(true);
		}
		
		switch(mode.getValue()) {
			case DUCK:
	       	 	if(((event.getPacket() instanceof C03PacketPlayer)) && (mc.thePlayer.ticksExisted % 80 == 0)) {
	                C03PacketPlayer c03 = (C03PacketPlayer)event.getPacket();
	                if(mc.thePlayer.fallDistance < 3) {
	                	c03.setY(0);
	                }
	       	 	}
	            if (mc.thePlayer.ticksExisted % 18 == 0) {
	            	mc.thePlayer.motionY = 0.5;
	            }
	       	 	mc.timer.timerSpeed = 0.6f;
	            if (mc.thePlayer.ticksExisted % 68 == 0) {
	        		mc.thePlayer.sendQueue.addToSendQueueNoEvents(new C03PacketPlayer.C04PacketPlayerPosition((mc.thePlayer.posX) - (mc.thePlayer.prevPosX), mc.thePlayer.posY - mc.thePlayer.prevPosY, (mc.thePlayer.posZ) - (mc.thePlayer.prevPosZ), true));
	            }else {
	            	if(((event.getPacket() instanceof C03PacketPlayer))) {
	                	C03PacketPlayer c03 = (C03PacketPlayer)event.getPacket();
	            		event.setCanceled(true); 	
	            	}
	    		}
				break;
			case VERUS:
				break;
			case VANILLA:
				break;
			case WATCHDOG:
				break;
			case MINEMORA:
				if (mc.thePlayer.ticksExisted % 3 == 0) {
					packets.add(new C03PacketPlayer.C04PacketPlayerPosition((mc.thePlayer.posX), mc.thePlayer.posY, (mc.thePlayer.posZ), true));
				}
				if(event.getPacket().getClass().getSimpleName().startsWith("C")) {
					packets.add(event.getPacket());
					event.setCanceled(true);
				}
				
				break;
			default:
				break;
			
		}
	}
	
	private boolean invCheck() {
        for (int i = 36; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                Item item = mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
                if (item instanceof ItemBow) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getArrowCount() {
        int arrowCount = 0;
        for (int i = 0; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                Item item = is.getItem();
                Item arrow = Item.getItemById(262);
                if (item == arrow) {
                    arrowCount += is.stackSize;
                }
            }
        }
        return arrowCount;
    }

    public int getBowCount() {
        int bowCount = 0;
        for (int i = 0; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                Item item = is.getItem();
                if (is.getItem() instanceof ItemBow) {
                    bowCount += is.stackSize;
                }
            }
        }
        return bowCount;
    }
	
	private void getBow() {
        ItemStack is = new ItemStack(Item.getItemById(262));
        try {
            if (mc.thePlayer.inventory.getCurrentItem() != null && !(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow))
                for (int i = 36; i < 45; i++) {
                    int theSlot = i - 36;
                    if (!mc.thePlayer.inventoryContainer.canAddItemToSlot(mc.thePlayer.inventoryContainer.getSlot(i), is, true)
                            && mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBow && mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null) {
                        mc.thePlayer.inventory.currentItem = theSlot;
                        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                        mc.playerController.updateController();
                        break;
                    }
                }

        } catch (Exception e) {
        }
    }

	private double getBaseMoveSpeed() {
		double n = 0.2873;
		if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			n *= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
		}
		return n;
	}

	private void setMoveSpeed(MotionEvent event, double speed) {
		double forward = mc.thePlayer.movementInput.moveForward;
		double strafe = mc.thePlayer.movementInput.moveStrafe;
		float yaw = mc.thePlayer.rotationYaw;
		if (forward == 0.0D && strafe == 0.0D) {
			event.setX(0);
			event.setZ(0);
		} else {
			if (forward != 0.0D) {
				if (strafe > 0.0D) {
					yaw += forward > 0.0D ? -45 : 45;
				} else if (strafe < 0.0D) {
					yaw += forward > 0.0D ? 45 : -45;
				}

				strafe = 0.0D;
				if (forward > 0.0D) {
					forward = 1.0D;
				} else if (forward < 0.0D) {
					forward = -1.0D;
				}
			}
			event.setX(forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw)));
			event.setZ(forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw)));
		}
	}
	private void setDownUpSpeed(double up, double down, boolean statico) {
		if (mc.gameSettings.keyBindJump.isKeyDown()) {
			mc.thePlayer.motionY = flyspeed.getValue() / up;
		}else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
			mc.thePlayer.motionY = -flyspeed.getValue() / down;
		}else {
			if(statico) {
				mc.thePlayer.motionY = 0;
			}
		}
	}
	
	private void disable() {
		if(mc.thePlayer == null || mc.theWorld == null) return;
		
		mc.timer.timerSpeed = 1f;
		
		if(packets.size() > 0) {
			packets.forEach(getMc().thePlayer.sendQueue.getNetworkManager()::sendPacket);
        	packets.clear();
		}
		
		switch (mode.getValue()) {
			case DUCK:
				break;
			case MINEMORA:
				break;
			case VANILLA:
				break;
			case VERUS:
				for (int a = 0; a < 10; a++) {
					mc.getNetHandler().addToSendQueue((Packet) packets);
				}
				break;
			case WATCHDOG:
				break;
			default:
				break;
			
		}
		
	}
	private void reset() {

		switch (mode.getValue()) {
			case DUCK:
				mc.thePlayer.jump();	
				if(mc.thePlayer.onGround) {
					PlayerManager.PacketDamageFall(3.394192039);
				}
				break;
			case VANILLA:
				break;
			case VERUS:
				PlayerManager.PacketDamageFall(-9);
				mc.thePlayer.jump();
				break;
			case WATCHDOG:
				mc.thePlayer.jump();
				PlayerManager.PacketDamageFall(3.3);
				break;
			case MINEMORA:
				mc.thePlayer.jump();
				//packets.add(new C03PacketPlayer.C04PacketPlayerPosition((mc.thePlayer.posX), mc.thePlayer.posY, (mc.thePlayer.posZ), true));
				//packets.add(new C03PacketPlayer.C04PacketPlayerPosition((mc.thePlayer.posX), Double.NEGATIVE_INFINITY, (mc.thePlayer.posZ), true));
				break;
			default:
				break;
		
		}
		xxxYaw = 0;
		check1 = false;
		check2 = false;
		moveSpeed = getBaseMoveSpeed();
		lastDist = 0.0D;
		dggyuouiiu = 0;
		ascension = 0;
		momo0k = 0;
		wait = 0;
		level = 0;
		lastDist = 0.0D;
		count = 0;
		lastYaw = mc.thePlayer.rotationYaw;
		lastPitch = mc.thePlayer.rotationPitch;
		posCosX = (float) mc.thePlayer.posX;
		posCosY = (float) mc.thePlayer.posY;
		posCosZ = (float) mc.thePlayer.posZ;
		currentlyCosX = (float) mc.thePlayer.posX;
		currentlyCosY = (float) mc.thePlayer.posY;
		currentlyCosZ = (float) mc.thePlayer.posZ;
		flagPositionX = (float) mc.thePlayer.posX;
		flagPositionY = (float) mc.thePlayer.posY;
		flagPositionZ = (float) mc.thePlayer.posZ;
        functiontimer.reset();
		functiontimer.reset();
		bowfly.reset();
		timer.reset();
	}
	
	
}
