/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.module.impl.visual;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventRender3D;
import de.fanta.module.Module;
import de.fanta.utils.UnicodeFontRenderer;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class NameTags
extends Module {
    final Color back = new Color(28, 25, 24, 255);
    final Color orange = new Color(255, 99, 0, 255);
    private String name = "";

    public NameTags() {
        super("NameTags", 0, Module.Type.Visual, Color.red);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender3D && event.isPre()) {
            UnicodeFontRenderer font = Client.INSTANCE.fontManager.arial;
            if (NameTags.mc.thePlayer != null && NameTags.mc.theWorld != null) {
                for (EntityPlayer e : NameTags.mc.theWorld.playerEntities) {
                    if (!(NameTags.mc.thePlayer.getDistanceToEntity(e) < 250.0f) || e == NameTags.mc.thePlayer) continue;
                    String health = String.valueOf(Math.round(e.getHealth()));
                    this.name = e.getDisplayName().getFormattedText();
                    this.name = this.name.replaceAll("\u00a7r", "");
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.disableTexture2D();
                    float pT = NameTags.mc.timer.renderPartialTicks;
                    double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)pT - RenderManager.renderPosX;
                    double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)pT - RenderManager.renderPosY;
                    double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)pT - RenderManager.renderPosZ;
                    float d = NameTags.mc.thePlayer.getDistanceToEntity(e);
                    float s = Math.min(Math.max(1.21f * (d * 0.1f), 1.25f), 6.0f) * 2.0f / 100.0f;
                    GlStateManager.translate((float)x, (float)y + e.height + 1.8f - e.height / 2.0f, (float)z);
                    GlStateManager.rotate(-NameTags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(NameTags.mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
                    GlStateManager.scale(-s, -s, s);
                    float string_width = Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(this.name) / 2 + 2;
                    GlStateManager.enableTexture2D();
                    Gui.drawRect(-Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(this.name) / 2 - 3, -2.0f, (int)string_width + 8, 13.0f, new Color(30, 30, 30, 255).getRGB());
                    Gui.drawRect(-Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(this.name) / 2 - 3, -1.5f, (int)string_width + 8, -2.0f, Color.green.getRGB());
                    GlStateManager.resetColor();
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    Client.INSTANCE.unicodeBasicFontRenderer.drawString(this.name, (int)(-string_width), 0.5f, Color.white.getRGB());
                    GlStateManager.disableBlend();
                    GlStateManager.enableDepth();
                    GlStateManager.resetColor();
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GlStateManager.popMatrix();
                }
            }
        }
    }

    public static String removeColorCode(String text) {
        String finalText = text;
        if (finalText.contains("\u00a7")) {
            int i = 0;
            while (i < finalText.length()) {
                if (Character.toString(finalText.charAt(i)).equals("\u00a7")) {
                    try {
                        String part1 = finalText.substring(0, i);
                        String part2 = finalText.substring(Math.min(i + 2, finalText.length()));
                        finalText = String.valueOf(part1) + part2;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                ++i;
            }
        }
        return finalText;
    }

    public void renderItem(ItemStack item, int xPos, int yPos, int zPos) {
        GL11.glPushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableBlend();
        GL11.glBlendFunc((int)770, (int)771);
        GlStateManager.disableLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        IBakedModel ibakedmodel = mc.getRenderItem().getItemModelMesher().getItemModel(item);
        NameTags.mc.getRenderItem().textureManager.bindTexture(TextureMap.locationBlocksTexture);
        GlStateManager.scale(16.0f, 16.0f, 0.0f);
        GL11.glTranslated((double)(((float)xPos - 7.85f) / 16.0f), (double)((float)(-5 + yPos) / 16.0f), (double)((float)zPos / 16.0f));
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        if (ibakedmodel.isBuiltInRenderer()) {
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5f, -0.5f, -0.5f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            TileEntityItemStackRenderer.instance.renderByItem(item);
        } else {
            mc.getRenderItem().renderModel(ibakedmodel, -1, item);
        }
        GlStateManager.enableAlpha();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
    }

    public String getHealthColor(int hp) {
        if (hp > 15) {
            return "a";
        }
        if (hp > 10) {
            return "e";
        }
        if (hp > 5) {
            return "6";
        }
        if (hp < 2) {
            return "4";
        }
        return "c";
    }
}

