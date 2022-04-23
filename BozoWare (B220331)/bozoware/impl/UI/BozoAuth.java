// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.UI;

import bozoware.base.security.Auth;
import java.io.IOException;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiScreen;

public class BozoAuth extends GuiScreen
{
    private GuiTextField uidField;
    
    public void keyTyped(final char character, final int key) {
        this.uidField.textboxKeyTyped(character, key);
    }
    
    @Override
    public void initGui() {
        final int j = this.height / 2 - 55;
        final int x = this.width / 2 - 100;
        this.uidField = new GuiTextField(this.height / 4 + 24, Minecraft.getMinecraft().fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.buttonList.add(new GuiButton(1, x, j + 10, "Login"));
        super.initGui();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.uidField.drawTextBox();
        if (this.uidField.getText().length() == 0) {
            this.mc.fontRendererObj.drawString("§TType your UID", (float)(int)(this.width / 2.0f - 95.0f), 66.0f, -1);
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        this.uidField.mouseClicked(mouseX, mouseY, button);
        try {
            super.mouseClicked(mouseX, mouseY, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 1) {
            if (!Auth.Authenticate()) {
                final int j = this.height / 2 - 55;
                final int x = this.width / 2 - 100;
                this.mc.fontRendererObj.drawStringWithShadow("Not Authenticated... DEBUG: " + Auth.Authenticate(), (float)x, (float)(j - 20), -1);
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                this.mc.fontRendererObj.drawStringWithShadow("Authenticating", (float)this.width, (float)(this.height + 50), -1);
                System.out.println("Authenticated, Welcome to BozoWare! DEBUG: " + Auth.Authenticate());
                this.mc.displayGuiScreen(new BozoMainMenu());
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
        super.actionPerformed(button);
    }
}
