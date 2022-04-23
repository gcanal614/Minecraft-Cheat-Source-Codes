// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import bozoware.base.BozoWare;
import java.util.Iterator;
import bozoware.base.util.player.PlayerUtils;
import net.minecraft.util.ResourceLocation;
import bozoware.impl.module.combat.AntiBot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import bozoware.impl.property.EnumProperty;
import bozoware.base.event.EventListener;
import bozoware.impl.event.visual.EventRender3D;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "ImageESP", moduleCategory = ModuleCategory.VISUAL)
public class ImageESP extends Module
{
    @EventListener
    EventConsumer<EventRender3D> onRender3D;
    public final EnumProperty<imgModes> imgMode;
    String location;
    
    public ImageESP() {
        this.imgMode = new EnumProperty<imgModes>("Image Mode", imgModes.Shidder, this);
        this.imgMode.onValueChange = (() -> this.setModuleSuffix(this.imgMode.getPropertyValue().toString()));
        this.setModuleSuffix(this.imgMode.getPropertyValue().toString());
        final Iterator<Entity> iterator;
        Entity e;
        this.onRender3D = (event -> {
            ImageESP.mc.theWorld.loadedEntityList.iterator();
            while (iterator.hasNext()) {
                e = iterator.next();
                if (e != null && e instanceof EntityLivingBase && e instanceof EntityPlayer && e != ImageESP.mc.thePlayer && e != null && e instanceof EntityLivingBase && e instanceof EntityPlayer && !AntiBot.botList.contains(e.getEntityId()) && !e.isInvisible() && e != ImageESP.mc.thePlayer) {
                    if (this.imgMode.getPropertyValue().equals(imgModes.Shidder)) {
                        this.location = "BozoWare/shidder.jpg";
                    }
                    if (this.imgMode.getPropertyValue().equals(imgModes.Posk)) {
                        this.location = "BozoWare/posk.png";
                    }
                    if (this.imgMode.getPropertyValue().equals(imgModes.Anth)) {
                        this.location = "BozoWare/anth.png";
                    }
                    if (this.imgMode.getPropertyValue().equals(imgModes.Phobos)) {
                        this.location = "BozoWare/phobo.png";
                    }
                    if (this.imgMode.getPropertyValue().equals(imgModes.Nusted)) {
                        this.location = "BozoWare/Nusted.png";
                    }
                    if (this.imgMode.getPropertyValue().equals(imgModes.Tear)) {
                        this.location = "BozoWare/Tear.png";
                    }
                    ImageESP.mc.getTextureManager().bindTexture(new ResourceLocation(this.location));
                    PlayerUtils.drawImageESP(e);
                }
            }
        });
    }
    
    public static ESP2D getInstance() {
        return BozoWare.getInstance().getModuleManager().getModuleByClass.apply(ESP2D.class);
    }
    
    public enum imgModes
    {
        Shidder, 
        Posk, 
        Nusted, 
        Tear, 
        Phobos, 
        Anth;
    }
}
