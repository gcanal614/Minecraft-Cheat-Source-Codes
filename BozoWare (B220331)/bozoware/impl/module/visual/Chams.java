// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import bozoware.base.BozoWare;
import java.util.Iterator;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;
import bozoware.impl.module.combat.AntiBot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import java.awt.Color;
import bozoware.base.event.EventListener;
import bozoware.impl.event.visual.EventRender3D;
import bozoware.base.event.EventConsumer;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.ColorProperty;
import bozoware.impl.property.EnumProperty;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Chams", moduleCategory = ModuleCategory.VISUAL)
public class Chams extends Module
{
    public EnumProperty<chamsMode> chamsXD;
    public ColorProperty color;
    public BooleanProperty hudColors;
    @EventListener
    EventConsumer<EventRender3D> onRender3D;
    String location;
    
    public Chams() {
        this.chamsXD = new EnumProperty<chamsMode>("Chams Mode", chamsMode.Colored, this);
        this.color = new ColorProperty("Color", Color.red, this);
        this.hudColors = new BooleanProperty("Client Colors", true, this);
        this.color.setHidden(false);
        this.hudColors.setHidden(false);
        this.chamsXD.onValueChange = (() -> {
            this.color.setHidden(!this.chamsXD.getPropertyValue().equals(chamsMode.Colored));
            this.hudColors.setHidden(!this.chamsXD.getPropertyValue().equals(chamsMode.Colored));
            return;
        });
        final Iterator<Entity> iterator;
        Entity e;
        this.onRender3D = (event -> {
            Chams.mc.theWorld.loadedEntityList.iterator();
            while (iterator.hasNext()) {
                e = iterator.next();
                if (e != null && e instanceof EntityLivingBase && e instanceof EntityPlayer && !AntiBot.botList.contains(e.getEntityId()) && e != Chams.mc.thePlayer) {
                    GL11.glPushMatrix();
                    GL11.glClear(256);
                    RenderHelper.enableStandardItemLighting();
                    Chams.mc.getRenderManager().renderEntitySimple(e, Chams.mc.timer.renderPartialTicks);
                    RenderHelper.disableStandardItemLighting();
                    GL11.glPopMatrix();
                }
            }
        });
    }
    
    public static Chams getInstance() {
        return BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Chams.class);
    }
    
    public enum chamsMode
    {
        Magic, 
        Colored;
    }
}
