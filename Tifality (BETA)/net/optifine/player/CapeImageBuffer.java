/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.player;

import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.util.ResourceLocation;
import net.optifine.player.CapeUtils;

public class CapeImageBuffer
extends ImageBufferDownload {
    public ImageBufferDownload imageBufferDownload;
    public final WeakReference<AbstractClientPlayer> playerRef;
    public final ResourceLocation resourceLocation;

    public CapeImageBuffer(AbstractClientPlayer player, ResourceLocation resourceLocation) {
        this.playerRef = new WeakReference<AbstractClientPlayer>(player);
        this.resourceLocation = resourceLocation;
        this.imageBufferDownload = new ImageBufferDownload();
    }

    @Override
    public BufferedImage parseUserSkin(BufferedImage imageRaw) {
        return CapeUtils.parseCape(imageRaw);
    }

    @Override
    public void skinAvailable() {
        AbstractClientPlayer player = (AbstractClientPlayer)this.playerRef.get();
        if (player != null) {
            player.setLocationOfCape(this.resourceLocation);
        }
    }
}

