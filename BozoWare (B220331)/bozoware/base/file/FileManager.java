// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.file;

import java.io.File;
import bozoware.base.util.Wrapper;

public class FileManager
{
    private final String clientDirectory;
    
    public FileManager() {
        this.clientDirectory = Wrapper.getMinecraft().mcDataDir.getPath() + "/BozoWare";
        final File clientDirectoryFolder = new File(this.clientDirectory);
        if (!clientDirectoryFolder.exists() && clientDirectoryFolder.mkdirs()) {
            System.out.println("Created client directory...");
        }
    }
    
    public void addSubDirectory(final String directoryName) {
        final File newDirectory = new File(String.format("%s/%s", this.clientDirectory, directoryName));
        if (!newDirectory.exists() && newDirectory.mkdirs()) {
            System.out.println("Created sub directory: " + directoryName);
        }
    }
    
    public String getClientDirectory() {
        return this.clientDirectory;
    }
}
