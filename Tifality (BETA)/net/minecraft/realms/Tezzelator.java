/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bfx
 */
package net.minecraft.realms;

import net.minecraft.realms.RealmsBufferBuilder;
import net.minecraft.realms.RealmsVertexFormat;

public class Tezzelator {
    public static bfx t = bfx.a();
    public static final Tezzelator instance = new Tezzelator();

    public void end() {
        t.b();
    }

    public Tezzelator vertex(double d, double d2, double d3) {
        t.c().b(d, d2, d3);
        return this;
    }

    public void color(float f2, float f3, float f4, float f5) {
        t.c().a(f2, f3, f4, f5);
    }

    public void tex2(short s, short s2) {
        t.c().a((int)s, (int)s2);
    }

    public void normal(float f2, float f3, float f4) {
        t.c().c(f2, f3, f4);
    }

    public void begin(int n, RealmsVertexFormat realmsVertexFormat) {
        t.c().a(n, realmsVertexFormat.getVertexFormat());
    }

    public void endVertex() {
        t.c().d();
    }

    public void offset(double d, double d2, double d3) {
        t.c().c(d, d2, d3);
    }

    public RealmsBufferBuilder color(int n, int n2, int n3, int n4) {
        return new RealmsBufferBuilder(t.c().b(n, n2, n3, n4));
    }

    public Tezzelator tex(double d, double d2) {
        t.c().a(d, d2);
        return this;
    }
}

