/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolverKt;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.NullabilityQualifierWithApplicability;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.utils.Jsr305State;
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AnnotationTypeQualifierResolver {
    private final MemoizedFunctionToNullable<ClassDescriptor, AnnotationDescriptor> resolvedNicknames;
    private final boolean disabled;
    private final Jsr305State jsr305State;

    /*
     * WARNING - void declaration
     */
    private final AnnotationDescriptor computeTypeQualifierNickname(ClassDescriptor classDescriptor) {
        AnnotationDescriptor annotationDescriptor;
        block2: {
            void $this$firstNotNullResult$iv;
            if (!classDescriptor.getAnnotations().hasAnnotation(AnnotationTypeQualifierResolverKt.getTYPE_QUALIFIER_NICKNAME_FQNAME())) {
                return null;
            }
            Iterable iterable = classDescriptor.getAnnotations();
            AnnotationTypeQualifierResolver annotationTypeQualifierResolver = this;
            boolean $i$f$firstNotNullResult = false;
            for (Object element$iv : $this$firstNotNullResult$iv) {
                AnnotationDescriptor p1 = (AnnotationDescriptor)element$iv;
                boolean bl = false;
                AnnotationDescriptor result$iv = annotationTypeQualifierResolver.resolveTypeQualifierAnnotation(p1);
                if (result$iv == null) continue;
                annotationDescriptor = result$iv;
                break block2;
            }
            annotationDescriptor = null;
        }
        return annotationDescriptor;
    }

    private final AnnotationDescriptor resolveTypeQualifierNickname(ClassDescriptor classDescriptor) {
        if (classDescriptor.getKind() != ClassKind.ANNOTATION_CLASS) {
            return null;
        }
        return (AnnotationDescriptor)this.resolvedNicknames.invoke(classDescriptor);
    }

    @Nullable
    public final AnnotationDescriptor resolveTypeQualifierAnnotation(@NotNull AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        if (this.jsr305State.getDisabled()) {
            return null;
        }
        ClassDescriptor classDescriptor = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
        if (classDescriptor == null) {
            return null;
        }
        ClassDescriptor annotationClass = classDescriptor;
        if (AnnotationTypeQualifierResolverKt.access$isAnnotatedWithTypeQualifier$p(annotationClass)) {
            return annotationDescriptor;
        }
        return this.resolveTypeQualifierNickname(annotationClass);
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public final NullabilityQualifierWithApplicability resolveQualifierBuiltInDefaultAnnotation(@NotNull AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        if (this.jsr305State.getDisabled()) {
            return null;
        }
        Object object = AnnotationTypeQualifierResolverKt.getBUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS();
        FqName fqName2 = annotationDescriptor.getFqName();
        boolean bl = false;
        NullabilityQualifierWithApplicability nullabilityQualifierWithApplicability = object.get(fqName2);
        if (nullabilityQualifierWithApplicability != null) {
            void qualifier;
            object = nullabilityQualifierWithApplicability;
            boolean bl2 = false;
            bl = false;
            Object $dstr$qualifier$applicability = object;
            boolean bl3 = false;
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus = ((NullabilityQualifierWithApplicability)$dstr$qualifier$applicability).component1();
            Collection<QualifierApplicabilityType> applicability = ((NullabilityQualifierWithApplicability)$dstr$qualifier$applicability).component2();
            ReportLevel reportLevel = this.resolveJsr305AnnotationState(annotationDescriptor);
            boolean bl4 = false;
            boolean bl5 = false;
            ReportLevel it = reportLevel;
            boolean bl6 = false;
            ReportLevel reportLevel2 = it != ReportLevel.IGNORE ? reportLevel : null;
            if (reportLevel2 == null) {
                return null;
            }
            ReportLevel state = reportLevel2;
            return new NullabilityQualifierWithApplicability(NullabilityQualifierWithMigrationStatus.copy$default((NullabilityQualifierWithMigrationStatus)qualifier, null, state.isWarning(), 1, null), applicability);
        }
        return null;
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public final TypeQualifierWithApplicability resolveTypeQualifierDefaultAnnotation(@NotNull AnnotationDescriptor annotationDescriptor) {
        Object v3;
        int elementTypesMask;
        block8: {
            void $this$fold$iv;
            void $this$flatMapTo$iv$iv;
            Object it;
            ClassDescriptor classDescriptor;
            block10: {
                block9: {
                    Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
                    if (this.jsr305State.getDisabled()) {
                        return null;
                    }
                    classDescriptor = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
                    if (classDescriptor == null) break block9;
                    ClassDescriptor classDescriptor2 = classDescriptor;
                    boolean bl = false;
                    boolean bl2 = false;
                    it = classDescriptor2;
                    boolean bl3 = false;
                    classDescriptor = it.getAnnotations().hasAnnotation(AnnotationTypeQualifierResolverKt.getTYPE_QUALIFIER_DEFAULT_FQNAME()) ? classDescriptor2 : null;
                    if (classDescriptor != null) break block10;
                }
                return null;
            }
            ClassDescriptor typeQualifierDefaultAnnotatedClass = classDescriptor;
            ClassDescriptor classDescriptor3 = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
            Intrinsics.checkNotNull(classDescriptor3);
            AnnotationDescriptor annotationDescriptor2 = classDescriptor3.getAnnotations().findAnnotation(AnnotationTypeQualifierResolverKt.getTYPE_QUALIFIER_DEFAULT_FQNAME());
            Intrinsics.checkNotNull(annotationDescriptor2);
            Object $this$flatMap$iv = annotationDescriptor2.getAllValueArguments();
            boolean $i$f$flatMap = false;
            it = $this$flatMap$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$flatMapTo = false;
            void var9_17 = $this$flatMapTo$iv$iv;
            boolean bl = false;
            Iterator iterator2 = var9_17.entrySet().iterator();
            while (iterator2.hasNext()) {
                void parameter;
                Map.Entry element$iv$iv;
                Map.Entry $dstr$parameter$argument = element$iv$iv = iterator2.next();
                boolean bl4 = false;
                Map.Entry entry = $dstr$parameter$argument;
                boolean bl5 = false;
                Name name = (Name)entry.getKey();
                entry = $dstr$parameter$argument;
                bl5 = false;
                ConstantValue argument = (ConstantValue)entry.getValue();
                Iterable list$iv$iv = Intrinsics.areEqual(parameter, JvmAnnotationNames.DEFAULT_ANNOTATION_MEMBER_NAME) ? this.mapConstantToQualifierApplicabilityTypes(argument) : CollectionsKt.emptyList();
                CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
            }
            $this$flatMap$iv = (List)destination$iv$iv;
            int initial$iv = 0;
            boolean $i$f$fold = false;
            int accumulator$iv = initial$iv;
            for (Object element$iv : $this$fold$iv) {
                void applicabilityType;
                QualifierApplicabilityType qualifierApplicabilityType = (QualifierApplicabilityType)((Object)element$iv);
                int acc = accumulator$iv;
                boolean bl6 = false;
                accumulator$iv = acc | 1 << applicabilityType.ordinal();
            }
            elementTypesMask = accumulator$iv;
            Iterable $this$firstOrNull$iv = typeQualifierDefaultAnnotatedClass.getAnnotations();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                AnnotationDescriptor it2 = (AnnotationDescriptor)element$iv;
                boolean bl7 = false;
                if (!(this.resolveTypeQualifierAnnotation(it2) != null)) continue;
                v3 = element$iv;
                break block8;
            }
            v3 = null;
        }
        AnnotationDescriptor annotationDescriptor3 = v3;
        if (annotationDescriptor3 == null) {
            return null;
        }
        AnnotationDescriptor typeQualifier = annotationDescriptor3;
        return new TypeQualifierWithApplicability(typeQualifier, elementTypesMask);
    }

    @NotNull
    public final ReportLevel resolveJsr305AnnotationState(@NotNull AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        ReportLevel reportLevel = this.resolveJsr305CustomState(annotationDescriptor);
        if (reportLevel != null) {
            ReportLevel reportLevel2 = reportLevel;
            boolean bl = false;
            boolean bl2 = false;
            ReportLevel it = reportLevel2;
            boolean bl3 = false;
            return it;
        }
        return this.jsr305State.getGlobal();
    }

    @Nullable
    public final ReportLevel resolveJsr305CustomState(@NotNull AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        Object object = this.jsr305State.getUser();
        FqName fqName2 = annotationDescriptor.getFqName();
        String string = fqName2 != null ? fqName2.asString() : null;
        boolean bl = false;
        ReportLevel reportLevel = object.get(string);
        if (reportLevel != null) {
            object = reportLevel;
            boolean bl2 = false;
            bl = false;
            Object it = object;
            boolean bl3 = false;
            return it;
        }
        ClassDescriptor classDescriptor = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
        return classDescriptor != null ? this.migrationAnnotationStatus(classDescriptor) : null;
    }

    private final ReportLevel migrationAnnotationStatus(ClassDescriptor $this$migrationAnnotationStatus) {
        ReportLevel reportLevel;
        AnnotationDescriptor annotationDescriptor = $this$migrationAnnotationStatus.getAnnotations().findAnnotation(AnnotationTypeQualifierResolverKt.getMIGRATION_ANNOTATION_FQNAME());
        ConstantValue<Object> constantValue = annotationDescriptor != null ? DescriptorUtilsKt.firstArgument(annotationDescriptor) : null;
        if (!(constantValue instanceof EnumValue)) {
            constantValue = null;
        }
        EnumValue enumValue = (EnumValue)constantValue;
        if (enumValue == null) {
            return null;
        }
        EnumValue enumValue2 = enumValue;
        ReportLevel reportLevel2 = this.jsr305State.getMigration();
        if (reportLevel2 != null) {
            ReportLevel reportLevel3 = reportLevel2;
            boolean bl = false;
            boolean bl2 = false;
            ReportLevel it = reportLevel3;
            boolean bl3 = false;
            return it;
        }
        switch (enumValue2.getEnumEntryName().asString()) {
            case "STRICT": {
                reportLevel = ReportLevel.STRICT;
                break;
            }
            case "WARN": {
                reportLevel = ReportLevel.WARN;
                break;
            }
            case "IGNORE": {
                reportLevel = ReportLevel.IGNORE;
                break;
            }
            default: {
                reportLevel = null;
            }
        }
        return reportLevel;
    }

    /*
     * WARNING - void declaration
     */
    private final List<QualifierApplicabilityType> mapConstantToQualifierApplicabilityTypes(ConstantValue<?> $this$mapConstantToQualifierApplicabilityTypes) {
        List list;
        ConstantValue<?> constantValue = $this$mapConstantToQualifierApplicabilityTypes;
        if (constantValue instanceof ArrayValue) {
            void $this$flatMapTo$iv$iv;
            Iterable $this$flatMap$iv = (Iterable)((ArrayValue)$this$mapConstantToQualifierApplicabilityTypes).getValue();
            boolean $i$f$flatMap = false;
            Iterable iterable = $this$flatMap$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$flatMapTo = false;
            for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
                ConstantValue it = (ConstantValue)element$iv$iv;
                boolean bl = false;
                Iterable list$iv$iv = this.mapConstantToQualifierApplicabilityTypes(it);
                CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
            }
            list = (List)destination$iv$iv;
        } else if (constantValue instanceof EnumValue) {
            QualifierApplicabilityType qualifierApplicabilityType;
            switch (((EnumValue)$this$mapConstantToQualifierApplicabilityTypes).getEnumEntryName().getIdentifier()) {
                case "METHOD": {
                    qualifierApplicabilityType = QualifierApplicabilityType.METHOD_RETURN_TYPE;
                    break;
                }
                case "FIELD": {
                    qualifierApplicabilityType = QualifierApplicabilityType.FIELD;
                    break;
                }
                case "PARAMETER": {
                    qualifierApplicabilityType = QualifierApplicabilityType.VALUE_PARAMETER;
                    break;
                }
                case "TYPE_USE": {
                    qualifierApplicabilityType = QualifierApplicabilityType.TYPE_USE;
                    break;
                }
                default: {
                    qualifierApplicabilityType = null;
                }
            }
            list = CollectionsKt.listOfNotNull(qualifierApplicabilityType);
        } else {
            list = CollectionsKt.emptyList();
        }
        return list;
    }

    public final boolean getDisabled() {
        return this.disabled;
    }

    public AnnotationTypeQualifierResolver(@NotNull StorageManager storageManager, @NotNull Jsr305State jsr305State) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(jsr305State, "jsr305State");
        this.jsr305State = jsr305State;
        this.resolvedNicknames = storageManager.createMemoizedFunctionWithNullableValues((Function1)new Function1<ClassDescriptor, AnnotationDescriptor>(this){

            @Nullable
            public final AnnotationDescriptor invoke(@NotNull ClassDescriptor p1) {
                Intrinsics.checkNotNullParameter(p1, "p1");
                return AnnotationTypeQualifierResolver.access$computeTypeQualifierNickname((AnnotationTypeQualifierResolver)this.receiver, p1);
            }

            public final KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(AnnotationTypeQualifierResolver.class);
            }

            public final String getName() {
                return "computeTypeQualifierNickname";
            }

            public final String getSignature() {
                return "computeTypeQualifierNickname(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;";
            }
        });
        this.disabled = this.jsr305State.getDisabled();
    }

    public static final /* synthetic */ AnnotationDescriptor access$computeTypeQualifierNickname(AnnotationTypeQualifierResolver $this, ClassDescriptor classDescriptor) {
        return $this.computeTypeQualifierNickname(classDescriptor);
    }

    public static final class QualifierApplicabilityType
    extends Enum<QualifierApplicabilityType> {
        public static final /* enum */ QualifierApplicabilityType METHOD_RETURN_TYPE;
        public static final /* enum */ QualifierApplicabilityType VALUE_PARAMETER;
        public static final /* enum */ QualifierApplicabilityType FIELD;
        public static final /* enum */ QualifierApplicabilityType TYPE_USE;
        private static final /* synthetic */ QualifierApplicabilityType[] $VALUES;

        static {
            QualifierApplicabilityType[] qualifierApplicabilityTypeArray = new QualifierApplicabilityType[4];
            QualifierApplicabilityType[] qualifierApplicabilityTypeArray2 = qualifierApplicabilityTypeArray;
            qualifierApplicabilityTypeArray[0] = METHOD_RETURN_TYPE = new QualifierApplicabilityType();
            qualifierApplicabilityTypeArray[1] = VALUE_PARAMETER = new QualifierApplicabilityType();
            qualifierApplicabilityTypeArray[2] = FIELD = new QualifierApplicabilityType();
            qualifierApplicabilityTypeArray[3] = TYPE_USE = new QualifierApplicabilityType();
            $VALUES = qualifierApplicabilityTypeArray;
        }

        public static QualifierApplicabilityType[] values() {
            return (QualifierApplicabilityType[])$VALUES.clone();
        }

        public static QualifierApplicabilityType valueOf(String string) {
            return Enum.valueOf(QualifierApplicabilityType.class, string);
        }
    }

    public static final class TypeQualifierWithApplicability {
        private final AnnotationDescriptor typeQualifier;
        private final int applicability;

        @NotNull
        public final AnnotationDescriptor component1() {
            return this.typeQualifier;
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final List<QualifierApplicabilityType> component2() {
            void $this$filterTo$iv$iv;
            void $this$filter$iv;
            QualifierApplicabilityType[] qualifierApplicabilityTypeArray = QualifierApplicabilityType.values();
            TypeQualifierWithApplicability typeQualifierWithApplicability = this;
            boolean $i$f$filter = false;
            void var4_4 = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            void var7_7 = $this$filterTo$iv$iv;
            int n = ((void)var7_7).length;
            for (int i = 0; i < n; ++i) {
                void element$iv$iv;
                void p1 = element$iv$iv = var7_7[i];
                boolean bl = false;
                if (!typeQualifierWithApplicability.isApplicableTo((QualifierApplicabilityType)p1)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            return (List)destination$iv$iv;
        }

        private final boolean isApplicableTo(QualifierApplicabilityType elementType) {
            return this.isApplicableConsideringMask(QualifierApplicabilityType.TYPE_USE) || this.isApplicableConsideringMask(elementType);
        }

        private final boolean isApplicableConsideringMask(QualifierApplicabilityType elementType) {
            return (this.applicability & 1 << elementType.ordinal()) != 0;
        }

        public TypeQualifierWithApplicability(@NotNull AnnotationDescriptor typeQualifier, int applicability) {
            Intrinsics.checkNotNullParameter(typeQualifier, "typeQualifier");
            this.typeQualifier = typeQualifier;
            this.applicability = applicability;
        }
    }
}

