package stellar.skid.utils;

import stellar.skid.gui.screen.click.DiscordGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class ScaleUtils {


    public static int[] getScaledMouseCoordinates(Minecraft mc, int mouseX, int mouseY){
        int x = mouseX;
        int y = mouseY;
        switch (mc.gameSettings.guiScale){
            case 0:
                x*=2;
                y*=2;
                break;
            case 1:
                x*=0.5;
                y*=0.5;
                break;
            case 3:
                x*=1.4999999999999999998;
                y*=1.4999999999999999998;
        }
        return new int[]{x,y};
    }


    public static double[] getScaledMouseCoordinates(Minecraft mc, double mouseX, double mouseY){
        double x = mouseX;
        double y = mouseY;
        switch (mc.gameSettings.guiScale){
            case 0:
                x*=2;
                y*=2;
                break;
            case 1:
                x*=0.5;
                y*=0.5;
                break;
            case 3:
                x*=1.4999999999999999998;
                y*=1.4999999999999999998;
        }
        return new double[]{x,y};
    }


    public static void scale(Minecraft mc){
        if(!(mc.currentScreen instanceof DiscordGUI)){
            switch (mc.gameSettings.guiScale){
                case 0:
                    GlStateManager.scale(0.5,0.5,0.5);
                    break;
                case 1:
                    GlStateManager.scale(2,2,2);
                    break;
                case 3:
                    GlStateManager.scale(0.6666666666666667,0.6666666666666667,0.6666666666666667);

            }
        }
    }



}
