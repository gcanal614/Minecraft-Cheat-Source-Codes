package me.script;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import me.Hime;
import org.json.simple.parser.ParseException;

import me.altmanager.AltLoginThread;
import me.log.LogUtil;
import me.log.LogUtil.LogType;
import me.util.MovementUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.ChatComponentText;


public class ScriptManager {

	    //private ArrayList<Script> realScripts = new ArrayList();
	    
	    public String s;
	    public String args1;
	    public boolean enabled = false;
	
		public File dir;
		public File bindDir;
		public File dataFile;
		public File loadBindFile;
		public File saveFile;
		public File bindFile;
		public File removeFile;
		public File removeFile2;
		public ArrayList<File> scripts = new ArrayList<File>();
		
		public ArrayList<String> enabledScripts = new ArrayList<String>();
		public ArrayList<Integer> scriptKeycodes = new ArrayList<Integer>();
		
		
		protected static Minecraft mc = Minecraft.getMinecraft();
		
		public static ScriptManager instance = new ScriptManager();
	    private AltLoginThread thread;
		
	    
	    public ScriptManager() {
	     dir = new File(Minecraft.getMinecraft().mcDataDir, "Hime Scripts");
	      if(dir.exists()) {
	    	for(File file : dir.listFiles()) {
	    		//scripts.add(file);
	    		this.enabledScripts.add("false");
	    		this.scriptKeycodes.add(0);
	    	}
	      }
	    }
	    
		public ArrayList<File> getScripts() {
			return this.scripts;
		}
	    
		public void enableScript(String filename) throws IOException, ParseException {
			dir = new File(Minecraft.getMinecraft().mcDataDir, "Hime Scripts");
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
			int count = 0;
			for(File file : dir.listFiles()) {
				String realName = file.getName().replace(".txt", "");
				if(realName.equals(filename)) {
					enabledScripts.set(count, "true");
					this.scriptEnable(file);
				}
				count++;
			}
	        //this.loadScript();
		}
		
		public void disableScript(String filename) throws IOException, ParseException {
			dir = new File(Minecraft.getMinecraft().mcDataDir, "Hime Scripts");
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
		//	this.enabled = false;
			
			int count = 0;
			for(File file : dir.listFiles()) {
				String realName = file.getName().replace(".txt", "");
				if(realName.equals(filename)) {
					enabledScripts.set(count, "false");
					this.scriptDisable(file);
				}
				count++;
			}
		}
		
		/*public void configSave(String filename) {
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
			this.scripts.add(saveFile);
	        this.saveConfig();
		}*/
		
		public void removeScript(String filename) {
			dir = new File(Minecraft.getMinecraft().mcDataDir, "Hime Scripts");
			if (!dir.exists()) {
				dir.mkdir();
				Hime.addClientChatMessage("Folder does not exist! Created new folder.");
				LogUtil.instance.log("Folder does not exist! Created new folder.", LogType.ERROR);
				return;
			}
			removeFile2 = new File(dir, filename + ".txt");
			if (removeFile2.exists()) {
				try {
					removeFile2.delete();
				} catch (Exception e) {e.printStackTrace();}
			}else {
				Hime.addClientChatMessage("File does not exist!");
				LogUtil.instance.log("File does not exist!", LogType.ERROR);
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
		 
		  
		    
		public void loadScript() {
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
				this.s = s;
				this.args1 = args[1];
				this.enabled = true;
			}
		}
		
		public void scriptEnable(File file) {
			ArrayList<String> lines = new ArrayList<String>();
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
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
				if (s.toLowerCase().startsWith("onenable:")) {
					 if (args[1].equalsIgnoreCase("timer")) {
						mc.timer.timerSpeed = Float.parseFloat(args[2]);
					}else if (args[1].equalsIgnoreCase("speed:")) {
						MovementUtils.actualSetSpeed(Float.parseFloat(args[2]));
					}else if (args[1].equalsIgnoreCase("speedinair:")) {
						mc.thePlayer.speedInAir = Float.parseFloat(args[2]);
					}else if (args[1].equalsIgnoreCase("jumpmovementfactor:")) {
						mc.thePlayer.jumpMovementFactor = Float.parseFloat(args[2]);
					}else if (args[1].equalsIgnoreCase("ground:")) {
						mc.thePlayer.onGround = Boolean.getBoolean(args[2]);
					}else if (args[1].equalsIgnoreCase("flying:")) {
						mc.thePlayer.capabilities.isFlying = Boolean.getBoolean(args[2]);
					}else if (args[1].equalsIgnoreCase("strafe:")) {
						  if(Boolean.getBoolean(args[2])) {
								MovementUtils.doStrafe();
							  }
					}else if (args[1].equalsIgnoreCase("printchat:")) {
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(args[2]));
					}else if (args[1].equalsIgnoreCase("printconsole:")) {
						System.out.println(args[2]);
					}
				}
				}
		}
		
		public void scriptDisable(File file) {
			ArrayList<String> lines = new ArrayList<String>();
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
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
				if (s.toLowerCase().startsWith("ondisable:")) {
					 if (args[1].equalsIgnoreCase("timer")) {
						mc.timer.timerSpeed = Float.parseFloat(args[2]);
					}else if (args[1].equalsIgnoreCase("speed:")) {
						MovementUtils.actualSetSpeed(Float.parseFloat(args[2]));
					}else if (args[1].equalsIgnoreCase("speedinair:")) {
						mc.thePlayer.speedInAir = Float.parseFloat(args[2]);
					}else if (args[1].equalsIgnoreCase("jumpmovementfactor:")) {
						mc.thePlayer.jumpMovementFactor = Float.parseFloat(args[2]);
					}else if (args[1].equalsIgnoreCase("ground:")) {
						mc.thePlayer.onGround = Boolean.getBoolean(args[2]);
					}else if (args[1].equalsIgnoreCase("flying:")) {
						mc.thePlayer.capabilities.isFlying = Boolean.getBoolean(args[2]);
					}else if (args[1].equalsIgnoreCase("strafe:")) {
						  if(Boolean.getBoolean(args[2])) {
								MovementUtils.doStrafe();
							  }
					}else if (args[1].equalsIgnoreCase("printchat:")) {
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(args[2]));
					}else if (args[1].equalsIgnoreCase("printconsole:")) {
						System.out.println(args[2]);
					}
				}
				}
		}
		
		public void onUpdateScript() {
		//  if(this.enabled) {
			int count = 0;
			for(File file : dir.listFiles()) {
			  if(this.enabledScripts.get(count).equalsIgnoreCase("true")) {
				String realName = file.getName().replace(".txt", "");
				ArrayList<String> lines = new ArrayList<String>();
				try {
					BufferedReader reader = new BufferedReader(new FileReader(file)); //this.dataFile
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
			if (s.toLowerCase().startsWith("name:")) {
				
			} else if (s.toLowerCase().startsWith("jump:")) {
				if(mc.thePlayer.onGround && mc.thePlayer.isMoving() && args[1].equalsIgnoreCase("true")) {
			           mc.thePlayer.jump();
		        }
			}else if (s.toLowerCase().startsWith("timer:")) {
				mc.timer.timerSpeed = Float.parseFloat(args[1]);
			}else if (s.toLowerCase().startsWith("speed:")) {
				MovementUtils.actualSetSpeed(Float.parseFloat(args[1]));
			}else if (s.toLowerCase().startsWith("speedinair:")) {
				mc.thePlayer.speedInAir = Float.parseFloat(args[1]);
			}else if (s.toLowerCase().startsWith("jumpmovementfactor:")) {
				mc.thePlayer.jumpMovementFactor = Float.parseFloat(args[1]);
			}else if (s.toLowerCase().startsWith("motiony:")) {
				mc.thePlayer.motionY = Double.valueOf(args[1]);
			}else if (s.toLowerCase().startsWith("ground:")) {
				mc.thePlayer.onGround = Boolean.getBoolean(args[1]);
			}else if (s.toLowerCase().startsWith("strafe:")) {
			  if(Boolean.getBoolean(args[1])) {
				MovementUtils.doStrafe();
			  }
			}else if (s.toLowerCase().startsWith("packet:")) {
				if(args[1].equalsIgnoreCase("c03packetplayer")) {
					mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
				}
			}else if (s.toLowerCase().startsWith("flying:")) {
				mc.thePlayer.capabilities.isFlying = Boolean.getBoolean(args[1]);
			}else if (s.toLowerCase().startsWith("printchat:")) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(args[1]));
			}else if (s.toLowerCase().startsWith("printconsole:")) {
				System.out.println(args[1]);
			}
		  }
		}
		  count++;
		  }
		}
}
