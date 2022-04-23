// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.dropdown;

import bozoware.base.module.ModuleCategory;
import java.io.IOException;
import bozoware.base.util.visual.RenderUtil;
import net.minecraft.util.ResourceLocation;
import bozoware.impl.module.visual.ClickGUI;
import bozoware.base.util.visual.BlurUtil;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.Gui;
import bozoware.impl.module.visual.HUD;
import java.util.Iterator;
import bozoware.visual.screens.dropdown.component.sub.ModuleButtonComponent;
import bozoware.visual.screens.dropdown.component.Component;
import bozoware.visual.screens.dropdown.component.ModuleCategoryFrame;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class GuiDropDown extends GuiScreen
{
    private static final ArrayList<ModuleCategoryFrame> MODULE_CATEGORY_FRAMES;
    private double nekoAnimation;
    private int imageWidth;
    private int imageHeight;
    private int imageX;
    public static Runnable onStartTask;
    
    @Override
    public void initGui() {
        final Iterator<Component> iterator;
        Component component;
        GuiDropDown.MODULE_CATEGORY_FRAMES.forEach(moduleCategoryFrame -> {
            moduleCategoryFrame.getChildrenComponents().iterator();
            while (iterator.hasNext()) {
                component = iterator.next();
                if (component instanceof ModuleButtonComponent) {
                    ((ModuleButtonComponent)component).subComponents.forEach(Component::onAnimationEvent);
                }
            }
            return;
        });
        this.nekoAnimation = this.height;
        super.initGui();
    }
    
    @Override
    public void onGuiClosed() {
        GuiDropDown.MODULE_CATEGORY_FRAMES.forEach(ModuleCategoryFrame::onGuiClosed);
        super.onGuiClosed();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        Gui.drawGradientRect(0.0, 0.0, this.width, this.height, 1627389951, HUD.getInstance().bozoColor);
        Gui.drawRectWithWidth(0.0, 0.0, this.width, this.height, 1879048192);
        GL11.glPushMatrix();
        BlurUtil.blurArea(0.0, 0.0, this.width, this.height);
        switch (ClickGUI.getInstance().loliMode.getPropertyValue()) {
            case Uzaki: {
                this.mc.getTextureManager().bindTexture(new ResourceLocation("BozoWare/Uzaki.png"));
                this.imageWidth = 250;
                this.imageHeight = 330;
                this.imageX = 250;
                break;
            }
            case ZeroTwo: {
                this.mc.getTextureManager().bindTexture(new ResourceLocation("BozoWare/ZeroTwo.png"));
                this.imageWidth = 300;
                this.imageHeight = 330;
                this.imageX = 300;
                break;
            }
            case Rias: {
                this.mc.getTextureManager().bindTexture(new ResourceLocation("BozoWare/anth.png"));
                this.imageWidth = 225;
                this.imageHeight = 330;
                this.imageX = 225;
                break;
            }
            case Kanna: {
                this.mc.getTextureManager().bindTexture(new ResourceLocation("BozoWare/Kanna.png"));
                this.imageWidth = 325;
                this.imageHeight = 330;
                this.imageX = 325;
                break;
            }
            case None: {
                break;
            }
            default: {
                this.mc.getTextureManager().bindTexture(new ResourceLocation("BozoWare/Uzaki.png"));
                this.imageWidth = 250;
                this.imageHeight = 330;
                break;
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.nekoAnimation = RenderUtil.animate(this.height - 330, this.nekoAnimation, 0.03);
        if (!ClickGUI.getInstance().loliMode.getPropertyValue().equals(ClickGUI.loliModes.None)) {
            Gui.drawModalRectWithCustomSizedTexture((float)(this.width - this.imageX), (float)Math.round((float)this.nekoAnimation), 0.0f, 0.0f, 250.0f, 330.0f, (float)this.imageWidth, (float)this.imageHeight);
        }
        GL11.glPopMatrix();
        GuiDropDown.MODULE_CATEGORY_FRAMES.forEach(moduleCategoryFrame -> {
            moduleCategoryFrame.onDrawScreen(mouseX, mouseY);
            moduleCategoryFrame.updateComponents();
            return;
        });
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        GuiDropDown.MODULE_CATEGORY_FRAMES.forEach(moduleCategoryFrame -> moduleCategoryFrame.onMouseClicked(mouseX, mouseY, mouseButton));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        GuiDropDown.MODULE_CATEGORY_FRAMES.forEach(moduleCategoryFrame -> moduleCategoryFrame.onMouseReleased(mouseX, mouseY, state));
        super.mouseReleased(mouseX, mouseY, state);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 54) {
            this.mc.thePlayer.closeScreen();
        }
        GuiDropDown.MODULE_CATEGORY_FRAMES.forEach(moduleCategoryFrame -> moduleCategoryFrame.onKeyTyped(typedChar));
        super.keyTyped(typedChar, keyCode);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    static {
        MODULE_CATEGORY_FRAMES = new ArrayList<ModuleCategoryFrame>();
        int i;
        GuiDropDown.onStartTask = (() -> {
            for (i = 0; i < ModuleCategory.values().length; ++i) {
                GuiDropDown.MODULE_CATEGORY_FRAMES.add(new ModuleCategoryFrame(ModuleCategory.values()[i], 10 + i * 125, 10));
            }
        });
    }
}
