package me.ui.clickgui2.component.components.sub;

import java.awt.Color;

import me.Hime;
import me.module.Module;
import me.module.Module.Category;
import me.ui.clickgui2.component.Component;
import me.ui.clickgui2.component.components.Button;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;


public class VisibleButton extends Component { // Remove this class if you don't want it (it's kinda useless)

	private boolean hovered;
	private Button parent;
	private int offset;
	private int x;
	private int y;
	private Module mod;
//	public static UnicodeFontRenderer ufr;
	public VisibleButton(Button button, Module mod, int offset) {
		this.parent = button;
		this.mod = mod;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
	}
	
	@Override
	public void renderComponentRect() {
		Gui.drawRect(parent.parent.getX(), parent.parent.getY()+ offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + parent.parent.getBarHeight()+ offset, new Color(25,25,25).getRGB());
	}
	
	@Override
	public void renderComponent() {
		GuiButton.ssss.drawString("Visible: " + mod.visible, (parent.parent.getX() + 7), (parent.parent.getY() + offset + 2) + 1, -1);
     }
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = isMouseOnButton(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
			mod.visible = (!mod.visible);
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
