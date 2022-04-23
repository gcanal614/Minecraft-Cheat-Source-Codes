/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithSource;
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PossiblyInnerType;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceFile;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterUtilsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationUseSiteTarget;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.renderer.AnnotationArgumentsRenderingPolicy;
import kotlin.reflect.jvm.internal.impl.renderer.ClassifierNamePolicy;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$RenderDeclarationDescriptorVisitor$WhenMappings;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$WhenMappings;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererModifier;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptionsImpl;
import kotlin.reflect.jvm.internal.impl.renderer.OverrideRenderingPolicy;
import kotlin.reflect.jvm.internal.impl.renderer.ParameterNameRenderingPolicy;
import kotlin.reflect.jvm.internal.impl.renderer.PropertyAccessorRenderingPolicy;
import kotlin.reflect.jvm.internal.impl.renderer.RenderingFormat;
import kotlin.reflect.jvm.internal.impl.renderer.RenderingUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.AbbreviatedType;
import kotlin.reflect.jvm.internal.impl.types.ErrorType;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.UnresolvedType;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.WrappedType;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DescriptorRendererImpl
extends DescriptorRenderer
implements DescriptorRendererOptions {
    private final Lazy functionTypeAnnotationsRenderer$delegate;
    @NotNull
    private final DescriptorRendererOptionsImpl options;

    private final DescriptorRendererImpl getFunctionTypeAnnotationsRenderer() {
        Lazy lazy = this.functionTypeAnnotationsRenderer$delegate;
        DescriptorRendererImpl descriptorRendererImpl = this;
        Object var3_3 = null;
        boolean bl = false;
        return (DescriptorRendererImpl)lazy.getValue();
    }

    private final String renderKeyword(String keyword) {
        String string;
        switch (DescriptorRendererImpl$WhenMappings.$EnumSwitchMapping$0[this.getTextFormat().ordinal()]) {
            case 1: {
                string = keyword;
                break;
            }
            case 2: {
                if (this.getBoldOnlyForNamesInHtml()) {
                    string = keyword;
                    break;
                }
                string = "<b>" + keyword + "</b>";
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return string;
    }

    private final String renderError(String keyword) {
        String string;
        switch (DescriptorRendererImpl$WhenMappings.$EnumSwitchMapping$1[this.getTextFormat().ordinal()]) {
            case 1: {
                string = keyword;
                break;
            }
            case 2: {
                string = "<font color=red><b>" + keyword + "</b></font>";
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return string;
    }

    private final String escape(String string) {
        return this.getTextFormat().escape(string);
    }

    private final String lt() {
        return this.escape("<");
    }

    private final String gt() {
        return this.escape(">");
    }

    private final String arrow() {
        String string;
        switch (DescriptorRendererImpl$WhenMappings.$EnumSwitchMapping$2[this.getTextFormat().ordinal()]) {
            case 1: {
                string = this.escape("->");
                break;
            }
            case 2: {
                string = "&rarr;";
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return string;
    }

    @NotNull
    public String renderMessage(@NotNull String message) {
        String string;
        Intrinsics.checkNotNullParameter(message, "message");
        switch (DescriptorRendererImpl$WhenMappings.$EnumSwitchMapping$3[this.getTextFormat().ordinal()]) {
            case 1: {
                string = message;
                break;
            }
            case 2: {
                string = "<i>" + message + "</i>";
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return string;
    }

    @Override
    @NotNull
    public String renderName(@NotNull Name name, boolean rootRenderedElement) {
        Intrinsics.checkNotNullParameter(name, "name");
        String escaped = this.escape(RenderingUtilsKt.render(name));
        return this.getBoldOnlyForNamesInHtml() && this.getTextFormat() == RenderingFormat.HTML && rootRenderedElement ? "<b>" + escaped + "</b>" : escaped;
    }

    private final void renderName(DeclarationDescriptor descriptor2, StringBuilder builder, boolean rootRenderedElement) {
        Name name = descriptor2.getName();
        Intrinsics.checkNotNullExpressionValue(name, "descriptor.name");
        builder.append(this.renderName(name, rootRenderedElement));
    }

    private final void renderCompanionObjectName(DeclarationDescriptor descriptor2, StringBuilder builder) {
        if (this.getRenderCompanionObjectName()) {
            if (this.getStartFromName()) {
                builder.append("companion object");
            }
            this.renderSpaceIfNeeded(builder);
            DeclarationDescriptor containingDeclaration = descriptor2.getContainingDeclaration();
            if (containingDeclaration != null) {
                builder.append("of ");
                Name name = containingDeclaration.getName();
                Intrinsics.checkNotNullExpressionValue(name, "containingDeclaration.name");
                builder.append(this.renderName(name, false));
            }
        }
        if (this.getVerbose() || Intrinsics.areEqual(descriptor2.getName(), SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT) ^ true) {
            if (!this.getStartFromName()) {
                this.renderSpaceIfNeeded(builder);
            }
            Name name = descriptor2.getName();
            Intrinsics.checkNotNullExpressionValue(name, "descriptor.name");
            builder.append(this.renderName(name, true));
        }
    }

    @Override
    @NotNull
    public String renderFqName(@NotNull FqNameUnsafe fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        List<Name> list = fqName2.pathSegments();
        Intrinsics.checkNotNullExpressionValue(list, "fqName.pathSegments()");
        return this.renderFqName(list);
    }

    private final String renderFqName(List<Name> pathSegments) {
        return this.escape(RenderingUtilsKt.renderFqName(pathSegments));
    }

    @NotNull
    public String renderClassifierName(@NotNull ClassifierDescriptor klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return ErrorUtils.isError(klass) ? klass.getTypeConstructor().toString() : this.getClassifierNamePolicy().renderClassifier(klass, this);
    }

    @Override
    @NotNull
    public String renderType(@NotNull KotlinType type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl3 = false;
        boolean bl4 = false;
        StringBuilder $this$buildString = stringBuilder;
        boolean bl5 = false;
        this.renderNormalizedType($this$buildString, this.getTypeNormalizer().invoke(type2));
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    private final void renderNormalizedType(StringBuilder $this$renderNormalizedType, KotlinType type2) {
        AbbreviatedType abbreviated;
        UnwrappedType unwrappedType = type2.unwrap();
        if (!(unwrappedType instanceof AbbreviatedType)) {
            unwrappedType = null;
        }
        if ((abbreviated = (AbbreviatedType)unwrappedType) != null) {
            if (this.getRenderTypeExpansions()) {
                this.renderNormalizedTypeAsIs($this$renderNormalizedType, abbreviated.getExpandedType());
            } else {
                this.renderNormalizedTypeAsIs($this$renderNormalizedType, abbreviated.getAbbreviation());
                if (this.getRenderUnabbreviatedType()) {
                    this.renderAbbreviatedTypeExpansion($this$renderNormalizedType, abbreviated);
                }
            }
            return;
        }
        this.renderNormalizedTypeAsIs($this$renderNormalizedType, type2);
    }

    private final void renderAbbreviatedTypeExpansion(StringBuilder $this$renderAbbreviatedTypeExpansion, AbbreviatedType abbreviated) {
        if (this.getTextFormat() == RenderingFormat.HTML) {
            $this$renderAbbreviatedTypeExpansion.append("<font color=\"808080\"><i>");
        }
        $this$renderAbbreviatedTypeExpansion.append(" /* = ");
        this.renderNormalizedTypeAsIs($this$renderAbbreviatedTypeExpansion, abbreviated.getExpandedType());
        $this$renderAbbreviatedTypeExpansion.append(" */");
        if (this.getTextFormat() == RenderingFormat.HTML) {
            $this$renderAbbreviatedTypeExpansion.append("</i></font>");
        }
    }

    private final void renderNormalizedTypeAsIs(StringBuilder $this$renderNormalizedTypeAsIs, KotlinType type2) {
        block2: {
            UnwrappedType unwrappedType;
            block1: {
                if (type2 instanceof WrappedType && this.getDebugMode() && !((WrappedType)type2).isComputed()) {
                    $this$renderNormalizedTypeAsIs.append("<Not computed yet>");
                    return;
                }
                unwrappedType = type2.unwrap();
                if (!(unwrappedType instanceof FlexibleType)) break block1;
                $this$renderNormalizedTypeAsIs.append(((FlexibleType)unwrappedType).render(this, this));
                break block2;
            }
            if (!(unwrappedType instanceof SimpleType)) break block2;
            this.renderSimpleType($this$renderNormalizedTypeAsIs, (SimpleType)unwrappedType);
        }
    }

    private final void renderSimpleType(StringBuilder $this$renderSimpleType, SimpleType type2) {
        if (Intrinsics.areEqual(type2, TypeUtils.CANT_INFER_FUNCTION_PARAM_TYPE) || TypeUtils.isDontCarePlaceholder(type2)) {
            $this$renderSimpleType.append("???");
            return;
        }
        if (ErrorUtils.isUninferredParameter(type2)) {
            if (this.getUninferredTypeParameterAsName()) {
                TypeConstructor typeConstructor2 = type2.getConstructor();
                if (typeConstructor2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.ErrorUtils.UninferredParameterTypeConstructor");
                }
                TypeParameterDescriptor typeParameterDescriptor = ((ErrorUtils.UninferredParameterTypeConstructor)typeConstructor2).getTypeParameterDescriptor();
                Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "(type.constructor as Uni\u2026).typeParameterDescriptor");
                String string = typeParameterDescriptor.getName().toString();
                Intrinsics.checkNotNullExpressionValue(string, "(type.constructor as Uni\u2026escriptor.name.toString()");
                $this$renderSimpleType.append(this.renderError(string));
            } else {
                $this$renderSimpleType.append("???");
            }
            return;
        }
        if (KotlinTypeKt.isError(type2)) {
            this.renderDefaultType($this$renderSimpleType, type2);
            return;
        }
        if (this.shouldRenderAsPrettyFunctionType(type2)) {
            this.renderFunctionType($this$renderSimpleType, type2);
        } else {
            this.renderDefaultType($this$renderSimpleType, type2);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean shouldRenderAsPrettyFunctionType(KotlinType type2) {
        TypeProjection it;
        if (!FunctionTypesKt.isBuiltinFunctionalType(type2)) return false;
        Iterable $this$none$iv = type2.getArguments();
        boolean $i$f$none = false;
        if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
            return true;
        }
        Iterator iterator2 = $this$none$iv.iterator();
        do {
            if (!iterator2.hasNext()) return true;
            Object element$iv = iterator2.next();
            it = (TypeProjection)element$iv;
            boolean bl = false;
        } while (!it.isStarProjection());
        return false;
    }

    @Override
    @NotNull
    public String renderFlexibleType(@NotNull String lowerRendered, @NotNull String upperRendered, @NotNull KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(lowerRendered, "lowerRendered");
        Intrinsics.checkNotNullParameter(upperRendered, "upperRendered");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        if (this.differsOnlyInNullability(lowerRendered, upperRendered)) {
            if (StringsKt.startsWith$default(upperRendered, "(", false, 2, null)) {
                return '(' + lowerRendered + ")!";
            }
            return lowerRendered + '!';
        }
        ClassifierNamePolicy classifierNamePolicy = this.getClassifierNamePolicy();
        ClassDescriptor classDescriptor = builtIns.getCollection();
        Intrinsics.checkNotNullExpressionValue(classDescriptor, "builtIns.collection");
        String kotlinCollectionsPrefix = StringsKt.substringBefore$default(classifierNamePolicy.renderClassifier(classDescriptor, this), "Collection", null, 2, null);
        String mutablePrefix = "Mutable";
        String simpleCollection = this.replacePrefixes(lowerRendered, kotlinCollectionsPrefix + mutablePrefix, upperRendered, kotlinCollectionsPrefix, kotlinCollectionsPrefix + '(' + mutablePrefix + ')');
        if (simpleCollection != null) {
            return simpleCollection;
        }
        String mutableEntry = this.replacePrefixes(lowerRendered, kotlinCollectionsPrefix + "MutableMap.MutableEntry", upperRendered, kotlinCollectionsPrefix + "Map.Entry", kotlinCollectionsPrefix + "(Mutable)Map.(Mutable)Entry");
        if (mutableEntry != null) {
            return mutableEntry;
        }
        ClassifierNamePolicy classifierNamePolicy2 = this.getClassifierNamePolicy();
        ClassDescriptor classDescriptor2 = builtIns.getArray();
        Intrinsics.checkNotNullExpressionValue(classDescriptor2, "builtIns.array");
        String kotlinPrefix = StringsKt.substringBefore$default(classifierNamePolicy2.renderClassifier(classDescriptor2, this), "Array", null, 2, null);
        String array = this.replacePrefixes(lowerRendered, kotlinPrefix + this.escape("Array<"), upperRendered, kotlinPrefix + this.escape("Array<out "), kotlinPrefix + this.escape("Array<(out) "));
        if (array != null) {
            return array;
        }
        return '(' + lowerRendered + ".." + upperRendered + ')';
    }

    @NotNull
    public String renderTypeArguments(@NotNull List<? extends TypeProjection> typeArguments) {
        String string;
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        if (typeArguments.isEmpty()) {
            string = "";
        } else {
            boolean bl = false;
            boolean bl2 = false;
            StringBuilder stringBuilder = new StringBuilder();
            boolean bl3 = false;
            boolean bl4 = false;
            StringBuilder $this$buildString = stringBuilder;
            boolean bl5 = false;
            $this$buildString.append(this.lt());
            this.appendTypeProjections($this$buildString, typeArguments);
            $this$buildString.append(this.gt());
            String string2 = stringBuilder.toString();
            string = string2;
            Intrinsics.checkNotNullExpressionValue(string2, "StringBuilder().apply(builderAction).toString()");
        }
        return string;
    }

    private final void renderDefaultType(StringBuilder $this$renderDefaultType, KotlinType type2) {
        DescriptorRendererImpl.renderAnnotations$default(this, $this$renderDefaultType, type2, null, 2, null);
        if (KotlinTypeKt.isError(type2)) {
            if (type2 instanceof UnresolvedType && this.getPresentableUnresolvedTypes()) {
                $this$renderDefaultType.append(((UnresolvedType)type2).getPresentableName());
            } else if (type2 instanceof ErrorType && !this.getInformativeErrorType()) {
                $this$renderDefaultType.append(((ErrorType)type2).getPresentableName());
            } else {
                $this$renderDefaultType.append(type2.getConstructor().toString());
            }
            $this$renderDefaultType.append(this.renderTypeArguments(type2.getArguments()));
        } else {
            DescriptorRendererImpl.renderTypeConstructorAndArguments$default(this, $this$renderDefaultType, type2, null, 2, null);
        }
        if (type2.isMarkedNullable()) {
            $this$renderDefaultType.append("?");
        }
        if (SpecialTypesKt.isDefinitelyNotNullType(type2)) {
            $this$renderDefaultType.append("!!");
        }
    }

    private final void renderTypeConstructorAndArguments(StringBuilder $this$renderTypeConstructorAndArguments, KotlinType type2, TypeConstructor typeConstructor2) {
        PossiblyInnerType possiblyInnerType = TypeParameterUtilsKt.buildPossiblyInnerType(type2);
        if (possiblyInnerType == null) {
            $this$renderTypeConstructorAndArguments.append(this.renderTypeConstructor(typeConstructor2));
            $this$renderTypeConstructorAndArguments.append(this.renderTypeArguments(type2.getArguments()));
            return;
        }
        this.renderPossiblyInnerType($this$renderTypeConstructorAndArguments, possiblyInnerType);
    }

    static /* synthetic */ void renderTypeConstructorAndArguments$default(DescriptorRendererImpl descriptorRendererImpl, StringBuilder stringBuilder, KotlinType kotlinType, TypeConstructor typeConstructor2, int n, Object object) {
        if ((n & 2) != 0) {
            typeConstructor2 = kotlinType.getConstructor();
        }
        descriptorRendererImpl.renderTypeConstructorAndArguments(stringBuilder, kotlinType, typeConstructor2);
    }

    private final void renderPossiblyInnerType(StringBuilder $this$renderPossiblyInnerType, PossiblyInnerType possiblyInnerType) {
        block3: {
            Object object;
            block2: {
                object = possiblyInnerType.getOuterType();
                if (object == null) break block2;
                PossiblyInnerType possiblyInnerType2 = object;
                boolean bl = false;
                boolean bl2 = false;
                PossiblyInnerType it = possiblyInnerType2;
                boolean bl3 = false;
                this.renderPossiblyInnerType($this$renderPossiblyInnerType, it);
                $this$renderPossiblyInnerType.append('.');
                Name name = possiblyInnerType.getClassifierDescriptor().getName();
                Intrinsics.checkNotNullExpressionValue(name, "possiblyInnerType.classifierDescriptor.name");
                object = $this$renderPossiblyInnerType.append(this.renderName(name, false));
                if (object != null) break block3;
            }
            TypeConstructor typeConstructor2 = possiblyInnerType.getClassifierDescriptor().getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor2, "possiblyInnerType.classi\u2026escriptor.typeConstructor");
            object = $this$renderPossiblyInnerType.append(this.renderTypeConstructor(typeConstructor2));
        }
        $this$renderPossiblyInnerType.append(this.renderTypeArguments(possiblyInnerType.getArguments()));
    }

    @NotNull
    public String renderTypeConstructor(@NotNull TypeConstructor typeConstructor2) {
        String string;
        Intrinsics.checkNotNullParameter(typeConstructor2, "typeConstructor");
        ClassifierDescriptor cd = typeConstructor2.getDeclarationDescriptor();
        if (cd instanceof TypeParameterDescriptor || cd instanceof ClassDescriptor || cd instanceof TypeAliasDescriptor) {
            string = this.renderClassifierName(cd);
        } else if (cd == null) {
            string = typeConstructor2.toString();
        } else {
            String string2 = "Unexpected classifier: " + cd.getClass();
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string2.toString());
        }
        return string;
    }

    @Override
    @NotNull
    public String renderTypeProjection(@NotNull TypeProjection typeProjection) {
        Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl3 = false;
        boolean bl4 = false;
        StringBuilder $this$buildString = stringBuilder;
        boolean bl5 = false;
        this.appendTypeProjections($this$buildString, CollectionsKt.listOf(typeProjection));
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    private final void appendTypeProjections(StringBuilder $this$appendTypeProjections, List<? extends TypeProjection> typeProjections) {
        CollectionsKt.joinTo$default(typeProjections, $this$appendTypeProjections, ", ", null, null, 0, null, new Function1<TypeProjection, CharSequence>(this){
            final /* synthetic */ DescriptorRendererImpl this$0;

            @NotNull
            public final CharSequence invoke(@NotNull TypeProjection it) {
                String string;
                Intrinsics.checkNotNullParameter(it, "it");
                if (it.isStarProjection()) {
                    string = "*";
                } else {
                    KotlinType kotlinType = it.getType();
                    Intrinsics.checkNotNullExpressionValue(kotlinType, "it.type");
                    String type2 = this.this$0.renderType(kotlinType);
                    string = it.getProjectionKind() == Variance.INVARIANT ? type2 : (Object)((Object)it.getProjectionKind()) + ' ' + type2;
                }
                return string;
            }
            {
                this.this$0 = descriptorRendererImpl;
                super(1);
            }
        }, 60, null);
    }

    /*
     * WARNING - void declaration
     */
    private final void renderFunctionType(StringBuilder $this$renderFunctionType, KotlinType type2) {
        boolean bl;
        boolean needParenthesis;
        int lengthBefore = $this$renderFunctionType.length();
        DescriptorRendererImpl descriptorRendererImpl = this.getFunctionTypeAnnotationsRenderer();
        boolean bl2 = false;
        boolean bl3 = false;
        DescriptorRendererImpl $this$with = descriptorRendererImpl;
        boolean bl4 = false;
        DescriptorRendererImpl.renderAnnotations$default($this$with, $this$renderFunctionType, type2, null, 2, null);
        boolean hasAnnotations = $this$renderFunctionType.length() != lengthBefore;
        boolean isSuspend = FunctionTypesKt.isSuspendFunctionType(type2);
        boolean isNullable = type2.isMarkedNullable();
        KotlinType receiverType = FunctionTypesKt.getReceiverTypeFromFunctionType(type2);
        boolean bl5 = needParenthesis = isNullable || hasAnnotations && receiverType != null;
        if (needParenthesis) {
            if (isSuspend) {
                $this$renderFunctionType.insert(lengthBefore, '(');
            } else {
                if (hasAnnotations) {
                    boolean bl6 = StringsKt.last($this$renderFunctionType) == ' ';
                    bl = false;
                    boolean bl7 = false;
                    if (_Assertions.ENABLED && !bl6) {
                        boolean bl8 = false;
                        String string = "Assertion failed";
                        throw (Throwable)((Object)new AssertionError((Object)string));
                    }
                    if ($this$renderFunctionType.charAt(StringsKt.getLastIndex($this$renderFunctionType) - 1) != ')') {
                        $this$renderFunctionType.insert(StringsKt.getLastIndex($this$renderFunctionType), "()");
                    }
                }
                $this$renderFunctionType.append("(");
            }
        }
        this.renderModifier($this$renderFunctionType, isSuspend, "suspend");
        if (receiverType != null) {
            boolean surroundReceiver;
            boolean bl9 = surroundReceiver = this.shouldRenderAsPrettyFunctionType(receiverType) && !receiverType.isMarkedNullable() || this.hasModifiersOrAnnotations(receiverType);
            if (surroundReceiver) {
                $this$renderFunctionType.append("(");
            }
            this.renderNormalizedType($this$renderFunctionType, receiverType);
            if (surroundReceiver) {
                $this$renderFunctionType.append(")");
            }
            $this$renderFunctionType.append(".");
        }
        $this$renderFunctionType.append("(");
        List<TypeProjection> parameterTypes = FunctionTypesKt.getValueParameterTypesFromFunctionType(type2);
        bl = false;
        for (TypeProjection typeProjection : (Iterable)parameterTypes) {
            Name name;
            Name name2;
            void index;
            if (index > 0) {
                $this$renderFunctionType.append(", ");
            }
            if (this.getParameterNamesInFunctionalTypes()) {
                KotlinType kotlinType = typeProjection.getType();
                Intrinsics.checkNotNullExpressionValue(kotlinType, "typeProjection.type");
                name2 = FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(kotlinType);
            } else {
                name2 = name = null;
            }
            if (name != null) {
                $this$renderFunctionType.append(this.renderName(name, false));
                $this$renderFunctionType.append(": ");
            }
            $this$renderFunctionType.append(this.renderTypeProjection(typeProjection));
            ++index;
        }
        $this$renderFunctionType.append(") ").append(this.arrow()).append(" ");
        this.renderNormalizedType($this$renderFunctionType, FunctionTypesKt.getReturnTypeFromFunctionType(type2));
        if (needParenthesis) {
            $this$renderFunctionType.append(")");
        }
        if (isNullable) {
            $this$renderFunctionType.append("?");
        }
    }

    private final boolean hasModifiersOrAnnotations(KotlinType $this$hasModifiersOrAnnotations) {
        return FunctionTypesKt.isSuspendFunctionType($this$hasModifiersOrAnnotations) || !$this$hasModifiersOrAnnotations.getAnnotations().isEmpty();
    }

    private final void appendDefinedIn(StringBuilder $this$appendDefinedIn, DeclarationDescriptor descriptor2) {
        block4: {
            if (descriptor2 instanceof PackageFragmentDescriptor || descriptor2 instanceof PackageViewDescriptor) {
                return;
            }
            if (descriptor2 instanceof ModuleDescriptor) {
                $this$appendDefinedIn.append(" is a module");
                return;
            }
            DeclarationDescriptor containingDeclaration = descriptor2.getContainingDeclaration();
            if (containingDeclaration == null || containingDeclaration instanceof ModuleDescriptor) break block4;
            $this$appendDefinedIn.append(" ").append(this.renderMessage("defined in")).append(" ");
            FqNameUnsafe fqNameUnsafe = DescriptorUtils.getFqName(containingDeclaration);
            Intrinsics.checkNotNullExpressionValue(fqNameUnsafe, "DescriptorUtils.getFqName(containingDeclaration)");
            FqNameUnsafe fqName2 = fqNameUnsafe;
            $this$appendDefinedIn.append(fqName2.isRoot() ? "root package" : this.renderFqName(fqName2));
            if (this.getWithSourceFileForTopLevel() && containingDeclaration instanceof PackageFragmentDescriptor && descriptor2 instanceof DeclarationDescriptorWithSource) {
                SourceElement sourceElement = ((DeclarationDescriptorWithSource)descriptor2).getSource();
                Intrinsics.checkNotNullExpressionValue(sourceElement, "descriptor.source");
                SourceFile sourceFile = sourceElement.getContainingFile();
                Intrinsics.checkNotNullExpressionValue(sourceFile, "descriptor.source.containingFile");
                String string = sourceFile.getName();
                if (string != null) {
                    String string2 = string;
                    boolean bl = false;
                    boolean bl2 = false;
                    String sourceFileName = string2;
                    boolean bl3 = false;
                    $this$appendDefinedIn.append(" ").append(this.renderMessage("in file")).append(" ").append(sourceFileName);
                }
            }
        }
    }

    private final void renderAnnotations(StringBuilder $this$renderAnnotations, Annotated annotated, AnnotationUseSiteTarget target) {
        if (!this.getModifiers().contains((Object)DescriptorRendererModifier.ANNOTATIONS)) {
            return;
        }
        Set<FqName> excluded = annotated instanceof KotlinType ? this.getExcludedTypeAnnotationClasses() : this.getExcludedAnnotationClasses();
        Function1<AnnotationDescriptor, Boolean> annotationFilter = this.getAnnotationFilter();
        for (AnnotationDescriptor annotation : annotated.getAnnotations()) {
            if (CollectionsKt.contains((Iterable)excluded, annotation.getFqName()) || this.isParameterName(annotation) || annotationFilter != null && !annotationFilter.invoke(annotation).booleanValue()) continue;
            $this$renderAnnotations.append(this.renderAnnotation(annotation, target));
            if (this.getEachAnnotationOnNewLine()) {
                StringsKt.appendln($this$renderAnnotations);
                continue;
            }
            $this$renderAnnotations.append(" ");
        }
    }

    static /* synthetic */ void renderAnnotations$default(DescriptorRendererImpl descriptorRendererImpl, StringBuilder stringBuilder, Annotated annotated, AnnotationUseSiteTarget annotationUseSiteTarget, int n, Object object) {
        if ((n & 2) != 0) {
            annotationUseSiteTarget = null;
        }
        descriptorRendererImpl.renderAnnotations(stringBuilder, annotated, annotationUseSiteTarget);
    }

    private final boolean isParameterName(AnnotationDescriptor $this$isParameterName) {
        return Intrinsics.areEqual($this$isParameterName.getFqName(), KotlinBuiltIns.FQ_NAMES.parameterName);
    }

    @Override
    @NotNull
    public String renderAnnotation(@NotNull AnnotationDescriptor annotation, @Nullable AnnotationUseSiteTarget target) {
        KotlinType annotationType;
        StringBuilder $this$buildString;
        StringBuilder stringBuilder;
        block5: {
            List<String> arguments2;
            block6: {
                Intrinsics.checkNotNullParameter(annotation, "annotation");
                boolean bl = false;
                boolean bl2 = false;
                stringBuilder = new StringBuilder();
                boolean bl3 = false;
                boolean bl4 = false;
                $this$buildString = stringBuilder;
                boolean bl5 = false;
                $this$buildString.append('@');
                if (target != null) {
                    $this$buildString.append(target.getRenderName() + ":");
                }
                annotationType = annotation.getType();
                $this$buildString.append(this.renderType(annotationType));
                if (!this.getIncludeAnnotationArguments()) break block5;
                arguments2 = this.renderAndSortAnnotationArguments(annotation);
                if (this.getIncludeEmptyAnnotationArguments()) break block6;
                Collection collection = arguments2;
                boolean bl6 = false;
                if (!(!collection.isEmpty())) break block5;
            }
            CollectionsKt.joinTo$default(arguments2, $this$buildString, ", ", "(", ")", 0, null, null, 112, null);
        }
        if (this.getVerbose() && (KotlinTypeKt.isError(annotationType) || annotationType.getConstructor().getDeclarationDescriptor() instanceof NotFoundClasses.MockClassDescriptor)) {
            $this$buildString.append(" /* annotation class not found */");
        }
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    /*
     * WARNING - void declaration
     */
    private final List<String> renderAndSortAnnotationArguments(AnnotationDescriptor descriptor2) {
        void $this$mapTo$iv$iv;
        void $this$mapTo$iv$iv2;
        Name name;
        Object object;
        boolean bl;
        Iterable $this$filterTo$iv$iv;
        List list;
        Object object2;
        Collection collection;
        boolean $i$f$map;
        Iterable $this$map$iv;
        Iterable destination$iv$iv;
        Map<Name, ConstantValue<?>> allValueArguments2 = descriptor2.getAllValueArguments();
        ClassDescriptor classDescriptor = this.getRenderDefaultAnnotationArguments() ? DescriptorUtilsKt.getAnnotationClass(descriptor2) : null;
        Object object3 = classDescriptor;
        if (object3 != null && (object3 = object3.getUnsubstitutedPrimaryConstructor()) != null && (object3 = object3.getValueParameters()) != null) {
            void $this$mapTo$iv$iv3;
            ValueParameterDescriptor it;
            Iterable $this$filterTo$iv$iv2;
            Iterable $this$filter$iv = (Iterable)object3;
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv2) {
                it = (ValueParameterDescriptor)element$iv$iv;
                boolean bl2 = false;
                if (!it.declaresDefaultValue()) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$map$iv = (List)destination$iv$iv;
            $i$f$map = false;
            $this$filterTo$iv$iv2 = $this$map$iv;
            destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Iterator item$iv$iv : $this$mapTo$iv$iv3) {
                it = (ValueParameterDescriptor)((Object)item$iv$iv);
                collection = destination$iv$iv;
                boolean bl3 = false;
                ValueParameterDescriptor valueParameterDescriptor = it;
                Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "it");
                object2 = valueParameterDescriptor.getName();
                collection.add(object2);
            }
            list = (List)destination$iv$iv;
        } else {
            list = null;
        }
        $this$map$iv = list;
        $i$f$map = false;
        List list2 = $this$map$iv;
        if (list2 == null) {
            list2 = CollectionsKt.emptyList();
        }
        List parameterDescriptorsWithDefaultValue = list2;
        Iterable $this$filter$iv = parameterDescriptorsWithDefaultValue;
        boolean $i$f$filter = false;
        destination$iv$iv = $this$filter$iv;
        Iterable destination$iv$iv2 = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Name it = (Name)element$iv$iv;
            boolean bl4 = false;
            Map<Name, ConstantValue<?>> map2 = allValueArguments2;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            bl = false;
            object = map2;
            boolean bl5 = false;
            if (!(!object.containsKey(name))) continue;
            destination$iv$iv2.add(element$iv$iv);
        }
        Iterable $this$map$iv2 = (List)destination$iv$iv2;
        boolean $i$f$map2 = false;
        $this$filterTo$iv$iv = $this$map$iv2;
        destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
            Name it = (Name)item$iv$iv;
            collection = destination$iv$iv2;
            boolean bl6 = false;
            object2 = it.asString() + " = ...";
            collection.add(object2);
        }
        List defaultList = (List)destination$iv$iv2;
        Iterable $this$map$iv3 = allValueArguments2.entrySet();
        boolean $i$f$map3 = false;
        destination$iv$iv2 = $this$map$iv3;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
        boolean $i$f$mapTo2 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void name2;
            void $dstr$name$value;
            Map.Entry bl6 = (Map.Entry)item$iv$iv;
            collection = destination$iv$iv3;
            boolean bl7 = false;
            name = $dstr$name$value;
            bl = false;
            object = (Name)name.getKey();
            name = $dstr$name$value;
            bl = false;
            ConstantValue value = (ConstantValue)name.getValue();
            object2 = name2.asString() + " = " + (!parameterDescriptorsWithDefaultValue.contains(name2) ? this.renderConstant(value) : "...");
            collection.add(object2);
        }
        List argumentList = (List)destination$iv$iv3;
        return CollectionsKt.sorted(CollectionsKt.plus((Collection)defaultList, (Iterable)argumentList));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final String renderConstant(ConstantValue<?> value) {
        String string;
        ConstantValue<?> constantValue = value;
        if (constantValue instanceof ArrayValue) {
            string = CollectionsKt.joinToString$default((Iterable)((ArrayValue)value).getValue(), ", ", "{", "}", 0, null, new Function1<ConstantValue<?>, CharSequence>(this){
                final /* synthetic */ DescriptorRendererImpl this$0;

                @NotNull
                public final CharSequence invoke(@NotNull ConstantValue<?> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return DescriptorRendererImpl.access$renderConstant(this.this$0, it);
                }
                {
                    this.this$0 = descriptorRendererImpl;
                    super(1);
                }
            }, 24, null);
            return string;
        } else if (constantValue instanceof AnnotationValue) {
            string = StringsKt.removePrefix(DescriptorRenderer.renderAnnotation$default(this, (AnnotationDescriptor)((AnnotationValue)value).getValue(), null, 2, null), (CharSequence)"@");
            return string;
        } else if (constantValue instanceof KClassValue) {
            KClassValue.Value classValue = (KClassValue.Value)((KClassValue)value).getValue();
            if (classValue instanceof KClassValue.Value.LocalClass) {
                string = ((KClassValue.Value.LocalClass)classValue).getType() + "::class";
                return string;
            } else {
                if (!(classValue instanceof KClassValue.Value.NormalClass)) throw new NoWhenBranchMatchedException();
                String string2 = ((KClassValue.Value.NormalClass)classValue).getClassId().asSingleFqName().asString();
                Intrinsics.checkNotNullExpressionValue(string2, "classValue.classId.asSingleFqName().asString()");
                String type2 = string2;
                int n = ((KClassValue.Value.NormalClass)classValue).getArrayDimensions();
                boolean bl = false;
                int n2 = 0;
                n2 = 0;
                int n3 = n;
                while (n2 < n3) {
                    int it = n2++;
                    boolean bl2 = false;
                    type2 = "kotlin.Array<" + type2 + '>';
                }
                string = type2 + "::class";
            }
            return string;
        } else {
            string = value.toString();
        }
        return string;
    }

    private final boolean renderVisibility(Visibility visibility, StringBuilder builder) {
        Visibility visibility2 = visibility;
        if (!this.getModifiers().contains((Object)DescriptorRendererModifier.VISIBILITY)) {
            return false;
        }
        if (this.getNormalizedVisibilities()) {
            visibility2 = visibility2.normalize();
        }
        if (!this.getRenderDefaultVisibility() && Intrinsics.areEqual(visibility2, Visibilities.DEFAULT_VISIBILITY)) {
            return false;
        }
        builder.append(this.renderKeyword(visibility2.getInternalDisplayName())).append(" ");
        return true;
    }

    private final void renderModality(Modality modality, StringBuilder builder, Modality defaultModality) {
        if (!this.getRenderDefaultModality() && modality == defaultModality) {
            return;
        }
        boolean bl = this.getModifiers().contains((Object)DescriptorRendererModifier.MODALITY);
        String string = modality.name();
        boolean bl2 = false;
        String string2 = string;
        if (string2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
        String string3 = string2.toLowerCase();
        Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.String).toLowerCase()");
        this.renderModifier(builder, bl, string3);
    }

    private final Modality implicitModalityWithoutExtensions(MemberDescriptor $this$implicitModalityWithoutExtensions) {
        if ($this$implicitModalityWithoutExtensions instanceof ClassDescriptor) {
            return ((ClassDescriptor)$this$implicitModalityWithoutExtensions).getKind() == ClassKind.INTERFACE ? Modality.ABSTRACT : Modality.FINAL;
        }
        DeclarationDescriptor declarationDescriptor = $this$implicitModalityWithoutExtensions.getContainingDeclaration();
        if (!(declarationDescriptor instanceof ClassDescriptor)) {
            declarationDescriptor = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor)declarationDescriptor;
        if (classDescriptor == null) {
            return Modality.FINAL;
        }
        ClassDescriptor containingClassDescriptor = classDescriptor;
        if (!($this$implicitModalityWithoutExtensions instanceof CallableMemberDescriptor)) {
            return Modality.FINAL;
        }
        Collection<? extends CallableMemberDescriptor> collection = ((CallableMemberDescriptor)$this$implicitModalityWithoutExtensions).getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(collection, "this.overriddenDescriptors");
        Collection<? extends CallableMemberDescriptor> collection2 = collection;
        boolean bl = false;
        if (!collection2.isEmpty() && containingClassDescriptor.getModality() != Modality.FINAL) {
            return Modality.OPEN;
        }
        return containingClassDescriptor.getKind() == ClassKind.INTERFACE && Intrinsics.areEqual(((CallableMemberDescriptor)$this$implicitModalityWithoutExtensions).getVisibility(), Visibilities.PRIVATE) ^ true ? (((CallableMemberDescriptor)$this$implicitModalityWithoutExtensions).getModality() == Modality.ABSTRACT ? Modality.ABSTRACT : Modality.OPEN) : Modality.FINAL;
    }

    private final void renderModalityForCallable(CallableMemberDescriptor callable, StringBuilder builder) {
        if (!DescriptorUtils.isTopLevelDeclaration(callable) || callable.getModality() != Modality.FINAL) {
            if (this.getOverrideRenderingPolicy() == OverrideRenderingPolicy.RENDER_OVERRIDE && callable.getModality() == Modality.OPEN && this.overridesSomething(callable)) {
                return;
            }
            Modality modality = callable.getModality();
            Intrinsics.checkNotNullExpressionValue((Object)modality, "callable.modality");
            this.renderModality(modality, builder, this.implicitModalityWithoutExtensions(callable));
        }
    }

    private final void renderOverride(CallableMemberDescriptor callableMember, StringBuilder builder) {
        if (!this.getModifiers().contains((Object)DescriptorRendererModifier.OVERRIDE)) {
            return;
        }
        if (this.overridesSomething(callableMember) && this.getOverrideRenderingPolicy() != OverrideRenderingPolicy.RENDER_OPEN) {
            this.renderModifier(builder, true, "override");
            if (this.getVerbose()) {
                builder.append("/*").append(callableMember.getOverriddenDescriptors().size()).append("*/ ");
            }
        }
    }

    private final void renderMemberKind(CallableMemberDescriptor callableMember, StringBuilder builder) {
        if (!this.getModifiers().contains((Object)DescriptorRendererModifier.MEMBER_KIND)) {
            return;
        }
        if (this.getVerbose() && callableMember.getKind() != CallableMemberDescriptor.Kind.DECLARATION) {
            StringBuilder stringBuilder = builder.append("/*");
            String string = callableMember.getKind().name();
            boolean bl = false;
            String string2 = string;
            if (string2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string3 = string2.toLowerCase();
            Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.String).toLowerCase()");
            stringBuilder.append(string3).append("*/ ");
        }
    }

    private final void renderModifier(StringBuilder builder, boolean value, String modifier) {
        if (value) {
            builder.append(this.renderKeyword(modifier));
            builder.append(" ");
        }
    }

    private final void renderMemberModifiers(MemberDescriptor descriptor2, StringBuilder builder) {
        this.renderModifier(builder, descriptor2.isExternal(), "external");
        this.renderModifier(builder, this.getModifiers().contains((Object)DescriptorRendererModifier.EXPECT) && descriptor2.isExpect(), "expect");
        this.renderModifier(builder, this.getModifiers().contains((Object)DescriptorRendererModifier.ACTUAL) && descriptor2.isActual(), "actual");
    }

    /*
     * Unable to fully structure code
     */
    private final void renderAdditionalModifiers(FunctionDescriptor functionDescriptor, StringBuilder builder) {
        block11: {
            block10: {
                if (!functionDescriptor.isOperator()) ** GOTO lbl-1000
                v0 = functionDescriptor.getOverriddenDescriptors();
                Intrinsics.checkNotNullExpressionValue(v0, "functionDescriptor.overriddenDescriptors");
                $this$none$iv = v0;
                $i$f$none = false;
                if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                    v1 = true;
                } else {
                    for (T element$iv : $this$none$iv) {
                        it = (FunctionDescriptor)element$iv;
                        $i$a$-none-DescriptorRendererImpl$renderAdditionalModifiers$isOperator$1 = false;
                        v2 = it;
                        Intrinsics.checkNotNullExpressionValue(v2, "it");
                        if (!v2.isOperator()) continue;
                        v1 = false;
                        break block10;
                    }
                    v1 = true;
                }
            }
            if (v1 || this.getAlwaysRenderModifiers()) {
                v3 = true;
            } else lbl-1000:
            // 2 sources

            {
                v3 = isOperator = false;
            }
            if (!functionDescriptor.isInfix()) ** GOTO lbl-1000
            v4 = functionDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(v4, "functionDescriptor.overriddenDescriptors");
            $this$none$iv = v4;
            $i$f$none = false;
            if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                v5 = true;
            } else {
                for (T element$iv : $this$none$iv) {
                    it = (FunctionDescriptor)element$iv;
                    $i$a$-none-DescriptorRendererImpl$renderAdditionalModifiers$isInfix$1 = false;
                    v6 = it;
                    Intrinsics.checkNotNullExpressionValue(v6, "it");
                    if (!v6.isInfix()) continue;
                    v5 = false;
                    break block11;
                }
                v5 = true;
            }
        }
        if (v5 || this.getAlwaysRenderModifiers()) {
            v7 = true;
        } else lbl-1000:
        // 2 sources

        {
            v7 = false;
        }
        isInfix = v7;
        this.renderModifier(builder, functionDescriptor.isTailrec(), "tailrec");
        this.renderSuspendModifier(functionDescriptor, builder);
        this.renderModifier(builder, functionDescriptor.isInline(), "inline");
        this.renderModifier(builder, isInfix, "infix");
        this.renderModifier(builder, isOperator, "operator");
    }

    private final void renderSuspendModifier(FunctionDescriptor functionDescriptor, StringBuilder builder) {
        this.renderModifier(builder, functionDescriptor.isSuspend(), "suspend");
    }

    @Override
    @NotNull
    public String render(@NotNull DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "declarationDescriptor");
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl3 = false;
        boolean bl4 = false;
        StringBuilder $this$buildString = stringBuilder;
        boolean bl5 = false;
        declarationDescriptor.accept(new RenderDeclarationDescriptorVisitor(), $this$buildString);
        if (this.getWithDefinedIn()) {
            this.appendDefinedIn($this$buildString, declarationDescriptor);
        }
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    private final void renderTypeParameter(TypeParameterDescriptor typeParameter, StringBuilder builder, boolean topLevel) {
        if (topLevel) {
            builder.append(this.lt());
        }
        if (this.getVerbose()) {
            builder.append("/*").append(typeParameter.getIndex()).append("*/ ");
        }
        this.renderModifier(builder, typeParameter.isReified(), "reified");
        String variance = typeParameter.getVariance().getLabel();
        CharSequence charSequence = variance;
        boolean bl = false;
        this.renderModifier(builder, charSequence.length() > 0, variance);
        DescriptorRendererImpl.renderAnnotations$default(this, builder, typeParameter, null, 2, null);
        this.renderName(typeParameter, builder, topLevel);
        int upperBoundsCount = typeParameter.getUpperBounds().size();
        if (upperBoundsCount > 1 && !topLevel || upperBoundsCount == 1) {
            KotlinType upperBound = typeParameter.getUpperBounds().iterator().next();
            if (!KotlinBuiltIns.isDefaultBound(upperBound)) {
                StringBuilder stringBuilder = builder.append(" : ");
                KotlinType kotlinType = upperBound;
                Intrinsics.checkNotNullExpressionValue(kotlinType, "upperBound");
                stringBuilder.append(this.renderType(kotlinType));
            }
        } else if (topLevel) {
            boolean first = true;
            for (KotlinType upperBound : typeParameter.getUpperBounds()) {
                if (KotlinBuiltIns.isDefaultBound(upperBound)) continue;
                if (first) {
                    builder.append(" : ");
                } else {
                    builder.append(" & ");
                }
                KotlinType kotlinType = upperBound;
                Intrinsics.checkNotNullExpressionValue(kotlinType, "upperBound");
                builder.append(this.renderType(kotlinType));
                first = false;
            }
        }
        if (topLevel) {
            builder.append(this.gt());
        }
    }

    private final void renderTypeParameters(List<? extends TypeParameterDescriptor> typeParameters2, StringBuilder builder, boolean withSpace) {
        if (this.getWithoutTypeParameters()) {
            return;
        }
        Collection collection = typeParameters2;
        boolean bl = false;
        if (!collection.isEmpty()) {
            builder.append(this.lt());
            this.renderTypeParameterList(builder, typeParameters2);
            builder.append(this.gt());
            if (withSpace) {
                builder.append(" ");
            }
        }
    }

    private final void renderTypeParameterList(StringBuilder builder, List<? extends TypeParameterDescriptor> typeParameters2) {
        Iterator<? extends TypeParameterDescriptor> iterator2 = typeParameters2.iterator();
        while (iterator2.hasNext()) {
            TypeParameterDescriptor typeParameterDescriptor = iterator2.next();
            this.renderTypeParameter(typeParameterDescriptor, builder, false);
            if (!iterator2.hasNext()) continue;
            builder.append(", ");
        }
    }

    private final void renderFunction(FunctionDescriptor function, StringBuilder builder) {
        if (!this.getStartFromName()) {
            if (!this.getStartFromDeclarationKeyword()) {
                DescriptorRendererImpl.renderAnnotations$default(this, builder, function, null, 2, null);
                Visibility visibility = function.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "function.visibility");
                this.renderVisibility(visibility, builder);
                this.renderModalityForCallable(function, builder);
                if (this.getIncludeAdditionalModifiers()) {
                    this.renderMemberModifiers(function, builder);
                }
                this.renderOverride(function, builder);
                if (this.getIncludeAdditionalModifiers()) {
                    this.renderAdditionalModifiers(function, builder);
                } else {
                    this.renderSuspendModifier(function, builder);
                }
                this.renderMemberKind(function, builder);
                if (this.getVerbose()) {
                    if (function.isHiddenToOvercomeSignatureClash()) {
                        builder.append("/*isHiddenToOvercomeSignatureClash*/ ");
                    }
                    if (function.isHiddenForResolutionEverywhereBesideSupercalls()) {
                        builder.append("/*isHiddenForResolutionEverywhereBesideSupercalls*/ ");
                    }
                }
            }
            builder.append(this.renderKeyword("fun")).append(" ");
            List<TypeParameterDescriptor> list = function.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(list, "function.typeParameters");
            this.renderTypeParameters(list, builder, true);
            this.renderReceiver(function, builder);
        }
        this.renderName(function, builder, true);
        List<ValueParameterDescriptor> list = function.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(list, "function.valueParameters");
        this.renderValueParameters((Collection<? extends ValueParameterDescriptor>)list, function.hasSynthesizedParameterNames(), builder);
        this.renderReceiverAfterName(function, builder);
        KotlinType returnType = function.getReturnType();
        if (!(this.getWithoutReturnType() || !this.getUnitReturnType() && returnType != null && KotlinBuiltIns.isUnit(returnType))) {
            builder.append(": ").append(returnType == null ? "[NULL]" : this.renderType(returnType));
        }
        List<TypeParameterDescriptor> list2 = function.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(list2, "function.typeParameters");
        this.renderWhereSuffix(list2, builder);
    }

    private final void renderReceiverAfterName(CallableDescriptor callableDescriptor, StringBuilder builder) {
        if (!this.getReceiverAfterName()) {
            return;
        }
        ReceiverParameterDescriptor receiver = callableDescriptor.getExtensionReceiverParameter();
        if (receiver != null) {
            StringBuilder stringBuilder = builder.append(" on ");
            KotlinType kotlinType = receiver.getType();
            Intrinsics.checkNotNullExpressionValue(kotlinType, "receiver.type");
            stringBuilder.append(this.renderType(kotlinType));
        }
    }

    private final void renderReceiver(CallableDescriptor callableDescriptor, StringBuilder builder) {
        ReceiverParameterDescriptor receiver = callableDescriptor.getExtensionReceiverParameter();
        if (receiver != null) {
            this.renderAnnotations(builder, receiver, AnnotationUseSiteTarget.RECEIVER);
            KotlinType kotlinType = receiver.getType();
            Intrinsics.checkNotNullExpressionValue(kotlinType, "receiver.type");
            KotlinType type2 = kotlinType;
            String result2 = this.renderType(type2);
            if (this.shouldRenderAsPrettyFunctionType(type2) && !TypeUtils.isNullableType(type2)) {
                result2 = '(' + result2 + ')';
            }
            builder.append(result2).append(".");
        }
    }

    /*
     * Unable to fully structure code
     */
    private final void renderConstructor(ConstructorDescriptor constructor, StringBuilder builder) {
        block9: {
            DescriptorRendererImpl.renderAnnotations$default(this, builder, constructor, null, 2, null);
            if (this.options.getRenderDefaultVisibility()) break block9;
            v0 = constructor.getConstructedClass();
            Intrinsics.checkNotNullExpressionValue(v0, "constructor.constructedClass");
            if (v0.getModality() == Modality.SEALED) ** GOTO lbl-1000
        }
        v1 = constructor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(v1, "constructor.visibility");
        if (this.renderVisibility(v1, builder)) {
            v2 = true;
        } else lbl-1000:
        // 2 sources

        {
            v2 = false;
        }
        visibilityRendered = v2;
        this.renderMemberKind(constructor, builder);
        v3 = constructorKeywordRendered = this.getRenderConstructorKeyword() != false || constructor.isPrimary() == false || visibilityRendered != false;
        if (constructorKeywordRendered) {
            builder.append(this.renderKeyword("constructor"));
        }
        v4 = constructor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(v4, "constructor.containingDeclaration");
        classDescriptor = v4;
        if (this.getSecondaryConstructorsAsPrimary()) {
            if (constructorKeywordRendered) {
                builder.append(" ");
            }
            this.renderName(classDescriptor, builder, true);
            v5 = constructor.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(v5, "constructor.typeParameters");
            this.renderTypeParameters(v5, builder, false);
        }
        v6 = constructor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(v6, "constructor.valueParameters");
        this.renderValueParameters((Collection<? extends ValueParameterDescriptor>)v6, constructor.hasSynthesizedParameterNames(), builder);
        if (this.getRenderConstructorDelegation() && !constructor.isPrimary() && classDescriptor instanceof ClassDescriptor && (primaryConstructor = ((ClassDescriptor)classDescriptor).getUnsubstitutedPrimaryConstructor()) != null) {
            v7 = primaryConstructor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(v7, "primaryConstructor.valueParameters");
            $this$filter$iv = v7;
            $i$f$filter = false;
            var10_10 = $this$filter$iv;
            destination$iv$iv = new ArrayList<E>();
            $i$f$filterTo = false;
            for (T element$iv$iv : $this$filterTo$iv$iv) {
                it = (ValueParameterDescriptor)element$iv$iv;
                $i$a$-filter-DescriptorRendererImpl$renderConstructor$parametersWithoutDefault$1 = false;
                if (!(it.declaresDefaultValue() == false && it.getVarargElementType() == null)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            parametersWithoutDefault = (List)destination$iv$iv;
            var8_7 = parametersWithoutDefault;
            var9_8 = false;
            if (var8_7.isEmpty() == false) {
                builder.append(" : ").append(this.renderKeyword("this"));
                var8_7 = renderConstructor.1.INSTANCE;
                var9_9 = null;
                var10_11 = 0;
                var11_12 = ", ";
                var12_14 = ")";
                var13_15 = "(";
                builder.append(CollectionsKt.joinToString$default(parametersWithoutDefault, var11_12, var13_15, var12_14, var10_11, var9_9, (Function1)var8_7, 24, null));
            }
        }
        if (this.getSecondaryConstructorsAsPrimary()) {
            v8 = constructor.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(v8, "constructor.typeParameters");
            this.renderWhereSuffix(v8, builder);
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void renderWhereSuffix(List<? extends TypeParameterDescriptor> typeParameters2, StringBuilder builder) {
        if (this.getWithoutTypeParameters()) {
            return;
        }
        ArrayList upperBoundStrings = new ArrayList(0);
        for (TypeParameterDescriptor collection : typeParameters2) {
            List<KotlinType> list = collection.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(list, "typeParameter.upperBounds");
            Iterable $this$mapTo$iv = CollectionsKt.drop((Iterable)list, 1);
            boolean $i$f$mapTo = false;
            for (Object item$iv : $this$mapTo$iv) {
                void it;
                KotlinType kotlinType = (KotlinType)item$iv;
                Collection collection2 = upperBoundStrings;
                boolean bl = false;
                StringBuilder stringBuilder = new StringBuilder();
                Name name = collection.getName();
                Intrinsics.checkNotNullExpressionValue(name, "typeParameter.name");
                StringBuilder stringBuilder2 = stringBuilder.append(this.renderName(name, false)).append(" : ");
                void v4 = it;
                Intrinsics.checkNotNullExpressionValue(v4, "it");
                String string = stringBuilder2.append(this.renderType((KotlinType)v4)).toString();
                collection2.add(string);
            }
        }
        Collection collection = upperBoundStrings;
        boolean bl = false;
        if (!collection.isEmpty()) {
            builder.append(" ").append(this.renderKeyword("where")).append(" ");
            CollectionsKt.joinTo$default(upperBoundStrings, builder, ", ", null, null, 0, null, null, 124, null);
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void renderValueParameters(Collection<? extends ValueParameterDescriptor> parameters2, boolean synthesizedParameterNames, StringBuilder builder) {
        boolean includeNames = this.shouldRenderParameterNames(synthesizedParameterNames);
        int parameterCount = parameters2.size();
        this.getValueParametersHandler().appendBeforeValueParameters(parameterCount, builder);
        boolean bl = false;
        for (ValueParameterDescriptor parameter : (Iterable)parameters2) {
            void index;
            this.getValueParametersHandler().appendBeforeValueParameter(parameter, (int)index, parameterCount, builder);
            this.renderValueParameter(parameter, includeNames, builder, false);
            this.getValueParametersHandler().appendAfterValueParameter(parameter, (int)index, parameterCount, builder);
            ++index;
        }
        this.getValueParametersHandler().appendAfterValueParameters(parameterCount, builder);
    }

    private final boolean shouldRenderParameterNames(boolean synthesizedParameterNames) {
        boolean bl;
        switch (DescriptorRendererImpl$WhenMappings.$EnumSwitchMapping$4[this.getParameterNameRenderingPolicy().ordinal()]) {
            case 1: {
                bl = true;
                break;
            }
            case 2: {
                if (!synthesizedParameterNames) {
                    bl = true;
                    break;
                }
                bl = false;
                break;
            }
            case 3: {
                bl = false;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return bl;
    }

    /*
     * Unable to fully structure code
     */
    private final void renderValueParameter(ValueParameterDescriptor valueParameter, boolean includeName, StringBuilder builder, boolean topLevel) {
        if (topLevel) {
            builder.append(this.renderKeyword("value-parameter")).append(" ");
        }
        if (this.getVerbose()) {
            builder.append("/*").append(valueParameter.getIndex()).append("*/ ");
        }
        DescriptorRendererImpl.renderAnnotations$default(this, builder, valueParameter, null, 2, null);
        this.renderModifier(builder, valueParameter.isCrossinline(), "crossinline");
        this.renderModifier(builder, valueParameter.isNoinline(), "noinline");
        if (!this.getRenderPrimaryConstructorParametersAsProperties()) ** GOTO lbl-1000
        v0 = valueParameter.getContainingDeclaration();
        if (!(v0 instanceof ClassConstructorDescriptor)) {
            v0 = null;
        }
        v1 = (ClassConstructorDescriptor)v0;
        if (v1 == null) ** GOTO lbl-1000
        if (v1.isPrimary()) {
            v2 = true;
        } else lbl-1000:
        // 3 sources

        {
            v2 = isPrimaryConstructor = false;
        }
        if (isPrimaryConstructor) {
            this.renderModifier(builder, this.getActualPropertiesInPrimaryConstructor(), "actual");
        }
        this.renderVariable(valueParameter, includeName, builder, topLevel, isPrimaryConstructor);
        v3 = this.getDefaultParameterValueRenderer() != null && (this.getDebugMode() != false ? valueParameter.declaresDefaultValue() : DescriptorUtilsKt.declaresOrInheritsDefaultValue(valueParameter)) != false ? true : (withDefaultValue = false);
        if (withDefaultValue) {
            v4 = new StringBuilder().append(" = ");
            v5 = this.getDefaultParameterValueRenderer();
            Intrinsics.checkNotNull(v5);
            builder.append(v4.append(v5.invoke(valueParameter)).toString());
        }
    }

    private final void renderValVarPrefix(VariableDescriptor variable, StringBuilder builder, boolean isInPrimaryConstructor) {
        if (isInPrimaryConstructor || !(variable instanceof ValueParameterDescriptor)) {
            builder.append(this.renderKeyword(variable.isVar() ? "var" : "val")).append(" ");
        }
    }

    static /* synthetic */ void renderValVarPrefix$default(DescriptorRendererImpl descriptorRendererImpl, VariableDescriptor variableDescriptor, StringBuilder stringBuilder, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        descriptorRendererImpl.renderValVarPrefix(variableDescriptor, stringBuilder, bl);
    }

    private final void renderVariable(VariableDescriptor variable, boolean includeName, StringBuilder builder, boolean topLevel, boolean isInPrimaryConstructor) {
        KotlinType kotlinType = variable.getType();
        Intrinsics.checkNotNullExpressionValue(kotlinType, "variable.type");
        KotlinType realType = kotlinType;
        VariableDescriptor variableDescriptor = variable;
        if (!(variableDescriptor instanceof ValueParameterDescriptor)) {
            variableDescriptor = null;
        }
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor)variableDescriptor;
        KotlinType varargElementType = valueParameterDescriptor != null ? valueParameterDescriptor.getVarargElementType() : null;
        KotlinType kotlinType2 = varargElementType;
        if (kotlinType2 == null) {
            kotlinType2 = realType;
        }
        KotlinType typeToRender = kotlinType2;
        this.renderModifier(builder, varargElementType != null, "vararg");
        if (isInPrimaryConstructor || topLevel && !this.getStartFromName()) {
            this.renderValVarPrefix(variable, builder, isInPrimaryConstructor);
        }
        if (includeName) {
            this.renderName(variable, builder, topLevel);
            builder.append(": ");
        }
        builder.append(this.renderType(typeToRender));
        this.renderInitializer(variable, builder);
        if (this.getVerbose() && varargElementType != null) {
            builder.append(" /*").append(this.renderType(realType)).append("*/");
        }
    }

    private final void renderProperty(PropertyDescriptor property, StringBuilder builder) {
        if (!this.getStartFromName()) {
            if (!this.getStartFromDeclarationKeyword()) {
                this.renderPropertyAnnotations(property, builder);
                Visibility visibility = property.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "property.visibility");
                this.renderVisibility(visibility, builder);
                this.renderModifier(builder, this.getModifiers().contains((Object)DescriptorRendererModifier.CONST) && property.isConst(), "const");
                this.renderMemberModifiers(property, builder);
                this.renderModalityForCallable(property, builder);
                this.renderOverride(property, builder);
                this.renderModifier(builder, this.getModifiers().contains((Object)DescriptorRendererModifier.LATEINIT) && property.isLateInit(), "lateinit");
                this.renderMemberKind(property, builder);
            }
            DescriptorRendererImpl.renderValVarPrefix$default(this, property, builder, false, 4, null);
            List<TypeParameterDescriptor> list = property.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(list, "property.typeParameters");
            this.renderTypeParameters(list, builder, true);
            this.renderReceiver(property, builder);
        }
        this.renderName(property, builder, true);
        StringBuilder stringBuilder = builder.append(": ");
        KotlinType kotlinType = property.getType();
        Intrinsics.checkNotNullExpressionValue(kotlinType, "property.type");
        stringBuilder.append(this.renderType(kotlinType));
        this.renderReceiverAfterName(property, builder);
        this.renderInitializer(property, builder);
        List<TypeParameterDescriptor> list = property.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(list, "property.typeParameters");
        this.renderWhereSuffix(list, builder);
    }

    private final void renderPropertyAnnotations(PropertyDescriptor property, StringBuilder builder) {
        block5: {
            FieldDescriptor it;
            boolean bl;
            boolean bl2;
            Annotated annotated;
            if (!this.getModifiers().contains((Object)DescriptorRendererModifier.ANNOTATIONS)) {
                return;
            }
            DescriptorRendererImpl.renderAnnotations$default(this, builder, property, null, 2, null);
            FieldDescriptor fieldDescriptor = property.getBackingField();
            if (fieldDescriptor != null) {
                annotated = fieldDescriptor;
                bl2 = false;
                bl = false;
                it = annotated;
                boolean bl3 = false;
                FieldDescriptor fieldDescriptor2 = it;
                Intrinsics.checkNotNullExpressionValue(fieldDescriptor2, "it");
                this.renderAnnotations(builder, fieldDescriptor2, AnnotationUseSiteTarget.FIELD);
            }
            FieldDescriptor fieldDescriptor3 = property.getDelegateField();
            if (fieldDescriptor3 != null) {
                annotated = fieldDescriptor3;
                bl2 = false;
                bl = false;
                it = annotated;
                boolean bl4 = false;
                FieldDescriptor fieldDescriptor4 = it;
                Intrinsics.checkNotNullExpressionValue(fieldDescriptor4, "it");
                this.renderAnnotations(builder, fieldDescriptor4, AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD);
            }
            if (this.getPropertyAccessorRenderingPolicy() != PropertyAccessorRenderingPolicy.NONE) break block5;
            PropertyGetterDescriptor propertyGetterDescriptor = property.getGetter();
            if (propertyGetterDescriptor != null) {
                annotated = propertyGetterDescriptor;
                bl2 = false;
                bl = false;
                it = annotated;
                boolean bl5 = false;
                FieldDescriptor fieldDescriptor5 = it;
                Intrinsics.checkNotNullExpressionValue(fieldDescriptor5, "it");
                this.renderAnnotations(builder, fieldDescriptor5, AnnotationUseSiteTarget.PROPERTY_GETTER);
            }
            PropertySetterDescriptor propertySetterDescriptor = property.getSetter();
            if (propertySetterDescriptor != null) {
                annotated = propertySetterDescriptor;
                bl2 = false;
                bl = false;
                Annotated setter = annotated;
                boolean bl6 = false;
                Annotated annotated2 = setter;
                boolean bl7 = false;
                boolean bl8 = false;
                Annotated it2 = annotated2;
                boolean bl9 = false;
                Annotated annotated3 = it2;
                Intrinsics.checkNotNullExpressionValue(annotated3, "it");
                this.renderAnnotations(builder, annotated3, AnnotationUseSiteTarget.PROPERTY_SETTER);
                Annotated annotated4 = setter;
                Intrinsics.checkNotNullExpressionValue(annotated4, "setter");
                List<ValueParameterDescriptor> list = annotated4.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(list, "setter.valueParameters");
                annotated2 = CollectionsKt.single(list);
                bl7 = false;
                bl8 = false;
                it2 = (ValueParameterDescriptor)annotated2;
                boolean bl10 = false;
                Annotated annotated5 = it2;
                Intrinsics.checkNotNullExpressionValue(annotated5, "it");
                this.renderAnnotations(builder, annotated5, AnnotationUseSiteTarget.SETTER_PARAMETER);
            }
        }
    }

    private final void renderInitializer(VariableDescriptor variable, StringBuilder builder) {
        block1: {
            if (!this.getIncludePropertyConstant()) break block1;
            ConstantValue<?> constantValue = variable.getCompileTimeInitializer();
            if (constantValue != null) {
                ConstantValue<?> constantValue2 = constantValue;
                boolean bl = false;
                boolean bl2 = false;
                ConstantValue<?> constant = constantValue2;
                boolean bl3 = false;
                StringBuilder stringBuilder = builder.append(" = ");
                ConstantValue<?> constantValue3 = constant;
                Intrinsics.checkNotNullExpressionValue(constantValue3, "constant");
                stringBuilder.append(this.escape(this.renderConstant(constantValue3)));
            }
        }
    }

    private final void renderTypeAlias(TypeAliasDescriptor typeAlias, StringBuilder builder) {
        DescriptorRendererImpl.renderAnnotations$default(this, builder, typeAlias, null, 2, null);
        Visibility visibility = typeAlias.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "typeAlias.visibility");
        this.renderVisibility(visibility, builder);
        this.renderMemberModifiers(typeAlias, builder);
        builder.append(this.renderKeyword("typealias")).append(" ");
        this.renderName(typeAlias, builder, true);
        List<TypeParameterDescriptor> list = typeAlias.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(list, "typeAlias.declaredTypeParameters");
        this.renderTypeParameters(list, builder, false);
        this.renderCapturedTypeParametersIfRequired(typeAlias, builder);
        builder.append(" = ").append(this.renderType(typeAlias.getUnderlyingType()));
    }

    private final void renderCapturedTypeParametersIfRequired(ClassifierDescriptorWithTypeParameters classifier2, StringBuilder builder) {
        List<TypeParameterDescriptor> list = classifier2.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(list, "classifier.declaredTypeParameters");
        List<TypeParameterDescriptor> typeParameters2 = list;
        TypeConstructor typeConstructor2 = classifier2.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "classifier.typeConstructor");
        List<TypeParameterDescriptor> list2 = typeConstructor2.getParameters();
        Intrinsics.checkNotNullExpressionValue(list2, "classifier.typeConstructor.parameters");
        List<TypeParameterDescriptor> typeConstructorParameters = list2;
        if (this.getVerbose() && classifier2.isInner() && typeConstructorParameters.size() > typeParameters2.size()) {
            builder.append(" /*captured type parameters: ");
            this.renderTypeParameterList(builder, typeConstructorParameters.subList(typeParameters2.size(), typeConstructorParameters.size()));
            builder.append("*/");
        }
    }

    private final void renderClass(ClassDescriptor klass, StringBuilder builder) {
        ClassConstructorDescriptor primaryConstructor2;
        boolean isEnumEntry;
        boolean bl = isEnumEntry = klass.getKind() == ClassKind.ENUM_ENTRY;
        if (!this.getStartFromName()) {
            DescriptorRendererImpl.renderAnnotations$default(this, builder, klass, null, 2, null);
            if (!isEnumEntry) {
                Visibility visibility = klass.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "klass.visibility");
                this.renderVisibility(visibility, builder);
            }
            if (klass.getKind() != ClassKind.INTERFACE || klass.getModality() != Modality.ABSTRACT) {
                ClassKind classKind = klass.getKind();
                Intrinsics.checkNotNullExpressionValue((Object)classKind, "klass.kind");
                if (!classKind.isSingleton() || klass.getModality() != Modality.FINAL) {
                    Modality modality = klass.getModality();
                    Intrinsics.checkNotNullExpressionValue((Object)modality, "klass.modality");
                    this.renderModality(modality, builder, this.implicitModalityWithoutExtensions(klass));
                }
            }
            this.renderMemberModifiers(klass, builder);
            this.renderModifier(builder, this.getModifiers().contains((Object)DescriptorRendererModifier.INNER) && klass.isInner(), "inner");
            this.renderModifier(builder, this.getModifiers().contains((Object)DescriptorRendererModifier.DATA) && klass.isData(), "data");
            this.renderModifier(builder, this.getModifiers().contains((Object)DescriptorRendererModifier.INLINE) && klass.isInline(), "inline");
            this.renderModifier(builder, this.getModifiers().contains((Object)DescriptorRendererModifier.FUN) && klass.isFun(), "fun");
            this.renderClassKindPrefix(klass, builder);
        }
        if (!DescriptorUtils.isCompanionObject(klass)) {
            if (!this.getStartFromName()) {
                this.renderSpaceIfNeeded(builder);
            }
            this.renderName(klass, builder, true);
        } else {
            this.renderCompanionObjectName(klass, builder);
        }
        if (isEnumEntry) {
            return;
        }
        List<TypeParameterDescriptor> list = klass.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(list, "klass.declaredTypeParameters");
        List<TypeParameterDescriptor> typeParameters2 = list;
        this.renderTypeParameters(typeParameters2, builder, false);
        this.renderCapturedTypeParametersIfRequired(klass, builder);
        ClassKind classKind = klass.getKind();
        Intrinsics.checkNotNullExpressionValue((Object)classKind, "klass.kind");
        if (!classKind.isSingleton() && this.getClassWithPrimaryConstructor() && (primaryConstructor2 = klass.getUnsubstitutedPrimaryConstructor()) != null) {
            builder.append(" ");
            DescriptorRendererImpl.renderAnnotations$default(this, builder, primaryConstructor2, null, 2, null);
            Visibility visibility = primaryConstructor2.getVisibility();
            Intrinsics.checkNotNullExpressionValue(visibility, "primaryConstructor.visibility");
            this.renderVisibility(visibility, builder);
            builder.append(this.renderKeyword("constructor"));
            List<ValueParameterDescriptor> list2 = primaryConstructor2.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(list2, "primaryConstructor.valueParameters");
            this.renderValueParameters((Collection<? extends ValueParameterDescriptor>)list2, primaryConstructor2.hasSynthesizedParameterNames(), builder);
        }
        this.renderSuperTypes(klass, builder);
        this.renderWhereSuffix(typeParameters2, builder);
    }

    private final void renderSuperTypes(ClassDescriptor klass, StringBuilder builder) {
        if (this.getWithoutSuperTypes()) {
            return;
        }
        if (KotlinBuiltIns.isNothing(klass.getDefaultType())) {
            return;
        }
        TypeConstructor typeConstructor2 = klass.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "klass.typeConstructor");
        Collection<KotlinType> collection = typeConstructor2.getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collection, "klass.typeConstructor.supertypes");
        Collection<KotlinType> supertypes2 = collection;
        if (supertypes2.isEmpty() || supertypes2.size() == 1 && KotlinBuiltIns.isAnyOrNullableAny(supertypes2.iterator().next())) {
            return;
        }
        this.renderSpaceIfNeeded(builder);
        builder.append(": ");
        CollectionsKt.joinTo$default(supertypes2, builder, ", ", null, null, 0, null, new Function1<KotlinType, CharSequence>(this){
            final /* synthetic */ DescriptorRendererImpl this$0;

            @NotNull
            public final CharSequence invoke(KotlinType it) {
                KotlinType kotlinType = it;
                Intrinsics.checkNotNullExpressionValue(kotlinType, "it");
                return this.this$0.renderType(kotlinType);
            }
            {
                this.this$0 = descriptorRendererImpl;
                super(1);
            }
        }, 60, null);
    }

    private final void renderClassKindPrefix(ClassDescriptor klass, StringBuilder builder) {
        builder.append(this.renderKeyword(DescriptorRenderer.Companion.getClassifierKindPrefix(klass)));
    }

    private final void renderPackageView(PackageViewDescriptor packageView, StringBuilder builder) {
        this.renderPackageHeader(packageView.getFqName(), "package", builder);
        if (this.getDebugMode()) {
            builder.append(" in context of ");
            this.renderName(packageView.getModule(), builder, false);
        }
    }

    private final void renderPackageFragment(PackageFragmentDescriptor fragment, StringBuilder builder) {
        this.renderPackageHeader(fragment.getFqName(), "package-fragment", builder);
        if (this.getDebugMode()) {
            builder.append(" in ");
            this.renderName(fragment.getContainingDeclaration(), builder, false);
        }
    }

    private final void renderPackageHeader(FqName fqName2, String fragmentOrView, StringBuilder builder) {
        builder.append(this.renderKeyword(fragmentOrView));
        FqNameUnsafe fqNameUnsafe = fqName2.toUnsafe();
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe, "fqName.toUnsafe()");
        String fqNameString = this.renderFqName(fqNameUnsafe);
        CharSequence charSequence = fqNameString;
        boolean bl = false;
        if (charSequence.length() > 0) {
            builder.append(" ");
            builder.append(fqNameString);
        }
    }

    private final void renderAccessorModifiers(PropertyAccessorDescriptor descriptor2, StringBuilder builder) {
        this.renderMemberModifiers(descriptor2, builder);
    }

    private final void renderSpaceIfNeeded(StringBuilder builder) {
        int length = builder.length();
        if (length == 0 || builder.charAt(length - 1) != ' ') {
            builder.append(' ');
        }
    }

    private final String replacePrefixes(String lowerRendered, String lowerPrefix, String upperRendered, String upperPrefix, String foldedPrefix) {
        if (StringsKt.startsWith$default(lowerRendered, lowerPrefix, false, 2, null) && StringsKt.startsWith$default(upperRendered, upperPrefix, false, 2, null)) {
            String string = lowerRendered;
            int n = lowerPrefix.length();
            int n2 = 0;
            String string2 = string;
            if (string2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string3 = string2.substring(n);
            Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.String).substring(startIndex)");
            String lowerWithoutPrefix = string3;
            String string4 = upperRendered;
            n2 = upperPrefix.length();
            boolean bl = false;
            String string5 = string4;
            if (string5 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String string6 = string5.substring(n2);
            Intrinsics.checkNotNullExpressionValue(string6, "(this as java.lang.String).substring(startIndex)");
            String upperWithoutPrefix = string6;
            String flexibleCollectionName = foldedPrefix + lowerWithoutPrefix;
            if (Intrinsics.areEqual(lowerWithoutPrefix, upperWithoutPrefix)) {
                return flexibleCollectionName;
            }
            if (this.differsOnlyInNullability(lowerWithoutPrefix, upperWithoutPrefix)) {
                return flexibleCollectionName + '!';
            }
        }
        return null;
    }

    private final boolean differsOnlyInNullability(String lower, String upper) {
        return Intrinsics.areEqual(lower, StringsKt.replace$default(upper, "?", "", false, 4, null)) || StringsKt.endsWith$default(upper, "?", false, 2, null) && Intrinsics.areEqual(lower + '?', upper) || Intrinsics.areEqual('(' + lower + ")?", upper);
    }

    private final boolean overridesSomething(CallableMemberDescriptor callable) {
        return !callable.getOverriddenDescriptors().isEmpty();
    }

    @NotNull
    public final DescriptorRendererOptionsImpl getOptions() {
        return this.options;
    }

    public DescriptorRendererImpl(@NotNull DescriptorRendererOptionsImpl options) {
        Intrinsics.checkNotNullParameter(options, "options");
        this.options = options;
        boolean bl = this.options.isLocked();
        boolean bl2 = false;
        boolean bl3 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl4 = false;
            String string = "Assertion failed";
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        this.functionTypeAnnotationsRenderer$delegate = LazyKt.lazy((Function0)new Function0<DescriptorRendererImpl>(this){
            final /* synthetic */ DescriptorRendererImpl this$0;

            @NotNull
            public final DescriptorRendererImpl invoke() {
                DescriptorRenderer descriptorRenderer2 = this.this$0.withOptions(functionTypeAnnotationsRenderer.1.INSTANCE);
                if (descriptorRenderer2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.renderer.DescriptorRendererImpl");
                }
                return (DescriptorRendererImpl)descriptorRenderer2;
            }
            {
                this.this$0 = descriptorRendererImpl;
                super(0);
            }
        });
    }

    public boolean getActualPropertiesInPrimaryConstructor() {
        return this.options.getActualPropertiesInPrimaryConstructor();
    }

    public boolean getAlwaysRenderModifiers() {
        return this.options.getAlwaysRenderModifiers();
    }

    @Override
    @NotNull
    public AnnotationArgumentsRenderingPolicy getAnnotationArgumentsRenderingPolicy() {
        return this.options.getAnnotationArgumentsRenderingPolicy();
    }

    @Override
    public void setAnnotationArgumentsRenderingPolicy(@NotNull AnnotationArgumentsRenderingPolicy annotationArgumentsRenderingPolicy) {
        Intrinsics.checkNotNullParameter((Object)annotationArgumentsRenderingPolicy, "<set-?>");
        this.options.setAnnotationArgumentsRenderingPolicy(annotationArgumentsRenderingPolicy);
    }

    @Nullable
    public Function1<AnnotationDescriptor, Boolean> getAnnotationFilter() {
        return this.options.getAnnotationFilter();
    }

    public boolean getBoldOnlyForNamesInHtml() {
        return this.options.getBoldOnlyForNamesInHtml();
    }

    public boolean getClassWithPrimaryConstructor() {
        return this.options.getClassWithPrimaryConstructor();
    }

    @NotNull
    public ClassifierNamePolicy getClassifierNamePolicy() {
        return this.options.getClassifierNamePolicy();
    }

    @Override
    public void setClassifierNamePolicy(@NotNull ClassifierNamePolicy classifierNamePolicy) {
        Intrinsics.checkNotNullParameter(classifierNamePolicy, "<set-?>");
        this.options.setClassifierNamePolicy(classifierNamePolicy);
    }

    @Override
    public boolean getDebugMode() {
        return this.options.getDebugMode();
    }

    @Override
    public void setDebugMode(boolean bl) {
        this.options.setDebugMode(bl);
    }

    @Nullable
    public Function1<ValueParameterDescriptor, String> getDefaultParameterValueRenderer() {
        return this.options.getDefaultParameterValueRenderer();
    }

    public boolean getEachAnnotationOnNewLine() {
        return this.options.getEachAnnotationOnNewLine();
    }

    @Override
    public boolean getEnhancedTypes() {
        return this.options.getEnhancedTypes();
    }

    @NotNull
    public Set<FqName> getExcludedAnnotationClasses() {
        return this.options.getExcludedAnnotationClasses();
    }

    @Override
    @NotNull
    public Set<FqName> getExcludedTypeAnnotationClasses() {
        return this.options.getExcludedTypeAnnotationClasses();
    }

    @Override
    public void setExcludedTypeAnnotationClasses(@NotNull Set<FqName> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.options.setExcludedTypeAnnotationClasses(set);
    }

    public boolean getIncludeAdditionalModifiers() {
        return this.options.getIncludeAdditionalModifiers();
    }

    public boolean getIncludeAnnotationArguments() {
        return this.options.getIncludeAnnotationArguments();
    }

    public boolean getIncludeEmptyAnnotationArguments() {
        return this.options.getIncludeEmptyAnnotationArguments();
    }

    public boolean getIncludePropertyConstant() {
        return this.options.getIncludePropertyConstant();
    }

    public boolean getInformativeErrorType() {
        return this.options.getInformativeErrorType();
    }

    @NotNull
    public Set<DescriptorRendererModifier> getModifiers() {
        return this.options.getModifiers();
    }

    @Override
    public void setModifiers(@NotNull Set<? extends DescriptorRendererModifier> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.options.setModifiers(set);
    }

    public boolean getNormalizedVisibilities() {
        return this.options.getNormalizedVisibilities();
    }

    @NotNull
    public OverrideRenderingPolicy getOverrideRenderingPolicy() {
        return this.options.getOverrideRenderingPolicy();
    }

    @NotNull
    public ParameterNameRenderingPolicy getParameterNameRenderingPolicy() {
        return this.options.getParameterNameRenderingPolicy();
    }

    @Override
    public void setParameterNameRenderingPolicy(@NotNull ParameterNameRenderingPolicy parameterNameRenderingPolicy) {
        Intrinsics.checkNotNullParameter((Object)parameterNameRenderingPolicy, "<set-?>");
        this.options.setParameterNameRenderingPolicy(parameterNameRenderingPolicy);
    }

    public boolean getParameterNamesInFunctionalTypes() {
        return this.options.getParameterNamesInFunctionalTypes();
    }

    public boolean getPresentableUnresolvedTypes() {
        return this.options.getPresentableUnresolvedTypes();
    }

    @NotNull
    public PropertyAccessorRenderingPolicy getPropertyAccessorRenderingPolicy() {
        return this.options.getPropertyAccessorRenderingPolicy();
    }

    public boolean getReceiverAfterName() {
        return this.options.getReceiverAfterName();
    }

    @Override
    public void setReceiverAfterName(boolean bl) {
        this.options.setReceiverAfterName(bl);
    }

    public boolean getRenderCompanionObjectName() {
        return this.options.getRenderCompanionObjectName();
    }

    @Override
    public void setRenderCompanionObjectName(boolean bl) {
        this.options.setRenderCompanionObjectName(bl);
    }

    public boolean getRenderConstructorDelegation() {
        return this.options.getRenderConstructorDelegation();
    }

    public boolean getRenderConstructorKeyword() {
        return this.options.getRenderConstructorKeyword();
    }

    public boolean getRenderDefaultAnnotationArguments() {
        return this.options.getRenderDefaultAnnotationArguments();
    }

    public boolean getRenderDefaultModality() {
        return this.options.getRenderDefaultModality();
    }

    public boolean getRenderDefaultVisibility() {
        return this.options.getRenderDefaultVisibility();
    }

    public boolean getRenderPrimaryConstructorParametersAsProperties() {
        return this.options.getRenderPrimaryConstructorParametersAsProperties();
    }

    public boolean getRenderTypeExpansions() {
        return this.options.getRenderTypeExpansions();
    }

    public boolean getRenderUnabbreviatedType() {
        return this.options.getRenderUnabbreviatedType();
    }

    public boolean getSecondaryConstructorsAsPrimary() {
        return this.options.getSecondaryConstructorsAsPrimary();
    }

    public boolean getStartFromDeclarationKeyword() {
        return this.options.getStartFromDeclarationKeyword();
    }

    public boolean getStartFromName() {
        return this.options.getStartFromName();
    }

    @Override
    public void setStartFromName(boolean bl) {
        this.options.setStartFromName(bl);
    }

    @NotNull
    public RenderingFormat getTextFormat() {
        return this.options.getTextFormat();
    }

    @Override
    public void setTextFormat(@NotNull RenderingFormat renderingFormat) {
        Intrinsics.checkNotNullParameter((Object)renderingFormat, "<set-?>");
        this.options.setTextFormat(renderingFormat);
    }

    @NotNull
    public Function1<KotlinType, KotlinType> getTypeNormalizer() {
        return this.options.getTypeNormalizer();
    }

    public boolean getUninferredTypeParameterAsName() {
        return this.options.getUninferredTypeParameterAsName();
    }

    public boolean getUnitReturnType() {
        return this.options.getUnitReturnType();
    }

    @NotNull
    public DescriptorRenderer.ValueParametersHandler getValueParametersHandler() {
        return this.options.getValueParametersHandler();
    }

    public boolean getVerbose() {
        return this.options.getVerbose();
    }

    @Override
    public void setVerbose(boolean bl) {
        this.options.setVerbose(bl);
    }

    public boolean getWithDefinedIn() {
        return this.options.getWithDefinedIn();
    }

    @Override
    public void setWithDefinedIn(boolean bl) {
        this.options.setWithDefinedIn(bl);
    }

    public boolean getWithSourceFileForTopLevel() {
        return this.options.getWithSourceFileForTopLevel();
    }

    public boolean getWithoutReturnType() {
        return this.options.getWithoutReturnType();
    }

    public boolean getWithoutSuperTypes() {
        return this.options.getWithoutSuperTypes();
    }

    @Override
    public void setWithoutSuperTypes(boolean bl) {
        this.options.setWithoutSuperTypes(bl);
    }

    public boolean getWithoutTypeParameters() {
        return this.options.getWithoutTypeParameters();
    }

    @Override
    public void setWithoutTypeParameters(boolean bl) {
        this.options.setWithoutTypeParameters(bl);
    }

    public static final /* synthetic */ String access$renderConstant(DescriptorRendererImpl $this, ConstantValue value) {
        return $this.renderConstant(value);
    }

    private final class RenderDeclarationDescriptorVisitor
    implements DeclarationDescriptorVisitor<Unit, StringBuilder> {
        @Override
        public void visitValueParameterDescriptor(@NotNull ValueParameterDescriptor descriptor2, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderValueParameter(descriptor2, true, builder, true);
        }

        @Override
        public void visitPropertyDescriptor(@NotNull PropertyDescriptor descriptor2, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderProperty(descriptor2, builder);
        }

        @Override
        public void visitPropertyGetterDescriptor(@NotNull PropertyGetterDescriptor descriptor2, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            this.visitPropertyAccessorDescriptor(descriptor2, builder, "getter");
        }

        @Override
        public void visitPropertySetterDescriptor(@NotNull PropertySetterDescriptor descriptor2, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            this.visitPropertyAccessorDescriptor(descriptor2, builder, "setter");
        }

        private final void visitPropertyAccessorDescriptor(PropertyAccessorDescriptor descriptor2, StringBuilder builder, String kind) {
            switch (DescriptorRendererImpl$RenderDeclarationDescriptorVisitor$WhenMappings.$EnumSwitchMapping$0[DescriptorRendererImpl.this.getPropertyAccessorRenderingPolicy().ordinal()]) {
                case 1: {
                    DescriptorRendererImpl.this.renderAccessorModifiers(descriptor2, builder);
                    builder.append(kind + " for ");
                    PropertyDescriptor propertyDescriptor = descriptor2.getCorrespondingProperty();
                    Intrinsics.checkNotNullExpressionValue(propertyDescriptor, "descriptor.correspondingProperty");
                    DescriptorRendererImpl.this.renderProperty(propertyDescriptor, builder);
                    break;
                }
                case 2: {
                    this.visitFunctionDescriptor((FunctionDescriptor)descriptor2, builder);
                    break;
                }
                case 3: {
                    break;
                }
            }
        }

        @Override
        public void visitFunctionDescriptor(@NotNull FunctionDescriptor descriptor2, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderFunction(descriptor2, builder);
        }

        @Override
        public void visitReceiverParameterDescriptor(@NotNull ReceiverParameterDescriptor descriptor2, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            builder.append(descriptor2.getName());
        }

        @Override
        public void visitConstructorDescriptor(@NotNull ConstructorDescriptor constructorDescriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(constructorDescriptor, "constructorDescriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderConstructor(constructorDescriptor, builder);
        }

        @Override
        public void visitTypeParameterDescriptor(@NotNull TypeParameterDescriptor descriptor2, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderTypeParameter(descriptor2, builder, true);
        }

        @Override
        public void visitPackageFragmentDescriptor(@NotNull PackageFragmentDescriptor descriptor2, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderPackageFragment(descriptor2, builder);
        }

        @Override
        public void visitPackageViewDescriptor(@NotNull PackageViewDescriptor descriptor2, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderPackageView(descriptor2, builder);
        }

        @Override
        public void visitModuleDeclaration(@NotNull ModuleDescriptor descriptor2, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderName(descriptor2, builder, true);
        }

        @Override
        public void visitClassDescriptor(@NotNull ClassDescriptor descriptor2, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderClass(descriptor2, builder);
        }

        @Override
        public void visitTypeAliasDescriptor(@NotNull TypeAliasDescriptor descriptor2, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderTypeAlias(descriptor2, builder);
        }
    }
}

