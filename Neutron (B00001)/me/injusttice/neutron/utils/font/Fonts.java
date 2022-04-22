package me.injusttice.neutron.utils.font;

import java.awt.Font;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Fonts {

    public static MCFontRenderer astolfoArray;

    public static void startFonts() {
        astolfoArray = new MCFontRenderer(fontFromTTF(new ResourceLocation("Desync/fonts/niggas.ttf"), 16.0F, 0), true, false);
    }

    public static Font fontFromTTF(ResourceLocation fontLocation, float fontSize, int fontType) {
        Font output = null;
        try {
            output = Font.createFont(fontType, Minecraft.getMinecraft().getResourceManager().getResource(fontLocation).getInputStream());
            output = output.deriveFont(fontSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
