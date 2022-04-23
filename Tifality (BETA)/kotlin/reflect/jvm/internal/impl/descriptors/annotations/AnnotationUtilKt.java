/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.BuiltInAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;

public final class AnnotationUtilKt {
    private static final Name DEPRECATED_MESSAGE_NAME;
    private static final Name DEPRECATED_REPLACE_WITH_NAME;
    private static final Name DEPRECATED_LEVEL_NAME;
    private static final Name REPLACE_WITH_EXPRESSION_NAME;
    private static final Name REPLACE_WITH_IMPORTS_NAME;

    @NotNull
    public static final AnnotationDescriptor createDeprecatedAnnotation(@NotNull KotlinBuiltIns $this$createDeprecatedAnnotation, @NotNull String message, @NotNull String replaceWith, @NotNull String level) {
        Intrinsics.checkNotNullParameter($this$createDeprecatedAnnotation, "$this$createDeprecatedAnnotation");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(replaceWith, "replaceWith");
        Intrinsics.checkNotNullParameter(level, "level");
        FqName fqName2 = KotlinBuiltIns.FQ_NAMES.replaceWith;
        Intrinsics.checkNotNullExpressionValue(fqName2, "KotlinBuiltIns.FQ_NAMES.replaceWith");
        BuiltInAnnotationDescriptor replaceWithAnnotation2 = new BuiltInAnnotationDescriptor($this$createDeprecatedAnnotation, fqName2, MapsKt.mapOf(TuplesKt.to(REPLACE_WITH_EXPRESSION_NAME, new StringValue(replaceWith)), TuplesKt.to(REPLACE_WITH_IMPORTS_NAME, new ArrayValue(CollectionsKt.emptyList(), (Function1<? super ModuleDescriptor, ? extends KotlinType>)new Function1<ModuleDescriptor, KotlinType>($this$createDeprecatedAnnotation){
            final /* synthetic */ KotlinBuiltIns $this_createDeprecatedAnnotation;

            @NotNull
            public final KotlinType invoke(@NotNull ModuleDescriptor module) {
                Intrinsics.checkNotNullParameter(module, "module");
                SimpleType simpleType2 = module.getBuiltIns().getArrayType(Variance.INVARIANT, this.$this_createDeprecatedAnnotation.getStringType());
                Intrinsics.checkNotNullExpressionValue(simpleType2, "module.builtIns.getArray\u2026ce.INVARIANT, stringType)");
                return simpleType2;
            }
            {
                this.$this_createDeprecatedAnnotation = kotlinBuiltIns;
                super(1);
            }
        }))));
        FqName fqName3 = KotlinBuiltIns.FQ_NAMES.deprecated;
        Intrinsics.checkNotNullExpressionValue(fqName3, "KotlinBuiltIns.FQ_NAMES.deprecated");
        Pair[] pairArray = new Pair[3];
        pairArray[0] = TuplesKt.to(DEPRECATED_MESSAGE_NAME, new StringValue(message));
        pairArray[1] = TuplesKt.to(DEPRECATED_REPLACE_WITH_NAME, new AnnotationValue(replaceWithAnnotation2));
        ClassId classId = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.deprecationLevel);
        Intrinsics.checkNotNullExpressionValue(classId, "ClassId.topLevel(KotlinB\u2026Q_NAMES.deprecationLevel)");
        Name name = Name.identifier(level);
        Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(level)");
        pairArray[2] = TuplesKt.to(DEPRECATED_LEVEL_NAME, new EnumValue(classId, name));
        return new BuiltInAnnotationDescriptor($this$createDeprecatedAnnotation, fqName3, MapsKt.mapOf(pairArray));
    }

    public static /* synthetic */ AnnotationDescriptor createDeprecatedAnnotation$default(KotlinBuiltIns kotlinBuiltIns, String string, String string2, String string3, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = "";
        }
        if ((n & 4) != 0) {
            string3 = "WARNING";
        }
        return AnnotationUtilKt.createDeprecatedAnnotation(kotlinBuiltIns, string, string2, string3);
    }

    static {
        Name name = Name.identifier("message");
        Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(\"message\")");
        DEPRECATED_MESSAGE_NAME = name;
        Name name2 = Name.identifier("replaceWith");
        Intrinsics.checkNotNullExpressionValue(name2, "Name.identifier(\"replaceWith\")");
        DEPRECATED_REPLACE_WITH_NAME = name2;
        Name name3 = Name.identifier("level");
        Intrinsics.checkNotNullExpressionValue(name3, "Name.identifier(\"level\")");
        DEPRECATED_LEVEL_NAME = name3;
        Name name4 = Name.identifier("expression");
        Intrinsics.checkNotNullExpressionValue(name4, "Name.identifier(\"expression\")");
        REPLACE_WITH_EXPRESSION_NAME = name4;
        Name name5 = Name.identifier("imports");
        Intrinsics.checkNotNullExpressionValue(name5, "Name.identifier(\"imports\")");
        REPLACE_WITH_IMPORTS_NAME = name5;
    }
}

