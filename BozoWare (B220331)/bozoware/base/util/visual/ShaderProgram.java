// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.visual;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.io.InputStream;
import org.lwjgl.opengl.GL20;

public class ShaderProgram
{
    private final String vertexName;
    private final String fragmentName;
    private final int vertexShaderID;
    private final int fragmentShaderID;
    private final int programID;
    private boolean initiated;
    
    public ShaderProgram(final String vertexName, final String fragmentName) {
        this.vertexName = vertexName;
        this.fragmentName = fragmentName;
        final int program = GL20.glCreateProgram();
        final String vertexSource = readShader(vertexName);
        GL20.glShaderSource(this.vertexShaderID = GL20.glCreateShader(35633), (CharSequence)vertexSource);
        GL20.glCompileShader(this.vertexShaderID);
        if (GL20.glGetShaderi(this.vertexShaderID, 35713) == 0) {
            System.err.println(GL20.glGetShaderInfoLog(this.vertexShaderID, 4096));
            throw new IllegalStateException(String.format("Vertex Shader (%s) failed to compile!", 35633));
        }
        final String fragmentSource = readShader(fragmentName);
        GL20.glShaderSource(this.fragmentShaderID = GL20.glCreateShader(35632), (CharSequence)fragmentSource);
        GL20.glCompileShader(this.fragmentShaderID);
        if (GL20.glGetShaderi(this.fragmentShaderID, 35713) == 0) {
            System.err.println(GL20.glGetShaderInfoLog(this.fragmentShaderID, 4096));
            throw new IllegalStateException(String.format("Fragment Shader failed to compile!: " + fragmentName, 35632));
        }
        GL20.glAttachShader(program, this.vertexShaderID);
        GL20.glAttachShader(program, this.fragmentShaderID);
        GL20.glLinkProgram(program);
        this.programID = program;
    }
    
    public ShaderProgram(final String fragmentName) {
        this("bozoware/base/util/visual/fragment/vertex.vert", fragmentName);
    }
    
    private static String readShader(final String fileName) {
        final StringBuilder stringBuilder = new StringBuilder();
        try {
            final InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream(String.format("bozoware/base/util/visual/fragment", fileName))));
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            bufferedReader.close();
            inputStreamReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    
    public void renderCanvas() {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        final float width = (float)sr.getScaledWidth_double();
        final float height = (float)sr.getScaledHeight_double();
        this.renderCanvas(0.0f, 0.0f, width, height);
    }
    
    public void renderCanvas(final float x, final float y, final float width, final float height) {
        if (Minecraft.getMinecraft().gameSettings.ofFastRender) {
            return;
        }
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2f(x, height);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(width, height);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f(width, y);
        GL11.glEnd();
    }
    
    public void deleteShaderProgram() {
        GL20.glDeleteShader(this.vertexShaderID);
        GL20.glDeleteShader(this.fragmentShaderID);
        GL20.glDeleteProgram(this.programID);
    }
    
    public void init() {
        GL20.glUseProgram(this.programID);
    }
    
    public void uninit() {
        GL20.glUseProgram(0);
    }
    
    public int getUniform(final String name) {
        return GL20.glGetUniformLocation(this.programID, (CharSequence)name);
    }
    
    public void setUniformf(final String name, final float... args) {
        final int loc = GL20.glGetUniformLocation(this.programID, (CharSequence)name);
        if (args.length > 1) {
            if (args.length > 2) {
                if (args.length > 3) {
                    GL20.glUniform4f(loc, args[0], args[1], args[2], args[3]);
                }
                else {
                    GL20.glUniform3f(loc, args[0], args[1], args[2]);
                }
            }
            else {
                GL20.glUniform2f(loc, args[0], args[1]);
            }
        }
        else {
            GL20.glUniform1f(loc, args[0]);
        }
    }
    
    public void setUniformi(final String name, final int... args) {
        final int loc = GL20.glGetUniformLocation(this.programID, (CharSequence)name);
        if (args.length > 1) {
            GL20.glUniform2i(loc, args[0], args[1]);
        }
        else {
            GL20.glUniform1i(loc, args[0]);
        }
    }
    
    public int getProgramID() {
        return this.programID;
    }
    
    @Override
    public String toString() {
        return "ShaderProgram{programID=" + this.programID + ", vertexName='" + this.vertexName + '\'' + ", fragmentName='" + this.fragmentName + '\'' + '}';
    }
}
