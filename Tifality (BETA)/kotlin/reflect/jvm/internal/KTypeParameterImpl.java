/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.TypeParameterReference;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVariance;
import kotlin.reflect.jvm.internal.CreateKCallableVisitor;
import kotlin.reflect.jvm.internal.KClassImpl;
import kotlin.reflect.jvm.internal.KClassifierImpl;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.KTypeParameterImpl$WhenMappings;
import kotlin.reflect.jvm.internal.KTypeParameterOwnerImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.ReflectProperties;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectKotlinClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmPackagePartSource;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0013\u0010\u001c\u001a\u00020\u000b2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0096\u0002J\b\u0010\u001f\u001a\u00020 H\u0016J\b\u0010!\u001a\u00020\u000eH\u0016J\u0010\u0010\"\u001a\u0006\u0012\u0002\b\u00030#*\u00020$H\u0002J\u0010\u0010%\u001a\u0006\u0012\u0002\b\u00030&*\u00020'H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R!\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006("}, d2={"Lkotlin/reflect/jvm/internal/KTypeParameterImpl;", "Lkotlin/reflect/KTypeParameter;", "Lkotlin/reflect/jvm/internal/KClassifierImpl;", "container", "Lkotlin/reflect/jvm/internal/KTypeParameterOwnerImpl;", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/TypeParameterDescriptor;", "(Lkotlin/reflect/jvm/internal/KTypeParameterOwnerImpl;Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "isReified", "", "()Z", "name", "", "getName", "()Ljava/lang/String;", "upperBounds", "", "Lkotlin/reflect/KType;", "getUpperBounds", "()Ljava/util/List;", "upperBounds$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazySoftVal;", "variance", "Lkotlin/reflect/KVariance;", "getVariance", "()Lkotlin/reflect/KVariance;", "equals", "other", "", "hashCode", "", "toString", "getContainerClass", "Ljava/lang/Class;", "Lkotlin/reflect/jvm/internal/impl/serialization/deserialization/descriptors/DeserializedMemberDescriptor;", "toKClassImpl", "Lkotlin/reflect/jvm/internal/KClassImpl;", "Lkotlin/reflect/jvm/internal/impl/descriptors/ClassDescriptor;", "kotlin-reflection"})
public final class KTypeParameterImpl
implements KTypeParameter,
KClassifierImpl {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    @NotNull
    private final ReflectProperties.LazySoftVal upperBounds$delegate;
    private final KTypeParameterOwnerImpl container;
    @NotNull
    private final TypeParameterDescriptor descriptor;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(KTypeParameterImpl.class), "upperBounds", "getUpperBounds()Ljava/util/List;"))};
    }

    @Override
    @NotNull
    public String getName() {
        String string = this.getDescriptor().getName().asString();
        Intrinsics.checkNotNullExpressionValue(string, "descriptor.name.asString()");
        return string;
    }

    @Override
    @NotNull
    public List<KType> getUpperBounds() {
        return (List)this.upperBounds$delegate.getValue(this, $$delegatedProperties[0]);
    }

    @Override
    @NotNull
    public KVariance getVariance() {
        KVariance kVariance;
        switch (KTypeParameterImpl$WhenMappings.$EnumSwitchMapping$0[this.getDescriptor().getVariance().ordinal()]) {
            case 1: {
                kVariance = KVariance.INVARIANT;
                break;
            }
            case 2: {
                kVariance = KVariance.IN;
                break;
            }
            case 3: {
                kVariance = KVariance.OUT;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return kVariance;
    }

    @Override
    public boolean isReified() {
        return this.getDescriptor().isReified();
    }

    private final KClassImpl<?> toKClassImpl(ClassDescriptor $this$toKClassImpl) {
        Class<?> clazz = UtilKt.toJavaClass($this$toKClassImpl);
        KClassImpl kClassImpl = (KClassImpl)(clazz != null ? JvmClassMappingKt.getKotlinClass(clazz) : null);
        if (kClassImpl == null) {
            throw (Throwable)new KotlinReflectionInternalError("Type parameter container is not resolved: " + $this$toKClassImpl.getContainingDeclaration());
        }
        return kClassImpl;
    }

    private final Class<?> getContainerClass(DeserializedMemberDescriptor $this$getContainerClass) {
        Object object;
        Object $this$safeAs$iv = $this$getContainerClass.getContainerSource();
        boolean $i$f$safeAs = false;
        Object object2 = $this$safeAs$iv;
        if (!(object2 instanceof JvmPackagePartSource)) {
            object2 = null;
        }
        JvmPackagePartSource jvmPackagePartSource = (JvmPackagePartSource)object2;
        $this$safeAs$iv = jvmPackagePartSource != null ? jvmPackagePartSource.getKnownJvmBinaryClass() : null;
        $i$f$safeAs = false;
        Object object3 = $this$safeAs$iv;
        if (!(object3 instanceof ReflectKotlinClass)) {
            object3 = null;
        }
        if ((object = (ReflectKotlinClass)object3) == null || (object = ((ReflectKotlinClass)object).getKlass()) == null) {
            throw (Throwable)new KotlinReflectionInternalError("Container of deserialized member is not resolved: " + $this$getContainerClass);
        }
        return object;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof KTypeParameterImpl && Intrinsics.areEqual(this.container, ((KTypeParameterImpl)other).container) && Intrinsics.areEqual(this.getName(), ((KTypeParameterImpl)other).getName());
    }

    public int hashCode() {
        return this.container.hashCode() * 31 + this.getName().hashCode();
    }

    @NotNull
    public String toString() {
        return TypeParameterReference.Companion.toString(this);
    }

    @Override
    @NotNull
    public TypeParameterDescriptor getDescriptor() {
        return this.descriptor;
    }

    /*
     * WARNING - void declaration
     */
    public KTypeParameterImpl(@Nullable KTypeParameterOwnerImpl container, @NotNull TypeParameterDescriptor descriptor2) {
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        this.descriptor = descriptor2;
        this.upperBounds$delegate = ReflectProperties.lazySoft((Function0)new Function0<List<? extends KTypeImpl>>(this){
            final /* synthetic */ KTypeParameterImpl this$0;

            /*
             * WARNING - void declaration
             */
            public final List<KTypeImpl> invoke() {
                void $this$mapTo$iv$iv;
                List<KotlinType> list = this.this$0.getDescriptor().getUpperBounds();
                Intrinsics.checkNotNullExpressionValue(list, "descriptor.upperBounds");
                Iterable $this$map$iv = list;
                boolean $i$f$map = false;
                Iterable iterable = $this$map$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    void p1;
                    KotlinType kotlinType = (KotlinType)item$iv$iv;
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    KTypeImpl kTypeImpl = new KTypeImpl((KotlinType)p1, null, 2, null);
                    collection.add(kTypeImpl);
                }
                return (List)destination$iv$iv;
            }
            {
                this.this$0 = kTypeParameterImpl;
                super(0);
            }
        });
        KTypeParameterImpl kTypeParameterImpl = this;
        KTypeParameterOwnerImpl kTypeParameterOwnerImpl = container;
        if (kTypeParameterOwnerImpl == null) {
            KClassImpl<Object> kClassImpl;
            void $this$run;
            KTypeParameterImpl kTypeParameterImpl2 = this;
            boolean bl = false;
            boolean bl2 = false;
            KTypeParameterImpl kTypeParameterImpl3 = kTypeParameterImpl2;
            KTypeParameterImpl kTypeParameterImpl4 = kTypeParameterImpl;
            boolean bl3 = false;
            DeclarationDescriptor declarationDescriptor = $this$run.getDescriptor().getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "descriptor.containingDeclaration");
            DeclarationDescriptor declaration = declarationDescriptor;
            if (declaration instanceof ClassDescriptor) {
                kClassImpl = super.toKClassImpl((ClassDescriptor)declaration);
            } else if (declaration instanceof CallableMemberDescriptor) {
                KClassImpl kClassImpl2;
                DeclarationDescriptor declarationDescriptor2 = ((CallableMemberDescriptor)declaration).getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(declarationDescriptor2, "declaration.containingDeclaration");
                DeclarationDescriptor callableContainer = declarationDescriptor2;
                if (callableContainer instanceof ClassDescriptor) {
                    kClassImpl2 = super.toKClassImpl((ClassDescriptor)callableContainer);
                } else {
                    DeclarationDescriptor declarationDescriptor3 = declaration;
                    if (!(declarationDescriptor3 instanceof DeserializedMemberDescriptor)) {
                        declarationDescriptor3 = null;
                    }
                    DeserializedMemberDescriptor deserializedMemberDescriptor = (DeserializedMemberDescriptor)declarationDescriptor3;
                    if (deserializedMemberDescriptor == null) {
                        throw (Throwable)new KotlinReflectionInternalError("Non-class callable descriptor must be deserialized: " + declaration);
                    }
                    DeserializedMemberDescriptor deserializedMember = deserializedMemberDescriptor;
                    KClass<?> kClass = JvmClassMappingKt.getKotlinClass(super.getContainerClass(deserializedMember));
                    if (kClass == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KClassImpl<*>");
                    }
                    kClassImpl2 = (KClassImpl)kClass;
                }
                KClassImpl callableContainerClass = kClassImpl2;
                Object r = declaration.accept(new CreateKCallableVisitor(callableContainerClass), Unit.INSTANCE);
                kClassImpl = r;
                Intrinsics.checkNotNullExpressionValue(r, "declaration.accept(Creat\u2026bleContainerClass), Unit)");
            } else {
                throw (Throwable)new KotlinReflectionInternalError("Unknown type parameter container: " + declaration);
            }
            KTypeParameterOwnerImpl kTypeParameterOwnerImpl2 = kClassImpl;
            kTypeParameterImpl = kTypeParameterImpl4;
            kTypeParameterOwnerImpl = kTypeParameterOwnerImpl2;
        }
        kTypeParameterImpl.container = kTypeParameterOwnerImpl;
    }
}

