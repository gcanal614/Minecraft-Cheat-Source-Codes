package non.asset.gui.materialui.plane.impl;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import non.asset.Clarinet;
import non.asset.gui.MainMenu;
import non.asset.gui.materialui.component.Component;
import non.asset.gui.materialui.component.impl.CategoryComponent;
import non.asset.gui.materialui.plane.Plane;
import non.asset.module.Module;
import non.asset.module.impl.visuals.ClickGui;
import non.asset.module.impl.visuals.HUD;
import non.asset.utils.AnimationUtils;
import non.asset.utils.MouseUtil;
import non.asset.utils.RenderUtil;

public class MainPlane extends Plane {
    private Module.Category selectedCategory = Module.Category.COMBAT;
    private double smooth1 = 0;
    private double smooth2 = 0;
    private double smooth3 = 0;
    private double smooth4 = 0;
    private double smooth5 = 0;
    private double smooth6 = 0;
    private double smooth7 = 0;
    private double smooth8 = 0;
    private ArrayList<Component> components = new ArrayList<>();
    public MainPlane(String label, float posX, float posY, float width, float height) {
        super(label, posX, posY, width, height);
    }

    @Override
    public void initializePlane() {
        super.initializePlane();
        for (Module.Category category : Module.Category.values()) {
            components.add(new CategoryComponent(category,getPosX(),getPosY(),46.5f,45f,getWidth() - 46.5f,getHeight() - 45f));
        }
        components.forEach(Component::initializeComponent);
    }

    @Override
    public void planeMoved(float movedX, float movedY) {
        super.planeMoved(movedX, movedY);
        components.forEach(component -> component.componentMoved(movedX, movedY));
    }

    @Override
    public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
        super.onDrawScreen(mouseX, mouseY, partialTicks);
        
        
        ClickGui clickgui = (ClickGui) Clarinet.INSTANCE.getModuleManager().getModuleClass(ClickGui.class);
        
        if (isDragging()) {
            setPosX(mouseX + getLastPosX());
            setPosY(mouseY + getLastPosY());
            planeMoved(getPosX(), getPosY());
        }
        if (getPosX() < 0 + 10) {
            setPosX(0 + 10);
            planeMoved(getPosX(), getPosY());
        }
        if (getPosX() + getWidth() > new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth()) {
            setPosX(new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() - getWidth());
            planeMoved(getPosX(), getPosY());
        }
        if (getPosY() < 0) {
            setPosY(0);
            planeMoved(getPosX(), getPosY());
        }
        if (getPosY() + getHeight() > new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight()) {
            setPosY(new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() - getHeight());
            planeMoved(getPosX(), getPosY());
        }
        RenderUtil.drawRoundedRect(getPosX() - 12, getPosY() - 2, getWidth() + 14, getHeight() + 4, 6, new Color(15, 15, 15, 255).getRGB());
        RenderUtil.drawRoundedRect(getPosX() - 10, getPosY(), getWidth() + 10, getHeight(), 6, new Color(25, 25, 25, 255).getRGB());
        
        float categoryOffsetY = getPosY() + 55;
        for (Module.Category category : Module.Category.values()) {
            if (getSelectedCategory() == category) {
            	smooth1 = AnimationUtils.animate(smooth1, categoryOffsetY, 0.9);
            	RenderUtil.drawRoundedRect(getPosX(), smooth1 - (MainMenu.dufnctrmgyot6mh.getHeight() * 2.5f) / 4 - 4, 2f, MainMenu.dufnctrmgyot6mh.getHeight() * 2.5f - 5, 1,HUD.getDefaultColor);
            }
            
            //RenderUtils.drawRoundedRect(getPosX() - 10, getPosY(), getWidth() + 10, 36, clickgui.rounded.getValue(), new Color(35, 35, 35, 255).getRGB());
            
            MainMenu.dufnctrmgyot6mh.drawString(category.getCharacter().toUpperCase(), getPosX() + 7, categoryOffsetY - 4, getSelectedCategory() == category ? HUD.getDefaultColor : new Color(229, 229, 223, 255).getRGB());
            
            categoryOffsetY += MainMenu.dufnctrmgyot6mh.getHeight() * 2.5f;
        }
        RenderUtil.drawRoundedRect(getPosX() + 42.5, getPosY(), 100, getHeight(), 5,new Color(45, 45, 45, 155).getRGB());
        
        for (Component component : getComponents()) {
            if (component instanceof CategoryComponent) {
                final CategoryComponent categoryComponent = (CategoryComponent) component;
                if (categoryComponent.getCategory() == getSelectedCategory())
                    categoryComponent.onDrawScreen(mouseX, mouseY, partialTicks);
            }
        }
    }

    @Override
    public void onMouseClicked(int mouseX, int mouseY, int button) {
        super.onMouseClicked(mouseX, mouseY, button);
        final boolean hovered = MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX(), getPosY(), getWidth(), 15);
        if (button == 0) {
            if (hovered) {
                setLastPosX(getPosX() - mouseX);
                setLastPosY(getPosY() - mouseY);
                setDragging(true);
            }
            float categoryOffsetY = getPosY() + 55;
            for (Module.Category category : Module.Category.values()) {
                if (MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX(), categoryOffsetY - (MainMenu.dufnctrmgyot6mh.getHeight() * 2.5f) / 4 - 4, 42.5f, MainMenu.dufnctrmgyot6mh.getHeight() * 2.5f))
                    setSelectedCategory(category);
                categoryOffsetY += MainMenu.dufnctrmgyot6mh.getHeight() * 2.5f;
            }
        }
        for (Component component : getComponents()) {
            if (component instanceof CategoryComponent) {
                final CategoryComponent categoryComponent = (CategoryComponent) component;
                if (categoryComponent.getCategory() == getSelectedCategory())
                    categoryComponent.onMouseClicked(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int button) {
        super.onMouseReleased(mouseX, mouseY, button);
        if (button == 0 && isDragging()) {
            setDragging(false);
        }
        for (Component component : getComponents()) {
            if (component instanceof CategoryComponent) {
                final CategoryComponent categoryComponent = (CategoryComponent) component;
                if (categoryComponent.getCategory() == getSelectedCategory())
                    categoryComponent.onMouseReleased(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void onKeyTyped(char character, int keyCode) {
        super.onKeyTyped(character, keyCode);
        for (Component component : getComponents()) {
            if (component instanceof CategoryComponent) {
                final CategoryComponent categoryComponent = (CategoryComponent) component;
                if (categoryComponent.getCategory() == getSelectedCategory())
                    categoryComponent.onKeyTyped(character, keyCode);
            }
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        for (Component component : getComponents()) {
            if (component instanceof CategoryComponent) {
                final CategoryComponent categoryComponent = (CategoryComponent) component;
                if (categoryComponent.getCategory() == getSelectedCategory()) categoryComponent.onGuiClosed();
            }
        }
    }

    public Module.Category getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Module.Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }
}
