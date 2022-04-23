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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorBase;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.NonReportingOverrideStrategy;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.ClassTypeConstructorImpl;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnumEntrySyntheticClassDescriptor
extends ClassDescriptorBase {
    private final TypeConstructor typeConstructor;
    private final MemberScope scope;
    private final NotNullLazyValue<Set<Name>> enumMemberNames;
    private final Annotations annotations;

    @NotNull
    public static EnumEntrySyntheticClassDescriptor create(@NotNull StorageManager storageManager, @NotNull ClassDescriptor enumClass, @NotNull Name name, @NotNull NotNullLazyValue<Set<Name>> enumMemberNames2, @NotNull Annotations annotations2, @NotNull SourceElement source) {
        if (storageManager == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(0);
        }
        if (enumClass == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(1);
        }
        if (name == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(2);
        }
        if (enumMemberNames2 == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(3);
        }
        if (annotations2 == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(4);
        }
        if (source == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(5);
        }
        SimpleType enumType = enumClass.getDefaultType();
        return new EnumEntrySyntheticClassDescriptor(storageManager, enumClass, enumType, name, enumMemberNames2, annotations2, source);
    }

    private EnumEntrySyntheticClassDescriptor(@NotNull StorageManager storageManager, @NotNull ClassDescriptor containingClass, @NotNull KotlinType supertype, @NotNull Name name, @NotNull NotNullLazyValue<Set<Name>> enumMemberNames2, @NotNull Annotations annotations2, @NotNull SourceElement source) {
        if (storageManager == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(6);
        }
        if (containingClass == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(7);
        }
        if (supertype == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(8);
        }
        if (name == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(9);
        }
        if (enumMemberNames2 == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(10);
        }
        if (annotations2 == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(11);
        }
        if (source == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(12);
        }
        super(storageManager, containingClass, name, source, false);
        assert (containingClass.getKind() == ClassKind.ENUM_CLASS);
        this.annotations = annotations2;
        this.typeConstructor = new ClassTypeConstructorImpl(this, Collections.emptyList(), Collections.singleton(supertype), storageManager);
        this.scope = new EnumEntryScope(storageManager);
        this.enumMemberNames = enumMemberNames2;
    }

    @Override
    @NotNull
    public MemberScope getUnsubstitutedMemberScope(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        if (kotlinTypeRefiner == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(13);
        }
        MemberScope memberScope2 = this.scope;
        if (memberScope2 == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(14);
        }
        return memberScope2;
    }

    @Override
    @NotNull
    public MemberScope getStaticScope() {
        MemberScope.Empty empty = MemberScope.Empty.INSTANCE;
        if (empty == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(15);
        }
        return empty;
    }

    @Override
    @NotNull
    public Collection<ClassConstructorDescriptor> getConstructors() {
        List<ClassConstructorDescriptor> list = Collections.emptyList();
        if (list == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(16);
        }
        return list;
    }

    @Override
    @NotNull
    public TypeConstructor getTypeConstructor() {
        TypeConstructor typeConstructor2 = this.typeConstructor;
        if (typeConstructor2 == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(17);
        }
        return typeConstructor2;
    }

    @Override
    @Nullable
    public ClassDescriptor getCompanionObjectDescriptor() {
        return null;
    }

    @Override
    @NotNull
    public ClassKind getKind() {
        ClassKind classKind = ClassKind.ENUM_ENTRY;
        if (classKind == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(18);
        }
        return classKind;
    }

    @Override
    @NotNull
    public Modality getModality() {
        Modality modality = Modality.FINAL;
        if (modality == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(19);
        }
        return modality;
    }

    @Override
    @NotNull
    public Visibility getVisibility() {
        Visibility visibility = Visibilities.PUBLIC;
        if (visibility == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(20);
        }
        return visibility;
    }

    @Override
    public boolean isInner() {
        return false;
    }

    @Override
    public boolean isData() {
        return false;
    }

    @Override
    public boolean isInline() {
        return false;
    }

    @Override
    public boolean isFun() {
        return false;
    }

    @Override
    public boolean isCompanionObject() {
        return false;
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
    @Nullable
    public ClassConstructorDescriptor getUnsubstitutedPrimaryConstructor() {
        return null;
    }

    @Override
    @NotNull
    public Annotations getAnnotations() {
        Annotations annotations2 = this.annotations;
        if (annotations2 == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(21);
        }
        return annotations2;
    }

    public String toString() {
        return "enum entry " + this.getName();
    }

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        List<TypeParameterDescriptor> list = Collections.emptyList();
        if (list == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(22);
        }
        return list;
    }

    @Override
    @NotNull
    public Collection<ClassDescriptor> getSealedSubclasses() {
        List<ClassDescriptor> list = Collections.emptyList();
        if (list == null) {
            EnumEntrySyntheticClassDescriptor.$$$reportNull$$$0(23);
        }
        return list;
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
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: {
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
                objectArray3[0] = "enumClass";
                break;
            }
            case 2: 
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 3: 
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "enumMemberNames";
                break;
            }
            case 4: 
            case 11: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotations";
                break;
            }
            case 5: 
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "source";
                break;
            }
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "containingClass";
                break;
            }
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "supertype";
                break;
            }
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlinTypeRefiner";
                break;
            }
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/EnumEntrySyntheticClassDescriptor";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/EnumEntrySyntheticClassDescriptor";
                break;
            }
            case 14: {
                objectArray = objectArray2;
                objectArray2[1] = "getUnsubstitutedMemberScope";
                break;
            }
            case 15: {
                objectArray = objectArray2;
                objectArray2[1] = "getStaticScope";
                break;
            }
            case 16: {
                objectArray = objectArray2;
                objectArray2[1] = "getConstructors";
                break;
            }
            case 17: {
                objectArray = objectArray2;
                objectArray2[1] = "getTypeConstructor";
                break;
            }
            case 18: {
                objectArray = objectArray2;
                objectArray2[1] = "getKind";
                break;
            }
            case 19: {
                objectArray = objectArray2;
                objectArray2[1] = "getModality";
                break;
            }
            case 20: {
                objectArray = objectArray2;
                objectArray2[1] = "getVisibility";
                break;
            }
            case 21: {
                objectArray = objectArray2;
                objectArray2[1] = "getAnnotations";
                break;
            }
            case 22: {
                objectArray = objectArray2;
                objectArray2[1] = "getDeclaredTypeParameters";
                break;
            }
            case 23: {
                objectArray = objectArray2;
                objectArray2[1] = "getSealedSubclasses";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "create";
                break;
            }
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 13: {
                objectArray = objectArray;
                objectArray[2] = "getUnsubstitutedMemberScope";
                break;
            }
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: {
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }

    private class EnumEntryScope
    extends MemberScopeImpl {
        private final MemoizedFunctionToNotNull<Name, Collection<? extends SimpleFunctionDescriptor>> functions;
        private final MemoizedFunctionToNotNull<Name, Collection<? extends PropertyDescriptor>> properties;
        private final NotNullLazyValue<Collection<DeclarationDescriptor>> allDescriptors;

        public EnumEntryScope(StorageManager storageManager) {
            if (storageManager == null) {
                EnumEntryScope.$$$reportNull$$$0(0);
            }
            this.functions = storageManager.createMemoizedFunction(new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>(){

                @Override
                public Collection<? extends SimpleFunctionDescriptor> invoke(Name name) {
                    return EnumEntryScope.this.computeFunctions(name);
                }
            });
            this.properties = storageManager.createMemoizedFunction(new Function1<Name, Collection<? extends PropertyDescriptor>>(){

                @Override
                public Collection<? extends PropertyDescriptor> invoke(Name name) {
                    return EnumEntryScope.this.computeProperties(name);
                }
            });
            this.allDescriptors = storageManager.createLazyValue(new Function0<Collection<DeclarationDescriptor>>(){

                @Override
                public Collection<DeclarationDescriptor> invoke() {
                    return EnumEntryScope.this.computeAllDeclarations();
                }
            });
        }

        @Override
        @NotNull
        public Collection<? extends PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
            if (name == null) {
                EnumEntryScope.$$$reportNull$$$0(1);
            }
            if (location == null) {
                EnumEntryScope.$$$reportNull$$$0(2);
            }
            Collection collection = (Collection)this.properties.invoke(name);
            if (collection == null) {
                EnumEntryScope.$$$reportNull$$$0(3);
            }
            return collection;
        }

        @NotNull
        private Collection<? extends PropertyDescriptor> computeProperties(@NotNull Name name) {
            if (name == null) {
                EnumEntryScope.$$$reportNull$$$0(4);
            }
            return this.resolveFakeOverrides(name, this.getSupertypeScope().getContributedVariables(name, NoLookupLocation.FOR_NON_TRACKED_SCOPE));
        }

        @Override
        @NotNull
        public Collection<? extends SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
            if (name == null) {
                EnumEntryScope.$$$reportNull$$$0(5);
            }
            if (location == null) {
                EnumEntryScope.$$$reportNull$$$0(6);
            }
            Collection collection = (Collection)this.functions.invoke(name);
            if (collection == null) {
                EnumEntryScope.$$$reportNull$$$0(7);
            }
            return collection;
        }

        @NotNull
        private Collection<? extends SimpleFunctionDescriptor> computeFunctions(@NotNull Name name) {
            if (name == null) {
                EnumEntryScope.$$$reportNull$$$0(8);
            }
            return this.resolveFakeOverrides(name, this.getSupertypeScope().getContributedFunctions(name, NoLookupLocation.FOR_NON_TRACKED_SCOPE));
        }

        @NotNull
        private MemberScope getSupertypeScope() {
            Collection<KotlinType> supertype = EnumEntrySyntheticClassDescriptor.this.getTypeConstructor().getSupertypes();
            assert (supertype.size() == 1) : "Enum entry and its companion object both should have exactly one supertype: " + supertype;
            MemberScope memberScope2 = supertype.iterator().next().getMemberScope();
            if (memberScope2 == null) {
                EnumEntryScope.$$$reportNull$$$0(9);
            }
            return memberScope2;
        }

        @NotNull
        private <D extends CallableMemberDescriptor> Collection<? extends D> resolveFakeOverrides(@NotNull Name name, @NotNull Collection<? extends D> fromSupertypes) {
            if (name == null) {
                EnumEntryScope.$$$reportNull$$$0(10);
            }
            if (fromSupertypes == null) {
                EnumEntryScope.$$$reportNull$$$0(11);
            }
            final LinkedHashSet result2 = new LinkedHashSet();
            OverridingUtil.DEFAULT.generateOverridesInFunctionGroup(name, fromSupertypes, Collections.emptySet(), EnumEntrySyntheticClassDescriptor.this, new NonReportingOverrideStrategy(){

                @Override
                public void addFakeOverride(@NotNull CallableMemberDescriptor fakeOverride) {
                    if (fakeOverride == null) {
                        4.$$$reportNull$$$0(0);
                    }
                    OverridingUtil.resolveUnknownVisibilityForMember(fakeOverride, null);
                    result2.add(fakeOverride);
                }

                @Override
                protected void conflict(@NotNull CallableMemberDescriptor fromSuper, @NotNull CallableMemberDescriptor fromCurrent) {
                    if (fromSuper == null) {
                        4.$$$reportNull$$$0(1);
                    }
                    if (fromCurrent == null) {
                        4.$$$reportNull$$$0(2);
                    }
                }

                private static /* synthetic */ void $$$reportNull$$$0(int n) {
                    Object[] objectArray;
                    Object[] objectArray2;
                    Object[] objectArray3 = new Object[3];
                    switch (n) {
                        default: {
                            objectArray2 = objectArray3;
                            objectArray3[0] = "fakeOverride";
                            break;
                        }
                        case 1: {
                            objectArray2 = objectArray3;
                            objectArray3[0] = "fromSuper";
                            break;
                        }
                        case 2: {
                            objectArray2 = objectArray3;
                            objectArray3[0] = "fromCurrent";
                            break;
                        }
                    }
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/EnumEntrySyntheticClassDescriptor$EnumEntryScope$4";
                    switch (n) {
                        default: {
                            objectArray = objectArray2;
                            objectArray2[2] = "addFakeOverride";
                            break;
                        }
                        case 1: 
                        case 2: {
                            objectArray = objectArray2;
                            objectArray2[2] = "conflict";
                            break;
                        }
                    }
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
                }
            });
            LinkedHashSet linkedHashSet = result2;
            if (linkedHashSet == null) {
                EnumEntryScope.$$$reportNull$$$0(12);
            }
            return linkedHashSet;
        }

        @Override
        @NotNull
        public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
            if (kindFilter == null) {
                EnumEntryScope.$$$reportNull$$$0(13);
            }
            if (nameFilter == null) {
                EnumEntryScope.$$$reportNull$$$0(14);
            }
            Collection collection = (Collection)this.allDescriptors.invoke();
            if (collection == null) {
                EnumEntryScope.$$$reportNull$$$0(15);
            }
            return collection;
        }

        @NotNull
        private Collection<DeclarationDescriptor> computeAllDeclarations() {
            HashSet<DeclarationDescriptor> result2 = new HashSet<DeclarationDescriptor>();
            for (Name name : (Set)EnumEntrySyntheticClassDescriptor.this.enumMemberNames.invoke()) {
                result2.addAll(this.getContributedFunctions(name, NoLookupLocation.FOR_NON_TRACKED_SCOPE));
                result2.addAll(this.getContributedVariables(name, NoLookupLocation.FOR_NON_TRACKED_SCOPE));
            }
            HashSet<DeclarationDescriptor> hashSet = result2;
            if (hashSet == null) {
                EnumEntryScope.$$$reportNull$$$0(16);
            }
            return hashSet;
        }

        @Override
        @NotNull
        public Set<Name> getFunctionNames() {
            Set set = (Set)EnumEntrySyntheticClassDescriptor.this.enumMemberNames.invoke();
            if (set == null) {
                EnumEntryScope.$$$reportNull$$$0(17);
            }
            return set;
        }

        @Override
        @NotNull
        public Set<Name> getClassifierNames() {
            Set<Name> set = Collections.emptySet();
            if (set == null) {
                EnumEntryScope.$$$reportNull$$$0(18);
            }
            return set;
        }

        @Override
        @NotNull
        public Set<Name> getVariableNames() {
            Set set = (Set)EnumEntrySyntheticClassDescriptor.this.enumMemberNames.invoke();
            if (set == null) {
                EnumEntryScope.$$$reportNull$$$0(19);
            }
            return set;
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
                case 3: 
                case 7: 
                case 9: 
                case 12: 
                case 15: 
                case 16: 
                case 17: 
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
                case 3: 
                case 7: 
                case 9: 
                case 12: 
                case 15: 
                case 16: 
                case 17: 
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
                case 1: 
                case 4: 
                case 5: 
                case 8: 
                case 10: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "name";
                    break;
                }
                case 2: 
                case 6: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "location";
                    break;
                }
                case 3: 
                case 7: 
                case 9: 
                case 12: 
                case 15: 
                case 16: 
                case 17: 
                case 18: 
                case 19: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/EnumEntrySyntheticClassDescriptor$EnumEntryScope";
                    break;
                }
                case 11: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "fromSupertypes";
                    break;
                }
                case 13: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kindFilter";
                    break;
                }
                case 14: {
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
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/EnumEntrySyntheticClassDescriptor$EnumEntryScope";
                    break;
                }
                case 3: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getContributedVariables";
                    break;
                }
                case 7: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getContributedFunctions";
                    break;
                }
                case 9: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getSupertypeScope";
                    break;
                }
                case 12: {
                    objectArray = objectArray2;
                    objectArray2[1] = "resolveFakeOverrides";
                    break;
                }
                case 15: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getContributedDescriptors";
                    break;
                }
                case 16: {
                    objectArray = objectArray2;
                    objectArray2[1] = "computeAllDeclarations";
                    break;
                }
                case 17: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getFunctionNames";
                    break;
                }
                case 18: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getClassifierNames";
                    break;
                }
                case 19: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getVariableNames";
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
                    objectArray[2] = "getContributedVariables";
                    break;
                }
                case 3: 
                case 7: 
                case 9: 
                case 12: 
                case 15: 
                case 16: 
                case 17: 
                case 18: 
                case 19: {
                    break;
                }
                case 4: {
                    objectArray = objectArray;
                    objectArray[2] = "computeProperties";
                    break;
                }
                case 5: 
                case 6: {
                    objectArray = objectArray;
                    objectArray[2] = "getContributedFunctions";
                    break;
                }
                case 8: {
                    objectArray = objectArray;
                    objectArray[2] = "computeFunctions";
                    break;
                }
                case 10: 
                case 11: {
                    objectArray = objectArray;
                    objectArray[2] = "resolveFakeOverrides";
                    break;
                }
                case 13: 
                case 14: {
                    objectArray = objectArray;
                    objectArray[2] = "getContributedDescriptors";
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
                case 3: 
                case 7: 
                case 9: 
                case 12: 
                case 15: 
                case 16: 
                case 17: 
                case 18: 
                case 19: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }
}

