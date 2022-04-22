package me.ui.config;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import me.Hime;
import org.apache.commons.lang3.RandomUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.file.Configer2;
import me.log.LogUtil;
import me.log.LogUtil.LogType;
import me.module.Module;
import me.settings.Setting;
import me.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ConfigSystem extends GuiScreen{
    
	public int offset;
	 public static Scanner scanner;

	 CustomTextField textField;
	 CustomTextField renameField;
	 CustomTextField urlField;
     public File dir;
     public File duplicateFile;
     public File rename;
     public boolean save =  false;
     public boolean settings =  false;
     public boolean renameConfig =  false;
     public String selectedConfig = "";
     public File selectedConfig2 = null;
     
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		 if (Mouse.hasWheel()) {
	            int wheel = Mouse.getDWheel();
	            if (wheel < 0) {
	                this.offset += 6;
	                if (this.offset < 0) {
	                    this.offset = 0;
	                }
	                if (this.offset > 70) {
	                    this.offset = 70;
	                }
	            } else if (wheel > 0) {
	                this.offset -= 6;
	                if (this.offset < 0) {
	                    this.offset = 0;
	                }
	                if (this.offset > 70) {
	                    this.offset = 70;
	                }
	            }
	        }
		 
		  
		 
		 float x2 = 260;
         float y2 = 32;
		 boolean hovered = mouseX >= x2 && mouseY >= y2 && mouseX < x2 + mc.fontRendererObj.getStringWidth("New") && mouseY < y2 + mc.fontRendererObj.FONT_HEIGHT;
		
			
			RenderUtil.drawRoundedRect(240, 25, 560, 300, 5, Color.DARK_GRAY);
			RenderUtil.drawRoundedRect(240, 25, 560, 45,  5, Color.BLACK);
			//RenderUtil.drawRoundedRect(450, 20, 560, 300, 5, Color.BLACK);
			//RenderUtil.drawRoundedRect(555, 58+   offset * 3, 555, 85+ offset * 3, 1, Color.GRAY);
			if(!this.save) {
			Hime.instance.cfrs.drawString("New", 260, 32, hovered ? Color.GRAY.darker().getRGB() : -1);
			}else if(this.save) {
		    Hime.instance.cfrs.drawString("Back", 260, 32, hovered ? Color.GRAY.darker().getRGB() : -1);
			}
		//	  RenderUtil.drawRoundedRect(22, 22, 100, 278, 0xFF111111);  
			int count = 0;
			if (this.settings) {				
        		if(this.isMouseOnConfig(mouseX, 460, mouseY, 50, "Delete")) {
          		 Hime.instance.cfrs.drawString("Delete", 460, 50, Color.GRAY.darker().getRGB());
          		}else {
          	    Hime.instance.cfrs.drawString("Delete", 460, 50, -1);
          		}
        		if(this.isMouseOnConfig(mouseX, 500, mouseY, 50, "Duplicate")) {
       		     Hime.instance.cfrs.drawString("Duplicate", 500, 50, Color.GRAY.darker().getRGB());
       		    }else {
       			 Hime.instance.cfrs.drawString("Duplicate", 500, 50, -1);
       		    }
        		if(this.isMouseOnConfig(mouseX, 500, mouseY, 65, "Back")) {
        		     Hime.instance.cfrs.drawString("Back", 500, 65, Color.GRAY.darker().getRGB());
        		}else {
        			 Hime.instance.cfrs.drawString("Back", 500, 65, -1);
        		}
        		if(this.isMouseOnConfig(mouseX, 460, mouseY, 65, "Save")) {
       		     Hime.instance.cfrs.drawString("Save", 460, 65, Color.GRAY.darker().getRGB());
       		    }else {
       			 Hime.instance.cfrs.drawString("Save", 460, 65, -1);
       		    }
        		if(this.isMouseOnConfig(mouseX, 460, mouseY, 80, "Rename")) {
          		     Hime.instance.cfrs.drawString("Rename", 460, 80, Color.GRAY.darker().getRGB());
          		}else {
          			 Hime.instance.cfrs.drawString("Rename", 460, 80, -1);
          		}
         }
			GL11.glPushMatrix();
		    this.prepareScissorBox(0.0f, 33.0f, width, height - 3);
		    GL11.glEnable(3089);
			for(File file : dir.listFiles()) {
				   float x = 260;
			         float y = 60 + count * 17- this.offset;
		             String filename = file.getName().replace(".json", "");
			boolean hoverd = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(filename) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT;
			   //RenderUtil1.drawRoundedRect(250, 58+ (count * 17) - offset, 430, 73+ (count * 17)- offset, 2, Color.DARK_GRAY.darker());  	
			Hime.instance.cfrs.drawString(file.getName().replace(".json", ""), 260, 60 + count * 17- this.offset, hoverd ? Color.GRAY.darker().getRGB() : -1);
		    
			
				count++;
			}
			 GL11.glDisable(3089);
			 GL11.glPopMatrix();
			if(this.renameConfig) {
				this.renameField.draw();
				 float x = 460;
		         float y = 120;
				 boolean hovered2 = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth("Save") && mouseY < y + mc.fontRendererObj.FONT_HEIGHT;
				Hime.instance.cfrs.drawString("Save", 460, 120, hovered2 ? Color.GRAY.darker().getRGB() : -1);
			}
			if(this.save) {
				 float x = 300;
		         float y = 32;
				 boolean hovered2 = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth("Save") && mouseY < y + mc.fontRendererObj.FONT_HEIGHT;
			 textField.draw();
			 urlField.draw();
				Hime.instance.cfrs.drawString("Save", 300, 32, hovered2 ? Color.GRAY.darker().getRGB() : -1);
				
				if(this.isMouseOnConfig(mouseX, 414, mouseY, 32, "Load")) {
         		     Hime.instance.cfrs.drawString("Load", 414, 32, Color.GRAY.darker().getRGB());
         		}else {
         			 Hime.instance.cfrs.drawString("Load", 414, 32, -1);
         		}
				
				
			}
	}
	 public void prepareScissorBox(float x2, float y2, float x22, float y22) {
	        ScaledResolution scale = new ScaledResolution(this.mc);
	        int factor = scale.getScaleFactor();

	        GL11.glScissor((int)(x2 * (float)factor), (int)(((float)scale.getScaledHeight() - y22) * (float)factor) + 190, (int)((x22 - x2) * (float)factor), (int)((y22 - y2) * (float)factor) - 235);

	 }
	@Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        textField.keyTyped(typedChar, keyCode);      
        urlField.keyTyped(typedChar, keyCode);      
           renameField.keyTyped(typedChar, keyCode);
        
        super.keyTyped(typedChar, keyCode);
    }
	
	 public void switchSave() {
	     save  = !save;
	 }
	 
	 public void switchSettings() {
	     settings  = !settings;
	 }
	 
	 public void switchRename() {
		 renameConfig  = !renameConfig;
	 }
	
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
	    if(textField != null) {
            textField.mouseClicked(mouseX, mouseY);
        }
	    if(urlField != null) {
	    	urlField.mouseClicked(mouseX, mouseY);
        }
	    if(this.renameField != null) {
	    	renameField.mouseClicked(mouseX, mouseY);
        }
	    
		if(button == 0) {
			if(this.settings) {
			if(this.isMouseOnConfig(mouseX, 500, mouseY, 65, "Back")) {
   		       this.settings = false;
   		    this.renameConfig = false;
   		   }
			if(this.isMouseOnConfig(mouseX, 460, mouseY, 50, "Delete")) {
	   		      Configer2.instance.configRemove(this.selectedConfig.replace(".json", ""));
	   		   }
			if(this.isMouseOnConfig(mouseX, 500, mouseY, 50, "Duplicate")) {
	   		      this.duplicate(this.selectedConfig.replace(".json", "") + " - Duplicate " + " " + RandomUtils.nextLong(4444L, 100000000L), this.selectedConfig2);
	   		   }
			if(this.isMouseOnConfig(mouseX, 460, mouseY, 65, "Save")) {
      		     this.save(selectedConfig2);
      		}
			if(this.isMouseOnConfig(mouseX, 460, mouseY, 80, "Rename")) {
				this.renameConfig = true;
     		  // this.rename(this.renameField.getText(), this.selectedConfig2);
     		}
			if(this.isMouseOnConfig(mouseX, 460, mouseY, 120, "Save") && this.renameConfig) {
				//this.renameConfig = true;
     		   this.rename(this.renameField.getText(), this.selectedConfig2);
     		}
	    }
			if(this.save) {
				if(this.isMouseOnConfig(mouseX, 414, mouseY, 32, "Load")) {
				  this.onlineConfig(this.urlField.getText());
	     		}
			}
			int count = 0;
			for(File file : dir.listFiles()) {
			
		    float x = 260;
	         float y = 60 + count * 17- this.offset;
             String filename = file.getName().replace(".json", "");
	         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(filename) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			    	  try {
						Configer2.instance.Config(filename);
					} catch (IOException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                  Hime.instance.config = filename;
	         }
	         count++;
			}
			 float x = 260;
	         float y = 32;
	            
		         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth("New") && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
		          // Configer.instance.configSave(this.textField.getText());
		              this.switchSave();
		              this.textField.setText("");
		              this.renameField.setText("");
		              this.urlField.setText("");
		         }
		         float x2 = 300;
		         float y2 = 32;
		         if(this.save) {
			     if(mouseX >= x2 && mouseY >= y2 && mouseX < x2 + mc.fontRendererObj.getStringWidth("Save") && mouseY < y2 + mc.fontRendererObj.FONT_HEIGHT) {
			    //     System.out.println(textField.getText());
			    	 Configer2.instance.configSave(this.textField.getText());
			             // this.switchSave();
			     }
		     }
		}else if(button == 1) {
			int count = 0;
			for(File file : dir.listFiles()) {
				
			    float x = 260;
		         float y = 60 + count * 17- this.offset;
	             String filename = file.getName().replace(".json", "");
		         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(filename) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
				  this.renameConfig = false;
		        	 this.selectedConfig2 = file;
		        	 this.selectedConfig = file.getName();	 
                 this.settings = true;
		         }
		         count++;
				}
		}
	}
	public boolean isMouseOnConfig(int mouseX, int x, int mouseY, int y, String text) {
		return mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(text) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT;
	}
	@Override
	public void initGui() {
	dir = new File(Minecraft.getMinecraft().mcDataDir, "Hime Configs");
    textField = new CustomTextField(350, 32, "", "Set Name");
    urlField = new CustomTextField(450, 32, "", "URL");
    renameField = new CustomTextField(460, 100, "", "Rename");
	super.initGui();
	}
	public void duplicate(String filename, File original) {
		duplicateFile = new File(dir, filename + ".json");
		if (!duplicateFile.exists()) {
			try {
				duplicateFile.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}else {
			Hime.addClientChatMessage("File already exists!");
			LogUtil.instance.log("File already exists!", LogType.ERROR);
			return;
		}
		   ArrayList<String> toSave = new ArrayList<>();
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(original));
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
			  toSave.add(s);
		}
		
	
		    try {
		      PrintWriter pw = new PrintWriter(this.duplicateFile);
		      for (String str : toSave)
		        pw.println(str); 
		      pw.close();
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } 
	}
	public void rename(String filename, File original) {
		rename = new File(dir, filename + ".json");
		if (!rename.exists()) {
			try {
				rename.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}else {
			Hime.addClientChatMessage("File already exists!");
			LogUtil.instance.log("File already exists!", LogType.ERROR);
			return;
		}
		   ArrayList<String> toSave = new ArrayList<>();
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(original));
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
			  toSave.add(s);
		}
		
	
		    try {
		      PrintWriter pw = new PrintWriter(this.rename);
		      for (String str : toSave)
		        pw.println(str); 
		      pw.close();
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } 
		    original.delete();
	}
	 public void save(File saveTo) {
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
	            joToggled.put("keybind", (int) mod.getKey());
	            joToggled.put("toggled", (Boolean) mod.isToggled());
	            jo.put(mod.getName(), joToggled);

	        }

	        try (FileWriter file = new FileWriter(saveTo, false)) {
	            file.write(jo.toString());
	            file.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
		  }
	 
	 public void onlineConfig(String URL) {
		    try {
	            final URL url = new URL(URL);
	            try {
	                this.scanner = new Scanner(url.openStream());
	                while (this.scanner.hasNextLine()) {
	                    

	                    final String creds = this.scanner.nextLine();

	                    if (creds.contains(":"))
	                    {
	                        String[] args = creds.split(":");

	                      
	                			if (creds.toLowerCase().startsWith("mod:")) {
	                				Module m = Hime.instance.moduleManager.getModule(args[1]);
	                				if (m != null) {
	                					m.setToggled(Boolean.parseBoolean(args[2]));
	                					m.setKey(Integer.parseInt(args[3]));
	                				}
	                			} else if (creds.toLowerCase().startsWith("set:")) {
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
	                

	            } catch (IOException e) {
	            	e.printStackTrace();
	            	Hime.addClientChatMessage("Scanning URL Error!");
					LogUtil.instance.log("Scanning URL Error!", LogType.ERROR);
	            	}
	        } catch (MalformedURLException e) {
	        	e.printStackTrace();
	        	Hime.addClientChatMessage("URL invalid!");
				LogUtil.instance.log("URL invalid!", LogType.ERROR);
	        }
	 }
}
