package wtf.astronicy.UIs.clickgui.panel.component.impl;

import java.awt.Color;

import wtf.astronicy.IMPL.module.options.impl.EnumOption;
import wtf.astronicy.IMPL.utils.ColorUtils;
import wtf.astronicy.UIs.clickgui.panel.Panel;
import wtf.astronicy.UIs.clickgui.panel.component.Component;
import net.minecraft.client.gui.Gui;

public final class EnumOptionComponent extends Component {
   private final EnumOption option;
   private int opacity = 120;

   public EnumOptionComponent(EnumOption option, Panel panel, int x, int y, int width, int height) {
      super(panel, x, y, width, height);
      this.option = option;
   }

   public void onDraw(int mouseX, int mouseY) {
      Panel parent = this.getPanel();
      int x = parent.getX() + this.getX();
      int y = parent.getY() + this.getY();
      boolean hovered = this.isMouseOver(mouseX, mouseY);
      if (hovered) {
         if (this.opacity < 200) {
            this.opacity += 5;
         }
      } else if (this.opacity > 120) {
         this.opacity -= 5;
      }

      Gui.drawRect((double)x, (double)y, (double)(x + this.getWidth()), (double)(y + this.getHeight()), ColorUtils.getColorWithOpacity(BACKGROUND, 255 - this.opacity).getRGB());
      int color = (new Color(this.opacity, this.opacity, this.opacity)).getRGB();
      FONT_RENDERER.drawStringWithShadow(String.format("%s: %s", this.option.getLabel(), this.option.getValue()), (float)x + 2.0F, (float)y + (float)this.getHeight() / 2.0F - 4.0F, color);
   }

   public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
      if (this.isMouseOver(mouseX, mouseY)) {
    	  
         this.option.setValue(this.option.getValues()[(((Enum)this.option.getValue()).ordinal() + 1) % this.option.getValues().length]);
      }

   }

   public boolean isHidden() {
      return !this.option.check();
   }
}
