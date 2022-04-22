/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 */
package de.fanta.gui.flux;

import com.google.common.base.Predicate;
import de.fanta.gui.font.BasicFontRenderer;
import de.fanta.gui.font.ClientFont;
import de.fanta.gui.font.GlyphPageFontRenderer;
import de.fanta.utils.RenderUtil;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.MathHelper;

public class GuiTextFieldFlux
extends GuiTextField {
    private String info;
    private GlyphPageFontRenderer fluxFont;
    private GlyphPageFontRenderer fluxFont2;

    public GuiTextFieldFlux(int componentId, BasicFontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height, String info) {
        super(componentId, fontrendererObj, x, y, par5Width, par6Height);
        this.info = info;
        this.fluxFont = ClientFont.font(17, "fluxfont", true);
        this.fluxFont2 = ClientFont.font(12, "fluxfont", true);
    }

    public void func_175207_a(GuiPageButtonList.GuiResponder p_175207_1_) {
        this.guiResponder = p_175207_1_;
    }

    @Override
    public void updateCursorCounter() {
        ++this.cursorCounter;
    }

    @Override
    public void setText(String p_146180_1_) {
        if (this.validator.apply((Object)p_146180_1_)) {
            this.text = p_146180_1_.length() > this.maxStringLength ? p_146180_1_.substring(0, this.maxStringLength) : p_146180_1_;
            this.setCursorPositionEnd();
        }
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String getSelectedText() {
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        return this.text.substring(i, j);
    }

    @Override
    public void func_175205_a(Predicate<String> p_175205_1_) {
        this.validator = p_175205_1_;
    }

    @Override
    public void writeText(String p_146191_1_) {
        String s = "";
        String s1 = ChatAllowedCharacters.filterAllowedCharacters(p_146191_1_);
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        int k = this.maxStringLength - this.text.length() - (i - j);
        int l = 0;
        if (this.text.length() > 0) {
            s = String.valueOf(s) + this.text.substring(0, i);
        }
        if (k < s1.length()) {
            s = String.valueOf(s) + s1.substring(0, k);
            l = k;
        } else {
            s = String.valueOf(s) + s1;
            l = s1.length();
        }
        if (this.text.length() > 0 && j < this.text.length()) {
            s = String.valueOf(s) + this.text.substring(j);
        }
        if (this.validator.apply((Object)s)) {
            this.text = s;
            this.moveCursorBy(i - this.selectionEnd + l);
            if (this.guiResponder != null) {
                this.guiResponder.func_175319_a(this.id, this.text);
            }
        }
    }

    @Override
    public void deleteWords(int p_146177_1_) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                this.deleteFromCursor(this.getNthWordFromCursor(p_146177_1_) - this.cursorPosition);
            }
        }
    }

    @Override
    public void deleteFromCursor(int p_146175_1_) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                boolean flag = p_146175_1_ < 0;
                int i = flag ? this.cursorPosition + p_146175_1_ : this.cursorPosition;
                int j = flag ? this.cursorPosition : this.cursorPosition + p_146175_1_;
                String s = "";
                if (i >= 0) {
                    s = this.text.substring(0, i);
                }
                if (j < this.text.length()) {
                    s = String.valueOf(s) + this.text.substring(j);
                }
                if (this.validator.apply((Object)s)) {
                    this.text = s;
                    if (flag) {
                        this.moveCursorBy(p_146175_1_);
                    }
                    if (this.guiResponder != null) {
                        this.guiResponder.func_175319_a(this.id, this.text);
                    }
                }
            }
        }
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getNthWordFromCursor(int p_146187_1_) {
        return this.getNthWordFromPos(p_146187_1_, this.getCursorPosition());
    }

    @Override
    public int getNthWordFromPos(int p_146183_1_, int p_146183_2_) {
        return this.func_146197_a(p_146183_1_, p_146183_2_, true);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public int func_146197_a(int p_146197_1_, int p_146197_2_, boolean p_146197_3_) {
        i = p_146197_2_;
        flag = p_146197_1_ < 0;
        j = Math.abs(p_146197_1_);
        k = 0;
        while (k < j) {
            block4: {
                if (flag) ** GOTO lbl15
                l = this.text.length();
                if ((i = this.text.indexOf(32, i)) != -1) ** GOTO lbl12
                i = l;
                break block4;
lbl-1000:
                // 1 sources

                {
                    ++i;
lbl12:
                    // 2 sources

                    ** while (p_146197_3_ && i < l && this.text.charAt((int)i) == ' ')
                }
lbl13:
                // 1 sources

                break block4;
lbl-1000:
                // 1 sources

                {
                    --i;
lbl15:
                    // 2 sources

                    ** while (p_146197_3_ && i > 0 && this.text.charAt((int)(i - 1)) == ' ')
                }
lbl16:
                // 2 sources

                while (i > 0 && this.text.charAt(i - 1) != ' ') {
                    --i;
                }
            }
            ++k;
        }
        return i;
    }

    @Override
    public void moveCursorBy(int p_146182_1_) {
        this.setCursorPosition(this.selectionEnd + p_146182_1_);
    }

    @Override
    public void setCursorPosition(int p_146190_1_) {
        this.cursorPosition = p_146190_1_;
        int i = this.text.length();
        this.cursorPosition = MathHelper.clamp_int(this.cursorPosition, 0, i);
        this.setSelectionPos(this.cursorPosition);
    }

    @Override
    public void setCursorPositionZero() {
        this.setCursorPosition(0);
    }

    @Override
    public void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }

    @Override
    public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
        if (!this.isFocused) {
            return false;
        }
        if (GuiScreen.isKeyComboCtrlA(p_146201_2_)) {
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
            return true;
        }
        if (GuiScreen.isKeyComboCtrlC(p_146201_2_)) {
            GuiScreen.setClipboardString(this.getSelectedText());
            return true;
        }
        if (GuiScreen.isKeyComboCtrlV(p_146201_2_)) {
            if (this.isEnabled) {
                this.writeText(GuiScreen.getClipboardString());
            }
            return true;
        }
        if (GuiScreen.isKeyComboCtrlX(p_146201_2_)) {
            GuiScreen.setClipboardString(this.getSelectedText());
            if (this.isEnabled) {
                this.writeText("");
            }
            return true;
        }
        switch (p_146201_2_) {
            case 14: {
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.isEnabled) {
                        this.deleteWords(-1);
                    }
                } else if (this.isEnabled) {
                    this.deleteFromCursor(-1);
                }
                return true;
            }
            case 199: {
                if (GuiScreen.isShiftKeyDown()) {
                    this.setSelectionPos(0);
                } else {
                    this.setCursorPositionZero();
                }
                return true;
            }
            case 203: {
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
                    } else {
                        this.setSelectionPos(this.getSelectionEnd() - 1);
                    }
                } else if (GuiScreen.isCtrlKeyDown()) {
                    this.setCursorPosition(this.getNthWordFromCursor(-1));
                } else {
                    this.moveCursorBy(-1);
                }
                return true;
            }
            case 205: {
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
                    } else {
                        this.setSelectionPos(this.getSelectionEnd() + 1);
                    }
                } else if (GuiScreen.isCtrlKeyDown()) {
                    this.setCursorPosition(this.getNthWordFromCursor(1));
                } else {
                    this.moveCursorBy(1);
                }
                return true;
            }
            case 207: {
                if (GuiScreen.isShiftKeyDown()) {
                    this.setSelectionPos(this.text.length());
                } else {
                    this.setCursorPositionEnd();
                }
                return true;
            }
            case 211: {
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.isEnabled) {
                        this.deleteWords(1);
                    }
                } else if (this.isEnabled) {
                    this.deleteFromCursor(1);
                }
                return true;
            }
        }
        if (ChatAllowedCharacters.isAllowedCharacter(p_146201_1_)) {
            if (this.isEnabled) {
                this.writeText(Character.toString(p_146201_1_));
            }
            return true;
        }
        return false;
    }

    @Override
    public void mouseClicked(int p_146192_1_, int p_146192_2_, int p_146192_3_) {
        boolean flag;
        boolean bl = flag = p_146192_1_ >= this.xPosition && p_146192_1_ < this.xPosition + this.width && p_146192_2_ >= this.yPosition && p_146192_2_ < this.yPosition + this.height;
        if (this.canLoseFocus) {
            this.setFocused(flag);
        }
        if (this.isFocused && flag && p_146192_3_ == 0) {
            int i = p_146192_1_ - this.xPosition;
            if (this.enableBackgroundDrawing) {
                i -= 4;
            }
            String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            this.setCursorPosition(this.fontRendererInstance.trimStringToWidth(s, i).length() + this.lineScrollOffset);
        }
    }

    @Override
    public void drawTextBox() {
        if (this.getVisible()) {
            if (!this.isFocused && this.getText().isEmpty()) {
                RenderUtil.drawRoundedRect(this.xPosition, this.yPosition, this.width, this.height, 6, Color.decode("#DDDDDD").getRGB());
                RenderUtil.drawRoundedRect(this.xPosition + 1, this.yPosition + 1, this.width - 2, this.height - 2, 6, Color.WHITE.getRGB());
                this.fluxFont.drawString(this.info, this.xPosition + 5, this.yPosition + this.height / 2 - this.fluxFont.getFontHeight() / 2, Color.decode("#DDDDDD").getRGB());
            } else {
                RenderUtil.drawRoundedRect(this.xPosition, this.yPosition, this.width, this.height, 6, Color.decode("#7D7D7D").getRGB());
                RenderUtil.drawRoundedRect(this.xPosition + 1, this.yPosition + 1, this.width - 2, this.height - 2, 6, Color.WHITE.getRGB());
                int width = this.fluxFont2.getStringWidth(this.info) + 4;
                GuiTextFieldFlux.drawRect(this.xPosition + 7, this.yPosition, this.xPosition + 7 + width, this.yPosition + 1, Color.white.getRGB());
                this.fluxFont2.drawCenteredString(this.info, this.xPosition + 7 + width / 2, this.yPosition - this.fluxFont2.getFontHeight() / 2, Color.decode("#7D7D7D").getRGB(), false);
            }
            int i = this.isEnabled ? this.enabledColor : this.disabledColor;
            int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;
            String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            boolean flag = j >= 0 && j <= s.length();
            boolean flag1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag;
            int l = this.enableBackgroundDrawing ? this.xPosition + 4 : this.xPosition;
            int i1 = this.enableBackgroundDrawing ? this.yPosition + (this.height - 8) / 2 : this.yPosition;
            int j1 = l;
            if (k > s.length()) {
                k = s.length();
            }
            if (s.length() > 0) {
                String s1 = flag ? s.substring(0, j) : s;
                j1 = this.fontRendererInstance.drawString(s1, l, (float)i1 + 1.0f, Color.black.getRGB());
            }
            boolean flag2 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int k1 = j1;
            if (!flag) {
                k1 = j > 0 ? l + this.width : l;
            } else if (flag2) {
                k1 = j1 - 1;
                --j1;
            }
            if (s.length() > 0 && flag && j < s.length()) {
                j1 = this.fontRendererInstance.drawString(s.substring(j), j1, (float)i1 + 1.0f, Color.black.getRGB());
            }
            if (flag1) {
                if (flag2) {
                    Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + this.fontRendererInstance.getFontHeight(), -3092272);
                } else {
                    Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + this.fontRendererInstance.getFontHeight(), -3092272);
                }
            }
            if (k != j) {
                int l1 = l + this.fontRendererInstance.getStringWidth(s.substring(0, k));
                this.drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRendererInstance.getFontHeight());
            }
        }
    }

    public void drawCursorVertical(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_) {
        if (p_146188_1_ < p_146188_3_) {
            int i = p_146188_1_;
            p_146188_1_ = p_146188_3_;
            p_146188_3_ = i;
        }
        if (p_146188_2_ < p_146188_4_) {
            int j = p_146188_2_;
            p_146188_2_ = p_146188_4_;
            p_146188_4_ = j;
        }
        if (p_146188_3_ > this.xPosition + this.width) {
            p_146188_3_ = this.xPosition + this.width;
        }
        if (p_146188_1_ > this.xPosition + this.width) {
            p_146188_1_ = this.xPosition + this.width;
        }
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.color(0.0f, 0.0f, 255.0f, 255.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(5387);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(p_146188_1_, p_146188_4_, 0.0).endVertex();
        worldrenderer.pos(p_146188_3_, p_146188_4_, 0.0).endVertex();
        worldrenderer.pos(p_146188_3_, p_146188_2_, 0.0).endVertex();
        worldrenderer.pos(p_146188_1_, p_146188_2_, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
    }

    @Override
    public void setMaxStringLength(int p_146203_1_) {
        this.maxStringLength = p_146203_1_;
        if (this.text.length() > p_146203_1_) {
            this.text = this.text.substring(0, p_146203_1_);
        }
    }

    @Override
    public int getMaxStringLength() {
        return this.maxStringLength;
    }

    @Override
    public int getCursorPosition() {
        return this.cursorPosition;
    }

    @Override
    public boolean getEnableBackgroundDrawing() {
        return this.enableBackgroundDrawing;
    }

    @Override
    public void setEnableBackgroundDrawing(boolean p_146185_1_) {
        this.enableBackgroundDrawing = p_146185_1_;
    }

    @Override
    public void setTextColor(int p_146193_1_) {
        this.enabledColor = p_146193_1_;
    }

    @Override
    public void setDisabledTextColour(int p_146204_1_) {
        this.disabledColor = p_146204_1_;
    }

    @Override
    public void setFocused(boolean p_146195_1_) {
        if (p_146195_1_ && !this.isFocused) {
            this.cursorCounter = 0;
        }
        this.isFocused = p_146195_1_;
    }

    @Override
    public boolean isFocused() {
        return this.isFocused;
    }

    @Override
    public void setEnabled(boolean p_146184_1_) {
        this.isEnabled = p_146184_1_;
    }

    @Override
    public int getSelectionEnd() {
        return this.selectionEnd;
    }

    @Override
    public int getWidth() {
        return this.getEnableBackgroundDrawing() ? this.width - 8 : this.width;
    }

    @Override
    public void setSelectionPos(int p_146199_1_) {
        int i = this.text.length();
        if (p_146199_1_ > i) {
            p_146199_1_ = i;
        }
        if (p_146199_1_ < 0) {
            p_146199_1_ = 0;
        }
        this.selectionEnd = p_146199_1_;
        if (this.fontRendererInstance != null) {
            if (this.lineScrollOffset > i) {
                this.lineScrollOffset = i;
            }
            int j = this.getWidth();
            String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), j);
            int k = s.length() + this.lineScrollOffset;
            if (p_146199_1_ == this.lineScrollOffset) {
                this.lineScrollOffset -= this.fontRendererInstance.trimStringToWidth(this.text, j, true).length();
            }
            if (p_146199_1_ > k) {
                this.lineScrollOffset += p_146199_1_ - k;
            } else if (p_146199_1_ <= this.lineScrollOffset) {
                this.lineScrollOffset -= this.lineScrollOffset - p_146199_1_;
            }
            this.lineScrollOffset = MathHelper.clamp_int(this.lineScrollOffset, 0, i);
        }
    }

    @Override
    public void setCanLoseFocus(boolean p_146205_1_) {
        this.canLoseFocus = p_146205_1_;
    }

    @Override
    public boolean getVisible() {
        return this.visible;
    }

    @Override
    public void setVisible(boolean p_146189_1_) {
        this.visible = p_146189_1_;
    }
}

