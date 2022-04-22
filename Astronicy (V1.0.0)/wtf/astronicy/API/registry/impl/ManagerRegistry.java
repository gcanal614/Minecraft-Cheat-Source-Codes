package wtf.astronicy.API.registry.impl;

import wtf.astronicy.API.config.ConfigManager;
import wtf.astronicy.API.file.FileManager;
import wtf.astronicy.API.friend.FriendManager;
import wtf.astronicy.API.registry.Registry;
import wtf.astronicy.IMPL.command.CommandManager;
import wtf.astronicy.IMPL.module.ModuleManager;

public final class ManagerRegistry implements Registry {
   public final ConfigManager configManager = new ConfigManager();
   public final FileManager fileManager = new FileManager();
   public final FriendManager friendManager = new FriendManager();
   public final ModuleManager moduleManager = new ModuleManager();
   public final CommandManager commandManager = new CommandManager();
}
