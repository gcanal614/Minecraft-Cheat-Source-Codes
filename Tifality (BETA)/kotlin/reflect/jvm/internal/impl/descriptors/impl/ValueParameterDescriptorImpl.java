/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.VariableDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ValueParameterDescriptorImpl
extends VariableDescriptorImpl
implements ValueParameterDescriptor {
    private final ValueParameterDescriptor original;
    private final int index;
    private final boolean declaresDefaultValue;
    private final boolean isCrossinline;
    private final boolean isNoinline;
    @Nullable
    private final KotlinType varargElementType;
    public static final Companion Companion = new Companion(null);

    @Override
    @NotNull
    public CallableDescriptor getContainingDeclaration() {
        DeclarationDescriptor declarationDescriptor = super.getContainingDeclaration();
        if (declarationDescriptor == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableDescriptor");
        }
        return (CallableDescriptor)declarationDescriptor;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean declaresDefaultValue() {
        if (!this.declaresDefaultValue) return false;
        CallableDescriptor callableDescriptor = this.getContainingDeclaration();
        if (callableDescriptor == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableMemberDescriptor");
        }
        CallableMemberDescriptor.Kind kind = ((CallableMemberDescriptor)callableDescriptor).getKind();
        Intrinsics.checkNotNullExpressionValue((Object)kind, "(containingDeclaration a\u2026bleMemberDescriptor).kind");
        if (!kind.isReal()) return false;
        return true;
    }

    @Override
    @NotNull
    public ValueParameterDescriptor getOriginal() {
        return this.original == this ? (ValueParameterDescriptor)this : this.original.getOriginal();
    }

    @Override
    @NotNull
    public ValueParameterDescriptor substitute(@NotNull TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        if (substitutor.isEmpty()) {
            return this;
        }
        throw (Throwable)new UnsupportedOperationException();
    }

    @Override
    public <R, D> R accept(@NotNull DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
        Intrinsics.checkNotNullParameter(visitor2, "visitor");
        return visitor2.visitValueParameterDescriptor(this, data2);
    }

    @Override
    public boolean isVar() {
        return false;
    }

    @Nullable
    public Void getCompileTimeInitializer() {
        return null;
    }

    @Override
    @NotNull
    public ValueParameterDescriptor copy(@NotNull CallableDescriptor newOwner, @NotNull Name newName, int newIndex) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter(newName, "newName");
        Annotations annotations2 = this.getAnnotations();
        Intrinsics.checkNotNullExpressionValue(annotations2, "annotations");
        KotlinType kotlinType = this.getType();
        Intrinsics.checkNotNullExpressionValue(kotlinType, "type");
        boolean bl = this.declaresDefaultValue();
        boolean bl2 = this.isCrossinline();
        boolean bl3 = this.isNoinline();
        KotlinType kotlinType2 = this.getVarargElementType();
        SourceElement sourceElement = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(sourceElement, "SourceElement.NO_SOURCE");
        return new ValueParameterDescriptorImpl(newOwner, null, newIndex, annotations2, newName, kotlinType, bl, bl2, bl3, kotlinType2, sourceElement);
    }

    @Override
    @NotNull
    public Visibility getVisibility() {
        Visibility visibility = Visibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(visibility, "Visibilities.LOCAL");
        return visibility;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Collection<ValueParameterDescriptor> getOverriddenDescriptors() {
        void $this$mapTo$iv$iv;
        Collection<? extends CallableDescriptor> collection = this.getContainingDeclaration().getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(collection, "containingDeclaration.overriddenDescriptors");
        Iterable $this$map$iv = collection;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            CallableDescriptor callableDescriptor = (CallableDescriptor)item$iv$iv;
            Collection collection2 = destination$iv$iv;
            boolean bl = false;
            void v1 = it;
            Intrinsics.checkNotNullExpressionValue(v1, "it");
            ValueParameterDescriptor valueParameterDescriptor = v1.getValueParameters().get(this.getIndex());
            collection2.add(valueParameterDescriptor);
        }
        return (List)destination$iv$iv;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public boolean isCrossinline() {
        return this.isCrossinline;
    }

    @Override
    public boolean isNoinline() {
        return this.isNoinline;
    }

    @Override
    @Nullable
    public KotlinType getVarargElementType() {
        return this.varargElementType;
    }

    public ValueParameterDescriptorImpl(@NotNull CallableDescriptor containingDeclaration, @Nullable ValueParameterDescriptor original, int index, @NotNull Annotations annotations2, @NotNull Name name, @NotNull KotlinType outType, boolean declaresDefaultValue, boolean isCrossinline, boolean isNoinline, @Nullable KotlinType varargElementType, @NotNull SourceElement source) {
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(outType, "outType");
        Intrinsics.checkNotNullParameter(source, "source");
        super(containingDeclaration, annotations2, name, outType, source);
        this.index = index;
        this.declaresDefaultValue = declaresDefaultValue;
        this.isCrossinline = isCrossinline;
        this.isNoinline = isNoinline;
        this.varargElementType = varargElementType;
        ValueParameterDescriptor valueParameterDescriptor = original;
        if (valueParameterDescriptor == null) {
            valueParameterDescriptor = this;
        }
        this.original = valueParameterDescriptor;
    }

    @Override
    public boolean isLateInit() {
        return ValueParameterDescriptor.DefaultImpls.isLateInit(this);
    }

    @JvmStatic
    @NotNull
    public static final ValueParameterDescriptorImpl createWithDestructuringDeclarations(@NotNull CallableDescriptor containingDeclaration, @Nullable ValueParameterDescriptor original, int index, @NotNull Annotations annotations2, @NotNull Name name, @NotNull KotlinType outType, boolean declaresDefaultValue, boolean isCrossinline, boolean isNoinline, @Nullable KotlinType varargElementType, @NotNull SourceElement source, @Nullable Function0<? extends List<? extends VariableDescriptor>> destructuringVariables) {
        return Companion.createWithDestructuringDeclarations(containingDeclaration, original, index, annotations2, name, outType, declaresDefaultValue, isCrossinline, isNoinline, varargElementType, source, destructuringVariables);
    }

    public static final class WithDestructuringDeclaration
    extends ValueParameterDescriptorImpl {
        @NotNull
        private final Lazy destructuringVariables$delegate;

        @NotNull
        public final List<VariableDescriptor> getDestructuringVariables() {
            Lazy lazy = this.destructuringVariables$delegate;
            WithDestructuringDeclaration withDestructuringDeclaration = this;
            Object var3_3 = null;
            boolean bl = false;
            return (List)lazy.getValue();
        }

        @Override
        @NotNull
        public ValueParameterDescriptor copy(@NotNull CallableDescriptor newOwner, @NotNull Name newName, int newIndex) {
            Intrinsics.checkNotNullParameter(newOwner, "newOwner");
            Intrinsics.checkNotNullParameter(newName, "newName");
            Annotations annotations2 = this.getAnnotations();
            Intrinsics.checkNotNullExpressionValue(annotations2, "annotations");
            KotlinType kotlinType = this.getType();
            Intrinsics.checkNotNullExpressionValue(kotlinType, "type");
            boolean bl = this.declaresDefaultValue();
            boolean bl2 = this.isCrossinline();
            boolean bl3 = this.isNoinline();
            KotlinType kotlinType2 = this.getVarargElementType();
            SourceElement sourceElement = SourceElement.NO_SOURCE;
            Intrinsics.checkNotNullExpressionValue(sourceElement, "SourceElement.NO_SOURCE");
            return new WithDestructuringDeclaration(newOwner, null, newIndex, annotations2, newName, kotlinType, bl, bl2, bl3, kotlinType2, sourceElement, (Function0<? extends List<? extends VariableDescriptor>>)new Function0<List<? extends VariableDescriptor>>(this){
                final /* synthetic */ WithDestructuringDeclaration this$0;

                @NotNull
                public final List<VariableDescriptor> invoke() {
                    return this.this$0.getDestructuringVariables();
                }
                {
                    this.this$0 = withDestructuringDeclaration;
                    super(0);
                }
            });
        }

        public WithDestructuringDeclaration(@NotNull CallableDescriptor containingDeclaration, @Nullable ValueParameterDescriptor original, int index, @NotNull Annotations annotations2, @NotNull Name name, @NotNull KotlinType outType, boolean declaresDefaultValue, boolean isCrossinline, boolean isNoinline, @Nullable KotlinType varargElementType, @NotNull SourceElement source, @NotNull Function0<? extends List<? extends VariableDescriptor>> destructuringVariables) {
            Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
            Intrinsics.checkNotNullParameter(annotations2, "annotations");
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(outType, "outType");
            Intrinsics.checkNotNullParameter(source, "source");
            Intrinsics.checkNotNullParameter(destructuringVariables, "destructuringVariables");
            super(containingDeclaration, original, index, annotations2, name, outType, declaresDefaultValue, isCrossinline, isNoinline, varargElementType, source);
            this.destructuringVariables$delegate = LazyKt.lazy(destructuringVariables);
        }
    }

    public static final class Companion {
        @JvmStatic
        @NotNull
        public final ValueParameterDescriptorImpl createWithDestructuringDeclarations(@NotNull CallableDescriptor containingDeclaration, @Nullable ValueParameterDescriptor original, int index, @NotNull Annotations annotations2, @NotNull Name name, @NotNull KotlinType outType, boolean declaresDefaultValue, boolean isCrossinline, boolean isNoinline, @Nullable KotlinType varargElementType, @NotNull SourceElement source, @Nullable Function0<? extends List<? extends VariableDescriptor>> destructuringVariables) {
            Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
            Intrinsics.checkNotNullParameter(annotations2, "annotations");
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(outType, "outType");
            Intrinsics.checkNotNullParameter(source, "source");
            return destructuringVariables == null ? new ValueParameterDescriptorImpl(containingDeclaration, original, index, annotations2, name, outType, declaresDefaultValue, isCrossinline, isNoinline, varargElementType, source) : (ValueParameterDescriptorImpl)new WithDestructuringDeclaration(containingDeclaration, original, index, annotations2, name, outType, declaresDefaultValue, isCrossinline, isNoinline, varargElementType, source, destructuringVariables);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

