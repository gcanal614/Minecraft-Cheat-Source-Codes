/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  org.lwjgl.opengl.GL11
 *  org.newdawn.slick.Color
 *  org.newdawn.slick.UnicodeFont
 *  org.newdawn.slick.font.effects.ColorEffect
 */
package de.fanta.utils;

import com.mojang.authlib.GameProfile;
import de.fanta.Client;
import de.liquiddev.ircclient.client.IrcPlayer;
import de.liquiddev.ircclient.client.IrcRank;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class UnicodeFontRenderer4 {
    public static UnicodeFontRenderer4 instance;
    public final int FONT_HEIGHT = 9;
    private final int[] colorCodes = new int[32];
    private final float kerning;
    private final Map<String, Float> cachedStringWidth = new HashMap<String, Float>();
    private final float antiAliasingFactor;
    private UnicodeFont unicodeFont;

    private UnicodeFontRenderer4(String fontName, int fontType, float fontSize, float kerning, float antiAliasingFactor) {
        this.antiAliasingFactor = antiAliasingFactor;
        try {
            this.unicodeFont = new UnicodeFont(this.getFontByName(fontName).deriveFont(fontSize * this.antiAliasingFactor));
        }
        catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        this.kerning = kerning;
        this.unicodeFont.addAsciiGlyphs();
        this.unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        try {
            this.unicodeFont.loadGlyphs();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        int i = 0;
        while (i < 32) {
            int shadow = (i >> 3 & 1) * 85;
            int red = (i >> 2 & 1) * 170 + shadow;
            int green = (i >> 1 & 1) * 170 + shadow;
            int blue = (i & 1) * 170 + shadow;
            if (i == 6) {
                red += 85;
            }
            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCodes[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
            ++i;
        }
    }

    private UnicodeFontRenderer4(Font font, float kerning, float antiAliasingFactor) {
        this.antiAliasingFactor = antiAliasingFactor;
        this.unicodeFont = new UnicodeFont(new Font(font.getName(), font.getStyle(), (int)((float)font.getSize() * antiAliasingFactor)));
        this.kerning = kerning;
        this.unicodeFont.addAsciiGlyphs();
        this.unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        try {
            this.unicodeFont.loadGlyphs();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        int i = 0;
        while (i < 32) {
            int shadow = (i >> 3 & 1) * 85;
            int red = (i >> 2 & 1) * 170 + shadow;
            int green = (i >> 1 & 1) * 170 + shadow;
            int blue = (i & 1) * 170 + shadow;
            if (i == 6) {
                red += 85;
            }
            if (i >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCodes[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
            ++i;
        }
    }

    public static UnicodeFontRenderer4 getFontOnPC(String name, int size) {
        return UnicodeFontRenderer4.getFontOnPC(name, size, 0);
    }

    public static UnicodeFontRenderer4 getFontOnPC(String name, int size, int fontType) {
        return UnicodeFontRenderer4.getFontOnPC(name, size, fontType, 0.0f);
    }

    public static UnicodeFontRenderer4 getFontOnPC(String name, int size, int fontType, float kerning) {
        return UnicodeFontRenderer4.getFontOnPC(name, size, fontType, kerning, 3.0f);
    }

    public static UnicodeFontRenderer4 getFontOnPC(String name, int size, int fontType, float kerning, float antiAliasingFactor) {
        return new UnicodeFontRenderer4(new Font(name, fontType, size), kerning, antiAliasingFactor);
    }

    public static UnicodeFontRenderer4 getFontFromAssets(String name, int size) {
        return UnicodeFontRenderer4.getFontOnPC(name, size, 0);
    }

    public static UnicodeFontRenderer4 getFontFromAssets(String name, int size, int fontType) {
        return UnicodeFontRenderer4.getFontOnPC(name, fontType, size, 0.0f);
    }

    public static UnicodeFontRenderer4 getFontFromAssets(String name, int size, float kerning, int fontType) {
        return UnicodeFontRenderer4.getFontFromAssets(name, size, fontType, kerning, 3.0f);
    }

    public static UnicodeFontRenderer4 getFontFromAssets(String name, int size, int fontType, float kerning, float antiAliasingFactor) {
        return new UnicodeFontRenderer4(name, fontType, size, kerning, antiAliasingFactor);
    }

    private Font getFontByName(String name) throws IOException, FontFormatException {
        return this.getFontFromInput("/assets/minecraft/Fanta/fonts/" + name + ".ttf");
    }

    private Font getFontFromInput(String path) throws IOException, FontFormatException {
        return Font.createFont(0, Client.class.getResourceAsStream(path));
    }

    public void drawStringScaled(String text, int givenX, int givenY, int color, double givenScale) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)givenX, (double)givenY, (double)0.0);
        GL11.glScaled((double)givenScale, (double)givenScale, (double)givenScale);
        this.drawString(text, 0.0f, 0.0f, color);
        GL11.glPopMatrix();
    }

    public static String getIrcPrefix(String name, GameProfile pr) {
        try {
            IrcPlayer ircPlayer = IrcPlayer.getByIngameName(pr.getName());
            if (ircPlayer != null) {
                String client = ircPlayer.getClientName();
                if (ircPlayer.getRank() == IrcRank.ADMIN || ircPlayer.getRank() == IrcRank.DEV) {
                    return "\u00a7C[" + client + "-Dev]";
                }
                if (ircPlayer.getRank() == IrcRank.MOD) {
                    return "[" + client + "-Mod]";
                }
                if (ircPlayer.getRank() == IrcRank.VIP) {
                    return "\u00a76[" + client + "-Vip]";
                }
                return "[" + client + "-User]";
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return "";
    }

    public int drawString(String text, float x, float y, int color) {
        if (text == null) {
            return 0;
        }
        y *= 2.0f;
        float originalX = x *= 2.0f;
        GL11.glPushMatrix();
        GlStateManager.scale(1.0f / this.antiAliasingFactor, 1.0f / this.antiAliasingFactor, 1.0f / this.antiAliasingFactor);
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        x *= this.antiAliasingFactor;
        y *= this.antiAliasingFactor;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
        boolean blend = GL11.glIsEnabled((int)3042);
        boolean lighting = GL11.glIsEnabled((int)2896);
        boolean texture = GL11.glIsEnabled((int)3553);
        boolean alphaTest = GL11.glIsEnabled((int)3008);
        if (!blend) {
            GL11.glEnable((int)3042);
        }
        if (lighting) {
            GL11.glDisable((int)2896);
        }
        if (texture) {
            GL11.glDisable((int)3553);
        }
        if (alphaTest) {
            GL11.glEnable((int)3008);
        }
        int currentColor = color;
        char[] characters = text.toCharArray();
        int index = 0;
        char[] cArray = characters;
        int n = characters.length;
        int n2 = 0;
        while (n2 < n) {
            block18: {
                block16: {
                    char c;
                    block17: {
                        block15: {
                            c = cArray[n2];
                            if (c == '\r') {
                                x = originalX;
                            }
                            if (c == '\n') {
                                y += this.getHeight(Character.toString(c)) * 2.0f;
                            }
                            if (c == '\u00a7' || index != 0 && index != characters.length - 1 && characters[index - 1] == '\u00a7') break block15;
                            this.unicodeFont.drawString(x, y, Character.toString(c), new Color(currentColor));
                            x += this.getWidth(Character.toString(c)) * 2.0f * this.antiAliasingFactor;
                            break block16;
                        }
                        if (c != ' ') break block17;
                        x += (float)this.unicodeFont.getSpaceWidth();
                        break block16;
                    }
                    if (c != '\u00a7' || index == characters.length - 1) break block16;
                    int codeIndex = "0123456789abcdefg".indexOf(text.charAt(index + 1));
                    if (codeIndex < 0) break block18;
                    currentColor = this.colorCodes[codeIndex];
                }
                ++index;
            }
            ++n2;
        }
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
        if (texture) {
            GL11.glEnable((int)3553);
        }
        if (lighting) {
            GL11.glEnable((int)2896);
        }
        if (!blend) {
            GL11.glDisable((int)3042);
        }
        if (alphaTest) {
            GL11.glEnable((int)3008);
        }
        GlStateManager.bindTexture(0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        return (int)x / 2;
    }

    public int drawStringWithShadow(String text, float x, float y, int color) {
        this.drawString(StringUtils.stripControlCodes(text), x + 0.4f, y + 0.4f, 0);
        return this.drawString(text, x, y, color);
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        this.drawString(text, x - (float)((int)this.getWidth(text) / 2), y, color);
    }

    public void drawCenteredTextScaled(String text, int givenX, int givenY, int color, double givenScale) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)givenX, (double)givenY, (double)0.0);
        GL11.glScaled((double)givenScale, (double)givenScale, (double)givenScale);
        this.drawCenteredString(text, 0.0f, 0.0f, color);
        GL11.glPopMatrix();
    }

    public void drawCenteredStringWithShadow(String text, float x, float y, int color) {
        this.drawCenteredString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, color);
        this.drawCenteredString(text, x, y, color);
    }

    public float getWidth(String s) {
        if (this.cachedStringWidth.size() > 1000) {
            this.cachedStringWidth.clear();
        }
        return this.cachedStringWidth.computeIfAbsent(s, e -> {
            float width = 0.0f;
            String str = StringUtils.stripControlCodes(s);
            char[] cArray = str.toCharArray();
            int n = cArray.length;
            int n2 = 0;
            while (n2 < n) {
                char c = cArray[n2];
                width += (float)this.unicodeFont.getWidth(Character.toString(c)) + this.kerning;
                ++n2;
            }
            return Float.valueOf(width / 2.0f / this.antiAliasingFactor);
        }).floatValue();
    }

    public int getStringWidth(String text) {
        if (EnumChatFormatting.getTextWithoutFormattingCodes(text) == null) {
            return 0;
        }
        int i = 0;
        boolean flag = false;
        int j = 0;
        while (j < EnumChatFormatting.getTextWithoutFormattingCodes(text).length()) {
            char c0 = EnumChatFormatting.getTextWithoutFormattingCodes(text).charAt(j);
            float k = this.getWidth(EnumChatFormatting.getTextWithoutFormattingCodes(String.valueOf(c0)));
            if (k < 0.0f && j < EnumChatFormatting.getTextWithoutFormattingCodes(text).length() - 1) {
                c0 = EnumChatFormatting.getTextWithoutFormattingCodes(text).charAt(++j);
                if (c0 != 'l' && c0 != 'L') {
                    if (c0 == 'r' || c0 == 'R') {
                        flag = false;
                    }
                } else {
                    flag = true;
                }
                k = 0.0f;
            }
            i = (int)((float)i + k);
            if (flag && k > 0.0f) {
                ++i;
            }
            ++j;
        }
        return i;
    }

    public float getCharWidth(char c) {
        return this.unicodeFont.getWidth(String.valueOf(c));
    }

    public float getHeight(String s) {
        return (float)this.unicodeFont.getHeight(s) / 2.0f;
    }

    public UnicodeFont getFont() {
        return this.unicodeFont;
    }

    public String trimStringToWidth(String par1Str, int par2) {
        StringBuilder builder = new StringBuilder();
        float var5 = 0.0f;
        int var6 = 0;
        int var7 = 1;
        boolean var8 = false;
        boolean var9 = false;
        int var10 = var6;
        while (var10 >= 0 && var10 < par1Str.length() && var5 < (float)par2) {
            char var11 = par1Str.charAt(var10);
            float var12 = this.getCharWidth(var11);
            if (var8) {
                var8 = false;
                if (var11 != 'l' && var11 != 'L') {
                    if (var11 == 'r' || var11 == 'R') {
                        var9 = false;
                    }
                } else {
                    var9 = true;
                }
            } else if (var12 < 0.0f) {
                var8 = true;
            } else {
                var5 += var12;
                if (var9) {
                    var5 += 1.0f;
                }
            }
            if (var5 > (float)par2) break;
            builder.append(var11);
            var10 += var7;
        }
        return builder.toString();
    }

    public void drawSplitString(ArrayList<String> lines, int x, int y, int color) {
        this.drawString(String.join((CharSequence)"\n\r", lines), x, y, color);
    }

    public List<String> splitString(String text, int wrapWidth) {
        ArrayList<String> lines = new ArrayList<String>();
        String[] splitText = text.split(" ");
        StringBuilder currentString = new StringBuilder();
        String[] stringArray = splitText;
        int n = splitText.length;
        int n2 = 0;
        while (n2 < n) {
            String word = stringArray[n2];
            String potential = currentString + " " + word;
            if (this.getWidth(potential) >= (float)wrapWidth) {
                lines.add(currentString.toString());
                currentString = new StringBuilder();
            }
            currentString.append(word).append(" ");
            ++n2;
        }
        lines.add(currentString.toString());
        return lines;
    }

    public static UnicodeFontRenderer4 getInstance() {
        return instance;
    }
}

