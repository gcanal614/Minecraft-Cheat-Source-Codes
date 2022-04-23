/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
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
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaEnumValueAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import org.jetbrains.annotations.NotNull;

public final class JavaTargetAnnotationDescriptor
extends JavaAnnotationDescriptor {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    @NotNull
    private final NotNullLazyValue allValueArguments$delegate;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JavaTargetAnnotationDescriptor.class), "allValueArguments", "getAllValueArguments()Ljava/util/Map;"))};
    }

    @NotNull
    public Map<Name, ConstantValue<Object>> getAllValueArguments() {
        return (Map)StorageKt.getValue(this.allValueArguments$delegate, (Object)this, $$delegatedProperties[0]);
    }

    public JavaTargetAnnotationDescriptor(@NotNull JavaAnnotation annotation, @NotNull LazyJavaResolverContext c) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(c, "c");
        FqName fqName2 = KotlinBuiltIns.FQ_NAMES.target;
        Intrinsics.checkNotNullExpressionValue(fqName2, "KotlinBuiltIns.FQ_NAMES.target");
        super(c, annotation, fqName2);
        this.allValueArguments$delegate = c.getStorageManager().createLazyValue((Function0)new Function0<Map<Name, ? extends ConstantValue<? extends Object>>>(this){
            final /* synthetic */ JavaTargetAnnotationDescriptor this$0;

            @NotNull
            public final Map<Name, ConstantValue<Object>> invoke() {
                Map<Name, Map<K, V>> map2;
                boolean bl;
                ConstantValue<?> targetArgument;
                Object object = this.this$0.getFirstArgument();
                ConstantValue<?> constantValue = targetArgument = object instanceof JavaArrayAnnotationArgument ? JavaAnnotationTargetMapper.INSTANCE.mapJavaTargetArguments$descriptors_jvm(((JavaArrayAnnotationArgument)this.this$0.getFirstArgument()).getElements()) : (object instanceof JavaEnumValueAnnotationArgument ? JavaAnnotationTargetMapper.INSTANCE.mapJavaTargetArguments$descriptors_jvm(CollectionsKt.listOf(this.this$0.getFirstArgument())) : null);
                if (constantValue != null) {
                    object = constantValue;
                    bl = false;
                    boolean bl2 = false;
                    Map<K, V> it = object;
                    boolean bl3 = false;
                    map2 = MapsKt.mapOf(TuplesKt.to(JavaAnnotationMapper.INSTANCE.getTARGET_ANNOTATION_ALLOWED_TARGETS$descriptors_jvm(), it));
                } else {
                    map2 = null;
                }
                object = map2;
                bl = false;
                Object object2 = object;
                if (object2 == null) {
                    object2 = MapsKt.emptyMap();
                }
                return object2;
            }
            {
                this.this$0 = javaTargetAnnotationDescriptor;
                super(0);
            }
        });
    }
}

