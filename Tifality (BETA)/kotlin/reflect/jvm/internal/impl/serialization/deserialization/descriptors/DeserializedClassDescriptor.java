/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeserializedDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ScopesHolderForClass;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterUtilsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassConstructorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.EnumEntrySyntheticClassDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.UtilsKt;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.NonReportingOverrideStrategy;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.StaticScopeForKotlinEnum;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationContext;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.NameResolverUtilKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ProtoContainer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ProtoEnumFlags;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedAnnotations;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.NonEmptyDeserializedAnnotations;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.NullableLazyValue;
import kotlin.reflect.jvm.internal.impl.types.AbstractClassTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DeserializedClassDescriptor
extends AbstractClassDescriptor
implements DeserializedDescriptor {
    private final ClassId classId;
    private final Modality modality;
    private final Visibility visibility;
    private final ClassKind kind;
    @NotNull
    private final DeserializationContext c;
    private final MemberScopeImpl staticScope;
    private final DeserializedClassTypeConstructor typeConstructor;
    private final ScopesHolderForClass<DeserializedClassMemberScope> memberScopeHolder;
    private final EnumEntryClassDescriptors enumEntries;
    private final DeclarationDescriptor containingDeclaration;
    private final NullableLazyValue<ClassConstructorDescriptor> primaryConstructor;
    private final NotNullLazyValue<Collection<ClassConstructorDescriptor>> constructors;
    private final NullableLazyValue<ClassDescriptor> companionObjectDescriptor;
    private final NotNullLazyValue<Collection<ClassDescriptor>> sealedSubclasses;
    @NotNull
    private final ProtoContainer.Class thisAsProtoContainer;
    @NotNull
    private final Annotations annotations;
    @NotNull
    private final ProtoBuf.Class classProto;
    @NotNull
    private final BinaryVersion metadataVersion;
    private final SourceElement sourceElement;

    @NotNull
    public final DeserializationContext getC() {
        return this.c;
    }

    private final DeserializedClassMemberScope getMemberScope() {
        return this.memberScopeHolder.getScope(this.c.getComponents().getKotlinTypeChecker().getKotlinTypeRefiner());
    }

    @NotNull
    public final ProtoContainer.Class getThisAsProtoContainer$deserialization() {
        return this.thisAsProtoContainer;
    }

    @Override
    @NotNull
    public Annotations getAnnotations() {
        return this.annotations;
    }

    @Override
    @NotNull
    public DeclarationDescriptor getContainingDeclaration() {
        return this.containingDeclaration;
    }

    @Override
    @NotNull
    public TypeConstructor getTypeConstructor() {
        return this.typeConstructor;
    }

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
        return this.visibility;
    }

    @Override
    public boolean isInner() {
        Boolean bl = Flags.IS_INNER.get(this.classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bl, "Flags.IS_INNER.get(classProto.flags)");
        return bl;
    }

    @Override
    public boolean isData() {
        Boolean bl = Flags.IS_DATA.get(this.classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bl, "Flags.IS_DATA.get(classProto.flags)");
        return bl;
    }

    @Override
    public boolean isInline() {
        Boolean bl = Flags.IS_INLINE_CLASS.get(this.classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bl, "Flags.IS_INLINE_CLASS.get(classProto.flags)");
        return bl;
    }

    @Override
    public boolean isExpect() {
        Boolean bl = Flags.IS_EXPECT_CLASS.get(this.classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bl, "Flags.IS_EXPECT_CLASS.get(classProto.flags)");
        return bl;
    }

    @Override
    public boolean isActual() {
        return false;
    }

    @Override
    public boolean isExternal() {
        Boolean bl = Flags.IS_EXTERNAL_CLASS.get(this.classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bl, "Flags.IS_EXTERNAL_CLASS.get(classProto.flags)");
        return bl;
    }

    @Override
    public boolean isFun() {
        Boolean bl = Flags.IS_FUN_INTERFACE.get(this.classProto.getFlags());
        Intrinsics.checkNotNullExpressionValue(bl, "Flags.IS_FUN_INTERFACE.get(classProto.flags)");
        return bl;
    }

    @Override
    @NotNull
    protected MemberScope getUnsubstitutedMemberScope(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this.memberScopeHolder.getScope(kotlinTypeRefiner);
    }

    @Override
    @NotNull
    public MemberScopeImpl getStaticScope() {
        return this.staticScope;
    }

    @Override
    public boolean isCompanionObject() {
        return Flags.CLASS_KIND.get(this.classProto.getFlags()) == ProtoBuf.Class.Kind.COMPANION_OBJECT;
    }

    private final ClassConstructorDescriptor computePrimaryConstructor() {
        ClassConstructorDescriptor classConstructorDescriptor;
        Object v2;
        block4: {
            if (this.kind.isSingleton()) {
                ClassConstructorDescriptorImpl classConstructorDescriptorImpl = DescriptorFactory.createPrimaryConstructorForObject(this, SourceElement.NO_SOURCE);
                boolean bl = false;
                boolean bl2 = false;
                ClassConstructorDescriptorImpl $this$apply = classConstructorDescriptorImpl;
                boolean bl3 = false;
                $this$apply.setReturnType(this.getDefaultType());
                return classConstructorDescriptorImpl;
            }
            List<ProtoBuf.Constructor> list = this.classProto.getConstructorList();
            Intrinsics.checkNotNullExpressionValue(list, "classProto.constructorList");
            Iterable $this$firstOrNull$iv = list;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                ProtoBuf.Constructor it = (ProtoBuf.Constructor)element$iv;
                boolean bl = false;
                ProtoBuf.Constructor constructor = it;
                Intrinsics.checkNotNullExpressionValue(constructor, "it");
                if (!(Flags.IS_SECONDARY.get(constructor.getFlags()) == false)) continue;
                v2 = element$iv;
                break block4;
            }
            v2 = null;
        }
        ProtoBuf.Constructor constructor = v2;
        if (constructor != null) {
            ProtoBuf.Constructor constructor2 = constructor;
            boolean bl = false;
            boolean bl4 = false;
            ProtoBuf.Constructor constructorProto = constructor2;
            boolean bl5 = false;
            classConstructorDescriptor = this.c.getMemberDeserializer().loadConstructor(constructorProto, true);
        } else {
            classConstructorDescriptor = null;
        }
        return classConstructorDescriptor;
    }

    @Override
    @Nullable
    public ClassConstructorDescriptor getUnsubstitutedPrimaryConstructor() {
        return (ClassConstructorDescriptor)this.primaryConstructor.invoke();
    }

    private final Collection<ClassConstructorDescriptor> computeConstructors() {
        return CollectionsKt.plus((Collection)CollectionsKt.plus((Collection)this.computeSecondaryConstructors(), (Iterable)CollectionsKt.listOfNotNull(this.getUnsubstitutedPrimaryConstructor())), (Iterable)this.c.getComponents().getAdditionalClassPartsProvider().getConstructors(this));
    }

    /*
     * WARNING - void declaration
     */
    private final List<ClassConstructorDescriptor> computeSecondaryConstructors() {
        void $this$mapTo$iv$iv;
        ProtoBuf.Constructor it;
        Iterable $this$filterTo$iv$iv;
        List<ProtoBuf.Constructor> list = this.classProto.getConstructorList();
        Intrinsics.checkNotNullExpressionValue(list, "classProto.constructorList");
        Iterable $this$filter$iv = list;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            it = (ProtoBuf.Constructor)element$iv$iv;
            boolean bl = false;
            ProtoBuf.Constructor constructor = it;
            Intrinsics.checkNotNullExpressionValue(constructor, "it");
            Boolean bl2 = Flags.IS_SECONDARY.get(constructor.getFlags());
            Intrinsics.checkNotNullExpressionValue(bl2, "Flags.IS_SECONDARY.get(it.flags)");
            if (!bl2.booleanValue()) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$map$iv = (List)destination$iv$iv;
        boolean $i$f$map = false;
        $this$filterTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            it = (ProtoBuf.Constructor)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            MemberDeserializer memberDeserializer = this.c.getMemberDeserializer();
            ProtoBuf.Constructor constructor = it;
            Intrinsics.checkNotNullExpressionValue(constructor, "it");
            ClassConstructorDescriptor classConstructorDescriptor = memberDeserializer.loadConstructor(constructor, false);
            collection.add(classConstructorDescriptor);
        }
        return (List)destination$iv$iv;
    }

    @Override
    @NotNull
    public Collection<ClassConstructorDescriptor> getConstructors() {
        return (Collection)this.constructors.invoke();
    }

    private final ClassDescriptor computeCompanionObjectDescriptor() {
        if (!this.classProto.hasCompanionObjectName()) {
            return null;
        }
        Name companionObjectName = NameResolverUtilKt.getName(this.c.getNameResolver(), this.classProto.getCompanionObjectName());
        ClassifierDescriptor classifierDescriptor = this.getMemberScope().getContributedClassifier(companionObjectName, NoLookupLocation.FROM_DESERIALIZATION);
        if (!(classifierDescriptor instanceof ClassDescriptor)) {
            classifierDescriptor = null;
        }
        return (ClassDescriptor)classifierDescriptor;
    }

    @Override
    @Nullable
    public ClassDescriptor getCompanionObjectDescriptor() {
        return (ClassDescriptor)this.companionObjectDescriptor.invoke();
    }

    public final boolean hasNestedClass$deserialization(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return this.getMemberScope().getClassNames$deserialization().contains(name);
    }

    /*
     * WARNING - void declaration
     */
    private final Collection<ClassDescriptor> computeSubclassesForSealedClass() {
        List<Integer> fqNames;
        if (this.modality != Modality.SEALED) {
            return CollectionsKt.emptyList();
        }
        List<Integer> list = fqNames = this.classProto.getSealedSubclassFqNameList();
        Intrinsics.checkNotNullExpressionValue(list, "fqNames");
        Collection collection = list;
        boolean bl = false;
        if (!collection.isEmpty()) {
            void $this$mapNotNullTo$iv$iv;
            Iterable $this$mapNotNull$iv = fqNames;
            boolean $i$f$mapNotNull = false;
            Iterable iterable = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
            while (iterator2.hasNext()) {
                ClassDescriptor classDescriptor;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = iterator2.next();
                boolean bl2 = false;
                Integer index = (Integer)element$iv$iv;
                boolean bl3 = false;
                DeserializationComponents deserializationComponents = this.c.getComponents();
                NameResolver nameResolver = this.c.getNameResolver();
                Integer n = index;
                Intrinsics.checkNotNullExpressionValue(n, "index");
                if (deserializationComponents.deserializeClass(NameResolverUtilKt.getClassId(nameResolver, n)) == null) continue;
                boolean bl4 = false;
                boolean bl5 = false;
                ClassDescriptor it$iv$iv = classDescriptor;
                boolean bl6 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            return (List)destination$iv$iv;
        }
        return DescriptorUtilsKt.computeSealedSubclasses(this);
    }

    @Override
    @NotNull
    public Collection<ClassDescriptor> getSealedSubclasses() {
        return (Collection)this.sealedSubclasses.invoke();
    }

    @NotNull
    public String toString() {
        return "deserialized " + (this.isExpect() ? "expect" : "") + " class " + this.getName();
    }

    @Override
    @NotNull
    public SourceElement getSource() {
        return this.sourceElement;
    }

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        return this.c.getTypeDeserializer().getOwnTypeParameters();
    }

    @NotNull
    public final ProtoBuf.Class getClassProto() {
        return this.classProto;
    }

    @NotNull
    public final BinaryVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    public DeserializedClassDescriptor(@NotNull DeserializationContext outerContext, @NotNull ProtoBuf.Class classProto, @NotNull NameResolver nameResolver, @NotNull BinaryVersion metadataVersion, @NotNull SourceElement sourceElement) {
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(sourceElement, "sourceElement");
        super(outerContext.getStorageManager(), NameResolverUtilKt.getClassId(nameResolver, classProto.getFqName()).getShortClassName());
        this.classProto = classProto;
        this.metadataVersion = metadataVersion;
        this.sourceElement = sourceElement;
        this.classId = NameResolverUtilKt.getClassId(nameResolver, this.classProto.getFqName());
        this.modality = ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(this.classProto.getFlags()));
        this.visibility = ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(this.classProto.getFlags()));
        this.kind = ProtoEnumFlags.INSTANCE.classKind(Flags.CLASS_KIND.get(this.classProto.getFlags()));
        DeclarationDescriptor declarationDescriptor = this;
        List<ProtoBuf.TypeParameter> list = this.classProto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(list, "classProto.typeParameterList");
        ProtoBuf.TypeTable typeTable = this.classProto.getTypeTable();
        Intrinsics.checkNotNullExpressionValue(typeTable, "classProto.typeTable");
        TypeTable typeTable2 = new TypeTable(typeTable);
        ProtoBuf.VersionRequirementTable versionRequirementTable = this.classProto.getVersionRequirementTable();
        Intrinsics.checkNotNullExpressionValue(versionRequirementTable, "classProto.versionRequirementTable");
        this.c = outerContext.childContext(declarationDescriptor, list, nameResolver, typeTable2, VersionRequirementTable.Companion.create(versionRequirementTable), this.metadataVersion);
        this.staticScope = this.kind == ClassKind.ENUM_CLASS ? (MemberScopeImpl)new StaticScopeForKotlinEnum(this.c.getStorageManager(), this) : (MemberScopeImpl)MemberScope.Empty.INSTANCE;
        this.typeConstructor = new DeserializedClassTypeConstructor();
        this.memberScopeHolder = ScopesHolderForClass.Companion.create(this, this.c.getStorageManager(), this.c.getComponents().getKotlinTypeChecker().getKotlinTypeRefiner(), (Function1)new Function1<KotlinTypeRefiner, DeserializedClassMemberScope>(this){

            @NotNull
            public final DeserializedClassMemberScope invoke(@NotNull KotlinTypeRefiner p1) {
                Intrinsics.checkNotNullParameter(p1, "p1");
                return (DeserializedClassDescriptor)this.receiver.new DeserializedClassMemberScope(p1);
            }

            public final KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(DeserializedClassMemberScope.class);
            }

            public final String getName() {
                return "<init>";
            }

            public final String getSignature() {
                return "<init>(Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedClassDescriptor;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;)V";
            }
        });
        this.enumEntries = this.kind == ClassKind.ENUM_CLASS ? new EnumEntryClassDescriptors() : null;
        this.containingDeclaration = outerContext.getContainingDeclaration();
        this.primaryConstructor = this.c.getStorageManager().createNullableLazyValue((Function0)new Function0<ClassConstructorDescriptor>(this){
            final /* synthetic */ DeserializedClassDescriptor this$0;

            @Nullable
            public final ClassConstructorDescriptor invoke() {
                return DeserializedClassDescriptor.access$computePrimaryConstructor(this.this$0);
            }
            {
                this.this$0 = deserializedClassDescriptor;
                super(0);
            }
        });
        this.constructors = this.c.getStorageManager().createLazyValue((Function0)new Function0<Collection<? extends ClassConstructorDescriptor>>(this){
            final /* synthetic */ DeserializedClassDescriptor this$0;

            @NotNull
            public final Collection<ClassConstructorDescriptor> invoke() {
                return DeserializedClassDescriptor.access$computeConstructors(this.this$0);
            }
            {
                this.this$0 = deserializedClassDescriptor;
                super(0);
            }
        });
        this.companionObjectDescriptor = this.c.getStorageManager().createNullableLazyValue((Function0)new Function0<ClassDescriptor>(this){
            final /* synthetic */ DeserializedClassDescriptor this$0;

            @Nullable
            public final ClassDescriptor invoke() {
                return DeserializedClassDescriptor.access$computeCompanionObjectDescriptor(this.this$0);
            }
            {
                this.this$0 = deserializedClassDescriptor;
                super(0);
            }
        });
        this.sealedSubclasses = this.c.getStorageManager().createLazyValue((Function0)new Function0<Collection<? extends ClassDescriptor>>(this){
            final /* synthetic */ DeserializedClassDescriptor this$0;

            @NotNull
            public final Collection<ClassDescriptor> invoke() {
                return DeserializedClassDescriptor.access$computeSubclassesForSealedClass(this.this$0);
            }
            {
                this.this$0 = deserializedClassDescriptor;
                super(0);
            }
        });
        NameResolver nameResolver2 = this.c.getNameResolver();
        TypeTable typeTable3 = this.c.getTypeTable();
        DeclarationDescriptor declarationDescriptor2 = this.containingDeclaration;
        if (!(declarationDescriptor2 instanceof DeserializedClassDescriptor)) {
            declarationDescriptor2 = null;
        }
        DeserializedClassDescriptor deserializedClassDescriptor = (DeserializedClassDescriptor)declarationDescriptor2;
        this.thisAsProtoContainer = new ProtoContainer.Class(this.classProto, nameResolver2, typeTable3, this.sourceElement, deserializedClassDescriptor != null ? deserializedClassDescriptor.thisAsProtoContainer : null);
        this.annotations = Flags.HAS_ANNOTATIONS.get(this.classProto.getFlags()) == false ? Annotations.Companion.getEMPTY() : (Annotations)new NonEmptyDeserializedAnnotations(this.c.getStorageManager(), (Function0<? extends List<? extends AnnotationDescriptor>>)new Function0<List<? extends AnnotationDescriptor>>(this){
            final /* synthetic */ DeserializedClassDescriptor this$0;

            @NotNull
            public final List<AnnotationDescriptor> invoke() {
                return CollectionsKt.toList((Iterable)this.this$0.getC().getComponents().getAnnotationAndConstantLoader().loadClassAnnotations(this.this$0.getThisAsProtoContainer$deserialization()));
            }
            {
                this.this$0 = deserializedClassDescriptor;
                super(0);
            }
        });
    }

    public static final /* synthetic */ ClassConstructorDescriptor access$computePrimaryConstructor(DeserializedClassDescriptor $this) {
        return $this.computePrimaryConstructor();
    }

    public static final /* synthetic */ Collection access$computeConstructors(DeserializedClassDescriptor $this) {
        return $this.computeConstructors();
    }

    public static final /* synthetic */ ClassDescriptor access$computeCompanionObjectDescriptor(DeserializedClassDescriptor $this) {
        return $this.computeCompanionObjectDescriptor();
    }

    public static final /* synthetic */ Collection access$computeSubclassesForSealedClass(DeserializedClassDescriptor $this) {
        return $this.computeSubclassesForSealedClass();
    }

    private final class DeserializedClassTypeConstructor
    extends AbstractClassTypeConstructor {
        private final NotNullLazyValue<List<TypeParameterDescriptor>> parameters;

        /*
         * WARNING - void declaration
         */
        @Override
        @NotNull
        protected Collection<KotlinType> computeSupertypes() {
            void $this$mapNotNullTo$iv$iv;
            Annotated annotated;
            Object object;
            void $this$mapTo$iv$iv;
            Iterable $this$map$iv = ProtoTypeTableUtilKt.supertypes(DeserializedClassDescriptor.this.getClassProto(), DeserializedClassDescriptor.this.getC().getTypeTable());
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Iterable destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void supertypeProto;
                ProtoBuf.Type type2 = (ProtoBuf.Type)item$iv$iv;
                object = destination$iv$iv;
                boolean bl = false;
                annotated = DeserializedClassDescriptor.this.getC().getTypeDeserializer().type((ProtoBuf.Type)supertypeProto);
                object.add(annotated);
            }
            List result2 = CollectionsKt.plus((Collection)((List)destination$iv$iv), (Iterable)DeserializedClassDescriptor.this.getC().getComponents().getAdditionalClassPartsProvider().getSupertypes(DeserializedClassDescriptor.this));
            Iterable $this$mapNotNull$iv = result2;
            boolean $i$f$mapNotNull = false;
            destination$iv$iv = $this$mapNotNull$iv;
            Collection destination$iv$iv2 = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Object bl = $this$forEach$iv$iv$iv.iterator();
            while (bl.hasNext()) {
                NotFoundClasses.MockClassDescriptor mockClassDescriptor;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = bl.next();
                boolean bl2 = false;
                KotlinType supertype = (KotlinType)element$iv$iv;
                boolean bl3 = false;
                ClassifierDescriptor classifierDescriptor = supertype.getConstructor().getDeclarationDescriptor();
                if (!(classifierDescriptor instanceof NotFoundClasses.MockClassDescriptor)) {
                    classifierDescriptor = null;
                }
                if ((NotFoundClasses.MockClassDescriptor)classifierDescriptor == null) continue;
                boolean bl4 = false;
                boolean bl5 = false;
                NotFoundClasses.MockClassDescriptor it$iv$iv = mockClassDescriptor;
                boolean bl6 = false;
                destination$iv$iv2.add(it$iv$iv);
            }
            List unresolved = (List)destination$iv$iv2;
            $this$mapNotNull$iv = unresolved;
            $i$f$mapNotNull = false;
            if (!$this$mapNotNull$iv.isEmpty()) {
                Collection<Object> collection;
                void $this$mapTo$iv$iv2;
                void $this$map$iv2;
                $this$mapNotNull$iv = unresolved;
                annotated = DeserializedClassDescriptor.this;
                object = DeserializedClassDescriptor.this.getC().getComponents().getErrorReporter();
                boolean $i$f$map2 = false;
                $this$mapNotNullTo$iv$iv = $this$map$iv2;
                destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
                boolean $i$f$mapTo2 = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv2) {
                    void it;
                    bl = (NotFoundClasses.MockClassDescriptor)item$iv$iv;
                    collection = destination$iv$iv2;
                    boolean bl7 = false;
                    Object object2 = DescriptorUtilsKt.getClassId((ClassifierDescriptor)it);
                    if (object2 == null || (object2 = ((ClassId)object2).asSingleFqName()) == null || (object2 = ((FqName)object2).asString()) == null) {
                        object2 = it.getName().asString();
                    }
                    Object object3 = object2;
                    collection.add(object3);
                }
                collection = (List)destination$iv$iv2;
                object.reportIncompleteHierarchy((ClassDescriptor)annotated, (List<String>)collection);
            }
            return CollectionsKt.toList(result2);
        }

        @Override
        @NotNull
        public List<TypeParameterDescriptor> getParameters() {
            return (List)this.parameters.invoke();
        }

        @Override
        public boolean isDenotable() {
            return true;
        }

        @Override
        @NotNull
        public DeserializedClassDescriptor getDeclarationDescriptor() {
            return DeserializedClassDescriptor.this;
        }

        @NotNull
        public String toString() {
            String string = DeserializedClassDescriptor.this.getName().toString();
            Intrinsics.checkNotNullExpressionValue(string, "name.toString()");
            return string;
        }

        @Override
        @NotNull
        protected SupertypeLoopChecker getSupertypeLoopChecker() {
            return SupertypeLoopChecker.EMPTY.INSTANCE;
        }

        public DeserializedClassTypeConstructor() {
            super(DeserializedClassDescriptor.this.getC().getStorageManager());
            this.parameters = DeserializedClassDescriptor.this.getC().getStorageManager().createLazyValue((Function0)new Function0<List<? extends TypeParameterDescriptor>>(this){
                final /* synthetic */ DeserializedClassTypeConstructor this$0;

                @NotNull
                public final List<TypeParameterDescriptor> invoke() {
                    return TypeParameterUtilsKt.computeConstructorTypeParameters(this.this$0.DeserializedClassDescriptor.this);
                }
                {
                    this.this$0 = deserializedClassTypeConstructor;
                    super(0);
                }
            });
        }
    }

    private final class DeserializedClassMemberScope
    extends DeserializedMemberScope {
        private final NotNullLazyValue<Collection<DeclarationDescriptor>> allDescriptors;
        private final NotNullLazyValue<Collection<KotlinType>> refinedSupertypes;
        private final KotlinTypeRefiner kotlinTypeRefiner;

        private final DeserializedClassDescriptor getClassDescriptor() {
            return DeserializedClassDescriptor.this;
        }

        @Override
        @NotNull
        public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
            Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
            Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
            return (Collection)this.allDescriptors.invoke();
        }

        @Override
        @NotNull
        public Collection<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            this.recordLookup(name, location);
            return super.getContributedFunctions(name, location);
        }

        @Override
        @NotNull
        public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            this.recordLookup(name, location);
            return super.getContributedVariables(name, location);
        }

        @Override
        protected void computeNonDeclaredFunctions(@NotNull Name name, @NotNull Collection<SimpleFunctionDescriptor> functions2) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(functions2, "functions");
            ArrayList<? extends SimpleFunctionDescriptor> fromSupertypes = new ArrayList<SimpleFunctionDescriptor>();
            for (KotlinType supertype : (Collection)this.refinedSupertypes.invoke()) {
                fromSupertypes.addAll(supertype.getMemberScope().getContributedFunctions(name, NoLookupLocation.FOR_ALREADY_TRACKED));
            }
            CollectionsKt.retainAll((Iterable)functions2, (Function1)new Function1<SimpleFunctionDescriptor, Boolean>(this){
                final /* synthetic */ DeserializedClassMemberScope this$0;

                public final boolean invoke(@NotNull SimpleFunctionDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.getC().getComponents().getPlatformDependentDeclarationFilter().isFunctionAvailable(this.this$0.DeserializedClassDescriptor.this, it);
                }
                {
                    this.this$0 = deserializedClassMemberScope;
                    super(1);
                }
            });
            functions2.addAll(this.getC().getComponents().getAdditionalClassPartsProvider().getFunctions(name, DeserializedClassDescriptor.this));
            this.generateFakeOverrides(name, (Collection)fromSupertypes, functions2);
        }

        @Override
        protected void computeNonDeclaredProperties(@NotNull Name name, @NotNull Collection<PropertyDescriptor> descriptors) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(descriptors, "descriptors");
            ArrayList<? extends PropertyDescriptor> fromSupertypes = new ArrayList<PropertyDescriptor>();
            for (KotlinType supertype : (Collection)this.refinedSupertypes.invoke()) {
                fromSupertypes.addAll(supertype.getMemberScope().getContributedVariables(name, NoLookupLocation.FOR_ALREADY_TRACKED));
            }
            this.generateFakeOverrides(name, (Collection)fromSupertypes, descriptors);
        }

        private final <D extends CallableMemberDescriptor> void generateFakeOverrides(Name name, Collection<? extends D> fromSupertypes, Collection<D> result2) {
            ArrayList<D> fromCurrent = new ArrayList<D>(result2);
            this.getC().getComponents().getKotlinTypeChecker().getOverridingUtil().generateOverridesInFunctionGroup(name, fromSupertypes, (Collection<? extends CallableMemberDescriptor>)fromCurrent, this.getClassDescriptor(), new NonReportingOverrideStrategy(result2){
                final /* synthetic */ Collection $result;

                public void addFakeOverride(@NotNull CallableMemberDescriptor fakeOverride) {
                    Intrinsics.checkNotNullParameter(fakeOverride, "fakeOverride");
                    OverridingUtil.resolveUnknownVisibilityForMember(fakeOverride, null);
                    this.$result.add(fakeOverride);
                }

                protected void conflict(@NotNull CallableMemberDescriptor fromSuper, @NotNull CallableMemberDescriptor fromCurrent) {
                    Intrinsics.checkNotNullParameter(fromSuper, "fromSuper");
                    Intrinsics.checkNotNullParameter(fromCurrent, "fromCurrent");
                }
                {
                    this.$result = $captured_local_variable$0;
                }
            });
        }

        /*
         * WARNING - void declaration
         */
        @Override
        @NotNull
        protected Set<Name> getNonDeclaredFunctionNames() {
            void var2_2;
            void $this$flatMapTo$iv;
            Iterable iterable = this.getClassDescriptor().typeConstructor.getSupertypes();
            Collection destination$iv = new LinkedHashSet();
            boolean $i$f$flatMapTo = false;
            for (Object element$iv : $this$flatMapTo$iv) {
                KotlinType it = (KotlinType)element$iv;
                boolean bl = false;
                Iterable list$iv = it.getMemberScope().getFunctionNames();
                CollectionsKt.addAll(destination$iv, list$iv);
            }
            iterable = var2_2;
            boolean bl = false;
            boolean bl2 = false;
            LinkedHashSet $this$apply = (LinkedHashSet)iterable;
            boolean bl3 = false;
            $this$apply.addAll(this.getC().getComponents().getAdditionalClassPartsProvider().getFunctionsNames(DeserializedClassDescriptor.this));
            return (Set)iterable;
        }

        /*
         * WARNING - void declaration
         */
        @Override
        @NotNull
        protected Set<Name> getNonDeclaredVariableNames() {
            void var2_2;
            void $this$flatMapTo$iv;
            Iterable iterable = this.getClassDescriptor().typeConstructor.getSupertypes();
            Collection destination$iv = new LinkedHashSet();
            boolean $i$f$flatMapTo = false;
            for (Object element$iv : $this$flatMapTo$iv) {
                KotlinType it = (KotlinType)element$iv;
                boolean bl = false;
                Iterable list$iv = it.getMemberScope().getVariableNames();
                CollectionsKt.addAll(destination$iv, list$iv);
            }
            return (Set)var2_2;
        }

        /*
         * WARNING - void declaration
         */
        @Override
        @Nullable
        protected Set<Name> getNonDeclaredClassifierNames() {
            Object v0;
            block2: {
                void var2_2;
                void $this$flatMapToNullable$iv;
                Iterable iterable = this.getClassDescriptor().typeConstructor.getSupertypes();
                Collection destination$iv = new LinkedHashSet();
                boolean $i$f$flatMapToNullable = false;
                for (Object element$iv : $this$flatMapToNullable$iv) {
                    Iterable list$iv;
                    KotlinType it = (KotlinType)element$iv;
                    boolean bl = false;
                    if ((Iterable)it.getMemberScope().getClassifierNames() == null) {
                        v0 = null;
                        break block2;
                    }
                    CollectionsKt.addAll(destination$iv, list$iv);
                }
                v0 = var2_2;
            }
            return v0;
        }

        @Override
        @Nullable
        public ClassifierDescriptor getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            this.recordLookup(name, location);
            Object object = this.getClassDescriptor().enumEntries;
            if (object != null && (object = ((EnumEntryClassDescriptors)object).findEnumEntry(name)) != null) {
                Object object2 = object;
                boolean bl = false;
                boolean bl2 = false;
                Object it = object2;
                boolean bl3 = false;
                return (ClassifierDescriptor)it;
            }
            return super.getContributedClassifier(name, location);
        }

        @Override
        @NotNull
        protected ClassId createClassId(@NotNull Name name) {
            Intrinsics.checkNotNullParameter(name, "name");
            ClassId classId = DeserializedClassDescriptor.this.classId.createNestedClassId(name);
            Intrinsics.checkNotNullExpressionValue(classId, "classId.createNestedClassId(name)");
            return classId;
        }

        @Override
        protected void addEnumEntryDescriptors(@NotNull Collection<DeclarationDescriptor> result2, @NotNull Function1<? super Name, Boolean> nameFilter) {
            Intrinsics.checkNotNullParameter(result2, "result");
            Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
            EnumEntryClassDescriptors enumEntryClassDescriptors = this.getClassDescriptor().enumEntries;
            Collection collection = enumEntryClassDescriptors != null ? enumEntryClassDescriptors.all() : null;
            boolean bl = false;
            Collection collection2 = collection;
            if (collection2 == null) {
                collection2 = CollectionsKt.emptyList();
            }
            result2.addAll(collection2);
        }

        @Override
        public void recordLookup(@NotNull Name name, @NotNull LookupLocation location) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            UtilsKt.record(this.getC().getComponents().getLookupTracker(), location, this.getClassDescriptor(), name);
        }

        /*
         * WARNING - void declaration
         */
        public DeserializedClassMemberScope(KotlinTypeRefiner kotlinTypeRefiner) {
            void it;
            Object object;
            Iterable $this$mapTo$iv$iv;
            void $this$map$iv;
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
            DeserializationContext deserializationContext = DeserializedClassDescriptor.this.getC();
            List<ProtoBuf.Function> list = DeserializedClassDescriptor.this.getClassProto().getFunctionList();
            Intrinsics.checkNotNullExpressionValue(list, "classProto.functionList");
            Collection collection = list;
            List<ProtoBuf.Property> list2 = DeserializedClassDescriptor.this.getClassProto().getPropertyList();
            Intrinsics.checkNotNullExpressionValue(list2, "classProto.propertyList");
            Collection collection2 = list2;
            List<ProtoBuf.TypeAlias> list3 = DeserializedClassDescriptor.this.getClassProto().getTypeAliasList();
            Intrinsics.checkNotNullExpressionValue(list3, "classProto.typeAliasList");
            Collection collection3 = list3;
            List<Integer> list4 = DeserializedClassDescriptor.this.getClassProto().getNestedClassNameList();
            Intrinsics.checkNotNullExpressionValue(list4, "classProto.nestedClassNameList");
            Iterable iterable = list4;
            NameResolver nameResolver = DeserializedClassDescriptor.this.getC().getNameResolver();
            Collection collection4 = collection3;
            Collection collection5 = collection2;
            Collection collection6 = collection;
            DeserializationContext deserializationContext2 = deserializationContext;
            DeserializedClassMemberScope deserializedClassMemberScope = this;
            boolean $i$f$map = false;
            void var6_12 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void p1;
                int n = ((Number)item$iv$iv).intValue();
                object = destination$iv$iv;
                boolean bl = false;
                Name name = NameResolverUtilKt.getName(nameResolver, (int)p1);
                object.add(name);
            }
            object = (List)destination$iv$iv;
            iterable = object;
            boolean bl = false;
            boolean bl2 = false;
            $this$mapTo$iv$iv = iterable;
            boolean bl3 = false;
            object = new Function0<List<? extends Name>>((List)it){
                final /* synthetic */ List $it;

                @NotNull
                public final List<Name> invoke() {
                    return this.$it;
                }
                {
                    this.$it = list;
                    super(0);
                }
            };
            super(deserializationContext2, collection6, collection5, collection4, (Function0<? extends Collection<Name>>)object);
            this.kotlinTypeRefiner = kotlinTypeRefiner;
            this.allDescriptors = this.getC().getStorageManager().createLazyValue((Function0)new Function0<Collection<? extends DeclarationDescriptor>>(this){
                final /* synthetic */ DeserializedClassMemberScope this$0;

                @NotNull
                public final Collection<DeclarationDescriptor> invoke() {
                    return this.this$0.computeDescriptors(DescriptorKindFilter.ALL, MemberScope.Companion.getALL_NAME_FILTER(), NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS);
                }
                {
                    this.this$0 = deserializedClassMemberScope;
                    super(0);
                }
            });
            this.refinedSupertypes = this.getC().getStorageManager().createLazyValue((Function0)new Function0<Collection<? extends KotlinType>>(this){
                final /* synthetic */ DeserializedClassMemberScope this$0;

                @NotNull
                public final Collection<KotlinType> invoke() {
                    return DeserializedClassMemberScope.access$getKotlinTypeRefiner$p(this.this$0).refineSupertypes(DeserializedClassMemberScope.access$getClassDescriptor$p(this.this$0));
                }
                {
                    this.this$0 = deserializedClassMemberScope;
                    super(0);
                }
            });
        }

        public static final /* synthetic */ KotlinTypeRefiner access$getKotlinTypeRefiner$p(DeserializedClassMemberScope $this) {
            return $this.kotlinTypeRefiner;
        }

        public static final /* synthetic */ DeserializedClassDescriptor access$getClassDescriptor$p(DeserializedClassMemberScope $this) {
            return $this.getClassDescriptor();
        }
    }

    private final class EnumEntryClassDescriptors {
        private final Map<Name, ProtoBuf.EnumEntry> enumEntryProtos;
        private final MemoizedFunctionToNullable<Name, ClassDescriptor> enumEntryByName;
        private final NotNullLazyValue<Set<Name>> enumMemberNames;

        @Nullable
        public final ClassDescriptor findEnumEntry(@NotNull Name name) {
            Intrinsics.checkNotNullParameter(name, "name");
            return (ClassDescriptor)this.enumEntryByName.invoke(name);
        }

        private final Set<Name> computeEnumMemberNames() {
            Object object;
            ProtoBuf.Property it;
            Collection collection;
            HashSet<Name> result2 = new HashSet<Name>();
            for (KotlinType supertype : DeserializedClassDescriptor.this.getTypeConstructor().getSupertypes()) {
                for (DeclarationDescriptor descriptor2 : ResolutionScope.DefaultImpls.getContributedDescriptors$default(supertype.getMemberScope(), null, null, 3, null)) {
                    if (!(descriptor2 instanceof SimpleFunctionDescriptor) && !(descriptor2 instanceof PropertyDescriptor)) continue;
                    result2.add(descriptor2.getName());
                }
            }
            List<ProtoBuf.Function> list = DeserializedClassDescriptor.this.getClassProto().getFunctionList();
            Intrinsics.checkNotNullExpressionValue(list, "classProto.functionList");
            Iterable $this$mapTo$iv = list;
            boolean $i$f$mapTo = false;
            for (Object item$iv : $this$mapTo$iv) {
                ProtoBuf.Function function = (ProtoBuf.Function)item$iv;
                collection = result2;
                boolean bl = false;
                NameResolver nameResolver = DeserializedClassDescriptor.this.getC().getNameResolver();
                void v2 = it;
                Intrinsics.checkNotNullExpressionValue(v2, "it");
                object = NameResolverUtilKt.getName(nameResolver, v2.getName());
                collection.add(object);
            }
            Set set = (Set)((Collection)result2);
            List<ProtoBuf.Property> list2 = DeserializedClassDescriptor.this.getClassProto().getPropertyList();
            Intrinsics.checkNotNullExpressionValue(list2, "classProto.propertyList");
            $this$mapTo$iv = list2;
            collection = set;
            $i$f$mapTo = false;
            for (Object item$iv : $this$mapTo$iv) {
                it = (ProtoBuf.Property)item$iv;
                object = result2;
                boolean bl = false;
                NameResolver nameResolver = DeserializedClassDescriptor.this.getC().getNameResolver();
                ProtoBuf.Property property = it;
                Intrinsics.checkNotNullExpressionValue(property, "it");
                Name name = NameResolverUtilKt.getName(nameResolver, property.getName());
                object.add(name);
            }
            object = result2;
            return SetsKt.plus(collection, (Iterable)object);
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final Collection<ClassDescriptor> all() {
            void $this$mapNotNullTo$iv$iv;
            Iterable $this$mapNotNull$iv = this.enumEntryProtos.keySet();
            boolean $i$f$mapNotNull = false;
            Iterable iterable = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
            while (iterator2.hasNext()) {
                ClassDescriptor classDescriptor;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = iterator2.next();
                boolean bl = false;
                Name name = (Name)element$iv$iv;
                boolean bl2 = false;
                if (this.findEnumEntry(name) == null) continue;
                boolean bl3 = false;
                boolean bl4 = false;
                ClassDescriptor it$iv$iv = classDescriptor;
                boolean bl5 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            return (List)destination$iv$iv;
        }

        /*
         * WARNING - void declaration
         */
        public EnumEntryClassDescriptors() {
            Map map2;
            void $this$associateByTo$iv$iv;
            void $this$associateBy$iv;
            List<ProtoBuf.EnumEntry> list = DeserializedClassDescriptor.this.getClassProto().getEnumEntryList();
            Intrinsics.checkNotNullExpressionValue(list, "classProto.enumEntryList");
            Iterable iterable = list;
            EnumEntryClassDescriptors enumEntryClassDescriptors = this;
            boolean $i$f$associateBy = false;
            int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associateBy$iv, 10)), 16);
            void var5_6 = $this$associateBy$iv;
            Map destination$iv$iv = new LinkedHashMap(capacity$iv);
            boolean $i$f$associateByTo = false;
            for (Object element$iv$iv : $this$associateByTo$iv$iv) {
                void it;
                ProtoBuf.EnumEntry enumEntry = (ProtoBuf.EnumEntry)element$iv$iv;
                map2 = destination$iv$iv;
                boolean bl = false;
                NameResolver nameResolver = DeserializedClassDescriptor.this.getC().getNameResolver();
                void v2 = it;
                Intrinsics.checkNotNullExpressionValue(v2, "it");
                Name name = NameResolverUtilKt.getName(nameResolver, v2.getName());
                map2.put(name, element$iv$iv);
            }
            enumEntryClassDescriptors.enumEntryProtos = map2 = destination$iv$iv;
            this.enumEntryByName = DeserializedClassDescriptor.this.getC().getStorageManager().createMemoizedFunctionWithNullableValues((Function1)new Function1<Name, ClassDescriptor>(this){
                final /* synthetic */ EnumEntryClassDescriptors this$0;

                @Nullable
                public final ClassDescriptor invoke(@NotNull Name name) {
                    EnumEntrySyntheticClassDescriptor enumEntrySyntheticClassDescriptor;
                    Intrinsics.checkNotNullParameter(name, "name");
                    ProtoBuf.EnumEntry enumEntry = (ProtoBuf.EnumEntry)EnumEntryClassDescriptors.access$getEnumEntryProtos$p(this.this$0).get(name);
                    if (enumEntry != null) {
                        ProtoBuf.EnumEntry enumEntry2 = enumEntry;
                        boolean bl = false;
                        boolean bl2 = false;
                        ProtoBuf.EnumEntry proto = enumEntry2;
                        boolean bl3 = false;
                        enumEntrySyntheticClassDescriptor = EnumEntrySyntheticClassDescriptor.create(this.this$0.DeserializedClassDescriptor.this.getC().getStorageManager(), this.this$0.DeserializedClassDescriptor.this, name, EnumEntryClassDescriptors.access$getEnumMemberNames$p(this.this$0), new DeserializedAnnotations(this.this$0.DeserializedClassDescriptor.this.getC().getStorageManager(), (Function0<? extends List<? extends AnnotationDescriptor>>)new Function0<List<? extends AnnotationDescriptor>>(proto, this, name){
                            final /* synthetic */ ProtoBuf.EnumEntry $proto;
                            final /* synthetic */ enumEntryByName.1 this$0;
                            final /* synthetic */ Name $name$inlined;
                            {
                                this.$proto = enumEntry;
                                this.this$0 = var2_2;
                                this.$name$inlined = name;
                                super(0);
                            }

                            @NotNull
                            public final List<AnnotationDescriptor> invoke() {
                                return CollectionsKt.toList((Iterable)this.this$0.this$0.DeserializedClassDescriptor.this.getC().getComponents().getAnnotationAndConstantLoader().loadEnumEntryAnnotations(this.this$0.this$0.DeserializedClassDescriptor.this.getThisAsProtoContainer$deserialization(), this.$proto));
                            }
                        }), SourceElement.NO_SOURCE);
                    } else {
                        enumEntrySyntheticClassDescriptor = null;
                    }
                    return enumEntrySyntheticClassDescriptor;
                }
                {
                    this.this$0 = enumEntryClassDescriptors;
                    super(1);
                }
            });
            this.enumMemberNames = DeserializedClassDescriptor.this.getC().getStorageManager().createLazyValue((Function0)new Function0<Set<? extends Name>>(this){
                final /* synthetic */ EnumEntryClassDescriptors this$0;

                @NotNull
                public final Set<Name> invoke() {
                    return EnumEntryClassDescriptors.access$computeEnumMemberNames(this.this$0);
                }
                {
                    this.this$0 = enumEntryClassDescriptors;
                    super(0);
                }
            });
        }

        public static final /* synthetic */ Map access$getEnumEntryProtos$p(EnumEntryClassDescriptors $this) {
            return $this.enumEntryProtos;
        }

        public static final /* synthetic */ NotNullLazyValue access$getEnumMemberNames$p(EnumEntryClassDescriptors $this) {
            return $this.enumMemberNames;
        }

        public static final /* synthetic */ Set access$computeEnumMemberNames(EnumEntryClassDescriptors $this) {
            return $this.computeEnumMemberNames();
        }
    }
}

