/*
 * Decompiled with CFR 0.152.
 */
package de.jo.scripting.utils.copy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static void writeToFile(File file, String text) {
        ArrayList<String> text1 = new ArrayList<String>();
        text1.clear();
        text1.add(text);
        FileUtil.writeToFile(file, text1);
    }

    public static synchronized void writeToFile(File file, List<String> text) {
        FileUtil.writeToFile(file, text.toArray(new String[text.size()]));
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

    public static synchronized List<String> readFile(File file) {
        ArrayList<String> tempList;
        block7: {
            FileReader fileReader;
            tempList = new ArrayList<String>();
            try {
                fileReader = new FileReader(file);
            }
            catch (FileNotFoundException e) {
                return tempList;
            }
            BufferedReader reader = null;
            try {
                String line;
                reader = new BufferedReader(fileReader);
                while ((line = reader.readLine()) != null) {
                    tempList.add(line);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                if (reader == null) break block7;
                try {
                    reader.close();
                }
                catch (IOException ex) {
                    e.printStackTrace();
                }
            }
        }
        return tempList;
    }
}

