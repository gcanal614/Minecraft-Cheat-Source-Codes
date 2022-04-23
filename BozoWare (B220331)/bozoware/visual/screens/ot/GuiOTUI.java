// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.ot;

import java.io.IOException;
import bozoware.visual.screens.ot.component.impl.sub.OTDropdownComponent;
import bozoware.impl.property.EnumProperty;
import bozoware.visual.screens.ot.component.impl.sub.OTCheckboxComponent;
import bozoware.impl.property.BooleanProperty;
import bozoware.visual.screens.ot.component.impl.sub.OTSliderComponent;
import bozoware.impl.property.ValueProperty;
import bozoware.base.property.Property;
import bozoware.impl.module.combat.Aura;
import bozoware.base.module.Module;
import bozoware.base.module.ModuleCategory;
import bozoware.base.BozoWare;
import bozoware.base.util.visual.GLDraw;
import java.awt.Color;
import bozoware.base.util.visual.RenderUtil;
import bozoware.visual.screens.ot.component.impl.ModuleButtonComponent;
import bozoware.visual.screens.ot.component.impl.TabSelectorComponent;
import java.util.ArrayList;
import bozoware.visual.screens.ot.component.OTComponent;
import net.minecraft.client.gui.GuiScreen;

public class GuiOTUI extends GuiScreen
{
    public OTComponent rootComponent;
    private double rootDistX;
    private double rootDistY;
    private boolean draggingRoot;
    public final ArrayList<OTComponent> components;
    public TabSelectorComponent currentTabComponent;
    public ModuleButtonComponent currentModule;
    
    public GuiOTUI() {
        this.components = new ArrayList<OTComponent>();
    }
    
    public void setupGui() {
        this.rootComponent = new OTComponent(null, 50.0, 50.0, 300.0, 250.0) {
            @Override
            public void onDrawScreen(final int mouseX, final int mouseY) {
                RenderUtil.drawSmoothRoundedRect((float)this.getXPosition(), (float)this.getYPosition(), (float)(this.getXPosition() + this.getWidth()), (float)(this.getYPosition() + this.getHeight()), 15.0f, -15395563);
                RenderUtil.drawSmoothRoundedRect((float)this.getXPosition(), (float)this.getYPosition(), (float)(this.getXPosition() + this.getWidth()), (float)(this.getYPosition() + 30.0), 8.0f, Color.ORANGE.getRGB());
                GLDraw.glFilledQuad(this.getXPosition() - 0.5, this.getYPosition() + 3.0, this.getWidth() + 1.0, 32.0, -15395563);
                RenderUtil.setColor(-1);
                BozoWare.getInstance().getFontManager().onetapFontRenderer.drawString("onetap", this.getXPosition() + 8.0, this.getYPosition() + 14.0, -1);
                GLDraw.glFilledQuad(this.getXPosition(), this.getYPosition() + 35.0, this.getWidth(), 0.5, -11513776);
                GLDraw.glFilledQuad(this.getXPosition() + 70.0, this.getYPosition() + 36.0, 0.5, this.getHeight() - 36.0, -11513776);
                if (GuiOTUI.this.draggingRoot) {
                    this.setXPosition(mouseX - GuiOTUI.this.rootDistX);
                    this.setYPosition(mouseY - GuiOTUI.this.rootDistY);
                }
                this.getChildrenComponents().forEach(child -> child.onDrawScreen(mouseX, mouseY));
            }
            
            @Override
            public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
                if (mouseX >= this.getXPosition() && mouseX <= this.getXPosition() + this.getWidth() && mouseY >= this.getYPosition() && mouseY <= this.getYPosition() + 4.0 && mouseButton == 0) {
                    GuiOTUI.this.draggingRoot = true;
                    GuiOTUI.this.rootDistX = mouseX - this.getXPosition();
                    GuiOTUI.this.rootDistY = mouseY - this.getYPosition();
                }
                this.getChildrenComponents().forEach(child -> child.onMouseClicked(mouseX, mouseY, mouseButton));
            }
            
            @Override
            public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
                GuiOTUI.this.draggingRoot = false;
                this.getChildrenComponents().forEach(child -> child.onMouseReleased(mouseX, mouseY, mouseButton));
            }
            
            @Override
            public void onKeyTyped(final int typedKey) {
                this.getChildrenComponents().forEach(child -> child.onKeyTyped(typedKey));
            }
            
            @Override
            public void handleMouseInput() {
                this.getChildrenComponents().forEach(child -> child.handleMouseInput());
            }
        };
        int offset = 0;
        for (int i = 0; i < ModuleCategory.values().length; ++i) {
            final ModuleCategory currentCategory = ModuleCategory.values()[i];
            final TabSelectorComponent tab = new TabSelectorComponent(this.rootComponent, currentCategory.categoryName, currentCategory.iconCode, 55 + offset, 17.0, 0.0, 0.0);
            for (int i2 = 0; i2 < BozoWare.getInstance().getModuleManager().getModulesByCategory(currentCategory).size(); ++i2) {
                final Module currentIteratedModule = BozoWare.getInstance().getModuleManager().getModulesByCategory(currentCategory).get(i2);
                final ModuleButtonComponent moduleButtonComponent = new ModuleButtonComponent(tab, currentIteratedModule, 8.0, 50 + i2 * 14, 0.0, 0.0);
                if (currentIteratedModule.equals(BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Aura.class))) {
                    this.currentModule = moduleButtonComponent;
                }
                double propertyOffset = 45.0;
                for (int i3 = 0; i3 < currentIteratedModule.getModuleProperties().size(); ++i3) {
                    final Property<?> currentProperty = currentIteratedModule.getModuleProperties().get(i3);
                    if (currentProperty instanceof ValueProperty) {
                        final ValueProperty valueProperty = (ValueProperty)currentProperty;
                        final OTSliderComponent slider = new OTSliderComponent(moduleButtonComponent, valueProperty::getPropertyValue, valueProperty::getMaximumValue, valueProperty::getMinimumValue, valueProperty::setPropertyValue, valueProperty.getPropertyLabel(), 85.0, propertyOffset, 0.0, 0.0);
                        moduleButtonComponent.addChild(slider);
                        propertyOffset += slider.getHeight();
                    }
                    if (currentProperty instanceof BooleanProperty) {
                        final BooleanProperty booleanProperty = (BooleanProperty)currentProperty;
                        final OTCheckboxComponent checkBox = new OTCheckboxComponent(moduleButtonComponent, booleanProperty.getPropertyLabel(), booleanProperty::getPropertyValue, booleanProperty::setPropertyValue, 85.0, propertyOffset, 0.0, 0.0);
                        moduleButtonComponent.addChild(checkBox);
                        propertyOffset += checkBox.getHeight();
                    }
                    if (currentProperty instanceof EnumProperty) {
                        final EnumProperty enumProperty = (EnumProperty)currentProperty;
                        final OTDropdownComponent dropdown = new OTDropdownComponent(moduleButtonComponent, enumProperty.getPropertyLabel(), enumProperty::getEnumValues, enumProperty::getPropertyValue, enumProperty::increment, enumProperty::decrement, 85.0, propertyOffset, 0.0, 0.0);
                        moduleButtonComponent.addChild(dropdown);
                        propertyOffset += dropdown.getHeight();
                    }
                }
                tab.addChild(moduleButtonComponent);
            }
            this.rootComponent.addChild(tab);
            if (currentCategory.equals(ModuleCategory.COMBAT)) {
                tab.alphaTarget = 255.0;
                tab.alphaAnimation = 255.0;
                this.currentTabComponent = tab;
            }
            offset += (int)(this.rootComponent.getDefaultFontRenderer().getStringWidth(currentCategory.categoryName) + 20.0);
        }
        this.components.add(this.rootComponent);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.components.forEach(component -> component.onDrawScreen(mouseX, mouseY));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.components.forEach(component -> component.onMouseClicked(mouseX, mouseY, mouseButton));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.components.forEach(component -> component.onMouseReleased(mouseX, mouseY, state));
        super.mouseReleased(mouseX, mouseY, state);
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        this.components.forEach(OTComponent::handleMouseInput);
        super.handleMouseInput();
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
