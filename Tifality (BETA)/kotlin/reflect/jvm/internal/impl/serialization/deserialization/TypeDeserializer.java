/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.SuspendFunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationContext;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.NameResolverUtilKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ProtoEnumFlags;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.SuspendFunctionTypeUtilKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedAnnotations;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionForAbsentTypeParameter;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionBase;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeDeserializer {
    private final Function1<Integer, ClassifierDescriptor> classifierDescriptors;
    private final Function1<Integer, ClassifierDescriptor> typeAliasDescriptors;
    private final Map<Integer, TypeParameterDescriptor> typeParameterDescriptors;
    private final DeserializationContext c;
    private final TypeDeserializer parent;
    private final String debugName;
    private final String containerPresentableName;
    private boolean experimentalSuspendFunctionTypeEncountered;

    @NotNull
    public final List<TypeParameterDescriptor> getOwnTypeParameters() {
        return CollectionsKt.toList((Iterable)this.typeParameterDescriptors.values());
    }

    @NotNull
    public final KotlinType type(@NotNull ProtoBuf.Type proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        if (proto.hasFlexibleTypeCapabilitiesId()) {
            String id = this.c.getNameResolver().getString(proto.getFlexibleTypeCapabilitiesId());
            SimpleType lowerBound = TypeDeserializer.simpleType$default(this, proto, false, 2, null);
            ProtoBuf.Type type2 = ProtoTypeTableUtilKt.flexibleUpperBound(proto, this.c.getTypeTable());
            Intrinsics.checkNotNull(type2);
            SimpleType upperBound = TypeDeserializer.simpleType$default(this, type2, false, 2, null);
            return this.c.getComponents().getFlexibleTypeDeserializer().create(proto, id, lowerBound, upperBound);
        }
        return this.simpleType(proto, true);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final SimpleType simpleType(@NotNull ProtoBuf.Type proto, boolean expandTypeAliases) {
        Object computedType;
        block11: {
            Object object;
            SimpleType simpleType2;
            block10: {
                SimpleType simpleType3;
                boolean bl;
                boolean bl2;
                void $this$mapIndexedTo$iv$iv;
                SimpleType localClassifierType;
                Intrinsics.checkNotNullParameter(proto, "proto");
                SimpleType simpleType4 = proto.hasClassName() ? this.computeLocalClassifierReplacementType(proto.getClassName()) : (localClassifierType = proto.hasTypeAliasName() ? this.computeLocalClassifierReplacementType(proto.getTypeAliasName()) : null);
                if (localClassifierType != null) {
                    return localClassifierType;
                }
                TypeConstructor constructor = this.typeConstructor(proto);
                if (ErrorUtils.isError(constructor.getDeclarationDescriptor())) {
                    SimpleType simpleType5 = ErrorUtils.createErrorTypeWithCustomConstructor(constructor.toString(), constructor);
                    Intrinsics.checkNotNullExpressionValue(simpleType5, "ErrorUtils.createErrorTy\u2026.toString(), constructor)");
                    return simpleType5;
                }
                DeserializedAnnotations annotations2 = new DeserializedAnnotations(this.c.getStorageManager(), (Function0<? extends List<? extends AnnotationDescriptor>>)new Function0<List<? extends AnnotationDescriptor>>(this, proto){
                    final /* synthetic */ TypeDeserializer this$0;
                    final /* synthetic */ ProtoBuf.Type $proto;

                    @NotNull
                    public final List<AnnotationDescriptor> invoke() {
                        return TypeDeserializer.access$getC$p(this.this$0).getComponents().getAnnotationAndConstantLoader().loadTypeAnnotations(this.$proto, TypeDeserializer.access$getC$p(this.this$0).getNameResolver());
                    }
                    {
                        this.this$0 = typeDeserializer;
                        this.$proto = type2;
                        super(0);
                    }
                });
                Function1<ProtoBuf.Type, List<? extends ProtoBuf.Type.Argument>> $fun$collectAllArguments$1 = new Function1<ProtoBuf.Type, List<? extends ProtoBuf.Type.Argument>>(this){
                    final /* synthetic */ TypeDeserializer this$0;

                    @NotNull
                    public final List<ProtoBuf.Type.Argument> invoke(@NotNull ProtoBuf.Type $this$collectAllArguments) {
                        Intrinsics.checkNotNullParameter($this$collectAllArguments, "$this$collectAllArguments");
                        List<ProtoBuf.Type.Argument> list = $this$collectAllArguments.getArgumentList();
                        Intrinsics.checkNotNullExpressionValue(list, "argumentList");
                        Collection collection = list;
                        ProtoBuf.Type type2 = ProtoTypeTableUtilKt.outerType($this$collectAllArguments, TypeDeserializer.access$getC$p(this.this$0).getTypeTable());
                        List<ProtoBuf.Type.Argument> list2 = type2 != null ? this.invoke(type2) : null;
                        boolean bl = false;
                        List<ProtoBuf.Type.Argument> list3 = list2;
                        if (list3 == null) {
                            list3 = CollectionsKt.emptyList();
                        }
                        return CollectionsKt.plus(collection, (Iterable)list3);
                    }
                    {
                        this.this$0 = typeDeserializer;
                        super(1);
                    }
                };
                Iterable $this$mapIndexed$iv = $fun$collectAllArguments$1.invoke(proto);
                boolean $i$f$mapIndexed = false;
                Iterable iterable = $this$mapIndexed$iv;
                Object destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
                boolean $i$f$mapIndexedTo = false;
                int index$iv$iv = 0;
                for (Object item$iv$iv : $this$mapIndexedTo$iv$iv) {
                    void argumentProto;
                    void index;
                    int n = index$iv$iv++;
                    boolean bl3 = false;
                    if (n < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    ProtoBuf.Type.Argument argument = (ProtoBuf.Type.Argument)item$iv$iv;
                    int n2 = n;
                    Collection collection = destination$iv$iv;
                    boolean bl4 = false;
                    List<TypeParameterDescriptor> list = constructor.getParameters();
                    Intrinsics.checkNotNullExpressionValue(list, "constructor.parameters");
                    TypeProjection typeProjection = this.typeArgument(CollectionsKt.getOrNull(list, (int)index), (ProtoBuf.Type.Argument)argumentProto);
                    collection.add(typeProjection);
                }
                List arguments2 = CollectionsKt.toList((List)destination$iv$iv);
                ClassifierDescriptor declarationDescriptor = constructor.getDeclarationDescriptor();
                if (expandTypeAliases && declarationDescriptor instanceof TypeAliasDescriptor) {
                    SimpleType expandedType;
                    destination$iv$iv = KotlinTypeFactory.INSTANCE;
                    bl2 = false;
                    bl = false;
                    Object $this$with = destination$iv$iv;
                    boolean bl5 = false;
                    simpleType3 = expandedType.makeNullableAsSpecified(KotlinTypeKt.isNullable(expandedType = KotlinTypeFactory.computeExpandedType((TypeAliasDescriptor)declarationDescriptor, arguments2)) || proto.getNullable()).replaceAnnotations(Annotations.Companion.create(CollectionsKt.plus((Iterable)annotations2, (Iterable)expandedType.getAnnotations())));
                } else {
                    Boolean bl6 = Flags.SUSPEND_TYPE.get(proto.getFlags());
                    Intrinsics.checkNotNullExpressionValue(bl6, "Flags.SUSPEND_TYPE.get(proto.flags)");
                    simpleType3 = simpleType2 = bl6 != false ? this.createSuspendFunctionType(annotations2, constructor, arguments2, proto.getNullable()) : KotlinTypeFactory.simpleType$default(annotations2, constructor, arguments2, proto.getNullable(), null, 16, null);
                }
                if ((object = ProtoTypeTableUtilKt.abbreviatedType(proto, this.c.getTypeTable())) == null) break block10;
                destination$iv$iv = object;
                bl2 = false;
                bl = false;
                Object it = destination$iv$iv;
                boolean bl7 = false;
                object = SpecialTypesKt.withAbbreviation(simpleType2, this.simpleType((ProtoBuf.Type)it, false));
                if (object != null) break block11;
            }
            object = computedType = simpleType2;
        }
        if (proto.hasClassName()) {
            ClassId classId = NameResolverUtilKt.getClassId(this.c.getNameResolver(), proto.getClassName());
            return this.c.getComponents().getPlatformDependentTypeTransformer().transformPlatformType(classId, (SimpleType)computedType);
        }
        return computedType;
    }

    public static /* synthetic */ SimpleType simpleType$default(TypeDeserializer typeDeserializer, ProtoBuf.Type type2, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = true;
        }
        return typeDeserializer.simpleType(type2, bl);
    }

    private final TypeConstructor typeConstructor(ProtoBuf.Type proto) {
        Object object;
        Function1<Integer, ClassDescriptor> $fun$notFoundClass$1 = new Function1<Integer, ClassDescriptor>(this, proto){
            final /* synthetic */ TypeDeserializer this$0;
            final /* synthetic */ ProtoBuf.Type $proto;

            @NotNull
            public final ClassDescriptor invoke(int classIdIndex) {
                ClassId classId = NameResolverUtilKt.getClassId(TypeDeserializer.access$getC$p(this.this$0).getNameResolver(), classIdIndex);
                List<Integer> typeParametersCount2 = SequencesKt.toMutableList(SequencesKt.map(SequencesKt.generateSequence(this.$proto, (Function1)new Function1<ProtoBuf.Type, ProtoBuf.Type>(this){
                    final /* synthetic */ typeConstructor.1 this$0;

                    @Nullable
                    public final ProtoBuf.Type invoke(@NotNull ProtoBuf.Type it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return ProtoTypeTableUtilKt.outerType(it, TypeDeserializer.access$getC$p(this.this$0.this$0).getTypeTable());
                    }
                    {
                        this.this$0 = var1_1;
                        super(1);
                    }
                }), typeConstructor.typeParametersCount.2.INSTANCE));
                int classNestingLevel2 = SequencesKt.count(SequencesKt.generateSequence(classId, (Function1)typeConstructor.classNestingLevel.1.INSTANCE));
                while (typeParametersCount2.size() < classNestingLevel2) {
                    typeParametersCount2.add(0);
                }
                return TypeDeserializer.access$getC$p(this.this$0).getComponents().getNotFoundClasses().getClass(classId, typeParametersCount2);
            }
            {
                this.this$0 = typeDeserializer;
                this.$proto = type2;
                super(1);
            }
        };
        if (proto.hasClassName()) {
            ClassifierDescriptor classifierDescriptor = this.classifierDescriptors.invoke(proto.getClassName());
            if (classifierDescriptor == null) {
                classifierDescriptor = $fun$notFoundClass$1.invoke(proto.getClassName());
            }
            TypeConstructor typeConstructor2 = classifierDescriptor.getTypeConstructor();
            object = typeConstructor2;
            Intrinsics.checkNotNullExpressionValue(typeConstructor2, "(classifierDescriptors(p\u2026assName)).typeConstructor");
        } else if (proto.hasTypeParameter()) {
            object = this.typeParameterTypeConstructor(proto.getTypeParameter());
            if (object == null) {
                TypeConstructor typeConstructor3 = ErrorUtils.createErrorTypeConstructor("Unknown type parameter " + proto.getTypeParameter() + ". Please try recompiling module containing \"" + this.containerPresentableName + '\"');
                object = typeConstructor3;
                Intrinsics.checkNotNullExpressionValue(typeConstructor3, "ErrorUtils.createErrorTy\u2026\\\"\"\n                    )");
            }
        } else if (proto.hasTypeParameterName()) {
            TypeParameterDescriptor parameter;
            Object object2;
            Object v4;
            String name;
            DeclarationDescriptor container;
            block13: {
                container = this.c.getContainingDeclaration();
                name = this.c.getNameResolver().getString(proto.getTypeParameterName());
                Iterable iterable = this.getOwnTypeParameters();
                boolean bl = false;
                Iterable iterable2 = iterable;
                boolean bl2 = false;
                for (Object t : iterable2) {
                    TypeParameterDescriptor it = (TypeParameterDescriptor)t;
                    boolean bl3 = false;
                    if (!Intrinsics.areEqual(it.getName().asString(), name)) continue;
                    v4 = t;
                    break block13;
                }
                v4 = null;
            }
            if ((object2 = (parameter = (TypeParameterDescriptor)v4)) == null || (object2 = object2.getTypeConstructor()) == null) {
                object2 = ErrorUtils.createErrorTypeConstructor("Deserialized type parameter " + name + " in " + container);
            }
            object = object2;
            Intrinsics.checkNotNullExpressionValue(object2, "parameter?.typeConstruct\u2026ter $name in $container\")");
        } else if (proto.hasTypeAliasName()) {
            ClassifierDescriptor classifierDescriptor = this.typeAliasDescriptors.invoke(proto.getTypeAliasName());
            if (classifierDescriptor == null) {
                classifierDescriptor = $fun$notFoundClass$1.invoke(proto.getTypeAliasName());
            }
            TypeConstructor typeConstructor4 = classifierDescriptor.getTypeConstructor();
            object = typeConstructor4;
            Intrinsics.checkNotNullExpressionValue(typeConstructor4, "(typeAliasDescriptors(pr\u2026iasName)).typeConstructor");
        } else {
            TypeConstructor typeConstructor5 = ErrorUtils.createErrorTypeConstructor("Unknown type");
            object = typeConstructor5;
            Intrinsics.checkNotNullExpressionValue(typeConstructor5, "ErrorUtils.createErrorTy\u2026nstructor(\"Unknown type\")");
        }
        return object;
    }

    private final SimpleType createSuspendFunctionType(Annotations annotations2, TypeConstructor functionTypeConstructor, List<? extends TypeProjection> arguments2, boolean nullable) {
        SimpleType result2;
        SimpleType simpleType2;
        SimpleType simpleType3;
        switch (functionTypeConstructor.getParameters().size() - arguments2.size()) {
            case 0: {
                simpleType3 = this.createSuspendFunctionTypeForBasicCase(annotations2, functionTypeConstructor, arguments2, nullable);
                break;
            }
            case 1: {
                int arity = arguments2.size() - 1;
                if (arity >= 0) {
                    ClassDescriptor classDescriptor = functionTypeConstructor.getBuiltIns().getSuspendFunction(arity);
                    Intrinsics.checkNotNullExpressionValue(classDescriptor, "functionTypeConstructor.\u2026getSuspendFunction(arity)");
                    TypeConstructor typeConstructor2 = classDescriptor.getTypeConstructor();
                    Intrinsics.checkNotNullExpressionValue(typeConstructor2, "functionTypeConstructor.\u2026on(arity).typeConstructor");
                    simpleType3 = KotlinTypeFactory.simpleType$default(annotations2, typeConstructor2, arguments2, nullable, null, 16, null);
                    break;
                }
                simpleType3 = null;
                break;
            }
            default: {
                simpleType3 = null;
            }
        }
        if ((simpleType2 = (result2 = simpleType3)) == null) {
            SimpleType simpleType4 = ErrorUtils.createErrorTypeWithArguments("Bad suspend function in metadata with constructor: " + functionTypeConstructor, arguments2);
            simpleType2 = simpleType4;
            Intrinsics.checkNotNullExpressionValue(simpleType4, "ErrorUtils.createErrorTy\u2026      arguments\n        )");
        }
        return simpleType2;
    }

    private final SimpleType createSuspendFunctionTypeForBasicCase(Annotations annotations2, TypeConstructor functionTypeConstructor, List<? extends TypeProjection> arguments2, boolean nullable) {
        SimpleType functionType = KotlinTypeFactory.simpleType$default(annotations2, functionTypeConstructor, arguments2, nullable, null, 16, null);
        return !FunctionTypesKt.isFunctionType(functionType) ? null : this.transformRuntimeFunctionTypeToSuspendFunction(functionType);
    }

    private final SimpleType transformRuntimeFunctionTypeToSuspendFunction(KotlinType funType) {
        FqName continuationArgumentFqName;
        boolean isReleaseCoroutines = this.c.getComponents().getConfiguration().getReleaseCoroutines();
        Object object = CollectionsKt.lastOrNull(FunctionTypesKt.getValueParameterTypesFromFunctionType(funType));
        if (object == null || (object = object.getType()) == null) {
            return null;
        }
        Intrinsics.checkNotNullExpressionValue(object, "funType.getValueParamete\u2026ll()?.type ?: return null");
        Object continuationArgumentType = object;
        ClassifierDescriptor classifierDescriptor = ((KotlinType)continuationArgumentType).getConstructor().getDeclarationDescriptor();
        FqName fqName2 = continuationArgumentFqName = classifierDescriptor != null ? DescriptorUtilsKt.getFqNameSafe(classifierDescriptor) : null;
        if (((KotlinType)continuationArgumentType).getArguments().size() != 1 || !SuspendFunctionTypesKt.isContinuation(continuationArgumentFqName, true) && !SuspendFunctionTypesKt.isContinuation(continuationArgumentFqName, false)) {
            return (SimpleType)funType;
        }
        KotlinType kotlinType = CollectionsKt.single(((KotlinType)continuationArgumentType).getArguments()).getType();
        Intrinsics.checkNotNullExpressionValue(kotlinType, "continuationArgumentType.arguments.single().type");
        KotlinType suspendReturnType = kotlinType;
        DeclarationDescriptor $this$safeAs$iv = this.c.getContainingDeclaration();
        boolean $i$f$safeAs = false;
        DeclarationDescriptor declarationDescriptor = $this$safeAs$iv;
        if (!(declarationDescriptor instanceof CallableDescriptor)) {
            declarationDescriptor = null;
        }
        CallableDescriptor callableDescriptor = (CallableDescriptor)declarationDescriptor;
        if (Intrinsics.areEqual(callableDescriptor != null ? DescriptorUtilsKt.fqNameOrNull(callableDescriptor) : null, SuspendFunctionTypeUtilKt.KOTLIN_SUSPEND_BUILT_IN_FUNCTION_FQ_NAME)) {
            return this.createSimpleSuspendFunctionType(funType, suspendReturnType);
        }
        this.experimentalSuspendFunctionTypeEncountered = this.experimentalSuspendFunctionTypeEncountered || isReleaseCoroutines && SuspendFunctionTypesKt.isContinuation(continuationArgumentFqName, !isReleaseCoroutines);
        return this.createSimpleSuspendFunctionType(funType, suspendReturnType);
    }

    /*
     * WARNING - void declaration
     */
    private final SimpleType createSimpleSuspendFunctionType(KotlinType funType, KotlinType suspendReturnType) {
        Collection<KotlinType> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Iterable iterable = CollectionsKt.dropLast(FunctionTypesKt.getValueParameterTypesFromFunctionType(funType), 1);
        KotlinType kotlinType = FunctionTypesKt.getReceiverTypeFromFunctionType(funType);
        Annotations annotations2 = funType.getAnnotations();
        KotlinBuiltIns kotlinBuiltIns = TypeUtilsKt.getBuiltIns(funType);
        boolean $i$f$map = false;
        void var5_8 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void p1;
            TypeProjection typeProjection = (TypeProjection)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            KotlinType kotlinType2 = p1.getType();
            collection.add(kotlinType2);
        }
        collection = (List)destination$iv$iv;
        return FunctionTypesKt.createFunctionType(kotlinBuiltIns, annotations2, kotlinType, (List<? extends KotlinType>)collection, null, suspendReturnType, true).makeNullableAsSpecified(funType.isMarkedNullable());
    }

    private final TypeConstructor typeParameterTypeConstructor(int typeParameterId) {
        Object object = this.typeParameterDescriptors.get(typeParameterId);
        if (object == null || (object = object.getTypeConstructor()) == null) {
            TypeDeserializer typeDeserializer = this.parent;
            object = typeDeserializer != null ? typeDeserializer.typeParameterTypeConstructor(typeParameterId) : null;
        }
        return object;
    }

    private final ClassifierDescriptor computeClassifierDescriptor(int fqNameIndex) {
        ClassId id = NameResolverUtilKt.getClassId(this.c.getNameResolver(), fqNameIndex);
        if (id.isLocal()) {
            return this.c.getComponents().deserializeClass(id);
        }
        return FindClassInModuleKt.findClassifierAcrossModuleDependencies(this.c.getComponents().getModuleDescriptor(), id);
    }

    private final SimpleType computeLocalClassifierReplacementType(int className) {
        if (NameResolverUtilKt.getClassId(this.c.getNameResolver(), className).isLocal()) {
            return this.c.getComponents().getLocalClassifierTypeSettings().getReplacementTypeForLocalClassifiers();
        }
        return null;
    }

    private final ClassifierDescriptor computeTypeAliasDescriptor(int fqNameIndex) {
        ClassId id = NameResolverUtilKt.getClassId(this.c.getNameResolver(), fqNameIndex);
        if (id.isLocal()) {
            return null;
        }
        return FindClassInModuleKt.findTypeAliasAcrossModuleDependencies(this.c.getComponents().getModuleDescriptor(), id);
    }

    private final TypeProjection typeArgument(TypeParameterDescriptor parameter, ProtoBuf.Type.Argument typeArgumentProto) {
        if (typeArgumentProto.getProjection() == ProtoBuf.Type.Argument.Projection.STAR) {
            return parameter == null ? (TypeProjectionBase)new StarProjectionForAbsentTypeParameter(this.c.getComponents().getModuleDescriptor().getBuiltIns()) : (TypeProjectionBase)new StarProjectionImpl(parameter);
        }
        ProtoBuf.Type.Argument.Projection projection = typeArgumentProto.getProjection();
        Intrinsics.checkNotNullExpressionValue(projection, "typeArgumentProto.projection");
        Variance projection2 = ProtoEnumFlags.INSTANCE.variance(projection);
        ProtoBuf.Type type2 = ProtoTypeTableUtilKt.type(typeArgumentProto, this.c.getTypeTable());
        if (type2 == null) {
            return new TypeProjectionImpl(ErrorUtils.createErrorType("No type recorded"));
        }
        ProtoBuf.Type type3 = type2;
        return new TypeProjectionImpl(projection2, this.type(type3));
    }

    @NotNull
    public String toString() {
        return this.debugName + (this.parent == null ? "" : ". Child of " + this.parent.debugName);
    }

    public final boolean getExperimentalSuspendFunctionTypeEncountered() {
        return this.experimentalSuspendFunctionTypeEncountered;
    }

    /*
     * WARNING - void declaration
     */
    public TypeDeserializer(@NotNull DeserializationContext c, @Nullable TypeDeserializer parent, @NotNull List<ProtoBuf.TypeParameter> typeParameterProtos, @NotNull String debugName, @NotNull String containerPresentableName, boolean experimentalSuspendFunctionTypeEncountered) {
        Map map2;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(typeParameterProtos, "typeParameterProtos");
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        Intrinsics.checkNotNullParameter(containerPresentableName, "containerPresentableName");
        this.c = c;
        this.parent = parent;
        this.debugName = debugName;
        this.containerPresentableName = containerPresentableName;
        this.experimentalSuspendFunctionTypeEncountered = experimentalSuspendFunctionTypeEncountered;
        this.classifierDescriptors = this.c.getStorageManager().createMemoizedFunctionWithNullableValues((Function1)new Function1<Integer, ClassifierDescriptor>(this){
            final /* synthetic */ TypeDeserializer this$0;

            @Nullable
            public final ClassifierDescriptor invoke(int fqNameIndex) {
                return TypeDeserializer.access$computeClassifierDescriptor(this.this$0, fqNameIndex);
            }
            {
                this.this$0 = typeDeserializer;
                super(1);
            }
        });
        this.typeAliasDescriptors = this.c.getStorageManager().createMemoizedFunctionWithNullableValues((Function1)new Function1<Integer, ClassifierDescriptor>(this){
            final /* synthetic */ TypeDeserializer this$0;

            @Nullable
            public final ClassifierDescriptor invoke(int fqNameIndex) {
                return TypeDeserializer.access$computeTypeAliasDescriptor(this.this$0, fqNameIndex);
            }
            {
                this.this$0 = typeDeserializer;
                super(1);
            }
        });
        if (typeParameterProtos.isEmpty()) {
            boolean bl = false;
            map2 = MapsKt.emptyMap();
        } else {
            LinkedHashMap result2 = new LinkedHashMap();
            boolean bl = false;
            for (ProtoBuf.TypeParameter proto : (Iterable)typeParameterProtos) {
                void index;
                ((Map)result2).put(proto.getId(), new DeserializedTypeParameterDescriptor(this.c, proto, (int)index));
                ++index;
            }
            map2 = result2;
        }
        this.typeParameterDescriptors = map2;
    }

    public /* synthetic */ TypeDeserializer(DeserializationContext deserializationContext, TypeDeserializer typeDeserializer, List list, String string, String string2, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 0x20) != 0) {
            bl = false;
        }
        this(deserializationContext, typeDeserializer, list, string, string2, bl);
    }

    public static final /* synthetic */ DeserializationContext access$getC$p(TypeDeserializer $this) {
        return $this.c;
    }

    public static final /* synthetic */ ClassifierDescriptor access$computeClassifierDescriptor(TypeDeserializer $this, int fqNameIndex) {
        return $this.computeClassifierDescriptor(fqNameIndex);
    }

    public static final /* synthetic */ ClassifierDescriptor access$computeTypeAliasDescriptor(TypeDeserializer $this, int fqNameIndex) {
        return $this.computeTypeAliasDescriptor(fqNameIndex);
    }
}

