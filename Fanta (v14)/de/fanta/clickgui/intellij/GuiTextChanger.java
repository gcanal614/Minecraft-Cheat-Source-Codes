/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package de.fanta.clickgui.intellij;

import de.fanta.clickgui.intellij.ClickGuiMainPane;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.ColorValue;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.setting.settings.Slider;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Mouse;

public class GuiTextChanger
extends Gui {
    public String accesModifier;
    public String accesModifier2;
    public String type;
    public String variableName;
    public String value;
    public float x;
    public float y;
    public float width;
    public float height;
    public Setting setting;
    public String currValue;
    public int lines = 1;
    private boolean focused = false;
    private TimeUtil timer = new TimeUtil();
    private boolean showCursor = false;

    public GuiTextChanger(String accesModifier, String accesModifier2, String type, String variableName, String value, float x, float y, Setting setting) {
        this.accesModifier = accesModifier;
        this.accesModifier2 = accesModifier2;
        this.type = type;
        this.variableName = variableName;
        this.value = value;
        this.x = x;
        this.y = y;
        this.setting = setting;
        this.currValue = value;
        if (setting.getSetting() instanceof ColorValue) {
            try {
                Integer i = Integer.parseInt(setting.getSetting().getValue().toString());
                this.currValue = "#" + Integer.toHexString(i).substring(0, 6);
            }
            catch (Exception e) {
                this.currValue = "#FFFFFF";
            }
        }
    }

    public void draw(float mouseX, float mouseY) {
        int widthAdd = 0;
        String space = " ";
        int typeColor = ClickGuiMainPane.MODIFIER_AND_TYPE_FONT_COLOR;
        int variableColor = ClickGuiMainPane.MODIFIER_AND_TYPE_FONT_COLOR;
        int commentColor = Color.decode("#808080").getRGB();
        String displayValue = this.currValue;
        String comment = "";
        if (this.setting.getSetting() instanceof DropdownBox) {
            typeColor = ClickGuiMainPane.TYPE_AS_OBJECT_FONT_COLOR;
            variableColor = Color.decode("#42B678").getRGB();
            displayValue = "\"" + this.currValue + "\"";
            comment = String.valueOf(comment) + "/* Allowed Values: {";
            String[] strings = (String[])this.setting.getSetting().getMaxValue();
            int i = 0;
            while (i < strings.length) {
                comment = i == strings.length - 1 ? String.valueOf(comment) + strings[i] : String.valueOf(comment) + strings[i] + ", ";
                ++i;
            }
            comment = String.valueOf(comment) + "}*/";
        } else if (this.setting.getSetting() instanceof Slider) {
            typeColor = ClickGuiMainPane.MODIFIER_AND_TYPE_FONT_COLOR;
            variableColor = ClickGuiMainPane.VALUE_FONT_COLOR;
            displayValue = String.valueOf(this.currValue) + "f";
            comment = String.valueOf(comment) + "/* Max Value: " + this.setting.getSetting().getMaxValue() + "*/";
        } else if (this.setting.getSetting() instanceof CheckBox) {
            typeColor = ClickGuiMainPane.MODIFIER_AND_TYPE_FONT_COLOR;
            variableColor = ClickGuiMainPane.MODIFIER_AND_TYPE_FONT_COLOR;
            comment = String.valueOf(comment) + "/* Opposite Value: " + ((Boolean)this.setting.getSetting().getValue() == false) + "*/";
        } else if (this.setting.getSetting() instanceof ColorValue) {
            typeColor = ClickGuiMainPane.MODIFIER_AND_TYPE_FONT_COLOR;
            variableColor = ClickGuiMainPane.MODIFIER_AND_TYPE_FONT_COLOR;
            comment = String.valueOf(comment) + "/* Please enter a Hex Code like this #FF0077*/";
        }
        float valX = 0.0f;
        float valY = 0.0f;
        float valWidth = 0.0f;
        float valHeight = 0.0f;
        ClickGuiMainPane.MENU_FONT.drawString(this.accesModifier, this.x + (float)widthAdd, this.y + 2.0f, ClickGuiMainPane.MODIFIER_AND_TYPE_FONT_COLOR);
        widthAdd += ClickGuiMainPane.MENU_FONT.getStringWidth(String.valueOf(this.accesModifier) + space);
        if (!this.accesModifier2.trim().isEmpty()) {
            ClickGuiMainPane.MENU_FONT.drawString(this.accesModifier2, this.x + (float)widthAdd, this.y + 2.0f, ClickGuiMainPane.MODIFIER_AND_TYPE_FONT_COLOR);
            widthAdd += ClickGuiMainPane.MENU_FONT.getStringWidth(String.valueOf(this.accesModifier2) + space);
        }
        ClickGuiMainPane.MENU_FONT.drawString(this.type, this.x + (float)widthAdd, this.y + 2.0f, typeColor);
        ClickGuiMainPane.MENU_FONT.drawString(this.variableName.toLowerCase().replace(" ", "_"), this.x + (float)(widthAdd += ClickGuiMainPane.MENU_FONT.getStringWidth(String.valueOf(this.type) + space)), this.y + 2.0f, ClickGuiMainPane.VARIABLE_NAME_FONT_COLOR);
        ClickGuiMainPane.MENU_FONT.drawString("=", this.x + (float)(widthAdd += ClickGuiMainPane.MENU_FONT.getStringWidth(String.valueOf(this.variableName.toLowerCase().replace(" ", "_")) + space)), this.y + 2.0f, ClickGuiMainPane.TYPE_AS_OBJECT_FONT_COLOR);
        valX = this.x + (float)(widthAdd += ClickGuiMainPane.MENU_FONT.getStringWidth("=" + space));
        valY = this.y;
        valWidth = ClickGuiMainPane.MENU_FONT.getStringWidth(displayValue) + 2;
        valHeight = 14.0f;
        ClickGuiMainPane.MENU_FONT.drawString(displayValue, this.x + (float)widthAdd, this.y + 2.0f, variableColor);
        widthAdd += ClickGuiMainPane.MENU_FONT.getStringWidth(displayValue);
        if (this.showCursor && this.focused) {
            ClickGuiMainPane.MENU_FONT.drawString("|", this.x + (float)widthAdd - (this.setting.getSetting() instanceof DropdownBox || this.setting.getSetting() instanceof Slider ? 7.5f : 2.0f), this.y + 2.0f, Color.white.getRGB());
        }
        ClickGuiMainPane.MENU_FONT.drawString(";", this.x + (float)widthAdd, this.y + 2.0f, ClickGuiMainPane.MODIFIER_AND_TYPE_FONT_COLOR);
        ClickGuiMainPane.MENU_FONT.drawString(comment, this.x + (float)(widthAdd += ClickGuiMainPane.MENU_FONT.getStringWidth(";" + space)), this.y + 2.0f, commentColor);
        widthAdd += ClickGuiMainPane.MENU_FONT.getStringWidth(comment);
        if (this.setting.getSetting() instanceof ColorValue) {
            try {
                GuiTextChanger.drawRect(this.x - 30.0f, this.y + 4.0f, this.x - 20.0f, this.y + 14.0f, Color.decode(this.currValue).getRGB());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (mouseX >= valX && mouseX <= valX + valWidth && mouseY >= this.y && mouseY <= this.y + valHeight && Mouse.isButtonDown((int)0) && this.accesModifier2.isEmpty()) {
            this.focused = true;
        } else if (!(!Mouse.isButtonDown((int)0) || mouseX >= valX && mouseX <= valX + valWidth && mouseY >= this.y && mouseY <= this.y + valHeight)) {
            this.focused = false;
            this.save();
        }
        if (this.timer.hasReached(500L)) {
            this.showCursor = true;
        }
        if (this.timer.hasReached(1000L)) {
            this.showCursor = false;
            this.timer.reset();
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (!this.focused) {
            return;
        }
        if (keyCode == 14 && this.currValue.length() > 0) {
            this.currValue = this.currValue.substring(0, this.currValue.length() - 1);
        } else if (keyCode == 28) {
            this.save();
            this.focused = false;
        } else if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
            this.writeText(Character.toString(typedChar));
        }
    }

    public void writeText(String s) {
        this.currValue = String.valueOf(this.currValue) + s;
    }

    public void save() {
        if (this.setting.getSetting() instanceof CheckBox) {
            this.currValue = this.currValue.trim();
            if (this.currValue.equalsIgnoreCase("true") || this.currValue.equalsIgnoreCase("false")) {
                this.setting.getSetting().setValue(Boolean.parseBoolean(this.currValue.toLowerCase()));
            } else {
                this.currValue = this.setting.getSetting().getValue().toString();
            }
        } else if (this.setting.getSetting() instanceof DropdownBox) {
            String[] strings;
            boolean allowContinue = false;
            String[] stringArray = strings = (String[])this.setting.getSetting().getMaxValue();
            int n = strings.length;
            int n2 = 0;
            while (n2 < n) {
                String string = stringArray[n2];
                if (string.trim().equalsIgnoreCase(this.currValue.trim())) {
                    allowContinue = true;
                }
                ++n2;
            }
            if (allowContinue) {
                this.setting.getSetting().setValue(this.currValue.trim());
            }
        } else if (this.setting.getSetting() instanceof ColorValue) {
            if (this.currValue.length() == 7 && this.currValue.startsWith("#")) {
                if (this.currValue.equals("#000000")) {
                    this.setting.getSetting().setValue(Color.black.getRGB());
                    return;
                }
                try {
                    int color = Color.decode(this.currValue).getRGB();
                    this.setting.getSetting().setValue(color);
                }
                catch (Exception e) {
                    this.setting.getSetting().setValue(this.setting.getSetting().getValue());
                }
            }
        } else if (this.setting.getSetting() instanceof Slider) {
            String result = this.currValue.replace(",", ".");
            if (result.startsWith(".")) {
                result = "0" + result;
            }
            try {
                Double d = Double.parseDouble(result);
                if (d > (Double)this.setting.getSetting().getMaxValue()) {
                    d = (Double)this.setting.getSetting().getMaxValue();
                }
                this.setting.getSetting().setValue(d);
                this.currValue = "" + d;
            }
            catch (Exception e) {
                this.setting.getSetting().setValue(this.setting.getSetting().getMaxValue());
            }
        }
    }
}

