/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.KotlinRetention;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.KotlinTarget;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaAnnotationTargetMapper;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaEnumValueAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JavaAnnotationTargetMapper {
    private static final Map<String, EnumSet<KotlinTarget>> targetNameLists;
    private static final Map<String, KotlinRetention> retentionNameList;
    public static final JavaAnnotationTargetMapper INSTANCE;

    @NotNull
    public final Set<KotlinTarget> mapJavaTargetArgumentByName(@Nullable String argumentName) {
        Map<String, EnumSet<KotlinTarget>> map2 = targetNameLists;
        boolean bl = false;
        EnumSet<KotlinTarget> enumSet = map2.get(argumentName);
        return enumSet != null ? (Set<KotlinTarget>)enumSet : SetsKt.emptySet();
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final ConstantValue<?> mapJavaTargetArguments$descriptors_jvm(@NotNull List<? extends JavaAnnotationArgument> arguments2) {
        void $this$mapTo$iv$iv;
        Object list$iv$iv;
        Iterable $this$flatMapTo$iv$iv;
        Iterable $this$filterIsInstanceTo$iv$iv;
        Intrinsics.checkNotNullParameter(arguments2, "arguments");
        Iterable $this$filterIsInstance$iv = arguments2;
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof JavaEnumValueAnnotationArgument)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$flatMap$iv = (List)destination$iv$iv;
        boolean $i$f$flatMap = false;
        $this$filterIsInstanceTo$iv$iv = $this$flatMap$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            JavaEnumValueAnnotationArgument it = (JavaEnumValueAnnotationArgument)element$iv$iv;
            boolean bl = false;
            Name name = it.getEntryName();
            list$iv$iv = INSTANCE.mapJavaTargetArgumentByName(name != null ? name.asString() : null);
            CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
        }
        Iterable $this$map$iv = (List)destination$iv$iv;
        boolean $i$f$map = false;
        $this$flatMapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void kotlinTarget;
            list$iv$iv = (KotlinTarget)((Object)item$iv$iv);
            Collection collection = destination$iv$iv;
            boolean bl = false;
            ClassId classId = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.annotationTarget);
            Intrinsics.checkNotNullExpressionValue(classId, "ClassId.topLevel(KotlinB\u2026Q_NAMES.annotationTarget)");
            Name name = Name.identifier(kotlinTarget.name());
            Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(kotlinTarget.name)");
            EnumValue enumValue = new EnumValue(classId, name);
            collection.add(enumValue);
        }
        List kotlinTargets = (List)destination$iv$iv;
        return new ArrayValue(kotlinTargets, mapJavaTargetArguments.1.INSTANCE);
    }

    @Nullable
    public final ConstantValue<?> mapJavaRetentionArgument$descriptors_jvm(@Nullable JavaAnnotationArgument element) {
        EnumValue enumValue;
        JavaAnnotationArgument javaAnnotationArgument = element;
        if (!(javaAnnotationArgument instanceof JavaEnumValueAnnotationArgument)) {
            javaAnnotationArgument = null;
        }
        JavaEnumValueAnnotationArgument javaEnumValueAnnotationArgument = (JavaEnumValueAnnotationArgument)javaAnnotationArgument;
        if (javaEnumValueAnnotationArgument != null) {
            JavaEnumValueAnnotationArgument javaEnumValueAnnotationArgument2 = javaEnumValueAnnotationArgument;
            boolean bl = false;
            boolean bl2 = false;
            JavaEnumValueAnnotationArgument it = javaEnumValueAnnotationArgument2;
            boolean bl3 = false;
            Object object = retentionNameList;
            Name name = it.getEntryName();
            String string = name != null ? name.asString() : null;
            boolean bl4 = false;
            KotlinRetention kotlinRetention = object.get(string);
            if (kotlinRetention != null) {
                object = kotlinRetention;
                boolean bl5 = false;
                bl4 = false;
                Object retention = object;
                boolean bl6 = false;
                ClassId classId = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.annotationRetention);
                Intrinsics.checkNotNullExpressionValue(classId, "ClassId.topLevel(KotlinB\u2026AMES.annotationRetention)");
                Name name2 = Name.identifier(((Enum)retention).name());
                Intrinsics.checkNotNullExpressionValue(name2, "Name.identifier(retention.name)");
                enumValue = new EnumValue(classId, name2);
            } else {
                enumValue = null;
            }
        } else {
            enumValue = null;
        }
        return enumValue;
    }

    private JavaAnnotationTargetMapper() {
    }

    static {
        JavaAnnotationTargetMapper javaAnnotationTargetMapper;
        INSTANCE = javaAnnotationTargetMapper = new JavaAnnotationTargetMapper();
        targetNameLists = MapsKt.mapOf(TuplesKt.to("PACKAGE", EnumSet.noneOf(KotlinTarget.class)), TuplesKt.to("TYPE", EnumSet.of((Enum)KotlinTarget.CLASS, (Enum)KotlinTarget.FILE)), TuplesKt.to("ANNOTATION_TYPE", EnumSet.of((Enum)KotlinTarget.ANNOTATION_CLASS)), TuplesKt.to("TYPE_PARAMETER", EnumSet.of((Enum)KotlinTarget.TYPE_PARAMETER)), TuplesKt.to("FIELD", EnumSet.of((Enum)KotlinTarget.FIELD)), TuplesKt.to("LOCAL_VARIABLE", EnumSet.of((Enum)KotlinTarget.LOCAL_VARIABLE)), TuplesKt.to("PARAMETER", EnumSet.of((Enum)KotlinTarget.VALUE_PARAMETER)), TuplesKt.to("CONSTRUCTOR", EnumSet.of((Enum)KotlinTarget.CONSTRUCTOR)), TuplesKt.to("METHOD", EnumSet.of((Enum)KotlinTarget.FUNCTION, (Enum)KotlinTarget.PROPERTY_GETTER, (Enum)KotlinTarget.PROPERTY_SETTER)), TuplesKt.to("TYPE_USE", EnumSet.of((Enum)KotlinTarget.TYPE)));
        retentionNameList = MapsKt.mapOf(TuplesKt.to("RUNTIME", KotlinRetention.RUNTIME), TuplesKt.to("CLASS", KotlinRetention.BINARY), TuplesKt.to("SOURCE", KotlinRetention.SOURCE));
    }
}

