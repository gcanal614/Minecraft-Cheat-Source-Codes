/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.src.Config;
import net.optifine.SmartAnimations;
import net.optifine.render.GlAlphaState;
import net.optifine.render.GlBlendState;
import net.optifine.shaders.Shaders;
import net.optifine.util.LockCounter;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

public class GlStateManager {
    public static boolean clearEnabled = true;
    private static final AlphaState alphaState = new AlphaState();
    private static final BooleanState lightingState = new BooleanState(2896);
    private static final BooleanState[] lightState = new BooleanState[8];
    private static final ColorMaterialState colorMaterialState = new ColorMaterialState();
    private static final BlendState blendState = new BlendState();
    private static final DepthState depthState = new DepthState();
    private static final FogState fogState = new FogState();
    private static final CullState cullState = new CullState();
    private static final PolygonOffsetState polygonOffsetState = new PolygonOffsetState();
    private static final ColorLogicState colorLogicState = new ColorLogicState();
    private static final TexGenState texGenState = new TexGenState();
    private static final ClearState clearState = new ClearState();
    private static final StencilState stencilState = new StencilState();
    private static final BooleanState normalizeState = new BooleanState(2977);
    public static int activeTextureUnit = 0;
    public static final TextureState[] textureState = new TextureState[32];
    private static int activeShadeModel = 7425;
    private static final BooleanState rescaleNormalState = new BooleanState(32826);
    private static final ColorMask colorMaskState = new ColorMask();
    private static final Color colorState = new Color();
    private static final LockCounter alphaLock = new LockCounter();
    private static final GlAlphaState alphaLockState = new GlAlphaState();
    private static final LockCounter blendLock = new LockCounter();
    private static final GlBlendState blendLockState = new GlBlendState();
    private static boolean creatingDisplayList = false;

    public static void pushMatrix() {
        GL11.glPushMatrix();
    }

    public static void popMatrix() {
        GL11.glPopMatrix();
    }

    public static void translate(float p_translate_0_, float p_translate_1_, float p_translate_2_) {
        GL11.glTranslatef(p_translate_0_, p_translate_1_, p_translate_2_);
    }

    public static void translate(double p_translate_0_, double p_translate_2_, double p_translate_4_) {
        GL11.glTranslated(p_translate_0_, p_translate_2_, p_translate_4_);
    }

    public static void rotate(float p_rotate_0_, float p_rotate_1_, float p_rotate_2_, float p_rotate_3_) {
        GL11.glRotatef(p_rotate_0_, p_rotate_1_, p_rotate_2_, p_rotate_3_);
    }

    public static void matrixMode(int p_matrixMode_0_) {
        GL11.glMatrixMode(p_matrixMode_0_);
    }

    public static void loadIdentity() {
        GL11.glLoadIdentity();
    }

    public static void scale(float p_scale_0_, float p_scale_1_, float p_scale_2_) {
        GL11.glScalef(p_scale_0_, p_scale_1_, p_scale_2_);
    }

    public static void scale(double p_scale_0_, double p_scale_2_, double p_scale_4_) {
        GL11.glScaled(p_scale_0_, p_scale_2_, p_scale_4_);
    }

    public static void ortho(double left, double right, double bottom, double top, double zNear, double zFar) {
        GL11.glOrtho(left, right, bottom, top, zNear, zFar);
    }

    public static void disableAlpha() {
        if (alphaLock.isLocked()) {
            alphaLockState.setDisabled();
        } else {
            GlStateManager.alphaState.field_179208_a.setDisabled();
        }
    }

    public static void enableAlpha() {
        if (alphaLock.isLocked()) {
            alphaLockState.setEnabled();
        } else {
            GlStateManager.alphaState.field_179208_a.setEnabled();
        }
    }

    public static void alphaFunc(int func, float ref) {
        if (alphaLock.isLocked()) {
            alphaLockState.setFuncRef(func, ref);
        } else if (func != GlStateManager.alphaState.func || ref != GlStateManager.alphaState.ref) {
            GlStateManager.alphaState.func = func;
            GlStateManager.alphaState.ref = ref;
            GL11.glAlphaFunc(func, ref);
        }
    }

    public static void enableLighting() {
        lightingState.setEnabled();
    }

    public static void disableLighting() {
        lightingState.setDisabled();
    }

    public static void enableLight(int light) {
        lightState[light].setEnabled();
    }

    public static void disableLight(int light) {
        lightState[light].setDisabled();
    }

    public static void enableColorMaterial() {
        GlStateManager.colorMaterialState.field_179191_a.setEnabled();
    }

    public static void disableColorMaterial() {
        GlStateManager.colorMaterialState.field_179191_a.setDisabled();
    }

    public static void colorMaterial(int face, int mode) {
        if (face != GlStateManager.colorMaterialState.field_179189_b || mode != GlStateManager.colorMaterialState.field_179190_c) {
            GlStateManager.colorMaterialState.field_179189_b = face;
            GlStateManager.colorMaterialState.field_179190_c = mode;
            GL11.glColorMaterial(face, mode);
        }
    }

    public static void disableDepth() {
        GlStateManager.depthState.depthTest.setDisabled();
    }

    public static void enableDepth() {
        GlStateManager.depthState.depthTest.setEnabled();
    }

    public static void depthFunc(int depthFunc) {
        if (depthFunc != GlStateManager.depthState.depthFunc) {
            GlStateManager.depthState.depthFunc = depthFunc;
            GL11.glDepthFunc(depthFunc);
        }
    }

    public static void depthMask(boolean flagIn) {
        if (flagIn != GlStateManager.depthState.maskEnabled) {
            GlStateManager.depthState.maskEnabled = flagIn;
            GL11.glDepthMask(flagIn);
        }
    }

    public static void disableBlend() {
        if (blendLock.isLocked()) {
            blendLockState.setDisabled();
        } else {
            GlStateManager.blendState.field_179213_a.setDisabled();
        }
    }

    public static void enableBlend() {
        if (blendLock.isLocked()) {
            blendLockState.setEnabled();
        } else {
            GlStateManager.blendState.field_179213_a.setEnabled();
        }
    }

    public static void blendFunc(int srcFactor, int dstFactor) {
        if (blendLock.isLocked()) {
            blendLockState.setFactors(srcFactor, dstFactor);
        } else if (srcFactor != GlStateManager.blendState.srcFactor || dstFactor != GlStateManager.blendState.dstFactor || srcFactor != GlStateManager.blendState.srcFactorAlpha || dstFactor != GlStateManager.blendState.dstFactorAlpha) {
            GlStateManager.blendState.srcFactor = srcFactor;
            GlStateManager.blendState.dstFactor = dstFactor;
            GlStateManager.blendState.srcFactorAlpha = srcFactor;
            GlStateManager.blendState.dstFactorAlpha = dstFactor;
            if (Config.isShaders()) {
                Shaders.uniform_blendFunc.setValue(srcFactor, dstFactor, srcFactor, dstFactor);
            }
            GL11.glBlendFunc(srcFactor, dstFactor);
        }
    }

    public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
        if (blendLock.isLocked()) {
            blendLockState.setFactors(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
        } else if (srcFactor != GlStateManager.blendState.srcFactor || dstFactor != GlStateManager.blendState.dstFactor || srcFactorAlpha != GlStateManager.blendState.srcFactorAlpha || dstFactorAlpha != GlStateManager.blendState.dstFactorAlpha) {
            GlStateManager.blendState.srcFactor = srcFactor;
            GlStateManager.blendState.dstFactor = dstFactor;
            GlStateManager.blendState.srcFactorAlpha = srcFactorAlpha;
            GlStateManager.blendState.dstFactorAlpha = dstFactorAlpha;
            if (Config.isShaders()) {
                Shaders.uniform_blendFunc.setValue(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
            }
            GL14.glBlendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
        }
    }

    public static void enableFog() {
        GlStateManager.fogState.field_179049_a.setEnabled();
    }

    public static void disableFog() {
        GlStateManager.fogState.field_179049_a.setDisabled();
    }

    public static void setFog(int param) {
        if (param != GlStateManager.fogState.field_179047_b) {
            GlStateManager.fogState.field_179047_b = param;
            GL11.glFogi(2917, param);
            if (Config.isShaders()) {
                Shaders.setFogMode(param);
            }
        }
    }

    public static void setFogDensity(float param) {
        if (param < 0.0f) {
            param = 0.0f;
        }
        if (param != GlStateManager.fogState.field_179048_c) {
            GlStateManager.fogState.field_179048_c = param;
            GL11.glFogf(2914, param);
            if (Config.isShaders()) {
                Shaders.setFogDensity(param);
            }
        }
    }

    public static void setFogStart(float param) {
        if (param != GlStateManager.fogState.field_179045_d) {
            GlStateManager.fogState.field_179045_d = param;
            GL11.glFogf(2915, param);
        }
    }

    public static void setFogEnd(float param) {
        if (param != GlStateManager.fogState.field_179046_e) {
            GlStateManager.fogState.field_179046_e = param;
            GL11.glFogf(2916, param);
        }
    }

    public static void enableCull() {
        GlStateManager.cullState.field_179054_a.setEnabled();
    }

    public static void disableCull() {
        GlStateManager.cullState.field_179054_a.setDisabled();
    }

    public static void cullFace(int mode) {
        if (mode != GlStateManager.cullState.field_179053_b) {
            GlStateManager.cullState.field_179053_b = mode;
            GL11.glCullFace(mode);
        }
    }

    public static void enablePolygonOffset() {
        GlStateManager.polygonOffsetState.field_179044_a.setEnabled();
    }

    public static void disablePolygonOffset() {
        GlStateManager.polygonOffsetState.field_179044_a.setDisabled();
    }

    public static void doPolygonOffset(float factor, float units) {
        if (factor != GlStateManager.polygonOffsetState.field_179043_c || units != GlStateManager.polygonOffsetState.field_179041_d) {
            GlStateManager.polygonOffsetState.field_179043_c = factor;
            GlStateManager.polygonOffsetState.field_179041_d = units;
            GL11.glPolygonOffset(factor, units);
        }
    }

    public static void enableColorLogic() {
        GlStateManager.colorLogicState.field_179197_a.setEnabled();
    }

    public static void disableColorLogic() {
        GlStateManager.colorLogicState.field_179197_a.setDisabled();
    }

    public static void colorLogicOp(int opcode) {
        if (opcode != GlStateManager.colorLogicState.field_179196_b) {
            GlStateManager.colorLogicState.field_179196_b = opcode;
            GL11.glLogicOp(opcode);
        }
    }

    public static void color(float p_color_0_, float p_color_1_, float p_color_2_, float p_color_3_) {
        if (p_color_0_ != GlStateManager.colorState.red || p_color_1_ != GlStateManager.colorState.green || p_color_2_ != GlStateManager.colorState.blue || p_color_3_ != GlStateManager.colorState.alpha) {
            GlStateManager.colorState.red = p_color_0_;
            GlStateManager.colorState.green = p_color_1_;
            GlStateManager.colorState.blue = p_color_2_;
            GlStateManager.colorState.alpha = p_color_3_;
            GL11.glColor4f(p_color_0_, p_color_1_, p_color_2_, p_color_3_);
        }
    }

    public static void enableTexGenCoord(TexGen p_179087_0_) {
        GlStateManager.texGenCoord((TexGen)p_179087_0_).field_179067_a.setEnabled();
    }

    public static void disableTexGenCoord(TexGen p_179100_0_) {
        GlStateManager.texGenCoord((TexGen)p_179100_0_).field_179067_a.setDisabled();
    }

    public static void texGen(TexGen p_179149_0_, int p_179149_1_) {
        TexGenCoord glstatemanager$texgencoord = GlStateManager.texGenCoord(p_179149_0_);
        if (p_179149_1_ != glstatemanager$texgencoord.field_179066_c) {
            glstatemanager$texgencoord.field_179066_c = p_179149_1_;
            GL11.glTexGeni(glstatemanager$texgencoord.field_179065_b, 9472, p_179149_1_);
        }
    }

    public static void func_179105_a(TexGen p_179105_0_, int pname, FloatBuffer params) {
        GL11.glTexGen(GlStateManager.texGenCoord((TexGen)p_179105_0_).field_179065_b, pname, params);
    }

    private static TexGenCoord texGenCoord(TexGen p_179125_0_) {
        switch (p_179125_0_) {
            case S: {
                return GlStateManager.texGenState.field_179064_a;
            }
            case T: {
                return GlStateManager.texGenState.field_179062_b;
            }
            case R: {
                return GlStateManager.texGenState.field_179063_c;
            }
            case Q: {
                return GlStateManager.texGenState.field_179061_d;
            }
        }
        return GlStateManager.texGenState.field_179064_a;
    }

    public static void setActiveTexture(int texture) {
        if (activeTextureUnit != texture - OpenGlHelper.defaultTexUnit) {
            activeTextureUnit = texture - OpenGlHelper.defaultTexUnit;
            OpenGlHelper.setActiveTexture(texture);
        }
    }

    public static void enableTexture2D() {
        GlStateManager.textureState[GlStateManager.activeTextureUnit].texture2DState.setEnabled();
    }

    public static void disableTexture2D() {
        GlStateManager.textureState[GlStateManager.activeTextureUnit].texture2DState.setDisabled();
    }

    public static void deleteTexture(int texture) {
        if (texture != 0) {
            GL11.glDeleteTextures(texture);
            for (TextureState glstatemanager$texturestate : textureState) {
                if (glstatemanager$texturestate.textureName != texture) continue;
                glstatemanager$texturestate.textureName = 0;
            }
        }
    }

    public static void bindTexture(int texture) {
        if (texture != GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName) {
            GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName = texture;
            GL11.glBindTexture(3553, texture);
            if (SmartAnimations.isActive()) {
                SmartAnimations.textureRendered(texture);
            }
        }
    }

    public static void enableNormalize() {
        normalizeState.setEnabled();
    }

    public static void disableNormalize() {
        normalizeState.setDisabled();
    }

    public static void shadeModel(int mode) {
        if (mode != activeShadeModel) {
            activeShadeModel = mode;
            GL11.glShadeModel(mode);
        }
    }

    public static void enableRescaleNormal() {
        rescaleNormalState.setEnabled();
    }

    public static void disableRescaleNormal() {
        rescaleNormalState.setDisabled();
    }

    public static void viewport(int x, int y, int width, int height) {
        GL11.glViewport(x, y, width, height);
    }

    public static void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        if (red != GlStateManager.colorMaskState.red || green != GlStateManager.colorMaskState.green || blue != GlStateManager.colorMaskState.blue || alpha != GlStateManager.colorMaskState.alpha) {
            GlStateManager.colorMaskState.red = red;
            GlStateManager.colorMaskState.green = green;
            GlStateManager.colorMaskState.blue = blue;
            GlStateManager.colorMaskState.alpha = alpha;
            GL11.glColorMask(red, green, blue, alpha);
        }
    }

    public static void clearDepth(double depth) {
        if (depth != GlStateManager.clearState.field_179205_a) {
            GlStateManager.clearState.field_179205_a = depth;
            GL11.glClearDepth(depth);
        }
    }

    public static void clearColor(float red, float green, float blue, float alpha) {
        if (red != GlStateManager.clearState.field_179203_b.red || green != GlStateManager.clearState.field_179203_b.green || blue != GlStateManager.clearState.field_179203_b.blue || alpha != GlStateManager.clearState.field_179203_b.alpha) {
            GlStateManager.clearState.field_179203_b.red = red;
            GlStateManager.clearState.field_179203_b.green = green;
            GlStateManager.clearState.field_179203_b.blue = blue;
            GlStateManager.clearState.field_179203_b.alpha = alpha;
            GL11.glClearColor(red, green, blue, alpha);
        }
    }

    public static void clear(int mask) {
        if (clearEnabled) {
            GL11.glClear(mask);
        }
    }

    public static void getFloat(int pname, FloatBuffer params) {
        GL11.glGetFloat(pname, params);
    }

    public static void resetColor() {
        GlStateManager.colorState.alpha = -1.0f;
        GlStateManager.colorState.blue = -1.0f;
        GlStateManager.colorState.green = -1.0f;
        GlStateManager.colorState.red = -1.0f;
    }

    public static void glDrawArrays(int p_glDrawArrays_0_, int p_glDrawArrays_1_, int p_glDrawArrays_2_) {
        int i;
        GL11.glDrawArrays(p_glDrawArrays_0_, p_glDrawArrays_1_, p_glDrawArrays_2_);
        if (Config.isShaders() && !creatingDisplayList && (i = Shaders.activeProgram.getCountInstances()) > 1) {
            for (int j = 1; j < i; ++j) {
                Shaders.uniform_instanceId.setValue(j);
                GL11.glDrawArrays(p_glDrawArrays_0_, p_glDrawArrays_1_, p_glDrawArrays_2_);
            }
            Shaders.uniform_instanceId.setValue(0);
        }
    }

    public static void callList(int list) {
        int i;
        GL11.glCallList(list);
        if (Config.isShaders() && !creatingDisplayList && (i = Shaders.activeProgram.getCountInstances()) > 1) {
            for (int j = 1; j < i; ++j) {
                Shaders.uniform_instanceId.setValue(j);
                GL11.glCallList(list);
            }
            Shaders.uniform_instanceId.setValue(0);
        }
    }

    public static void callLists(IntBuffer p_callLists_0_) {
        int i;
        GL11.glCallLists(p_callLists_0_);
        if (Config.isShaders() && !creatingDisplayList && (i = Shaders.activeProgram.getCountInstances()) > 1) {
            for (int j = 1; j < i; ++j) {
                Shaders.uniform_instanceId.setValue(j);
                GL11.glCallLists(p_callLists_0_);
            }
            Shaders.uniform_instanceId.setValue(0);
        }
    }

    public static void glNewList(int p_glNewList_0_, int p_glNewList_1_) {
        GL11.glNewList(p_glNewList_0_, p_glNewList_1_);
        creatingDisplayList = true;
    }

    public static void glEndList() {
        GL11.glEndList();
        creatingDisplayList = false;
    }

    public static int getActiveTextureUnit() {
        return OpenGlHelper.defaultTexUnit + activeTextureUnit;
    }

    public static void bindCurrentTexture() {
        GL11.glBindTexture(3553, GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName);
    }

    public static int getBoundTexture() {
        return GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName;
    }

    public static void checkBoundTexture() {
        if (Config.isMinecraftThread()) {
            int i = GL11.glGetInteger(34016);
            int j = GL11.glGetInteger(32873);
            int k = GlStateManager.getActiveTextureUnit();
            int l = GlStateManager.getBoundTexture();
            if (l > 0 && (i != k || j != l)) {
                Config.dbg("checkTexture: act: " + k + ", glAct: " + i + ", tex: " + l + ", glTex: " + j);
            }
        }
    }

    public static void deleteTextures(IntBuffer p_deleteTextures_0_) {
        p_deleteTextures_0_.rewind();
        while (p_deleteTextures_0_.position() < p_deleteTextures_0_.limit()) {
            int i = p_deleteTextures_0_.get();
            GlStateManager.deleteTexture(i);
        }
        p_deleteTextures_0_.rewind();
    }

    public static boolean isFogEnabled() {
        return GlStateManager.fogState.field_179049_a.currentState;
    }

    public static void setFogEnabled(boolean p_setFogEnabled_0_) {
        GlStateManager.fogState.field_179049_a.setState(p_setFogEnabled_0_);
    }

    public static void lockAlpha(GlAlphaState p_lockAlpha_0_) {
        if (!alphaLock.isLocked()) {
            GlStateManager.getAlphaState(alphaLockState);
            GlStateManager.setAlphaState(p_lockAlpha_0_);
            alphaLock.lock();
        }
    }

    public static void unlockAlpha() {
        if (alphaLock.unlock()) {
            GlStateManager.setAlphaState(alphaLockState);
        }
    }

    public static void getAlphaState(GlAlphaState p_getAlphaState_0_) {
        if (alphaLock.isLocked()) {
            p_getAlphaState_0_.setState(alphaLockState);
        } else {
            p_getAlphaState_0_.setState(GlStateManager.alphaState.field_179208_a.currentState, GlStateManager.alphaState.func, GlStateManager.alphaState.ref);
        }
    }

    public static void setAlphaState(GlAlphaState p_setAlphaState_0_) {
        if (alphaLock.isLocked()) {
            alphaLockState.setState(p_setAlphaState_0_);
        } else {
            GlStateManager.alphaState.field_179208_a.setState(p_setAlphaState_0_.isEnabled());
            GlStateManager.alphaFunc(p_setAlphaState_0_.getFunc(), p_setAlphaState_0_.getRef());
        }
    }

    public static void lockBlend(GlBlendState p_lockBlend_0_) {
        if (!blendLock.isLocked()) {
            GlStateManager.getBlendState(blendLockState);
            GlStateManager.setBlendState(p_lockBlend_0_);
            blendLock.lock();
        }
    }

    public static void unlockBlend() {
        if (blendLock.unlock()) {
            GlStateManager.setBlendState(blendLockState);
        }
    }

    public static void getBlendState(GlBlendState p_getBlendState_0_) {
        if (blendLock.isLocked()) {
            p_getBlendState_0_.setState(blendLockState);
        } else {
            p_getBlendState_0_.setState(GlStateManager.blendState.field_179213_a.currentState, GlStateManager.blendState.srcFactor, GlStateManager.blendState.dstFactor, GlStateManager.blendState.srcFactorAlpha, GlStateManager.blendState.dstFactorAlpha);
        }
    }

    public static void setBlendState(GlBlendState p_setBlendState_0_) {
        if (blendLock.isLocked()) {
            blendLockState.setState(p_setBlendState_0_);
        } else {
            GlStateManager.blendState.field_179213_a.setState(p_setBlendState_0_.isEnabled());
            if (!p_setBlendState_0_.isSeparate()) {
                GlStateManager.blendFunc(p_setBlendState_0_.getSrcFactor(), p_setBlendState_0_.getDstFactor());
            } else {
                GlStateManager.tryBlendFuncSeparate(p_setBlendState_0_.getSrcFactor(), p_setBlendState_0_.getDstFactor(), p_setBlendState_0_.getSrcFactorAlpha(), p_setBlendState_0_.getDstFactorAlpha());
            }
        }
    }

    public static void glMultiDrawArrays(int p_glMultiDrawArrays_0_, IntBuffer p_glMultiDrawArrays_1_, IntBuffer p_glMultiDrawArrays_2_) {
        int i;
        GL14.glMultiDrawArrays(p_glMultiDrawArrays_0_, p_glMultiDrawArrays_1_, p_glMultiDrawArrays_2_);
        if (Config.isShaders() && !creatingDisplayList && (i = Shaders.activeProgram.getCountInstances()) > 1) {
            for (int j = 1; j < i; ++j) {
                Shaders.uniform_instanceId.setValue(j);
                GL14.glMultiDrawArrays(p_glMultiDrawArrays_0_, p_glMultiDrawArrays_1_, p_glMultiDrawArrays_2_);
            }
            Shaders.uniform_instanceId.setValue(0);
        }
    }

    static {
        for (int i = 0; i < 8; ++i) {
            GlStateManager.lightState[i] = new BooleanState(16384 + i);
        }
        for (int j = 0; j < textureState.length; ++j) {
            GlStateManager.textureState[j] = new TextureState();
        }
    }

    public static class TextureState {
        public BooleanState texture2DState = new BooleanState(3553);
        public int textureName = 0;

        private TextureState() {
        }
    }

    static class TexGenState {
        public TexGenCoord field_179064_a = new TexGenCoord(8192, 3168);
        public TexGenCoord field_179062_b = new TexGenCoord(8193, 3169);
        public TexGenCoord field_179063_c = new TexGenCoord(8194, 3170);
        public TexGenCoord field_179061_d = new TexGenCoord(8195, 3171);

        private TexGenState() {
        }
    }

    static class TexGenCoord {
        public BooleanState field_179067_a;
        public int field_179065_b;
        public int field_179066_c = -1;

        public TexGenCoord(int p_i46254_1_, int p_i46254_2_) {
            this.field_179065_b = p_i46254_1_;
            this.field_179067_a = new BooleanState(p_i46254_2_);
        }
    }

    static class StencilState {
        public StencilFunc field_179078_a = new StencilFunc();
        public int field_179076_b = -1;
        public int field_179077_c = 7680;
        public int field_179074_d = 7680;
        public int field_179075_e = 7680;

        private StencilState() {
        }
    }

    static class StencilFunc {
        public int field_179081_a = 519;
        public int field_179079_b = 0;
        public int field_179080_c = -1;

        private StencilFunc() {
        }
    }

    static class PolygonOffsetState {
        public BooleanState field_179044_a = new BooleanState(32823);
        public BooleanState field_179042_b = new BooleanState(10754);
        public float field_179043_c = 0.0f;
        public float field_179041_d = 0.0f;

        private PolygonOffsetState() {
        }
    }

    static class FogState {
        public BooleanState field_179049_a = new BooleanState(2912);
        public int field_179047_b = 2048;
        public float field_179048_c = 1.0f;
        public float field_179045_d = 0.0f;
        public float field_179046_e = 1.0f;

        private FogState() {
        }
    }

    static class DepthState {
        public BooleanState depthTest = new BooleanState(2929);
        public boolean maskEnabled = true;
        public int depthFunc = 513;

        private DepthState() {
        }
    }

    static class CullState {
        public BooleanState field_179054_a = new BooleanState(2884);
        public int field_179053_b = 1029;

        private CullState() {
        }
    }

    static class ColorMaterialState {
        public BooleanState field_179191_a = new BooleanState(2903);
        public int field_179189_b = 1032;
        public int field_179190_c = 5634;

        private ColorMaterialState() {
        }
    }

    static class ColorMask {
        public boolean red = true;
        public boolean green = true;
        public boolean blue = true;
        public boolean alpha = true;

        private ColorMask() {
        }
    }

    static class ColorLogicState {
        public BooleanState field_179197_a = new BooleanState(3058);
        public int field_179196_b = 5379;

        private ColorLogicState() {
        }
    }

    static class Color {
        public float red = 1.0f;
        public float green = 1.0f;
        public float blue = 1.0f;
        public float alpha = 1.0f;

        public Color() {
        }

        public Color(float redIn, float greenIn, float blueIn, float alphaIn) {
            this.red = redIn;
            this.green = greenIn;
            this.blue = blueIn;
            this.alpha = alphaIn;
        }
    }

    static class ClearState {
        public double field_179205_a = 1.0;
        public Color field_179203_b = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        public int field_179204_c = 0;

        private ClearState() {
        }
    }

    static class BooleanState {
        private final int capability;
        private boolean currentState = false;

        public BooleanState(int capabilityIn) {
            this.capability = capabilityIn;
        }

        public void setDisabled() {
            this.setState(false);
        }

        public void setEnabled() {
            this.setState(true);
        }

        public void setState(boolean state) {
            if (state != this.currentState) {
                this.currentState = state;
                if (state) {
                    GL11.glEnable(this.capability);
                } else {
                    GL11.glDisable(this.capability);
                }
            }
        }
    }

    static class BlendState {
        public BooleanState field_179213_a = new BooleanState(3042);
        public int srcFactor = 1;
        public int dstFactor = 0;
        public int srcFactorAlpha = 1;
        public int dstFactorAlpha = 0;

        private BlendState() {
        }
    }

    static class AlphaState {
        public BooleanState field_179208_a = new BooleanState(3008);
        public int func = 519;
        public float ref = -1.0f;

        private AlphaState() {
        }
    }

    public static enum TexGen {
        S,
        T,
        R,
        Q;

    }
}

