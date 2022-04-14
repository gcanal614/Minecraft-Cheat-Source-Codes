package net.minecraft.client.renderer;

import net.minecraft.util.*;
import net.minecraft.client.renderer.chunk.*;
import java.util.*;
import net.minecraft.client.renderer.vertex.*;
import optifine.*;
import shadersmod.client.*;
import org.lwjgl.opengl.*;

public class VboRenderList extends ChunkRenderContainer
{
    @Override
    public void func_178001_a(final EnumWorldBlockLayer p_178001_1_) {
        if (this.field_178007_b) {
            for (final RenderChunk var3 : this.field_178009_a) {
                final VertexBuffer var4 = var3.func_178565_b(p_178001_1_.ordinal());
                GlStateManager.pushMatrix();
                this.func_178003_a(var3);
                var3.func_178572_f();
                var4.func_177359_a();
                this.func_178010_a();
                var4.func_177358_a(7);
                GlStateManager.popMatrix();
            }
            OpenGlHelper.func_176072_g(OpenGlHelper.field_176089_P, 0);
            GlStateManager.func_179117_G();
            this.field_178009_a.clear();
        }
    }
    
    private void func_178010_a() {
        if (Config.isShaders()) {
            ShadersRender.setupArrayPointersVbo();
        }
        else {
            GL11.glVertexPointer(3, 5126, 28, 0L);
            GL11.glColorPointer(4, 5121, 28, 12L);
            GL11.glTexCoordPointer(2, 5126, 28, 16L);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glTexCoordPointer(2, 5122, 28, 24L);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
        }
    }
}
