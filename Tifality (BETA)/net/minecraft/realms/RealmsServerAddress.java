/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bdd
 */
package net.minecraft.realms;

public class RealmsServerAddress {
    private final String host;
    private final int port;

    protected RealmsServerAddress(String string, int n) {
        this.host = string;
        this.port = n;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public static RealmsServerAddress parseString(String string) {
        bdd bdd2 = bdd.a((String)string);
        return new RealmsServerAddress(bdd2.a(), bdd2.b());
    }
}

