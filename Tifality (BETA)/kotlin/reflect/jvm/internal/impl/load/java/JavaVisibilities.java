/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaVisibilities {
    @NotNull
    public static final Visibility PACKAGE_VISIBILITY = new Visibility("package", false){

        @Override
        public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
            if (what == null) {
                1.$$$reportNull$$$0(0);
            }
            if (from == null) {
                1.$$$reportNull$$$0(1);
            }
            return JavaVisibilities.areInSamePackage(what, from);
        }

        @Override
        protected Integer compareTo(@NotNull Visibility visibility) {
            if (visibility == null) {
                1.$$$reportNull$$$0(2);
            }
            if (this == visibility) {
                return 0;
            }
            if (Visibilities.isPrivate(visibility)) {
                return 1;
            }
            return -1;
        }

        @Override
        @NotNull
        public String getInternalDisplayName() {
            return "public/*package*/";
        }

        @Override
        @NotNull
        public Visibility normalize() {
            Visibility visibility = Visibilities.PROTECTED;
            if (visibility == null) {
                1.$$$reportNull$$$0(3);
            }
            return visibility;
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
                case 3: 
                case 5: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
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
                    objectArray3[0] = "visibility";
                    break;
                }
                case 3: 
                case 5: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/load/java/JavaVisibilities$1";
                    break;
                }
                case 4: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "classDescriptor";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/load/java/JavaVisibilities$1";
                    break;
                }
                case 3: {
                    objectArray = objectArray2;
                    objectArray2[1] = "normalize";
                    break;
                }
                case 5: {
                    objectArray = objectArray2;
                    objectArray2[1] = "effectiveVisibility";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "isVisible";
                    break;
                }
                case 2: {
                    objectArray = objectArray;
                    objectArray[2] = "compareTo";
                    break;
                }
                case 3: 
                case 5: {
                    break;
                }
                case 4: {
                    objectArray = objectArray;
                    objectArray[2] = "effectiveVisibility";
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
                case 5: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    };
    @NotNull
    public static final Visibility PROTECTED_STATIC_VISIBILITY = new Visibility("protected_static", true){

        @Override
        public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
            if (what == null) {
                2.$$$reportNull$$$0(0);
            }
            if (from == null) {
                2.$$$reportNull$$$0(1);
            }
            return JavaVisibilities.isVisibleForProtectedAndPackage(receiver, what, from);
        }

        @Override
        @NotNull
        public String getInternalDisplayName() {
            return "protected/*protected static*/";
        }

        @Override
        @NotNull
        public Visibility normalize() {
            Visibility visibility = Visibilities.PROTECTED;
            if (visibility == null) {
                2.$$$reportNull$$$0(2);
            }
            return visibility;
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
                case 2: {
                    string = "@NotNull method %s.%s must not return null";
                    break;
                }
            }
            switch (n) {
                default: {
                    n2 = 3;
                    break;
                }
                case 2: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
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
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/load/java/JavaVisibilities$2";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/load/java/JavaVisibilities$2";
                    break;
                }
                case 2: {
                    objectArray = objectArray2;
                    objectArray2[1] = "normalize";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "isVisible";
                    break;
                }
                case 2: {
                    break;
                }
            }
            String string2 = String.format(string, objectArray);
            switch (n) {
                default: {
                    runtimeException = new IllegalArgumentException(string2);
                    break;
                }
                case 2: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    };
    @NotNull
    public static final Visibility PROTECTED_AND_PACKAGE = new Visibility("protected_and_package", true){

        @Override
        public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
            if (what == null) {
                3.$$$reportNull$$$0(0);
            }
            if (from == null) {
                3.$$$reportNull$$$0(1);
            }
            return JavaVisibilities.isVisibleForProtectedAndPackage(receiver, what, from);
        }

        @Override
        protected Integer compareTo(@NotNull Visibility visibility) {
            if (visibility == null) {
                3.$$$reportNull$$$0(2);
            }
            if (this == visibility) {
                return 0;
            }
            if (visibility == Visibilities.INTERNAL) {
                return null;
            }
            if (Visibilities.isPrivate(visibility)) {
                return 1;
            }
            return -1;
        }

        @Override
        @NotNull
        public String getInternalDisplayName() {
            return "protected/*protected and package*/";
        }

        @Override
        @NotNull
        public Visibility normalize() {
            Visibility visibility = Visibilities.PROTECTED;
            if (visibility == null) {
                3.$$$reportNull$$$0(3);
            }
            return visibility;
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
                case 3: {
                    string = "@NotNull method %s.%s must not return null";
                    break;
                }
            }
            switch (n) {
                default: {
                    n2 = 3;
                    break;
                }
                case 3: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
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
                    objectArray3[0] = "visibility";
                    break;
                }
                case 3: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/load/java/JavaVisibilities$3";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/load/java/JavaVisibilities$3";
                    break;
                }
                case 3: {
                    objectArray = objectArray2;
                    objectArray2[1] = "normalize";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "isVisible";
                    break;
                }
                case 2: {
                    objectArray = objectArray;
                    objectArray[2] = "compareTo";
                    break;
                }
                case 3: {
                    break;
                }
            }
            String string2 = String.format(string, objectArray);
            switch (n) {
                default: {
                    runtimeException = new IllegalArgumentException(string2);
                    break;
                }
                case 3: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    };

    private static boolean isVisibleForProtectedAndPackage(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
        if (what == null) {
            JavaVisibilities.$$$reportNull$$$0(0);
        }
        if (from == null) {
            JavaVisibilities.$$$reportNull$$$0(1);
        }
        if (JavaVisibilities.areInSamePackage(DescriptorUtils.unwrapFakeOverrideToAnyDeclaration(what), from)) {
            return true;
        }
        return Visibilities.PROTECTED.isVisible(receiver, what, from);
    }

    private static boolean areInSamePackage(@NotNull DeclarationDescriptor first, @NotNull DeclarationDescriptor second) {
        if (first == null) {
            JavaVisibilities.$$$reportNull$$$0(2);
        }
        if (second == null) {
            JavaVisibilities.$$$reportNull$$$0(3);
        }
        PackageFragmentDescriptor whatPackage = DescriptorUtils.getParentOfType(first, PackageFragmentDescriptor.class, false);
        PackageFragmentDescriptor fromPackage = DescriptorUtils.getParentOfType(second, PackageFragmentDescriptor.class, false);
        return fromPackage != null && whatPackage != null && whatPackage.getFqName().equals(fromPackage.getFqName());
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
                objectArray3[0] = "first";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "second";
                break;
            }
        }
        objectArray2[1] = "kotlin/reflect/jvm/internal/impl/load/java/JavaVisibilities";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "isVisibleForProtectedAndPackage";
                break;
            }
            case 2: 
            case 3: {
                objectArray = objectArray2;
                objectArray2[2] = "areInSamePackage";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}

