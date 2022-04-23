/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorNonRootImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ReceiverParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ExtensionReceiver;
import kotlin.reflect.jvm.internal.impl.types.DescriptorSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class FunctionDescriptorImpl
extends DeclarationDescriptorNonRootImpl
implements FunctionDescriptor {
    private List<TypeParameterDescriptor> typeParameters;
    private List<ValueParameterDescriptor> unsubstitutedValueParameters;
    private KotlinType unsubstitutedReturnType;
    private ReceiverParameterDescriptor extensionReceiverParameter;
    private ReceiverParameterDescriptor dispatchReceiverParameter;
    private Modality modality;
    private Visibility visibility;
    private boolean isOperator;
    private boolean isInfix;
    private boolean isExternal;
    private boolean isInline;
    private boolean isTailrec;
    private boolean isExpect;
    private boolean isActual;
    private boolean isHiddenToOvercomeSignatureClash;
    private boolean isHiddenForResolutionEverywhereBesideSupercalls;
    private boolean isSuspend;
    private boolean hasStableParameterNames;
    private boolean hasSynthesizedParameterNames;
    private Collection<? extends FunctionDescriptor> overriddenFunctions;
    private volatile Function0<Collection<FunctionDescriptor>> lazyOverriddenFunctionsTask;
    private final FunctionDescriptor original;
    private final CallableMemberDescriptor.Kind kind;
    @Nullable
    private FunctionDescriptor initialSignatureDescriptor;
    protected Map<CallableDescriptor.UserDataKey<?>, Object> userDataMap;

    protected FunctionDescriptorImpl(@NotNull DeclarationDescriptor containingDeclaration, @Nullable FunctionDescriptor original, @NotNull Annotations annotations2, @NotNull Name name, @NotNull CallableMemberDescriptor.Kind kind, @NotNull SourceElement source) {
        if (containingDeclaration == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(0);
        }
        if (annotations2 == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(1);
        }
        if (name == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(2);
        }
        if (kind == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(3);
        }
        if (source == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(4);
        }
        super(containingDeclaration, annotations2, name, source);
        this.visibility = Visibilities.UNKNOWN;
        this.isOperator = false;
        this.isInfix = false;
        this.isExternal = false;
        this.isInline = false;
        this.isTailrec = false;
        this.isExpect = false;
        this.isActual = false;
        this.isHiddenToOvercomeSignatureClash = false;
        this.isHiddenForResolutionEverywhereBesideSupercalls = false;
        this.isSuspend = false;
        this.hasStableParameterNames = true;
        this.hasSynthesizedParameterNames = false;
        this.overriddenFunctions = null;
        this.lazyOverriddenFunctionsTask = null;
        this.initialSignatureDescriptor = null;
        this.userDataMap = null;
        this.original = original == null ? this : original;
        this.kind = kind;
    }

    @NotNull
    public FunctionDescriptorImpl initialize(@Nullable ReceiverParameterDescriptor extensionReceiverParameter, @Nullable ReceiverParameterDescriptor dispatchReceiverParameter, @NotNull List<? extends TypeParameterDescriptor> typeParameters2, @NotNull List<ValueParameterDescriptor> unsubstitutedValueParameters, @Nullable KotlinType unsubstitutedReturnType, @Nullable Modality modality, @NotNull Visibility visibility) {
        int i;
        if (typeParameters2 == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(5);
        }
        if (unsubstitutedValueParameters == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(6);
        }
        if (visibility == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(7);
        }
        this.typeParameters = CollectionsKt.toList(typeParameters2);
        this.unsubstitutedValueParameters = CollectionsKt.toList(unsubstitutedValueParameters);
        this.unsubstitutedReturnType = unsubstitutedReturnType;
        this.modality = modality;
        this.visibility = visibility;
        this.extensionReceiverParameter = extensionReceiverParameter;
        this.dispatchReceiverParameter = dispatchReceiverParameter;
        for (i = 0; i < typeParameters2.size(); ++i) {
            TypeParameterDescriptor typeParameterDescriptor = typeParameters2.get(i);
            if (typeParameterDescriptor.getIndex() == i) continue;
            throw new IllegalStateException(typeParameterDescriptor + " index is " + typeParameterDescriptor.getIndex() + " but position is " + i);
        }
        for (i = 0; i < unsubstitutedValueParameters.size(); ++i) {
            int firstValueParameterOffset = 0;
            ValueParameterDescriptor valueParameterDescriptor = unsubstitutedValueParameters.get(i);
            if (valueParameterDescriptor.getIndex() == i + firstValueParameterOffset) continue;
            throw new IllegalStateException(valueParameterDescriptor + "index is " + valueParameterDescriptor.getIndex() + " but position is " + i);
        }
        FunctionDescriptorImpl functionDescriptorImpl = this;
        if (functionDescriptorImpl == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(8);
        }
        return functionDescriptorImpl;
    }

    public void setVisibility(@NotNull Visibility visibility) {
        if (visibility == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(9);
        }
        this.visibility = visibility;
    }

    public void setOperator(boolean isOperator) {
        this.isOperator = isOperator;
    }

    public void setInfix(boolean isInfix) {
        this.isInfix = isInfix;
    }

    public void setExternal(boolean isExternal) {
        this.isExternal = isExternal;
    }

    public void setInline(boolean isInline) {
        this.isInline = isInline;
    }

    public void setTailrec(boolean isTailrec) {
        this.isTailrec = isTailrec;
    }

    public void setExpect(boolean isExpect) {
        this.isExpect = isExpect;
    }

    public void setActual(boolean isActual) {
        this.isActual = isActual;
    }

    private void setHiddenToOvercomeSignatureClash(boolean hiddenToOvercomeSignatureClash) {
        this.isHiddenToOvercomeSignatureClash = hiddenToOvercomeSignatureClash;
    }

    private void setHiddenForResolutionEverywhereBesideSupercalls(boolean hiddenForResolutionEverywhereBesideSupercalls) {
        this.isHiddenForResolutionEverywhereBesideSupercalls = hiddenForResolutionEverywhereBesideSupercalls;
    }

    public void setSuspend(boolean suspend) {
        this.isSuspend = suspend;
    }

    public void setReturnType(@NotNull KotlinType unsubstitutedReturnType) {
        if (unsubstitutedReturnType == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(10);
        }
        if (this.unsubstitutedReturnType != null) {
            // empty if block
        }
        this.unsubstitutedReturnType = unsubstitutedReturnType;
    }

    public void setHasStableParameterNames(boolean hasStableParameterNames) {
        this.hasStableParameterNames = hasStableParameterNames;
    }

    public void setHasSynthesizedParameterNames(boolean hasSynthesizedParameterNames) {
        this.hasSynthesizedParameterNames = hasSynthesizedParameterNames;
    }

    @Override
    @Nullable
    public ReceiverParameterDescriptor getExtensionReceiverParameter() {
        return this.extensionReceiverParameter;
    }

    @Override
    @Nullable
    public ReceiverParameterDescriptor getDispatchReceiverParameter() {
        return this.dispatchReceiverParameter;
    }

    @Override
    @NotNull
    public Collection<? extends FunctionDescriptor> getOverriddenDescriptors() {
        this.performOverriddenLazyCalculationIfNeeded();
        Collection<Object> collection = this.overriddenFunctions != null ? this.overriddenFunctions : Collections.emptyList();
        if (collection == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(12);
        }
        return collection;
    }

    private void performOverriddenLazyCalculationIfNeeded() {
        Function0<Collection<FunctionDescriptor>> overriddenTask = this.lazyOverriddenFunctionsTask;
        if (overriddenTask != null) {
            this.overriddenFunctions = overriddenTask.invoke();
            this.lazyOverriddenFunctionsTask = null;
        }
    }

    @Override
    @NotNull
    public Modality getModality() {
        Modality modality = this.modality;
        if (modality == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(13);
        }
        return modality;
    }

    @Override
    @NotNull
    public Visibility getVisibility() {
        Visibility visibility = this.visibility;
        if (visibility == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(14);
        }
        return visibility;
    }

    @Override
    public boolean isOperator() {
        if (this.isOperator) {
            return true;
        }
        for (FunctionDescriptor functionDescriptor : this.getOriginal().getOverriddenDescriptors()) {
            if (!functionDescriptor.isOperator()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isInfix() {
        if (this.isInfix) {
            return true;
        }
        for (FunctionDescriptor functionDescriptor : this.getOriginal().getOverriddenDescriptors()) {
            if (!functionDescriptor.isInfix()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isExternal() {
        return this.isExternal;
    }

    @Override
    public boolean isInline() {
        return this.isInline;
    }

    @Override
    public boolean isTailrec() {
        return this.isTailrec;
    }

    @Override
    public boolean isSuspend() {
        return this.isSuspend;
    }

    @Override
    public boolean isExpect() {
        return this.isExpect;
    }

    @Override
    public boolean isActual() {
        return this.isActual;
    }

    @Override
    public <V> V getUserData(CallableDescriptor.UserDataKey<V> key) {
        if (this.userDataMap == null) {
            return null;
        }
        return (V)this.userDataMap.get(key);
    }

    @Override
    public boolean isHiddenToOvercomeSignatureClash() {
        return this.isHiddenToOvercomeSignatureClash;
    }

    @Override
    public void setOverriddenDescriptors(@NotNull Collection<? extends CallableMemberDescriptor> overriddenDescriptors) {
        if (overriddenDescriptors == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(15);
        }
        this.overriddenFunctions = overriddenDescriptors;
        for (FunctionDescriptor functionDescriptor : this.overriddenFunctions) {
            if (!functionDescriptor.isHiddenForResolutionEverywhereBesideSupercalls()) continue;
            this.isHiddenForResolutionEverywhereBesideSupercalls = true;
            break;
        }
    }

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getTypeParameters() {
        List<TypeParameterDescriptor> parameters2 = this.typeParameters;
        if (parameters2 == null) {
            throw new IllegalStateException("typeParameters == null for " + this);
        }
        List<TypeParameterDescriptor> list = parameters2;
        if (list == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(16);
        }
        return list;
    }

    @Override
    @NotNull
    public List<ValueParameterDescriptor> getValueParameters() {
        List<ValueParameterDescriptor> list = this.unsubstitutedValueParameters;
        if (list == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(17);
        }
        return list;
    }

    public boolean hasStableParameterNames() {
        return this.hasStableParameterNames;
    }

    @Override
    public boolean hasSynthesizedParameterNames() {
        return this.hasSynthesizedParameterNames;
    }

    @Override
    public KotlinType getReturnType() {
        return this.unsubstitutedReturnType;
    }

    @Override
    @NotNull
    public FunctionDescriptor getOriginal() {
        FunctionDescriptor functionDescriptor = this.original == this ? this : this.original.getOriginal();
        if (functionDescriptor == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(18);
        }
        return functionDescriptor;
    }

    @Override
    @NotNull
    public CallableMemberDescriptor.Kind getKind() {
        CallableMemberDescriptor.Kind kind = this.kind;
        if (kind == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(19);
        }
        return kind;
    }

    @Override
    public FunctionDescriptor substitute(@NotNull TypeSubstitutor originalSubstitutor) {
        if (originalSubstitutor == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(20);
        }
        if (originalSubstitutor.isEmpty()) {
            return this;
        }
        return this.newCopyBuilder(originalSubstitutor).setOriginal(this.getOriginal()).setPreserveSourceElement().setJustForTypeSubstitution(true).build();
    }

    @Override
    public boolean isHiddenForResolutionEverywhereBesideSupercalls() {
        return this.isHiddenForResolutionEverywhereBesideSupercalls;
    }

    @Override
    @NotNull
    public FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> newCopyBuilder() {
        CopyConfiguration copyConfiguration = this.newCopyBuilder(TypeSubstitutor.EMPTY);
        if (copyConfiguration == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(21);
        }
        return copyConfiguration;
    }

    @NotNull
    protected CopyConfiguration newCopyBuilder(@NotNull TypeSubstitutor substitutor) {
        if (substitutor == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(22);
        }
        return new CopyConfiguration(substitutor.getSubstitution(), this.getContainingDeclaration(), this.getModality(), this.getVisibility(), this.getKind(), this.getValueParameters(), this.getExtensionReceiverParameter(), this.getReturnType(), null);
    }

    @Nullable
    protected FunctionDescriptor doSubstitute(@NotNull CopyConfiguration configuration) {
        List<ValueParameterDescriptor> substitutedValueParameters;
        if (configuration == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(23);
        }
        boolean[] wereChanges = new boolean[1];
        Annotations resultAnnotations = configuration.additionalAnnotations != null ? AnnotationsKt.composeAnnotations(this.getAnnotations(), configuration.additionalAnnotations) : this.getAnnotations();
        FunctionDescriptorImpl substitutedDescriptor = this.createSubstitutedCopy(configuration.newOwner, configuration.original, configuration.kind, configuration.name, resultAnnotations, this.getSourceToUseForCopy(configuration.preserveSourceElement, configuration.original));
        List unsubstitutedTypeParameters = configuration.newTypeParameters == null ? this.getTypeParameters() : configuration.newTypeParameters;
        wereChanges[0] = wereChanges[0] | !unsubstitutedTypeParameters.isEmpty();
        ArrayList<TypeParameterDescriptor> substitutedTypeParameters = new ArrayList<TypeParameterDescriptor>(unsubstitutedTypeParameters.size());
        final TypeSubstitutor substitutor = DescriptorSubstitutor.substituteTypeParameters(unsubstitutedTypeParameters, configuration.substitution, substitutedDescriptor, substitutedTypeParameters, wereChanges);
        if (substitutor == null) {
            return null;
        }
        ReceiverParameterDescriptorImpl substitutedReceiverParameter = null;
        if (configuration.newExtensionReceiverParameter != null) {
            KotlinType substitutedExtensionReceiverType = substitutor.substitute(configuration.newExtensionReceiverParameter.getType(), Variance.IN_VARIANCE);
            if (substitutedExtensionReceiverType == null) {
                return null;
            }
            substitutedReceiverParameter = new ReceiverParameterDescriptorImpl(substitutedDescriptor, new ExtensionReceiver(substitutedDescriptor, substitutedExtensionReceiverType, configuration.newExtensionReceiverParameter.getValue()), configuration.newExtensionReceiverParameter.getAnnotations());
            wereChanges[0] = wereChanges[0] | substitutedExtensionReceiverType != configuration.newExtensionReceiverParameter.getType();
        }
        ReceiverParameterDescriptor substitutedExpectedThis = null;
        if (configuration.dispatchReceiverParameter != null) {
            substitutedExpectedThis = configuration.dispatchReceiverParameter.substitute(substitutor);
            if (substitutedExpectedThis == null) {
                return null;
            }
            wereChanges[0] = wereChanges[0] | substitutedExpectedThis != configuration.dispatchReceiverParameter;
        }
        if ((substitutedValueParameters = FunctionDescriptorImpl.getSubstitutedValueParameters(substitutedDescriptor, configuration.newValueParameterDescriptors, substitutor, configuration.dropOriginalInContainingParts, configuration.preserveSourceElement, wereChanges)) == null) {
            return null;
        }
        KotlinType substitutedReturnType = substitutor.substitute(configuration.newReturnType, Variance.OUT_VARIANCE);
        if (substitutedReturnType == null) {
            return null;
        }
        wereChanges[0] = wereChanges[0] | substitutedReturnType != configuration.newReturnType;
        if (!wereChanges[0] && configuration.justForTypeSubstitution) {
            return this;
        }
        substitutedDescriptor.initialize(substitutedReceiverParameter, substitutedExpectedThis, substitutedTypeParameters, substitutedValueParameters, substitutedReturnType, configuration.newModality, configuration.newVisibility);
        substitutedDescriptor.setOperator(this.isOperator);
        substitutedDescriptor.setInfix(this.isInfix);
        substitutedDescriptor.setExternal(this.isExternal);
        substitutedDescriptor.setInline(this.isInline);
        substitutedDescriptor.setTailrec(this.isTailrec);
        substitutedDescriptor.setSuspend(this.isSuspend);
        substitutedDescriptor.setExpect(this.isExpect);
        substitutedDescriptor.setActual(this.isActual);
        substitutedDescriptor.setHasStableParameterNames(this.hasStableParameterNames);
        substitutedDescriptor.setHiddenToOvercomeSignatureClash(configuration.isHiddenToOvercomeSignatureClash);
        substitutedDescriptor.setHiddenForResolutionEverywhereBesideSupercalls(configuration.isHiddenForResolutionEverywhereBesideSupercalls);
        substitutedDescriptor.setHasSynthesizedParameterNames(configuration.newHasSynthesizedParameterNames != null ? configuration.newHasSynthesizedParameterNames : this.hasSynthesizedParameterNames);
        if (!configuration.userDataMap.isEmpty() || this.userDataMap != null) {
            Map newMap = configuration.userDataMap;
            if (this.userDataMap != null) {
                for (Map.Entry<CallableDescriptor.UserDataKey<?>, Object> entry : this.userDataMap.entrySet()) {
                    if (newMap.containsKey(entry.getKey())) continue;
                    newMap.put(entry.getKey(), entry.getValue());
                }
            }
            substitutedDescriptor.userDataMap = newMap.size() == 1 ? Collections.singletonMap(newMap.keySet().iterator().next(), newMap.values().iterator().next()) : newMap;
        }
        if (configuration.signatureChange || this.getInitialSignatureDescriptor() != null) {
            FunctionDescriptor initialSignature = this.getInitialSignatureDescriptor() != null ? this.getInitialSignatureDescriptor() : this;
            FunctionDescriptor initialSignatureSubstituted = initialSignature.substitute(substitutor);
            substitutedDescriptor.setInitialSignatureDescriptor(initialSignatureSubstituted);
        }
        if (configuration.copyOverrides && !this.getOriginal().getOverriddenDescriptors().isEmpty()) {
            if (configuration.substitution.isEmpty()) {
                Function0<Collection<FunctionDescriptor>> overriddenFunctionsTask = this.lazyOverriddenFunctionsTask;
                if (overriddenFunctionsTask != null) {
                    substitutedDescriptor.lazyOverriddenFunctionsTask = overriddenFunctionsTask;
                } else {
                    substitutedDescriptor.setOverriddenDescriptors(this.getOverriddenDescriptors());
                }
            } else {
                substitutedDescriptor.lazyOverriddenFunctionsTask = new Function0<Collection<FunctionDescriptor>>(){

                    @Override
                    public Collection<FunctionDescriptor> invoke() {
                        SmartList<FunctionDescriptor> result2 = new SmartList<FunctionDescriptor>();
                        for (FunctionDescriptor functionDescriptor : FunctionDescriptorImpl.this.getOverriddenDescriptors()) {
                            result2.add(functionDescriptor.substitute(substitutor));
                        }
                        return result2;
                    }
                };
            }
        }
        return substitutedDescriptor;
    }

    @Override
    @NotNull
    public FunctionDescriptor copy(DeclarationDescriptor newOwner, Modality modality, Visibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        FunctionDescriptor functionDescriptor = this.newCopyBuilder().setOwner(newOwner).setModality(modality).setVisibility(visibility).setKind(kind).setCopyOverrides(copyOverrides).build();
        if (functionDescriptor == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(24);
        }
        return functionDescriptor;
    }

    @NotNull
    protected abstract FunctionDescriptorImpl createSubstitutedCopy(@NotNull DeclarationDescriptor var1, @Nullable FunctionDescriptor var2, @NotNull CallableMemberDescriptor.Kind var3, @Nullable Name var4, @NotNull Annotations var5, @NotNull SourceElement var6);

    @NotNull
    private SourceElement getSourceToUseForCopy(boolean preserveSource, @Nullable FunctionDescriptor original) {
        SourceElement sourceElement = preserveSource ? (original != null ? original : this.getOriginal()).getSource() : SourceElement.NO_SOURCE;
        if (sourceElement == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(25);
        }
        return sourceElement;
    }

    @Override
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
        return visitor2.visitFunctionDescriptor(this, data2);
    }

    @Nullable
    public static List<ValueParameterDescriptor> getSubstitutedValueParameters(FunctionDescriptor substitutedDescriptor, @NotNull List<ValueParameterDescriptor> unsubstitutedValueParameters, @NotNull TypeSubstitutor substitutor) {
        if (unsubstitutedValueParameters == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(26);
        }
        if (substitutor == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(27);
        }
        return FunctionDescriptorImpl.getSubstitutedValueParameters(substitutedDescriptor, unsubstitutedValueParameters, substitutor, false, false, null);
    }

    @Nullable
    public static List<ValueParameterDescriptor> getSubstitutedValueParameters(FunctionDescriptor substitutedDescriptor, @NotNull List<ValueParameterDescriptor> unsubstitutedValueParameters, @NotNull TypeSubstitutor substitutor, boolean dropOriginal, boolean preserveSourceElement, @Nullable boolean[] wereChanges) {
        if (unsubstitutedValueParameters == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(28);
        }
        if (substitutor == null) {
            FunctionDescriptorImpl.$$$reportNull$$$0(29);
        }
        ArrayList<ValueParameterDescriptor> result2 = new ArrayList<ValueParameterDescriptor>(unsubstitutedValueParameters.size());
        for (ValueParameterDescriptor unsubstitutedValueParameter : unsubstitutedValueParameters) {
            KotlinType substituteVarargElementType;
            KotlinType substitutedType = substitutor.substitute(unsubstitutedValueParameter.getType(), Variance.IN_VARIANCE);
            KotlinType varargElementType = unsubstitutedValueParameter.getVarargElementType();
            KotlinType kotlinType = substituteVarargElementType = varargElementType == null ? null : substitutor.substitute(varargElementType, Variance.IN_VARIANCE);
            if (substitutedType == null) {
                return null;
            }
            if ((substitutedType != unsubstitutedValueParameter.getType() || varargElementType != substituteVarargElementType) && wereChanges != null) {
                wereChanges[0] = true;
            }
            Function0<List<VariableDescriptor>> destructuringVariablesAction = null;
            if (unsubstitutedValueParameter instanceof ValueParameterDescriptorImpl.WithDestructuringDeclaration) {
                final List<VariableDescriptor> destructuringVariables = ((ValueParameterDescriptorImpl.WithDestructuringDeclaration)unsubstitutedValueParameter).getDestructuringVariables();
                destructuringVariablesAction = new Function0<List<VariableDescriptor>>(){

                    @Override
                    public List<VariableDescriptor> invoke() {
                        return destructuringVariables;
                    }
                };
            }
            result2.add(ValueParameterDescriptorImpl.createWithDestructuringDeclarations(substitutedDescriptor, dropOriginal ? null : unsubstitutedValueParameter, unsubstitutedValueParameter.getIndex(), unsubstitutedValueParameter.getAnnotations(), unsubstitutedValueParameter.getName(), substitutedType, unsubstitutedValueParameter.declaresDefaultValue(), unsubstitutedValueParameter.isCrossinline(), unsubstitutedValueParameter.isNoinline(), substituteVarargElementType, preserveSourceElement ? unsubstitutedValueParameter.getSource() : SourceElement.NO_SOURCE, (Function0<? extends List<? extends VariableDescriptor>>)destructuringVariablesAction));
        }
        return result2;
    }

    @Override
    @Nullable
    public FunctionDescriptor getInitialSignatureDescriptor() {
        return this.initialSignatureDescriptor;
    }

    private void setInitialSignatureDescriptor(@Nullable FunctionDescriptor initialSignatureDescriptor) {
        this.initialSignatureDescriptor = initialSignatureDescriptor;
    }

    public <V> void putInUserDataMap(CallableDescriptor.UserDataKey<V> key, Object value) {
        if (this.userDataMap == null) {
            this.userDataMap = new LinkedHashMap();
        }
        this.userDataMap.put(key, value);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        RuntimeException runtimeException;
        Object[] objectArray;
        Object[] objectArray2;
        int n2;
        String string;
        switch (n) {
            default: {
                string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            }
            case 8: 
            case 12: 
            case 13: 
            case 14: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 21: 
            case 24: 
            case 25: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 8: 
            case 12: 
            case 13: 
            case 14: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 21: 
            case 24: 
            case 25: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "containingDeclaration";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotations";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kind";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "source";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeParameters";
                break;
            }
            case 6: 
            case 26: 
            case 28: {
                objectArray2 = objectArray3;
                objectArray3[0] = "unsubstitutedValueParameters";
                break;
            }
            case 7: 
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "visibility";
                break;
            }
            case 8: 
            case 12: 
            case 13: 
            case 14: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 21: 
            case 24: 
            case 25: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/FunctionDescriptorImpl";
                break;
            }
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "unsubstitutedReturnType";
                break;
            }
            case 11: {
                objectArray2 = objectArray3;
                objectArray3[0] = "extensionReceiverParameter";
                break;
            }
            case 15: {
                objectArray2 = objectArray3;
                objectArray3[0] = "overriddenDescriptors";
                break;
            }
            case 20: {
                objectArray2 = objectArray3;
                objectArray3[0] = "originalSubstitutor";
                break;
            }
            case 22: 
            case 27: 
            case 29: {
                objectArray2 = objectArray3;
                objectArray3[0] = "substitutor";
                break;
            }
            case 23: {
                objectArray2 = objectArray3;
                objectArray3[0] = "configuration";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/FunctionDescriptorImpl";
                break;
            }
            case 8: {
                objectArray = objectArray2;
                objectArray2[1] = "initialize";
                break;
            }
            case 12: {
                objectArray = objectArray2;
                objectArray2[1] = "getOverriddenDescriptors";
                break;
            }
            case 13: {
                objectArray = objectArray2;
                objectArray2[1] = "getModality";
                break;
            }
            case 14: {
                objectArray = objectArray2;
                objectArray2[1] = "getVisibility";
                break;
            }
            case 16: {
                objectArray = objectArray2;
                objectArray2[1] = "getTypeParameters";
                break;
            }
            case 17: {
                objectArray = objectArray2;
                objectArray2[1] = "getValueParameters";
                break;
            }
            case 18: {
                objectArray = objectArray2;
                objectArray2[1] = "getOriginal";
                break;
            }
            case 19: {
                objectArray = objectArray2;
                objectArray2[1] = "getKind";
                break;
            }
            case 21: {
                objectArray = objectArray2;
                objectArray2[1] = "newCopyBuilder";
                break;
            }
            case 24: {
                objectArray = objectArray2;
                objectArray2[1] = "copy";
                break;
            }
            case 25: {
                objectArray = objectArray2;
                objectArray2[1] = "getSourceToUseForCopy";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 5: 
            case 6: 
            case 7: {
                objectArray = objectArray;
                objectArray[2] = "initialize";
                break;
            }
            case 8: 
            case 12: 
            case 13: 
            case 14: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 21: 
            case 24: 
            case 25: {
                break;
            }
            case 9: {
                objectArray = objectArray;
                objectArray[2] = "setVisibility";
                break;
            }
            case 10: {
                objectArray = objectArray;
                objectArray[2] = "setReturnType";
                break;
            }
            case 11: {
                objectArray = objectArray;
                objectArray[2] = "setExtensionReceiverParameter";
                break;
            }
            case 15: {
                objectArray = objectArray;
                objectArray[2] = "setOverriddenDescriptors";
                break;
            }
            case 20: {
                objectArray = objectArray;
                objectArray[2] = "substitute";
                break;
            }
            case 22: {
                objectArray = objectArray;
                objectArray[2] = "newCopyBuilder";
                break;
            }
            case 23: {
                objectArray = objectArray;
                objectArray[2] = "doSubstitute";
                break;
            }
            case 26: 
            case 27: 
            case 28: 
            case 29: {
                objectArray = objectArray;
                objectArray[2] = "getSubstitutedValueParameters";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 8: 
            case 12: 
            case 13: 
            case 14: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 21: 
            case 24: 
            case 25: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }

    public class CopyConfiguration
    implements FunctionDescriptor.CopyBuilder<FunctionDescriptor> {
        @NotNull
        protected TypeSubstitution substitution;
        @NotNull
        protected DeclarationDescriptor newOwner;
        @NotNull
        protected Modality newModality;
        @NotNull
        protected Visibility newVisibility;
        @Nullable
        protected FunctionDescriptor original;
        @NotNull
        protected CallableMemberDescriptor.Kind kind;
        @NotNull
        protected List<ValueParameterDescriptor> newValueParameterDescriptors;
        @Nullable
        protected ReceiverParameterDescriptor newExtensionReceiverParameter;
        @Nullable
        protected ReceiverParameterDescriptor dispatchReceiverParameter;
        @NotNull
        protected KotlinType newReturnType;
        @Nullable
        protected Name name;
        protected boolean copyOverrides;
        protected boolean signatureChange;
        protected boolean preserveSourceElement;
        protected boolean dropOriginalInContainingParts;
        private boolean isHiddenToOvercomeSignatureClash;
        private List<TypeParameterDescriptor> newTypeParameters;
        private Annotations additionalAnnotations;
        private boolean isHiddenForResolutionEverywhereBesideSupercalls;
        private Map<CallableDescriptor.UserDataKey<?>, Object> userDataMap;
        private Boolean newHasSynthesizedParameterNames;
        protected boolean justForTypeSubstitution;

        public CopyConfiguration(@NotNull TypeSubstitution substitution, @NotNull DeclarationDescriptor newOwner, @NotNull Modality newModality, @NotNull Visibility newVisibility, @NotNull CallableMemberDescriptor.Kind kind, @Nullable List<ValueParameterDescriptor> newValueParameterDescriptors, @NotNull ReceiverParameterDescriptor newExtensionReceiverParameter, @Nullable KotlinType newReturnType, Name name) {
            if (substitution == null) {
                CopyConfiguration.$$$reportNull$$$0(0);
            }
            if (newOwner == null) {
                CopyConfiguration.$$$reportNull$$$0(1);
            }
            if (newModality == null) {
                CopyConfiguration.$$$reportNull$$$0(2);
            }
            if (newVisibility == null) {
                CopyConfiguration.$$$reportNull$$$0(3);
            }
            if (kind == null) {
                CopyConfiguration.$$$reportNull$$$0(4);
            }
            if (newValueParameterDescriptors == null) {
                CopyConfiguration.$$$reportNull$$$0(5);
            }
            if (newReturnType == null) {
                CopyConfiguration.$$$reportNull$$$0(6);
            }
            this.original = null;
            this.dispatchReceiverParameter = FunctionDescriptorImpl.this.dispatchReceiverParameter;
            this.copyOverrides = true;
            this.signatureChange = false;
            this.preserveSourceElement = false;
            this.dropOriginalInContainingParts = false;
            this.isHiddenToOvercomeSignatureClash = FunctionDescriptorImpl.this.isHiddenToOvercomeSignatureClash();
            this.newTypeParameters = null;
            this.additionalAnnotations = null;
            this.isHiddenForResolutionEverywhereBesideSupercalls = FunctionDescriptorImpl.this.isHiddenForResolutionEverywhereBesideSupercalls();
            this.userDataMap = new LinkedHashMap();
            this.newHasSynthesizedParameterNames = null;
            this.justForTypeSubstitution = false;
            this.substitution = substitution;
            this.newOwner = newOwner;
            this.newModality = newModality;
            this.newVisibility = newVisibility;
            this.kind = kind;
            this.newValueParameterDescriptors = newValueParameterDescriptors;
            this.newExtensionReceiverParameter = newExtensionReceiverParameter;
            this.newReturnType = newReturnType;
            this.name = name;
        }

        @NotNull
        public CopyConfiguration setOwner(@NotNull DeclarationDescriptor owner) {
            if (owner == null) {
                CopyConfiguration.$$$reportNull$$$0(7);
            }
            this.newOwner = owner;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(8);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setModality(@NotNull Modality modality) {
            if (modality == null) {
                CopyConfiguration.$$$reportNull$$$0(9);
            }
            this.newModality = modality;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(10);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setVisibility(@NotNull Visibility visibility) {
            if (visibility == null) {
                CopyConfiguration.$$$reportNull$$$0(11);
            }
            this.newVisibility = visibility;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(12);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setKind(@NotNull CallableMemberDescriptor.Kind kind) {
            if (kind == null) {
                CopyConfiguration.$$$reportNull$$$0(13);
            }
            this.kind = kind;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(14);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setCopyOverrides(boolean copyOverrides) {
            this.copyOverrides = copyOverrides;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(15);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setName(@NotNull Name name) {
            if (name == null) {
                CopyConfiguration.$$$reportNull$$$0(16);
            }
            this.name = name;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(17);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setValueParameters(@NotNull List<ValueParameterDescriptor> parameters2) {
            if (parameters2 == null) {
                CopyConfiguration.$$$reportNull$$$0(18);
            }
            this.newValueParameterDescriptors = parameters2;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(19);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setTypeParameters(@NotNull List<TypeParameterDescriptor> parameters2) {
            if (parameters2 == null) {
                CopyConfiguration.$$$reportNull$$$0(20);
            }
            this.newTypeParameters = parameters2;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(21);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setReturnType(@NotNull KotlinType type2) {
            if (type2 == null) {
                CopyConfiguration.$$$reportNull$$$0(22);
            }
            this.newReturnType = type2;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(23);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setExtensionReceiverParameter(@Nullable ReceiverParameterDescriptor extensionReceiverParameter) {
            this.newExtensionReceiverParameter = extensionReceiverParameter;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(24);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setDispatchReceiverParameter(@Nullable ReceiverParameterDescriptor dispatchReceiverParameter) {
            this.dispatchReceiverParameter = dispatchReceiverParameter;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(25);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setOriginal(@Nullable CallableMemberDescriptor original) {
            this.original = (FunctionDescriptor)original;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(26);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setSignatureChange() {
            this.signatureChange = true;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(27);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setPreserveSourceElement() {
            this.preserveSourceElement = true;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(28);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setDropOriginalInContainingParts() {
            this.dropOriginalInContainingParts = true;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(29);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setHiddenToOvercomeSignatureClash() {
            this.isHiddenToOvercomeSignatureClash = true;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(30);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setHiddenForResolutionEverywhereBesideSupercalls() {
            this.isHiddenForResolutionEverywhereBesideSupercalls = true;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(31);
            }
            return copyConfiguration;
        }

        @NotNull
        public CopyConfiguration setAdditionalAnnotations(@NotNull Annotations additionalAnnotations) {
            if (additionalAnnotations == null) {
                CopyConfiguration.$$$reportNull$$$0(32);
            }
            this.additionalAnnotations = additionalAnnotations;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(33);
            }
            return copyConfiguration;
        }

        public CopyConfiguration setHasSynthesizedParameterNames(boolean value) {
            this.newHasSynthesizedParameterNames = value;
            return this;
        }

        @NotNull
        public CopyConfiguration setSubstitution(@NotNull TypeSubstitution substitution) {
            if (substitution == null) {
                CopyConfiguration.$$$reportNull$$$0(34);
            }
            this.substitution = substitution;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(35);
            }
            return copyConfiguration;
        }

        @Override
        @Nullable
        public FunctionDescriptor build() {
            return FunctionDescriptorImpl.this.doSubstitute(this);
        }

        @NotNull
        public CopyConfiguration setJustForTypeSubstitution(boolean value) {
            this.justForTypeSubstitution = value;
            CopyConfiguration copyConfiguration = this;
            if (copyConfiguration == null) {
                CopyConfiguration.$$$reportNull$$$0(39);
            }
            return copyConfiguration;
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            RuntimeException runtimeException;
            Object[] objectArray;
            Object[] objectArray2;
            int n2;
            String string;
            switch (n) {
                default: {
                    string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                }
                case 8: 
                case 10: 
                case 12: 
                case 14: 
                case 15: 
                case 17: 
                case 19: 
                case 21: 
                case 23: 
                case 24: 
                case 25: 
                case 26: 
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 33: 
                case 35: 
                case 37: 
                case 38: 
                case 39: {
                    string = "@NotNull method %s.%s must not return null";
                    break;
                }
            }
            switch (n) {
                default: {
                    n2 = 3;
                    break;
                }
                case 8: 
                case 10: 
                case 12: 
                case 14: 
                case 15: 
                case 17: 
                case 19: 
                case 21: 
                case 23: 
                case 24: 
                case 25: 
                case 26: 
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 33: 
                case 35: 
                case 37: 
                case 38: 
                case 39: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "substitution";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "newOwner";
                    break;
                }
                case 2: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "newModality";
                    break;
                }
                case 3: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "newVisibility";
                    break;
                }
                case 4: 
                case 13: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kind";
                    break;
                }
                case 5: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "newValueParameterDescriptors";
                    break;
                }
                case 6: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "newReturnType";
                    break;
                }
                case 7: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "owner";
                    break;
                }
                case 8: 
                case 10: 
                case 12: 
                case 14: 
                case 15: 
                case 17: 
                case 19: 
                case 21: 
                case 23: 
                case 24: 
                case 25: 
                case 26: 
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 33: 
                case 35: 
                case 37: 
                case 38: 
                case 39: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/FunctionDescriptorImpl$CopyConfiguration";
                    break;
                }
                case 9: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "modality";
                    break;
                }
                case 11: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "visibility";
                    break;
                }
                case 16: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "name";
                    break;
                }
                case 18: 
                case 20: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "parameters";
                    break;
                }
                case 22: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "type";
                    break;
                }
                case 32: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "additionalAnnotations";
                    break;
                }
                case 36: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "userDataKey";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/FunctionDescriptorImpl$CopyConfiguration";
                    break;
                }
                case 8: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setOwner";
                    break;
                }
                case 10: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setModality";
                    break;
                }
                case 12: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setVisibility";
                    break;
                }
                case 14: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setKind";
                    break;
                }
                case 15: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setCopyOverrides";
                    break;
                }
                case 17: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setName";
                    break;
                }
                case 19: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setValueParameters";
                    break;
                }
                case 21: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setTypeParameters";
                    break;
                }
                case 23: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setReturnType";
                    break;
                }
                case 24: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setExtensionReceiverParameter";
                    break;
                }
                case 25: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setDispatchReceiverParameter";
                    break;
                }
                case 26: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setOriginal";
                    break;
                }
                case 27: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setSignatureChange";
                    break;
                }
                case 28: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setPreserveSourceElement";
                    break;
                }
                case 29: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setDropOriginalInContainingParts";
                    break;
                }
                case 30: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setHiddenToOvercomeSignatureClash";
                    break;
                }
                case 31: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setHiddenForResolutionEverywhereBesideSupercalls";
                    break;
                }
                case 33: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setAdditionalAnnotations";
                    break;
                }
                case 35: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setSubstitution";
                    break;
                }
                case 37: {
                    objectArray = objectArray2;
                    objectArray2[1] = "putUserData";
                    break;
                }
                case 38: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getSubstitution";
                    break;
                }
                case 39: {
                    objectArray = objectArray2;
                    objectArray2[1] = "setJustForTypeSubstitution";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "<init>";
                    break;
                }
                case 7: {
                    objectArray = objectArray;
                    objectArray[2] = "setOwner";
                    break;
                }
                case 8: 
                case 10: 
                case 12: 
                case 14: 
                case 15: 
                case 17: 
                case 19: 
                case 21: 
                case 23: 
                case 24: 
                case 25: 
                case 26: 
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 33: 
                case 35: 
                case 37: 
                case 38: 
                case 39: {
                    break;
                }
                case 9: {
                    objectArray = objectArray;
                    objectArray[2] = "setModality";
                    break;
                }
                case 11: {
                    objectArray = objectArray;
                    objectArray[2] = "setVisibility";
                    break;
                }
                case 13: {
                    objectArray = objectArray;
                    objectArray[2] = "setKind";
                    break;
                }
                case 16: {
                    objectArray = objectArray;
                    objectArray[2] = "setName";
                    break;
                }
                case 18: {
                    objectArray = objectArray;
                    objectArray[2] = "setValueParameters";
                    break;
                }
                case 20: {
                    objectArray = objectArray;
                    objectArray[2] = "setTypeParameters";
                    break;
                }
                case 22: {
                    objectArray = objectArray;
                    objectArray[2] = "setReturnType";
                    break;
                }
                case 32: {
                    objectArray = objectArray;
                    objectArray[2] = "setAdditionalAnnotations";
                    break;
                }
                case 34: {
                    objectArray = objectArray;
                    objectArray[2] = "setSubstitution";
                    break;
                }
                case 36: {
                    objectArray = objectArray;
                    objectArray[2] = "putUserData";
                    break;
                }
            }
            String string2 = String.format(string, objectArray);
            switch (n) {
                default: {
                    runtimeException = new IllegalArgumentException(string2);
                    break;
                }
                case 8: 
                case 10: 
                case 12: 
                case 14: 
                case 15: 
                case 17: 
                case 19: 
                case 21: 
                case 23: 
                case 24: 
                case 25: 
                case 26: 
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 33: 
                case 35: 
                case 37: 
                case 38: 
                case 39: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }
}

