package non.asset.gui.materialui.component.impl.subcomponents;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import net.minecraft.util.MathHelper;
import non.asset.Clarinet;
import non.asset.gui.MainMenu;
import non.asset.gui.materialui.component.Component;
import non.asset.module.impl.visuals.ClickGui;
import non.asset.module.impl.visuals.HUD;
import non.asset.utils.AnimationUtils;
import non.asset.utils.MouseUtil;
import non.asset.utils.RenderUtil;
import non.asset.utils.value.impl.NumberValue;

public class NumberComponent extends Component {
    private double smooth1 = 0;
    private double smooth2 = 0;
    private double smooth3 = 0;
    private double smooth4 = 0;
    private double smooth5 = 0;
    private double smooth6 = 0;
    private double smooth7 = 0;
    private double smooth8 = 0;
    private NumberValue numberValue;
    private boolean sliding;
    public NumberComponent(NumberValue numberValue, float posX, float posY, float offsetX, float offsetY, float width, float height) {
        super(numberValue.getLabel(), posX, posY, offsetX, offsetY, width, height);
        this.numberValue = numberValue;
    }

    @Override
    public void componentMoved(float movedX, float movedY) {
        super.componentMoved(movedX, movedY);
    }

    @Override
    public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
        super.onDrawScreen(mouseX, mouseY, partialTicks);
        
        HUD hud = (HUD) Clarinet.INSTANCE.getModuleManager().getModule("hud");

        ClickGui clickgui = (ClickGui) Clarinet.INSTANCE.getModuleManager().getModuleClass(ClickGui.class);
        
        final float sliderWidth = MathHelper.floor_double(((numberValue.getValue()).floatValue() - numberValue.getMinimum().floatValue()) / (numberValue.getMaximum().floatValue() - numberValue.getMinimum().floatValue()) * (getWidth() - MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) - 10));
        
        MainMenu.dufnctrmgyot6m18.drawStringWithShadow(getLabel(), getPosX(), getPosY() + 3, new Color(229, 229, 223, 255).getRGB());
        smooth1 = AnimationUtils.animate(smooth1, sliderWidth, 0.6);
        RenderUtil.drawRoundedRect(getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + 4, getPosY() + 5, getWidth() - MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) - 10, 2, 2, new Color(55, 55, 55, 255).getRGB());
        RenderUtil.drawRoundedRect(getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + 4, getPosY() + 5, smooth1, 2, 2, HUD.getDefaultColor);
        
        RenderUtil.drawCircle((float) (getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + smooth1 + 3),getPosY() + 4f,4,HUD.getDefaultColor);
        MainMenu.dufnctrmgyot6m18.drawStringWithShadow(String.valueOf(numberValue.getValue()),(float) (getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + smooth1 + 3),getPosY(),-1);
        if (sliding) {
            if (numberValue.getValue() instanceof Double) {
                numberValue.setValue(round(((mouseX - (getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + 4)) * (numberValue.getMaximum().doubleValue() - numberValue.getMinimum().doubleValue()) / ((getWidth() - MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) - 10)) + numberValue.getMinimum().doubleValue()), 2));
            } else if (numberValue.getValue() instanceof Float) {
                numberValue.setValue((float) round(((mouseX - (getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + 4)) * (numberValue.getMaximum().floatValue() - numberValue.getMinimum().floatValue()) / ((getWidth() - MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) - 10)) + numberValue.getMinimum().floatValue()), 2));
            } else if (numberValue.getValue() instanceof Long) {
                numberValue.setValue((long) round(((mouseX - (getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + 4)) * (numberValue.getMaximum().longValue() - numberValue.getMinimum().longValue()) / ((getWidth() - MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) - 10)) + numberValue.getMinimum().longValue()), 2));
            } else if (numberValue.getValue() instanceof Integer) {
                numberValue.setValue((int) round((((mouseX - (getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + 4))) * (numberValue.getMaximum().intValue() - numberValue.getMinimum().intValue()) / ((getWidth() - MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) - 10)) + numberValue.getMinimum().intValue()), 2));
            } else if (numberValue.getValue() instanceof Short) {
                numberValue.setValue((short) round((((mouseX - (getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + 4))) * (numberValue.getMaximum().shortValue() - numberValue.getMinimum().shortValue()) / ((getWidth() - MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) - 10)) + numberValue.getMinimum().shortValue()), 2));
            } else if (numberValue.getValue() instanceof Byte) {
                numberValue.setValue((byte) round((((mouseX - (getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + 4))) * (numberValue.getMaximum().byteValue() - numberValue.getMinimum().byteValue()) / ((getWidth() - MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) - 10)) + numberValue.getMinimum().byteValue()), 2));
            }
        }
    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY, int button) {
        super.onMouseClicked(mouseX, mouseY, button);
        final boolean hovered = MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + 2, getPosY() + 5, getWidth() - MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) - 8, 2);
        if (button == 0) {
            if (hovered) {
                sliding = true;
            }
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int button) {
        super.onMouseReleased(mouseX, mouseY, button);
        if (button == 0 && sliding) sliding = false;
    }

    @Override
    public void onKeyTyped(char character, int keyCode) {
        super.onKeyTyped(character, keyCode);
    }

    private double round(final double val, final int places) {
        final double v = Math.round(val / numberValue.getInc().doubleValue()) * numberValue.getInc().doubleValue();
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public NumberValue getNumberValue() {
        return numberValue;
    }
}
