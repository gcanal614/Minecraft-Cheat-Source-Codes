/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  aws
 */
package net.minecraft.realms;

import net.minecraft.realms.Tezzelator;

public class RealmsScrolledSelectionList {
    private final aws proxy;

    public RealmsScrolledSelectionList(int n, int n2, int n3, int n4, int n5) {
        this.proxy = new aws(this, n, n2, n3, n4, n5);
    }

    public void render(int n, int n2, float f) {
        this.proxy.a(n, n2, f);
    }

    public int width() {
        return this.proxy.e();
    }

    public int ym() {
        return this.proxy.f();
    }

    public int xm() {
        return this.proxy.g();
    }

    protected void renderItem(int n, int n2, int n3, int n4, Tezzelator tezzelator, int n5, int n6) {
    }

    public void renderItem(int n, int n2, int n3, int n4, int n5, int n6) {
        this.renderItem(n, n2, n3, n4, Tezzelator.instance, n5, n6);
    }

    public int getItemCount() {
        return 0;
    }

    public void selectItem(int n, boolean bl, int n2, int n3) {
    }

    public boolean isSelectedItem(int n) {
        return false;
    }

    public void renderBackground() {
    }

    public int getMaxPosition() {
        return 0;
    }

    public int getScrollbarPosition() {
        return this.proxy.e() / 2 + 124;
    }

    public void mouseEvent() {
        this.proxy.p();
    }

    public void scroll(int n) {
        this.proxy.h(n);
    }

    public int getScroll() {
        return this.proxy.n();
    }

    protected void renderList(int n, int n2, int n3, int n4) {
    }
}

