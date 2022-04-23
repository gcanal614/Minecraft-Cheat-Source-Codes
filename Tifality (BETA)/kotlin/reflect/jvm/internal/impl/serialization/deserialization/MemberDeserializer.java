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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FieldDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertySetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotatedCallableKind;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotationAndConstantLoader;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotationDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationContext;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer$containsSuspendFunctionType$1;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.NameResolverUtilKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ProtoContainer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ProtoEnumFlags;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.SuspendFunctionTypeUtilKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedAnnotations;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedSimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedTypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.NonEmptyDeserializedAnnotations;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MemberDeserializer {
    private final AnnotationDeserializer annotationDeserializer;
    private final DeserializationContext c;

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @NotNull
    public final PropertyDescriptor loadProperty(@NotNull ProtoBuf.Property proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        flags = proto.hasFlags() != false ? proto.getFlags() : this.loadOldFlags(proto.getOldFlags());
        v0 = this.c.getContainingDeclaration();
        v1 = this.getAnnotations(proto, flags, AnnotatedCallableKind.PROPERTY);
        v2 = ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(flags));
        v3 = ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(flags));
        v4 = Flags.IS_VAR.get(flags);
        Intrinsics.checkNotNullExpressionValue(v4, "Flags.IS_VAR.get(flags)");
        v5 = v4;
        v6 = NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName());
        v7 = ProtoEnumFlags.INSTANCE.memberKind(Flags.MEMBER_KIND.get(flags));
        v8 = Flags.IS_LATEINIT.get(flags);
        Intrinsics.checkNotNullExpressionValue(v8, "Flags.IS_LATEINIT.get(flags)");
        v9 = v8;
        v10 = Flags.IS_CONST.get(flags);
        Intrinsics.checkNotNullExpressionValue(v10, "Flags.IS_CONST.get(flags)");
        v11 = v10;
        v12 = Flags.IS_EXTERNAL_PROPERTY.get(flags);
        Intrinsics.checkNotNullExpressionValue(v12, "Flags.IS_EXTERNAL_PROPERTY.get(flags)");
        v13 = v12;
        v14 = Flags.IS_DELEGATED.get(flags);
        Intrinsics.checkNotNullExpressionValue(v14, "Flags.IS_DELEGATED.get(flags)");
        v15 = v14;
        v16 = Flags.IS_EXPECT_PROPERTY.get(flags);
        Intrinsics.checkNotNullExpressionValue(v16, "Flags.IS_EXPECT_PROPERTY.get(flags)");
        property = new DeserializedPropertyDescriptor(v0, null, v1, v2, v3, v5, v6, v7, v9, v11, v13, v15, v16, proto, this.c.getNameResolver(), this.c.getTypeTable(), this.c.getVersionRequirementTable(), this.c.getContainerSource());
        v17 = property;
        v18 = proto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(v18, "proto.typeParameterList");
        local = DeserializationContext.childContext$default(this.c, v17, v18, null, null, null, null, 60, null);
        v19 = Flags.HAS_GETTER.get(flags);
        Intrinsics.checkNotNullExpressionValue(v19, "Flags.HAS_GETTER.get(flags)");
        hasGetter = v19;
        receiverAnnotations = hasGetter != false && ProtoTypeTableUtilKt.hasReceiver(proto) != false ? this.getReceiverParameterAnnotations(proto, AnnotatedCallableKind.PROPERTY_GETTER) : Annotations.Companion.getEMPTY();
        v20 = property;
        v21 = local.getTypeDeserializer().type(ProtoTypeTableUtilKt.returnType(proto, this.c.getTypeTable()));
        v22 = local.getTypeDeserializer().getOwnTypeParameters();
        v23 = this.getDispatchReceiverParameter();
        v24 = ProtoTypeTableUtilKt.receiverType(proto, this.c.getTypeTable());
        if (v24 == null) ** GOTO lbl-1000
        var7_7 = v24;
        var8_9 = local.getTypeDeserializer();
        var9_12 = false;
        var10_14 = false;
        var11_18 = var7_7;
        var21_22 = v23;
        var20_23 = v22;
        var19_24 = v21;
        var18_25 = v20;
        $i$a$-unknown-MemberDeserializer$loadProperty$1 = false;
        var22_27 /* !! */  = var8_9.type((ProtoBuf.Type)p1);
        v20 = var18_25;
        v21 = var19_24;
        v22 = var20_23;
        v23 = var21_22;
        v24 = var22_27 /* !! */ ;
        if (v24 != null) {
            var7_7 = v24;
            var8_10 = false;
            var9_12 = false;
            var10_15 = var7_7;
            var21_22 = v23;
            var20_23 = v22;
            var19_24 = v21;
            var18_25 = v20;
            $i$a$-let-MemberDeserializer$loadProperty$2 = false;
            var22_27 /* !! */  = DescriptorFactory.createExtensionReceiverParameterForCallable(property, (KotlinType)receiverType, receiverAnnotations);
            v20 = var18_25;
            v21 = var19_24;
            v22 = var20_23;
            v23 = var21_22;
            v25 /* !! */  = var22_27 /* !! */ ;
        } else lbl-1000:
        // 2 sources

        {
            v25 /* !! */  = null;
        }
        v20.setType(v21, (List<? extends TypeParameterDescriptor>)v22, v23, (ReceiverParameterDescriptor)v25 /* !! */ );
        v26 = Flags.HAS_ANNOTATIONS.get(flags);
        Intrinsics.checkNotNullExpressionValue(v26, "Flags.HAS_ANNOTATIONS.get(flags)");
        defaultAccessorFlags = Flags.getAccessorFlags(v26, Flags.VISIBILITY.get(flags), Flags.MODALITY.get(flags), false, false, false);
        if (hasGetter) {
            getterFlags = proto.hasGetterFlags() != false ? proto.getGetterFlags() : defaultAccessorFlags;
            v27 = Flags.IS_NOT_DEFAULT.get(getterFlags);
            Intrinsics.checkNotNullExpressionValue(v27, "Flags.IS_NOT_DEFAULT.get(getterFlags)");
            isNotDefault = v27;
            v28 = Flags.IS_EXTERNAL_ACCESSOR.get(getterFlags);
            Intrinsics.checkNotNullExpressionValue(v28, "Flags.IS_EXTERNAL_ACCESSOR.get(getterFlags)");
            isExternal = v28;
            v29 = Flags.IS_INLINE_ACCESSOR.get(getterFlags);
            Intrinsics.checkNotNullExpressionValue(v29, "Flags.IS_INLINE_ACCESSOR.get(getterFlags)");
            isInline = v29;
            annotations = this.getAnnotations(proto, getterFlags, AnnotatedCallableKind.PROPERTY_GETTER);
            if (isNotDefault) {
                v30 = new PropertyGetterDescriptorImpl(property, annotations, ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(getterFlags)), ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(getterFlags)), isNotDefault == false, isExternal, isInline, property.getKind(), null, SourceElement.NO_SOURCE);
            } else {
                v31 = DescriptorFactory.createDefaultGetter(property, annotations);
                v30 = v31;
                Intrinsics.checkNotNullExpressionValue(v31, "DescriptorFactory.create\u2026er(property, annotations)");
            }
            getter = v30;
            getter.initialize(property.getReturnType());
            v32 = getter;
        } else {
            v32 = null;
        }
        getter = v32;
        v33 = Flags.HAS_SETTER.get(flags);
        Intrinsics.checkNotNullExpressionValue(v33, "Flags.HAS_SETTER.get(flags)");
        if (v33.booleanValue()) {
            setterFlags = proto.hasSetterFlags() != false ? proto.getSetterFlags() : defaultAccessorFlags;
            v34 = Flags.IS_NOT_DEFAULT.get(setterFlags);
            Intrinsics.checkNotNullExpressionValue(v34, "Flags.IS_NOT_DEFAULT.get(setterFlags)");
            isNotDefault = v34;
            v35 = Flags.IS_EXTERNAL_ACCESSOR.get(setterFlags);
            Intrinsics.checkNotNullExpressionValue(v35, "Flags.IS_EXTERNAL_ACCESSOR.get(setterFlags)");
            isExternal = v35;
            v36 = Flags.IS_INLINE_ACCESSOR.get(setterFlags);
            Intrinsics.checkNotNullExpressionValue(v36, "Flags.IS_INLINE_ACCESSOR.get(setterFlags)");
            isInline = v36;
            annotations = this.getAnnotations(proto, setterFlags, AnnotatedCallableKind.PROPERTY_SETTER);
            if (isNotDefault) {
                setter = new PropertySetterDescriptorImpl(property, annotations, ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(setterFlags)), ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(setterFlags)), isNotDefault == false, isExternal, isInline, property.getKind(), null, SourceElement.NO_SOURCE);
                var17_32 = false;
                setterLocal = DeserializationContext.childContext$default(local, setter, CollectionsKt.<T>emptyList(), null, null, null, null, 60, null);
                valueParameters = setterLocal.getMemberDeserializer().valueParameters(CollectionsKt.listOf(proto.getSetterValueParameter()), proto, AnnotatedCallableKind.PROPERTY_SETTER);
                setter.initialize(CollectionsKt.single(valueParameters));
                v37 = setter;
            } else {
                v38 = DescriptorFactory.createDefaultSetter(property, annotations, Annotations.Companion.getEMPTY());
                v37 = v38;
                Intrinsics.checkNotNullExpressionValue(v38, "DescriptorFactory.create\u2026ptor */\n                )");
            }
        } else {
            v37 = null;
        }
        setter = v37;
        v39 = Flags.HAS_CONSTANT.get(flags);
        Intrinsics.checkNotNullExpressionValue(v39, "Flags.HAS_CONSTANT.get(flags)");
        if (v39.booleanValue()) {
            property.setCompileTimeInitializer(this.c.getStorageManager().createNullableLazyValue((Function0)new Function0<ConstantValue<?>>(this, proto, property){
                final /* synthetic */ MemberDeserializer this$0;
                final /* synthetic */ ProtoBuf.Property $proto;
                final /* synthetic */ DeserializedPropertyDescriptor $property;

                @Nullable
                public final ConstantValue<?> invoke() {
                    ProtoContainer protoContainer = MemberDeserializer.access$asProtoContainer(this.this$0, MemberDeserializer.access$getC$p(this.this$0).getContainingDeclaration());
                    Intrinsics.checkNotNull(protoContainer);
                    ProtoContainer container = protoContainer;
                    AnnotationAndConstantLoader<AnnotationDescriptor, ConstantValue<?>> annotationAndConstantLoader = MemberDeserializer.access$getC$p(this.this$0).getComponents().getAnnotationAndConstantLoader();
                    KotlinType kotlinType = this.$property.getReturnType();
                    Intrinsics.checkNotNullExpressionValue(kotlinType, "property.returnType");
                    return annotationAndConstantLoader.loadPropertyConstant(container, this.$proto, kotlinType);
                }
                {
                    this.this$0 = memberDeserializer;
                    this.$proto = property;
                    this.$property = deserializedPropertyDescriptor;
                    super(0);
                }
            }));
        }
        property.initialize(getter, setter, new FieldDescriptorImpl(this.getPropertyFieldAnnotations(proto, false), property), new FieldDescriptorImpl(this.getPropertyFieldAnnotations(proto, true), property), this.checkExperimentalCoroutine(property, local.getTypeDeserializer()));
        return property;
    }

    private final DeserializedMemberDescriptor.CoroutinesCompatibilityMode checkExperimentalCoroutine(DeserializedMemberDescriptor $this$checkExperimentalCoroutine, TypeDeserializer typeDeserializer) {
        if (!this.versionAndReleaseCoroutinesMismatch($this$checkExperimentalCoroutine)) {
            return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
        }
        this.forceUpperBoundsComputation(typeDeserializer);
        return typeDeserializer.getExperimentalSuspendFunctionTypeEncountered() ? DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE : DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
    }

    private final void forceUpperBoundsComputation(TypeDeserializer typeDeserializer) {
        Iterable $this$forEach$iv = typeDeserializer.getOwnTypeParameters();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            TypeParameterDescriptor it = (TypeParameterDescriptor)element$iv;
            boolean bl = false;
            it.getUpperBounds();
        }
    }

    private final void initializeWithCoroutinesExperimentalityStatus(DeserializedSimpleFunctionDescriptor $this$initializeWithCoroutinesExperimentalityStatus, ReceiverParameterDescriptor extensionReceiverParameter, ReceiverParameterDescriptor dispatchReceiverParameter, List<? extends TypeParameterDescriptor> typeParameters2, List<? extends ValueParameterDescriptor> unsubstitutedValueParameters, KotlinType unsubstitutedReturnType, Modality modality, Visibility visibility, Map<? extends CallableDescriptor.UserDataKey<?>, ?> userDataMap, boolean isSuspend) {
        $this$initializeWithCoroutinesExperimentalityStatus.initialize(extensionReceiverParameter, dispatchReceiverParameter, typeParameters2, unsubstitutedValueParameters, unsubstitutedReturnType, modality, visibility, userDataMap, this.computeExperimentalityModeForFunctions($this$initializeWithCoroutinesExperimentalityStatus, extensionReceiverParameter, (Collection<? extends ValueParameterDescriptor>)unsubstitutedValueParameters, (Collection<? extends TypeParameterDescriptor>)typeParameters2, unsubstitutedReturnType, isSuspend));
    }

    /*
     * WARNING - void declaration
     */
    private final DeserializedMemberDescriptor.CoroutinesCompatibilityMode computeExperimentalityModeForFunctions(DeserializedCallableMemberDescriptor $this$computeExperimentalityModeForFunctions, ReceiverParameterDescriptor extensionReceiverParameter, Collection<? extends ValueParameterDescriptor> parameters2, Collection<? extends TypeParameterDescriptor> typeParameters2, KotlinType returnType, boolean isSuspend) {
        void $this$mapTo$iv$iv;
        Object element$iv2;
        boolean bl;
        List types;
        Object object;
        Collection collection;
        block20: {
            Iterator $this$mapTo$iv$iv2;
            if (!this.versionAndReleaseCoroutinesMismatch($this$computeExperimentalityModeForFunctions)) {
                return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
            }
            if (Intrinsics.areEqual(DescriptorUtilsKt.fqNameOrNull($this$computeExperimentalityModeForFunctions), SuspendFunctionTypeUtilKt.KOTLIN_SUSPEND_BUILT_IN_FUNCTION_FQ_NAME)) {
                return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
            }
            Iterable $this$map$iv = parameters2;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            Iterator iterator2 = $this$mapTo$iv$iv2.iterator();
            while (iterator2.hasNext()) {
                void it;
                Object item$iv$iv = iterator2.next();
                ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl2 = false;
                object = it.getType();
                collection.add(object);
            }
            ReceiverParameterDescriptor receiverParameterDescriptor = extensionReceiverParameter;
            types = CollectionsKt.plus((Collection)((List)destination$iv$iv), (Iterable)CollectionsKt.listOfNotNull(receiverParameterDescriptor != null ? receiverParameterDescriptor.getType() : null));
            KotlinType kotlinType = returnType;
            if (kotlinType != null) {
                if (this.containsSuspendFunctionType(kotlinType)) {
                    return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE;
                }
            }
            Iterable $this$any$iv = typeParameters2;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv2 : $this$any$iv) {
                    boolean bl3;
                    block19: {
                        TypeParameterDescriptor typeParameter = (TypeParameterDescriptor)element$iv2;
                        boolean bl4 = false;
                        List<KotlinType> list = typeParameter.getUpperBounds();
                        Intrinsics.checkNotNullExpressionValue(list, "typeParameter.upperBounds");
                        Iterable $this$any$iv2 = list;
                        boolean $i$f$any2 = false;
                        if ($this$any$iv2 instanceof Collection && ((Collection)$this$any$iv2).isEmpty()) {
                            bl3 = false;
                        } else {
                            for (Object element$iv3 : $this$any$iv2) {
                                KotlinType it = (KotlinType)element$iv3;
                                boolean bl5 = false;
                                KotlinType kotlinType2 = it;
                                Intrinsics.checkNotNullExpressionValue(kotlinType2, "it");
                                if (!this.containsSuspendFunctionType(kotlinType2)) continue;
                                bl3 = true;
                                break block19;
                            }
                            bl3 = false;
                        }
                    }
                    if (!bl3) continue;
                    bl = true;
                    break block20;
                }
                bl = false;
            }
        }
        if (bl) {
            return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE;
        }
        Iterable $this$map$iv = types;
        boolean $i$f$map = false;
        element$iv2 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            DeserializedMemberDescriptor.CoroutinesCompatibilityMode coroutinesCompatibilityMode;
            void type2;
            KotlinType bl2 = (KotlinType)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl6 = false;
            void v6 = type2;
            Intrinsics.checkNotNullExpressionValue(v6, "type");
            if (FunctionTypesKt.isSuspendFunctionType((KotlinType)v6) && type2.getArguments().size() <= 3) {
                boolean bl7;
                block21: {
                    Iterable $this$any$iv = type2.getArguments();
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl7 = false;
                    } else {
                        for (Object element$iv4 : $this$any$iv) {
                            TypeProjection it = (TypeProjection)element$iv4;
                            boolean bl8 = false;
                            KotlinType kotlinType = it.getType();
                            Intrinsics.checkNotNullExpressionValue(kotlinType, "it.type");
                            if (!this.containsSuspendFunctionType(kotlinType)) continue;
                            bl7 = true;
                            break block21;
                        }
                        bl7 = false;
                    }
                }
                coroutinesCompatibilityMode = bl7 ? DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE : DeserializedMemberDescriptor.CoroutinesCompatibilityMode.NEEDS_WRAPPER;
            } else {
                coroutinesCompatibilityMode = this.containsSuspendFunctionType((KotlinType)type2) ? DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE : DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
            }
            object = coroutinesCompatibilityMode;
            collection.add(object);
        }
        DeserializedMemberDescriptor.CoroutinesCompatibilityMode coroutinesCompatibilityMode = (DeserializedMemberDescriptor.CoroutinesCompatibilityMode)((Object)CollectionsKt.max((List)destination$iv$iv));
        if (coroutinesCompatibilityMode == null) {
            coroutinesCompatibilityMode = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
        }
        DeserializedMemberDescriptor.CoroutinesCompatibilityMode maxFromParameters = coroutinesCompatibilityMode;
        return (DeserializedMemberDescriptor.CoroutinesCompatibilityMode)((Object)ComparisonsKt.maxOf((Comparable)((Object)(isSuspend ? DeserializedMemberDescriptor.CoroutinesCompatibilityMode.NEEDS_WRAPPER : DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE)), (Comparable)((Object)maxFromParameters)));
    }

    private final boolean containsSuspendFunctionType(KotlinType $this$containsSuspendFunctionType) {
        return TypeUtilsKt.contains($this$containsSuspendFunctionType, MemberDeserializer$containsSuspendFunctionType$1.INSTANCE);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean versionAndReleaseCoroutinesMismatch(DeserializedMemberDescriptor $this$versionAndReleaseCoroutinesMismatch) {
        boolean bl;
        if (!this.c.getComponents().getConfiguration().getReleaseCoroutines()) return false;
        Iterable $this$none$iv = $this$versionAndReleaseCoroutinesMismatch.getVersionRequirements();
        boolean $i$f$none = false;
        if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
            return true;
        }
        Iterator iterator2 = $this$none$iv.iterator();
        do {
            if (!iterator2.hasNext()) return true;
            Object element$iv = iterator2.next();
            VersionRequirement it = (VersionRequirement)element$iv;
            boolean bl2 = false;
            if (Intrinsics.areEqual(it.getVersion(), new VersionRequirement.Version(1, 3, 0, 4, null)) && it.getKind() == ProtoBuf.VersionRequirement.VersionKind.LANGUAGE_VERSION) {
                return false;
            }
            bl = false;
        } while (!bl);
        return false;
    }

    private final int loadOldFlags(int oldFlags) {
        int lowSixBits = oldFlags & 0x3F;
        int rest = oldFlags >> 8 << 6;
        return lowSixBits + rest;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @NotNull
    public final SimpleFunctionDescriptor loadFunction(@NotNull ProtoBuf.Function proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        flags = proto.hasFlags() != false ? proto.getFlags() : this.loadOldFlags(proto.getOldFlags());
        annotations = this.getAnnotations(proto, flags, AnnotatedCallableKind.FUNCTION);
        receiverAnnotations = ProtoTypeTableUtilKt.hasReceiver(proto) != false ? this.getReceiverParameterAnnotations(proto, AnnotatedCallableKind.FUNCTION) : Annotations.Companion.getEMPTY();
        versionRequirementTable = Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(this.c.getContainingDeclaration()).child(NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName())), SuspendFunctionTypeUtilKt.KOTLIN_SUSPEND_BUILT_IN_FUNCTION_FQ_NAME) != false ? VersionRequirementTable.Companion.getEMPTY() : this.c.getVersionRequirementTable();
        function = new DeserializedSimpleFunctionDescriptor(this.c.getContainingDeclaration(), null, annotations, NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName()), ProtoEnumFlags.INSTANCE.memberKind(Flags.MEMBER_KIND.get(flags)), proto, this.c.getNameResolver(), this.c.getTypeTable(), versionRequirementTable, this.c.getContainerSource(), null, 1024, null);
        v0 = function;
        v1 = proto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(v1, "proto.typeParameterList");
        local = DeserializationContext.childContext$default(this.c, v0, v1, null, null, null, null, 60, null);
        v2 = this;
        v3 = function;
        v4 = ProtoTypeTableUtilKt.receiverType(proto, this.c.getTypeTable());
        if (v4 == null) ** GOTO lbl-1000
        var8_8 = v4;
        var9_9 = local.getTypeDeserializer();
        var10_11 = false;
        var11_12 = false;
        var12_14 = var8_8;
        var15_16 = v3;
        var14_17 = v2;
        $i$a$-unknown-MemberDeserializer$loadFunction$1 = false;
        var16_19 /* !! */  = var9_9.type((ProtoBuf.Type)p1);
        v2 = var14_17;
        v3 = var15_16;
        v4 = var16_19 /* !! */ ;
        if (v4 != null) {
            var8_8 = v4;
            var9_10 = false;
            var10_11 = false;
            var11_13 = var8_8;
            var15_16 = v3;
            var14_17 = v2;
            $i$a$-let-MemberDeserializer$loadFunction$2 = false;
            var16_19 /* !! */  = DescriptorFactory.createExtensionReceiverParameterForCallable(function, (KotlinType)receiverType, receiverAnnotations);
            v2 = var14_17;
            v3 = var15_16;
            v5 /* !! */  = var16_19 /* !! */ ;
        } else lbl-1000:
        // 2 sources

        {
            v5 /* !! */  = null;
        }
        v6 = this.getDispatchReceiverParameter();
        v7 = local.getTypeDeserializer().getOwnTypeParameters();
        v8 = local.getMemberDeserializer();
        v9 = proto.getValueParameterList();
        Intrinsics.checkNotNullExpressionValue(v9, "proto.valueParameterList");
        v10 = v8.valueParameters(v9, proto, AnnotatedCallableKind.FUNCTION);
        v11 = local.getTypeDeserializer().type(ProtoTypeTableUtilKt.returnType(proto, this.c.getTypeTable()));
        v12 = ProtoEnumFlags.INSTANCE.modality(Flags.MODALITY.get(flags));
        v13 = ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(flags));
        v14 = MapsKt.emptyMap();
        v15 = Flags.IS_SUSPEND.get(flags);
        Intrinsics.checkNotNullExpressionValue(v15, "Flags.IS_SUSPEND.get(flags)");
        v2.initializeWithCoroutinesExperimentalityStatus(v3, (ReceiverParameterDescriptor)v5 /* !! */ , v6, v7, v10, v11, v12, v13, v14, v15);
        v16 = Flags.IS_OPERATOR.get(flags);
        Intrinsics.checkNotNullExpressionValue(v16, "Flags.IS_OPERATOR.get(flags)");
        function.setOperator(v16);
        v17 = Flags.IS_INFIX.get(flags);
        Intrinsics.checkNotNullExpressionValue(v17, "Flags.IS_INFIX.get(flags)");
        function.setInfix(v17);
        v18 = Flags.IS_EXTERNAL_FUNCTION.get(flags);
        Intrinsics.checkNotNullExpressionValue(v18, "Flags.IS_EXTERNAL_FUNCTION.get(flags)");
        function.setExternal(v18);
        v19 = Flags.IS_INLINE.get(flags);
        Intrinsics.checkNotNullExpressionValue(v19, "Flags.IS_INLINE.get(flags)");
        function.setInline(v19);
        v20 = Flags.IS_TAILREC.get(flags);
        Intrinsics.checkNotNullExpressionValue(v20, "Flags.IS_TAILREC.get(flags)");
        function.setTailrec(v20);
        v21 = Flags.IS_SUSPEND.get(flags);
        Intrinsics.checkNotNullExpressionValue(v21, "Flags.IS_SUSPEND.get(flags)");
        function.setSuspend(v21);
        v22 = Flags.IS_EXPECT_FUNCTION.get(flags);
        Intrinsics.checkNotNullExpressionValue(v22, "Flags.IS_EXPECT_FUNCTION.get(flags)");
        function.setExpect(v22);
        mapValueForContract = this.c.getComponents().getContractDeserializer().deserializeContractFromFunction(proto, function, this.c.getTypeTable(), local.getTypeDeserializer());
        if (mapValueForContract != null) {
            function.putInUserDataMap(mapValueForContract.getFirst(), mapValueForContract.getSecond());
        }
        return function;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final TypeAliasDescriptor loadTypeAlias(@NotNull ProtoBuf.TypeAlias proto) {
        Collection<AnnotationDescriptor> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter(proto, "proto");
        List<ProtoBuf.Annotation> list = proto.getAnnotationList();
        Intrinsics.checkNotNullExpressionValue(list, "proto.annotationList");
        Iterable iterable = list;
        Annotations.Companion companion = Annotations.Companion;
        boolean $i$f$map = false;
        void var5_6 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ProtoBuf.Annotation annotation = (ProtoBuf.Annotation)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            void v1 = it;
            Intrinsics.checkNotNullExpressionValue(v1, "it");
            AnnotationDescriptor annotationDescriptor = this.annotationDeserializer.deserializeAnnotation((ProtoBuf.Annotation)v1, this.c.getNameResolver());
            collection.add(annotationDescriptor);
        }
        collection = (List)destination$iv$iv;
        Annotations annotations2 = companion.create((List<? extends AnnotationDescriptor>)collection);
        Visibility visibility = ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(proto.getFlags()));
        DeserializedTypeAliasDescriptor typeAlias = new DeserializedTypeAliasDescriptor(this.c.getStorageManager(), this.c.getContainingDeclaration(), annotations2, NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName()), visibility, proto, this.c.getNameResolver(), this.c.getTypeTable(), this.c.getVersionRequirementTable(), this.c.getContainerSource());
        DeclarationDescriptor declarationDescriptor = typeAlias;
        List<ProtoBuf.TypeParameter> list2 = proto.getTypeParameterList();
        Intrinsics.checkNotNullExpressionValue(list2, "proto.typeParameterList");
        DeserializationContext local = DeserializationContext.childContext$default(this.c, declarationDescriptor, list2, null, null, null, null, 60, null);
        typeAlias.initialize(local.getTypeDeserializer().getOwnTypeParameters(), local.getTypeDeserializer().simpleType(ProtoTypeTableUtilKt.underlyingType(proto, this.c.getTypeTable()), false), local.getTypeDeserializer().simpleType(ProtoTypeTableUtilKt.expandedType(proto, this.c.getTypeTable()), false), this.checkExperimentalCoroutine(typeAlias, local.getTypeDeserializer()));
        return typeAlias;
    }

    private final ReceiverParameterDescriptor getDispatchReceiverParameter() {
        DeclarationDescriptor declarationDescriptor = this.c.getContainingDeclaration();
        if (!(declarationDescriptor instanceof ClassDescriptor)) {
            declarationDescriptor = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor)declarationDescriptor;
        return classDescriptor != null ? classDescriptor.getThisAsReceiverParameter() : null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @NotNull
    public final ClassConstructorDescriptor loadConstructor(@NotNull ProtoBuf.Constructor proto, boolean isPrimary) {
        DeserializedMemberDescriptor.CoroutinesCompatibilityMode coroutinesCompatibilityMode;
        boolean doesClassContainIncompatibility;
        Object object;
        Intrinsics.checkNotNullParameter(proto, "proto");
        DeclarationDescriptor declarationDescriptor = this.c.getContainingDeclaration();
        if (declarationDescriptor == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        }
        ClassDescriptor classDescriptor = (ClassDescriptor)declarationDescriptor;
        DeserializedClassConstructorDescriptor descriptor2 = new DeserializedClassConstructorDescriptor(classDescriptor, null, this.getAnnotations(proto, proto.getFlags(), AnnotatedCallableKind.FUNCTION), isPrimary, CallableMemberDescriptor.Kind.DECLARATION, proto, this.c.getNameResolver(), this.c.getTypeTable(), this.c.getVersionRequirementTable(), this.c.getContainerSource(), null, 1024, null);
        boolean bl = false;
        DeserializationContext local = DeserializationContext.childContext$default(this.c, descriptor2, CollectionsKt.emptyList(), null, null, null, null, 60, null);
        MemberDeserializer memberDeserializer = local.getMemberDeserializer();
        List<ProtoBuf.ValueParameter> list = proto.getValueParameterList();
        Intrinsics.checkNotNullExpressionValue(list, "proto.valueParameterList");
        descriptor2.initialize(memberDeserializer.valueParameters(list, proto, AnnotatedCallableKind.FUNCTION), ProtoEnumFlags.INSTANCE.visibility(Flags.VISIBILITY.get(proto.getFlags())));
        descriptor2.setReturnType(classDescriptor.getDefaultType());
        DeclarationDescriptor declarationDescriptor2 = this.c.getContainingDeclaration();
        if (!(declarationDescriptor2 instanceof DeserializedClassDescriptor)) {
            declarationDescriptor2 = null;
        }
        boolean bl2 = (object = (DeserializedClassDescriptor)declarationDescriptor2) != null && (object = ((DeserializedClassDescriptor)object).getC()) != null && (object = ((DeserializationContext)object).getTypeDeserializer()) != null && ((TypeDeserializer)object).getExperimentalSuspendFunctionTypeEncountered() && this.versionAndReleaseCoroutinesMismatch(descriptor2) ? true : (doesClassContainIncompatibility = false);
        if (doesClassContainIncompatibility) {
            coroutinesCompatibilityMode = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE;
        } else {
            DeserializedCallableMemberDescriptor deserializedCallableMemberDescriptor = descriptor2;
            List<ValueParameterDescriptor> list2 = descriptor2.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(list2, "descriptor.valueParameters");
            Collection collection = list2;
            List<TypeParameterDescriptor> list3 = descriptor2.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(list3, "descriptor.typeParameters");
            coroutinesCompatibilityMode = this.computeExperimentalityModeForFunctions(deserializedCallableMemberDescriptor, null, collection, (Collection<? extends TypeParameterDescriptor>)list3, descriptor2.getReturnType(), false);
        }
        descriptor2.setCoroutinesExperimentalCompatibilityMode$deserialization(coroutinesCompatibilityMode);
        return descriptor2;
    }

    private final Annotations getAnnotations(MessageLite proto, int flags, AnnotatedCallableKind kind) {
        if (!Flags.HAS_ANNOTATIONS.get(flags).booleanValue()) {
            return Annotations.Companion.getEMPTY();
        }
        return new NonEmptyDeserializedAnnotations(this.c.getStorageManager(), (Function0<? extends List<? extends AnnotationDescriptor>>)new Function0<List<? extends AnnotationDescriptor>>(this, proto, kind){
            final /* synthetic */ MemberDeserializer this$0;
            final /* synthetic */ MessageLite $proto;
            final /* synthetic */ AnnotatedCallableKind $kind;

            @NotNull
            public final List<AnnotationDescriptor> invoke() {
                List<T> list;
                boolean bl;
                ProtoContainer protoContainer;
                ProtoContainer protoContainer2 = MemberDeserializer.access$asProtoContainer(this.this$0, MemberDeserializer.access$getC$p(this.this$0).getContainingDeclaration());
                if (protoContainer2 != null) {
                    protoContainer = protoContainer2;
                    bl = false;
                    boolean bl2 = false;
                    ProtoContainer it = protoContainer;
                    boolean bl3 = false;
                    list = CollectionsKt.toList((Iterable)MemberDeserializer.access$getC$p(this.this$0).getComponents().getAnnotationAndConstantLoader().loadCallableAnnotations(it, this.$proto, this.$kind));
                } else {
                    list = null;
                }
                protoContainer = list;
                bl = false;
                Object object = protoContainer;
                if (object == null) {
                    object = CollectionsKt.emptyList();
                }
                return object;
            }
            {
                this.this$0 = memberDeserializer;
                this.$proto = messageLite;
                this.$kind = annotatedCallableKind;
                super(0);
            }
        });
    }

    private final Annotations getPropertyFieldAnnotations(ProtoBuf.Property proto, boolean isDelegate) {
        if (!Flags.HAS_ANNOTATIONS.get(proto.getFlags()).booleanValue()) {
            return Annotations.Companion.getEMPTY();
        }
        return new NonEmptyDeserializedAnnotations(this.c.getStorageManager(), (Function0<? extends List<? extends AnnotationDescriptor>>)new Function0<List<? extends AnnotationDescriptor>>(this, isDelegate, proto){
            final /* synthetic */ MemberDeserializer this$0;
            final /* synthetic */ boolean $isDelegate;
            final /* synthetic */ ProtoBuf.Property $proto;

            @NotNull
            public final List<AnnotationDescriptor> invoke() {
                List<T> list;
                boolean bl;
                ProtoContainer protoContainer;
                ProtoContainer protoContainer2 = MemberDeserializer.access$asProtoContainer(this.this$0, MemberDeserializer.access$getC$p(this.this$0).getContainingDeclaration());
                if (protoContainer2 != null) {
                    protoContainer = protoContainer2;
                    bl = false;
                    boolean bl2 = false;
                    ProtoContainer it = protoContainer;
                    boolean bl3 = false;
                    list = this.$isDelegate ? CollectionsKt.toList((Iterable)MemberDeserializer.access$getC$p(this.this$0).getComponents().getAnnotationAndConstantLoader().loadPropertyDelegateFieldAnnotations(it, this.$proto)) : CollectionsKt.toList((Iterable)MemberDeserializer.access$getC$p(this.this$0).getComponents().getAnnotationAndConstantLoader().loadPropertyBackingFieldAnnotations(it, this.$proto));
                } else {
                    list = null;
                }
                protoContainer = list;
                bl = false;
                Object object = protoContainer;
                if (object == null) {
                    object = CollectionsKt.emptyList();
                }
                return object;
            }
            {
                this.this$0 = memberDeserializer;
                this.$isDelegate = bl;
                this.$proto = property;
                super(0);
            }
        });
    }

    private final Annotations getReceiverParameterAnnotations(MessageLite proto, AnnotatedCallableKind kind) {
        return new DeserializedAnnotations(this.c.getStorageManager(), (Function0<? extends List<? extends AnnotationDescriptor>>)new Function0<List<? extends AnnotationDescriptor>>(this, proto, kind){
            final /* synthetic */ MemberDeserializer this$0;
            final /* synthetic */ MessageLite $proto;
            final /* synthetic */ AnnotatedCallableKind $kind;

            @NotNull
            public final List<AnnotationDescriptor> invoke() {
                List<AnnotationDescriptor> list;
                boolean bl;
                ProtoContainer protoContainer;
                ProtoContainer protoContainer2 = MemberDeserializer.access$asProtoContainer(this.this$0, MemberDeserializer.access$getC$p(this.this$0).getContainingDeclaration());
                if (protoContainer2 != null) {
                    protoContainer = protoContainer2;
                    bl = false;
                    boolean bl2 = false;
                    ProtoContainer it = protoContainer;
                    boolean bl3 = false;
                    list = MemberDeserializer.access$getC$p(this.this$0).getComponents().getAnnotationAndConstantLoader().loadExtensionReceiverParameterAnnotations(it, this.$proto, this.$kind);
                } else {
                    list = null;
                }
                protoContainer = list;
                bl = false;
                Object object = protoContainer;
                if (object == null) {
                    object = CollectionsKt.emptyList();
                }
                return object;
            }
            {
                this.this$0 = memberDeserializer;
                this.$proto = messageLite;
                this.$kind = annotatedCallableKind;
                super(0);
            }
        });
    }

    /*
     * Unable to fully structure code
     */
    private final List<ValueParameterDescriptor> valueParameters(List<ProtoBuf.ValueParameter> valueParameters, MessageLite callable, AnnotatedCallableKind kind) {
        v0 = this.c.getContainingDeclaration();
        if (v0 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableDescriptor");
        }
        callableDescriptor = (CallableDescriptor)v0;
        v1 = callableDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(v1, "callableDescriptor.containingDeclaration");
        containerOfCallable = this.asProtoContainer(v1);
        $this$mapIndexed$iv = valueParameters;
        $i$f$mapIndexed = false;
        var8_8 = $this$mapIndexed$iv;
        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
        $i$f$mapIndexedTo = false;
        index$iv$iv = 0;
        for (T item$iv$iv : $this$mapIndexedTo$iv$iv) {
            var14_14 = index$iv$iv++;
            var15_15 = false;
            if (var14_14 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            var16_16 = (ProtoBuf.ValueParameter)item$iv$iv;
            var17_17 = var14_14;
            var38_36 = destination$iv$iv;
            $i$a$-mapIndexed-MemberDeserializer$valueParameters$1 = false;
            v2 = flags = proto.hasFlags() != false ? proto.getFlags() : 0;
            if (containerOfCallable == null) ** GOTO lbl-1000
            v3 = Flags.HAS_ANNOTATIONS.get(flags);
            Intrinsics.checkNotNullExpressionValue(v3, "Flags.HAS_ANNOTATIONS.get(flags)");
            if (v3.booleanValue()) {
                v4 = new NonEmptyDeserializedAnnotations(this.c.getStorageManager(), (Function0<? extends List<? extends AnnotationDescriptor>>)new Function0<List<? extends AnnotationDescriptor>>((int)i, (ProtoBuf.ValueParameter)proto, this, containerOfCallable, callable, kind, callableDescriptor){
                    final /* synthetic */ int $i;
                    final /* synthetic */ ProtoBuf.ValueParameter $proto;
                    final /* synthetic */ MemberDeserializer this$0;
                    final /* synthetic */ ProtoContainer $containerOfCallable$inlined;
                    final /* synthetic */ MessageLite $callable$inlined;
                    final /* synthetic */ AnnotatedCallableKind $kind$inlined;
                    final /* synthetic */ CallableDescriptor $callableDescriptor$inlined;
                    {
                        this.$i = n;
                        this.$proto = valueParameter;
                        this.this$0 = memberDeserializer;
                        this.$containerOfCallable$inlined = protoContainer;
                        this.$callable$inlined = messageLite;
                        this.$kind$inlined = annotatedCallableKind;
                        this.$callableDescriptor$inlined = callableDescriptor;
                        super(0);
                    }

                    @NotNull
                    public final List<AnnotationDescriptor> invoke() {
                        return CollectionsKt.toList((Iterable)MemberDeserializer.access$getC$p(this.this$0).getComponents().getAnnotationAndConstantLoader().loadValueParameterAnnotations(this.$containerOfCallable$inlined, this.$callable$inlined, this.$kind$inlined, this.$i, this.$proto));
                    }
                });
            } else lbl-1000:
            // 2 sources

            {
                v4 = Annotations.Companion.getEMPTY();
            }
            annotations = v4;
            v5 = callableDescriptor;
            v6 = null;
            v7 = i;
            v8 = annotations;
            v9 = NameResolverUtilKt.getName(this.c.getNameResolver(), proto.getName());
            v10 = this.c.getTypeDeserializer().type(ProtoTypeTableUtilKt.type((ProtoBuf.ValueParameter)proto, this.c.getTypeTable()));
            v11 = Flags.DECLARES_DEFAULT_VALUE.get(flags);
            Intrinsics.checkNotNullExpressionValue(v11, "Flags.DECLARES_DEFAULT_VALUE.get(flags)");
            v12 = v11;
            v13 = Flags.IS_CROSSINLINE.get(flags);
            Intrinsics.checkNotNullExpressionValue(v13, "Flags.IS_CROSSINLINE.get(flags)");
            v14 = v13;
            v15 = Flags.IS_NOINLINE.get(flags);
            Intrinsics.checkNotNullExpressionValue(v15, "Flags.IS_NOINLINE.get(flags)");
            v16 = v15;
            if (ProtoTypeTableUtilKt.varargElementType((ProtoBuf.ValueParameter)proto, this.c.getTypeTable()) != null) {
                var22_22 = false;
                var23_23 = false;
                var24_24 = var21_21;
                var25_25 = v16;
                var26_26 = v14;
                var27_27 = v12;
                var28_28 = v10;
                var29_29 = v9;
                var30_30 = v8;
                var31_31 = v7;
                var32_32 = v6;
                var33_33 = v5;
                $i$a$-let-MemberDeserializer$valueParameters$1$1 = false;
                var37_35 = this.c.getTypeDeserializer().type((ProtoBuf.Type)it);
                v5 = var33_33;
                v6 = var32_32;
                v7 = var31_31;
                v8 = var30_30;
                v9 = var29_29;
                v10 = var28_28;
                v12 = var27_27;
                v14 = var26_26;
                v16 = var25_25;
                v17 = var37_35;
            } else {
                v17 = null;
            }
            Intrinsics.checkNotNullExpressionValue(SourceElement.NO_SOURCE, "SourceElement.NO_SOURCE");
            var41_39 = v17;
            var42_40 = v16;
            var43_41 = v14;
            var44_42 = v12;
            var45_43 = v10;
            var46_44 = v9;
            var47_45 = v8;
            var48_46 = v7;
            var49_47 = v6;
            var50_48 = v5;
            var39_37 = new ValueParameterDescriptorImpl(var50_48, var49_47, (int)var48_46, var47_45, var46_44, var45_43, var44_42, var43_41, var42_40, var41_39, var40_38);
            var38_36.add(var39_37);
        }
        return CollectionsKt.toList((List)destination$iv$iv);
    }

    private final ProtoContainer asProtoContainer(DeclarationDescriptor $this$asProtoContainer) {
        DeclarationDescriptor declarationDescriptor = $this$asProtoContainer;
        return declarationDescriptor instanceof PackageFragmentDescriptor ? (ProtoContainer)new ProtoContainer.Package(((PackageFragmentDescriptor)$this$asProtoContainer).getFqName(), this.c.getNameResolver(), this.c.getTypeTable(), this.c.getContainerSource()) : (declarationDescriptor instanceof DeserializedClassDescriptor ? (ProtoContainer)((DeserializedClassDescriptor)$this$asProtoContainer).getThisAsProtoContainer$deserialization() : null);
    }

    public MemberDeserializer(@NotNull DeserializationContext c) {
        Intrinsics.checkNotNullParameter(c, "c");
        this.c = c;
        this.annotationDeserializer = new AnnotationDeserializer(this.c.getComponents().getModuleDescriptor(), this.c.getComponents().getNotFoundClasses());
    }

    public static final /* synthetic */ ProtoContainer access$asProtoContainer(MemberDeserializer $this, DeclarationDescriptor $this$access_u24asProtoContainer) {
        return $this.asProtoContainer($this$access_u24asProtoContainer);
    }

    public static final /* synthetic */ DeserializationContext access$getC$p(MemberDeserializer $this) {
        return $this.c;
    }
}

