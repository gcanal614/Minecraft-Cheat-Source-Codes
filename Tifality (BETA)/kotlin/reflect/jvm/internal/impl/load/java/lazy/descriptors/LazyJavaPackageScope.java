/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaPackageFragment;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticScope;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.load.java.structure.LightClassOriginKind;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinderKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.NullableLazyValue;
import kotlin.reflect.jvm.internal.impl.utils.FunctionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LazyJavaPackageScope
extends LazyJavaStaticScope {
    private final NullableLazyValue<Set<String>> knownClassNamesInPackage;
    private final MemoizedFunctionToNullable<FindClassRequest, ClassDescriptor> classes;
    private final JavaPackage jPackage;
    @NotNull
    private final LazyJavaPackageFragment ownerDescriptor;

    private final KotlinClassLookupResult resolveKotlinBinaryClass(KotlinJvmBinaryClass kotlinClass2) {
        ClassDescriptor descriptor2;
        KotlinClassLookupResult kotlinClassLookupResult = kotlinClass2 == null ? (KotlinClassLookupResult)KotlinClassLookupResult.NotFound.INSTANCE : (kotlinClass2.getClassHeader().getKind() == KotlinClassHeader.Kind.CLASS ? ((descriptor2 = this.getC().getComponents().getDeserializedDescriptorResolver().resolveClass(kotlinClass2)) != null ? (KotlinClassLookupResult)new KotlinClassLookupResult.Found(descriptor2) : (KotlinClassLookupResult)KotlinClassLookupResult.NotFound.INSTANCE) : (KotlinClassLookupResult)KotlinClassLookupResult.SyntheticClass.INSTANCE);
        return kotlinClassLookupResult;
    }

    @Override
    @Nullable
    public ClassDescriptor getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.findClassifier(name, null);
    }

    private final ClassDescriptor findClassifier(Name name, JavaClass javaClass) {
        if (!SpecialNames.isSafeIdentifier(name)) {
            return null;
        }
        Set knownClassNamesInPackage2 = (Set)this.knownClassNamesInPackage.invoke();
        if (javaClass == null && knownClassNamesInPackage2 != null && !knownClassNamesInPackage2.contains(name.asString())) {
            return null;
        }
        return (ClassDescriptor)this.classes.invoke(new FindClassRequest(name, javaClass));
    }

    @Nullable
    public final ClassDescriptor findClassifierByJavaClass$descriptors_jvm(@NotNull JavaClass javaClass) {
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        return this.findClassifier(javaClass.getName(), javaClass);
    }

    @Override
    @NotNull
    public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return CollectionsKt.emptyList();
    }

    @Override
    @NotNull
    protected DeclaredMemberIndex computeMemberIndex() {
        return DeclaredMemberIndex.Empty.INSTANCE;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    protected Set<Name> computeClassNames(@NotNull DescriptorKindFilter kindFilter, @Nullable Function1<? super Name, Boolean> nameFilter) {
        void $this$mapNotNullTo$iv;
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        if (!kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getNON_SINGLETON_CLASSIFIERS_MASK())) {
            return SetsKt.emptySet();
        }
        Set knownClassNamesInPackage2 = (Set)this.knownClassNamesInPackage.invoke();
        if (knownClassNamesInPackage2 != null) {
            void $this$mapTo$iv;
            Iterable iterable = knownClassNamesInPackage2;
            Collection destination$iv = new HashSet();
            boolean $i$f$mapTo = false;
            for (Object item$iv : $this$mapTo$iv) {
                void it;
                String string = (String)item$iv;
                Collection collection = destination$iv;
                boolean bl = false;
                Name name = Name.identifier((String)it);
                collection.add(name);
            }
            return (Set)destination$iv;
        }
        Function1<Name, Boolean> function1 = nameFilter;
        if (function1 == null) {
            function1 = FunctionsKt.alwaysTrue();
        }
        Iterable $this$mapTo$iv = this.jPackage.getClasses(function1);
        boolean destination$iv = false;
        Collection destination$iv2 = new LinkedHashSet();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv = $this$mapNotNullTo$iv;
        boolean $i$f$forEach = false;
        Iterator iterator2 = $this$forEach$iv$iv.iterator();
        while (iterator2.hasNext()) {
            Name name;
            Object element$iv$iv;
            Object element$iv = element$iv$iv = iterator2.next();
            boolean bl = false;
            JavaClass klass = (JavaClass)element$iv;
            boolean bl2 = false;
            if ((klass.getLightClassOriginKind() == LightClassOriginKind.SOURCE ? null : klass.getName()) == null) continue;
            name = name;
            boolean bl3 = false;
            boolean bl4 = false;
            Name it$iv = name;
            boolean bl5 = false;
            destination$iv2.add(it$iv);
        }
        return (Set)destination$iv2;
    }

    @Override
    @NotNull
    protected Set<Name> computeFunctionNames(@NotNull DescriptorKindFilter kindFilter, @Nullable Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        return SetsKt.emptySet();
    }

    @Override
    protected void computeNonDeclaredFunctions(@NotNull Collection<SimpleFunctionDescriptor> result2, @NotNull Name name) {
        Intrinsics.checkNotNullParameter(result2, "result");
        Intrinsics.checkNotNullParameter(name, "name");
    }

    @Override
    @NotNull
    protected Set<Name> computePropertyNames(@NotNull DescriptorKindFilter kindFilter, @Nullable Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        return SetsKt.emptySet();
    }

    /*
     * Unable to fully structure code
     */
    @Override
    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        block4: {
            block3: {
                Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
                Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
                if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getCLASSIFIERS_MASK() | DescriptorKindFilter.Companion.getNON_SINGLETON_CLASSIFIERS_MASK())) break block3;
                v0 = CollectionsKt.emptyList();
                break block4;
            }
            $this$filter$iv = (Iterable)this.getAllDescriptors().invoke();
            $i$f$filter = false;
            var5_5 = $this$filter$iv;
            destination$iv$iv = new ArrayList<E>();
            $i$f$filterTo = false;
            for (T element$iv$iv : $this$filterTo$iv$iv) {
                it = (DeclarationDescriptor)element$iv$iv;
                $i$a$-filter-LazyJavaPackageScope$getContributedDescriptors$1 = false;
                if (!(it instanceof ClassDescriptor)) ** GOTO lbl-1000
                v1 = ((ClassDescriptor)it).getName();
                Intrinsics.checkNotNullExpressionValue(v1, "it.name");
                if (nameFilter.invoke(v1).booleanValue()) {
                    v2 = true;
                } else lbl-1000:
                // 2 sources

                {
                    v2 = false;
                }
                if (!v2) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            v0 = (List)destination$iv$iv;
        }
        return v0;
    }

    @Override
    @NotNull
    protected LazyJavaPackageFragment getOwnerDescriptor() {
        return this.ownerDescriptor;
    }

    public LazyJavaPackageScope(@NotNull LazyJavaResolverContext c, @NotNull JavaPackage jPackage, @NotNull LazyJavaPackageFragment ownerDescriptor) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(jPackage, "jPackage");
        Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
        super(c);
        this.jPackage = jPackage;
        this.ownerDescriptor = ownerDescriptor;
        this.knownClassNamesInPackage = c.getStorageManager().createNullableLazyValue((Function0)new Function0<Set<? extends String>>(this, c){
            final /* synthetic */ LazyJavaPackageScope this$0;
            final /* synthetic */ LazyJavaResolverContext $c;

            @Nullable
            public final Set<String> invoke() {
                return this.$c.getComponents().getFinder().knownClassNamesInPackage(this.this$0.getOwnerDescriptor().getFqName());
            }
            {
                this.this$0 = lazyJavaPackageScope;
                this.$c = lazyJavaResolverContext;
                super(0);
            }
        });
        this.classes = c.getStorageManager().createMemoizedFunctionWithNullableValues((Function1)new Function1<FindClassRequest, ClassDescriptor>(this, c){
            final /* synthetic */ LazyJavaPackageScope this$0;
            final /* synthetic */ LazyJavaResolverContext $c;

            /*
             * Unable to fully structure code
             */
            @Nullable
            public final ClassDescriptor invoke(@NotNull FindClassRequest request) {
                block8: {
                    block10: {
                        block11: {
                            block9: {
                                block7: {
                                    Intrinsics.checkNotNullParameter(request, "request");
                                    requestClassId = new ClassId(this.this$0.getOwnerDescriptor().getFqName(), request.getName());
                                    v0 = kotlinClassOrClassFileContent = request.getJavaClass() != null ? this.$c.getComponents().getKotlinClassFinder().findKotlinClassOrContent(request.getJavaClass()) : this.$c.getComponents().getKotlinClassFinder().findKotlinClassOrContent(requestClassId);
                                    v1 = kotlinBinaryClass = v0 != null ? v0.toKotlinJvmBinaryClass() : null;
                                    v2 = classId = v1 != null ? v1.getClassId() : null;
                                    if (classId != null && (classId.isNestedClass() || classId.isLocal())) {
                                        return null;
                                    }
                                    kotlinResult = LazyJavaPackageScope.access$resolveKotlinBinaryClass(this.this$0, kotlinBinaryClass);
                                    if (!(kotlinResult instanceof KotlinClassLookupResult.Found)) break block7;
                                    v3 = ((KotlinClassLookupResult.Found)kotlinResult).getDescriptor();
                                    break block8;
                                }
                                if (!(kotlinResult instanceof KotlinClassLookupResult.SyntheticClass)) break block9;
                                v3 = null;
                                break block8;
                            }
                            if (!(kotlinResult instanceof KotlinClassLookupResult.NotFound)) break block10;
                            v4 = request.getJavaClass();
                            if (v4 != null) break block11;
                            v5 = this.$c.getComponents().getFinder();
                            v6 = kotlinClassOrClassFileContent;
                            if (v6 == null) ** GOTO lbl-1000
                            $this$safeAs$iv = v6;
                            $i$f$safeAs = false;
                            v7 = $this$safeAs$iv;
                            if (!(v7 instanceof KotlinClassFinder.Result.ClassFileContent)) {
                                v7 = null;
                            }
                            if ((v6 = (KotlinClassFinder.Result.ClassFileContent)v7) != null) {
                                v8 = v6.getContent();
                            } else lbl-1000:
                            // 2 sources

                            {
                                v8 = null;
                            }
                            v4 = v5.findClass(new JavaClassFinder.Request(requestClassId, v8, null, 4, null));
                        }
                        v9 = javaClass = v4;
                        if ((v9 != null ? v9.getLightClassOriginKind() : null) == LightClassOriginKind.BINARY) {
                            throw (Throwable)new IllegalStateException("Couldn't find kotlin binary class for light class created by kotlin binary file\n" + "JavaClass: " + javaClass + '\n' + "ClassId: " + requestClassId + '\n' + "findKotlinClass(JavaClass) = " + KotlinClassFinderKt.findKotlinClass(this.$c.getComponents().getKotlinClassFinder(), javaClass) + '\n' + "findKotlinClass(ClassId) = " + KotlinClassFinderKt.findKotlinClass(this.$c.getComponents().getKotlinClassFinder(), requestClassId) + '\n');
                        }
                        v10 = javaClass;
                        v11 = actualFqName = v10 != null ? v10.getFqName() : null;
                        if (actualFqName == null || actualFqName.isRoot() || Intrinsics.areEqual(actualFqName.parent(), this.this$0.getOwnerDescriptor().getFqName()) ^ true) {
                            v12 = null;
                        } else {
                            var9_9 = new LazyJavaClassDescriptor(this.$c, this.this$0.getOwnerDescriptor(), javaClass, null, 8, null);
                            var10_11 = this.$c.getComponents().getJavaClassesTracker();
                            var11_12 = false;
                            var12_13 = false;
                            p1 = var9_9;
                            $i$a$-unknown-LazyJavaPackageScope$classes$1$1 = false;
                            var10_11.reportClass(p1);
                            v12 = var9_9;
                        }
                        v3 = v12;
                        break block8;
                    }
                    throw new NoWhenBranchMatchedException();
                }
                return v3;
            }
            {
                this.this$0 = lazyJavaPackageScope;
                this.$c = lazyJavaResolverContext;
                super(1);
            }
        });
    }

    public static final /* synthetic */ KotlinClassLookupResult access$resolveKotlinBinaryClass(LazyJavaPackageScope $this, KotlinJvmBinaryClass kotlinClass2) {
        return $this.resolveKotlinBinaryClass(kotlinClass2);
    }

    private static abstract class KotlinClassLookupResult {
        private KotlinClassLookupResult() {
        }

        public /* synthetic */ KotlinClassLookupResult(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public static final class Found
        extends KotlinClassLookupResult {
            @NotNull
            private final ClassDescriptor descriptor;

            @NotNull
            public final ClassDescriptor getDescriptor() {
                return this.descriptor;
            }

            public Found(@NotNull ClassDescriptor descriptor2) {
                Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
                super(null);
                this.descriptor = descriptor2;
            }
        }

        public static final class NotFound
        extends KotlinClassLookupResult {
            public static final NotFound INSTANCE;

            private NotFound() {
                super(null);
            }

            static {
                NotFound notFound;
                INSTANCE = notFound = new NotFound();
            }
        }

        public static final class SyntheticClass
        extends KotlinClassLookupResult {
            public static final SyntheticClass INSTANCE;

            private SyntheticClass() {
                super(null);
            }

            static {
                SyntheticClass syntheticClass;
                INSTANCE = syntheticClass = new SyntheticClass();
            }
        }
    }

    private static final class FindClassRequest {
        @NotNull
        private final Name name;
        @Nullable
        private final JavaClass javaClass;

        public boolean equals(@Nullable Object other) {
            return other instanceof FindClassRequest && Intrinsics.areEqual(this.name, ((FindClassRequest)other).name);
        }

        public int hashCode() {
            return this.name.hashCode();
        }

        @NotNull
        public final Name getName() {
            return this.name;
        }

        @Nullable
        public final JavaClass getJavaClass() {
            return this.javaClass;
        }

        public FindClassRequest(@NotNull Name name, @Nullable JavaClass javaClass) {
            Intrinsics.checkNotNullParameter(name, "name");
            this.name = name;
            this.javaClass = javaClass;
        }
    }
}

