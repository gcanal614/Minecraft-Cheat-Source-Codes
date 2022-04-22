package wtf.astronicy.IMPL.module.impl.visuals.hud.impl;

import wtf.astronicy.IMPL.module.impl.visuals.hud.Component;
import wtf.astronicy.IMPL.module.impl.visuals.hud.HUDMod;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public final class Watermark extends Component {
   public Watermark(HUDMod parent) {
      super(parent);
   }

   public void draw(ScaledResolution sr) {
	      FontRenderer fr = null;
	      int firstLetterColor = ModList.color; 
	      int y2 = 0;
	      
	      switch ((HUDMod.ArrayListFontModes)parent.defaultFont.getValue()) {
		case ADVENT:
			fr = mc.AdventFont;
			break;
		case BUNGEE:
			y2 = 2;
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
      String firstLetter = HUDMod.clientName.substring(0, 1);
      fr.drawString(firstLetter, 1.5F, 2.0F + y2, firstLetterColor, true);
      fr.drawString(HUDMod.clientName.substring(1), (float)(fr.getStringWidth(firstLetter) + 2), 2.0F + y2, -1, true);
   }
}
