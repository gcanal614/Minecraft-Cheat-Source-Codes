/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.config;

import club.tifality.gui.altmanager.Alt;
import club.tifality.gui.altmanager.AltManager;
import club.tifality.manager.config.ConfigManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Alts
extends ConfigManager.CustomFile {
    public Alts(String name, boolean Module2, boolean loadOnStart) {
        super(name, Module2, loadOnStart);
    }

    @Override
    public void loadFile() throws IOException {
        String line;
        BufferedReader variable9 = new BufferedReader(new FileReader(this.getFile()));
        while ((line = variable9.readLine()) != null) {
            String[] arguments2 = line.split(":");
            for (int i = 0; i < 2; ++i) {
                arguments2[i].replace(" ", "");
            }
            if (arguments2.length > 2) {
                AltManager.registry.add(new Alt(arguments2[0], arguments2[1], arguments2[2], arguments2.length > 3 ? Alt.Status.valueOf(arguments2[3]) : Alt.Status.Unchecked));
                continue;
            }
            AltManager.registry.add(new Alt(arguments2[0], arguments2[1]));
        }
        variable9.close();
        System.out.println("Loaded " + this.getName() + " File!");
    }

    @Override
    public void saveFile() throws IOException {
        PrintWriter alts = new PrintWriter(new FileWriter(this.getFile()));
        for (Object o : AltManager.registry) {
            Alt alt = (Alt)o;
            if (alt.getMask().equals("")) {
                alts.println(alt.getUsername() + ":" + alt.getPassword() + ":" + alt.getUsername() + ":" + (Object)((Object)alt.getStatus()));
                continue;
            }
            alts.println(alt.getUsername() + ":" + alt.getPassword() + ":" + alt.getMask() + ":" + (Object)((Object)alt.getStatus()));
        }
        alts.close();
    }
}

