package non.asset.module.impl.visuals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import non.asset.Clarinet;
import non.asset.arraylist.ArrayMember;
import non.asset.event.bus.Handler;
import non.asset.event.impl.input.KeyInputEvent;
import non.asset.event.impl.render.Render2DEvent;
import non.asset.gui.MainMenu;
import non.asset.module.Module;
import non.asset.utils.OFC.MathUtils;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.ColorValue;
import non.asset.utils.value.impl.EnumValue;
import non.asset.utils.value.impl.FontValue;
import non.asset.utils.value.impl.NumberValue;

public class HUD extends Module {
	
    public ColorValue Start = new ColorValue("Start Color", new Color(0, 80, 104).getRGB());

    public ColorValue Finish = new ColorValue("Finish Color", new Color(0, 179, 42).getRGB());
	
    public static EnumValue<bordermode> BORDERMODE = new EnumValue<>("Border", bordermode.NONE);
    private BooleanValue notifications = new BooleanValue("Notifications", "Hud Notifications", true);
    
    public BooleanValue background = new BooleanValue("Background", "Arraylist Background", false);
    public BooleanValue font = new BooleanValue("Font", "Enable Custom Font", true);
    public HashMap<Module, Integer> modColors = new HashMap<>();
    public NumberValue<Integer> backgroundAlpha = new NumberValue<>("Background Alpha", 75, 1, 255, 1);
    public NumberValue<Float> borderWidth = new NumberValue<>("Border Width", 2.0f, 0.25f, 5.0f, 0.25f);
    public FontValue fontValue = new FontValue("HudFont", MainMenu.dufnctrmgyot6m18);
    
    public static int getColor;
    
    public static int getDefaultColor;
    
    public HUD() {
        super("HUD", Category.VISUALS);
        setHidden(true);
    }

    public static enum colors {
        DEFAULT, WHITE, RAINBOW, WAVE
    }

    public enum casemode {
        NORMAL, LOWER, UPPER
    }

    public enum bordermode {
        RIGHT, LEFT, WRAPPERLEFT, NONE
    }
    public enum logot {
        NORMAL, REDUCED, SKIDMA, CIRCLE
    }
    
    public enum cgmode {
        NORMAL, UPDATED
    }


    @Override
    public void onEnable() {
    	
    }

    @Override
    public void onDisable() {
    	//sako = false;
    }
    
    public boolean canRender(AbstractClientPlayer player) {
        if(player == getMc().thePlayer) return true;
        
        return isEnabled() && Clarinet.INSTANCE.getFriendManager().isFriend(player.getName());
    }
    @Handler
    public void onRender2D(Render2DEvent event) {
        if (getMc().gameSettings.showDebugInfo) return;
        final HUD hud = (HUD) Clarinet.INSTANCE.getModuleManager().getModule("hud");
        int colorWaterMark = 0;
        
        GlStateManager.pushMatrix();
		GlStateManager.translate(2f,3f, 0);
		GlStateManager.scale(2, 2, 1);
		GlStateManager.translate(-(2f), 3f, 0);
        String efgyu = "AlphaSense X" + " " + Clarinet.version;
        
        GlStateManager.popMatrix();
        
        
        getColor = hud.getGradientOffset(new Color(Start.getColor().getRed(), Start.getColor().getGreen(), Start.getColor().getBlue()), new Color(Finish.getColor().getRed(), Finish.getColor().getGreen(), Finish.getColor().getBlue()), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D)).getRGB();
        
        getDefaultColor = getColor;
        
        
        if (font.isEnabled()) {
            fontValue.getValue().drawStringWithShadow(efgyu, 3f, 3, hud.getGradientOffset(new Color(Start.getColor().getRed(), Start.getColor().getGreen(), Start.getColor().getBlue()), new Color(Finish.getColor().getRed(), Finish.getColor().getGreen(), Finish.getColor().getBlue()), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D)).getRGB());
        }else {
            getMc().fontRendererObj.drawStringWithShadow(efgyu, 3f, 3, hud.getGradientOffset(new Color(Start.getColor().getRed(), Start.getColor().getGreen(), Start.getColor().getBlue()), new Color(Finish.getColor().getRed(), Finish.getColor().getGreen(), Finish.getColor().getBlue()), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D)).getRGB());
        }
        double fuckagain = Math.hypot((this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX)  * (double)this.mc.timer.timerSpeed * 20.0D, (this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ) * (double)this.mc.timer.timerSpeed * 20.0D);
        double fukkkkk = MathUtils.round(fuckagain, 1);
        String getblo = "BPS: " + fukkkkk;
        
        if (font.isEnabled()) {
            fontValue.getValue().drawStringWithShadow(getblo, 3f, 14, hud.getGradientOffset(new Color(Start.getColor().getRed(), Start.getColor().getGreen(), Start.getColor().getBlue()), new Color(Finish.getColor().getRed(), Finish.getColor().getGreen(), Finish.getColor().getBlue()), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D)).getRGB());
        }else {
            getMc().fontRendererObj.drawStringWithShadow(getblo, 3f, 14, hud.getGradientOffset(new Color(Start.getColor().getRed(), Start.getColor().getGreen(), Start.getColor().getBlue()), new Color(Finish.getColor().getRed(), Finish.getColor().getGreen(), Finish.getColor().getBlue()), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D)).getRGB());
        }
        
        drawArraylist();   
        if (notifications.isEnabled()) {
        	drawNotifications(event.getScaledResolution());
        }
    }

    @Handler
    public void onKeyPress(KeyInputEvent event) {
        
    }

    private int getPotions() {
        int healing = 0;
        for (Slot s : getMc().thePlayer.inventoryContainer.inventorySlots)
            if (s.getHasStack()) {
                ItemStack is = s.getStack();
                if ((is.getItem() instanceof ItemPotion)) {
                    ItemPotion ip = (ItemPotion) is.getItem();
                    if (ItemPotion.isSplash(is.getMetadata())) {
                        for (PotionEffect pe : ip.getEffects(is))
                            if (pe.getPotionID() == Potion.heal.getId()) {
                                healing++;
                                break;
                            }
                    }
                }
            }
        return healing;
    }

    private void drawNotifications(ScaledResolution scaledResolution) {
        float y = scaledResolution.getScaledHeight() - 26;
        
        if (getMc().ingameGUI.getChatGUI().getChatOpen()) y -= 12;
        for (int i = 0; i < Clarinet.INSTANCE.getNotificationManager().getNotifications().size(); i++) {
            Clarinet.INSTANCE.getNotificationManager().getNotifications().get(i).draw(y);
            y -= 26;
        }
    }

    private void drawArraylist() {
        for (Module module : Clarinet.INSTANCE.getModuleManager().getModuleMap().values()) {
            if (!module.isEnabled() || module.isHidden() || Clarinet.INSTANCE.getArraylistManager().isArrayMember(module))
                continue;
            Clarinet.INSTANCE.getArraylistManager().addArray(module);
        }
        float posY = 3;
        final ArrayList<ArrayMember> arrayMembers = new ArrayList<>(Clarinet.INSTANCE.getArraylistManager().getArrayMembers());
        arrayMembers.sort(Comparator.comparingDouble(arrayMember -> -(font.isEnabled() ? fontValue.getValue().getStringWidth(getModuleRenderString(arrayMember.getModule())) : getMc().fontRendererObj.getStringWidth(getModuleRenderString(arrayMember.getModule())))));
        for (ArrayMember arrayMember : arrayMembers) {
            arrayMember.draw(arrayMembers,posY - (font.isEnabled() ? fontValue.getValue().getHeight() + 4 : getMc().fontRendererObj.FONT_HEIGHT + 2), posY);
            posY += (font.isEnabled() ? fontValue.getValue().getHeight() + 4 : getMc().fontRendererObj.FONT_HEIGHT + 2);
        }
    }

    private void drawArmor(int x, int y) {
        if (getMc().thePlayer.inventory.armorInventory.length > 0) {
            List<ItemStack> items = new ArrayList<>();
            if (getMc().thePlayer.getHeldItem() != null) {
                items.add(getMc().thePlayer.getHeldItem());
            }
            for (int index = 3; index >= 0; index--) {
                ItemStack stack = getMc().thePlayer.inventory.armorInventory[index];
                if (stack != null) {
                    items.add(stack);
                }
            }
            for (ItemStack stack : items) {
                GlStateManager.pushMatrix();
                GlStateManager.enableLighting();
                getMc().getRenderItem().renderItemIntoGUI(stack, x, y);
                getMc().getRenderItem().renderItemOverlayIntoGUI(getMc().fontRendererObj, stack, x, y, "");
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                if (stack.isStackable() && stack.stackSize > 0) {
                    if (font.isEnabled())
                        fontValue.getValue().drawStringWithShadow(String.valueOf(stack.stackSize), x + 4, y + 8, 0xDDD1E6);
                    else
                        getMc().fontRendererObj.drawStringWithShadow(String.valueOf(stack.stackSize), x + 4, y + 8, 0xDDD1E6);
                }
                GlStateManager.enableDepth();
                GlStateManager.popMatrix();
                x += 18;
            }
        }
    }
    
    public int color(int index, int count) {
        float[] hsb = new float[3];
        final Color clr = new Color(50, 40, 220);
        Color.RGBtoHSB(clr.getRed(), clr.getGreen(), clr.getBlue(), hsb);
        float brightness = Math.abs(((getOffset() + (index / (float) count) * 4) % 2) - 1);
        brightness = 0.4f + (0.4f * brightness);

        hsb[2] = brightness % 1f;
        return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }
    
    private float getOffset() {
        return (System.currentTimeMillis() % 2000) / 1000f;
    }

    public int getRandomColor() {
        return new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), 255).getRGB();
    }

    public Color getGradientOffset(Color color1, Color color2, double offset) {
        if (offset > 1) {
            double left = offset % 1;
            int off = (int) offset;
            offset = off % 2 == 0 ? left : 1 - left;

        }
        double inverse_percent = 1 - offset;
        int redPart = (int) (color1.getRed() * inverse_percent + color2.getRed() * offset);
        int greenPart = (int) (color1.getGreen() * inverse_percent + color2.getGreen() * offset);
        int bluePart = (int) (color1.getBlue() * inverse_percent + color2.getBlue() * offset);
        return new Color(redPart, greenPart, bluePart);
    }

    private String getModuleRenderString(Module module) {
    	return Objects.nonNull(module.getRenderLabel()) ? (module.getRenderLabel() + (Objects.nonNull(module.getSuffix()) ?  " " + module.getSuffix() : "")) : (module.getLabel() + (Objects.nonNull(module.getSuffix()) ?  " " + module.getSuffix() : ""));
    }
}
