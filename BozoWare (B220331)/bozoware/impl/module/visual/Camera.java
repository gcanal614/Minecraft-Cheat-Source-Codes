// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import bozoware.base.BozoWare;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.visual.HurtShakeEvent;
import bozoware.base.event.EventConsumer;
import bozoware.impl.property.BooleanProperty;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Camera", moduleCategory = ModuleCategory.VISUAL)
public class Camera extends Module
{
    private final BooleanProperty hurtCam;
    private final BooleanProperty motionBlurBool;
    @EventListener
    EventConsumer<HurtShakeEvent> hurtShakeEvent;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    
    public Camera() {
        this.hurtCam = new BooleanProperty("No Hurt Cam", true, this);
        this.motionBlurBool = new BooleanProperty("Motion Blur", false, this);
        this.hurtShakeEvent = (e -> {
            if (this.hurtCam.getPropertyValue()) {
                e.setCancelled(true);
            }
            return;
        });
        EntityRenderer er;
        this.onUpdatePositionEvent = (e -> {
            Camera.mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 1604400, 2));
            if (this.motionBlurBool.getPropertyValue()) {
                er = Camera.mc.entityRenderer;
                Camera.mc.entityRenderer.useShader = true;
                if (Camera.mc.theWorld != null && (Camera.mc.entityRenderer.theShaderGroup == null || !Camera.mc.entityRenderer.theShaderGroup.getShaderGroupName().contains("phosphor"))) {
                    if (er.theShaderGroup != null) {
                        er.theShaderGroup.deleteShaderGroup();
                    }
                    er.loadShader(EntityRenderer.shaderResourceLocations[12]);
                }
            }
            return;
        });
        this.onModuleDisabled = (() -> {
            Camera.mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
            Camera.mc.entityRenderer.useShader = true;
            if (Camera.mc.entityRenderer.theShaderGroup != null) {
                Camera.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            }
            return;
        });
        EntityRenderer er2;
        this.motionBlurBool.onValueChange = (() -> {
            if (this.motionBlurBool.getPropertyValue()) {
                er2 = Camera.mc.entityRenderer;
                er2.activateNextShader();
            }
            else {
                Camera.mc.entityRenderer.useShader = true;
                if (Camera.mc.entityRenderer.theShaderGroup != null) {
                    Camera.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
                }
            }
        });
    }
    
    public static Camera getInstance() {
        return BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Camera.class);
    }
}
