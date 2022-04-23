/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ave
 *  bfl
 *  ns
 */
package net.minecraft.realms;

import net.minecraft.realms.RealmsButton;

public class RealmsSliderButton
extends RealmsButton {
    public float value = 1.0f;
    public boolean sliding;
    private final float minValue;
    private final float maxValue;
    private int steps;

    public RealmsSliderButton(int n, int n2, int n3, int n4, int n5, int n6) {
        this(n, n2, n3, n4, n6, 0, 1.0f, n5);
    }

    public RealmsSliderButton(int n, int n2, int n3, int n4, int n5, int n6, float f2, float f3) {
        super(n, n2, n3, n4, 20, "");
        this.minValue = f2;
        this.maxValue = f3;
        this.value = this.toPct(n6);
        this.getProxy().j = this.getMessage();
    }

    public String getMessage() {
        return "";
    }

    public float toPct(float f2) {
        return ns.a((float)((this.clamp(f2) - this.minValue) / (this.maxValue - this.minValue)), (float)0.0f, (float)1.0f);
    }

    public float toValue(float f2) {
        return this.clamp(this.minValue + (this.maxValue - this.minValue) * ns.a((float)f2, (float)0.0f, (float)1.0f));
    }

    public float clamp(float f2) {
        f2 = this.clampSteps(f2);
        return ns.a((float)f2, (float)this.minValue, (float)this.maxValue);
    }

    protected float clampSteps(float f2) {
        if (this.steps > 0) {
            f2 = this.steps * Math.round(f2 / (float)this.steps);
        }
        return f2;
    }

    @Override
    public int getYImage(boolean bl) {
        return 0;
    }

    @Override
    public void renderBg(int n, int n2) {
        if (!this.getProxy().m) {
            return;
        }
        if (this.sliding) {
            this.value = (float)(n - (this.getProxy().h + 4)) / (float)(this.getProxy().b() - 8);
            this.value = ns.a((float)this.value, (float)0.0f, (float)1.0f);
            float f2 = this.toValue(this.value);
            this.clicked(f2);
            this.value = this.toPct(f2);
            this.getProxy().j = this.getMessage();
        }
        ave.A().P().a(WIDGETS_LOCATION);
        bfl.c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.blit(this.getProxy().h + (int)(this.value * (float)(this.getProxy().b() - 8)), this.getProxy().i, 0, 66, 4, 20);
        this.blit(this.getProxy().h + (int)(this.value * (float)(this.getProxy().b() - 8)) + 4, this.getProxy().i, 196, 66, 4, 20);
    }

    @Override
    public void clicked(int n, int n2) {
        this.value = (float)(n - (this.getProxy().h + 4)) / (float)(this.getProxy().b() - 8);
        this.value = ns.a((float)this.value, (float)0.0f, (float)1.0f);
        this.clicked(this.toValue(this.value));
        this.getProxy().j = this.getMessage();
        this.sliding = true;
    }

    public void clicked(float f2) {
    }

    @Override
    public void released(int n, int n2) {
        this.sliding = false;
    }
}

