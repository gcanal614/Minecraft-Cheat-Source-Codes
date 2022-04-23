// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.visual;

import org.lwjgl.opengl.GL20;
import java.util.HashMap;
import java.util.Map;

public class GLShader
{
    private int program;
    private final Map<String, Integer> uniformLocationMap;
    
    public GLShader(final String vertexSource, final String fragSource) {
        this.uniformLocationMap = new HashMap<String, Integer>();
        GL20.glAttachShader(this.program = GL20.glCreateProgram(), createShader(vertexSource, 35633));
        GL20.glAttachShader(this.program, createShader(fragSource, 35632));
        GL20.glLinkProgram(this.program);
        final int status = GL20.glGetProgrami(this.program, 35714);
        if (status == 0) {
            this.program = -1;
            return;
        }
        this.setupUniforms();
    }
    
    private static int createShader(final String source, final int type) {
        final int shader = GL20.glCreateShader(type);
        GL20.glShaderSource(shader, (CharSequence)source);
        GL20.glCompileShader(shader);
        final int status = GL20.glGetShaderi(shader, 35713);
        if (status == 0) {
            return -1;
        }
        return shader;
    }
    
    public void use() {
        GL20.glUseProgram(this.program);
        this.updateUniforms();
    }
    
    public int getProgram() {
        return this.program;
    }
    
    public void setupUniforms() {
    }
    
    public void updateUniforms() {
    }
    
    public void setupUniform(final String uniform) {
        this.uniformLocationMap.put(uniform, GL20.glGetUniformLocation(this.program, (CharSequence)uniform));
    }
    
    public int getUniformLocation(final String uniform) {
        return this.uniformLocationMap.get(uniform);
    }
}
