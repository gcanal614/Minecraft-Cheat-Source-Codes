/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.java.components.DescriptorResolverUtils;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.PossiblyExternalAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolverKt;
import kotlin.reflect.jvm.internal.impl.load.java.sources.JavaSourceElement;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationAsAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassObjectAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaEnumValueAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaLiteralAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValueFactory;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.NullValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.NullableLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LazyJavaAnnotationDescriptor
implements AnnotationDescriptor,
PossiblyExternalAnnotationDescriptor {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    @Nullable
    private final NullableLazyValue fqName$delegate;
    @NotNull
    private final NotNullLazyValue type$delegate;
    @NotNull
    private final JavaSourceElement source;
    @NotNull
    private final NotNullLazyValue allValueArguments$delegate;
    private final boolean isIdeExternalAnnotation;
    private final LazyJavaResolverContext c;
    private final JavaAnnotation javaAnnotation;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaAnnotationDescriptor.class), "fqName", "getFqName()Lorg/jetbrains/kotlin/name/FqName;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaAnnotationDescriptor.class), "type", "getType()Lorg/jetbrains/kotlin/types/SimpleType;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaAnnotationDescriptor.class), "allValueArguments", "getAllValueArguments()Ljava/util/Map;"))};
    }

    @Override
    @Nullable
    public FqName getFqName() {
        return (FqName)StorageKt.getValue(this.fqName$delegate, (Object)this, $$delegatedProperties[0]);
    }

    @Override
    @NotNull
    public SimpleType getType() {
        return (SimpleType)StorageKt.getValue(this.type$delegate, (Object)this, $$delegatedProperties[1]);
    }

    @Override
    @NotNull
    public JavaSourceElement getSource() {
        return this.source;
    }

    @Override
    @NotNull
    public Map<Name, ConstantValue<?>> getAllValueArguments() {
        return (Map)StorageKt.getValue(this.allValueArguments$delegate, (Object)this, $$delegatedProperties[2]);
    }

    private final ConstantValue<?> resolveAnnotationArgument(JavaAnnotationArgument argument) {
        ConstantValue<Object> constantValue;
        JavaAnnotationArgument javaAnnotationArgument = argument;
        if (javaAnnotationArgument instanceof JavaLiteralAnnotationArgument) {
            constantValue = ConstantValueFactory.INSTANCE.createConstantValue(((JavaLiteralAnnotationArgument)argument).getValue());
        } else if (javaAnnotationArgument instanceof JavaEnumValueAnnotationArgument) {
            constantValue = this.resolveFromEnumValue(((JavaEnumValueAnnotationArgument)argument).getEnumClassId(), ((JavaEnumValueAnnotationArgument)argument).getEntryName());
        } else if (javaAnnotationArgument instanceof JavaArrayAnnotationArgument) {
            Name name = argument.getName();
            if (name == null) {
                name = JvmAnnotationNames.DEFAULT_ANNOTATION_MEMBER_NAME;
            }
            Intrinsics.checkNotNullExpressionValue(name, "argument.name ?: DEFAULT_ANNOTATION_MEMBER_NAME");
            constantValue = this.resolveFromArray(name, ((JavaArrayAnnotationArgument)argument).getElements());
        } else {
            constantValue = javaAnnotationArgument instanceof JavaAnnotationAsAnnotationArgument ? this.resolveFromAnnotation(((JavaAnnotationAsAnnotationArgument)argument).getAnnotation()) : (javaAnnotationArgument instanceof JavaClassObjectAnnotationArgument ? this.resolveFromJavaClassObjectType(((JavaClassObjectAnnotationArgument)argument).getReferencedType()) : null);
        }
        return constantValue;
    }

    private final ConstantValue<?> resolveFromAnnotation(JavaAnnotation javaAnnotation) {
        return new AnnotationValue(new LazyJavaAnnotationDescriptor(this.c, javaAnnotation));
    }

    /*
     * WARNING - void declaration
     */
    private final ConstantValue<?> resolveFromArray(Name argumentName, List<? extends JavaAnnotationArgument> elements) {
        void $this$mapTo$iv$iv;
        SimpleType simpleType2 = this.getType();
        Intrinsics.checkNotNullExpressionValue(simpleType2, "type");
        if (KotlinTypeKt.isError(simpleType2)) {
            return null;
        }
        ClassDescriptor classDescriptor = DescriptorUtilsKt.getAnnotationClass(this);
        Intrinsics.checkNotNull(classDescriptor);
        Annotated annotated = DescriptorResolverUtils.getAnnotationParameterByName(argumentName, classDescriptor);
        if (annotated == null || (annotated = annotated.getType()) == null) {
            annotated = this.c.getComponents().getModule().getBuiltIns().getArrayType(Variance.INVARIANT, ErrorUtils.createErrorType("Unknown array element type"));
        }
        Intrinsics.checkNotNullExpressionValue(annotated, "DescriptorResolverUtils.\u2026 type\")\n                )");
        Annotated arrayType = annotated;
        Iterable $this$map$iv = elements;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void argument;
            JavaAnnotationArgument javaAnnotationArgument = (JavaAnnotationArgument)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            ConstantValue constantValue = this.resolveAnnotationArgument((JavaAnnotationArgument)argument);
            if (constantValue == null) {
                constantValue = new NullValue();
            }
            ConstantValue constantValue2 = constantValue;
            collection.add(constantValue2);
        }
        List values2 = (List)destination$iv$iv;
        return ConstantValueFactory.INSTANCE.createArrayValue(values2, (KotlinType)arrayType);
    }

    private final ConstantValue<?> resolveFromEnumValue(ClassId enumClassId, Name entryName) {
        if (enumClassId == null || entryName == null) {
            return null;
        }
        return new EnumValue(enumClassId, entryName);
    }

    private final ConstantValue<?> resolveFromJavaClassObjectType(JavaType javaType) {
        return KClassValue.Companion.create(this.c.getTypeResolver().transformJavaType(javaType, JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, null, 3, null)));
    }

    @NotNull
    public String toString() {
        return DescriptorRenderer.renderAnnotation$default(DescriptorRenderer.FQ_NAMES_IN_TYPES, this, null, 2, null);
    }

    private final ClassDescriptor createTypeForMissingDependencies(FqName fqName2) {
        ModuleDescriptor moduleDescriptor = this.c.getModule();
        ClassId classId = ClassId.topLevel(fqName2);
        Intrinsics.checkNotNullExpressionValue(classId, "ClassId.topLevel(fqName)");
        return FindClassInModuleKt.findNonGenericClassAcrossDependencies(moduleDescriptor, classId, this.c.getComponents().getDeserializedDescriptorResolver().getComponents().getNotFoundClasses());
    }

    @Override
    public boolean isIdeExternalAnnotation() {
        return this.isIdeExternalAnnotation;
    }

    public LazyJavaAnnotationDescriptor(@NotNull LazyJavaResolverContext c, @NotNull JavaAnnotation javaAnnotation) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(javaAnnotation, "javaAnnotation");
        this.c = c;
        this.javaAnnotation = javaAnnotation;
        this.fqName$delegate = this.c.getStorageManager().createNullableLazyValue((Function0)new Function0<FqName>(this){
            final /* synthetic */ LazyJavaAnnotationDescriptor this$0;

            @Nullable
            public final FqName invoke() {
                ClassId classId = LazyJavaAnnotationDescriptor.access$getJavaAnnotation$p(this.this$0).getClassId();
                return classId != null ? classId.asSingleFqName() : null;
            }
            {
                this.this$0 = lazyJavaAnnotationDescriptor;
                super(0);
            }
        });
        this.type$delegate = this.c.getStorageManager().createLazyValue((Function0)new Function0<SimpleType>(this){
            final /* synthetic */ LazyJavaAnnotationDescriptor this$0;

            @NotNull
            public final SimpleType invoke() {
                ClassDescriptor classDescriptor;
                FqName fqName2 = this.this$0.getFqName();
                if (fqName2 == null) {
                    return ErrorUtils.createErrorType("No fqName: " + LazyJavaAnnotationDescriptor.access$getJavaAnnotation$p(this.this$0));
                }
                Intrinsics.checkNotNullExpressionValue(fqName2, "fqName ?: return@createL\u2026fqName: $javaAnnotation\")");
                FqName fqName3 = fqName2;
                ClassDescriptor classDescriptor2 = JavaToKotlinClassMap.mapJavaToKotlin$default(JavaToKotlinClassMap.INSTANCE, fqName3, LazyJavaAnnotationDescriptor.access$getC$p(this.this$0).getModule().getBuiltIns(), null, 4, null);
                if (classDescriptor2 == null) {
                    JavaClass javaClass = LazyJavaAnnotationDescriptor.access$getJavaAnnotation$p(this.this$0).resolve();
                    if (javaClass != null) {
                        JavaClass javaClass2 = javaClass;
                        boolean bl = false;
                        boolean bl2 = false;
                        JavaClass javaClass3 = javaClass2;
                        boolean bl3 = false;
                        classDescriptor2 = LazyJavaAnnotationDescriptor.access$getC$p(this.this$0).getComponents().getModuleClassResolver().resolveClass(javaClass3);
                    } else {
                        classDescriptor2 = classDescriptor = null;
                    }
                }
                if (classDescriptor2 == null) {
                    classDescriptor = LazyJavaAnnotationDescriptor.access$createTypeForMissingDependencies(this.this$0, fqName3);
                }
                ClassDescriptor annotationClass = classDescriptor;
                return annotationClass.getDefaultType();
            }
            {
                this.this$0 = lazyJavaAnnotationDescriptor;
                super(0);
            }
        });
        this.source = this.c.getComponents().getSourceElementFactory().source(this.javaAnnotation);
        this.allValueArguments$delegate = this.c.getStorageManager().createLazyValue(new Function0<Map<Name, ? extends ConstantValue<?>>>(this){
            final /* synthetic */ LazyJavaAnnotationDescriptor this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final Map<Name, ConstantValue<?>> invoke() {
                void $this$mapNotNullTo$iv$iv;
                Iterable $this$mapNotNull$iv = LazyJavaAnnotationDescriptor.access$getJavaAnnotation$p(this.this$0).getArguments();
                boolean $i$f$mapNotNull = false;
                Iterable iterable = $this$mapNotNull$iv;
                Collection destination$iv$iv = new ArrayList<E>();
                boolean $i$f$mapNotNullTo = false;
                void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                boolean $i$f$forEach = false;
                Iterator<T> iterator2 = $this$forEach$iv$iv$iv.iterator();
                while (iterator2.hasNext()) {
                    Pair<Name, ConstantValue> pair;
                    Name name;
                    T element$iv$iv$iv;
                    T element$iv$iv = element$iv$iv$iv = iterator2.next();
                    boolean bl = false;
                    JavaAnnotationArgument arg = (JavaAnnotationArgument)element$iv$iv;
                    boolean bl2 = false;
                    Name name2 = arg.getName();
                    if (name2 == null) {
                        name2 = name = JvmAnnotationNames.DEFAULT_ANNOTATION_MEMBER_NAME;
                    }
                    if (LazyJavaAnnotationDescriptor.access$resolveAnnotationArgument(this.this$0, arg) != null) {
                        ConstantValue constantValue;
                        boolean bl3 = false;
                        boolean bl4 = false;
                        ConstantValue value = constantValue;
                        boolean bl5 = false;
                        pair = TuplesKt.to(name, value);
                    } else {
                        pair = null;
                    }
                    if (pair == null) continue;
                    Pair<Name, ConstantValue> pair2 = pair;
                    boolean bl6 = false;
                    boolean bl7 = false;
                    Pair<Name, ConstantValue> it$iv$iv = pair2;
                    boolean bl8 = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                return MapsKt.toMap((List)destination$iv$iv);
            }
            {
                this.this$0 = lazyJavaAnnotationDescriptor;
                super(0);
            }
        });
        this.isIdeExternalAnnotation = this.javaAnnotation.isIdeExternalAnnotation();
    }

    public static final /* synthetic */ JavaAnnotation access$getJavaAnnotation$p(LazyJavaAnnotationDescriptor $this) {
        return $this.javaAnnotation;
    }

    public static final /* synthetic */ LazyJavaResolverContext access$getC$p(LazyJavaAnnotationDescriptor $this) {
        return $this.c;
    }

    public static final /* synthetic */ ClassDescriptor access$createTypeForMissingDependencies(LazyJavaAnnotationDescriptor $this, FqName fqName2) {
        return $this.createTypeForMissingDependencies(fqName2);
    }

    public static final /* synthetic */ ConstantValue access$resolveAnnotationArgument(LazyJavaAnnotationDescriptor $this, JavaAnnotationArgument argument) {
        return $this.resolveAnnotationArgument(argument);
    }
}

