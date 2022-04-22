package non.asset.gui.materialui.component.impl.subcomponents;

import java.awt.Color;

import org.apache.commons.lang3.StringUtils;

import non.asset.Clarinet;
import non.asset.gui.MainMenu;
import non.asset.gui.materialui.component.Component;
import non.asset.module.impl.visuals.ClickGui;
import non.asset.module.impl.visuals.HUD;
import non.asset.utils.MouseUtil;
import non.asset.utils.RenderUtil;
import non.asset.utils.value.impl.EnumValue;

public class EnumComponent extends Component {
    private double smooth1 = 0;
    private double smooth2 = 0;
    private double smooth3 = 0;
    private double smooth4 = 0;
    private double smooth5 = 0;
    private double smooth6 = 0;
    private double smooth7 = 0;
    private double smooth8 = 0;
    private EnumValue enumValue;
    private boolean extended;

    public EnumComponent(EnumValue enumValue, float posX, float posY, float offsetX, float offsetY, float width, float height) {
        super(enumValue.getLabel(), posX, posY, offsetX, offsetY, width, height);
        this.enumValue = enumValue;
    }

    @Override
    public void componentMoved(float movedX, float movedY) {
        super.componentMoved(movedX, movedY);
    }

    @Override
    public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
        super.onDrawScreen(mouseX, mouseY, partialTicks);
        

        ClickGui clickgui = (ClickGui) Clarinet.INSTANCE.getModuleManager().getModuleClass(ClickGui.class);
        
        MainMenu.dufnctrmgyot6m18.drawStringWithShadow(getLabel(), getPosX(), getPosY() + 3, new Color(229, 229, 223, 255).getRGB());
        
        RenderUtil.drawRoundedRect(getPosX() + getWidth() - 124, getPosY() - 1.5f, 80, 15, 3, new Color(50, 50, 50, 255).getRGB());
        RenderUtil.drawOutlinedRoundedRect(getPosX() + getWidth() - 124, getPosY() - 1.5f, 80, 15, 3, 0.5f, HUD.getDefaultColor);
        MainMenu.dufnctrmgyot6m18.drawStringWithShadow(StringUtils.capitalize(enumValue.getValue().toString().toLowerCase()), getPosX() + getWidth() - 120, getPosY() + 3, new Color(229, 229, 223, 255).getRGB());
    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY, int button) {
        super.onMouseClicked(mouseX, mouseY, button);
        if (MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX() + getWidth() - 124, getPosY() - 1.5f, 80, 15)) {
            if(button == 0) {
            	getEnumValue().increment();
            }
            if(button == 1) {
            	getEnumValue().decrement();
            }
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int button) {
        super.onMouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void onKeyTyped(char character, int keyCode) {
        super.onKeyTyped(character, keyCode);
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public EnumValue getEnumValue() {
        return enumValue;
    }
}
