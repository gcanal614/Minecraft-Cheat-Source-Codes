/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;

public class FileManager {
    public static final File CLIENT_DIR = new File(Minecraft.getMinecraft().mcDataDir, "Fanta");

    public static void writeToFile(File file, String text) {
        ArrayList<String> text1 = new ArrayList<String>();
        text1.clear();
        text1.add(text);
        FileManager.writeToFile(file, text1);
    }

    public static boolean createDirectory(String name) {
        File dir = new File(CLIENT_DIR, name);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
            dir.mkdirs();
        }
        return dir.isDirectory();
    }

    public static File getDirectory(String name) {
        FileManager.createDirectory(name);
        return new File(CLIENT_DIR, name.endsWith(File.separator) ? name : String.valueOf(name) + "/");
    }

    public static synchronized void writeToFile(File file, List<String> text) {
        FileManager.writeToFile(file, text.toArray(new String[text.size()]));
    }

    public static synchronized void writeToFile(File file, String[] text) {
        try (PrintWriter writer = null;){
            try {
                writer = new PrintWriter(new FileWriter(file));
                String[] arrayOfString = text;
                int i = text.length;
                int b = 0;
                while (b < i) {
                    String line = arrayOfString[b];
                    writer.println(line);
                    writer.flush();
                    b = (byte)(b + 1);
                }
            }
            catch (Exception exception) {
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }
}

