/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.render;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.render.Render2DEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.utils.render.RenderingUtils;
import java.util.ArrayList;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

@ModuleInfo(label="ArmorHUD", category=ModuleCategory.RENDER)
public final class ArmorHUD
extends Module {
    @Listener
    public void onEvent(Render2DEvent event) {
        GL11.glPushMatrix();
        ArrayList<ItemStack> stuff = new ArrayList<ItemStack>();
        boolean onWater = ArmorHUD.mc.thePlayer.isEntityAlive() && ArmorHUD.mc.thePlayer.isInsideOfMaterial(Material.water);
        int split = -3;
        for (int index = 3; index >= 0; --index) {
            ItemStack armer = ArmorHUD.mc.thePlayer.inventory.armorInventory[index];
            if (armer == null) continue;
            stuff.add(armer);
        }
        if (ArmorHUD.mc.thePlayer.getCurrentEquippedItem() != null) {
            stuff.add(ArmorHUD.mc.thePlayer.getCurrentEquippedItem());
        }
        for (ItemStack everything : stuff) {
            if (ArmorHUD.mc.theWorld != null) {
                RenderHelper.enableGUIStandardItemLighting();
                split += 16;
            }
            GlStateManager.pushMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.clear(256);
            GlStateManager.enableBlend();
            ArmorHUD.mc.getRenderItem().zLevel = -150.0f;
            mc.getRenderItem().renderItemIntoGUI(everything, split + event.getResolution().getWidth() / 2 - 4, event.getResolution().getHeight() - (onWater ? 65 : 55) + (ArmorHUD.mc.thePlayer.capabilities.isCreativeMode ? 14 : 0));
            mc.getRenderItem().renderItemOverlays(ArmorHUD.mc.fontRendererObj, everything, split + event.getResolution().getWidth() / 2 - 4, event.getResolution().getHeight() - (onWater ? 65 : 55) + (ArmorHUD.mc.thePlayer.capabilities.isCreativeMode ? 14 : 0));
            RenderingUtils.renderEnchantText(everything, split + event.getResolution().getWidth() / 2 - 3, event.getResolution().getHeight() * 2 - (onWater ? 145 : 135) + (ArmorHUD.mc.thePlayer.capabilities.isCreativeMode ? 14 : 0));
            ArmorHUD.mc.getRenderItem().zLevel = 0.0f;
            GlStateManager.disableBlend();
            GlStateManager.scale(0.5, 0.5, 0.5);
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.enableDepth();
            GlStateManager.scale(2.0f, 2.0f, 2.0f);
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();
            everything.getEnchantmentTagList();
        }
        GL11.glPopMatrix();
    }
}

