package non.asset.gui.materialui.component.impl.subcomponents;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import non.asset.gui.MainMenu;
import non.asset.gui.materialui.component.Component;
import non.asset.gui.materialui.component.impl.CategoryComponent;
import non.asset.module.Module;
import non.asset.utils.MouseUtil;
import non.asset.utils.value.Value;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.ColorValue;
import non.asset.utils.value.impl.EnumValue;
import non.asset.utils.value.impl.NumberValue;
import non.asset.utils.value.impl.RangedValue;

public class ModuleComponent extends Component {
    private double smooth1 = 0;
    private double smooth2 = 0;
    private double smooth3 = 0;
    private double smooth4 = 0;
    private double smooth5 = 0;
    private double smooth6 = 0;
    private double smooth7 = 0;
    private double smooth8 = 0;
    private Module module;
    private ArrayList<Component> components = new ArrayList<>();
    private boolean extended;
    private CategoryComponent categoryComponent;
    private int scrollY;

    public ModuleComponent(CategoryComponent categoryComponent, Module module, float posX, float posY, float offsetX, float offsetY, float width, float height) {
        super(module.getLabel(), posX, posY, offsetX, offsetY, width, height);
        this.module = module;
        this.categoryComponent = categoryComponent;
    }


    @Override
    public void initializeComponent() {
        super.initializeComponent();
        if (!module.getValues().isEmpty()) {
            float valueOffsetY = 0;
            for (Value value : module.getValues()) {
                if (value instanceof BooleanValue) {
                    components.add(new BooleanComponent((BooleanValue) value, getCategoryComponent().getPosX(), getCategoryComponent().getPosY(), 105, valueOffsetY, 165, 20));
                    valueOffsetY += 20;
                }
                if (value instanceof EnumValue) {
                    components.add(new EnumComponent((EnumValue) value, getCategoryComponent().getPosX(), getCategoryComponent().getPosY(), 105, valueOffsetY, 165, 20));
                    valueOffsetY += 20;
                }
                if (value instanceof NumberValue) {
                    components.add(new NumberComponent((NumberValue) value, getCategoryComponent().getPosX(), getCategoryComponent().getPosY(), 105, valueOffsetY, 165, 20));
                    valueOffsetY += 20;
                }
                if (value instanceof RangedValue) {
                    components.add(new RangedNumberComponent((RangedValue) value, getCategoryComponent().getPosX(), getCategoryComponent().getPosY(), 105, valueOffsetY, 165, 20));
                    valueOffsetY += 20;
                }
                if (value instanceof ColorValue) {
                    components.add(new ColorComponent((ColorValue) value, getCategoryComponent().getPosX(), getCategoryComponent().getPosY(), 105, valueOffsetY, 165, 20));
                    valueOffsetY += 20;
                }
            }
        }
        components.forEach(Component::initializeComponent);
    }

    @Override
    public void componentMoved(float movedX, float movedY) {
        super.componentMoved(movedX, movedY);
        if (isExtended()) {
            for (Component component : getComponents()) {
                component.componentMoved(getPosX(), getCategoryComponent().getPosY());
            }
        }
    }

    @Override
    public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
        super.onDrawScreen(mouseX, mouseY, partialTicks);
        double scrollbarHeight = (getCategoryComponent().getHeight() / getComponentHeight()) * getCategoryComponent().getHeight();
        
        
        if (MouseUtil.mouseWithinBounds(mouseX, mouseY, getCategoryComponent().getPosX() + 100, getCategoryComponent().getPosY(), 175, getCategoryComponent().getHeight()) && getComponentHeight() >= getCategoryComponent().getHeight()) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                if (getScrollY() - 6 < -(getComponentHeight() - getCategoryComponent().getHeight()))
                    setScrollY((int) -(getComponentHeight() - getCategoryComponent().getHeight()));
                else setScrollY(getScrollY() - 6);
            } else if (wheel > 0) {
                setScrollY(getScrollY() + 6);
            }
        }
        if (getScrollY() > 0) setScrollY(0);
        if (getComponentHeight() >= getCategoryComponent().getHeight()) {
            if (getScrollY() - 6 < -(getComponentHeight() - getCategoryComponent().getHeight()))
                setScrollY((int) -(getComponentHeight() - getCategoryComponent().getHeight()));
        } else if (getScrollY() < 0) setScrollY(0);
        
        MainMenu.dufnctrmgyot6m18.drawStringWithShadow(getLabel(), getPosX(), getPosY(), getModule().isEnabled() ? -1 : new Color(167, 167, 161, 255).getRGB());
        
        if (isExtended()) {
            for (Component component : getComponents()) {
                if (component.isHidden()) continue;
                component.onDrawScreen(mouseX, mouseY, partialTicks);
                component.setOffsetY(component.getOriginalOffsetY() + getScrollY());
                component.componentMoved(getPosX(), getCategoryComponent().getPosY());
            }
            setupHeight();
        }
    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY, int button) {
        super.onMouseClicked(mouseX, mouseY, button);
        if (button == 0 && MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX(), getPosY() - 4, getWidth(), getHeight() - 8)) {
            getModule().setEnabled(!getModule().isEnabled());
        }
        if (button == 1 && !getComponents().isEmpty() && MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX(), getPosY() - 4, getWidth(), getHeight() - 8)) {
            getCategoryComponent().getComponents().stream().filter(component -> component instanceof ModuleComponent && component != this).forEach(component -> ((ModuleComponent) component).setExtended(false));
            setExtended(!isExtended());
        }
        if (isExtended()) {
            for (Component component : getComponents()) {
                if (component.isHidden()) continue;
                component.onMouseClicked(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int button) {
        super.onMouseReleased(mouseX, mouseY, button);
        if (isExtended()) {
            for (Component component : getComponents()) {
                if (component.isHidden()) continue;
                component.onMouseReleased(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void onKeyTyped(char character, int keyCode) {
        super.onKeyTyped(character, keyCode);
        if (isExtended()) {
            for (Component component : getComponents()) {
                if (component.isHidden()) continue;
                component.onKeyTyped(character, keyCode);
            }
        }
    }

    public Module getModule() {
        return module;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public CategoryComponent getCategoryComponent() {
        return categoryComponent;
    }

    public int getComponentHeight() {
        int h = 0;
        for (Component component : getComponents()) {
            if (component.isHidden()) continue;
            h += component.getHeight();
        }
        return h;
    }

    public void setupHeight() {
        int h = getScrollY();
        for (Component component : getComponents()) {
            if (component instanceof BooleanComponent) {
                final BooleanComponent booleanComponent = (BooleanComponent) component;
                if (booleanComponent.getBooleanValue().getParentValueObject() != null && !booleanComponent.getBooleanValue().getParentValueObject().getValueAsString().equalsIgnoreCase(booleanComponent.getBooleanValue().getParentValue())) {
                    booleanComponent.setHidden(true);
                    continue;
                }
                booleanComponent.setHidden(false);
                component.setOffsetY(h);
                h += component.getHeight();
            }
            if (component instanceof NumberComponent) {
                final NumberComponent numberComponent = (NumberComponent) component;
                if (numberComponent.getNumberValue().getParentValueObject() != null && !numberComponent.getNumberValue().getParentValueObject().getValueAsString().equalsIgnoreCase(numberComponent.getNumberValue().getParentValue())) {
                    numberComponent.setHidden(true);
                    continue;
                }
                numberComponent.setHidden(false);
                component.setOffsetY(h);
                h += component.getHeight();
            }
            if (component instanceof ColorComponent) {
                final ColorComponent colorComponent = (ColorComponent) component;
                if (colorComponent.getColorValue().getParentValueObject() != null && !colorComponent.getColorValue().getParentValueObject().getValueAsString().equalsIgnoreCase(colorComponent.getColorValue().getParentValue())) {
                    colorComponent.setHidden(true);
                    continue;
                }
                colorComponent.setHidden(false);
                component.setOffsetY(h);
                h += component.getHeight();
            }
            if (component instanceof RangedNumberComponent) {
                final RangedNumberComponent rangedNumberComponent = (RangedNumberComponent) component;
                if (rangedNumberComponent.getRangedValue().getParentValueObject() != null && !rangedNumberComponent.getRangedValue().getParentValueObject().getValueAsString().equalsIgnoreCase(rangedNumberComponent.getRangedValue().getParentValue())) {
                    rangedNumberComponent.setHidden(true);
                    continue;
                }
                rangedNumberComponent.setHidden(false);
                component.setOffsetY(h);
                h += component.getHeight();
            }
            if (component instanceof EnumComponent) {
                final EnumComponent enumComponent = (EnumComponent) component;
                if (enumComponent.getEnumValue().getParentValueObject() != null && !enumComponent.getEnumValue().getParentValueObject().getValueAsString().equalsIgnoreCase(enumComponent.getEnumValue().getParentValue())) {
                    enumComponent.setHidden(true);
                    continue;
                }
                enumComponent.setHidden(false);
                component.setOffsetY(h);
                h += component.getHeight();
            }
        }
    }

    public int getScrollY() {
        return scrollY;
    }

    public void setScrollY(int scrollY) {
        this.scrollY = scrollY;
    }
}
