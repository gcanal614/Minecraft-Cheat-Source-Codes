package wtf.astronicy.IMPL.module.impl.visuals;

import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.registery.Aliases;
import wtf.astronicy.IMPL.module.registery.Bind;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import wtf.astronicy.UIs.clickgui.ClickGuiScreen;

@ModName("Click GUI")
@Bind("RSHIFT")
@Category(ModuleCategory.VISUALS)
@Aliases({"clickgui", "gui"})
public final class ClickGUIMod extends Module {
   public ClickGUIMod() {
      this.setHidden(true);
   }

   public void onEnabled() {
      mc.displayGuiScreen(ClickGuiScreen.getInstance());
      this.setEnabled(false);
   }
}
