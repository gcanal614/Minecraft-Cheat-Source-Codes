package net.minecraft.client.gui;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class GuiLabel extends Gui
{
    public int field_146162_g;
    public int field_146174_h;
    public int field_175204_i;
    public boolean visible;
    protected int field_146167_a;
    protected int field_146161_f;
    private List field_146173_k;
    private boolean centered;
    private boolean labelBgEnabled;
    private int field_146168_n;
    private int field_146169_o;
    private int field_146166_p;
    private int field_146165_q;
    private FontRenderer fontRenderer;
    private int field_146163_s;
    
    public GuiLabel(final FontRenderer p_i45540_1_, final int p_i45540_2_, final int p_i45540_3_, final int p_i45540_4_, final int p_i45540_5_, final int p_i45540_6_, final int p_i45540_7_) {
        this.visible = true;
        this.field_146167_a = 200;
        this.field_146161_f = 20;
        this.fontRenderer = p_i45540_1_;
        this.field_175204_i = p_i45540_2_;
        this.field_146162_g = p_i45540_3_;
        this.field_146174_h = p_i45540_4_;
        this.field_146167_a = p_i45540_5_;
        this.field_146161_f = p_i45540_6_;
        this.field_146173_k = Lists.newArrayList();
        this.centered = false;
        this.labelBgEnabled = false;
        this.field_146168_n = p_i45540_7_;
        this.field_146169_o = -1;
        this.field_146166_p = -1;
        this.field_146165_q = -1;
        this.field_146163_s = 0;
    }
    
    public void func_175202_a(final String p_175202_1_) {
        this.field_146173_k.add(I18n.format(p_175202_1_, new Object[0]));
    }
    
    public GuiLabel func_175203_a() {
        this.centered = true;
        return this;
    }
    
    public void drawLabel(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.visible) {
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            this.drawLabelBackground(mc, mouseX, mouseY);
            final int var4 = this.field_146174_h + this.field_146161_f / 2 + this.field_146163_s / 2;
            final int var5 = var4 - this.field_146173_k.size() * 10 / 2;
            for (int var6 = 0; var6 < this.field_146173_k.size(); ++var6) {
                if (this.centered) {
                    Gui.drawCenteredString(this.fontRenderer, this.field_146173_k.get(var6), this.field_146162_g + this.field_146167_a / 2, var5 + var6 * 10, this.field_146168_n);
                }
                else {
                    this.drawString(this.fontRenderer, this.field_146173_k.get(var6), this.field_146162_g, var5 + var6 * 10, this.field_146168_n);
                }
            }
        }
    }
    
    protected void drawLabelBackground(final Minecraft mcIn, final int p_146160_2_, final int p_146160_3_) {
        if (this.labelBgEnabled) {
            final int var4 = this.field_146167_a + this.field_146163_s * 2;
            final int var5 = this.field_146161_f + this.field_146163_s * 2;
            final int var6 = this.field_146162_g - this.field_146163_s;
            final int var7 = this.field_146174_h - this.field_146163_s;
            Gui.drawRect(var6, var7, var6 + var4, var7 + var5, this.field_146169_o);
            this.drawHorizontalLine(var6, var6 + var4, var7, this.field_146166_p);
            this.drawHorizontalLine(var6, var6 + var4, var7 + var5, this.field_146165_q);
            this.drawVerticalLine(var6, var7, var7 + var5, this.field_146166_p);
            this.drawVerticalLine(var6 + var4, var7, var7 + var5, this.field_146165_q);
        }
    }
}
