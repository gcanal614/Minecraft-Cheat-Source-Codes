/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal;

import java.util.Collections;
import java.util.List;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.MutablePropertyReference0;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference2;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference2;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KProperty2;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.KVariance;
import kotlin.reflect.full.KClassifiers;
import kotlin.reflect.jvm.ReflectLambdaKt;
import kotlin.reflect.jvm.internal.EmptyContainerForLocal;
import kotlin.reflect.jvm.internal.KClassCacheKt;
import kotlin.reflect.jvm.internal.KClassImpl;
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl;
import kotlin.reflect.jvm.internal.KFunctionImpl;
import kotlin.reflect.jvm.internal.KMutableProperty0Impl;
import kotlin.reflect.jvm.internal.KMutableProperty1Impl;
import kotlin.reflect.jvm.internal.KMutableProperty2Impl;
import kotlin.reflect.jvm.internal.KPackageImpl;
import kotlin.reflect.jvm.internal.KProperty0Impl;
import kotlin.reflect.jvm.internal.KProperty1Impl;
import kotlin.reflect.jvm.internal.KProperty2Impl;
import kotlin.reflect.jvm.internal.ModuleByClassLoaderKt;
import kotlin.reflect.jvm.internal.ReflectionObjectRenderer;
import kotlin.reflect.jvm.internal.UtilKt;

public class ReflectionFactoryImpl
extends ReflectionFactory {
    @Override
    public KClass createKotlinClass(Class javaClass) {
        return new KClassImpl(javaClass);
    }

    @Override
    public KClass createKotlinClass(Class javaClass, String internalName) {
        return new KClassImpl(javaClass);
    }

    @Override
    public KDeclarationContainer getOrCreateKotlinPackage(Class javaClass, String moduleName) {
        return new KPackageImpl(javaClass, moduleName);
    }

    @Override
    public KClass getOrCreateKotlinClass(Class javaClass) {
        return KClassCacheKt.getOrCreateKotlinClass(javaClass);
    }

    @Override
    public KClass getOrCreateKotlinClass(Class javaClass, String internalName) {
        return KClassCacheKt.getOrCreateKotlinClass(javaClass);
    }

    @Override
    public String renderLambdaToString(Lambda lambda2) {
        return this.renderLambdaToString((FunctionBase)lambda2);
    }

    @Override
    public String renderLambdaToString(FunctionBase lambda2) {
        KFunctionImpl impl;
        KFunction kFunction = ReflectLambdaKt.reflect(lambda2);
        if (kFunction != null && (impl = UtilKt.asKFunctionImpl(kFunction)) != null) {
            return ReflectionObjectRenderer.INSTANCE.renderLambda(impl.getDescriptor());
        }
        return super.renderLambdaToString(lambda2);
    }

    @Override
    public KFunction function(FunctionReference f2) {
        return new KFunctionImpl(ReflectionFactoryImpl.getOwner(f2), f2.getName(), f2.getSignature(), f2.getBoundReceiver());
    }

    @Override
    public KProperty0 property0(PropertyReference0 p) {
        return new KProperty0Impl(ReflectionFactoryImpl.getOwner(p), p.getName(), p.getSignature(), p.getBoundReceiver());
    }

    @Override
    public KMutableProperty0 mutableProperty0(MutablePropertyReference0 p) {
        return new KMutableProperty0Impl(ReflectionFactoryImpl.getOwner(p), p.getName(), p.getSignature(), p.getBoundReceiver());
    }

    @Override
    public KProperty1 property1(PropertyReference1 p) {
        return new KProperty1Impl(ReflectionFactoryImpl.getOwner(p), p.getName(), p.getSignature(), p.getBoundReceiver());
    }

    @Override
    public KMutableProperty1 mutableProperty1(MutablePropertyReference1 p) {
        return new KMutableProperty1Impl(ReflectionFactoryImpl.getOwner(p), p.getName(), p.getSignature(), p.getBoundReceiver());
    }

    @Override
    public KProperty2 property2(PropertyReference2 p) {
        return new KProperty2Impl(ReflectionFactoryImpl.getOwner(p), p.getName(), p.getSignature());
    }

    @Override
    public KMutableProperty2 mutableProperty2(MutablePropertyReference2 p) {
        return new KMutableProperty2Impl(ReflectionFactoryImpl.getOwner(p), p.getName(), p.getSignature());
    }

    private static KDeclarationContainerImpl getOwner(CallableReference reference) {
        KDeclarationContainer owner = reference.getOwner();
        return owner instanceof KDeclarationContainerImpl ? (KDeclarationContainerImpl)owner : EmptyContainerForLocal.INSTANCE;
    }

    @Override
    public KType typeOf(KClassifier klass, List<KTypeProjection> arguments2, boolean isMarkedNullable) {
        return KClassifiers.createType(klass, arguments2, isMarkedNullable, Collections.emptyList());
    }

    @Override
    public KTypeParameter typeParameter(Object container, String name, KVariance variance, boolean isReified) {
        List<KTypeParameter> typeParameters2;
        if (container instanceof KClass) {
            typeParameters2 = ((KClass)container).getTypeParameters();
        } else if (container instanceof KCallable) {
            typeParameters2 = ((KCallable)container).getTypeParameters();
        } else {
            throw new IllegalArgumentException("Type parameter container must be a class or a callable: " + container);
        }
        for (KTypeParameter typeParameter : typeParameters2) {
            if (!typeParameter.getName().equals(name)) continue;
            return typeParameter;
        }
        throw new IllegalArgumentException("Type parameter " + name + " is not found in container: " + container);
    }

    @Override
    public void setUpperBounds(KTypeParameter typeParameter, List<KType> bounds) {
    }

    public static void clearCaches() {
        KClassCacheKt.clearKClassCache();
        ModuleByClassLoaderKt.clearModuleByClassLoaderCache();
    }
}

