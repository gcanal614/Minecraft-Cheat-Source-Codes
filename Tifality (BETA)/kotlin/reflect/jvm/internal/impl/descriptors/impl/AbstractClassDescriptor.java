/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.LazyClassReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.LazySubstitutingClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleAwareClassDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.InnerClassesScopeWrapper;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.SubstitutingScope;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractClassDescriptor
extends ModuleAwareClassDescriptor {
    private final Name name;
    protected final NotNullLazyValue<SimpleType> defaultType;
    private final NotNullLazyValue<MemberScope> unsubstitutedInnerClassesScope;
    private final NotNullLazyValue<ReceiverParameterDescriptor> thisAsReceiverParameter;

    public AbstractClassDescriptor(@NotNull StorageManager storageManager, @NotNull Name name) {
        if (storageManager == null) {
            AbstractClassDescriptor.$$$reportNull$$$0(0);
        }
        if (name == null) {
            AbstractClassDescriptor.$$$reportNull$$$0(1);
        }
        this.name = name;
        this.defaultType = storageManager.createLazyValue(new Function0<SimpleType>(){

            @Override
            public SimpleType invoke() {
                return TypeUtils.makeUnsubstitutedType(AbstractClassDescriptor.this, AbstractClassDescriptor.this.getUnsubstitutedMemberScope(), new Function1<KotlinTypeRefiner, SimpleType>(){

                    @Override
                    public SimpleType invoke(KotlinTypeRefiner kotlinTypeRefiner) {
                        ClassifierDescriptor descriptor2 = kotlinTypeRefiner.refineDescriptor(AbstractClassDescriptor.this);
                        if (descriptor2 == null) {
                            return (SimpleType)AbstractClassDescriptor.this.defaultType.invoke();
                        }
                        if (descriptor2 instanceof TypeAliasDescriptor) {
                            return KotlinTypeFactory.computeExpandedType((TypeAliasDescriptor)descriptor2, TypeUtils.getDefaultTypeProjections(descriptor2.getTypeConstructor().getParameters()));
                        }
                        if (descriptor2 instanceof ModuleAwareClassDescriptor) {
                            TypeConstructor refinedConstructor = descriptor2.getTypeConstructor().refine(kotlinTypeRefiner);
                            return TypeUtils.makeUnsubstitutedType(refinedConstructor, ((ModuleAwareClassDescriptor)descriptor2).getUnsubstitutedMemberScope(kotlinTypeRefiner), (Function1<KotlinTypeRefiner, SimpleType>)this);
                        }
                        return descriptor2.getDefaultType();
                    }
                });
            }
        });
        this.unsubstitutedInnerClassesScope = storageManager.createLazyValue(new Function0<MemberScope>(){

            @Override
            public MemberScope invoke() {
                return new InnerClassesScopeWrapper(AbstractClassDescriptor.this.getUnsubstitutedMemberScope());
            }
        });
        this.thisAsReceiverParameter = storageManager.createLazyValue(new Function0<ReceiverParameterDescriptor>(){

            @Override
            public ReceiverParameterDescriptor invoke() {
                return new LazyClassReceiverParameterDescriptor(AbstractClassDescriptor.this);
            }
        });
    }

    @Override
    @NotNull
    public Name getName() {
        Name name = this.name;
        if (name == null) {
            AbstractClassDescriptor.$$$reportNull$$$0(2);
        }
        return name;
    }

    @Override
    @NotNull
    public ClassDescriptor getOriginal() {
        AbstractClassDescriptor abstractClassDescriptor = this;
        if (abstractClassDescriptor == null) {
            AbstractClassDescriptor.$$$reportNull$$$0(3);
        }
        return abstractClassDescriptor;
    }

    @Override
    @NotNull
    public MemberScope getUnsubstitutedInnerClassesScope() {
        MemberScope memberScope2 = (MemberScope)this.unsubstitutedInnerClassesScope.invoke();
        if (memberScope2 == null) {
            AbstractClassDescriptor.$$$reportNull$$$0(4);
        }
        return memberScope2;
    }

    @Override
    @NotNull
    public ReceiverParameterDescriptor getThisAsReceiverParameter() {
        ReceiverParameterDescriptor receiverParameterDescriptor = (ReceiverParameterDescriptor)this.thisAsReceiverParameter.invoke();
        if (receiverParameterDescriptor == null) {
            AbstractClassDescriptor.$$$reportNull$$$0(5);
        }
        return receiverParameterDescriptor;
    }

    @Override
    @NotNull
    public MemberScope getMemberScope(@NotNull TypeSubstitution typeSubstitution, @NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        if (typeSubstitution == null) {
            AbstractClassDescriptor.$$$reportNull$$$0(9);
        }
        if (kotlinTypeRefiner == null) {
            AbstractClassDescriptor.$$$reportNull$$$0(10);
        }
        if (typeSubstitution.isEmpty()) {
            MemberScope memberScope2 = this.getUnsubstitutedMemberScope(kotlinTypeRefiner);
            if (memberScope2 == null) {
                AbstractClassDescriptor.$$$reportNull$$$0(11);
            }
            return memberScope2;
        }
        TypeSubstitutor substitutor = TypeSubstitutor.create(typeSubstitution);
        return new SubstitutingScope(this.getUnsubstitutedMemberScope(kotlinTypeRefiner), substitutor);
    }

    @Override
    @NotNull
    public MemberScope getMemberScope(@NotNull TypeSubstitution typeSubstitution) {
        if (typeSubstitution == null) {
            AbstractClassDescriptor.$$$reportNull$$$0(14);
        }
        MemberScope memberScope2 = this.getMemberScope(typeSubstitution, DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtils.getContainingModule(this)));
        if (memberScope2 == null) {
            AbstractClassDescriptor.$$$reportNull$$$0(15);
        }
        return memberScope2;
    }

    @Override
    @NotNull
    public MemberScope getUnsubstitutedMemberScope() {
        MemberScope memberScope2 = this.getUnsubstitutedMemberScope(DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtils.getContainingModule(this)));
        if (memberScope2 == null) {
            AbstractClassDescriptor.$$$reportNull$$$0(16);
        }
        return memberScope2;
    }

    @Override
    @NotNull
    public ClassDescriptor substitute(@NotNull TypeSubstitutor substitutor) {
        if (substitutor == null) {
            AbstractClassDescriptor.$$$reportNull$$$0(17);
        }
        if (substitutor.isEmpty()) {
            AbstractClassDescriptor abstractClassDescriptor = this;
            if (abstractClassDescriptor == null) {
                AbstractClassDescriptor.$$$reportNull$$$0(18);
            }
            return abstractClassDescriptor;
        }
        return new LazySubstitutingClassDescriptor(this, substitutor);
    }

    @Override
    @NotNull
    public SimpleType getDefaultType() {
        SimpleType simpleType2 = (SimpleType)this.defaultType.invoke();
        if (simpleType2 == null) {
            AbstractClassDescriptor.$$$reportNull$$$0(19);
        }
        return simpleType2;
    }

    @Override
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
        return visitor2.visitClassDescriptor(this, data2);
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
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 8: 
            case 11: 
            case 13: 
            case 15: 
            case 16: 
            case 18: 
            case 19: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 8: 
            case 11: 
            case 13: 
            case 15: 
            case 16: 
            case 18: 
            case 19: {
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
                objectArray3[0] = "name";
                break;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 8: 
            case 11: 
            case 13: 
            case 15: 
            case 16: 
            case 18: 
            case 19: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractClassDescriptor";
                break;
            }
            case 6: 
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeArguments";
                break;
            }
            case 7: 
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlinTypeRefiner";
                break;
            }
            case 9: 
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeSubstitution";
                break;
            }
            case 17: {
                objectArray2 = objectArray3;
                objectArray3[0] = "substitutor";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractClassDescriptor";
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[1] = "getName";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = "getOriginal";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "getUnsubstitutedInnerClassesScope";
                break;
            }
            case 5: {
                objectArray = objectArray2;
                objectArray2[1] = "getThisAsReceiverParameter";
                break;
            }
            case 8: 
            case 11: 
            case 13: 
            case 15: {
                objectArray = objectArray2;
                objectArray2[1] = "getMemberScope";
                break;
            }
            case 16: {
                objectArray = objectArray2;
                objectArray2[1] = "getUnsubstitutedMemberScope";
                break;
            }
            case 18: {
                objectArray = objectArray2;
                objectArray2[1] = "substitute";
                break;
            }
            case 19: {
                objectArray = objectArray2;
                objectArray2[1] = "getDefaultType";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 8: 
            case 11: 
            case 13: 
            case 15: 
            case 16: 
            case 18: 
            case 19: {
                break;
            }
            case 6: 
            case 7: 
            case 9: 
            case 10: 
            case 12: 
            case 14: {
                objectArray = objectArray;
                objectArray[2] = "getMemberScope";
                break;
            }
            case 17: {
                objectArray = objectArray;
                objectArray[2] = "substitute";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 8: 
            case 11: 
            case 13: 
            case 15: 
            case 16: 
            case 18: 
            case 19: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

