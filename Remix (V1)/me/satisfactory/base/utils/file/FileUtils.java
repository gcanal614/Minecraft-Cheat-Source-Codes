package me.satisfactory.base.utils.file;

import java.io.*;
import java.util.*;
import net.minecraft.client.*;

public final class FileUtils
{
    public static List<String> read(final File inputFile) {
        final List<String> readContent = new ArrayList<String>();
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
            String str;
            while ((str = in.readLine()) != null) {
                readContent.add(str);
            }
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return readContent;
    }
    
    public static void write(final File outputFile, final List<String> writeContent, final boolean overrideContent) {
        try {
            final Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
            for (final String outputLine : writeContent) {
                out.write(String.valueOf(outputLine) + System.getProperty("line.separator"));
            }
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static File getConfigDir() {
        final File file = new File(Minecraft.getMinecraft().mcDataDir, "Remix Config");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }
    
    public static File getConfigDirConf() {
        final File file = new File(Minecraft.getMinecraft().mcDataDir, "Remix Config/Configs");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }
    
    public static File getConfigFile(final String name) {
        final File file = new File(getConfigDir(), String.format("%s.txt", name));
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    
    public static File getConfigFileConf(final String name) {
        final File file = new File(getConfigDirConf(), String.format("%s.txt", name));
        return file;
    }
}
