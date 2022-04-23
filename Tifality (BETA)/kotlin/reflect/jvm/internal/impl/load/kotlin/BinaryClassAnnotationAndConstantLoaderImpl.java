/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.components.DescriptorResolverUtils;
import kotlin.reflect.jvm.internal.impl.load.kotlin.AbstractBinaryClassAnnotationAndConstantLoader;
import kotlin.reflect.jvm.internal.impl.load.kotlin.BinaryClassAnnotationAndConstantLoaderImpl;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ByteValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ClassLiteralValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValueFactory;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ErrorValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.LongValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ShortValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UByteValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UIntValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ULongValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UShortValue;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotationDeserializer;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BinaryClassAnnotationAndConstantLoaderImpl
extends AbstractBinaryClassAnnotationAndConstantLoader<AnnotationDescriptor, ConstantValue<?>> {
    private final AnnotationDeserializer annotationDeserializer;
    private final ModuleDescriptor module;
    private final NotFoundClasses notFoundClasses;

    @Override
    @NotNull
    protected AnnotationDescriptor loadTypeAnnotation(@NotNull ProtoBuf.Annotation proto, @NotNull NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        return this.annotationDeserializer.deserializeAnnotation(proto, nameResolver);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    @Nullable
    protected ConstantValue<?> loadConstant(@NotNull String desc, @NotNull Object initializer) {
        Object object;
        block9: {
            block6: {
                int intValue;
                block8: {
                    block7: {
                        Intrinsics.checkNotNullParameter(desc, "desc");
                        Intrinsics.checkNotNullParameter(initializer, "initializer");
                        if (!StringsKt.contains$default((CharSequence)"ZBCS", desc, false, 2, null)) break block6;
                        intValue = (Integer)initializer;
                        String string = desc;
                        switch (string.hashCode()) {
                            case 66: {
                                if (!string.equals("B")) throw (Throwable)((Object)new AssertionError((Object)desc));
                                break;
                            }
                            case 67: {
                                if (!string.equals("C")) throw (Throwable)((Object)new AssertionError((Object)desc));
                                break block7;
                            }
                            case 83: {
                                if (!string.equals("S")) throw (Throwable)((Object)new AssertionError((Object)desc));
                                break block8;
                            }
                            case 90: {
                                if (!string.equals("Z")) throw (Throwable)((Object)new AssertionError((Object)desc));
                                object = intValue != 0;
                                break block9;
                            }
                        }
                        object = (byte)intValue;
                        break block9;
                    }
                    object = Character.valueOf((char)intValue);
                    break block9;
                }
                object = (short)intValue;
                break block9;
                throw (Throwable)((Object)new AssertionError((Object)desc));
            }
            object = initializer;
        }
        Object normalizedValue = object;
        return ConstantValueFactory.INSTANCE.createConstantValue(normalizedValue);
    }

    @Override
    @Nullable
    protected ConstantValue<?> transformToUnsignedConstant(@NotNull ConstantValue<?> constant) {
        Intrinsics.checkNotNullParameter(constant, "constant");
        ConstantValue constantValue = constant;
        return constantValue instanceof ByteValue ? (ConstantValue)new UByteValue(((Number)((ByteValue)constant).getValue()).byteValue()) : (constantValue instanceof ShortValue ? (ConstantValue)new UShortValue(((Number)((ShortValue)constant).getValue()).shortValue()) : (constantValue instanceof IntValue ? (ConstantValue)new UIntValue(((Number)((IntValue)constant).getValue()).intValue()) : (constantValue instanceof LongValue ? (ConstantValue)new ULongValue(((Number)((LongValue)constant).getValue()).longValue()) : constant)));
    }

    @Override
    @Nullable
    protected KotlinJvmBinaryClass.AnnotationArgumentVisitor loadAnnotation(@NotNull ClassId annotationClassId, @NotNull SourceElement source, @NotNull List<AnnotationDescriptor> result2) {
        Intrinsics.checkNotNullParameter(annotationClassId, "annotationClassId");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(result2, "result");
        ClassDescriptor annotationClass = this.resolveClass(annotationClassId);
        return new KotlinJvmBinaryClass.AnnotationArgumentVisitor(this, annotationClass, result2, source){
            private final HashMap<Name, ConstantValue<?>> arguments;
            final /* synthetic */ BinaryClassAnnotationAndConstantLoaderImpl this$0;
            final /* synthetic */ ClassDescriptor $annotationClass;
            final /* synthetic */ List $result;
            final /* synthetic */ SourceElement $source;

            public void visit(@Nullable Name name, @Nullable Object value) {
                if (name != null) {
                    ((Map)this.arguments).put(name, this.createConstant(name, value));
                }
            }

            public void visitClassLiteral(@NotNull Name name, @NotNull ClassLiteralValue value) {
                Intrinsics.checkNotNullParameter(name, "name");
                Intrinsics.checkNotNullParameter(value, "value");
                ((Map)this.arguments).put(name, new KClassValue(value));
            }

            public void visitEnum(@NotNull Name name, @NotNull ClassId enumClassId, @NotNull Name enumEntryName) {
                Intrinsics.checkNotNullParameter(name, "name");
                Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
                Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
                ((Map)this.arguments).put(name, new EnumValue(enumClassId, enumEntryName));
            }

            @Nullable
            public KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor visitArray(@NotNull Name name) {
                Intrinsics.checkNotNullParameter(name, "name");
                return new KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor(this, name){
                    private final ArrayList<ConstantValue<?>> elements;
                    final /* synthetic */ loadAnnotation.1 this$0;
                    final /* synthetic */ Name $name;

                    public void visit(@Nullable Object value) {
                        this.elements.add(loadAnnotation.1.access$createConstant(this.this$0, this.$name, value));
                    }

                    public void visitEnum(@NotNull ClassId enumClassId, @NotNull Name enumEntryName) {
                        Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
                        Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
                        this.elements.add(new EnumValue(enumClassId, enumEntryName));
                    }

                    public void visitClassLiteral(@NotNull ClassLiteralValue value) {
                        Intrinsics.checkNotNullParameter(value, "value");
                        this.elements.add(new KClassValue(value));
                    }

                    public void visitEnd() {
                        ValueParameterDescriptor parameter = DescriptorResolverUtils.getAnnotationParameterByName(this.$name, this.this$0.$annotationClass);
                        if (parameter != null) {
                            Map map2 = loadAnnotation.1.access$getArguments$p(this.this$0);
                            List<ConstantValue<?>> list = kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.compact(this.elements);
                            KotlinType kotlinType = parameter.getType();
                            Intrinsics.checkNotNullExpressionValue(kotlinType, "parameter.type");
                            map2.put(this.$name, ConstantValueFactory.INSTANCE.createArrayValue(list, kotlinType));
                        }
                    }
                    {
                        this.this$0 = this$0;
                        this.$name = $captured_local_variable$1;
                        this.elements = new ArrayList<E>();
                    }
                };
            }

            @Nullable
            public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(@NotNull Name name, @NotNull ClassId classId) {
                Intrinsics.checkNotNullParameter(name, "name");
                Intrinsics.checkNotNullParameter(classId, "classId");
                ArrayList<E> list = new ArrayList<E>();
                SourceElement sourceElement = SourceElement.NO_SOURCE;
                Intrinsics.checkNotNullExpressionValue(sourceElement, "SourceElement.NO_SOURCE");
                KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitor = this.this$0.loadAnnotation(classId, sourceElement, (List<AnnotationDescriptor>)list);
                Intrinsics.checkNotNull(annotationArgumentVisitor);
                KotlinJvmBinaryClass.AnnotationArgumentVisitor visitor2 = annotationArgumentVisitor;
                return new KotlinJvmBinaryClass.AnnotationArgumentVisitor(this, visitor2, name, list){
                    private final /* synthetic */ KotlinJvmBinaryClass.AnnotationArgumentVisitor $$delegate_0;
                    final /* synthetic */ loadAnnotation.1 this$0;
                    final /* synthetic */ KotlinJvmBinaryClass.AnnotationArgumentVisitor $visitor;
                    final /* synthetic */ Name $name;
                    final /* synthetic */ ArrayList $list;

                    public void visitEnd() {
                        this.$visitor.visitEnd();
                        ((Map)loadAnnotation.1.access$getArguments$p(this.this$0)).put(this.$name, new AnnotationValue((AnnotationDescriptor)CollectionsKt.single(this.$list)));
                    }
                    {
                        this.this$0 = this$0;
                        this.$visitor = $captured_local_variable$1;
                        this.$name = $captured_local_variable$2;
                        this.$list = $captured_local_variable$3;
                        this.$$delegate_0 = $captured_local_variable$1;
                    }

                    public void visit(@Nullable Name name, @Nullable Object value) {
                        this.$$delegate_0.visit(name, value);
                    }

                    @Nullable
                    public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(@NotNull Name name, @NotNull ClassId classId) {
                        Intrinsics.checkNotNullParameter(name, "name");
                        Intrinsics.checkNotNullParameter(classId, "classId");
                        return this.$$delegate_0.visitAnnotation(name, classId);
                    }

                    @Nullable
                    public KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor visitArray(@NotNull Name name) {
                        Intrinsics.checkNotNullParameter(name, "name");
                        return this.$$delegate_0.visitArray(name);
                    }

                    public void visitClassLiteral(@NotNull Name name, @NotNull ClassLiteralValue value) {
                        Intrinsics.checkNotNullParameter(name, "name");
                        Intrinsics.checkNotNullParameter(value, "value");
                        this.$$delegate_0.visitClassLiteral(name, value);
                    }

                    public void visitEnum(@NotNull Name name, @NotNull ClassId enumClassId, @NotNull Name enumEntryName) {
                        Intrinsics.checkNotNullParameter(name, "name");
                        Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
                        Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
                        this.$$delegate_0.visitEnum(name, enumClassId, enumEntryName);
                    }
                };
            }

            public void visitEnd() {
                this.$result.add(new AnnotationDescriptorImpl(this.$annotationClass.getDefaultType(), (Map)this.arguments, this.$source));
            }

            private final ConstantValue<?> createConstant(Name name, Object value) {
                ConstantValue constantValue = ConstantValueFactory.INSTANCE.createConstantValue(value);
                if (constantValue == null) {
                    constantValue = ErrorValue.Companion.create("Unsupported annotation argument: " + name);
                }
                return constantValue;
            }
            {
                this.this$0 = this$0;
                this.$annotationClass = $captured_local_variable$1;
                this.$result = $captured_local_variable$2;
                this.$source = $captured_local_variable$3;
                this.arguments = new HashMap<K, V>();
            }

            public static final /* synthetic */ ConstantValue access$createConstant(loadAnnotation.1 $this, Name name, Object value) {
                return $this.createConstant(name, value);
            }

            public static final /* synthetic */ HashMap access$getArguments$p(loadAnnotation.1 $this) {
                return $this.arguments;
            }
        };
    }

    private final ClassDescriptor resolveClass(ClassId classId) {
        return FindClassInModuleKt.findNonGenericClassAcrossDependencies(this.module, classId, this.notFoundClasses);
    }

    public BinaryClassAnnotationAndConstantLoaderImpl(@NotNull ModuleDescriptor module, @NotNull NotFoundClasses notFoundClasses, @NotNull StorageManager storageManager, @NotNull KotlinClassFinder kotlinClassFinder) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
        super(storageManager, kotlinClassFinder);
        this.module = module;
        this.notFoundClasses = notFoundClasses;
        this.annotationDeserializer = new AnnotationDeserializer(this.module, this.notFoundClasses);
    }
}

