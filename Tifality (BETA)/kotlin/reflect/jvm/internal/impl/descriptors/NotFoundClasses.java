/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassOrPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterUtilsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorBase;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.EmptyPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.ClassTypeConstructorImpl;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NotFoundClasses {
    private final MemoizedFunctionToNotNull<FqName, PackageFragmentDescriptor> packageFragments;
    private final MemoizedFunctionToNotNull<ClassRequest, ClassDescriptor> classes;
    private final StorageManager storageManager;
    private final ModuleDescriptor module;

    @NotNull
    public final ClassDescriptor getClass(@NotNull ClassId classId, @NotNull List<Integer> typeParametersCount2) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(typeParametersCount2, "typeParametersCount");
        return (ClassDescriptor)this.classes.invoke(new ClassRequest(classId, typeParametersCount2));
    }

    public NotFoundClasses(@NotNull StorageManager storageManager, @NotNull ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        this.storageManager = storageManager;
        this.module = module;
        this.packageFragments = this.storageManager.createMemoizedFunction((Function1)new Function1<FqName, PackageFragmentDescriptor>(this){
            final /* synthetic */ NotFoundClasses this$0;

            @NotNull
            public final PackageFragmentDescriptor invoke(@NotNull FqName fqName2) {
                Intrinsics.checkNotNullParameter(fqName2, "fqName");
                return new EmptyPackageFragmentDescriptor(NotFoundClasses.access$getModule$p(this.this$0), fqName2);
            }
            {
                this.this$0 = notFoundClasses;
                super(1);
            }
        });
        this.classes = this.storageManager.createMemoizedFunction((Function1)new Function1<ClassRequest, ClassDescriptor>(this){
            final /* synthetic */ NotFoundClasses this$0;

            /*
             * Unable to fully structure code
             */
            @NotNull
            public final ClassDescriptor invoke(@NotNull ClassRequest $dstr$classId$typeParametersCount) {
                Intrinsics.checkNotNullParameter($dstr$classId$typeParametersCount, "<name for destructuring parameter 0>");
                var2_2 = $dstr$classId$typeParametersCount.component1();
                typeParametersCount = $dstr$classId$typeParametersCount.component2();
                if (classId.isLocal()) {
                    throw (Throwable)new UnsupportedOperationException("Unresolved local class: " + classId);
                }
                v0 = classId.getOuterClassId();
                if (v0 == null) ** GOTO lbl-1000
                var5_4 = v0;
                var6_6 = false;
                var7_7 = false;
                outerClassId = var5_4;
                $i$a$-let-NotFoundClasses$classes$1$container$1 = false;
                v1 = outerClassId;
                Intrinsics.checkNotNullExpressionValue(v1, "outerClassId");
                v0 = this.this$0.getClass(v1, CollectionsKt.drop((Iterable)typeParametersCount, 1));
                if (v0 != null) {
                    v2 = (ClassOrPackageFragmentDescriptor)v0;
                } else lbl-1000:
                // 2 sources

                {
                    v3 = NotFoundClasses.access$getPackageFragments$p(this.this$0);
                    v4 = classId.getPackageFqName();
                    Intrinsics.checkNotNullExpressionValue(v4, "classId.packageFqName");
                    v2 = (ClassOrPackageFragmentDescriptor)v3.invoke(v4);
                }
                container = v2;
                isInner = classId.isNestedClass();
                v5 = NotFoundClasses.access$getStorageManager$p(this.this$0);
                v6 = container;
                v7 = classId.getShortClassName();
                Intrinsics.checkNotNullExpressionValue(v7, "classId.shortClassName");
                v8 = CollectionsKt.firstOrNull(typeParametersCount);
                return new MockClassDescriptor(v5, v6, v7, isInner, v8 != null ? v8 : 0);
            }
            {
                this.this$0 = notFoundClasses;
                super(1);
            }
        });
    }

    public static final /* synthetic */ ModuleDescriptor access$getModule$p(NotFoundClasses $this) {
        return $this.module;
    }

    public static final /* synthetic */ MemoizedFunctionToNotNull access$getPackageFragments$p(NotFoundClasses $this) {
        return $this.packageFragments;
    }

    public static final /* synthetic */ StorageManager access$getStorageManager$p(NotFoundClasses $this) {
        return $this.storageManager;
    }

    private static final class ClassRequest {
        @NotNull
        private final ClassId classId;
        @NotNull
        private final List<Integer> typeParametersCount;

        public ClassRequest(@NotNull ClassId classId, @NotNull List<Integer> typeParametersCount2) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            Intrinsics.checkNotNullParameter(typeParametersCount2, "typeParametersCount");
            this.classId = classId;
            this.typeParametersCount = typeParametersCount2;
        }

        @NotNull
        public final ClassId component1() {
            return this.classId;
        }

        @NotNull
        public final List<Integer> component2() {
            return this.typeParametersCount;
        }

        @NotNull
        public String toString() {
            return "ClassRequest(classId=" + this.classId + ", typeParametersCount=" + this.typeParametersCount + ")";
        }

        public int hashCode() {
            ClassId classId = this.classId;
            List<Integer> list = this.typeParametersCount;
            return (classId != null ? ((Object)classId).hashCode() : 0) * 31 + (list != null ? ((Object)list).hashCode() : 0);
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof ClassRequest)) break block3;
                    ClassRequest classRequest = (ClassRequest)object;
                    if (!Intrinsics.areEqual(this.classId, classRequest.classId) || !Intrinsics.areEqual(this.typeParametersCount, classRequest.typeParametersCount)) break block3;
                }
                return true;
            }
            return false;
        }
    }

    public static final class MockClassDescriptor
    extends ClassDescriptorBase {
        private final List<TypeParameterDescriptor> declaredTypeParameters;
        private final ClassTypeConstructorImpl typeConstructor;
        private final boolean isInner;

        @Override
        @NotNull
        public ClassKind getKind() {
            return ClassKind.CLASS;
        }

        @Override
        @NotNull
        public Modality getModality() {
            return Modality.FINAL;
        }

        @Override
        @NotNull
        public Visibility getVisibility() {
            Visibility visibility = Visibilities.PUBLIC;
            Intrinsics.checkNotNullExpressionValue(visibility, "Visibilities.PUBLIC");
            return visibility;
        }

        @Override
        @NotNull
        public ClassTypeConstructorImpl getTypeConstructor() {
            return this.typeConstructor;
        }

        @Override
        @NotNull
        public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
            return this.declaredTypeParameters;
        }

        @Override
        public boolean isInner() {
            return this.isInner;
        }

        @Override
        public boolean isCompanionObject() {
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
        protected MemberScope.Empty getUnsubstitutedMemberScope(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
            return MemberScope.Empty.INSTANCE;
        }

        @Override
        @NotNull
        public MemberScope.Empty getStaticScope() {
            return MemberScope.Empty.INSTANCE;
        }

        @Override
        @NotNull
        public Collection<ClassConstructorDescriptor> getConstructors() {
            return SetsKt.emptySet();
        }

        @Override
        @Nullable
        public ClassConstructorDescriptor getUnsubstitutedPrimaryConstructor() {
            return null;
        }

        @Override
        @Nullable
        public ClassDescriptor getCompanionObjectDescriptor() {
            return null;
        }

        @Override
        @NotNull
        public Collection<ClassDescriptor> getSealedSubclasses() {
            return CollectionsKt.emptyList();
        }

        @NotNull
        public String toString() {
            return "class " + this.getName() + " (not found)";
        }

        /*
         * WARNING - void declaration
         */
        public MockClassDescriptor(@NotNull StorageManager storageManager, @NotNull DeclarationDescriptor container, @NotNull Name name, boolean isInner2, int numberOfDeclaredTypeParameters) {
            Collection<TypeParameterDescriptor> collection;
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            Intrinsics.checkNotNullParameter(container, "container");
            Intrinsics.checkNotNullParameter(name, "name");
            super(storageManager, container, name, SourceElement.NO_SOURCE, false);
            this.isInner = isInner2;
            Iterable iterable = RangesKt.until(0, numberOfDeclaredTypeParameters);
            MockClassDescriptor mockClassDescriptor = this;
            boolean $i$f$map = false;
            void var8_9 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            Iterator iterator2 = $this$mapTo$iv$iv.iterator();
            while (iterator2.hasNext()) {
                void index;
                int item$iv$iv;
                int n = item$iv$iv = ((IntIterator)iterator2).nextInt();
                collection = destination$iv$iv;
                boolean bl = false;
                TypeParameterDescriptor typeParameterDescriptor = TypeParameterDescriptorImpl.createWithDefaultBound(this, Annotations.Companion.getEMPTY(), false, Variance.INVARIANT, Name.identifier("" + 'T' + (int)index), (int)index, storageManager);
                collection.add(typeParameterDescriptor);
            }
            collection = (List)destination$iv$iv;
            mockClassDescriptor.declaredTypeParameters = collection;
            this.typeConstructor = new ClassTypeConstructorImpl(this, TypeParameterUtilsKt.computeConstructorTypeParameters(this), (Collection<KotlinType>)SetsKt.setOf(DescriptorUtilsKt.getModule(this).getBuiltIns().getAnyType()), storageManager);
        }
    }
}

