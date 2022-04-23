/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.builtins.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.BuiltInsPackageFragment;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionInterfacePackageFragment;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BuiltInFictitiousFunctionClassFactory
implements ClassDescriptorFactory {
    private final StorageManager storageManager;
    private final ModuleDescriptor module;
    public static final Companion Companion = new Companion(null);

    @Override
    public boolean shouldCreateClass(@NotNull FqName packageFqName, @NotNull Name name) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name, "name");
        String string = name.asString();
        Intrinsics.checkNotNullExpressionValue(string, "name.asString()");
        String string2 = string;
        return (StringsKt.startsWith$default(string2, "Function", false, 2, null) || StringsKt.startsWith$default(string2, "KFunction", false, 2, null) || StringsKt.startsWith$default(string2, "SuspendFunction", false, 2, null) || StringsKt.startsWith$default(string2, "KSuspendFunction", false, 2, null)) && BuiltInFictitiousFunctionClassFactory.Companion.parseClassName(string2, packageFqName) != null;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @Nullable
    public ClassDescriptor createClass(@NotNull ClassId classId) {
        void kind;
        void $this$filterIsInstanceTo$iv$iv;
        void $this$filterIsInstanceTo$iv$iv2;
        Intrinsics.checkNotNullParameter(classId, "classId");
        if (classId.isLocal() || classId.isNestedClass()) {
            return null;
        }
        String string = classId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(string, "classId.relativeClassName.asString()");
        String className = string;
        if (!StringsKt.contains$default((CharSequence)className, "Function", false, 2, null)) {
            return null;
        }
        FqName fqName2 = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(fqName2, "classId.packageFqName");
        FqName packageFqName = fqName2;
        KindWithArity kindWithArity = BuiltInFictitiousFunctionClassFactory.Companion.parseClassName(className, packageFqName);
        if (kindWithArity == null) {
            return null;
        }
        KindWithArity kindWithArity2 = kindWithArity;
        FunctionClassDescriptor.Kind kind2 = kindWithArity2.component1();
        int arity = kindWithArity2.component2();
        Iterable $this$filterIsInstance$iv = this.module.getPackage(packageFqName).getFragments();
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv;
        Iterable destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv2) {
            if (!(element$iv$iv instanceof BuiltInsPackageFragment)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List builtInsFragments = (List)destination$iv$iv;
        Iterable $this$filterIsInstance$iv2 = builtInsFragments;
        boolean $i$f$filterIsInstance2 = false;
        destination$iv$iv = $this$filterIsInstance$iv2;
        Collection destination$iv$iv2 = new ArrayList();
        boolean $i$f$filterIsInstanceTo2 = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof FunctionInterfacePackageFragment)) continue;
            destination$iv$iv2.add(element$iv$iv);
        }
        FunctionInterfacePackageFragment functionInterfacePackageFragment = (FunctionInterfacePackageFragment)CollectionsKt.firstOrNull((List)destination$iv$iv2);
        BuiltInsPackageFragment containingPackageFragment = functionInterfacePackageFragment != null ? (BuiltInsPackageFragment)functionInterfacePackageFragment : (BuiltInsPackageFragment)CollectionsKt.first(builtInsFragments);
        return new FunctionClassDescriptor(this.storageManager, containingPackageFragment, (FunctionClassDescriptor.Kind)kind, arity);
    }

    @Override
    @NotNull
    public Collection<ClassDescriptor> getAllContributedClassesIfPossible(@NotNull FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return SetsKt.emptySet();
    }

    public BuiltInFictitiousFunctionClassFactory(@NotNull StorageManager storageManager, @NotNull ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        this.storageManager = storageManager;
        this.module = module;
    }

    private static final class KindWithArity {
        @NotNull
        private final FunctionClassDescriptor.Kind kind;
        private final int arity;

        @NotNull
        public final FunctionClassDescriptor.Kind getKind() {
            return this.kind;
        }

        public KindWithArity(@NotNull FunctionClassDescriptor.Kind kind, int arity) {
            Intrinsics.checkNotNullParameter((Object)kind, "kind");
            this.kind = kind;
            this.arity = arity;
        }

        @NotNull
        public final FunctionClassDescriptor.Kind component1() {
            return this.kind;
        }

        public final int component2() {
            return this.arity;
        }

        @NotNull
        public String toString() {
            return "KindWithArity(kind=" + (Object)((Object)this.kind) + ", arity=" + this.arity + ")";
        }

        public int hashCode() {
            FunctionClassDescriptor.Kind kind = this.kind;
            return (kind != null ? ((Object)((Object)kind)).hashCode() : 0) * 31 + this.arity;
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof KindWithArity)) break block3;
                    KindWithArity kindWithArity = (KindWithArity)object;
                    if (!Intrinsics.areEqual((Object)this.kind, (Object)kindWithArity.kind) || this.arity != kindWithArity.arity) break block3;
                }
                return true;
            }
            return false;
        }
    }

    public static final class Companion {
        private final KindWithArity parseClassName(String className, FqName packageFqName) {
            FunctionClassDescriptor.Kind kind = FunctionClassDescriptor.Kind.Companion.byClassNamePrefix(packageFqName, className);
            if (kind == null) {
                return null;
            }
            FunctionClassDescriptor.Kind kind2 = kind;
            String prefix = kind2.getClassNamePrefix();
            String string = className;
            int n = prefix.length();
            boolean bl = false;
            String string2 = string;
            if (string2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string3 = string2.substring(n);
            Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.String).substring(startIndex)");
            Integer n2 = this.toInt(string3);
            if (n2 == null) {
                return null;
            }
            int arity = n2;
            return new KindWithArity(kind2, arity);
        }

        @JvmStatic
        @Nullable
        public final FunctionClassDescriptor.Kind getFunctionalClassKind(@NotNull String className, @NotNull FqName packageFqName) {
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            KindWithArity kindWithArity = this.parseClassName(className, packageFqName);
            return kindWithArity != null ? kindWithArity.getKind() : null;
        }

        private final Integer toInt(String s) {
            CharSequence charSequence = s;
            boolean bl = false;
            if (charSequence.length() == 0) {
                return null;
            }
            int result2 = 0;
            String string = s;
            int n = string.length();
            for (int i = 0; i < n; ++i) {
                char c = string.charAt(i);
                int d = c - 48;
                int n2 = d;
                if (0 > n2 || 9 < n2) {
                    return null;
                }
                result2 = result2 * 10 + d;
            }
            return result2;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

