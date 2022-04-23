// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.font;

import java.util.HashMap;
import java.io.InputStream;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import java.util.Map;
import java.awt.Font;

public class FontManager
{
    private int completed;
    public MinecraftFontRenderer smallFontRenderer;
    private Font smallFontRendererFont;
    public MinecraftFontRenderer mediumFontRenderer;
    private Font mediumFontRendererFont;
    public MinecraftFontRenderer largeFontRenderer;
    private Font largeFontRendererFont;
    public MinecraftFontRenderer largeFontRenderer2;
    private Font largeFontRendererFont2;
    public MinecraftFontRenderer SUPALargeFontRenderer;
    private Font SUPALargeFontRendererFont;
    public MinecraftFontRenderer ArrowIcons;
    private Font ArrowIconsFont;
    public MinecraftFontRenderer novolineFontRenderer;
    private Font novolineFont;
    public MinecraftFontRenderer BasicIcons;
    private Font BasicIconsFont;
    public MinecraftFontRenderer MenuIcons;
    private Font MenuIconsFont;
    public MinecraftFontRenderer MenuIcons2;
    private Font MenuIconsFont2;
    public MinecraftFontRenderer MenuIcons3;
    private Font MenuIconsFont3;
    public MinecraftFontRenderer LargeBasicIcons;
    private Font LargeBasicIconsFont;
    public MinecraftFontRenderer hotMcFontRenderer;
    private Font hotMcFont;
    public MinecraftFontRenderer SkeetIcons;
    private Font SkeetIconsFont;
    public MinecraftFontRenderer smallCSGORenderer;
    private Font smallCSGOFont;
    public MinecraftFontRenderer onetapFontRenderer;
    private Font onetapFont;
    public MinecraftFontRenderer McFontRenderer;
    private Font McFontRendererFont;
    public MinecraftFontRenderer SmallMcFontRenderer;
    private Font SmallMcFontRendererFont;
    public MinecraftFontRenderer onetapIconsRenderer;
    private Font onetapIcons;
    public MinecraftFontRenderer onetapDefaultRenderer;
    private Font onetapDefaultFont;
    public MinecraftFontRenderer BitFontRenderer;
    private Font BitFont;
    
    public FontManager() {
        this.setupFonts();
    }
    
    private Font getFont(final Map<String, Font> locationMap, final String location, final int size) {
        Font font;
        try {
            if (locationMap.containsKey(location)) {
                font = locationMap.get(location).deriveFont(0, (float)size);
            }
            else {
                final InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("BozoWare/Fonts/" + location)).getInputStream();
                font = Font.createFont(0, is);
                locationMap.put(location, font);
                font = font.deriveFont(0, (float)size);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, 10);
        }
        return font;
    }
    
    private boolean hasLoaded() {
        return this.completed >= 3;
    }
    
    private void setupFonts() {
        final HashMap<String, Font> locationMap;
        new Thread(() -> {
            locationMap = new HashMap<String, Font>();
            this.SmallMcFontRendererFont = this.getFont(locationMap, "mc.ttf", 12);
            this.McFontRendererFont = this.getFont(locationMap, "mc.ttf", 21);
            this.smallFontRendererFont = this.getFont(locationMap, "font.ttf", 17);
            this.mediumFontRendererFont = this.getFont(locationMap, "font.ttf", 19);
            this.largeFontRendererFont = this.getFont(locationMap, "font.ttf", 21);
            this.largeFontRendererFont2 = this.getFont(locationMap, "font.ttf", 40);
            this.hotMcFont = this.getFont(locationMap, "Minecraft.ttf", 19);
            this.SUPALargeFontRendererFont = this.getFont(locationMap, "font.ttf", 25);
            this.ArrowIconsFont = this.getFont(locationMap, "Arrows.ttf", 21);
            this.BasicIconsFont = this.getFont(locationMap, "BasicIcons.ttf", 21);
            this.MenuIconsFont = this.getFont(locationMap, "menuicons.ttf", 45);
            this.MenuIconsFont2 = this.getFont(locationMap, "menuicons2.ttf", 45);
            this.novolineFont = this.getFont(locationMap, "novoline.ttf", 19);
            this.MenuIconsFont3 = this.getFont(locationMap, "menuicons3.ttf", 45);
            this.LargeBasicIconsFont = this.getFont(locationMap, "BasicIcons.ttf", 40);
            this.SkeetIconsFont = this.getFont(locationMap, "SkeetIcons.ttf", 21);
            this.smallCSGOFont = new Font("tahoma", 0, 15);
            this.onetapFont = this.getFont(locationMap, "onetap.ttf", 24);
            this.onetapIcons = this.getFont(locationMap, "onetapicon.ttf", 18);
            this.onetapDefaultFont = new Font("roboto", 0, 16);
            this.BitFont = this.getFont(locationMap, "BitFont.ttf", 21);
            ++this.completed;
            return;
        }).start();
        new Thread(() -> ++this.completed).start();
        new Thread(() -> ++this.completed).start();
        while (!this.hasLoaded()) {
            try {
                Thread.sleep(5L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.SmallMcFontRenderer = new MinecraftFontRenderer(this.SmallMcFontRendererFont, true, true);
        this.McFontRenderer = new MinecraftFontRenderer(this.McFontRendererFont, true, true);
        this.smallFontRenderer = new MinecraftFontRenderer(this.smallFontRendererFont, true, true);
        this.mediumFontRenderer = new MinecraftFontRenderer(this.mediumFontRendererFont, true, true);
        this.largeFontRenderer = new MinecraftFontRenderer(this.largeFontRendererFont, true, true);
        this.BitFontRenderer = new MinecraftFontRenderer(this.BitFont, true, true);
        this.novolineFontRenderer = new MinecraftFontRenderer(this.novolineFont, true, true);
        this.largeFontRenderer2 = new MinecraftFontRenderer(this.largeFontRendererFont2, true, true);
        this.SUPALargeFontRenderer = new MinecraftFontRenderer(this.SUPALargeFontRendererFont, true, true);
        this.hotMcFontRenderer = new MinecraftFontRenderer(this.hotMcFont, true, true);
        this.ArrowIcons = new MinecraftFontRenderer(this.ArrowIconsFont, true, true);
        this.BasicIcons = new MinecraftFontRenderer(this.BasicIconsFont, true, true);
        this.MenuIcons = new MinecraftFontRenderer(this.MenuIconsFont, true, true);
        this.MenuIcons2 = new MinecraftFontRenderer(this.MenuIconsFont2, true, true);
        this.MenuIcons3 = new MinecraftFontRenderer(this.MenuIconsFont3, true, true);
        this.LargeBasicIcons = new MinecraftFontRenderer(this.LargeBasicIconsFont, true, true);
        this.SkeetIcons = new MinecraftFontRenderer(this.SkeetIconsFont, true, true);
        this.smallCSGORenderer = new MinecraftFontRenderer(this.smallCSGOFont, false, true);
        this.onetapFontRenderer = new MinecraftFontRenderer(this.onetapFont, true, true);
        this.onetapIconsRenderer = new MinecraftFontRenderer(this.onetapIcons, true, true);
        this.onetapDefaultRenderer = new MinecraftFontRenderer(this.onetapDefaultFont, true, true);
    }
}
