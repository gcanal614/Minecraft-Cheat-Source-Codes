// Decompiled with: CFR 0.152
// Class Version: 8
package bozoware.impl.module.visual;

import bozoware.base.BozoWare;
import bozoware.base.event.EventConsumer;
import bozoware.base.event.EventListener;
import bozoware.base.module.Module;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.util.Wrapper;
import bozoware.base.util.player.MovementUtil;
import bozoware.base.util.visual.Animate.Animate;
import bozoware.base.util.visual.Animate.Easing;
import bozoware.base.util.visual.BloomUtil;
import bozoware.base.util.visual.BlurUtil;
import bozoware.base.util.visual.ColorUtil;
import bozoware.base.util.visual.RenderUtil;
import bozoware.impl.command.WatermarkCommand;
import bozoware.impl.event.visual.Render2DEvent;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.ColorProperty;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.property.StringProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.visual.font.MinecraftFontRenderer;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@ModuleData(moduleName="HUD", moduleCategory=ModuleCategory.VISUAL)
public class HUD
        extends Module {
    @EventListener
    EventConsumer<Render2DEvent> onRender2DEvent;
    private final EnumProperty<watermarkModes> watermarkMode = new EnumProperty<watermarkModes>("HUD Mode", watermarkModes.CSGO, this);
    private final EnumProperty<colorModes> colorMode = new EnumProperty<colorModes>("Color Mode", colorModes.CustomFade, this);
    private final EnumProperty<lineModes> lineMode = new EnumProperty<lineModes>("Line Mode", lineModes.Right, this);
    private final BooleanProperty cfontBool = new BooleanProperty("CFont", true, (Module)this);
    private final BooleanProperty lineBool = new BooleanProperty("Lines", true, (Module)this);
    private final BooleanProperty bgBool = new BooleanProperty("BackGround", true, (Module)this);
    public final BooleanProperty hideSuffixes = new BooleanProperty("Hide Suffixes", true, (Module)this);
    private final EnumProperty<bgModes> bgMode = new EnumProperty<bgModes>("BG Mode", bgModes.Shadow, this);
    private final ValueProperty<Integer> bgOpacity = new ValueProperty<Integer>("BackGround Opacity", 100, 0, 255, this);
    private final ColorProperty colorProperty = new ColorProperty("Color", new Color(-65536), (Module)this);
    private final ColorProperty colorProperty2 = new ColorProperty("Color 2", new Color(-65536), (Module)this);
    private final ValueProperty<Integer> offSetValue = new ValueProperty<Integer>("Offset", 6, 0, 12, this);
    private final ValueProperty<Integer> spacingValue = new ValueProperty<Integer>("Spacing", 3, 0, 3, this);
    private final StringProperty theWatermark = new StringProperty("Watermark", WatermarkCommand.watermark, (Module)this);
    public final EnumProperty<arrayListPos> arrayListPosition = new EnumProperty<arrayListPos>("Array List Position", arrayListPos.Top, this);
    static int bruh = (int)System.currentTimeMillis();
    Animate anim = new Animate();
    public static int bozoColor2;
    public int bozoColor;
    public int bozoColorDarker;
    static float posY;
    static float posX;
    public Color bozoColorColor = new Color(this.bozoColor, true);
    public Color bozoColor2Color = new Color(bozoColor2, true);
    public int bozoColorColorFinal = this.bozoColorColor.darker().darker().getRGB();
    public int bozoColorDarkerTest = new Color(Math.max((int)((double)this.bozoColorColor.getRed() * 0.7), 0), Math.max((int)((double)this.bozoColorColor.getGreen() * 0.7), 0), Math.max((int)((double)this.bozoColorColor.getBlue() * 0.7), 0), this.bozoColorColor.getAlpha()).getRGB();
    public int bozoColorX;
    public String serverIp;
    public Iterator<PotionEffect> iterator2;
    public PotionEffect effect;
    public String effectName;
    String getServerIp;

    public int getColor1() {
        return this.colorProperty.getPropertyValue().getRGB();
    }

    public int getColor2Gradient() {
        return this.colorProperty2.getPropertyValue().getRGB();
    }

    public int getColor2() {
        return this.colorProperty.getPropertyValue().darker().darker().darker().darker().darker().getRGB();
    }

    public int getColor2NotAsDark() {
        return this.colorProperty.getPropertyValue().darker().darker().getRGB();
    }

    public static int getRainbow(int speed, int offset) {
        float hue = (System.currentTimeMillis() + (long)offset) % (long)speed;
        return Color.getHSBColor(hue /= (float)speed, 0.85f, 1.0f).getRGB();
    }

    public static int rainbow(int idk, float bright, float st) {
        double d;
        double v1 = Math.ceil(System.currentTimeMillis() + (long)(idk * 109)) / 5.0;
        return Color.getHSBColor((double)((float)(d / 360.0)) < 0.5 ? -((float)(v1 / 360.0)) : (float)((v1 %= 360.0) / 360.0), st, bright).getRGB();
    }

    public HUD() {
        this.theWatermark.setHidden(true);
        this.colorProperty2.setHidden(true);
        this.colorMode.onValueChange = () -> {
            if (((colorModes)((Object)((Object)this.colorMode.getPropertyValue()))).equals((Object)colorModes.Gradient)) {
                this.colorProperty2.setHidden(false);
            } else {
                this.colorProperty2.setHidden(true);
            }
        };
        this.onRender2DEvent = render2DEvent -> {
            block44: {
                int i1;
                int fontSize;
                AtomicInteger moduleCounter;
                ScaledResolution sr;
                block42: {
                    block43: {
                        this.theWatermark.setPropertyValue(WatermarkCommand.watermark);
                        this.theWatermark.setPropertyValue(this.theWatermark.getPropertyValue().replaceAll("_", " "));
                        MinecraftFontRenderer SFR = BozoWare.getInstance().getFontManager().smallFontRenderer;
                        MinecraftFontRenderer MFR = BozoWare.getInstance().getFontManager().mediumFontRenderer;
                        MinecraftFontRenderer SLFR = BozoWare.getInstance().getFontManager().SUPALargeFontRenderer;
                        MinecraftFontRenderer MCFR = BozoWare.getInstance().getFontManager().McFontRenderer;
                        MinecraftFontRenderer SMCFR = BozoWare.getInstance().getFontManager().SmallMcFontRenderer;
                        MinecraftFontRenderer skeetIcons = BozoWare.getInstance().getFontManager().SkeetIcons;
                        MinecraftFontRenderer LbasicIcons = BozoWare.getInstance().getFontManager().LargeBasicIcons;
                        MinecraftFontRenderer arrowIcons = BozoWare.getInstance().getFontManager().ArrowIcons;
                        this.getServerIp = Wrapper.getCurrentServerIP();
                        sr = render2DEvent.getScaledResolution();
                        float yaw = System.currentTimeMillis() / 5L % 360L;
                        float bounce = Math.abs(System.currentTimeMillis() / 15L % 100L - 50L);
                        float rotationYaw = MathHelper.clamp_float(HUD.mc.thePlayer.rotationYaw, -45.0f, 45.0f);
                        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                        this.anim.setEase(Easing.EXPO_IN).setMin(0.0f).setMax(1.0f).setSpeed(500.0f).setReversed(false).update();
                        String watermark = String.format("%s §7|§r v%s §7|§r %s FPS §7|§r %s BPS", this.theWatermark.getPropertyValue(), BozoWare.getInstance().CLIENT_VERSION, Minecraft.getDebugFPS(), MovementUtil.getBPS());
                        String OTWatermark = String.format("%s | %s | %s FPS | %s BPS", this.theWatermark.getPropertyValue(), this.getServerIp, Minecraft.getDebugFPS(), MovementUtil.getBPS());
                        switch ((colorModes)((Object)((Object)this.colorMode.getPropertyValue()))) {
                            case CustomFade: {
                                bozoColor2 = ColorUtil.interpolateColorsDynamic(3, sr.getScaledWidth() * Integer.MAX_VALUE * 1500, new Color(this.getColor1()), new Color(this.getColor2NotAsDark())).getRGB();
                                break;
                            }
                            case Gradient: {
                                bozoColor2 = ColorUtil.interpolateColorsDynamic(3, sr.getScaledWidth() * Integer.MAX_VALUE * 1500, new Color(this.getColor1()), new Color(this.getColor2Gradient())).getRGB();
                                break;
                            }
                            case Rainbow: {
                                break;
                            }
                            case Astolfo: {
                                bozoColor2 = HUD.rainbow(-sr.getScaledWidth() * Integer.MAX_VALUE, 1.0f, 0.47f);
                                break;
                            }
                            default: {
                                bozoColor2 = -1;
                            }
                        }
                        switch (((watermarkModes)((Object)((Object)this.watermarkMode.getPropertyValue()))).toString()) {
                            case "Bozoware": {
                                char firstLetter = this.theWatermark.getPropertyValue().charAt(0);
                                String lettersBeyond = this.theWatermark.getPropertyValue().replaceFirst(String.valueOf(firstLetter), "");
                                MCFR.drawStringWithShadow(String.valueOf(firstLetter), 5.0, 6.0, bozoColor2);
                                HUD.mc.fontRendererObj.drawStringWithShadow(lettersBeyond, 13.0f, 8.0f, -1);
                                break;
                            }
                            case "CSGO": {
                                if (this.cfontBool.getPropertyValue().booleanValue()) {
                                    RenderUtil.drawSmoothRoundedRect(2.0f, 2.0f, (float)(2.0 + MFR.getStringWidth(watermark) + 5.0), 2 + MFR.getHeight() + 5, 8.0f, -1879048192);
                                    MFR.drawStringWithShadow(watermark, 4.0, 5.0, -1);
                                    break;
                                }
                                RenderUtil.drawSmoothRoundedRect(2.0f, 2.0f, 2 + HUD.mc.fontRendererObj.getStringWidth(watermark) + 5, 2 + HUD.mc.fontRendererObj.FONT_HEIGHT + 5, 8.0f, -1879048192);
                                HUD.mc.fontRendererObj.drawStringWithShadow(watermark, 4.0f, 5.0f, -1);
                                break;
                            }
                            case "idkwhat2call": {
                                String lettersBeyond;
                                char firstLetter;
                                if (!(HUD.mc.currentScreen instanceof GuiChat)) {
                                    // empty if block
                                }
                                if (this.cfontBool.getPropertyValue().booleanValue()) {
                                    String dateFormat = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                                    String[] directions = new String[]{"S", "SW", "W", "NW", "N", "NE", "E", "SE"};
                                    String direction = directions[HUD.wrapAngleToDirection(HUD.mc.thePlayer.rotationYaw, directions.length)];
                                    firstLetter = this.theWatermark.getPropertyValue().charAt(0);
                                    lettersBeyond = this.theWatermark.getPropertyValue().replaceFirst(String.valueOf(firstLetter), "");
                                    int x = (int)HUD.mc.thePlayer.posX;
                                    int y = (int)HUD.mc.thePlayer.posY;
                                    int z = (int)HUD.mc.thePlayer.posZ;
                                    MFR.drawStringWithShadow("Location: " + x + " " + y + " " + z, 4.0, 18.0, bozoColor2);
                                    MFR.drawStringWithShadow(String.valueOf(firstLetter) + lettersBeyond + " [" + BozoWare.getInstance().CLIENT_VERSION + "]" + " [" + this.getServerIp  + "]"  + " ["  + dateFormat  + "]"  + " ["  + direction  + "]", 4.0, 6.0, bozoColor2);
                                    break;
                                }
                                int x = (int)HUD.mc.thePlayer.posX;
                                int y = (int)HUD.mc.thePlayer.posY;
                                int z = (int)HUD.mc.thePlayer.posZ;
                                String[] directions = new String[]{"S", "SW", "W", "NW", "N", "NE", "E", "SE"};
                                String direction = directions[HUD.wrapAngleToDirection(HUD.mc.thePlayer.rotationYaw, directions.length)];
                                String dateFormat = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                                firstLetter = this.theWatermark.getPropertyValue().charAt(0);
                                lettersBeyond = this.theWatermark.getPropertyValue().replaceFirst(String.valueOf(firstLetter), "");
                                HUD.mc.fontRendererObj.drawStringWithShadow( "Location: "  + x + " " + y + " " + z, 4.0f, 18.0f, bozoColor2);
                                HUD.mc.fontRendererObj.drawStringWithShadow(String.valueOf(firstLetter)  + lettersBeyond  + " ["  + BozoWare.getInstance().CLIENT_VERSION  + "]"  + " ["  + this.getServerIp  + "]"  + " ["  + dateFormat  + "]"  + " ["  + direction  + "]", 4.0f, 6.0f, bozoColor2);
                                break;
                            }
                            case "Basic": {
                                char firstLetter = this.theWatermark.getPropertyValue().charAt(0);
                                String lettersBeyond = this.theWatermark.getPropertyValue().replaceFirst(String.valueOf(firstLetter), "");
                                if (this.cfontBool.getPropertyValue().booleanValue()) {
                                    MFR.drawStringWithShadow(String.valueOf(firstLetter)  + lettersBeyond, 4.0, 5.0, bozoColor2);
                                    break;
                                }
                                HUD.mc.fontRendererObj.drawStringWithShadow(String.valueOf(firstLetter)  + lettersBeyond, 4.0f, 5.0f, bozoColor2);
                                break;
                            }
                            case "Onetap": {
                                BloomUtil.drawAndBloom(() -> BozoWare.blurrer.blur(2.0, 2.0, BozoWare.getInstance().getFontManager().smallCSGORenderer.getStringWidth(OTWatermark) + 4.0, MFR.getHeight() + 8, 2));
                                RenderUtil.glHorizontalGradientQuad(2.0, 2.0, (float)(BozoWare.getInstance().getFontManager().smallCSGORenderer.getStringWidth(OTWatermark) + 4.0), 1.0, bozoColor2, this.bozoColor);
                                BozoWare.getInstance().getFontManager().smallCSGORenderer.drawStringWithShadow(OTWatermark, 4.0, 6.5, -1);
                            }
                        }
                        moduleCounter = new AtomicInteger();
                        if (!this.cfontBool.getPropertyValue().booleanValue()) break block42;
                        BozoWare.getInstance().getModuleManager().getModulesToDraw(false).forEach(module -> {
                            if (((colorModes)((Object)((Object)((Object)this.colorMode.getPropertyValue())))).equals((Object)colorModes.Category)) {
                                switch (module.getModuleCategory()) {
                                    case VISUAL: {
                                        this.bozoColor = -103;
                                        bozoColor2 = -256;
                                        break;
                                    }
                                    case PLAYER: {
                                        this.bozoColor = -44976;
                                        bozoColor2 = -44976;
                                        break;
                                    }
                                    case WORLD: {
                                        this.bozoColor = -16740304;
                                        bozoColor2 = -16740304;
                                        break;
                                    }
                                    case COMBAT: {
                                        this.bozoColor = -65504;
                                        bozoColor2 = -65504;
                                        break;
                                    }
                                    case MOVEMENT: {
                                        this.bozoColor = -14647264;
                                        bozoColor2 = -14647264;
                                        break;
                                    }
                                    default: {
                                        this.bozoColor = -16756608;
                                        bozoColor2 = -16756608;
                                    }
                                }
                            }
                            if (this.cfontBool.getPropertyValue().booleanValue()) {
                                // empty if block
                            }
                            posX = this.lineBool.getPropertyValue() != false ? (float)((double)sr.getScaledWidth() - MFR.getStringWidth(module.getModuleDisplayName()) - 5.0 - (double)((Integer)this.offSetValue.getPropertyValue()).intValue()) : (float)((double)sr.getScaledWidth() - MFR.getStringWidth(module.getModuleDisplayName()) - 3.0 - (double)((Integer)this.offSetValue.getPropertyValue()).intValue());
                            posY = ((arrayListPos)((Object)((Object)((Object)this.arrayListPosition.getPropertyValue())))).equals((Object)arrayListPos.Top) ? (float)(3 + (Integer)this.offSetValue.getPropertyValue() + moduleCounter.get() * (13 - (Integer)this.spacingValue.getPropertyValue())) : (float)(sr.getScaledHeight() - (9 + (Integer)this.offSetValue.getPropertyValue() + moduleCounter.get() * (13 - (Integer)this.spacingValue.getPropertyValue())));
                            switch ((colorModes)((Object)((Object)((Object)this.colorMode.getPropertyValue())))) {
                                case CustomFade: {
                                    this.bozoColor = ColorUtil.interpolateColorsDynamic(3, moduleCounter.get() * 15, new Color(this.getColor1()), new Color(this.getColor2NotAsDark())).getRGB();
                                    this.bozoColorX = ColorUtil.interpolateColorsDynamic(3, sr.getScaledWidth() * Integer.MAX_VALUE, new Color(this.getColor1()), new Color(this.getColor2NotAsDark())).getRGB();
                                    this.bozoColorDarker = ColorUtil.interpolateColorsDynamic(3, moduleCounter.get() * 15, new Color(this.getColor1()).darker().darker(), new Color(this.getColor2NotAsDark())).darker().darker().getRGB();
                                    break;
                                }
                                case Gradient: {
                                    this.bozoColor = ColorUtil.interpolateColorsDynamic(3, moduleCounter.get() * 15, new Color(this.getColor1()), new Color(this.getColor2Gradient())).getRGB();
                                    this.bozoColorDarker = ColorUtil.interpolateColorsDynamic(3, moduleCounter.get() * 15, new Color(this.getColor1()).darker().darker(), new Color(this.getColor2Gradient())).darker().darker().getRGB();
                                    bozoColor2 = ColorUtil.interpolateColorsDynamic(3, moduleCounter.get() * 15, new Color(this.getColor1()), new Color(this.getColor2Gradient())).getRGB();
                                    this.bozoColorX = ColorUtil.interpolateColorsDynamic(3, sr.getScaledWidth() * Integer.MAX_VALUE, new Color(this.getColor1()), new Color(this.getColor2Gradient())).getRGB();
                                    break;
                                }
                                case Rainbow: {
                                    this.bozoColor = HUD.getRainbow(3000, (int)(posY * 5.0f));
                                    this.bozoColorDarker = new Color(HUD.getRainbow(3000, (int)(posY * 5.0f))).darker().darker().getRGB();
                                    bozoColor2 = HUD.getRainbow(3000, (int)(posY * 5.0f));
                                    this.bozoColorX = HUD.getRainbow(3000, (int)(posY * 5.0f));
                                    break;
                                }
                                case Astolfo: {
                                    this.bozoColor = HUD.rainbow((int)(-posY) * 5, 1.0f, 0.47f);
                                    this.bozoColorX = HUD.rainbow(sr.getScaledWidth() * Integer.MAX_VALUE, 1.0f, 0.47f);
                                    this.bozoColorDarker = new Color(HUD.rainbow((int)(-posY) * 5, 1.0f, 0.47f)).darker().darker().getRGB();
                                }
                            }
                            if (this.bgBool.getPropertyValue().booleanValue() && this.bgBool.getPropertyValue().equals(((bgModes)((Object)((Object)((Object)this.bgMode.getPropertyValue())))).equals((Object)bgModes.Shadow))) {
                                RenderUtil.drawRoundedRect(posX - 2.0f, posY - 3.0f, sr.getScaledWidth() - (Integer)this.offSetValue.getPropertyValue(), posY + 10.0f - (float)((Integer)this.spacingValue.getPropertyValue()).intValue(), 1.0f, new Color(0, 0, 0, (Integer)this.bgOpacity.getPropertyValue()).getRGB());
                            }
                            if (this.bgBool.getPropertyValue().booleanValue() && ((bgModes)((Object)((Object)((Object)this.bgMode.getPropertyValue())))).equals((Object)bgModes.Blur)) {
                                BlurUtil.blurArea(posX - 3.0f, posY - 4.0f, MFR.getStringWidth(module.getModuleDisplayName()) + 8.0, 15 - (Integer)this.spacingValue.getPropertyValue());
                            }
                            MFR.drawStringWithShadow(module.getModuleDisplayName(), posX + 1.0f, posY - (float)((Integer)this.spacingValue.getPropertyValue() / 2), this.bozoColor);
                            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                            if (this.lineBool.getPropertyValue().booleanValue()) {
                                switch ((lineModes)((Object)((Object)((Object)this.lineMode.getPropertyValue())))) {
                                    case Right: {
                                        Gui.drawRect(sr.getScaledWidth() + 2 - (Integer)this.offSetValue.getPropertyValue() - 1, posY - 3.0f, sr.getScaledWidth() - (Integer)this.offSetValue.getPropertyValue(), posY + 10.0f - (float)((Integer)this.spacingValue.getPropertyValue()).intValue(), this.bozoColor);
                                        break;
                                    }
                                    case Left: {
                                        Gui.drawRect(posX - 3.0f, posY - 4.0f, posX - 1.0f, posY + 10.0f, this.bozoColor);
                                        break;
                                    }
                                    case Outlined: {
                                        int toggledIndex = BozoWare.getInstance().getModuleManager().getModulesToDraw(false).indexOf(module);
                                        int m1Offset = -1;
                                        if (toggledIndex != BozoWare.getInstance().getModuleManager().getModulesToDraw(false).size() - 1) {
                                            m1Offset = (int)((double)m1Offset + MFR.getStringWidth(BozoWare.getInstance().getModuleManager().getModulesToDraw(false).get(toggledIndex + 1).getModuleDisplayName()));
                                            m1Offset += 7;
                                        }
                                        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                                        Gui.drawRect((double)sr.getScaledWidth() - MFR.getStringWidth(module.getModuleDisplayName()) - 7.0 - (double)((Integer)this.offSetValue.getPropertyValue()).intValue(), posY + 1.0f + (float)MFR.getHeight(), sr.getScaledWidth() - m1Offset - (Integer)this.offSetValue.getPropertyValue(), posY + 2.0f + (float)MFR.getHeight(), this.bozoColor);
                                        Gui.drawRectWithWidth((int)posX - 2, posY - 4.0f, 1.0, 14.0, this.bozoColor);
                                        Gui.drawRectWithWidth(sr.getScaledWidth() - 1 - (Integer)this.offSetValue.getPropertyValue(), posY - 4.0f, 1.0, 14.0, this.bozoColor);
                                    }
                                }
                            }
                            moduleCounter.getAndIncrement();
                        });
                        Collection collection = HUD.mc.thePlayer.getActivePotionEffects();
                        if (collection.isEmpty()) {
                            return;
                        }
                        collection = (Collection) collection.stream().sorted(Comparator.comparingInt(e -> (int) e)).collect(Collectors.toList());
                        fontSize = 16;
                        int textureSize = 12;
                        int y = sr.getScaledHeight() - 15;
                        for (PotionEffect potioneffect : collection) {
                            Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                            String potionName = I18n.format(potion.getName(), new Object[0]);
                            if (potioneffect.getAmplifier() == 1) {
                                potionName = potionName + " " + I18n.format("enchantment.level.2", new Object[0]);
                            } else if (potioneffect.getAmplifier() == 2) {
                                potionName = potionName + " " + I18n.format("enchantment.level.3", new Object[0]);
                            } else if (potioneffect.getAmplifier() == 3) {
                                potionName = potionName + " " + I18n.format("enchantment.level.4", new Object[0]);
                            }
                            potionName = potionName + " (" + potioneffect.getDuration() / 20 * 1000 / 60000 % 60 + "m " + potioneffect.getDuration() / 20 * 1000 / 1000 % 60 + "s)";
                            GL11.glPushMatrix();
                            GL11.glTranslated((double)(sr.getScaledWidth() - 20), (double)y, (double)0.0);
                            GL11.glTranslated((double)(-(sr.getScaledWidth() - 20)), (double)(-y), (double)0.0);
                            MFR.drawStringWithShadow(potionName, ((arrayListPos)((Object)((Object)this.arrayListPosition.getPropertyValue()))).equals((Object)arrayListPos.Top) ? (double)(sr.getScaledWidth() - 5 - HUD.mc.fontRendererObj.getStringWidth(potionName)) : 17.0, y + 3, -1);
                            GL11.glPopMatrix();
                            if (potion.hasStatusIcon()) {
                                mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                                i1 = potion.getStatusIconIndex();
                                GL11.glPushMatrix();
                                GL11.glTranslated((double)(((arrayListPos)((Object)((Object)this.arrayListPosition.getPropertyValue()))).equals((Object)arrayListPos.Top) ? (double)(sr.getScaledWidth() - 17) : 4.0), (double)y, (double)0.0);
                                GL11.glScaled((double)0.666666667, (double)0.666666667, (double)1.0);
                                GL11.glTranslated((double)(-(((arrayListPos)((Object)((Object)this.arrayListPosition.getPropertyValue()))).equals((Object)arrayListPos.Top) ? sr.getScaledWidth() - 17 : 4)), (double)(-y), (double)0.0);
                                HUD.mc.ingameGUI.drawTexturedModalRect(((arrayListPos)((Object)((Object)this.arrayListPosition.getPropertyValue()))).equals((Object)arrayListPos.Top) ? sr.getScaledWidth() - 17 : 0, y, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                                GL11.glPopMatrix();
                            }
                            y -= 12;
                        }
                        if (!this.cfontBool.getPropertyValue().booleanValue()) break block43;
                        if (((arrayListPos)((Object)((Object)this.arrayListPosition.getPropertyValue()))).equals((Object)arrayListPos.Top)) {
                            MFR.drawStringWithShadow("BPS: " + MovementUtil.getBPS(), 2.0, sr.getScaledHeight() - 20, bozoColor2);
                            MFR.drawStringWithShadow("FPS: " + Minecraft.getDebugFPS(), 2.0, sr.getScaledHeight() - 10, bozoColor2);
                        } else {
                            MFR.drawStringWithShadow("BPS: " + MovementUtil.getBPS(), 2.0, sr.getScaledHeight() - 30, bozoColor2);
                            MFR.drawStringWithShadow("FPS: " + Minecraft.getDebugFPS(), 2.0, sr.getScaledHeight() - 20, bozoColor2);
                        }
                        break block44;
                    }
                    if (!((arrayListPos)((Object)((Object)this.arrayListPosition.getPropertyValue()))).equals((Object)arrayListPos.Top)) break block44;
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime localTime = LocalTime.now();
                    HUD.mc.fontRendererObj.drawStringWithShadow("BPS: " + MovementUtil.getBPS(), 2.0f, sr.getScaledHeight() - 20, bozoColor2);
                    HUD.mc.fontRendererObj.drawStringWithShadow("FPS: " + Minecraft.getDebugFPS(), 2.0f, sr.getScaledHeight() - 10, bozoColor2);
                    break block44;
                }
                BozoWare.getInstance().getModuleManager().getModulesToDraw(true).forEach(module -> {
                    if (((colorModes)((Object)((Object)((Object)this.colorMode.getPropertyValue())))).equals((Object)colorModes.Category)) {
                        switch (module.getModuleCategory()) {
                            case VISUAL: {
                                this.bozoColor = -103;
                                bozoColor2 = -256;
                                break;
                            }
                            case PLAYER: {
                                this.bozoColor = -44976;
                                bozoColor2 = -44976;
                                break;
                            }
                            case WORLD: {
                                this.bozoColor = -16740304;
                                bozoColor2 = -16740304;
                                break;
                            }
                            case COMBAT: {
                                this.bozoColor = -65504;
                                bozoColor2 = -65504;
                                break;
                            }
                            case MOVEMENT: {
                                this.bozoColor = -14647264;
                                bozoColor2 = -14647264;
                                break;
                            }
                            default: {
                                this.bozoColor = -16756608;
                                bozoColor2 = -16756608;
                            }
                        }
                    }
                    posX = this.lineBool.getPropertyValue() != false ? (float)(sr.getScaledWidth() - HUD.mc.fontRendererObj.getStringWidth(module.getModuleDisplayName()) - 5 - (Integer)this.offSetValue.getPropertyValue()) : (float)(sr.getScaledWidth() - HUD.mc.fontRendererObj.getStringWidth(module.getModuleDisplayName()) - 3 - (Integer)this.offSetValue.getPropertyValue());
                    posY = ((arrayListPos)((Object)((Object)((Object)this.arrayListPosition.getPropertyValue())))).equals((Object)arrayListPos.Top) ? (float)(3 + (Integer)this.offSetValue.getPropertyValue() + moduleCounter.get() * (13 - (Integer)this.spacingValue.getPropertyValue())) : (float)(sr.getScaledHeight() - (9 + (Integer)this.offSetValue.getPropertyValue() + moduleCounter.get() * (13 - (Integer)this.spacingValue.getPropertyValue())));
                    switch ((colorModes)((Object)((Object)((Object)this.colorMode.getPropertyValue())))) {
                        case CustomFade: {
                            this.bozoColor = ColorUtil.interpolateColorsDynamic(3, moduleCounter.get() * 15, new Color(this.getColor1()), new Color(this.getColor2NotAsDark())).getRGB();
                            this.bozoColorDarker = ColorUtil.interpolateColorsDynamic(3, moduleCounter.get() * 15, new Color(this.getColor1()), new Color(this.getColor2NotAsDark())).darker().darker().getRGB();
                            this.bozoColorX = ColorUtil.interpolateColorsDynamic(3, sr.getScaledWidth() * Integer.MAX_VALUE, new Color(this.getColor1()), new Color(this.getColor2NotAsDark())).getRGB();
                            break;
                        }
                        case Gradient: {
                            this.bozoColor = ColorUtil.interpolateColorsDynamic(3, moduleCounter.get() * 15, new Color(this.getColor1()), new Color(this.getColor2Gradient())).getRGB();
                            bozoColor2 = ColorUtil.interpolateColorsDynamic(3, moduleCounter.get() * 15, new Color(this.getColor1()), new Color(this.getColor2Gradient())).getRGB();
                            this.bozoColorDarker = ColorUtil.interpolateColorsDynamic(3, moduleCounter.get() * 15, new Color(this.getColor1()), new Color(this.getColor2Gradient())).darker().darker().getRGB();
                            this.bozoColorX = ColorUtil.interpolateColorsDynamic(3, sr.getScaledWidth() * Integer.MAX_VALUE, new Color(this.getColor1()), new Color(this.getColor2Gradient())).getRGB();
                            break;
                        }
                        case Rainbow: {
                            this.bozoColor = HUD.getRainbow(3000, (int)(posY * 5.0f));
                            this.bozoColorDarker = new Color(HUD.getRainbow(3000, (int)(posY * 5.0f))).darker().darker().getRGB();
                            bozoColor2 = HUD.getRainbow(3000, (int)(posY * 5.0f));
                            this.bozoColorX = HUD.getRainbow(3000, (int)(posY * 5.0f));
                            break;
                        }
                        case Astolfo: {
                            this.bozoColor = HUD.rainbow((int)(-posY) * 5, 1.0f, 0.47f);
                            this.bozoColorDarker = new Color(HUD.rainbow((int)(-posY) * 5, 1.0f, 0.47f)).darker().darker().getRGB();
                            this.bozoColorX = HUD.rainbow(sr.getScaledWidth() * Integer.MAX_VALUE, 1.0f, 0.47f);
                            break;
                        }
                    }
                    if (this.lineBool.getPropertyValue().booleanValue() && ((lineModes)((Object)((Object)((Object)this.lineMode.getPropertyValue())))).equals((Object)lineModes.Left)) {
                        if (this.bgBool.getPropertyValue().booleanValue() && ((bgModes)((Object)((Object)((Object)this.bgMode.getPropertyValue())))).equals((Object)bgModes.Shadow)) {
                            RenderUtil.drawRoundedRect(posX - 1.0f, posY - 3.0f, sr.getScaledWidth() - (Integer)this.offSetValue.getPropertyValue() + 2, posY + 10.0f - (float)((Integer)this.spacingValue.getPropertyValue()).intValue(), 1.0f, new Color(0, 0, 0, (Integer)this.bgOpacity.getPropertyValue()).getRGB());
                        } else if (this.bgBool.getPropertyValue().booleanValue() && ((bgModes)((Object)((Object)((Object)this.bgMode.getPropertyValue())))).equals((Object)bgModes.Blur)) {
                            BlurUtil.blurArea(posX - 1.0f, posY - 3.0f, sr.getScaledWidth() - (Integer)this.offSetValue.getPropertyValue() + 1, posY + 10.0f - (float)((Integer)this.spacingValue.getPropertyValue()).intValue());
                        }
                    } else if (this.bgBool.getPropertyValue().booleanValue() && ((bgModes)((Object)((Object)((Object)this.bgMode.getPropertyValue())))).equals((Object)bgModes.Shadow)) {
                        RenderUtil.drawRoundedRect(posX, posY - 3.0f, sr.getScaledWidth() - (Integer)this.offSetValue.getPropertyValue() + 2, posY + 10.0f - (float)((Integer)this.spacingValue.getPropertyValue()).intValue(), 1.0f, new Color(0, 0, 0, (Integer)this.bgOpacity.getPropertyValue()).getRGB());
                    }
                    if (this.lineBool.getPropertyValue().booleanValue() && ((lineModes)((Object)((Object)((Object)this.lineMode.getPropertyValue())))).equals((Object)lineModes.Left)) {
                        HUD.mc.fontRendererObj.drawStringWithShadow(module.getModuleDisplayName(), posX + 4.0f, posY - 0.5f - (float)((Integer)this.spacingValue.getPropertyValue() / 2), this.bozoColor);
                        if (this.bgBool.getPropertyValue().booleanValue() && ((bgModes)((Object)((Object)((Object)this.bgMode.getPropertyValue())))).equals((Object)bgModes.Blur)) {
                            BlurUtil.blurArea(posX, posY - 3.0f, HUD.mc.fontRendererObj.getStringWidth(module.getModuleDisplayName()) + 7, 13 - (Integer)this.spacingValue.getPropertyValue());
                        }
                    } else {
                        if (this.bgBool.getPropertyValue().booleanValue() && ((bgModes)((Object)((Object)((Object)this.bgMode.getPropertyValue())))).equals((Object)bgModes.Blur)) {
                            BlurUtil.blurArea(posX - 1.0f, posY - 3.0f, HUD.mc.fontRendererObj.getStringWidth(module.getModuleDisplayName()) + 7, 15 - (Integer)this.spacingValue.getPropertyValue());
                        }
                        HUD.mc.fontRendererObj.drawStringWithShadow(module.getModuleDisplayName(), posX + 2.5f, posY - 0.5f - (float)((Integer)this.spacingValue.getPropertyValue() / 2), this.bozoColor);
                    }
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    if (this.lineBool.getPropertyValue().booleanValue()) {
                        switch ((lineModes)((Object)((Object)((Object)this.lineMode.getPropertyValue())))) {
                            case Right: {
                                Gui.drawRect(sr.getScaledWidth() - (Integer)this.offSetValue.getPropertyValue() + 1, posY - 4.0f, sr.getScaledWidth() - (Integer)this.offSetValue.getPropertyValue(), posY + 10.0f - (float)((Integer)this.spacingValue.getPropertyValue()).intValue(), this.bozoColor);
                                break;
                            }
                            case Left: {
                                Gui.drawRect(posX - 1.0f, posY - 3.0f, posX + 1.0f, posY + 10.0f - (float)((Integer)this.spacingValue.getPropertyValue()).intValue(), this.bozoColor);
                                break;
                            }
                            case Outlined: {
                                int toggledIndex = BozoWare.getInstance().getModuleManager().getModulesToDraw(true).indexOf(module);
                                int m1Offset = -1;
                                if (toggledIndex != BozoWare.getInstance().getModuleManager().getModulesToDraw(true).size() - 1) {
                                    m1Offset += HUD.mc.fontRendererObj.getStringWidth(BozoWare.getInstance().getModuleManager().getModulesToDraw(true).get(toggledIndex + 1).getModuleDisplayName());
                                    m1Offset += 6;
                                }
                                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                                Gui.drawRect(sr.getScaledWidth() - HUD.mc.fontRendererObj.getStringWidth(module.getModuleDisplayName()) - 6 - (Integer)this.offSetValue.getPropertyValue(), posY + 1.0f + (float)HUD.mc.fontRendererObj.FONT_HEIGHT, sr.getScaledWidth() - m1Offset - (Integer)this.offSetValue.getPropertyValue(), posY + 2.0f + (float)HUD.mc.fontRendererObj.FONT_HEIGHT, this.bozoColor);
                                Gui.drawRectWithWidth((int)posX - 1, (double)posY - 1.8, 1.0, 12.0, this.bozoColor);
                                Gui.drawRectWithWidth(sr.getScaledWidth() + 1 - (Integer)this.offSetValue.getPropertyValue() - 2, posY - 4.0f, 2.0, 14.0, this.bozoColor);
                            }
                        }
                    }
                    moduleCounter.getAndIncrement();
                });
                Collection collection = HUD.mc.thePlayer.getActivePotionEffects();
                if (collection.isEmpty()) {
                    return;
                }
                collection = collection.stream().sorted(Comparator.comparingInt(e -> e.getEffectName().length())).collect(Collectors.toList());
                fontSize = 16;
                int textureSize = 12;
                int y = sr.getScaledHeight() - 15;
                for (PotionEffect potioneffect : collection) {
                    Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    String potionName = I18n.format(potion.getName(), new Object[0]);
                    if (potioneffect.getAmplifier() == 1) {
                        potionName = potionName + " " + I18n.format("enchantment.level.2", new Object[0]);
                    } else if (potioneffect.getAmplifier() == 2) {
                        potionName = potionName + " " + I18n.format("enchantment.level.3", new Object[0]);
                    } else if (potioneffect.getAmplifier() == 3) {
                        potionName = potionName + " " + I18n.format("enchantment.level.4", new Object[0]);
                    }
                    potionName = potionName + " (" + potioneffect.getDuration() / 20 * 1000 / 60000 % 60 + "m " + potioneffect.getDuration() / 20 * 1000 / 1000 % 60 + "s)";
                    GL11.glPushMatrix();
                    GL11.glTranslated((double)(sr.getScaledWidth() - 20), (double)y, (double)0.0);
                    GL11.glTranslated((double)(-(sr.getScaledWidth() - 20)), (double)(-y), (double)0.0);
                    HUD.mc.fontRendererObj.drawStringWithShadow(potionName, ((arrayListPos)((Object)((Object)this.arrayListPosition.getPropertyValue()))).equals((Object)arrayListPos.Top) ? (float)(sr.getScaledWidth() - 20 - HUD.mc.fontRendererObj.getStringWidth(potionName)) : 17.0f, y + 3, -1);
                    GL11.glPopMatrix();
                    if (potion.hasStatusIcon()) {
                        mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                        i1 = potion.getStatusIconIndex();
                        GL11.glPushMatrix();
                        GL11.glTranslated((double)(((arrayListPos)((Object)((Object)this.arrayListPosition.getPropertyValue()))).equals((Object)arrayListPos.Top) ? (double)(sr.getScaledWidth() - 17) : 4.0), (double)y, (double)0.0);
                        GL11.glScaled((double)0.666666667, (double)0.666666667, (double)1.0);
                        GL11.glTranslated((double)(-(((arrayListPos)((Object)((Object)this.arrayListPosition.getPropertyValue()))).equals((Object)arrayListPos.Top) ? sr.getScaledWidth() - 17 : 4)), (double)(-y), (double)0.0);
                        HUD.mc.ingameGUI.drawTexturedModalRect(((arrayListPos)((Object)((Object)this.arrayListPosition.getPropertyValue()))).equals((Object)arrayListPos.Top) ? sr.getScaledWidth() - 17 : 0, y, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                        GL11.glPopMatrix();
                    }
                    y -= 12;
                }
            }
        };
        this.setModuleToggled(true);
    }

    public static HUD getInstance() {
        return (HUD)BozoWare.getInstance().getModuleManager().getModuleByClass.apply(HUD.class);
    }

    public static int wrapAngleToDirection(float yaw, int zones) {
        int angle = (int)((double)(yaw + (float)(360 / (2 * zones))) + 0.5) % 360;
        if (angle < 0) {
            angle += 360;
        }
        return angle / (360 / zones);
    }

    public void drawPotionEffects(int color) {
        Collection collection = HUD.mc.thePlayer.getActivePotionEffects();
        if (collection.isEmpty()) {
            return;
        }
        collection = collection.stream().sorted(Comparator.comparingInt(e -> ((PotionEffect)e).getEffectName().length()).reversed()).collect(Collectors.toList());
        int fontSize = 16;
        int textureSize = 12;
        ScaledResolution sr = new ScaledResolution(mc, HUD.mc.displayWidth, HUD.mc.displayHeight);
        int y = ((arrayListPos)((Object)this.arrayListPosition.getPropertyValue())).equals((Object)arrayListPos.Top) ? sr.getScaledHeight() - 15 : 4;
        for (PotionEffect potioneffect : collection) {
            Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            String potionName = I18n.format(potion.getName(), new Object[0]);
            if (potioneffect.getAmplifier() == 1) {
                potionName = potionName + " " + I18n.format("enchantment.level.2", new Object[0]);
            } else if (potioneffect.getAmplifier() == 2) {
                potionName = potionName + " " + I18n.format("enchantment.level.3", new Object[0]);
            } else if (potioneffect.getAmplifier() == 3) {
                potionName = potionName + " " + I18n.format("enchantment.level.4", new Object[0]);
            }
            potionName = potionName + " (" + potioneffect.getDuration() / 20 * 1000 / 60000 % 60 + "m " + potioneffect.getDuration() / 20 * 1000 / 1000 % 60 + "s)";
            GL11.glPushMatrix();
            GL11.glTranslated((double)(sr.getScaledWidth() - 20), (double)y, (double)0.0);
            GL11.glTranslated((double)(-(sr.getScaledWidth() - 20)), (double)(-y), (double)0.0);
            HUD.mc.fontRendererObj.drawStringWithShadow(potionName, sr.getScaledWidth() - 20 - HUD.mc.fontRendererObj.getStringWidth(potionName), y + 3, -1);
            GL11.glPopMatrix();
            if (potion.hasStatusIcon()) {
                mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                int i1 = potion.getStatusIconIndex();
                GL11.glPushMatrix();
                GL11.glTranslated((double)(sr.getScaledWidth() - 17), (double)y, (double)0.0);
                GL11.glScaled((double)0.666666667, (double)0.666666667, (double)1.0);
                GL11.glTranslated((double)(-(sr.getScaledWidth() - 17)), (double)(-y), (double)0.0);
                HUD.mc.ingameGUI.drawTexturedModalRect(sr.getScaledWidth() - 17, y, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                GL11.glPopMatrix();
            }
            y -= 12;
        }
    }

    public static enum arrayListPos {
        Top,
        Bottom;

    }

    public static enum bgModes {
        Shadow,
        Blur;

    }

    public static enum lineModes {
        Right,
        Left,
        Outlined;

    }

    public static enum watermarkModes {
        Bozoware,
        CSGO,
        Basic,
        idkwhat2call,
        Onetap;

    }

    public static enum colorModes {
        CustomFade,
        Gradient,
        Rainbow,
        Astolfo,
        Category;

    }
}
