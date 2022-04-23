/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorNonRootImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.LazyScopeAdapter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.TypeIntersectionScope;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractTypeParameterDescriptor
extends DeclarationDescriptorNonRootImpl
implements TypeParameterDescriptor {
    private final Variance variance;
    private final boolean reified;
    private final int index;
    private final NotNullLazyValue<TypeConstructor> typeConstructor;
    private final NotNullLazyValue<SimpleType> defaultType;
    private final StorageManager storageManager;

    protected AbstractTypeParameterDescriptor(final @NotNull StorageManager storageManager, @NotNull DeclarationDescriptor containingDeclaration, @NotNull Annotations annotations2, final @NotNull Name name, @NotNull Variance variance, boolean isReified, int index, @NotNull SourceElement source, final @NotNull SupertypeLoopChecker supertypeLoopChecker) {
        if (storageManager == null) {
            AbstractTypeParameterDescriptor.$$$reportNull$$$0(0);
        }
        if (containingDeclaration == null) {
            AbstractTypeParameterDescriptor.$$$reportNull$$$0(1);
        }
        if (annotations2 == null) {
            AbstractTypeParameterDescriptor.$$$reportNull$$$0(2);
        }
        if (name == null) {
            AbstractTypeParameterDescriptor.$$$reportNull$$$0(3);
        }
        if (variance == null) {
            AbstractTypeParameterDescriptor.$$$reportNull$$$0(4);
        }
        if (source == null) {
            AbstractTypeParameterDescriptor.$$$reportNull$$$0(5);
        }
        if (supertypeLoopChecker == null) {
            AbstractTypeParameterDescriptor.$$$reportNull$$$0(6);
        }
        super(containingDeclaration, annotations2, name, source);
        this.variance = variance;
        this.reified = isReified;
        this.index = index;
        this.typeConstructor = storageManager.createLazyValue(new Function0<TypeConstructor>(){

            @Override
            public TypeConstructor invoke() {
                return new TypeParameterTypeConstructor(storageManager, supertypeLoopChecker);
            }
        });
        this.defaultType = storageManager.createLazyValue(new Function0<SimpleType>(){

            @Override
            public SimpleType invoke() {
                return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(Annotations.Companion.getEMPTY(), AbstractTypeParameterDescriptor.this.getTypeConstructor(), Collections.emptyList(), false, new LazyScopeAdapter((Function0<? extends MemberScope>)new Function0<MemberScope>(){

                    @Override
                    public MemberScope invoke() {
                        return TypeIntersectionScope.create("Scope for type parameter " + name.asString(), AbstractTypeParameterDescriptor.this.getUpperBounds());
                    }
                }));
            }
        });
        this.storageManager = storageManager;
    }

    protected abstract void reportSupertypeLoopError(@NotNull KotlinType var1);

    @NotNull
    protected abstract List<KotlinType> resolveUpperBounds();

    @Override
    @NotNull
    public Variance getVariance() {
        Variance variance = this.variance;
        if (variance == null) {
            AbstractTypeParameterDescriptor.$$$reportNull$$$0(7);
        }
        return variance;
    }

    @Override
    public boolean isReified() {
        return this.reified;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public boolean isCapturedFromOuterDeclaration() {
        return false;
    }

    @Override
    @NotNull
    public List<KotlinType> getUpperBounds() {
        Collection collection = ((TypeParameterTypeConstructor)this.getTypeConstructor()).getSupertypes();
        if (collection == null) {
            AbstractTypeParameterDescriptor.$$$reportNull$$$0(8);
        }
        return collection;
    }

    @Override
    @NotNull
    public final TypeConstructor getTypeConstructor() {
        TypeConstructor typeConstructor2 = (TypeConstructor)this.typeConstructor.invoke();
        if (typeConstructor2 == null) {
            AbstractTypeParameterDescriptor.$$$reportNull$$$0(9);
        }
        return typeConstructor2;
    }

    @Override
    @NotNull
    public SimpleType getDefaultType() {
        SimpleType simpleType2 = (SimpleType)this.defaultType.invoke();
        if (simpleType2 == null) {
            AbstractTypeParameterDescriptor.$$$reportNull$$$0(10);
        }
        return simpleType2;
    }

    @Override
    @NotNull
    public TypeParameterDescriptor getOriginal() {
        TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor)super.getOriginal();
        if (typeParameterDescriptor == null) {
            AbstractTypeParameterDescriptor.$$$reportNull$$$0(11);
        }
        return typeParameterDescriptor;
    }

    @Override
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
        return visitor2.visitTypeParameterDescriptor(this, data2);
    }

    @Override
    @NotNull
    public StorageManager getStorageManager() {
        StorageManager storageManager = this.storageManager;
        if (storageManager == null) {
            AbstractTypeParameterDescriptor.$$$reportNull$$$0(12);
        }
        return storageManager;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        RuntimeException runtimeException;
        Object[] objectArray;
        Object[] objectArray2;
        int n2;
        String string;
        switch (n) {
            default: {
                string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            }
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "storageManager";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "containingDeclaration";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotations";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "variance";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "source";
                break;
            }
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "supertypeLoopChecker";
                break;
            }
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractTypeParameterDescriptor";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractTypeParameterDescriptor";
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "getVariance";
                break;
            }
            case 8: {
                objectArray = objectArray2;
                objectArray2[1] = "getUpperBounds";
                break;
            }
            case 9: {
                objectArray = objectArray2;
                objectArray2[1] = "getTypeConstructor";
                break;
            }
            case 10: {
                objectArray = objectArray2;
                objectArray2[1] = "getDefaultType";
                break;
            }
            case 11: {
                objectArray = objectArray2;
                objectArray2[1] = "getOriginal";
                break;
            }
            case 12: {
                objectArray = objectArray2;
                objectArray2[1] = "getStorageManager";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: {
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }

    private class TypeParameterTypeConstructor
    extends AbstractTypeConstructor {
        private final SupertypeLoopChecker supertypeLoopChecker;

        public TypeParameterTypeConstructor(StorageManager storageManager, SupertypeLoopChecker supertypeLoopChecker) {
            if (storageManager == null) {
                TypeParameterTypeConstructor.$$$reportNull$$$0(0);
            }
            super(storageManager);
            this.supertypeLoopChecker = supertypeLoopChecker;
        }

        @Override
        @NotNull
        protected Collection<KotlinType> computeSupertypes() {
            List<KotlinType> list = AbstractTypeParameterDescriptor.this.resolveUpperBounds();
            if (list == null) {
                TypeParameterTypeConstructor.$$$reportNull$$$0(1);
            }
            return list;
        }

        @Override
        @NotNull
        public List<TypeParameterDescriptor> getParameters() {
            List<TypeParameterDescriptor> list = Collections.emptyList();
            if (list == null) {
                TypeParameterTypeConstructor.$$$reportNull$$$0(2);
            }
            return list;
        }

        @Override
        public boolean isDenotable() {
            return true;
        }

        @Override
        @NotNull
        public ClassifierDescriptor getDeclarationDescriptor() {
            AbstractTypeParameterDescriptor abstractTypeParameterDescriptor = AbstractTypeParameterDescriptor.this;
            if (abstractTypeParameterDescriptor == null) {
                TypeParameterTypeConstructor.$$$reportNull$$$0(3);
            }
            return abstractTypeParameterDescriptor;
        }

        @Override
        @NotNull
        public KotlinBuiltIns getBuiltIns() {
            KotlinBuiltIns kotlinBuiltIns = DescriptorUtilsKt.getBuiltIns(AbstractTypeParameterDescriptor.this);
            if (kotlinBuiltIns == null) {
                TypeParameterTypeConstructor.$$$reportNull$$$0(4);
            }
            return kotlinBuiltIns;
        }

        public String toString() {
            return AbstractTypeParameterDescriptor.this.getName().toString();
        }

        @Override
        @NotNull
        protected SupertypeLoopChecker getSupertypeLoopChecker() {
            SupertypeLoopChecker supertypeLoopChecker = this.supertypeLoopChecker;
            if (supertypeLoopChecker == null) {
                TypeParameterTypeConstructor.$$$reportNull$$$0(5);
            }
            return supertypeLoopChecker;
        }

        @Override
        protected void reportSupertypeLoopError(@NotNull KotlinType type2) {
            if (type2 == null) {
                TypeParameterTypeConstructor.$$$reportNull$$$0(6);
            }
            AbstractTypeParameterDescriptor.this.reportSupertypeLoopError(type2);
        }

        @Override
        @Nullable
        protected KotlinType defaultSupertypeIfEmpty() {
            return ErrorUtils.createErrorType("Cyclic upper bounds");
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            RuntimeException runtimeException;
            Object[] objectArray;
            Object[] objectArray2;
            int n2;
            String string;
            switch (n) {
                default: {
                    string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                }
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: {
                    string = "@NotNull method %s.%s must not return null";
                    break;
                }
            }
            switch (n) {
                default: {
                    n2 = 3;
                    break;
                }
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "storageManager";
                    break;
                }
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractTypeParameterDescriptor$TypeParameterTypeConstructor";
                    break;
                }
                case 6: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "type";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractTypeParameterDescriptor$TypeParameterTypeConstructor";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[1] = "computeSupertypes";
                    break;
                }
                case 2: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getParameters";
                    break;
                }
                case 3: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getDeclarationDescriptor";
                    break;
                }
                case 4: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getBuiltIns";
                    break;
                }
                case 5: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getSupertypeLoopChecker";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "<init>";
                    break;
                }
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: {
                    break;
                }
                case 6: {
                    objectArray = objectArray;
                    objectArray[2] = "reportSupertypeLoopError";
                    break;
                }
            }
            String string2 = String.format(string, objectArray);
            switch (n) {
                default: {
                    runtimeException = new IllegalArgumentException(string2);
                    break;
                }
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }
}

