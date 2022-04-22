/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL20
 */
package de.fanta.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderBackgrundAPI {
    public static final String VERTEX_SHADER = "#version 130\n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}";
    private Minecraft mc = Minecraft.getMinecraft();
    private int program = GL20.glCreateProgram();
    private long startTime = System.currentTimeMillis();
    private String fragment;

    public ShaderBackgrundAPI() {
        this("precision mediump float;\r\nuniform float time;\r\nuniform vec2  mouse;\r\nuniform vec2  resolution;\r\n\r\n#define PI 3.14159265359\r\n\r\nmat2 rotate3d(float angle){\r\n\treturn mat2(cos(angle), -sin(angle), sin(angle), cos(angle));\r\n}\r\n\r\nvoid main(void){\r\n   \tvec2 p = (gl_FragCoord.xy * 2.0 - resolution) / min(resolution.x, resolution.y);\r\n\tp = rotate3d(time / 3.0 * PI) * p;\r\n\tfloat t = 0.02 / abs(abs(sin(time)) - length(p));\r\n    \tgl_FragColor = vec4(vec3(t) * vec3(p.x,p.y,1.0), 1.0);\r\n}");
    }

    public ShaderBackgrundAPI(String fragment) {
        this.initShader(fragment);
        this.fragment = fragment;
    }

    public void initShader(String frag) {
        int vertex = GL20.glCreateShader((int)35633);
        int fragment = GL20.glCreateShader((int)35632);
        GL20.glShaderSource((int)vertex, (CharSequence)VERTEX_SHADER);
        GL20.glShaderSource((int)fragment, (CharSequence)frag);
        GL20.glValidateProgram((int)this.program);
        GL20.glCompileShader((int)vertex);
        GL20.glCompileShader((int)fragment);
        GL20.glAttachShader((int)this.program, (int)vertex);
        GL20.glAttachShader((int)this.program, (int)fragment);
        GL20.glLinkProgram((int)this.program);
    }

    public void renderFirst() {
        GL11.glClear((int)16640);
        this.bindShader();
    }

    public void renderSecond(int scaledWidth, int scaledHeight) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glBegin((int)7);
        GL11.glTexCoord2d((double)0.0, (double)1.0);
        GL11.glVertex2d((double)0.0, (double)0.0);
        GL11.glTexCoord2d((double)0.0, (double)0.0);
        GL11.glVertex2d((double)0.0, (double)scaledHeight);
        GL11.glTexCoord2d((double)1.0, (double)0.0);
        GL11.glVertex2d((double)scaledWidth, (double)scaledHeight);
        GL11.glTexCoord2d((double)1.0, (double)1.0);
        GL11.glVertex2d((double)scaledWidth, (double)0.0);
        GL11.glEnd();
        GL20.glUseProgram((int)0);
    }

    public void renderShader() {
        ScaledResolution sr = new ScaledResolution(this.mc);
        this.renderFirst();
        this.addDefaultUniforms();
        this.renderSecond(sr.getScaledWidth(), sr.getScaledHeight());
    }

    public void renderShader(int width, int height) {
        this.renderFirst();
        this.addDefaultUniforms();
        this.renderSecond(width, height);
    }

    public void bindShader() {
        GL20.glUseProgram((int)this.program);
    }

    public void addDefaultUniforms() {
        GL20.glUniform2f((int)GL20.glGetUniformLocation((int)this.program, (CharSequence)"resolution"), (float)this.mc.displayWidth, (float)this.mc.displayHeight);
        float time = (float)(System.currentTimeMillis() - this.startTime) / 1000.0f;
        GL20.glUniform1f((int)GL20.glGetUniformLocation((int)this.program, (CharSequence)"time"), (float)time);
    }
}

