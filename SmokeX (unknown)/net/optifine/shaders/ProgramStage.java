// 
// Decompiled by Procyon v0.6.0
// 

package net.optifine.shaders;

public enum ProgramStage
{
    NONE(""), 
    SHADOW("shadow"), 
    GBUFFERS("gbuffers"), 
    DEFERRED("deferred"), 
    COMPOSITE("composite");
    
    private String name;
    
    private ProgramStage(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
