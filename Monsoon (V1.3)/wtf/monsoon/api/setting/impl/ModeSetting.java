package wtf.monsoon.api.setting.impl;

import java.util.Arrays;
import java.util.List;

import wtf.monsoon.api.module.Module;
import wtf.monsoon.api.setting.Setting;


public class ModeSetting extends Setting {
	  public int index;
	  
	  public List<String> modes;
	  
	  public ModeSetting(String name, Module parent, String defaultMode, String... modes) {
	    this.name = name;
	    this.parent = parent;
	    this.modes = Arrays.asList(modes);
	    this.index = this.modes.indexOf(defaultMode);
	    this.shouldRender = true;
	  }

	public ModeSetting(String name, Module parent, boolean shouldRender, String defaultMode, String... modes) {
		this.name = name;
		this.parent = parent;
		this.modes = Arrays.asList(modes);
		this.index = this.modes.indexOf(defaultMode);

		this.shouldRender = shouldRender;
	}
	  
	  public String getMode() {
	    return this.modes.get(this.index);
	  }
	  
	  public void setMode(String mode) {
		  this.index = this.modes.indexOf(mode);
	  }
	  
	  public boolean is(String mode) {
	    return (this.index == this.modes.indexOf(mode));
	  }
	  
	  public void cycle() {
	    if (this.index < this.modes.size() - 1) {
	      this.index++;
	    } else {
	      this.index = 0;
	    } 
	  }

	public String getValueName() {
		return this.modes.get(this.index);
	}

	public void increment() {
		 if (this.index < this.modes.size() - 1) {
		      this.index++;
		    } else {
		      this.index = 0;
		    } 
		  }
	}
