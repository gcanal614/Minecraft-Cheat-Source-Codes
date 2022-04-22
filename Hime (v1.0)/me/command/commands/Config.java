package me.command.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import me.Hime;
import me.notification.Notification;
import me.notification.NotificationManager;
import me.notification.NotificationType;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import me.command.Command;
import me.file.Configer2;
import me.log.LogUtil;
import me.log.LogUtil.LogType;
import me.module.Module;
import me.settings.Setting;

public class Config extends Command{

	public static Scanner scanner;
	@Override
	public String getName() {
		return "config";
	}

	@Override
	public String getDescription() {
		return "Allows user to load configs.";
	}

	@Override
	public String getSyntax() {
		return ".config save [Name] | .config load [Name] | .config remove [Name] | .config upload [Name] | "
		+ ".config loadonline [Url] | .config bind load [Name] | .config bind save [Name] | .config bind remove [Name]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if(!args[0].equalsIgnoreCase("bind")) {
			if(args[0].equalsIgnoreCase("save")) {
				Configer2.instance.configSave(args[1]);
				NotificationManager.show(new Notification(NotificationType.WARNING, "Script Alert", args[1] + " Config Was Saved", 2));
			} if(args[0].equalsIgnoreCase("load")) {
			    Configer2.instance.Config(args[1]);
			    Hime.instance.config = args[1];
				NotificationManager.show(new Notification(NotificationType.WARNING, "Script Alert", args[1] + " Config Was Loaded", 2));
			} else if(args[0].equalsIgnoreCase("remove")) {
				Configer2.instance.configRemove(args[1]);
				NotificationManager.show(new Notification(NotificationType.WARNING, "Script Alert", args[1] + " Config Was Removed", 2));
			}else if(args[0].equalsIgnoreCase("upload")) {
				String content = "";
				
				content = this.saveConfig();
				
				String configLink = uploadConfig(args[1], content);
				if (configLink.isEmpty()) {
				  Hime.addClientChatMessage("Error!");
				} else {
					configLink = "https://pastebin.com/raw/".concat(configLink);
					
					final StringSelection stringSelection = new StringSelection(configLink);
					final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(stringSelection, null);
					
					Hime.addClientChatMessage(args[1] + " config has been uploaded to pastebin: " + configLink);
				}
			}else if(args[0].equalsIgnoreCase("loadonline")) {
				if (args[1].startsWith("http")) {
					   this.onlineConfig(args[1]);
					    Hime.addClientChatMessage("Loaded config: " + args[1]);
				}
			}
		}else {
		  if(args[1].equalsIgnoreCase("load")) {
			Configer2.instance.loadBinds(args[2]); 
			Hime.addClientChatMessage("Loaded Binds from " + args[2]);
		 }if(args[1].equalsIgnoreCase("save")) {
			 Configer2.instance.bindsSave(args[2]);
			 Hime.addClientChatMessage("Saved Binds to " + args[2]);
		 }if(args[1].equalsIgnoreCase("remove")) {
			 Configer2.instance.bindRemove(args[2]);  
			 Hime.addClientChatMessage("Removed Binds from " + args[2]);
		 }
		}
	}
	

	 public String saveConfig() {
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
		  
		    String result = "";
		    for (String str : toSave) {
		        result += (str + "\n"); 
		    }
		    return result;
		  }
	
	private String uploadConfig(String configName, String config) {
		String link = "";
		final HttpClient httpClient = HttpClients.createDefault();
		final HttpPost httpPost = new HttpPost("https://pastebin.com/api/api_post.php");
		
		final List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("api_dev_key", "s4MSgIs1jQDM6PfXKAUWX7vdVXcWLHai"));
		params.add(new BasicNameValuePair("api_option", "paste"));
		params.add(new BasicNameValuePair("api_paste_code", config));
		params.add(new BasicNameValuePair("api_paste_private", "0"));
		params.add(new BasicNameValuePair("api_paste_name", "ArtemisConfig-".concat(configName).concat(".txt")));
		params.add(new BasicNameValuePair("api_paste_expire_date", "N"));
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
			final HttpResponse response = httpClient.execute(httpPost);
			final HttpEntity entity = response.getEntity();
			
			if (entity == null) {
				return link;
			}
			
			try (InputStream stream = entity.getContent()) {
				final byte[] buffer = new byte[1024];
				final StringBuilder stringBuilder = new StringBuilder();
			    int length = 0;

			    while ((length = stream.read(buffer)) >= 0) {
			        stringBuilder.append(new String(Arrays.copyOfRange(buffer, 0, length), "UTF-8"));
			    }

			    link = stringBuilder.toString().substring(21);
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return link;
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
