/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.utils.render;

import club.tifality.utils.Wrapper;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import net.minecraft.util.ResourceLocation;

public final class TTFUtils {
    public static Font getFontFromLocation(String fileName, int size) {
        try {
            return Font.createFont(0, Wrapper.getMinecraft().getResourceManager().getResource(new ResourceLocation("tifality/" + fileName)).getInputStream()).deriveFont(0, size);
        }
        catch (FontFormatException | IOException ignored) {
            return null;
        }
    }
}

