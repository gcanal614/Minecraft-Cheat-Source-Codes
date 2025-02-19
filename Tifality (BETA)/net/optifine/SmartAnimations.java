/*
 * Decompiled with CFR 0.152.
 */
package net.optifine;

import java.util.BitSet;
import net.minecraft.src.Config;
import net.optifine.shaders.Shaders;

public class SmartAnimations {
    private static boolean active;
    private static BitSet spritesRendered;
    private static BitSet texturesRendered;

    public static boolean isActive() {
        return active && !Shaders.isShadowPass;
    }

    public static void update() {
        active = Config.getGameSettings().ofSmartAnimations;
    }

    public static void spriteRendered(int animationIndex) {
        spritesRendered.set(animationIndex);
    }

    public static void spritesRendered(BitSet animationIndexes) {
        if (animationIndexes != null) {
            spritesRendered.or(animationIndexes);
        }
    }

    public static boolean isSpriteRendered(int animationIndex) {
        return spritesRendered.get(animationIndex);
    }

    public static void resetSpritesRendered() {
        spritesRendered.clear();
    }

    public static void textureRendered(int textureId) {
        if (textureId >= 0) {
            texturesRendered.set(textureId);
        }
    }

    public static boolean isTextureRendered(int texId) {
        return texId >= 0 && texturesRendered.get(texId);
    }

    public static void resetTexturesRendered() {
        texturesRendered.clear();
    }

    static {
        spritesRendered = new BitSet();
        texturesRendered = new BitSet();
    }
}

