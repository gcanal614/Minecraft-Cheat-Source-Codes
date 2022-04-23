/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.PossiblyExternalAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaElement;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaAnnotationDescriptor
implements AnnotationDescriptor,
PossiblyExternalAnnotationDescriptor {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    @NotNull
    private final SourceElement source;
    @NotNull
    private final NotNullLazyValue type$delegate;
    @Nullable
    private final JavaAnnotationArgument firstArgument;
    private final boolean isIdeExternalAnnotation;
    @NotNull
    private final FqName fqName;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JavaAnnotationDescriptor.class), "type", "getType()Lorg/jetbrains/kotlin/types/SimpleType;"))};
    }

    @Override
    @NotNull
    public SourceElement getSource() {
        return this.source;
    }

    @Override
    @NotNull
    public SimpleType getType() {
        return (SimpleType)StorageKt.getValue(this.type$delegate, (Object)this, $$delegatedProperties[0]);
    }

    @Nullable
    protected final JavaAnnotationArgument getFirstArgument() {
        return this.firstArgument;
    }

    @Override
    @NotNull
    public Map<Name, ConstantValue<?>> getAllValueArguments() {
        return MapsKt.emptyMap();
    }

    @Override
    public boolean isIdeExternalAnnotation() {
        return this.isIdeExternalAnnotation;
    }

    @Override
    @NotNull
    public FqName getFqName() {
        return this.fqName;
    }

    /*
     * Unable to fully structure code
     */
    public JavaAnnotationDescriptor(@NotNull LazyJavaResolverContext c, @Nullable JavaAnnotation annotation, @NotNull FqName fqName) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        super();
        this.fqName = fqName;
        v0 = this;
        v1 = annotation;
        if (v1 == null) ** GOTO lbl-1000
        var4_4 = v1;
        var5_5 = false;
        var6_6 = false;
        var7_7 = var4_4;
        var9_8 = v0;
        $i$a$-let-JavaAnnotationDescriptor$source$1 = false;
        var10_10 = c.getComponents().getSourceElementFactory().source((JavaElement)it);
        v0 = var9_8;
        v1 = var10_10;
        if (v1 != null) {
            v2 = (SourceElement)v1;
        } else lbl-1000:
        // 2 sources

        {
            v3 = SourceElement.NO_SOURCE;
            v2 = v3;
            Intrinsics.checkNotNullExpressionValue(v3, "SourceElement.NO_SOURCE");
        }
        v0.source = v2;
        this.type$delegate = c.getStorageManager().createLazyValue((Function0)new Function0<SimpleType>(this, c){
            final /* synthetic */ JavaAnnotationDescriptor this$0;
            final /* synthetic */ LazyJavaResolverContext $c;

            @NotNull
            public final SimpleType invoke() {
                ClassDescriptor classDescriptor = this.$c.getModule().getBuiltIns().getBuiltInClassByFqName(this.this$0.getFqName());
                Intrinsics.checkNotNullExpressionValue(classDescriptor, "c.module.builtIns.getBuiltInClassByFqName(fqName)");
                SimpleType simpleType2 = classDescriptor.getDefaultType();
                Intrinsics.checkNotNullExpressionValue(simpleType2, "c.module.builtIns.getBui\u2026qName(fqName).defaultType");
                return simpleType2;
            }
            {
                this.this$0 = javaAnnotationDescriptor;
                this.$c = lazyJavaResolverContext;
                super(0);
            }
        });
        v4 = annotation;
        this.firstArgument = v4 != null && (v4 = v4.getArguments()) != null ? (JavaAnnotationArgument)CollectionsKt.firstOrNull((Iterable)v4) : null;
        v5 = annotation;
        this.isIdeExternalAnnotation = v5 != null && v5.isIdeExternalAnnotation() == true;
    }
}

