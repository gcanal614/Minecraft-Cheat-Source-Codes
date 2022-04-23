/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer;

import java.util.Collection;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public abstract class InventoryEffectRenderer
extends GuiContainer {
    private boolean hasActivePotionEffects;

    public InventoryEffectRenderer(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.updateActivePotionEffects();
    }

    protected void updateActivePotionEffects() {
        if (!this.mc.thePlayer.getActivePotionEffects().isEmpty()) {
            this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
            this.hasActivePotionEffects = true;
        } else {
            this.guiLeft = (this.width - this.xSize) / 2;
            this.hasActivePotionEffects = false;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (this.hasActivePotionEffects) {
            this.drawActivePotionEffects();
        }
    }

    private void drawActivePotionEffects() {
        int i = this.guiLeft - 124;
        int j = this.guiTop;
        Collection<PotionEffect> collection = this.mc.thePlayer.getActivePotionEffects();
        if (!collection.isEmpty()) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableLighting();
            int l = 33;
            if (collection.size() > 5) {
                l = 132 / (collection.size() - 1);
            }
            for (PotionEffect potioneffect : this.mc.thePlayer.getActivePotionEffects()) {
                Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                if (potion == null) {
                    return;
                }
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                this.mc.getTextureManager().bindTexture(inventoryBackground);
                this.drawTexturedModalRect(i, j, 0, 166, 140, 32);
                if (potion.hasStatusIcon()) {
                    int i1 = potion.getStatusIconIndex();
                    this.drawTexturedModalRect(i + 6, j + 7, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                }
                String s1 = I18n.format(potion.getName(), new Object[0]);
                switch (potioneffect.getAmplifier()) {
                    case 1: {
                        s1 = s1 + " " + I18n.format("enchantment.level.2", new Object[0]);
                        break;
                    }
                    case 2: {
                        s1 = s1 + " " + I18n.format("enchantment.level.3", new Object[0]);
                        break;
                    }
                    case 3: {
                        s1 = s1 + " " + I18n.format("enchantment.level.4", new Object[0]);
                        break;
                    }
                    case 4: {
                        s1 = s1 + " " + I18n.format("enchantment.level.5", new Object[0]);
                        break;
                    }
                    case 5: {
                        s1 = s1 + " " + I18n.format("enchantment.level.6", new Object[0]);
                        break;
                    }
                    case 6: {
                        s1 = s1 + " " + I18n.format("enchantment.level.7", new Object[0]);
                        break;
                    }
                    case 7: {
                        s1 = s1 + " " + I18n.format("enchantment.level.8", new Object[0]);
                        break;
                    }
                    case 8: {
                        s1 = s1 + " " + I18n.format("enchantment.level.9", new Object[0]);
                        break;
                    }
                    case 9: {
                        s1 = s1 + " " + I18n.format("enchantment.level.10", new Object[0]);
                    }
                }
                this.fontRendererObj.drawStringWithShadow(s1, i + 10 + 18, j + 6, 0xFFFFFF);
                String s = Potion.getDurationString(potioneffect);
                this.fontRendererObj.drawStringWithShadow(s, i + 10 + 18, j + 6 + 10, 0x7F7F7F);
                j += l;
            }
        }
    }
}

