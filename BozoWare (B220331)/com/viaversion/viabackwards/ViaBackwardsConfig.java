// 
// Decompiled by Procyon v0.5.36
// 

package com.viaversion.viabackwards;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.net.URL;
import java.io.File;
import com.viaversion.viaversion.util.Config;

public class ViaBackwardsConfig extends Config implements com.viaversion.viabackwards.api.ViaBackwardsConfig
{
    private boolean addCustomEnchantsToLore;
    private boolean addTeamColorToPrefix;
    private boolean fix1_13FacePlayer;
    private boolean alwaysShowOriginalMobName;
    private boolean handlePingsAsInvAcknowledgements;
    
    public ViaBackwardsConfig(final File configFile) {
        super(configFile);
    }
    
    @Override
    public void reloadConfig() {
        super.reloadConfig();
        this.loadFields();
    }
    
    private void loadFields() {
        this.addCustomEnchantsToLore = this.getBoolean("add-custom-enchants-into-lore", true);
        this.addTeamColorToPrefix = this.getBoolean("add-teamcolor-to-prefix", true);
        this.fix1_13FacePlayer = this.getBoolean("fix-1_13-face-player", false);
        this.alwaysShowOriginalMobName = this.getBoolean("always-show-original-mob-name", true);
        this.handlePingsAsInvAcknowledgements = this.getBoolean("handle-pings-as-inv-acknowledgements", false);
    }
    
    @Override
    public boolean addCustomEnchantsToLore() {
        return this.addCustomEnchantsToLore;
    }
    
    @Override
    public boolean addTeamColorTo1_13Prefix() {
        return this.addTeamColorToPrefix;
    }
    
    @Override
    public boolean isFix1_13FacePlayer() {
        return this.fix1_13FacePlayer;
    }
    
    @Override
    public boolean alwaysShowOriginalMobName() {
        return this.alwaysShowOriginalMobName;
    }
    
    @Override
    public boolean handlePingsAsInvAcknowledgements() {
        return this.handlePingsAsInvAcknowledgements || Boolean.getBoolean("com.viaversion.handlePingsAsInvAcknowledgements");
    }
    
    @Override
    public URL getDefaultConfigURL() {
        return this.getClass().getClassLoader().getResource("assets/viabackwards/config.yml");
    }
    
    @Override
    protected void handleConfig(final Map<String, Object> map) {
    }
    
    @Override
    public List<String> getUnsupportedOptions() {
        return Collections.emptyList();
    }
}
