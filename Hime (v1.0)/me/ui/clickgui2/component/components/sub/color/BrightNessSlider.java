package me.ui.clickgui2.component.components.sub.color;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import me.Hime;
import me.module.Module.Category;
import me.settings.Setting;
import me.ui.clickgui2.component.Component;
import me.ui.clickgui2.component.components.Button;
import me.util.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

//Your Imports

public class BrightNessSlider extends Component {

	private boolean hovered;
	//public static UnicodeFontRenderer ufr;
	private Setting set;
	private Button parent;
	private int offset;
	private int x;
	private int y;
	private boolean dragging = false;

	private double renderWidth;
	
	public BrightNessSlider(Setting value, Button button, int offset) {
		this.set = value;
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
	}
	@Override
	public void renderComponentRect() {
		    Gui.drawRect(parent.parent.getX(), parent.parent.getY()+ offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + parent.parent.getBarHeight()+ offset, new Color(25,25,25).getRGB());	
			 RenderUtil.instance.draw2DImage(new ResourceLocation("client/bright.png"), parent.parent.getX() + 2,  parent.parent.getY() + offset + 6, 86, 5, Color.WHITE);
				//	RenderUtil.instance.draw2DImage(new ResourceLocation("textures/hue.png"), parent.parent.getX() + 45,  parent.parent.getY() + offset + 6, 43, 5, Color.WHITE);
					 Gui.drawRect(parent.parent.getX() + 2 + (int) renderWidth - 2, parent.parent.getY() + offset + 5, parent.parent.getX() + (int) renderWidth + 3, parent.parent.getY() + offset + 12,this.hovered ? -1 : -1);
	}
	
	@Override
	public void renderComponent() {
		 GuiButton.ssss.drawString(this.set.getName() + ": " + this.set.getValDouble(), (parent.parent.getX() + 15), (parent.parent.getY() + offset - 2) + 1, -1);
		}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = isMouseOnButtonD(mouseX, mouseY) || isMouseOnButtonI(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
		
		double diff = Math.min(88, Math.max(0, mouseX - this.x));

		double min = 0;
		double max = 1;
		
		renderWidth = (88) * (set.getValDouble() - min) / (max - min);
		
		if (dragging) {
			if (diff == 0) {
				set.setValDouble(0);
			}
			else {
				double newValue = roundToPlace(((diff / 88) * (max - min) + min), 2);
				set.setValDouble(newValue);
			}
		}
	}
	
	private static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
			dragging = true;
		}
		if(isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
			dragging = true;
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		dragging = false;
	}
	
	public boolean isMouseOnButtonD(int x, int y) {
		if(x > this.x && x < this.x + (parent.parent.getWidth() / 2 + 1) && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
	
	public boolean isMouseOnButtonI(int x, int y) {
		if(x > this.x + parent.parent.getWidth() / 2 && x < this.x + parent.parent.getWidth() && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
