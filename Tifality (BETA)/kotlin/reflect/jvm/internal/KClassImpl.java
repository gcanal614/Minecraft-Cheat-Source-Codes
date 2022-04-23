/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KProperty;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KClassImpl;
import kotlin.reflect.jvm.internal.KClassImpl$WhenMappings;
import kotlin.reflect.jvm.internal.KClassifierImpl;
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl;
import kotlin.reflect.jvm.internal.KFunctionImpl;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.KTypeParameterImpl;
import kotlin.reflect.jvm.internal.KTypeParameterOwnerImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.ReflectProperties;
import kotlin.reflect.jvm.internal.RuntimeTypeMapper;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.impl.builtins.CompanionObjectMapping;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectKotlinClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.RuntimeModuleData;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u00ba\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0001\n\u0002\b\u0003\b\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00010\u00042\u00020\u00052\u00020\u0006:\u0001bB\u0013\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b\u00a2\u0006\u0002\u0010\tJ\u0013\u0010Q\u001a\u00020&2\b\u0010R\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\u0016\u0010S\u001a\b\u0012\u0004\u0012\u00020T0\u00142\u0006\u0010U\u001a\u00020VH\u0016J\u0012\u0010W\u001a\u0004\u0018\u00010X2\u0006\u0010Y\u001a\u00020ZH\u0016J\u0016\u0010[\u001a\b\u0012\u0004\u0012\u00020X0\u00142\u0006\u0010U\u001a\u00020VH\u0016J\b\u0010\\\u001a\u00020ZH\u0016J\u0012\u0010]\u001a\u00020&2\b\u0010^\u001a\u0004\u0018\u00010\u0002H\u0016J\b\u0010_\u001a\u00020`H\u0002J\b\u0010a\u001a\u00020>H\u0016R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R \u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00190\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u0017R3\u0010\u001b\u001a$\u0012 \u0012\u001e \u001e*\u000e\u0018\u00010\u001dR\b\u0012\u0004\u0012\u00028\u00000\u00000\u001dR\b\u0012\u0004\u0012\u00028\u00000\u00000\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b#\u0010$R\u0014\u0010%\u001a\u00020&8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b%\u0010'R\u0014\u0010(\u001a\u00020&8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b(\u0010'R\u0014\u0010)\u001a\u00020&8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b)\u0010'R\u0014\u0010*\u001a\u00020&8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b*\u0010'R\u0014\u0010+\u001a\u00020&8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b+\u0010'R\u0014\u0010,\u001a\u00020&8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b,\u0010'R\u0014\u0010-\u001a\u00020&8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b-\u0010'R\u0014\u0010.\u001a\u00020&8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b.\u0010'R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0014\u00101\u001a\u0002028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b3\u00104R\u001e\u00105\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u0003060\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b7\u0010\u0017R\u001e\u00108\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b9\u0010\u0017R\u0016\u0010:\u001a\u0004\u0018\u00018\u00008VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b;\u0010<R\u0016\u0010=\u001a\u0004\u0018\u00010>8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b?\u0010@R\"\u0010A\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u00040\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bB\u0010\u000eR\u0016\u0010C\u001a\u0004\u0018\u00010>8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bD\u0010@R\u0014\u0010E\u001a\u0002028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\bF\u00104R\u001a\u0010G\u001a\b\u0012\u0004\u0012\u00020H0\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bI\u0010\u000eR\u001a\u0010J\u001a\b\u0012\u0004\u0012\u00020K0\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bL\u0010\u000eR\u0016\u0010M\u001a\u0004\u0018\u00010N8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bO\u0010P\u00a8\u0006c"}, d2={"Lkotlin/reflect/jvm/internal/KClassImpl;", "T", "", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "Lkotlin/reflect/KClass;", "Lkotlin/reflect/jvm/internal/KClassifierImpl;", "Lkotlin/reflect/jvm/internal/KTypeParameterOwnerImpl;", "jClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "classId", "Lkotlin/reflect/jvm/internal/impl/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "constructorDescriptors", "", "Lkotlin/reflect/jvm/internal/impl/descriptors/ConstructorDescriptor;", "getConstructorDescriptors", "()Ljava/util/Collection;", "constructors", "Lkotlin/reflect/KFunction;", "getConstructors", "data", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazyVal;", "Lkotlin/reflect/jvm/internal/KClassImpl$Data;", "kotlin.jvm.PlatformType", "getData", "()Lkotlin/reflect/jvm/internal/ReflectProperties$LazyVal;", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/ClassDescriptor;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "isAbstract", "", "()Z", "isCompanion", "isData", "isFinal", "isFun", "isInner", "isOpen", "isSealed", "getJClass", "()Ljava/lang/Class;", "memberScope", "Lkotlin/reflect/jvm/internal/impl/resolve/scopes/MemberScope;", "getMemberScope$kotlin_reflection", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "members", "Lkotlin/reflect/KCallable;", "getMembers", "nestedClasses", "getNestedClasses", "objectInstance", "getObjectInstance", "()Ljava/lang/Object;", "qualifiedName", "", "getQualifiedName", "()Ljava/lang/String;", "sealedSubclasses", "getSealedSubclasses", "simpleName", "getSimpleName", "staticScope", "getStaticScope$kotlin_reflection", "supertypes", "Lkotlin/reflect/KType;", "getSupertypes", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "getVisibility", "()Lkotlin/reflect/KVisibility;", "equals", "other", "getFunctions", "Lkotlin/reflect/jvm/internal/impl/descriptors/FunctionDescriptor;", "name", "Lkotlin/reflect/jvm/internal/impl/name/Name;", "getLocalProperty", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "index", "", "getProperties", "hashCode", "isInstance", "value", "reportUnresolvedClass", "", "toString", "Data", "kotlin-reflection"})
public final class KClassImpl<T>
extends KDeclarationContainerImpl
implements KClass<T>,
KClassifierImpl,
KTypeParameterOwnerImpl {
    @NotNull
    private final ReflectProperties.LazyVal<Data> data;
    @NotNull
    private final Class<T> jClass;

    @NotNull
    public final ReflectProperties.LazyVal<Data> getData() {
        return this.data;
    }

    @Override
    @NotNull
    public ClassDescriptor getDescriptor() {
        return this.data.invoke().getDescriptor();
    }

    @Override
    @NotNull
    public List<Annotation> getAnnotations() {
        return this.data.invoke().getAnnotations();
    }

    private final ClassId getClassId() {
        return RuntimeTypeMapper.INSTANCE.mapJvmClassToKotlinClassId(this.getJClass());
    }

    @NotNull
    public final MemberScope getMemberScope$kotlin_reflection() {
        return this.getDescriptor().getDefaultType().getMemberScope();
    }

    @NotNull
    public final MemberScope getStaticScope$kotlin_reflection() {
        MemberScope memberScope2 = this.getDescriptor().getStaticScope();
        Intrinsics.checkNotNullExpressionValue(memberScope2, "descriptor.staticScope");
        return memberScope2;
    }

    @Override
    @NotNull
    public Collection<KCallable<?>> getMembers() {
        return this.data.invoke().getAllMembers();
    }

    @Override
    @NotNull
    public Collection<ConstructorDescriptor> getConstructorDescriptors() {
        ClassDescriptor descriptor2 = this.getDescriptor();
        if (descriptor2.getKind() == ClassKind.INTERFACE || descriptor2.getKind() == ClassKind.OBJECT) {
            return kotlin.collections.CollectionsKt.emptyList();
        }
        Collection<ConstructorDescriptor> collection = descriptor2.getConstructors();
        Intrinsics.checkNotNullExpressionValue(collection, "descriptor.constructors");
        return collection;
    }

    @Override
    @NotNull
    public Collection<PropertyDescriptor> getProperties(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return kotlin.collections.CollectionsKt.plus(this.getMemberScope$kotlin_reflection().getContributedVariables(name, NoLookupLocation.FROM_REFLECTION), (Iterable)this.getStaticScope$kotlin_reflection().getContributedVariables(name, NoLookupLocation.FROM_REFLECTION));
    }

    @Override
    @NotNull
    public Collection<FunctionDescriptor> getFunctions(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return kotlin.collections.CollectionsKt.plus(this.getMemberScope$kotlin_reflection().getContributedFunctions(name, NoLookupLocation.FROM_REFLECTION), (Iterable)this.getStaticScope$kotlin_reflection().getContributedFunctions(name, NoLookupLocation.FROM_REFLECTION));
    }

    @Override
    @Nullable
    public PropertyDescriptor getLocalProperty(int index) {
        PropertyDescriptor propertyDescriptor;
        ClassDescriptor classDescriptor;
        boolean bl;
        boolean bl2;
        Object object;
        if (Intrinsics.areEqual(this.getJClass().getSimpleName(), "DefaultImpls")) {
            Class<?> clazz = this.getJClass().getDeclaringClass();
            if (clazz != null) {
                object = clazz;
                bl2 = false;
                bl = false;
                Object interfaceClass = object;
                boolean bl3 = false;
                if (((Class)interfaceClass).isInterface()) {
                    KClass kClass = JvmClassMappingKt.getKotlinClass(interfaceClass);
                    if (kClass == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KClassImpl<*>");
                    }
                    return ((KClassImpl)kClass).getLocalProperty(index);
                }
            }
        }
        if (!((classDescriptor = this.getDescriptor()) instanceof DeserializedClassDescriptor)) {
            classDescriptor = null;
        }
        DeserializedClassDescriptor deserializedClassDescriptor = (DeserializedClassDescriptor)classDescriptor;
        if (deserializedClassDescriptor != null) {
            object = deserializedClassDescriptor;
            bl2 = false;
            bl = false;
            Object descriptor2 = object;
            boolean bl4 = false;
            GeneratedMessageLite.ExtendableMessage extendableMessage = ((DeserializedClassDescriptor)descriptor2).getClassProto();
            GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Property>> generatedExtension = JvmProtoBuf.classLocalVariable;
            Intrinsics.checkNotNullExpressionValue(generatedExtension, "JvmProtoBuf.classLocalVariable");
            ProtoBuf.Property property = ProtoBufUtilKt.getExtensionOrNull(extendableMessage, generatedExtension, index);
            if (property != null) {
                ProtoBuf.Property property2 = property;
                boolean bl5 = false;
                boolean bl6 = false;
                ProtoBuf.Property proto = property2;
                boolean bl7 = false;
                propertyDescriptor = (PropertyDescriptor)UtilKt.deserializeToDescriptor(this.getJClass(), (MessageLite)proto, ((DeserializedClassDescriptor)descriptor2).getC().getNameResolver(), ((DeserializedClassDescriptor)descriptor2).getC().getTypeTable(), ((DeserializedClassDescriptor)descriptor2).getMetadataVersion(), getLocalProperty.2.1.1.INSTANCE);
            } else {
                propertyDescriptor = null;
            }
        } else {
            propertyDescriptor = null;
        }
        return propertyDescriptor;
    }

    @Override
    @Nullable
    public String getSimpleName() {
        return this.data.invoke().getSimpleName();
    }

    @Override
    @Nullable
    public String getQualifiedName() {
        return this.data.invoke().getQualifiedName();
    }

    @Override
    @NotNull
    public Collection<KFunction<T>> getConstructors() {
        return this.data.invoke().getConstructors();
    }

    @Override
    @NotNull
    public Collection<KClass<?>> getNestedClasses() {
        return this.data.invoke().getNestedClasses();
    }

    @Override
    @Nullable
    public T getObjectInstance() {
        return this.data.invoke().getObjectInstance();
    }

    @Override
    public boolean isInstance(@Nullable Object value) {
        Integer n = ReflectClassUtilKt.getFunctionClassArity(this.getJClass());
        if (n != null) {
            Integer n2 = n;
            boolean bl = false;
            boolean bl2 = false;
            int arity = ((Number)n2).intValue();
            boolean bl3 = false;
            return TypeIntrinsics.isFunctionOfArity(value, arity);
        }
        Class<?> clazz = ReflectClassUtilKt.getWrapperByPrimitive(this.getJClass());
        if (clazz == null) {
            clazz = this.getJClass();
        }
        return clazz.isInstance(value);
    }

    @Override
    @NotNull
    public List<KTypeParameter> getTypeParameters() {
        return this.data.invoke().getTypeParameters();
    }

    @Override
    @NotNull
    public List<KType> getSupertypes() {
        return this.data.invoke().getSupertypes();
    }

    @Override
    @NotNull
    public List<KClass<? extends T>> getSealedSubclasses() {
        return this.data.invoke().getSealedSubclasses();
    }

    @Override
    @Nullable
    public KVisibility getVisibility() {
        Visibility visibility = this.getDescriptor().getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "descriptor.visibility");
        return UtilKt.toKVisibility(visibility);
    }

    @Override
    public boolean isFinal() {
        return this.getDescriptor().getModality() == Modality.FINAL;
    }

    @Override
    public boolean isOpen() {
        return this.getDescriptor().getModality() == Modality.OPEN;
    }

    @Override
    public boolean isAbstract() {
        return this.getDescriptor().getModality() == Modality.ABSTRACT;
    }

    @Override
    public boolean isSealed() {
        return this.getDescriptor().getModality() == Modality.SEALED;
    }

    @Override
    public boolean isData() {
        return this.getDescriptor().isData();
    }

    @Override
    public boolean isInner() {
        return this.getDescriptor().isInner();
    }

    @Override
    public boolean isCompanion() {
        return this.getDescriptor().isCompanionObject();
    }

    @Override
    public boolean isFun() {
        return this.getDescriptor().isFun();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        return other instanceof KClassImpl && Intrinsics.areEqual(JvmClassMappingKt.getJavaObjectType(this), JvmClassMappingKt.getJavaObjectType((KClass)other));
    }

    @Override
    public int hashCode() {
        return JvmClassMappingKt.getJavaObjectType(this).hashCode();
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public String toString() {
        void classId;
        ClassId classId2 = this.getClassId();
        boolean bl = false;
        boolean bl2 = false;
        ClassId classId3 = classId2;
        StringBuilder stringBuilder = new StringBuilder().append("class ");
        boolean bl3 = false;
        FqName fqName2 = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(fqName2, "classId.packageFqName");
        FqName packageFqName = fqName2;
        String packagePrefix = packageFqName.isRoot() ? "" : packageFqName.asString() + ".";
        String string = classId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(string, "classId.relativeClassName.asString()");
        String classSuffix = StringsKt.replace$default(string, '.', '$', false, 4, null);
        String string2 = packagePrefix + classSuffix;
        return stringBuilder.append(string2).toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final Void reportUnresolvedClass() {
        KotlinClassHeader.Kind kind;
        Object object = ReflectKotlinClass.Factory.create(this.getJClass());
        KotlinClassHeader.Kind kind2 = kind = object != null && (object = ((ReflectKotlinClass)object).getClassHeader()) != null ? ((KotlinClassHeader)object).getKind() : null;
        if (kind2 == null) throw (Throwable)new KotlinReflectionInternalError("Unresolved class: " + this.getJClass());
        switch (KClassImpl$WhenMappings.$EnumSwitchMapping$0[kind2.ordinal()]) {
            case 1: 
            case 2: 
            case 3: {
                throw (Throwable)new UnsupportedOperationException("Packages and file facades are not yet supported in Kotlin reflection. " + "Meanwhile please use Java reflection to inspect this class: " + this.getJClass());
            }
            case 4: {
                throw (Throwable)new UnsupportedOperationException("This class is an internal synthetic class generated by the Kotlin compiler, such as an anonymous class for a lambda, a SAM wrapper, a callable reference, etc. It's not a Kotlin class or interface, so the reflection " + "library has no idea what declarations does it have. Please use Java reflection to inspect this class: " + this.getJClass());
            }
            case 5: {
                throw (Throwable)new KotlinReflectionInternalError("Unknown class: " + this.getJClass() + " (kind = " + (Object)((Object)kind) + ')');
            }
            case 6: {
                throw (Throwable)new KotlinReflectionInternalError("Unresolved class: " + this.getJClass());
            }
        }
        throw new NoWhenBranchMatchedException();
    }

    @NotNull
    public Class<T> getJClass() {
        return this.jClass;
    }

    public KClassImpl(@NotNull Class<T> jClass) {
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        this.jClass = jClass;
        ReflectProperties.LazyVal lazyVal = ReflectProperties.lazy((Function0)new Function0<Data>(this){
            final /* synthetic */ KClassImpl this$0;

            public final Data invoke() {
                return this.this$0.new Data();
            }
            {
                this.this$0 = kClassImpl;
                super(0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(lazyVal, "ReflectProperties.lazy { Data() }");
        this.data = lazyVal;
    }

    public static final /* synthetic */ ClassId access$getClassId$p(KClassImpl $this) {
        return $this.getClassId();
    }

    public static final /* synthetic */ Void access$reportUnresolvedClass(KClassImpl $this) {
        return $this.reportUnresolvedClass();
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0014\u0010N\u001a\u00020<2\n\u0010O\u001a\u0006\u0012\u0002\b\u00030PH\u0002R%\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u00058FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR%\u0010\u000b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u00058FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\n\u001a\u0004\b\f\u0010\bR%\u0010\u000e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u00058FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\n\u001a\u0004\b\u000f\u0010\bR!\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\n\u001a\u0004\b\u0014\u0010\u0015R-\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00180\u00058FX\u0086\u0084\u0002\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\n\u0012\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\bR%\u0010\u001d\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u00058FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001f\u0010\n\u001a\u0004\b\u001e\u0010\bR%\u0010 \u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u00058FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\"\u0010\n\u001a\u0004\b!\u0010\bR%\u0010#\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u00058BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b%\u0010\n\u001a\u0004\b$\u0010\bR\u001b\u0010&\u001a\u00020'8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b*\u0010\n\u001a\u0004\b(\u0010)R%\u0010+\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u00058BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b-\u0010\n\u001a\u0004\b,\u0010\bR%\u0010.\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u00058BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b0\u0010\n\u001a\u0004\b/\u0010\bR%\u00101\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u0003020\u00058FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b4\u0010\n\u001a\u0004\b3\u0010\bR#\u00105\u001a\u0004\u0018\u00018\u00008FX\u0086\u0084\u0002\u00a2\u0006\u0012\n\u0004\b9\u0010:\u0012\u0004\b6\u0010\u001a\u001a\u0004\b7\u00108R\u001d\u0010;\u001a\u0004\u0018\u00010<8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b?\u0010\n\u001a\u0004\b=\u0010>R)\u0010@\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00028\u0000020\u00128FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\bB\u0010\n\u001a\u0004\bA\u0010\u0015R\u001d\u0010C\u001a\u0004\u0018\u00010<8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\bE\u0010\n\u001a\u0004\bD\u0010>R!\u0010F\u001a\b\u0012\u0004\u0012\u00020G0\u00128FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\bI\u0010\n\u001a\u0004\bH\u0010\u0015R!\u0010J\u001a\b\u0012\u0004\u0012\u00020K0\u00128FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\bM\u0010\n\u001a\u0004\bL\u0010\u0015\u00a8\u0006Q"}, d2={"Lkotlin/reflect/jvm/internal/KClassImpl$Data;", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl$Data;", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "(Lkotlin/reflect/jvm/internal/KClassImpl;)V", "allMembers", "", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "getAllMembers", "()Ljava/util/Collection;", "allMembers$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazySoftVal;", "allNonStaticMembers", "getAllNonStaticMembers", "allNonStaticMembers$delegate", "allStaticMembers", "getAllStaticMembers", "allStaticMembers$delegate", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "annotations$delegate", "constructors", "Lkotlin/reflect/KFunction;", "getConstructors$annotations", "()V", "getConstructors", "constructors$delegate", "declaredMembers", "getDeclaredMembers", "declaredMembers$delegate", "declaredNonStaticMembers", "getDeclaredNonStaticMembers", "declaredNonStaticMembers$delegate", "declaredStaticMembers", "getDeclaredStaticMembers", "declaredStaticMembers$delegate", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/ClassDescriptor;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "descriptor$delegate", "inheritedNonStaticMembers", "getInheritedNonStaticMembers", "inheritedNonStaticMembers$delegate", "inheritedStaticMembers", "getInheritedStaticMembers", "inheritedStaticMembers$delegate", "nestedClasses", "Lkotlin/reflect/KClass;", "getNestedClasses", "nestedClasses$delegate", "objectInstance", "getObjectInstance$annotations", "getObjectInstance", "()Ljava/lang/Object;", "objectInstance$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazyVal;", "qualifiedName", "", "getQualifiedName", "()Ljava/lang/String;", "qualifiedName$delegate", "sealedSubclasses", "getSealedSubclasses", "sealedSubclasses$delegate", "simpleName", "getSimpleName", "simpleName$delegate", "supertypes", "Lkotlin/reflect/KType;", "getSupertypes", "supertypes$delegate", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters", "typeParameters$delegate", "calculateLocalClassName", "jClass", "Ljava/lang/Class;", "kotlin-reflection"})
    public final class Data
    extends KDeclarationContainerImpl.Data {
        static final /* synthetic */ KProperty[] $$delegatedProperties;
        @NotNull
        private final ReflectProperties.LazySoftVal descriptor$delegate;
        @NotNull
        private final ReflectProperties.LazySoftVal annotations$delegate;
        @Nullable
        private final ReflectProperties.LazySoftVal simpleName$delegate;
        @Nullable
        private final ReflectProperties.LazySoftVal qualifiedName$delegate;
        @NotNull
        private final ReflectProperties.LazySoftVal constructors$delegate;
        @NotNull
        private final ReflectProperties.LazySoftVal nestedClasses$delegate;
        @Nullable
        private final ReflectProperties.LazyVal objectInstance$delegate;
        @NotNull
        private final ReflectProperties.LazySoftVal typeParameters$delegate;
        @NotNull
        private final ReflectProperties.LazySoftVal supertypes$delegate;
        @NotNull
        private final ReflectProperties.LazySoftVal sealedSubclasses$delegate;
        @NotNull
        private final ReflectProperties.LazySoftVal declaredNonStaticMembers$delegate;
        private final ReflectProperties.LazySoftVal declaredStaticMembers$delegate;
        private final ReflectProperties.LazySoftVal inheritedNonStaticMembers$delegate;
        private final ReflectProperties.LazySoftVal inheritedStaticMembers$delegate;
        @NotNull
        private final ReflectProperties.LazySoftVal allNonStaticMembers$delegate;
        @NotNull
        private final ReflectProperties.LazySoftVal allStaticMembers$delegate;
        @NotNull
        private final ReflectProperties.LazySoftVal declaredMembers$delegate;
        @NotNull
        private final ReflectProperties.LazySoftVal allMembers$delegate;

        static {
            $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "descriptor", "getDescriptor()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "annotations", "getAnnotations()Ljava/util/List;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "simpleName", "getSimpleName()Ljava/lang/String;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "qualifiedName", "getQualifiedName()Ljava/lang/String;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "constructors", "getConstructors()Ljava/util/Collection;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "nestedClasses", "getNestedClasses()Ljava/util/Collection;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "objectInstance", "getObjectInstance()Ljava/lang/Object;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "typeParameters", "getTypeParameters()Ljava/util/List;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "supertypes", "getSupertypes()Ljava/util/List;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "sealedSubclasses", "getSealedSubclasses()Ljava/util/List;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "declaredNonStaticMembers", "getDeclaredNonStaticMembers()Ljava/util/Collection;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "declaredStaticMembers", "getDeclaredStaticMembers()Ljava/util/Collection;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "inheritedNonStaticMembers", "getInheritedNonStaticMembers()Ljava/util/Collection;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "inheritedStaticMembers", "getInheritedStaticMembers()Ljava/util/Collection;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "allNonStaticMembers", "getAllNonStaticMembers()Ljava/util/Collection;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "allStaticMembers", "getAllStaticMembers()Ljava/util/Collection;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "declaredMembers", "getDeclaredMembers()Ljava/util/Collection;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Data.class), "allMembers", "getAllMembers()Ljava/util/Collection;"))};
        }

        @NotNull
        public final ClassDescriptor getDescriptor() {
            return (ClassDescriptor)this.descriptor$delegate.getValue(this, $$delegatedProperties[0]);
        }

        @NotNull
        public final List<Annotation> getAnnotations() {
            return (List)this.annotations$delegate.getValue(this, $$delegatedProperties[1]);
        }

        @Nullable
        public final String getSimpleName() {
            return (String)this.simpleName$delegate.getValue(this, $$delegatedProperties[2]);
        }

        @Nullable
        public final String getQualifiedName() {
            return (String)this.qualifiedName$delegate.getValue(this, $$delegatedProperties[3]);
        }

        private final String calculateLocalClassName(Class<?> jClass) {
            String name = jClass.getSimpleName();
            Method method = jClass.getEnclosingMethod();
            if (method != null) {
                Method method2 = method;
                boolean bl = false;
                boolean bl2 = false;
                Method method3 = method2;
                boolean bl3 = false;
                String string = name;
                Intrinsics.checkNotNullExpressionValue(string, "name");
                return StringsKt.substringAfter$default(string, method3.getName() + "$", null, 2, null);
            }
            Constructor<?> constructor = jClass.getEnclosingConstructor();
            if (constructor != null) {
                Constructor<?> constructor2 = constructor;
                boolean bl = false;
                boolean bl4 = false;
                Constructor<?> constructor3 = constructor2;
                boolean bl5 = false;
                String string = name;
                Intrinsics.checkNotNullExpressionValue(string, "name");
                return StringsKt.substringAfter$default(string, constructor3.getName() + "$", null, 2, null);
            }
            String string = name;
            Intrinsics.checkNotNullExpressionValue(string, "name");
            return StringsKt.substringAfter$default(string, '$', null, 2, null);
        }

        @NotNull
        public final Collection<KFunction<T>> getConstructors() {
            return (Collection)this.constructors$delegate.getValue(this, $$delegatedProperties[4]);
        }

        @NotNull
        public final Collection<KClass<?>> getNestedClasses() {
            return (Collection)this.nestedClasses$delegate.getValue(this, $$delegatedProperties[5]);
        }

        @Nullable
        public final T getObjectInstance() {
            return this.objectInstance$delegate.getValue(this, $$delegatedProperties[6]);
        }

        @NotNull
        public final List<KTypeParameter> getTypeParameters() {
            return (List)this.typeParameters$delegate.getValue(this, $$delegatedProperties[7]);
        }

        @NotNull
        public final List<KType> getSupertypes() {
            return (List)this.supertypes$delegate.getValue(this, $$delegatedProperties[8]);
        }

        @NotNull
        public final List<KClass<? extends T>> getSealedSubclasses() {
            return (List)this.sealedSubclasses$delegate.getValue(this, $$delegatedProperties[9]);
        }

        @NotNull
        public final Collection<KCallableImpl<?>> getDeclaredNonStaticMembers() {
            return (Collection)this.declaredNonStaticMembers$delegate.getValue(this, $$delegatedProperties[10]);
        }

        private final Collection<KCallableImpl<?>> getDeclaredStaticMembers() {
            return (Collection)this.declaredStaticMembers$delegate.getValue(this, $$delegatedProperties[11]);
        }

        private final Collection<KCallableImpl<?>> getInheritedNonStaticMembers() {
            return (Collection)this.inheritedNonStaticMembers$delegate.getValue(this, $$delegatedProperties[12]);
        }

        private final Collection<KCallableImpl<?>> getInheritedStaticMembers() {
            return (Collection)this.inheritedStaticMembers$delegate.getValue(this, $$delegatedProperties[13]);
        }

        @NotNull
        public final Collection<KCallableImpl<?>> getAllNonStaticMembers() {
            return (Collection)this.allNonStaticMembers$delegate.getValue(this, $$delegatedProperties[14]);
        }

        @NotNull
        public final Collection<KCallableImpl<?>> getAllStaticMembers() {
            return (Collection)this.allStaticMembers$delegate.getValue(this, $$delegatedProperties[15]);
        }

        @NotNull
        public final Collection<KCallableImpl<?>> getDeclaredMembers() {
            return (Collection)this.declaredMembers$delegate.getValue(this, $$delegatedProperties[16]);
        }

        @NotNull
        public final Collection<KCallableImpl<?>> getAllMembers() {
            return (Collection)this.allMembers$delegate.getValue(this, $$delegatedProperties[17]);
        }

        public Data() {
            super(KClassImpl.this);
            this.descriptor$delegate = ReflectProperties.lazySoft((Function0)new Function0<ClassDescriptor>(this){
                final /* synthetic */ Data this$0;

                public final ClassDescriptor invoke() {
                    ClassDescriptor descriptor2;
                    ClassId classId = KClassImpl.access$getClassId$p(this.this$0.KClassImpl.this);
                    RuntimeModuleData moduleData2 = this.this$0.KClassImpl.this.getData().invoke().getModuleData();
                    ClassDescriptor classDescriptor = descriptor2 = classId.isLocal() ? moduleData2.getDeserialization().deserializeClass(classId) : FindClassInModuleKt.findClassAcrossModuleDependencies(moduleData2.getModule(), classId);
                    if (classDescriptor == null) {
                        Void void_ = KClassImpl.access$reportUnresolvedClass(this.this$0.KClassImpl.this);
                        throw null;
                    }
                    return classDescriptor;
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.annotations$delegate = ReflectProperties.lazySoft((Function0)new Function0<List<? extends Annotation>>(this){
                final /* synthetic */ Data this$0;

                public final List<Annotation> invoke() {
                    return UtilKt.computeAnnotations(this.this$0.getDescriptor());
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.simpleName$delegate = ReflectProperties.lazySoft((Function0)new Function0<String>(this){
                final /* synthetic */ Data this$0;

                @Nullable
                public final String invoke() {
                    String string;
                    if (this.this$0.KClassImpl.this.getJClass().isAnonymousClass()) {
                        return null;
                    }
                    ClassId classId = KClassImpl.access$getClassId$p(this.this$0.KClassImpl.this);
                    if (classId.isLocal()) {
                        string = Data.access$calculateLocalClassName(this.this$0, this.this$0.KClassImpl.this.getJClass());
                    } else {
                        String string2 = classId.getShortClassName().asString();
                        string = string2;
                        Intrinsics.checkNotNullExpressionValue(string2, "classId.shortClassName.asString()");
                    }
                    return string;
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.qualifiedName$delegate = ReflectProperties.lazySoft((Function0)new Function0<String>(this){
                final /* synthetic */ Data this$0;

                @Nullable
                public final String invoke() {
                    if (this.this$0.KClassImpl.this.getJClass().isAnonymousClass()) {
                        return null;
                    }
                    ClassId classId = KClassImpl.access$getClassId$p(this.this$0.KClassImpl.this);
                    return classId.isLocal() ? null : classId.asSingleFqName().asString();
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.constructors$delegate = ReflectProperties.lazySoft(new Function0<List<? extends KFunction<? extends T>>>(this){
                final /* synthetic */ Data this$0;

                /*
                 * WARNING - void declaration
                 */
                public final List<KFunction<T>> invoke() {
                    void $this$mapTo$iv$iv;
                    Iterable $this$map$iv = this.this$0.KClassImpl.this.getConstructorDescriptors();
                    boolean $i$f$map = false;
                    Iterable iterable = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList<E>(kotlin.collections.CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        void descriptor2;
                        ConstructorDescriptor constructorDescriptor = (ConstructorDescriptor)item$iv$iv;
                        Collection collection = destination$iv$iv;
                        boolean bl = false;
                        KFunction kFunction = new KFunctionImpl(this.this$0.KClassImpl.this, (FunctionDescriptor)descriptor2);
                        collection.add(kFunction);
                    }
                    return (List)destination$iv$iv;
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.nestedClasses$delegate = ReflectProperties.lazySoft((Function0)new Function0<List<? extends KClassImpl<? extends Object>>>(this){
                final /* synthetic */ Data this$0;

                /*
                 * WARNING - void declaration
                 */
                public final List<KClassImpl<? extends Object>> invoke() {
                    void $this$mapNotNullTo$iv$iv;
                    Iterable $this$filterNotTo$iv$iv;
                    Iterable $this$filterNot$iv = ResolutionScope.DefaultImpls.getContributedDescriptors$default(this.this$0.getDescriptor().getUnsubstitutedInnerClassesScope(), null, null, 3, null);
                    boolean $i$f$filterNot = false;
                    Iterable iterable = $this$filterNot$iv;
                    Collection destination$iv$iv = new ArrayList<E>();
                    boolean $i$f$filterNotTo = false;
                    for (T element$iv$iv : $this$filterNotTo$iv$iv) {
                        DeclarationDescriptor p1 = (DeclarationDescriptor)element$iv$iv;
                        boolean bl = false;
                        if (DescriptorUtils.isEnumEntry(p1)) continue;
                        destination$iv$iv.add(element$iv$iv);
                    }
                    Iterable $this$mapNotNull$iv = (List)destination$iv$iv;
                    boolean $i$f$mapNotNull = false;
                    $this$filterNotTo$iv$iv = $this$mapNotNull$iv;
                    destination$iv$iv = new ArrayList<E>();
                    boolean $i$f$mapNotNullTo = false;
                    void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                    boolean $i$f$forEach = false;
                    Iterator<T> iterator2 = $this$forEach$iv$iv$iv.iterator();
                    while (iterator2.hasNext()) {
                        KClassImpl<?> kClassImpl;
                        T element$iv$iv$iv;
                        T element$iv$iv = element$iv$iv$iv = iterator2.next();
                        boolean bl = false;
                        DeclarationDescriptor nestedClass = (DeclarationDescriptor)element$iv$iv;
                        boolean bl2 = false;
                        DeclarationDescriptor declarationDescriptor = nestedClass;
                        if (declarationDescriptor == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                        }
                        Class<?> jClass = UtilKt.toJavaClass((ClassDescriptor)declarationDescriptor);
                        if (jClass != null) {
                            Class<?> clazz;
                            boolean bl3 = false;
                            boolean bl4 = false;
                            Class<?> it = clazz;
                            boolean bl5 = false;
                            kClassImpl = new KClassImpl<?>(it);
                        } else {
                            kClassImpl = null;
                        }
                        if (kClassImpl == null) continue;
                        KClassImpl<?> kClassImpl2 = kClassImpl;
                        boolean bl6 = false;
                        boolean bl7 = false;
                        KClassImpl<?> it$iv$iv = kClassImpl2;
                        boolean bl8 = false;
                        destination$iv$iv.add(it$iv$iv);
                    }
                    return (List)destination$iv$iv;
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.objectInstance$delegate = ReflectProperties.lazy(new Function0<T>(this){
                final /* synthetic */ Data this$0;

                @Nullable
                public final T invoke() {
                    ClassDescriptor descriptor2 = this.this$0.getDescriptor();
                    if (descriptor2.getKind() != ClassKind.OBJECT) {
                        return null;
                    }
                    Field field = descriptor2.isCompanionObject() && !CompanionObjectMapping.INSTANCE.isMappedIntrinsicCompanionObject(descriptor2) ? this.this$0.KClassImpl.this.getJClass().getEnclosingClass().getDeclaredField(descriptor2.getName().asString()) : this.this$0.KClassImpl.this.getJClass().getDeclaredField("INSTANCE");
                    Object object = field.get(null);
                    if (object == null) {
                        throw new NullPointerException("null cannot be cast to non-null type T");
                    }
                    return (T)object;
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.typeParameters$delegate = ReflectProperties.lazySoft((Function0)new Function0<List<? extends KTypeParameterImpl>>(this){
                final /* synthetic */ Data this$0;

                /*
                 * WARNING - void declaration
                 */
                public final List<KTypeParameterImpl> invoke() {
                    void $this$mapTo$iv$iv;
                    List<TypeParameterDescriptor> list = this.this$0.getDescriptor().getDeclaredTypeParameters();
                    Intrinsics.checkNotNullExpressionValue(list, "descriptor.declaredTypeParameters");
                    Iterable $this$map$iv = list;
                    boolean $i$f$map = false;
                    Iterable iterable = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList<E>(kotlin.collections.CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        void descriptor2;
                        TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor)item$iv$iv;
                        Collection collection = destination$iv$iv;
                        boolean bl = false;
                        KTypeParameterOwnerImpl kTypeParameterOwnerImpl = this.this$0.KClassImpl.this;
                        void v2 = descriptor2;
                        Intrinsics.checkNotNullExpressionValue(v2, "descriptor");
                        KTypeParameterImpl kTypeParameterImpl = new KTypeParameterImpl(kTypeParameterOwnerImpl, (TypeParameterDescriptor)v2);
                        collection.add(kTypeParameterImpl);
                    }
                    return (List)destination$iv$iv;
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.supertypes$delegate = ReflectProperties.lazySoft((Function0)new Function0<List<? extends KTypeImpl>>(this){
                final /* synthetic */ Data this$0;

                /*
                 * WARNING - void declaration
                 */
                public final List<KTypeImpl> invoke() {
                    TypeConstructor typeConstructor2 = this.this$0.getDescriptor().getTypeConstructor();
                    Intrinsics.checkNotNullExpressionValue(typeConstructor2, "descriptor.typeConstructor");
                    Collection<KotlinType> collection = typeConstructor2.getSupertypes();
                    Intrinsics.checkNotNullExpressionValue(collection, "descriptor.typeConstructor.supertypes");
                    Collection<KotlinType> kotlinTypes = collection;
                    ArrayList<E> result2 = new ArrayList<E>(kotlinTypes.size());
                    Iterable $this$mapTo$iv = kotlinTypes;
                    boolean $i$f$mapTo = false;
                    for (T item$iv : $this$mapTo$iv) {
                        void kotlinType;
                        KotlinType kotlinType2 = (KotlinType)item$iv;
                        Collection collection2 = result2;
                        boolean bl = false;
                        void v2 = kotlinType;
                        Intrinsics.checkNotNullExpressionValue(v2, "kotlinType");
                        KTypeImpl kTypeImpl = new KTypeImpl((KotlinType)v2, (Function0<? extends Type>)new Function0<Type>((KotlinType)kotlinType, this){
                            final /* synthetic */ KotlinType $kotlinType;
                            final /* synthetic */ supertypes.2 this$0;
                            {
                                this.$kotlinType = kotlinType;
                                this.this$0 = var2_2;
                                super(0);
                            }

                            @NotNull
                            public final Type invoke() {
                                Type type2;
                                ClassifierDescriptor superClass = this.$kotlinType.getConstructor().getDeclarationDescriptor();
                                if (!(superClass instanceof ClassDescriptor)) {
                                    throw (Throwable)new KotlinReflectionInternalError("Supertype not a class: " + superClass);
                                }
                                Class<?> clazz = UtilKt.toJavaClass((ClassDescriptor)superClass);
                                if (clazz == null) {
                                    throw (Throwable)new KotlinReflectionInternalError("Unsupported superclass of " + this.this$0.this$0 + ": " + superClass);
                                }
                                Class<?> superJavaClass = clazz;
                                if (Intrinsics.areEqual(this.this$0.this$0.KClassImpl.this.getJClass().getSuperclass(), superJavaClass)) {
                                    type2 = this.this$0.this$0.KClassImpl.this.getJClass().getGenericSuperclass();
                                } else {
                                    Class<?>[] classArray = this.this$0.this$0.KClassImpl.this.getJClass().getInterfaces();
                                    Intrinsics.checkNotNullExpressionValue(classArray, "jClass.interfaces");
                                    int index = ArraysKt.indexOf(classArray, superJavaClass);
                                    if (index < 0) {
                                        throw (Throwable)new KotlinReflectionInternalError("No superclass of " + this.this$0.this$0 + " in Java reflection for " + superClass);
                                    }
                                    type2 = this.this$0.this$0.KClassImpl.this.getJClass().getGenericInterfaces()[index];
                                }
                                Intrinsics.checkNotNullExpressionValue(type2, "if (jClass.superclass ==\u2026ex]\n                    }");
                                return type2;
                            }
                        });
                        collection2.add(kTypeImpl);
                    }
                    Collection cfr_ignored_0 = (Collection)result2;
                    if (!KotlinBuiltIns.isSpecialClassWithNoSupertypes(this.this$0.getDescriptor())) {
                        boolean bl;
                        block6: {
                            Iterable $this$all$iv = result2;
                            boolean $i$f$all = false;
                            if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                                bl = true;
                            } else {
                                for (T element$iv : $this$all$iv) {
                                    ClassKind classKind;
                                    KTypeImpl it = (KTypeImpl)element$iv;
                                    boolean bl2 = false;
                                    ClassDescriptor classDescriptor = DescriptorUtils.getClassDescriptorForType(it.getType());
                                    Intrinsics.checkNotNullExpressionValue(classDescriptor, "DescriptorUtils.getClassDescriptorForType(it.type)");
                                    Intrinsics.checkNotNullExpressionValue((Object)((Object)classDescriptor.getKind()), "DescriptorUtils.getClass\u2026ptorForType(it.type).kind");
                                    if (classKind == ClassKind.INTERFACE || classKind == ClassKind.ANNOTATION_CLASS) continue;
                                    bl = false;
                                    break block6;
                                }
                                bl = true;
                            }
                        }
                        if (bl) {
                            Collection collection3 = result2;
                            SimpleType simpleType2 = DescriptorUtilsKt.getBuiltIns(this.this$0.getDescriptor()).getAnyType();
                            Intrinsics.checkNotNullExpressionValue(simpleType2, "descriptor.builtIns.anyType");
                            KTypeImpl kTypeImpl = new KTypeImpl(simpleType2, supertypes.3.INSTANCE);
                            boolean bl3 = false;
                            collection3.add(kTypeImpl);
                        }
                    }
                    return CollectionsKt.compact(result2);
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.sealedSubclasses$delegate = ReflectProperties.lazySoft(new Function0<List<? extends KClassImpl<? extends T>>>(this){
                final /* synthetic */ Data this$0;

                /*
                 * WARNING - void declaration
                 */
                public final List<KClassImpl<? extends T>> invoke() {
                    void $this$mapNotNullTo$iv$iv;
                    Collection<ClassDescriptor> collection = this.this$0.getDescriptor().getSealedSubclasses();
                    Intrinsics.checkNotNullExpressionValue(collection, "descriptor.sealedSubclasses");
                    Iterable $this$mapNotNull$iv = collection;
                    boolean $i$f$mapNotNull = false;
                    Iterable iterable = $this$mapNotNull$iv;
                    Collection destination$iv$iv = new ArrayList<E>();
                    boolean $i$f$mapNotNullTo = false;
                    void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                    boolean $i$f$forEach = false;
                    Iterator<T> iterator2 = $this$forEach$iv$iv$iv.iterator();
                    while (iterator2.hasNext()) {
                        KClassImpl<?> kClassImpl;
                        T element$iv$iv$iv;
                        T element$iv$iv = element$iv$iv$iv = iterator2.next();
                        boolean bl = false;
                        ClassDescriptor subclass = (ClassDescriptor)element$iv$iv;
                        boolean bl2 = false;
                        ClassDescriptor classDescriptor = subclass;
                        if (classDescriptor == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                        }
                        Class<?> jClass = UtilKt.toJavaClass(classDescriptor);
                        if (jClass != null) {
                            Class<?> clazz;
                            boolean bl3 = false;
                            boolean bl4 = false;
                            Class<?> it = clazz;
                            boolean bl5 = false;
                            kClassImpl = new KClassImpl<?>(it);
                        } else {
                            kClassImpl = null;
                        }
                        if (kClassImpl == null) continue;
                        KClassImpl<?> kClassImpl2 = kClassImpl;
                        boolean bl6 = false;
                        boolean bl7 = false;
                        KClassImpl<?> it$iv$iv = kClassImpl2;
                        boolean bl8 = false;
                        destination$iv$iv.add(it$iv$iv);
                    }
                    return (List)destination$iv$iv;
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.declaredNonStaticMembers$delegate = ReflectProperties.lazySoft(new Function0<Collection<? extends KCallableImpl<?>>>(this){
                final /* synthetic */ Data this$0;

                public final Collection<KCallableImpl<?>> invoke() {
                    return this.this$0.KClassImpl.this.getMembers(this.this$0.KClassImpl.this.getMemberScope$kotlin_reflection(), KDeclarationContainerImpl.MemberBelonginess.DECLARED);
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.declaredStaticMembers$delegate = ReflectProperties.lazySoft(new Function0<Collection<? extends KCallableImpl<?>>>(this){
                final /* synthetic */ Data this$0;

                public final Collection<KCallableImpl<?>> invoke() {
                    return this.this$0.KClassImpl.this.getMembers(this.this$0.KClassImpl.this.getStaticScope$kotlin_reflection(), KDeclarationContainerImpl.MemberBelonginess.DECLARED);
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.inheritedNonStaticMembers$delegate = ReflectProperties.lazySoft(new Function0<Collection<? extends KCallableImpl<?>>>(this){
                final /* synthetic */ Data this$0;

                public final Collection<KCallableImpl<?>> invoke() {
                    return this.this$0.KClassImpl.this.getMembers(this.this$0.KClassImpl.this.getMemberScope$kotlin_reflection(), KDeclarationContainerImpl.MemberBelonginess.INHERITED);
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.inheritedStaticMembers$delegate = ReflectProperties.lazySoft(new Function0<Collection<? extends KCallableImpl<?>>>(this){
                final /* synthetic */ Data this$0;

                public final Collection<KCallableImpl<?>> invoke() {
                    return this.this$0.KClassImpl.this.getMembers(this.this$0.KClassImpl.this.getStaticScope$kotlin_reflection(), KDeclarationContainerImpl.MemberBelonginess.INHERITED);
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.allNonStaticMembers$delegate = ReflectProperties.lazySoft(new Function0<List<? extends KCallableImpl<?>>>(this){
                final /* synthetic */ Data this$0;

                public final List<KCallableImpl<?>> invoke() {
                    return kotlin.collections.CollectionsKt.plus(this.this$0.getDeclaredNonStaticMembers(), (Iterable)Data.access$getInheritedNonStaticMembers$p(this.this$0));
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.allStaticMembers$delegate = ReflectProperties.lazySoft(new Function0<List<? extends KCallableImpl<?>>>(this){
                final /* synthetic */ Data this$0;

                public final List<KCallableImpl<?>> invoke() {
                    return kotlin.collections.CollectionsKt.plus(Data.access$getDeclaredStaticMembers$p(this.this$0), (Iterable)Data.access$getInheritedStaticMembers$p(this.this$0));
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.declaredMembers$delegate = ReflectProperties.lazySoft(new Function0<List<? extends KCallableImpl<?>>>(this){
                final /* synthetic */ Data this$0;

                public final List<KCallableImpl<?>> invoke() {
                    return kotlin.collections.CollectionsKt.plus(this.this$0.getDeclaredNonStaticMembers(), (Iterable)Data.access$getDeclaredStaticMembers$p(this.this$0));
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
            this.allMembers$delegate = ReflectProperties.lazySoft(new Function0<List<? extends KCallableImpl<?>>>(this){
                final /* synthetic */ Data this$0;

                public final List<KCallableImpl<?>> invoke() {
                    return kotlin.collections.CollectionsKt.plus(this.this$0.getAllNonStaticMembers(), (Iterable)this.this$0.getAllStaticMembers());
                }
                {
                    this.this$0 = data2;
                    super(0);
                }
            });
        }

        public static final /* synthetic */ String access$calculateLocalClassName(Data $this, Class jClass) {
            return $this.calculateLocalClassName(jClass);
        }

        public static final /* synthetic */ Collection access$getInheritedNonStaticMembers$p(Data $this) {
            return $this.getInheritedNonStaticMembers();
        }

        public static final /* synthetic */ Collection access$getDeclaredStaticMembers$p(Data $this) {
            return $this.getDeclaredStaticMembers();
        }

        public static final /* synthetic */ Collection access$getInheritedStaticMembers$p(Data $this) {
            return $this.getInheritedStaticMembers();
        }
    }
}

