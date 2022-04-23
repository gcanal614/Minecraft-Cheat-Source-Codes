/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package club.tifality.module.impl.render;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.render.Render3DEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.impl.render.hud.Hud;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.utils.render.RenderingUtils;
import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

@ModuleInfo(label="ChinaHat", category=ModuleCategory.RENDER)
@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000fH\u0007R\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lclub/tifality/module/impl/render/ChinaHat;", "Lclub/tifality/module/Module;", "()V", "renderInFirstPerson", "Lclub/tifality/property/Property;", "", "kotlin.jvm.PlatformType", "sideValue", "Lclub/tifality/property/impl/DoubleProperty;", "stackValue", "drawChinaHat", "", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "e", "Lclub/tifality/manager/event/impl/render/Render3DEvent;", "onRender3D", "event", "Client"})
public final class ChinaHat
extends Module {
    private final Property<Boolean> renderInFirstPerson = new Property<Boolean>("ShowInFirstPerson", false);
    private final DoubleProperty sideValue = new DoubleProperty("Side", 45.0, 30.0, 50.0, 1.0);
    private final DoubleProperty stackValue = new DoubleProperty("Stacks", 50.0, 45.0, 200.0, 5.0);

    @Listener
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (Module.mc.gameSettings.thirdPersonView == 0 && !this.renderInFirstPerson.get().booleanValue()) {
            return;
        }
        EntityPlayerSP entityPlayerSP = Module.mc.thePlayer;
        Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
        this.drawChinaHat(entityPlayerSP, event);
    }

    private final void drawChinaHat(EntityLivingBase entity, Render3DEvent e) {
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)e.getPartialTicks() - RenderManager.renderPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)e.getPartialTicks() - RenderManager.renderPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)e.getPartialTicks() - RenderManager.renderPosZ;
        int side = (int)((Number)this.sideValue.get()).doubleValue();
        int stack = (int)((Number)this.stackValue.get()).doubleValue();
        GL11.glPushMatrix();
        GL11.glTranslated(x, y + 2.2, z);
        GL11.glRotatef(-entity.width, 0.0f, 1.0f, 0.0f);
        Integer n = Hud.hudColor.get();
        Intrinsics.checkNotNullExpressionValue(n, "Hud.hudColor.get()");
        Color color = new Color(((Number)n).intValue());
        GL11.glColor4f((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, 0.2f);
        RenderingUtils.enableSmoothLine(1.0f);
        Cylinder c = new Cylinder();
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        c.setDrawStyle(100011);
        c.draw(0.0f, 0.8f, 0.4f, side, stack);
        RenderingUtils.disableSmoothLine();
        GL11.glPopMatrix();
    }
}

