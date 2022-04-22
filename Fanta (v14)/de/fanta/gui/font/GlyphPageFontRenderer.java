/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  optifine.Config
 *  optifine.CustomColors
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.gui.font;

import de.fanta.gui.font.BasicFontRenderer;
import de.fanta.gui.font.GlyphPage;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import optifine.Config;
import optifine.CustomColors;
import org.lwjgl.opengl.GL11;

public class GlyphPageFontRenderer
implements BasicFontRenderer {
    public Random fontRandom = new Random();
    private float posX;
    private float posY;
    private int[] colorCode = new int[32];
    private float red;
    private float blue;
    private float green;
    private float alpha;
    private int textColor;
    private boolean randomStyle;
    private boolean boldStyle;
    private boolean italicStyle;
    private boolean underlineStyle;
    private boolean strikethroughStyle;
    private GlyphPage regularGlyphPage;
    private GlyphPage boldGlyphPage;
    private GlyphPage italicGlyphPage;
    private GlyphPage boldItalicGlyphPage;

    public GlyphPageFontRenderer(GlyphPage regularGlyphPage, GlyphPage boldGlyphPage, GlyphPage italicGlyphPage, GlyphPage boldItalicGlyphPage) {
        this.regularGlyphPage = regularGlyphPage;
        this.boldGlyphPage = boldGlyphPage;
        this.italicGlyphPage = italicGlyphPage;
        this.boldItalicGlyphPage = boldItalicGlyphPage;
        int i = 0;
        while (i < 32) {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i1 = (i & 1) * 170 + j;
            if (i == 6) {
                k += 85;
            }
            if (i >= 16) {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }
            this.colorCode[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF;
            ++i;
        }
    }

    public static GlyphPageFontRenderer create(String fontName, int size, boolean bold, boolean italic, boolean boldItalic) {
        char[] chars = new char[256];
        int i = 0;
        while (i < chars.length) {
            chars[i] = (char)i;
            ++i;
        }
        GlyphPage regularPage = new GlyphPage(new Font(fontName, 0, size), true, true);
        regularPage.generateGlyphPage(chars);
        regularPage.setupTexture();
        GlyphPage boldPage = regularPage;
        GlyphPage italicPage = regularPage;
        GlyphPage boldItalicPage = regularPage;
        if (bold) {
            boldPage = new GlyphPage(new Font(fontName, 1, size), true, true);
            boldPage.generateGlyphPage(chars);
            boldPage.setupTexture();
        }
        if (italic) {
            italicPage = new GlyphPage(new Font(fontName, 2, size), true, true);
            italicPage.generateGlyphPage(chars);
            italicPage.setupTexture();
        }
        if (boldItalic) {
            boldItalicPage = new GlyphPage(new Font(fontName, 3, size), true, true);
            boldItalicPage.generateGlyphPage(chars);
            boldItalicPage.setupTexture();
        }
        return new GlyphPageFontRenderer(regularPage, boldPage, italicPage, boldItalicPage);
    }

    @Override
    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
        int i;
        GlStateManager.enableAlpha();
        this.resetStyles();
        if (dropShadow) {
            i = this.renderString(text, x + 1.0f, y + 1.0f, color, true);
            i = Math.max(i, this.renderString(text, x, y, color, false));
        } else {
            i = this.renderString(text, x, y, color, false);
        }
        return i;
    }

    @Override
    public int drawStringWithShadow(String text, float x, float y, int color) {
        return this.drawString(text, x, y, color, true);
    }

    @Override
    public int drawString(String text, float x, float y, int color) {
        return this.drawString(text, x, y, color, false);
    }

    @Override
    public int drawCenteredString(String text, float x, float y, int color, boolean dropShadow) {
        return this.drawString(text, x - (float)(this.getStringWidth(text) / 2), y, color, dropShadow);
    }

    private int renderString(String text, float x, float y, int color, boolean dropShadow) {
        if (text == null) {
            return 0;
        }
        if ((color & 0xFC000000) == 0) {
            color |= 0xFF000000;
        }
        if (dropShadow) {
            color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
        }
        this.red = (float)(color >> 16 & 0xFF) / 255.0f;
        this.blue = (float)(color >> 8 & 0xFF) / 255.0f;
        this.green = (float)(color & 0xFF) / 255.0f;
        this.alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        GlStateManager.color(this.red, this.blue, this.green, this.alpha);
        this.posX = x * 2.0f;
        this.posY = y * 2.0f;
        this.renderStringAtPos(text, dropShadow);
        return (int)(this.posX / 2.0f);
    }

    private void renderStringAtPos(String text, boolean shadow) {
        GlyphPage glyphPage = this.getCurrentGlyphPage();
        GL11.glPushMatrix();
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.enableTexture2D();
        glyphPage.bindTexture();
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        int i = 0;
        while (i < text.length()) {
            char c0 = text.charAt(i);
            if (c0 == '\u00a7' && i + 1 < text.length()) {
                int i1 = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
                if (i1 < 16) {
                    int j1;
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    if (i1 < 0) {
                        i1 = 15;
                    }
                    if (shadow) {
                        i1 += 16;
                    }
                    this.textColor = j1 = this.colorCode[i1];
                    GlStateManager.color((float)(j1 >> 16) / 255.0f, (float)(j1 >> 8 & 0xFF) / 255.0f, (float)(j1 & 0xFF) / 255.0f, this.alpha);
                } else if (i1 == 16) {
                    this.randomStyle = true;
                } else if (i1 == 17) {
                    this.boldStyle = true;
                } else if (i1 == 18) {
                    this.strikethroughStyle = true;
                } else if (i1 == 19) {
                    this.underlineStyle = true;
                } else if (i1 == 20) {
                    this.italicStyle = true;
                } else {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    GlStateManager.color(this.red, this.blue, this.green, this.alpha);
                }
                ++i;
            } else {
                glyphPage = this.getCurrentGlyphPage();
                glyphPage.bindTexture();
                float f = glyphPage.drawChar(c0, this.posX, this.posY);
                this.doDraw(f, glyphPage);
            }
            ++i;
        }
        glyphPage.unbindTexture();
        GL11.glPopMatrix();
    }

    private void doDraw(float f, GlyphPage glyphPage) {
        if (this.strikethroughStyle) {
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION);
            worldrenderer.pos(this.posX, this.posY + (float)(glyphPage.getMaxFontHeight() / 2), 0.0).endVertex();
            worldrenderer.pos(this.posX + f, this.posY + (float)(glyphPage.getMaxFontHeight() / 2), 0.0).endVertex();
            worldrenderer.pos(this.posX + f, this.posY + (float)(glyphPage.getMaxFontHeight() / 2) - 1.0f, 0.0).endVertex();
            worldrenderer.pos(this.posX, this.posY + (float)(glyphPage.getMaxFontHeight() / 2) - 1.0f, 0.0).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
        }
        if (this.underlineStyle) {
            Tessellator tessellator1 = Tessellator.getInstance();
            WorldRenderer worldrenderer1 = tessellator1.getWorldRenderer();
            GlStateManager.disableTexture2D();
            worldrenderer1.begin(7, DefaultVertexFormats.POSITION);
            int l = this.underlineStyle ? -1 : 0;
            worldrenderer1.pos(this.posX + (float)l, this.posY + (float)glyphPage.getMaxFontHeight(), 0.0).endVertex();
            worldrenderer1.pos(this.posX + f, this.posY + (float)glyphPage.getMaxFontHeight(), 0.0).endVertex();
            worldrenderer1.pos(this.posX + f, this.posY + (float)glyphPage.getMaxFontHeight() - 1.0f, 0.0).endVertex();
            worldrenderer1.pos(this.posX + (float)l, this.posY + (float)glyphPage.getMaxFontHeight() - 1.0f, 0.0).endVertex();
            tessellator1.draw();
            GlStateManager.enableTexture2D();
        }
        this.posX += f;
    }

    private GlyphPage getCurrentGlyphPage() {
        if (this.boldStyle && this.italicStyle) {
            return this.boldItalicGlyphPage;
        }
        if (this.boldStyle) {
            return this.boldGlyphPage;
        }
        if (this.italicStyle) {
            return this.italicGlyphPage;
        }
        return this.regularGlyphPage;
    }

    private void resetStyles() {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }

    @Override
    public int getFontHeight() {
        return this.regularGlyphPage.getMaxFontHeight() / 2;
    }

    @Override
    public int getStringWidth(String text) {
        if (text == null) {
            return 0;
        }
        int width = 0;
        String abc = "0123456789abcdefklmnor";
        int i = 0;
        while (i < abc.length()) {
            text = text.replace("\ufffd" + abc.charAt(i), "");
            ++i;
        }
        int size = text.length();
        boolean on = false;
        int i2 = 0;
        while (i2 < size) {
            char character = text.charAt(i2);
            if (character == '\ufffd') {
                on = true;
            } else if (on && character >= '0' && character <= 'r') {
                int colorIndex = "0123456789abcdefklmnor".indexOf(character);
                if (colorIndex < 16) {
                    this.boldStyle = false;
                    this.italicStyle = false;
                } else if (colorIndex == 17) {
                    this.boldStyle = true;
                } else if (colorIndex == 20) {
                    this.italicStyle = true;
                } else if (colorIndex == 21) {
                    this.boldStyle = false;
                    this.italicStyle = false;
                }
                ++i2;
                on = false;
            } else {
                if (on) {
                    --i2;
                }
                character = text.charAt(i2);
                GlyphPage currentPage = this.getCurrentGlyphPage();
                width = (int)((float)width + (currentPage.getWidth(character) - 8.0f));
            }
            ++i2;
        }
        return width / 2;
    }

    @Override
    public String trimStringToWidth(String text, int width) {
        return this.trimStringToWidth(text, width, false);
    }

    @Override
    public String trimStringToWidth(String text, int maxWidth, boolean reverse) {
        StringBuilder stringbuilder = new StringBuilder();
        boolean on = false;
        int j = reverse ? text.length() - 1 : 0;
        int k = reverse ? -1 : 1;
        int width = 0;
        int i = j;
        while (i >= 0 && i < text.length() && i < maxWidth) {
            char character = text.charAt(i);
            if (character == '\ufffd') {
                on = true;
            } else if (on && character >= '0' && character <= 'r') {
                int colorIndex = "0123456789abcdefklmnor".indexOf(character);
                if (colorIndex < 16) {
                    this.boldStyle = false;
                    this.italicStyle = false;
                } else if (colorIndex == 17) {
                    this.boldStyle = true;
                } else if (colorIndex == 20) {
                    this.italicStyle = true;
                } else if (colorIndex == 21) {
                    this.boldStyle = false;
                    this.italicStyle = false;
                }
                ++i;
                on = false;
            } else {
                if (on) {
                    --i;
                }
                character = text.charAt(i);
                GlyphPage currentPage = this.getCurrentGlyphPage();
                width = (int)((float)width + (currentPage.getWidth(character) - 8.0f) / 2.0f);
            }
            if (i > width) break;
            if (reverse) {
                stringbuilder.insert(0, character);
            } else {
                stringbuilder.append(character);
            }
            i += k;
        }
        return stringbuilder.toString();
    }

    @Override
    public int getCharWidth(char c) {
        return this.getStringWidth("" + c);
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
    }

    @Override
    public void setUnicodeFlag(boolean state) {
    }

    @Override
    public void setBidiFlag(boolean state) {
    }

    @Override
    public boolean getBidiFlag() {
        return false;
    }

    @Override
    public String wrapFormattedStringToWidth(String str, int wrapWidth) {
        int i = this.sizeStringToWidth(str, wrapWidth);
        if (str.length() <= i) {
            return str;
        }
        String s = str.substring(0, i);
        char c0 = str.charAt(i);
        boolean flag = c0 == ' ' || c0 == '\n';
        String s1 = String.valueOf(GlyphPageFontRenderer.getFormatFromString(s)) + str.substring(i + (flag ? 1 : 0));
        return s;
    }

    public static String getFormatFromString(String text) {
        String s = "";
        int i = -1;
        int j = text.length();
        while ((i = text.indexOf(167, i + 1)) != -1) {
            if (i >= j - 1) continue;
            char c0 = text.charAt(i + 1);
            if (GlyphPageFontRenderer.isFormatColor(c0)) {
                s = "\u00a7" + c0;
                continue;
            }
            if (!GlyphPageFontRenderer.isFormatSpecial(c0)) continue;
            s = String.valueOf(s) + "\u00a7" + c0;
        }
        return s;
    }

    private int sizeStringToWidth(String str, int wrapWidth) {
        int i = str.length();
        float f = 0.0f;
        int j = 0;
        int k = -1;
        boolean flag = false;
        while (j < i) {
            char c0 = str.charAt(j);
            switch (c0) {
                case '\n': {
                    --j;
                    break;
                }
                case ' ': {
                    k = j;
                }
                default: {
                    f += this.getCharWidthFloat(c0);
                    if (!flag) break;
                    f += 1.0f;
                    break;
                }
                case '\u00a7': {
                    char c1;
                    if (j >= i - 1) break;
                    if ((c1 = str.charAt(++j)) != 'l' && c1 != 'L') {
                        if (c1 != 'r' && c1 != 'R' && !GlyphPageFontRenderer.isFormatColor(c1)) break;
                        flag = false;
                        break;
                    }
                    flag = true;
                }
            }
            if (c0 == '\n') {
                k = ++j;
                break;
            }
            if (f > (float)wrapWidth) break;
            ++j;
        }
        return j != i && k != -1 && k < j ? k : j;
    }

    private static boolean isFormatColor(char colorChar) {
        return colorChar >= '0' && colorChar <= '9' || colorChar >= 'a' && colorChar <= 'f' || colorChar >= 'A' && colorChar <= 'F';
    }

    private static boolean isFormatSpecial(char formatChar) {
        return formatChar >= 'k' && formatChar <= 'o' || formatChar >= 'K' && formatChar <= 'O' || formatChar == 'r' || formatChar == 'R';
    }

    @Override
    public List listFormattedStringToWidth(String str, int wrapWidth) {
        return Arrays.asList(this.wrapFormattedStringToWidth(str, wrapWidth).split("\n"));
    }

    @Override
    public int getColorCode(char character) {
        int i = "0123456789abcdef".indexOf(character);
        if (i >= 0 && i < this.colorCode.length) {
            int j = this.colorCode[i];
            if (Config.isCustomColors()) {
                j = CustomColors.getTextColor((int)i, (int)j);
            }
            return j;
        }
        return 0xFFFFFF;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean setEnabled(boolean state) {
        return true;
    }

    @Override
    public void setFontRandomSeed(long seed) {
        this.fontRandom.setSeed(seed);
    }

    @Override
    public void drawSplitString(String str, int x, int y, int wrapWidth, int textColor) {
        this.resetStyles();
        this.textColor = textColor;
        str = this.trimStringNewline(str);
        this.renderSplitString(str, x, y, wrapWidth, false);
    }

    @Override
    public int splitStringWidth(String p_78267_1_, int p_78267_2_) {
        return this.getFontHeight() * this.listFormattedStringToWidth(p_78267_1_, p_78267_2_).size();
    }

    @Override
    public boolean getUnicodeFlag() {
        return false;
    }

    private float getCharWidthFloat(char p_getCharWidthFloat_1_) {
        return this.getCharWidth(p_getCharWidthFloat_1_);
    }

    private int renderStringAligned(String text, int x, int y, int p_78274_4_, int color, boolean dropShadow) {
        return this.renderString(text, x, y, color, dropShadow);
    }

    private void renderSplitString(String str, int x, int y, int wrapWidth, boolean addShadow) {
        for (Object s : this.listFormattedStringToWidth(str, wrapWidth)) {
            this.renderStringAligned((String)s, x, y, wrapWidth, this.textColor, addShadow);
            y += this.getFontHeight();
        }
    }

    private String trimStringNewline(String text) {
        while (text != null && text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }
}

