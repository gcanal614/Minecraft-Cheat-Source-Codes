/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.modules.impl.miscellaneous;

import java.util.ArrayList;
import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.entity.EventUpdate;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;

@ModuleInfo(internalName="MinemenBoats", name="Minemen Boats", desc="Allows you to spawn a shit ton of boats.\nThis is the most retarded module I've ever made.\n\nWARNING: This module can ban you on servers (from jumping on the boats). Beware...", category=Module.Category.MISCELLANEOUS, legit=true)
public class MinemenBoats
extends Module {
    private float yawIncrement;
    private double x;
    private double y;
    private double z;
    private final ArrayList<EntityBoat> boats = new ArrayList();

    public MinemenBoats(int key, boolean enabled) {
        super(key, enabled);
    }

    @Override
    public void onEnable() {
        if (MinemenBoats.MC.thePlayer != null) {
            this.x = MinemenBoats.MC.thePlayer.posX;
            this.y = MinemenBoats.MC.thePlayer.posY;
            this.z = MinemenBoats.MC.thePlayer.posZ;
        } else {
            this.toggle();
        }
    }

    @Override
    public void onDisable() {
        this.boats.forEach(boat -> MinemenBoats.MC.theWorld.removeEntity((Entity)boat));
        this.boats.clear();
        this.yawIncrement = 0.0f;
    }

    @EventTarget(target=EventUpdate.class)
    public void onUpdate(EventUpdate e) {
        if (this.yawIncrement < 360.0f) {
            EntityBoat boat = new EntityBoat(MinemenBoats.MC.theWorld, this.x, this.y, this.z);
            this.boats.add(boat);
            boat.rotationYaw = this.yawIncrement += 1.0f;
            MinemenBoats.MC.theWorld.spawnEntityInWorld(boat);
        }
    }
}

