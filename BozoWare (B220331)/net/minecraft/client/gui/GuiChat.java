// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.commons.lang3.StringUtils;
import net.minecraft.util.BlockPos;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.MovingObjectPosition;
import java.util.Iterator;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Mouse;
import java.io.IOException;
import net.minecraft.client.network.NetworkPlayerInfo;
import com.mojang.realmsclient.gui.ChatFormatting;
import bozoware.base.util.visual.BloomUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import bozoware.impl.module.combat.Aura;
import bozoware.impl.module.visual.HUD;
import net.minecraft.entity.Entity;
import java.text.DecimalFormat;
import org.lwjgl.opengl.GL11;
import bozoware.base.util.visual.ColorUtil;
import java.awt.Color;
import bozoware.base.util.visual.RenderUtil;
import net.minecraft.util.MathHelper;
import bozoware.base.util.visual.BlurUtil;
import bozoware.impl.module.visual.TargetHUD;
import bozoware.base.module.Module;
import org.lwjgl.input.Keyboard;
import com.google.common.collect.Lists;
import bozoware.base.BozoWare;
import java.util.List;
import bozoware.visual.font.MinecraftFontRenderer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import org.apache.logging.log4j.Logger;

public class GuiChat extends GuiScreen
{
    private static final Logger logger;
    private String historyBuffer;
    private int x;
    private int y;
    private int distX;
    private int distY;
    private boolean dragging;
    boolean isTHUDShowing;
    private double healthBarWidth;
    private double hpPercentage;
    private double hpWidth;
    private double Width;
    public double xPosi;
    public double xPosition;
    private int hp;
    private double armorBarWidth;
    private EntityOtherPlayerMP target;
    private double hudHeight;
    MinecraftFontRenderer SFR;
    MinecraftFontRenderer MFR;
    private int sentHistoryCursor;
    private boolean playerNamesFound;
    private boolean waitingOnAutocomplete;
    private int autocompleteIndex;
    private List<String> foundPlayerNames;
    protected GuiTextField inputField;
    private String defaultInputFieldText;
    
    public GuiChat() {
        this.historyBuffer = "";
        this.SFR = BozoWare.getInstance().getFontManager().smallFontRenderer;
        this.MFR = BozoWare.getInstance().getFontManager().mediumFontRenderer;
        this.sentHistoryCursor = -1;
        this.foundPlayerNames = (List<String>)Lists.newArrayList();
        this.defaultInputFieldText = "";
    }
    
    public GuiChat(final String defaultText) {
        this.historyBuffer = "";
        this.SFR = BozoWare.getInstance().getFontManager().smallFontRenderer;
        this.MFR = BozoWare.getInstance().getFontManager().mediumFontRenderer;
        this.sentHistoryCursor = -1;
        this.foundPlayerNames = (List<String>)Lists.newArrayList();
        this.defaultInputFieldText = "";
        this.defaultInputFieldText = defaultText;
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        (this.inputField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 4, 12)).setMaxStringLength(100);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final int partialTicks) {
        if (BozoWare.getInstance().getModuleManager().getModuleByClass.apply(TargetHUD.class).isModuleToggled()) {
            if (TargetHUD.getInstance().targetHUDMode.equals(TargetHUD.targetHUDModes.Bozo)) {
                this.xPosi = TargetHUD.getInstance().xPos.getPropertyValue();
                this.xPosition = this.xPosi;
                BlurUtil.blurArea(this.xPosition - 5.0, TargetHUD.getInstance().yPos.getPropertyValue() - 1, 135 + this.mc.fontRendererObj.getStringWidth(this.mc.thePlayer.getName()), 22.0);
                this.hpPercentage = this.mc.thePlayer.getHealth() / this.mc.thePlayer.getMaxHealth();
                this.hpPercentage = MathHelper.clamp_double(this.hpPercentage, 0.0, 1.0);
                this.hpWidth = 115.0 * this.hpPercentage;
                this.hp = Math.round(this.mc.thePlayer.getHealth());
                this.healthBarWidth = RenderUtil.animate(this.hpWidth, this.healthBarWidth, 0.05);
                final ScaledResolution SR = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                Gui.drawRect(this.xPosition, TargetHUD.getInstance().yPos.getPropertyValue() + 17, this.xPosition + 15.0 + this.healthBarWidth, TargetHUD.getInstance().yPos.getPropertyValue() + 19, ColorUtil.interpolateColorsDynamic(3, SR.getScaledWidth() * 15, new Color(-65536), new Color(-11534336)).getRGB());
                this.mc.fontRendererObj.drawStringWithShadow(this.mc.thePlayer.getName(), (float)(this.xPosition + 12.0), (float)(TargetHUD.getInstance().yPos.getPropertyValue() + 5), -1);
                this.mc.fontRendererObj.drawStringWithShadow("\u2764", (float)(this.xPosition + 105.0 + this.mc.fontRendererObj.getStringWidth(this.mc.thePlayer.getName()) - 3.0), (float)(TargetHUD.getInstance().yPos.getPropertyValue() + 5), -65536);
                this.mc.fontRendererObj.drawStringWithShadow(String.valueOf(this.hp), (float)(this.xPosition + 115.0 + this.mc.fontRendererObj.getStringWidth(this.mc.thePlayer.getName()) - 3.0), (float)(TargetHUD.getInstance().yPos.getPropertyValue() + 5), -1);
            }
            if (TargetHUD.getInstance().targetHUDMode.equals(TargetHUD.targetHUDModes.Rise)) {
                this.xPosi = TargetHUD.getInstance().xPos.getPropertyValue();
                this.xPosition = this.xPosi;
                RenderUtil.drawSmoothRoundedRect((float)(this.xPosition - 5.0), (float)(TargetHUD.getInstance().yPos.getPropertyValue() - 1), (float)this.xPosition + 135.0f + this.mc.thePlayer.getName().length(), (float)(TargetHUD.getInstance().yPos.getPropertyValue() + 44), 15.0f, 1073741824);
                final NetworkPlayerInfo playerInf = this.mc.getNetHandler().getPlayerInfo(this.mc.thePlayer.getUniqueID());
                if (playerInf != null) {
                    this.mc.getTextureManager().bindTexture(playerInf.getLocationSkin());
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    Gui.drawScaledCustomSizeModalRect((int)this.xPosition, TargetHUD.getInstance().yPos.getPropertyValue() + 2, 8.0f, 8.0f, 8, 8, 30, 30, 64.0f, 64.0f);
                }
                this.MFR.drawStringWithShadow("Name " + this.mc.thePlayer.getName(), (int)this.xPosition + 35, TargetHUD.getInstance().yPos.getPropertyValue() + 8, -1);
                final DecimalFormat df = new DecimalFormat("0.0");
                final String distance = df.format(this.mc.thePlayer.getDistanceToEntity(this.mc.thePlayer));
                this.SFR.drawStringWithShadow("Distance " + String.valueOf(distance), (int)this.xPosition + 35, TargetHUD.getInstance().yPos.getPropertyValue() + 22, -1);
                this.SFR.drawStringWithShadow("Hurt " + this.mc.thePlayer.hurtTime, (int)this.xPosition + 87, TargetHUD.getInstance().yPos.getPropertyValue() + 22, -1);
                this.hpPercentage = this.mc.thePlayer.getHealth() / this.mc.thePlayer.getMaxHealth();
                this.hpPercentage = MathHelper.clamp_double(this.hpPercentage, 0.0, 1.0);
                this.hpWidth = 115.0 * this.hpPercentage;
                this.healthBarWidth = RenderUtil.animate(this.hpWidth, this.healthBarWidth, 0.05);
                Gui.drawRect((int)this.xPosition, TargetHUD.getInstance().yPos.getPropertyValue() + 34, (int)this.xPosition + 15 + this.healthBarWidth, TargetHUD.getInstance().yPos.getPropertyValue() + 39, HUD.getInstance().bozoColor);
                final DecimalFormat df2 = new DecimalFormat("00.0");
                String healthFormatted;
                if (this.mc.thePlayer.getHealth() > 10.0f) {
                    healthFormatted = df2.format(this.mc.thePlayer.getHealth());
                }
                else {
                    healthFormatted = df.format(this.mc.thePlayer.getHealth());
                }
                if (this.mc.thePlayer.getHealth() < this.mc.thePlayer.getMaxHealth()) {
                    this.SFR.drawStringWithShadow(healthFormatted, (int)this.xPosition + 12 + this.healthBarWidth + 5.0, TargetHUD.getInstance().yPos.getPropertyValue() + 34, -1);
                }
                this.isTHUDShowing = true;
            }
            if (TargetHUD.getInstance().targetHUDMode.equals(TargetHUD.targetHUDModes.Novoline)) {
                this.xPosi = TargetHUD.getInstance().xPos.getPropertyValue();
                this.xPosition = this.xPosi;
                final Aura ka = BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Aura.class);
                final ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                if (this.mc.thePlayer != null && ka.isModuleToggled()) {
                    final float startX = 20.0f;
                    final float renderX = (int)this.xPosition + startX;
                    final float renderY = (float)(TargetHUD.getInstance().yPos.getPropertyValue() + 10);
                    int maxX2 = 30;
                    if (Aura.target.getCurrentArmor(3) != null) {
                        maxX2 += 15;
                    }
                    if (Aura.target.getCurrentArmor(2) != null) {
                        maxX2 += 15;
                    }
                    if (Aura.target.getCurrentArmor(1) != null) {
                        maxX2 += 15;
                    }
                    if (Aura.target.getCurrentArmor(0) != null) {
                        maxX2 += 15;
                    }
                    if (Aura.target.getHeldItem() != null) {
                        maxX2 += 15;
                    }
                    final int healthColor = TargetHUD.getHealthColor(this.mc.thePlayer.getHealth(), this.mc.thePlayer.getMaxHealth()).getRGB();
                    final float maxX3 = (float)Math.max(maxX2, this.mc.fontRendererObj.getStringWidth(this.mc.thePlayer.getName()) + 30);
                    this.hpPercentage = this.mc.thePlayer.getHealth() / this.mc.thePlayer.getMaxHealth();
                    this.hpPercentage = MathHelper.clamp_double(this.hpPercentage, 0.0, 1.0);
                    this.hpWidth = maxX3 * this.hpPercentage;
                    this.hp = Math.round(this.mc.thePlayer.getHealth());
                    this.healthBarWidth = RenderUtil.animate(this.hpWidth, this.healthBarWidth, 0.05);
                    Gui.drawRect(renderX, renderY, renderX + maxX3, renderY + 40.0f, new Color(0.0f, 0.0f, 0.0f, 0.3f).getRGB());
                    Gui.drawRect(renderX, renderY + 38.0f, renderX + this.healthBarWidth, renderY + 40.0f, healthColor);
                    this.mc.fontRendererObj.drawStringWithShadow(this.mc.thePlayer.getName(), renderX + 25.0f, renderY + 7.0f, -1);
                    int xAdd = 0;
                    final double multiplier = 0.85;
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(multiplier, multiplier, multiplier);
                    if (this.mc.thePlayer.getCurrentArmor(3) != null) {
                        this.mc.getRenderItem().renderItemAndEffectIntoGUI(this.mc.thePlayer.getCurrentArmor(3), (int)((this.xPosition + startX + 23.0 + xAdd) / multiplier), (int)((TargetHUD.getInstance().yPos.getPropertyValue() + 28) / multiplier));
                        xAdd += 15;
                    }
                    if (this.mc.thePlayer.getCurrentArmor(2) != null) {
                        this.mc.getRenderItem().renderItemAndEffectIntoGUI(this.mc.thePlayer.getCurrentArmor(2), (int)((this.xPosition + startX + 23.0 + xAdd) / multiplier), (int)((TargetHUD.getInstance().yPos.getPropertyValue() + 28) / multiplier));
                        xAdd += 15;
                    }
                    if (this.mc.thePlayer.getCurrentArmor(1) != null) {
                        this.mc.getRenderItem().renderItemAndEffectIntoGUI(this.mc.thePlayer.getCurrentArmor(1), (int)((this.xPosition + startX + 23.0 + xAdd) / multiplier), (int)((TargetHUD.getInstance().yPos.getPropertyValue() + 28) / multiplier));
                        xAdd += 15;
                    }
                    if (this.mc.thePlayer.getCurrentArmor(0) != null) {
                        this.mc.getRenderItem().renderItemAndEffectIntoGUI(this.mc.thePlayer.getCurrentArmor(0), (int)((this.xPosition + startX + 23.0 + xAdd) / multiplier), (int)((TargetHUD.getInstance().yPos.getPropertyValue() + 28) / multiplier));
                        xAdd += 15;
                    }
                    if (this.mc.thePlayer.getHeldItem() != null) {
                        this.mc.getRenderItem().renderItemAndEffectIntoGUI(this.mc.thePlayer.getHeldItem(), (int)((this.xPosition + startX + 23.0 + xAdd) / multiplier), (int)((TargetHUD.getInstance().yPos.getPropertyValue() + 28) / multiplier));
                    }
                    GlStateManager.popMatrix();
                    GuiInventory.drawEntityOnScreen((int)renderX + 12, (int)renderY + 33, 15, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, this.mc.thePlayer);
                }
            }
            if (TargetHUD.getInstance().targetHUDMode.equals(TargetHUD.targetHUDModes.Skeet)) {
                this.xPosi = TargetHUD.getInstance().xPos.getPropertyValue();
                this.xPosition = this.xPosi;
                if (this.mc.thePlayer.getName().length() <= 4) {
                    this.Width = -this.healthBarWidth;
                }
                else {
                    this.Width = -this.healthBarWidth;
                }
                Gui.drawRect(this.xPosition, TargetHUD.getInstance().yPos.getPropertyValue(), this.xPosition + 147.0, TargetHUD.getInstance().yPos.getPropertyValue() + 35, -16777216);
                RenderUtil.drawRoundedOutline((float)this.xPosition, TargetHUD.getInstance().yPos.getPropertyValue(), (float)this.xPosition + 147.0f, (float)(TargetHUD.getInstance().yPos.getPropertyValue() + 35), -16777216, 3.0f, 2.0f);
                BloomUtil.drawAndBloom(() -> RenderUtil.drawRoundedOutline((float)this.xPosition, TargetHUD.getInstance().yPos.getPropertyValue(), (float)this.xPosition + 147.0f, (float)(TargetHUD.getInstance().yPos.getPropertyValue() + 35), -14145496, 0.8f, 2.0f));
                RenderUtil.glHorizontalGradientQuad(this.xPosition + 2.0, TargetHUD.getInstance().yPos.getPropertyValue(), 145.0, 2.0, -13127206, -3644747);
                RenderUtil.glHorizontalGradientQuad(this.xPosition + 2.0, TargetHUD.getInstance().yPos.getPropertyValue(), 145.0, 2.0, -3644747, -3349962);
                this.hpPercentage = this.mc.thePlayer.getHealth() / this.mc.thePlayer.getMaxHealth();
                this.hpPercentage = MathHelper.clamp_double(this.hpPercentage, 0.0, 1.0);
                this.hpWidth = 115.0 * this.hpPercentage;
                this.hp = (int)Math.floor(this.mc.thePlayer.getHealth());
                this.healthBarWidth = RenderUtil.animate(this.hpWidth, this.healthBarWidth, 0.05);
                final Color bozoColor = new Color(HUD.getInstance().bozoColor, true);
                HUD.getInstance();
                final Color bozoColor2 = new Color(HUD.bozoColor2, true);
                final int healthColor2 = TargetHUD.getHealthColor(this.mc.thePlayer.getHealth(), this.mc.thePlayer.getMaxHealth()).getRGB();
                Gui.drawGradientRect(this.xPosition + 12.5, TargetHUD.getInstance().yPos.getPropertyValue() + 22, this.xPosition + 18.0 + this.healthBarWidth, TargetHUD.getInstance().yPos.getPropertyValue() + 33, healthColor2, 1);
                this.mc.fontRendererObj.drawStringWithShadow(this.mc.thePlayer.getName(), (float)(this.xPosition + 12.0), (float)(TargetHUD.getInstance().yPos.getPropertyValue() + 5), -1);
                this.mc.fontRendererObj.drawStringWithShadow("\u2764", (float)(this.xPosition + 110.0), (float)(TargetHUD.getInstance().yPos.getPropertyValue() + 5), -65536);
                this.mc.fontRendererObj.drawStringWithShadow(ChatFormatting.RED + " " + ChatFormatting.WHITE + String.valueOf(this.hp), (float)(this.xPosition + 116.0), (float)(TargetHUD.getInstance().yPos.getPropertyValue() + 5), -1);
            }
            if (TargetHUD.getInstance().targetHUDMode.equals(TargetHUD.targetHUDModes.Crazy)) {
                this.hpPercentage = this.mc.thePlayer.getHealth() / this.mc.thePlayer.getMaxHealth();
                this.hpWidth = 125.0 * this.hpPercentage;
                this.hp = Math.round(this.mc.thePlayer.getHealth());
                this.healthBarWidth = RenderUtil.animate(this.hpWidth, this.healthBarWidth, 0.05);
                this.xPosi = TargetHUD.getInstance().xPos.getPropertyValue();
                this.xPosition = this.xPosi;
                final NetworkPlayerInfo playerInfo = this.mc.getNetHandler().getPlayerInfo(this.mc.thePlayer.getUniqueID());
                BlurUtil.blurArea(this.xPosition - ((playerInfo == null) ? -20 : 10), TargetHUD.getInstance().yPos.getPropertyValue() + 10, 170.0 - this.xPosition - ((playerInfo == null) ? 10 : 0), 40.0);
                BloomUtil.bloom(() -> Gui.drawRect(this.xPosition - ((playerInfo == null) ? -20 : 10), TargetHUD.getInstance().yPos.getPropertyValue() + 10, this.xPosition + 170.0, TargetHUD.getInstance().yPos.getPropertyValue() + 49.5, HUD.getInstance().bozoColor));
                BloomUtil.drawAndBloom(() -> Gui.drawRect(this.xPosition - ((playerInfo == null) ? -20 : 10), TargetHUD.getInstance().yPos.getPropertyValue() + 10, this.xPosition + 170.0, TargetHUD.getInstance().yPos.getPropertyValue() + 50, 1879048192));
                if (playerInfo != null) {
                    this.mc.getTextureManager().bindTexture(playerInfo.getLocationSkin());
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    BloomUtil.drawAndBloom(() -> Gui.drawScaledCustomSizeModalRect((int)(this.xPosition - 5.0), TargetHUD.getInstance().yPos.getPropertyValue() + 15, 8.0f, 8.0f, 8, 8, 30, 30, 64.0f, 64.0f));
                }
                this.mc.fontRendererObj.drawStringWithShadow("\u2764", (float)(this.xPosition + 157.0), (float)(TargetHUD.getInstance().yPos.getPropertyValue() + 15), -1);
                this.MFR.drawStringWithShadow(this.mc.thePlayer.getName(), this.xPosition + 34.0, TargetHUD.getInstance().yPos.getPropertyValue() + 16, -1);
                this.MFR.drawStringWithShadow(String.valueOf(this.hp / 2), this.xPosition + 155.0 - this.MFR.getStringWidth(String.valueOf(this.hp / 2)), TargetHUD.getInstance().yPos.getPropertyValue() + 16, -1);
                this.MFR.drawStringWithShadow("Hurt Time: " + this.mc.thePlayer.hurtTime, this.xPosition + 34.0, TargetHUD.getInstance().yPos.getPropertyValue() + 26, -1);
                this.MFR.drawStringWithShadow("Distance: " + Math.round(this.mc.thePlayer.getDistanceToEntity(this.mc.thePlayer)), this.xPosition + 120.0 - this.MFR.getStringWidth(String.valueOf(Math.round(this.mc.thePlayer.getDistanceToEntity(this.mc.thePlayer)))), TargetHUD.getInstance().yPos.getPropertyValue() + 26, -1);
                RenderUtil.glHorizontalGradientQuad(this.xPosition + 35.0, TargetHUD.getInstance().yPos.getPropertyValue() + 40, 125.0, 7.0, HUD.getInstance().bozoColorDarker, HUD.getInstance().bozoColorDarker);
                final double x;
                final double y;
                final double healthBarWidth;
                final int bozoColor3;
                final double height;
                BloomUtil.drawAndBloom(() -> {
                    x = this.xPosition + 35.0;
                    y = TargetHUD.getInstance().yPos.getPropertyValue() + 40;
                    healthBarWidth = this.healthBarWidth;
                    bozoColor3 = HUD.getInstance().bozoColor;
                    HUD.getInstance();
                    RenderUtil.glHorizontalGradientQuad(x, y, healthBarWidth, height, bozoColor3, HUD.bozoColor2);
                    return;
                });
            }
        }
        if (this.dragging) {
            this.x = mouseX - this.distX;
            this.y = mouseY - this.distY;
        }
        super.drawScreen(mouseX, mouseY, (float)partialTicks);
    }
    
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHoveringFrame(mouseX, mouseY) && mouseButton == 0) {
            this.distX = mouseX - this.x;
            this.distY = mouseY - this.y;
            this.dragging = true;
        }
    }
    
    private boolean isHoveringFrame(final int mouseX, final int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + 170 && mouseY >= this.y && mouseY <= this.y + 250;
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.mc.ingameGUI.getChatGUI().resetScroll();
    }
    
    @Override
    public void updateScreen() {
        this.inputField.updateCursorCounter();
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        this.waitingOnAutocomplete = false;
        if (keyCode == 15) {
            this.autocompletePlayerNames();
        }
        else {
            this.playerNamesFound = false;
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
        else if (keyCode != 28 && keyCode != 156) {
            if (keyCode == 200) {
                this.getSentHistory(-1);
            }
            else if (keyCode == 208) {
                this.getSentHistory(1);
            }
            else if (keyCode == 201) {
                this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
            }
            else if (keyCode == 209) {
                this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
            }
            else {
                this.inputField.textboxKeyTyped(typedChar, keyCode);
            }
        }
        else {
            final String s = this.inputField.getText().trim();
            if (s.length() > 0) {
                this.sendChatMessage(s);
            }
            this.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();
        if (i != 0) {
            if (i > 1) {
                i = 1;
            }
            if (i < -1) {
                i = -1;
            }
            if (!GuiScreen.isShiftKeyDown()) {
                i *= 7;
            }
            this.mc.ingameGUI.getChatGUI().scroll(i);
        }
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        if (mouseButton == 0) {
            final IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
            if (this.handleComponentClick(ichatcomponent)) {
                return;
            }
        }
        this.inputField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void setText(final String newChatText, final boolean shouldOverwrite) {
        if (shouldOverwrite) {
            this.inputField.setText(newChatText);
        }
        else {
            this.inputField.writeText(newChatText);
        }
    }
    
    public void autocompletePlayerNames() {
        if (this.playerNamesFound) {
            this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
            if (this.autocompleteIndex >= this.foundPlayerNames.size()) {
                this.autocompleteIndex = 0;
            }
        }
        else {
            final int i = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
            this.foundPlayerNames.clear();
            this.autocompleteIndex = 0;
            final String s = this.inputField.getText().substring(i).toLowerCase();
            final String s2 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
            this.sendAutocompleteRequest(s2, s);
            if (this.foundPlayerNames.isEmpty()) {
                return;
            }
            this.playerNamesFound = true;
            this.inputField.deleteFromCursor(i - this.inputField.getCursorPosition());
        }
        if (this.foundPlayerNames.size() > 1) {
            final StringBuilder stringbuilder = new StringBuilder();
            for (final String s3 : this.foundPlayerNames) {
                if (stringbuilder.length() > 0) {
                    stringbuilder.append(", ");
                }
                stringbuilder.append(s3);
            }
            this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(stringbuilder.toString()), 1);
        }
        this.inputField.writeText(this.foundPlayerNames.get(this.autocompleteIndex++));
    }
    
    private void sendAutocompleteRequest(final String p_146405_1_, final String p_146405_2_) {
        if (p_146405_1_.length() >= 1) {
            BlockPos blockpos = null;
            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                blockpos = this.mc.objectMouseOver.getBlockPos();
            }
            this.mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(p_146405_1_, blockpos));
            this.waitingOnAutocomplete = true;
        }
    }
    
    public void getSentHistory(final int msgPos) {
        int i = this.sentHistoryCursor + msgPos;
        final int j = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        i = MathHelper.clamp_int(i, 0, j);
        if (i != this.sentHistoryCursor) {
            if (i == j) {
                this.sentHistoryCursor = j;
                this.inputField.setText(this.historyBuffer);
            }
            else {
                if (this.sentHistoryCursor == j) {
                    this.historyBuffer = this.inputField.getText();
                }
                this.inputField.setText(this.mc.ingameGUI.getChatGUI().getSentMessages().get(i));
                this.sentHistoryCursor = i;
            }
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        Gui.drawRect(2.0, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        this.inputField.drawTextBox();
        final IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
        if (ichatcomponent != null && ichatcomponent.getChatStyle().getChatHoverEvent() != null) {
            this.handleComponentHover(ichatcomponent, mouseX, mouseY);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void onAutocompleteResponse(final String[] p_146406_1_) {
        if (this.waitingOnAutocomplete) {
            this.playerNamesFound = false;
            this.foundPlayerNames.clear();
            for (final String s : p_146406_1_) {
                if (s.length() > 0) {
                    this.foundPlayerNames.add(s);
                }
            }
            final String s2 = this.inputField.getText().substring(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false));
            final String s3 = StringUtils.getCommonPrefix(p_146406_1_);
            if (s3.length() > 0 && !s2.equalsIgnoreCase(s3)) {
                this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
                this.inputField.writeText(s3);
            }
            else if (this.foundPlayerNames.size() > 0) {
                this.playerNamesFound = true;
                this.autocompletePlayerNames();
            }
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
