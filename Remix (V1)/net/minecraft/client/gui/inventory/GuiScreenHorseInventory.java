package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class GuiScreenHorseInventory extends GuiContainer
{
    private static final ResourceLocation horseGuiTextures;
    private IInventory field_147030_v;
    private IInventory field_147029_w;
    private EntityHorse field_147034_x;
    private float field_147033_y;
    private float field_147032_z;
    
    public GuiScreenHorseInventory(final IInventory p_i1093_1_, final IInventory p_i1093_2_, final EntityHorse p_i1093_3_) {
        super(new ContainerHorseInventory(p_i1093_1_, p_i1093_2_, p_i1093_3_, Minecraft.getMinecraft().thePlayer));
        this.field_147030_v = p_i1093_1_;
        this.field_147029_w = p_i1093_2_;
        this.field_147034_x = p_i1093_3_;
        this.allowUserInput = false;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        this.fontRendererObj.drawString(this.field_147029_w.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.field_147030_v.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiScreenHorseInventory.mc.getTextureManager().bindTexture(GuiScreenHorseInventory.horseGuiTextures);
        final int var4 = (GuiScreenHorseInventory.width - this.xSize) / 2;
        final int var5 = (GuiScreenHorseInventory.height - this.ySize) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
        if (this.field_147034_x.isChested()) {
            this.drawTexturedModalRect(var4 + 79, var5 + 17, 0, this.ySize, 90, 54);
        }
        if (this.field_147034_x.canWearArmor()) {
            this.drawTexturedModalRect(var4 + 7, var5 + 35, 0, this.ySize + 54, 18, 18);
        }
        GuiInventory.drawEntityOnScreen(var4 + 51, var5 + 60, 17, var4 + 51 - this.field_147033_y, var5 + 75 - 50 - this.field_147032_z, this.field_147034_x);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.field_147033_y = (float)mouseX;
        this.field_147032_z = (float)mouseY;
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    static {
        horseGuiTextures = new ResourceLocation("textures/gui/container/horse.png");
    }
}
