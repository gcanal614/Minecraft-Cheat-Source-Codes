/*
 * Decompiled with CFR 0.152.
 */
package net.java.games.input;

final class LinuxAxisDescriptor {
    private int type;
    private int code;

    LinuxAxisDescriptor() {
    }

    public final void set(int type2, int code) {
        this.type = type2;
        this.code = code;
    }

    public final int getType() {
        return this.type;
    }

    public final int getCode() {
        return this.code;
    }

    public final int hashCode() {
        return this.type ^ this.code;
    }

    public final boolean equals(Object other) {
        if (!(other instanceof LinuxAxisDescriptor)) {
            return false;
        }
        LinuxAxisDescriptor descriptor2 = (LinuxAxisDescriptor)other;
        return descriptor2.type == this.type && descriptor2.code == this.code;
    }

    public final String toString() {
        return "LinuxAxis: type = 0x" + Integer.toHexString(this.type) + ", code = 0x" + Integer.toHexString(this.code);
    }
}

