/*
 * Decompiled with CFR 0.152.
 */
package de.jo.scripting.base.copy;

import de.jo.scripting.utils.FileUtil;
import java.io.File;
import java.util.List;

public class Script {
    private File file;
    private String name;
    private String author;
    private String description;
    private float version;
    private List<String> lines;
    private boolean tick;
    public static final String FILE_TYPE = ".js";

    public Script(File file) {
        if (!file.getName().endsWith(FILE_TYPE)) {
            System.err.println("Invalid file");
            return;
        }
        try {
            this.file = file;
            this.lines = FileUtil.readFile(file);
            if (this.lines.size() < 4) {
                System.err.println("Couldn't load Script");
                return;
            }
            this.name = "Default";
            this.author = "None";
            this.version = 1.0f;
            int i = 0;
            while (i < 4) {
                String line = this.lines.get(i);
                if (line.toLowerCase().startsWith("//name")) {
                    this.name = line.split(" ")[1].trim();
                }
                if (line.toLowerCase().startsWith("//author")) {
                    this.author = line.split(" ")[1].trim();
                }
                if (line.toLowerCase().startsWith("//version")) {
                    this.version = Float.valueOf(line.split(" ")[1].trim()).floatValue();
                }
                if (line.toLowerCase().startsWith("//tick")) {
                    String arg = line.split(" ")[1].trim();
                    this.tick = arg.equalsIgnoreCase("true");
                }
                ++i;
            }
        }
        catch (Exception e) {
            System.err.println("Couldn't load Script");
        }
    }

    public String getAuthor() {
        return this.author;
    }

    public String getDescription() {
        return this.description;
    }

    public File getFile() {
        return this.file;
    }

    public List<String> getLines() {
        return this.lines;
    }

    public String getName() {
        return this.name;
    }

    public float getVersion() {
        return this.version;
    }

    public boolean isTick() {
        return this.tick;
    }
}

