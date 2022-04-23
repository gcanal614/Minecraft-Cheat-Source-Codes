/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bfd
 *  bfd$a
 *  bmu
 */
package net.minecraft.realms;

import java.nio.ByteBuffer;
import net.minecraft.realms.RealmsVertexFormat;

public class RealmsBufferBuilder {
    private bfd b;

    public RealmsBufferBuilder(bfd bfd2) {
        this.b = bfd2;
    }

    public RealmsBufferBuilder from(bfd bfd2) {
        this.b = bfd2;
        return this;
    }

    public void sortQuads(float f, float f2, float f3) {
        this.b.a(f, f2, f3);
    }

    public void fixupQuadColor(int n) {
        this.b.a(n);
    }

    public ByteBuffer getBuffer() {
        return this.b.f();
    }

    public void postNormal(float f, float f2, float f3) {
        this.b.b(f, f2, f3);
    }

    public int getDrawMode() {
        return this.b.i();
    }

    public void offset(double d, double d2, double d3) {
        this.b.c(d, d2, d3);
    }

    public void restoreState(bfd.a a2) {
        this.b.a(a2);
    }

    public void endVertex() {
        this.b.d();
    }

    public RealmsBufferBuilder normal(float f, float f2, float f3) {
        return this.from(this.b.c(f, f2, f3));
    }

    public void end() {
        this.b.e();
    }

    public void begin(int n, bmu bmu2) {
        this.b.a(n, bmu2);
    }

    public RealmsBufferBuilder color(int n, int n2, int n3, int n4) {
        return this.from(this.b.b(n, n2, n3, n4));
    }

    public void faceTex2(int n, int n2, int n3, int n4) {
        this.b.a(n, n2, n3, n4);
    }

    public void postProcessFacePosition(double d, double d2, double d3) {
        this.b.a(d, d2, d3);
    }

    public void fixupVertexColor(float f, float f2, float f3, int n) {
        this.b.b(f, f2, f3, n);
    }

    public RealmsBufferBuilder color(float f, float f2, float f3, float f4) {
        return this.from(this.b.a(f, f2, f3, f4));
    }

    public RealmsVertexFormat getVertexFormat() {
        return new RealmsVertexFormat(this.b.g());
    }

    public void faceTint(float f, float f2, float f3, int n) {
        this.b.a(f, f2, f3, n);
    }

    public RealmsBufferBuilder tex2(int n, int n2) {
        return this.from(this.b.a(n, n2));
    }

    public void putBulkData(int[] nArray) {
        this.b.a(nArray);
    }

    public RealmsBufferBuilder tex(double d, double d2) {
        return this.from(this.b.a(d, d2));
    }

    public int getVertexCount() {
        return this.b.h();
    }

    public void clear() {
        this.b.b();
    }

    public RealmsBufferBuilder vertex(double d, double d2, double d3) {
        return this.from(this.b.b(d, d2, d3));
    }

    public void fixupQuadColor(float f, float f2, float f3) {
        this.b.d(f, f2, f3);
    }

    public void noColor() {
        this.b.c();
    }
}

