/*
 * Decompiled with CFR 0.152.
 */
package net.optifine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.src.Config;
import net.optifine.http.FileUploadThread;
import net.optifine.http.IFileUploadListener;
import net.optifine.shaders.Shaders;

public class CrashReporter {
    public static void onCrashReport(CrashReport crashReport, CrashReportCategory category) {
        try {
            Throwable throwable = crashReport.getCrashCause();
            if (throwable == null) {
                return;
            }
            if (throwable.getClass().getName().contains(".fml.client.SplashProgress")) {
                return;
            }
            CrashReporter.extendCrashReport(category);
            if (throwable.getClass() == Throwable.class) {
                return;
            }
            GameSettings gamesettings = Config.getGameSettings();
            if (gamesettings == null) {
                return;
            }
            if (!gamesettings.snooperEnabled) {
                return;
            }
            String s = "http://optifine.net/crashReport";
            String s1 = CrashReporter.makeReport(crashReport);
            byte[] abyte = s1.getBytes(StandardCharsets.US_ASCII);
            IFileUploadListener ifileuploadlistener = (url, content, exception) -> {};
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("OF-Version", Config.getVersion());
            map.put("OF-Summary", CrashReporter.makeSummary(crashReport));
            FileUploadThread fileuploadthread = new FileUploadThread(s, map, abyte, ifileuploadlistener);
            fileuploadthread.setPriority(10);
            fileuploadthread.start();
            Thread.sleep(1000L);
        }
        catch (Exception exception2) {
            Config.dbg(exception2.getClass().getName() + ": " + exception2.getMessage());
        }
    }

    private static String makeReport(CrashReport crashReport) {
        return "OptiFineVersion: " + Config.getVersion() + "\nSummary: " + CrashReporter.makeSummary(crashReport) + "\n\n" + crashReport.getCompleteReport() + "\n";
    }

    private static String makeSummary(CrashReport crashReport) {
        Throwable throwable = crashReport.getCrashCause();
        if (throwable == null) {
            return "Unknown";
        }
        StackTraceElement[] astacktraceelement = throwable.getStackTrace();
        String s = "unknown";
        if (astacktraceelement.length > 0) {
            s = astacktraceelement[0].toString().trim();
        }
        return throwable.getClass().getName() + ": " + throwable.getMessage() + " (" + crashReport.getDescription() + ") [" + s + "]";
    }

    public static void extendCrashReport(CrashReportCategory cat) {
        cat.addCrashSection("OptiFine Version", Config.getVersion());
        cat.addCrashSection("OptiFine Build", Config.getBuild());
        if (Config.getGameSettings() != null) {
            cat.addCrashSection("Render Distance Chunks", "" + Config.getChunkViewDistance());
            cat.addCrashSection("Mipmaps", "" + Config.getMipmapLevels());
            cat.addCrashSection("Anisotropic Filtering", "" + Config.getAnisotropicFilterLevel());
            cat.addCrashSection("Antialiasing", "" + Config.getAntialiasingLevel());
            cat.addCrashSection("Multitexture", "" + Config.isMultiTexture());
        }
        cat.addCrashSection("Shaders", "" + Shaders.getShaderPackName());
        cat.addCrashSection("OpenGlVersion", "" + Config.openGlVersion);
        cat.addCrashSection("OpenGlRenderer", "" + Config.openGlRenderer);
        cat.addCrashSection("OpenGlVendor", "" + Config.openGlVendor);
        cat.addCrashSection("CpuCount", "" + Config.getAvailableProcessors());
    }
}

