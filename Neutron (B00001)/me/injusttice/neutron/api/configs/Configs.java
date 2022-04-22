package me.injusttice.neutron.api.configs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.ModuleCategory;
import me.injusttice.neutron.api.settings.Setting;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.api.settings.impl.StringSet;

public class Configs {
    private File dir;

    private File dataFile;

    public void save(String name) {
        dir = new File(String.valueOf(NeutronMain.instance.dir));
        if (!dir.exists())
            dir.mkdir();
        dataFile = new File(dir, name + ".file");
        if (!dataFile.exists())
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        ArrayList<String> toSave = new ArrayList<>();
        for (Module m : NeutronMain.instance.moduleManager.getModules()) {
            toSave.add("Module:" + m.getName() + ":" + m.isToggled() + ":" + m.getKey() + ":" + '\001');
            for (Setting s : m.settings) {
                if (s instanceof DoubleSet) {
                    DoubleSet set = (DoubleSet)s;
                    toSave.add("NumberSet:" + m.getName() + ":" + set.name + ":" + set.getValue());
                    continue;
                }
                if (s instanceof BooleanSet) {
                    BooleanSet set = (BooleanSet)s;
                    toSave.add("BooleanSet:" + m.getName() + ":" + set.name + ":" + set.enabled);
                    continue;
                }
                if (s instanceof ModeSet) {
                    ModeSet set = (ModeSet)s;
                    toSave.add("ModeSet:" + m.getName() + ":" + set.name + ":" + set.getMode());
                    continue;
                }
                if (s instanceof StringSet) {
                    StringSet set = (StringSet)s;
                    toSave.add("StringSet:" + m.getName() + ":" + set.name + ":" + set.getText());
                    continue;
                }
                if (s instanceof ModuleCategory) {
                    ModuleCategory category = (ModuleCategory)s;
                    for (Setting set : category.settingsOnCat) {
                        if (set instanceof DoubleSet) {
                            DoubleSet dset = (DoubleSet)set;
                            toSave.add("CategoryNumberSet:" + m.getName() + ":" + category.name + ":" + set.name + ":" + dset.getValue());
                            continue;
                        }
                        if (set instanceof BooleanSet) {
                            BooleanSet bset = (BooleanSet)set;
                            toSave.add("CategoryBooleanSet:" + m.getName() + ":" + category.name + ":" + set.name + ":" + bset.enabled);
                            continue;
                        }
                        if (set instanceof ModeSet) {
                            ModeSet mset = (ModeSet)set;
                            toSave.add("CategoryModeSet:" + m.getName() + ":" + category.name + ":" + set.name + ":" + mset.getMode());
                            continue;
                        }
                        if (set instanceof StringSet) {
                            StringSet mset = (StringSet)set;
                            toSave.add("CategoryStringSet:" + m.getName() + ":" + category.name + ":" + set.name + ":" + mset.getText());
                        }
                    }
                }
            }
        }
        try {
            PrintWriter pw = new PrintWriter(dataFile);
            for (String str : toSave)
                pw.println(str);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void delete(String name) {
        dir = new File(String.valueOf(NeutronMain.instance.dir));
        if (!dir.exists())
            dir.mkdir();
        dataFile = new File(dir, name + ".file");
        try {
            dataFile.delete();
        } catch (Exception exception) {}
    }

    public void load(String name) {
        dir = new File(String.valueOf(NeutronMain.instance.dir));
        if (!dir.exists())
            dir.mkdir();
        dataFile = new File(dir, name + ".file");
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            for (String s : lines) {
                String[] args = s.split(":");
                if (s.toLowerCase().startsWith("module:"))
                    for (Module m : NeutronMain.instance.moduleManager.getModules()) {
                        if (m.getName().equalsIgnoreCase(args[1])) {
                            boolean shouldEnable = Boolean.parseBoolean(args[2]);
                            if (shouldEnable && !m.isToggled())
                                m.setToggled(true);
                            if (args.length > 4);
                        }
                    }
                if (s.toLowerCase().startsWith("numberset:"))
                    for (Module m : NeutronMain.instance.moduleManager.getModules()) {
                        if (m.getName().equalsIgnoreCase(args[1]))
                            for (Setting setting : m.settings) {
                                if (!(setting instanceof DoubleSet))
                                    continue;
                                if (setting.name.equalsIgnoreCase(args[2])) {
                                    DoubleSet setting1 = (DoubleSet)setting;
                                    setting1.setValue(Double.parseDouble(args[3]));
                                }
                            }
                    }
                if (s.toLowerCase().startsWith("booleanset:"))
                    for (Module m : NeutronMain.instance.moduleManager.getModules()) {
                        if (m.getName().equalsIgnoreCase(args[1]))
                            for (Setting setting : m.settings) {
                                if (!(setting instanceof BooleanSet))
                                    continue;
                                if (setting.name.equalsIgnoreCase(args[2])) {
                                    BooleanSet setting1 = (BooleanSet)setting;
                                    setting1.setEnabled(Boolean.parseBoolean(args[3]));
                                }
                            }
                    }
                if (s.toLowerCase().startsWith("modeset:"))
                    for (Module m : NeutronMain.instance.moduleManager.getModules()) {
                        if (m.getName().equalsIgnoreCase(args[1]))
                            for (Setting setting : m.settings) {
                                if (!(setting instanceof ModeSet))
                                    continue;
                                for (String str : ((ModeSet)setting).modes) {
                                    if (setting.name.equalsIgnoreCase(args[2]) &&
                                            args[3].equalsIgnoreCase(str)) {
                                        ModeSet setting1 = (ModeSet)setting;
                                        setting1.setSelected(args[3]);
                                    }
                                }
                            }
                    }
                if (s.toLowerCase().startsWith("stringset:"))
                    for (Module m : NeutronMain.instance.moduleManager.getModules()) {
                        if (m.getName().equalsIgnoreCase(args[1]))
                            for (Setting setting : m.settings) {
                                if (!(setting instanceof StringSet))
                                    continue;
                                if (setting.name.equalsIgnoreCase(args[2])) {
                                    StringSet setting1 = (StringSet)setting;
                                    setting1.setText(args[3]);
                                }
                            }
                    }
                if (s.toLowerCase().startsWith("categorynumberset:"))
                    for (Module m : NeutronMain.instance.moduleManager.getModules()) {
                        if (m.getName().equalsIgnoreCase(args[1]))
                            for (Setting setting : m.settings) {
                                if (!(setting instanceof ModuleCategory))
                                    continue;
                                if (setting.name.equalsIgnoreCase(args[2]))
                                    for (Setting setting1 : ((ModuleCategory)setting).settingsOnCat) {
                                        if (setting1.name.equalsIgnoreCase(args[3])) {
                                            DoubleSet setting3 = (DoubleSet)setting1;
                                            setting3.setValue(Double.parseDouble(args[4]));
                                        }
                                    }
                            }
                    }
                if (s.toLowerCase().startsWith("categorybooleanset:"))
                    for (Module m : NeutronMain.instance.moduleManager.getModules()) {
                        if (m.getName().equalsIgnoreCase(args[1]))
                            for (Setting setting : m.settings) {
                                if (!(setting instanceof ModuleCategory))
                                    continue;
                                if (setting.name.equalsIgnoreCase(args[2]))
                                    try {
                                        for (Setting setting1 : ((ModuleCategory)setting).settingsOnCat) {
                                            try {
                                                if (setting1.name.equalsIgnoreCase(args[3])) {
                                                    BooleanSet setting3 = (BooleanSet)setting1;
                                                    setting3.setEnabled(Boolean.parseBoolean(args[4]));
                                                }
                                            } catch (Exception e) {
                                                System.out.println("A: " + setting1.name + " B: " + setting.name + " C: " + m.getName());
                                            }
                                        }
                                    } catch (Exception e) {
                                        System.out.println(setting.name);
                                    }
                            }
                    }
                if (s.toLowerCase().startsWith("categorymodeset:"))
                    for (Module m : NeutronMain.instance.moduleManager.getModules()) {
                        if (m.getName().equalsIgnoreCase(args[1]))
                            for (Setting setting : m.settings) {
                                if (!(setting instanceof ModuleCategory))
                                    continue;
                                if (setting.name.equalsIgnoreCase(args[2]))
                                    for (Setting setting1 : ((ModuleCategory)setting).settingsOnCat) {
                                        if (!(setting1 instanceof ModeSet))
                                            continue;
                                        for (String str : ((ModeSet)setting1).modes) {
                                            if (setting1.name.equalsIgnoreCase(args[3]) &&
                                                    args[4].equalsIgnoreCase(str)) {
                                                ModeSet setting3 = (ModeSet)setting1;
                                                setting3.setSelected(args[4]);
                                            }
                                        }
                                    }
                            }
                    }
                if (s.toLowerCase().startsWith("categorystringset:"))
                    for (Module m : NeutronMain.instance.moduleManager.getModules()) {
                        if (m.getName().equalsIgnoreCase(args[1]))
                            for (Setting setting : m.settings) {
                                if (!(setting instanceof ModuleCategory))
                                    continue;
                                if (setting.name.equalsIgnoreCase(args[2]))
                                    for (Setting setting1 : ((ModuleCategory)setting).settingsOnCat) {
                                        if (setting1.name.equalsIgnoreCase(args[3])) {
                                            StringSet setting3 = (StringSet)setting1;
                                            setting3.setText(args[4]);
                                        }
                                    }
                            }
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
