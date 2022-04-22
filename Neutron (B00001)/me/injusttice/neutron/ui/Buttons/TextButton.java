package me.injusttice.neutron.ui.Buttons;

import me.injusttice.neutron.utils.font.Fonts;
import me.injusttice.neutron.utils.font.MCFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class TextButton extends GuiButton {
	
	private String buttonText;
	private int x, y, widthIn, heightIn;
	MCFontRenderer font;

	public TextButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.buttonText = buttonText;
		this.x = x;
		this.y = y;
		this.widthIn = widthIn;
		this.heightIn = heightIn;
		font = new MCFontRenderer(Fonts.fontFromTTF(new ResourceLocation("Desync/fonts/SF-Pro.ttf"), 18, 0), true, true);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		font.drawString(buttonText, x, y, -1);
	}
}
