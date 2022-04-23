// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagList;
import java.util.List;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.RenderHelper;
import java.text.DecimalFormat;
import net.minecraft.util.StringUtils;
import bozoware.base.util.player.PlayerUtils;
import net.minecraft.entity.EntityLivingBase;
import java.util.Iterator;
import bozoware.visual.font.MinecraftFontRenderer;
import bozoware.base.util.visual.RenderUtil;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;
import bozoware.impl.module.combat.AntiBot;
import net.minecraft.entity.player.EntityPlayer;
import bozoware.base.BozoWare;
import bozoware.impl.event.visual.Render2DEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.RenderNametagEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Nametags", moduleCategory = ModuleCategory.VISUAL)
public class Nametags extends Module
{
    @EventListener
    EventConsumer<RenderNametagEvent> onRenderNameTagEvent;
    @EventListener
    EventConsumer<Render2DEvent> onRender3D;
    
    public Nametags() {
        this.onRenderNameTagEvent = (e -> e.setCancelled(true));
        final MinecraftFontRenderer fr;
        final Iterator<EntityPlayer> iterator;
        EntityPlayer entity;
        double n;
        double x;
        double n2;
        double y;
        double n3;
        double z;
        float distance;
        float scaleConst_1;
        float scaleConst_2;
        double maxDist;
        float scaleFactor;
        float scaleConst_3;
        float scaleBet;
        float scaleConst_4;
        String colorCode;
        int colorrectCode;
        String thing;
        float namewidth;
        double movingArmor;
        double movingArmor2;
        double movingArmor3;
        double movingArmor4;
        int index;
        this.onRender3D = (event -> {
            fr = BozoWare.getInstance().getFontManager().McFontRenderer;
            Nametags.mc.theWorld.playerEntities.iterator();
            while (iterator.hasNext()) {
                entity = iterator.next();
                if (!entity.isInvisible()) {
                    if (entity == Nametags.mc.thePlayer) {
                        continue;
                    }
                    else if (!AntiBot.botList.contains(entity.getEntityId())) {
                        GL11.glPushMatrix();
                        n = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * Nametags.mc.timer.renderPartialTicks;
                        Nametags.mc.getRenderManager();
                        x = n - RenderManager.renderPosX;
                        n2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * Nametags.mc.timer.renderPartialTicks;
                        Nametags.mc.getRenderManager();
                        y = n2 - RenderManager.renderPosY;
                        n3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * Nametags.mc.timer.renderPartialTicks;
                        Nametags.mc.getRenderManager();
                        z = n3 - RenderManager.renderPosZ;
                        GL11.glTranslated(x, y + entity.getEyeHeight() + 1.7, z);
                        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                        if (Nametags.mc.gameSettings.thirdPersonView == 2) {
                            GlStateManager.rotate(-Nametags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
                            GlStateManager.rotate(-Nametags.mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
                        }
                        else {
                            GlStateManager.rotate(-Nametags.mc.thePlayer.rotationYaw, 0.0f, 1.0f, 0.0f);
                            GlStateManager.rotate(Nametags.mc.thePlayer.rotationPitch, 1.0f, 0.0f, 0.0f);
                        }
                        distance = Nametags.mc.thePlayer.getDistanceToEntity(entity);
                        scaleConst_1 = 0.02672f;
                        scaleConst_2 = 0.1f;
                        maxDist = 7.0;
                        scaleFactor = (float)((distance <= maxDist) ? (maxDist * scaleConst_2) : (distance * scaleConst_2));
                        scaleConst_3 = scaleConst_1 * scaleFactor;
                        scaleBet = 0.03f;
                        scaleConst_4 = Math.min(scaleBet, scaleConst_3);
                        GL11.glScalef(-scaleConst_4, -scaleConst_4, 0.2f);
                        GlStateManager.disableLighting();
                        GlStateManager.depthMask(false);
                        GL11.glDisable(2929);
                        colorCode = ((entity.getHealth() > 15.0f) ? "§a" : ((entity.getHealth() > 10.0f) ? "§e" : ((entity.getHealth() > 7.0f) ? "§6" : "§c")));
                        colorrectCode = ((entity.getHealth() > 15.0f) ? -11667621 : ((entity.getHealth() > 10.0f) ? -919731 : ((entity.getHealth() > 7.0f) ? -555699 : -568755)));
                        thing = entity.getName() + " " + colorCode + (int)entity.getHealth();
                        namewidth = (float)fr.getStringWidth(thing);
                        RenderUtil.drawBoxOutline(-20.0f - namewidth / 2.0f, 43.0, namewidth / 2.0f + 15.0f, 26.0, 30, 1.0);
                        fr.drawCenteredStringWithShadow(entity.getName(), -20.0, 30.0, -1);
                        fr.drawCenteredStringWithShadow(colorCode + (int)entity.getHealth(), namewidth / 2.0f, 30.0, -1);
                        GlStateManager.disableBlend();
                        GlStateManager.depthMask(true);
                        GL11.glEnable(2929);
                        movingArmor = 1.2;
                        if (namewidth <= 65.0f) {
                            movingArmor2 = 2.0;
                        }
                        if (namewidth <= 85.0f) {
                            movingArmor3 = 1.2;
                        }
                        if (namewidth <= 100.0f) {
                            movingArmor4 = 1.1;
                        }
                        for (index = 0; index < 5; ++index) {
                            if (entity.getEquipmentInSlot(index) == null) {}
                        }
                        GL11.glPopMatrix();
                    }
                }
            }
        });
    }
    
    private boolean isValidEntity(final Entity entity) {
        return entity != null && entity != Nametags.mc.thePlayer;
    }
    
    public int getNametagColor(final EntityLivingBase entity) {
        int color = -1;
        if (entity instanceof EntityPlayer && PlayerUtils.isOnSameTeam(entity)) {
            color = -11684865;
        }
        else if (entity.isInvisibleToPlayer(Nametags.mc.thePlayer)) {
            color = -6656;
        }
        else if (entity.isSneaking()) {
            color = -65536;
        }
        return color;
    }
    
    public void drawNametags(final EntityLivingBase entity, final double x, final double y, final double z) {
        String entityName = entity.getDisplayName().getFormattedText();
        if (this.getNametagColor(entity) != -1) {
            entityName = StringUtils.stripControlCodes(entityName);
        }
        if (entity.isDead) {
            return;
        }
        final double health = entity.getHealth() / 2.0f;
        final double maxHealth = entity.getMaxHealth() / 2.0f;
        final double percentage = 100.0 * (health / maxHealth);
        final String healthColor = "2";
        final DecimalFormat df = new DecimalFormat();
        final String healthDisplay = df.format(Math.floor((health + 0.25) / 0.5) * 0.5);
        final String maxHealthDisplay = df.format(Math.floor((entity.getMaxHealth() + 0.25) / 0.5) * 0.5);
        entityName = String.format("%s §%s%s", entityName, healthColor, healthDisplay);
        final float distance = Nametags.mc.thePlayer.getDistanceToEntity(entity);
        final float var13 = ((distance / 5.0f <= 2.0f) ? 2.0f : (distance / 5.0f)) * 0.95f;
        final float var14 = 0.016666668f * var13;
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(x + 0.0, y + entity.height + 0.5, z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        if (Nametags.mc.gameSettings.thirdPersonView == 2) {
            GlStateManager.rotate(-Nametags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(Nametags.mc.getRenderManager().playerViewX, -1.0f, 0.0f, 0.0f);
        }
        else {
            GlStateManager.rotate(-Nametags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(Nametags.mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
        }
        GlStateManager.scale(-var14, -var14, var14);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        int var15 = 0;
        if (entity.isSneaking()) {
            var15 += 4;
        }
        var15 -= (int)(distance / 5.0f);
        if (var15 < -8) {
            var15 = -8;
        }
        GlStateManager.enableAlpha();
        worldRenderer.startDrawingQuads();
        final int var16 = Nametags.mc.fontRendererObj.getStringWidth(entityName) / 2;
        worldRenderer.color(0.0f, 0.0f, 0.0f, 0.25f);
        worldRenderer.pos(-var16 - 2, -2 + var15, 0.0);
        worldRenderer.pos(-var16 - 2, 9 + var15, 0.0);
        worldRenderer.pos(var16 + 2, 9 + var15, 0.0);
        worldRenderer.pos(var16 + 2, -2 + var15, 0.0);
        tessellator.draw();
        GlStateManager.disableAlpha();
        BozoWare.getInstance().getFontManager().mediumFontRenderer.drawNoBSString(entityName, -var16, var15, this.getNametagColor(entity), true);
        if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entity;
            final List<ItemStack> items = new ArrayList<ItemStack>();
            if (player.getCurrentEquippedItem() != null) {
                items.add(player.getCurrentEquippedItem());
            }
            for (int index = 3; index >= 0; --index) {
                final ItemStack stack = player.inventory.armorInventory[index];
                if (stack != null) {
                    items.add(stack);
                }
            }
            final int offset = var16 - (items.size() - 1) * 9 - 9;
            int xPos = 0;
            for (final ItemStack stack2 : items) {
                GlStateManager.pushMatrix();
                RenderHelper.enableStandardItemLighting();
                Nametags.mc.getRenderItem().zLevel = -100.0f;
                Nametags.mc.getRenderItem().renderItemAboveHead(stack2, -var16 + offset + xPos, var15 - 20);
                Nametags.mc.getRenderItem().renderItemOverlays(Nametags.mc.fontRendererObj, stack2, -var16 + offset + xPos, var15 - 20);
                Nametags.mc.getRenderItem().zLevel = 0.0f;
                RenderHelper.disableStandardItemLighting();
                GlStateManager.enableAlpha();
                GlStateManager.disableBlend();
                GlStateManager.disableLighting();
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);
                GlStateManager.disableDepth();
                GlStateManager.scale(0.5f, 0.5f, 0.5f);
                if (stack2.getItem() == Items.golden_apple && stack2.hasEffect()) {
                    BozoWare.getInstance().getFontManager().mediumFontRenderer.drawStringWithShadow("god", (-var16 + offset + xPos) * 2, (var15 - 20) * 2, -65536);
                }
                else {
                    final NBTTagList enchants = stack2.getEnchantmentTagList();
                    if (enchants != null) {
                        int encY = 0;
                        final Enchantment[] important = { Enchantment.protection, Enchantment.unbreaking, Enchantment.sharpness, Enchantment.fireAspect, Enchantment.efficiency, Enchantment.featherFalling, Enchantment.power, Enchantment.flame, Enchantment.punch, Enchantment.fortune, Enchantment.infinity, Enchantment.thorns };
                        if (enchants.tagCount() >= 6) {
                            BozoWare.getInstance().getFontManager().mediumFontRenderer.drawStringWithShadow("god", (-var16 + offset + xPos) * 2, (var15 - 20) * 2, -65536);
                        }
                        else {
                            for (int index2 = 0; index2 < enchants.tagCount(); ++index2) {
                                final short id = enchants.getCompoundTagAt(index2).getShort("id");
                                final short level = enchants.getCompoundTagAt(index2).getShort("lvl");
                                final Enchantment enc = Enchantment.getEnchantmentById(id);
                                if (enc != null) {
                                    for (final Enchantment importantEnchantment : important) {
                                        if (enc == importantEnchantment) {
                                            String encName = enc.getTranslatedName(level).substring(0, 1).toLowerCase();
                                            if (level > 99) {
                                                encName += "99+";
                                            }
                                            else {
                                                encName += level;
                                            }
                                            BozoWare.getInstance().getFontManager().mediumFontRenderer.drawStringWithShadow(encName, (-var16 + offset + xPos) * 2, (var15 - 20 + encY) * 2, -5592406);
                                            encY += 5;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
                xPos += 18;
            }
        }
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }
    
    public static void renderItem(final ItemStack stack, final int x, final int y) {
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
}
