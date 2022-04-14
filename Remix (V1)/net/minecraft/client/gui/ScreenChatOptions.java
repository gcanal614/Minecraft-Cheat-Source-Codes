package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;

public class ScreenChatOptions extends GuiScreen
{
    private static final GameSettings.Options[] field_146399_a;
    private final GuiScreen field_146396_g;
    private final GameSettings game_settings;
    private String field_146401_i;
    private String field_146398_r;
    private int field_146397_s;
    
    public ScreenChatOptions(final GuiScreen p_i1023_1_, final GameSettings p_i1023_2_) {
        this.field_146396_g = p_i1023_1_;
        this.game_settings = p_i1023_2_;
    }
    
    @Override
    public void initGui() {
        int var1 = 0;
        this.field_146401_i = I18n.format("options.chat.title", new Object[0]);
        this.field_146398_r = I18n.format("options.multiplayer.title", new Object[0]);
        for (final GameSettings.Options var5 : ScreenChatOptions.field_146399_a) {
            if (var5.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), ScreenChatOptions.width / 2 - 155 + var1 % 2 * 160, ScreenChatOptions.height / 6 + 24 * (var1 >> 1), var5));
            }
            else {
                this.buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), ScreenChatOptions.width / 2 - 155 + var1 % 2 * 160, ScreenChatOptions.height / 6 + 24 * (var1 >> 1), var5, this.game_settings.getKeyBinding(var5)));
            }
            ++var1;
        }
        if (var1 % 2 == 1) {
            ++var1;
        }
        this.field_146397_s = ScreenChatOptions.height / 6 + 24 * (var1 >> 1);
        this.buttonList.add(new GuiButton(200, ScreenChatOptions.width / 2 - 100, ScreenChatOptions.height / 6 + 120, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button.id < 100 && button instanceof GuiOptionButton) {
                this.game_settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
                button.displayString = this.game_settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
            }
            if (button.id == 200) {
                ScreenChatOptions.mc.gameSettings.saveOptions();
                ScreenChatOptions.mc.displayGuiScreen(this.field_146396_g);
            }
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.field_146401_i, ScreenChatOptions.width / 2, 20, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, this.field_146398_r, ScreenChatOptions.width / 2, this.field_146397_s + 7, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    static {
        field_146399_a = new GameSettings.Options[] { GameSettings.Options.CHAT_VISIBILITY, GameSettings.Options.CHAT_COLOR, GameSettings.Options.CHAT_LINKS, GameSettings.Options.CHAT_OPACITY, GameSettings.Options.CHAT_LINKS_PROMPT, GameSettings.Options.CHAT_SCALE, GameSettings.Options.CHAT_HEIGHT_FOCUSED, GameSettings.Options.CHAT_HEIGHT_UNFOCUSED, GameSettings.Options.CHAT_WIDTH, GameSettings.Options.REDUCED_DEBUG_INFO };
    }
}
