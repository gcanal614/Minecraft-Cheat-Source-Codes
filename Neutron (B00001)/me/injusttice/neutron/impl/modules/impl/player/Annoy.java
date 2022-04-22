package me.injusttice.neutron.impl.modules.impl.player;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.impl.modules.impl.combat.KillAura;
import me.injusttice.neutron.api.settings.ModuleCategory;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;

import java.util.concurrent.ThreadLocalRandom;

public class Annoy extends Module {
  public ModeSet mode = new ModeSet("Mode", "Spinner", new String[] { "Spinner", "Custom" });

  public BooleanSet moduleCheck = new BooleanSet("Module Check", true);

  public DoubleSet spinnerSpeed = new DoubleSet("Spinner Speed", 3.0D, 1.0D, 90.0D, 0.1D);

  public ModuleCategory customCategory = new ModuleCategory("Custom...");

  public ModeSet customYawMode = new ModeSet("Yaw", "None", new String[] { "None", "Random", "Backwards" });

  public BooleanSet customJump = new BooleanSet("Jump", false);

  public DoubleSet minPitch = new DoubleSet("Min Pitch", 90.0D, -180.0D, 180.0D, 1.0D);

  public DoubleSet maxPitch = new DoubleSet("Max Pitch", 90.0D, -180.0D, 180.0D, 1.0D);

  public BooleanSet customSneak = new BooleanSet("Sneak", false);

  public BooleanSet customSwing = new BooleanSet("Swing", false);

  double spinnerYaw = 0.0D;

  public Annoy() {
    super("Annoy", 0, Category.PLAYER);
    addSettings(mode, moduleCheck, spinnerSpeed, customCategory);
    this.customCategory.addCatSettings(customYawMode, customJump, minPitch, maxPitch, customSneak, customSwing);
  }

  @EventTarget
  public void onPre(EventMotion e) {
    double min, max, finalPitch, yaw;
    boolean killauraDoinStuff = (NeutronMain.instance.moduleManager.getModuleByName("KillAura").isToggled() && KillAura.target != null);
    boolean scaffoldToggled = NeutronMain.instance.moduleManager.getModuleByName("Scaffold").isToggled();
    boolean isModuleMotifying = (scaffoldToggled || killauraDoinStuff);
    if (this.moduleCheck.isEnabled() &&
            isModuleMotifying)
      return;
    switch (this.mode.getMode()) {
      case "Spinner":
        this.spinnerYaw += this.spinnerSpeed.getValue();
        e.setYaw((float)this.spinnerYaw);
        break;
      case "Custom":
        if (this.customJump.isEnabled() &&
                localPlayer.onGround)
          localPlayer.jump();
        if (this.customSneak.isEnabled())
          this.mc.gameSettings.keyBindSneak.pressed = (localPlayer.ticksExisted % 4 != 0);
        if (this.customSwing.isEnabled())
          localPlayer.swingItem();
        min = this.minPitch.getValue();
        max = this.maxPitch.getValue();
        if (min >= max)
          max++;
        finalPitch = ThreadLocalRandom.current().nextDouble(min, max);
        e.setPitch((float)finalPitch);
        yaw = localPlayer.rotationYaw;
        switch (this.customYawMode.getMode()) {
          case "Random":
            yaw = ThreadLocalRandom.current().nextDouble(-180.0D, 180.0D);
            break;
          case "Backwards":
            yaw = (localPlayer.rotationYaw + 180.0F);
            break;
        }
        e.setYaw((float)yaw);
        break;
    }
    this.mc.thePlayer.rotationPitchHead = e.getPitch();
    this.mc.thePlayer.renderYawOffset = e.getYaw();
    this.mc.thePlayer.prevRenderYawOffset = e.getYaw();
  }
}
