package non.asset.gui.materialui.component.impl.subcomponents;

import java.awt.Color;

import non.asset.Clarinet;
import non.asset.gui.MainMenu;
import non.asset.gui.materialui.component.Component;
import non.asset.module.impl.visuals.ClickGui;
import non.asset.module.impl.visuals.HUD;
import non.asset.utils.AnimationUtils;
import non.asset.utils.MouseUtil;
import non.asset.utils.RenderUtil;
import non.asset.utils.value.impl.BooleanValue;

public class BooleanComponent extends Component {
    private double smooth1 = 0;
    private double smooth2 = 0;
    private double smooth3 = 0;
    private double smooth4 = 0;
    private double smooth5 = 0;
    private double smooth6 = 0;
    private double smooth7 = 0;
    private double smooth8 = 0;
    private BooleanValue booleanValue;
    public BooleanComponent(BooleanValue booleanValue, float posX, float posY, float offsetX, float offsetY, float width, float height) {
        super(booleanValue.getLabel(), posX, posY, offsetX, offsetY, width, height);
        this.booleanValue = booleanValue;
    }

    @Override
    public void componentMoved(float movedX, float movedY) {
        super.componentMoved(movedX, movedY);
    }

    @Override
    public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
        super.onDrawScreen(mouseX, mouseY, partialTicks);

        ClickGui clickgui = (ClickGui) Clarinet.INSTANCE.getModuleManager().getModuleClass(ClickGui.class);
        
        MainMenu.dufnctrmgyot6m18.drawStringWithShadow(getLabel(), getPosX() + 15, getPosY() + 3, new Color(229, 229, 223, 255).getRGB());
        RenderUtil.drawOutlinedRoundedRect(getPosX(),getPosY(),10,10,5,1,new Color(0xff202020).getRGB());
        if (booleanValue.isEnabled()) {
            smooth1 = AnimationUtils.animate(smooth1, 10, 0.6);
            RenderUtil.drawRoundedRect(getPosX(),getPosY(),10, smooth1,5, HUD.getDefaultColor);
        }else {
        	smooth1 = AnimationUtils.animate(smooth1, 0, 0.6);
        	if(smooth1 > 3) {
        		RenderUtil.drawRoundedRect(getPosX(),getPosY(),10,smooth1,5, HUD.getDefaultColor);
        	}
        }
    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY, int button) {
        super.onMouseClicked(mouseX, mouseY, button);
        if (button == 0 && MouseUtil.mouseWithinBounds(mouseX,mouseY,getPosX(),getPosY(),10,10)) {
            booleanValue.setEnabled(!booleanValue.isEnabled());
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

    public BooleanValue getBooleanValue() {
        return booleanValue;
    }
}
