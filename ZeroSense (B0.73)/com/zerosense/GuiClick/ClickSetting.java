package com.zerosense.GuiClick;

import com.zerosense.Settings.BooleanSetting;
import com.zerosense.Settings.ModeSetting;
import com.zerosense.Settings.NumberSetting;
import com.zerosense.Settings.Setting;
import com.zerosense.Utils.Component;
import net.minecraft.client.gui.Gui;

public class ClickSetting extends Component {
    int index;
    Setting setting;
    ClickModule parent;
    public ClickSetting(int index, Setting setting, ClickModule parent) {
        super(0, 0, 100, 20);
        this.parent = parent;
        this.setting = setting;
        this.index = index;
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (setting instanceof BooleanSetting)
                ((BooleanSetting) setting).setToggled(!((BooleanSetting) setting).isToggled());
            if (setting instanceof ModeSetting)
                ((ModeSetting) setting).cycle(false);
        }
        if (mouseButton == 1) {
            if (setting instanceof BooleanSetting)
                ((BooleanSetting) setting).setToggled(!((BooleanSetting) setting).isToggled());
            if (setting instanceof ModeSetting)
                ((ModeSetting) setting).cycle(true);
        }
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (!parent.showSettings)
            return;
        if (mouseOn(mouseX, mouseY))
            onClick(mouseX, mouseY, mouseButton);
    }

    public void draw(int ind, int mouseX, int mouseY) {
        this.index = ind;
        setPosition(parent.getX()+ parent.getWidth()/2+getWidth()/2, parent.getY() + getHeight()*index-parent.getHeight()/2+getHeight()/2);

        if (isPressed(mouseX, mouseY, 0) && setting instanceof NumberSetting) {
            NumberSetting numberSetting = (NumberSetting) setting;

            int relativeMouseX = mouseX - getLeft();
            numberSetting.setValue(relativeMouseX * (numberSetting.getMax() - numberSetting.getMin()) / getWidth() + numberSetting.getMin());
        }

        if (!(setting instanceof BooleanSetting)) {
            Gui.drawRect(getLeft(),getBottom(),getRight(),getTop(), 0x77000000);
        } else {
            Gui.drawRect(getLeft(),getBottom(),getRight(),getTop(), ((BooleanSetting) setting).isToggled() ? 0x77515151 : 0x77000000);
        }
        if (setting instanceof NumberSetting) {
            NumberSetting numberSetting = (NumberSetting) setting;

            Gui.drawRect(getLeft(), getTop(), (int) (getLeft() + numberSetting.getValue() / numberSetting.getMax() * getWidth()), getBottom(), 0x77616161);
        }

        String txt = setting.getName() + " ";
        if (setting instanceof NumberSetting)
            txt += ((NumberSetting) setting).getValue();
        if (setting instanceof ModeSetting && mouseOn(mouseX, mouseY))
            txt = ((ModeSetting) setting).getMode();

        mc.fontRendererObj.drawString(txt, x-mc.fontRendererObj.getStringWidth(txt)/2,y-mc.fontRendererObj.FONT_HEIGHT/2,0xFFFFFFFF);
    }
}
