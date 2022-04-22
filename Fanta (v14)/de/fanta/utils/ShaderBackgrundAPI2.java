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

public class ShaderBackgrundAPI2 {
    public static final String VERTEX_SHADER = "#version 130\n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}";
    private Minecraft mc = Minecraft.getMinecraft();
    private int program = GL20.glCreateProgram();
    private long startTime = System.currentTimeMillis();
    private String fragment;

    public ShaderBackgrundAPI2() {
        this("#ifdef GL_ES\r\nprecision mediump float;\r\n#endif\r\n//made by C04PacketPlayerPosition#1337       \r\n#extension GL_OES_standard_derivatives : enable\r\n\r\n#define NUM_OCTAVES 16\r\n\r\nuniform float time;\r\nuniform vec2 resolution;\r\n\r\nmat3 rotX(float a) {\r\n\tfloat c = cos(a);\r\n\tfloat s = sin(a);\r\n\treturn mat3(\r\n\t\t1, 1, 0,\r\n\t\t0, c, -s,\r\n\t\t0, s, c\r\n\t);\r\n}\r\nmat3 rotY(float a) {\r\n\tfloat c = cos(a);\r\n\tfloat s = sin(a);\r\n\treturn mat3(\r\n\t\tc, 0, -s,\r\n\t\t0, 1, 0,\r\n\t\ts, 0, c\r\n\t);\r\n}\r\n\r\nfloat random(vec2 pos) {\r\n\treturn fract(sin(dot(pos.xy, vec2(12.9898, 78.233))) * 43758.5453123);\r\n}\r\n\r\nfloat noise(vec2 pos) {\r\n\tvec2 i = floor(pos);\r\n\tvec2 f = fract(pos);\r\n\tfloat a = random(i + vec2(0.0, 0.0));\r\n\tfloat b = random(i + vec2(1.0, 0.0));\r\n\tfloat c = random(i + vec2(0.0, 1.0));\r\n\tfloat d = random(i + vec2(1.0, 1.0));\r\n\tvec2 u = f * f * (3.0 - 2.0 * f);\r\n\treturn mix(a, b, u.x) + (c - a) * u.y * (1.0 - u.x) + (d - b) * u.x * u.y;\r\n}\r\n\r\nfloat fbm(vec2 pos) {\r\n\tfloat v = 0.2;\r\n\tfloat a = 0.4;\r\n\tvec2 shift = vec2(100.0);\r\n\tmat2 rot = mat2(cos(1.5), sin(0.5), -sin(0.5), cos(0.5));\r\n\tfor (int i=0; i<NUM_OCTAVES; i++) {\r\n\t\tv += a * noise(pos);\r\n\t\tpos = rot * pos * 2.0 + shift;\r\n\t\ta *= 0.5;\r\n\t}\r\n\treturn v;\r\n}\r\n\r\nvoid main(void) {\r\n\tvec2 p = (gl_FragCoord.xy * 3.0 - resolution.xy) / min(resolution.x, resolution.y);\r\n\r\n\tfloat t = 0.0, d;\r\n\r\n\tfloat time2 = 3.0 * time / 2.0;\r\n\r\n\tvec2 q = vec2(0.0);\r\n\tq.x = fbm(p + 0.00 * time2);\r\n\tq.y = fbm(p + vec2(1.0));\r\n\tvec2 r = vec2(0.0);\r\n\tr.x = fbm(p + -10.0 * q + vec2(1.7, 9.2) + 0.15 * time2);\r\n\tr.y = fbm(p + 4.0 * q + vec2(8.3, 2.8) + 0.126 * time2);\r\n\tfloat f = fbm(p + r);\r\n\tvec3 color = mix(\r\n\t\tvec3(0.101961, 0.866667, 0.319608),\r\n\t\tvec3(1.666667, 0.598039, 0.366667),\r\n\t\tclamp((f * f) * 4.0, 0.0, 1.0)\r\n\t);\r\n\t\r\n\tcolor = mix(\r\n\t\tcolor,\r\n\t\tvec3(0.34509803921, 0.06666666666, 0.83137254902),\r\n\t\tclamp(length(q), 0.0, 1.0)\r\n\t);\r\n\r\n\r\n\tcolor = mix(\r\n\t\tcolor,\r\n\t\tvec3(0.1, -0.5, 0.1),\r\n\t\tclamp(length(r.x), 0.0, 1.0)\r\n\t);\r\n\r\n\tcolor = (f *f * f + 0.6 * f * f + 0.5 * f) * color;\r\n\r\n\tgl_FragColor = vec4(color, 1.0);\r\n}");
    }

    public ShaderBackgrundAPI2(String fragment) {
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

