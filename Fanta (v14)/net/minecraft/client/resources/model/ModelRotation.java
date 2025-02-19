/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Optional
 *  com.google.common.collect.Maps
 *  javax.vecmath.Matrix4f
 *  optifine.Reflector
 *  optifine.ReflectorMethod
 *  org.lwjgl.util.vector.Matrix4f
 *  org.lwjgl.util.vector.Vector3f
 */
package net.minecraft.client.resources.model;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.vecmath.Matrix4f;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.model.IModelPart;
import net.minecraftforge.client.model.IModelState;
import net.minecraftforge.client.model.ITransformation;
import net.minecraftforge.client.model.TRSRTransformation;
import optifine.Reflector;
import optifine.ReflectorMethod;
import org.lwjgl.util.vector.Vector3f;

public enum ModelRotation implements IModelState,
ITransformation
{
    X0_Y0("X0_Y0", 0, 0, 0),
    X0_Y90("X0_Y90", 1, 0, 90),
    X0_Y180("X0_Y180", 2, 0, 180),
    X0_Y270("X0_Y270", 3, 0, 270),
    X90_Y0("X90_Y0", 4, 90, 0),
    X90_Y90("X90_Y90", 5, 90, 90),
    X90_Y180("X90_Y180", 6, 90, 180),
    X90_Y270("X90_Y270", 7, 90, 270),
    X180_Y0("X180_Y0", 8, 180, 0),
    X180_Y90("X180_Y90", 9, 180, 90),
    X180_Y180("X180_Y180", 10, 180, 180),
    X180_Y270("X180_Y270", 11, 180, 270),
    X270_Y0("X270_Y0", 12, 270, 0),
    X270_Y90("X270_Y90", 13, 270, 90),
    X270_Y180("X270_Y180", 14, 270, 180),
    X270_Y270("X270_Y270", 15, 270, 270);

    private static final Map mapRotations;
    private final int combinedXY;
    private final org.lwjgl.util.vector.Matrix4f matrix4d;
    private final int quartersX;
    private final int quartersY;
    private static final ModelRotation[] $VALUES;
    private static final String __OBFID = "CL_00002393";

    static {
        mapRotations = Maps.newHashMap();
        $VALUES = new ModelRotation[]{X0_Y0, X0_Y90, X0_Y180, X0_Y270, X90_Y0, X90_Y90, X90_Y180, X90_Y270, X180_Y0, X180_Y90, X180_Y180, X180_Y270, X270_Y0, X270_Y90, X270_Y180, X270_Y270};
        ModelRotation[] modelRotationArray = ModelRotation.values();
        int n = modelRotationArray.length;
        int n2 = 0;
        while (n2 < n) {
            ModelRotation modelrotation = modelRotationArray[n2];
            mapRotations.put(modelrotation.combinedXY, modelrotation);
            ++n2;
        }
    }

    private static int combineXY(int p_177521_0_, int p_177521_1_) {
        return p_177521_0_ * 360 + p_177521_1_;
    }

    private ModelRotation(String p_i13_3_, int p_i13_4_, int p_i13_5_, int p_i13_6_) {
        this.combinedXY = ModelRotation.combineXY(p_i13_5_, p_i13_6_);
        this.matrix4d = new org.lwjgl.util.vector.Matrix4f();
        org.lwjgl.util.vector.Matrix4f matrix4f = new org.lwjgl.util.vector.Matrix4f();
        matrix4f.setIdentity();
        org.lwjgl.util.vector.Matrix4f.rotate((float)((float)(-p_i13_5_) * ((float)Math.PI / 180)), (Vector3f)new Vector3f(1.0f, 0.0f, 0.0f), (org.lwjgl.util.vector.Matrix4f)matrix4f, (org.lwjgl.util.vector.Matrix4f)matrix4f);
        this.quartersX = MathHelper.abs_int(p_i13_5_ / 90);
        org.lwjgl.util.vector.Matrix4f matrix4f1 = new org.lwjgl.util.vector.Matrix4f();
        matrix4f1.setIdentity();
        org.lwjgl.util.vector.Matrix4f.rotate((float)((float)(-p_i13_6_) * ((float)Math.PI / 180)), (Vector3f)new Vector3f(0.0f, 1.0f, 0.0f), (org.lwjgl.util.vector.Matrix4f)matrix4f1, (org.lwjgl.util.vector.Matrix4f)matrix4f1);
        this.quartersY = MathHelper.abs_int(p_i13_6_ / 90);
        org.lwjgl.util.vector.Matrix4f.mul((org.lwjgl.util.vector.Matrix4f)matrix4f1, (org.lwjgl.util.vector.Matrix4f)matrix4f, (org.lwjgl.util.vector.Matrix4f)this.matrix4d);
    }

    public org.lwjgl.util.vector.Matrix4f getMatrix4d() {
        return this.matrix4d;
    }

    public EnumFacing rotateFace(EnumFacing p_177523_1_) {
        EnumFacing enumfacing = p_177523_1_;
        int i = 0;
        while (i < this.quartersX) {
            enumfacing = enumfacing.rotateAround(EnumFacing.Axis.X);
            ++i;
        }
        if (enumfacing.getAxis() != EnumFacing.Axis.Y) {
            int j = 0;
            while (j < this.quartersY) {
                enumfacing = enumfacing.rotateAround(EnumFacing.Axis.Y);
                ++j;
            }
        }
        return enumfacing;
    }

    public int rotateVertex(EnumFacing facing, int vertexIndex) {
        int i = vertexIndex;
        if (facing.getAxis() == EnumFacing.Axis.X) {
            i = (vertexIndex + this.quartersX) % 4;
        }
        EnumFacing enumfacing = facing;
        int j = 0;
        while (j < this.quartersX) {
            enumfacing = enumfacing.rotateAround(EnumFacing.Axis.X);
            ++j;
        }
        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            i = (i + this.quartersY) % 4;
        }
        return i;
    }

    public static ModelRotation getModelRotation(int p_177524_0_, int p_177524_1_) {
        return (ModelRotation)mapRotations.get(ModelRotation.combineXY(MathHelper.normalizeAngle(p_177524_0_, 360), MathHelper.normalizeAngle(p_177524_1_, 360)));
    }

    @Override
    public Optional<TRSRTransformation> apply(Optional<? extends IModelPart> p_apply_1_) {
        return (Optional)Reflector.call((ReflectorMethod)Reflector.ForgeHooksClient_applyTransform, (Object[])new Object[]{this.getMatrix(), p_apply_1_});
    }

    @Override
    public Matrix4f getMatrix() {
        return Reflector.ForgeHooksClient_getMatrix.exists() ? (Matrix4f)Reflector.call((ReflectorMethod)Reflector.ForgeHooksClient_getMatrix, (Object[])new Object[]{this}) : new Matrix4f();
    }

    @Override
    public EnumFacing rotate(EnumFacing p_rotate_1_) {
        return this.rotateFace(p_rotate_1_);
    }

    @Override
    public int rotate(EnumFacing p_rotate_1_, int p_rotate_2_) {
        return this.rotateVertex(p_rotate_1_, p_rotate_2_);
    }
}

