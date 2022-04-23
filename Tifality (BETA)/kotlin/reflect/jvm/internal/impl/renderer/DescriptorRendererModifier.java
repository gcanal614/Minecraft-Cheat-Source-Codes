/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

public final class DescriptorRendererModifier
extends Enum<DescriptorRendererModifier> {
    public static final /* enum */ DescriptorRendererModifier VISIBILITY;
    public static final /* enum */ DescriptorRendererModifier MODALITY;
    public static final /* enum */ DescriptorRendererModifier OVERRIDE;
    public static final /* enum */ DescriptorRendererModifier ANNOTATIONS;
    public static final /* enum */ DescriptorRendererModifier INNER;
    public static final /* enum */ DescriptorRendererModifier MEMBER_KIND;
    public static final /* enum */ DescriptorRendererModifier DATA;
    public static final /* enum */ DescriptorRendererModifier INLINE;
    public static final /* enum */ DescriptorRendererModifier EXPECT;
    public static final /* enum */ DescriptorRendererModifier ACTUAL;
    public static final /* enum */ DescriptorRendererModifier CONST;
    public static final /* enum */ DescriptorRendererModifier LATEINIT;
    public static final /* enum */ DescriptorRendererModifier FUN;
    private static final /* synthetic */ DescriptorRendererModifier[] $VALUES;
    private final boolean includeByDefault;
    @JvmField
    @NotNull
    public static final Set<DescriptorRendererModifier> ALL_EXCEPT_ANNOTATIONS;
    @JvmField
    @NotNull
    public static final Set<DescriptorRendererModifier> ALL;
    public static final Companion Companion;

    /*
     * WARNING - void declaration
     */
    static {
        void var3_4;
        void $this$filterTo$iv$iv;
        void $this$filter$iv;
        VISIBILITY = new DescriptorRendererModifier(true);
        MODALITY = new DescriptorRendererModifier(true);
        OVERRIDE = new DescriptorRendererModifier(true);
        ANNOTATIONS = new DescriptorRendererModifier(false);
        INNER = new DescriptorRendererModifier(true);
        MEMBER_KIND = new DescriptorRendererModifier(true);
        DATA = new DescriptorRendererModifier(true);
        INLINE = new DescriptorRendererModifier(true);
        EXPECT = new DescriptorRendererModifier(true);
        ACTUAL = new DescriptorRendererModifier(true);
        CONST = new DescriptorRendererModifier(true);
        LATEINIT = new DescriptorRendererModifier(true);
        FUN = new DescriptorRendererModifier(true);
        $VALUES = new DescriptorRendererModifier[]{VISIBILITY, MODALITY, OVERRIDE, ANNOTATIONS, INNER, MEMBER_KIND, DATA, INLINE, EXPECT, ACTUAL, CONST, LATEINIT, FUN};
        Companion = new Companion(null);
        DescriptorRendererModifier[] descriptorRendererModifierArray = DescriptorRendererModifier.values();
        DescriptorRendererModifier[] descriptorRendererModifierArray2 = $VALUES;
        boolean $i$f$filter = false;
        void var2_3 = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        void var5_6 = $this$filterTo$iv$iv;
        int n = ((void)var5_6).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void it = element$iv$iv = var5_6[i];
            boolean bl = false;
            if (!it.includeByDefault) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List list = (List)var3_4;
        DescriptorRendererModifier[] descriptorRendererModifierArray3 = descriptorRendererModifierArray2;
        ALL_EXCEPT_ANNOTATIONS = CollectionsKt.toSet(list);
        ALL = ArraysKt.toSet(DescriptorRendererModifier.values());
    }

    private DescriptorRendererModifier(boolean includeByDefault) {
        this.includeByDefault = includeByDefault;
    }

    public static DescriptorRendererModifier[] values() {
        return (DescriptorRendererModifier[])$VALUES.clone();
    }

    public static DescriptorRendererModifier valueOf(String string) {
        return Enum.valueOf(DescriptorRendererModifier.class, string);
    }

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

