/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.renderer;

import java.lang.reflect.Field;
import java.util.Set;
import kotlin._Assertions;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ObservableProperty;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.renderer.AnnotationArgumentsRenderingPolicy;
import kotlin.reflect.jvm.internal.impl.renderer.ClassifierNamePolicy;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererModifier;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptionsImpl;
import kotlin.reflect.jvm.internal.impl.renderer.ExcludedTypeAnnotations;
import kotlin.reflect.jvm.internal.impl.renderer.OverrideRenderingPolicy;
import kotlin.reflect.jvm.internal.impl.renderer.ParameterNameRenderingPolicy;
import kotlin.reflect.jvm.internal.impl.renderer.PropertyAccessorRenderingPolicy;
import kotlin.reflect.jvm.internal.impl.renderer.RenderingFormat;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DescriptorRendererOptionsImpl
implements DescriptorRendererOptions {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private boolean isLocked;
    @NotNull
    private final ReadWriteProperty classifierNamePolicy$delegate = this.property(ClassifierNamePolicy.SOURCE_CODE_QUALIFIED.INSTANCE);
    @NotNull
    private final ReadWriteProperty withDefinedIn$delegate = this.property(true);
    @NotNull
    private final ReadWriteProperty withSourceFileForTopLevel$delegate = this.property(true);
    @NotNull
    private final ReadWriteProperty modifiers$delegate = this.property(DescriptorRendererModifier.ALL_EXCEPT_ANNOTATIONS);
    @NotNull
    private final ReadWriteProperty startFromName$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty startFromDeclarationKeyword$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty debugMode$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty classWithPrimaryConstructor$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty verbose$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty unitReturnType$delegate = this.property(true);
    @NotNull
    private final ReadWriteProperty withoutReturnType$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty enhancedTypes$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty normalizedVisibilities$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty renderDefaultVisibility$delegate = this.property(true);
    @NotNull
    private final ReadWriteProperty renderDefaultModality$delegate = this.property(true);
    @NotNull
    private final ReadWriteProperty renderConstructorDelegation$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty renderPrimaryConstructorParametersAsProperties$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty actualPropertiesInPrimaryConstructor$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty uninferredTypeParameterAsName$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty includePropertyConstant$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty withoutTypeParameters$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty withoutSuperTypes$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty typeNormalizer$delegate = this.property(typeNormalizer.2.INSTANCE);
    @Nullable
    private final ReadWriteProperty defaultParameterValueRenderer$delegate = this.property(defaultParameterValueRenderer.2.INSTANCE);
    @NotNull
    private final ReadWriteProperty secondaryConstructorsAsPrimary$delegate = this.property(true);
    @NotNull
    private final ReadWriteProperty overrideRenderingPolicy$delegate = this.property(OverrideRenderingPolicy.RENDER_OPEN);
    @NotNull
    private final ReadWriteProperty valueParametersHandler$delegate = this.property(DescriptorRenderer.ValueParametersHandler.DEFAULT.INSTANCE);
    @NotNull
    private final ReadWriteProperty textFormat$delegate = this.property(RenderingFormat.PLAIN);
    @NotNull
    private final ReadWriteProperty parameterNameRenderingPolicy$delegate = this.property(ParameterNameRenderingPolicy.ALL);
    @NotNull
    private final ReadWriteProperty receiverAfterName$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty renderCompanionObjectName$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty propertyAccessorRenderingPolicy$delegate = this.property(PropertyAccessorRenderingPolicy.DEBUG);
    @NotNull
    private final ReadWriteProperty renderDefaultAnnotationArguments$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty eachAnnotationOnNewLine$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty excludedAnnotationClasses$delegate = this.property(SetsKt.emptySet());
    @NotNull
    private final ReadWriteProperty excludedTypeAnnotationClasses$delegate = this.property(ExcludedTypeAnnotations.INSTANCE.getInternalAnnotationsForResolve());
    @Nullable
    private final ReadWriteProperty annotationFilter$delegate = this.property(null);
    @NotNull
    private final ReadWriteProperty annotationArgumentsRenderingPolicy$delegate = this.property(AnnotationArgumentsRenderingPolicy.NO_ARGUMENTS);
    @NotNull
    private final ReadWriteProperty alwaysRenderModifiers$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty renderConstructorKeyword$delegate = this.property(true);
    @NotNull
    private final ReadWriteProperty renderUnabbreviatedType$delegate = this.property(true);
    @NotNull
    private final ReadWriteProperty renderTypeExpansions$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty includeAdditionalModifiers$delegate = this.property(true);
    @NotNull
    private final ReadWriteProperty parameterNamesInFunctionalTypes$delegate = this.property(true);
    @NotNull
    private final ReadWriteProperty renderFunctionContracts$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty presentableUnresolvedTypes$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty boldOnlyForNamesInHtml$delegate = this.property(false);
    @NotNull
    private final ReadWriteProperty informativeErrorType$delegate = this.property(true);

    static {
        $$delegatedProperties = new KProperty[]{Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "classifierNamePolicy", "getClassifierNamePolicy()Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "withDefinedIn", "getWithDefinedIn()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "withSourceFileForTopLevel", "getWithSourceFileForTopLevel()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "modifiers", "getModifiers()Ljava/util/Set;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "startFromName", "getStartFromName()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "startFromDeclarationKeyword", "getStartFromDeclarationKeyword()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "debugMode", "getDebugMode()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "classWithPrimaryConstructor", "getClassWithPrimaryConstructor()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "verbose", "getVerbose()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "unitReturnType", "getUnitReturnType()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "withoutReturnType", "getWithoutReturnType()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "enhancedTypes", "getEnhancedTypes()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "normalizedVisibilities", "getNormalizedVisibilities()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderDefaultVisibility", "getRenderDefaultVisibility()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderDefaultModality", "getRenderDefaultModality()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderConstructorDelegation", "getRenderConstructorDelegation()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderPrimaryConstructorParametersAsProperties", "getRenderPrimaryConstructorParametersAsProperties()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "actualPropertiesInPrimaryConstructor", "getActualPropertiesInPrimaryConstructor()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "uninferredTypeParameterAsName", "getUninferredTypeParameterAsName()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "includePropertyConstant", "getIncludePropertyConstant()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "withoutTypeParameters", "getWithoutTypeParameters()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "withoutSuperTypes", "getWithoutSuperTypes()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "typeNormalizer", "getTypeNormalizer()Lkotlin/jvm/functions/Function1;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "defaultParameterValueRenderer", "getDefaultParameterValueRenderer()Lkotlin/jvm/functions/Function1;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "secondaryConstructorsAsPrimary", "getSecondaryConstructorsAsPrimary()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "overrideRenderingPolicy", "getOverrideRenderingPolicy()Lorg/jetbrains/kotlin/renderer/OverrideRenderingPolicy;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "valueParametersHandler", "getValueParametersHandler()Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$ValueParametersHandler;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "textFormat", "getTextFormat()Lorg/jetbrains/kotlin/renderer/RenderingFormat;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "parameterNameRenderingPolicy", "getParameterNameRenderingPolicy()Lorg/jetbrains/kotlin/renderer/ParameterNameRenderingPolicy;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "receiverAfterName", "getReceiverAfterName()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderCompanionObjectName", "getRenderCompanionObjectName()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "propertyAccessorRenderingPolicy", "getPropertyAccessorRenderingPolicy()Lorg/jetbrains/kotlin/renderer/PropertyAccessorRenderingPolicy;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderDefaultAnnotationArguments", "getRenderDefaultAnnotationArguments()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "eachAnnotationOnNewLine", "getEachAnnotationOnNewLine()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "excludedAnnotationClasses", "getExcludedAnnotationClasses()Ljava/util/Set;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "excludedTypeAnnotationClasses", "getExcludedTypeAnnotationClasses()Ljava/util/Set;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "annotationFilter", "getAnnotationFilter()Lkotlin/jvm/functions/Function1;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "annotationArgumentsRenderingPolicy", "getAnnotationArgumentsRenderingPolicy()Lorg/jetbrains/kotlin/renderer/AnnotationArgumentsRenderingPolicy;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "alwaysRenderModifiers", "getAlwaysRenderModifiers()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderConstructorKeyword", "getRenderConstructorKeyword()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderUnabbreviatedType", "getRenderUnabbreviatedType()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderTypeExpansions", "getRenderTypeExpansions()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "includeAdditionalModifiers", "getIncludeAdditionalModifiers()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "parameterNamesInFunctionalTypes", "getParameterNamesInFunctionalTypes()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderFunctionContracts", "getRenderFunctionContracts()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "presentableUnresolvedTypes", "getPresentableUnresolvedTypes()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "boldOnlyForNamesInHtml", "getBoldOnlyForNamesInHtml()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "informativeErrorType", "getInformativeErrorType()Z"))};
    }

    public final boolean isLocked() {
        return this.isLocked;
    }

    public final void lock() {
        boolean bl = !this.isLocked;
        boolean bl2 = false;
        boolean bl3 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl4 = false;
            String string = "Assertion failed";
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        this.isLocked = true;
    }

    @NotNull
    public final DescriptorRendererOptionsImpl copy() {
        DescriptorRendererOptionsImpl copy2 = new DescriptorRendererOptionsImpl();
        Field[] fieldArray = this.getClass().getDeclaredFields();
        int n = fieldArray.length;
        for (int i = 0; i < n; ++i) {
            ObservableProperty property;
            Field field;
            Field field2 = field = fieldArray[i];
            Intrinsics.checkNotNullExpressionValue(field2, "field");
            if ((field2.getModifiers() & 8) != 0) continue;
            field.setAccessible(true);
            Object object = field.get(this);
            if (!(object instanceof ObservableProperty)) {
                object = null;
            }
            if ((ObservableProperty)object == null) {
                continue;
            }
            String string = field.getName();
            Intrinsics.checkNotNullExpressionValue(string, "field.name");
            boolean bl = !StringsKt.startsWith$default(string, "is", false, 2, null);
            boolean bl2 = false;
            if (_Assertions.ENABLED && !bl) {
                boolean bl3 = false;
                String string2 = "Fields named is* are not supported here yet";
                throw (Throwable)((Object)new AssertionError((Object)string2));
            }
            KDeclarationContainer kDeclarationContainer = Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class);
            String string3 = field.getName();
            StringBuilder stringBuilder = new StringBuilder().append("get");
            String string4 = field.getName();
            Intrinsics.checkNotNullExpressionValue(string4, "field.name");
            Object value = property.getValue(this, (KProperty<?>)new PropertyReference1Impl(kDeclarationContainer, string3, stringBuilder.append(StringsKt.capitalize(string4)).toString()));
            field.set(copy2, copy2.property(value));
        }
        return copy2;
    }

    private final <T> ReadWriteProperty<DescriptorRendererOptionsImpl, T> property(T initialValue) {
        Delegates this_$iv = Delegates.INSTANCE;
        boolean $i$f$vetoable = false;
        return new ObservableProperty<T>(initialValue, initialValue, this){
            final /* synthetic */ Object $initialValue;
            final /* synthetic */ DescriptorRendererOptionsImpl this$0;
            {
                this.$initialValue = $captured_local_variable$1;
                this.this$0 = descriptorRendererOptionsImpl;
                super($super_call_param$2);
            }

            protected boolean beforeChange(@NotNull KProperty<?> property, T oldValue, T newValue) {
                Intrinsics.checkNotNullParameter(property, "property");
                T t = newValue;
                T t2 = oldValue;
                KProperty<?> $noName_0 = property;
                boolean bl = false;
                if (this.this$0.isLocked()) {
                    throw (Throwable)new IllegalStateException("Cannot modify readonly DescriptorRendererOptions");
                }
                return true;
            }
        };
    }

    @NotNull
    public ClassifierNamePolicy getClassifierNamePolicy() {
        return (ClassifierNamePolicy)this.classifierNamePolicy$delegate.getValue(this, $$delegatedProperties[0]);
    }

    @Override
    public void setClassifierNamePolicy(@NotNull ClassifierNamePolicy classifierNamePolicy) {
        Intrinsics.checkNotNullParameter(classifierNamePolicy, "<set-?>");
        this.classifierNamePolicy$delegate.setValue(this, $$delegatedProperties[0], classifierNamePolicy);
    }

    public boolean getWithDefinedIn() {
        return (Boolean)this.withDefinedIn$delegate.getValue(this, $$delegatedProperties[1]);
    }

    @Override
    public void setWithDefinedIn(boolean bl) {
        this.withDefinedIn$delegate.setValue(this, $$delegatedProperties[1], bl);
    }

    public boolean getWithSourceFileForTopLevel() {
        return (Boolean)this.withSourceFileForTopLevel$delegate.getValue(this, $$delegatedProperties[2]);
    }

    @NotNull
    public Set<DescriptorRendererModifier> getModifiers() {
        return (Set)this.modifiers$delegate.getValue(this, $$delegatedProperties[3]);
    }

    @Override
    public void setModifiers(@NotNull Set<? extends DescriptorRendererModifier> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.modifiers$delegate.setValue(this, $$delegatedProperties[3], set);
    }

    public boolean getStartFromName() {
        return (Boolean)this.startFromName$delegate.getValue(this, $$delegatedProperties[4]);
    }

    @Override
    public void setStartFromName(boolean bl) {
        this.startFromName$delegate.setValue(this, $$delegatedProperties[4], bl);
    }

    public boolean getStartFromDeclarationKeyword() {
        return (Boolean)this.startFromDeclarationKeyword$delegate.getValue(this, $$delegatedProperties[5]);
    }

    @Override
    public boolean getDebugMode() {
        return (Boolean)this.debugMode$delegate.getValue(this, $$delegatedProperties[6]);
    }

    @Override
    public void setDebugMode(boolean bl) {
        this.debugMode$delegate.setValue(this, $$delegatedProperties[6], bl);
    }

    public boolean getClassWithPrimaryConstructor() {
        return (Boolean)this.classWithPrimaryConstructor$delegate.getValue(this, $$delegatedProperties[7]);
    }

    public boolean getVerbose() {
        return (Boolean)this.verbose$delegate.getValue(this, $$delegatedProperties[8]);
    }

    @Override
    public void setVerbose(boolean bl) {
        this.verbose$delegate.setValue(this, $$delegatedProperties[8], bl);
    }

    public boolean getUnitReturnType() {
        return (Boolean)this.unitReturnType$delegate.getValue(this, $$delegatedProperties[9]);
    }

    public boolean getWithoutReturnType() {
        return (Boolean)this.withoutReturnType$delegate.getValue(this, $$delegatedProperties[10]);
    }

    @Override
    public boolean getEnhancedTypes() {
        return (Boolean)this.enhancedTypes$delegate.getValue(this, $$delegatedProperties[11]);
    }

    public boolean getNormalizedVisibilities() {
        return (Boolean)this.normalizedVisibilities$delegate.getValue(this, $$delegatedProperties[12]);
    }

    public boolean getRenderDefaultVisibility() {
        return (Boolean)this.renderDefaultVisibility$delegate.getValue(this, $$delegatedProperties[13]);
    }

    public boolean getRenderDefaultModality() {
        return (Boolean)this.renderDefaultModality$delegate.getValue(this, $$delegatedProperties[14]);
    }

    public boolean getRenderConstructorDelegation() {
        return (Boolean)this.renderConstructorDelegation$delegate.getValue(this, $$delegatedProperties[15]);
    }

    public boolean getRenderPrimaryConstructorParametersAsProperties() {
        return (Boolean)this.renderPrimaryConstructorParametersAsProperties$delegate.getValue(this, $$delegatedProperties[16]);
    }

    public boolean getActualPropertiesInPrimaryConstructor() {
        return (Boolean)this.actualPropertiesInPrimaryConstructor$delegate.getValue(this, $$delegatedProperties[17]);
    }

    public boolean getUninferredTypeParameterAsName() {
        return (Boolean)this.uninferredTypeParameterAsName$delegate.getValue(this, $$delegatedProperties[18]);
    }

    public boolean getIncludePropertyConstant() {
        return (Boolean)this.includePropertyConstant$delegate.getValue(this, $$delegatedProperties[19]);
    }

    public boolean getWithoutTypeParameters() {
        return (Boolean)this.withoutTypeParameters$delegate.getValue(this, $$delegatedProperties[20]);
    }

    @Override
    public void setWithoutTypeParameters(boolean bl) {
        this.withoutTypeParameters$delegate.setValue(this, $$delegatedProperties[20], bl);
    }

    public boolean getWithoutSuperTypes() {
        return (Boolean)this.withoutSuperTypes$delegate.getValue(this, $$delegatedProperties[21]);
    }

    @Override
    public void setWithoutSuperTypes(boolean bl) {
        this.withoutSuperTypes$delegate.setValue(this, $$delegatedProperties[21], bl);
    }

    @NotNull
    public Function1<KotlinType, KotlinType> getTypeNormalizer() {
        return (Function1)this.typeNormalizer$delegate.getValue(this, $$delegatedProperties[22]);
    }

    @Nullable
    public Function1<ValueParameterDescriptor, String> getDefaultParameterValueRenderer() {
        return (Function1)this.defaultParameterValueRenderer$delegate.getValue(this, $$delegatedProperties[23]);
    }

    public boolean getSecondaryConstructorsAsPrimary() {
        return (Boolean)this.secondaryConstructorsAsPrimary$delegate.getValue(this, $$delegatedProperties[24]);
    }

    @NotNull
    public OverrideRenderingPolicy getOverrideRenderingPolicy() {
        return (OverrideRenderingPolicy)((Object)this.overrideRenderingPolicy$delegate.getValue(this, $$delegatedProperties[25]));
    }

    @NotNull
    public DescriptorRenderer.ValueParametersHandler getValueParametersHandler() {
        return (DescriptorRenderer.ValueParametersHandler)this.valueParametersHandler$delegate.getValue(this, $$delegatedProperties[26]);
    }

    @NotNull
    public RenderingFormat getTextFormat() {
        return (RenderingFormat)((Object)this.textFormat$delegate.getValue(this, $$delegatedProperties[27]));
    }

    @Override
    public void setTextFormat(@NotNull RenderingFormat renderingFormat) {
        Intrinsics.checkNotNullParameter((Object)renderingFormat, "<set-?>");
        this.textFormat$delegate.setValue(this, $$delegatedProperties[27], renderingFormat);
    }

    @NotNull
    public ParameterNameRenderingPolicy getParameterNameRenderingPolicy() {
        return (ParameterNameRenderingPolicy)((Object)this.parameterNameRenderingPolicy$delegate.getValue(this, $$delegatedProperties[28]));
    }

    @Override
    public void setParameterNameRenderingPolicy(@NotNull ParameterNameRenderingPolicy parameterNameRenderingPolicy) {
        Intrinsics.checkNotNullParameter((Object)parameterNameRenderingPolicy, "<set-?>");
        this.parameterNameRenderingPolicy$delegate.setValue(this, $$delegatedProperties[28], parameterNameRenderingPolicy);
    }

    public boolean getReceiverAfterName() {
        return (Boolean)this.receiverAfterName$delegate.getValue(this, $$delegatedProperties[29]);
    }

    @Override
    public void setReceiverAfterName(boolean bl) {
        this.receiverAfterName$delegate.setValue(this, $$delegatedProperties[29], bl);
    }

    public boolean getRenderCompanionObjectName() {
        return (Boolean)this.renderCompanionObjectName$delegate.getValue(this, $$delegatedProperties[30]);
    }

    @Override
    public void setRenderCompanionObjectName(boolean bl) {
        this.renderCompanionObjectName$delegate.setValue(this, $$delegatedProperties[30], bl);
    }

    @NotNull
    public PropertyAccessorRenderingPolicy getPropertyAccessorRenderingPolicy() {
        return (PropertyAccessorRenderingPolicy)((Object)this.propertyAccessorRenderingPolicy$delegate.getValue(this, $$delegatedProperties[31]));
    }

    public boolean getRenderDefaultAnnotationArguments() {
        return (Boolean)this.renderDefaultAnnotationArguments$delegate.getValue(this, $$delegatedProperties[32]);
    }

    public boolean getEachAnnotationOnNewLine() {
        return (Boolean)this.eachAnnotationOnNewLine$delegate.getValue(this, $$delegatedProperties[33]);
    }

    @NotNull
    public Set<FqName> getExcludedAnnotationClasses() {
        return (Set)this.excludedAnnotationClasses$delegate.getValue(this, $$delegatedProperties[34]);
    }

    @Override
    @NotNull
    public Set<FqName> getExcludedTypeAnnotationClasses() {
        return (Set)this.excludedTypeAnnotationClasses$delegate.getValue(this, $$delegatedProperties[35]);
    }

    @Override
    public void setExcludedTypeAnnotationClasses(@NotNull Set<FqName> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.excludedTypeAnnotationClasses$delegate.setValue(this, $$delegatedProperties[35], set);
    }

    @Nullable
    public Function1<AnnotationDescriptor, Boolean> getAnnotationFilter() {
        return (Function1)this.annotationFilter$delegate.getValue(this, $$delegatedProperties[36]);
    }

    @Override
    @NotNull
    public AnnotationArgumentsRenderingPolicy getAnnotationArgumentsRenderingPolicy() {
        return (AnnotationArgumentsRenderingPolicy)((Object)this.annotationArgumentsRenderingPolicy$delegate.getValue(this, $$delegatedProperties[37]));
    }

    @Override
    public void setAnnotationArgumentsRenderingPolicy(@NotNull AnnotationArgumentsRenderingPolicy annotationArgumentsRenderingPolicy) {
        Intrinsics.checkNotNullParameter((Object)annotationArgumentsRenderingPolicy, "<set-?>");
        this.annotationArgumentsRenderingPolicy$delegate.setValue(this, $$delegatedProperties[37], annotationArgumentsRenderingPolicy);
    }

    public boolean getAlwaysRenderModifiers() {
        return (Boolean)this.alwaysRenderModifiers$delegate.getValue(this, $$delegatedProperties[38]);
    }

    public boolean getRenderConstructorKeyword() {
        return (Boolean)this.renderConstructorKeyword$delegate.getValue(this, $$delegatedProperties[39]);
    }

    public boolean getRenderUnabbreviatedType() {
        return (Boolean)this.renderUnabbreviatedType$delegate.getValue(this, $$delegatedProperties[40]);
    }

    public boolean getRenderTypeExpansions() {
        return (Boolean)this.renderTypeExpansions$delegate.getValue(this, $$delegatedProperties[41]);
    }

    public boolean getIncludeAdditionalModifiers() {
        return (Boolean)this.includeAdditionalModifiers$delegate.getValue(this, $$delegatedProperties[42]);
    }

    public boolean getParameterNamesInFunctionalTypes() {
        return (Boolean)this.parameterNamesInFunctionalTypes$delegate.getValue(this, $$delegatedProperties[43]);
    }

    public boolean getPresentableUnresolvedTypes() {
        return (Boolean)this.presentableUnresolvedTypes$delegate.getValue(this, $$delegatedProperties[45]);
    }

    public boolean getBoldOnlyForNamesInHtml() {
        return (Boolean)this.boldOnlyForNamesInHtml$delegate.getValue(this, $$delegatedProperties[46]);
    }

    public boolean getInformativeErrorType() {
        return (Boolean)this.informativeErrorType$delegate.getValue(this, $$delegatedProperties[47]);
    }

    public boolean getIncludeAnnotationArguments() {
        return DescriptorRendererOptions.DefaultImpls.getIncludeAnnotationArguments(this);
    }

    public boolean getIncludeEmptyAnnotationArguments() {
        return DescriptorRendererOptions.DefaultImpls.getIncludeEmptyAnnotationArguments(this);
    }
}

