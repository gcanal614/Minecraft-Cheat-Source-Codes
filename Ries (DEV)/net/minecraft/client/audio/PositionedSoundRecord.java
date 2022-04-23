/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.ResourceLocation;

public class PositionedSoundRecord
extends PositionedSound {
    public static PositionedSoundRecord create(ResourceLocation soundResource, float pitch) {
        return new PositionedSoundRecord(soundResource, 0.25f, pitch, ISound.AttenuationType.NONE, 0.0f, 0.0f, 0.0f);
    }

    public static PositionedSoundRecord create(ResourceLocation soundResource) {
        return new PositionedSoundRecord(soundResource, 1.0f, 1.0f, ISound.AttenuationType.NONE, 0.0f, 0.0f, 0.0f);
    }

    public static PositionedSoundRecord create(ResourceLocation soundResource, float xPosition, float yPosition, float zPosition) {
        return new PositionedSoundRecord(soundResource, 4.0f, 1.0f, ISound.AttenuationType.LINEAR, xPosition, yPosition, zPosition);
    }

    public PositionedSoundRecord(ResourceLocation soundResource, float volume, float pitch, float xPosition, float yPosition, float zPosition) {
        this(soundResource, volume, pitch, ISound.AttenuationType.LINEAR, xPosition, yPosition, zPosition);
    }

    private PositionedSoundRecord(ResourceLocation soundResource, float volume, float pitch, ISound.AttenuationType attenuationType, float xPosition, float yPosition, float zPosition) {
        super(soundResource);
        this.volume = volume;
        this.pitch = pitch;
        this.xPosF = xPosition;
        this.yPosF = yPosition;
        this.zPosF = zPosition;
        this.repeat = false;
        this.repeatDelay = 0;
        this.attenuationType = attenuationType;
    }
}

