package wtf.astronicy.IMPL.module.impl.visuals.hud;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import wtf.astronicy.Astronicy;
import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.render.RenderGuiEvent;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.impl.visuals.hud.impl.InfoComponent;
import wtf.astronicy.IMPL.module.impl.visuals.hud.impl.ModList;
import wtf.astronicy.IMPL.module.impl.visuals.hud.impl.Watermark;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import wtf.astronicy.IMPL.module.options.Option;
import wtf.astronicy.IMPL.module.options.impl.BoolOption;
import wtf.astronicy.IMPL.module.options.impl.ColorOption;
import wtf.astronicy.IMPL.module.options.impl.DoubleOption;
import wtf.astronicy.IMPL.module.options.impl.EnumOption;
import wtf.astronicy.UIs.Notifications.NotificationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

@ModName("HUD")
@Category(ModuleCategory.VISUALS)
public final class HUDMod extends Module {
   public static String clientName;
   private static final Minecraft mc;
   public final ColorOption color;
   public final EnumOption arrayListPosition;
   public final BoolOption watermark;
   public final EnumOption defaultFont;
   public final EnumOption infoDisplayMode;
   public final EnumOption modListColorMode;
   public final BoolOption modListSideBar;
   public final BoolOption modListOutline;
   public final BoolOption modListBackground;
   public final DoubleOption modListBackgroundAlpha;
   private final List components;

   public HUDMod() {
      this.arrayListPosition = new EnumOption("Mod List Position", HUDMod.ArrayListPosition.TOP);
      this.color = new ColorOption("Color", new Color(102, 0, 102));
      this.watermark = new BoolOption("Watermark", true);
      this.defaultFont = new EnumOption("Default Font", HUDMod.ArrayListFontModes.BUNGEE);
      this.infoDisplayMode = new EnumOption("Info Display Mode", HUDMod.InfoDisplayMode.LEFT);
      this.modListColorMode = new EnumOption("Mod List Color Mode", HUDMod.ArrayListColor.PULSING);
      this.modListSideBar = new BoolOption("Mod List Side Bar", false);
      this.modListOutline = new BoolOption("Mod List Outline", true);
      this.modListBackground = new BoolOption("Mod List Background", true);
      this.modListBackgroundAlpha = new DoubleOption("Mod List Background Alpha", 0.2D, 0.0D, 1.0D, 0.05D);
      this.components = new ArrayList();
      this.setEnabled(true);
      this.components.add(new ModList(this));
      this.components.add(new Watermark(this));
      this.components.add(new InfoComponent(this));
      this.addOptions(new Option[]{this.color, this.defaultFont, this.infoDisplayMode, this.arrayListPosition, this.modListColorMode, this.modListSideBar, this.modListOutline, this.modListBackground, this.modListBackgroundAlpha, this.watermark});
   }

   @Listener(RenderGuiEvent.class)
   public final void onRenderGui(RenderGuiEvent event) {
       ScaledResolution sr = event.getScaledResolution();
      if (!mc.gameSettings.showDebugInfo) {
         int i = 0;
         NotificationManager.doRender(sr.getScaledWidth(), sr.getScaledHeight());
         for(int componentsSize = this.components.size(); i < componentsSize; ++i) {
            Component component = (Component)this.components.get(i);
            component.draw(sr);
         }

      }
   }

   static {
      clientName = Astronicy.INSTANCE.getName() + " " + Astronicy.INSTANCE.getVersion();
      mc = Minecraft.getMinecraft();
   }

   public static enum InfoDisplayMode {
      LEFT,
      RIGHT,
      OFF;
   }

   public static enum ArrayListColor {
      STATIC,
      PULSING,
      RAINBOW;
   }

   public static enum ArrayListPosition {
      BOTTOM,
      TOP;
   }
   
   public static enum ArrayListFontModes {
	      ADVENT,
	      BUNGEE,
	      COMFORTAA,
	      MONTSERRAT;
	   }
}
