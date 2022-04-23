/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaDeprecatedAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaRetentionAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaTargetAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.PossiblyExternalAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationOwner;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JavaAnnotationMapper {
    private static final FqName JAVA_TARGET_FQ_NAME;
    private static final FqName JAVA_RETENTION_FQ_NAME;
    private static final FqName JAVA_DEPRECATED_FQ_NAME;
    private static final FqName JAVA_DOCUMENTED_FQ_NAME;
    private static final FqName JAVA_REPEATABLE_FQ_NAME;
    @NotNull
    private static final Name DEPRECATED_ANNOTATION_MESSAGE;
    @NotNull
    private static final Name TARGET_ANNOTATION_ALLOWED_TARGETS;
    @NotNull
    private static final Name RETENTION_ANNOTATION_VALUE;
    private static final Map<FqName, FqName> kotlinToJavaNameMap;
    @NotNull
    private static final Map<FqName, FqName> javaToKotlinNameMap;
    public static final JavaAnnotationMapper INSTANCE;

    @NotNull
    public final Name getDEPRECATED_ANNOTATION_MESSAGE$descriptors_jvm() {
        return DEPRECATED_ANNOTATION_MESSAGE;
    }

    @NotNull
    public final Name getTARGET_ANNOTATION_ALLOWED_TARGETS$descriptors_jvm() {
        return TARGET_ANNOTATION_ALLOWED_TARGETS;
    }

    @NotNull
    public final Name getRETENTION_ANNOTATION_VALUE$descriptors_jvm() {
        return RETENTION_ANNOTATION_VALUE;
    }

    @Nullable
    public final AnnotationDescriptor mapOrResolveJavaAnnotation(@NotNull JavaAnnotation annotation, @NotNull LazyJavaResolverContext c) {
        PossiblyExternalAnnotationDescriptor possiblyExternalAnnotationDescriptor;
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(c, "c");
        ClassId classId = annotation.getClassId();
        if (Intrinsics.areEqual(classId, ClassId.topLevel(JAVA_TARGET_FQ_NAME))) {
            possiblyExternalAnnotationDescriptor = new JavaTargetAnnotationDescriptor(annotation, c);
        } else if (Intrinsics.areEqual(classId, ClassId.topLevel(JAVA_RETENTION_FQ_NAME))) {
            possiblyExternalAnnotationDescriptor = new JavaRetentionAnnotationDescriptor(annotation, c);
        } else if (Intrinsics.areEqual(classId, ClassId.topLevel(JAVA_REPEATABLE_FQ_NAME))) {
            FqName fqName2 = KotlinBuiltIns.FQ_NAMES.repeatable;
            Intrinsics.checkNotNullExpressionValue(fqName2, "KotlinBuiltIns.FQ_NAMES.repeatable");
            possiblyExternalAnnotationDescriptor = new JavaAnnotationDescriptor(c, annotation, fqName2);
        } else if (Intrinsics.areEqual(classId, ClassId.topLevel(JAVA_DOCUMENTED_FQ_NAME))) {
            FqName fqName3 = KotlinBuiltIns.FQ_NAMES.mustBeDocumented;
            Intrinsics.checkNotNullExpressionValue(fqName3, "KotlinBuiltIns.FQ_NAMES.mustBeDocumented");
            possiblyExternalAnnotationDescriptor = new JavaAnnotationDescriptor(c, annotation, fqName3);
        } else {
            possiblyExternalAnnotationDescriptor = Intrinsics.areEqual(classId, ClassId.topLevel(JAVA_DEPRECATED_FQ_NAME)) ? null : (PossiblyExternalAnnotationDescriptor)new LazyJavaAnnotationDescriptor(c, annotation);
        }
        return possiblyExternalAnnotationDescriptor;
    }

    @Nullable
    public final AnnotationDescriptor findMappedJavaAnnotation(@NotNull FqName kotlinName, @NotNull JavaAnnotationOwner annotationOwner, @NotNull LazyJavaResolverContext c) {
        AnnotationDescriptor annotationDescriptor;
        JavaAnnotation javaAnnotation;
        Intrinsics.checkNotNullParameter(kotlinName, "kotlinName");
        Intrinsics.checkNotNullParameter(annotationOwner, "annotationOwner");
        Intrinsics.checkNotNullParameter(c, "c");
        if (Intrinsics.areEqual(kotlinName, KotlinBuiltIns.FQ_NAMES.deprecated) && ((javaAnnotation = annotationOwner.findAnnotation(JAVA_DEPRECATED_FQ_NAME)) != null || annotationOwner.isDeprecatedInJavaDoc())) {
            return new JavaDeprecatedAnnotationDescriptor(javaAnnotation, c);
        }
        FqName fqName2 = kotlinToJavaNameMap.get(kotlinName);
        if (fqName2 != null) {
            FqName fqName3 = fqName2;
            boolean bl = false;
            boolean bl2 = false;
            FqName javaName = fqName3;
            boolean bl3 = false;
            JavaAnnotation javaAnnotation2 = annotationOwner.findAnnotation(javaName);
            if (javaAnnotation2 != null) {
                JavaAnnotation javaAnnotation3 = javaAnnotation2;
                boolean bl4 = false;
                boolean bl5 = false;
                JavaAnnotation annotation = javaAnnotation3;
                boolean bl6 = false;
                annotationDescriptor = INSTANCE.mapOrResolveJavaAnnotation(annotation, c);
            } else {
                annotationDescriptor = null;
            }
        } else {
            annotationDescriptor = null;
        }
        return annotationDescriptor;
    }

    private JavaAnnotationMapper() {
    }

    static {
        JavaAnnotationMapper javaAnnotationMapper;
        INSTANCE = javaAnnotationMapper = new JavaAnnotationMapper();
        JAVA_TARGET_FQ_NAME = new FqName(Target.class.getCanonicalName());
        JAVA_RETENTION_FQ_NAME = new FqName(Retention.class.getCanonicalName());
        JAVA_DEPRECATED_FQ_NAME = new FqName(Deprecated.class.getCanonicalName());
        JAVA_DOCUMENTED_FQ_NAME = new FqName(Documented.class.getCanonicalName());
        JAVA_REPEATABLE_FQ_NAME = new FqName("java.lang.annotation.Repeatable");
        Name name = Name.identifier("message");
        Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(\"message\")");
        DEPRECATED_ANNOTATION_MESSAGE = name;
        Name name2 = Name.identifier("allowedTargets");
        Intrinsics.checkNotNullExpressionValue(name2, "Name.identifier(\"allowedTargets\")");
        TARGET_ANNOTATION_ALLOWED_TARGETS = name2;
        Name name3 = Name.identifier("value");
        Intrinsics.checkNotNullExpressionValue(name3, "Name.identifier(\"value\")");
        RETENTION_ANNOTATION_VALUE = name3;
        kotlinToJavaNameMap = MapsKt.mapOf(TuplesKt.to(KotlinBuiltIns.FQ_NAMES.target, JAVA_TARGET_FQ_NAME), TuplesKt.to(KotlinBuiltIns.FQ_NAMES.retention, JAVA_RETENTION_FQ_NAME), TuplesKt.to(KotlinBuiltIns.FQ_NAMES.repeatable, JAVA_REPEATABLE_FQ_NAME), TuplesKt.to(KotlinBuiltIns.FQ_NAMES.mustBeDocumented, JAVA_DOCUMENTED_FQ_NAME));
        javaToKotlinNameMap = MapsKt.mapOf(TuplesKt.to(JAVA_TARGET_FQ_NAME, KotlinBuiltIns.FQ_NAMES.target), TuplesKt.to(JAVA_RETENTION_FQ_NAME, KotlinBuiltIns.FQ_NAMES.retention), TuplesKt.to(JAVA_DEPRECATED_FQ_NAME, KotlinBuiltIns.FQ_NAMES.deprecated), TuplesKt.to(JAVA_REPEATABLE_FQ_NAME, KotlinBuiltIns.FQ_NAMES.repeatable), TuplesKt.to(JAVA_DOCUMENTED_FQ_NAME, KotlinBuiltIns.FQ_NAMES.mustBeDocumented));
    }
}

