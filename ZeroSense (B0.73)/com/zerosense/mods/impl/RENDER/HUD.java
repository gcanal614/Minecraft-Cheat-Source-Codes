package com.zerosense.mods.impl.RENDER;

import java.awt.Color;
import java.util.*;

import com.zerosense.Events.Event;
import com.zerosense.Events.impl.EventRenderGUI;
import com.zerosense.Methods;
import com.zerosense.Settings.ModeSetting;
import com.zerosense.Utils.MathUtils;
import com.zerosense.ZeroSense;
import com.zerosense.mods.Module;
import com.zerosense.mods.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;

import org.lwjgl.opengl.GL11;

public class HUD extends Module {
	public HUD(){
		super("HUD", 0, Category.RENDER);
		this.toggled = true;
		this.addSettings(arraycolor, Pos);
	}
	public static Color healthColor;
	public static int JJ;
	public static ModeSetting arraycolor = new ModeSetting("ArrayList color", "Astolfo", "PulseWhite", "PulseRed", "PulseBlue", "PulseGreen", "Random");
	public static ModeSetting Pos = new ModeSetting("ArrayList Pos", "Right", "Left");

	public static transient double healthBarTarget = 0, healthBar = 0;
	private String winning;
	Timer timer = new Timer();
	public static int healthRect;
	public Minecraft mc = Minecraft.getMinecraft();

	private static int getAbsoluteX() {
		return 410;
	}

	private static int getAbsoluteY() {
		return 300;
	}



	public static Color winColor;
/*
	private int getWinColor() {
		EntityLivingBase var1 = ZeroSense.killAura.target;
		if (var1.getHealth() > this.mc.thePlayer.getHealth()) {
			return (new Color(255, 0, 0, 255)).getRGB();
		} else if (var1.getHealth() == this.mc.thePlayer.getHealth() && var1.getHealth() != 20.0F && this.mc.thePlayer.getHealth() != 20.0F) {
			return (new Color(255, 255, 0, 255)).getRGB();
		} else if (var1.getHealth() < this.mc.thePlayer.getHealth()) {
			return (new Color(0, 255, 0, 255)).getRGB();
		} else if (var1.getHealth() == 0.0F) {
			return (new Color(0, 255, 0, 255)).getRGB();
		} else if (this.mc.thePlayer.getHealth() == 0.0F) {
			return (new Color(255, 0, 0, 255)).getRGB();
		} else {
			return var1.getHealth() == 20.0F && this.mc.thePlayer.getHealth() == 20.0F ? (new Color(0, 188, 255, 255)).getRGB() : (new Color(0, 0, 255, 255)).getRGB();
		}


	}
	*/

	public void renderTargetHud() {

	}

	public static int getHeight() {
		return 52;
	}
/*
	private static void drawWinColor() {
		Minecraft mc = Minecraft.getMinecraft();
		EntityLivingBase entityLivingBase = ZeroSense.killAura.target;
		if (entityLivingBase.getHealth() >= entityLivingBase.getMaxHealth()) {
			winColor = new Color(0, 255, 0, 255);
		} else if (entityLivingBase.getHealth() >= 18.0F) {
			winColor = new Color(255, 242, 0, 255);
		} else if (entityLivingBase.getHealth() >= 13.0F) {
			winColor = new Color(173, 117, 3, 255);
		} else if (entityLivingBase.getHealth() >= 10.0F) {
			winColor = new Color(173, 80, 3, 255);
		} else if (entityLivingBase.getHealth() >= 7.0F) {
			winColor = new Color(173, 80, 3, 255);
		} else if (entityLivingBase.getHealth() >= 5.0F) {
			winColor = new Color(184, 43, 0, 255);
		} else if (entityLivingBase.getHealth() <= 3.0F) {
			winColor = new Color(255, 0, 0, 255);
		}
		mc.fontRendererObj.drawString(getWin(), (getAbsoluteX() + 59), (getAbsoluteY() + 20), winColor.getRGB());
	}

 */
/*
	private void drawEntityHealth() {
		EntityLivingBase entityLivingBase = ZeroSense.killAura.target;
		if (entityLivingBase.getHealth() >= entityLivingBase.getMaxHealth()) {
			this.healthColor = new Color(0, 255, 0, 255);
			this.healthRect = 154;
		} else if (entityLivingBase.getHealth() >= 18.0F) {
			this.healthColor = new Color(255, 242, 0, 255);
			this.healthRect = 132;
		} else if (entityLivingBase.getHealth() >= 13.0F) {
			this.healthColor = new Color(173, 117, 3, 255);
			this.healthRect = 110;
		} else if (entityLivingBase.getHealth() >= 10.0F) {
			this.healthColor = new Color(173, 80, 3, 255);
			this.healthRect = 88;
		} else if (entityLivingBase.getHealth() >= 7.0F) {
			this.healthColor = new Color(173, 80, 3, 255);
			this.healthRect = 66;
		} else if (entityLivingBase.getHealth() >= 5.0F) {
			this.healthColor = new Color(184, 43, 0, 255);
			this.healthRect = 44;
		} else if (entityLivingBase.getHealth() <= 3.0F) {
			this.healthColor = new Color(255, 0, 0, 255);
			this.healthRect = 22;
		}
		Gui.drawRect(getAbsoluteX(), (getAbsoluteY() + 52), (getAbsoluteX() + this.healthRect), (getAbsoluteY() + 50), this.healthColor.getRGB());
	}

 */

	private int getScaledX() {
		final ScaledResolution newSR = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		return newSR.getScaledWidth();
	}

	private int getScaledY() {
		final ScaledResolution newSR = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		return newSR.getScaledHeight();
	}
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventRenderGUI) {

			int var8;

			ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
			FontRenderer var2 = this.mc.fontRendererObj;

			float var9 = (float) (MathUtils.square(mc.thePlayer.motionX) + MathUtils.square(mc.thePlayer.motionX));
			float var10 = (float) (MathUtils.round(Math.sqrt((double) var9) * 20.0D * (double) this.mc.timer.timerSpeed, (int) 2.0D));
				if (ModuleManager.flight.toggled || ModuleManager.speed.toggled) {
					var2.drawString(String.valueOf(new StringBuilder("BPS : " + var10)), sr.getScaledWidth() / 2 + 280, sr.getScaledHeight() / 2 + 167 , Methods.getRainbow(100, 3000, 0.6F, 1.0F).getRGB());}/*
				if (ZeroSense.killAura.target instanceof EntityLivingBase && !ZeroSense.killAura.target.isDead && this.mc.thePlayer.getDistanceToEntity((Entity) ZeroSense.killAura.target) < ZeroSense.killAura.range.getValue() + 2.0D && ZeroSense.killAura.isEnabled() && ZeroSense.killAura.targethud.isToggled()) {
					if (ZeroSense.killAura.targethudmode.is("ZeroSense")) {
						EntityLivingBase entityLivingBase = ZeroSense.killAura.target;
						final DecimalFormat decimalFormat2 = new DecimalFormat("#.#");
						decimalFormat2.applyPattern("#.#");
						Gui.drawRect(getAbsoluteX(), getAbsoluteY(), (getAbsoluteX() + getWidth()), (getAbsoluteY() + getHeight()), -1879048192);
						this.mc.fontRendererObj.drawString(entityLivingBase.getName(), (getAbsoluteX() + 31), (getAbsoluteY() + 8), -1);
						this.mc.fontRendererObj.drawString(String.valueOf(new StringBuilder().append(decimalFormat2.format(entityLivingBase.getHealth() / 2.0f)).append(" \u2764")), (getAbsoluteX() + 31), (getAbsoluteY() + 20), (new Color(255, 255, 255, 255)).getRGB());
						GuiInventory.drawEntityOnScreen(getAbsoluteX() + 12, getAbsoluteY() + 45, 25, -35.0F, -5.0F, entityLivingBase);
						drawWinColor();
						drawEntityHealth();
					}
					if (ZeroSense.killAura.targethudmode.is("Astolfo")) {
						Astolfo();
					}
					if (ZeroSense.killAura.targethudmode.is("Spicy")) {
						Spicy();
					}
					if (ZeroSense.killAura.targethudmode.is("Test1")) {
						test1();
					}
				}

				 */
			float var3 = (float) (System.currentTimeMillis() % 3500L) / 3500.0F;
			boolean var4 = false;
			int var5 = 1610612736;
			Collections.sort(ModuleManager.modules, new HUD.ModuleComparator());
			new Formatter();
			Calendar var7 = Calendar.getInstance();
			Formatter var6 = new Formatter();
			var6.format("%tl:%tM", var7, var7);
			if(Pos.is("Left")) {
				GL11.glColor3d(1.0D, 1.0D, 1.0D);
				String str2 = ZeroSense.VERSION;
				GL11.glScaled(2.0D, 2.0D, 1.0D);
				GL11.glTranslated(-5.0D, -5.0D, 1.0D);
				var2.drawStringWithShadow(String.valueOf("Mikron".charAt(0)), 10.0F, 200.0F, Methods.getRainbow(0, 3000, 0.6F, 1.0F).getRGB());
				GL11.glScaled(0.5D, 0.5D, 1.0D);
				GL11.glScaled(1.5D, 1.5D, 1.0D);
				GL11.glTranslated(5.0D, 5.0D, 1.0D);
				var2.drawStringWithShadow("Mikron".substring(1) + "Client", (10 + var2.getStringWidth(String.valueOf("Mikron".charAt(0)))), 263.0F, -1);
				GL11.glScaled(0.6666666666666666D, 0.6666666666666666D, 1.0D);
				for (byte b2 = 0; b2 < ZeroSense.VERSION.length(); b2++)
					var2.drawStringWithShadow(String.valueOf(ZeroSense.VERSION.charAt(b2)), (10 + var2.getStringWidth("MikronClient") + var2.getStringWidth(ZeroSense.VERSION.substring(0, b2))), 388.0F, Methods.getRainbow(100 * (b2 + 1), 3000, 0.6F, 1.0F).getRGB());
			} else if(Pos.is("Right")) {
				mc.fontRendererObj.drawStringWithShadow(String.valueOf("Mikron".charAt(0)), 10.0F, 10.0F, Methods.getRainbow(0, 3000, 0.6F, 1.0F).getRGB());
				mc.fontRendererObj.drawStringWithShadow("Mikron".substring(1) + "Client", (10 + var2.getStringWidth(String.valueOf("Mikron".charAt(0)))), 10.0F, -1);
			}
			var8 = 0;

			Iterator var12 = ModuleManager.modules.iterator();
			while (var12.hasNext()) {

				Module var11 = (Module) var12.next();
				if (var11.toggled && !var11.name.equals("TabGUI") && !var11.name.equals("HeadRotations")) {
					double translateX = com.zerosense.Translate.getX();
					double translateY = com.zerosense.Translate.getY();
					double var13 = (double) (var8 * (var2.FONT_HEIGHT + 1.45345125));
					int var15;
					if(Pos.is("Right")) {
						Gui.drawRect(sr.getScaledWidth() - var2.getStringWidth(var11.getDisplayName()) - 8, 1.0D + var13, sr.getScaledWidth() + 2, 2.5 + var2.FONT_HEIGHT + var13,
								new Color(0, 0, 0, 80).getRGB());
						if (arraycolor.is("Astolfo")) {
							JJ = -1;
						}
						if (arraycolor.is("PulseWhite")) {
							JJ = -1;
						}
						if (arraycolor.is("PulseRed")) {
							JJ = -1;							}
						if (arraycolor.is("PulseBlue")) {
							JJ = -1;							}
						if (arraycolor.is("PulseGreen")) {
							JJ = -1;
						}
						if (arraycolor.is("Random")) {
							JJ = Chill( (int) (1000 + var13), 0.6f, 0.3f);
						}
						var2.drawStringWithShadow(var11.getDisplayName(), sr.getScaledWidth() - var2.getStringWidth(var11.getDisplayName()) - 5, (float) (2.0D + var13), JJ);
						GlStateManager.color(1, 1, 1, 1);
						Gui.drawRect( sr.getScaledWidth() - 1.5, 1.0D + var13, sr.getScaledWidth() + 1, var2.FONT_HEIGHT + var13 + 2.5, JJ);

					}
					if(Pos.is("Left")){
						if (arraycolor.is("Astolfo")) {
							JJ = -1;
						}
						if (arraycolor.is("PulseWhite")) {
							JJ = -1;
						}
						if (arraycolor.is("PulseRed")) {
							JJ = -1;							}
						if (arraycolor.is("PulseBlue")) {
							JJ = -1;							}
						if (arraycolor.is("PulseGreen")) {
							JJ = -1;
						}
						if (arraycolor.is("Random")) {
							JJ = Chill( (int) (1000 + var13), 0.6f, 0.3f);
						}
						Gui.drawRect(var2.getStringWidth(var11.getDisplayName()) + 8, 1.0D + var13,2, 2.5 + var2.FONT_HEIGHT + var13,
								new Color(0, 0, 0, 80).getRGB());
						var2.drawStringWithShadow(var11.getDisplayName(), 4, (float) (0.5 + var13), JJ);
					}
					++var8;
				}
			}
		}
	}

	public static int Chill(int var2, float bright, float st) {
		double v1 = Math.ceil(System.currentTimeMillis() + (long) (var2 * 109)) / 5;
		return Color.getHSBColor((double) ((float) ((v1 %= 360.0) / 360.0)) < 0.5 ? -((float) (v1 / 360.0)) : (float) (v1 / 360.0), st, bright).getRGB();
	}

	public void draw(){
		ModuleManager.onEvent(new EventRenderGUI());
	}


	private RenderItem itemRenderer;
	/*
        private void test1() {

            ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            float x = (sr.getScaledWidth() >> 1) - 5;
            final DecimalFormat decimalFormat2 = new DecimalFormat("#.#");
            decimalFormat2.applyPattern("#.#");
            float y = (sr.getScaledHeight() >> 1) + 120;
            Gui.drawBorderedRect(x, y, 140, 45, 0.5, new Color(0, 0, 0, 255).getRGB(), new Color(0, 0, 0, 90).getRGB());
            mc.fontRendererObj.drawStringWithShadow("Name: " + ZeroSense.killAura.target.getName(), x + 46.5, y + 4, -1);
            mc.fontRendererObj.drawStringWithShadow("Distance: " + MathUtils.round(mc.thePlayer.getDistanceToEntity(ZeroSense.killAura.target), 2), x + 46.5, y + 12, -1);
            mc.fontRendererObj.drawStringWithShadow("Health: " + String.valueOf(new StringBuilder().append(decimalFormat2.format(ZeroSense.killAura.target.getHealth() / 2.0f)).append(" \u2764")), x + 46.5, y + 20, getHealthColor(ZeroSense.killAura.target));
            Gui.drawBorderedRect(x + 46, y + 45 - 10, 92, 8, 0.5, new Color(0).getRGB(), new Color(35, 35, 35).getRGB());
            double inc = 91 /  ZeroSense.killAura.target.getMaxHealth();
            double end = inc * (Math.min( ZeroSense.killAura.target.getHealth(),  ZeroSense.killAura.target.getMaxHealth()));
            RenderUtils.drawRect(x + 46.5, y + 45 - 9.5, end, 7, getHealthColor( ZeroSense.killAura.target));

        }

        private float fromPercentToScaledX(final float x) {
            final ScaledResolution newSR = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
            return newSR.getScaledWidth() / 100.0f * x;
        }

        private float fromPercentToScaledY(final float y) {
            final ScaledResolution newSR = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
            return newSR.getScaledHeight() / 100.0f * y;
        }

        private int getHealthColor(EntityLivingBase player) {
            float f = player.getHealth();
            float f1 = player.getMaxHealth();
            float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
            return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 0.75F) | 0xFF000000;
        }

        public static int getWidth() {
            return 154;
        }

        private static String getWin() {
            EntityLivingBase var1 = ZeroSense.killAura.target;
            Minecraft mc = Minecraft.getMinecraft();
            if (var1.getHealth() > var1.getHealth()) {
                return "You are Losing";
            } else if (var1.getHealth() == mc.thePlayer.getHealth() && var1.getHealth() != 20.0F && var1.getHealth() != 20.0F) {
                return "You may win";
            } else if (var1.getHealth() < mc.thePlayer.getHealth()) {
                return "You are Winning";
            } else if (var1.getHealth() == 0.0F) {
                return "You won!";
            } else if (var1.getHealth() == 0.0F) {
                return "You lost!";
            } else {
                return var1.getHealth() == 20.0F && var1.getHealth() == 20.0F ? "Not fighting" : "You May Win";
            }
        }
    */
	public static class ModuleComparator implements Comparator<Module> {

		public int compare(Module var1, Module var2) {
			if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(var1.getDisplayName()) > Minecraft.getMinecraft().fontRendererObj.getStringWidth(var2.getDisplayName())) {
				return -1;
			} else {
				return Minecraft.getMinecraft().fontRendererObj.getStringWidth(var1.getDisplayName()) < Minecraft.getMinecraft().fontRendererObj.getStringWidth(var2.getDisplayName()) ? 1 : 0;
			}
		}

		@Override
		public Comparator<Module> reversed() {
			return Comparator.super.reversed();
		}
	}
	/*
	private double width;
	private double animHealth;

	public void Astolfo(){
		final EntityLivingBase entityOtherPlayerMP2 = ZeroSense.killAura.target;
		this.width = 107.5;
		if (entityOtherPlayerMP2 != null) {
			GL11.glPushMatrix();
			GL11.glTranslated((double)(GuiScreen.width / 2), (double)(GuiScreen.height / 2), (double)(GuiScreen.width / 2));
			Gui.drawRect(-32.5f, 0.0f, 112.5f, 50.0f, new Color(0, 0, 0, 120).getRGB());
			GL11.glScalef(2.0f, 2.0f, 2.0f);
			final DecimalFormat decimalFormat2 = new DecimalFormat("#.#");
			decimalFormat2.applyPattern("#.#");
			this.mc.fontRendererObj.drawStringWithShadow(String.valueOf(new StringBuilder().append(decimalFormat2.format(entityOtherPlayerMP2.getHealth() / 2.0f)).append(" \u2764")), 0.0f, 8.35f, -1);
			GL11.glScalef(0.5f, 0.5f, 0.5f);
			this.mc.fontRendererObj.drawStringWithShadow(entityOtherPlayerMP2.getName(), 0.0f, 4.0f, -1);
			GuiInventory.drawEntityOnScreen(-15, 45, 20, ZeroSense.killAura.target.rotationYaw, ZeroSense.killAura.target.rotationPitch, ZeroSense.killAura.target);
			Gui.drawRect(0.0f, 37.5f, 107.5f, 45.5f, new Color(66, 66, 66, 255).getRGB());
			this.animHealth += (entityOtherPlayerMP2.getHealth() - this.animHealth) / 32.0 * 0.5;
			if (this.animHealth < 0.0 || this.animHealth > entityOtherPlayerMP2.getMaxHealth()) {
				this.animHealth = entityOtherPlayerMP2.getHealth();
			}
			else {
				Gui.drawRect(0.0f, 37.5f, (float)(int)(this.animHealth / entityOtherPlayerMP2.getMaxHealth() * this.width + 2.0), 45.5f, new Color(191, 190, 190, 255).getRGB());
				Gui.drawRect(0.0f, 37.5f, (float)(int)(mc.thePlayer.getHealth() / entityOtherPlayerMP2.getMaxHealth() * this.width), 45.5f, new Color(255, 255, 255, 255).getRGB());
			}
			GL11.glPopMatrix();
		}
	}

	ScaledResolution scaledResolution;
	private static final Color COLOR = new Color(0, 0, 0, 180);
	private EntityOtherPlayerMP target;
	private double healthBarWidth;
	private double hudHeight;

	public ScaledResolution getScaledResolution() {
		return this.scaledResolution;
	}

	public void Spicy(){
		ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		final EntityLivingBase target = ZeroSense.killAura.target;
		FontRenderer fr = mc.fontRendererObj;
		DecimalFormat dec = new DecimalFormat("#");
		healthBar = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight).getScaledWidth() / 2 - 41;

		healthBarTarget = sr.getScaledWidth() / 2 - 41 + (((140) / (target.getMaxHealth())) * (target.getHealth()));

		double HealthBarSpeed = 5;

		if (healthBar > healthBarTarget) {
			healthBar = ((healthBar) - ((healthBar - healthBarTarget) / HealthBarSpeed));
		}else if (healthBar < healthBarTarget) {
			//healthBar = healthBarTarget;
			healthBar = ((healthBar) + ((healthBarTarget - healthBar) / HealthBarSpeed));
		}
		int color = (target.getHealth() / target.getMaxHealth() > 0.66f) ? 0xff00ff00 : (target.getHealth() / target.getMaxHealth() > 0.33f) ? 0xffff9900 : 0xffff0000;

		float[] hsb = Color.RGBtoHSB(127, 127, 127, null);
		float hue = hsb[0];
		float saturation = hsb[1];

		Gui.drawRect(sr.getScaledWidth() / 2 - 110, sr.getScaledHeight() / 2 + 100, sr.getScaledWidth() / 2 + 110, sr.getScaledHeight() / 2 + 170, 0xff36393f);
		Gui.drawRect(sr.getScaledWidth() / 2 - 41, sr.getScaledHeight() / 2 + 100 + 54, sr.getScaledWidth() / 2 + 100, sr.getScaledHeight() / 2 + 96 + 45, 0xff202225);
		Gui.drawRect(sr.getScaledWidth() / 2 - 41, sr.getScaledHeight() / 2 + 100 + 54, healthBar, sr.getScaledHeight() / 2 + 96 + 45, color);
		Gui.drawRect(sr.getScaledWidth() / 2 - 41, sr.getScaledHeight() / 2 + 100 + 54, healthBarTarget, sr.getScaledHeight() / 2 + 96 + 45, color);

		GlStateManager.color(1, 1, 1);
		GuiInventory.drawEntityOnScreen(sr.getScaledWidth() / 2 - 75, sr.getScaledHeight() / 2 + 165, 25, 1f, 1f, target);
		fr.drawString(target.getName(), sr.getScaledWidth() / 2 - 40, sr.getScaledHeight() / 2 + 110, -1);
		fr.drawString("HP: ", sr.getScaledWidth() / 2 - 40, sr.getScaledHeight() / 2 + 125, -1);
		fr.drawString("" + dec.format(target.getHealth()), sr.getScaledWidth() / 2 - 40 + fr.getStringWidth("HP: "), sr.getScaledHeight() / 2 + 125, color);
	}

	//jello class

	public int smoothAnimation(double current, double last) {
		return (int) (current * mc.timer.renderPartialTicks + (last * (1.0f - mc.timer.renderPartialTicks)));
	}

	public float smoothTrans(double current, double last) {
		return (float) (current * mc.timer.renderPartialTicks + (last * (1.0f - mc.timer.renderPartialTicks)));
	}

 */

}
