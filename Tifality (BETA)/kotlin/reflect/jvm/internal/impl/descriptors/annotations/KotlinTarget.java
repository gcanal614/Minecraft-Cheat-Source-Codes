/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.annotations.NotNull;

public final class KotlinTarget
extends Enum<KotlinTarget> {
    public static final /* enum */ KotlinTarget CLASS;
    public static final /* enum */ KotlinTarget ANNOTATION_CLASS;
    public static final /* enum */ KotlinTarget TYPE_PARAMETER;
    public static final /* enum */ KotlinTarget PROPERTY;
    public static final /* enum */ KotlinTarget FIELD;
    public static final /* enum */ KotlinTarget LOCAL_VARIABLE;
    public static final /* enum */ KotlinTarget VALUE_PARAMETER;
    public static final /* enum */ KotlinTarget CONSTRUCTOR;
    public static final /* enum */ KotlinTarget FUNCTION;
    public static final /* enum */ KotlinTarget PROPERTY_GETTER;
    public static final /* enum */ KotlinTarget PROPERTY_SETTER;
    public static final /* enum */ KotlinTarget TYPE;
    public static final /* enum */ KotlinTarget EXPRESSION;
    public static final /* enum */ KotlinTarget FILE;
    public static final /* enum */ KotlinTarget TYPEALIAS;
    public static final /* enum */ KotlinTarget TYPE_PROJECTION;
    public static final /* enum */ KotlinTarget STAR_PROJECTION;
    public static final /* enum */ KotlinTarget PROPERTY_PARAMETER;
    public static final /* enum */ KotlinTarget CLASS_ONLY;
    public static final /* enum */ KotlinTarget OBJECT;
    public static final /* enum */ KotlinTarget COMPANION_OBJECT;
    public static final /* enum */ KotlinTarget INTERFACE;
    public static final /* enum */ KotlinTarget ENUM_CLASS;
    public static final /* enum */ KotlinTarget ENUM_ENTRY;
    public static final /* enum */ KotlinTarget LOCAL_CLASS;
    public static final /* enum */ KotlinTarget LOCAL_FUNCTION;
    public static final /* enum */ KotlinTarget MEMBER_FUNCTION;
    public static final /* enum */ KotlinTarget TOP_LEVEL_FUNCTION;
    public static final /* enum */ KotlinTarget MEMBER_PROPERTY;
    public static final /* enum */ KotlinTarget MEMBER_PROPERTY_WITH_BACKING_FIELD;
    public static final /* enum */ KotlinTarget MEMBER_PROPERTY_WITH_DELEGATE;
    public static final /* enum */ KotlinTarget MEMBER_PROPERTY_WITHOUT_FIELD_OR_DELEGATE;
    public static final /* enum */ KotlinTarget TOP_LEVEL_PROPERTY;
    public static final /* enum */ KotlinTarget TOP_LEVEL_PROPERTY_WITH_BACKING_FIELD;
    public static final /* enum */ KotlinTarget TOP_LEVEL_PROPERTY_WITH_DELEGATE;
    public static final /* enum */ KotlinTarget TOP_LEVEL_PROPERTY_WITHOUT_FIELD_OR_DELEGATE;
    public static final /* enum */ KotlinTarget INITIALIZER;
    public static final /* enum */ KotlinTarget DESTRUCTURING_DECLARATION;
    public static final /* enum */ KotlinTarget LAMBDA_EXPRESSION;
    public static final /* enum */ KotlinTarget ANONYMOUS_FUNCTION;
    public static final /* enum */ KotlinTarget OBJECT_LITERAL;
    private static final /* synthetic */ KotlinTarget[] $VALUES;
    @NotNull
    private final String description;
    private final boolean isDefault;
    private static final HashMap<String, KotlinTarget> map;
    @NotNull
    private static final Set<KotlinTarget> DEFAULT_TARGET_SET;
    @NotNull
    private static final Set<KotlinTarget> ALL_TARGET_SET;
    @NotNull
    private static final Map<AnnotationUseSiteTarget, KotlinTarget> USE_SITE_MAPPING;
    public static final Companion Companion;

    /*
     * WARNING - void declaration
     */
    static {
        void var3_2;
        void $this$filterTo$iv$iv;
        CLASS = new KotlinTarget("CLASS", 0, "class", false, 2, null);
        ANNOTATION_CLASS = new KotlinTarget("ANNOTATION_CLASS", 1, "annotation class", false, 2, null);
        TYPE_PARAMETER = new KotlinTarget("type parameter", false);
        PROPERTY = new KotlinTarget("PROPERTY", 3, "property", false, 2, null);
        FIELD = new KotlinTarget("FIELD", 4, "field", false, 2, null);
        LOCAL_VARIABLE = new KotlinTarget("LOCAL_VARIABLE", 5, "local variable", false, 2, null);
        VALUE_PARAMETER = new KotlinTarget("VALUE_PARAMETER", 6, "value parameter", false, 2, null);
        CONSTRUCTOR = new KotlinTarget("CONSTRUCTOR", 7, "constructor", false, 2, null);
        FUNCTION = new KotlinTarget("FUNCTION", 8, "function", false, 2, null);
        PROPERTY_GETTER = new KotlinTarget("PROPERTY_GETTER", 9, "getter", false, 2, null);
        PROPERTY_SETTER = new KotlinTarget("PROPERTY_SETTER", 10, "setter", false, 2, null);
        TYPE = new KotlinTarget("type usage", false);
        EXPRESSION = new KotlinTarget("expression", false);
        FILE = new KotlinTarget("file", false);
        TYPEALIAS = new KotlinTarget("typealias", false);
        TYPE_PROJECTION = new KotlinTarget("type projection", false);
        STAR_PROJECTION = new KotlinTarget("star projection", false);
        PROPERTY_PARAMETER = new KotlinTarget("property constructor parameter", false);
        CLASS_ONLY = new KotlinTarget("class", false);
        OBJECT = new KotlinTarget("object", false);
        COMPANION_OBJECT = new KotlinTarget("companion object", false);
        INTERFACE = new KotlinTarget("interface", false);
        ENUM_CLASS = new KotlinTarget("enum class", false);
        ENUM_ENTRY = new KotlinTarget("enum entry", false);
        LOCAL_CLASS = new KotlinTarget("local class", false);
        LOCAL_FUNCTION = new KotlinTarget("local function", false);
        MEMBER_FUNCTION = new KotlinTarget("member function", false);
        TOP_LEVEL_FUNCTION = new KotlinTarget("top level function", false);
        MEMBER_PROPERTY = new KotlinTarget("member property", false);
        MEMBER_PROPERTY_WITH_BACKING_FIELD = new KotlinTarget("member property with backing field", false);
        MEMBER_PROPERTY_WITH_DELEGATE = new KotlinTarget("member property with delegate", false);
        MEMBER_PROPERTY_WITHOUT_FIELD_OR_DELEGATE = new KotlinTarget("member property without backing field or delegate", false);
        TOP_LEVEL_PROPERTY = new KotlinTarget("top level property", false);
        TOP_LEVEL_PROPERTY_WITH_BACKING_FIELD = new KotlinTarget("top level property with backing field", false);
        TOP_LEVEL_PROPERTY_WITH_DELEGATE = new KotlinTarget("top level property with delegate", false);
        TOP_LEVEL_PROPERTY_WITHOUT_FIELD_OR_DELEGATE = new KotlinTarget("top level property without backing field or delegate", false);
        INITIALIZER = new KotlinTarget("initializer", false);
        DESTRUCTURING_DECLARATION = new KotlinTarget("destructuring declaration", false);
        LAMBDA_EXPRESSION = new KotlinTarget("lambda expression", false);
        ANONYMOUS_FUNCTION = new KotlinTarget("anonymous function", false);
        OBJECT_LITERAL = new KotlinTarget("object literal", false);
        $VALUES = new KotlinTarget[]{CLASS, ANNOTATION_CLASS, TYPE_PARAMETER, PROPERTY, FIELD, LOCAL_VARIABLE, VALUE_PARAMETER, CONSTRUCTOR, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER, TYPE, EXPRESSION, FILE, TYPEALIAS, TYPE_PROJECTION, STAR_PROJECTION, PROPERTY_PARAMETER, CLASS_ONLY, OBJECT, COMPANION_OBJECT, INTERFACE, ENUM_CLASS, ENUM_ENTRY, LOCAL_CLASS, LOCAL_FUNCTION, MEMBER_FUNCTION, TOP_LEVEL_FUNCTION, MEMBER_PROPERTY, MEMBER_PROPERTY_WITH_BACKING_FIELD, MEMBER_PROPERTY_WITH_DELEGATE, MEMBER_PROPERTY_WITHOUT_FIELD_OR_DELEGATE, TOP_LEVEL_PROPERTY, TOP_LEVEL_PROPERTY_WITH_BACKING_FIELD, TOP_LEVEL_PROPERTY_WITH_DELEGATE, TOP_LEVEL_PROPERTY_WITHOUT_FIELD_OR_DELEGATE, INITIALIZER, DESTRUCTURING_DECLARATION, LAMBDA_EXPRESSION, ANONYMOUS_FUNCTION, OBJECT_LITERAL};
        Companion = new Companion(null);
        map = new HashMap();
        for (KotlinTarget kotlinTarget : KotlinTarget.values()) {
            ((Map)map).put(kotlinTarget.name(), kotlinTarget);
        }
        KotlinTarget[] kotlinTargetArray = KotlinTarget.values();
        KotlinTarget[] kotlinTargetArray2 = $VALUES;
        boolean $i$f$filter = false;
        KotlinTarget[] kotlinTargetArray22 = kotlinTargetArray;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        void var5_8 = $this$filterTo$iv$iv;
        int n = ((void)var5_8).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void it = element$iv$iv = var5_8[i];
            boolean bl = false;
            if (!it.isDefault) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List list = (List)var3_2;
        KotlinTarget[] kotlinTargetArray3 = kotlinTargetArray2;
        DEFAULT_TARGET_SET = CollectionsKt.toSet(list);
        ALL_TARGET_SET = ArraysKt.toSet(KotlinTarget.values());
        USE_SITE_MAPPING = MapsKt.mapOf(TuplesKt.to(AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER, VALUE_PARAMETER), TuplesKt.to(AnnotationUseSiteTarget.FIELD, FIELD), TuplesKt.to(AnnotationUseSiteTarget.PROPERTY, PROPERTY), TuplesKt.to(AnnotationUseSiteTarget.FILE, FILE), TuplesKt.to(AnnotationUseSiteTarget.PROPERTY_GETTER, PROPERTY_GETTER), TuplesKt.to(AnnotationUseSiteTarget.PROPERTY_SETTER, PROPERTY_SETTER), TuplesKt.to(AnnotationUseSiteTarget.RECEIVER, VALUE_PARAMETER), TuplesKt.to(AnnotationUseSiteTarget.SETTER_PARAMETER, VALUE_PARAMETER), TuplesKt.to(AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD, FIELD));
    }

    private KotlinTarget(String description2, boolean isDefault) {
        this.description = description2;
        this.isDefault = isDefault;
    }

    /* synthetic */ KotlinTarget(String string, int n, String string2, boolean bl, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 2) != 0) {
            bl = true;
        }
        this(string2, bl);
    }

    public static KotlinTarget[] values() {
        return (KotlinTarget[])$VALUES.clone();
    }

    public static KotlinTarget valueOf(String string) {
        return Enum.valueOf(KotlinTarget.class, string);
    }

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

