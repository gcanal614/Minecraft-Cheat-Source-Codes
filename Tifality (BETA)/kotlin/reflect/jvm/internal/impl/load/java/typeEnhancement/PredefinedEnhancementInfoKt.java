/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedFunctionEnhancementInfo;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancementBuilder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.annotations.NotNull;

public final class PredefinedEnhancementInfoKt {
    private static final JavaTypeQualifiers NULLABLE;
    private static final JavaTypeQualifiers NOT_PLATFORM;
    private static final JavaTypeQualifiers NOT_NULLABLE;
    @NotNull
    private static final Map<String, PredefinedFunctionEnhancementInfo> PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE;

    @NotNull
    public static final Map<String, PredefinedFunctionEnhancementInfo> getPREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE() {
        return PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE;
    }

    static {
        SignatureEnhancementBuilder this_$iv;
        NULLABLE = new JavaTypeQualifiers(NullabilityQualifier.NULLABLE, null, false, false, 8, null);
        NOT_PLATFORM = new JavaTypeQualifiers(NullabilityQualifier.NOT_NULL, null, false, false, 8, null);
        NOT_NULLABLE = new JavaTypeQualifiers(NullabilityQualifier.NOT_NULL, null, true, false, 8, null);
        boolean $i$f$signatures = false;
        SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
        boolean bl = false;
        boolean bl2 = false;
        SignatureBuildingComponents $this$signatures = signatureBuildingComponents;
        boolean bl3 = false;
        String JLObject = $this$signatures.javaLang("Object");
        String JFPredicate = $this$signatures.javaFunction("Predicate");
        String JFFunction = $this$signatures.javaFunction("Function");
        String JFConsumer = $this$signatures.javaFunction("Consumer");
        String JFBiFunction = $this$signatures.javaFunction("BiFunction");
        String JFBiConsumer = $this$signatures.javaFunction("BiConsumer");
        String JFUnaryOperator = $this$signatures.javaFunction("UnaryOperator");
        String JUStream = $this$signatures.javaUtil("stream/Stream");
        String JUOptional = $this$signatures.javaUtil("Optional");
        boolean $i$f$enhancement = false;
        SignatureEnhancementBuilder signatureEnhancementBuilder = new SignatureEnhancementBuilder();
        boolean bl4 = false;
        boolean bl5 = false;
        SignatureEnhancementBuilder $this$enhancement = signatureEnhancementBuilder;
        boolean bl6 = false;
        SignatureEnhancementBuilder signatureEnhancementBuilder2 = $this$enhancement;
        String internalName$iv = $this$signatures.javaUtil("Iterator");
        boolean $i$f$forClass = false;
        SignatureEnhancementBuilder.ClassEnhancementBuilder $this$forClass = new SignatureEnhancementBuilder.ClassEnhancementBuilder(this_$iv, internalName$iv);
        boolean bl7 = false;
        $this$forClass.function("forEachRemaining", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JFConsumer$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
            }
        });
        this_$iv = $this$enhancement;
        internalName$iv = $this$signatures.javaLang("Iterable");
        $i$f$forClass = false;
        $this$forClass = new SignatureEnhancementBuilder.ClassEnhancementBuilder(this_$iv, internalName$iv);
        boolean bl8 = false;
        $this$forClass.function("spliterator", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.returns(this.$this_signatures$inlined.javaUtil("Spliterator"), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
            }
        });
        this_$iv = $this$enhancement;
        internalName$iv = $this$signatures.javaUtil("Collection");
        $i$f$forClass = false;
        $this$forClass = new SignatureEnhancementBuilder.ClassEnhancementBuilder(this_$iv, internalName$iv);
        boolean bl9 = false;
        $this$forClass.function("removeIf", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JFPredicate$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.returns(JvmPrimitiveType.BOOLEAN);
            }
        });
        $this$forClass.function("stream", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.returns(this.$JUStream$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
            }
        });
        $this$forClass.function("parallelStream", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.returns(this.$JUStream$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
            }
        });
        this_$iv = $this$enhancement;
        internalName$iv = $this$signatures.javaUtil("List");
        $i$f$forClass = false;
        $this$forClass = new SignatureEnhancementBuilder.ClassEnhancementBuilder(this_$iv, internalName$iv);
        boolean bl10 = false;
        $this$forClass.function("replaceAll", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JFUnaryOperator$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
            }
        });
        this_$iv = $this$enhancement;
        internalName$iv = $this$signatures.javaUtil("Map");
        $i$f$forClass = false;
        $this$forClass = new SignatureEnhancementBuilder.ClassEnhancementBuilder(this_$iv, internalName$iv);
        boolean bl11 = false;
        $this$forClass.function("forEach", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JFBiConsumer$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
            }
        });
        $this$forClass.function("putIfAbsent", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.returns(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNULLABLE$p());
            }
        });
        $this$forClass.function("replace", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.returns(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNULLABLE$p());
            }
        });
        $this$forClass.function("replace", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.returns(JvmPrimitiveType.BOOLEAN);
            }
        });
        $this$forClass.function("replaceAll", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JFBiFunction$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
            }
        });
        $this$forClass.function("compute", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.parameter(this.$JFBiFunction$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNULLABLE$p(), PredefinedEnhancementInfoKt.access$getNULLABLE$p());
                $this$function.returns(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNULLABLE$p());
            }
        });
        $this$forClass.function("computeIfAbsent", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.parameter(this.$JFFunction$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.returns(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
            }
        });
        $this$forClass.function("computeIfPresent", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.parameter(this.$JFBiFunction$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p(), PredefinedEnhancementInfoKt.access$getNULLABLE$p());
                $this$function.returns(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNULLABLE$p());
            }
        });
        $this$forClass.function("merge", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p());
                $this$function.parameter(this.$JFBiFunction$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p(), PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p(), PredefinedEnhancementInfoKt.access$getNULLABLE$p());
                $this$function.returns(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNULLABLE$p());
            }
        });
        this_$iv = $this$enhancement;
        internalName$iv = JUOptional;
        $i$f$forClass = false;
        $this$forClass = new SignatureEnhancementBuilder.ClassEnhancementBuilder(this_$iv, internalName$iv);
        boolean bl12 = false;
        $this$forClass.function("empty", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.returns(this.$JUOptional$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p());
            }
        });
        $this$forClass.function("of", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p());
                $this$function.returns(this.$JUOptional$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p());
            }
        });
        $this$forClass.function("ofNullable", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNULLABLE$p());
                $this$function.returns(this.$JUOptional$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p());
            }
        });
        $this$forClass.function("get", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.returns(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p());
            }
        });
        $this$forClass.function("ifPresent", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JFConsumer$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p());
            }
        });
        this_$iv = $this$enhancement;
        internalName$iv = $this$signatures.javaLang("ref/Reference");
        $i$f$forClass = false;
        $this$forClass = new SignatureEnhancementBuilder.ClassEnhancementBuilder(this_$iv, internalName$iv);
        boolean bl13 = false;
        $this$forClass.function("get", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.returns(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNULLABLE$p());
            }
        });
        this_$iv = $this$enhancement;
        internalName$iv = JFPredicate;
        $i$f$forClass = false;
        $this$forClass = new SignatureEnhancementBuilder.ClassEnhancementBuilder(this_$iv, internalName$iv);
        boolean bl14 = false;
        $this$forClass.function("test", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.returns(JvmPrimitiveType.BOOLEAN);
            }
        });
        this_$iv = $this$enhancement;
        internalName$iv = $this$signatures.javaFunction("BiPredicate");
        $i$f$forClass = false;
        $this$forClass = new SignatureEnhancementBuilder.ClassEnhancementBuilder(this_$iv, internalName$iv);
        boolean bl15 = false;
        $this$forClass.function("test", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.returns(JvmPrimitiveType.BOOLEAN);
            }
        });
        this_$iv = $this$enhancement;
        internalName$iv = JFConsumer;
        $i$f$forClass = false;
        $this$forClass = new SignatureEnhancementBuilder.ClassEnhancementBuilder(this_$iv, internalName$iv);
        boolean bl16 = false;
        $this$forClass.function("accept", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
            }
        });
        this_$iv = $this$enhancement;
        internalName$iv = JFBiConsumer;
        $i$f$forClass = false;
        $this$forClass = new SignatureEnhancementBuilder.ClassEnhancementBuilder(this_$iv, internalName$iv);
        boolean bl17 = false;
        $this$forClass.function("accept", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
            }
        });
        this_$iv = $this$enhancement;
        internalName$iv = JFFunction;
        $i$f$forClass = false;
        $this$forClass = new SignatureEnhancementBuilder.ClassEnhancementBuilder(this_$iv, internalName$iv);
        boolean bl18 = false;
        $this$forClass.function("apply", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.returns(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
            }
        });
        this_$iv = $this$enhancement;
        internalName$iv = JFBiFunction;
        $i$f$forClass = false;
        $this$forClass = new SignatureEnhancementBuilder.ClassEnhancementBuilder(this_$iv, internalName$iv);
        boolean bl19 = false;
        $this$forClass.function("apply", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.parameter(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
                $this$function.returns(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
            }
        });
        this_$iv = $this$enhancement;
        internalName$iv = $this$signatures.javaFunction("Supplier");
        $i$f$forClass = false;
        $this$forClass = new SignatureEnhancementBuilder.ClassEnhancementBuilder(this_$iv, internalName$iv);
        boolean bl20 = false;
        $this$forClass.function("get", (Function1<? super SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>)new Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit>($this$signatures, JFConsumer, JFPredicate, JUStream, JFUnaryOperator, JFBiConsumer, JLObject, JFBiFunction, JFFunction, JUOptional){
            final /* synthetic */ SignatureBuildingComponents $this_signatures$inlined;
            final /* synthetic */ String $JFConsumer$inlined;
            final /* synthetic */ String $JFPredicate$inlined;
            final /* synthetic */ String $JUStream$inlined;
            final /* synthetic */ String $JFUnaryOperator$inlined;
            final /* synthetic */ String $JFBiConsumer$inlined;
            final /* synthetic */ String $JLObject$inlined;
            final /* synthetic */ String $JFBiFunction$inlined;
            final /* synthetic */ String $JFFunction$inlined;
            final /* synthetic */ String $JUOptional$inlined;
            {
                this.$this_signatures$inlined = signatureBuildingComponents;
                this.$JFConsumer$inlined = string;
                this.$JFPredicate$inlined = string2;
                this.$JUStream$inlined = string3;
                this.$JFUnaryOperator$inlined = string4;
                this.$JFBiConsumer$inlined = string5;
                this.$JLObject$inlined = string6;
                this.$JFBiFunction$inlined = string7;
                this.$JFFunction$inlined = string8;
                this.$JUOptional$inlined = string9;
                super(1);
            }

            public final void invoke(@NotNull SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder $this$function) {
                Intrinsics.checkNotNullParameter($this$function, "$receiver");
                $this$function.returns(this.$JLObject$inlined, PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p());
            }
        });
        PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE = signatureEnhancementBuilder.build();
    }

    public static final /* synthetic */ JavaTypeQualifiers access$getNOT_PLATFORM$p() {
        return NOT_PLATFORM;
    }

    public static final /* synthetic */ JavaTypeQualifiers access$getNULLABLE$p() {
        return NULLABLE;
    }

    public static final /* synthetic */ JavaTypeQualifiers access$getNOT_NULLABLE$p() {
        return NOT_NULLABLE;
    }
}

