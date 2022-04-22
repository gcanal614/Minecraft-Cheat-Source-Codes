package me.ui.clickgui2.component.components;

import java.awt.Color;
import java.util.ArrayList;

import me.Hime;
import me.module.Module;
import me.module.Module.Category;
import me.settings.Setting;
import me.ui.clickgui2.ClickGui;
import me.ui.clickgui2.component.Component;
import me.ui.clickgui2.component.Frame;
import me.ui.clickgui2.component.components.sub.Checkbox;
import me.ui.clickgui2.component.components.sub.Keybind;
import me.ui.clickgui2.component.components.sub.ModeButton;
import me.ui.clickgui2.component.components.sub.Slider;
import me.ui.clickgui2.component.components.sub.VisibleButton;
import me.ui.clickgui2.component.components.sub.color.BrightNessSlider;
import me.ui.clickgui2.component.components.sub.color.HueSlider;
import me.ui.clickgui2.component.components.sub.color.SaturationSlider;
import me.util.ColorUtil;
import me.util.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class Button extends Component {

	public Module mod;
	public Frame parent;
	public int offset;
	public int animatex;

	private boolean isHovered;
	private ArrayList<Component> subcomponents;
	public boolean open;
	public boolean animate;
	private int height;
	public int fade;
	
	public Button(Module mod, Frame parent, int offset) {
		this.mod = mod;
		this.parent = parent;
		this.offset = offset;
		this.subcomponents = new ArrayList<Component>();
		this.open = false;
		height = 12;
		int opY = offset + 12;
		if(Hime.instance.settingsManager.getSettingsByMod(mod) != null) {
			for(Setting s : Hime.instance.settingsManager.getSettingsByMod(mod)){
				if(s.isCombo()){
					this.subcomponents.add(new ModeButton(s, this, mod, opY));
					opY += 12;
				}
				if(s.isSlider()){
					this.subcomponents.add(new Slider(s, this, opY));
					opY += 12;
				}
				if(s.isCheck()){
					this.subcomponents.add(new Checkbox(s, this, opY));
					opY += 12;
				}
				if(s.isHueSlider()){
					this.subcomponents.add(new HueSlider(s, this, opY));
					opY += 12;
				}
				if(s.isBrightSlider()){
					this.subcomponents.add(new BrightNessSlider(s, this, opY));
					opY += 12;
				}
				if(s.isSaturationSlider()){
					this.subcomponents.add(new SaturationSlider(s, this, opY));
					opY += 12;
				}
			}
		}
		this.subcomponents.add(new Keybind(this, opY));
		this.subcomponents.add(new VisibleButton(this, mod, opY));
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
		int opY = offset + 12;
		for(Component comp : this.subcomponents) {
			comp.setOff(opY);
			opY += 12;
		}
	}
	
	@Override
	public void renderComponentRect() {
			if (mod.isToggled() && fade != 255) {
		                fade += 5;
		        } else {
		            if (!mod.isToggled() && fade != 0) {
		                fade -= 5;
		            }
		        }
			
			 final float hue = (float) (ColorUtil.getClickGUIColor());

		    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
				
		    Color colorFix = new Color(color.getRed(), color.getGreen(), color.getBlue(), fade);
		    Gui.drawRect(this.parent.getX(), this.parent.getY() + offset - 2, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + this.parent.getBarHeight()+ offset - 5, new Color(40,39,42).getRGB());
		    RenderUtil.drawRoundedRect2(this.parent.getX(), this.parent.getY() + offset, this.parent.getWidth(), this.parent.getBarHeight(), 3, new Color(40,39,42));
		RenderUtil.drawRoundedRect2(this.parent.getX(), this.parent.getY()+ offset, this.parent.getWidth(), this.parent.getBarHeight(), 3, mod.isToggled() ? new Color(25,0,25) : new Color(40,39,42));
		if(this.open) {

			if(!this.subcomponents.isEmpty()) {
		for(Component comp2 : this.subcomponents) {
			comp2.renderComponentRect();
		      }
			}
		}
	}
	
	@Override
	public void renderComponent() {
		if(this.parent.category == Category.TARGETS) {
				GuiButton.ssss.drawString(this.mod.getName(), parent.getX() + 2 , (parent.getY() + offset + 1) , -1);
			}else {
				GuiButton.aaaa.drawString(this.mod.getName(), parent.getX() + 2 , (parent.getY() + offset + 1) , -1);
		   if(Hime.instance.settingsManager.hasSettings(mod)) {
		   	if(this.open) {
		   		RenderUtil.instance.draw2DImage(new ResourceLocation("client/arrow4.png"), parent.getX() + parent.getWidth() - 12, (parent.getY() + offset + 2.5), 14, 7, Color.WHITE);
			}else {
		   		RenderUtil.instance.draw2DImage(new ResourceLocation("client/arrow3.png"), parent.getX() + parent.getWidth() - 12, (parent.getY() + offset + 2.5), 14, 7, Color.WHITE);
			}
		}
	}

		if(this.open) {
			if(!this.subcomponents.isEmpty()) {

				for(Component comp : this.subcomponents) {
					comp.renderComponent();
				}

				Gui.drawRect(parent.getX(), parent.getY() + this.offset, parent.getX() + 1, parent.getY() + this.offset + ((this.subcomponents.size() + 1) * 12) - 2, ClickGui.color);
			}

		}

	}
	
	@Override
	public int getHeight() {
		if(this.open) {
			return (12 * (this.subcomponents.size() + 1));
		}
		return 12;
	}
	
	@Override
	public int getOffset() {
		return offset; 
	}
	
	@Override
	public Module getMod() {
		return this.mod; 
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.isHovered = isMouseOnButton(mouseX, mouseY);
		if(!this.subcomponents.isEmpty()) {
			for(Component comp : this.subcomponents) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0) {
			this.mod.toggle();
		}
		if(isMouseOnButton(mouseX, mouseY) && button == 1) {
			this.open = !this.open;
			this.parent.refresh();
		}
		if(isMouseOnButton(mouseX, mouseY) && button == 0) {
			this.animatex = 0;
			this.animate = !this.animate;
		}
		for(Component comp : this.subcomponents) {
			comp.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		for(Component comp : this.subcomponents) {
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int key) {
		for(Component comp : this.subcomponents) {
			comp.keyTyped(typedChar, key);
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
			return true;
		}
		return false;
	}
}
