package me.injusttice.neutron.impl.modules.impl.visual;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventRender3D;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.utils.astolfo.AstolfoUtils;
import me.injusttice.neutron.utils.render.Render2DUtils;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ChinaHat extends Module {

    public ChinaHat() {
        super("ChinaHat", 0, Category.VISUAL);
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
        if(mc.gameSettings.thirdPersonView != 0) {
            for (int i = 0; i < 400; i++) {
                drawHat(mc.thePlayer, 0.009 + i * 0.0014, mc.timer.elapsedPartialTicks, 12, (float) 2, 2.20f - i * 0.000785f - (0.03f), AstolfoUtils.rainbow(-100, 1f, 0.47f));
            }
        }
    }

    public static void drawHat(Entity entity, double radius, float partialTicks, int points, float width, float yAdd, int color) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glDepthMask(false);
        GL11.glLineWidth(width);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glBegin(GL11.GL_LINE_STRIP);

        double x = Render2DUtils.interpolate(entity.prevPosX, entity.posX, partialTicks) - RenderManager.viewerPosX;
        double y = Render2DUtils.interpolate(entity.prevPosY + yAdd, entity.posY + yAdd, partialTicks) - RenderManager.viewerPosY;
        double z = Render2DUtils.interpolate(entity.prevPosZ, entity.posZ, partialTicks) - RenderManager.viewerPosZ;

        GL11.glColor4f(new Color(color).getRed() / 255f, new Color(color).getGreen() / 255f, new Color(color).getBlue() / 255f, 0.15f);
        for (int i = 0; i <= points; i++) GL11.glVertex3d(x + radius * Math.cos(i * Math.PI * 2 / points), y, z  + radius * Math.sin(i * Math.PI * 2 / points));

        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }
}
