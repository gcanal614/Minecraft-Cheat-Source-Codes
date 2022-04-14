package net.minecraft.client.entity;

import net.minecraft.entity.player.*;
import net.minecraft.client.network.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import optifine.*;
import net.minecraft.entity.ai.attributes.*;

public abstract class AbstractClientPlayer extends EntityPlayer
{
    private NetworkPlayerInfo field_175157_a;
    private ResourceLocation locationOfCape;
    private String nameClear;
    
    public AbstractClientPlayer(final World worldIn, final GameProfile p_i45074_2_) {
        super(worldIn, p_i45074_2_);
        this.locationOfCape = null;
        this.nameClear = null;
        this.nameClear = p_i45074_2_.getName();
        if (this.nameClear != null && !this.nameClear.isEmpty()) {
            this.nameClear = StringUtils.stripControlCodes(this.nameClear);
        }
        CapeUtils.downloadCape(this);
        PlayerConfigurations.getPlayerConfiguration(this);
    }
    
    public static ThreadDownloadImageData getDownloadImageSkin(final ResourceLocation resourceLocationIn, final String username) {
        final TextureManager var2 = Minecraft.getMinecraft().getTextureManager();
        Object var3 = var2.getTexture(resourceLocationIn);
        if (var3 == null) {
            var3 = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.stripControlCodes(username)), DefaultPlayerSkin.func_177334_a(EntityPlayer.func_175147_b(username)), new ImageBufferDownload());
            var2.loadTexture(resourceLocationIn, (ITextureObject)var3);
        }
        return (ThreadDownloadImageData)var3;
    }
    
    public static ResourceLocation getLocationSkin(final String username) {
        return new ResourceLocation("skins/" + StringUtils.stripControlCodes(username));
    }
    
    @Override
    public boolean func_175149_v() {
        final NetworkPlayerInfo var1 = Minecraft.getMinecraft().getNetHandler().func_175102_a(this.getGameProfile().getId());
        return var1 != null && var1.getGameType() == WorldSettings.GameType.SPECTATOR;
    }
    
    public boolean hasCape() {
        return this.func_175155_b() != null;
    }
    
    protected NetworkPlayerInfo func_175155_b() {
        if (this.field_175157_a == null) {
            this.field_175157_a = Minecraft.getMinecraft().getNetHandler().func_175102_a(this.getUniqueID());
        }
        return this.field_175157_a;
    }
    
    public boolean hasSkin() {
        final NetworkPlayerInfo var1 = this.func_175155_b();
        return var1 != null && var1.func_178856_e();
    }
    
    public ResourceLocation getLocationSkin() {
        final NetworkPlayerInfo var1 = this.func_175155_b();
        return (var1 == null) ? DefaultPlayerSkin.func_177334_a(this.getUniqueID()) : var1.func_178837_g();
    }
    
    public ResourceLocation getLocationCape() {
        if (!Config.isShowCapes()) {
            return null;
        }
        if (this.locationOfCape != null) {
            return this.locationOfCape;
        }
        final NetworkPlayerInfo var1 = this.func_175155_b();
        return (var1 == null) ? null : var1.func_178861_h();
    }
    
    public String func_175154_l() {
        final NetworkPlayerInfo var1 = this.func_175155_b();
        return (var1 == null) ? DefaultPlayerSkin.func_177332_b(this.getUniqueID()) : var1.func_178851_f();
    }
    
    public float func_175156_o() {
        float var1 = 1.0f;
        if (this.capabilities.isFlying) {
            var1 *= 1.1f;
        }
        final IAttributeInstance var2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        var1 *= (float)((var2.getAttributeValue() / this.capabilities.getWalkSpeed() + 1.0) / 2.0);
        if (this.capabilities.getWalkSpeed() == 0.0f || Float.isNaN(var1) || Float.isInfinite(var1)) {
            var1 = 1.0f;
        }
        if (this.isUsingItem() && this.getItemInUse().getItem() == Items.bow) {
            final int var3 = this.getItemInUseDuration();
            float var4 = var3 / 20.0f;
            if (var4 > 1.0f) {
                var4 = 1.0f;
            }
            else {
                var4 *= var4;
            }
            var1 *= 1.0f - var4 * 0.15f;
        }
        return Reflector.ForgeHooksClient_getOffsetFOV.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getOffsetFOV, this, var1) : var1;
    }
    
    public String getNameClear() {
        return this.nameClear;
    }
    
    public ResourceLocation getLocationOfCape() {
        return this.locationOfCape;
    }
    
    public void setLocationOfCape(final ResourceLocation locationOfCape) {
        this.locationOfCape = locationOfCape;
    }
}
