/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.shaders.config;

import java.util.Arrays;
import java.util.List;
import net.minecraft.src.Config;
import net.optifine.shaders.Shaders;
import net.optifine.util.StrUtils;

public abstract class ShaderOption {
    private String name = null;
    private String description = null;
    private String value = null;
    private String[] values = null;
    private String valueDefault = null;
    private String[] paths = null;
    private boolean enabled = true;
    private boolean visible = true;
    public static final String COLOR_GREEN = "\u00a7a";
    public static final String COLOR_RED = "\u00a7c";
    public static final String COLOR_BLUE = "\u00a79";

    public ShaderOption(String name, String description2, String value, String[] values2, String valueDefault, String path) {
        this.name = name;
        this.description = description2;
        this.value = value;
        this.values = values2;
        this.valueDefault = valueDefault;
        if (path != null) {
            this.paths = new String[]{path};
        }
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDescriptionText() {
        String s = Config.normalize(this.description);
        s = StrUtils.removePrefix(s, "//");
        s = Shaders.translate("option." + this.getName() + ".comment", s);
        return s;
    }

    public void setDescription(String description2) {
        this.description = description2;
    }

    public String getValue() {
        return this.value;
    }

    public boolean setValue(String value) {
        int i = ShaderOption.getIndex(value, this.values);
        if (i < 0) {
            return false;
        }
        this.value = value;
        return true;
    }

    public String getValueDefault() {
        return this.valueDefault;
    }

    public void resetValue() {
        this.value = this.valueDefault;
    }

    public void nextValue() {
        int i = ShaderOption.getIndex(this.value, this.values);
        if (i >= 0) {
            i = (i + 1) % this.values.length;
            this.value = this.values[i];
        }
    }

    public void prevValue() {
        int i = ShaderOption.getIndex(this.value, this.values);
        if (i >= 0) {
            i = (i - 1 + this.values.length) % this.values.length;
            this.value = this.values[i];
        }
    }

    private static int getIndex(String str, String[] strs) {
        for (int i = 0; i < strs.length; ++i) {
            String s = strs[i];
            if (!s.equals(str)) continue;
            return i;
        }
        return -1;
    }

    public String[] getPaths() {
        return this.paths;
    }

    public void addPaths(String[] newPaths) {
        List<String> list = Arrays.asList(this.paths);
        for (int i = 0; i < newPaths.length; ++i) {
            String s = newPaths[i];
            if (list.contains(s)) continue;
            this.paths = (String[])Config.addObjectToArray(this.paths, s);
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isChanged() {
        return !Config.equals(this.value, this.valueDefault);
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isValidValue(String val) {
        return ShaderOption.getIndex(val, this.values) >= 0;
    }

    public String getNameText() {
        return Shaders.translate("option." + this.name, this.name);
    }

    public String getValueText(String val) {
        return Shaders.translate("value." + this.name + "." + val, val);
    }

    public String getValueColor(String val) {
        return "";
    }

    public boolean matchesLine(String line) {
        return false;
    }

    public boolean checkUsed() {
        return false;
    }

    public boolean isUsedInLine(String line) {
        return false;
    }

    public String getSourceLine() {
        return null;
    }

    public String[] getValues() {
        return (String[])this.values.clone();
    }

    public float getIndexNormalized() {
        if (this.values.length <= 1) {
            return 0.0f;
        }
        int i = ShaderOption.getIndex(this.value, this.values);
        if (i < 0) {
            return 0.0f;
        }
        float f = 1.0f * (float)i / ((float)this.values.length - 1.0f);
        return f;
    }

    public void setIndexNormalized(float f) {
        if (this.values.length > 1) {
            f = Config.limit(f, 0.0f, 1.0f);
            int i = Math.round(f * (float)(this.values.length - 1));
            this.value = this.values[i];
        }
    }

    public String toString() {
        return "" + this.name + ", value: " + this.value + ", valueDefault: " + this.valueDefault + ", paths: " + Config.arrayToString(this.paths);
    }
}

