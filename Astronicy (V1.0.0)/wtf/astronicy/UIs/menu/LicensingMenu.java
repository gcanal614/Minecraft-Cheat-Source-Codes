package wtf.astronicy.UIs.menu;

import wtf.astronicy.IMPL.utils.TimerUtility;
import wtf.astronicy.IMPL.utils.render.AnimationUtils;
import wtf.astronicy.IMPL.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public final class LicensingMenu extends GuiScreen {
   private float p = -100.0F;
   private final TimerUtility stopwatch = new TimerUtility();
   private static final ResourceLocation locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");

   public void initGui() {
      super.initGui();
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      if (this.stopwatch.elapsed(10L)) {
         this.p = (float) AnimationUtils.animate((double)this.height, (double)this.p, 0.25D);
         this.stopwatch.reset();
      }

      drawRect(0.0D, 0.0D, (double)this.width, (double)this.height, -1);
      RenderUtils.drawImg(locationMojangPng, (double)this.width / 5.0D, 0.0D, (double)this.width - (double)this.width / 2.5D, (double)this.height);
      drawRect(0.0D, 0.0D, (double)this.width, (double)this.p, -13878632);
      super.drawScreen(mouseX, mouseY, partialTicks);
   }
}
