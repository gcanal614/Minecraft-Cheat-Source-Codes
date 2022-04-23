/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  store.intent.intentguard.annotation.Exclude
 *  store.intent.intentguard.annotation.Strategy
 */
package net.optifine.shaders.config;

import net.optifine.shaders.Shaders;
import net.optifine.shaders.config.ShaderOption;
import store.intent.intentguard.annotation.Exclude;
import store.intent.intentguard.annotation.Strategy;

@Exclude(value={Strategy.NAME_REMAPPING, Strategy.STRING_ENCRYPTION, Strategy.FLOW_OBFUSCATION, Strategy.NUMBER_OBFUSCATION, Strategy.REFERENCE_OBFUSCATION, Strategy.PARAMETER_OBFUSCATION})
public class ShaderOptionScreen
extends ShaderOption {
    public ShaderOptionScreen(String name) {
        super(name, null, null, new String[0], null, null);
    }

    @Override
    public String getNameText() {
        return Shaders.translate("screen." + this.getName(), this.getName());
    }

    @Override
    public String getDescriptionText() {
        return Shaders.translate("screen." + this.getName() + ".comment", null);
    }
}

