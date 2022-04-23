// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.util;

public class Vector3d
{
    public double field_181059_a;
    public double field_181060_b;
    public double field_181061_c;
    
    public Vector3d() {
        final double field_181059_a = 0.0;
        this.field_181061_c = field_181059_a;
        this.field_181060_b = field_181059_a;
        this.field_181059_a = field_181059_a;
    }
    
    public Vector3d(final float v, final float v1, final float v2) {
        this.field_181059_a = v;
        this.field_181060_b = v1;
        this.field_181061_c = v2;
    }
    
    public Vector3d(final double minX, final double minY, final double minZ) {
        this.field_181059_a = minX;
        this.field_181060_b = minY;
        this.field_181061_c = minZ;
    }
}
