package me.ui.clickgui2.component.components.sub;

import java.awt.Color;

import me.Hime;
import org.lwjgl.input.Keyboard;

import me.module.Module.Category;
import me.ui.clickgui2.component.Component;
import me.ui.clickgui2.component.components.Button;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;  

//Your Imports

public class Keybind extends Component {

	private boolean hovered;
	private boolean binding;
	private Button parent;
	private int offset;
	private int x;
	private int y;

	public Keybind(Button button, int offset) {
		this.parent = button;
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
		GuiButton.ssss.drawString(binding ? "Press a key..." : ("Key: " + Keyboard.getKeyName(this.parent.mod.getKey())), (parent.parent.getX() + 7), (parent.parent.getY() + offset -2) + 5, -1);
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
			this.binding = !this.binding;
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int key) {
		if(this.binding) {
			this.parent.mod.setKey(key);
			this.binding = false;
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
