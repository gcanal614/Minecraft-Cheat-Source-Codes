package me.module.impl.render;
import java.awt.Color;
import java.util.List;

import me.Hime;
import org.lwjgl.input.Keyboard;

import me.event.impl.EventRenderHUD;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class TabGui extends Module {

	public int currentTab;
	public boolean expanded;
	public TabGui() {
		super("TabGui2", Keyboard.KEY_NONE, Category.RENDER);
		
	}
	
	@Handler
	public void onRender2D(EventRenderHUD event) {
			FontRenderer fr = mc.fontRendererObj;
			Gui.drawRect(5, 30.5, 70, 30 + Category.values().length * 16 + 1.5, 0x90000000);
			Gui.drawRect(7, 33 + currentTab * 16, 7 + 2, 33 + currentTab * 16 + 12, -1);
			
			int count= 0;
			for(Category c : Category.values()) {
				Hime.instance.cfrs.drawString(c.toString().substring(0, 1) + c.toString().substring(1).toLowerCase(), 10, 35 + count*16, -1);
				count++;
			}
			if(expanded) {
				Category category = Category.values()[currentTab];
				List<Module> Modules = Hime.instance.moduleManager.getModulebyCategory(category);
				
				if(Modules.size() == 0)
					return;
				//GL11.glScissor(x, y, width, height);
				Gui.drawRect(70, 30.5, 70 + 68, 30 + Modules.size() * 16 + 1.5, 0x90000000);
			//	Gui.drawRect(70, 33 + category.ModuleIndex * 16, 4 + 68, 33 + category.ModuleIndex * 16 + 12, -1);
				
				count = 0;
				for(Module m : Modules) {
					Hime.instance.cfrs.drawString(m.getName(), 73, 35 + count*16, (m.isToggled() ? Color.BLACK.getRGB() : -1));
					count++;
			}
	}
   }
	/*public void onKey(EventKey e) {
		Category category = Category.values()[currentTab];
		List<Module> Modules = Hime.getModuleByCategory(category);
		
		if(code == Keyboard.KEY_UP) {
			if(expanded) {
				if(category.ModuleIndex <= 0) {
					category.ModuleIndex = Modules.size() - 1;
				}else
					category.ModuleIndex--;
			}else {
				if(currentTab <= 0) {
					currentTab = Category.values().length - 1;
				}else
					currentTab--;
			}
		}		
		
		if(code == Keyboard.KEY_DOWN) {
			if(expanded) {
				if(category.ModuleIndex >= Modules.size() - 1) {
					category.ModuleIndex = 0;
				}else
					category.ModuleIndex++;
			}else {
				if(currentTab >= Category.values().length - 1) {
					currentTab = 0;
				}else 
					currentTab++;
			}
		}
		
		if(code == Keyboard.KEY_RIGHT) {
			if(expanded && Modules.size() !=0) {
				Module Module = Modules.get(category.ModuleIndex);
					Module.toggle();
			}else {
			expanded = true;
			}
		}
		
		if(code == Keyboard.KEY_LEFT) {
			expanded = false;
			}
		}*/
}
