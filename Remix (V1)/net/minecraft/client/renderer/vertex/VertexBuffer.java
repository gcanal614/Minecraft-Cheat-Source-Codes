package net.minecraft.client.renderer.vertex;

import net.minecraft.client.renderer.*;
import java.nio.*;
import org.lwjgl.opengl.*;

public class VertexBuffer
{
    private final VertexFormat field_177363_b;
    private int field_177365_a;
    private int field_177364_c;
    
    public VertexBuffer(final VertexFormat p_i46098_1_) {
        this.field_177363_b = p_i46098_1_;
        this.field_177365_a = OpenGlHelper.func_176073_e();
    }
    
    public void func_177359_a() {
        OpenGlHelper.func_176072_g(OpenGlHelper.field_176089_P, this.field_177365_a);
    }
    
    public void func_177360_a(final ByteBuffer p_177360_1_, final int p_177360_2_) {
        this.func_177359_a();
        OpenGlHelper.func_176071_a(OpenGlHelper.field_176089_P, p_177360_1_, 35044);
        this.func_177361_b();
        this.field_177364_c = p_177360_2_ / this.field_177363_b.func_177338_f();
    }
    
    public void func_177358_a(final int p_177358_1_) {
        GL11.glDrawArrays(p_177358_1_, 0, this.field_177364_c);
    }
    
    public void func_177361_b() {
        OpenGlHelper.func_176072_g(OpenGlHelper.field_176089_P, 0);
    }
    
    public void func_177362_c() {
        if (this.field_177365_a >= 0) {
            OpenGlHelper.func_176074_g(this.field_177365_a);
            this.field_177365_a = -1;
        }
    }
}
