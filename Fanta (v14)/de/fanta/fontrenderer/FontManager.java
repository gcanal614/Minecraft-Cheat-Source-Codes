/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.fontrenderer;

import de.fanta.fontrenderer.TTFFontRenderer;
import de.fanta.fontrenderer.TextureData;
import java.awt.Font;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class FontManager {
    private final HashMap<String, TTFFontRenderer> fonts = new HashMap();
    private final TTFFontRenderer defaultFront;

    public FontManager() {
        this.addAll();
        this.defaultFront = this.getFont("Arial 20");
    }

    private void addAll() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(8);
        ConcurrentLinkedQueue<TextureData> textureQueue = new ConcurrentLinkedQueue<TextureData>();
        this.addFonts(executorService, textureQueue);
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (!textureQueue.isEmpty()) {
                TextureData textureData = textureQueue.poll();
                GlStateManager.bindTexture(textureData.getTextureId());
                GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
                GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
                GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)textureData.getWidth(), (int)textureData.getHeight(), (int)0, (int)6408, (int)5121, (ByteBuffer)textureData.getBuffer());
            }
        }
    }

    private void addFonts(ThreadPoolExecutor executorService, ConcurrentLinkedQueue<TextureData> textureQueue) {
        int[] nArray = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 200};
        int n = nArray.length;
        int n2 = 0;
        while (n2 < n) {
            int i = nArray[n2];
            try {
                InputStream istream = this.getClass().getResourceAsStream("/assets/minecraft/Fanta/fonts/arial/Arial.ttf");
                Font font = Font.createFont(0, istream);
                font = font.deriveFont(0, i);
                this.fonts.put("Arial " + i, new TTFFontRenderer(executorService, textureQueue, font));
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Fanta/fonts/roboto/Roboto-Bold.ttf");
                font = Font.createFont(0, istream);
                font = font.deriveFont(0, i);
                this.fonts.put("Roboto bold " + i, new TTFFontRenderer(executorService, textureQueue, font));
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Fanta/fonts/roboto/Roboto-Medium.ttf");
                font = Font.createFont(0, istream);
                font = font.deriveFont(0, i);
                this.fonts.put("Roboto medium " + i, new TTFFontRenderer(executorService, textureQueue, font));
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Fanta/fonts/arrow/arrows-regular.ttf");
                font = Font.createFont(0, istream);
                font = font.deriveFont(0, i);
                this.fonts.put("Arrow " + i, new TTFFontRenderer(executorService, textureQueue, font));
                istream = this.getClass().getResourceAsStream("/assets/minecraft/Fanta/fonts/icon/icon-font.otf");
                font = Font.createFont(0, istream);
                font = font.deriveFont(0, i);
                this.fonts.put("ICON " + i, new TTFFontRenderer(executorService, textureQueue, font));
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++n2;
        }
    }

    public final TTFFontRenderer getFont(String name) {
        return this.fonts.getOrDefault(name, this.defaultFront);
    }
}

