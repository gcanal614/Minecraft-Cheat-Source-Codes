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
import non.asset.utils.value.impl.RangedValue;

public class RangedNumberComponent extends Component {
    private double smooth1 = 0;
    private double smooth2 = 0;
    private double smooth3 = 0;
    private double smooth4 = 0;
    private double smooth5 = 0;
    private double smooth6 = 0;
    private double smooth7 = 0;
    private double smooth8 = 0;
    private RangedValue rangedValue;
    private boolean leftDown, rightDown;

    public RangedNumberComponent(RangedValue rangedValue, float posX, float posY, float offsetX, float offsetY, float width, float height) {
        super(rangedValue.getLabel(), posX, posY, offsetX, offsetY, width, height);
        this.rangedValue = rangedValue;
    }

    @Override
    public void componentMoved(float movedX, float movedY) {
        super.componentMoved(movedX, movedY);
    }

    @Override
    public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
        super.onDrawScreen(mouseX, mouseY, partialTicks);
        ClickGui clickgui = (ClickGui) Clarinet.INSTANCE.getModuleManager().getModuleClass(ClickGui.class);
        final float startX = getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + 4;
        final float sliderlenght = getWidth() - MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) - 10;
        final float rightX = MathHelper.floor_double((rangedValue.getRightVal().floatValue() - rangedValue.getMinimum().floatValue()) / (rangedValue.getMaximum().floatValue() - rangedValue.getMinimum().floatValue()) * sliderlenght);
        final float leftX = MathHelper.floor_double((rangedValue.getLeftVal().floatValue() - rangedValue.getMinimum().floatValue()) / (rangedValue.getMaximum().floatValue() - rangedValue.getMinimum().floatValue()) * sliderlenght);
        smooth1 = AnimationUtils.animate(smooth1, leftX, 0.7);
        smooth2 = AnimationUtils.animate(smooth2, rightX, 0.7);
        
        MainMenu.dufnctrmgyot6m18.drawStringWithShadow(getLabel(), getPosX(), getPosY() + 3, new Color(229, 229, 223, 255).getRGB());
        RenderUtil.drawRoundedRect(getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + 4, getPosY() + 5, getWidth() - MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) - 10, 2, 2, new Color(55, 55, 55, 255).getRGB());
        RenderUtil.drawRoundedRect(startX + smooth1, getPosY() + 5, smooth2 - smooth1, 2, 2, HUD.getDefaultColor);
        RenderUtil.drawCircle((float) (getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + smooth1 + 2), getPosY() + 4f, 4, HUD.getDefaultColor);
        RenderUtil.drawCircle((float) (getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + smooth2 + 2), getPosY() + 4f, 4, HUD.getDefaultColor);
        MainMenu.dufnctrmgyot6m18.drawStringWithShadow(String.valueOf(rangedValue.getLeftVal()),(float) (getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + smooth1 + 2),getPosY(),-1);
        MainMenu.dufnctrmgyot6m18.drawStringWithShadow(String.valueOf(rangedValue.getRightVal()),(float) (getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + smooth2 + 2),getPosY(),-1);
        if (leftDown) {
            if (rangedValue.getLeftVal() instanceof Double) {
                rangedValue.setLeftVal(round(((mouseX - startX) * (rangedValue.getMaximum().doubleValue() - rangedValue.getMinimum().doubleValue()) / sliderlenght + rangedValue.getMinimum().doubleValue())));
            }
            if (rangedValue.getLeftVal() instanceof Float) {
                rangedValue.setLeftVal((float) round(((mouseX - startX) * (rangedValue.getMaximum().floatValue() - rangedValue.getMinimum().floatValue()) / sliderlenght + rangedValue.getMinimum().floatValue())));
            }
            if (rangedValue.getLeftVal() instanceof Long) {
                rangedValue.setLeftVal((long) round(((mouseX - startX) * (rangedValue.getMaximum().longValue() - rangedValue.getMinimum().longValue()) / sliderlenght + rangedValue.getMinimum().longValue())));
            }
            if (rangedValue.getLeftVal() instanceof Integer) {
                rangedValue.setLeftVal((int) round(((mouseX - startX) * (rangedValue.getMaximum().intValue() - rangedValue.getMinimum().intValue()) / sliderlenght + rangedValue.getMinimum().intValue())));
            }
            if (rangedValue.getLeftVal() instanceof Short) {
                rangedValue.setLeftVal((short) round(((mouseX - startX) * (rangedValue.getMaximum().shortValue() - rangedValue.getMinimum().shortValue()) / sliderlenght + rangedValue.getMinimum().shortValue())));
            }
            if (rangedValue.getLeftVal() instanceof Byte) {
                rangedValue.setLeftVal((byte) round(((mouseX - startX) * (rangedValue.getMaximum().byteValue() - rangedValue.getMinimum().byteValue()) / sliderlenght + rangedValue.getMinimum().byteValue())));
            }
        }
        if (rightDown) {
            if (rangedValue.getRightVal() instanceof Double) {
                rangedValue.setRightVal(round(((mouseX - startX) * (rangedValue.getMaximum().doubleValue() - rangedValue.getMinimum().doubleValue()) / sliderlenght + rangedValue.getMinimum().doubleValue())));
            }
            if (rangedValue.getRightVal() instanceof Float) {
                rangedValue.setRightVal((float) round(((mouseX - startX) * (rangedValue.getMaximum().floatValue() - rangedValue.getMinimum().floatValue()) / sliderlenght + rangedValue.getMinimum().floatValue())));
            }
            if (rangedValue.getRightVal() instanceof Long) {
                rangedValue.setRightVal((long) round(((mouseX - startX) * (rangedValue.getMaximum().longValue() - rangedValue.getMinimum().longValue()) / sliderlenght + rangedValue.getMinimum().longValue())));
            }
            if (rangedValue.getRightVal() instanceof Integer) {
                rangedValue.setRightVal((int) round(((mouseX - startX) * (rangedValue.getMaximum().intValue() - rangedValue.getMinimum().intValue()) / sliderlenght + rangedValue.getMinimum().intValue())));
            }
            if (rangedValue.getRightVal() instanceof Short) {
                rangedValue.setRightVal((short) round(((mouseX - startX) * (rangedValue.getMaximum().shortValue() - rangedValue.getMinimum().shortValue()) / sliderlenght + rangedValue.getMinimum().shortValue())));
            }
            if (rangedValue.getRightVal() instanceof Byte) {
                rangedValue.setRightVal((byte) round(((mouseX - startX) * (rangedValue.getMaximum().byteValue() - rangedValue.getMinimum().byteValue()) / sliderlenght + rangedValue.getMinimum().byteValue())));
            }
        }
        if (rangedValue.getInc() instanceof Double) {
            if (leftDown) {
                if (rangedValue.getLeftVal().doubleValue() > rangedValue.getRightVal().doubleValue() - rangedValue.getInc().doubleValue())
                    rangedValue.setLeftVal(rangedValue.getRightVal().doubleValue() - rangedValue.getInc().doubleValue());
            }
            if (rightDown) {
                if (rangedValue.getRightVal().doubleValue() < rangedValue.getLeftVal().doubleValue() + rangedValue.getInc().doubleValue())
                    rangedValue.setRightVal(rangedValue.getLeftVal().doubleValue() + rangedValue.getInc().doubleValue());
            }
        }
        if (rangedValue.getInc() instanceof Float) {
            if (leftDown) {
                if (rangedValue.getLeftVal().floatValue() > rangedValue.getRightVal().floatValue() - rangedValue.getInc().floatValue())
                    rangedValue.setLeftVal(rangedValue.getRightVal().floatValue() - rangedValue.getInc().floatValue());
            }
            if (rightDown) {
                if (rangedValue.getRightVal().floatValue() < rangedValue.getLeftVal().floatValue() + rangedValue.getInc().floatValue())
                    rangedValue.setRightVal(rangedValue.getLeftVal().floatValue() + rangedValue.getInc().floatValue());
            }
        }
        if (rangedValue.getInc() instanceof Long) {
            if (leftDown) {
                if (rangedValue.getLeftVal().longValue() > rangedValue.getRightVal().longValue() - rangedValue.getInc().longValue())
                    rangedValue.setLeftVal(rangedValue.getRightVal().longValue() - rangedValue.getInc().longValue());
            }
            if (rightDown) {
                if (rangedValue.getRightVal().longValue() < rangedValue.getLeftVal().longValue() + rangedValue.getValue().longValue())
                    rangedValue.setRightVal(rangedValue.getLeftVal().longValue() + rangedValue.getInc().longValue());
            }
        }
        if (rangedValue.getInc() instanceof Integer) {
            if (leftDown) {
                if (rangedValue.getLeftVal().intValue() > rangedValue.getRightVal().intValue() - rangedValue.getInc().intValue())
                    rangedValue.setLeftVal(rangedValue.getRightVal().intValue() - rangedValue.getInc().intValue());
            }
            if (rightDown) {
                if (rangedValue.getRightVal().intValue() < rangedValue.getLeftVal().intValue() + rangedValue.getInc().intValue())
                    rangedValue.setRightVal(rangedValue.getLeftVal().intValue() + rangedValue.getInc().intValue());
            }
        }
        if (rangedValue.getInc() instanceof Short) {
            if (leftDown) {
                if (rangedValue.getLeftVal().shortValue() > rangedValue.getRightVal().shortValue() - rangedValue.getInc().shortValue())
                    rangedValue.setLeftVal(rangedValue.getRightVal().shortValue() - rangedValue.getInc().shortValue());
            }
            if (rightDown) {
                if (rangedValue.getRightVal().shortValue() < rangedValue.getLeftVal().shortValue() + rangedValue.getInc().shortValue())
                    rangedValue.setRightVal((short) (rangedValue.getLeftVal().shortValue() + rangedValue.getInc().shortValue()));
            }
        }
        if (rangedValue.getInc() instanceof Byte) {
            if (leftDown) {
                if (rangedValue.getLeftVal().byteValue() > rangedValue.getRightVal().byteValue() - rangedValue.getInc().byteValue())
                    rangedValue.setLeftVal(rangedValue.getRightVal().byteValue() - rangedValue.getInc().byteValue());
            }
            if (rightDown) {
                if (rangedValue.getRightVal().byteValue() < rangedValue.getLeftVal().byteValue() + rangedValue.getInc().byteValue())
                    rangedValue.setRightVal(rangedValue.getLeftVal().byteValue() + rangedValue.getInc().byteValue());
            }
        }
    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY, int button) {
        super.onMouseClicked(mouseX, mouseY, button);
        if (button == 0) {
            if (hoveredLeft(mouseX, mouseY)) leftDown = true;
            else if (hoveredRight(mouseX, mouseY)) rightDown = true;
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int button) {
        super.onMouseReleased(mouseX, mouseY, button);
        if (button == 0)
            leftDown = rightDown = false;
    }

    private boolean hoveredLeft(int mouseX, int mouseY) {
        final float leftX = MathHelper.floor_double((rangedValue.getLeftVal().floatValue() - rangedValue.getMinimum().floatValue()) / (rangedValue.getMaximum().floatValue() - rangedValue.getMinimum().floatValue()) * (getWidth() - MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) - 10));
        return MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + leftX, getPosY() + 4, 5, 3);
    }

    private boolean hoveredRight(int mouseX, int mouseY) {
        final float rightX = MathHelper.floor_double((rangedValue.getRightVal().floatValue() - rangedValue.getMinimum().floatValue()) / (rangedValue.getMaximum().floatValue() - rangedValue.getMinimum().floatValue()) * (getWidth() - MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) - 10));
        return MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX() + MainMenu.dufnctrmgyot6mh.getStringWidth(getLabel()) + 2 + rightX, getPosY() + 4, 5, 3);
    }

    private double round(final double val) {
        final double v = Math.round(val / rangedValue.getInc().doubleValue()) * rangedValue.getInc().doubleValue();
        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public RangedValue getRangedValue() {
        return rangedValue;
    }
}
