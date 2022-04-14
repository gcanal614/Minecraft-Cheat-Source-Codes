/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui;

import cafe.corrosion.Corrosion;
import cafe.corrosion.font.TTFFontRenderer;
import cafe.corrosion.module.impl.player.SmoothChat;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiNewChat
extends Gui {
    private static final Logger logger = LogManager.getLogger();
    private final Minecraft mc;
    private final List<String> sentMessages = Lists.newArrayList();
    private final List<ChatLine> chatLines = Lists.newArrayList();
    private final List<ChatLine> field_146253_i = Lists.newArrayList();
    private int scrollPos;
    private boolean isScrolled;

    public GuiNewChat(Minecraft mcIn) {
        this.mc = mcIn;
    }

    public void drawChat(int p_146230_1_) {
        SmoothChat smoothChat = (SmoothChat)Corrosion.INSTANCE.getModuleManager().getModule(SmoothChat.class);
        TTFFontRenderer renderer = smoothChat.buildFontRenderer();
        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            int i2 = this.getLineCount();
            boolean flag = false;
            int j2 = 0;
            int k2 = this.field_146253_i.size();
            float f2 = this.mc.gameSettings.chatOpacity * 0.9f + 0.1f;
            if (k2 > 0) {
                if (this.getChatOpen()) {
                    flag = true;
                }
                float f1 = this.getChatScale();
                int l2 = MathHelper.ceiling_float_int((float)this.getChatWidth() / f1);
                GlStateManager.pushMatrix();
                GlStateManager.translate(2.0f, 20.0f, 0.0f);
                GlStateManager.scale(f1, f1, 1.0f);
                for (int i1 = 0; i1 + this.scrollPos < this.field_146253_i.size() && i1 < i2; ++i1) {
                    int j1;
                    ChatLine chatline = this.field_146253_i.get(i1 + this.scrollPos);
                    if (chatline == null || (j1 = p_146230_1_ - chatline.getUpdatedCounter()) >= 200 && !flag) continue;
                    double d0 = (double)j1 / 200.0;
                    d0 = 1.0 - d0;
                    d0 *= 10.0;
                    d0 = MathHelper.clamp_double(d0, 0.0, 1.0);
                    d0 *= d0;
                    int l1 = (int)(255.0 * d0);
                    if (flag) {
                        l1 = 255;
                    }
                    l1 = (int)((float)l1 * f2);
                    ++j2;
                    if (l1 <= 3) continue;
                    int i22 = 0;
                    int j22 = -i1 * 9;
                    if (!smoothChat.isEnabled() || smoothChat.isEnabled() && !((Boolean)smoothChat.getTransparentChat().getValue()).booleanValue()) {
                        GuiNewChat.drawRect(i22, j22 - 9, i22 + l2 + 4, j22, l1 / 2 << 24);
                    }
                    String s2 = chatline.getChatComponent().getFormattedText();
                    GlStateManager.enableBlend();
                    if (smoothChat.isEnabled()) {
                        renderer.drawString(s2, i22, j22 - 8, 0xFFFFFF + (l1 << 24));
                    } else {
                        this.mc.fontRendererObj.drawStringWithShadow(s2, i22, j22 - 8, 0xFFFFFF + (l1 << 24));
                    }
                    GlStateManager.disableAlpha();
                    GlStateManager.disableBlend();
                }
                if (flag) {
                    int k22 = this.mc.fontRendererObj.FONT_HEIGHT;
                    GlStateManager.translate(-3.0f, 0.0f, 0.0f);
                    int l22 = k2 * k22 + k2;
                    int i3 = j2 * k22 + j2;
                    int j3 = this.scrollPos * i3 / k2;
                    int k1 = i3 * i3 / l22;
                    if (l22 != i3) {
                        int k3 = j3 > 0 ? 170 : 96;
                        int l3 = this.isScrolled ? 0xCC3333 : 0x3333AA;
                        GuiNewChat.drawRect(0.0, -j3, 2.0, -j3 - k1, l3 + (k3 << 24));
                        GuiNewChat.drawRect(2.0, -j3, 1.0, -j3 - k1, 0xCCCCCC + (k3 << 24));
                    }
                }
                GlStateManager.popMatrix();
            }
        }
    }

    public void clearChatMessages() {
        this.field_146253_i.clear();
        this.chatLines.clear();
        this.sentMessages.clear();
    }

    public void printChatMessage(IChatComponent p_146227_1_) {
        this.printChatMessageWithOptionalDeletion(p_146227_1_, 0);
    }

    public void printChatMessageWithOptionalDeletion(IChatComponent p_146234_1_, int p_146234_2_) {
        this.setChatLine(p_146234_1_, p_146234_2_, this.mc.ingameGUI.getUpdateCounter(), false);
        System.out.println("[CHAT] " + p_146234_1_.getUnformattedText());
    }

    private void setChatLine(IChatComponent p_146237_1_, int p_146237_2_, int p_146237_3_, boolean p_146237_4_) {
        if (p_146237_2_ != 0) {
            this.deleteChatLine(p_146237_2_);
        }
        int i2 = MathHelper.floor_float((float)this.getChatWidth() / this.getChatScale());
        List<IChatComponent> list = GuiUtilRenderComponents.func_178908_a(p_146237_1_, i2, this.mc.fontRendererObj, false, false);
        boolean flag = this.getChatOpen();
        for (IChatComponent ichatcomponent : list) {
            if (flag && this.scrollPos > 0) {
                this.isScrolled = true;
                this.scroll(1);
            }
            this.field_146253_i.add(0, new ChatLine(p_146237_3_, ichatcomponent, p_146237_2_));
        }
        while (this.field_146253_i.size() > 100) {
            this.field_146253_i.remove(this.field_146253_i.size() - 1);
        }
        if (!p_146237_4_) {
            this.chatLines.add(0, new ChatLine(p_146237_3_, p_146237_1_, p_146237_2_));
            while (this.chatLines.size() > 100) {
                this.chatLines.remove(this.chatLines.size() - 1);
            }
        }
    }

    public void refreshChat() {
        this.field_146253_i.clear();
        this.resetScroll();
        for (int i2 = this.chatLines.size() - 1; i2 >= 0; --i2) {
            ChatLine chatline = this.chatLines.get(i2);
            this.setChatLine(chatline.getChatComponent(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
        }
    }

    public List<String> getSentMessages() {
        return this.sentMessages;
    }

    public void addToSentMessages(String p_146239_1_) {
        if (this.sentMessages.isEmpty() || !this.sentMessages.get(this.sentMessages.size() - 1).equals(p_146239_1_)) {
            this.sentMessages.add(p_146239_1_);
        }
    }

    public void resetScroll() {
        this.scrollPos = 0;
        this.isScrolled = false;
    }

    public void scroll(int p_146229_1_) {
        this.scrollPos += p_146229_1_;
        int i2 = this.field_146253_i.size();
        if (this.scrollPos > i2 - this.getLineCount()) {
            this.scrollPos = i2 - this.getLineCount();
        }
        if (this.scrollPos <= 0) {
            this.scrollPos = 0;
            this.isScrolled = false;
        }
    }

    public IChatComponent getChatComponent(int p_146236_1_, int p_146236_2_) {
        if (!this.getChatOpen()) {
            return null;
        }
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        int i2 = scaledresolution.getScaleFactor();
        float f2 = this.getChatScale();
        int j2 = p_146236_1_ / i2 - 3;
        int k2 = p_146236_2_ / i2 - 27;
        j2 = MathHelper.floor_float((float)j2 / f2);
        k2 = MathHelper.floor_float((float)k2 / f2);
        if (j2 >= 0 && k2 >= 0) {
            int l2 = Math.min(this.getLineCount(), this.field_146253_i.size());
            if (j2 <= MathHelper.floor_float((float)this.getChatWidth() / this.getChatScale()) && k2 < this.mc.fontRendererObj.FONT_HEIGHT * l2 + l2) {
                int i1 = k2 / this.mc.fontRendererObj.FONT_HEIGHT + this.scrollPos;
                if (i1 >= 0 && i1 < this.field_146253_i.size()) {
                    ChatLine chatline = this.field_146253_i.get(i1);
                    int j1 = 0;
                    for (IChatComponent ichatcomponent : chatline.getChatComponent()) {
                        if (!(ichatcomponent instanceof ChatComponentText)) continue;
                        SmoothChat smoothChat = (SmoothChat)Corrosion.INSTANCE.getModuleManager().getModule(SmoothChat.class);
                        String text = GuiUtilRenderComponents.func_178909_a(((ChatComponentText)ichatcomponent).getChatComponentText_TextValue(), false);
                        j1 = smoothChat.isEnabled() ? (int)((float)j1 + smoothChat.buildFontRenderer().getWidth(text) * 2.0f) : (j1 += this.mc.fontRendererObj.getStringWidth(text));
                        if (j1 <= j2) continue;
                        return ichatcomponent;
                    }
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public boolean getChatOpen() {
        return this.mc.currentScreen instanceof GuiChat;
    }

    public void deleteChatLine(int p_146242_1_) {
        Iterator<ChatLine> iterator = this.field_146253_i.iterator();
        while (iterator.hasNext()) {
            ChatLine chatline = iterator.next();
            if (chatline.getChatLineID() != p_146242_1_) continue;
            iterator.remove();
        }
        iterator = this.chatLines.iterator();
        while (iterator.hasNext()) {
            ChatLine chatline1 = iterator.next();
            if (chatline1.getChatLineID() != p_146242_1_) continue;
            iterator.remove();
            break;
        }
    }

    public int getChatWidth() {
        return GuiNewChat.calculateChatboxWidth(this.mc.gameSettings.chatWidth);
    }

    public int getChatHeight() {
        return GuiNewChat.calculateChatboxHeight(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
    }

    public float getChatScale() {
        return this.mc.gameSettings.chatScale;
    }

    public static int calculateChatboxWidth(float p_146233_0_) {
        int i2 = 320;
        int j2 = 40;
        return MathHelper.floor_float(p_146233_0_ * (float)(i2 - j2) + (float)j2);
    }

    public static int calculateChatboxHeight(float p_146243_0_) {
        int i2 = 180;
        int j2 = 20;
        return MathHelper.floor_float(p_146243_0_ * (float)(i2 - j2) + (float)j2);
    }

    public int getLineCount() {
        return this.getChatHeight() / 9;
    }
}

