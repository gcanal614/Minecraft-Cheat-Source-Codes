/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.Set;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.renderer.AnnotationArgumentsRenderingPolicy;
import kotlin.reflect.jvm.internal.impl.renderer.ClassifierNamePolicy;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererModifier;
import kotlin.reflect.jvm.internal.impl.renderer.ParameterNameRenderingPolicy;
import kotlin.reflect.jvm.internal.impl.renderer.RenderingFormat;
import org.jetbrains.annotations.NotNull;

public interface DescriptorRendererOptions {
    public void setClassifierNamePolicy(@NotNull ClassifierNamePolicy var1);

    public void setWithDefinedIn(boolean var1);

    public void setModifiers(@NotNull Set<? extends DescriptorRendererModifier> var1);

    public void setStartFromName(boolean var1);

    public boolean getDebugMode();

    public void setDebugMode(boolean var1);

    public void setVerbose(boolean var1);

    public boolean getEnhancedTypes();

    public void setTextFormat(@NotNull RenderingFormat var1);

    @NotNull
    public Set<FqName> getExcludedTypeAnnotationClasses();

    public void setExcludedTypeAnnotationClasses(@NotNull Set<FqName> var1);

    @NotNull
    public AnnotationArgumentsRenderingPolicy getAnnotationArgumentsRenderingPolicy();

    public void setAnnotationArgumentsRenderingPolicy(@NotNull AnnotationArgumentsRenderingPolicy var1);

    public void setParameterNameRenderingPolicy(@NotNull ParameterNameRenderingPolicy var1);

    public void setWithoutTypeParameters(boolean var1);

    public void setReceiverAfterName(boolean var1);

    public void setRenderCompanionObjectName(boolean var1);

    public void setWithoutSuperTypes(boolean var1);

    public static final class DefaultImpls {
        public static boolean getIncludeAnnotationArguments(@NotNull DescriptorRendererOptions $this) {
            return $this.getAnnotationArgumentsRenderingPolicy().getIncludeAnnotationArguments();
        }

        public static boolean getIncludeEmptyAnnotationArguments(@NotNull DescriptorRendererOptions $this) {
            return $this.getAnnotationArgumentsRenderingPolicy().getIncludeEmptyAnnotationArguments();
        }
    }
}

