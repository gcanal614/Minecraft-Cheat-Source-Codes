/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ave
 *  avs
 *  awp
 *  jy
 */
package net.minecraft.realms;

public class RealmsButton {
    protected static final jy WIDGETS_LOCATION = new jy("textures/gui/widgets.png");
    private awp proxy;

    public RealmsButton(int n, int n2, int n3, String string) {
        this.proxy = new awp(this, n, n2, n3, string);
    }

    public RealmsButton(int n, int n2, int n3, int n4, int n5, String string) {
        this.proxy = new awp(this, n, n2, n3, string, n4, n5);
    }

    public avs getProxy() {
        return this.proxy;
    }

    public int id() {
        return this.proxy.c();
    }

    public boolean active() {
        return this.proxy.d();
    }

    public void active(boolean bl) {
        this.proxy.b(bl);
    }

    public void msg(String string) {
        this.proxy.a(string);
    }

    public int getWidth() {
        return this.proxy.b();
    }

    public int getHeight() {
        return this.proxy.g();
    }

    public int y() {
        return this.proxy.e();
    }

    public void render(int n, int n2) {
        this.proxy.a(ave.A(), n, n2);
    }

    public void clicked(int n, int n2) {
    }

    public void released(int n, int n2) {
    }

    public void blit(int n, int n2, int n3, int n4, int n5, int n6) {
        this.proxy.b(n, n2, n3, n4, n5, n6);
    }

    public void renderBg(int n, int n2) {
    }

    public int getYImage(boolean bl) {
        return this.proxy.c(bl);
    }
}

