/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class NameAndSignature {
    @NotNull
    private final Name name;
    @NotNull
    private final String signature;

    @NotNull
    public final Name getName() {
        return this.name;
    }

    @NotNull
    public final String getSignature() {
        return this.signature;
    }

    public NameAndSignature(@NotNull Name name, @NotNull String signature2) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(signature2, "signature");
        this.name = name;
        this.signature = signature2;
    }

    @NotNull
    public String toString() {
        return "NameAndSignature(name=" + this.name + ", signature=" + this.signature + ")";
    }

    public int hashCode() {
        Name name = this.name;
        String string = this.signature;
        return (name != null ? ((Object)name).hashCode() : 0) * 31 + (string != null ? string.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof NameAndSignature)) break block3;
                NameAndSignature nameAndSignature = (NameAndSignature)object;
                if (!Intrinsics.areEqual(this.name, nameAndSignature.name) || !Intrinsics.areEqual(this.signature, nameAndSignature.signature)) break block3;
            }
            return true;
        }
        return false;
    }
}

