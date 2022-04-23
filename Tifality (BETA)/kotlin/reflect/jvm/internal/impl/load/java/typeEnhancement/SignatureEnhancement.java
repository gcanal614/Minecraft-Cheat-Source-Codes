/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationsKt;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.DeprecationCausedByFunctionN;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNamesKt;
import kotlin.reflect.jvm.internal.impl.load.java.UtilsKt;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.AnnotationDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaCallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.NullDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.PossiblyExternalAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.StringDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.UtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.ValueParameterData;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaTypeQualifiersByElementType;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.JavaDescriptorUtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NotNullTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancementKt;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeAndDefaultQualifiers;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnhancementInfo;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnhancementKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.deprecation.DeprecationKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.Jsr305State;
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SignatureEnhancement {
    private final AnnotationTypeQualifierResolver annotationTypeQualifierResolver;
    private final Jsr305State jsr305State;

    /*
     * WARNING - bad return control flow
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final NullabilityQualifierWithMigrationStatus extractNullabilityTypeFromArgument(AnnotationDescriptor $this$extractNullabilityTypeFromArgument) {
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus;
        block7: {
            ConstantValue<?> constantValue = DescriptorUtilsKt.firstArgument($this$extractNullabilityTypeFromArgument);
            if (!(constantValue instanceof EnumValue)) {
                constantValue = null;
            }
            EnumValue enumValue = (EnumValue)constantValue;
            if (enumValue == null) return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null);
            EnumValue enumValue2 = enumValue;
            String string = enumValue2.getEnumEntryName().asString();
            switch (string.hashCode()) {
                case 73135176: {
                    if (!string.equals("MAYBE")) return null;
                    break;
                }
                case 74175084: {
                    if (!string.equals("NEVER")) return null;
                    break;
                }
                case 433141802: {
                    if (!string.equals("UNKNOWN")) return null;
                    break block7;
                }
                case 1933739535: {
                    if (!string.equals("ALWAYS")) return null;
                    nullabilityQualifierWithMigrationStatus = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null);
                    return nullabilityQualifierWithMigrationStatus;
                }
            }
            nullabilityQualifierWithMigrationStatus = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null);
            return nullabilityQualifierWithMigrationStatus;
        }
        nullabilityQualifierWithMigrationStatus = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.FORCE_FLEXIBILITY, false, 2, null);
        return nullabilityQualifierWithMigrationStatus;
        return null;
    }

    @Nullable
    public final NullabilityQualifierWithMigrationStatus extractNullability(@NotNull AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus = this.extractNullabilityFromKnownAnnotations(annotationDescriptor);
        if (nullabilityQualifierWithMigrationStatus != null) {
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus2 = nullabilityQualifierWithMigrationStatus;
            boolean bl = false;
            boolean bl2 = false;
            NullabilityQualifierWithMigrationStatus it = nullabilityQualifierWithMigrationStatus2;
            boolean bl3 = false;
            return it;
        }
        AnnotationDescriptor annotationDescriptor2 = this.annotationTypeQualifierResolver.resolveTypeQualifierAnnotation(annotationDescriptor);
        if (annotationDescriptor2 == null) {
            return null;
        }
        AnnotationDescriptor typeQualifierAnnotation = annotationDescriptor2;
        ReportLevel jsr305State = this.annotationTypeQualifierResolver.resolveJsr305AnnotationState(annotationDescriptor);
        if (jsr305State.isIgnore()) {
            return null;
        }
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus3 = this.extractNullabilityFromKnownAnnotations(typeQualifierAnnotation);
        return nullabilityQualifierWithMigrationStatus3 != null ? NullabilityQualifierWithMigrationStatus.copy$default(nullabilityQualifierWithMigrationStatus3, null, jsr305State.isWarning(), 1, null) : null;
    }

    private final NullabilityQualifierWithMigrationStatus extractNullabilityFromKnownAnnotations(AnnotationDescriptor annotationDescriptor) {
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus;
        FqName fqName2 = annotationDescriptor.getFqName();
        if (fqName2 == null) {
            return null;
        }
        FqName annotationFqName = fqName2;
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus2 = JvmAnnotationNamesKt.getNULLABLE_ANNOTATIONS().contains(annotationFqName) ? new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null) : (JvmAnnotationNamesKt.getNOT_NULL_ANNOTATIONS().contains(annotationFqName) ? new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null) : (Intrinsics.areEqual(annotationFqName, JvmAnnotationNamesKt.getJAVAX_NONNULL_ANNOTATION()) ? this.extractNullabilityTypeFromArgument(annotationDescriptor) : (Intrinsics.areEqual(annotationFqName, JvmAnnotationNamesKt.getCOMPATQUAL_NULLABLE_ANNOTATION()) && this.jsr305State.getEnableCompatqualCheckerFrameworkAnnotations() ? new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null) : (Intrinsics.areEqual(annotationFqName, JvmAnnotationNamesKt.getCOMPATQUAL_NONNULL_ANNOTATION()) && this.jsr305State.getEnableCompatqualCheckerFrameworkAnnotations() ? new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null) : (Intrinsics.areEqual(annotationFqName, JvmAnnotationNamesKt.getANDROIDX_RECENTLY_NON_NULL_ANNOTATION()) ? new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, true) : (Intrinsics.areEqual(annotationFqName, JvmAnnotationNamesKt.getANDROIDX_RECENTLY_NULLABLE_ANNOTATION()) ? new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, true) : null))))));
        if (nullabilityQualifierWithMigrationStatus2 != null) {
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus3 = nullabilityQualifierWithMigrationStatus2;
            boolean bl = false;
            boolean bl2 = false;
            NullabilityQualifierWithMigrationStatus migrationStatus = nullabilityQualifierWithMigrationStatus3;
            boolean bl3 = false;
            nullabilityQualifierWithMigrationStatus = !migrationStatus.isForWarningOnly() && annotationDescriptor instanceof PossiblyExternalAnnotationDescriptor && ((PossiblyExternalAnnotationDescriptor)annotationDescriptor).isIdeExternalAnnotation() ? NullabilityQualifierWithMigrationStatus.copy$default(migrationStatus, null, true, 1, null) : migrationStatus;
        } else {
            nullabilityQualifierWithMigrationStatus = null;
        }
        return nullabilityQualifierWithMigrationStatus;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final <D extends CallableMemberDescriptor> Collection<D> enhanceSignatures(@NotNull LazyJavaResolverContext c, @NotNull Collection<? extends D> platformSignatures) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(platformSignatures, "platformSignatures");
        Iterable $this$map$iv = platformSignatures;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            void var13_13 = this.enhanceSignature(it, c);
            collection.add(var13_13);
        }
        return (List)destination$iv$iv;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final <D extends CallableMemberDescriptor> D enhanceSignature(D $this$enhanceSignature, LazyJavaResolverContext c) {
        block37: {
            block36: {
                block35: {
                    block34: {
                        if (!($this$enhanceSignature instanceof JavaCallableMemberDescriptor)) {
                            return $this$enhanceSignature;
                        }
                        if (((JavaCallableMemberDescriptor)$this$enhanceSignature).getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                            v0 = ((JavaCallableMemberDescriptor)$this$enhanceSignature).getOriginal();
                            Intrinsics.checkNotNullExpressionValue(v0, "original");
                            if (v0.getOverriddenDescriptors().size() == 1) {
                                return $this$enhanceSignature;
                            }
                        }
                        memberContext = ContextKt.copyWithNewDefaultTypeQualifiers(c, $this$enhanceSignature.getAnnotations());
                        if (!($this$enhanceSignature instanceof JavaPropertyDescriptor)) ** GOTO lbl-1000
                        v1 = ((JavaPropertyDescriptor)$this$enhanceSignature).getGetter();
                        if (v1 == null) ** GOTO lbl-1000
                        if (!v1.isDefault()) {
                            v2 = ((JavaPropertyDescriptor)$this$enhanceSignature).getGetter();
                            Intrinsics.checkNotNull(v2);
                            Intrinsics.checkNotNullExpressionValue(v2, "getter!!");
                            v3 /* !! */  = v2;
                        } else lbl-1000:
                        // 3 sources

                        {
                            v3 /* !! */  = annotationOwnerForMember = $this$enhanceSignature;
                        }
                        if (((JavaCallableMemberDescriptor)$this$enhanceSignature).getExtensionReceiverParameter() != null) {
                            $this$safeAs$iv = annotationOwnerForMember;
                            $i$f$safeAs = false;
                            v4 = $this$safeAs$iv;
                            if (!(v4 instanceof FunctionDescriptor)) {
                                v4 = null;
                            }
                            v5 = (FunctionDescriptor)v4;
                            v6 = SignatureParts.enhance$default(this.partsForValueParameter($this$enhanceSignature, v5 != null ? v5.getUserData(JavaMethodDescriptor.ORIGINAL_VALUE_PARAMETER_FOR_EXTENSION_RECEIVER) : null, memberContext, enhanceSignature.receiverTypeEnhancement.1.INSTANCE), null, 1, null);
                        } else {
                            v6 = receiverTypeEnhancement = null;
                        }
                        if (!((v7 = $this$enhanceSignature) instanceof JavaMethodDescriptor)) {
                            v7 = null;
                        }
                        if ((v8 = (JavaMethodDescriptor)v7) == null) ** GOTO lbl-1000
                        $i$f$safeAs = v8;
                        var8_11 = false;
                        var9_13 = false;
                        $this$run = $i$f$safeAs;
                        $i$a$-run-SignatureEnhancement$enhanceSignature$predefinedEnhancementInfo$1 = false;
                        v9 = $this$run.getContainingDeclaration();
                        if (v9 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                        }
                        v8 = SignatureBuildingComponents.INSTANCE.signature((ClassDescriptor)v9, MethodSignatureMappingKt.computeJvmDescriptor$default($this$run, false, false, 3, null));
                        if (v8 != null) {
                            $i$f$safeAs = v8;
                            var8_11 = false;
                            var9_13 = false;
                            signature = $i$f$safeAs;
                            $i$a$-let-SignatureEnhancement$enhanceSignature$predefinedEnhancementInfo$2 = false;
                            v10 = PredefinedEnhancementInfoKt.getPREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE().get(signature);
                        } else lbl-1000:
                        // 2 sources

                        {
                            v10 = null;
                        }
                        v11 = predefinedEnhancementInfo = v10;
                        if (v11 != null) {
                            $i$f$safeAs = v11;
                            var8_11 = false;
                            var9_13 = false;
                            it = $i$f$safeAs;
                            $i$a$-let-SignatureEnhancement$enhanceSignature$1 = false;
                            var12_26 = it.getParametersInfo().size() == ((JavaCallableMemberDescriptor)$this$enhanceSignature).getValueParameters().size();
                            var13_30 = false;
                            if (_Assertions.ENABLED && !var12_26) {
                                $i$a$-assert-SignatureEnhancement$enhanceSignature$1$1 = false;
                                $i$a$-assert-SignatureEnhancement$enhanceSignature$1$1 = "Predefined enhancement info for " + $this$enhanceSignature + " has " + it.getParametersInfo().size() + ", but " + ((JavaCallableMemberDescriptor)$this$enhanceSignature).getValueParameters().size() + " expected";
                                throw (Throwable)new AssertionError((Object)$i$a$-assert-SignatureEnhancement$enhanceSignature$1$1);
                            }
                        }
                        v12 = annotationOwnerForMember.getValueParameters();
                        Intrinsics.checkNotNullExpressionValue(v12, "annotationOwnerForMember.valueParameters");
                        $this$map$iv = v12;
                        $i$f$map = false;
                        it = $this$map$iv;
                        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        $i$f$mapTo = false;
                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                            var15_35 = (ValueParameterDescriptor)item$iv$iv;
                            var21_45 = destination$iv$iv;
                            $i$a$-map-SignatureEnhancement$enhanceSignature$valueParameterEnhancements$1 = false;
                            enhancementResult = this.partsForValueParameter($this$enhanceSignature, (ValueParameterDescriptor)p, memberContext, (Function1<? super CallableMemberDescriptor, ? extends KotlinType>)new Function1<CallableMemberDescriptor, KotlinType>((ValueParameterDescriptor)p){
                                final /* synthetic */ ValueParameterDescriptor $p;

                                @NotNull
                                public final KotlinType invoke(@NotNull CallableMemberDescriptor it) {
                                    Intrinsics.checkNotNullParameter(it, "it");
                                    ValueParameterDescriptor valueParameterDescriptor = it.getValueParameters().get(this.$p.getIndex());
                                    Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "it.valueParameters[p.index]");
                                    KotlinType kotlinType = valueParameterDescriptor.getType();
                                    Intrinsics.checkNotNullExpressionValue(kotlinType, "it.valueParameters[p.index].type");
                                    return kotlinType;
                                }
                                {
                                    this.$p = valueParameterDescriptor;
                                    super(1);
                                }
                            }).enhance((v13 = predefinedEnhancementInfo) != null && (v13 = v13.getParametersInfo()) != null ? (TypeEnhancementInfo)CollectionsKt.getOrNull(v13, p.getIndex()) : null);
                            if (enhancementResult.getWereChanges()) {
                                v14 = enhancementResult.getType();
                            } else {
                                v15 = p;
                                Intrinsics.checkNotNullExpressionValue(v15, "p");
                                v16 = v15.getType();
                                v14 = v16;
                                Intrinsics.checkNotNullExpressionValue(v16, "p.type");
                            }
                            actualType = v14;
                            v17 = p;
                            Intrinsics.checkNotNullExpressionValue(v17, "p");
                            hasDefaultValue = this.hasDefaultValueInAnnotation((ValueParameterDescriptor)v17, (KotlinType)actualType);
                            wereChanges = enhancementResult.getWereChanges() != false || hasDefaultValue != p.declaresDefaultValue();
                            var22_46 = new ValueParameterEnhancementResult(enhancementResult.getType(), hasDefaultValue, wereChanges, enhancementResult.getContainsFunctionN());
                            var21_45.add(var22_46);
                        }
                        valueParameterEnhancements = (List)destination$iv$iv;
                        v18 = annotationOwnerForMember;
                        $this$safeAs$iv = $this$enhanceSignature;
                        $i$f$safeAs = false;
                        v19 = $this$safeAs$iv;
                        if (!(v19 instanceof PropertyDescriptor)) {
                            v19 = null;
                        }
                        v20 = (PropertyDescriptor)v19;
                        if (v20 == null) ** GOTO lbl-1000
                        if (JavaDescriptorUtilKt.isJavaField(v20)) {
                            v21 = AnnotationTypeQualifierResolver.QualifierApplicabilityType.FIELD;
                        } else lbl-1000:
                        // 2 sources

                        {
                            v21 = AnnotationTypeQualifierResolver.QualifierApplicabilityType.METHOD_RETURN_TYPE;
                        }
                        v22 = predefinedEnhancementInfo;
                        returnTypeEnhancement = this.parts($this$enhanceSignature, v18, true, memberContext, v21, enhanceSignature.returnTypeEnhancement.1.INSTANCE).enhance(v22 != null ? v22.getReturnTypeInfo() : null);
                        v23 = receiverTypeEnhancement;
                        if (v23 != null && v23.getContainsFunctionN()) ** GOTO lbl-1000
                        if (returnTypeEnhancement.getContainsFunctionN()) ** GOTO lbl-1000
                        $this$any$iv = valueParameterEnhancements;
                        $i$f$any = false;
                        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                            v24 = false;
                        } else {
                            for (T element$iv : $this$any$iv) {
                                it = (ValueParameterEnhancementResult)element$iv;
                                $i$a$-any-SignatureEnhancement$enhanceSignature$containsFunctionN$1 = false;
                                if (!it.getContainsFunctionN()) continue;
                                v24 = true;
                                break block34;
                            }
                            v24 = false;
                        }
                    }
                    if (v24) lbl-1000:
                    // 3 sources

                    {
                        v25 = true;
                    } else {
                        v25 = false;
                    }
                    containsFunctionN = v25;
                    v26 = receiverTypeEnhancement;
                    if (v26 != null && v26.getWereChanges()) break block36;
                    if (returnTypeEnhancement.getWereChanges()) break block36;
                    $this$any$iv = valueParameterEnhancements;
                    $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        v27 = false;
                    } else {
                        for (T element$iv : $this$any$iv) {
                            it = (ValueParameterEnhancementResult)element$iv;
                            $i$a$-any-SignatureEnhancement$enhanceSignature$2 = false;
                            if (!it.getWereChanges()) continue;
                            v27 = true;
                            break block35;
                        }
                        v27 = false;
                    }
                }
                if (!v27 && !containsFunctionN) break block37;
            }
            additionalUserData = containsFunctionN != false ? TuplesKt.to(DeprecationKt.getDEPRECATED_FUNCTION_KEY(), new DeprecationCausedByFunctionN($this$enhanceSignature)) : null;
            v28 = receiverTypeEnhancement;
            $i$f$any = valueParameterEnhancements;
            var22_46 = v28 != null ? v28.getType() : null;
            var21_45 = (JavaCallableMemberDescriptor)$this$enhanceSignature;
            $i$f$map = false;
            element$iv = $this$map$iv;
            destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            $i$f$mapTo = false;
            for (T item$iv$iv : $this$mapTo$iv$iv) {
                actualType = (ValueParameterEnhancementResult)item$iv$iv;
                var23_47 = destination$iv$iv;
                $i$a$-map-SignatureEnhancement$enhanceSignature$3 = false;
                var24_48 = new ValueParameterData(it.getType(), it.getHasDefaultValue());
                var23_47.add(var24_48);
            }
            var23_47 = (List)destination$iv$iv;
            v29 = var21_45.enhance((KotlinType)var22_46, (List<ValueParameterData>)var23_47, returnTypeEnhancement.getType(), additionalUserData);
            if (v29 == null) {
                throw new NullPointerException("null cannot be cast to non-null type D");
            }
            return (D)v29;
        }
        return $this$enhanceSignature;
    }

    private final boolean hasDefaultValueInAnnotation(ValueParameterDescriptor $this$hasDefaultValueInAnnotation, KotlinType type2) {
        boolean bl;
        AnnotationDefaultValue defaultValue = UtilKt.getDefaultValueFromAnnotation($this$hasDefaultValueInAnnotation);
        AnnotationDefaultValue annotationDefaultValue = defaultValue;
        if (annotationDefaultValue instanceof StringDefaultValue) {
            bl = UtilsKt.lexicalCastFrom(type2, ((StringDefaultValue)defaultValue).getValue()) != null;
        } else if (Intrinsics.areEqual(annotationDefaultValue, NullDefaultValue.INSTANCE)) {
            bl = TypeUtils.acceptsNullable(type2);
        } else if (annotationDefaultValue == null) {
            bl = $this$hasDefaultValueInAnnotation.declaresDefaultValue();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return bl && $this$hasDefaultValueInAnnotation.getOverriddenDescriptors().isEmpty();
    }

    /*
     * WARNING - void declaration
     */
    private final SignatureParts partsForValueParameter(CallableMemberDescriptor $this$partsForValueParameter, ValueParameterDescriptor parameterDescriptor, LazyJavaResolverContext methodContext, Function1<? super CallableMemberDescriptor, ? extends KotlinType> collector) {
        Object object;
        boolean bl;
        Annotated annotated;
        CallableMemberDescriptor callableMemberDescriptor;
        SignatureEnhancement signatureEnhancement;
        block3: {
            block2: {
                void it;
                signatureEnhancement = this;
                callableMemberDescriptor = $this$partsForValueParameter;
                annotated = parameterDescriptor;
                bl = false;
                object = parameterDescriptor;
                if (object == null) break block2;
                ValueParameterDescriptor valueParameterDescriptor = object;
                boolean bl2 = false;
                boolean bl3 = false;
                ValueParameterDescriptor valueParameterDescriptor2 = valueParameterDescriptor;
                boolean bl4 = bl;
                Annotated annotated2 = annotated;
                CallableMemberDescriptor callableMemberDescriptor2 = callableMemberDescriptor;
                SignatureEnhancement signatureEnhancement2 = signatureEnhancement;
                boolean bl5 = false;
                LazyJavaResolverContext lazyJavaResolverContext = ContextKt.copyWithNewDefaultTypeQualifiers(methodContext, it.getAnnotations());
                signatureEnhancement = signatureEnhancement2;
                callableMemberDescriptor = callableMemberDescriptor2;
                annotated = annotated2;
                bl = bl4;
                object = lazyJavaResolverContext;
                if (object != null) break block3;
            }
            object = methodContext;
        }
        return signatureEnhancement.parts(callableMemberDescriptor, annotated, bl, (LazyJavaResolverContext)object, AnnotationTypeQualifierResolver.QualifierApplicabilityType.VALUE_PARAMETER, collector);
    }

    /*
     * WARNING - void declaration
     */
    private final SignatureParts parts(CallableMemberDescriptor $this$parts, Annotated typeContainer, boolean isCovariant, LazyJavaResolverContext containerContext, AnnotationTypeQualifierResolver.QualifierApplicabilityType containerApplicabilityType, Function1<? super CallableMemberDescriptor, ? extends KotlinType> collector) {
        Collection<KotlinType> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Collection<? extends CallableMemberDescriptor> collection2 = $this$parts.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(collection2, "this.overriddenDescriptors");
        Iterable iterable = collection2;
        KotlinType kotlinType = collector.invoke($this$parts);
        Annotated annotated = typeContainer;
        SignatureEnhancement signatureEnhancement = this;
        boolean $i$f$map = false;
        void var9_12 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            void v1 = it;
            Intrinsics.checkNotNullExpressionValue(v1, "it");
            KotlinType kotlinType2 = collector.invoke((CallableMemberDescriptor)v1);
            collection.add(kotlinType2);
        }
        collection = (List)destination$iv$iv;
        AnnotationTypeQualifierResolver.QualifierApplicabilityType qualifierApplicabilityType = containerApplicabilityType;
        LazyJavaResolverContext lazyJavaResolverContext = ContextKt.copyWithNewDefaultTypeQualifiers(containerContext, collector.invoke($this$parts).getAnnotations());
        boolean bl = isCovariant;
        Collection collection3 = collection;
        KotlinType kotlinType3 = kotlinType;
        Annotated annotated2 = annotated;
        SignatureEnhancement signatureEnhancement2 = signatureEnhancement;
        return signatureEnhancement2.new SignatureParts(annotated2, kotlinType3, collection3, bl, lazyJavaResolverContext, qualifierApplicabilityType);
    }

    public SignatureEnhancement(@NotNull AnnotationTypeQualifierResolver annotationTypeQualifierResolver, @NotNull Jsr305State jsr305State) {
        Intrinsics.checkNotNullParameter(annotationTypeQualifierResolver, "annotationTypeQualifierResolver");
        Intrinsics.checkNotNullParameter(jsr305State, "jsr305State");
        this.annotationTypeQualifierResolver = annotationTypeQualifierResolver;
        this.jsr305State = jsr305State;
    }

    private final class SignatureParts {
        private final Annotated typeContainer;
        private final KotlinType fromOverride;
        private final Collection<KotlinType> fromOverridden;
        private final boolean isCovariant;
        private final LazyJavaResolverContext containerContext;
        private final AnnotationTypeQualifierResolver.QualifierApplicabilityType containerApplicabilityType;

        private final boolean isForVarargParameter() {
            Annotated $this$safeAs$iv = this.typeContainer;
            boolean $i$f$safeAs = false;
            Annotated annotated = $this$safeAs$iv;
            if (!(annotated instanceof ValueParameterDescriptor)) {
                annotated = null;
            }
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor)annotated;
            return (valueParameterDescriptor != null ? valueParameterDescriptor.getVarargElementType() : null) != null;
        }

        @NotNull
        public final PartEnhancementResult enhance(@Nullable TypeEnhancementInfo predefined) {
            PartEnhancementResult partEnhancementResult;
            Function1<Integer, JavaTypeQualifiers> function1;
            boolean bl;
            Function1<Integer, JavaTypeQualifiers> qualifiers = this.computeIndexedQualifiersForOverride();
            TypeEnhancementInfo typeEnhancementInfo = predefined;
            if (typeEnhancementInfo != null) {
                TypeEnhancementInfo typeEnhancementInfo2 = typeEnhancementInfo;
                boolean bl2 = false;
                bl = false;
                TypeEnhancementInfo it = typeEnhancementInfo2;
                boolean bl3 = false;
                function1 = new Function1<Integer, JavaTypeQualifiers>(predefined, qualifiers){
                    final /* synthetic */ TypeEnhancementInfo $predefined$inlined;
                    final /* synthetic */ Function1 $qualifiers$inlined;
                    {
                        this.$predefined$inlined = typeEnhancementInfo;
                        this.$qualifiers$inlined = function1;
                        super(1);
                    }

                    @NotNull
                    public final JavaTypeQualifiers invoke(int index) {
                        JavaTypeQualifiers javaTypeQualifiers = this.$predefined$inlined.getMap().get(index);
                        if (javaTypeQualifiers == null) {
                            javaTypeQualifiers = (JavaTypeQualifiers)this.$qualifiers$inlined.invoke(index);
                        }
                        return javaTypeQualifiers;
                    }
                };
            } else {
                function1 = null;
            }
            Function1<Integer, JavaTypeQualifiers> qualifiersWithPredefined = function1;
            boolean containsFunctionN2 = TypeUtils.contains(this.fromOverride, enhance.containsFunctionN.1.INSTANCE);
            Function1<Integer, JavaTypeQualifiers> function12 = qualifiersWithPredefined;
            if (function12 == null) {
                function12 = qualifiers;
            }
            KotlinType kotlinType = TypeEnhancementKt.enhance(this.fromOverride, (Function1<? super Integer, JavaTypeQualifiers>)function12);
            if (kotlinType != null) {
                KotlinType kotlinType2 = kotlinType;
                bl = false;
                boolean bl4 = false;
                KotlinType enhanced = kotlinType2;
                boolean bl5 = false;
                partEnhancementResult = new PartEnhancementResult(enhanced, true, containsFunctionN2);
            } else {
                partEnhancementResult = new PartEnhancementResult(this.fromOverride, false, containsFunctionN2);
            }
            return partEnhancementResult;
        }

        public static /* synthetic */ PartEnhancementResult enhance$default(SignatureParts signatureParts, TypeEnhancementInfo typeEnhancementInfo, int n, Object object) {
            if ((n & 1) != 0) {
                typeEnhancementInfo = null;
            }
            return signatureParts.enhance(typeEnhancementInfo);
        }

        /*
         * WARNING - void declaration
         */
        private final JavaTypeQualifiers extractQualifiers(KotlinType $this$extractQualifiers) {
            void lower;
            Pair<SimpleType, SimpleType> pair;
            if (FlexibleTypesKt.isFlexible($this$extractQualifiers)) {
                FlexibleType flexibleType = FlexibleTypesKt.asFlexibleType($this$extractQualifiers);
                boolean bl = false;
                boolean bl2 = false;
                FlexibleType it = flexibleType;
                boolean bl3 = false;
                pair = new Pair<SimpleType, SimpleType>(it.getLowerBound(), it.getUpperBound());
            } else {
                pair = new Pair<SimpleType, SimpleType>((SimpleType)$this$extractQualifiers, (SimpleType)$this$extractQualifiers);
            }
            Pair<SimpleType, SimpleType> pair2 = pair;
            KotlinType kotlinType = pair2.component1();
            KotlinType upper = pair2.component2();
            JavaToKotlinClassMap mapping = JavaToKotlinClassMap.INSTANCE;
            return new JavaTypeQualifiers(lower.isMarkedNullable() ? NullabilityQualifier.NULLABLE : (!upper.isMarkedNullable() ? NullabilityQualifier.NOT_NULL : null), mapping.isReadOnly((KotlinType)lower) ? MutabilityQualifier.READ_ONLY : (mapping.isMutable(upper) ? MutabilityQualifier.MUTABLE : null), $this$extractQualifiers.unwrap() instanceof NotNullTypeParameter, false, 8, null);
        }

        private final JavaTypeQualifiers extractQualifiersFromAnnotations(KotlinType $this$extractQualifiersFromAnnotations, boolean isHeadTypeConstructor, JavaTypeQualifiers defaultQualifiersForType) {
            NullabilityQualifierWithMigrationStatus nullabilityInfo;
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus;
            JavaTypeQualifiers defaultTypeQualifier;
            JavaTypeQualifiers javaTypeQualifiers;
            Annotations composedAnnotation = isHeadTypeConstructor && this.typeContainer != null ? AnnotationsKt.composeAnnotations(this.typeContainer.getAnnotations(), $this$extractQualifiersFromAnnotations.getAnnotations()) : $this$extractQualifiersFromAnnotations.getAnnotations();
            Function2 $fun$ifPresent$1 = new Function2<List<? extends FqName>, T, T>(composedAnnotation){
                final /* synthetic */ Annotations $composedAnnotation;

                @Nullable
                public final <T> T invoke(@NotNull List<FqName> $this$ifPresent, @NotNull T qualifier) {
                    boolean bl;
                    block3: {
                        Intrinsics.checkNotNullParameter($this$ifPresent, "$this$ifPresent");
                        Intrinsics.checkNotNullParameter(qualifier, "qualifier");
                        Iterable $this$any$iv = $this$ifPresent;
                        boolean $i$f$any = false;
                        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                            bl = false;
                        } else {
                            for (T element$iv : $this$any$iv) {
                                FqName it = (FqName)element$iv;
                                boolean bl2 = false;
                                if (!(this.$composedAnnotation.findAnnotation(it) != null)) continue;
                                bl = true;
                                break block3;
                            }
                            bl = false;
                        }
                    }
                    return (T)(bl ? qualifier : null);
                }
                {
                    this.$composedAnnotation = annotations2;
                    super(2);
                }
            };
            extractQualifiersFromAnnotations.2 $fun$uniqueNotNull$2 = extractQualifiersFromAnnotations.2.INSTANCE;
            if (isHeadTypeConstructor) {
                JavaTypeQualifiersByElementType javaTypeQualifiersByElementType = this.containerContext.getDefaultTypeQualifiers();
                javaTypeQualifiers = javaTypeQualifiersByElementType != null ? javaTypeQualifiersByElementType.get(this.containerApplicabilityType) : null;
            } else {
                javaTypeQualifiers = defaultTypeQualifier = defaultQualifiersForType;
            }
            if ((nullabilityQualifierWithMigrationStatus = this.extractNullability(composedAnnotation)) == null) {
                Object object = defaultTypeQualifier;
                if (object != null && (object = object.getNullability()) != null) {
                    Object object2 = object;
                    boolean bl = false;
                    boolean bl2 = false;
                    Object it = object2;
                    boolean bl3 = false;
                    nullabilityQualifierWithMigrationStatus = new NullabilityQualifierWithMigrationStatus(defaultTypeQualifier.getNullability(), defaultTypeQualifier.isNullabilityQualifierForWarning());
                } else {
                    nullabilityQualifierWithMigrationStatus = null;
                }
            }
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus2 = nullabilityInfo = nullabilityQualifierWithMigrationStatus;
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus3 = nullabilityInfo;
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus4 = nullabilityInfo;
            return new JavaTypeQualifiers(nullabilityQualifierWithMigrationStatus2 != null ? nullabilityQualifierWithMigrationStatus2.getQualifier() : null, $fun$uniqueNotNull$2.invoke($fun$ifPresent$1.invoke(JvmAnnotationNamesKt.getREAD_ONLY_ANNOTATIONS(), MutabilityQualifier.READ_ONLY), $fun$ifPresent$1.invoke(JvmAnnotationNamesKt.getMUTABLE_ANNOTATIONS(), MutabilityQualifier.MUTABLE)), (nullabilityQualifierWithMigrationStatus3 != null ? nullabilityQualifierWithMigrationStatus3.getQualifier() : null) == NullabilityQualifier.NOT_NULL && TypeUtilsKt.isTypeParameter($this$extractQualifiersFromAnnotations), nullabilityQualifierWithMigrationStatus4 != null && nullabilityQualifierWithMigrationStatus4.isForWarningOnly());
        }

        /*
         * WARNING - void declaration
         */
        private final NullabilityQualifierWithMigrationStatus extractNullability(Annotations $this$extractNullability) {
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus;
            block1: {
                void $this$firstNotNullResult$iv;
                Iterable iterable = $this$extractNullability;
                SignatureEnhancement signatureEnhancement = SignatureEnhancement.this;
                boolean $i$f$firstNotNullResult = false;
                for (Object element$iv : $this$firstNotNullResult$iv) {
                    AnnotationDescriptor p1 = (AnnotationDescriptor)element$iv;
                    boolean bl = false;
                    NullabilityQualifierWithMigrationStatus result$iv = signatureEnhancement.extractNullability(p1);
                    if (result$iv == null) continue;
                    nullabilityQualifierWithMigrationStatus = result$iv;
                    break block1;
                }
                nullabilityQualifierWithMigrationStatus = null;
            }
            return nullabilityQualifierWithMigrationStatus;
        }

        /*
         * Unable to fully structure code
         */
        private final Function1<Integer, JavaTypeQualifiers> computeIndexedQualifiersForOverride() {
            block9: {
                $this$map$iv = this.fromOverridden;
                $i$f$map = false;
                var4_3 = $this$map$iv;
                destination$iv$iv = (JavaTypeQualifiers[])new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    var9_18 = (KotlinType)item$iv$iv;
                    var32_13 = destination$iv$iv;
                    $i$a$-map-SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$indexedFromSupertypes$1 = false;
                    var33_14 = this.toIndexed((KotlinType)it);
                    var32_13.add(var33_14);
                }
                indexedFromSupertypes = (List)destination$iv$iv;
                indexedThisType = this.toIndexed(this.fromOverride);
                if (!this.isCovariant) ** GOTO lbl-1000
                $this$any$iv = this.fromOverridden;
                $i$f$any = false;
                if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                    v0 = false;
                } else {
                    for (T element$iv : $this$any$iv) {
                        it = (KotlinType)element$iv;
                        $i$a$-any-SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$onlyHeadTypeConstructor$1 = false;
                        if (!(KotlinTypeChecker.DEFAULT.equalTypes(it, this.fromOverride) == false)) continue;
                        v0 = true;
                        break block9;
                    }
                    v0 = false;
                }
            }
            if (v0) {
                v1 = true;
            } else lbl-1000:
            // 2 sources

            {
                v1 = false;
            }
            onlyHeadTypeConstructor = v1;
            treeSize = onlyHeadTypeConstructor != false ? 1 : indexedThisType.size();
            var6_10 = new JavaTypeQualifiers[treeSize];
            for (var7_12 = 0; var7_12 < treeSize; ++var7_12) {
                it = var7_12;
                var33_15 = var7_12;
                var32_13 = var6_10;
                $i$a$-<init>-SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$computedResult$1 = false;
                isHeadTypeConstructor = index == false;
                var11_24 = isHeadTypeConstructor != false || onlyHeadTypeConstructor == false;
                var12_26 = false;
                if (_Assertions.ENABLED && !var11_24) {
                    $i$a$-assert-SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$computedResult$1$1 = false;
                    $i$a$-assert-SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$computedResult$1$1 = "Only head type constructors should be computed";
                    throw (Throwable)new AssertionError((Object)$i$a$-assert-SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$computedResult$1$1);
                }
                $i$a$-assert-SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$computedResult$1$1 = indexedThisType.get((int)index);
                var11_23 = $i$a$-assert-SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$computedResult$1$1.component1();
                defaultQualifiers = $i$a$-assert-SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$computedResult$1$1.component2();
                $this$mapNotNull$iv = indexedFromSupertypes;
                $i$f$mapNotNull = false;
                var16_32 = $this$mapNotNull$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$mapNotNullTo = false;
                $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                $i$f$forEach = false;
                var21_37 = $this$forEach$iv$iv$iv.iterator();
                while (var21_37.hasNext()) {
                    element$iv$iv = element$iv$iv$iv = var21_37.next();
                    $i$a$-forEach-CollectionsKt___CollectionsKt$mapNotNullTo$1$iv$iv = false;
                    it = (List)element$iv$iv;
                    $i$a$-mapNotNull-SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$computedResult$1$verticalSlice$1 = false;
                    v2 = (TypeAndDefaultQualifiers)CollectionsKt.getOrNull(it, (int)index);
                    if ((v2 != null ? v2.getType() : null) == null) continue;
                    var27_43 = var27_43;
                    var28_44 = false;
                    var29_45 = false;
                    it$iv$iv = var27_43;
                    $i$a$-let-CollectionsKt___CollectionsKt$mapNotNullTo$1$1$iv$iv = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                verticalSlice = (List)destination$iv$iv;
                var32_13[var33_15] = var34_48 = this.computeQualifiersForOverride((KotlinType)qualifiers, verticalSlice, defaultQualifiers, isHeadTypeConstructor);
            }
            computedResult = var6_10;
            return new Function1<Integer, JavaTypeQualifiers>(computedResult){
                final /* synthetic */ JavaTypeQualifiers[] $computedResult;

                @NotNull
                public final JavaTypeQualifiers invoke(int index) {
                    JavaTypeQualifiers javaTypeQualifiers;
                    JavaTypeQualifiers[] javaTypeQualifiersArray = this.$computedResult;
                    boolean bl = false;
                    if (index >= 0 && index <= ArraysKt.getLastIndex(javaTypeQualifiersArray)) {
                        javaTypeQualifiers = javaTypeQualifiersArray[index];
                    } else {
                        int it = index;
                        boolean bl2 = false;
                        javaTypeQualifiers = JavaTypeQualifiers.Companion.getNONE();
                    }
                    return javaTypeQualifiers;
                }
                {
                    this.$computedResult = javaTypeQualifiersArray;
                    super(1);
                }
            };
        }

        private final List<TypeAndDefaultQualifiers> toIndexed(KotlinType $this$toIndexed) {
            ArrayList list = new ArrayList(1);
            Function2<KotlinType, LazyJavaResolverContext, Unit> $fun$add$1 = new Function2<KotlinType, LazyJavaResolverContext, Unit>(list){
                final /* synthetic */ ArrayList $list;

                public final void invoke(@NotNull KotlinType type2, @NotNull LazyJavaResolverContext ownerContext) {
                    Intrinsics.checkNotNullParameter(type2, "type");
                    Intrinsics.checkNotNullParameter(ownerContext, "ownerContext");
                    LazyJavaResolverContext c = ContextKt.copyWithNewDefaultTypeQualifiers(ownerContext, type2.getAnnotations());
                    JavaTypeQualifiersByElementType javaTypeQualifiersByElementType = c.getDefaultTypeQualifiers();
                    this.$list.add(new TypeAndDefaultQualifiers(type2, javaTypeQualifiersByElementType != null ? javaTypeQualifiersByElementType.get(AnnotationTypeQualifierResolver.QualifierApplicabilityType.TYPE_USE) : null));
                    for (TypeProjection arg : type2.getArguments()) {
                        if (arg.isStarProjection()) {
                            KotlinType kotlinType = arg.getType();
                            Intrinsics.checkNotNullExpressionValue(kotlinType, "arg.type");
                            this.$list.add(new TypeAndDefaultQualifiers(kotlinType, null));
                            continue;
                        }
                        KotlinType kotlinType = arg.getType();
                        Intrinsics.checkNotNullExpressionValue(kotlinType, "arg.type");
                        this.invoke(kotlinType, c);
                    }
                }
                {
                    this.$list = arrayList;
                    super(2);
                }
            };
            $fun$add$1.invoke($this$toIndexed, this.containerContext);
            return list;
        }

        /*
         * Unable to fully structure code
         * Could not resolve type clashes
         */
        private final JavaTypeQualifiers computeQualifiersForOverride(KotlinType $this$computeQualifiersForOverride, Collection<? extends KotlinType> fromSupertypes, JavaTypeQualifiers defaultQualifiersForType, boolean isHeadTypeConstructor) {
            block12: {
                $this$map$iv = fromSupertypes;
                $i$f$map = false;
                var8_8 = $this$map$iv;
                destination$iv$iv /* !! */  = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    var13_25 = (KotlinType)item$iv$iv;
                    var27_34 = destination$iv$iv /* !! */ ;
                    $i$a$-map-SignatureEnhancement$SignatureParts$computeQualifiersForOverride$superQualifiers$1 = false;
                    var28_35 = this.extractQualifiers((KotlinType)it);
                    var27_34.add(var28_35);
                }
                superQualifiers = (List)destination$iv$iv /* !! */ ;
                $this$mapNotNull$iv = superQualifiers;
                $i$f$mapNotNull = false;
                destination$iv$iv /* !! */  = $this$mapNotNull$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$mapNotNullTo = false;
                $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                $i$f$forEach = false;
                for (Object element$iv$iv$iv : $this$forEach$iv$iv$iv) {
                    element$iv$iv = element$iv$iv$iv;
                    $i$a$-forEach-CollectionsKt___CollectionsKt$mapNotNullTo$1$iv$iv = false;
                    it = (JavaTypeQualifiers)element$iv$iv;
                    $i$a$-mapNotNull-SignatureEnhancement$SignatureParts$computeQualifiersForOverride$mutabilityFromSupertypes$1 = false;
                    if (it.getMutability() == null) continue;
                    var21_61 = false;
                    var22_65 = false;
                    it$iv$iv = var20_57;
                    $i$a$-let-CollectionsKt___CollectionsKt$mapNotNullTo$1$1$iv$iv = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                mutabilityFromSupertypes = CollectionsKt.toSet((List)destination$iv$iv);
                $this$mapNotNull$iv = superQualifiers;
                $i$f$mapNotNull = false;
                destination$iv$iv = $this$mapNotNull$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$mapNotNullTo = false;
                $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                $i$f$forEach = false;
                for (Object element$iv$iv$iv : $this$forEach$iv$iv$iv) {
                    element$iv$iv = element$iv$iv$iv;
                    $i$a$-forEach-CollectionsKt___CollectionsKt$mapNotNullTo$1$iv$iv = false;
                    it = (JavaTypeQualifiers)element$iv$iv;
                    $i$a$-mapNotNull-SignatureEnhancement$SignatureParts$computeQualifiersForOverride$nullabilityFromSupertypes$1 = false;
                    if (it.getNullability() == null) continue;
                    var22_65 = false;
                    var23_69 = false;
                    it$iv$iv = var21_62;
                    $i$a$-let-CollectionsKt___CollectionsKt$mapNotNullTo$1$1$iv$iv = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                nullabilityFromSupertypes = CollectionsKt.toSet((List)destination$iv$iv);
                $this$mapNotNull$iv = fromSupertypes;
                $i$f$mapNotNull = false;
                destination$iv$iv = $this$mapNotNull$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$mapNotNullTo = false;
                $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                $i$f$forEach = false;
                element$iv$iv$iv = $this$forEach$iv$iv$iv.iterator();
                while (element$iv$iv$iv.hasNext()) {
                    element$iv$iv = element$iv$iv$iv = element$iv$iv$iv.next();
                    $i$a$-forEach-CollectionsKt___CollectionsKt$mapNotNullTo$1$iv$iv = false;
                    it = (KotlinType)element$iv$iv;
                    $i$a$-mapNotNull-SignatureEnhancement$SignatureParts$computeQualifiersForOverride$nullabilityFromSupertypesWithWarning$1 = false;
                    if (this.extractQualifiers(TypeWithEnhancementKt.unwrapEnhancement(it)).getNullability() == null) continue;
                    var23_70 = false;
                    var24_73 = false;
                    it$iv$iv = var22_66;
                    $i$a$-let-CollectionsKt___CollectionsKt$mapNotNullTo$1$1$iv$iv = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                nullabilityFromSupertypesWithWarning = CollectionsKt.toSet((List)destination$iv$iv);
                $this$mapNotNullTo$iv$iv = own = this.extractQualifiersFromAnnotations($this$computeQualifiersForOverride, isHeadTypeConstructor, defaultQualifiersForType);
                destination$iv$iv = false;
                $i$f$mapNotNullTo = false;
                it /* !! */  = $this$mapNotNullTo$iv$iv;
                $i$a$-takeIf-SignatureEnhancement$SignatureParts$computeQualifiersForOverride$ownNullability$1 = false;
                v0 = it /* !! */ .isNullabilityQualifierForWarning() == false != false ? $this$mapNotNullTo$iv$iv : null;
                ownNullability = v0 != null ? v0.getNullability() : null;
                ownNullabilityForWarning = own.getNullability();
                isCovariantPosition = this.isCovariant != false && isHeadTypeConstructor != false;
                v1 = SignatureEnhancementKt.select(nullabilityFromSupertypes, ownNullability, isCovariantPosition);
                if (v1 != null) {
                    it /* !! */  = v1;
                    $i$a$-takeIf-SignatureEnhancement$SignatureParts$computeQualifiersForOverride$ownNullability$1 = false;
                    element$iv$iv$iv = false;
                    it /* !! */  = it /* !! */ ;
                    $i$a$-takeUnless-SignatureEnhancement$SignatureParts$computeQualifiersForOverride$nullability$1 = false;
                    v2 /* !! */  = !(this.isForVarargParameter() != false && isHeadTypeConstructor != false && it /* !! */  == NullabilityQualifier.NULLABLE) ? it /* !! */  : null;
                } else {
                    v2 /* !! */  = null;
                }
                nullability = v2 /* !! */ ;
                mutability = SignatureEnhancementKt.select(mutabilityFromSupertypes, MutabilityQualifier.MUTABLE, MutabilityQualifier.READ_ONLY, own.getMutability(), isCovariantPosition);
                v3 = canChange = ownNullabilityForWarning != ownNullability || (Intrinsics.areEqual(nullabilityFromSupertypesWithWarning, nullabilityFromSupertypes) ^ true) != false;
                if (own.isNotNullTypeParameter()) ** GOTO lbl-1000
                $this$any$iv = superQualifiers;
                $i$f$any = false;
                if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                    v4 = false;
                } else {
                    for (T element$iv : $this$any$iv) {
                        it = (JavaTypeQualifiers)element$iv;
                        $i$a$-any-SignatureEnhancement$SignatureParts$computeQualifiersForOverride$isAnyNonNullTypeParameter$1 = false;
                        if (!it.isNotNullTypeParameter()) continue;
                        v4 = true;
                        break block12;
                    }
                    v4 = false;
                }
            }
            if (v4) lbl-1000:
            // 2 sources

            {
                v5 = true;
            } else {
                v5 = isAnyNonNullTypeParameter = false;
            }
            if (nullability == null && canChange) {
                nullabilityWithWarning = SignatureEnhancementKt.select(nullabilityFromSupertypesWithWarning, ownNullabilityForWarning, isCovariantPosition);
                return SignatureEnhancementKt.createJavaTypeQualifiers(nullabilityWithWarning, mutability, true, isAnyNonNullTypeParameter);
            }
            return SignatureEnhancementKt.createJavaTypeQualifiers((NullabilityQualifier)nullability, mutability, nullability == null, isAnyNonNullTypeParameter);
        }

        public SignatureParts(@NotNull Annotated typeContainer, @NotNull KotlinType fromOverride, Collection<? extends KotlinType> fromOverridden, @NotNull boolean isCovariant, @NotNull LazyJavaResolverContext containerContext, AnnotationTypeQualifierResolver.QualifierApplicabilityType containerApplicabilityType) {
            Intrinsics.checkNotNullParameter(fromOverride, "fromOverride");
            Intrinsics.checkNotNullParameter(fromOverridden, "fromOverridden");
            Intrinsics.checkNotNullParameter(containerContext, "containerContext");
            Intrinsics.checkNotNullParameter((Object)containerApplicabilityType, "containerApplicabilityType");
            this.typeContainer = typeContainer;
            this.fromOverride = fromOverride;
            this.fromOverridden = fromOverridden;
            this.isCovariant = isCovariant;
            this.containerContext = containerContext;
            this.containerApplicabilityType = containerApplicabilityType;
        }
    }

    private static class PartEnhancementResult {
        @NotNull
        private final KotlinType type;
        private final boolean wereChanges;
        private final boolean containsFunctionN;

        @NotNull
        public final KotlinType getType() {
            return this.type;
        }

        public final boolean getWereChanges() {
            return this.wereChanges;
        }

        public final boolean getContainsFunctionN() {
            return this.containsFunctionN;
        }

        public PartEnhancementResult(@NotNull KotlinType type2, boolean wereChanges, boolean containsFunctionN2) {
            Intrinsics.checkNotNullParameter(type2, "type");
            this.type = type2;
            this.wereChanges = wereChanges;
            this.containsFunctionN = containsFunctionN2;
        }
    }

    private static final class ValueParameterEnhancementResult
    extends PartEnhancementResult {
        private final boolean hasDefaultValue;

        public final boolean getHasDefaultValue() {
            return this.hasDefaultValue;
        }

        public ValueParameterEnhancementResult(@NotNull KotlinType type2, boolean hasDefaultValue, boolean wereChanges, boolean containsFunctionN2) {
            Intrinsics.checkNotNullParameter(type2, "type");
            super(type2, wereChanges, containsFunctionN2);
            this.hasDefaultValue = hasDefaultValue;
        }
    }
}

