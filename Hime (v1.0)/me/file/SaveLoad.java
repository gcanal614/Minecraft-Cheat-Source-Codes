package me.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Proxy;
import java.util.ArrayList;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import me.Hime;
import me.altmanager.Alt;
import me.altmanager.AltLoginThread;
import me.altmanager.AltManager;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.client.Minecraft;

public class SaveLoad {

	private File dir;
	private File dir2;
	private File dataFile;
	private File dataFile2;
	private File dataFile3;
	
    private AltLoginThread thread;
	
	public SaveLoad() {
		dir = new File(Minecraft.getMinecraft().mcDataDir, "Hime");
		if (!dir.exists()) {
			dir.mkdir();
		}
		dir2 = new File(Minecraft.getMinecraft().mcDataDir, "Hime Configs");
		if (!dir2.exists()) {
			dir2.mkdir();
		}
		dataFile = new File(dir2, "Default.txt");
		dataFile2 = new File(dir, "accounts.txt");
		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}
		
        //this.load();
		if (!dataFile2.exists()) {
			try {
				dataFile2.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}
		
	
		Hime.instance.config = "Default";
        //this.load();
	
	}
	
	
	
	
	public void save2() {
		ArrayList<String> toSave2 = new ArrayList<String>();
		
		   for (Alt alt2 : AltManager.registry) {
			  // System.out.println("Account");
			   String name = alt2.getUsername();
			   String pass = alt2.getPassword();
			   if(pass == "") {
				   toSave2.add("AC:" + name + ":" + "example");
			   }else {
			   toSave2.add("AC:" + name + ":" + pass);
			   }
		   }
		   try {
				PrintWriter pw = new PrintWriter(this.dataFile2);
			
				for (String str : toSave2) {
					//System.out.println(str);
					pw.println(str);
				}
				pw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	  public void save() {
		  if(!Hime.instance.config.equalsIgnoreCase("Default")) {
			  return;
		  }
		  
		  
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
		      PrintWriter pw = new PrintWriter(this.dataFile);
		      for (String str : toSave)
		        pw.println(str); 
		      pw.close();
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } 
		  }
	  public void load() {
		    ArrayList<String> lines = new ArrayList<>();
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
		        continue;
		      } 
		      if (s.toLowerCase().startsWith("set:")) {
		        Module m = Hime.instance.moduleManager.getModule(args[2]);
		        if (m != null) {
		          Setting set = Hime.instance.settingsManager.getSettingByName(args[1]);
		          if (set != null) {
		            if (set.isCheck())
		              set.setValBoolean(Boolean.parseBoolean(args[3])); 
		            if (set.isCombo())
		              set.setValString(args[3]); 
		            if (set.isSlider())
		              set.setValDouble(Double.parseDouble(args[3])); 
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
	public void load2() {
	
		
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.dataFile2));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<String> lines2 = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
			String line = reader.readLine();
			while (line != null) {
				lines2.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (String s : lines) {
			String[] args = s.split(":");
			if (s.toLowerCase().startsWith("ac:")) {
				
				System.out.println(args[1]);
					//System.out.println(args[2]);
				
						//this.checkAndAddAlt(args[1], "");
					
				
				  if(args[2].equals("example")) {
						
				  AddAltThread login = new AddAltThread(args[1], "");
                  login.start();
				  }else {
					  AddAltThread login = new AddAltThread(args[1], args[2]);
	                  login.start();
				  }


					}
			}
			
	
		
	}

	public void toggleModule(boolean enbaled, String module) {
		//for(module m : Hime.getModules()) {
			//if(m.getName().equalsIgnoreCase(module)) {
			//	m.setToggled(enbaled);
			//}
		//}
	}
	 private final void checkAndAddAlt(String username, String password) throws IOException {
         YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
         YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
         auth.setUsername(username);
         auth.setPassword(password);
         try {
             auth.logIn();
            // AltManager altManager = Hexa.theClient.altManager;
             AltManager.registry.add(new Alt(username, password, auth.getSelectedProfile().getName()));
         
             //GuiAddAlt.access$0(GuiAddAlt.this, "Alt added. (" + username + ")");
         }
         catch (AuthenticationException e) {
           //  GuiAddAlt.access$0(GuiAddAlt.this, (Object)((Object)EnumChatFormatting.RED) + "Alt failed!");
             e.printStackTrace();
         }
     }
	 private class AddAltThread
	    extends Thread {
	        private String password;
	        private final String username;

	        public AddAltThread(String username, String password) {
	            this.username = username;
	            this.password = password;
//	          /  GuiAddAlt.access$0(GuiAddAlt.this, (Object)((Object)EnumChatFormatting.GRAY) + "Idle...");
	        }

	        private final void checkAndAddAlt(String username, String password) throws IOException {
	            YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
	            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
	            auth.setUsername(username);
	            auth.setPassword(password);
	            try {
	                auth.logIn();
	               // AltManager altManager = Hexa.theClient.altManager;
	                AltManager.registry.add(new Alt(username, password, auth.getSelectedProfile().getName()));
	               
	             //   GuiAddAlt.access$0(GuiAddAlt.this, "Alt added. (" + username + ")");
	            }
	            catch (AuthenticationException e) {
	              //  GuiAddAlt.access$0(GuiAddAlt.this, (Object)((Object)EnumChatFormatting.RED) + "Alt failed!");
	                e.printStackTrace();
	            }
	        }

	        @Override
	        public void run() {
	            if (this.password.equals("")) {
	            	//this.password = "example";
	                //AltManager altManager = Hexa.theClient.altManager;
	                AltManager.registry.add(new Alt(this.username, ""));
	               
	            //    GuiAddAlt.access$0(GuiAddAlt.this, (Object)((Object)EnumChatFormatting.GREEN) + "Alt added. (" + this.username + " - offline name)");
	                return;
	            }
	            //GuiAddAlt.access$0(GuiAddAlt.this, (Object)((Object)EnumChatFormatting.YELLOW) + "Trying alt...");
	            try {
	                this.checkAndAddAlt(this.username, this.password);
	              
	            }
	            catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	
}