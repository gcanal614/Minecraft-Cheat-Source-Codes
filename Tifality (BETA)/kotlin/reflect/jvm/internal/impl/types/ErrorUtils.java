/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.builtins.DefaultBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassConstructorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.types.ErrorType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorSimpleFunctionDescriptorImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ErrorUtils {
    private static final ModuleDescriptor ERROR_MODULE = new ModuleDescriptor(){

        @Override
        @Nullable
        public <T> T getCapability(@NotNull ModuleDescriptor.Capability<T> capability) {
            if (capability == null) {
                1.$$$reportNull$$$0(0);
            }
            return null;
        }

        @Override
        @NotNull
        public Annotations getAnnotations() {
            Annotations annotations2 = Annotations.Companion.getEMPTY();
            if (annotations2 == null) {
                1.$$$reportNull$$$0(1);
            }
            return annotations2;
        }

        @Override
        @NotNull
        public Collection<FqName> getSubPackagesOf(@NotNull FqName fqName2, @NotNull Function1<? super Name, Boolean> nameFilter) {
            if (fqName2 == null) {
                1.$$$reportNull$$$0(2);
            }
            if (nameFilter == null) {
                1.$$$reportNull$$$0(3);
            }
            List<FqName> list = CollectionsKt.emptyList();
            if (list == null) {
                1.$$$reportNull$$$0(4);
            }
            return list;
        }

        @Override
        @NotNull
        public Name getName() {
            Name name = Name.special("<ERROR MODULE>");
            if (name == null) {
                1.$$$reportNull$$$0(5);
            }
            return name;
        }

        @Override
        @NotNull
        public PackageViewDescriptor getPackage(@NotNull FqName fqName2) {
            if (fqName2 == null) {
                1.$$$reportNull$$$0(7);
            }
            throw new IllegalStateException("Should not be called!");
        }

        @Override
        @NotNull
        public List<ModuleDescriptor> getExpectedByModules() {
            List<ModuleDescriptor> list = CollectionsKt.emptyList();
            if (list == null) {
                1.$$$reportNull$$$0(9);
            }
            return list;
        }

        @Override
        public <R, D> R accept(@NotNull DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
            if (visitor2 == null) {
                1.$$$reportNull$$$0(10);
            }
            return null;
        }

        @Override
        public boolean shouldSeeInternalsOf(@NotNull ModuleDescriptor targetModule) {
            if (targetModule == null) {
                1.$$$reportNull$$$0(11);
            }
            return false;
        }

        @Override
        @NotNull
        public DeclarationDescriptor getOriginal() {
            1 v0 = this;
            if (v0 == null) {
                1.$$$reportNull$$$0(12);
            }
            return v0;
        }

        @Override
        @Nullable
        public DeclarationDescriptor getContainingDeclaration() {
            return null;
        }

        @Override
        @NotNull
        public KotlinBuiltIns getBuiltIns() {
            DefaultBuiltIns defaultBuiltIns = DefaultBuiltIns.getInstance();
            if (defaultBuiltIns == null) {
                1.$$$reportNull$$$0(13);
            }
            return defaultBuiltIns;
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
                case 4: 
                case 5: 
                case 6: 
                case 8: 
                case 9: 
                case 12: 
                case 13: {
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
                case 4: 
                case 5: 
                case 6: 
                case 8: 
                case 9: 
                case 12: 
                case 13: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "capability";
                    break;
                }
                case 1: 
                case 4: 
                case 5: 
                case 6: 
                case 8: 
                case 9: 
                case 12: 
                case 13: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$1";
                    break;
                }
                case 2: 
                case 7: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "fqName";
                    break;
                }
                case 3: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "nameFilter";
                    break;
                }
                case 10: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "visitor";
                    break;
                }
                case 11: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "targetModule";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$1";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getAnnotations";
                    break;
                }
                case 4: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getSubPackagesOf";
                    break;
                }
                case 5: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getName";
                    break;
                }
                case 6: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getStableName";
                    break;
                }
                case 8: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getAllDependencyModules";
                    break;
                }
                case 9: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getExpectedByModules";
                    break;
                }
                case 12: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getOriginal";
                    break;
                }
                case 13: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getBuiltIns";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "getCapability";
                    break;
                }
                case 1: 
                case 4: 
                case 5: 
                case 6: 
                case 8: 
                case 9: 
                case 12: 
                case 13: {
                    break;
                }
                case 2: 
                case 3: {
                    objectArray = objectArray;
                    objectArray[2] = "getSubPackagesOf";
                    break;
                }
                case 7: {
                    objectArray = objectArray;
                    objectArray[2] = "getPackage";
                    break;
                }
                case 10: {
                    objectArray = objectArray;
                    objectArray[2] = "accept";
                    break;
                }
                case 11: {
                    objectArray = objectArray;
                    objectArray[2] = "shouldSeeInternalsOf";
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
                case 4: 
                case 5: 
                case 6: 
                case 8: 
                case 9: 
                case 12: 
                case 13: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    };
    private static final ErrorClassDescriptor ERROR_CLASS = new ErrorClassDescriptor(Name.special("<ERROR CLASS>"));
    public static final SimpleType ERROR_TYPE_FOR_LOOP_IN_SUPERTYPES = ErrorUtils.createErrorType("<LOOP IN SUPERTYPES>");
    private static final KotlinType ERROR_PROPERTY_TYPE = ErrorUtils.createErrorType("<ERROR PROPERTY TYPE>");
    private static final PropertyDescriptor ERROR_PROPERTY = ErrorUtils.createErrorProperty();
    private static final Set<PropertyDescriptor> ERROR_PROPERTY_GROUP = Collections.singleton(ERROR_PROPERTY);

    @NotNull
    public static ClassDescriptor createErrorClass(@NotNull String debugMessage) {
        if (debugMessage == null) {
            ErrorUtils.$$$reportNull$$$0(1);
        }
        return new ErrorClassDescriptor(Name.special("<ERROR CLASS: " + debugMessage + ">"));
    }

    @NotNull
    public static MemberScope createErrorScope(@NotNull String debugMessage) {
        if (debugMessage == null) {
            ErrorUtils.$$$reportNull$$$0(2);
        }
        return ErrorUtils.createErrorScope(debugMessage, false);
    }

    @NotNull
    public static MemberScope createErrorScope(@NotNull String debugMessage, boolean throwExceptions) {
        if (debugMessage == null) {
            ErrorUtils.$$$reportNull$$$0(3);
        }
        if (throwExceptions) {
            return new ThrowingScope(debugMessage);
        }
        return new ErrorScope(debugMessage);
    }

    @NotNull
    private static PropertyDescriptorImpl createErrorProperty() {
        PropertyDescriptorImpl descriptor2 = PropertyDescriptorImpl.create(ERROR_CLASS, Annotations.Companion.getEMPTY(), Modality.OPEN, Visibilities.PUBLIC, true, Name.special("<ERROR PROPERTY>"), CallableMemberDescriptor.Kind.DECLARATION, SourceElement.NO_SOURCE, false, false, false, false, false, false);
        descriptor2.setType(ERROR_PROPERTY_TYPE, Collections.emptyList(), null, null);
        PropertyDescriptorImpl propertyDescriptorImpl = descriptor2;
        if (propertyDescriptorImpl == null) {
            ErrorUtils.$$$reportNull$$$0(4);
        }
        return propertyDescriptorImpl;
    }

    @NotNull
    private static SimpleFunctionDescriptor createErrorFunction(@NotNull ErrorScope ownerScope) {
        if (ownerScope == null) {
            ErrorUtils.$$$reportNull$$$0(5);
        }
        ErrorSimpleFunctionDescriptorImpl function = new ErrorSimpleFunctionDescriptorImpl(ERROR_CLASS, ownerScope);
        function.initialize((ReceiverParameterDescriptor)null, (ReceiverParameterDescriptor)null, Collections.emptyList(), Collections.emptyList(), (KotlinType)ErrorUtils.createErrorType("<ERROR FUNCTION RETURN TYPE>"), Modality.OPEN, Visibilities.PUBLIC);
        ErrorSimpleFunctionDescriptorImpl errorSimpleFunctionDescriptorImpl = function;
        if (errorSimpleFunctionDescriptorImpl == null) {
            ErrorUtils.$$$reportNull$$$0(6);
        }
        return errorSimpleFunctionDescriptorImpl;
    }

    @NotNull
    public static SimpleType createErrorType(@NotNull String debugMessage) {
        if (debugMessage == null) {
            ErrorUtils.$$$reportNull$$$0(7);
        }
        return ErrorUtils.createErrorTypeWithArguments(debugMessage, Collections.<TypeProjection>emptyList());
    }

    @NotNull
    public static SimpleType createErrorTypeWithCustomDebugName(@NotNull String debugName) {
        if (debugName == null) {
            ErrorUtils.$$$reportNull$$$0(8);
        }
        return ErrorUtils.createErrorTypeWithCustomConstructor(debugName, ErrorUtils.createErrorTypeConstructorWithCustomDebugName(debugName));
    }

    @NotNull
    public static SimpleType createErrorTypeWithCustomConstructor(@NotNull String debugName, @NotNull TypeConstructor typeConstructor2) {
        if (debugName == null) {
            ErrorUtils.$$$reportNull$$$0(9);
        }
        if (typeConstructor2 == null) {
            ErrorUtils.$$$reportNull$$$0(10);
        }
        return new ErrorType(typeConstructor2, ErrorUtils.createErrorScope(debugName));
    }

    @NotNull
    public static SimpleType createErrorTypeWithArguments(@NotNull String debugMessage, @NotNull List<TypeProjection> arguments2) {
        if (debugMessage == null) {
            ErrorUtils.$$$reportNull$$$0(11);
        }
        if (arguments2 == null) {
            ErrorUtils.$$$reportNull$$$0(12);
        }
        return new ErrorType(ErrorUtils.createErrorTypeConstructor(debugMessage), ErrorUtils.createErrorScope(debugMessage), arguments2, false);
    }

    @NotNull
    public static TypeConstructor createErrorTypeConstructor(@NotNull String debugMessage) {
        if (debugMessage == null) {
            ErrorUtils.$$$reportNull$$$0(15);
        }
        return ErrorUtils.createErrorTypeConstructorWithCustomDebugName("[ERROR : " + debugMessage + "]", ERROR_CLASS);
    }

    @NotNull
    public static TypeConstructor createErrorTypeConstructorWithCustomDebugName(@NotNull String debugName) {
        if (debugName == null) {
            ErrorUtils.$$$reportNull$$$0(16);
        }
        return ErrorUtils.createErrorTypeConstructorWithCustomDebugName(debugName, ERROR_CLASS);
    }

    @NotNull
    private static TypeConstructor createErrorTypeConstructorWithCustomDebugName(final @NotNull String debugName, final @NotNull ErrorClassDescriptor errorClass) {
        if (debugName == null) {
            ErrorUtils.$$$reportNull$$$0(17);
        }
        if (errorClass == null) {
            ErrorUtils.$$$reportNull$$$0(18);
        }
        return new TypeConstructor(){

            @Override
            @NotNull
            public List<TypeParameterDescriptor> getParameters() {
                List<TypeParameterDescriptor> list = CollectionsKt.emptyList();
                if (list == null) {
                    2.$$$reportNull$$$0(0);
                }
                return list;
            }

            @Override
            @NotNull
            public Collection<KotlinType> getSupertypes() {
                List<KotlinType> list = CollectionsKt.emptyList();
                if (list == null) {
                    2.$$$reportNull$$$0(1);
                }
                return list;
            }

            @Override
            public boolean isDenotable() {
                return false;
            }

            @Override
            @Nullable
            public ClassifierDescriptor getDeclarationDescriptor() {
                return errorClass;
            }

            @Override
            @NotNull
            public KotlinBuiltIns getBuiltIns() {
                DefaultBuiltIns defaultBuiltIns = DefaultBuiltIns.getInstance();
                if (defaultBuiltIns == null) {
                    2.$$$reportNull$$$0(2);
                }
                return defaultBuiltIns;
            }

            public String toString() {
                return debugName;
            }

            @Override
            @NotNull
            public TypeConstructor refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
                if (kotlinTypeRefiner == null) {
                    2.$$$reportNull$$$0(3);
                }
                2 v0 = this;
                if (v0 == null) {
                    2.$$$reportNull$$$0(4);
                }
                return v0;
            }

            private static /* synthetic */ void $$$reportNull$$$0(int n) {
                RuntimeException runtimeException;
                Object[] objectArray;
                Object[] objectArray2;
                int n2;
                String string;
                switch (n) {
                    default: {
                        string = "@NotNull method %s.%s must not return null";
                        break;
                    }
                    case 3: {
                        string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                        break;
                    }
                }
                switch (n) {
                    default: {
                        n2 = 2;
                        break;
                    }
                    case 3: {
                        n2 = 3;
                        break;
                    }
                }
                Object[] objectArray3 = new Object[n2];
                switch (n) {
                    default: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$2";
                        break;
                    }
                    case 3: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "kotlinTypeRefiner";
                        break;
                    }
                }
                switch (n) {
                    default: {
                        objectArray = objectArray2;
                        objectArray2[1] = "getParameters";
                        break;
                    }
                    case 1: {
                        objectArray = objectArray2;
                        objectArray2[1] = "getSupertypes";
                        break;
                    }
                    case 2: {
                        objectArray = objectArray2;
                        objectArray2[1] = "getBuiltIns";
                        break;
                    }
                    case 3: {
                        objectArray = objectArray2;
                        objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$2";
                        break;
                    }
                    case 4: {
                        objectArray = objectArray2;
                        objectArray2[1] = "refine";
                        break;
                    }
                }
                switch (n) {
                    default: {
                        break;
                    }
                    case 3: {
                        objectArray = objectArray;
                        objectArray[2] = "refine";
                        break;
                    }
                }
                String string2 = String.format(string, objectArray);
                switch (n) {
                    default: {
                        runtimeException = new IllegalStateException(string2);
                        break;
                    }
                    case 3: {
                        runtimeException = new IllegalArgumentException(string2);
                        break;
                    }
                }
                throw runtimeException;
            }
        };
    }

    public static boolean isError(@Nullable DeclarationDescriptor candidate) {
        if (candidate == null) {
            return false;
        }
        return ErrorUtils.isErrorClass(candidate) || ErrorUtils.isErrorClass(candidate.getContainingDeclaration()) || candidate == ERROR_MODULE;
    }

    private static boolean isErrorClass(@Nullable DeclarationDescriptor candidate) {
        return candidate instanceof ErrorClassDescriptor;
    }

    @NotNull
    public static ModuleDescriptor getErrorModule() {
        ModuleDescriptor moduleDescriptor = ERROR_MODULE;
        if (moduleDescriptor == null) {
            ErrorUtils.$$$reportNull$$$0(19);
        }
        return moduleDescriptor;
    }

    public static boolean isUninferredParameter(@Nullable KotlinType type2) {
        return type2 != null && type2.getConstructor() instanceof UninferredParameterTypeConstructor;
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
            case 4: 
            case 6: 
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
            case 4: 
            case 6: 
            case 19: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "function";
                break;
            }
            case 1: 
            case 2: 
            case 3: 
            case 7: 
            case 11: 
            case 15: {
                objectArray2 = objectArray3;
                objectArray3[0] = "debugMessage";
                break;
            }
            case 4: 
            case 6: 
            case 19: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "ownerScope";
                break;
            }
            case 8: 
            case 9: 
            case 16: 
            case 17: {
                objectArray2 = objectArray3;
                objectArray3[0] = "debugName";
                break;
            }
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeConstructor";
                break;
            }
            case 12: 
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "arguments";
                break;
            }
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "presentableName";
                break;
            }
            case 18: {
                objectArray2 = objectArray3;
                objectArray3[0] = "errorClass";
                break;
            }
            case 20: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeParameterDescriptor";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "createErrorProperty";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "createErrorFunction";
                break;
            }
            case 19: {
                objectArray = objectArray2;
                objectArray2[1] = "getErrorModule";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "containsErrorTypeInParameters";
                break;
            }
            case 1: {
                objectArray = objectArray;
                objectArray[2] = "createErrorClass";
                break;
            }
            case 2: 
            case 3: {
                objectArray = objectArray;
                objectArray[2] = "createErrorScope";
                break;
            }
            case 4: 
            case 6: 
            case 19: {
                break;
            }
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "createErrorFunction";
                break;
            }
            case 7: {
                objectArray = objectArray;
                objectArray[2] = "createErrorType";
                break;
            }
            case 8: {
                objectArray = objectArray;
                objectArray[2] = "createErrorTypeWithCustomDebugName";
                break;
            }
            case 9: 
            case 10: {
                objectArray = objectArray;
                objectArray[2] = "createErrorTypeWithCustomConstructor";
                break;
            }
            case 11: 
            case 12: {
                objectArray = objectArray;
                objectArray[2] = "createErrorTypeWithArguments";
                break;
            }
            case 13: 
            case 14: {
                objectArray = objectArray;
                objectArray[2] = "createUnresolvedType";
                break;
            }
            case 15: {
                objectArray = objectArray;
                objectArray[2] = "createErrorTypeConstructor";
                break;
            }
            case 16: 
            case 17: 
            case 18: {
                objectArray = objectArray;
                objectArray[2] = "createErrorTypeConstructorWithCustomDebugName";
                break;
            }
            case 20: {
                objectArray = objectArray;
                objectArray[2] = "createUninferredParameterType";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 4: 
            case 6: 
            case 19: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }

    public static class UninferredParameterTypeConstructor
    implements TypeConstructor {
        private final TypeParameterDescriptor typeParameterDescriptor;
        private final TypeConstructor errorTypeConstructor;

        @NotNull
        public TypeParameterDescriptor getTypeParameterDescriptor() {
            TypeParameterDescriptor typeParameterDescriptor = this.typeParameterDescriptor;
            if (typeParameterDescriptor == null) {
                UninferredParameterTypeConstructor.$$$reportNull$$$0(1);
            }
            return typeParameterDescriptor;
        }

        @Override
        @NotNull
        public List<TypeParameterDescriptor> getParameters() {
            List<TypeParameterDescriptor> list = this.errorTypeConstructor.getParameters();
            if (list == null) {
                UninferredParameterTypeConstructor.$$$reportNull$$$0(2);
            }
            return list;
        }

        @Override
        @NotNull
        public Collection<KotlinType> getSupertypes() {
            Collection<KotlinType> collection = this.errorTypeConstructor.getSupertypes();
            if (collection == null) {
                UninferredParameterTypeConstructor.$$$reportNull$$$0(3);
            }
            return collection;
        }

        @Override
        public boolean isDenotable() {
            return this.errorTypeConstructor.isDenotable();
        }

        @Override
        @Nullable
        public ClassifierDescriptor getDeclarationDescriptor() {
            return this.errorTypeConstructor.getDeclarationDescriptor();
        }

        @Override
        @NotNull
        public KotlinBuiltIns getBuiltIns() {
            KotlinBuiltIns kotlinBuiltIns = DescriptorUtilsKt.getBuiltIns(this.typeParameterDescriptor);
            if (kotlinBuiltIns == null) {
                UninferredParameterTypeConstructor.$$$reportNull$$$0(4);
            }
            return kotlinBuiltIns;
        }

        @Override
        @NotNull
        public TypeConstructor refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
            if (kotlinTypeRefiner == null) {
                UninferredParameterTypeConstructor.$$$reportNull$$$0(5);
            }
            UninferredParameterTypeConstructor uninferredParameterTypeConstructor = this;
            if (uninferredParameterTypeConstructor == null) {
                UninferredParameterTypeConstructor.$$$reportNull$$$0(6);
            }
            return uninferredParameterTypeConstructor;
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
                case 6: {
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
                case 6: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "descriptor";
                    break;
                }
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 6: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$UninferredParameterTypeConstructor";
                    break;
                }
                case 5: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlinTypeRefiner";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$UninferredParameterTypeConstructor";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getTypeParameterDescriptor";
                    break;
                }
                case 2: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getParameters";
                    break;
                }
                case 3: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getSupertypes";
                    break;
                }
                case 4: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getBuiltIns";
                    break;
                }
                case 6: {
                    objectArray = objectArray2;
                    objectArray2[1] = "refine";
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
                case 6: {
                    break;
                }
                case 5: {
                    objectArray = objectArray;
                    objectArray[2] = "refine";
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
                case 6: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }

    private static class ErrorClassDescriptor
    extends ClassDescriptorImpl {
        public ErrorClassDescriptor(@NotNull Name name) {
            if (name == null) {
                ErrorClassDescriptor.$$$reportNull$$$0(0);
            }
            super(ErrorUtils.getErrorModule(), name, Modality.OPEN, ClassKind.CLASS, Collections.<KotlinType>emptyList(), SourceElement.NO_SOURCE, false, LockBasedStorageManager.NO_LOCKS);
            ClassConstructorDescriptorImpl errorConstructor = ClassConstructorDescriptorImpl.create(this, Annotations.Companion.getEMPTY(), true, SourceElement.NO_SOURCE);
            errorConstructor.initialize(Collections.<ValueParameterDescriptor>emptyList(), Visibilities.INTERNAL);
            MemberScope memberScope2 = ErrorUtils.createErrorScope(this.getName().asString());
            errorConstructor.setReturnType(new ErrorType(ErrorUtils.createErrorTypeConstructorWithCustomDebugName("<ERROR>", this), memberScope2));
            this.initialize(memberScope2, Collections.singleton(errorConstructor), errorConstructor);
        }

        @Override
        @NotNull
        public ClassDescriptor substitute(@NotNull TypeSubstitutor substitutor) {
            if (substitutor == null) {
                ErrorClassDescriptor.$$$reportNull$$$0(1);
            }
            ErrorClassDescriptor errorClassDescriptor = this;
            if (errorClassDescriptor == null) {
                ErrorClassDescriptor.$$$reportNull$$$0(2);
            }
            return errorClassDescriptor;
        }

        @Override
        public String toString() {
            return this.getName().asString();
        }

        @Override
        @NotNull
        public MemberScope getMemberScope(@NotNull TypeSubstitution typeSubstitution, @NotNull KotlinTypeRefiner kotlinTypeRefiner) {
            if (typeSubstitution == null) {
                ErrorClassDescriptor.$$$reportNull$$$0(6);
            }
            if (kotlinTypeRefiner == null) {
                ErrorClassDescriptor.$$$reportNull$$$0(7);
            }
            MemberScope memberScope2 = ErrorUtils.createErrorScope("Error scope for class " + this.getName() + " with arguments: " + typeSubstitution);
            if (memberScope2 == null) {
                ErrorClassDescriptor.$$$reportNull$$$0(8);
            }
            return memberScope2;
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
                case 5: 
                case 8: {
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
                case 5: 
                case 8: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "name";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "substitutor";
                    break;
                }
                case 2: 
                case 5: 
                case 8: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$ErrorClassDescriptor";
                    break;
                }
                case 3: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "typeArguments";
                    break;
                }
                case 4: 
                case 7: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlinTypeRefiner";
                    break;
                }
                case 6: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "typeSubstitution";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$ErrorClassDescriptor";
                    break;
                }
                case 2: {
                    objectArray = objectArray2;
                    objectArray2[1] = "substitute";
                    break;
                }
                case 5: 
                case 8: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getMemberScope";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "<init>";
                    break;
                }
                case 1: {
                    objectArray = objectArray;
                    objectArray[2] = "substitute";
                    break;
                }
                case 2: 
                case 5: 
                case 8: {
                    break;
                }
                case 3: 
                case 4: 
                case 6: 
                case 7: {
                    objectArray = objectArray;
                    objectArray[2] = "getMemberScope";
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
                case 5: 
                case 8: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }

    private static class ThrowingScope
    implements MemberScope {
        private final String debugMessage;

        private ThrowingScope(@NotNull String message) {
            if (message == null) {
                ThrowingScope.$$$reportNull$$$0(0);
            }
            this.debugMessage = message;
        }

        @Override
        @Nullable
        public ClassifierDescriptor getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
            if (name == null) {
                ThrowingScope.$$$reportNull$$$0(1);
            }
            if (location == null) {
                ThrowingScope.$$$reportNull$$$0(2);
            }
            throw new IllegalStateException(this.debugMessage + ", required name: " + name);
        }

        @Override
        @NotNull
        public Collection<? extends PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
            if (name == null) {
                ThrowingScope.$$$reportNull$$$0(5);
            }
            if (location == null) {
                ThrowingScope.$$$reportNull$$$0(6);
            }
            throw new IllegalStateException(this.debugMessage + ", required name: " + name);
        }

        @Override
        @NotNull
        public Collection<? extends SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
            if (name == null) {
                ThrowingScope.$$$reportNull$$$0(7);
            }
            if (location == null) {
                ThrowingScope.$$$reportNull$$$0(8);
            }
            throw new IllegalStateException(this.debugMessage + ", required name: " + name);
        }

        @Override
        @NotNull
        public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
            if (kindFilter == null) {
                ThrowingScope.$$$reportNull$$$0(9);
            }
            if (nameFilter == null) {
                ThrowingScope.$$$reportNull$$$0(10);
            }
            throw new IllegalStateException(this.debugMessage);
        }

        @Override
        @NotNull
        public Set<Name> getFunctionNames() {
            throw new IllegalStateException();
        }

        @Override
        @NotNull
        public Set<Name> getVariableNames() {
            throw new IllegalStateException();
        }

        @Override
        public Set<Name> getClassifierNames() {
            throw new IllegalStateException();
        }

        @Override
        public void recordLookup(@NotNull Name name, @NotNull LookupLocation location) {
            if (name == null) {
                ThrowingScope.$$$reportNull$$$0(11);
            }
            if (location == null) {
                ThrowingScope.$$$reportNull$$$0(12);
            }
            throw new IllegalStateException();
        }

        public String toString() {
            return "ThrowingScope{" + this.debugMessage + '}';
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2;
            Object[] objectArray3 = new Object[3];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "message";
                    break;
                }
                case 1: 
                case 3: 
                case 5: 
                case 7: 
                case 11: 
                case 13: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "name";
                    break;
                }
                case 2: 
                case 4: 
                case 6: 
                case 8: 
                case 12: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "location";
                    break;
                }
                case 9: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kindFilter";
                    break;
                }
                case 10: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "nameFilter";
                    break;
                }
                case 14: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "p";
                    break;
                }
            }
            objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$ThrowingScope";
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[2] = "<init>";
                    break;
                }
                case 1: 
                case 2: {
                    objectArray = objectArray2;
                    objectArray2[2] = "getContributedClassifier";
                    break;
                }
                case 3: 
                case 4: {
                    objectArray = objectArray2;
                    objectArray2[2] = "getContributedClassifierIncludeDeprecated";
                    break;
                }
                case 5: 
                case 6: {
                    objectArray = objectArray2;
                    objectArray2[2] = "getContributedVariables";
                    break;
                }
                case 7: 
                case 8: {
                    objectArray = objectArray2;
                    objectArray2[2] = "getContributedFunctions";
                    break;
                }
                case 9: 
                case 10: {
                    objectArray = objectArray2;
                    objectArray2[2] = "getContributedDescriptors";
                    break;
                }
                case 11: 
                case 12: {
                    objectArray = objectArray2;
                    objectArray2[2] = "recordLookup";
                    break;
                }
                case 13: {
                    objectArray = objectArray2;
                    objectArray2[2] = "definitelyDoesNotContainName";
                    break;
                }
                case 14: {
                    objectArray = objectArray2;
                    objectArray2[2] = "printScopeStructure";
                    break;
                }
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    }

    public static class ErrorScope
    implements MemberScope {
        private final String debugMessage;

        private ErrorScope(@NotNull String debugMessage) {
            if (debugMessage == null) {
                ErrorScope.$$$reportNull$$$0(0);
            }
            this.debugMessage = debugMessage;
        }

        @Override
        @Nullable
        public ClassifierDescriptor getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
            if (name == null) {
                ErrorScope.$$$reportNull$$$0(1);
            }
            if (location == null) {
                ErrorScope.$$$reportNull$$$0(2);
            }
            return ErrorUtils.createErrorClass(name.asString());
        }

        @NotNull
        public Set<? extends PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
            if (name == null) {
                ErrorScope.$$$reportNull$$$0(5);
            }
            if (location == null) {
                ErrorScope.$$$reportNull$$$0(6);
            }
            Set set = ERROR_PROPERTY_GROUP;
            if (set == null) {
                ErrorScope.$$$reportNull$$$0(7);
            }
            return set;
        }

        @NotNull
        public Set<? extends SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
            if (name == null) {
                ErrorScope.$$$reportNull$$$0(8);
            }
            if (location == null) {
                ErrorScope.$$$reportNull$$$0(9);
            }
            Set<SimpleFunctionDescriptor> set = Collections.singleton(ErrorUtils.createErrorFunction(this));
            if (set == null) {
                ErrorScope.$$$reportNull$$$0(10);
            }
            return set;
        }

        @Override
        @NotNull
        public Set<Name> getFunctionNames() {
            Set<Name> set = Collections.emptySet();
            if (set == null) {
                ErrorScope.$$$reportNull$$$0(11);
            }
            return set;
        }

        @Override
        @NotNull
        public Set<Name> getVariableNames() {
            Set<Name> set = Collections.emptySet();
            if (set == null) {
                ErrorScope.$$$reportNull$$$0(12);
            }
            return set;
        }

        @Override
        @NotNull
        public Set<Name> getClassifierNames() {
            Set<Name> set = Collections.emptySet();
            if (set == null) {
                ErrorScope.$$$reportNull$$$0(13);
            }
            return set;
        }

        @Override
        public void recordLookup(@NotNull Name name, @NotNull LookupLocation location) {
            if (name == null) {
                ErrorScope.$$$reportNull$$$0(14);
            }
            if (location == null) {
                ErrorScope.$$$reportNull$$$0(15);
            }
        }

        @Override
        @NotNull
        public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
            if (kindFilter == null) {
                ErrorScope.$$$reportNull$$$0(16);
            }
            if (nameFilter == null) {
                ErrorScope.$$$reportNull$$$0(17);
            }
            List<DeclarationDescriptor> list = Collections.emptyList();
            if (list == null) {
                ErrorScope.$$$reportNull$$$0(18);
            }
            return list;
        }

        public String toString() {
            return "ErrorScope{" + this.debugMessage + '}';
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
                case 10: 
                case 11: 
                case 12: 
                case 13: 
                case 18: {
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
                case 10: 
                case 11: 
                case 12: 
                case 13: 
                case 18: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "debugMessage";
                    break;
                }
                case 1: 
                case 3: 
                case 5: 
                case 8: 
                case 14: 
                case 19: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "name";
                    break;
                }
                case 2: 
                case 4: 
                case 6: 
                case 9: 
                case 15: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "location";
                    break;
                }
                case 7: 
                case 10: 
                case 11: 
                case 12: 
                case 13: 
                case 18: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$ErrorScope";
                    break;
                }
                case 16: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kindFilter";
                    break;
                }
                case 17: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "nameFilter";
                    break;
                }
                case 20: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "p";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/ErrorUtils$ErrorScope";
                    break;
                }
                case 7: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getContributedVariables";
                    break;
                }
                case 10: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getContributedFunctions";
                    break;
                }
                case 11: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getFunctionNames";
                    break;
                }
                case 12: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getVariableNames";
                    break;
                }
                case 13: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getClassifierNames";
                    break;
                }
                case 18: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getContributedDescriptors";
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
                case 2: {
                    objectArray = objectArray;
                    objectArray[2] = "getContributedClassifier";
                    break;
                }
                case 3: 
                case 4: {
                    objectArray = objectArray;
                    objectArray[2] = "getContributedClassifierIncludeDeprecated";
                    break;
                }
                case 5: 
                case 6: {
                    objectArray = objectArray;
                    objectArray[2] = "getContributedVariables";
                    break;
                }
                case 7: 
                case 10: 
                case 11: 
                case 12: 
                case 13: 
                case 18: {
                    break;
                }
                case 8: 
                case 9: {
                    objectArray = objectArray;
                    objectArray[2] = "getContributedFunctions";
                    break;
                }
                case 14: 
                case 15: {
                    objectArray = objectArray;
                    objectArray[2] = "recordLookup";
                    break;
                }
                case 16: 
                case 17: {
                    objectArray = objectArray;
                    objectArray[2] = "getContributedDescriptors";
                    break;
                }
                case 19: {
                    objectArray = objectArray;
                    objectArray[2] = "definitelyDoesNotContainName";
                    break;
                }
                case 20: {
                    objectArray = objectArray;
                    objectArray[2] = "printScopeStructure";
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
                case 10: 
                case 11: 
                case 12: 
                case 13: 
                case 18: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }
}

