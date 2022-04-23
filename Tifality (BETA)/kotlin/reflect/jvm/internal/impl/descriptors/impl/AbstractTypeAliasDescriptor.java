/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithSource;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractTypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorNonRootImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeAliasConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeAliasConstructorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractTypeAliasDescriptor
extends DeclarationDescriptorNonRootImpl
implements TypeAliasDescriptor {
    private List<? extends TypeParameterDescriptor> declaredTypeParametersImpl;
    private final typeConstructor.1 typeConstructor;
    private final Visibility visibilityImpl;

    @NotNull
    protected abstract StorageManager getStorageManager();

    public final void initialize(@NotNull List<? extends TypeParameterDescriptor> declaredTypeParameters) {
        Intrinsics.checkNotNullParameter(declaredTypeParameters, "declaredTypeParameters");
        this.declaredTypeParametersImpl = declaredTypeParameters;
    }

    @Override
    public <R, D> R accept(@NotNull DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
        Intrinsics.checkNotNullParameter(visitor2, "visitor");
        return visitor2.visitTypeAliasDescriptor(this, data2);
    }

    @Override
    public boolean isInner() {
        return TypeUtils.contains(this.getUnderlyingType(), new Function1<UnwrappedType, Boolean>(this){
            final /* synthetic */ AbstractTypeAliasDescriptor this$0;

            /*
             * Enabled aggressive block sorting
             */
            public final Boolean invoke(UnwrappedType type2) {
                boolean bl;
                UnwrappedType unwrappedType = type2;
                Intrinsics.checkNotNullExpressionValue(unwrappedType, "type");
                if (!KotlinTypeKt.isError(unwrappedType)) {
                    AbstractTypeAliasDescriptor abstractTypeAliasDescriptor = this.this$0;
                    boolean bl2 = false;
                    boolean bl3 = false;
                    AbstractTypeAliasDescriptor $this$run = abstractTypeAliasDescriptor;
                    boolean bl4 = false;
                    ClassifierDescriptor constructorDescriptor = type2.getConstructor().getDeclarationDescriptor();
                    boolean bl5 = constructorDescriptor instanceof TypeParameterDescriptor && Intrinsics.areEqual(((TypeParameterDescriptor)constructorDescriptor).getContainingDeclaration(), this.this$0) ^ true;
                    if (bl5) {
                        bl = true;
                        return bl;
                    }
                }
                bl = false;
                return bl;
            }
            {
                this.this$0 = abstractTypeAliasDescriptor;
                super(1);
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Collection<TypeAliasConstructorDescriptor> getTypeAliasConstructors() {
        void $this$mapNotNullTo$iv$iv;
        ClassDescriptor classDescriptor = this.getClassDescriptor();
        if (classDescriptor == null) {
            return CollectionsKt.emptyList();
        }
        ClassDescriptor classDescriptor2 = classDescriptor;
        Collection<ClassConstructorDescriptor> collection = classDescriptor2.getConstructors();
        Intrinsics.checkNotNullExpressionValue(collection, "classDescriptor.constructors");
        Iterable $this$mapNotNull$iv = collection;
        boolean $i$f$mapNotNull = false;
        Iterable iterable = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
        while (iterator2.hasNext()) {
            TypeAliasConstructorDescriptor typeAliasConstructorDescriptor;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator2.next();
            boolean bl = false;
            ClassConstructorDescriptor it = (ClassConstructorDescriptor)element$iv$iv;
            boolean bl2 = false;
            StorageManager storageManager = this.getStorageManager();
            TypeAliasDescriptor typeAliasDescriptor = this;
            ClassConstructorDescriptor classConstructorDescriptor = it;
            Intrinsics.checkNotNullExpressionValue(classConstructorDescriptor, "it");
            if (TypeAliasConstructorDescriptorImpl.Companion.createIfAvailable(storageManager, typeAliasDescriptor, classConstructorDescriptor) == null) continue;
            boolean bl3 = false;
            boolean bl4 = false;
            TypeAliasConstructorDescriptor it$iv$iv = typeAliasConstructorDescriptor;
            boolean bl5 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        List<TypeParameterDescriptor> list = this.declaredTypeParametersImpl;
        if (list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("declaredTypeParametersImpl");
        }
        return list;
    }

    @Override
    @NotNull
    public Modality getModality() {
        return Modality.FINAL;
    }

    @Override
    @NotNull
    public Visibility getVisibility() {
        return this.visibilityImpl;
    }

    @Override
    public boolean isExpect() {
        return false;
    }

    @Override
    public boolean isActual() {
        return false;
    }

    @Override
    public boolean isExternal() {
        return false;
    }

    @Override
    @NotNull
    public TypeConstructor getTypeConstructor() {
        return this.typeConstructor;
    }

    @Override
    @NotNull
    public String toString() {
        return "typealias " + this.getName().asString();
    }

    @Override
    @NotNull
    public TypeAliasDescriptor getOriginal() {
        DeclarationDescriptorWithSource declarationDescriptorWithSource = super.getOriginal();
        if (declarationDescriptorWithSource == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeAliasDescriptor");
        }
        return (TypeAliasDescriptor)declarationDescriptorWithSource;
    }

    @NotNull
    protected abstract List<TypeParameterDescriptor> getTypeConstructorTypeParameters();

    @NotNull
    protected final SimpleType computeDefaultType() {
        ClassifierDescriptor classifierDescriptor = this;
        Object object = this.getClassDescriptor();
        if (object == null || (object = object.getUnsubstitutedMemberScope()) == null) {
            object = MemberScope.Empty.INSTANCE;
        }
        SimpleType simpleType2 = TypeUtils.makeUnsubstitutedType(classifierDescriptor, (MemberScope)object, new Function1<KotlinTypeRefiner, SimpleType>(this){
            final /* synthetic */ AbstractTypeAliasDescriptor this$0;

            public final SimpleType invoke(KotlinTypeRefiner kotlinTypeRefiner) {
                ClassifierDescriptor classifierDescriptor = kotlinTypeRefiner.refineDescriptor(this.this$0);
                return classifierDescriptor != null ? classifierDescriptor.getDefaultType() : null;
            }
            {
                this.this$0 = abstractTypeAliasDescriptor;
                super(1);
            }
        });
        Intrinsics.checkNotNullExpressionValue(simpleType2, "TypeUtils.makeUnsubstitu\u2026s)?.defaultType\n        }");
        return simpleType2;
    }

    public AbstractTypeAliasDescriptor(@NotNull DeclarationDescriptor containingDeclaration, @NotNull Annotations annotations2, @NotNull Name name, @NotNull SourceElement sourceElement, @NotNull Visibility visibilityImpl) {
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(sourceElement, "sourceElement");
        Intrinsics.checkNotNullParameter(visibilityImpl, "visibilityImpl");
        super(containingDeclaration, annotations2, name, sourceElement);
        this.visibilityImpl = visibilityImpl;
        this.typeConstructor = new TypeConstructor(this){
            final /* synthetic */ AbstractTypeAliasDescriptor this$0;

            @NotNull
            public TypeAliasDescriptor getDeclarationDescriptor() {
                return this.this$0;
            }

            @NotNull
            public List<TypeParameterDescriptor> getParameters() {
                return this.this$0.getTypeConstructorTypeParameters();
            }

            @NotNull
            public Collection<KotlinType> getSupertypes() {
                Collection<KotlinType> collection = this.getDeclarationDescriptor().getUnderlyingType().getConstructor().getSupertypes();
                Intrinsics.checkNotNullExpressionValue(collection, "declarationDescriptor.un\u2026pe.constructor.supertypes");
                return collection;
            }

            public boolean isDenotable() {
                return true;
            }

            @NotNull
            public KotlinBuiltIns getBuiltIns() {
                return DescriptorUtilsKt.getBuiltIns(this.getDeclarationDescriptor());
            }

            @NotNull
            public String toString() {
                return "[typealias " + this.getDeclarationDescriptor().getName().asString() + ']';
            }

            @NotNull
            public TypeConstructor refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
                Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
                return this;
            }
            {
                this.this$0 = this$0;
            }
        };
    }
}

