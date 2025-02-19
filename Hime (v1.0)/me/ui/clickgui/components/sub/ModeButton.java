package me.ui.clickgui.components.sub;



import java.awt.Color;

import me.settings.Setting;
import me.ui.clickgui.components.Component;
import net.minecraft.client.gui.GuiButton;

public class ModeButton extends Component {

	private boolean hovered;
	private Setting set;
	private int x;
	private int y;
	
	private int modeIndex;
	
	public ModeButton(Setting set, int x, int y) {
		this.set = set;
		this.x = x;
		this.y = y;
		this.modeIndex = 0;
	}
	

	@Override
	public void renderComponent() {
	
	//	Gui.drawRect(this.x- 21, this.y, this.x + 8, this.y + 8, 0xFF111111);
	//	GL11.glPushMatrix();
		//GL11.glScalef(0.9f,0.9f, 0.9f);
	//	Gui.drawRect(x + Hime.instance.cfr.getWidth(set.getName()) / 10 - 10, y - 1, x + 10 + Hime.instance.cfr.getWidth(set.getName()) / 2, y + 12, Color.BLACK.getRGB());
	
		GuiButton.appleSmall.drawString(set.getName(),x - 100, y, -1);	
		GuiButton.appleSmall.drawString(set.getValString(), x + 15, y, Color.GRAY.getRGB());
		///GL11.glPopMatrix();
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = isMouseOnButton(mouseX, mouseY);
	}
	
	@Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if(isMouseOnButton(mouseX, mouseY) && button == 0) {
            int maxIndex = set.getOptions().size();

            if(modeIndex > maxIndex - 2)
                modeIndex = 0;
            else
                modeIndex++;

            set.setValString(set.getOptions().get(modeIndex));
        }
    }
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > this.x + 5 && x < this.x + 60 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}