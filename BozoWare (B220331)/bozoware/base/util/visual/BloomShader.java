// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.visual;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

public class BloomShader
{
    private static ShaderProgram bloomShader;
    private static Framebuffer bloomBuffer;
    private static float radius;
    
    public BloomShader(final int radius) {
        BloomShader.radius = (float)radius;
    }
    
    public static void bloom() {
        BloomShader.bloomShader.init();
        setupUniforms(1.0f, 0.0f, 0);
        BloomShader.bloomBuffer.framebufferClear();
        BloomShader.bloomBuffer.bindFramebuffer(true);
        GL11.glBindTexture(3553, Minecraft.getMinecraft().getFramebuffer().framebufferTexture);
        BloomShader.bloomShader.renderCanvas();
        BloomShader.bloomBuffer.unbindFramebuffer();
        BloomShader.bloomShader.init();
        setupUniforms(0.0f, 1.0f, 0);
        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
        GL11.glBindTexture(3553, BloomShader.bloomBuffer.framebufferTexture);
        BloomShader.bloomShader.renderCanvas();
        BloomShader.bloomShader.uninit();
    }
    
    public static void setupUniforms(final float x, final float y, final int textureID) {
        BloomShader.bloomShader.setUniformi("originalTexture", 0);
        BloomShader.bloomShader.setUniformi("checkedTexture", textureID);
        BloomShader.bloomShader.setUniformf("texelSize", (float)(1.0 / Minecraft.getMinecraft().displayWidth), (float)(1.0 / Minecraft.getMinecraft().displayHeight));
        BloomShader.bloomShader.setUniformf("direction", x, y);
        BloomShader.bloomShader.setUniformf("shadowAlpha", 120.0f);
        BloomShader.bloomShader.setUniformf("radius", BloomShader.radius);
    }
    
    static {
        BloomShader.bloomShader = new ShaderProgram("bozoware/base/util/visual/fragment/bloom.frag");
        BloomShader.bloomBuffer = new Framebuffer(1, 1, false);
    }
}
