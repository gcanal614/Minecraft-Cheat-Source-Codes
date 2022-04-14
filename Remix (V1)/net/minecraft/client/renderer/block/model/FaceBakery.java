package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.model.*;
import net.minecraftforge.client.model.*;
import optifine.*;
import net.minecraft.util.*;
import shadersmod.client.*;
import net.minecraft.client.renderer.*;
import javax.vecmath.*;

public class FaceBakery
{
    private static final double field_178418_a;
    private static final double field_178417_b;
    
    public static EnumFacing func_178410_a(final int[] p_178410_0_) {
        final int step = p_178410_0_.length / 4;
        final int step2 = step * 2;
        final int step3 = step * 3;
        final Vector3f var1 = new Vector3f(Float.intBitsToFloat(p_178410_0_[0]), Float.intBitsToFloat(p_178410_0_[1]), Float.intBitsToFloat(p_178410_0_[2]));
        final Vector3f var2 = new Vector3f(Float.intBitsToFloat(p_178410_0_[step]), Float.intBitsToFloat(p_178410_0_[step + 1]), Float.intBitsToFloat(p_178410_0_[step + 2]));
        final Vector3f var3 = new Vector3f(Float.intBitsToFloat(p_178410_0_[step2]), Float.intBitsToFloat(p_178410_0_[step2 + 1]), Float.intBitsToFloat(p_178410_0_[step2 + 2]));
        final Vector3f var4 = new Vector3f();
        final Vector3f var5 = new Vector3f();
        final Vector3f var6 = new Vector3f();
        var4.sub(var1, var2);
        var5.sub(var3, var2);
        var6.cross(var5, var4);
        var6.normalize();
        EnumFacing var7 = null;
        float var8 = 0.0f;
        for (final EnumFacing var12 : EnumFacing.values()) {
            final Vec3i var13 = var12.getDirectionVec();
            final Vector3f var14 = new Vector3f((float)var13.getX(), (float)var13.getY(), (float)var13.getZ());
            final float var15 = var6.dot(var14);
            if (var15 >= 0.0f && var15 > var8) {
                var8 = var15;
                var7 = var12;
            }
        }
        if (var8 < 0.719f) {
            if (var7 != EnumFacing.EAST && var7 != EnumFacing.WEST && var7 != EnumFacing.NORTH && var7 != EnumFacing.SOUTH) {
                var7 = EnumFacing.UP;
            }
            else {
                var7 = EnumFacing.NORTH;
            }
        }
        return (var7 == null) ? EnumFacing.UP : var7;
    }
    
    public BakedQuad func_178414_a(final Vector3f posFrom, final Vector3f posTo, final BlockPartFace face, final TextureAtlasSprite sprite, final EnumFacing facing, final ModelRotation modelRotationIn, final BlockPartRotation partRotation, final boolean uvLocked, final boolean shade) {
        return this.makeBakedQuad(posFrom, posTo, face, sprite, facing, modelRotationIn, partRotation, uvLocked, shade);
    }
    
    public BakedQuad makeBakedQuad(final Vector3f posFrom, final Vector3f posTo, final BlockPartFace face, final TextureAtlasSprite sprite, final EnumFacing facing, final ITransformation modelRotationIn, final BlockPartRotation partRotation, final boolean uvLocked, final boolean shade) {
        final int[] var10 = this.makeQuadVertexData(face, sprite, facing, this.func_178403_a(posFrom, posTo), modelRotationIn, partRotation, uvLocked, shade);
        final EnumFacing var11 = func_178410_a(var10);
        if (uvLocked) {
            this.func_178409_a(var10, var11, face.field_178243_e, sprite);
        }
        if (partRotation == null) {
            this.func_178408_a(var10, var11);
        }
        if (Reflector.ForgeHooksClient_fillNormal.exists()) {
            Reflector.callVoid(Reflector.ForgeHooksClient_fillNormal, var10, var11);
        }
        return new BakedQuad(var10, face.field_178245_c, var11, sprite);
    }
    
    private int[] makeQuadVertexData(final BlockPartFace p_178405_1_, final TextureAtlasSprite p_178405_2_, final EnumFacing p_178405_3_, final float[] p_178405_4_, final ITransformation p_178405_5_, final BlockPartRotation p_178405_6_, final boolean p_178405_7_, final boolean shade) {
        byte vertexSize = 28;
        if (Config.isShaders()) {
            vertexSize = 56;
        }
        final int[] var9 = new int[vertexSize];
        for (int var10 = 0; var10 < 4; ++var10) {
            this.fillVertexData(var9, var10, p_178405_3_, p_178405_1_, p_178405_4_, p_178405_2_, p_178405_5_, p_178405_6_, p_178405_7_, shade);
        }
        return var9;
    }
    
    private int func_178413_a(final EnumFacing p_178413_1_) {
        final float var2 = this.func_178412_b(p_178413_1_);
        final int var3 = MathHelper.clamp_int((int)(var2 * 255.0f), 0, 255);
        return 0xFF000000 | var3 << 16 | var3 << 8 | var3;
    }
    
    private float func_178412_b(final EnumFacing p_178412_1_) {
        switch (SwitchEnumFacing.field_178400_a[p_178412_1_.ordinal()]) {
            case 1: {
                if (Config.isShaders()) {
                    return Shaders.blockLightLevel05;
                }
                return 0.5f;
            }
            case 2: {
                return 1.0f;
            }
            case 3:
            case 4: {
                if (Config.isShaders()) {
                    return Shaders.blockLightLevel08;
                }
                return 0.8f;
            }
            case 5:
            case 6: {
                if (Config.isShaders()) {
                    return Shaders.blockLightLevel06;
                }
                return 0.6f;
            }
            default: {
                return 1.0f;
            }
        }
    }
    
    private float[] func_178403_a(final Vector3f pos1, final Vector3f pos2) {
        final float[] var3 = new float[EnumFacing.values().length];
        var3[EnumFaceing.Constants.field_179176_f] = pos1.x / 16.0f;
        var3[EnumFaceing.Constants.field_179178_e] = pos1.y / 16.0f;
        var3[EnumFaceing.Constants.field_179177_d] = pos1.z / 16.0f;
        var3[EnumFaceing.Constants.field_179180_c] = pos2.x / 16.0f;
        var3[EnumFaceing.Constants.field_179179_b] = pos2.y / 16.0f;
        var3[EnumFaceing.Constants.field_179181_a] = pos2.z / 16.0f;
        return var3;
    }
    
    private void fillVertexData(final int[] faceData, final int vertexIndex, final EnumFacing facing, final BlockPartFace partFace, final float[] p_178402_5_, final TextureAtlasSprite sprite, final ITransformation modelRotationIn, final BlockPartRotation partRotation, final boolean uvLocked, final boolean shade) {
        final EnumFacing var11 = modelRotationIn.rotate(facing);
        final int var12 = shade ? this.func_178413_a(var11) : -1;
        final EnumFaceing.VertexInformation var13 = EnumFaceing.func_179027_a(facing).func_179025_a(vertexIndex);
        final Vector3d var14 = new Vector3d(p_178402_5_[var13.field_179184_a], p_178402_5_[var13.field_179182_b], p_178402_5_[var13.field_179183_c]);
        this.func_178407_a(var14, partRotation);
        final int var15 = this.rotateVertex(var14, facing, vertexIndex, modelRotationIn, uvLocked);
        this.func_178404_a(faceData, var15, vertexIndex, var14, var12, sprite, partFace.field_178243_e);
    }
    
    private void func_178404_a(final int[] faceData, final int storeIndex, final int vertexIndex, final Vector3d position, final int shadeColor, final TextureAtlasSprite sprite, final BlockFaceUV faceUV) {
        final int step = faceData.length / 4;
        final int var8 = storeIndex * step;
        faceData[var8] = Float.floatToRawIntBits((float)position.x);
        faceData[var8 + 1] = Float.floatToRawIntBits((float)position.y);
        faceData[var8 + 2] = Float.floatToRawIntBits((float)position.z);
        faceData[var8 + 3] = shadeColor;
        faceData[var8 + 4] = Float.floatToRawIntBits(sprite.getInterpolatedU(faceUV.func_178348_a(vertexIndex)));
        faceData[var8 + 4 + 1] = Float.floatToRawIntBits(sprite.getInterpolatedV(faceUV.func_178346_b(vertexIndex)));
    }
    
    private void func_178407_a(final Vector3d p_178407_1_, final BlockPartRotation p_178407_2_) {
        if (p_178407_2_ != null) {
            final Matrix4d var3 = this.func_178411_a();
            final Vector3d var4 = new Vector3d(0.0, 0.0, 0.0);
            switch (SwitchEnumFacing.field_178399_b[p_178407_2_.field_178342_b.ordinal()]) {
                case 1: {
                    var3.mul(this.func_178416_a(new AxisAngle4d(1.0, 0.0, 0.0, p_178407_2_.field_178343_c * 0.017453292519943295)));
                    var4.set(0.0, 1.0, 1.0);
                    break;
                }
                case 2: {
                    var3.mul(this.func_178416_a(new AxisAngle4d(0.0, 1.0, 0.0, p_178407_2_.field_178343_c * 0.017453292519943295)));
                    var4.set(1.0, 0.0, 1.0);
                    break;
                }
                case 3: {
                    var3.mul(this.func_178416_a(new AxisAngle4d(0.0, 0.0, 1.0, p_178407_2_.field_178343_c * 0.017453292519943295)));
                    var4.set(1.0, 1.0, 0.0);
                    break;
                }
            }
            if (p_178407_2_.field_178341_d) {
                if (Math.abs(p_178407_2_.field_178343_c) == 22.5f) {
                    var4.scale(FaceBakery.field_178418_a);
                }
                else {
                    var4.scale(FaceBakery.field_178417_b);
                }
                var4.add(new Vector3d(1.0, 1.0, 1.0));
            }
            else {
                var4.set(new Vector3d(1.0, 1.0, 1.0));
            }
            this.func_178406_a(p_178407_1_, new Vector3d(p_178407_2_.field_178344_a), var3, var4);
        }
    }
    
    public int func_178415_a(final Vector3d position, final EnumFacing facing, final int vertexIndex, final ModelRotation modelRotationIn, final boolean uvLocked) {
        return this.rotateVertex(position, facing, vertexIndex, modelRotationIn, uvLocked);
    }
    
    public int rotateVertex(final Vector3d position, final EnumFacing facing, final int vertexIndex, final ITransformation modelRotationIn, final boolean uvLocked) {
        if (modelRotationIn == ModelRotation.X0_Y0) {
            return vertexIndex;
        }
        if (Reflector.ForgeHooksClient_transform.exists()) {
            Reflector.call(Reflector.ForgeHooksClient_transform, position, modelRotationIn.getMatrix());
        }
        else {
            this.func_178406_a(position, new Vector3d(0.5, 0.5, 0.5), new Matrix4d(modelRotationIn.getMatrix()), new Vector3d(1.0, 1.0, 1.0));
        }
        return modelRotationIn.rotate(facing, vertexIndex);
    }
    
    private void func_178406_a(final Vector3d position, final Vector3d rotationOrigin, final Matrix4d rotationMatrix, final Vector3d scale) {
        position.sub(rotationOrigin);
        rotationMatrix.transform(position);
        position.x *= scale.x;
        position.y *= scale.y;
        position.z *= scale.z;
        position.add(rotationOrigin);
    }
    
    private Matrix4d func_178416_a(final AxisAngle4d p_178416_1_) {
        final Matrix4d var2 = this.func_178411_a();
        var2.setRotation(p_178416_1_);
        return var2;
    }
    
    private Matrix4d func_178411_a() {
        final Matrix4d var1 = new Matrix4d();
        var1.setIdentity();
        return var1;
    }
    
    public void func_178409_a(final int[] p_178409_1_, final EnumFacing p_178409_2_, final BlockFaceUV p_178409_3_, final TextureAtlasSprite p_178409_4_) {
        for (int var5 = 0; var5 < 4; ++var5) {
            this.func_178401_a(var5, p_178409_1_, p_178409_2_, p_178409_3_, p_178409_4_);
        }
    }
    
    private void func_178408_a(final int[] p_178408_1_, final EnumFacing p_178408_2_) {
        final int[] var3 = new int[p_178408_1_.length];
        System.arraycopy(p_178408_1_, 0, var3, 0, p_178408_1_.length);
        final float[] var4 = new float[EnumFacing.values().length];
        var4[EnumFaceing.Constants.field_179176_f] = 999.0f;
        var4[EnumFaceing.Constants.field_179178_e] = 999.0f;
        var4[EnumFaceing.Constants.field_179177_d] = 999.0f;
        var4[EnumFaceing.Constants.field_179180_c] = -999.0f;
        var4[EnumFaceing.Constants.field_179179_b] = -999.0f;
        var4[EnumFaceing.Constants.field_179181_a] = -999.0f;
        final int step = p_178408_1_.length / 4;
        for (int var5 = 0; var5 < 4; ++var5) {
            final int var6 = step * var5;
            final float var7 = Float.intBitsToFloat(var3[var6]);
            final float var8 = Float.intBitsToFloat(var3[var6 + 1]);
            final float var9 = Float.intBitsToFloat(var3[var6 + 2]);
            if (var7 < var4[EnumFaceing.Constants.field_179176_f]) {
                var4[EnumFaceing.Constants.field_179176_f] = var7;
            }
            if (var8 < var4[EnumFaceing.Constants.field_179178_e]) {
                var4[EnumFaceing.Constants.field_179178_e] = var8;
            }
            if (var9 < var4[EnumFaceing.Constants.field_179177_d]) {
                var4[EnumFaceing.Constants.field_179177_d] = var9;
            }
            if (var7 > var4[EnumFaceing.Constants.field_179180_c]) {
                var4[EnumFaceing.Constants.field_179180_c] = var7;
            }
            if (var8 > var4[EnumFaceing.Constants.field_179179_b]) {
                var4[EnumFaceing.Constants.field_179179_b] = var8;
            }
            if (var9 > var4[EnumFaceing.Constants.field_179181_a]) {
                var4[EnumFaceing.Constants.field_179181_a] = var9;
            }
        }
        final EnumFaceing var10 = EnumFaceing.func_179027_a(p_178408_2_);
        for (int var6 = 0; var6 < 4; ++var6) {
            final int var11 = step * var6;
            final EnumFaceing.VertexInformation var12 = var10.func_179025_a(var6);
            final float var9 = var4[var12.field_179184_a];
            final float var13 = var4[var12.field_179182_b];
            final float var14 = var4[var12.field_179183_c];
            p_178408_1_[var11] = Float.floatToRawIntBits(var9);
            p_178408_1_[var11 + 1] = Float.floatToRawIntBits(var13);
            p_178408_1_[var11 + 2] = Float.floatToRawIntBits(var14);
            for (int var15 = 0; var15 < 4; ++var15) {
                final int var16 = step * var15;
                final float var17 = Float.intBitsToFloat(var3[var16]);
                final float var18 = Float.intBitsToFloat(var3[var16 + 1]);
                final float var19 = Float.intBitsToFloat(var3[var16 + 2]);
                if (MathHelper.func_180185_a(var9, var17) && MathHelper.func_180185_a(var13, var18) && MathHelper.func_180185_a(var14, var19)) {
                    p_178408_1_[var11 + 4] = var3[var16 + 4];
                    p_178408_1_[var11 + 4 + 1] = var3[var16 + 4 + 1];
                }
            }
        }
    }
    
    private void func_178401_a(final int p_178401_1_, final int[] p_178401_2_, final EnumFacing p_178401_3_, final BlockFaceUV p_178401_4_, final TextureAtlasSprite p_178401_5_) {
        final int step = p_178401_2_.length / 4;
        final int var6 = step * p_178401_1_;
        float var7 = Float.intBitsToFloat(p_178401_2_[var6]);
        float var8 = Float.intBitsToFloat(p_178401_2_[var6 + 1]);
        float var9 = Float.intBitsToFloat(p_178401_2_[var6 + 2]);
        if (var7 < -0.1f || var7 >= 1.1f) {
            var7 -= MathHelper.floor_float(var7);
        }
        if (var8 < -0.1f || var8 >= 1.1f) {
            var8 -= MathHelper.floor_float(var8);
        }
        if (var9 < -0.1f || var9 >= 1.1f) {
            var9 -= MathHelper.floor_float(var9);
        }
        float var10 = 0.0f;
        float var11 = 0.0f;
        switch (SwitchEnumFacing.field_178400_a[p_178401_3_.ordinal()]) {
            case 1: {
                var10 = var7 * 16.0f;
                var11 = (1.0f - var9) * 16.0f;
                break;
            }
            case 2: {
                var10 = var7 * 16.0f;
                var11 = var9 * 16.0f;
                break;
            }
            case 3: {
                var10 = (1.0f - var7) * 16.0f;
                var11 = (1.0f - var8) * 16.0f;
                break;
            }
            case 4: {
                var10 = var7 * 16.0f;
                var11 = (1.0f - var8) * 16.0f;
                break;
            }
            case 5: {
                var10 = var9 * 16.0f;
                var11 = (1.0f - var8) * 16.0f;
                break;
            }
            case 6: {
                var10 = (1.0f - var9) * 16.0f;
                var11 = (1.0f - var8) * 16.0f;
                break;
            }
        }
        final int var12 = p_178401_4_.func_178345_c(p_178401_1_) * step;
        p_178401_2_[var12 + 4] = Float.floatToRawIntBits(p_178401_5_.getInterpolatedU(var10));
        p_178401_2_[var12 + 4 + 1] = Float.floatToRawIntBits(p_178401_5_.getInterpolatedV(var11));
    }
    
    static {
        field_178418_a = 1.0 / Math.cos(0.39269908169872414) - 1.0;
        field_178417_b = 1.0 / Math.cos(0.7853981633974483) - 1.0;
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_178400_a;
        static final int[] field_178399_b;
        
        static {
            field_178399_b = new int[EnumFacing.Axis.values().length];
            try {
                SwitchEnumFacing.field_178399_b[EnumFacing.Axis.X.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_178399_b[EnumFacing.Axis.Y.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_178399_b[EnumFacing.Axis.Z.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            field_178400_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                SwitchEnumFacing.field_178400_a[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
        }
    }
}
