/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ave
 *  avw
 */
package net.minecraft.realms;

public class RealmsEditBox {
    private final avw editBox;

    public RealmsEditBox(int n, int n2, int n3, int n4, int n5) {
        this.editBox = new avw(n, ave.A().k, n2, n3, n4, n5);
    }

    public String getValue() {
        return this.editBox.b();
    }

    public void tick() {
        this.editBox.a();
    }

    public void setFocus(boolean bl) {
        this.editBox.b(bl);
    }

    public void setValue(String string) {
        this.editBox.a(string);
    }

    public void keyPressed(char c, int n) {
        this.editBox.a(c, n);
    }

    public boolean isFocused() {
        return this.editBox.m();
    }

    public void mouseClicked(int n, int n2, int n3) {
        this.editBox.a(n, n2, n3);
    }

    public void render() {
        this.editBox.g();
    }

    public void setMaxLength(int n) {
        this.editBox.f(n);
    }

    public void setIsEditable(boolean bl) {
        this.editBox.c(bl);
    }
}

