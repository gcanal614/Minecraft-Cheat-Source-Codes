/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.List;
import kotlin._Assertions;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeAliasConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.storage.NullableLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeAliasConstructorDescriptorImpl
extends FunctionDescriptorImpl
implements TypeAliasConstructorDescriptor {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    @Nullable
    private final NullableLazyValue withDispatchReceiver$delegate;
    @NotNull
    private ClassConstructorDescriptor underlyingConstructorDescriptor;
    @NotNull
    private final StorageManager storageManager;
    @NotNull
    private final TypeAliasDescriptor typeAliasDescriptor;
    public static final Companion Companion;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(TypeAliasConstructorDescriptorImpl.class), "withDispatchReceiver", "getWithDispatchReceiver()Lorg/jetbrains/kotlin/descriptors/impl/TypeAliasConstructorDescriptor;"))};
        Companion = new Companion(null);
    }

    @Override
    @NotNull
    public ClassConstructorDescriptor getUnderlyingConstructorDescriptor() {
        return this.underlyingConstructorDescriptor;
    }

    @Override
    public boolean isPrimary() {
        return this.getUnderlyingConstructorDescriptor().isPrimary();
    }

    @Override
    @NotNull
    public TypeAliasDescriptor getContainingDeclaration() {
        return this.getTypeAliasDescriptor();
    }

    @Override
    @NotNull
    public ClassDescriptor getConstructedClass() {
        ClassDescriptor classDescriptor = this.getUnderlyingConstructorDescriptor().getConstructedClass();
        Intrinsics.checkNotNullExpressionValue(classDescriptor, "underlyingConstructorDescriptor.constructedClass");
        return classDescriptor;
    }

    @Override
    @NotNull
    public KotlinType getReturnType() {
        KotlinType kotlinType = super.getReturnType();
        Intrinsics.checkNotNull(kotlinType);
        return kotlinType;
    }

    @Override
    @NotNull
    public TypeAliasConstructorDescriptor getOriginal() {
        FunctionDescriptor functionDescriptor = super.getOriginal();
        if (functionDescriptor == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor");
        }
        return (TypeAliasConstructorDescriptor)functionDescriptor;
    }

    @Override
    @Nullable
    public TypeAliasConstructorDescriptor substitute(@NotNull TypeSubstitutor substitutor) {
        ClassConstructorDescriptor substitutedUnderlyingConstructor;
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        FunctionDescriptor functionDescriptor = super.substitute(substitutor);
        if (functionDescriptor == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptorImpl");
        }
        TypeAliasConstructorDescriptorImpl substitutedTypeAliasConstructor = (TypeAliasConstructorDescriptorImpl)functionDescriptor;
        TypeSubstitutor typeSubstitutor2 = TypeSubstitutor.create(substitutedTypeAliasConstructor.getReturnType());
        Intrinsics.checkNotNullExpressionValue(typeSubstitutor2, "TypeSubstitutor.create(s\u2026asConstructor.returnType)");
        TypeSubstitutor underlyingConstructorSubstitutor = typeSubstitutor2;
        ClassConstructorDescriptor classConstructorDescriptor = this.getUnderlyingConstructorDescriptor().getOriginal().substitute(underlyingConstructorSubstitutor);
        if (classConstructorDescriptor == null) {
            return null;
        }
        substitutedTypeAliasConstructor.underlyingConstructorDescriptor = substitutedUnderlyingConstructor = classConstructorDescriptor;
        return substitutedTypeAliasConstructor;
    }

    @Override
    @NotNull
    public TypeAliasConstructorDescriptor copy(@NotNull DeclarationDescriptor newOwner, @NotNull Modality modality, @NotNull Visibility visibility, @NotNull CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter((Object)modality, "modality");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        FunctionDescriptor functionDescriptor = this.newCopyBuilder().setOwner(newOwner).setModality(modality).setVisibility(visibility).setKind(kind).setCopyOverrides(copyOverrides).build();
        if (functionDescriptor == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor");
        }
        return (TypeAliasConstructorDescriptor)functionDescriptor;
    }

    @Override
    @NotNull
    protected TypeAliasConstructorDescriptorImpl createSubstitutedCopy(@NotNull DeclarationDescriptor newOwner, @Nullable FunctionDescriptor original, @NotNull CallableMemberDescriptor.Kind kind, @Nullable Name newName, @NotNull Annotations annotations2, @NotNull SourceElement source) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter(source, "source");
        boolean bl = kind == CallableMemberDescriptor.Kind.DECLARATION || kind == CallableMemberDescriptor.Kind.SYNTHESIZED;
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean $i$a$-assert-TypeAliasConstructorDescriptorImpl$createSubstitutedCopy$32 = false;
            String $i$a$-assert-TypeAliasConstructorDescriptorImpl$createSubstitutedCopy$32 = "Creating a type alias constructor that is not a declaration: \ncopy from: " + this + "\nnewOwner: " + newOwner + "\nkind: " + (Object)((Object)kind);
            throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-TypeAliasConstructorDescriptorImpl$createSubstitutedCopy$32));
        }
        bl = newName == null;
        bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "Renaming type alias constructor: " + this;
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        return new TypeAliasConstructorDescriptorImpl(this.storageManager, this.getTypeAliasDescriptor(), this.getUnderlyingConstructorDescriptor(), this, annotations2, CallableMemberDescriptor.Kind.DECLARATION, source);
    }

    @NotNull
    public final StorageManager getStorageManager() {
        return this.storageManager;
    }

    @NotNull
    public TypeAliasDescriptor getTypeAliasDescriptor() {
        return this.typeAliasDescriptor;
    }

    private TypeAliasConstructorDescriptorImpl(StorageManager storageManager, TypeAliasDescriptor typeAliasDescriptor, ClassConstructorDescriptor underlyingConstructorDescriptor, TypeAliasConstructorDescriptor original, Annotations annotations2, CallableMemberDescriptor.Kind kind, SourceElement source) {
        super(typeAliasDescriptor, original, annotations2, Name.special("<init>"), kind, source);
        this.storageManager = storageManager;
        this.typeAliasDescriptor = typeAliasDescriptor;
        this.setActual(this.getTypeAliasDescriptor().isActual());
        this.withDispatchReceiver$delegate = this.storageManager.createNullableLazyValue((Function0)new Function0<TypeAliasConstructorDescriptorImpl>(this, underlyingConstructorDescriptor){
            final /* synthetic */ TypeAliasConstructorDescriptorImpl this$0;
            final /* synthetic */ ClassConstructorDescriptor $underlyingConstructorDescriptor;

            @Nullable
            public final TypeAliasConstructorDescriptorImpl invoke() {
                StorageManager storageManager = this.this$0.getStorageManager();
                TypeAliasDescriptor typeAliasDescriptor = this.this$0.getTypeAliasDescriptor();
                TypeAliasConstructorDescriptor typeAliasConstructorDescriptor = this.this$0;
                Annotations annotations2 = this.$underlyingConstructorDescriptor.getAnnotations();
                CallableMemberDescriptor.Kind kind = this.$underlyingConstructorDescriptor.getKind();
                Intrinsics.checkNotNullExpressionValue((Object)((Object)kind), "underlyingConstructorDescriptor.kind");
                SourceElement sourceElement = this.this$0.getTypeAliasDescriptor().getSource();
                Intrinsics.checkNotNullExpressionValue(sourceElement, "typeAliasDescriptor.source");
                TypeAliasConstructorDescriptorImpl typeAliasConstructorDescriptorImpl = new TypeAliasConstructorDescriptorImpl(storageManager, typeAliasDescriptor, this.$underlyingConstructorDescriptor, typeAliasConstructorDescriptor, annotations2, kind, sourceElement, null);
                boolean bl = false;
                boolean bl2 = false;
                TypeAliasConstructorDescriptorImpl typeAliasConstructor = typeAliasConstructorDescriptorImpl;
                boolean bl3 = false;
                TypeSubstitutor typeSubstitutor2 = kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeAliasConstructorDescriptorImpl$Companion.access$getTypeSubstitutorForUnderlyingClass(TypeAliasConstructorDescriptorImpl.Companion, this.this$0.getTypeAliasDescriptor());
                if (typeSubstitutor2 == null) {
                    return null;
                }
                TypeSubstitutor substitutorForUnderlyingClass = typeSubstitutor2;
                ReceiverParameterDescriptor receiverParameterDescriptor = this.$underlyingConstructorDescriptor.getDispatchReceiverParameter();
                typeAliasConstructor.initialize(null, receiverParameterDescriptor != null ? receiverParameterDescriptor.substitute(substitutorForUnderlyingClass) : null, this.this$0.getTypeAliasDescriptor().getDeclaredTypeParameters(), this.this$0.getValueParameters(), this.this$0.getReturnType(), Modality.FINAL, this.this$0.getTypeAliasDescriptor().getVisibility());
                return typeAliasConstructorDescriptorImpl;
            }
            {
                this.this$0 = typeAliasConstructorDescriptorImpl;
                this.$underlyingConstructorDescriptor = classConstructorDescriptor;
                super(0);
            }
        });
        this.underlyingConstructorDescriptor = underlyingConstructorDescriptor;
    }

    public /* synthetic */ TypeAliasConstructorDescriptorImpl(StorageManager storageManager, TypeAliasDescriptor typeAliasDescriptor, ClassConstructorDescriptor underlyingConstructorDescriptor, TypeAliasConstructorDescriptor original, Annotations annotations2, CallableMemberDescriptor.Kind kind, SourceElement source, DefaultConstructorMarker $constructor_marker) {
        this(storageManager, typeAliasDescriptor, underlyingConstructorDescriptor, original, annotations2, kind, source);
    }

    public static final class Companion {
        private final TypeSubstitutor getTypeSubstitutorForUnderlyingClass(TypeAliasDescriptor $this$getTypeSubstitutorForUnderlyingClass) {
            if ($this$getTypeSubstitutorForUnderlyingClass.getClassDescriptor() == null) {
                return null;
            }
            return TypeSubstitutor.create($this$getTypeSubstitutorForUnderlyingClass.getExpandedType());
        }

        @Nullable
        public final TypeAliasConstructorDescriptor createIfAvailable(@NotNull StorageManager storageManager, @NotNull TypeAliasDescriptor typeAliasDescriptor, @NotNull ClassConstructorDescriptor constructor) {
            ReceiverParameterDescriptor receiverParameterDescriptor;
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            Intrinsics.checkNotNullParameter(typeAliasDescriptor, "typeAliasDescriptor");
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            TypeSubstitutor typeSubstitutor2 = this.getTypeSubstitutorForUnderlyingClass(typeAliasDescriptor);
            if (typeSubstitutor2 == null) {
                return null;
            }
            TypeSubstitutor substitutorForUnderlyingClass = typeSubstitutor2;
            ClassConstructorDescriptor classConstructorDescriptor = constructor.substitute(substitutorForUnderlyingClass);
            if (classConstructorDescriptor == null) {
                return null;
            }
            ClassConstructorDescriptor substitutedConstructor = classConstructorDescriptor;
            Annotations annotations2 = constructor.getAnnotations();
            CallableMemberDescriptor.Kind kind = constructor.getKind();
            Intrinsics.checkNotNullExpressionValue((Object)kind, "constructor.kind");
            SourceElement sourceElement = typeAliasDescriptor.getSource();
            Intrinsics.checkNotNullExpressionValue(sourceElement, "typeAliasDescriptor.source");
            TypeAliasConstructorDescriptorImpl typeAliasConstructor = new TypeAliasConstructorDescriptorImpl(storageManager, typeAliasDescriptor, substitutedConstructor, null, annotations2, kind, sourceElement, null);
            List<ValueParameterDescriptor> list = FunctionDescriptorImpl.getSubstitutedValueParameters(typeAliasConstructor, constructor.getValueParameters(), substitutorForUnderlyingClass);
            if (list == null) {
                return null;
            }
            Intrinsics.checkNotNullExpressionValue(list, "FunctionDescriptorImpl.g\u2026         ) ?: return null");
            List<ValueParameterDescriptor> valueParameters = list;
            SimpleType simpleType2 = FlexibleTypesKt.lowerIfFlexible(substitutedConstructor.getReturnType().unwrap());
            SimpleType simpleType3 = typeAliasDescriptor.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(simpleType3, "typeAliasDescriptor.defaultType");
            SimpleType returnType = SpecialTypesKt.withAbbreviation(simpleType2, simpleType3);
            ReceiverParameterDescriptor receiverParameterDescriptor2 = constructor.getDispatchReceiverParameter();
            if (receiverParameterDescriptor2 != null) {
                ReceiverParameterDescriptor receiverParameterDescriptor3 = receiverParameterDescriptor2;
                boolean bl = false;
                boolean bl2 = false;
                ReceiverParameterDescriptor it = receiverParameterDescriptor3;
                boolean bl3 = false;
                CallableDescriptor callableDescriptor = typeAliasConstructor;
                ReceiverParameterDescriptor receiverParameterDescriptor4 = it;
                Intrinsics.checkNotNullExpressionValue(receiverParameterDescriptor4, "it");
                receiverParameterDescriptor = DescriptorFactory.createExtensionReceiverParameterForCallable(callableDescriptor, substitutorForUnderlyingClass.safeSubstitute(receiverParameterDescriptor4.getType(), Variance.INVARIANT), Annotations.Companion.getEMPTY());
            } else {
                receiverParameterDescriptor = null;
            }
            ReceiverParameterDescriptor receiverParameter = receiverParameterDescriptor;
            typeAliasConstructor.initialize(receiverParameter, null, typeAliasDescriptor.getDeclaredTypeParameters(), valueParameters, returnType, Modality.FINAL, typeAliasDescriptor.getVisibility());
            return typeAliasConstructor;
        }

        private Companion() {
        }

        public static final /* synthetic */ TypeSubstitutor access$getTypeSubstitutorForUnderlyingClass(Companion $this, TypeAliasDescriptor $this$access_u24getTypeSubstitutorForUnderlyingClass) {
            return $this.getTypeSubstitutorForUnderlyingClass($this$access_u24getTypeSubstitutorForUnderlyingClass);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

