/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin.header;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmBytecodeBinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class KotlinClassHeader {
    @NotNull
    private final Kind kind;
    @NotNull
    private final JvmMetadataVersion metadataVersion;
    @NotNull
    private final JvmBytecodeBinaryVersion bytecodeVersion;
    @Nullable
    private final String[] data;
    @Nullable
    private final String[] incompatibleData;
    @Nullable
    private final String[] strings;
    private final String extraString;
    private final int extraInt;
    @Nullable
    private final String packageName;

    @Nullable
    public final String getMultifileClassName() {
        String string = this.extraString;
        boolean bl = false;
        boolean bl2 = false;
        String it = string;
        boolean bl3 = false;
        return this.kind == Kind.MULTIFILE_CLASS_PART ? string : null;
    }

    @NotNull
    public final List<String> getMultifilePartNames() {
        Object object = this.data;
        boolean bl = false;
        boolean bl2 = false;
        String[] it = object;
        boolean bl3 = false;
        Object object2 = this.kind == Kind.MULTIFILE_CLASS ? object : null;
        object = object2 != null ? ArraysKt.asList(object2) : null;
        bl = false;
        Object object3 = object;
        if (object == null) {
            object3 = CollectionsKt.emptyList();
        }
        return object3;
    }

    public final boolean isUnstableJvmIrBinary() {
        return (this.extraInt & 0x10) != 0 && (this.extraInt & 0x20) == 0;
    }

    public final boolean isPreRelease() {
        return (this.extraInt & 2) != 0;
    }

    @NotNull
    public String toString() {
        return (Object)((Object)this.kind) + " version=" + this.metadataVersion;
    }

    @NotNull
    public final Kind getKind() {
        return this.kind;
    }

    @NotNull
    public final JvmMetadataVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    @Nullable
    public final String[] getData() {
        return this.data;
    }

    @Nullable
    public final String[] getIncompatibleData() {
        return this.incompatibleData;
    }

    @Nullable
    public final String[] getStrings() {
        return this.strings;
    }

    public KotlinClassHeader(@NotNull Kind kind, @NotNull JvmMetadataVersion metadataVersion, @NotNull JvmBytecodeBinaryVersion bytecodeVersion, @Nullable String[] data2, @Nullable String[] incompatibleData, @Nullable String[] strings, @Nullable String extraString, int extraInt, @Nullable String packageName) {
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(bytecodeVersion, "bytecodeVersion");
        this.kind = kind;
        this.metadataVersion = metadataVersion;
        this.bytecodeVersion = bytecodeVersion;
        this.data = data2;
        this.incompatibleData = incompatibleData;
        this.strings = strings;
        this.extraString = extraString;
        this.extraInt = extraInt;
        this.packageName = packageName;
    }

    public static final class Kind
    extends Enum<Kind> {
        public static final /* enum */ Kind UNKNOWN;
        public static final /* enum */ Kind CLASS;
        public static final /* enum */ Kind FILE_FACADE;
        public static final /* enum */ Kind SYNTHETIC_CLASS;
        public static final /* enum */ Kind MULTIFILE_CLASS;
        public static final /* enum */ Kind MULTIFILE_CLASS_PART;
        private static final /* synthetic */ Kind[] $VALUES;
        private final int id;
        private static final Map<Integer, Kind> entryById;
        public static final Companion Companion;

        /*
         * WARNING - void declaration
         */
        static {
            Map map2;
            void $this$associateByTo$iv$iv;
            void $this$associateBy$iv;
            UNKNOWN = new Kind(0);
            CLASS = new Kind(1);
            FILE_FACADE = new Kind(2);
            SYNTHETIC_CLASS = new Kind(3);
            MULTIFILE_CLASS = new Kind(4);
            MULTIFILE_CLASS_PART = new Kind(5);
            $VALUES = new Kind[]{UNKNOWN, CLASS, FILE_FACADE, SYNTHETIC_CLASS, MULTIFILE_CLASS, MULTIFILE_CLASS_PART};
            Companion = new Companion(null);
            Kind[] kindArray = Kind.values();
            Kind[] kindArray2 = $VALUES;
            boolean $i$f$associateBy = false;
            int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(((void)$this$associateBy$iv).length), 16);
            void var3_4 = $this$associateBy$iv;
            Map destination$iv$iv = new LinkedHashMap(capacity$iv);
            boolean $i$f$associateByTo = false;
            void var6_7 = $this$associateByTo$iv$iv;
            int n = ((void)var6_7).length;
            for (int i = 0; i < n; ++i) {
                void receiver;
                void element$iv$iv;
                void var10_11 = element$iv$iv = var6_7[i];
                map2 = destination$iv$iv;
                boolean bl = false;
                Integer n2 = receiver.id;
                map2.put(n2, element$iv$iv);
            }
            map2 = destination$iv$iv;
            Kind[] kindArray3 = kindArray2;
            entryById = map2;
        }

        private Kind(int id) {
            this.id = id;
        }

        public static Kind[] values() {
            return (Kind[])$VALUES.clone();
        }

        public static Kind valueOf(String string) {
            return Enum.valueOf(Kind.class, string);
        }

        @JvmStatic
        @NotNull
        public static final Kind getById(int id) {
            return Companion.getById(id);
        }

        public static final class Companion {
            @JvmStatic
            @NotNull
            public final Kind getById(int id) {
                Kind kind = (Kind)((Object)entryById.get(id));
                if (kind == null) {
                    kind = UNKNOWN;
                }
                return kind;
            }

            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }
    }
}

