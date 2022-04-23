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
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.ReflectionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassDescriptor$FunctionTypeConstructor$WhenMappings;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassScope;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.AbstractClassTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FunctionClassDescriptor
extends AbstractClassDescriptor {
    private final FunctionTypeConstructor typeConstructor;
    private final FunctionClassScope memberScope;
    private final List<TypeParameterDescriptor> parameters;
    private final StorageManager storageManager;
    private final PackageFragmentDescriptor containingDeclaration;
    @NotNull
    private final Kind functionKind;
    private final int arity;
    private static final ClassId functionClassId;
    private static final ClassId kFunctionClassId;
    public static final Companion Companion;

    @Override
    @NotNull
    public PackageFragmentDescriptor getContainingDeclaration() {
        return this.containingDeclaration;
    }

    @Override
    @NotNull
    public MemberScope.Empty getStaticScope() {
        return MemberScope.Empty.INSTANCE;
    }

    @Override
    @NotNull
    public TypeConstructor getTypeConstructor() {
        return this.typeConstructor;
    }

    @Override
    @NotNull
    protected FunctionClassScope getUnsubstitutedMemberScope(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this.memberScope;
    }

    @Nullable
    public Void getCompanionObjectDescriptor() {
        return null;
    }

    @NotNull
    public List<ClassConstructorDescriptor> getConstructors() {
        return CollectionsKt.emptyList();
    }

    @Override
    @NotNull
    public ClassKind getKind() {
        return ClassKind.INTERFACE;
    }

    @Override
    @NotNull
    public Modality getModality() {
        return Modality.ABSTRACT;
    }

    @Nullable
    public Void getUnsubstitutedPrimaryConstructor() {
        return null;
    }

    @Override
    @NotNull
    public Visibility getVisibility() {
        Visibility visibility = Visibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(visibility, "Visibilities.PUBLIC");
        return visibility;
    }

    @Override
    public boolean isCompanionObject() {
        return false;
    }

    @Override
    public boolean isInner() {
        return false;
    }

    @Override
    public boolean isData() {
        return false;
    }

    @Override
    public boolean isInline() {
        return false;
    }

    @Override
    public boolean isFun() {
        return false;
    }

    @Override
    public boolean isExpect() {
        return false;
    }

    @Override
    public boolean isActual() {
        return false;
    }

    @Override
    public boolean isExternal() {
        return false;
    }

    @Override
    @NotNull
    public Annotations getAnnotations() {
        return Annotations.Companion.getEMPTY();
    }

    @Override
    @NotNull
    public SourceElement getSource() {
        SourceElement sourceElement = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(sourceElement, "SourceElement.NO_SOURCE");
        return sourceElement;
    }

    @NotNull
    public List<ClassDescriptor> getSealedSubclasses() {
        return CollectionsKt.emptyList();
    }

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        return this.parameters;
    }

    @NotNull
    public String toString() {
        String string = this.getName().asString();
        Intrinsics.checkNotNullExpressionValue(string, "name.asString()");
        return string;
    }

    @NotNull
    public final Kind getFunctionKind() {
        return this.functionKind;
    }

    public final int getArity() {
        return this.arity;
    }

    /*
     * WARNING - void declaration
     */
    public FunctionClassDescriptor(@NotNull StorageManager storageManager, @NotNull PackageFragmentDescriptor containingDeclaration, @NotNull Kind functionKind, int arity) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter((Object)functionKind, "functionKind");
        super(storageManager, functionKind.numberedClassName(arity));
        this.storageManager = storageManager;
        this.containingDeclaration = containingDeclaration;
        this.functionKind = functionKind;
        this.arity = arity;
        this.typeConstructor = new FunctionTypeConstructor();
        this.memberScope = new FunctionClassScope(this.storageManager, this);
        final ArrayList result2 = new ArrayList();
        Function2<Variance, String, Unit> $fun$typeParameter$1 = new Function2<Variance, String, Unit>(){

            @Override
            public final void invoke(@NotNull Variance variance, @NotNull String name) {
                Intrinsics.checkNotNullParameter((Object)variance, "variance");
                Intrinsics.checkNotNullParameter(name, "name");
                result2.add(TypeParameterDescriptorImpl.createWithDefaultBound(this, Annotations.Companion.getEMPTY(), false, variance, Name.identifier(name), result2.size(), storageManager));
            }
        };
        int n = 1;
        Iterable $this$map$iv = new IntRange(n, this.arity);
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        Iterator iterator2 = $this$mapTo$iv$iv.iterator();
        while (iterator2.hasNext()) {
            void i;
            int item$iv$iv;
            int n2 = item$iv$iv = ((IntIterator)iterator2).nextInt();
            Collection collection = destination$iv$iv;
            boolean bl = false;
            $fun$typeParameter$1.invoke(Variance.IN_VARIANCE, "" + 'P' + (int)i);
            Unit unit = Unit.INSTANCE;
            collection.add(unit);
        }
        List cfr_ignored_0 = (List)destination$iv$iv;
        $fun$typeParameter$1.invoke(Variance.OUT_VARIANCE, "R");
        this.parameters = CollectionsKt.toList(result2);
    }

    static {
        Companion = new Companion(null);
        functionClassId = new ClassId(KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME, Name.identifier("Function"));
        kFunctionClassId = new ClassId(ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME(), Name.identifier("KFunction"));
    }

    public static final class Kind
    extends Enum<Kind> {
        public static final /* enum */ Kind Function;
        public static final /* enum */ Kind SuspendFunction;
        public static final /* enum */ Kind KFunction;
        public static final /* enum */ Kind KSuspendFunction;
        private static final /* synthetic */ Kind[] $VALUES;
        @NotNull
        private final FqName packageFqName;
        @NotNull
        private final String classNamePrefix;
        public static final Companion Companion;

        static {
            Kind[] kindArray = new Kind[4];
            Kind[] kindArray2 = kindArray;
            FqName fqName2 = KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME;
            Intrinsics.checkNotNullExpressionValue(fqName2, "BUILT_INS_PACKAGE_FQ_NAME");
            kindArray[0] = Function = new Kind(fqName2, "Function");
            FqName fqName3 = DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE;
            Intrinsics.checkNotNullExpressionValue(fqName3, "COROUTINES_PACKAGE_FQ_NAME_RELEASE");
            kindArray[1] = SuspendFunction = new Kind(fqName3, "SuspendFunction");
            kindArray[2] = KFunction = new Kind(ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME(), "KFunction");
            kindArray[3] = KSuspendFunction = new Kind(ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME(), "KSuspendFunction");
            $VALUES = kindArray;
            Companion = new Companion(null);
        }

        @NotNull
        public final Name numberedClassName(int arity) {
            Name name = Name.identifier(this.classNamePrefix + arity);
            Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(\"$classNamePrefix$arity\")");
            return name;
        }

        @NotNull
        public final FqName getPackageFqName() {
            return this.packageFqName;
        }

        @NotNull
        public final String getClassNamePrefix() {
            return this.classNamePrefix;
        }

        private Kind(FqName packageFqName, String classNamePrefix) {
            this.packageFqName = packageFqName;
            this.classNamePrefix = classNamePrefix;
        }

        public static Kind[] values() {
            return (Kind[])$VALUES.clone();
        }

        public static Kind valueOf(String string) {
            return Enum.valueOf(Kind.class, string);
        }

        public static final class Companion {
            @Nullable
            public final Kind byClassNamePrefix(@NotNull FqName packageFqName, @NotNull String className) {
                Kind kind;
                block1: {
                    Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                    Intrinsics.checkNotNullParameter(className, "className");
                    Kind[] $this$firstOrNull$iv = Kind.values();
                    boolean $i$f$firstOrNull = false;
                    Kind[] kindArray = $this$firstOrNull$iv;
                    int n = kindArray.length;
                    for (int i = 0; i < n; ++i) {
                        Kind element$iv;
                        Kind it = element$iv = kindArray[i];
                        boolean bl = false;
                        if (!(Intrinsics.areEqual(it.getPackageFqName(), packageFqName) && StringsKt.startsWith$default(className, it.getClassNamePrefix(), false, 2, null))) continue;
                        kind = element$iv;
                        break block1;
                    }
                    kind = null;
                }
                return kind;
            }

            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }
    }

    private final class FunctionTypeConstructor
    extends AbstractClassTypeConstructor {
        /*
         * WARNING - void declaration
         */
        @Override
        @NotNull
        protected Collection<KotlinType> computeSupertypes() {
            void $this$mapTo$iv$iv;
            List<ClassId> list;
            switch (FunctionClassDescriptor$FunctionTypeConstructor$WhenMappings.$EnumSwitchMapping$0[FunctionClassDescriptor.this.getFunctionKind().ordinal()]) {
                case 1: {
                    list = CollectionsKt.listOf(functionClassId);
                    break;
                }
                case 2: {
                    list = CollectionsKt.listOf(kFunctionClassId, new ClassId(KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME, Kind.Function.numberedClassName(FunctionClassDescriptor.this.getArity())));
                    break;
                }
                case 3: {
                    list = CollectionsKt.listOf(functionClassId);
                    break;
                }
                case 4: {
                    list = CollectionsKt.listOf(kFunctionClassId, new ClassId(DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE, Kind.SuspendFunction.numberedClassName(FunctionClassDescriptor.this.getArity())));
                    break;
                }
                default: {
                    throw new NoWhenBranchMatchedException();
                }
            }
            List<ClassId> supertypes2 = list;
            ModuleDescriptor moduleDescriptor = FunctionClassDescriptor.this.containingDeclaration.getContainingDeclaration();
            Iterable $this$map$iv = supertypes2;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void $this$mapTo$iv$iv2;
                ClassDescriptor descriptor2;
                void id;
                ClassId classId = (ClassId)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                if (FindClassInModuleKt.findClassAcrossModuleDependencies(moduleDescriptor, (ClassId)id) == null) {
                    String string = "Built-in class " + id + " not found";
                    boolean bl2 = false;
                    throw (Throwable)new IllegalStateException(string.toString());
                }
                List<TypeParameterDescriptor> list2 = this.getParameters();
                TypeConstructor typeConstructor2 = descriptor2.getTypeConstructor();
                Intrinsics.checkNotNullExpressionValue(typeConstructor2, "descriptor.typeConstructor");
                Iterable $this$map$iv2 = CollectionsKt.takeLast(list2, typeConstructor2.getParameters().size());
                boolean $i$f$map2 = false;
                Iterable iterable2 = $this$map$iv2;
                Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
                boolean $i$f$mapTo2 = false;
                for (Object item$iv$iv2 : $this$mapTo$iv$iv2) {
                    void it;
                    TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor)item$iv$iv2;
                    Collection collection2 = destination$iv$iv2;
                    boolean bl3 = false;
                    TypeProjectionImpl typeProjectionImpl = new TypeProjectionImpl(it.getDefaultType());
                    collection2.add(typeProjectionImpl);
                }
                List arguments2 = (List)destination$iv$iv2;
                SimpleType simpleType2 = KotlinTypeFactory.simpleNotNullType(Annotations.Companion.getEMPTY(), descriptor2, arguments2);
                collection.add(simpleType2);
            }
            return CollectionsKt.toList((List)destination$iv$iv);
        }

        @Override
        @NotNull
        public List<TypeParameterDescriptor> getParameters() {
            return FunctionClassDescriptor.this.parameters;
        }

        @Override
        @NotNull
        public FunctionClassDescriptor getDeclarationDescriptor() {
            return FunctionClassDescriptor.this;
        }

        @Override
        public boolean isDenotable() {
            return true;
        }

        @NotNull
        public String toString() {
            return this.getDeclarationDescriptor().toString();
        }

        @Override
        @NotNull
        protected SupertypeLoopChecker getSupertypeLoopChecker() {
            return SupertypeLoopChecker.EMPTY.INSTANCE;
        }

        public FunctionTypeConstructor() {
            super(FunctionClassDescriptor.this.storageManager);
        }
    }

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

