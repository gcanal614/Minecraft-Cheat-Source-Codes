/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.compress.archivers.dump;

class Dirent {
    private final int ino;
    private final int parentIno;
    private final int type;
    private final String name;

    Dirent(int ino, int parentIno, int type2, String name) {
        this.ino = ino;
        this.parentIno = parentIno;
        this.type = type2;
        this.name = name;
    }

    int getIno() {
        return this.ino;
    }

    int getParentIno() {
        return this.parentIno;
    }

    int getType() {
        return this.type;
    }

    String getName() {
        return this.name;
    }

    public String toString() {
        return String.format("[%d]: %s", this.ino, this.name);
    }
}

