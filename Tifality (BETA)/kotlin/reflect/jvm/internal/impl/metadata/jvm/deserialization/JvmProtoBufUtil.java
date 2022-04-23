/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.BitEncoding;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.ClassMapperLite;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmFlags;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmNameResolver;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JvmProtoBufUtil {
    @NotNull
    private static final ExtensionRegistryLite EXTENSION_REGISTRY;
    public static final JvmProtoBufUtil INSTANCE;

    @NotNull
    public final ExtensionRegistryLite getEXTENSION_REGISTRY() {
        return EXTENSION_REGISTRY;
    }

    @JvmStatic
    @NotNull
    public static final Pair<JvmNameResolver, ProtoBuf.Class> readClassDataFrom(@NotNull String[] data2, @NotNull String[] strings) {
        Intrinsics.checkNotNullParameter(data2, "data");
        Intrinsics.checkNotNullParameter(strings, "strings");
        byte[] byArray = BitEncoding.decodeBytes(data2);
        Intrinsics.checkNotNullExpressionValue(byArray, "BitEncoding.decodeBytes(data)");
        return JvmProtoBufUtil.readClassDataFrom(byArray, strings);
    }

    @JvmStatic
    @NotNull
    public static final Pair<JvmNameResolver, ProtoBuf.Class> readClassDataFrom(@NotNull byte[] bytes, @NotNull String[] strings) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        Intrinsics.checkNotNullParameter(strings, "strings");
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        return new Pair<JvmNameResolver, ProtoBuf.Class>(INSTANCE.readNameResolver(input, strings), ProtoBuf.Class.parseFrom(input, EXTENSION_REGISTRY));
    }

    @JvmStatic
    @NotNull
    public static final Pair<JvmNameResolver, ProtoBuf.Package> readPackageDataFrom(@NotNull String[] data2, @NotNull String[] strings) {
        Intrinsics.checkNotNullParameter(data2, "data");
        Intrinsics.checkNotNullParameter(strings, "strings");
        byte[] byArray = BitEncoding.decodeBytes(data2);
        Intrinsics.checkNotNullExpressionValue(byArray, "BitEncoding.decodeBytes(data)");
        return JvmProtoBufUtil.readPackageDataFrom(byArray, strings);
    }

    @JvmStatic
    @NotNull
    public static final Pair<JvmNameResolver, ProtoBuf.Package> readPackageDataFrom(@NotNull byte[] bytes, @NotNull String[] strings) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        Intrinsics.checkNotNullParameter(strings, "strings");
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        return new Pair<JvmNameResolver, ProtoBuf.Package>(INSTANCE.readNameResolver(input, strings), ProtoBuf.Package.parseFrom(input, EXTENSION_REGISTRY));
    }

    @JvmStatic
    @NotNull
    public static final Pair<JvmNameResolver, ProtoBuf.Function> readFunctionDataFrom(@NotNull String[] data2, @NotNull String[] strings) {
        Intrinsics.checkNotNullParameter(data2, "data");
        Intrinsics.checkNotNullParameter(strings, "strings");
        ByteArrayInputStream input = new ByteArrayInputStream(BitEncoding.decodeBytes(data2));
        return new Pair<JvmNameResolver, ProtoBuf.Function>(INSTANCE.readNameResolver(input, strings), ProtoBuf.Function.parseFrom(input, EXTENSION_REGISTRY));
    }

    private final JvmNameResolver readNameResolver(InputStream $this$readNameResolver, String[] strings) {
        JvmProtoBuf.StringTableTypes stringTableTypes = JvmProtoBuf.StringTableTypes.parseDelimitedFrom($this$readNameResolver, EXTENSION_REGISTRY);
        Intrinsics.checkNotNullExpressionValue(stringTableTypes, "JvmProtoBuf.StringTableT\u2026this, EXTENSION_REGISTRY)");
        return new JvmNameResolver(stringTableTypes, strings);
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public final JvmMemberSignature.Method getJvmMethodSignature(@NotNull ProtoBuf.Function proto, @NotNull NameResolver nameResolver, @NotNull TypeTable typeTable) {
        String string;
        int name;
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        GeneratedMessageLite.ExtendableMessage extendableMessage = proto;
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, JvmProtoBuf.JvmMethodSignature> generatedExtension = JvmProtoBuf.methodSignature;
        Intrinsics.checkNotNullExpressionValue(generatedExtension, "JvmProtoBuf.methodSignature");
        JvmProtoBuf.JvmMethodSignature signature2 = ProtoBufUtilKt.getExtensionOrNull(extendableMessage, generatedExtension);
        int n = name = signature2 != null && signature2.hasName() ? signature2.getName() : proto.getName();
        if (signature2 != null && signature2.hasDesc()) {
            string = nameResolver.getString(signature2.getDesc());
        } else {
            void $this$mapTo$iv$iv;
            Object object;
            void $this$mapTo$iv$iv2;
            void $this$map$iv;
            Collection collection = CollectionsKt.listOfNotNull(ProtoTypeTableUtilKt.receiverType(proto, typeTable));
            List<ProtoBuf.ValueParameter> list = proto.getValueParameterList();
            Intrinsics.checkNotNullExpressionValue(list, "proto.valueParameterList");
            Iterable iterable = list;
            Collection collection2 = collection;
            boolean $i$f$map = false;
            void var10_10 = $this$map$iv;
            Iterable destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv2) {
                void it;
                ProtoBuf.ValueParameter valueParameter = (ProtoBuf.ValueParameter)item$iv$iv;
                object = destination$iv$iv;
                boolean bl = false;
                void v6 = it;
                Intrinsics.checkNotNullExpressionValue(v6, "it");
                ProtoBuf.Type type2 = ProtoTypeTableUtilKt.type((ProtoBuf.ValueParameter)v6, typeTable);
                object.add(type2);
            }
            object = (List)destination$iv$iv;
            List parameterTypes = CollectionsKt.plus(collection2, (Iterable)object);
            Iterable $this$map$iv2 = parameterTypes;
            boolean $i$f$map2 = false;
            destination$iv$iv = $this$map$iv2;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
            boolean $i$f$mapTo2 = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                ProtoBuf.Type bl = (ProtoBuf.Type)item$iv$iv;
                collection2 = destination$iv$iv2;
                boolean bl2 = false;
                if (INSTANCE.mapTypeDefault((ProtoBuf.Type)it, nameResolver) == null) {
                    return null;
                }
                collection2.add(object);
            }
            List parametersDesc = (List)destination$iv$iv2;
            String string2 = this.mapTypeDefault(ProtoTypeTableUtilKt.returnType(proto, typeTable), nameResolver);
            if (string2 == null) {
                return null;
            }
            String returnTypeDesc = string2;
            string = CollectionsKt.joinToString$default(parametersDesc, "", "(", ")", 0, null, null, 56, null) + returnTypeDesc;
        }
        String desc = string;
        return new JvmMemberSignature.Method(nameResolver.getString(name), desc);
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public final JvmMemberSignature.Method getJvmConstructorSignature(@NotNull ProtoBuf.Constructor proto, @NotNull NameResolver nameResolver, @NotNull TypeTable typeTable) {
        String string;
        String name;
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        GeneratedMessageLite.ExtendableMessage extendableMessage = proto;
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, JvmProtoBuf.JvmMethodSignature> generatedExtension = JvmProtoBuf.constructorSignature;
        Intrinsics.checkNotNullExpressionValue(generatedExtension, "JvmProtoBuf.constructorSignature");
        JvmProtoBuf.JvmMethodSignature signature2 = ProtoBufUtilKt.getExtensionOrNull(extendableMessage, generatedExtension);
        String string2 = name = signature2 != null && signature2.hasName() ? nameResolver.getString(signature2.getName()) : "<init>";
        if (signature2 != null && signature2.hasDesc()) {
            string = nameResolver.getString(signature2.getDesc());
        } else {
            void $this$mapTo$iv$iv;
            List<ProtoBuf.ValueParameter> list = proto.getValueParameterList();
            Intrinsics.checkNotNullExpressionValue(list, "proto.valueParameterList");
            Iterable $this$map$iv = list;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                String string3;
                void it;
                ProtoBuf.ValueParameter valueParameter = (ProtoBuf.ValueParameter)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                void v5 = it;
                Intrinsics.checkNotNullExpressionValue(v5, "it");
                if (INSTANCE.mapTypeDefault(ProtoTypeTableUtilKt.type((ProtoBuf.ValueParameter)v5, typeTable), nameResolver) == null) {
                    return null;
                }
                collection.add(string3);
            }
            string = CollectionsKt.joinToString$default((List)destination$iv$iv, "", "(", ")V", 0, null, null, 56, null);
        }
        String desc = string;
        return new JvmMemberSignature.Method(name, desc);
    }

    @Nullable
    public final JvmMemberSignature.Field getJvmFieldSignature(@NotNull ProtoBuf.Property proto, @NotNull NameResolver nameResolver, @NotNull TypeTable typeTable, boolean requireHasFieldFlag) {
        String string;
        int name;
        JvmProtoBuf.JvmFieldSignature field;
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        GeneratedMessageLite.ExtendableMessage extendableMessage = proto;
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, JvmProtoBuf.JvmPropertySignature> generatedExtension = JvmProtoBuf.propertySignature;
        Intrinsics.checkNotNullExpressionValue(generatedExtension, "JvmProtoBuf.propertySignature");
        JvmProtoBuf.JvmPropertySignature jvmPropertySignature = ProtoBufUtilKt.getExtensionOrNull(extendableMessage, generatedExtension);
        if (jvmPropertySignature == null) {
            return null;
        }
        JvmProtoBuf.JvmPropertySignature signature2 = jvmPropertySignature;
        JvmProtoBuf.JvmFieldSignature jvmFieldSignature = field = signature2.hasField() ? signature2.getField() : null;
        if (field == null && requireHasFieldFlag) {
            return null;
        }
        int n = name = field != null && field.hasName() ? field.getName() : proto.getName();
        if (field != null && field.hasDesc()) {
            string = nameResolver.getString(field.getDesc());
        } else {
            string = this.mapTypeDefault(ProtoTypeTableUtilKt.returnType(proto, typeTable), nameResolver);
            if (string == null) {
                return null;
            }
        }
        String desc = string;
        return new JvmMemberSignature.Field(nameResolver.getString(name), desc);
    }

    public static /* synthetic */ JvmMemberSignature.Field getJvmFieldSignature$default(JvmProtoBufUtil jvmProtoBufUtil, ProtoBuf.Property property, NameResolver nameResolver, TypeTable typeTable, boolean bl, int n, Object object) {
        if ((n & 8) != 0) {
            bl = true;
        }
        return jvmProtoBufUtil.getJvmFieldSignature(property, nameResolver, typeTable, bl);
    }

    private final String mapTypeDefault(ProtoBuf.Type type2, NameResolver nameResolver) {
        return type2.hasClassName() ? ClassMapperLite.mapClass(nameResolver.getQualifiedClassName(type2.getClassName())) : null;
    }

    @JvmStatic
    public static final boolean isMovedFromInterfaceCompanion(@NotNull ProtoBuf.Property proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Flags.BooleanFlagField booleanFlagField = JvmFlags.INSTANCE.getIS_MOVED_FROM_INTERFACE_COMPANION();
        Integer n = proto.getExtension(JvmProtoBuf.flags);
        Intrinsics.checkNotNullExpressionValue(n, "proto.getExtension(JvmProtoBuf.flags)");
        Boolean bl = booleanFlagField.get(((Number)n).intValue());
        Intrinsics.checkNotNullExpressionValue(bl, "JvmFlags.IS_MOVED_FROM_I\u2026nsion(JvmProtoBuf.flags))");
        return bl;
    }

    private JvmProtoBufUtil() {
    }

    static {
        JvmProtoBufUtil jvmProtoBufUtil;
        INSTANCE = jvmProtoBufUtil = new JvmProtoBufUtil();
        ExtensionRegistryLite extensionRegistryLite = ExtensionRegistryLite.newInstance();
        boolean bl = false;
        boolean bl2 = false;
        ExtensionRegistryLite p1 = extensionRegistryLite;
        boolean bl3 = false;
        JvmProtoBuf.registerAllExtensions(p1);
        ExtensionRegistryLite extensionRegistryLite2 = extensionRegistryLite;
        Intrinsics.checkNotNullExpressionValue(extensionRegistryLite2, "ExtensionRegistryLite.ne\u2026f::registerAllExtensions)");
        EXTENSION_REGISTRY = extensionRegistryLite2;
    }
}

