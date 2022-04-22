package me.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import me.Hime;
import me.altmanager.AltLoginThread;
import me.log.LogUtil;
import me.log.LogUtil.LogType;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.client.Minecraft;


public class Configer {
	

	public File dir;
	public File dataFile;
	public File saveFile;
	public File removeFile;
	public ArrayList<File> configs = new ArrayList<File>();
	public static Configer instance = new Configer();
    private AltLoginThread thread;
	
	public ArrayList<File> getConfigs() {
		return this.configs;
	}
    
	public void Config(String filename) {
		dir = new File(Minecraft.getMinecraft().mcDataDir, "Hime Configs");
		if (!dir.exists()) {
			dir.mkdir();
			Hime.addClientChatMessage("Folder does not exist! Created new folder.");
			LogUtil.instance.log("Folder does not exist! Created new folder.", LogType.ERROR);
			return;
		}
		dataFile = new File(dir, filename + ".txt");
		
		if (!dataFile.exists()) {
			Hime.addClientChatMessage("File does not exist!");
			LogUtil.instance.log("File does not exist!", LogType.ERROR);
			return;
		}
        this.load();
	
	}
	
	public void configSave(String filename) {
		dir = new File(Minecraft.getMinecraft().mcDataDir, "Hime Configs");
		if (!dir.exists()) {
			dir.mkdir();
			Hime.addClientChatMessage("Folder does not exist! Created new folder.");
			LogUtil.instance.log("Folder does not exist! Created new folder.", LogType.ERROR);
			return;
		}
		saveFile = new File(dir, filename + ".txt");
		if (!saveFile.exists()) {
			try {
				saveFile.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}else {
			Hime.addClientChatMessage("File already exists!");
			LogUtil.instance.log("File already exists!", LogType.ERROR);
			return;
		}
		this.configs.add(saveFile);
        this.save();
	
	}
	
	public void configRemove(String filename) {
		dir = new File(Minecraft.getMinecraft().mcDataDir, "Hime Configs");
		if (!dir.exists()) {
			dir.mkdir();
			Hime.addClientChatMessage("Folder does not exist! Created new folder.");
			LogUtil.instance.log("Folder does not exist! Created new folder.", LogType.ERROR);
			return;
		}
		removeFile = new File(dir, filename + ".txt");
		if (removeFile.exists()) {
			try {
				removeFile.delete();
			} catch (Exception e) {e.printStackTrace();}
		}else {
			Hime.addClientChatMessage("File does not exist!");
			LogUtil.instance.log("File does not exist!", LogType.ERROR);
		}
	
	}
	 public void save() {
		    ArrayList<String> toSave = new ArrayList<>();
		    for (Module mod : Hime.instance.moduleManager.getModules())
		      toSave.add("MOD:" + mod.getName() + ":" + mod.isToggled() + ":" + mod.getKey()); 
		    for (Setting set : Hime.instance.settingsManager.getSettings()) {
		      if (set.isCheck())
		        toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValBoolean()); 
		      if (set.isCombo())
		        toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValString()); 
		      if (set.isSlider())
		        toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValDouble());
		      if (set.isHueSlider())
			        toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValDouble()); 
		      if (set.isSaturationSlider())
			        toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValDouble()); 
		      if (set.isBrightSlider())
			        toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValDouble()); 
		    } 
		    try {
		      PrintWriter pw = new PrintWriter(this.saveFile);
		      for (String str : toSave)
		        pw.println(str); 
		      pw.close();
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } 
		  }
	 
	 
	 public void saveJSON() {
		    ArrayList<String> toSave = new ArrayList<>();
		    for (Module mod : Hime.instance.moduleManager.getModules())
		      toSave.add("MOD:" + mod.getName() + ":" + mod.isToggled() + ":" + mod.getKey()); 
		    for (Setting set : Hime.instance.settingsManager.getSettings()) {
		      if (set.isCheck())
		        toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValBoolean()); 
		      if (set.isCombo())
		        toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValString()); 
		      if (set.isSlider())
		        toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValDouble());
		      if (set.isHueSlider())
			        toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValDouble()); 
		      if (set.isSaturationSlider())
			        toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValDouble()); 
		      if (set.isBrightSlider())
			        toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValDouble()); 
		    } 
		    try {
		      PrintWriter pw = new PrintWriter(this.saveFile);
		      for (String str : toSave)
		        pw.println(str); 
		      pw.close();
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } 
		  }


	public void load() {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (String s : lines) {
			String[] args = s.split(":");
			if (s.toLowerCase().startsWith("mod:")) {
				Module m = Hime.instance.moduleManager.getModule(args[1]);
				if (m != null) {
					m.setToggled(Boolean.parseBoolean(args[2]));
					m.setKey(Integer.parseInt(args[3]));
				}
			} else if (s.toLowerCase().startsWith("set:")) {
				Module m = Hime.instance.moduleManager.getModule(args[2]);
				if (m != null) {
					Setting set = Hime.instance.settingsManager.getSettingByName(args[1]);
					if (set != null) {
						if (set.isCheck()) {
							set.setValBoolean(Boolean.parseBoolean(args[3]));
						}
						if (set.isCombo()) {
							set.setValString(args[3]);
						}
						if (set.isSlider()) {
							set.setValDouble(Double.parseDouble(args[3]));
						}
						if (set.isHueSlider()) {
							set.setValDouble(Double.parseDouble(args[3]));
						}
						if (set.isSaturationSlider()) {
							set.setValDouble(Double.parseDouble(args[3]));
						}
						if (set.isBrightSlider()) {
							set.setValDouble(Double.parseDouble(args[3]));
						}
					}
				}
			}
		}
	}
	
}