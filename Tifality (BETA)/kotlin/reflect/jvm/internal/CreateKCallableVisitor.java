/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl;
import kotlin.reflect.jvm.internal.KFunctionImpl;
import kotlin.reflect.jvm.internal.KMutableProperty0Impl;
import kotlin.reflect.jvm.internal.KMutableProperty1Impl;
import kotlin.reflect.jvm.internal.KMutableProperty2Impl;
import kotlin.reflect.jvm.internal.KProperty0Impl;
import kotlin.reflect.jvm.internal.KProperty1Impl;
import kotlin.reflect.jvm.internal.KProperty2Impl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorVisitorEmptyBodies;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0010\u0018\u00002\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J!\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0003H\u0016\u00a2\u0006\u0002\u0010\u000bJ!\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\b\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u0003H\u0016\u00a2\u0006\u0002\u0010\u000eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lkotlin/reflect/jvm/internal/CreateKCallableVisitor;", "Lkotlin/reflect/jvm/internal/impl/descriptors/impl/DeclarationDescriptorVisitorEmptyBodies;", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "", "container", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "(Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;)V", "visitFunctionDescriptor", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/FunctionDescriptor;", "data", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lkotlin/Unit;)Lkotlin/reflect/jvm/internal/KCallableImpl;", "visitPropertyDescriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lkotlin/Unit;)Lkotlin/reflect/jvm/internal/KCallableImpl;", "kotlin-reflection"})
public class CreateKCallableVisitor
extends DeclarationDescriptorVisitorEmptyBodies<KCallableImpl<?>, Unit> {
    private final KDeclarationContainerImpl container;

    @Override
    @NotNull
    public KCallableImpl<?> visitPropertyDescriptor(@NotNull PropertyDescriptor descriptor2, @NotNull Unit data2) {
        int n;
        int n2;
        ReceiverParameterDescriptor it;
        boolean bl;
        boolean bl2;
        ReceiverParameterDescriptor receiverParameterDescriptor;
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        Intrinsics.checkNotNullParameter(data2, "data");
        ReceiverParameterDescriptor receiverParameterDescriptor2 = descriptor2.getDispatchReceiverParameter();
        if (receiverParameterDescriptor2 != null) {
            receiverParameterDescriptor = receiverParameterDescriptor2;
            bl2 = false;
            bl = false;
            it = receiverParameterDescriptor;
            boolean bl3 = false;
            n2 = 1;
        } else {
            n2 = 0;
        }
        ReceiverParameterDescriptor receiverParameterDescriptor3 = descriptor2.getExtensionReceiverParameter();
        if (receiverParameterDescriptor3 != null) {
            receiverParameterDescriptor = receiverParameterDescriptor3;
            bl2 = false;
            bl = false;
            it = receiverParameterDescriptor;
            int n3 = n2;
            boolean bl4 = false;
            int n4 = 1;
            n2 = n3;
            n = n4;
        } else {
            n = 0;
        }
        int receiverCount = n2 + n;
        if (descriptor2.isVar()) {
            switch (receiverCount) {
                case 0: {
                    return new KMutableProperty0Impl(this.container, descriptor2);
                }
                case 1: {
                    return new KMutableProperty1Impl(this.container, descriptor2);
                }
                case 2: {
                    return new KMutableProperty2Impl(this.container, descriptor2);
                }
            }
        } else {
            switch (receiverCount) {
                case 0: {
                    return new KProperty0Impl(this.container, descriptor2);
                }
                case 1: {
                    return new KProperty1Impl(this.container, descriptor2);
                }
                case 2: {
                    return new KProperty2Impl(this.container, descriptor2);
                }
            }
        }
        throw (Throwable)new KotlinReflectionInternalError("Unsupported property: " + descriptor2);
    }

    @Override
    @NotNull
    public KCallableImpl<?> visitFunctionDescriptor(@NotNull FunctionDescriptor descriptor2, @NotNull Unit data2) {
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        Intrinsics.checkNotNullParameter(data2, "data");
        return new KFunctionImpl(this.container, descriptor2);
    }

    public CreateKCallableVisitor(@NotNull KDeclarationContainerImpl container) {
        Intrinsics.checkNotNullParameter(container, "container");
        this.container = container;
    }
}

