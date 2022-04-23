/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SignaturePropagator {
    public static final SignaturePropagator DO_NOTHING = new SignaturePropagator(){

        @Override
        @NotNull
        public PropagatedSignature resolvePropagatedSignature(@NotNull JavaMethod method, @NotNull ClassDescriptor owner, @NotNull KotlinType returnType, @Nullable KotlinType receiverType, @NotNull List<ValueParameterDescriptor> valueParameters, @NotNull List<TypeParameterDescriptor> typeParameters2) {
            if (method == null) {
                1.$$$reportNull$$$0(0);
            }
            if (owner == null) {
                1.$$$reportNull$$$0(1);
            }
            if (returnType == null) {
                1.$$$reportNull$$$0(2);
            }
            if (valueParameters == null) {
                1.$$$reportNull$$$0(3);
            }
            if (typeParameters2 == null) {
                1.$$$reportNull$$$0(4);
            }
            return new PropagatedSignature(returnType, receiverType, valueParameters, typeParameters2, Collections.<String>emptyList(), false);
        }

        @Override
        public void reportSignatureErrors(@NotNull CallableMemberDescriptor descriptor2, @NotNull List<String> signatureErrors) {
            if (descriptor2 == null) {
                1.$$$reportNull$$$0(5);
            }
            if (signatureErrors == null) {
                1.$$$reportNull$$$0(6);
            }
            throw new UnsupportedOperationException("Should not be called");
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2;
            Object[] objectArray3 = new Object[3];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "method";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "owner";
                    break;
                }
                case 2: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "returnType";
                    break;
                }
                case 3: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "valueParameters";
                    break;
                }
                case 4: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "typeParameters";
                    break;
                }
                case 5: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "descriptor";
                    break;
                }
                case 6: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "signatureErrors";
                    break;
                }
            }
            objectArray2[1] = "kotlin/reflect/jvm/internal/impl/load/java/components/SignaturePropagator$1";
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[2] = "resolvePropagatedSignature";
                    break;
                }
                case 5: 
                case 6: {
                    objectArray = objectArray2;
                    objectArray2[2] = "reportSignatureErrors";
                    break;
                }
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    };

    @NotNull
    public PropagatedSignature resolvePropagatedSignature(@NotNull JavaMethod var1, @NotNull ClassDescriptor var2, @NotNull KotlinType var3, @Nullable KotlinType var4, @NotNull List<ValueParameterDescriptor> var5, @NotNull List<TypeParameterDescriptor> var6);

    public void reportSignatureErrors(@NotNull CallableMemberDescriptor var1, @NotNull List<String> var2);

    public static class PropagatedSignature {
        private final KotlinType returnType;
        private final KotlinType receiverType;
        private final List<ValueParameterDescriptor> valueParameters;
        private final List<TypeParameterDescriptor> typeParameters;
        private final List<String> signatureErrors;
        private final boolean hasStableParameterNames;

        public PropagatedSignature(@NotNull KotlinType returnType, @Nullable KotlinType receiverType, @NotNull List<ValueParameterDescriptor> valueParameters, @NotNull List<TypeParameterDescriptor> typeParameters2, @NotNull List<String> signatureErrors, boolean hasStableParameterNames) {
            if (returnType == null) {
                PropagatedSignature.$$$reportNull$$$0(0);
            }
            if (valueParameters == null) {
                PropagatedSignature.$$$reportNull$$$0(1);
            }
            if (typeParameters2 == null) {
                PropagatedSignature.$$$reportNull$$$0(2);
            }
            if (signatureErrors == null) {
                PropagatedSignature.$$$reportNull$$$0(3);
            }
            this.returnType = returnType;
            this.receiverType = receiverType;
            this.valueParameters = valueParameters;
            this.typeParameters = typeParameters2;
            this.signatureErrors = signatureErrors;
            this.hasStableParameterNames = hasStableParameterNames;
        }

        @NotNull
        public KotlinType getReturnType() {
            KotlinType kotlinType = this.returnType;
            if (kotlinType == null) {
                PropagatedSignature.$$$reportNull$$$0(4);
            }
            return kotlinType;
        }

        @Nullable
        public KotlinType getReceiverType() {
            return this.receiverType;
        }

        @NotNull
        public List<ValueParameterDescriptor> getValueParameters() {
            List<ValueParameterDescriptor> list = this.valueParameters;
            if (list == null) {
                PropagatedSignature.$$$reportNull$$$0(5);
            }
            return list;
        }

        @NotNull
        public List<TypeParameterDescriptor> getTypeParameters() {
            List<TypeParameterDescriptor> list = this.typeParameters;
            if (list == null) {
                PropagatedSignature.$$$reportNull$$$0(6);
            }
            return list;
        }

        public boolean hasStableParameterNames() {
            return this.hasStableParameterNames;
        }

        @NotNull
        public List<String> getErrors() {
            List<String> list = this.signatureErrors;
            if (list == null) {
                PropagatedSignature.$$$reportNull$$$0(7);
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
                case 4: 
                case 5: 
                case 6: 
                case 7: {
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
                case 5: 
                case 6: 
                case 7: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "returnType";
                    break;
                }
                case 1: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "valueParameters";
                    break;
                }
                case 2: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "typeParameters";
                    break;
                }
                case 3: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "signatureErrors";
                    break;
                }
                case 4: 
                case 5: 
                case 6: 
                case 7: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/load/java/components/SignaturePropagator$PropagatedSignature";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/load/java/components/SignaturePropagator$PropagatedSignature";
                    break;
                }
                case 4: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getReturnType";
                    break;
                }
                case 5: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getValueParameters";
                    break;
                }
                case 6: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getTypeParameters";
                    break;
                }
                case 7: {
                    objectArray = objectArray2;
                    objectArray2[1] = "getErrors";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "<init>";
                    break;
                }
                case 4: 
                case 5: 
                case 6: 
                case 7: {
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
                case 5: 
                case 6: 
                case 7: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }
}

