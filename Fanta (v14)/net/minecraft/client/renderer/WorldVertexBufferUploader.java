/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  optifine.Config
 *  optifine.Reflector
 *  optifine.ReflectorMethod
 *  org.lwjgl.opengl.GL11
 *  shadersmod.client.SVertexBuilder
 */
package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import optifine.Config;
import optifine.Reflector;
import optifine.ReflectorMethod;
import org.lwjgl.opengl.GL11;
import shadersmod.client.SVertexBuilder;

public class WorldVertexBufferUploader {
    private static final String __OBFID = "CL_00002567";

    public void func_181679_a(WorldRenderer p_181679_1_) {
        if (p_181679_1_.getVertexCount() > 0) {
            VertexFormat vertexformat = p_181679_1_.getVertexFormat();
            int i = vertexformat.getNextOffset();
            ByteBuffer bytebuffer = p_181679_1_.getByteBuffer();
            List<VertexFormatElement> list = vertexformat.getElements();
            boolean flag = Reflector.ForgeVertexFormatElementEnumUseage_preDraw.exists();
            boolean flag1 = Reflector.ForgeVertexFormatElementEnumUseage_postDraw.exists();
            int j = 0;
            while (j < list.size()) {
                VertexFormatElement vertexformatelement = list.get(j);
                VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();
                if (flag) {
                    Reflector.callVoid((Object)((Object)vertexformatelement$enumusage), (ReflectorMethod)Reflector.ForgeVertexFormatElementEnumUseage_preDraw, (Object[])new Object[]{vertexformat, j, i, bytebuffer});
                } else {
                    int l = vertexformatelement.getType().getGlConstant();
                    int k = vertexformatelement.getIndex();
                    bytebuffer.position(vertexformat.func_181720_d(j));
                    switch (vertexformatelement$enumusage) {
                        case POSITION: {
                            GL11.glVertexPointer((int)vertexformatelement.getElementCount(), (int)l, (int)i, (ByteBuffer)bytebuffer);
                            GL11.glEnableClientState((int)32884);
                            break;
                        }
                        case UV: {
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + k);
                            GL11.glTexCoordPointer((int)vertexformatelement.getElementCount(), (int)l, (int)i, (ByteBuffer)bytebuffer);
                            GL11.glEnableClientState((int)32888);
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                            break;
                        }
                        case COLOR: {
                            GL11.glColorPointer((int)vertexformatelement.getElementCount(), (int)l, (int)i, (ByteBuffer)bytebuffer);
                            GL11.glEnableClientState((int)32886);
                            break;
                        }
                        case NORMAL: {
                            GL11.glNormalPointer((int)l, (int)i, (ByteBuffer)bytebuffer);
                            GL11.glEnableClientState((int)32885);
                        }
                    }
                }
                ++j;
            }
            if (p_181679_1_.isMultiTexture()) {
                p_181679_1_.drawMultiTexture();
            } else if (Config.isShaders()) {
                SVertexBuilder.drawArrays((int)p_181679_1_.getDrawMode(), (int)0, (int)p_181679_1_.getVertexCount(), (WorldRenderer)p_181679_1_);
            } else {
                GL11.glDrawArrays((int)p_181679_1_.getDrawMode(), (int)0, (int)p_181679_1_.getVertexCount());
            }
            int i1 = 0;
            int k1 = list.size();
            while (i1 < k1) {
                VertexFormatElement vertexformatelement1 = list.get(i1);
                VertexFormatElement.EnumUsage vertexformatelement$enumusage1 = vertexformatelement1.getUsage();
                if (flag1) {
                    Reflector.callVoid((Object)((Object)vertexformatelement$enumusage1), (ReflectorMethod)Reflector.ForgeVertexFormatElementEnumUseage_postDraw, (Object[])new Object[]{vertexformat, i1, i, bytebuffer});
                } else {
                    int j1 = vertexformatelement1.getIndex();
                    switch (vertexformatelement$enumusage1) {
                        case POSITION: {
                            GL11.glDisableClientState((int)32884);
                            break;
                        }
                        case UV: {
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + j1);
                            GL11.glDisableClientState((int)32888);
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                            break;
                        }
                        case COLOR: {
                            GL11.glDisableClientState((int)32886);
                            GlStateManager.resetColor();
                            break;
                        }
                        case NORMAL: {
                            GL11.glDisableClientState((int)32885);
                        }
                    }
                }
                ++i1;
            }
        }
        p_181679_1_.reset();
    }
}

