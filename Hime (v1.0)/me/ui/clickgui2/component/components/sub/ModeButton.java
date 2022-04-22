package me.ui.clickgui2.component.components.sub;

import java.awt.Color;

import me.Hime;
import me.module.Module;
import me.module.Module.Category;
import me.settings.Setting;
import me.ui.clickgui2.component.Component;
import me.ui.clickgui2.component.components.Button;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

//Your Imports

public class ModeButton extends Component {

	private boolean hovered;
	private Button parent;
	private Setting set;
	private int offset;
	private int x;
	private int y;
	private Module mod;

	private int modeIndex;
	
	public ModeButton(Setting set, Button button, Module mod, int offset) {
		this.set = set;
		this.parent = button;
		this.mod = mod;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
		this.modeIndex = 0;
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
		GuiButton.ssss.drawString(set.getName() + ": " + set.getValString(), (parent.parent.getX() + 7), (parent.parent.getY() + offset + 2) + 1, -1);
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = isMouseOnButton(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
	}
	
	public void mouseClicked(int mouseX, int mouseY, int button) {
	    if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
	      int maxIndex = this.set.getOptions().size();
	      if (this.modeIndex > maxIndex - 2) {
	        this.modeIndex = 0;
	      } else {
	        this.modeIndex++;
	      } 
	      this.set.setValString(this.set.getOptions().get(this.modeIndex));
	    } 
	   /* if (isMouseOnButton(mouseX, mouseY) && button == 1 && this.parent.open) {
		      int maxIndex = this.set.getOptions().size();
		      System.out.println(modeIndex);
		      if (this.modeIndex == 2) {
		        this.modeIndex = 0;
		        //System.out.println("ee");
		        this.set.setValString(this.set.getOptions().get(1));
		      } else {
		        this.modeIndex--;
		        //this.set.setValString(this.set.getOptions().get(this.modeIndex));
		      } 
		     
		    } */
	  }
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
