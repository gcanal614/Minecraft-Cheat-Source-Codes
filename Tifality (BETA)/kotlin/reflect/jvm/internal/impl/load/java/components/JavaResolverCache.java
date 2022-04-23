/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.components;

import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaElement;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface JavaResolverCache {
    public static final JavaResolverCache EMPTY = new JavaResolverCache(){

        @Override
        @Nullable
        public ClassDescriptor getClassResolvedFromSource(@NotNull FqName fqName2) {
            if (fqName2 == null) {
                1.$$$reportNull$$$0(0);
            }
            return null;
        }

        @Override
        public void recordMethod(@NotNull JavaMethod method, @NotNull SimpleFunctionDescriptor descriptor2) {
            if (method == null) {
                1.$$$reportNull$$$0(1);
            }
            if (descriptor2 == null) {
                1.$$$reportNull$$$0(2);
            }
        }

        @Override
        public void recordConstructor(@NotNull JavaElement element, @NotNull ConstructorDescriptor descriptor2) {
            if (element == null) {
                1.$$$reportNull$$$0(3);
            }
            if (descriptor2 == null) {
                1.$$$reportNull$$$0(4);
            }
        }

        @Override
        public void recordField(@NotNull JavaField field, @NotNull PropertyDescriptor descriptor2) {
            if (field == null) {
                1.$$$reportNull$$$0(5);
            }
            if (descriptor2 == null) {
                1.$$$reportNull$$$0(6);
            }
        }

        @Override
        public void recordClass(@NotNull JavaClass javaClass, @NotNull ClassDescriptor descriptor2) {
            if (javaClass == null) {
                1.$$$reportNull$$$0(7);
            }
            if (descriptor2 == null) {
                1.$$$reportNull$$$0(8);
            }
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2;
            Object[] objectArray3 = new Object[3];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "fqName";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "method";
                    break;
                }
                case 2: 
                case 4: 
                case 6: 
                case 8: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "descriptor";
                    break;
                }
                case 3: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "element";
                    break;
                }
                case 5: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "field";
                    break;
                }
                case 7: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "javaClass";
                    break;
                }
            }
            objectArray2[1] = "kotlin/reflect/jvm/internal/impl/load/java/components/JavaResolverCache$1";
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[2] = "getClassResolvedFromSource";
                    break;
                }
                case 1: 
                case 2: {
                    objectArray = objectArray2;
                    objectArray2[2] = "recordMethod";
                    break;
                }
                case 3: 
                case 4: {
                    objectArray = objectArray2;
                    objectArray2[2] = "recordConstructor";
                    break;
                }
                case 5: 
                case 6: {
                    objectArray = objectArray2;
                    objectArray2[2] = "recordField";
                    break;
                }
                case 7: 
                case 8: {
                    objectArray = objectArray2;
                    objectArray2[2] = "recordClass";
                    break;
                }
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    };

    @Nullable
    public ClassDescriptor getClassResolvedFromSource(@NotNull FqName var1);

    public void recordMethod(@NotNull JavaMethod var1, @NotNull SimpleFunctionDescriptor var2);

    public void recordConstructor(@NotNull JavaElement var1, @NotNull ConstructorDescriptor var2);

    public void recordField(@NotNull JavaField var1, @NotNull PropertyDescriptor var2);

    public void recordClass(@NotNull JavaClass var1, @NotNull ClassDescriptor var2);
}

