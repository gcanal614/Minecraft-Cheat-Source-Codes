/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.shaders.config;

import java.util.ArrayList;
import net.optifine.Lang;
import net.optifine.shaders.ShaderUtils;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderProfile;

public class ShaderOptionProfile
extends ShaderOption {
    private final ShaderProfile[] profiles;
    private final ShaderOption[] options;
    private static final String NAME_PROFILE = "<profile>";
    private static final String VALUE_CUSTOM = "<custom>";

    public ShaderOptionProfile(ShaderProfile[] profiles, ShaderOption[] options) {
        super(NAME_PROFILE, "", ShaderOptionProfile.detectProfileName(profiles, options), ShaderOptionProfile.getProfileNames(profiles), ShaderOptionProfile.detectProfileName(profiles, options, true), null);
        this.profiles = profiles;
        this.options = options;
    }

    @Override
    public void nextValue() {
        super.nextValue();
        if (this.getValue().equals(VALUE_CUSTOM)) {
            super.nextValue();
        }
        this.applyProfileOptions();
    }

    public void updateProfile() {
        ShaderProfile shaderprofile = this.getProfile(this.getValue());
        if (!ShaderUtils.matchProfile(shaderprofile, this.options, false)) {
            String s = ShaderOptionProfile.detectProfileName(this.profiles, this.options);
            this.setValue(s);
        }
    }

    private void applyProfileOptions() {
        ShaderProfile shaderprofile = this.getProfile(this.getValue());
        if (shaderprofile != null) {
            String[] astring;
            for (String s : astring = shaderprofile.getOptions()) {
                ShaderOption shaderoption = this.getOption(s);
                if (shaderoption == null) continue;
                String s1 = shaderprofile.getValue(s);
                shaderoption.setValue(s1);
            }
        }
    }

    private ShaderOption getOption(String name) {
        for (ShaderOption shaderoption : this.options) {
            if (!shaderoption.getName().equals(name)) continue;
            return shaderoption;
        }
        return null;
    }

    private ShaderProfile getProfile(String name) {
        for (ShaderProfile shaderprofile : this.profiles) {
            if (!shaderprofile.getName().equals(name)) continue;
            return shaderprofile;
        }
        return null;
    }

    @Override
    public String getNameText() {
        return Lang.get("of.shaders.profile");
    }

    @Override
    public String getValueText(String val) {
        return val.equals(VALUE_CUSTOM) ? Lang.get("of.general.custom", VALUE_CUSTOM) : Shaders.translate("profile." + val, val);
    }

    @Override
    public String getValueColor(String val) {
        return val.equals(VALUE_CUSTOM) ? "\u00a7c" : "\u00a7a";
    }

    @Override
    public String getDescriptionText() {
        String s = Shaders.translate("profile.comment", null);
        if (s != null) {
            return s;
        }
        StringBuilder stringbuffer = new StringBuilder();
        for (ShaderProfile profile : this.profiles) {
            String s2;
            String s1 = profile.getName();
            if (s1 == null || (s2 = Shaders.translate("profile." + s1 + ".comment", null)) == null) continue;
            stringbuffer.append(s2);
            if (s2.endsWith(". ")) continue;
            stringbuffer.append(". ");
        }
        return stringbuffer.toString();
    }

    private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts) {
        return ShaderOptionProfile.detectProfileName(profs, opts, false);
    }

    private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts, boolean def) {
        ShaderProfile shaderprofile = ShaderUtils.detectProfile(profs, opts, def);
        return shaderprofile == null ? VALUE_CUSTOM : shaderprofile.getName();
    }

    private static String[] getProfileNames(ShaderProfile[] profs) {
        ArrayList<String> list = new ArrayList<String>();
        for (ShaderProfile shaderprofile : profs) {
            list.add(shaderprofile.getName());
        }
        list.add(VALUE_CUSTOM);
        return list.toArray(new String[0]);
    }
}

