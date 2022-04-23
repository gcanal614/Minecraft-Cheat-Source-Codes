/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.event.impl.player;

import club.tifality.manager.event.Event;

public final class StepEvent
implements Event {
    private float stepHeight;
    private double heightStepped;
    private boolean pre;

    public StepEvent(float stepHeight) {
        this.stepHeight = stepHeight;
        this.pre = true;
    }

    public double getHeightStepped() {
        return this.heightStepped;
    }

    public void setHeightStepped(double heightStepped) {
        this.heightStepped = heightStepped;
    }

    public boolean isPre() {
        return this.pre;
    }

    public void setPost() {
        this.pre = false;
    }

    public float getStepHeight() {
        return this.stepHeight;
    }

    public void setStepHeight(float stepHeight) {
        this.stepHeight = stepHeight;
    }
}

