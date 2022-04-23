/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindExclude;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DescriptorKindFilter {
    private final int kindMask;
    @NotNull
    private final List<DescriptorKindExclude> excludes;
    private static int nextMaskValue;
    private static final int NON_SINGLETON_CLASSIFIERS_MASK;
    private static final int SINGLETON_CLASSIFIERS_MASK;
    private static final int TYPE_ALIASES_MASK;
    private static final int PACKAGES_MASK;
    private static final int FUNCTIONS_MASK;
    private static final int VARIABLES_MASK;
    private static final int ALL_KINDS_MASK;
    private static final int CLASSIFIERS_MASK;
    private static final int VALUES_MASK;
    private static final int CALLABLES_MASK;
    @JvmField
    @NotNull
    public static final DescriptorKindFilter ALL;
    @JvmField
    @NotNull
    public static final DescriptorKindFilter CALLABLES;
    @JvmField
    @NotNull
    public static final DescriptorKindFilter NON_SINGLETON_CLASSIFIERS;
    @JvmField
    @NotNull
    public static final DescriptorKindFilter SINGLETON_CLASSIFIERS;
    @JvmField
    @NotNull
    public static final DescriptorKindFilter TYPE_ALIASES;
    @JvmField
    @NotNull
    public static final DescriptorKindFilter CLASSIFIERS;
    @JvmField
    @NotNull
    public static final DescriptorKindFilter PACKAGES;
    @JvmField
    @NotNull
    public static final DescriptorKindFilter FUNCTIONS;
    @JvmField
    @NotNull
    public static final DescriptorKindFilter VARIABLES;
    @JvmField
    @NotNull
    public static final DescriptorKindFilter VALUES;
    private static final List<Companion.MaskToName> DEBUG_PREDEFINED_FILTERS_MASK_NAMES;
    private static final List<Companion.MaskToName> DEBUG_MASK_BIT_NAMES;
    public static final Companion Companion;

    public final int getKindMask() {
        return this.kindMask;
    }

    public final boolean acceptsKinds(int kinds) {
        return (this.kindMask & kinds) != 0;
    }

    @Nullable
    public final DescriptorKindFilter restrictedToKindsOrNull(int kinds) {
        int mask = this.kindMask & kinds;
        if (mask == 0) {
            return null;
        }
        return new DescriptorKindFilter(mask, this.excludes);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public String toString() {
        Object v0;
        block3: {
            Iterable $this$firstOrNull$iv = DEBUG_PREDEFINED_FILTERS_MASK_NAMES;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                Companion.MaskToName it = (Companion.MaskToName)element$iv;
                boolean bl = false;
                if (!(it.getMask() == this.kindMask)) continue;
                v0 = element$iv;
                break block3;
            }
            v0 = null;
        }
        Companion.MaskToName maskToName = v0;
        String predefinedFilterName = maskToName != null ? maskToName.getName() : null;
        String string = predefinedFilterName;
        if (string == null) {
            void $this$mapNotNullTo$iv$iv;
            Object element$iv;
            Iterable $this$mapNotNull$iv = DEBUG_MASK_BIT_NAMES;
            boolean $i$f$mapNotNull = false;
            element$iv = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
            while (iterator2.hasNext()) {
                String string2;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = iterator2.next();
                boolean bl = false;
                Companion.MaskToName it = (Companion.MaskToName)element$iv$iv;
                boolean bl2 = false;
                if ((this.acceptsKinds(it.getMask()) ? it.getName() : null) == null) continue;
                string2 = string2;
                boolean bl3 = false;
                boolean bl4 = false;
                String it$iv$iv = string2;
                boolean bl5 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            string = CollectionsKt.joinToString$default((List)destination$iv$iv, " | ", null, null, 0, null, null, 62, null);
        }
        String kindString = string;
        return "DescriptorKindFilter(" + kindString + ", " + this.excludes + ')';
    }

    @NotNull
    public final List<DescriptorKindExclude> getExcludes() {
        return this.excludes;
    }

    public DescriptorKindFilter(int kindMask, @NotNull List<? extends DescriptorKindExclude> excludes) {
        Intrinsics.checkNotNullParameter(excludes, "excludes");
        this.excludes = excludes;
        int mask = kindMask;
        Iterable $this$forEach$iv = this.excludes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DescriptorKindExclude it = (DescriptorKindExclude)element$iv;
            boolean bl = false;
            mask &= ~it.getFullyExcludedDescriptorKinds();
        }
        this.kindMask = mask;
    }

    public /* synthetic */ DescriptorKindFilter(int n, List list, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 2) != 0) {
            boolean bl = false;
            list = CollectionsKt.emptyList();
        }
        this(n, list);
    }

    /*
     * WARNING - void declaration
     */
    static {
        void var3_6;
        Iterable $this$filterTo$iv$iv;
        void $this$filterTo$iv$iv$iv;
        boolean bl;
        Field field;
        void $this$mapNotNullTo$iv$iv;
        boolean bl2;
        Iterator it$iv;
        void $this$filterTo$iv$iv$iv2;
        Companion = new Companion(null);
        nextMaskValue = 1;
        NON_SINGLETON_CLASSIFIERS_MASK = DescriptorKindFilter.Companion.nextMask();
        SINGLETON_CLASSIFIERS_MASK = DescriptorKindFilter.Companion.nextMask();
        TYPE_ALIASES_MASK = DescriptorKindFilter.Companion.nextMask();
        PACKAGES_MASK = DescriptorKindFilter.Companion.nextMask();
        FUNCTIONS_MASK = DescriptorKindFilter.Companion.nextMask();
        VARIABLES_MASK = DescriptorKindFilter.Companion.nextMask();
        ALL_KINDS_MASK = DescriptorKindFilter.Companion.nextMask() - 1;
        CLASSIFIERS_MASK = NON_SINGLETON_CLASSIFIERS_MASK | SINGLETON_CLASSIFIERS_MASK | TYPE_ALIASES_MASK;
        VALUES_MASK = SINGLETON_CLASSIFIERS_MASK | FUNCTIONS_MASK | VARIABLES_MASK;
        CALLABLES_MASK = FUNCTIONS_MASK | VARIABLES_MASK;
        ALL = new DescriptorKindFilter(ALL_KINDS_MASK, null, 2, null);
        CALLABLES = new DescriptorKindFilter(CALLABLES_MASK, null, 2, null);
        NON_SINGLETON_CLASSIFIERS = new DescriptorKindFilter(NON_SINGLETON_CLASSIFIERS_MASK, null, 2, null);
        SINGLETON_CLASSIFIERS = new DescriptorKindFilter(SINGLETON_CLASSIFIERS_MASK, null, 2, null);
        TYPE_ALIASES = new DescriptorKindFilter(TYPE_ALIASES_MASK, null, 2, null);
        CLASSIFIERS = new DescriptorKindFilter(CLASSIFIERS_MASK, null, 2, null);
        PACKAGES = new DescriptorKindFilter(PACKAGES_MASK, null, 2, null);
        FUNCTIONS = new DescriptorKindFilter(FUNCTIONS_MASK, null, 2, null);
        VARIABLES = new DescriptorKindFilter(VARIABLES_MASK, null, 2, null);
        VALUES = new DescriptorKindFilter(VALUES_MASK, null, 2, null);
        Companion this_$iv = Companion;
        boolean $i$f$staticFields = false;
        Field[] fieldArray = DescriptorKindFilter.class.getFields();
        Intrinsics.checkNotNullExpressionValue(fieldArray, "T::class.java.fields");
        Object $this$filter$iv$iv = fieldArray;
        boolean $i$f$filter = false;
        Field[] fieldArray2 = $this$filter$iv$iv;
        Object destination$iv$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (void element$iv$iv$iv : $this$filterTo$iv$iv$iv2) {
            it$iv = element$iv$iv$iv;
            bl2 = false;
            void v1 = it$iv;
            Intrinsics.checkNotNullExpressionValue(v1, "it");
            if (!Modifier.isStatic(v1.getModifiers())) continue;
            destination$iv$iv$iv.add(element$iv$iv$iv);
        }
        Iterable $this$mapNotNull$iv = (List)destination$iv$iv$iv;
        boolean $i$f$mapNotNull = false;
        $this$filter$iv$iv = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo22 = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
        while (iterator2.hasNext()) {
            Companion.MaskToName maskToName;
            DescriptorKindFilter filter;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator2.next();
            boolean bl3 = false;
            field = (Field)element$iv$iv;
            boolean bl4 = false;
            Object object = field.get(null);
            if (!(object instanceof DescriptorKindFilter)) {
                object = null;
            }
            if ((filter = (DescriptorKindFilter)object) != null) {
                int n = filter.kindMask;
                Field field2 = field;
                Intrinsics.checkNotNullExpressionValue(field2, "field");
                String string = field2.getName();
                Intrinsics.checkNotNullExpressionValue(string, "field.name");
                maskToName = new Companion.MaskToName(n, string);
            } else {
                maskToName = null;
            }
            if (maskToName == null) continue;
            Companion.MaskToName maskToName2 = maskToName;
            boolean bl5 = false;
            bl = false;
            Companion.MaskToName it$iv$iv = maskToName2;
            boolean bl6 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        DEBUG_PREDEFINED_FILTERS_MASK_NAMES = (List)destination$iv$iv;
        this_$iv = Companion;
        $i$f$staticFields = false;
        Field[] fieldArray3 = DescriptorKindFilter.class.getFields();
        Intrinsics.checkNotNullExpressionValue(fieldArray3, "T::class.java.fields");
        $this$filter$iv$iv = fieldArray3;
        $i$f$filter = false;
        Object $i$f$mapNotNullTo22 = $this$filter$iv$iv;
        destination$iv$iv$iv = new ArrayList();
        $i$f$filterTo = false;
        iterator2 = $this$filterTo$iv$iv$iv;
        int element$iv$iv$iv = ((Iterator<T>)iterator2).length;
        for (int element$iv$iv = 0; element$iv$iv < element$iv$iv$iv; ++element$iv$iv) {
            Iterator element$iv$iv$iv2;
            it$iv = element$iv$iv$iv2 = iterator2[element$iv$iv];
            bl2 = false;
            Iterator iterator3 = it$iv;
            Intrinsics.checkNotNullExpressionValue(iterator3, "it");
            if (!Modifier.isStatic(((Field)((Object)iterator3)).getModifiers())) continue;
            destination$iv$iv$iv.add(element$iv$iv$iv2);
        }
        Iterable $this$filter$iv = (List)destination$iv$iv$iv;
        boolean $i$f$filter2 = false;
        $this$filter$iv$iv = $this$filter$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo2 = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Field it = (Field)element$iv$iv;
            boolean bl7 = false;
            Field field3 = it;
            Intrinsics.checkNotNullExpressionValue(field3, "it");
            if (!Intrinsics.areEqual(field3.getType(), Integer.TYPE)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$mapNotNull$iv = (List)destination$iv$iv;
        $i$f$mapNotNull = false;
        $this$filterTo$iv$iv = $this$mapNotNull$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        $i$f$forEach = false;
        iterator2 = $this$forEach$iv$iv$iv.iterator();
        while (iterator2.hasNext()) {
            Companion.MaskToName maskToName;
            boolean isOneBitMask;
            Object element$iv$iv = element$iv$iv$iv = iterator2.next();
            boolean bl8 = false;
            field = (Field)element$iv$iv;
            boolean bl9 = false;
            Object object = field.get(null);
            if (object == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Int");
            }
            int mask = (Integer)object;
            boolean bl10 = isOneBitMask = mask == (mask & -mask);
            if (isOneBitMask) {
                Field field4 = field;
                Intrinsics.checkNotNullExpressionValue(field4, "field");
                String string = field4.getName();
                Intrinsics.checkNotNullExpressionValue(string, "field.name");
                maskToName = new Companion.MaskToName(mask, string);
            } else {
                maskToName = null;
            }
            if (maskToName == null) continue;
            Companion.MaskToName maskToName3 = maskToName;
            bl = false;
            boolean bl11 = false;
            Companion.MaskToName it$iv$iv = maskToName3;
            boolean bl12 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        DEBUG_MASK_BIT_NAMES = (List)var3_6;
    }

    public static final class Companion {
        private final int nextMask() {
            int n = nextMaskValue;
            boolean bl = false;
            boolean bl2 = false;
            int $this$apply = n;
            boolean bl3 = false;
            nextMaskValue = nextMaskValue << 1;
            return n;
        }

        public final int getNON_SINGLETON_CLASSIFIERS_MASK() {
            return NON_SINGLETON_CLASSIFIERS_MASK;
        }

        public final int getSINGLETON_CLASSIFIERS_MASK() {
            return SINGLETON_CLASSIFIERS_MASK;
        }

        public final int getTYPE_ALIASES_MASK() {
            return TYPE_ALIASES_MASK;
        }

        public final int getPACKAGES_MASK() {
            return PACKAGES_MASK;
        }

        public final int getFUNCTIONS_MASK() {
            return FUNCTIONS_MASK;
        }

        public final int getVARIABLES_MASK() {
            return VARIABLES_MASK;
        }

        public final int getALL_KINDS_MASK() {
            return ALL_KINDS_MASK;
        }

        public final int getCLASSIFIERS_MASK() {
            return CLASSIFIERS_MASK;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private static final class MaskToName {
            private final int mask;
            @NotNull
            private final String name;

            public final int getMask() {
                return this.mask;
            }

            @NotNull
            public final String getName() {
                return this.name;
            }

            public MaskToName(int mask, @NotNull String name) {
                Intrinsics.checkNotNullParameter(name, "name");
                this.mask = mask;
                this.name = name;
            }
        }
    }
}

