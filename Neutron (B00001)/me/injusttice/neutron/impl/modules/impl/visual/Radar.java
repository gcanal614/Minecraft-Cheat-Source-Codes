package me.injusttice.neutron.impl.modules.impl.visual;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventRender2D;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.utils.render.Render2DUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Radar extends Module {

    public Radar(){
        super("Radar", 0, Category.VISUAL);
    }

    @EventTarget
    public void onDraw(EventRender2D e) {
        double center = 50;
        double toAddX = 5, toAddZ = 5;

        Gui.drawRect(0 + toAddX, 0 + toAddZ - 1, 100 + toAddX, 0 + toAddZ, new Color(0x9C66FF).getRGB());

        Render2DUtils.prepareScissorBox(0 + toAddX, 0 + toAddZ, 100 + toAddX, 100 + toAddZ);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        Gui.drawRect(0 + toAddX, 0 + toAddZ, 100 + toAddX, 100 + toAddZ, new Color(0xFF131313, true).getRGB());

        for (Entity ent : mc.theWorld.getLoadedEntityList()) {
            if (ent != mc.thePlayer) {

                int color = 0;
                if (ent instanceof EntityMob) {
                    color = new Color(0x94FFFFFF, true).getRGB();
                }
                if (ent instanceof EntityAnimal) {
                    color = new Color(0x6AFFFFFF, true).getRGB();
                }
                if (ent instanceof EntityPlayer) {
                    color = new Color(0xFFFFFFFF, true).getRGB();
                }
                if (ent instanceof EntityItem) {
                    color = new Color(0x27FFFFFF, true).getRGB();
                }

                double drawX = center + (Math.round(mc.thePlayer.posX) - Math.round(ent.posX));
                double drawZ = center + (Math.round(mc.thePlayer.posZ) - Math.round(ent.posZ));

                Gui.drawRect(drawX + toAddX, drawZ + toAddZ, drawX + 1 + toAddX, drawZ + 1 + toAddZ, color);
            }
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        Gui.drawRect(toAddX + center - 1, 0 + toAddZ, toAddX + center, 100 + toAddZ, new Color(0x56FFFFFF, true).getRGB());
        Gui.drawRect(0 + toAddX, toAddZ + center - 1, 100 + toAddX, toAddZ + center, new Color(0x56FFFFFF, true).getRGB());
    }
}
