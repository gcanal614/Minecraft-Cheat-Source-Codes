/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.Set;
import kotlin.Pair;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.DeserializationComponentsForJava;
import kotlin.reflect.jvm.internal.impl.load.kotlin.DeserializedDescriptorResolver;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmPackagePartSource;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinarySourceElement;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmNameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassData;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.IncompatibleVersionErrorData;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedPackageMemberScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DeserializedDescriptorResolver {
    @NotNull
    public DeserializationComponents components;
    @NotNull
    private static final Set<KotlinClassHeader.Kind> KOTLIN_CLASS;
    private static final Set<KotlinClassHeader.Kind> KOTLIN_FILE_FACADE_OR_MULTIFILE_CLASS_PART;
    private static final JvmMetadataVersion KOTLIN_1_1_EAP_METADATA_VERSION;
    private static final JvmMetadataVersion KOTLIN_1_3_M1_METADATA_VERSION;
    @NotNull
    private static final JvmMetadataVersion KOTLIN_1_3_RC_METADATA_VERSION;
    public static final Companion Companion;

    @NotNull
    public final DeserializationComponents getComponents() {
        DeserializationComponents deserializationComponents = this.components;
        if (deserializationComponents == null) {
            Intrinsics.throwUninitializedPropertyAccessException("components");
        }
        return deserializationComponents;
    }

    public final void setComponents(@NotNull DeserializationComponentsForJava components) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.components = components.getComponents();
    }

    private final boolean getSkipMetadataVersionCheck() {
        DeserializationComponents deserializationComponents = this.components;
        if (deserializationComponents == null) {
            Intrinsics.throwUninitializedPropertyAccessException("components");
        }
        return deserializationComponents.getConfiguration().getSkipMetadataVersionCheck();
    }

    @Nullable
    public final ClassDescriptor resolveClass(@NotNull KotlinJvmBinaryClass kotlinClass2) {
        Intrinsics.checkNotNullParameter(kotlinClass2, "kotlinClass");
        ClassData classData = this.readClassData$descriptors_jvm(kotlinClass2);
        if (classData == null) {
            return null;
        }
        ClassData classData2 = classData;
        DeserializationComponents deserializationComponents = this.components;
        if (deserializationComponents == null) {
            Intrinsics.throwUninitializedPropertyAccessException("components");
        }
        return deserializationComponents.getClassDeserializer().deserializeClass(kotlinClass2.getClassId(), classData2);
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public final ClassData readClassData$descriptors_jvm(@NotNull KotlinJvmBinaryClass kotlinClass2) {
        void nameResolver;
        Pair<JvmNameResolver, ProtoBuf.Class> pair;
        Intrinsics.checkNotNullParameter(kotlinClass2, "kotlinClass");
        String[] stringArray = this.readData(kotlinClass2, KOTLIN_CLASS);
        if (stringArray == null) {
            return null;
        }
        String[] data2 = stringArray;
        String[] stringArray2 = kotlinClass2.getClassHeader().getStrings();
        if (stringArray2 == null) {
            return null;
        }
        String[] strings = stringArray2;
        DeserializedDescriptorResolver this_$iv = this;
        boolean $i$f$parseProto = false;
        try {
            try {
                boolean bl = false;
                pair = JvmProtoBufUtil.readClassDataFrom(data2, strings);
            }
            catch (InvalidProtocolBufferException e$iv) {
                throw (Throwable)new IllegalStateException("Could not read data from " + kotlinClass2.getLocation(), e$iv);
            }
        }
        catch (Throwable e$iv) {
            if (this_$iv.getSkipMetadataVersionCheck() || kotlinClass2.getClassHeader().getMetadataVersion().isCompatible()) {
                throw e$iv;
            }
            pair = null;
        }
        Pair<JvmNameResolver, ProtoBuf.Class> pair2 = pair;
        if (pair2 == null) {
            return null;
        }
        Pair<JvmNameResolver, ProtoBuf.Class> pair3 = pair2;
        JvmNameResolver jvmNameResolver = pair3.component1();
        ProtoBuf.Class classProto = pair3.component2();
        KotlinJvmBinarySourceElement source = new KotlinJvmBinarySourceElement(kotlinClass2, this.getIncompatibility(kotlinClass2), this.isPreReleaseInvisible(kotlinClass2), this.isInvisibleJvmIrDependency(kotlinClass2));
        return new ClassData((NameResolver)nameResolver, classProto, kotlinClass2.getClassHeader().getMetadataVersion(), source);
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public final MemberScope createKotlinPackagePartScope(@NotNull PackageFragmentDescriptor descriptor2, @NotNull KotlinJvmBinaryClass kotlinClass2) {
        void nameResolver;
        Pair<JvmNameResolver, ProtoBuf.Package> pair;
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        Intrinsics.checkNotNullParameter(kotlinClass2, "kotlinClass");
        String[] stringArray = this.readData(kotlinClass2, KOTLIN_FILE_FACADE_OR_MULTIFILE_CLASS_PART);
        if (stringArray == null) {
            return null;
        }
        String[] data2 = stringArray;
        String[] stringArray2 = kotlinClass2.getClassHeader().getStrings();
        if (stringArray2 == null) {
            return null;
        }
        String[] strings = stringArray2;
        DeserializedDescriptorResolver this_$iv = this;
        boolean $i$f$parseProto = false;
        try {
            try {
                boolean bl = false;
                pair = JvmProtoBufUtil.readPackageDataFrom(data2, strings);
            }
            catch (InvalidProtocolBufferException e$iv) {
                throw (Throwable)new IllegalStateException("Could not read data from " + kotlinClass2.getLocation(), e$iv);
            }
        }
        catch (Throwable e$iv) {
            if (this_$iv.getSkipMetadataVersionCheck() || kotlinClass2.getClassHeader().getMetadataVersion().isCompatible()) {
                throw e$iv;
            }
            pair = null;
        }
        Pair<JvmNameResolver, ProtoBuf.Package> pair2 = pair;
        if (pair2 == null) {
            return null;
        }
        Pair<JvmNameResolver, ProtoBuf.Package> pair3 = pair2;
        JvmNameResolver jvmNameResolver = pair3.component1();
        ProtoBuf.Package packageProto = pair3.component2();
        JvmPackagePartSource source = new JvmPackagePartSource(kotlinClass2, packageProto, (NameResolver)nameResolver, this.getIncompatibility(kotlinClass2), this.isPreReleaseInvisible(kotlinClass2), this.isInvisibleJvmIrDependency(kotlinClass2));
        NameResolver nameResolver2 = (NameResolver)nameResolver;
        BinaryVersion binaryVersion = kotlinClass2.getClassHeader().getMetadataVersion();
        DeserializedContainerSource deserializedContainerSource = source;
        DeserializationComponents deserializationComponents = this.components;
        if (deserializationComponents == null) {
            Intrinsics.throwUninitializedPropertyAccessException("components");
        }
        return new DeserializedPackageMemberScope(descriptor2, packageProto, nameResolver2, binaryVersion, deserializedContainerSource, deserializationComponents, createKotlinPackagePartScope.2.INSTANCE);
    }

    private final IncompatibleVersionErrorData<JvmMetadataVersion> getIncompatibility(KotlinJvmBinaryClass $this$incompatibility) {
        if (this.getSkipMetadataVersionCheck() || $this$incompatibility.getClassHeader().getMetadataVersion().isCompatible()) {
            return null;
        }
        return new IncompatibleVersionErrorData<BinaryVersion>($this$incompatibility.getClassHeader().getMetadataVersion(), JvmMetadataVersion.INSTANCE, $this$incompatibility.getLocation(), $this$incompatibility.getClassId());
    }

    private final boolean isPreReleaseInvisible(KotlinJvmBinaryClass $this$isPreReleaseInvisible) {
        DeserializationComponents deserializationComponents = this.components;
        if (deserializationComponents == null) {
            Intrinsics.throwUninitializedPropertyAccessException("components");
        }
        return deserializationComponents.getConfiguration().getReportErrorsOnPreReleaseDependencies() && ($this$isPreReleaseInvisible.getClassHeader().isPreRelease() || Intrinsics.areEqual($this$isPreReleaseInvisible.getClassHeader().getMetadataVersion(), KOTLIN_1_1_EAP_METADATA_VERSION)) || this.isCompiledWith13M1($this$isPreReleaseInvisible);
    }

    private final boolean isCompiledWith13M1(KotlinJvmBinaryClass $this$isCompiledWith13M1) {
        DeserializationComponents deserializationComponents = this.components;
        if (deserializationComponents == null) {
            Intrinsics.throwUninitializedPropertyAccessException("components");
        }
        return !deserializationComponents.getConfiguration().getSkipPrereleaseCheck() && $this$isCompiledWith13M1.getClassHeader().isPreRelease() && Intrinsics.areEqual($this$isCompiledWith13M1.getClassHeader().getMetadataVersion(), KOTLIN_1_3_M1_METADATA_VERSION);
    }

    private final boolean isInvisibleJvmIrDependency(KotlinJvmBinaryClass $this$isInvisibleJvmIrDependency) {
        DeserializationComponents deserializationComponents = this.components;
        if (deserializationComponents == null) {
            Intrinsics.throwUninitializedPropertyAccessException("components");
        }
        return deserializationComponents.getConfiguration().getReportErrorsOnIrDependencies() && $this$isInvisibleJvmIrDependency.getClassHeader().isUnstableJvmIrBinary();
    }

    private final String[] readData(KotlinJvmBinaryClass kotlinClass2, Set<? extends KotlinClassHeader.Kind> expectedKinds) {
        Object object;
        KotlinClassHeader header = kotlinClass2.getClassHeader();
        String[] stringArray = header.getData();
        if (stringArray == null) {
            stringArray = header.getIncompatibleData();
        }
        if (stringArray != null) {
            String[] stringArray2 = stringArray;
            boolean bl = false;
            boolean bl2 = false;
            String[] it = stringArray2;
            boolean bl3 = false;
            object = expectedKinds.contains((Object)header.getKind()) ? stringArray2 : null;
        } else {
            object = null;
        }
        return object;
    }

    static {
        Companion = new Companion(null);
        KOTLIN_CLASS = SetsKt.setOf(KotlinClassHeader.Kind.CLASS);
        KOTLIN_FILE_FACADE_OR_MULTIFILE_CLASS_PART = SetsKt.setOf(KotlinClassHeader.Kind.FILE_FACADE, KotlinClassHeader.Kind.MULTIFILE_CLASS_PART);
        KOTLIN_1_1_EAP_METADATA_VERSION = new JvmMetadataVersion(1, 1, 2);
        KOTLIN_1_3_M1_METADATA_VERSION = new JvmMetadataVersion(1, 1, 11);
        KOTLIN_1_3_RC_METADATA_VERSION = new JvmMetadataVersion(1, 1, 13);
    }

    public static final class Companion {
        @NotNull
        public final JvmMetadataVersion getKOTLIN_1_3_RC_METADATA_VERSION$descriptors_jvm() {
            return KOTLIN_1_3_RC_METADATA_VERSION;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

