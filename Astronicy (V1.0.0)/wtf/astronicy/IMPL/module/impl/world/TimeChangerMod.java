package wtf.astronicy.IMPL.module.impl.world;

import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.registery.Aliases;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import wtf.astronicy.IMPL.module.options.Option;
import wtf.astronicy.IMPL.module.options.impl.DoubleOption;

@ModName("Time Changer")
@Category(ModuleCategory.WORLD)
@Aliases({"timechanger", "worldtime"})
public final class TimeChangerMod extends Module {
   public final DoubleOption time = new DoubleOption("Time", 16000.0D, 1.0D, 24000.0D, 100.0D);

   public TimeChangerMod() {
      this.addOptions(new Option[]{this.time});
   }
}
