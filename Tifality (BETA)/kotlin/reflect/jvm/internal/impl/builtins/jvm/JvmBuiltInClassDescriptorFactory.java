/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.util.Collection;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.CloneableClassScope;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JvmBuiltInClassDescriptorFactory
implements ClassDescriptorFactory {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private final NotNullLazyValue cloneable$delegate;
    private final ModuleDescriptor moduleDescriptor;
    private final Function1<ModuleDescriptor, DeclarationDescriptor> computeContainingDeclaration;
    private static final FqName KOTLIN_FQ_NAME;
    private static final Name CLONEABLE_NAME;
    @NotNull
    private static final ClassId CLONEABLE_CLASS_ID;
    public static final Companion Companion;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBuiltInClassDescriptorFactory.class), "cloneable", "getCloneable()Lorg/jetbrains/kotlin/descriptors/impl/ClassDescriptorImpl;"))};
        Companion = new Companion(null);
        KOTLIN_FQ_NAME = KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME;
        Name name = KotlinBuiltIns.FQ_NAMES.cloneable.shortName();
        Intrinsics.checkNotNullExpressionValue(name, "KotlinBuiltIns.FQ_NAMES.cloneable.shortName()");
        CLONEABLE_NAME = name;
        ClassId classId = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.cloneable.toSafe());
        Intrinsics.checkNotNullExpressionValue(classId, "ClassId.topLevel(KotlinB\u2026NAMES.cloneable.toSafe())");
        CLONEABLE_CLASS_ID = classId;
    }

    private final ClassDescriptorImpl getCloneable() {
        return (ClassDescriptorImpl)StorageKt.getValue(this.cloneable$delegate, (Object)this, $$delegatedProperties[0]);
    }

    @Override
    public boolean shouldCreateClass(@NotNull FqName packageFqName, @NotNull Name name) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name, "name");
        return Intrinsics.areEqual(name, CLONEABLE_NAME) && Intrinsics.areEqual(packageFqName, KOTLIN_FQ_NAME);
    }

    @Override
    @Nullable
    public ClassDescriptor createClass(@NotNull ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        ClassId classId2 = classId;
        return Intrinsics.areEqual(classId2, CLONEABLE_CLASS_ID) ? this.getCloneable() : null;
    }

    @Override
    @NotNull
    public Collection<ClassDescriptor> getAllContributedClassesIfPossible(@NotNull FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        FqName fqName2 = packageFqName;
        return Intrinsics.areEqual(fqName2, KOTLIN_FQ_NAME) ? SetsKt.setOf(this.getCloneable()) : SetsKt.emptySet();
    }

    public JvmBuiltInClassDescriptorFactory(@NotNull StorageManager storageManager, @NotNull ModuleDescriptor moduleDescriptor, @NotNull Function1<? super ModuleDescriptor, ? extends DeclarationDescriptor> computeContainingDeclaration) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(computeContainingDeclaration, "computeContainingDeclaration");
        this.moduleDescriptor = moduleDescriptor;
        this.computeContainingDeclaration = computeContainingDeclaration;
        this.cloneable$delegate = storageManager.createLazyValue((Function0)new Function0<ClassDescriptorImpl>(this, storageManager){
            final /* synthetic */ JvmBuiltInClassDescriptorFactory this$0;
            final /* synthetic */ StorageManager $storageManager;

            @NotNull
            public final ClassDescriptorImpl invoke() {
                ClassDescriptorImpl classDescriptorImpl = new ClassDescriptorImpl((DeclarationDescriptor)JvmBuiltInClassDescriptorFactory.access$getComputeContainingDeclaration$p(this.this$0).invoke(JvmBuiltInClassDescriptorFactory.access$getModuleDescriptor$p(this.this$0)), JvmBuiltInClassDescriptorFactory.access$getCLONEABLE_NAME$cp(), Modality.ABSTRACT, ClassKind.INTERFACE, (Collection<KotlinType>)CollectionsKt.listOf(JvmBuiltInClassDescriptorFactory.access$getModuleDescriptor$p(this.this$0).getBuiltIns().getAnyType()), SourceElement.NO_SOURCE, false, this.$storageManager);
                boolean bl = false;
                boolean bl2 = false;
                ClassDescriptorImpl $this$apply = classDescriptorImpl;
                boolean bl3 = false;
                $this$apply.initialize(new CloneableClassScope(this.$storageManager, $this$apply), SetsKt.<ClassConstructorDescriptor>emptySet(), null);
                return classDescriptorImpl;
            }
            {
                this.this$0 = jvmBuiltInClassDescriptorFactory;
                this.$storageManager = storageManager;
                super(0);
            }
        });
    }

    public /* synthetic */ JvmBuiltInClassDescriptorFactory(StorageManager storageManager, ModuleDescriptor moduleDescriptor, Function1 function1, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            function1 = 1.INSTANCE;
        }
        this(storageManager, moduleDescriptor, function1);
    }

    public static final /* synthetic */ Function1 access$getComputeContainingDeclaration$p(JvmBuiltInClassDescriptorFactory $this) {
        return $this.computeContainingDeclaration;
    }

    public static final /* synthetic */ ModuleDescriptor access$getModuleDescriptor$p(JvmBuiltInClassDescriptorFactory $this) {
        return $this.moduleDescriptor;
    }

    public static final /* synthetic */ Name access$getCLONEABLE_NAME$cp() {
        return CLONEABLE_NAME;
    }

    public static final class Companion {
        @NotNull
        public final ClassId getCLONEABLE_CLASS_ID() {
            return CLONEABLE_CLASS_ID;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

