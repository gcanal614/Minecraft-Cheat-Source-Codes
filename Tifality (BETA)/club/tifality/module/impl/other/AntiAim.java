/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.other;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.impl.EnumProperty;
import club.tifality.utils.timer.TimerUtil;

@ModuleInfo(label="AntiAim", category=ModuleCategory.OTHER)
public final class AntiAim
extends Module {
    public final EnumProperty<AAYAWMODE> yaw = new EnumProperty<AAYAWMODE>("Yaw", AAYAWMODE.FAKEJITTER);
    public final EnumProperty<AAPITCHMODE> pitch = new EnumProperty<AAPITCHMODE>("Pitch", AAPITCHMODE.HALFDOWN);
    float[] lastAngles;
    public static float rotationPitch;
    private boolean fake;
    private boolean fake1;
    TimerUtil fakeJitter = new TimerUtil();

    @Listener
    private void onUpdatePosition(UpdatePositionEvent em) {
        if (em.isPre()) {
            if (this.lastAngles == null) {
                this.lastAngles = new float[]{AntiAim.mc.thePlayer.rotationYaw, AntiAim.mc.thePlayer.rotationPitch};
            }
            this.fake = !this.fake;
            switch ((AAYAWMODE)((Object)this.yaw.get())) {
                case Jitter: {
                    float yawJitter = 0.0f;
                    yawJitter = this.lastAngles[0] + 180.0f;
                    em.setYaw(yawJitter);
                    this.lastAngles = new float[]{yawJitter, this.lastAngles[1]};
                    this.updateAngles(yawJitter, this.lastAngles[1]);
                    break;
                }
                case Lisp: {
                    float yaw = this.lastAngles[0] + 150000.0f;
                    this.lastAngles = new float[]{yaw, this.lastAngles[1]};
                    em.setYaw(yaw);
                    this.updateAngles(yaw, this.lastAngles[1]);
                    break;
                }
                case Reverse: {
                    float yawReverse = AntiAim.mc.thePlayer.rotationYaw + 180.0f;
                    this.lastAngles = new float[]{yawReverse, this.lastAngles[1]};
                    em.setYaw(yawReverse);
                    this.updateAngles(yawReverse, this.lastAngles[1]);
                    break;
                }
                case Sideways: {
                    float yawLeft = AntiAim.mc.thePlayer.rotationYaw - 90.0f;
                    this.lastAngles = new float[]{yawLeft, this.lastAngles[1]};
                    em.setYaw(yawLeft);
                    this.updateAngles(yawLeft, this.lastAngles[1]);
                    break;
                }
                case FakeJitter: {
                    if (this.fakeJitter.delay(350.0f)) {
                        this.fake1 = !this.fake1;
                        this.fakeJitter.reset();
                    }
                    float yawRight = AntiAim.mc.thePlayer.rotationYaw + (float)(this.fake1 ? 90 : -90);
                    this.lastAngles = new float[]{yawRight, this.lastAngles[1]};
                    em.setYaw(yawRight);
                    this.updateAngles(yawRight, this.lastAngles[1]);
                    break;
                }
                case SpinFast: {
                    float yawSpinFast = this.lastAngles[0] + 45.0f;
                    this.lastAngles = new float[]{yawSpinFast, this.lastAngles[1]};
                    em.setYaw(yawSpinFast);
                    this.updateAngles(yawSpinFast, this.lastAngles[1]);
                    break;
                }
                case SpinSlow: {
                    float yawSpinSlow = this.lastAngles[0] + 10.0f;
                    this.lastAngles = new float[]{yawSpinSlow, this.lastAngles[1]};
                    em.setYaw(yawSpinSlow);
                    this.updateAngles(yawSpinSlow, this.lastAngles[1]);
                    break;
                }
            }
            switch ((AAPITCHMODE)((Object)this.pitch.get())) {
                case HalfDown: {
                    float pitchDown = 90.0f;
                    this.lastAngles = new float[]{this.lastAngles[0], 90.0f};
                    em.setPitch(90.0f);
                    this.updateAngles(this.lastAngles[0], 90.0f);
                    break;
                }
                case Meme: {
                    float lastMeme = this.lastAngles[1];
                    lastMeme += 10.0f;
                    if (lastMeme > 90.0f) {
                        lastMeme = -90.0f;
                    }
                    this.lastAngles = new float[]{this.lastAngles[0], lastMeme};
                    em.setPitch(lastMeme);
                    this.updateAngles(this.lastAngles[0], lastMeme);
                    break;
                }
                case Normal: {
                    this.updateAngles(this.lastAngles[0], 0.0f);
                    break;
                }
                case Reverse: {
                    float reverse = AntiAim.mc.thePlayer.rotationPitch + 180.0f;
                    this.lastAngles = new float[]{this.lastAngles[0], reverse};
                    em.setPitch(reverse);
                    this.updateAngles(this.lastAngles[0], reverse);
                    break;
                }
                case Stutter: {
                    float sutter;
                    if (this.fake) {
                        sutter = 90.0f;
                        em.setPitch(sutter);
                    } else {
                        sutter = -45.0f;
                        em.setPitch(sutter);
                    }
                    this.lastAngles = new float[]{this.lastAngles[0], sutter};
                    this.updateAngles(this.lastAngles[0], sutter);
                    break;
                }
                case Up: {
                    this.lastAngles = new float[]{this.lastAngles[0], -90.0f};
                    em.setPitch(-90.0f);
                    this.updateAngles(this.lastAngles[0], -90.0f);
                    break;
                }
                case DOWN: {
                    this.lastAngles = new float[]{this.lastAngles[0], 90.0f};
                    em.setPitch(90.0f);
                    this.updateAngles(this.lastAngles[0], 90.0f);
                    break;
                }
                case Zero: {
                    this.lastAngles = new float[]{this.lastAngles[0], -179.0f};
                    em.setPitch(-180.0f);
                    this.updateAngles(this.lastAngles[0], -179.0f);
                }
            }
        }
    }

    @Override
    public void onDisable() {
        this.fake1 = true;
        this.lastAngles = null;
        rotationPitch = 0.0f;
        AntiAim.mc.thePlayer.renderYawOffset = AntiAim.mc.thePlayer.rotationYaw;
    }

    @Override
    public void onEnable() {
        this.fake1 = true;
        this.lastAngles = null;
        rotationPitch = 0.0f;
    }

    public void updateAngles(float yaw, float pitch) {
        if (AntiAim.mc.gameSettings.thirdPersonView != 0) {
            rotationPitch = pitch;
            AntiAim.mc.thePlayer.rotationYawHead = yaw;
            AntiAim.mc.thePlayer.renderYawOffset = yaw;
        }
    }

    public static enum AAPITCHMODE {
        HALFDOWN,
        Normal,
        HalfDown,
        Zero,
        Up,
        Stutter,
        Reverse,
        DOWN,
        Meme;

    }

    public static enum AAYAWMODE {
        FAKEJITTER,
        Reverse,
        Jitter,
        Lisp,
        SpinSlow,
        SpinFast,
        Sideways,
        FakeJitter;

    }
}

