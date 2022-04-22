package me.ui.clickgui2.component.components.sub;

import java.awt.Color;

import me.Hime;
import me.module.Module.Category;
import me.settings.Setting;
import me.ui.clickgui2.component.Component;
import me.ui.clickgui2.component.components.Button;
import me.util.ColorUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

//Your Imports

public class Checkbox extends Component {

	private boolean hovered;
	private Setting op;

	private Button parent;
	private int offset;
	private int x;
	private int y;
	
	public Checkbox(Setting option, Button button, int offset) {
		this.op = option;
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
	}
	@Override
	public void renderComponentRect() {
		    Gui.drawRect(parent.parent.getX(), parent.parent.getY()+ offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + parent.parent.getBarHeight()+ offset, new Color(25,25,25).getRGB());	
			
		    
		    Gui.drawRect(parent.parent.getX() + 3 + 4, parent.parent.getY() + offset + 3, parent.parent.getX() + 9 + 4, parent.parent.getY() + offset + 9, 0xFF999999);
			
		    final float hue = (float) (ColorUtil.getClickGUIColor());
			  //                           colorSaturation  colorBrightness 
		    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
		    
			if(this.op.getValBoolean())
				Gui.drawRect(parent.parent.getX() + 4 + 4, parent.parent.getY() + offset + 4, parent.parent.getX() + 8 + 4, parent.parent.getY() + offset + 8, color.getRGB());
		}
	
	
	@Override
	public void renderComponent() {
		GuiButton.ssss.drawString(this.op.getName(), (parent.parent.getX() + 10 + 4), (parent.parent.getY() + offset + 3), -1);
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
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
			this.op.setValBoolean(!op.getValBoolean());;
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
