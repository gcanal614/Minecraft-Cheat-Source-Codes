/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceFile;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeAliasConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.SuperCallReceiverValue;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ThisClassReceiver;
import kotlin.reflect.jvm.internal.impl.types.DynamicTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.util.ModuleVisibilityHelper;
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Visibilities {
    @NotNull
    public static final Visibility PRIVATE = new Visibility("private", false){

        private boolean hasContainingSourceFile(@NotNull DeclarationDescriptor descriptor2) {
            if (descriptor2 == null) {
                1.$$$reportNull$$$0(0);
            }
            return DescriptorUtils.getContainingSourceFile(descriptor2) != SourceFile.NO_SOURCE_FILE;
        }

        @Override
        public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
            ClassifierDescriptorWithTypeParameters classDescriptor;
            if (what == null) {
                1.$$$reportNull$$$0(1);
            }
            if (from == null) {
                1.$$$reportNull$$$0(2);
            }
            if (DescriptorUtils.isTopLevelDeclaration(what) && this.hasContainingSourceFile(from)) {
                return Visibilities.inSameFile(what, from);
            }
            if (what instanceof ConstructorDescriptor && DescriptorUtils.isSealedClass(classDescriptor = ((ConstructorDescriptor)what).getContainingDeclaration()) && DescriptorUtils.isTopLevelDeclaration(classDescriptor) && from instanceof ConstructorDescriptor && DescriptorUtils.isTopLevelDeclaration(from.getContainingDeclaration()) && Visibilities.inSameFile(what, from)) {
                return true;
            }
            DeclarationDescriptor parent = what;
            while (!(parent == null || (parent = parent.getContainingDeclaration()) instanceof ClassDescriptor && !DescriptorUtils.isCompanionObject(parent) || parent instanceof PackageFragmentDescriptor)) {
            }
            if (parent == null) {
                return false;
            }
            for (DeclarationDescriptor fromParent = from; fromParent != null; fromParent = fromParent.getContainingDeclaration()) {
                if (parent == fromParent) {
                    return true;
                }
                if (!(fromParent instanceof PackageFragmentDescriptor)) continue;
                return parent instanceof PackageFragmentDescriptor && ((PackageFragmentDescriptor)parent).getFqName().equals(((PackageFragmentDescriptor)fromParent).getFqName()) && DescriptorUtils.areInSameModule(fromParent, parent);
            }
            return false;
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2;
            Object[] objectArray3 = new Object[3];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "descriptor";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "what";
                    break;
                }
                case 2: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "from";
                    break;
                }
            }
            objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$1";
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[2] = "hasContainingSourceFile";
                    break;
                }
                case 1: 
                case 2: {
                    objectArray = objectArray2;
                    objectArray2[2] = "isVisible";
                    break;
                }
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    };
    @NotNull
    public static final Visibility PRIVATE_TO_THIS = new Visibility("private_to_this", false){

        @Override
        public boolean isVisible(@Nullable ReceiverValue thisObject, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
            if (what == null) {
                2.$$$reportNull$$$0(0);
            }
            if (from == null) {
                2.$$$reportNull$$$0(1);
            }
            if (PRIVATE.isVisible(thisObject, what, from)) {
                if (thisObject == ALWAYS_SUITABLE_RECEIVER) {
                    return true;
                }
                if (thisObject == IRRELEVANT_RECEIVER) {
                    return false;
                }
                ClassDescriptor classDescriptor = DescriptorUtils.getParentOfType(what, ClassDescriptor.class);
                if (classDescriptor != null && thisObject instanceof ThisClassReceiver) {
                    return ((ThisClassReceiver)thisObject).getClassDescriptor().getOriginal().equals(classDescriptor.getOriginal());
                }
            }
            return false;
        }

        @Override
        @NotNull
        public String getInternalDisplayName() {
            return "private/*private to this*/";
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2 = new Object[3];
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[0] = "what";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[0] = "from";
                    break;
                }
            }
            objectArray[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$2";
            objectArray[2] = "isVisible";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    };
    @NotNull
    public static final Visibility PROTECTED = new Visibility("protected", true){

        @Override
        public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
            ClassDescriptor companionOwner;
            if (what == null) {
                3.$$$reportNull$$$0(0);
            }
            if (from == null) {
                3.$$$reportNull$$$0(1);
            }
            ClassDescriptor givenDescriptorContainingClass = DescriptorUtils.getParentOfType(what, ClassDescriptor.class);
            ClassDescriptor fromClass = DescriptorUtils.getParentOfType(from, ClassDescriptor.class, false);
            if (fromClass == null) {
                return false;
            }
            if (givenDescriptorContainingClass != null && DescriptorUtils.isCompanionObject(givenDescriptorContainingClass) && (companionOwner = DescriptorUtils.getParentOfType(givenDescriptorContainingClass, ClassDescriptor.class)) != null && DescriptorUtils.isSubclass(fromClass, companionOwner)) {
                return true;
            }
            DeclarationDescriptorWithVisibility whatDeclaration = DescriptorUtils.unwrapFakeOverrideToAnyDeclaration(what);
            ClassDescriptor classDescriptor = DescriptorUtils.getParentOfType(whatDeclaration, ClassDescriptor.class);
            if (classDescriptor == null) {
                return false;
            }
            if (DescriptorUtils.isSubclass(fromClass, classDescriptor) && this.doesReceiverFitForProtectedVisibility(receiver, whatDeclaration, fromClass)) {
                return true;
            }
            return this.isVisible(receiver, what, fromClass.getContainingDeclaration());
        }

        private boolean doesReceiverFitForProtectedVisibility(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility whatDeclaration, @NotNull ClassDescriptor fromClass) {
            if (whatDeclaration == null) {
                3.$$$reportNull$$$0(2);
            }
            if (fromClass == null) {
                3.$$$reportNull$$$0(3);
            }
            if (receiver == FALSE_IF_PROTECTED) {
                return false;
            }
            if (!(whatDeclaration instanceof CallableMemberDescriptor)) {
                return true;
            }
            if (whatDeclaration instanceof ConstructorDescriptor) {
                return true;
            }
            if (receiver == ALWAYS_SUITABLE_RECEIVER) {
                return true;
            }
            if (receiver == IRRELEVANT_RECEIVER || receiver == null) {
                return false;
            }
            KotlinType actualReceiverType = receiver instanceof SuperCallReceiverValue ? ((SuperCallReceiverValue)receiver).getThisType() : receiver.getType();
            return DescriptorUtils.isSubtypeOfClass(actualReceiverType, fromClass) || DynamicTypesKt.isDynamic(actualReceiverType);
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2;
            Object[] objectArray3 = new Object[3];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "what";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "from";
                    break;
                }
                case 2: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "whatDeclaration";
                    break;
                }
                case 3: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "fromClass";
                    break;
                }
            }
            objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$3";
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[2] = "isVisible";
                    break;
                }
                case 2: 
                case 3: {
                    objectArray = objectArray2;
                    objectArray2[2] = "doesReceiverFitForProtectedVisibility";
                    break;
                }
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    };
    @NotNull
    public static final Visibility INTERNAL = new Visibility("internal", false){

        @Override
        public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
            if (what == null) {
                4.$$$reportNull$$$0(0);
            }
            if (from == null) {
                4.$$$reportNull$$$0(1);
            }
            ModuleDescriptor whatModule = DescriptorUtils.getContainingModule(what);
            ModuleDescriptor fromModule = DescriptorUtils.getContainingModule(from);
            if (!fromModule.shouldSeeInternalsOf(whatModule)) {
                return false;
            }
            return MODULE_VISIBILITY_HELPER.isInFriendModule(what, from);
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2 = new Object[3];
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[0] = "what";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[0] = "from";
                    break;
                }
            }
            objectArray[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$4";
            objectArray[2] = "isVisible";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    };
    @NotNull
    public static final Visibility PUBLIC = new Visibility("public", true){

        @Override
        public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
            if (what == null) {
                5.$$$reportNull$$$0(0);
            }
            if (from == null) {
                5.$$$reportNull$$$0(1);
            }
            return true;
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2 = new Object[3];
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[0] = "what";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[0] = "from";
                    break;
                }
            }
            objectArray[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$5";
            objectArray[2] = "isVisible";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    };
    @NotNull
    public static final Visibility LOCAL = new Visibility("local", false){

        @Override
        public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
            if (what == null) {
                6.$$$reportNull$$$0(0);
            }
            if (from == null) {
                6.$$$reportNull$$$0(1);
            }
            throw new IllegalStateException("This method shouldn't be invoked for LOCAL visibility");
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2 = new Object[3];
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[0] = "what";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[0] = "from";
                    break;
                }
            }
            objectArray[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$6";
            objectArray[2] = "isVisible";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    };
    @NotNull
    public static final Visibility INHERITED = new Visibility("inherited", false){

        @Override
        public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
            if (what == null) {
                7.$$$reportNull$$$0(0);
            }
            if (from == null) {
                7.$$$reportNull$$$0(1);
            }
            throw new IllegalStateException("Visibility is unknown yet");
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2 = new Object[3];
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[0] = "what";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[0] = "from";
                    break;
                }
            }
            objectArray[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$7";
            objectArray[2] = "isVisible";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    };
    @NotNull
    public static final Visibility INVISIBLE_FAKE = new Visibility("invisible_fake", false){

        @Override
        public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
            if (what == null) {
                8.$$$reportNull$$$0(0);
            }
            if (from == null) {
                8.$$$reportNull$$$0(1);
            }
            return false;
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2 = new Object[3];
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[0] = "what";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[0] = "from";
                    break;
                }
            }
            objectArray[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$8";
            objectArray[2] = "isVisible";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    };
    @NotNull
    public static final Visibility UNKNOWN = new Visibility("unknown", false){

        @Override
        public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
            if (what == null) {
                9.$$$reportNull$$$0(0);
            }
            if (from == null) {
                9.$$$reportNull$$$0(1);
            }
            return false;
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2 = new Object[3];
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[0] = "what";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[0] = "from";
                    break;
                }
            }
            objectArray[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities$9";
            objectArray[2] = "isVisible";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    };
    public static final Set<Visibility> INVISIBLE_FROM_OTHER_MODULES = Collections.unmodifiableSet(SetsKt.setOf(PRIVATE, PRIVATE_TO_THIS, INTERNAL, LOCAL));
    private static final Map<Visibility, Integer> ORDERED_VISIBILITIES;
    public static final Visibility DEFAULT_VISIBILITY;
    private static final ReceiverValue IRRELEVANT_RECEIVER;
    public static final ReceiverValue ALWAYS_SUITABLE_RECEIVER;
    @Deprecated
    public static final ReceiverValue FALSE_IF_PROTECTED;
    @NotNull
    private static final ModuleVisibilityHelper MODULE_VISIBILITY_HELPER;

    public static boolean isVisibleIgnoringReceiver(@NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
        if (what == null) {
            Visibilities.$$$reportNull$$$0(2);
        }
        if (from == null) {
            Visibilities.$$$reportNull$$$0(3);
        }
        return Visibilities.findInvisibleMember(ALWAYS_SUITABLE_RECEIVER, what, from) == null;
    }

    public static boolean inSameFile(@NotNull DeclarationDescriptor what, @NotNull DeclarationDescriptor from) {
        SourceFile fromContainingFile;
        if (what == null) {
            Visibilities.$$$reportNull$$$0(6);
        }
        if (from == null) {
            Visibilities.$$$reportNull$$$0(7);
        }
        if ((fromContainingFile = DescriptorUtils.getContainingSourceFile(from)) != SourceFile.NO_SOURCE_FILE) {
            return fromContainingFile.equals(DescriptorUtils.getContainingSourceFile(what));
        }
        return false;
    }

    @Nullable
    public static DeclarationDescriptorWithVisibility findInvisibleMember(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
        DeclarationDescriptorWithVisibility invisibleUnderlying;
        if (what == null) {
            Visibilities.$$$reportNull$$$0(8);
        }
        if (from == null) {
            Visibilities.$$$reportNull$$$0(9);
        }
        DeclarationDescriptorWithVisibility parent = (DeclarationDescriptorWithVisibility)what.getOriginal();
        while (parent != null && parent.getVisibility() != LOCAL) {
            if (!parent.getVisibility().isVisible(receiver, parent, from)) {
                return parent;
            }
            parent = DescriptorUtils.getParentOfType(parent, DeclarationDescriptorWithVisibility.class);
        }
        if (what instanceof TypeAliasConstructorDescriptor && (invisibleUnderlying = Visibilities.findInvisibleMember(receiver, ((TypeAliasConstructorDescriptor)what).getUnderlyingConstructorDescriptor(), from)) != null) {
            return invisibleUnderlying;
        }
        return null;
    }

    @Nullable
    static Integer compareLocal(@NotNull Visibility first, @NotNull Visibility second) {
        if (first == null) {
            Visibilities.$$$reportNull$$$0(10);
        }
        if (second == null) {
            Visibilities.$$$reportNull$$$0(11);
        }
        if (first == second) {
            return 0;
        }
        Integer firstIndex = ORDERED_VISIBILITIES.get(first);
        Integer secondIndex = ORDERED_VISIBILITIES.get(second);
        if (firstIndex == null || secondIndex == null || firstIndex.equals(secondIndex)) {
            return null;
        }
        return firstIndex - secondIndex;
    }

    @Nullable
    public static Integer compare(@NotNull Visibility first, @NotNull Visibility second) {
        Integer result2;
        if (first == null) {
            Visibilities.$$$reportNull$$$0(12);
        }
        if (second == null) {
            Visibilities.$$$reportNull$$$0(13);
        }
        if ((result2 = first.compareTo(second)) != null) {
            return result2;
        }
        Integer oppositeResult = second.compareTo(first);
        if (oppositeResult != null) {
            return -oppositeResult.intValue();
        }
        return null;
    }

    public static boolean isPrivate(@NotNull Visibility visibility) {
        if (visibility == null) {
            Visibilities.$$$reportNull$$$0(14);
        }
        return visibility == PRIVATE || visibility == PRIVATE_TO_THIS;
    }

    static {
        HashMap<Visibility, Integer> visibilities = CollectionsKt.newHashMapWithExpectedSize(4);
        visibilities.put(PRIVATE_TO_THIS, 0);
        visibilities.put(PRIVATE, 0);
        visibilities.put(INTERNAL, 1);
        visibilities.put(PROTECTED, 1);
        visibilities.put(PUBLIC, 2);
        ORDERED_VISIBILITIES = Collections.unmodifiableMap(visibilities);
        DEFAULT_VISIBILITY = PUBLIC;
        IRRELEVANT_RECEIVER = new ReceiverValue(){

            @Override
            @NotNull
            public KotlinType getType() {
                throw new IllegalStateException("This method should not be called");
            }
        };
        ALWAYS_SUITABLE_RECEIVER = new ReceiverValue(){

            @Override
            @NotNull
            public KotlinType getType() {
                throw new IllegalStateException("This method should not be called");
            }
        };
        FALSE_IF_PROTECTED = new ReceiverValue(){

            @Override
            @NotNull
            public KotlinType getType() {
                throw new IllegalStateException("This method should not be called");
            }
        };
        Iterator<ModuleVisibilityHelper> iterator2 = ServiceLoader.load(ModuleVisibilityHelper.class, ModuleVisibilityHelper.class.getClassLoader()).iterator();
        MODULE_VISIBILITY_HELPER = iterator2.hasNext() ? iterator2.next() : ModuleVisibilityHelper.EMPTY.INSTANCE;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[3];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "what";
                break;
            }
            case 1: 
            case 3: 
            case 5: 
            case 7: 
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "from";
                break;
            }
            case 10: 
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "first";
                break;
            }
            case 11: 
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "second";
                break;
            }
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "visibility";
                break;
            }
        }
        objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/Visibilities";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "isVisible";
                break;
            }
            case 2: 
            case 3: {
                objectArray = objectArray2;
                objectArray2[2] = "isVisibleIgnoringReceiver";
                break;
            }
            case 4: 
            case 5: {
                objectArray = objectArray2;
                objectArray2[2] = "isVisibleWithAnyReceiver";
                break;
            }
            case 6: 
            case 7: {
                objectArray = objectArray2;
                objectArray2[2] = "inSameFile";
                break;
            }
            case 8: 
            case 9: {
                objectArray = objectArray2;
                objectArray2[2] = "findInvisibleMember";
                break;
            }
            case 10: 
            case 11: {
                objectArray = objectArray2;
                objectArray2[2] = "compareLocal";
                break;
            }
            case 12: 
            case 13: {
                objectArray = objectArray2;
                objectArray2[2] = "compare";
                break;
            }
            case 14: {
                objectArray = objectArray2;
                objectArray2[2] = "isPrivate";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}

