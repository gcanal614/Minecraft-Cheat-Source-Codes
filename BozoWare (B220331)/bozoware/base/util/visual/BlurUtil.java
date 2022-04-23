// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.visual;

import java.util.ArrayList;
import java.nio.FloatBuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.BufferUtils;
import java.util.Iterator;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import net.minecraft.client.gui.ScaledResolution;
import java.util.List;
import net.minecraft.client.shader.Framebuffer;

public final class BlurUtil
{
    private static final String BLUR_FRAG_SHADER = "#version 120\n\nuniform sampler2D texture;\nuniform sampler2D texture2;\nuniform vec2 texelSize;\nuniform vec2 direction;\nuniform float radius;\nuniform float weights[256];\n\nvoid main() {\n    vec4 color = vec4(0.0);\n    vec2 texCoord = gl_TexCoord[0].st;\n    if (direction.y == 0)\n        if (texture2D(texture2, texCoord).a == 0.0) return;\n    for (float f = -radius; f <= radius; f++) {\n        color += texture2D(texture, texCoord + f * texelSize * direction) * (weights[int(abs(f))]);\n    }\n    gl_FragColor = vec4(color.rgb, 1.0);\n}";
    public static final String VERTEX_SHADER = "#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}";
    private static final GLShader blurShader;
    private static Framebuffer framebuffer;
    private static Framebuffer framebufferRender;
    public static boolean disableBlur;
    private static List<double[]> blurAreas;
    
    private BlurUtil() {
    }
    
    public static void blurArea(final double x, final double y, final double width, final double height) {
        if (BlurUtil.disableBlur) {
            return;
        }
        BlurUtil.blurAreas.add(new double[] { x, y, width, height });
    }
    
    public static void onRenderGameOverlay(final Framebuffer mcFramebuffer, final ScaledResolution sr) {
        if (BlurUtil.framebuffer == null || BlurUtil.framebufferRender == null || BlurUtil.blurAreas.isEmpty()) {
            return;
        }
        BlurUtil.framebufferRender.framebufferClear();
        BlurUtil.framebufferRender.bindFramebuffer(false);
        for (final double[] area : BlurUtil.blurAreas) {
            glDrawFilledQuad(area[0], area[1], area[2], area[3], -16777216);
        }
        BlurUtil.blurAreas.clear();
        final boolean restore = BloomUtil.glEnableBlend();
        BlurUtil.framebuffer.bindFramebuffer(false);
        BlurUtil.blurShader.use();
        onPass(1);
        glDrawFramebuffer(sr, mcFramebuffer);
        GL20.glUseProgram(0);
        mcFramebuffer.bindFramebuffer(false);
        BlurUtil.blurShader.use();
        onPass(0);
        GL13.glActiveTexture(34004);
        GL11.glBindTexture(3553, BlurUtil.framebufferRender.framebufferTexture);
        GL13.glActiveTexture(33984);
        glDrawFramebuffer(sr, BlurUtil.framebuffer);
        GL20.glUseProgram(0);
        BloomUtil.glRestoreBlend(restore);
    }
    
    private static void onPass(final int pass) {
        GL20.glUniform2f(BlurUtil.blurShader.getUniformLocation("direction"), (float)(1 - pass), (float)pass);
    }
    
    private static void glDrawFramebuffer(final ScaledResolution scaledResolution, final Framebuffer framebuffer) {
        GL11.glBindTexture(3553, framebuffer.framebufferTexture);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2i(0, 0);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2i(0, scaledResolution.getScaledHeight());
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2i(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2i(scaledResolution.getScaledWidth(), 0);
        GL11.glEnd();
    }
    
    public static void onFrameBufferResize(final int width, final int height) {
        if (BlurUtil.framebuffer != null) {
            BlurUtil.framebuffer.deleteFramebuffer();
        }
        if (BlurUtil.framebufferRender != null) {
            BlurUtil.framebufferRender.deleteFramebuffer();
        }
        BlurUtil.framebuffer = new Framebuffer(width, height, false);
        BlurUtil.framebufferRender = new Framebuffer(width, height, false);
    }
    
    static float calculateGaussianOffset(final float x, final float sigma) {
        final float pow = x / sigma;
        return (float)(1.0 / (Math.abs(sigma) * 2.50662827463) * Math.exp(-0.5 * pow * pow));
    }
    
    public static void glDrawFilledQuad(final double x, final double y, final double width, final double height, final int colour) {
        final boolean restore = BloomUtil.glEnableBlend();
        GL11.glDisable(3553);
        glColour(colour);
        GL11.glBegin(7);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y + height);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x + width, y);
        GL11.glEnd();
        BloomUtil.glRestoreBlend(restore);
        GL11.glEnable(3553);
    }
    
    public static void glColour(final int color) {
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
    }
    
    static {
        blurShader = new GLShader("#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}", "#version 120\n\nuniform sampler2D texture;\nuniform sampler2D texture2;\nuniform vec2 texelSize;\nuniform vec2 direction;\nuniform float radius;\nuniform float weights[256];\n\nvoid main() {\n    vec4 color = vec4(0.0);\n    vec2 texCoord = gl_TexCoord[0].st;\n    if (direction.y == 0)\n        if (texture2D(texture2, texCoord).a == 0.0) return;\n    for (float f = -radius; f <= radius; f++) {\n        color += texture2D(texture, texCoord + f * texelSize * direction) * (weights[int(abs(f))]);\n    }\n    gl_FragColor = vec4(color.rgb, 1.0);\n}") {
            @Override
            public void setupUniforms() {
                this.setupUniform("texture");
                this.setupUniform("texture2");
                this.setupUniform("texelSize");
                this.setupUniform("radius");
                this.setupUniform("direction");
                this.setupUniform("weights");
            }
            
            @Override
            public void updateUniforms() {
                final float radius = 20.0f;
                GL20.glUniform1i(this.getUniformLocation("texture"), 0);
                GL20.glUniform1i(this.getUniformLocation("texture2"), 20);
                GL20.glUniform1f(this.getUniformLocation("radius"), 20.0f);
                final FloatBuffer buffer = BufferUtils.createFloatBuffer(256);
                final float blurRadius = 10.0f;
                for (int i = 0; i <= 10.0f; ++i) {
                    buffer.put(BlurUtil.calculateGaussianOffset((float)i, 5.0f));
                }
                buffer.rewind();
                GL20.glUniform1(this.getUniformLocation("weights"), buffer);
                GL20.glUniform2f(this.getUniformLocation("texelSize"), 1.0f / Display.getWidth(), 1.0f / Display.getHeight());
            }
        };
        BlurUtil.disableBlur = false;
        BlurUtil.blurAreas = new ArrayList<double[]>();
    }
}
