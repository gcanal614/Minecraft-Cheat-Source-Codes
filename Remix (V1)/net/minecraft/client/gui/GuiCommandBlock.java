package net.minecraft.client.gui;

import net.minecraft.command.server.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import me.satisfactory.base.gui.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;

public class GuiCommandBlock extends GuiScreen
{
    private static final Logger field_146488_a;
    private final CommandBlockLogic localCommandBlock;
    private GuiTextField commandTextField;
    private GuiTextField field_146486_g;
    private GuiButton doneBtn;
    private GuiButton cancelBtn;
    private GuiButton field_175390_s;
    private boolean field_175389_t;
    
    public GuiCommandBlock(final CommandBlockLogic p_i45032_1_) {
        this.localCommandBlock = p_i45032_1_;
    }
    
    @Override
    public void updateScreen() {
        this.commandTextField.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.doneBtn = new DarkButton(0, GuiCommandBlock.width / 2 - 4 - 150, GuiCommandBlock.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(this.cancelBtn = new DarkButton(1, GuiCommandBlock.width / 2 + 4, GuiCommandBlock.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(this.field_175390_s = new DarkButton(4, GuiCommandBlock.width / 2 + 150 - 20, 150, 20, 20, "O"));
        (this.commandTextField = new GuiTextField(2, this.fontRendererObj, GuiCommandBlock.width / 2 - 150, 50, 300, 20)).setMaxStringLength(32767);
        this.commandTextField.setFocused(true);
        this.commandTextField.setText(this.localCommandBlock.getCustomName());
        (this.field_146486_g = new GuiTextField(3, this.fontRendererObj, GuiCommandBlock.width / 2 - 150, 150, 276, 20)).setMaxStringLength(32767);
        this.field_146486_g.setEnabled(false);
        this.field_146486_g.setText("-");
        this.field_175389_t = this.localCommandBlock.func_175571_m();
        this.func_175388_a();
        this.doneBtn.enabled = (this.commandTextField.getText().trim().length() > 0);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            if (button.id == 1) {
                this.localCommandBlock.func_175573_a(this.field_175389_t);
                GuiCommandBlock.mc.displayGuiScreen(null);
            }
            else if (button.id == 0) {
                final PacketBuffer var2 = new PacketBuffer(Unpooled.buffer());
                var2.writeByte(this.localCommandBlock.func_145751_f());
                this.localCommandBlock.func_145757_a(var2);
                var2.writeString(this.commandTextField.getText());
                var2.writeBoolean(this.localCommandBlock.func_175571_m());
                GuiCommandBlock.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|AdvCdm", var2));
                if (!this.localCommandBlock.func_175571_m()) {
                    this.localCommandBlock.func_145750_b(null);
                }
                GuiCommandBlock.mc.displayGuiScreen(null);
            }
            else if (button.id == 4) {
                this.localCommandBlock.func_175573_a(!this.localCommandBlock.func_175571_m());
                this.func_175388_a();
            }
        }
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
        this.commandTextField.textboxKeyTyped(typedChar, keyCode);
        this.field_146486_g.textboxKeyTyped(typedChar, keyCode);
        this.doneBtn.enabled = (this.commandTextField.getText().trim().length() > 0);
        if (keyCode != 28 && keyCode != 156) {
            if (keyCode == 1) {
                this.actionPerformed(this.cancelBtn);
            }
        }
        else {
            this.actionPerformed(this.doneBtn);
        }
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.commandTextField.mouseClicked(mouseX, mouseY, mouseButton);
        this.field_146486_g.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand", new Object[0]), GuiCommandBlock.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("advMode.command", new Object[0]), GuiCommandBlock.width / 2 - 150, 37, 10526880);
        this.commandTextField.drawTextBox();
        final byte var4 = 75;
        final byte var5 = 0;
        final FontRenderer var6 = this.fontRendererObj;
        final String var7 = I18n.format("advMode.nearestPlayer", new Object[0]);
        final int var8 = GuiCommandBlock.width / 2 - 150;
        int var9 = var5 + 1;
        this.drawString(var6, var7, var8, var4 + var5 * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("advMode.randomPlayer", new Object[0]), GuiCommandBlock.width / 2 - 150, var4 + var9++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("advMode.allPlayers", new Object[0]), GuiCommandBlock.width / 2 - 150, var4 + var9++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("advMode.allEntities", new Object[0]), GuiCommandBlock.width / 2 - 150, var4 + var9++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRendererObj, "", GuiCommandBlock.width / 2 - 150, var4 + var9++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        if (this.field_146486_g.getText().length() > 0) {
            final int var10 = var4 + var9 * this.fontRendererObj.FONT_HEIGHT + 16;
            this.drawString(this.fontRendererObj, I18n.format("advMode.previousOutput", new Object[0]), GuiCommandBlock.width / 2 - 150, var10, 10526880);
            this.field_146486_g.drawTextBox();
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    private void func_175388_a() {
        if (this.localCommandBlock.func_175571_m()) {
            this.field_175390_s.displayString = "O";
            if (this.localCommandBlock.getLastOutput() != null) {
                this.field_146486_g.setText(this.localCommandBlock.getLastOutput().getUnformattedText());
            }
        }
        else {
            this.field_175390_s.displayString = "X";
            this.field_146486_g.setText("-");
        }
    }
    
    static {
        field_146488_a = LogManager.getLogger();
    }
}
