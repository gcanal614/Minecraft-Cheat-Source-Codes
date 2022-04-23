/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.ReflectionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectionTypes {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private final Lazy kotlinReflectScope$delegate;
    @NotNull
    private final ClassLookup kClass$delegate;
    @NotNull
    private final ClassLookup kProperty$delegate;
    @NotNull
    private final ClassLookup kProperty0$delegate;
    @NotNull
    private final ClassLookup kProperty1$delegate;
    @NotNull
    private final ClassLookup kProperty2$delegate;
    @NotNull
    private final ClassLookup kMutableProperty0$delegate;
    @NotNull
    private final ClassLookup kMutableProperty1$delegate;
    @NotNull
    private final ClassLookup kMutableProperty2$delegate;
    private final NotFoundClasses notFoundClasses;
    public static final Companion Companion;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kClass", "getKClass()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kProperty", "getKProperty()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kProperty0", "getKProperty0()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kProperty1", "getKProperty1()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kProperty2", "getKProperty2()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kMutableProperty0", "getKMutableProperty0()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kMutableProperty1", "getKMutableProperty1()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ReflectionTypes.class), "kMutableProperty2", "getKMutableProperty2()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;"))};
        Companion = new Companion(null);
    }

    private final MemberScope getKotlinReflectScope() {
        Lazy lazy = this.kotlinReflectScope$delegate;
        ReflectionTypes reflectionTypes = this;
        Object var3_3 = null;
        boolean bl = false;
        return (MemberScope)lazy.getValue();
    }

    private final ClassDescriptor find(String className, int numberOfTypeParameters) {
        ClassDescriptor classDescriptor;
        Name name = Name.identifier(className);
        Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(className)");
        Name name2 = name;
        ClassifierDescriptor classifierDescriptor = this.getKotlinReflectScope().getContributedClassifier(name2, NoLookupLocation.FROM_REFLECTION);
        if (!(classifierDescriptor instanceof ClassDescriptor)) {
            classifierDescriptor = null;
        }
        if ((classDescriptor = (ClassDescriptor)classifierDescriptor) == null) {
            classDescriptor = this.notFoundClasses.getClass(new ClassId(ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME(), name2), CollectionsKt.listOf(numberOfTypeParameters));
        }
        return classDescriptor;
    }

    @NotNull
    public final ClassDescriptor getKClass() {
        return this.kClass$delegate.getValue(this, $$delegatedProperties[0]);
    }

    public ReflectionTypes(@NotNull ModuleDescriptor module, @NotNull NotFoundClasses notFoundClasses) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        this.notFoundClasses = notFoundClasses;
        this.kotlinReflectScope$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0)new Function0<MemberScope>(module){
            final /* synthetic */ ModuleDescriptor $module;

            @NotNull
            public final MemberScope invoke() {
                return this.$module.getPackage(ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME()).getMemberScope();
            }
            {
                this.$module = moduleDescriptor;
                super(0);
            }
        });
        this.kClass$delegate = new ClassLookup(1);
        this.kProperty$delegate = new ClassLookup(1);
        this.kProperty0$delegate = new ClassLookup(1);
        this.kProperty1$delegate = new ClassLookup(2);
        this.kProperty2$delegate = new ClassLookup(3);
        this.kMutableProperty0$delegate = new ClassLookup(1);
        this.kMutableProperty1$delegate = new ClassLookup(2);
        this.kMutableProperty2$delegate = new ClassLookup(3);
    }

    private static final class ClassLookup {
        private final int numberOfTypeParameters;

        @NotNull
        public final ClassDescriptor getValue(@NotNull ReflectionTypes types, @NotNull KProperty<?> property) {
            Intrinsics.checkNotNullParameter(types, "types");
            Intrinsics.checkNotNullParameter(property, "property");
            return types.find(StringsKt.capitalize(property.getName()), this.numberOfTypeParameters);
        }

        public ClassLookup(int numberOfTypeParameters) {
            this.numberOfTypeParameters = numberOfTypeParameters;
        }
    }

    public static final class Companion {
        @Nullable
        public final KotlinType createKPropertyStarType(@NotNull ModuleDescriptor module) {
            Intrinsics.checkNotNullParameter(module, "module");
            ClassId classId = KotlinBuiltIns.FQ_NAMES.kProperty;
            Intrinsics.checkNotNullExpressionValue(classId, "KotlinBuiltIns.FQ_NAMES.kProperty");
            ClassDescriptor classDescriptor = FindClassInModuleKt.findClassAcrossModuleDependencies(module, classId);
            if (classDescriptor == null) {
                return null;
            }
            ClassDescriptor kPropertyClass = classDescriptor;
            Annotations annotations2 = Annotations.Companion.getEMPTY();
            TypeConstructor typeConstructor2 = kPropertyClass.getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor2, "kPropertyClass.typeConstructor");
            List<TypeParameterDescriptor> list = typeConstructor2.getParameters();
            Intrinsics.checkNotNullExpressionValue(list, "kPropertyClass.typeConstructor.parameters");
            TypeParameterDescriptor typeParameterDescriptor = CollectionsKt.single(list);
            Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "kPropertyClass.typeConstructor.parameters.single()");
            return KotlinTypeFactory.simpleNotNullType(annotations2, kPropertyClass, CollectionsKt.listOf(new StarProjectionImpl(typeParameterDescriptor)));
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

