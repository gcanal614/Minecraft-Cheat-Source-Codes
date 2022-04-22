package non.asset.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class loadingMenu {

	private int value = 0;
	
	static Minecraft mc = Minecraft.getMinecraft();
	
	public static void drawLoadingMenu(int value) {
		mc.getTextureManager().bindTexture(new ResourceLocation("textures/client/dtgygubuhiu.png"));
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, mc.displayWidth, mc.displayHeight, mc.displayWidth, mc.displayHeight);
	}
	
}
