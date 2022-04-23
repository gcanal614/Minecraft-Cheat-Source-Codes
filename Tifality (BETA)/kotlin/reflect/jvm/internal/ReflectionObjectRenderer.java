/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.KParameterImpl;
import kotlin.reflect.jvm.internal.ReflectionObjectRenderer;
import kotlin.reflect.jvm.internal.ReflectionObjectRenderer$WhenMappings;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\nJ\u000e\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0014J\u001a\u0010\u0015\u001a\u00020\u0016*\u00060\u0017j\u0002`\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002J\u0018\u0010\u001b\u001a\u00020\u0016*\u00060\u0017j\u0002`\u00182\u0006\u0010\u001c\u001a\u00020\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2={"Lkotlin/reflect/jvm/internal/ReflectionObjectRenderer;", "", "()V", "renderer", "Lkotlin/reflect/jvm/internal/impl/renderer/DescriptorRenderer;", "renderCallable", "", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableDescriptor;", "renderFunction", "Lkotlin/reflect/jvm/internal/impl/descriptors/FunctionDescriptor;", "renderLambda", "invoke", "renderParameter", "parameter", "Lkotlin/reflect/jvm/internal/KParameterImpl;", "renderProperty", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "renderType", "type", "Lkotlin/reflect/jvm/internal/impl/types/KotlinType;", "appendReceiverType", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "receiver", "Lkotlin/reflect/jvm/internal/impl/descriptors/ReceiverParameterDescriptor;", "appendReceivers", "callable", "kotlin-reflection"})
public final class ReflectionObjectRenderer {
    private static final DescriptorRenderer renderer;
    public static final ReflectionObjectRenderer INSTANCE;

    private final void appendReceiverType(StringBuilder $this$appendReceiverType, ReceiverParameterDescriptor receiver) {
        if (receiver != null) {
            KotlinType kotlinType = receiver.getType();
            Intrinsics.checkNotNullExpressionValue(kotlinType, "receiver.type");
            $this$appendReceiverType.append(this.renderType(kotlinType));
            $this$appendReceiverType.append(".");
        }
    }

    private final void appendReceivers(StringBuilder $this$appendReceivers, CallableDescriptor callable) {
        boolean addParentheses;
        ReceiverParameterDescriptor dispatchReceiver = UtilKt.getInstanceReceiverParameter(callable);
        ReceiverParameterDescriptor extensionReceiver = callable.getExtensionReceiverParameter();
        this.appendReceiverType($this$appendReceivers, dispatchReceiver);
        boolean bl = addParentheses = dispatchReceiver != null && extensionReceiver != null;
        if (addParentheses) {
            $this$appendReceivers.append("(");
        }
        this.appendReceiverType($this$appendReceivers, extensionReceiver);
        if (addParentheses) {
            $this$appendReceivers.append(")");
        }
    }

    private final String renderCallable(CallableDescriptor descriptor2) {
        String string;
        CallableDescriptor callableDescriptor = descriptor2;
        if (callableDescriptor instanceof PropertyDescriptor) {
            string = this.renderProperty((PropertyDescriptor)descriptor2);
        } else if (callableDescriptor instanceof FunctionDescriptor) {
            string = this.renderFunction((FunctionDescriptor)descriptor2);
        } else {
            String string2 = "Illegal callable: " + descriptor2;
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string2.toString());
        }
        return string;
    }

    @NotNull
    public final String renderProperty(@NotNull PropertyDescriptor descriptor2) {
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl3 = false;
        boolean bl4 = false;
        StringBuilder $this$buildString = stringBuilder;
        boolean bl5 = false;
        $this$buildString.append(descriptor2.isVar() ? "var " : "val ");
        INSTANCE.appendReceivers($this$buildString, descriptor2);
        Name name = descriptor2.getName();
        Intrinsics.checkNotNullExpressionValue(name, "descriptor.name");
        $this$buildString.append(renderer.renderName(name, true));
        $this$buildString.append(": ");
        KotlinType kotlinType = descriptor2.getType();
        Intrinsics.checkNotNullExpressionValue(kotlinType, "descriptor.type");
        $this$buildString.append(INSTANCE.renderType(kotlinType));
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    @NotNull
    public final String renderFunction(@NotNull FunctionDescriptor descriptor2) {
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl3 = false;
        boolean bl4 = false;
        StringBuilder $this$buildString = stringBuilder;
        boolean bl5 = false;
        $this$buildString.append("fun ");
        INSTANCE.appendReceivers($this$buildString, descriptor2);
        Name name = descriptor2.getName();
        Intrinsics.checkNotNullExpressionValue(name, "descriptor.name");
        $this$buildString.append(renderer.renderName(name, true));
        List<ValueParameterDescriptor> list = descriptor2.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(list, "descriptor.valueParameters");
        CollectionsKt.joinTo$default(list, $this$buildString, ", ", "(", ")", 0, null, renderFunction.1.1.INSTANCE, 48, null);
        $this$buildString.append(": ");
        KotlinType kotlinType = descriptor2.getReturnType();
        Intrinsics.checkNotNull(kotlinType);
        Intrinsics.checkNotNullExpressionValue(kotlinType, "descriptor.returnType!!");
        $this$buildString.append(INSTANCE.renderType(kotlinType));
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    @NotNull
    public final String renderLambda(@NotNull FunctionDescriptor invoke) {
        Intrinsics.checkNotNullParameter(invoke, "invoke");
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl3 = false;
        boolean bl4 = false;
        StringBuilder $this$buildString = stringBuilder;
        boolean bl5 = false;
        INSTANCE.appendReceivers($this$buildString, invoke);
        List<ValueParameterDescriptor> list = invoke.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(list, "invoke.valueParameters");
        CollectionsKt.joinTo$default(list, $this$buildString, ", ", "(", ")", 0, null, renderLambda.1.1.INSTANCE, 48, null);
        $this$buildString.append(" -> ");
        KotlinType kotlinType = invoke.getReturnType();
        Intrinsics.checkNotNull(kotlinType);
        Intrinsics.checkNotNullExpressionValue(kotlinType, "invoke.returnType!!");
        $this$buildString.append(INSTANCE.renderType(kotlinType));
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    @NotNull
    public final String renderParameter(@NotNull KParameterImpl parameter) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl3 = false;
        boolean bl4 = false;
        StringBuilder $this$buildString = stringBuilder;
        boolean bl5 = false;
        switch (ReflectionObjectRenderer$WhenMappings.$EnumSwitchMapping$0[parameter.getKind().ordinal()]) {
            case 1: {
                $this$buildString.append("extension receiver parameter");
                break;
            }
            case 2: {
                $this$buildString.append("instance parameter");
                break;
            }
            case 3: {
                $this$buildString.append("parameter #" + parameter.getIndex() + ' ' + parameter.getName());
            }
        }
        $this$buildString.append(" of ");
        $this$buildString.append(INSTANCE.renderCallable(parameter.getCallable().getDescriptor()));
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    @NotNull
    public final String renderType(@NotNull KotlinType type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
        return renderer.renderType(type2);
    }

    private ReflectionObjectRenderer() {
    }

    static {
        ReflectionObjectRenderer reflectionObjectRenderer;
        INSTANCE = reflectionObjectRenderer = new ReflectionObjectRenderer();
        renderer = DescriptorRenderer.FQ_NAMES_IN_TYPES;
    }
}

