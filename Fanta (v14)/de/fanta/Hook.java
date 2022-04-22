/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.LWJGLException
 */
package de.fanta;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.LWJGLException;

public class Hook
extends Minecraft {
    public Hook(GameConfiguration gameConfig) {
        super(gameConfig);
    }

    @Override
    protected void drawSplashScreen(TextureManager textureManagerInstance) throws LWJGLException {
        super.drawSplashScreen(textureManagerInstance);
    }
}

