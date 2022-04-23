/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class AnnotationUseSiteTarget
extends Enum<AnnotationUseSiteTarget> {
    public static final /* enum */ AnnotationUseSiteTarget FIELD;
    public static final /* enum */ AnnotationUseSiteTarget FILE;
    public static final /* enum */ AnnotationUseSiteTarget PROPERTY;
    public static final /* enum */ AnnotationUseSiteTarget PROPERTY_GETTER;
    public static final /* enum */ AnnotationUseSiteTarget PROPERTY_SETTER;
    public static final /* enum */ AnnotationUseSiteTarget RECEIVER;
    public static final /* enum */ AnnotationUseSiteTarget CONSTRUCTOR_PARAMETER;
    public static final /* enum */ AnnotationUseSiteTarget SETTER_PARAMETER;
    public static final /* enum */ AnnotationUseSiteTarget PROPERTY_DELEGATE_FIELD;
    private static final /* synthetic */ AnnotationUseSiteTarget[] $VALUES;
    @NotNull
    private final String renderName;

    static {
        AnnotationUseSiteTarget[] annotationUseSiteTargetArray = new AnnotationUseSiteTarget[9];
        AnnotationUseSiteTarget[] annotationUseSiteTargetArray2 = annotationUseSiteTargetArray;
        annotationUseSiteTargetArray[0] = FIELD = new AnnotationUseSiteTarget("FIELD", 0, null, 1, null);
        annotationUseSiteTargetArray[1] = FILE = new AnnotationUseSiteTarget("FILE", 1, null, 1, null);
        annotationUseSiteTargetArray[2] = PROPERTY = new AnnotationUseSiteTarget("PROPERTY", 2, null, 1, null);
        annotationUseSiteTargetArray[3] = PROPERTY_GETTER = new AnnotationUseSiteTarget("get");
        annotationUseSiteTargetArray[4] = PROPERTY_SETTER = new AnnotationUseSiteTarget("set");
        annotationUseSiteTargetArray[5] = RECEIVER = new AnnotationUseSiteTarget("RECEIVER", 5, null, 1, null);
        annotationUseSiteTargetArray[6] = CONSTRUCTOR_PARAMETER = new AnnotationUseSiteTarget("param");
        annotationUseSiteTargetArray[7] = SETTER_PARAMETER = new AnnotationUseSiteTarget("setparam");
        annotationUseSiteTargetArray[8] = PROPERTY_DELEGATE_FIELD = new AnnotationUseSiteTarget("delegate");
        $VALUES = annotationUseSiteTargetArray;
    }

    @NotNull
    public final String getRenderName() {
        return this.renderName;
    }

    private AnnotationUseSiteTarget(String renderName) {
        String string = renderName;
        if (string == null) {
            String string2 = this.name();
            boolean bl = false;
            String string3 = string2;
            if (string3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string4 = string3.toLowerCase();
            string = string4;
            Intrinsics.checkNotNullExpressionValue(string4, "(this as java.lang.String).toLowerCase()");
        }
        this.renderName = string;
    }

    /* synthetic */ AnnotationUseSiteTarget(String string, int n, String string2, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            string2 = null;
        }
        this(string2);
    }

    public static AnnotationUseSiteTarget[] values() {
        return (AnnotationUseSiteTarget[])$VALUES.clone();
    }

    public static AnnotationUseSiteTarget valueOf(String string) {
        return Enum.valueOf(AnnotationUseSiteTarget.class, string);
    }
}

