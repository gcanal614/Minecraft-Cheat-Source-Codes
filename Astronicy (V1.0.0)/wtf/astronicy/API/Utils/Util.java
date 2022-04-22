package wtf.astronicy.API.Utils;

import net.minecraft.client.Minecraft;

public class Util {

    protected static final Minecraft mc = Minecraft.getMinecraft();

    String utilName;
    UtilCategory utilCat;

    public Util(String name, UtilCategory utilCategory){
        utilName = name;
        utilCat = utilCategory;
    }

}
