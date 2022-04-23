/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.render.hud;

import club.tifality.Tifality;
import club.tifality.gui.font.FontRenderer;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.api.annotations.Priority;
import club.tifality.manager.event.impl.packet.PacketReceiveEvent;
import club.tifality.manager.event.impl.render.Render2DEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.impl.movement.Scaffold;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.property.impl.MultiSelectEnumProperty;
import club.tifality.utils.Wrapper;
import club.tifality.utils.movement.MovementUtils;
import club.tifality.utils.render.Colors;
import club.tifality.utils.render.LockedResolution;
import club.tifality.utils.render.RenderingUtils;
import club.tifality.utils.render.Translate;
import club.tifality.utils.timer.TimerUtil;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.MinecraftFontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

@ModuleInfo(label="HUD", category=ModuleCategory.RENDER)
public final class Hud
extends Module {
    private final EnumProperty<FontRendererMode> fontRender = new EnumProperty<FontRendererMode>("Font Renderer", FontRendererMode.SMOOTHTTF);
    private final MultiSelectEnumProperty<HudOptions> hudOption = new MultiSelectEnumProperty("HUD Options", (Enum[])new HudOptions[]{HudOptions.ARRAYLIST, HudOptions.PROTOCOL, HudOptions.SUFFIX, HudOptions.FPS});
    public final EnumProperty<ArrayListColorMode> arrayListColorModeProperty = new EnumProperty<ArrayListColorMode>("Color Mode", ArrayListColorMode.WHITE);
    public final Property<String> watermarkText = new Property<String>("Client Name", this.hudOption.isSelected(HudOptions.NOSTALGIA) ? "" : "Tifality".charAt(0) + "\u00a7R\u00a7F" + "Tifality".substring(1));
    private final Property<Boolean> arraylistBg = new Property<Boolean>("Arraylist BG", false, () -> this.hudOption.isSelected(HudOptions.ARRAYLIST));
    public final EnumProperty<ArrayPosition> arrayPositionProperty = new EnumProperty<ArrayPosition>("Array Position", ArrayPosition.RIGHT, () -> this.hudOption.isSelected(HudOptions.ARRAYLIST));
    private final DoubleProperty fadeSpeedProperty = new DoubleProperty("Fade Speed", 1.0, () -> this.hudOption.isSelected(HudOptions.ARRAYLIST) && this.arrayListColorModeProperty.get() == ArrayListColorMode.FADE, 0.1, 10.0, 0.1);
    public final DoubleProperty rainbowSpeed = new DoubleProperty("Rainbow Speed", 80.0, () -> this.hudOption.isSelected(HudOptions.ARRAYLIST) && this.arrayListColorModeProperty.get() == ArrayListColorMode.RAINBOW, 80.0, 1000.0, 1.0);
    public final DoubleProperty rainbowWidth = new DoubleProperty("Rainbow Width", 100.0, () -> this.hudOption.isSelected(HudOptions.ARRAYLIST) && this.arrayListColorModeProperty.get() == ArrayListColorMode.RAINBOW, 1.0, 300.0, 1.0);
    public static final Property<Integer> hudColor = new Property<Integer>("Color", new Color(0, 150, 250).getRGB());
    public static final DoubleProperty scoreBoardHeight = new DoubleProperty("Scoreboard Height", 0.0, 0.0, 300.0, 1.0);
    private final TimerUtil timer = new TimerUtil();

    public Hud() {
        this.setHidden(true);
    }

    private FontRenderer getFontRenderer() {
        return this.fontRender.get() == FontRendererMode.SMOOTHTTF ? Wrapper.getTestFont1() : Hud.mc.fontRendererObj;
    }

    @Listener(value=Priority.HIGH)
    public void onRenderOverlay(Render2DEvent e) {
        int notificationYOffset;
        LockedResolution lockedResolution = e.getResolution();
        int screenX = lockedResolution.getWidth();
        int n = notificationYOffset = Hud.mc.currentScreen instanceof GuiChat ? 14 : 0;
        FontRenderer fontRenderer = this.hudOption.isSelected(HudOptions.NOSTALGIA) ? Hud.mc.fontRendererObj : (this.fontRender.isSelected(FontRendererMode.BLOCKY) ? Hud.mc.blockyFont : this.getFontRenderer());
        Hud.drawPotionStatus(new ScaledResolution(mc));
        float speed = ((Double)this.fadeSpeedProperty.getValue()).floatValue();
        long ms = (long)(speed * 1000.0f);
        float darkFactor = 0.48999998f;
        long currentMillis = -1L;
        int arrayListColor = hudColor.get();
        if (this.hudOption.isSelected(HudOptions.ARRAYLIST)) {
            currentMillis = System.currentTimeMillis();
            int y = ((ArrayPosition)((Object)this.arrayPositionProperty.get())).equals((Object)ArrayPosition.RIGHT) ? 2 : 12;
            int i = 0;
            ArrayList<Module> enabledMods = new ArrayList<Module>(Tifality.INSTANCE.getModuleManager().getModules());
            enabledMods.sort((m1, m2) -> (int)(fontRenderer.getWidth(m2.getDisplayLabel()) - fontRenderer.getWidth(m1.getDisplayLabel())));
            for (Module module : enabledMods) {
                boolean shown;
                float moduleWidth;
                Translate translate = module.getTranslate();
                String name = module.getDisplayLabel();
                float f = moduleWidth = this.hudOption.isSelected(HudOptions.NOSTALGIA) ? Hud.mc.fontRendererObj.getWidth(name) : fontRenderer.getWidth(name);
                if (((ArrayPosition)((Object)this.arrayPositionProperty.get())).equals((Object)ArrayPosition.RIGHT)) {
                    if (module.isEnabled() && !module.isHidden()) {
                        translate.translate((float)screenX - moduleWidth - 1.0f, y);
                        y += 9;
                    } else {
                        translate.animate(screenX - 1, -25.0);
                    }
                } else if (module.isEnabled() && !module.isHidden()) {
                    translate.translate(2.0f, y);
                    y += 9;
                } else {
                    translate.animate(-moduleWidth, -25.0);
                }
                if (!(shown = translate.getX() < (double)screenX)) continue;
                int wColor = -1;
                float offset = (float)((currentMillis + (long)(i * 100)) % ms) / ((float)ms / 2.0f);
                switch ((ArrayListColorMode)((Object)this.arrayListColorModeProperty.getValue())) {
                    case WHITE: {
                        wColor = new Color(255, 255, 255).getRGB();
                        break;
                    }
                    case CUSTOM: {
                        wColor = arrayListColor;
                        break;
                    }
                    case RAINBOW: {
                        wColor = RenderingUtils.getRainbow(((Double)this.rainbowSpeed.get()).intValue(), ((Double)this.rainbowWidth.get()).intValue(), (int)((long)i + System.currentTimeMillis() / 15L));
                        break;
                    }
                    case FADE: {
                        wColor = this.fadeBetween(arrayListColor, this.darker(arrayListColor, darkFactor), offset);
                    }
                }
                if (this.arraylistBg.get().booleanValue()) {
                    double bgY = i == 0 ? translate.getY() - 2.0 : translate.getY();
                    if (this.arrayPositionProperty.isSelected(ArrayPosition.RIGHT)) {
                        RenderingUtils.drawRect((float)translate.getX() - 1.0f, (float)bgY, screenX, (float)translate.getY() + 9.0f, new Color(0, 0, 0, 120).getRGB());
                    } else {
                        RenderingUtils.rectangle(translate.getX() - 1.0, bgY, translate.getX() + (double)moduleWidth + 4.0, 9.0 + translate.getY(), new Color(12, 12, 12, 135).getRGB());
                    }
                }
                if (this.hudOption.isSelected(HudOptions.NOSTALGIA)) {
                    Hud.mc.fontRendererObj.drawStringWithShadow(name, (float)translate.getX() - 0.5f, (float)translate.getY(), module.getCategory().getColor());
                } else {
                    fontRenderer.drawStringWithShadow(name, (float)translate.getX(), (float)(translate.getY() + (double)(this.fontRender.get() == FontRendererMode.SMOOTHTTF ? -1 : 0)), wColor);
                }
                ++i;
            }
        }
        int i = 0;
        int watermarkColor = -1;
        float offset = ((float)currentMillis + (float)i * 100.0f) % (float)ms / ((float)ms / 2.0f);
        switch ((ArrayListColorMode)((Object)this.arrayListColorModeProperty.getValue())) {
            case WHITE: 
            case CUSTOM: {
                watermarkColor = arrayListColor;
                break;
            }
            case RAINBOW: {
                watermarkColor = RenderingUtils.getRainbow(((Double)this.rainbowSpeed.get()).intValue(), ((Double)this.rainbowWidth.get()).intValue(), (int)((long)i + System.currentTimeMillis() / 15L));
                break;
            }
            case FADE: {
                watermarkColor = this.fadeBetween(arrayListColor, this.darker(arrayListColor, darkFactor), offset);
            }
        }
        SimpleDateFormat shit = new SimpleDateFormat("HH:mm");
        DecimalFormat yes = new DecimalFormat("0");
        DecimalFormat bps = new DecimalFormat("0.00");
        boolean nostalgia = this.hudOption.isSelected(HudOptions.NOSTALGIA);
        boolean time = this.hudOption.isSelected(HudOptions.TIME);
        boolean protocol = this.hudOption.isSelected(HudOptions.PROTOCOL);
        boolean fps = this.hudOption.isSelected(HudOptions.FPS);
        boolean ping = this.hudOption.isSelected(HudOptions.PING);
        boolean coords = this.hudOption.isSelected(HudOptions.COORDS);
        boolean sessionTime = this.hudOption.isSelected(HudOptions.SESSIONTIME);
        float y = Hud.mc.currentScreen instanceof GuiChat ? -14.0f : -3.0f;
        ScaledResolution sr = new ScaledResolution(mc);
        int endTime = (int)System.currentTimeMillis();
        int lmfao = endTime - Tifality.startTime;
        String sigma = " " + (protocol ? "\u00a77[\u00a7f1.8.x\u00a77]\u00a7r " : "") + (time ? "\u00a77[\u00a7f" + shit.format(System.currentTimeMillis()) + "\u00a77]\u00a7r " : "") + (fps ? "\u00a77[\u00a7f" + Minecraft.getDebugFPS() + " FPS\u00a77]\u00a7r " : "") + (ping ? "\u00a77[\u00a7f" + Hud.getPing(Hud.mc.thePlayer) + "ms\u00a77]\u00a7r " : "");
        String bruh = "\u00a77XYZ:\u00a7r  " + yes.format(Hud.mc.thePlayer.posX) + " " + yes.format(Hud.mc.thePlayer.posY) + " " + yes.format(Hud.mc.thePlayer.posZ);
        String kekw = "\u00a77b/s:  \u00a7r" + bps.format(MovementUtils.getBPS());
        String timed = String.format("%dh %dm %ds", TimeUnit.MILLISECONDS.toHours(lmfao), TimeUnit.MILLISECONDS.toMinutes(lmfao), TimeUnit.MILLISECONDS.toSeconds(lmfao) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(lmfao)));
        float x = (float)sr.getScaledWidth() / 2.0f - (float)Hud.mc.fontRendererObj.getStringWidth(bruh) / 2.0f;
        if (nostalgia) {
            Hud.mc.fontRendererObj.drawStringWithShadow("Tifality".charAt(0) + "\u00a77" + "Tifality".substring(1) + sigma, 2.0f, 2.0f, new Color(188, 255, 188).getRGB());
        } else {
            fontRenderer.drawStringWithShadow(this.fontRender.get() == FontRendererMode.SMOOTHTTF ? this.watermarkText.getValue() + sigma : "\u00a7l" + this.watermarkText.getValue() + sigma, 2.0f, 2.0f, watermarkColor);
        }
        if (coords && nostalgia) {
            Hud.mc.fontRendererObj.drawStringWithShadow(bruh, x, 2.0f, watermarkColor);
            Hud.mc.fontRendererObj.drawStringWithShadow(kekw, (float)sr.getScaledWidth() / 2.0f - (float)Hud.mc.fontRendererObj.getStringWidth(kekw) / 2.0f, 12.0f, watermarkColor);
        } else if (coords) {
            fontRenderer.drawStringWithShadow("\u00a77XYZ:\u00a7r " + yes.format(Hud.mc.thePlayer.posX) + " " + yes.format(Hud.mc.thePlayer.posY) + " " + yes.format(Hud.mc.thePlayer.posZ) + " \u00a77b/s: \u00a7r" + bps.format(MovementUtils.getBPS()), 2.0f, (float)(sr.getScaledHeight() - 9) + y, watermarkColor);
        }
        if (sessionTime) {
            Hud.mc.fontRendererObj.drawStringWithShadow(timed, (float)sr.getScaledWidth() / 2.0f - (float)Hud.mc.fontRendererObj.getStringWidth(timed) / 2.0f, 30.0f, -1);
        }
        Scaffold scaffold = Tifality.INSTANCE.getModuleManager().getModule(Scaffold.class);
        if (this.timer.hasElapsed(1000L)) {
            if (this.timer.hasElapsed(150L)) {
                RenderingUtils.drawImage(new ResourceLocation("tifality/lag2.png"), sr.getScaledWidth() / 2 - 20, sr.getScaledHeight() / 2 - (scaffold.isEnabled() ? 85 : 65), 40, 40);
            } else {
                RenderingUtils.drawImage(new ResourceLocation("tifality/lag.png"), sr.getScaledWidth() / 2 - 20, sr.getScaledHeight() / 2 - (scaffold.isEnabled() ? 85 : 65), 40, 40);
            }
            RenderingUtils.drawOutlinedString("\u00a7lLag Detected", (float)sr.getScaledWidth() / 2.0f - (float)Hud.mc.fontRendererObj.getStringWidth("\u00a7lLag Detected") / 2.0f - 3.0f, (float)sr.getScaledHeight() / 2.0f - (float)(scaffold.isEnabled() ? 40 : 20), new Color(255, 127, 0).getRGB(), new Color(0, 0, 0).getRGB());
        }
        Tifality.getInstance().getNotificationManager().render(null, lockedResolution, true, notificationYOffset);
    }

    private static void drawPotionStatus(ScaledResolution sr) {
        MinecraftFontRenderer font = Hud.mc.fontRendererObj;
        ArrayList<PotionEffect> potions = new ArrayList<PotionEffect>();
        for (PotionEffect o : Hud.mc.thePlayer.getActivePotionEffects()) {
            potions.add(o);
        }
        potions.sort(Comparator.comparingDouble(effectx -> -Hud.mc.fontRendererObj.getWidth(I18n.format(Potion.potionTypes[effectx.getPotionID()].getName(), new Object[0]))));
        float pY = Hud.mc.currentScreen instanceof GuiChat ? -14.0f : -3.0f;
        for (PotionEffect effect : potions) {
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            String name = I18n.format(potion.getName(), new Object[0]);
            String PType = "";
            if (effect.getAmplifier() == 1) {
                name = name + " II";
            } else if (effect.getAmplifier() == 2) {
                name = name + " III";
            } else if (effect.getAmplifier() == 3) {
                name = name + " IV";
            }
            if (effect.getDuration() < 600 && effect.getDuration() > 300) {
                PType = PType + "\u00a76 " + Potion.getDurationString(effect);
            } else if (effect.getDuration() < 300) {
                PType = PType + "\u00a7c " + Potion.getDurationString(effect);
            } else if (effect.getDuration() > 600) {
                PType = PType + "\u00a77 " + Potion.getDurationString(effect);
            }
            Color c = new Color(potion.getLiquidColor());
            font.drawStringWithShadow(name, (float)sr.getScaledWidth() - font.getWidth(name + PType), (float)(sr.getScaledHeight() - 9) + pY, Colors.getColor(c.getRed(), c.getGreen(), c.getBlue()));
            font.drawStringWithShadow(PType, (float)(sr.getScaledWidth() + 4) - font.getWidth(PType), (float)(sr.getScaledHeight() - 9) + pY, -1);
            pY -= 9.0f;
        }
    }

    @Listener
    private void onPacket(PacketReceiveEvent event) {
        Packet<?> receive = event.getPacket();
        if (!(receive instanceof S02PacketChat)) {
            this.timer.reset();
        }
    }

    private int darker(int color, float factor) {
        int r = (int)((float)(color >> 16 & 0xFF) * factor);
        int g = (int)((float)(color >> 8 & 0xFF) * factor);
        int b = (int)((float)(color & 0xFF) * factor);
        int a = color >> 24 & 0xFF;
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF | (a & 0xFF) << 24;
    }

    private int fadeBetween(int color1, int color2, float offset) {
        if (offset > 1.0f) {
            offset = 1.0f - offset % 1.0f;
        }
        double invert = 1.0f - offset;
        int r = (int)((double)(color1 >> 16 & 0xFF) * invert + (double)((float)(color2 >> 16 & 0xFF) * offset));
        int g = (int)((double)(color1 >> 8 & 0xFF) * invert + (double)((float)(color2 >> 8 & 0xFF) * offset));
        int b = (int)((double)(color1 & 0xFF) * invert + (double)((float)(color2 & 0xFF) * offset));
        int a = (int)((double)(color1 >> 24 & 0xFF) * invert + (double)((float)(color2 >> 24 & 0xFF) * offset));
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
    }

    public static int getPing(EntityPlayer entityPlayer) {
        if (entityPlayer == null) {
            return 0;
        }
        NetworkPlayerInfo networkPlayerInfo = mc.getNetHandler().getPlayerInfo(entityPlayer.getUniqueID());
        return networkPlayerInfo == null ? 0 : networkPlayerInfo.getResponseTime();
    }

    private static enum ArrayPosition {
        LEFT,
        RIGHT;

    }

    private static enum HudOptions {
        SESSIONTIME,
        NOSTALGIA,
        ARRAYLIST,
        PROTOCOL,
        COORDS,
        SUFFIX,
        TIME,
        PING,
        FPS;

    }

    public static enum FontRendererMode {
        SMOOTHTTF,
        MINECRAFT,
        BLOCKY;

    }

    public static enum ArrayListColorMode {
        WHITE,
        CUSTOM,
        RAINBOW,
        FADE;

    }
}

