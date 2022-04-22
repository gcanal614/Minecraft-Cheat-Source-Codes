package wtf.astronicy.IMPL.module.impl.visuals.hud.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import wtf.astronicy.Astronicy;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.impl.visuals.hud.Component;
import wtf.astronicy.IMPL.module.impl.visuals.hud.HUDMod;
import wtf.astronicy.IMPL.utils.render.Palette;
import wtf.astronicy.IMPL.utils.render.Translate;

public final class ModList extends Component {
   private float hue = 1.0F;
   public static int color;

   public ModList(HUDMod parent) {
      super(parent);
   }

   public void draw(ScaledResolution sr) {
      HUDMod hud = this.getParent();
      int width = sr.getScaledWidth();
      int height = sr.getScaledHeight();
      int y2 = 0;
      FontRenderer fr = null;
      
      switch ((HUDMod.ArrayListFontModes)hud.defaultFont.getValue()) {
	case ADVENT:
		fr = mc.AdventFont;
		break;
	case BUNGEE:
		y2 = 3;
		fr = mc.BungeeFont;
		break;
	case COMFORTAA:
		fr = mc.comfortaaFont;
		break;
	case MONTSERRAT:
		fr = mc.MontserratFont;
		break;
	default:
		break;
	}
      
      boolean bottom = hud.arrayListPosition.getValue() == HUDMod.ArrayListPosition.BOTTOM;
      List sortedList = this.getSortedModules(fr);
      Color modListColor = (Color)hud.color.getValue();
      float translationFactor = 14.4F / (float)Minecraft.getDebugFPS();
      int listOffset = 10;
      int y = bottom ? height - listOffset : 0;
      this.hue += translationFactor / 100.0F;
      if (this.hue > 1.0F) {
         this.hue = 0.0F;
      }

      float h = this.hue;
      GL11.glEnable(3042);
      int i = 0;

      for(int sortedListSize = sortedList.size(); i < sortedListSize; ++i) {
         Module module = (Module)sortedList.get(i);
         Translate translate = module.getTranslate();
         String moduleLabel = module.getDisplayLabel();
         float length = (float)fr.getStringWidth(moduleLabel);
         float featureX = (float)width - length - 2.0F;
         boolean enable = module.isEnabled();
         if (bottom) {
            if (enable) {
               translate.interpolate((double)featureX, (double)(y + 1), (double)translationFactor);
            } else {
               translate.interpolate((double)width, (double)(height + 1), (double)translationFactor);
            }
         } else if (enable) {
            translate.interpolate((double)featureX, (double)(y + 1), (double)translationFactor);
         } else {
            translate.interpolate((double)width, (double)(-listOffset - 1), (double)translationFactor);
         }

         double translateX = translate.getX();
         double translateY = translate.getY();
         boolean visible = bottom ? translateY < (double)height : translateY > (double)(-listOffset);
         if (visible) {
            switch((HUDMod.ArrayListColor)hud.modListColorMode.getValue()) {
            case PULSING:
               color = Palette.fade(modListColor, 100, sortedList.indexOf(module) * 2 + 10).getRGB();
               break;
            case RAINBOW:
               color = Color.HSBtoRGB(h, 0.7F, 1.0F);
               break;
            default:
               color = modListColor.getRGB();
            }

            int nextIndex = sortedList.indexOf(module) + 1;
            Module nextModule = null;
            if (sortedList.size() > nextIndex) {
               nextModule = this.getNextEnabledModule(sortedList, nextIndex);
            }

            if (hud.modListBackground.getValue()) {
               //Astronicy.MANAGER_REGISTRY.blurrerManager.blur((int) (translateX), (int) (translateY), width, (int) (listOffset - 1.0D), true);
               //Astronicy.MANAGER_REGISTRY.blurrerManager.bloom((int) (translateX), (int) (translateY), width, (int) (listOffset - 1.0D), 10, new Color(13, 13, 13, 150));
               Gui.drawRect(translateX - 2.0D, translateY - 1.0D, (double)width, translateY + (double)listOffset - 1.0D, (new Color(13, 13, 13, 30)).getRGB());
            }

            if (hud.modListSideBar.getValue()) {
               Gui.drawRect(translateX + (double)length + 1.0D, translateY - 1.0D, (double)width, translateY + (double)listOffset - 1.0D, color);
            }

            if (hud.modListOutline.getValue()) {
               Gui.drawRect(translateX - 2.5D, translateY - 1.0D, translateX - 2.0D, translateY + (double)listOffset - 1.0D, color);
               double offsetY = bottom ? -0.5D : (double)listOffset;
               if (nextModule != null) {
                  double dif = (double)(length - (float)fr.getStringWidth(nextModule.getDisplayLabel()));
                  Gui.drawRect(translateX - 2.5D, translateY + offsetY - 1.0D, translateX - 2.5D + dif, translateY + offsetY - 0.5D, color);
               } else {
                  Gui.drawRect(translateX - 2.5D, translateY + offsetY - 1.0D, (double)width, translateY + offsetY - 0.5D, color);
               }
            }

            fr.drawString(moduleLabel, (float)translateX, (float)translateY + y2, color, true);
            if (module.isEnabled()) {
               y += bottom ? -listOffset : listOffset;
            }

            h += translationFactor / 6.0F;
         }
      }

   }

   private Module getNextEnabledModule(List modules, int startingIndex) {
      int i = startingIndex;

      for(int modulesSize = modules.size(); i < modulesSize; ++i) {
         Module module = (Module)modules.get(i);
         if (module.isEnabled()) {
            return module;
         }
      }

      return null;
   }

   private List getSortedModules(FontRenderer fr) {
      List<Module> sortedList = new ArrayList(Astronicy.MANAGER_REGISTRY.moduleManager.getModules());
      sortedList.removeIf(Module::isHidden);
      sortedList.sort(Comparator.comparingDouble((e) -> {
         return (double)(-fr.getStringWidth(e.getDisplayLabel()));
      }));
      return sortedList;
   }
}
