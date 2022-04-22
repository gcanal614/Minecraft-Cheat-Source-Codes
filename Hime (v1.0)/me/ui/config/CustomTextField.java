package me.ui.config;


import java.awt.Color;

import me.Hime;
import org.lwjgl.input.Keyboard;

import me.util.TimeUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class CustomTextField {

    int x, y;
    String text;
    String name;
    boolean isFocused = false;
    int length = 0;
    TimeUtil timer = new TimeUtil();

    public CustomTextField(int x, int y, String text, String name) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.name = name;
    }

    public void draw(){
        GlStateManager.color(1.0f, 1.0f,1.0f,1.0f);
        float diff = Hime.instance.cfrs.getWidth(text) - length;
        length += diff / 4;
        if(Hime.instance.cfrs.getWidth(text) < 50){
            Gui.drawRect(x, y + Hime.instance.cfrs.getHeight("A") + 3, x + 50 + Hime.instance.cfrs.getWidth(name) - 40, y + Hime.instance.cfrs.getHeight("A") + 4, -1);
        }else {
            Gui.drawRect(x, y + Hime.instance.cfrs.getHeight("A") + 3, x + length + 7, y + Hime.instance.cfrs.getHeight("A") + 4, -1);
        }
        if(text.equalsIgnoreCase("") && !isFocused){
            Hime.instance.cfrs.drawString(name, x, y + 1, new Color(160, 160, 160, 255).getRGB());
        }
        if(!timer.hasTimePassed(1000)) {
            if(timer.hasTimePassed(500) && isFocused) {
                Hime.instance.cfrs.drawString(text + "|", x, y + 1, new Color(160, 160, 160, 255).getRGB());
            }else {
                Hime.instance.cfrs.drawString(text, x, y + 1, new Color(160, 160, 160, 255).getRGB());
            }
        }

        if(timer.hasTimePassed(950)){
            timer.reset();
        }
        GlStateManager.color(1.0f, 1.0f,1.0f,1.0f);


    }

    public void mouseClicked(int mouseX, int mouseY){
        if(mouseX > x && mouseX < (length > 50 ? x + length : x + Hime.instance.cfrs.getWidth(name)) && mouseY > y && mouseY < y + Hime.instance.cfrs.getHeight("A")){
            this.isFocused = true;
        }else {
            this.isFocused = false;
        }
    }

    public void keyTyped(char keyTyped, int key){
        if(isFocused){
            switch(key){
                case 14:
                    text = text.substring(0, text.length() > 0 ? text.length() - 1 : text.length());
                    break;
                default:
                    if (GuiScreen.isKeyComboCtrlV(key))
                    {

                        this.text = this.text + (GuiScreen.getClipboardString());
                        return;


                    }
                    if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                        text += Character.toUpperCase(keyTyped);
                    }else {
                        text += keyTyped;
                    }
                    break;
            }


        }
    }

    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
