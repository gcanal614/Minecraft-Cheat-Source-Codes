package non.asset.command.impl;


import java.io.IOException;

import non.asset.Clarinet;
import non.asset.command.Command;
import non.asset.config.Config;
import non.asset.utils.OFC.Printer;

public class ConfigCMD extends Command {

    public ConfigCMD() {
        super("Config", new String[]{"c", "config", "configs"});
    }

    @Override
    public void onRun(final String[] s) {
    	if (s.length == 1) {
            Printer.print("config create/save (configname) (you can save with keybinds) - saves a config.");
            Printer.print("config override (configname) (you can override with keybinds) - overrides existing configs.");
            Printer.print("config delete/remove (configname) - removes a config.");
    		return;
    	}
        switch (s[1]) {
            case "list":
                if (!Clarinet.INSTANCE.getConfigManager().getConfigs().isEmpty()) {
                    Printer.print("Current Configs:");
                    Clarinet.INSTANCE.getConfigManager().getConfigs().forEach(cfg -> {
                        Printer.print(cfg.getName());
                    });
                } else {
                    Printer.print("You have no saved configs!");
                }
                break;
            case "help":
                Printer.print("config list - shows all configs.");
                Printer.print("config create/save configname ((keys) if you want to save with keybinds) - saves a config.");
                Printer.print("config override configname ((keys) if you want to override with keybinds) - overrides existing configs.");
                Printer.print("config delete/remove configname - removes a config.");
                break;
            case "create":
            case "save":
                if (!Clarinet.INSTANCE.getConfigManager().isConfig(s[2])) {
                    Clarinet.INSTANCE.getConfigManager().saveConfig(s[2], s.length > 3 && s[3].equalsIgnoreCase("keys"));
                    Clarinet.INSTANCE.getConfigManager().getConfigs().add(new Config(s[2]));
                    Printer.print("Created a config named " + s[2] + (s.length > 3 && s[3].equalsIgnoreCase("keys") ? " with keys included" : "") + "!");
                    Clarinet.INSTANCE.getNotificationManager().addNotification("Created a config named " + s[2] + (s.length > 3 && s[3].equalsIgnoreCase("keys") ? " with keys included" : "") + "!",2000);
                } else {
                    Printer.print(s[2] + " is already a saved config!");
                    Clarinet.INSTANCE.getNotificationManager().addNotification( s[2] + " is already a saved config!",2000);
                }
                break;
            case "delete":
            case "remove":
                if (Clarinet.INSTANCE.getConfigManager().isConfig(s[2])) {
                    Clarinet.INSTANCE.getConfigManager().deleteConfig(s[2]);
                    Printer.print("Deleted the config named " + s[2] + "!");
                    Clarinet.INSTANCE.getNotificationManager().addNotification( "Deleted the config named " + s[2] + "!",2000);
                } else {
                    Printer.print(s[2] + " is not a saved config!");
                    Clarinet.INSTANCE.getNotificationManager().addNotification( s[2] + " is not a saved config!",2000);
                }
                break;
            case "reload":
                Clarinet.INSTANCE.getConfigManager().getConfigs().clear();
                Clarinet.INSTANCE.getConfigManager().load();
                Printer.print("Reloaded all saved configs. Current number of configs: " + Clarinet.INSTANCE.getConfigManager().getConfigs().size() + "!");
                Clarinet.INSTANCE.getNotificationManager().addNotification( "Reloaded all saved configs. Current number of configs: " + Clarinet.INSTANCE.getConfigManager().getConfigs().size() + "!",2000);
                break;
            case "clear":
                try {
                    if (!Clarinet.INSTANCE.getConfigManager().getConfigs().isEmpty()) {
                        Clarinet.INSTANCE.getConfigManager().clear();
                        Clarinet.INSTANCE.getConfigManager().getConfigs().clear();
                        Printer.print("Cleared all saved configs!");
                        Clarinet.INSTANCE.getNotificationManager().addNotification( "Cleared all saved configs!",2000);
                    } else {
                        Printer.print("You have no saved configs!");
                        Clarinet.INSTANCE.getNotificationManager().addNotification( "You have no saved configs!",2000);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "override":
                if (Clarinet.INSTANCE.getConfigManager().isConfig(s[2])) {
                    Clarinet.INSTANCE.getConfigManager().saveConfig(s[2], s.length > 3 && s[3].equalsIgnoreCase("keys"));
                    Printer.print("Replaced the config named " + s[2] + (s.length > 3 && s[3].equalsIgnoreCase("keys") ? " with keys included" : "") + "!");
                    Clarinet.INSTANCE.getNotificationManager().addNotification( "Replaced the config named " + s[2] + (s.length > 3 && s[3].equalsIgnoreCase("keys") ? " with keys included" : "") + "!",2000);
                } else {
                    Printer.print(s[2] + " is not a saved config!");
                    Clarinet.INSTANCE.getNotificationManager().addNotification( s[2] + " is not a saved config!",2000);
                }
                break;
            case "load":
                if (Clarinet.INSTANCE.getConfigManager().isConfig(s[2])) {
                    Clarinet.INSTANCE.getConfigManager().loadConfig(s[2]);
                    Printer.print("Loaded the config named " + s[2] + "!");
                    Clarinet.INSTANCE.getNotificationManager().addNotification( "Loaded the config named " + s[2] + "!",2000);
                } else {
                    Printer.print(s[2] + " is not a saved config!");
                    Clarinet.INSTANCE.getNotificationManager().addNotification( s[2] + " is not a saved config!",2000);
                }
                break;
        }
    }
}
