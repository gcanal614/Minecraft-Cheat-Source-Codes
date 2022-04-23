/*
 * Decompiled with CFR 0.152.
 */
package net.minecraftforge.client.model;

import net.minecraft.util.EnumFacing;
import org.lwjgl.util.vector.Matrix4f;

public interface ITransformation {
    public Matrix4f getMatrix();

    public EnumFacing rotate(EnumFacing var1);

    public int rotate(EnumFacing var1, int var2);
}

