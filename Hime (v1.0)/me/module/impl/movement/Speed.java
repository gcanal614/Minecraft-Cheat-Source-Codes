package me.module.impl.movement;

import java.util.ArrayList;

import me.Hime;
import me.notification.Notification;
import me.notification.NotificationManager;
import me.notification.NotificationType;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import org.lwjgl.input.Keyboard;

import me.event.impl.EventReceivePacket;
import me.event.impl.EventUpdate;
import me.event.impl.MoveEvent;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.MovementUtils;
import me.util.TimeUtil;
import me.util.setBlockAndFacing.BlockUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

public class Speed extends Module {
	//TimeUtils2 time;
	TimeUtil time = new TimeUtil();
	BlockPos blockpos;
	public static Speed instance = new Speed();
	public Speed() {
		super("Speed", Keyboard.KEY_V, Category.MOVEMENT);
	}
	int ticks2;
	double difference;
	public Setting mode;
	public Setting speed;
	public Setting lagback;
	public Setting strafe;
	public Setting timer;
	public Setting customspeed;
	public Setting motiony;
	public Setting damageboost;
	public Setting damagespeed;
	public Setting ncpHopTimer;
	private double moveSpeed2;
	private double lastDist2;
	private double G8Gay;
	private double moveSpeed2dxvxvxv;
	int ticks1 = 0;
	public double motionXZ;
	double rounded;
	private double stage3;
	public int mineplex;
	float mp2speed, mp2slowdown, mp2boost;
	private int stage;
	private double moveSpeed;
	double moveSpeed3 = 0.0D;
	private double mineplex2 = 0.0D;
	private double ViperMC = 0.0D;
	private double lastDist;
	private int delay2;
	private double y;
	private int stage2;
	private int airMoves;
	private int ticks;
	boolean half;
	private float groundTicks;
	boolean collided;
	private float air;
	private int hops;
	public Setting jump;
	boolean doSlow = false;
	double motion = 0;
	public double lastDistReal = 0;

	public void onEnable() {
		this.hops = 1;
		super.onEnable();
		this.moveSpeed3 = 0.0D;
		// this.moveSpeed = MovementUtils.getBaseMoveSpeed();
		this.mineplex2 = -2.0D;
		this.stage3 = 0.0D;
		this.stage2 = 0;
		mc.thePlayer.speedInAir = .02F;
	}

	public void onDisable() {
		this.y = 0.0D;
		mc.thePlayer.speedInAir = (float) 0.02;
		//this.moveSpeed = MovementUtils.getBaseMoveSpeed();
		this.stage = 0;
		lastDistReal = 0;
		mc.timer.timerSpeed = 1.0F;
		super.onDisable();
		mc.timer.timerSpeed = 1F;
		// ((Object) mc.thePlayer).setSpeed(1F);

		// mc.thePlayer.motionX=0;
		// mc.thePlayer.motionY=0;
		// mc.thePlayer.motionZ=0;
		mc.thePlayer.speedInAir = .02F;
	}
	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<String>();
		options.add("Hypixel");
		options.add("HypixelTimer");
		options.add("HypixelHop");
		options.add("HypixelFast");
		options.add("HypixelOnGround");
		options.add("HypixelOnGround2");
		options.add("Hypixel Lowhop");
		options.add("Hypixel Lowhop2");
		options.add("NCP YPort");
		options.add("NCPHop");
		options.add("Vanilla");
		options.add("Mineplex");
		options.add("Mineplex Lowhop");
		options.add("MineplexOnGround");
		options.add("ViperMC");
		options.add("Ghostly");
		options.add("McGamester");
		options.add("Redesky");
		options.add("RedeskyTest");
		options.add("Legit");
		Hime.instance.settingsManager.rSetting(mode = new Setting("Speed Mode", this, "Hypixel", options));
		Hime.instance.settingsManager.rSetting(speed = new Setting("Speed", this, 2, 0, 20, false));
		Hime.instance.settingsManager.rSetting(customspeed = new Setting("Mineplex Custom Speed", this, 0.57D, 0, 1.3D, false));
		Hime.instance.settingsManager.rSetting(motiony = new Setting("Lowhop Motion Y", this, 0.12D, 0, 1.5D, false));
		Hime.instance.settingsManager.rSetting(lagback = new Setting("Lagback Check", this, false));
		Hime.instance.settingsManager.rSetting(strafe = new Setting("Watchdog Strafe Disabler", this, false));
		Hime.instance.settingsManager.rSetting(timer = new Setting("Timer Change Hypixel", this, true));
		Hime.instance.settingsManager.rSetting(damageboost = new Setting("Damage Boost", this, true));
		Hime.instance.settingsManager.rSetting(damagespeed = new Setting("Damage Boost Speed", this, 0.19D, 0.1, 1D, false));
		Hime.instance.settingsManager.rSetting(ncpHopTimer = new Setting("Ncp Hop Timer", this, 35f, 1f, 35.f, false));
		Hime.instance.settingsManager.rSetting(jump = new Setting("Vanilla Jump", this, true));
	}

	@Handler
	public void onPre(EventUpdate event) {
		if(event.isPre()) {
			if(mode.getValString().equalsIgnoreCase("MineplexOnGround")) {
				// System.out.println("ee");

				if (this.airSlot() == -10)
					return;
				if (mc.thePlayer.isMoving()) {
					this.mc.getNetHandler().getNetworkManager().sendPacket(new C09PacketHeldItemChange(this.airSlot()));
					BlockUtil.placeHeldItemUnderPlayer();
				} else {
					this.mc.getNetHandler().getNetworkManager().sendPacket(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
				}
				double targetSpeed = this.speed.getValDouble() / 2;
				if (this.moveSpeed3 < targetSpeed) {
					this.moveSpeed3 += 0.12412D;
				} else {
					this.moveSpeed3 = targetSpeed;
				}
				MovementUtils.actualSetSpeed(0.28);
				//MovementUtils.actualSetSpeed(this.speed.getValDouble());
			}
			if(mode.getValString().equalsIgnoreCase("Mineplex")) {
				double speed = 0.15D;
				if (this.mc.thePlayer.isCollidedHorizontally || !mc.thePlayer.isMoving())
					this.mineplex2 = -9.0D;
				mc.thePlayer.motionX = 0;
				mc.thePlayer.motionZ =0;
				if (MovementUtils.isOnGround(0.009D) && mc.thePlayer.isMoving()) {
					this.stage3 = 14D;
					this.mc.thePlayer.motionY = 0.4268D;
					//  this.mc.thePlayer.motionY = 0.42D;
					if (this.mineplex2 < 1.0D)
						this.mineplex2++;
					if (this.mc.thePlayer.posY != (int)this.mc.thePlayer.posY)
						this.mineplex2 = -0.3D;
					this.mc.timer.timerSpeed = 2.001F;
				} else {
					if (this.mc.timer.timerSpeed == 2.001F)
						this.mc.timer.timerSpeed = 1.0F;
					// speed = 0.62D - this.stage3 / 300.0D + this.mineplex / 5.0D;
					//0.57D
					speed = this.customspeed.getValDouble() - this.stage3 / 300.0D + this.mineplex / 5.0D;
					this.stage3++;
				}
				MovementUtils.actualSetSpeed(speed);
				// this.mc.thePlayer.motionY += 0.001D;
				this.motionXZ = speed;
			}
		}
	}


	public static int airSlot() {
		for (int x = 0; x < 8; x++) {
			if (mc.thePlayer.inventory.mainInventory[x] == null) {
				return x;
			}
		}
		Hime.instance.addClientChatMessage("Clear a hotbar slot");
		return -10;
	}

	@Handler
	public void onUpdate(EventUpdate event) {
		double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
		double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
		lastDistReal = Math.sqrt(xDist * xDist + zDist * zDist);
		this.setSuffix(mode.getValString());
		if (event.isPre()) {
			if(mode.getValString().equalsIgnoreCase("NCPHop")) {

				double Y = 0.00;

				MovementUtils.doStrafe(0.25D);
				if (mc.thePlayer.isMoving()) {
					if (!mc.thePlayer.onGround) {

						mc.thePlayer.motionY -= Y;
						mc.timer.timerSpeed = 1f;
					}
					if (mc.thePlayer.onGround) {

						mc.thePlayer.jump();

						if (time.hasTimePassed(1500)) {
							mc.timer.timerSpeed = (float) ncpHopTimer.getValDouble();
							time.reset();
						}

					}
				}
			}
			if(this.mode.getValString().equalsIgnoreCase("Hypixel Lowhop")) {
				if (mc.thePlayer.isMoving()) {
					if (mc.thePlayer.onGround) {
						//mc.thePlayer.motionY = 0.22D;
						//mc.thePlayer.motionY -= .30000001192092879;
						//	mc.thePlayer.motionY = 0.12D;
						mc.thePlayer.motionY = this.motiony.getValDouble();
					}
				}
			}
		}
		if(this.mode.getValString().equalsIgnoreCase("HypixelHop")) {
			mc.thePlayer.cameraYaw = -0.00F;
			if (mc.thePlayer.onGround && mc.thePlayer.isMoving()) {
				if (event.isPre()) {
					///event.setY(event.getY() + 7.0312E-9);
					//event.setY(event.getY() + 1.67346739E-4);
				}
				//mc.thePlayer.jump();
				//event.setY(event.getY() + 4.67346739E-6);

				if(this.strafe.getValBoolean()){
					event.setY(event.getY() + (0.0003F + (Math.random() * 0.0002F)));
				}
				if(this.timer.getValBoolean()) {
					mc.timer.timerSpeed = 1.1123345F;
				}
			}
		}
		if(this.mode.getValString().equalsIgnoreCase("Hypixel Lowhop2")) {
			mc.thePlayer.cameraYaw = -0.00F;
			if (mc.thePlayer.onGround &&  mc.thePlayer.isMoving()) {
				if (event.isPre()) {
					///event.setY(event.getY() + 7.0312E-9);
					//event.setY(event.getY() + 1.67346739E-4);
				}
				//mc.thePlayer.jump();
				//event.setY(event.getY() + 4.67346739E-6);

				event.setY(event.getY() + (0.0003F + (Math.random() * 0.0002F)));
				mc.timer.timerSpeed = 1.1123345F;
			}
		}
		if(mode.getValString().equalsIgnoreCase("HypixelOnGround2")) {
			mc.thePlayer.cameraYaw = -0.00F;
			if (mc.thePlayer.onGround &&  mc.thePlayer.isMoving()) {
				if (event.isPre()) {
					///event.setY(event.getY() + 7.0312E-9);
					//event.setY(event.getY() + 1.67346739E-4);
				}
				//mc.thePlayer.jump();
				//event.setY(event.getY() + 4.67346739E-6);

				event.setY(event.getY() + (0.0003F + (Math.random() * 0.0002F)));

				mc.timer.timerSpeed = 1.1123345F;
			}
		}
		if(mode.getValString().equalsIgnoreCase("Ghostly")) {
			double speed = 0.15D;
			if (this.mc.thePlayer.isCollidedHorizontally || !mc.thePlayer.isMoving())
				this.ViperMC = -2.0D;
			if (MovementUtils.isOnGround(0.001D) && mc.thePlayer.isMoving()) {
				this.stage3 = 0.0D;
				this.mc.thePlayer.motionY = 0.42D;
				if (this.ViperMC < 0.0D)
					this.ViperMC++;
				if (this.mc.thePlayer.posY != (int)this.mc.thePlayer.posY)
					this.mineplex2 = -1.0D;
				this.mc.timer.timerSpeed = 2.001F;
			} else {
				if (this.mc.timer.timerSpeed == 2.001F)
					this.mc.timer.timerSpeed = 1.0F;
				speed = 0.82D - this.stage3 / 300.0D + this.mineplex / 5.0D;
				this.stage3++;
			}
			MovementUtils.actualSetSpeed(speed);
			this.motionXZ = speed;
			if (mc.thePlayer.fallDistance < 1.0F) {
				mc.thePlayer.onGround = true;
				if (mc.thePlayer.motionY < 0.2D)
					mc.thePlayer.motionY *= 0.75D;

				mc.thePlayer.motionY += 0.005D;
			}
		}

		if(mode.getValString().equalsIgnoreCase("ViperMC")) {
			double speed = 0.15D;
			if (this.mc.thePlayer.isCollidedHorizontally || !mc.thePlayer.isMoving())
				this.ViperMC = -2.0D;
			if (MovementUtils.isOnGround(0.001D) && mc.thePlayer.isMoving()) {
				this.stage3 = 0.0D;
				this.mc.thePlayer.motionY = 0.42D;
				if (this.ViperMC < 0.0D)
					this.ViperMC++;
				if (this.mc.thePlayer.posY != (int)this.mc.thePlayer.posY)
					this.mineplex2 = -1.0D;
				this.mc.timer.timerSpeed = 2.001F;
			} else {
				if (this.mc.timer.timerSpeed == 2.001F)
					this.mc.timer.timerSpeed = 1.0F;
				speed = 0.62D - this.stage3 / 300.0D + this.mineplex / 5.0D;
				this.stage3++;
			}
			MovementUtils.actualSetSpeed(speed);
			this.motionXZ = speed;
		}

		if(mode.getValString().equalsIgnoreCase("Vanilla")) {
			MovementUtils.actualSetSpeed(speed.getValDouble() / 6);
			if(mc.thePlayer.onGround && mc.thePlayer.isMoving() && this.jump.getValBoolean()) {
				mc.thePlayer.jump();
			}
		}
		if(mode.getValString().equalsIgnoreCase("Legit")) {
			if(mc.thePlayer.onGround && mc.thePlayer.isMoving()) {
				mc.thePlayer.jump();
			}
		}
		if(mode.getValString().equalsIgnoreCase("McGamester")) {
			if(mc.thePlayer.onGround && mc.thePlayer.moveForward > 0) {
				double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
				double motionX = -Math.sin(yaw);
				double motionZ = Math.cos(yaw);
				mc.thePlayer.motionX = motionX * 5;
				mc.thePlayer.motionZ = motionZ * 5;
				mc.thePlayer.cameraYaw = 0.15F;
				mc.thePlayer.setSprinting(true);
				motionXZ = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
			}
		}

		if(mode.getValString().equalsIgnoreCase("HypixelTimer")) {
			if (mc.thePlayer.onGround &&  mc.thePlayer.isMoving()) {
				mc.thePlayer.jump();
			}
			mc.timer.timerSpeed = 1.3F;
		}

		if(mode.getValString().equalsIgnoreCase("Hypixel")) {
			if (mc.thePlayer.onGround &&  mc.thePlayer.isMoving()) {
				if (event.isPre()) {
					//event.setY(event.getY() + 7.0312E-9);
					if(this.strafe.getValBoolean()) {
						event.setY(event.getY() + 1.67346739E-4);
					}
				}
				mc.thePlayer.jump();
				//event.setY(event.getY() + 7.67346739E-14);
				if(this.timer.getValBoolean()) {
					mc.timer.timerSpeed = 1.2123345F;
				}
			}
        	/*  if (mc.thePlayer.onGround &&  mc.thePlayer.isMoving()) {
                  mc.thePlayer.jump();
                  event.setY(event.getY() + 1.67346739E-7);
                  //old value
                  //mc.timer.timerSpeed = 1.1123345F;
                  mc.timer.timerSpeed = 1.1012343F;
                  //TODO Hypixel1
              }*/
		}
		if(mode.getValString().equalsIgnoreCase("HypixelOnGround")) {
			if (mc.thePlayer.onGround &&  mc.thePlayer.isMoving()) {
				//old value
				//mc.timer.timerSpeed = 1.1123345F;
				mc.timer.timerSpeed = 1.1012343F;
			}
		}

		if(mode.getValString().equalsIgnoreCase("Hypixel Lowhop")) {
			if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround) {
				mc.thePlayer.jump();
			}
		}


		if(mode.getValString().equalsIgnoreCase("Mineplex Lowhop")) {
			this.mc.thePlayer.motionY -= 0.04000000000001D;
			MovementUtils.actualSetSpeed(0.2873D);
			if (this.mc.thePlayer.onGround )
				this.mc.thePlayer.motionY = 0.4299999D;
		}

		if(mode.getValString().equalsIgnoreCase("NCP YPort")) {
			if (this.mc.gameSettings.keyBindForward.pressed) {
				if (this.mc.thePlayer.onGround) {

					double speedValue = 0.27;
					motionXZ = speedValue;

					double yaw = Math.toRadians(this.mc.thePlayer.rotationYaw);
					double x = -Math.sin(yaw) * speedValue;
					double z = Math.cos(yaw) * speedValue;

					this.mc.thePlayer.motionX = x;
					this.mc.thePlayer.motionZ = z;
					this.mc.thePlayer.jump();

				} else {
					this.mc.thePlayer.motionY = -0.5;
				}
			}
		}
		if(mode.getValString().equalsIgnoreCase("Redesky")) {
			if (mc.thePlayer.onGround &&  mc.thePlayer.isMoving()) {
				if (event.isPre()) {
					mc.thePlayer.jump();
					// MovementUtils.actualSetSpeed(mc.thePlayer.motionX * mc.thePlayer.motionY + 0.24021);
					//  mc.timer.timerSpeed = 1.012f;
					mc.thePlayer.speedInAir = 0.035f;
				}
			}
		}
		if(mode.getValString().equalsIgnoreCase("RedeskyTest")) {
			if (mc.thePlayer.onGround && mc.thePlayer.isMoving()) {
				mc.thePlayer.jump();
				motion = 1.02995;
				doSlow = true;
				MovementUtils.doStrafe();
			} else {
				if (doSlow) {
					motion += 0.0015625;
					doSlow = false;
				}
				else {
					motion *= 0.99585;
				}
				motion = Math.max(0.999998, motion);
			}
			mc.thePlayer.motionX *= motion;
			mc.thePlayer.motionZ *= motion;
			// mc.thePlayer.setSpeed((float) motion);
		}
		if(mode.getValString().equalsIgnoreCase("HypixelFast")) {
			if(mc.thePlayer.onGround && mc.thePlayer.isMoving()) {
				mc.thePlayer.jump();
				if(!mc.thePlayer.onGround && mc.thePlayer.isMoving()) {
					mc.timer.timerSpeed = 1.0850558259423f;
					mc.thePlayer.speedInAir = 0.02485261f;
					MovementUtils.actualSetSpeed(1.0862572);

				}
			}
		}


	}

	@Handler
	public void onMove(MoveEvent event) {

		if(mode.getValString().equalsIgnoreCase("Redesky")) {
			MovementUtils.doStrafe();
		}
		if(mode.getValString().equalsIgnoreCase("HypixelTimer")) {
			if (mc.thePlayer.isMoving()) {
				MovementUtils.doStrafe();
				float f1 = (float) (this.getMotion() < 0.5600000023841858D ? this.getMotion() / 1.0449999570846558D : 0.5600000023841858D);
				if ((mc.thePlayer.onGround) && (mc.thePlayer.isPotionActive(Potion.moveSpeed))) {
					f1 *= (1.0F + 0.13F * (0x1 | mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()));
				}
				//      MovementUtils.setSpeed(event, 0.041 + f1);
				//   actualSetSpeed(f1);
			} else {
				actualSetSpeed(this.getMotion());
			}
		}
		if(mode.getValString().equalsIgnoreCase("HypixelHop")) {
			//	System.out.println("");
			double motionY = 0.4025;
			if ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) && mc.thePlayer.onGround) {
				if (mc.thePlayer.isPotionActive(Potion.jump))
					motionY += ((mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
				event.actualSetSpeedY(mc.thePlayer.motionY = motionY);
			}
			if (mc.thePlayer.isMoving() && !mc.thePlayer.isCollidedHorizontally) {
				MovementUtils.doStrafe();
				float f1 = (float) (this.getMotion() < 0.5600000023841858D ? this.getMotion() / 1.0449999570846558D : 0.5600000023841858D);
				if ((mc.thePlayer.onGround) && (mc.thePlayer.isPotionActive(Potion.moveSpeed))) {
					f1 *= (1.0F + 0.13F * (0x1 | mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()));
				}
				moveSpeed = lastDist - lastDist / 149.0D;
				G8Gay = lastDist - lastDist + f1;
				float doggiegobyebrrrrrrrr;
				//	 float bomtGhostsMe = (float) (this.lastDistReal - this.lastDistReal / 2);
				//	 Hime.addClientChatMessage(lastDistReal + "");
				if(this.damageboost.getValBoolean() && mc.thePlayer.hurtTime > 4) {
					// Hime.addClientChatMessage("Damage Boost");
					//0.32
					doggiegobyebrrrrrrrr = (float) (this.damagespeed.getValDouble() + 0.003F - 0.002);
				}else {
					if(mc.thePlayer.isSprinting()) {
						doggiegobyebrrrrrrrr = (float) (0.168 + 0.003F - 0.002);
					}else {
						doggiegobyebrrrrrrrr = (float) (0.182 + 0.003F - 0.002);
					}
				}
				//best 0.1D
				MovementUtils.setSpeed(event, lastDist + lastDist + doggiegobyebrrrrrrrr + lastDist - moveSpeed + moveSpeed + G8Gay);

				//og is calculate f1 and not moveSpeed2
				//MovementUtils.calculateFriction(G8Gay, f1,MovementUtils.getBaseMoveSpeed());
				actualSetSpeed(f1);
				// actualSetSpeed(this.getMotion() + 0.0065D);
				//   mc.thePlayer.onGround = true;
				if(this.timer.getValBoolean()) {
					mc.timer.timerSpeed = 1.25f;

				}
				if(!mc.thePlayer.onGround) {
					mc.thePlayer.onGround = true;
				}else {
					mc.timer.timerSpeed = 1.0f;
				}
			} else {
				actualSetSpeed(this.getMotion());
				//  mc.thePlayer.onGround = false;

			}
		}
		if(mode.getValString().equalsIgnoreCase("Hypixel")) {
			if (mc.thePlayer.isMoving()) {
				MovementUtils.doStrafe();
				float f1 = (float) (this.getMotion() < 0.5600000023841858D ? this.getMotion() / 1.0449999570846558D : 0.5600000023841858D);
				if ((mc.thePlayer.onGround) && (mc.thePlayer.isPotionActive(Potion.moveSpeed))) {
					f1 *= (1.0F + 0.13F * (0x1 | mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()));
				}
				MovementUtils.setSpeed(event, 0.101 + f1);
				actualSetSpeed(f1);
				actualSetSpeed(this.getMotion() + 0.0095D);
				mc.thePlayer.onGround = true;
			} else {
				actualSetSpeed(this.getMotion());
				mc.thePlayer.onGround = false;
			}
		}
		if(mode.getValString().equalsIgnoreCase("HypixelOnGround2")) {
			mc.timer.timerSpeed = 1.35f;

			double motionY = 0.2666;
			if ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) && mc.thePlayer.onGround) {
				if (mc.thePlayer.isPotionActive(Potion.jump))
					motionY += ((mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
				event.actualSetSpeedY(mc.thePlayer.motionY = motionY - 0.22);
			}
			if (mc.thePlayer.isMoving() && !mc.thePlayer.isCollidedHorizontally) {
				MovementUtils.doStrafe();
				float f1 = (float) (this.getMotion() < 0.5600000023841858D ? this.getMotion() / 1.0449999570846558D : 0.5600000023841858D);
				if ((mc.thePlayer.onGround) && (mc.thePlayer.isPotionActive(Potion.moveSpeed))) {
					f1 *= (1.0F + 0.13F * (0x1 | mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()));
				}
				moveSpeed = lastDist - lastDist / 149.0D;
				G8Gay = lastDist - lastDist + f1;
				float doggiegobyebrrrrrrrr = (float) (0.1 + 0.003F - 0.002);
				//best 0.1D
				MovementUtils.setSpeed(event, lastDist + lastDist + doggiegobyebrrrrrrrr + lastDist - moveSpeed + moveSpeed + G8Gay);

				//og is calculate f1 and not moveSpeed2
				//MovementUtils.calculateFriction(G8Gay, f1, MovementUtils.getBaseMoveSpeed());
				actualSetSpeed(f1);
				// actualSetSpeed(this.getMotion() + 0.0065D);
				mc.thePlayer.onGround = true;
			} else {
				actualSetSpeed(this.getMotion());
				mc.thePlayer.onGround = false;
			}
		}
		if(mode.getValString().equalsIgnoreCase("HypixelOnGround")) {
			if (mc.thePlayer.isMoving()) {
				MovementUtils.doStrafe();
				float f1 = (float) (this.getMotion() < 0.5600000023841858D ? this.getMotion() / 1.0449999570846558D : 0.5600000023841858D);
				if ((mc.thePlayer.onGround) && (mc.thePlayer.isPotionActive(Potion.moveSpeed))) {
					f1 *= (1.0F + 0.13F * (0x1 | mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()));
				}
				moveSpeed = lastDist - lastDist / 149.0D;
				G8Gay = lastDist - lastDist + f1;
				float doggiegobyebrrrrrrrr = (float) (0.1 + 0.003F - 0.002);
				//best 0.1D
				MovementUtils.setSpeed(event, lastDist + lastDist + doggiegobyebrrrrrrrr + lastDist - moveSpeed + moveSpeed + G8Gay);
				//og is calculate f1 and not moveSpeed2
				actualSetSpeed(f1);
				actualSetSpeed(this.getMotion() + 0.0095D);
			} else {
				actualSetSpeed(this.getMotion());
			}
		}
		if(mode.getValString().equalsIgnoreCase("Hypixel Lowhop")) {
			final double yaw = MovementUtils.getYaw(true);
			if (mc.thePlayer.isMoving()) {
				MovementUtils.doStrafe(MovementUtils.getSpeed(), yaw);
				//og was 0
				if (mc.thePlayer.hurtTime > 0) {
				} else {
					mc.thePlayer.speedInAir = (float) (0.035D);
					mc.timer.timerSpeed = 1.102F;
					//System.out.println(G8Gay);
					//	System.out.println(0.255D + G8Gay);
					MovementUtils.doStrafe(MovementUtils.getBaseMoveSpeed() - 0.25500000052154065);
					//+ this.lastDist - 0.01F + 0.004D - 0.149D + 0.15D - 0.2D - 0.05f
					// moveSpeed2 = lastDist - lastDist / 159D;
					// lastDist = 0;
					// og was remove + 0.02;
					G8Gay = MovementUtils.getSpeed();
					//lastDist - lastDist + 0.255D + lastDist - moveSpeed + moveSpeed2 + G8Gay
					MovementUtils.setSpeed(event, 0.255D + G8Gay);
				}
				//	moveSpeed = lastDist - lastDist / 159D;
				MovementUtils.calculateFriction(MovementUtils.getSpeed(), yaw, MovementUtils.getBaseMoveSpeed());
			}
		}
		if(mode.getValString().equalsIgnoreCase("Hypixel Lowhop2")) {
			double motionY = 0.4025;
			if ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) && mc.thePlayer.onGround) {
				if (mc.thePlayer.isPotionActive(Potion.jump))
					motionY += ((mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
				event.actualSetSpeedY(mc.thePlayer.motionY = motionY - 0.22);
			}
			if (mc.thePlayer.isMoving() && !mc.thePlayer.isCollidedHorizontally) {
				MovementUtils.doStrafe();
				float f1 = (float) (this.getMotion() < 0.5600000023841858D ? this.getMotion() / 1.0449999570846558D : 0.5600000023841858D);
				if ((mc.thePlayer.onGround) && (mc.thePlayer.isPotionActive(Potion.moveSpeed))) {
					f1 *= (1.0F + 0.13F * (0x1 | mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()));
				}
				moveSpeed = lastDist - lastDist / 149.0D;
				G8Gay = lastDist - lastDist + f1;
				float doggiegobyebrrrrrrrr = (float) (0.1 + 0.003F - 0.002);
				//best 0.1D
				MovementUtils.setSpeed(event, lastDist + lastDist + doggiegobyebrrrrrrrr + lastDist - moveSpeed + moveSpeed + G8Gay);
				//og is calculate f1 and not moveSpeed2
				//MovementUtils.calculateFriction(G8Gay, f1, MovementUtils.getBaseMoveSpeed());
				actualSetSpeed(f1);
				// actualSetSpeed(this.getMotion() + 0.0065D);
				mc.thePlayer.onGround = true;
			} else {
				actualSetSpeed(this.getMotion());
				mc.thePlayer.onGround = false;
			}
		}
	}

	@Handler
	public final void onPacket(EventReceivePacket event) {
		if(mode.getValString().equalsIgnoreCase("HypixelHop")) {
			if(event.getPacket() instanceof C0FPacketConfirmTransaction) {
				event.cancel();
			}
		}
		if(this.lagback.getValBoolean()) {
			if (event.getPacket() instanceof S08PacketPlayerPosLook) {
				NotificationManager.show(new Notification(NotificationType.INFO, "Lagback Alert", "Disabled Speed Due to a Lagback!", 2));
				this.toggle();
			}
		}
	}

	public static double getMotion() {
		Minecraft localMinecraft = Minecraft.getMinecraft();
		double d1 = localMinecraft.thePlayer.motionX;
		double d2 = localMinecraft.thePlayer.motionZ;
		return Math.sqrt(d1 * d1 + d2 * d2);
	}


	public void actualSetSpeed(double paramDouble) {
		double d1 = mc.thePlayer.movementInput.moveForward;
		double d2 = mc.thePlayer.movementInput.moveStrafe;
		float f = mc.thePlayer.rotationYaw;
		if ((d1 == 0.0D) && (d2 == 0.0D)) {
			mc.thePlayer.motionX = 0.0D;
			mc.thePlayer.motionZ = 0.0D;
		} else {
			if (d1 != 0.0D) {
				int i = 20;
				if (d2 > 0.0D) {
					f += (d1 > 0.0D ? -35 : 35);
				} else if (d2 < 0.0D) {
					f += (d1 > 0.0D ? 35 : -35);
				}
				d2 = 0.0D;
				if (d1 > 0.0D) {
					d1 = 1.0D;
				} else if (d1 < 0.0D) {
					d1 = -1.0D;
				}
			}
			mc.thePlayer.motionX = (d1 * paramDouble * Math.cos(Math.toRadians(f + 90.0F)) + d2 * paramDouble * Math.sin(Math.toRadians(f + 90.0F)));
			mc.thePlayer.motionZ = (d1 * paramDouble * Math.sin(Math.toRadians(f + 90.0F)) - d2 * paramDouble * Math.cos(Math.toRadians(f + 90.0F)));
		}
	}

}
