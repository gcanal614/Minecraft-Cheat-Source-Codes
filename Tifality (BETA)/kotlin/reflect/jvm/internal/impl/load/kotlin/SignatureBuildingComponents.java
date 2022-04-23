/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import org.jetbrains.annotations.NotNull;

public final class SignatureBuildingComponents {
    public static final SignatureBuildingComponents INSTANCE;

    @NotNull
    public final String javaLang(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return "java/lang/" + name;
    }

    @NotNull
    public final String javaUtil(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return "java/util/" + name;
    }

    @NotNull
    public final String javaFunction(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return "java/util/function/" + name;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final String[] constructors(String ... signatures) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        String[] $this$map$iv = signatures;
        boolean $i$f$map = false;
        String[] stringArray = $this$map$iv;
        Collection destination$iv$iv = new ArrayList($this$map$iv.length);
        boolean $i$f$mapTo = false;
        void var7_7 = $this$mapTo$iv$iv;
        int n = ((void)var7_7).length;
        for (int i = 0; i < n; ++i) {
            void it;
            void item$iv$iv;
            void var11_11 = item$iv$iv = var7_7[i];
            Collection collection = destination$iv$iv;
            boolean bl = false;
            String string = "<init>(" + (String)it + ")V";
            collection.add(string);
        }
        Collection $this$toTypedArray$iv = (List)destination$iv$iv;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        String[] stringArray2 = thisCollection$iv.toArray(new String[0]);
        if (stringArray2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return stringArray2;
    }

    @NotNull
    public final LinkedHashSet<String> inJavaLang(@NotNull String name, String ... signatures) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        return this.inClass(this.javaLang(name), Arrays.copyOf(signatures, signatures.length));
    }

    @NotNull
    public final LinkedHashSet<String> inJavaUtil(@NotNull String name, String ... signatures) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        return this.inClass(this.javaUtil(name), Arrays.copyOf(signatures, signatures.length));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final LinkedHashSet<String> inClass(@NotNull String internalName, String ... signatures) {
        void $this$mapTo$iv;
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        String[] stringArray = signatures;
        Collection destination$iv = new LinkedHashSet();
        boolean $i$f$mapTo = false;
        void var6_6 = $this$mapTo$iv;
        int n = ((void)var6_6).length;
        for (int i = 0; i < n; ++i) {
            void it;
            void item$iv;
            void var10_10 = item$iv = var6_6[i];
            Collection collection = destination$iv;
            boolean bl = false;
            String string = internalName + '.' + (String)it;
            collection.add(string);
        }
        return (LinkedHashSet)destination$iv;
    }

    @NotNull
    public final String signature(@NotNull ClassDescriptor classDescriptor, @NotNull String jvmDescriptor2) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(jvmDescriptor2, "jvmDescriptor");
        return this.signature(MethodSignatureMappingKt.getInternalName(classDescriptor), jvmDescriptor2);
    }

    @NotNull
    public final String signature(@NotNull String internalName, @NotNull String jvmDescriptor2) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        Intrinsics.checkNotNullParameter(jvmDescriptor2, "jvmDescriptor");
        return internalName + '.' + jvmDescriptor2;
    }

    @NotNull
    public final String jvmDescriptor(@NotNull String name, @NotNull List<String> parameters2, @NotNull String ret) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(parameters2, "parameters");
        Intrinsics.checkNotNullParameter(ret, "ret");
        return name + '(' + CollectionsKt.joinToString$default(parameters2, "", null, null, 0, null, jvmDescriptor.1.INSTANCE, 30, null) + ')' + this.escapeClassName(ret);
    }

    private final String escapeClassName(String internalName) {
        return internalName.length() > 1 ? 'L' + internalName + ';' : internalName;
    }

    private SignatureBuildingComponents() {
    }

    static {
        SignatureBuildingComponents signatureBuildingComponents;
        INSTANCE = signatureBuildingComponents = new SignatureBuildingComponents();
    }

    public static final /* synthetic */ String access$escapeClassName(SignatureBuildingComponents $this, String internalName) {
        return $this.escapeClassName(internalName);
    }
}

