package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.dto.RealmsOptions;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.gui.RealmsConstants;
import net.minecraft.realms.Realms;
import net.minecraft.realms.RealmsButton;
import net.minecraft.realms.RealmsEditBox;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.realms.RealmsSliderButton;
import org.lwjgl.input.Keyboard;

public class RealmsSlotOptionsScreen extends RealmsScreen {
  private static final int BUTTON_CANCEL_ID = 0;
  
  private static final int BUTTON_DONE_ID = 1;
  
  private static final int BUTTON_DIFFICULTY_ID = 2;
  
  private static final int BUTTON_GAMEMODE_ID = 3;
  
  private static final int BUTTON_PVP_ID = 4;
  
  private static final int BUTTON_SPAWN_ANIMALS_ID = 5;
  
  private static final int BUTTON_SPAWN_MONSTERS_ID = 6;
  
  private static final int BUTTON_SPAWN_NPCS_ID = 7;
  
  private static final int BUTTON_SPAWN_PROTECTION_ID = 8;
  
  private static final int BUTTON_COMMANDBLOCKS_ID = 9;
  
  private static final int BUTTON_FORCE_GAMEMODE_ID = 10;
  
  private static final int NAME_EDIT_BOX = 11;
  
  private RealmsEditBox nameEdit;
  
  protected final RealmsConfigureWorldScreen parent;
  
  private int column1_x;
  
  private int column_width;
  
  private int column2_x;
  
  private RealmsOptions options;
  
  private RealmsServer.WorldType worldType;
  
  private int activeSlot;
  
  private int difficultyIndex;
  
  private int gameModeIndex;
  
  private Boolean pvp;
  
  private Boolean spawnNPCs;
  
  private Boolean spawnAnimals;
  
  private Boolean spawnMonsters;
  
  private Integer spawnProtection;
  
  private Boolean commandBlocks;
  
  private Boolean forceGameMode;
  
  private RealmsButton pvpButton;
  
  private RealmsButton spawnAnimalsButton;
  
  private RealmsButton spawnMonstersButton;
  
  private RealmsButton spawnNPCsButton;
  
  private RealmsSliderButton spawnProtectionButton;
  
  private RealmsButton commandBlocksButton;
  
  private RealmsButton forceGameModeButton;
  
  private boolean notNormal = false;
  
  String[] difficulties;
  
  String[] gameModes;
  
  String[][] gameModeHints;
  
  public RealmsSlotOptionsScreen(RealmsConfigureWorldScreen configureWorldScreen, RealmsOptions options, RealmsServer.WorldType worldType, int activeSlot) {
    this.parent = configureWorldScreen;
    this.options = options;
    this.worldType = worldType;
    this.activeSlot = activeSlot;
  }
  
  public void removed() {
    Keyboard.enableRepeatEvents(false);
  }
  
  public void tick() {
    this.nameEdit.tick();
  }
  
  public void buttonClicked(RealmsButton button) {
    if (!button.active())
      return; 
    switch (button.id()) {
      case 1:
        saveSettings();
        return;
      case 0:
        Realms.setScreen(this.parent);
        return;
      case 2:
        this.difficultyIndex = (this.difficultyIndex + 1) % this.difficulties.length;
        button.msg(difficultyTitle());
        if (this.worldType.equals(RealmsServer.WorldType.NORMAL)) {
          this.spawnMonstersButton.active((this.difficultyIndex != 0));
          this.spawnMonstersButton.msg(spawnMonstersTitle());
        } 
        return;
      case 3:
        this.gameModeIndex = (this.gameModeIndex + 1) % this.gameModes.length;
        button.msg(gameModeTitle());
        return;
      case 4:
        this.pvp = Boolean.valueOf(!this.pvp.booleanValue());
        button.msg(pvpTitle());
        return;
      case 5:
        this.spawnAnimals = Boolean.valueOf(!this.spawnAnimals.booleanValue());
        button.msg(spawnAnimalsTitle());
        return;
      case 7:
        this.spawnNPCs = Boolean.valueOf(!this.spawnNPCs.booleanValue());
        button.msg(spawnNPCsTitle());
        return;
      case 6:
        this.spawnMonsters = Boolean.valueOf(!this.spawnMonsters.booleanValue());
        button.msg(spawnMonstersTitle());
        return;
      case 9:
        this.commandBlocks = Boolean.valueOf(!this.commandBlocks.booleanValue());
        button.msg(commandBlocksTitle());
        return;
      case 10:
        this.forceGameMode = Boolean.valueOf(!this.forceGameMode.booleanValue());
        button.msg(forceGameModeTitle());
        return;
    } 
  }
  
  public void keyPressed(char eventCharacter, int eventKey) {
    this.nameEdit.keyPressed(eventCharacter, eventKey);
    switch (eventKey) {
      case 15:
        this.nameEdit.setFocus(!this.nameEdit.isFocused());
        return;
      case 1:
        Realms.setScreen(this.parent);
        return;
      case 28:
      case 156:
        saveSettings();
        return;
    } 
  }
  
  public void mouseClicked(int x, int y, int buttonNum) {
    super.mouseClicked(x, y, buttonNum);
    this.nameEdit.mouseClicked(x, y, buttonNum);
  }
  
  public void init() {
    this.column1_x = width() / 2 - 122;
    this.column_width = 122;
    this.column2_x = width() / 2 + 10;
    createDifficultyAndGameMode();
    this.difficultyIndex = this.options.difficulty.intValue();
    this.gameModeIndex = this.options.gameMode.intValue();
    if (!this.worldType.equals(RealmsServer.WorldType.NORMAL)) {
      this.notNormal = true;
      this.pvp = Boolean.valueOf(true);
      this.spawnProtection = Integer.valueOf(0);
      this.forceGameMode = Boolean.valueOf(false);
      this.spawnAnimals = Boolean.valueOf(true);
      this.spawnMonsters = Boolean.valueOf(true);
      this.spawnNPCs = Boolean.valueOf(true);
      this.commandBlocks = Boolean.valueOf(true);
    } else {
      this.pvp = this.options.pvp;
      this.spawnProtection = this.options.spawnProtection;
      this.forceGameMode = this.options.forceGameMode;
      this.spawnAnimals = this.options.spawnAnimals;
      this.spawnMonsters = this.options.spawnMonsters;
      this.spawnNPCs = this.options.spawnNPCs;
      this.commandBlocks = this.options.commandBlocks;
    } 
    this.nameEdit = newEditBox(11, this.column1_x + 2, RealmsConstants.row(2), this.column_width - 4, 20);
    this.nameEdit.setFocus(true);
    this.nameEdit.setMaxLength(10);
    this.nameEdit.setValue(this.options.getSlotName(this.activeSlot));
    buttonsAdd(newButton(3, this.column2_x, RealmsConstants.row(2), this.column_width, 20, gameModeTitle()));
    buttonsAdd(this.pvpButton = newButton(4, this.column1_x, RealmsConstants.row(4), this.column_width, 20, pvpTitle()));
    buttonsAdd(this.spawnAnimalsButton = newButton(5, this.column2_x, RealmsConstants.row(4), this.column_width, 20, spawnAnimalsTitle()));
    buttonsAdd(newButton(2, this.column1_x, RealmsConstants.row(6), this.column_width, 20, difficultyTitle()));
    buttonsAdd(this.spawnMonstersButton = newButton(6, this.column2_x, RealmsConstants.row(6), this.column_width, 20, spawnMonstersTitle()));
    buttonsAdd((RealmsButton)(this.spawnProtectionButton = new SettingsSlider(8, this.column1_x, RealmsConstants.row(8), this.column_width, 17, this.spawnProtection.intValue(), 0.0F, 16.0F)));
    buttonsAdd(this.spawnNPCsButton = newButton(7, this.column2_x, RealmsConstants.row(8), this.column_width, 20, spawnNPCsTitle()));
    buttonsAdd(this.forceGameModeButton = newButton(10, this.column1_x, RealmsConstants.row(10), this.column_width, 20, forceGameModeTitle()));
    buttonsAdd(this.commandBlocksButton = newButton(9, this.column2_x, RealmsConstants.row(10), this.column_width, 20, commandBlocksTitle()));
    if (!this.worldType.equals(RealmsServer.WorldType.NORMAL)) {
      this.pvpButton.active(false);
      this.spawnAnimalsButton.active(false);
      this.spawnNPCsButton.active(false);
      this.spawnMonstersButton.active(false);
      this.spawnProtectionButton.active(false);
      this.commandBlocksButton.active(false);
      this.spawnProtectionButton.active(false);
      this.forceGameModeButton.active(false);
    } 
    if (this.difficultyIndex == 0)
      this.spawnMonstersButton.active(false); 
    buttonsAdd(newButton(1, this.column1_x, RealmsConstants.row(13), this.column_width, 20, getLocalizedString("mco.configure.world.buttons.done")));
    buttonsAdd(newButton(0, this.column2_x, RealmsConstants.row(13), this.column_width, 20, getLocalizedString("gui.cancel")));
  }
  
  private void createDifficultyAndGameMode() {
    this.difficulties = new String[] { getLocalizedString("options.difficulty.peaceful"), getLocalizedString("options.difficulty.easy"), getLocalizedString("options.difficulty.normal"), getLocalizedString("options.difficulty.hard") };
    this.gameModes = new String[] { getLocalizedString("selectWorld.gameMode.survival"), getLocalizedString("selectWorld.gameMode.creative"), getLocalizedString("selectWorld.gameMode.adventure") };
    this.gameModeHints = new String[][] { { getLocalizedString("selectWorld.gameMode.survival.line1"), getLocalizedString("selectWorld.gameMode.survival.line2") }, { getLocalizedString("selectWorld.gameMode.creative.line1"), getLocalizedString("selectWorld.gameMode.creative.line2") }, { getLocalizedString("selectWorld.gameMode.adventure.line1"), getLocalizedString("selectWorld.gameMode.adventure.line2") } };
  }
  
  private String difficultyTitle() {
    String difficulty = getLocalizedString("options.difficulty");
    return difficulty + ": " + this.difficulties[this.difficultyIndex];
  }
  
  private String gameModeTitle() {
    String gameMode = getLocalizedString("selectWorld.gameMode");
    return gameMode + ": " + this.gameModes[this.gameModeIndex];
  }
  
  private String pvpTitle() {
    return getLocalizedString("mco.configure.world.pvp") + ": " + (this.pvp.booleanValue() ? getLocalizedString("mco.configure.world.on") : getLocalizedString("mco.configure.world.off"));
  }
  
  private String spawnAnimalsTitle() {
    return getLocalizedString("mco.configure.world.spawnAnimals") + ": " + (this.spawnAnimals.booleanValue() ? getLocalizedString("mco.configure.world.on") : getLocalizedString("mco.configure.world.off"));
  }
  
  private String spawnMonstersTitle() {
    if (this.difficultyIndex == 0)
      return getLocalizedString("mco.configure.world.spawnMonsters") + ": " + getLocalizedString("mco.configure.world.off"); 
    return getLocalizedString("mco.configure.world.spawnMonsters") + ": " + (this.spawnMonsters.booleanValue() ? getLocalizedString("mco.configure.world.on") : getLocalizedString("mco.configure.world.off"));
  }
  
  private String spawnNPCsTitle() {
    return getLocalizedString("mco.configure.world.spawnNPCs") + ": " + (this.spawnNPCs.booleanValue() ? getLocalizedString("mco.configure.world.on") : getLocalizedString("mco.configure.world.off"));
  }
  
  private String commandBlocksTitle() {
    return getLocalizedString("mco.configure.world.commandBlocks") + ": " + (this.commandBlocks.booleanValue() ? getLocalizedString("mco.configure.world.on") : getLocalizedString("mco.configure.world.off"));
  }
  
  private String forceGameModeTitle() {
    return getLocalizedString("mco.configure.world.forceGameMode") + ": " + (this.forceGameMode.booleanValue() ? getLocalizedString("mco.configure.world.on") : getLocalizedString("mco.configure.world.off"));
  }
  
  public void render(int xm, int ym, float a) {
    renderBackground();
    String slotName = getLocalizedString("mco.configure.world.edit.slot.name");
    drawString(slotName, this.column1_x + fontWidth(slotName) / 2, RealmsConstants.row(0) + 5, 16777215);
    drawCenteredString(getLocalizedString("mco.configure.world.buttons.options"), width() / 2, 17, 16777215);
    if (this.notNormal)
      drawCenteredString(getLocalizedString("mco.configure.world.edit.subscreen.adventuremap"), width() / 2, 30, 16711680); 
    this.nameEdit.render();
    super.render(xm, ym, a);
  }
  
  public void renderHints() {
    drawString(this.gameModeHints[this.gameModeIndex][0], this.column2_x + 2, RealmsConstants.row(0), 10526880);
    drawString(this.gameModeHints[this.gameModeIndex][1], this.column2_x + 2, RealmsConstants.row(0) + fontLineHeight() + 2, 10526880);
  }
  
  public void mouseReleased(int x, int y, int buttonNum) {
    if (!this.spawnProtectionButton.active())
      return; 
    this.spawnProtectionButton.released(x, y);
  }
  
  public void mouseDragged(int x, int y, int buttonNum, long delta) {
    if (!this.spawnProtectionButton.active())
      return; 
    if (x < this.column1_x + this.spawnProtectionButton.getWidth() && x > this.column1_x && y < this.spawnProtectionButton.y() + 20 && y > this.spawnProtectionButton.y())
      this.spawnProtectionButton.clicked(x, y); 
  }
  
  private class SettingsSlider extends RealmsSliderButton {
    public SettingsSlider(int id, int x, int y, int width, int steps, int currentValue, float minValue, float maxValue) {
      super(id, x, y, width, steps, currentValue, minValue, maxValue);
    }
    
    public String getMessage() {
      return RealmsScreen.getLocalizedString("mco.configure.world.spawnProtection") + ": " + ((RealmsSlotOptionsScreen.this.spawnProtection.intValue() == 0) ? RealmsScreen.getLocalizedString("mco.configure.world.off") : (String)RealmsSlotOptionsScreen.this.spawnProtection);
    }
    
    public void clicked(float value) {
      if (!RealmsSlotOptionsScreen.this.spawnProtectionButton.active())
        return; 
      RealmsSlotOptionsScreen.this.spawnProtection = Integer.valueOf((int)value);
    }
  }
  
  private String getSlotName() {
    if (this.nameEdit.getValue().equals(this.options.getDefaultSlotName(this.activeSlot)))
      return ""; 
    return this.nameEdit.getValue();
  }
  
  private void saveSettings() {
    if (this.worldType.equals(RealmsServer.WorldType.ADVENTUREMAP)) {
      this.parent.saveSlotSettings(new RealmsOptions(this.options.pvp, this.options.spawnAnimals, this.options.spawnMonsters, this.options.spawnNPCs, this.options.spawnProtection, this.options.commandBlocks, Integer.valueOf(this.difficultyIndex), Integer.valueOf(this.gameModeIndex), this.options.forceGameMode, getSlotName()));
    } else {
      this.parent.saveSlotSettings(new RealmsOptions(this.pvp, this.spawnAnimals, this.spawnMonsters, this.spawnNPCs, this.spawnProtection, this.commandBlocks, Integer.valueOf(this.difficultyIndex), Integer.valueOf(this.gameModeIndex), this.forceGameMode, getSlotName()));
    } 
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\com\mojang\realmsclient\gui\screens\RealmsSlotOptionsScreen.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */