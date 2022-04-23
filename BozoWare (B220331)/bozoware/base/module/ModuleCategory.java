// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.module;

public enum ModuleCategory
{
    COMBAT("Combat", "A"), 
    PLAYER("Player", "B"), 
    MOVEMENT("Movement", "C"), 
    VISUAL("Visual", "D"), 
    WORLD("World", "E");
    
    public final String categoryName;
    public final String iconCode;
    
    private ModuleCategory(final String categoryName, final String iconCode) {
        this.categoryName = categoryName;
        this.iconCode = iconCode;
    }
}
