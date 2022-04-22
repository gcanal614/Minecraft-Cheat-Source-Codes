package me.injusttice.neutron.ui.Buttons;

import me.injusttice.neutron.utils.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class ExitButton extends GuiButton {
	
	private String buttonText;
	
	private int x, y, widthIn, heightIn;

	public ExitButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.buttonText = buttonText;
		this.x = x;
		this.y = y;
		this.widthIn = widthIn;
		this.heightIn = heightIn;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		RenderUtil.drawImage(new ResourceLocation("Desync/mainmenu/close.png"), x, y, widthIn, heightIn);
	}
}
