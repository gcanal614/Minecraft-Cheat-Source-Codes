package com.zerosense.mods.impl.PLAYER;

import com.zerosense.Events.impl.EventTick;
import com.zerosense.Events.impl.EventUpdate;
import com.zerosense.mods.Module;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;

import java.awt.*;

import static com.zerosense.Utils.DrawUtil.setColor;

public class FastPlace extends Module {

    public FastPlace() {
        super("FastPlace", Keyboard.KEY_NONE, Category.PLAYER);
        setColor(new Color(226, 197, 78).getRGB());
    }

    public void onUpdate(EventUpdate event) {
        //mc.rightClickDelayTimer = 0;
    }
}