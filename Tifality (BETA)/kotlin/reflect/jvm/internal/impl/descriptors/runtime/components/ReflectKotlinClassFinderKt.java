/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.text.StringsKt;

public final class ReflectKotlinClassFinderKt {
    private static final String toRuntimeFqName(ClassId $this$toRuntimeFqName) {
        String string = $this$toRuntimeFqName.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(string, "relativeClassName.asString()");
        String className = StringsKt.replace$default(string, '.', '$', false, 4, null);
        FqName fqName2 = $this$toRuntimeFqName.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(fqName2, "packageFqName");
        return fqName2.isRoot() ? className : $this$toRuntimeFqName.getPackageFqName() + '.' + className;
    }

    public static final /* synthetic */ String access$toRuntimeFqName(ClassId $this$access_u24toRuntimeFqName) {
        return ReflectKotlinClassFinderKt.toRuntimeFqName($this$access_u24toRuntimeFqName);
    }
}

