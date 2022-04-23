/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  eu
 */
package net.minecraft.realms;

import java.util.List;
import net.minecraft.realms.Realms;
import net.minecraft.realms.RealmsButton;
import net.minecraft.realms.RealmsScreen;

public class DisconnectedRealmsScreen
extends RealmsScreen {
    private String title;
    private eu reason;
    private List<String> lines;
    private final RealmsScreen parent;
    private int textHeight;

    public DisconnectedRealmsScreen(RealmsScreen realmsScreen, String string, eu eu2) {
        this.parent = realmsScreen;
        this.title = DisconnectedRealmsScreen.getLocalizedString(string);
        this.reason = eu2;
    }

    @Override
    public void init() {
        Realms.setConnectedToRealms(false);
        this.buttonsClear();
        this.lines = this.fontSplit(this.reason.d(), this.width() - 50);
        this.textHeight = this.lines.size() * this.fontLineHeight();
        this.buttonsAdd(DisconnectedRealmsScreen.newButton(0, this.width() / 2 - 100, this.height() / 2 + this.textHeight / 2 + this.fontLineHeight(), DisconnectedRealmsScreen.getLocalizedString("gui.back")));
    }

    @Override
    public void keyPressed(char c, int n) {
        if (n == 1) {
            Realms.setScreen(this.parent);
        }
    }

    @Override
    public void buttonClicked(RealmsButton realmsButton) {
        if (realmsButton.id() == 0) {
            Realms.setScreen(this.parent);
        }
    }

    @Override
    public void render(int n3, int n2, float f) {
        int n3;
        this.renderBackground();
        this.drawCenteredString(this.title, this.width() / 2, this.height() / 2 - this.textHeight / 2 - this.fontLineHeight() * 2, 0xAAAAAA);
        int n4 = this.height() / 2 - this.textHeight / 2;
        if (this.lines != null) {
            for (String string : this.lines) {
                this.drawCenteredString(string, this.width() / 2, n4, 0xFFFFFF);
                n4 += this.fontLineHeight();
            }
        }
        super.render(n3, n2, f);
    }
}

