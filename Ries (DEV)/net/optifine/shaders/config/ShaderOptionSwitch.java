/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.shaders.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.src.Config;
import net.optifine.Lang;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.util.StrUtils;

public class ShaderOptionSwitch
extends ShaderOption {
    private static final Pattern PATTERN_DEFINE = Pattern.compile("^\\s*(//)?\\s*#define\\s+([A-Za-z0-9_]+)\\s*(//.*)?$");
    private static final Pattern PATTERN_IFDEF = Pattern.compile("^\\s*#if(n)?def\\s+([A-Za-z0-9_]+)(\\s*)?$");

    public ShaderOptionSwitch(String name, String description, String value, String path) {
        super(name, description, value, new String[]{"false", "true"}, value, path);
    }

    @Override
    public String getSourceLine() {
        return ShaderOptionSwitch.isTrue(this.getValue()) ? "#define " + this.getName() + " // Shader option ON" : "//#define " + this.getName() + " // Shader option OFF";
    }

    @Override
    public String getValueText(String val) {
        String s = super.getValueText(val);
        return !s.equals(val) ? s : (ShaderOptionSwitch.isTrue(val) ? Lang.getOn() : Lang.getOff());
    }

    @Override
    public String getValueColor(String val) {
        return ShaderOptionSwitch.isTrue(val) ? "\u00a7a" : "\u00a7c";
    }

    public static ShaderOption parseOption(String line, String path) {
        Matcher matcher = PATTERN_DEFINE.matcher(line);
        if (!matcher.matches()) {
            return null;
        }
        String s = matcher.group(1);
        String s1 = matcher.group(2);
        String s2 = matcher.group(3);
        if (s1 != null && s1.length() > 0) {
            boolean flag = Config.equals(s, "//");
            boolean flag1 = !flag;
            path = StrUtils.removePrefix(path, "/shaders/");
            return new ShaderOptionSwitch(s1, s2, String.valueOf(flag1), path);
        }
        return null;
    }

    @Override
    public boolean matchesLine(String line) {
        Matcher matcher = PATTERN_DEFINE.matcher(line);
        if (!matcher.matches()) {
            return false;
        }
        String s = matcher.group(2);
        return s.matches(this.getName());
    }

    @Override
    public boolean checkUsed() {
        return true;
    }

    @Override
    public boolean isUsedInLine(String line) {
        Matcher matcher = PATTERN_IFDEF.matcher(line);
        if (matcher.matches()) {
            String s = matcher.group(2);
            return s.equals(this.getName());
        }
        return false;
    }

    public static boolean isTrue(String val) {
        return Boolean.parseBoolean(val);
    }
}

