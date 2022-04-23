/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import java.util.List;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.builtins.BuiltInsProtoBuf;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.serialization.SerializerExtensionProtocol;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class BuiltInSerializerProtocol
extends SerializerExtensionProtocol {
    public static final BuiltInSerializerProtocol INSTANCE;

    @NotNull
    public final String getBuiltInsFilePath(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        StringBuilder stringBuilder = new StringBuilder();
        String string = fqName2.asString();
        Intrinsics.checkNotNullExpressionValue(string, "fqName.asString()");
        return stringBuilder.append(StringsKt.replace$default(string, '.', '/', false, 4, null)).append("/").append(this.getBuiltInsFileName(fqName2)).toString();
    }

    @NotNull
    public final String getBuiltInsFileName(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return this.shortName(fqName2) + "." + "kotlin_builtins";
    }

    private final String shortName(FqName fqName2) {
        String string;
        if (fqName2.isRoot()) {
            string = "default-package";
        } else {
            String string2 = fqName2.shortName().asString();
            string = string2;
            Intrinsics.checkNotNullExpressionValue(string2, "fqName.shortName().asString()");
        }
        return string;
    }

    /*
     * WARNING - void declaration
     */
    private BuiltInSerializerProtocol() {
        void p1;
        ExtensionRegistryLite extensionRegistryLite = ExtensionRegistryLite.newInstance();
        boolean bl = false;
        boolean bl2 = false;
        ExtensionRegistryLite extensionRegistryLite2 = extensionRegistryLite;
        BuiltInSerializerProtocol builtInSerializerProtocol = this;
        boolean bl3 = false;
        BuiltInsProtoBuf.registerAllExtensions((ExtensionRegistryLite)p1);
        Unit unit = Unit.INSTANCE;
        ExtensionRegistryLite extensionRegistryLite3 = extensionRegistryLite;
        Intrinsics.checkNotNullExpressionValue(extensionRegistryLite3, "ExtensionRegistryLite.ne\u2026f::registerAllExtensions)");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> generatedExtension = BuiltInsProtoBuf.packageFqName;
        Intrinsics.checkNotNullExpressionValue(generatedExtension, "BuiltInsProtoBuf.packageFqName");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, List<ProtoBuf.Annotation>> generatedExtension2 = BuiltInsProtoBuf.constructorAnnotation;
        Intrinsics.checkNotNullExpressionValue(generatedExtension2, "BuiltInsProtoBuf.constructorAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Annotation>> generatedExtension3 = BuiltInsProtoBuf.classAnnotation;
        Intrinsics.checkNotNullExpressionValue(generatedExtension3, "BuiltInsProtoBuf.classAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> generatedExtension4 = BuiltInsProtoBuf.functionAnnotation;
        Intrinsics.checkNotNullExpressionValue(generatedExtension4, "BuiltInsProtoBuf.functionAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> generatedExtension5 = BuiltInsProtoBuf.propertyAnnotation;
        Intrinsics.checkNotNullExpressionValue(generatedExtension5, "BuiltInsProtoBuf.propertyAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> generatedExtension6 = BuiltInsProtoBuf.propertyGetterAnnotation;
        Intrinsics.checkNotNullExpressionValue(generatedExtension6, "BuiltInsProtoBuf.propertyGetterAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> generatedExtension7 = BuiltInsProtoBuf.propertySetterAnnotation;
        Intrinsics.checkNotNullExpressionValue(generatedExtension7, "BuiltInsProtoBuf.propertySetterAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, List<ProtoBuf.Annotation>> generatedExtension8 = BuiltInsProtoBuf.enumEntryAnnotation;
        Intrinsics.checkNotNullExpressionValue(generatedExtension8, "BuiltInsProtoBuf.enumEntryAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, ProtoBuf.Annotation.Argument.Value> generatedExtension9 = BuiltInsProtoBuf.compileTimeValue;
        Intrinsics.checkNotNullExpressionValue(generatedExtension9, "BuiltInsProtoBuf.compileTimeValue");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.ValueParameter, List<ProtoBuf.Annotation>> generatedExtension10 = BuiltInsProtoBuf.parameterAnnotation;
        Intrinsics.checkNotNullExpressionValue(generatedExtension10, "BuiltInsProtoBuf.parameterAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, List<ProtoBuf.Annotation>> generatedExtension11 = BuiltInsProtoBuf.typeAnnotation;
        Intrinsics.checkNotNullExpressionValue(generatedExtension11, "BuiltInsProtoBuf.typeAnnotation");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, List<ProtoBuf.Annotation>> generatedExtension12 = BuiltInsProtoBuf.typeParameterAnnotation;
        Intrinsics.checkNotNullExpressionValue(generatedExtension12, "BuiltInsProtoBuf.typeParameterAnnotation");
        super(extensionRegistryLite3, generatedExtension, generatedExtension2, generatedExtension3, generatedExtension4, generatedExtension5, generatedExtension6, generatedExtension7, generatedExtension8, generatedExtension9, generatedExtension10, generatedExtension11, generatedExtension12);
    }

    static {
        BuiltInSerializerProtocol builtInSerializerProtocol;
        INSTANCE = builtInSerializerProtocol = new BuiltInSerializerProtocol();
    }
}

