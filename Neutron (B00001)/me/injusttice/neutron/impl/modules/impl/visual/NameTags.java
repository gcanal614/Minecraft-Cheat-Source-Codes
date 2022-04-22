package me.injusttice.neutron.impl.modules.impl.visual;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventNamePlayer;
import me.injusttice.neutron.api.events.impl.EventRender3D;
import me.injusttice.neutron.api.events.impl.EventUpdate;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.utils.player.TagUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class NameTags extends Module {

    public NameTags(){
        super("NameTags", 0, Category.VISUAL);
    }

    @EventTarget
    public void on3D(EventRender3D e){
        for (Object o : this.mc.theWorld.playerEntities) {
            EntityPlayer player = (EntityPlayer) o;
            if (player.isInvisible())
                continue;
            double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * e.getPartialTicks()
                    - RenderManager.renderPosX;
            double y2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * e.getPartialTicks()
                    - RenderManager.renderPosY;
            double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * e.getPartialTicks()
                    - RenderManager.renderPosZ;
            TagUtils.renderNametag(player, x, y2, z);
        }
    }

    public static void renderItem(ItemStack stack, int x, int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().zLevel = -100.0f;
        GlStateManager.scale(1.0f, 1.0f, 0.01f);
        GlStateManager.enableDepth();
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(stack, x, y + 8);
        Minecraft.getMinecraft().getRenderItem().zLevel = 0.0f;
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }

    @EventTarget
    public void onUpdate(EventNamePlayer e) {
        e.setCancelled(true);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        this.setDisplayName("Name Tags");
    }
}
