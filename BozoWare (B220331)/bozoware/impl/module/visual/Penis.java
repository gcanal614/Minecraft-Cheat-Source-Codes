// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.player.EntityPlayer;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.visual.EventRender3D;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Penis", moduleCategory = ModuleCategory.VISUAL)
public class Penis extends Module
{
    @EventListener
    EventConsumer<EventRender3D> onRender3D;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    
    public Penis() {
        this.onModuleEnabled = (() -> {});
        this.onUpdatePositionEvent = (e -> {});
        this.onRender3D = (event -> this.esp(Penis.mc.thePlayer, Penis.mc.thePlayer.posX, Penis.mc.thePlayer.getEntityBoundingBox().minY + 1.0, Penis.mc.thePlayer.posZ));
    }
    
    public void esp(final EntityPlayer player, final double x, final double y, final double z) {
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDepthMask(true);
        GL11.glLineWidth(1.0f);
        GL11.glRotatef(-player.rotationYaw, 0.0f, player.height, 0.0f);
        GL11.glTranslated(-x, -y, -z);
        GL11.glTranslated(x, y + player.height / 2.0f - 0.22499999403953552, z);
        GL11.glColor4f(0.225f, 0.172f, 0.15f, 1.0f);
        final int lines = 180;
        GL11.glTranslated(0.0, 0.0, 0.07500000298023224);
        final Cylinder shaft = new Cylinder();
        shaft.setDrawStyle(100013);
        shaft.draw(0.1f, 0.11f, 0.4f, 180, lines);
        GL11.glColor4f(0.24f, 0.184f, 0.16f, 1.0f);
        GL11.glTranslated(0.0, 0.0, -0.12500000298023223);
        GL11.glTranslated(-0.09000000074505805, 0.0, 0.0);
        final Sphere right = new Sphere();
        right.setDrawStyle(100013);
        right.draw(0.14f, 10, lines);
        GL11.glTranslated(0.16000000149011612, 0.0, 0.0);
        final Sphere left = new Sphere();
        left.setDrawStyle(100013);
        left.draw(0.14f, 10, lines);
        GL11.glTranslated(-0.07000000074505806, 0.0, 0.589999952316284);
        final Sphere tip = new Sphere();
        tip.setDrawStyle(100013);
        tip.draw(0.13f, 45, lines);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
    }
}
