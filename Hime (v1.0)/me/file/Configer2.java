package me.file;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import me.Hime;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import me.altmanager.AltLoginThread;
import me.log.LogUtil;
import me.log.LogUtil.LogType;
import me.module.Module;
import net.minecraft.client.Minecraft;


public class Configer2 {
	

	public File dir;
	public File bindDir;
	public File dataFile;
	public File loadBindFile;
	public File saveFile;
	public File bindFile;
	public File removeFile;
	public File removeFile2;
	public ArrayList<File> configs = new ArrayList<File>();
	public ArrayList<File> binds = new ArrayList<File>();
	public static Configer2 instance = new Configer2();
    private AltLoginThread thread;
	
	public ArrayList<File> getConfigs() {
		return this.configs;
	}
    
	public void Config(String filename) throws IOException, ParseException {
		dir = new File(Minecraft.getMinecraft().mcDataDir, "Hime Configs");
		if (!dir.exists()) {
			dir.mkdir();
			Hime.addClientChatMessage("Folder does not exist! Created new folder.");
			LogUtil.instance.log("Folder does not exist! Created new folder.", LogType.ERROR);
			return;
		}
		dataFile = new File(dir, filename + ".json");
		
		if (!dataFile.exists()) {
			Hime.addClientChatMessage("File does not exist!");
			LogUtil.instance.log("File does not exist!", LogType.ERROR);
			return;
		}
        this.loadConfig();
	}
	
	public void loadBinds(String filename) throws IOException, ParseException {
		bindDir = new File(Minecraft.getMinecraft().mcDataDir, "Hime Binds");
		if (!bindDir.exists()) {
			bindDir.mkdir();
			Hime.addClientChatMessage("Folder does not exist! Created new folder.");
			LogUtil.instance.log("Folder does not exist! Created new folder.", LogType.ERROR);
			return;
		}
		loadBindFile = new File(bindDir, filename + ".json");
		
		if (!loadBindFile.exists()) {
			Hime.addClientChatMessage("File does not exist!");
			LogUtil.instance.log("File does not exist!", LogType.ERROR);
			return;
		}
        this.loadBinds();
	}
	
	
	public void configSave(String filename) {
		dir = new File(Minecraft.getMinecraft().mcDataDir, "Hime Configs");
		if (!dir.exists()) {
			dir.mkdir();
			Hime.addClientChatMessage("Folder does not exist! Created new folder.");
			LogUtil.instance.log("Folder does not exist! Created new folder.", LogType.ERROR);
			return;
		}
		saveFile = new File(dir, filename + ".json");
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
        this.saveConfig();
	}
	
	public void bindsSave(String filename) {
		bindDir = new File(Minecraft.getMinecraft().mcDataDir, "Hime Binds");
		if (!bindDir.exists()) {
			bindDir.mkdir();
			Hime.addClientChatMessage("Folder does not exist! Created new folder.");
			LogUtil.instance.log("Folder does not exist! Created new folder.", LogType.ERROR);
			return;
		}
		bindFile = new File(bindDir, filename + ".json");
		if (!bindFile.exists()) {
			try {
				bindFile.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}else {
			Hime.addClientChatMessage("File already exists!");
			LogUtil.instance.log("File already exists!", LogType.ERROR);
			return;
		}
		this.binds.add(bindFile);
        this.saveBinds();
	
	}
	
	public void configRemove(String filename) {
		dir = new File(Minecraft.getMinecraft().mcDataDir, "Hime Configs");
		if (!dir.exists()) {
			dir.mkdir();
			Hime.addClientChatMessage("Folder does not exist! Created new folder.");
			LogUtil.instance.log("Folder does not exist! Created new folder.", LogType.ERROR);
			return;
		}
		removeFile2 = new File(dir, filename + ".json");
		if (removeFile2.exists()) {
			try {
				removeFile2.delete();
			} catch (Exception e) {e.printStackTrace();}
		}else {
			Hime.addClientChatMessage("File does not exist!");
			LogUtil.instance.log("File does not exist!", LogType.ERROR);
		}
	
	}
	
	public void bindRemove(String filename) {
		bindDir = new File(Minecraft.getMinecraft().mcDataDir, "Hime Binds");
		if (!bindDir.exists()) {
			bindDir.mkdir();
			Hime.addClientChatMessage("Folder does not exist! Created new folder.");
			LogUtil.instance.log("Folder does not exist! Created new folder.", LogType.ERROR);
			return;
		}
		removeFile2 = new File(bindDir, filename + ".json");
		if (removeFile2.exists()) {
			try {
				removeFile2.delete();
			} catch (Exception e) {e.printStackTrace();}
		}else {
			Hime.addClientChatMessage("File does not exist!");
			LogUtil.instance.log("File does not exist!", LogType.ERROR);
		}
	
	}
	
	  public void saveConfig() {
	        JSONObject jo = new JSONObject();

	        for (Module mod : Hime.instance.moduleManager.getModules()) {
	           JSONObject joToggled = new JSONObject();
	            if (Hime.instance.settingsManager.getSettingsByMod(mod) != null) {
	                for (int i = 0; i < Hime.instance.settingsManager.getSettingsByMod(mod).size(); i++) {
	                    String settingName = Hime.instance.settingsManager.getSettingsByMod(mod).get(i).getName();
	                    if (Hime.instance.settingsManager.getSettingsByMod(mod).get(i).isCombo()) {
	                        joToggled.put(settingName, Hime.instance.settingsManager.getSettingsByMod(mod).get(i).getValString());
	                    } else if (Hime.instance.settingsManager.getSettingsByMod(mod).get(i).isCheck()) {
	                        joToggled.put(settingName, Hime.instance.settingsManager.getSettingsByMod(mod).get(i).getValBoolean());
	                    } else if(Hime.instance.settingsManager.getSettingsByMod(mod).get(i).isSlider()){
	                        joToggled.put(settingName, Hime.instance.settingsManager.getSettingsByMod(mod).get(i).getValDouble());
	                    } else if(Hime.instance.settingsManager.getSettingsByMod(mod).get(i).isBrightSlider()){
	                        joToggled.put(settingName, Hime.instance.settingsManager.getSettingsByMod(mod).get(i).getValDouble());
	                    } else if(Hime.instance.settingsManager.getSettingsByMod(mod).get(i).isSaturationSlider()){
	                        joToggled.put(settingName, Hime.instance.settingsManager.getSettingsByMod(mod).get(i).getValDouble());
	                    } else if(Hime.instance.settingsManager.getSettingsByMod(mod).get(i).isHueSlider()){
	                        joToggled.put(settingName, Hime.instance.settingsManager.getSettingsByMod(mod).get(i).getValDouble());
	                    }

	                }
	            }
	           // joToggled.put("keybind", (int) mod.getKey());
	            joToggled.put("toggled", (Boolean) mod.isToggled());
	            jo.put(mod.getName(), joToggled);

	        }

	        try (FileWriter file = new FileWriter(saveFile, false)) {
	            file.write(jo.toString());
	            file.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  
	  public void saveBinds() {
	        JSONObject jo = new JSONObject();

	        for (Module mod : Hime.instance.moduleManager.getModules()) {
	           JSONObject joToggled = new JSONObject();
	            joToggled.put("keybind", (int) mod.getKey());
	            jo.put(mod.getName(), joToggled);
	        }
	        try (FileWriter file = new FileWriter(bindFile, false)) {
	            file.write(jo.toString());
	            file.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	 /*public void save() {
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
		  }*/
	 
	    public void loadConfig() throws IOException, ParseException {
	        JSONObject jo = (JSONObject) (new JSONParser().parse(new FileReader(dataFile)));
	        for (Module mod : Hime.instance.moduleManager.getModules()) {
	           JSONObject joModule = (JSONObject) jo.get(mod.getName());
	            if (jo.containsKey(mod.getName())) {
	                mod.setToggled((Boolean) joModule.get("toggled"));
	               // mod.setKey(Integer.parseInt(joModule.get("keybind").toString()));
	                if (Hime.instance.settingsManager.getSettingsByMod(mod) != null) {
	                    for (int i = 0; i < Hime.instance.settingsManager.getSettingsByMod(mod).size(); i++) {
	                        String settingName = Hime.instance.settingsManager.getSettingsByMod(mod).get(i).getName();
	                       try {
	                        if (Hime.instance.settingsManager.getSettingsByMod(mod).get(i).isCombo()) {
	                            Hime.instance.settingsManager.getSettingByName(mod, settingName).setValString(joModule.get(settingName).toString());
	                        } else if (Hime.instance.settingsManager.getSettingsByMod(mod).get(i).isCheck()) {
	                            Hime.instance.settingsManager.getSettingByName(mod, settingName).setValBoolean((Boolean) joModule.get(settingName));
	                        } else if (Hime.instance.settingsManager.getSettingsByMod(mod).get(i).isSlider()) {
	                            Hime.instance.settingsManager.getSettingByName(mod, settingName).setValDouble(Double.parseDouble(joModule.get(settingName).toString()));
	                        } else if (Hime.instance.settingsManager.getSettingsByMod(mod).get(i).isHueSlider()) {
	                            Hime.instance.settingsManager.getSettingByName(mod, settingName).setValDouble(Double.parseDouble(joModule.get(settingName).toString()));
	                        } else if (Hime.instance.settingsManager.getSettingsByMod(mod).get(i).isBrightSlider()) {
	                            Hime.instance.settingsManager.getSettingByName(mod, settingName).setValDouble(Double.parseDouble(joModule.get(settingName).toString()));
	                        } else if (Hime.instance.settingsManager.getSettingsByMod(mod).get(i).isSaturationSlider()) {
	                            Hime.instance.settingsManager.getSettingByName(mod, settingName).setValDouble(Double.parseDouble(joModule.get(settingName).toString()));
	                        }
	                    }catch(Exception e) {
	                    	e.printStackTrace();
	                    	LogUtil.instance.log("Something went wrong while loading config!", LogUtil.LogType.ERROR);
	                    }
	                    }
	                }
	            }
	        }
	    }
	    
	    public void loadBinds() throws IOException, ParseException {
	        JSONObject jo = (JSONObject) (new JSONParser().parse(new FileReader(dataFile)));
	        for (Module mod : Hime.instance.moduleManager.getModules()) {
	           JSONObject joModule = (JSONObject) jo.get(mod.getName());
	            if (jo.containsKey(mod.getName())) {
	              try {
	               mod.setKey(Integer.parseInt(joModule.get("keybind").toString()));
	              }catch(Exception e) {
	            	  e.printStackTrace();
	            	  LogUtil.instance.log("Something went wrong while loading binds!", LogUtil.LogType.ERROR);
	              }
	            }
	        }
	    }

	/*public void load() {
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
					Setting set = Hime.instance.settingsManager.getSettingByName(m, args[1]);
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
	}*/
	
}