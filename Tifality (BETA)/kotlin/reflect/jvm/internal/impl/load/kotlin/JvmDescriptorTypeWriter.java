/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactory;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JvmDescriptorTypeWriter<T> {
    private int jvmCurrentTypeArrayLevel;
    @Nullable
    private T jvmCurrentType;
    private final JvmTypeFactory<T> jvmTypeFactory;

    public void writeArrayType() {
        if (this.jvmCurrentType == null) {
            JvmDescriptorTypeWriter jvmDescriptorTypeWriter = this;
            ++jvmDescriptorTypeWriter.jvmCurrentTypeArrayLevel;
            int cfr_ignored_0 = jvmDescriptorTypeWriter.jvmCurrentTypeArrayLevel;
        }
    }

    public void writeArrayEnd() {
    }

    public void writeClass(@NotNull T objectType) {
        Intrinsics.checkNotNullParameter(objectType, "objectType");
        this.writeJvmTypeAsIs(objectType);
    }

    protected final void writeJvmTypeAsIs(@NotNull T type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
        if (this.jvmCurrentType == null) {
            this.jvmCurrentType = this.jvmCurrentTypeArrayLevel > 0 ? this.jvmTypeFactory.createFromString(StringsKt.repeat("[", this.jvmCurrentTypeArrayLevel) + this.jvmTypeFactory.toString(type2)) : type2;
        }
    }

    public void writeTypeVariable(@NotNull Name name, @NotNull T type2) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(type2, "type");
        this.writeJvmTypeAsIs(type2);
    }
}

