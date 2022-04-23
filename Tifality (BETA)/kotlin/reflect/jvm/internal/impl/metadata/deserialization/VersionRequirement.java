/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.DeprecationLevel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement$Companion$WhenMappings;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class VersionRequirement {
    @NotNull
    private final Version version;
    @NotNull
    private final ProtoBuf.VersionRequirement.VersionKind kind;
    @NotNull
    private final DeprecationLevel level;
    @Nullable
    private final Integer errorCode;
    @Nullable
    private final String message;
    public static final Companion Companion = new Companion(null);

    @NotNull
    public String toString() {
        return "since " + this.version + ' ' + (Object)((Object)this.level) + (this.errorCode != null ? " error " + this.errorCode : "") + (this.message != null ? ": " + this.message : "");
    }

    @NotNull
    public final Version getVersion() {
        return this.version;
    }

    @NotNull
    public final ProtoBuf.VersionRequirement.VersionKind getKind() {
        return this.kind;
    }

    public VersionRequirement(@NotNull Version version, @NotNull ProtoBuf.VersionRequirement.VersionKind kind, @NotNull DeprecationLevel level, @Nullable Integer errorCode, @Nullable String message) {
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter((Object)level, "level");
        this.version = version;
        this.kind = kind;
        this.level = level;
        this.errorCode = errorCode;
        this.message = message;
    }

    public static final class Version {
        private final int major;
        private final int minor;
        private final int patch;
        @JvmField
        @NotNull
        public static final Version INFINITY;
        public static final Companion Companion;

        @NotNull
        public final String asString() {
            return this.patch == 0 ? "" + this.major + '.' + this.minor : "" + this.major + '.' + this.minor + '.' + this.patch;
        }

        @NotNull
        public String toString() {
            return this.asString();
        }

        public Version(int major, int minor, int patch) {
            this.major = major;
            this.minor = minor;
            this.patch = patch;
        }

        public /* synthetic */ Version(int n, int n2, int n3, int n4, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n4 & 4) != 0) {
                n3 = 0;
            }
            this(n, n2, n3);
        }

        static {
            Companion = new Companion(null);
            INFINITY = new Version(256, 256, 256);
        }

        public int hashCode() {
            return (this.major * 31 + this.minor) * 31 + this.patch;
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof Version)) break block3;
                    Version version = (Version)object;
                    if (this.major != version.major || this.minor != version.minor || this.patch != version.patch) break block3;
                }
                return true;
            }
            return false;
        }

        public static final class Companion {
            @NotNull
            public final Version decode(@Nullable Integer version, @Nullable Integer versionFull) {
                return versionFull != null ? new Version((int)(versionFull & 0xFF), versionFull >> 8 & 0xFF, versionFull >> 16 & 0xFF) : (version != null ? new Version(version & 7, version >> 3 & 0xF, version >> 7 & 0x7F) : INFINITY);
            }

            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }
    }

    public static final class Companion {
        /*
         * WARNING - void declaration
         */
        @NotNull
        public final List<VersionRequirement> create(@NotNull MessageLite proto, @NotNull NameResolver nameResolver, @NotNull VersionRequirementTable table) {
            void $this$mapNotNullTo$iv$iv;
            List<Integer> ids;
            List<Integer> list;
            Intrinsics.checkNotNullParameter(proto, "proto");
            Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
            Intrinsics.checkNotNullParameter(table, "table");
            MessageLite messageLite = proto;
            if (messageLite instanceof ProtoBuf.Class) {
                list = ((ProtoBuf.Class)proto).getVersionRequirementList();
            } else if (messageLite instanceof ProtoBuf.Constructor) {
                list = ((ProtoBuf.Constructor)proto).getVersionRequirementList();
            } else if (messageLite instanceof ProtoBuf.Function) {
                list = ((ProtoBuf.Function)proto).getVersionRequirementList();
            } else if (messageLite instanceof ProtoBuf.Property) {
                list = ((ProtoBuf.Property)proto).getVersionRequirementList();
            } else if (messageLite instanceof ProtoBuf.TypeAlias) {
                list = ((ProtoBuf.TypeAlias)proto).getVersionRequirementList();
            } else {
                throw (Throwable)new IllegalStateException("Unexpected declaration: " + proto.getClass());
            }
            List<Integer> list2 = ids = list;
            Intrinsics.checkNotNullExpressionValue(list2, "ids");
            Iterable $this$mapNotNull$iv = list2;
            boolean $i$f$mapNotNull = false;
            Iterable iterable = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
            while (iterator2.hasNext()) {
                VersionRequirement versionRequirement;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = iterator2.next();
                boolean bl = false;
                Integer id = (Integer)element$iv$iv;
                boolean bl2 = false;
                Integer n = id;
                Intrinsics.checkNotNullExpressionValue(n, "id");
                if (Companion.create(n, nameResolver, table) == null) continue;
                boolean bl3 = false;
                boolean bl4 = false;
                VersionRequirement it$iv$iv = versionRequirement;
                boolean bl5 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            return (List)destination$iv$iv;
        }

        @Nullable
        public final VersionRequirement create(int id, @NotNull NameResolver nameResolver, @NotNull VersionRequirementTable table) {
            DeprecationLevel deprecationLevel;
            Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
            Intrinsics.checkNotNullParameter(table, "table");
            ProtoBuf.VersionRequirement versionRequirement = table.get(id);
            if (versionRequirement == null) {
                return null;
            }
            ProtoBuf.VersionRequirement info = versionRequirement;
            Version version = Version.Companion.decode(info.hasVersion() ? Integer.valueOf(info.getVersion()) : null, info.hasVersionFull() ? Integer.valueOf(info.getVersionFull()) : null);
            ProtoBuf.VersionRequirement.Level level = info.getLevel();
            Intrinsics.checkNotNull(level);
            switch (VersionRequirement$Companion$WhenMappings.$EnumSwitchMapping$0[level.ordinal()]) {
                case 1: {
                    deprecationLevel = DeprecationLevel.WARNING;
                    break;
                }
                case 2: {
                    deprecationLevel = DeprecationLevel.ERROR;
                    break;
                }
                case 3: {
                    deprecationLevel = DeprecationLevel.HIDDEN;
                    break;
                }
                default: {
                    throw new NoWhenBranchMatchedException();
                }
            }
            DeprecationLevel level2 = deprecationLevel;
            Integer errorCode = info.hasErrorCode() ? Integer.valueOf(info.getErrorCode()) : null;
            String message = info.hasMessage() ? nameResolver.getString(info.getMessage()) : null;
            ProtoBuf.VersionRequirement.VersionKind versionKind = info.getVersionKind();
            Intrinsics.checkNotNullExpressionValue(versionKind, "info.versionKind");
            return new VersionRequirement(version, versionKind, level2, errorCode, message);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

