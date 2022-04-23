/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public abstract class SimpleType
extends UnwrappedType
implements SimpleTypeMarker,
TypeArgumentListMarker {
    @Override
    @NotNull
    public abstract SimpleType replaceAnnotations(@NotNull Annotations var1);

    @Override
    @NotNull
    public abstract SimpleType makeNullableAsSpecified(boolean var1);

    @NotNull
    public String toString() {
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl3 = false;
        boolean bl4 = false;
        StringBuilder $this$buildString = stringBuilder;
        boolean bl5 = false;
        for (AnnotationDescriptor annotation : this.getAnnotations()) {
            StringsKt.append($this$buildString, "[", DescriptorRenderer.renderAnnotation$default(DescriptorRenderer.DEBUG_TEXT, annotation, null, 2, null), "] ");
        }
        $this$buildString.append(this.getConstructor());
        Collection collection = this.getArguments();
        boolean bl6 = false;
        if (!collection.isEmpty()) {
            CollectionsKt.joinTo$default(this.getArguments(), $this$buildString, ", ", "<", ">", 0, null, null, 112, null);
        }
        if (this.isMarkedNullable()) {
            $this$buildString.append("?");
        }
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    public SimpleType() {
        super(null);
    }
}

