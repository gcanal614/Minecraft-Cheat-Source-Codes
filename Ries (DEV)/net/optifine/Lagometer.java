/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.optifine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.profiler.Profiler;
import net.minecraft.src.Config;
import org.lwjgl.opengl.GL11;

public class Lagometer {
    private static Minecraft mc;
    private static GameSettings gameSettings;
    private static Profiler profiler;
    public static boolean active;
    public static final TimerNano timerTick;
    public static final TimerNano timerScheduledExecutables;
    public static final TimerNano timerChunkUpload;
    public static final TimerNano timerChunkUpdate;
    public static final TimerNano timerVisibility;
    public static final TimerNano timerTerrain;
    public static final TimerNano timerServer;
    private static final long[] timesFrame;
    private static final long[] timesTick;
    private static final long[] timesScheduledExecutables;
    private static final long[] timesChunkUpload;
    private static final long[] timesChunkUpdate;
    private static final long[] timesVisibility;
    private static final long[] timesTerrain;
    private static final long[] timesServer;
    private static final boolean[] gcs;
    private static int numRecordedFrameTimes;
    private static long prevFrameTimeNano;
    private static long renderTimeNano;
    private static long memTimeStartMs;
    private static long memStart;
    private static long memTimeLast;
    private static long memLast;
    private static long memTimeDiffMs;
    private static long memDiff;
    private static int memMbSec;

    public static boolean updateMemoryAllocation() {
        long i = System.currentTimeMillis();
        long j = Lagometer.getMemoryUsed();
        boolean flag = false;
        if (j < memLast) {
            double d0 = (double)memDiff / 1000000.0;
            double d1 = (double)memTimeDiffMs / 1000.0;
            int k = (int)(d0 / d1);
            if (k > 0) {
                memMbSec = k;
            }
            memTimeStartMs = i;
            memStart = j;
            memTimeDiffMs = 0L;
            memDiff = 0L;
            flag = true;
        } else {
            memTimeDiffMs = i - memTimeStartMs;
            memDiff = j - memStart;
        }
        memTimeLast = i;
        memLast = j;
        return flag;
    }

    private static long getMemoryUsed() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static void updateLagometer() {
        if (mc == null) {
            mc = Minecraft.getMinecraft();
            gameSettings = Lagometer.mc.gameSettings;
            profiler = Lagometer.mc.mcProfiler;
        }
        if (Lagometer.gameSettings.showDebugInfo && (Lagometer.gameSettings.ofLagometer || Lagometer.gameSettings.field_181657_aC)) {
            active = true;
            long timeNowNano = System.nanoTime();
            if (prevFrameTimeNano == -1L) {
                prevFrameTimeNano = timeNowNano;
            } else {
                int j = numRecordedFrameTimes & timesFrame.length - 1;
                ++numRecordedFrameTimes;
                boolean flag = Lagometer.updateMemoryAllocation();
                Lagometer.timesFrame[j] = timeNowNano - prevFrameTimeNano - renderTimeNano;
                Lagometer.timesTick[j] = Lagometer.timerTick.timeNano;
                Lagometer.timesScheduledExecutables[j] = Lagometer.timerScheduledExecutables.timeNano;
                Lagometer.timesChunkUpload[j] = Lagometer.timerChunkUpload.timeNano;
                Lagometer.timesChunkUpdate[j] = Lagometer.timerChunkUpdate.timeNano;
                Lagometer.timesVisibility[j] = Lagometer.timerVisibility.timeNano;
                Lagometer.timesTerrain[j] = Lagometer.timerTerrain.timeNano;
                Lagometer.timesServer[j] = Lagometer.timerServer.timeNano;
                Lagometer.gcs[j] = flag;
                Lagometer.timerTick.reset();
                Lagometer.timerScheduledExecutables.reset();
                Lagometer.timerVisibility.reset();
                Lagometer.timerChunkUpdate.reset();
                Lagometer.timerChunkUpload.reset();
                Lagometer.timerTerrain.reset();
                Lagometer.timerServer.reset();
                prevFrameTimeNano = System.nanoTime();
            }
        } else {
            active = false;
            prevFrameTimeNano = -1L;
        }
    }

    public static void showLagometer(ScaledResolution scaledResolution) {
        if (gameSettings != null && (Lagometer.gameSettings.ofLagometer || Lagometer.gameSettings.field_181657_aC)) {
            long i = System.nanoTime();
            GlStateManager.clear(256);
            GlStateManager.matrixMode(5889);
            GlStateManager.pushMatrix();
            GlStateManager.enableColorMaterial();
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0, Lagometer.mc.displayWidth, Lagometer.mc.displayHeight, 0.0, 1000.0, 3000.0);
            GlStateManager.matrixMode(5888);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0f, 0.0f, -2000.0f);
            GL11.glLineWidth((float)1.0f);
            GlStateManager.disableTexture2D();
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            worldrenderer.func_181668_a(1, DefaultVertexFormats.field_181706_f);
            for (int j = 0; j < timesFrame.length; ++j) {
                int k = (j - numRecordedFrameTimes & timesFrame.length - 1) * 100 / timesFrame.length;
                k += 155;
                float f = Lagometer.mc.displayHeight;
                if (gcs[j]) {
                    Lagometer.renderTime(j, timesFrame[j], k, k / 2, 0, f, worldrenderer);
                    continue;
                }
                Lagometer.renderTime(j, timesFrame[j], k, k, k, f, worldrenderer);
                f -= (float)Lagometer.renderTime(j, timesServer[j], k / 2, k / 2, k / 2, f, worldrenderer);
                f -= (float)Lagometer.renderTime(j, timesTerrain[j], 0, k, 0, f, worldrenderer);
                f -= (float)Lagometer.renderTime(j, timesVisibility[j], k, k, 0, f, worldrenderer);
                f -= (float)Lagometer.renderTime(j, timesChunkUpdate[j], k, 0, 0, f, worldrenderer);
                f -= (float)Lagometer.renderTime(j, timesChunkUpload[j], k, 0, k, f, worldrenderer);
                f -= (float)Lagometer.renderTime(j, timesScheduledExecutables[j], 0, 0, k, f, worldrenderer);
            }
            Lagometer.renderTimeDivider(timesFrame.length, 33333333L, Lagometer.mc.displayHeight, worldrenderer);
            Lagometer.renderTimeDivider(timesFrame.length, 16666666L, Lagometer.mc.displayHeight, worldrenderer);
            tessellator.draw();
            GlStateManager.enableTexture2D();
            int j2 = Lagometer.mc.displayHeight - 80;
            int k2 = Lagometer.mc.displayHeight - 160;
            Lagometer.mc.fontRendererObj.drawString("30", 2, k2 + 1, -8947849);
            Lagometer.mc.fontRendererObj.drawString("30", 1, k2, -3881788);
            Lagometer.mc.fontRendererObj.drawString("60", 2, j2 + 1, -8947849);
            Lagometer.mc.fontRendererObj.drawString("60", 1, j2, -3881788);
            GlStateManager.matrixMode(5889);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.enableTexture2D();
            float f1 = 1.0f - (float)((double)(System.currentTimeMillis() - memTimeStartMs) / 1000.0);
            f1 = Config.limit(f1, 0.0f, 1.0f);
            int l2 = (int)(170.0f + f1 * 85.0f);
            int i1 = (int)(100.0f + f1 * 55.0f);
            int j1 = (int)(10.0f + f1 * 10.0f);
            int k1 = l2 << 16 | i1 << 8 | j1;
            int l1 = 512 / scaledResolution.getScaleFactor() + 2;
            int i2 = Lagometer.mc.displayHeight / scaledResolution.getScaleFactor() - 8;
            GuiIngame.drawRect(l1 - 1, i2 - 1, l1 + 50, i2 + 10, -1605349296);
            Lagometer.mc.fontRendererObj.drawString(" " + memMbSec + " MB/s", l1, i2, k1);
            renderTimeNano = System.nanoTime() - i;
        }
    }

    private static long renderTime(int frameNum, long time, int r, int g, int b, float baseHeight, WorldRenderer tessellator) {
        long i = time / 200000L;
        if (i < 3L) {
            return 0L;
        }
        tessellator.func_181662_b((float)frameNum + 0.5f, baseHeight - (float)i + 0.5f, 0.0).func_181669_b(r, g, b, 255).func_181675_d();
        tessellator.func_181662_b((float)frameNum + 0.5f, baseHeight + 0.5f, 0.0).func_181669_b(r, g, b, 255).func_181675_d();
        return i;
    }

    private static void renderTimeDivider(int frameEnd, long time, float baseHeight, WorldRenderer tessellator) {
        long i = time / 200000L;
        if (i >= 3L) {
            tessellator.func_181662_b(0.5, baseHeight - (float)i + 0.5f, 0.0).func_181669_b(196, 196, 196, 255).func_181675_d();
            tessellator.func_181662_b((float)frameEnd + 0.5f, baseHeight - (float)i + 0.5f, 0.0).func_181669_b(196, 196, 196, 255).func_181675_d();
        }
    }

    public static boolean isActive() {
        return active;
    }

    static {
        active = false;
        timerTick = new TimerNano();
        timerScheduledExecutables = new TimerNano();
        timerChunkUpload = new TimerNano();
        timerChunkUpdate = new TimerNano();
        timerVisibility = new TimerNano();
        timerTerrain = new TimerNano();
        timerServer = new TimerNano();
        timesFrame = new long[512];
        timesTick = new long[512];
        timesScheduledExecutables = new long[512];
        timesChunkUpload = new long[512];
        timesChunkUpdate = new long[512];
        timesVisibility = new long[512];
        timesTerrain = new long[512];
        timesServer = new long[512];
        gcs = new boolean[512];
        numRecordedFrameTimes = 0;
        prevFrameTimeNano = -1L;
        renderTimeNano = 0L;
        memTimeStartMs = System.currentTimeMillis();
        memStart = Lagometer.getMemoryUsed();
        memTimeLast = memTimeStartMs;
        memLast = memStart;
        memTimeDiffMs = 1L;
        memDiff = 0L;
        memMbSec = 0;
    }

    public static class TimerNano {
        public long timeStartNano = 0L;
        public long timeNano = 0L;

        public void start() {
            if (active && this.timeStartNano == 0L) {
                this.timeStartNano = System.nanoTime();
            }
        }

        public void end() {
            if (active && this.timeStartNano != 0L) {
                this.timeNano += System.nanoTime() - this.timeStartNano;
                this.timeStartNano = 0L;
            }
        }

        private void reset() {
            this.timeNano = 0L;
            this.timeStartNano = 0L;
        }
    }
}

