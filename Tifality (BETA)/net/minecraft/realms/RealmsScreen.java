/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ave
 *  avp
 *  awr
 *  bet
 *  bmz
 *  bnq
 *  jy
 *  zx
 */
package net.minecraft.realms;

import com.mojang.util.UUIDTypeAdapter;
import java.util.List;
import java.util.UUID;
import net.minecraft.realms.RealmsAnvilLevelStorageSource;
import net.minecraft.realms.RealmsButton;
import net.minecraft.realms.RealmsEditBox;

public class RealmsScreen {
    public static final int SKIN_HEAD_U = 8;
    public static final int SKIN_HEAD_V = 8;
    public static final int SKIN_HEAD_WIDTH = 8;
    public static final int SKIN_HEAD_HEIGHT = 8;
    public static final int SKIN_HAT_U = 40;
    public static final int SKIN_HAT_V = 8;
    public static final int SKIN_HAT_WIDTH = 8;
    public static final int SKIN_HAT_HEIGHT = 8;
    public static final int SKIN_TEX_WIDTH = 64;
    public static final int SKIN_TEX_HEIGHT = 64;
    protected ave minecraft;
    public int width;
    public int height;
    private awr proxy = new awr(this);

    public awr getProxy() {
        return this.proxy;
    }

    public void init() {
    }

    public void init(ave ave2, int n, int n2) {
    }

    public void drawCenteredString(String string, int n, int n2, int n3) {
        this.proxy.a(string, n, n2, n3);
    }

    public void drawString(String string, int n, int n2, int n3) {
        this.proxy.b(string, n, n2, n3);
    }

    public void blit(int n, int n2, int n3, int n4, int n5, int n6) {
        this.proxy.b(n, n2, n3, n4, n5, n6);
    }

    public static void blit(int n, int n2, float f, float f2, int n3, int n4, int n5, int n6, float f3, float f4) {
        avp.a((int)n, (int)n2, (float)f, (float)f2, (int)n3, (int)n4, (int)n5, (int)n6, (float)f3, (float)f4);
    }

    public static void blit(int n, int n2, float f, float f2, int n3, int n4, float f3, float f4) {
        avp.a((int)n, (int)n2, (float)f, (float)f2, (int)n3, (int)n4, (float)f3, (float)f4);
    }

    public void fillGradient(int n, int n2, int n3, int n4, int n5, int n6) {
        this.proxy.a(n, n2, n3, n4, n5, n6);
    }

    public void renderBackground() {
        this.proxy.c();
    }

    public boolean isPauseScreen() {
        return this.proxy.d();
    }

    public void renderBackground(int n) {
        this.proxy.b_(n);
    }

    public void render(int n, int n2, float f) {
        for (int i = 0; i < this.proxy.j().size(); ++i) {
            ((RealmsButton)this.proxy.j().get(i)).render(n, n2);
        }
    }

    public void renderTooltip(zx zx2, int n, int n2) {
        this.proxy.a(zx2, n, n2);
    }

    public void renderTooltip(String string, int n, int n2) {
        this.proxy.a(string, n, n2);
    }

    public void renderTooltip(List<String> list, int n, int n2) {
        this.proxy.a(list, n, n2);
    }

    public static void bindFace(String string, String string2) {
        jy jy2 = bet.c((String)string2);
        if (jy2 == null) {
            jy2 = bmz.a((UUID)UUIDTypeAdapter.fromString(string));
        }
        bet.a((jy)jy2, (String)string2);
        ave.A().P().a(jy2);
    }

    public static void bind(String string) {
        jy jy2 = new jy(string);
        ave.A().P().a(jy2);
    }

    public void tick() {
    }

    public int width() {
        return this.proxy.l;
    }

    public int height() {
        return this.proxy.m;
    }

    public int fontLineHeight() {
        return this.proxy.h();
    }

    public int fontWidth(String string) {
        return this.proxy.c(string);
    }

    public void fontDrawShadow(String string, int n, int n2, int n3) {
        this.proxy.c(string, n, n2, n3);
    }

    public List<String> fontSplit(String string, int n) {
        return this.proxy.a(string, n);
    }

    public void buttonClicked(RealmsButton realmsButton) {
    }

    public static RealmsButton newButton(int n, int n2, int n3, String string) {
        return new RealmsButton(n, n2, n3, string);
    }

    public static RealmsButton newButton(int n, int n2, int n3, int n4, int n5, String string) {
        return new RealmsButton(n, n2, n3, n4, n5, string);
    }

    public void buttonsClear() {
        this.proxy.i();
    }

    public void buttonsAdd(RealmsButton realmsButton) {
        this.proxy.a(realmsButton);
    }

    public List<RealmsButton> buttons() {
        return this.proxy.j();
    }

    public void buttonsRemove(RealmsButton realmsButton) {
        this.proxy.b(realmsButton);
    }

    public RealmsEditBox newEditBox(int n, int n2, int n3, int n4, int n5) {
        return new RealmsEditBox(n, n2, n3, n4, n5);
    }

    public void mouseClicked(int n, int n2, int n3) {
    }

    public void mouseEvent() {
    }

    public void keyboardEvent() {
    }

    public void mouseReleased(int n, int n2, int n3) {
    }

    public void mouseDragged(int n, int n2, int n3, long l) {
    }

    public void keyPressed(char c, int n) {
    }

    public void confirmResult(boolean bl, int n) {
    }

    public static String getLocalizedString(String string) {
        return bnq.a((String)string, (Object[])new Object[0]);
    }

    public static String getLocalizedString(String string, Object ... objectArray) {
        return bnq.a((String)string, (Object[])objectArray);
    }

    public RealmsAnvilLevelStorageSource getLevelStorageSource() {
        return new RealmsAnvilLevelStorageSource(ave.A().f());
    }

    public void removed() {
    }
}

