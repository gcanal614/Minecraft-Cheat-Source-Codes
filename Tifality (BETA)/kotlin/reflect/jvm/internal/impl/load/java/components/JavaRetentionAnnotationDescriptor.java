/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaAnnotationMapper;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaAnnotationTargetMapper;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import org.jetbrains.annotations.NotNull;

public final class JavaRetentionAnnotationDescriptor
extends JavaAnnotationDescriptor {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    @NotNull
    private final NotNullLazyValue allValueArguments$delegate;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JavaRetentionAnnotationDescriptor.class), "allValueArguments", "getAllValueArguments()Ljava/util/Map;"))};
    }

    @Override
    @NotNull
    public Map<Name, ConstantValue<?>> getAllValueArguments() {
        return (Map)StorageKt.getValue(this.allValueArguments$delegate, (Object)this, $$delegatedProperties[0]);
    }

    public JavaRetentionAnnotationDescriptor(@NotNull JavaAnnotation annotation, @NotNull LazyJavaResolverContext c) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(c, "c");
        FqName fqName2 = KotlinBuiltIns.FQ_NAMES.retention;
        Intrinsics.checkNotNullExpressionValue(fqName2, "KotlinBuiltIns.FQ_NAMES.retention");
        super(c, annotation, fqName2);
        this.allValueArguments$delegate = c.getStorageManager().createLazyValue(new Function0<Map<Name, ? extends ConstantValue<?>>>(this){
            final /* synthetic */ JavaRetentionAnnotationDescriptor this$0;

            @NotNull
            public final Map<Name, ConstantValue<?>> invoke() {
                Map<Name, ConstantValue<?>> map2;
                boolean bl;
                ConstantValue<?> constantValue;
                ConstantValue<?> retentionArgument;
                ConstantValue<?> constantValue2 = retentionArgument = JavaAnnotationTargetMapper.INSTANCE.mapJavaRetentionArgument$descriptors_jvm(this.this$0.getFirstArgument());
                if (constantValue2 != null) {
                    constantValue = constantValue2;
                    bl = false;
                    boolean bl2 = false;
                    ConstantValue<?> it = constantValue;
                    boolean bl3 = false;
                    map2 = MapsKt.mapOf(TuplesKt.to(JavaAnnotationMapper.INSTANCE.getRETENTION_ANNOTATION_VALUE$descriptors_jvm(), it));
                } else {
                    map2 = null;
                }
                constantValue = map2;
                bl = false;
                ConstantValue<?> constantValue3 = constantValue;
                if (constantValue3 == null) {
                    constantValue3 = MapsKt.emptyMap();
                }
                return constantValue3;
            }
            {
                this.this$0 = javaRetentionAnnotationDescriptor;
                super(0);
            }
        });
    }
}

