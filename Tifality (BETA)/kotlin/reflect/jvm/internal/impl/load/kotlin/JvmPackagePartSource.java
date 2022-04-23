/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceFile;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.IncompatibleVersionErrorData;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JvmPackagePartSource
implements DeserializedContainerSource {
    @NotNull
    private final String moduleName;
    @NotNull
    private final JvmClassName className;
    @Nullable
    private final JvmClassName facadeClassName;
    @Nullable
    private final IncompatibleVersionErrorData<JvmMetadataVersion> incompatibility;
    private final boolean isPreReleaseInvisible;
    private final boolean isInvisibleIrDependency;
    @Nullable
    private final KotlinJvmBinaryClass knownJvmBinaryClass;

    @Override
    @NotNull
    public String getPresentableString() {
        return "Class '" + this.getClassId().asSingleFqName().asString() + '\'';
    }

    @NotNull
    public final Name getSimpleName() {
        String string = this.className.getInternalName();
        Intrinsics.checkNotNullExpressionValue(string, "className.internalName");
        Name name = Name.identifier(StringsKt.substringAfterLast$default(string, '/', null, 2, null));
        Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(classNam\u2026.substringAfterLast('/'))");
        return name;
    }

    @NotNull
    public final ClassId getClassId() {
        return new ClassId(this.className.getPackageFqName(), this.getSimpleName());
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() + ": " + this.className;
    }

    @Override
    @NotNull
    public SourceFile getContainingFile() {
        SourceFile sourceFile = SourceFile.NO_SOURCE_FILE;
        Intrinsics.checkNotNullExpressionValue(sourceFile, "SourceFile.NO_SOURCE_FILE");
        return sourceFile;
    }

    @Nullable
    public final JvmClassName getFacadeClassName() {
        return this.facadeClassName;
    }

    @Nullable
    public final KotlinJvmBinaryClass getKnownJvmBinaryClass() {
        return this.knownJvmBinaryClass;
    }

    /*
     * WARNING - void declaration
     */
    public JvmPackagePartSource(@NotNull JvmClassName className, @Nullable JvmClassName facadeClassName, @NotNull ProtoBuf.Package packageProto, @NotNull NameResolver nameResolver, @Nullable IncompatibleVersionErrorData<JvmMetadataVersion> incompatibility, boolean isPreReleaseInvisible, boolean isInvisibleIrDependency, @Nullable KotlinJvmBinaryClass knownJvmBinaryClass) {
        Object object;
        block3: {
            block2: {
                void p1;
                Intrinsics.checkNotNullParameter(className, "className");
                Intrinsics.checkNotNullParameter(packageProto, "packageProto");
                Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
                this.className = className;
                this.facadeClassName = facadeClassName;
                this.incompatibility = incompatibility;
                this.isPreReleaseInvisible = isPreReleaseInvisible;
                this.isInvisibleIrDependency = isInvisibleIrDependency;
                this.knownJvmBinaryClass = knownJvmBinaryClass;
                JvmPackagePartSource jvmPackagePartSource = this;
                GeneratedMessageLite.ExtendableMessage extendableMessage = packageProto;
                GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> generatedExtension = JvmProtoBuf.packageModuleName;
                Intrinsics.checkNotNullExpressionValue(generatedExtension, "JvmProtoBuf.packageModuleName");
                object = ProtoBufUtilKt.getExtensionOrNull(extendableMessage, generatedExtension);
                if (object == null) break block2;
                Integer n = object;
                boolean bl = false;
                boolean bl2 = false;
                int n2 = ((Number)n).intValue();
                JvmPackagePartSource jvmPackagePartSource2 = jvmPackagePartSource;
                boolean bl3 = false;
                String string = nameResolver.getString((int)p1);
                jvmPackagePartSource = jvmPackagePartSource2;
                object = string;
                if (object != null) break block3;
            }
            object = "main";
        }
        jvmPackagePartSource.moduleName = object;
    }

    /*
     * WARNING - void declaration
     */
    public JvmPackagePartSource(@NotNull KotlinJvmBinaryClass kotlinClass2, @NotNull ProtoBuf.Package packageProto, @NotNull NameResolver nameResolver, @Nullable IncompatibleVersionErrorData<JvmMetadataVersion> incompatibility, boolean isPreReleaseInvisible, boolean isInvisibleIrDependency) {
        JvmClassName jvmClassName;
        Intrinsics.checkNotNullParameter(kotlinClass2, "kotlinClass");
        Intrinsics.checkNotNullParameter(packageProto, "packageProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        JvmPackagePartSource jvmPackagePartSource = this;
        JvmClassName jvmClassName2 = JvmClassName.byClassId(kotlinClass2.getClassId());
        JvmClassName jvmClassName3 = jvmClassName2;
        Intrinsics.checkNotNullExpressionValue(jvmClassName2, "JvmClassName.byClassId(kotlinClass.classId)");
        String string = kotlinClass2.getClassHeader().getMultifileClassName();
        if (string != null) {
            void it;
            String string2 = string;
            boolean bl = false;
            boolean bl2 = false;
            String string3 = string2;
            JvmClassName jvmClassName4 = jvmClassName3;
            JvmPackagePartSource jvmPackagePartSource2 = jvmPackagePartSource;
            boolean bl3 = false;
            CharSequence charSequence = (CharSequence)it;
            boolean bl4 = false;
            JvmClassName jvmClassName5 = charSequence.length() > 0 ? JvmClassName.byInternalName((String)it) : null;
            jvmPackagePartSource = jvmPackagePartSource2;
            jvmClassName3 = jvmClassName4;
            jvmClassName = jvmClassName5;
        } else {
            jvmClassName = null;
        }
        jvmPackagePartSource(jvmClassName3, jvmClassName, packageProto, nameResolver, incompatibility, isPreReleaseInvisible, isInvisibleIrDependency, kotlinClass2);
    }
}

