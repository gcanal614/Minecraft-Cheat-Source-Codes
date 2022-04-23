/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

public final class BuiltInAnnotationDescriptor
implements AnnotationDescriptor {
    @NotNull
    private final Lazy type$delegate;
    private final KotlinBuiltIns builtIns;
    @NotNull
    private final FqName fqName;
    @NotNull
    private final Map<Name, ConstantValue<?>> allValueArguments;

    @Override
    @NotNull
    public KotlinType getType() {
        Lazy lazy = this.type$delegate;
        BuiltInAnnotationDescriptor builtInAnnotationDescriptor = this;
        Object var3_3 = null;
        boolean bl = false;
        return (KotlinType)lazy.getValue();
    }

    @Override
    @NotNull
    public SourceElement getSource() {
        SourceElement sourceElement = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(sourceElement, "SourceElement.NO_SOURCE");
        return sourceElement;
    }

    @Override
    @NotNull
    public FqName getFqName() {
        return this.fqName;
    }

    @Override
    @NotNull
    public Map<Name, ConstantValue<?>> getAllValueArguments() {
        return this.allValueArguments;
    }

    public BuiltInAnnotationDescriptor(@NotNull KotlinBuiltIns builtIns, @NotNull FqName fqName2, @NotNull Map<Name, ? extends ConstantValue<?>> allValueArguments2) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        Intrinsics.checkNotNullParameter(allValueArguments2, "allValueArguments");
        this.builtIns = builtIns;
        this.fqName = fqName2;
        this.allValueArguments = allValueArguments2;
        this.type$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0)new Function0<SimpleType>(this){
            final /* synthetic */ BuiltInAnnotationDescriptor this$0;

            @NotNull
            public final SimpleType invoke() {
                ClassDescriptor classDescriptor = BuiltInAnnotationDescriptor.access$getBuiltIns$p(this.this$0).getBuiltInClassByFqName(this.this$0.getFqName());
                Intrinsics.checkNotNullExpressionValue(classDescriptor, "builtIns.getBuiltInClassByFqName(fqName)");
                return classDescriptor.getDefaultType();
            }
            {
                this.this$0 = builtInAnnotationDescriptor;
                super(0);
            }
        });
    }

    public static final /* synthetic */ KotlinBuiltIns access$getBuiltIns$p(BuiltInAnnotationDescriptor $this) {
        return $this.builtIns;
    }
}

