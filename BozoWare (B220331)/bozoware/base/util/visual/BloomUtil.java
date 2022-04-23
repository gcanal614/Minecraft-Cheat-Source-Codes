// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.visual;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL11;
import java.util.Iterator;
import org.lwjgl.opengl.GL20;
import net.minecraft.client.gui.ScaledResolution;
import java.util.List;
import net.minecraft.client.shader.Framebuffer;

public class BloomUtil
{
    private static final String OUTLINE_SHADER = "#version 120\n\nuniform sampler2D texture;\nuniform vec2 texelSize;\n\nuniform vec4 colour;\nuniform float radius;\n\nvoid main() {\n    float a = 0.0;\n    vec3 rgb = colour.rgb;\n    float closest = 1.0;\n    for (float x = -radius; x <= radius; x++) {\n        for (float y = -radius; y <= radius; y++) {\n            vec2 st = gl_TexCoord[0].st + vec2(x, y) * texelSize;\n            vec4 smpl = texture2D(texture, st);\n            float dist = distance(st, gl_TexCoord[0].st);\n            if (smpl.a > 0.0 && dist < closest) {               rgb = smpl.rgb;\n               closest = dist;\n            }\n            a += smpl.a*smpl.a;\n        }\n    }\n    vec4 smpl = texture2D(texture, gl_TexCoord[0].st);\n    gl_FragColor = vec4(rgb, a * colour.a / (4.0 * radius * radius)) * (smpl.a > 0.0 ? 0.0 : 1.0);\n}\n";
    private static final String VERTEX_SHADER = "#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}";
    private static final GLShader shader;
    private static Framebuffer framebuffer;
    private static List<RenderCallback> renders;
    
    private BloomUtil() {
    }
    
    public static void bloom(final RenderCallback render) {
        BloomUtil.renders.add(render);
    }
    
    public static void drawAndBloom(final RenderCallback render) {
        render.render();
        BloomUtil.renders.add(render);
    }
    
    public static void onRenderGameOverlay(final ScaledResolution scaledResolution, final Framebuffer mcFramebuffer) {
        if (BloomUtil.framebuffer == null) {
            return;
        }
        BloomUtil.framebuffer.bindFramebuffer(false);
        for (final RenderCallback callback : BloomUtil.renders) {
            callback.render();
        }
        BloomUtil.renders.clear();
        mcFramebuffer.bindFramebuffer(false);
        BloomUtil.shader.use();
        glDrawFramebuffer(BloomUtil.framebuffer.framebufferTexture, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
        GL20.glUseProgram(0);
        BloomUtil.framebuffer.framebufferClear();
        mcFramebuffer.bindFramebuffer(false);
    }
    
    public static void onFrameBufferResize(final int width, final int height) {
        if (BloomUtil.framebuffer != null) {
            BloomUtil.framebuffer.deleteFramebuffer();
        }
        BloomUtil.framebuffer = new Framebuffer(width, height, false);
    }
    
    public static void glDrawFramebuffer(final int framebufferTexture, final int width, final int height) {
        GL11.glBindTexture(3553, framebufferTexture);
        GL11.glDisable(3008);
        final boolean restore = glEnableBlend();
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2f(0.0f, (float)height);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f((float)width, (float)height);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f((float)width, 0.0f);
        GL11.glEnd();
        glRestoreBlend(restore);
        GL11.glEnable(3008);
    }
    
    public static boolean glEnableBlend() {
        final boolean wasEnabled = GL11.glIsEnabled(3042);
        if (!wasEnabled) {
            GL11.glEnable(3042);
            GL14.glBlendFuncSeparate(770, 771, 1, 0);
        }
        return wasEnabled;
    }
    
    public static void glRestoreBlend(final boolean wasEnabled) {
        if (!wasEnabled) {
            GL11.glDisable(3042);
        }
    }
    
    static {
        shader = new GLShader("#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}", "#version 120\n\nuniform sampler2D texture;\nuniform vec2 texelSize;\n\nuniform vec4 colour;\nuniform float radius;\n\nvoid main() {\n    float a = 0.0;\n    vec3 rgb = colour.rgb;\n    float closest = 1.0;\n    for (float x = -radius; x <= radius; x++) {\n        for (float y = -radius; y <= radius; y++) {\n            vec2 st = gl_TexCoord[0].st + vec2(x, y) * texelSize;\n            vec4 smpl = texture2D(texture, st);\n            float dist = distance(st, gl_TexCoord[0].st);\n            if (smpl.a > 0.0 && dist < closest) {               rgb = smpl.rgb;\n               closest = dist;\n            }\n            a += smpl.a*smpl.a;\n        }\n    }\n    vec4 smpl = texture2D(texture, gl_TexCoord[0].st);\n    gl_FragColor = vec4(rgb, a * colour.a / (4.0 * radius * radius)) * (smpl.a > 0.0 ? 0.0 : 1.0);\n}\n") {
            @Override
            public void setupUniforms() {
                this.setupUniform("texture");
                this.setupUniform("texelSize");
                this.setupUniform("colour");
                this.setupUniform("radius");
                GL20.glUniform1i(this.getUniformLocation("texture"), 0);
            }
            
            @Override
            public void updateUniforms() {
                GL20.glUniform4f(this.getUniformLocation("colour"), 1.0f, 1.0f, 1.0f, 1.0f);
                GL20.glUniform2f(this.getUniformLocation("texelSize"), 1.0f / Minecraft.getMinecraft().displayWidth, 1.0f / Minecraft.getMinecraft().displayHeight);
                GL20.glUniform1f(this.getUniformLocation("radius"), 5.0f);
            }
        };
        BloomUtil.renders = new ArrayList<RenderCallback>();
    }
}
