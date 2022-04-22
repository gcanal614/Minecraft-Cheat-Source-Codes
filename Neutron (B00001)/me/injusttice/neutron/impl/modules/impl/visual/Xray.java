package me.injusttice.neutron.impl.modules.impl.visual;

import java.util.HashSet;
import java.util.List;

import com.google.common.collect.Lists;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.DoubleSet;

public class Xray extends Module {

    public static final HashSet<Integer> blockIDs = new HashSet();
    private List<Integer> KEY_IDS = Lists.newArrayList(10, 11, 8, 9, 14, 15, 16, 21, 41, 42, 46, 48, 52, 56, 57, 61, 62, 73, 74, 84, 89, 103, 116, 117, 118, 120, 129, 133, 137, 145, 152, 153, 154);
    private int opacity = 160;

    public DoubleSet opacitySet = new DoubleSet("Opacity", 5, 0, 255, 0.01D);

    public Xray() {
        super("XRay", 0, Category.VISUAL);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        blockIDs.clear();
        opacity = (int) opacitySet.getValue();
        try {
            for (Integer o : KEY_IDS) {
                blockIDs.add(o);
            }
            //loadIDs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.renderGlobal.loadRenderers();
    }

    public boolean containsID(int id) {
        return blockIDs.contains(id);
    }

    public int getOpacity() {
        return opacity;
    }
}
