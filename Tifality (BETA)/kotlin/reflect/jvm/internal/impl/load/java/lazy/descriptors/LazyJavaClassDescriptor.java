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
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.MappingUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.ScopesHolderForClass;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterUtilsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorBase;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.FakePureImplementationsProvider;
import kotlin.reflect.jvm.internal.impl.load.java.JavaVisibilities;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaResolverCache;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaAnnotationsKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassMemberScope;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolverKt;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifierType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNamesUtilKt;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.InnerClassesScopeWrapper;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.types.AbstractClassTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LazyJavaClassDescriptor
extends ClassDescriptorBase
implements JavaClassDescriptor {
    private final LazyJavaResolverContext c;
    private final ClassKind kind;
    private final Modality modality;
    private final Visibility visibility;
    private final boolean isInner;
    private final LazyJavaClassTypeConstructor typeConstructor;
    private final LazyJavaClassMemberScope unsubstitutedMemberScope;
    private final ScopesHolderForClass<LazyJavaClassMemberScope> scopeHolder;
    private final InnerClassesScopeWrapper innerClassesScope;
    private final LazyJavaStaticClassScope staticScope;
    @NotNull
    private final Annotations annotations;
    private final NotNullLazyValue<List<TypeParameterDescriptor>> declaredParameters;
    @NotNull
    private final LazyJavaResolverContext outerContext;
    @NotNull
    private final JavaClass jClass;
    private final ClassDescriptor additionalSupertypeClassDescriptor;
    private static final Set<String> PUBLIC_METHOD_NAMES_IN_OBJECT;
    public static final Companion Companion;

    @Override
    @NotNull
    public ClassKind getKind() {
        return this.kind;
    }

    @Override
    @NotNull
    public Modality getModality() {
        return this.modality;
    }

    @Override
    @NotNull
    public Visibility getVisibility() {
        Visibility visibility = Intrinsics.areEqual(this.visibility, Visibilities.PRIVATE) && this.jClass.getOuterClass() == null ? JavaVisibilities.PACKAGE_VISIBILITY : this.visibility;
        Intrinsics.checkNotNullExpressionValue(visibility, "if (visibility == Visibi\u2026ISIBILITY else visibility");
        return visibility;
    }

    @Override
    public boolean isInner() {
        return this.isInner;
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
    public boolean isCompanionObject() {
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
    public boolean isFun() {
        return false;
    }

    @Override
    @NotNull
    public TypeConstructor getTypeConstructor() {
        return this.typeConstructor;
    }

    @Override
    @NotNull
    protected LazyJavaClassMemberScope getUnsubstitutedMemberScope(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this.scopeHolder.getScope(kotlinTypeRefiner);
    }

    @Override
    @NotNull
    public MemberScope getUnsubstitutedInnerClassesScope() {
        return this.innerClassesScope;
    }

    @Override
    @NotNull
    public MemberScope getStaticScope() {
        return this.staticScope;
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
    public LazyJavaClassMemberScope getUnsubstitutedMemberScope() {
        MemberScope memberScope2 = super.getUnsubstitutedMemberScope();
        if (memberScope2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassMemberScope");
        }
        return (LazyJavaClassMemberScope)memberScope2;
    }

    @NotNull
    public List<ClassConstructorDescriptor> getConstructors() {
        return (List)this.unsubstitutedMemberScope.getConstructors$descriptors_jvm().invoke();
    }

    @Override
    @NotNull
    public Annotations getAnnotations() {
        return this.annotations;
    }

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        return (List)this.declaredParameters.invoke();
    }

    @Override
    @NotNull
    public Collection<ClassDescriptor> getSealedSubclasses() {
        return CollectionsKt.emptyList();
    }

    @NotNull
    public String toString() {
        return "Lazy Java class " + DescriptorUtilsKt.getFqNameUnsafe(this);
    }

    @NotNull
    public final LazyJavaClassDescriptor copy$descriptors_jvm(@NotNull JavaResolverCache javaResolverCache, @Nullable ClassDescriptor additionalSupertypeClassDescriptor) {
        Intrinsics.checkNotNullParameter(javaResolverCache, "javaResolverCache");
        LazyJavaResolverContext lazyJavaResolverContext = ContextKt.replaceComponents(this.c, this.c.getComponents().replace(javaResolverCache));
        DeclarationDescriptor declarationDescriptor = this.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "containingDeclaration");
        return new LazyJavaClassDescriptor(lazyJavaResolverContext, declarationDescriptor, this.jClass, additionalSupertypeClassDescriptor);
    }

    @NotNull
    public final JavaClass getJClass() {
        return this.jClass;
    }

    public LazyJavaClassDescriptor(@NotNull LazyJavaResolverContext outerContext, @NotNull DeclarationDescriptor containingDeclaration, @NotNull JavaClass jClass, @Nullable ClassDescriptor additionalSupertypeClassDescriptor) {
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        super(outerContext.getStorageManager(), containingDeclaration, jClass.getName(), outerContext.getComponents().getSourceElementFactory().source(jClass), false);
        this.outerContext = outerContext;
        this.jClass = jClass;
        this.additionalSupertypeClassDescriptor = additionalSupertypeClassDescriptor;
        this.c = ContextKt.childForClassOrPackage$default(this.outerContext, this, this.jClass, 0, 4, null);
        this.c.getComponents().getJavaResolverCache().recordClass(this.jClass, this);
        boolean bl = this.jClass.getLightClassOriginKind() == null;
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "Creating LazyJavaClassDescriptor for light class " + this.jClass;
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        ClassKind classKind = this.jClass.isAnnotationType() ? ClassKind.ANNOTATION_CLASS : (this.jClass.isInterface() ? ClassKind.INTERFACE : (this.kind = this.jClass.isEnum() ? ClassKind.ENUM_CLASS : ClassKind.CLASS));
        this.modality = this.jClass.isAnnotationType() || this.jClass.isEnum() ? Modality.FINAL : Modality.Companion.convertFromFlags(this.jClass.isAbstract() || this.jClass.isInterface(), !this.jClass.isFinal());
        this.visibility = this.jClass.getVisibility();
        this.isInner = this.jClass.getOuterClass() != null && !this.jClass.isStatic();
        this.typeConstructor = new LazyJavaClassTypeConstructor();
        this.unsubstitutedMemberScope = new LazyJavaClassMemberScope(this.c, this, this.jClass, this.additionalSupertypeClassDescriptor != null, null, 16, null);
        this.scopeHolder = ScopesHolderForClass.Companion.create(this, this.c.getStorageManager(), this.c.getComponents().getKotlinTypeChecker().getKotlinTypeRefiner(), (Function1)new Function1<KotlinTypeRefiner, LazyJavaClassMemberScope>(this){
            final /* synthetic */ LazyJavaClassDescriptor this$0;

            @NotNull
            public final LazyJavaClassMemberScope invoke(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
                Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
                return new LazyJavaClassMemberScope(LazyJavaClassDescriptor.access$getC$p(this.this$0), this.this$0, this.this$0.getJClass(), LazyJavaClassDescriptor.access$getAdditionalSupertypeClassDescriptor$p(this.this$0) != null, LazyJavaClassDescriptor.access$getUnsubstitutedMemberScope$p(this.this$0));
            }
            {
                this.this$0 = lazyJavaClassDescriptor;
                super(1);
            }
        });
        this.innerClassesScope = new InnerClassesScopeWrapper(this.unsubstitutedMemberScope);
        this.staticScope = new LazyJavaStaticClassScope(this.c, this.jClass, this);
        this.annotations = LazyJavaAnnotationsKt.resolveAnnotations(this.c, this.jClass);
        this.declaredParameters = this.c.getStorageManager().createLazyValue((Function0)new Function0<List<? extends TypeParameterDescriptor>>(this){
            final /* synthetic */ LazyJavaClassDescriptor this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final List<TypeParameterDescriptor> invoke() {
                void $this$mapTo$iv$iv;
                Iterable $this$map$iv = this.this$0.getJClass().getTypeParameters();
                boolean $i$f$map = false;
                Iterable iterable = $this$map$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    TypeParameterDescriptor typeParameterDescriptor;
                    void p;
                    JavaTypeParameter javaTypeParameter = (JavaTypeParameter)item$iv$iv;
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    if (LazyJavaClassDescriptor.access$getC$p(this.this$0).getTypeParameterResolver().resolveTypeParameter((JavaTypeParameter)p) == null) {
                        throw (Throwable)((Object)new AssertionError((Object)("Parameter " + p + " surely belongs to class " + this.this$0.getJClass() + ", so it must be resolved")));
                    }
                    collection.add(typeParameterDescriptor);
                }
                return (List)destination$iv$iv;
            }
            {
                this.this$0 = lazyJavaClassDescriptor;
                super(0);
            }
        });
    }

    public /* synthetic */ LazyJavaClassDescriptor(LazyJavaResolverContext lazyJavaResolverContext, DeclarationDescriptor declarationDescriptor, JavaClass javaClass, ClassDescriptor classDescriptor, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            classDescriptor = null;
        }
        this(lazyJavaResolverContext, declarationDescriptor, javaClass, classDescriptor);
    }

    static {
        Companion = new Companion(null);
        PUBLIC_METHOD_NAMES_IN_OBJECT = SetsKt.setOf("equals", "hashCode", "getClass", "wait", "notify", "notifyAll", "toString");
    }

    public static final /* synthetic */ LazyJavaClassMemberScope access$getUnsubstitutedMemberScope$p(LazyJavaClassDescriptor $this) {
        return $this.unsubstitutedMemberScope;
    }

    private final class LazyJavaClassTypeConstructor
    extends AbstractClassTypeConstructor {
        private final NotNullLazyValue<List<TypeParameterDescriptor>> parameters;

        @Override
        @NotNull
        public List<TypeParameterDescriptor> getParameters() {
            return (List)this.parameters.invoke();
        }

        /*
         * WARNING - void declaration
         */
        @Override
        @NotNull
        protected Collection<KotlinType> computeSupertypes() {
            KotlinType kotlinType;
            Annotated annotated;
            Object object;
            Collection<JavaClassifierType> javaTypes = LazyJavaClassDescriptor.this.getJClass().getSupertypes();
            ArrayList<KotlinType> result2 = new ArrayList<KotlinType>(javaTypes.size());
            ArrayList<JavaClassifierType> incomplete = new ArrayList<JavaClassifierType>(0);
            KotlinType purelyImplementedSupertype = this.getPurelyImplementedSupertype();
            for (JavaClassifierType collection3 : javaTypes) {
                KotlinType kotlinType2 = LazyJavaClassDescriptor.this.c.getTypeResolver().transformJavaType(collection3, JavaTypeResolverKt.toAttributes$default(TypeUsage.SUPERTYPE, false, null, 3, null));
                if (kotlinType2.getConstructor().getDeclarationDescriptor() instanceof NotFoundClasses.MockClassDescriptor) {
                    incomplete.add(collection3);
                }
                KotlinType kotlinType3 = purelyImplementedSupertype;
                if (Intrinsics.areEqual(kotlinType2.getConstructor(), kotlinType3 != null ? kotlinType3.getConstructor() : null) || KotlinBuiltIns.isAnyOrNullableAny(kotlinType2)) continue;
                result2.add(kotlinType2);
            }
            Collection collection = result2;
            ClassDescriptor classDescriptor = LazyJavaClassDescriptor.this.additionalSupertypeClassDescriptor;
            if (classDescriptor != null) {
                void it;
                ClassDescriptor classDescriptor2 = classDescriptor;
                boolean bl = false;
                boolean kotlinType2 = false;
                ClassDescriptor classDescriptor3 = classDescriptor2;
                object = collection;
                boolean bl2 = false;
                annotated = MappingUtilKt.createMappedTypeParametersSubstitution((ClassDescriptor)it, LazyJavaClassDescriptor.this).buildSubstitutor().substitute(it.getDefaultType(), Variance.INVARIANT);
                collection = object;
                kotlinType = annotated;
            } else {
                kotlinType = null;
            }
            kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(collection, kotlinType);
            kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull((Collection)result2, purelyImplementedSupertype);
            Collection collection2 = incomplete;
            boolean bl = false;
            if (!collection2.isEmpty()) {
                Collection<String> collection3;
                void $this$mapTo$iv$iv;
                Iterable iterable = incomplete;
                annotated = this.getDeclarationDescriptor();
                object = LazyJavaClassDescriptor.this.c.getComponents().getErrorReporter();
                boolean $i$f$map = false;
                Iterable kotlinType2 = iterable;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    void javaType3;
                    JavaType javaType = (JavaType)item$iv$iv;
                    collection3 = destination$iv$iv;
                    boolean bl3 = false;
                    void v4 = javaType3;
                    if (v4 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.load.java.structure.JavaClassifierType");
                    }
                    String string = ((JavaClassifierType)v4).getPresentableText();
                    collection3.add(string);
                }
                collection3 = (List)destination$iv$iv;
                object.reportIncompleteHierarchy((ClassDescriptor)annotated, (List<String>)collection3);
            }
            Collection collection4 = result2;
            bl = false;
            return !collection4.isEmpty() ? CollectionsKt.toList((Iterable)result2) : CollectionsKt.listOf(LazyJavaClassDescriptor.this.c.getModule().getBuiltIns().getAnyType());
        }

        /*
         * WARNING - void declaration
         */
        private final KotlinType getPurelyImplementedSupertype() {
            List list;
            TypeProjectionImpl typeProjectionImpl;
            int n;
            Collection collection;
            Iterable destination$iv$iv;
            FqName annotatedPurelyImplementedFqName;
            FqName fqName2;
            FqName fqName3;
            FqName fqName4 = this.getPurelyImplementsFqNameFromAnnotation();
            if (fqName4 != null) {
                FqName fqName5 = fqName4;
                boolean bl = false;
                boolean bl2 = false;
                FqName fqName6 = fqName5;
                boolean bl3 = false;
                fqName3 = !fqName6.isRoot() && fqName6.startsWith(KotlinBuiltIns.BUILT_INS_PACKAGE_NAME) ? fqName5 : null;
            } else {
                fqName3 = null;
            }
            if ((fqName2 = (annotatedPurelyImplementedFqName = fqName3)) == null) {
                fqName2 = FakePureImplementationsProvider.INSTANCE.getPurelyImplementedInterface(DescriptorUtilsKt.getFqNameSafe(LazyJavaClassDescriptor.this));
            }
            if (fqName2 == null) {
                return null;
            }
            FqName purelyImplementedFqName = fqName2;
            ClassDescriptor classDescriptor = DescriptorUtilsKt.resolveTopLevelClass(LazyJavaClassDescriptor.this.c.getModule(), purelyImplementedFqName, NoLookupLocation.FROM_JAVA_LOADER);
            if (classDescriptor == null) {
                return null;
            }
            ClassDescriptor classDescriptor2 = classDescriptor;
            TypeConstructor typeConstructor2 = classDescriptor2.getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor2, "classDescriptor.typeConstructor");
            int supertypeParameterCount = typeConstructor2.getParameters().size();
            List<TypeParameterDescriptor> list2 = LazyJavaClassDescriptor.this.getTypeConstructor().getParameters();
            Intrinsics.checkNotNullExpressionValue(list2, "getTypeConstructor().parameters");
            List<TypeParameterDescriptor> typeParameters2 = list2;
            int typeParameterCount = typeParameters2.size();
            if (typeParameterCount == supertypeParameterCount) {
                void $this$mapTo$iv$iv;
                Iterable $this$map$iv = typeParameters2;
                boolean $i$f$map = false;
                Iterable iterable = $this$map$iv;
                destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    void parameter;
                    TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor)item$iv$iv;
                    collection = destination$iv$iv;
                    n = 0;
                    void v6 = parameter;
                    Intrinsics.checkNotNullExpressionValue(v6, "parameter");
                    typeProjectionImpl = new TypeProjectionImpl(Variance.INVARIANT, v6.getDefaultType());
                    collection.add(typeProjectionImpl);
                }
                list = (List)destination$iv$iv;
            } else if (typeParameterCount == 1 && supertypeParameterCount > 1 && annotatedPurelyImplementedFqName == null) {
                void $this$mapTo$iv$iv;
                TypeParameterDescriptor typeParameterDescriptor = CollectionsKt.single(typeParameters2);
                Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "typeParameters.single()");
                TypeProjectionImpl parameter = new TypeProjectionImpl(Variance.INVARIANT, typeParameterDescriptor.getDefaultType());
                int $i$f$map = 1;
                Iterable $this$map$iv = new IntRange($i$f$map, supertypeParameterCount);
                boolean $i$f$map2 = false;
                destination$iv$iv = $this$map$iv;
                Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                Iterator iterator2 = $this$mapTo$iv$iv.iterator();
                while (iterator2.hasNext()) {
                    int item$iv$iv;
                    n = item$iv$iv = ((IntIterator)iterator2).nextInt();
                    collection = destination$iv$iv2;
                    boolean bl = false;
                    typeProjectionImpl = parameter;
                    collection.add(typeProjectionImpl);
                }
                list = (List)destination$iv$iv2;
            } else {
                return null;
            }
            List parametersAsTypeProjections = list;
            return KotlinTypeFactory.simpleNotNullType(Annotations.Companion.getEMPTY(), classDescriptor2, parametersAsTypeProjections);
        }

        private final FqName getPurelyImplementsFqNameFromAnnotation() {
            Object object;
            Annotations annotations2 = LazyJavaClassDescriptor.this.getAnnotations();
            FqName fqName2 = JvmAnnotationNames.PURELY_IMPLEMENTS_ANNOTATION;
            Intrinsics.checkNotNullExpressionValue(fqName2, "JvmAnnotationNames.PURELY_IMPLEMENTS_ANNOTATION");
            AnnotationDescriptor annotationDescriptor = annotations2.findAnnotation(fqName2);
            if (annotationDescriptor == null) {
                return null;
            }
            AnnotationDescriptor annotation = annotationDescriptor;
            Object t = CollectionsKt.singleOrNull((Iterable)annotation.getAllValueArguments().values());
            if (!(t instanceof StringValue)) {
                t = null;
            }
            if ((object = (StringValue)t) == null || (object = (String)((ConstantValue)object).getValue()) == null) {
                return null;
            }
            Object fqNameString = object;
            if (!FqNamesUtilKt.isValidJavaFqName((String)fqNameString)) {
                return null;
            }
            return new FqName((String)fqNameString);
        }

        @Override
        @NotNull
        protected SupertypeLoopChecker getSupertypeLoopChecker() {
            return LazyJavaClassDescriptor.this.c.getComponents().getSupertypeLoopChecker();
        }

        @Override
        public boolean isDenotable() {
            return true;
        }

        @Override
        @NotNull
        public ClassDescriptor getDeclarationDescriptor() {
            return LazyJavaClassDescriptor.this;
        }

        @NotNull
        public String toString() {
            String string = LazyJavaClassDescriptor.this.getName().asString();
            Intrinsics.checkNotNullExpressionValue(string, "name.asString()");
            return string;
        }

        public LazyJavaClassTypeConstructor() {
            super(LazyJavaClassDescriptor.this.c.getStorageManager());
            this.parameters = LazyJavaClassDescriptor.this.c.getStorageManager().createLazyValue((Function0)new Function0<List<? extends TypeParameterDescriptor>>(this){
                final /* synthetic */ LazyJavaClassTypeConstructor this$0;

                @NotNull
                public final List<TypeParameterDescriptor> invoke() {
                    return TypeParameterUtilsKt.computeConstructorTypeParameters(this.this$0.LazyJavaClassDescriptor.this);
                }
                {
                    this.this$0 = lazyJavaClassTypeConstructor;
                    super(0);
                }
            });
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

