/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.gui.font;

import de.fanta.gui.font.GlyphPage;
import de.fanta.gui.font.GlyphPageFontRenderer;
import java.awt.Font;

public class ClientFont {
    /*
     * Unable to fully structure code
     */
    public static GlyphPageFontRenderer font(int size, String fontName, boolean ttf) {
        try {
            istream = ClientFont.class.getResourceAsStream("/resources/" + fontName + ".ttf");
            myFont = Font.createFont(0, istream).deriveFont((float)size);
            fontPage = new GlyphPage(ttf != false ? myFont : new Font(fontName, 0, size), true, true);
            chars = new char[256];
            i = 0;
            while (i < chars.length) {
                chars[i] = (char)i;
                ++i;
            }
            fontPage.generateGlyphPage(chars);
            fontPage.setupTexture();
            fontrenderer = new GlyphPageFontRenderer(fontPage, fontPage, fontPage, fontPage);
            return fontrenderer;
        }
        catch (Exception e) {
            fontPage = new GlyphPage(new Font("Arial", 0, size), true, true);
            chars = new char[256];
            i = 0;
            ** while (i < chars.length)
        }
lbl-1000:
        // 1 sources

        {
            chars[i] = (char)i;
            ++i;
            continue;
        }
lbl23:
        // 1 sources

        fontPage.generateGlyphPage(chars);
        fontPage.setupTexture();
        fontrenderer = new GlyphPageFontRenderer(fontPage, fontPage, fontPage, fontPage);
        return fontrenderer;
    }

    /*
     * Unable to fully structure code
     */
    public static GlyphPageFontRenderer font(int size, FontType type, boolean ttf) {
        try {
            istream = ClientFont.class.getResourceAsStream("/resources/" + type.getType() + ".ttf");
            myFont = Font.createFont(0, istream).deriveFont((float)size);
            fontPage = new GlyphPage(ttf != false ? myFont : new Font(type.getType(), 0, size), true, true);
            chars = new char[256];
            i = 0;
            while (i < chars.length) {
                chars[i] = (char)i;
                ++i;
            }
            fontPage.generateGlyphPage(chars);
            fontPage.setupTexture();
            fontrenderer = new GlyphPageFontRenderer(fontPage, fontPage, fontPage, fontPage);
            return fontrenderer;
        }
        catch (Exception e) {
            fontPage = new GlyphPage(new Font("Arial", 0, size), true, true);
            chars = new char[256];
            i = 0;
            ** while (i < chars.length)
        }
lbl-1000:
        // 1 sources

        {
            chars[i] = (char)i;
            ++i;
            continue;
        }
lbl23:
        // 1 sources

        fontPage.generateGlyphPage(chars);
        fontPage.setupTexture();
        fontrenderer = new GlyphPageFontRenderer(fontPage, fontPage, fontPage, fontPage);
        return fontrenderer;
    }

    public static enum FontType {
        COMFORTAA("Comfortaa-Regular"),
        JETBRAINS_MONO("JetBrainsMono-Regular"),
        ACUMIN("Acumin-RPro"),
        ROBOTO_LIGHT("RobotoLight"),
        ARIAL("Arial"),
        FLUX_ICONS("Icon");

        private String type;

        private FontType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }
    }
}

