/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.render;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.render.Render3DEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.ModuleManager;
import club.tifality.utils.Wrapper;
import club.tifality.utils.render.RenderingUtils;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

@ModuleInfo(label="Skeletal", category=ModuleCategory.RENDER)
public final class Skeletal
extends Module {
    private final Map<EntityPlayer, float[][]> playerRotationMap = new HashMap<EntityPlayer, float[][]>();

    public static Skeletal getInstance() {
        return ModuleManager.getInstance(Skeletal.class);
    }

    public static boolean shouldDrawSkeletons() {
        return Skeletal.getInstance().isEnabled();
    }

    @Listener
    public void onRender3DEvent(Render3DEvent e) {
        GL11.glPushMatrix();
        GL11.glLineWidth(0.5f);
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glDepthMask(false);
        for (EntityPlayer player : this.playerRotationMap.keySet()) {
            this.drawSkeleton(e, player);
        }
        this.playerRotationMap.clear();
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
    }

    private void drawSkeleton(Render3DEvent event, EntityPlayer player) {
        float[][] entPos = this.playerRotationMap.get(player);
        if (entPos != null) {
            GL11.glPushMatrix();
            float pt = event.getPartialTicks();
            float x = (float)(RenderingUtils.interpolate(player.prevPosX, player.posX, pt) - RenderManager.renderPosX);
            float y = (float)(RenderingUtils.interpolate(player.prevPosY, player.posY, pt) - RenderManager.renderPosY);
            float z = (float)(RenderingUtils.interpolate(player.prevPosZ, player.posZ, pt) - RenderManager.renderPosZ);
            GL11.glTranslated(x, y, z);
            boolean sneaking = player.isSneaking();
            float xOff = RenderingUtils.interpolate(player.prevRenderYawOffset, player.renderYawOffset, pt);
            float yOff = sneaking ? 0.6f : 0.75f;
            GL11.glRotatef(-xOff, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.0f, 0.0f, sneaking ? -0.235f : 0.0f);
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.125f, yOff, 0.0f);
            if (entPos[3][0] != 0.0f) {
                GL11.glRotatef(entPos[3][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[3][1] != 0.0f) {
                GL11.glRotatef(entPos[3][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[3][2] != 0.0f) {
                GL11.glRotatef(entPos[3][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3i(0, 0, 0);
            GL11.glVertex3f(0.0f, -yOff, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(0.125f, yOff, 0.0f);
            if (entPos[4][0] != 0.0f) {
                GL11.glRotatef(entPos[4][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[4][1] != 0.0f) {
                GL11.glRotatef(entPos[4][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[4][2] != 0.0f) {
                GL11.glRotatef(entPos[4][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3i(0, 0, 0);
            GL11.glVertex3f(0.0f, -yOff, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glTranslatef(0.0f, 0.0f, sneaking ? 0.25f : 0.0f);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, sneaking ? -0.05f : 0.0f, sneaking ? -0.01725f : 0.0f);
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.375f, yOff + 0.55f, 0.0f);
            if (entPos[1][0] != 0.0f) {
                GL11.glRotatef(entPos[1][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[1][1] != 0.0f) {
                GL11.glRotatef(entPos[1][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[1][2] != 0.0f) {
                GL11.glRotatef(-entPos[1][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3i(0, 0, 0);
            GL11.glVertex3f(0.0f, -0.5f, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(0.375f, yOff + 0.55f, 0.0f);
            if (entPos[2][0] != 0.0f) {
                GL11.glRotatef(entPos[2][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[2][1] != 0.0f) {
                GL11.glRotatef(entPos[2][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[2][2] != 0.0f) {
                GL11.glRotatef(-entPos[2][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3i(0, 0, 0);
            GL11.glVertex3f(0.0f, -0.5f, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glRotatef(xOff - player.rotationYawHead, 0.0f, 1.0f, 0.0f);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, yOff + 0.55f, 0.0f);
            if (entPos[0][0] != 0.0f) {
                GL11.glRotatef(entPos[0][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3i(0, 0, 0);
            GL11.glVertex3f(0.0f, 0.3f, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glRotatef(sneaking ? 25.0f : 0.0f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(0.0f, sneaking ? -0.16175f : 0.0f, sneaking ? -0.48025f : 0.0f);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, yOff, 0.0);
            GL11.glBegin(3);
            GL11.glVertex3f(-0.125f, 0.0f, 0.0f);
            GL11.glVertex3f(0.125f, 0.0f, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, yOff, 0.0f);
            GL11.glBegin(3);
            GL11.glVertex3i(0, 0, 0);
            GL11.glVertex3f(0.0f, 0.55f, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, yOff + 0.55f, 0.0f);
            GL11.glBegin(3);
            GL11.glVertex3f(-0.375f, 0.0f, 0.0f);
            GL11.glVertex3f(0.375f, 0.0f, 0.0f);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }

    public static boolean isValid(Entity entity) {
        return Wrapper.getLoadedPlayers().contains(entity) && Skeletal.mc.gameSettings.thirdPersonView != 0 && entity instanceof EntityPlayer && entity.isEntityAlive() && !entity.isInvisible() && RenderingUtils.isBBInFrustum(entity.getEntityBoundingBox());
    }

    public static void addEntity(EntityPlayer e, ModelPlayer model) {
        if (e instanceof EntityOtherPlayerMP) {
            Skeletal.getInstance().playerRotationMap.put(e, new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ}, {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ}, {model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ}, {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}});
        }
    }
}

