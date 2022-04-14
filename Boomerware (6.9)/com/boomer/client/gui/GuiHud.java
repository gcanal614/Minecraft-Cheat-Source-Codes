package com.boomer.client.gui;

import com.boomer.client.Client;
import com.boomer.client.gui.hudsettings.HudSettings;
import com.boomer.client.module.modules.visuals.hudcomps.HudComp;
import com.boomer.client.utils.RenderUtil;
import com.boomer.client.utils.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;

/**
 * made by oHare for BoomerWare
 *
 * @since 5/31/2019
 **/
public class GuiHud extends GuiScreen {

    public boolean dragging;
    public HudComp selectedHud;
    private double lastPosX, lastPosY;

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        RenderUtil.drawRect(0, sr.getScaledHeight() / 2 - 0.5, sr.getScaledWidth(), 1, 0xff000000);
        RenderUtil.drawRect(sr.getScaledWidth() / 2 - 0.5, 0, 1, sr.getScaledHeight(), 0xff000000);
        Client.INSTANCE.getHudCompManager().getHudMap().values().forEach(hudComp -> {
            hudComp.onRender(sr);
            RenderUtil.drawRect(hudComp.getX(), hudComp.getY() - 2, hudComp.getWidth(), hudComp.getHeight() + 2, hudComp.isEnabled() ? 0x40000000 : 0x99000000);
            Fonts.clickfont.drawStringWithShadow(hudComp.getLabel(), (hudComp.getX() + hudComp.getWidth() / 2) - Fonts.clickfont.getStringWidth(hudComp.getLabel()) / 2, (hudComp.getY() + hudComp.getHeight() / 2) - Fonts.clickfont.getStringHeight(hudComp.getLabel()) / 2, -1);
            if (dragging && selectedHud == hudComp) {
                hudComp.setX(mouseX + this.lastPosX);
                hudComp.setY(mouseY + this.lastPosY);
                hudComp.onDrag();
            }
            if (hudComp.getX() + hudComp.getWidth() > sr.getScaledWidth())
                hudComp.setX(sr.getScaledWidth() - hudComp.getWidth());
            if (hudComp.getX() < 0) hudComp.setX(0);
            if (hudComp.getY() + hudComp.getHeight() > sr.getScaledHeight())
                hudComp.setY(sr.getScaledHeight() - hudComp.getHeight());
            if (hudComp.getY() < 0) hudComp.setY(0);
        });
        snapToGuideLines(sr);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        dragging = false;
        selectedHud = null;
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (state == 0 && dragging) {
            dragging = false;
            selectedHud = null;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        Client.INSTANCE.getHudCompManager().getHudMap().values().forEach(hudComp -> {
            if (mouseWithinBounds(mouseX, mouseY, hudComp.getX(), hudComp.getY(), hudComp.getWidth(), hudComp.getHeight())) {
                if (mouseButton == 0) {
                    dragging = true;
                    selectedHud = hudComp;
                    this.lastPosX = (hudComp.getX() - mouseX);
                    this.lastPosY = (hudComp.getY() - mouseY);
                }
                if(mouseButton == 1 && !hudComp.getValues().isEmpty()) {
                    mc.displayGuiScreen(new HudSettings(hudComp));
                }
                if (mouseButton == 2) hudComp.setEnabled(!hudComp.isEnabled());
            }
        });
    }


    private void snapToGuideLines(ScaledResolution resolution) {
        HudComp dragging = selectedHud;
        if(selectedHud == null) return;

        final float height = resolution.getScaledHeight() / 2;
        final float width = resolution.getScaledWidth() / 2;

        final double draggingMinX = dragging.getX();
        final double draggingMaxX = draggingMinX + dragging.getWidth();
        final double draggingHalfX = draggingMinX + dragging.getWidth() / 2;

        final double draggingMinY = dragging.getY();
        final double draggingMaxY = draggingMinY + dragging.getHeight();
        final double draggingHalfY = draggingMinY + dragging.getHeight() / 2;

        if (checkBounds(draggingMinX, width)) dragging.setX(width);

        if (checkBounds(draggingMinY, height)) dragging.setY(height);

        if (checkBounds(draggingMaxX, width)) dragging.setX(width - dragging.getWidth());

        if (checkBounds(draggingMaxY, height)) dragging.setY(height - dragging.getHeight());

        if (checkBounds(draggingHalfX, width)) dragging.setX(width - dragging.getWidth() / 2);

        if (checkBounds(draggingHalfY, height)) dragging.setY(height - dragging.getHeight() / 2);
    }

    private boolean checkBounds(double f1, double f2) {
        return f1 >= f2 - 2 && f1 <= f2 + 2;
    }

    public boolean mouseWithinBounds(int mouseX, int mouseY, double x, double y, double width, double height) {
        return (mouseX >= x && mouseX <= (x + width)) && (mouseY >= y && mouseY <= (y + height));
    }
}
