/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.entity.model;

import net.minecraft.util.ResourceLocation;
import net.optifine.entity.model.CustomModelRenderer;

public class CustomEntityRenderer {
    private final String name;
    private final String basePath;
    private final ResourceLocation textureLocation;
    private final CustomModelRenderer[] customModelRenderers;
    private final float shadowSize;

    public CustomEntityRenderer(String name, String basePath, ResourceLocation textureLocation, CustomModelRenderer[] customModelRenderers, float shadowSize) {
        this.name = name;
        this.basePath = basePath;
        this.textureLocation = textureLocation;
        this.customModelRenderers = customModelRenderers;
        this.shadowSize = shadowSize;
    }

    public String getName() {
        return this.name;
    }

    public String getBasePath() {
        return this.basePath;
    }

    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }

    public CustomModelRenderer[] getCustomModelRenderers() {
        return this.customModelRenderers;
    }

    public float getShadowSize() {
        return this.shadowSize;
    }
}

