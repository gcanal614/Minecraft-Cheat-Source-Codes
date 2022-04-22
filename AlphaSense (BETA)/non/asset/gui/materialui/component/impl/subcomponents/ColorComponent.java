package non.asset.gui.materialui.component.impl.subcomponents;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import non.asset.gui.MainMenu;
import non.asset.gui.materialui.component.Component;
import non.asset.utils.MouseUtil;
import non.asset.utils.RenderUtil;
import non.asset.utils.value.impl.ColorValue;

public class ColorComponent extends Component {
    private double smooth1 = 0;
    private double smooth2 = 0;
    private double smooth3 = 0;
    private double smooth4 = 0;
    private double smooth5 = 0;
    private double smooth6 = 0;
    private double smooth7 = 0;
    private double smooth8 = 0;
    private ColorValue colorValue;
    private boolean pressedhue;
    private float pos, saturation, brightness;
    private boolean hovered;
    public ColorComponent(ColorValue colorValue, float posX, float posY, float offsetX, float offsetY, float width, float height) {
        super(colorValue.getLabel(), posX, posY, offsetX, offsetY, width, height);
        this.colorValue = colorValue;
        float[] hsb = new float[3];
        final Color clr = new Color(colorValue.getValue());
        hsb = Color.RGBtoHSB(clr.getRed(), clr.getGreen(), clr.getBlue(), hsb);
        this.saturation = hsb[1];
        this.brightness = hsb[2];
        pos = 0;
    }

    @Override
    public void componentMoved(float movedX, float movedY) {
        super.componentMoved(movedX, movedY);
    }

    @Override
    public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
        super.onDrawScreen(mouseX, mouseY, partialTicks);
        MainMenu.dufnctrmgyot6m18.drawStringWithShadow(getLabel(), getPosX(), getPosY() + 3, new Color(229, 229, 223, 255).getRGB());
        Keyboard.enableRepeatEvents(true);
        hovered = MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX() + getWidth() - 100,getPosY() + 4.0f, getWidth() - 70, 4);
        for (float i = 0; i < getWidth() - 70; i++) {
            int color = Color.getHSBColor(i / (getWidth() - 70), saturation, brightness).getRGB();
            RenderUtil.drawRect((getPosX() + getWidth() - 100) + i, getPosY() + 4.0f, 1, 4, color);
            if (mouseX == (int)(getPosX() + getWidth() - 100) + i) {
                if (pressedhue) {
                    colorValue.setValue(color);
                    pos = i;
                }
            }
        }
        RenderUtil.drawRect(getPosX() + getWidth() - 100 + pos, getPosY() + 3.5f, 1, 4.5f,  0xffffffff);
        RenderUtil.drawOutlinedRoundedRect(getPosX()+ getWidth() - 100.5f,getPosY() + 3.5f, getWidth() - 69,5,3,2,new Color(45, 45, 45, 255).getRGB());
    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY, int button) {
        super.onMouseClicked(mouseX, mouseY, button);
        final boolean hovered = MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX()+ getWidth() - 100.5f,getPosY() + 3f, getWidth() - 69,5);
        if (button == 0) {
            if (hovered) {
                pressedhue = true;
            }
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int button) {
        super.onMouseReleased(mouseX, mouseY, button);
        if (button == 0) {
            if (pressedhue) {
                pressedhue = false;
            }
        }
    }
    @Override
    public void onKeyTyped(char keyChar, int key) {
        super.onKeyTyped(keyChar, key);
    }
    public ColorValue getColorValue() {
        return colorValue;
    }
}
