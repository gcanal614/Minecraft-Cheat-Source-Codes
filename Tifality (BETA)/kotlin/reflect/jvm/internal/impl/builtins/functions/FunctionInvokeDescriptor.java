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
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.util.OperatorNameConventions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FunctionInvokeDescriptor
extends SimpleFunctionDescriptorImpl {
    public static final Factory Factory = new Factory(null);

    /*
     * WARNING - void declaration
     */
    @Override
    @Nullable
    protected FunctionDescriptor doSubstitute(@NotNull FunctionDescriptorImpl.CopyConfiguration configuration) {
        void $this$mapTo$iv$iv;
        Object element$iv2;
        boolean bl;
        FunctionInvokeDescriptor substituted;
        block6: {
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            FunctionInvokeDescriptor functionInvokeDescriptor = (FunctionInvokeDescriptor)super.doSubstitute(configuration);
            if (functionInvokeDescriptor == null) {
                return null;
            }
            substituted = functionInvokeDescriptor;
            List<ValueParameterDescriptor> list = substituted.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(list, "substituted.valueParameters");
            Iterable $this$none$iv = list;
            boolean $i$f$none = false;
            if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv2 : $this$none$iv) {
                    ValueParameterDescriptor it = (ValueParameterDescriptor)element$iv2;
                    boolean bl2 = false;
                    ValueParameterDescriptor valueParameterDescriptor = it;
                    Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "it");
                    KotlinType kotlinType = valueParameterDescriptor.getType();
                    Intrinsics.checkNotNullExpressionValue(kotlinType, "it.type");
                    if (!(FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(kotlinType) != null)) continue;
                    bl = false;
                    break block6;
                }
                bl = true;
            }
        }
        if (bl) {
            return substituted;
        }
        List<ValueParameterDescriptor> list = substituted.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(list, "substituted.valueParameters");
        Iterable $this$map$iv = list;
        boolean $i$f$map = false;
        element$iv2 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl3 = false;
            void v6 = it;
            Intrinsics.checkNotNullExpressionValue(v6, "it");
            KotlinType kotlinType = v6.getType();
            Intrinsics.checkNotNullExpressionValue(kotlinType, "it.type");
            Name name = FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(kotlinType);
            collection.add(name);
        }
        List parameterNames = (List)destination$iv$iv;
        return substituted.replaceParameterNames(parameterNames);
    }

    @Override
    @NotNull
    protected FunctionDescriptorImpl createSubstitutedCopy(@NotNull DeclarationDescriptor newOwner, @Nullable FunctionDescriptor original, @NotNull CallableMemberDescriptor.Kind kind, @Nullable Name newName, @NotNull Annotations annotations2, @NotNull SourceElement source) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter(source, "source");
        return new FunctionInvokeDescriptor(newOwner, (FunctionInvokeDescriptor)original, kind, this.isSuspend());
    }

    @Override
    public boolean isExternal() {
        return false;
    }

    @Override
    public boolean isInline() {
        return false;
    }

    @Override
    public boolean isTailrec() {
        return false;
    }

    /*
     * WARNING - void declaration
     */
    private final FunctionDescriptor replaceParameterNames(List<Name> parameterNames) {
        boolean bl;
        List newValueParameters;
        Object object;
        block6: {
            void $this$any$iv;
            void $this$mapTo$iv$iv;
            int indexShift = this.getValueParameters().size() - parameterNames.size();
            boolean bl2 = indexShift == 0 || indexShift == 1;
            boolean bl3 = false;
            boolean bl4 = false;
            if (_Assertions.ENABLED && !bl2) {
                boolean bl5 = false;
                String string = "Assertion failed";
                throw (Throwable)((Object)new AssertionError((Object)string));
            }
            List<ValueParameterDescriptor> list = this.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(list, "valueParameters");
            Iterable $this$map$iv = list;
            boolean $i$f$map22 = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                Name newName;
                Name parameterName;
                void it;
                ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor)item$iv$iv;
                object = destination$iv$iv;
                boolean bl6 = false;
                void v1 = it;
                Intrinsics.checkNotNullExpressionValue(v1, "it");
                Intrinsics.checkNotNullExpressionValue(v1.getName(), "it.name");
                int parameterIndex = it.getIndex();
                int nameIndex = parameterIndex - indexShift;
                if (nameIndex >= 0 && (parameterName = parameterNames.get(nameIndex)) != null) {
                    newName = parameterName;
                }
                ValueParameterDescriptor valueParameterDescriptor2 = it.copy(this, newName, parameterIndex);
                object.add(valueParameterDescriptor2);
            }
            newValueParameters = (List)destination$iv$iv;
            Iterable $i$f$map22 = parameterNames;
            object = this.newCopyBuilder(TypeSubstitutor.EMPTY);
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    Name it = (Name)element$iv;
                    boolean bl7 = false;
                    if (!(it == null)) continue;
                    bl = true;
                    break block6;
                }
                bl = false;
            }
        }
        boolean bl8 = bl;
        FunctionDescriptorImpl.CopyConfiguration copyConfiguration = ((FunctionDescriptorImpl.CopyConfiguration)((FunctionDescriptorImpl.CopyConfiguration)object).setHasSynthesizedParameterNames(bl8).setValueParameters(newValueParameters)).setOriginal(this.getOriginal());
        Intrinsics.checkNotNullExpressionValue(copyConfiguration, "newCopyBuilder(TypeSubst\u2026   .setOriginal(original)");
        FunctionDescriptorImpl.CopyConfiguration copyConfiguration2 = copyConfiguration;
        FunctionDescriptor functionDescriptor = super.doSubstitute(copyConfiguration2);
        Intrinsics.checkNotNull(functionDescriptor);
        return functionDescriptor;
    }

    private FunctionInvokeDescriptor(DeclarationDescriptor container, FunctionInvokeDescriptor original, CallableMemberDescriptor.Kind callableKind, boolean isSuspend) {
        super(container, original, Annotations.Companion.getEMPTY(), OperatorNameConventions.INVOKE, callableKind, SourceElement.NO_SOURCE);
        this.setOperator(true);
        this.setSuspend(isSuspend);
        this.setHasStableParameterNames(false);
    }

    public /* synthetic */ FunctionInvokeDescriptor(DeclarationDescriptor container, FunctionInvokeDescriptor original, CallableMemberDescriptor.Kind callableKind, boolean isSuspend, DefaultConstructorMarker $constructor_marker) {
        this(container, original, callableKind, isSuspend);
    }

    public static final class Factory {
        /*
         * WARNING - void declaration
         */
        @NotNull
        public final FunctionInvokeDescriptor create(@NotNull FunctionClassDescriptor functionClass, boolean isSuspend) {
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            Iterable $this$takeWhile$iv;
            Intrinsics.checkNotNullParameter(functionClass, "functionClass");
            List<TypeParameterDescriptor> typeParameters2 = functionClass.getDeclaredTypeParameters();
            FunctionInvokeDescriptor result2 = new FunctionInvokeDescriptor(functionClass, null, CallableMemberDescriptor.Kind.DECLARATION, isSuspend, null);
            boolean bl = false;
            Iterable iterable = typeParameters2;
            List list = CollectionsKt.emptyList();
            ReceiverParameterDescriptor receiverParameterDescriptor = functionClass.getThisAsReceiverParameter();
            ReceiverParameterDescriptor receiverParameterDescriptor2 = null;
            FunctionInvokeDescriptor functionInvokeDescriptor = result2;
            boolean $i$f$takeWhile = false;
            ArrayList list$iv = new ArrayList();
            for (Object t : $this$takeWhile$iv) {
                TypeParameterDescriptor it = (TypeParameterDescriptor)t;
                boolean bl2 = false;
                if (!(it.getVariance() == Variance.IN_VARIANCE)) break;
                list$iv.add(t);
            }
            Collection<ValueParameterDescriptor> collection = list$iv;
            $this$takeWhile$iv = CollectionsKt.withIndex((Iterable)collection);
            boolean $i$f$map = false;
            list$iv = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean bl2 = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                IndexedValue indexedValue = (IndexedValue)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl3 = false;
                ValueParameterDescriptor valueParameterDescriptor = Factory.createValueParameter(result2, it.getIndex(), (TypeParameterDescriptor)it.getValue());
                collection.add(valueParameterDescriptor);
            }
            collection = (List)destination$iv$iv;
            functionInvokeDescriptor.initialize(receiverParameterDescriptor2, receiverParameterDescriptor, list, (List)collection, (KotlinType)CollectionsKt.last(typeParameters2).getDefaultType(), Modality.ABSTRACT, Visibilities.PUBLIC);
            result2.setHasSynthesizedParameterNames(true);
            return result2;
        }

        private final ValueParameterDescriptor createValueParameter(FunctionInvokeDescriptor containingDeclaration, int index, TypeParameterDescriptor typeParameter) {
            String string;
            String typeParameterName;
            String string2 = typeParameter.getName().asString();
            Intrinsics.checkNotNullExpressionValue(string2, "typeParameter.name.asString()");
            switch (typeParameterName = string2) {
                case "T": {
                    string = "instance";
                    break;
                }
                case "E": {
                    string = "receiver";
                    break;
                }
                default: {
                    String string3 = typeParameterName;
                    boolean bl = false;
                    String string4 = string3;
                    if (string4 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string5 = string4.toLowerCase();
                    string = string5;
                    Intrinsics.checkNotNullExpressionValue(string5, "(this as java.lang.String).toLowerCase()");
                }
            }
            String name = string;
            CallableDescriptor callableDescriptor = containingDeclaration;
            Annotations annotations2 = Annotations.Companion.getEMPTY();
            Name name2 = Name.identifier(name);
            Intrinsics.checkNotNullExpressionValue(name2, "Name.identifier(name)");
            SimpleType simpleType2 = typeParameter.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(simpleType2, "typeParameter.defaultType");
            KotlinType kotlinType = simpleType2;
            SourceElement sourceElement = SourceElement.NO_SOURCE;
            Intrinsics.checkNotNullExpressionValue(sourceElement, "SourceElement.NO_SOURCE");
            return new ValueParameterDescriptorImpl(callableDescriptor, null, index, annotations2, name2, kotlinType, false, false, false, null, sourceElement);
        }

        private Factory() {
        }

        public /* synthetic */ Factory(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

