package me.module.impl.movement;

import me.Hime;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;

public class InvMove extends Module {
  public Setting sneak;
  private Object GuiContainer;
  private GuiScreen GuiIngameMenu;
  
  public InvMove() {
    super("InvMove", 0, Category.MOVEMENT);
  }
  public void setup(){
      Hime.instance.settingsManager.rSetting(sneak = new Setting("Sneak", this, false));
  }
  
  @Handler
  public void onUpdate(EventUpdate event) {
    if (this.mc.currentScreen != this.GuiIngameMenu && this.mc.currentScreen != this.GuiContainer && !(this.mc.currentScreen instanceof net.minecraft.client.gui.GuiChat)) {
      this.mc.gameSettings.keyBindForward.pressed = GameSettings.isKeyDown(this.mc.gameSettings.keyBindForward);
      this.mc.gameSettings.keyBindBack.pressed = GameSettings.isKeyDown(this.mc.gameSettings.keyBindBack);
      this.mc.gameSettings.keyBindRight.pressed = GameSettings.isKeyDown(this.mc.gameSettings.keyBindRight);
      this.mc.gameSettings.keyBindLeft.pressed = GameSettings.isKeyDown(this.mc.gameSettings.keyBindLeft);
      this.mc.gameSettings.keyBindJump.pressed = GameSettings.isKeyDown(this.mc.gameSettings.keyBindJump);
      this.mc.gameSettings.keyBindSprint.pressed = GameSettings.isKeyDown(this.mc.gameSettings.keyBindSprint);
      if(sneak.getValBoolean())
      this.mc.gameSettings.keyBindSneak.pressed = GameSettings.isKeyDown(this.mc.gameSettings.keyBindSneak);
    }
  }
}