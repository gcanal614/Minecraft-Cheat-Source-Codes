/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.font;

import net.minecraft.client.gui.MinecraftFontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

public final class MinecraftFontRendererHook
extends MinecraftFontRenderer {
    public MinecraftFontRendererHook(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn) {
        super(gameSettingsIn, location, textureManagerIn);
    }

    @Override
    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
        GlStateManager.enableAlpha();
        this.resetStyles();
        if (dropShadow) {
            this.renderString(text, x + 1.0f, y + 1.0f, color, true);
        }
        GlStateManager.disableBlend();
        return this.renderString(text, x, y, color, false);
    }

    @Override
    public float getWidth(String string) {
        int strLen;
        if (string != null && (strLen = string.length()) != 0) {
            float len = this.getCharWidthFloat(string.charAt(0));
            boolean boldChar = false;
            for (int i = 1; i < strLen; ++i) {
                float cLen = this.getCharWidthFloat(string.charAt(i));
                if (cLen < 0.0f && i < strLen - 1) {
                    char c;
                    if ((c = string.charAt(++i)) != 'L' && c != 'l') {
                        if (c == 'R' || c == 'r') {
                            boldChar = false;
                        }
                    } else {
                        boldChar = true;
                    }
                    cLen = 0.0f;
                }
                if (boldChar && cLen > 0.0f) {
                    cLen += this.offsetBold;
                }
                len += cLen;
            }
            return (int)len;
        }
        return 0.0f;
    }
}

