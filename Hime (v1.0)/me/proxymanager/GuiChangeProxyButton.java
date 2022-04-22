package me.proxymanager;

import java.awt.Color;

import me.Hime;
import net.minecraft.client.gui.Gui;

public class GuiChangeProxyButton extends Gui {

	 public int x;
	 public int y;
	 protected boolean hovered;
	 protected boolean hovered2;
	 protected boolean hovered3;
	 
	 public GuiChangeProxyButton(int x, int y)
	 {
		 this.x = x;
		 this.y = y;
	 }
	 
	 public void drawButton(int mouseX, int mouseY)
	 {
		   this.hovered = mouseX >= this.x && mouseY >= this.y + 17 && mouseX < this.x + 50 && mouseY < this.y + 25;
		   this.hovered2 = mouseX >= this.x && mouseY >= this.y + 32 && mouseX < this.x + 50 && mouseY < this.y + 40;
		   this.hovered3 = mouseX >= this.x && mouseY >= this.y + 48 && mouseX < this.x + 34 && mouseY < this.y + 55;
		 
		   this.drawRect(x, y, x + 200, y + 70, new Color(0, 0, 0, 190).getRGB());
		   Hime.instance.cfrs.drawCenteredString("Proxy Types:", x + 100, y + 5, -1);
		 
		 if(!hovered) {
		   Hime.instance.cfrs.drawCenteredString("SOCKS4", x + 26, y + 20, -1);
		 }else {
		   Hime.instance.cfrs.drawCenteredString("SOCKS4", x + 26, y + 20, Color.GRAY.getRGB());
		 }
		 if(!hovered2) {
		   Hime.instance.cfrs.drawCenteredString("SOCKS5", x + 26, y + 35, -1);
		 }else {
		   Hime.instance.cfrs.drawCenteredString("SOCKS5", x + 26, y + 35, Color.GRAY.getRGB());
		 }
		 if(!hovered3) {
		   Hime.instance.cfrs.drawCenteredString("HTTP", x + 20, y + 50, -1);
		 }else {
		   Hime.instance.cfrs.drawCenteredString("HTTP", x + 20, y + 50, Color.GRAY.getRGB());
		 }
	 }
	 
	 
	public void mousePressed(int mouseX, int mouseY, int button)
	{
	   if(button == 0) {	
		if(this.hovered) {
			GuiAddProxy.setProxyType(GuiAddProxy.ProxyType.SOCKS4);
		}
		if(this.hovered2) {
			GuiAddProxy.setProxyType(GuiAddProxy.ProxyType.SOCKS5);
		}
		if(this.hovered3) {
			GuiAddProxy.setProxyType(GuiAddProxy.ProxyType.HTTP);
		}
	   }
	}
	
	public boolean isHoverd() {
		return hovered;
	}
	
}
