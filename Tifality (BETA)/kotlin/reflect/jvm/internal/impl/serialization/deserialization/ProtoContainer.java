/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ProtoContainer {
    @NotNull
    private final NameResolver nameResolver;
    @NotNull
    private final TypeTable typeTable;
    @Nullable
    private final SourceElement source;

    @NotNull
    public abstract FqName debugFqName();

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() + ": " + this.debugFqName();
    }

    @NotNull
    public final NameResolver getNameResolver() {
        return this.nameResolver;
    }

    @NotNull
    public final TypeTable getTypeTable() {
        return this.typeTable;
    }

    @Nullable
    public final SourceElement getSource() {
        return this.source;
    }

    private ProtoContainer(NameResolver nameResolver, TypeTable typeTable, SourceElement source) {
        this.nameResolver = nameResolver;
        this.typeTable = typeTable;
        this.source = source;
    }

    public /* synthetic */ ProtoContainer(NameResolver nameResolver, TypeTable typeTable, SourceElement source, DefaultConstructorMarker $constructor_marker) {
        this(nameResolver, typeTable, source);
    }

    public static final class Class
    extends ProtoContainer {
        @NotNull
        private final ClassId classId;
        @NotNull
        private final ProtoBuf.Class.Kind kind;
        private final boolean isInner;
        @NotNull
        private final ProtoBuf.Class classProto;
        @Nullable
        private final Class outerClass;

        @NotNull
        public final ClassId getClassId() {
            return this.classId;
        }

        @NotNull
        public final ProtoBuf.Class.Kind getKind() {
            return this.kind;
        }

        public final boolean isInner() {
            return this.isInner;
        }

        @Override
        @NotNull
        public FqName debugFqName() {
            FqName fqName2 = this.classId.asSingleFqName();
            Intrinsics.checkNotNullExpressionValue(fqName2, "classId.asSingleFqName()");
            return fqName2;
        }

        @NotNull
        public final ProtoBuf.Class getClassProto() {
            return this.classProto;
        }

        @Nullable
        public final Class getOuterClass() {
            return this.outerClass;
        }

        public Class(@NotNull ProtoBuf.Class classProto, @NotNull NameResolver nameResolver, @NotNull TypeTable typeTable, @Nullable SourceElement source, @Nullable Class outerClass) {
            Intrinsics.checkNotNullParameter(classProto, "classProto");
            Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
            Intrinsics.checkNotNullParameter(typeTable, "typeTable");
            super(nameResolver, typeTable, source, null);
            this.classProto = classProto;
            this.outerClass = outerClass;
            this.classId = NameResolverUtilKt.getClassId(nameResolver, this.classProto.getFqName());
            ProtoBuf.Class.Kind kind = Flags.CLASS_KIND.get(this.classProto.getFlags());
            if (kind == null) {
                kind = ProtoBuf.Class.Kind.CLASS;
            }
            this.kind = kind;
            Boolean bl = Flags.IS_INNER.get(this.classProto.getFlags());
            Intrinsics.checkNotNullExpressionValue(bl, "Flags.IS_INNER.get(classProto.flags)");
            this.isInner = bl;
        }
    }

    public static final class Package
    extends ProtoContainer {
        @NotNull
        private final FqName fqName;

        @Override
        @NotNull
        public FqName debugFqName() {
            return this.fqName;
        }

        public Package(@NotNull FqName fqName2, @NotNull NameResolver nameResolver, @NotNull TypeTable typeTable, @Nullable SourceElement source) {
            Intrinsics.checkNotNullParameter(fqName2, "fqName");
            Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
            Intrinsics.checkNotNullParameter(typeTable, "typeTable");
            super(nameResolver, typeTable, source, null);
            this.fqName = fqName2;
        }
    }
}

