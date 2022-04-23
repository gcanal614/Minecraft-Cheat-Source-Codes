/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;

public class SkinManager {
    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    private final TextureManager textureManager;
    private final File skinCacheDir;
    private final MinecraftSessionService sessionService;
    private final LoadingCache<GameProfile, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>> skinCacheLoader;

    public SkinManager(TextureManager p_i1044_1_, File p_i1044_2_, MinecraftSessionService p_i1044_3_) {
        this.textureManager = p_i1044_1_;
        this.skinCacheDir = p_i1044_2_;
        this.sessionService = p_i1044_3_;
        this.skinCacheLoader = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build(new CacheLoader<GameProfile, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>>(){

            @Override
            public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> load(GameProfile p_load_1_) throws Exception {
                return Minecraft.getMinecraft().getSessionService().getTextures(p_load_1_, false);
            }
        });
    }

    public ResourceLocation loadSkin(MinecraftProfileTexture p_loadSkin_1_, MinecraftProfileTexture.Type p_loadSkin_2_) {
        return this.loadSkin(p_loadSkin_1_, p_loadSkin_2_, null);
    }

    public ResourceLocation loadSkin(final MinecraftProfileTexture p_loadSkin_1_, final MinecraftProfileTexture.Type p_loadSkin_2_, final SkinAvailableCallback p_loadSkin_3_) {
        final ResourceLocation lvt_4_1_ = new ResourceLocation("skins/" + p_loadSkin_1_.getHash());
        ITextureObject lvt_5_1_ = this.textureManager.getTexture(lvt_4_1_);
        if (lvt_5_1_ != null) {
            if (p_loadSkin_3_ != null) {
                p_loadSkin_3_.skinAvailable(p_loadSkin_2_, lvt_4_1_, p_loadSkin_1_);
            }
        } else {
            File lvt_6_1_ = new File(this.skinCacheDir, p_loadSkin_1_.getHash().length() > 2 ? p_loadSkin_1_.getHash().substring(0, 2) : "xx");
            File lvt_7_1_ = new File(lvt_6_1_, p_loadSkin_1_.getHash());
            final ImageBufferDownload lvt_8_1_ = p_loadSkin_2_ == MinecraftProfileTexture.Type.SKIN ? new ImageBufferDownload() : null;
            ThreadDownloadImageData lvt_9_1_ = new ThreadDownloadImageData(lvt_7_1_, p_loadSkin_1_.getUrl(), DefaultPlayerSkin.getDefaultSkinLegacy(), new IImageBuffer(){

                @Override
                public BufferedImage parseUserSkin(BufferedImage p_parseUserSkin_1_) {
                    if (lvt_8_1_ != null) {
                        p_parseUserSkin_1_ = lvt_8_1_.parseUserSkin(p_parseUserSkin_1_);
                    }
                    return p_parseUserSkin_1_;
                }

                @Override
                public void skinAvailable() {
                    if (lvt_8_1_ != null) {
                        lvt_8_1_.skinAvailable();
                    }
                    if (p_loadSkin_3_ != null) {
                        p_loadSkin_3_.skinAvailable(p_loadSkin_2_, lvt_4_1_, p_loadSkin_1_);
                    }
                }
            });
            this.textureManager.loadTexture(lvt_4_1_, lvt_9_1_);
        }
        return lvt_4_1_;
    }

    public void loadProfileTextures(final GameProfile p_loadProfileTextures_1_, final SkinAvailableCallback p_loadProfileTextures_2_, final boolean p_loadProfileTextures_3_) {
        THREAD_POOL.submit(new Runnable(){

            @Override
            public void run() {
                final HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture> lvt_1_1_ = Maps.newHashMap();
                try {
                    lvt_1_1_.putAll(SkinManager.this.sessionService.getTextures(p_loadProfileTextures_1_, p_loadProfileTextures_3_));
                }
                catch (InsecureTextureException insecureTextureException) {
                    // empty catch block
                }
                if (lvt_1_1_.isEmpty() && p_loadProfileTextures_1_.getId().equals(Minecraft.getMinecraft().getSession().getProfile().getId())) {
                    p_loadProfileTextures_1_.getProperties().clear();
                    p_loadProfileTextures_1_.getProperties().putAll(Minecraft.getMinecraft().getProfileProperties());
                    lvt_1_1_.putAll(SkinManager.this.sessionService.getTextures(p_loadProfileTextures_1_, false));
                }
                Minecraft.getMinecraft().addScheduledTask(new Runnable(){

                    @Override
                    public void run() {
                        if (lvt_1_1_.containsKey((Object)MinecraftProfileTexture.Type.SKIN)) {
                            SkinManager.this.loadSkin((MinecraftProfileTexture)lvt_1_1_.get((Object)MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN, p_loadProfileTextures_2_);
                        }
                        if (lvt_1_1_.containsKey((Object)MinecraftProfileTexture.Type.CAPE)) {
                            SkinManager.this.loadSkin((MinecraftProfileTexture)lvt_1_1_.get((Object)MinecraftProfileTexture.Type.CAPE), MinecraftProfileTexture.Type.CAPE, p_loadProfileTextures_2_);
                        }
                    }
                });
            }
        });
    }

    public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> loadSkinFromCache(GameProfile p_loadSkinFromCache_1_) {
        return this.skinCacheLoader.getUnchecked(p_loadSkinFromCache_1_);
    }

    public static interface SkinAvailableCallback {
        public void skinAvailable(MinecraftProfileTexture.Type var1, ResourceLocation var2, MinecraftProfileTexture var3);
    }
}

