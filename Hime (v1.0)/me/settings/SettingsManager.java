package me.settings;

import java.util.ArrayList;

import me.Hime;
import me.module.Module;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class SettingsManager {

    private ArrayList<Setting> settings;

    public SettingsManager(){
        this.settings = new ArrayList<>();
    }

    public void rSetting(Setting in){
        this.settings.add(in);
    }

    public ArrayList<Setting> getSettings(){
        return this.settings;
    }

    public boolean hasSettings(Module mod) {
    	  ArrayList<Setting> out = new ArrayList<>();
          for(Setting s : getSettings()){
              if(s.getParentMod().equals(mod)){
                  out.add(s);
              }
          }
          if(out.isEmpty()){
              return false;
          }
          return true;
    }
    
    public ArrayList<Setting> getSettingsByMod(Module mod){
        ArrayList<Setting> out = new ArrayList<>();
        for(Setting s : getSettings()){
            if(s.getParentMod().equals(mod)){
                out.add(s);
            }
        }
        if(out.isEmpty()){
            return null;
        }
        return out;
    }

    public Setting getSettingByName(String name){
        for(Setting set : getSettings()){
            if(set.getName().equalsIgnoreCase(name)){
                return set;
            }
        }
        System.err.println("["+ Hime.instance.name + "] Error Setting NOT found: '" + name +"'!");
        return null;
    }
    
    public Setting getSettingByName(Module mod, String name){
		for(Setting set : getSettings()){
			if(set.getName().equalsIgnoreCase(name) && set.getParentMod() == mod){
				return set;
			}
		}
		System.err.println("[Hime] Error Setting NOT found: '" + name +"'!");
		return null;
	}

}