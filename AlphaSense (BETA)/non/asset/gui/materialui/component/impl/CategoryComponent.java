package non.asset.gui.materialui.component.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import non.asset.Clarinet;
import non.asset.gui.MainMenu;
import non.asset.gui.materialui.component.Component;
import non.asset.gui.materialui.component.impl.subcomponents.ModuleComponent;
import non.asset.module.Module;
import non.asset.utils.AnimationUtils;
import non.asset.utils.MouseUtil;
import non.asset.utils.RenderUtil;
import non.asset.utils.RenderUtils;

public class CategoryComponent extends Component {
    private double smooth1 = 0;
    private double smooth2 = 0;
    private double smooth3 = 0;
    private double smooth4 = 0;
    private double smooth5 = 0;
    private double smooth6 = 0;
    private double smooth7 = 0;
    private double smooth8 = 0;
    private Module.Category category;
    private ArrayList<Component> components = new ArrayList<>();
    private int scrollY;

    public CategoryComponent(Module.Category category, float posX, float posY, float offsetX, float offsetY, float width, float height) {
        super(StringUtils.capitalize(category.name().toLowerCase()), posX, posY, offsetX, offsetY, width, height);
        this.category = category;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        float moduleOffsetY = 0;
        for (Module module : Clarinet.INSTANCE.getModuleManager().getModulesInCategory(category)) {
            components.add(new ModuleComponent(this, module, getPosX(), getPosY(), 0, moduleOffsetY, 90, 20));
            moduleOffsetY += 20;
        }
        components.forEach(Component::initializeComponent);
    }

    @Override
    public void componentMoved(float movedX, float movedY) {
        super.componentMoved(movedX, movedY);
        components.forEach(component -> component.componentMoved(getPosX(), getPosY()));
    }

    @Override
    public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
        super.onDrawScreen(mouseX, mouseY, partialTicks);
        double scrollbarHeight = (getHeight() / getComponentHeight()) * getHeight();
        
        if (MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX() - 2, getPosY(), 95, getHeight()) && getComponentHeight() >= getHeight()) {
           
        	int wheel = Mouse.getDWheel();
            
        	if (wheel < 0) {
                if (getScrollY() - 6 < -(getComponentHeight() - getHeight()))
                    setScrollY((int) -(getComponentHeight() - getHeight()));
                else setScrollY(getScrollY() - 9);
            } else if (wheel > 0) {
                setScrollY(getScrollY() + 9);
            }
        }
        if (getScrollY() > 0) setScrollY(0);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        
        RenderUtil.prepareScissorBox(new ScaledResolution(Minecraft.getMinecraft()), getPosX() - 2, getPosY() - 2, getWidth(), getHeight());

        for (Component component : getComponents()) {
            if (component instanceof ModuleComponent) {
                final ModuleComponent moduleComponent = (ModuleComponent) component;
                moduleComponent.onDrawScreen(mouseX, mouseY, partialTicks);
                moduleComponent.setOffsetY(component.getOriginalOffsetY() + getScrollY());
                moduleComponent.componentMoved(getPosX(), getPosY());
            }
        }
        
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
        
        if (getComponents().stream().filter(component -> component instanceof ModuleComponent && ((ModuleComponent) component).isExtended()).toArray().length > 0) {
            final ModuleComponent moduleComponent = (ModuleComponent) getComponents().stream().filter(component -> component instanceof ModuleComponent && ((ModuleComponent) component).isExtended()).collect(Collectors.toList()).get(0);
        }
    }


    @Override
    public void onMouseClicked(int mouseX, int mouseY, int button) {
        super.onMouseClicked(mouseX, mouseY, button);
        if (MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX() - 2, getPosY() - 2, getWidth(), getHeight())) {
            for (Component component : getComponents()) {
                if (component instanceof ModuleComponent) {
                    final ModuleComponent moduleComponent = (ModuleComponent) component;
                    moduleComponent.onMouseClicked(mouseX, mouseY, button);
                }
            }
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int button) {
        super.onMouseReleased(mouseX, mouseY, button);
        for (Component component : getComponents()) {
            if (component instanceof ModuleComponent) {
                final ModuleComponent moduleComponent = (ModuleComponent) component;
                moduleComponent.onMouseReleased(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void onKeyTyped(char character, int keyCode) {
        super.onKeyTyped(character, keyCode);
        for (Component component : getComponents()) {
            if (component instanceof ModuleComponent) {
                final ModuleComponent moduleComponent = (ModuleComponent) component;
                moduleComponent.onKeyTyped(character, keyCode);
            }
        }
    }

    public Module.Category getCategory() {
        return category;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public int getScrollY() {
        return scrollY;
    }

    public void setScrollY(int scrollY) {
        this.scrollY = scrollY;
    }

    public int getComponentHeight() {
        int h = 0;
        for (Component component : getComponents()) {
            h += component.getHeight();
        }
        return h;
    }
}
