/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class Stream {
    private final Provider provider;
    private final String channelName;
    private final String fullChannelName;
    private final String channelURL;
    private String title;
    private String fulltitle;
    private String artist;
    private String coverURL;
    private BufferedImage image;
    private DynamicTexture texture;
    private ResourceLocation location;

    public Stream(Provider provider, String channelName, String channelURL, String title, String artist, String coverURL) throws MalformedURLException, IOException {
        this.provider = provider;
        this.channelName = this.shortenString(channelName, 23);
        this.fullChannelName = channelName;
        this.channelURL = channelURL;
        this.title = this.shortenString(title, 17);
        this.artist = this.shortenString(artist, 17);
        this.coverURL = coverURL;
        this.fulltitle = title;
        this.image = ImageIO.read(new URL(coverURL));
        if (this.image != null) {
            this.texture = new DynamicTexture(this.image);
            System.out.println("NEw");
            Minecraft.getMinecraft().getTextureManager().deleteTexture(this.location);
            this.location = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(this.getChannelName(), this.texture);
        }
    }

    public String getArtist() {
        return this.artist;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public String getChannelURL() {
        return this.channelURL;
    }

    public String getCoverURL() {
        return this.coverURL;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public ResourceLocation getLocation() {
        return this.location;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public DynamicTexture getTexture() {
        return this.texture;
    }

    public String getTitle() {
        return this.title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public void setTitle(String title) {
        this.title = this.shortenString(title, 17);
        this.fulltitle = title;
    }

    public String getFulltitle() {
        return this.fulltitle;
    }

    public String getFullChannelName() {
        return this.fullChannelName;
    }

    public void setTexture(DynamicTexture texture) {
        this.texture = texture;
    }

    public void setLocation(ResourceLocation location) {
        this.location = location;
    }

    public void updateStream(String title, String artist, String coverURL) throws MalformedURLException, IOException {
        this.title = this.shortenString(title, 17);
        this.artist = this.shortenString(artist, 17);
        this.fulltitle = title;
        if (this.coverURL != coverURL) {
            this.coverURL = coverURL;
            this.image = ImageIO.read(new URL(coverURL));
        }
    }

    private String shortenString(String input, int length) {
        if (input.length() > length) {
            String[] args = input.split(" ");
            StringBuilder builder = new StringBuilder(args[0]);
            int i = 1;
            while (i < args.length) {
                if (builder.toString().length() + args[i].length() < length && !args[i].equals("&")) {
                    builder.append(" " + args[i]);
                }
                ++i;
            }
            return builder.toString();
        }
        return input;
    }

    public static enum Provider {
        ILoveMusik;

    }
}

