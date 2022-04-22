package me.ui.clickgui.components.sub;

import java.awt.Color;

import me.settings.Setting;
import me.ui.clickgui.components.Component;
import net.minecraft.client.gui.GuiButton;

public class Checkbox extends Component{

	private boolean hovered;
	private Setting op;



	private int x;
	private int y;

	public int offset;
	public Checkbox(Setting option, int x, int y) {
		offset = 0;
		this.op = option;
		this.x = x;
		this.y = y;
	
	}

    @Override
	public void renderComponent() {
    	
		  //if(ufr == null)
	         // ufr = UnicodeFontRenderer.getFontOnPC("fontname", 15, Font.PLAIN, 20, 10);
	//	Gui.drawRect(this.x, this.y -  offset, this.x + 10, this.y + 10 -  offset, hovered ? op.getValBoolean() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : op.getValBoolean() ? Color.GRAY.darker().getRGB() : Color.LIGHT_GRAY.getRGB());
		//Gui.drawRect(30, 30 + 2, 30 + 12, 30, 0xFF111111);
		//GL11.glPushMatrix();
		//GL11.glScalef(0.5f,0.5f, 0.5f);
	GuiButton.appleSmall.drawString(this.op.getName(), x - 100, y - offset, -1);
	
	GuiButton.appleSmall.drawString(this.op.getValBoolean() ? "On" : "Off", x + 15, y - offset, Color.GRAY.getRGB());
		//GL11.glPopMatrix();
		//Gui.drawRect(30 + 3 + 4, 30 + 3, 30 + 9 + 4, 30 + 30 + 9, 0xFF999999);
		//if(this.op.getValBoolean())
			//Gui.drawRect(30 + 4 + 4, 30 + 4, 30 + 8 + 4,30 + 8, 0xFF666666);
	}
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0) {
	
			this.op.setValBoolean(!op.getValBoolean());;
		}
	}
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = isMouseOnButton(mouseX, mouseY);
	}
	public boolean isMouseOnButton(int x, int y) {
		if(x > this.x + 10 && x < this.x + 30 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
	
}