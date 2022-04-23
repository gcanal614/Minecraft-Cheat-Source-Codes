/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.RenderingUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import org.jetbrains.annotations.NotNull;

public interface ClassifierNamePolicy {
    @NotNull
    public String renderClassifier(@NotNull ClassifierDescriptor var1, @NotNull DescriptorRenderer var2);

    public static final class SHORT
    implements ClassifierNamePolicy {
        public static final SHORT INSTANCE;

        @Override
        @NotNull
        public String renderClassifier(@NotNull ClassifierDescriptor classifier2, @NotNull DescriptorRenderer renderer) {
            Intrinsics.checkNotNullParameter(classifier2, "classifier");
            Intrinsics.checkNotNullParameter(renderer, "renderer");
            if (classifier2 instanceof TypeParameterDescriptor) {
                Name name = ((TypeParameterDescriptor)classifier2).getName();
                Intrinsics.checkNotNullExpressionValue(name, "classifier.name");
                return renderer.renderName(name, false);
            }
            ArrayList<Name> qualifiedNameElements = new ArrayList<Name>();
            DeclarationDescriptor current = classifier2;
            do {
                qualifiedNameElements.add(current.getName());
            } while ((current = current.getContainingDeclaration()) instanceof ClassDescriptor);
            return RenderingUtilsKt.renderFqName(CollectionsKt.asReversedMutable((List)qualifiedNameElements));
        }

        private SHORT() {
        }

        static {
            SHORT sHORT;
            INSTANCE = sHORT = new SHORT();
        }
    }

    public static final class FULLY_QUALIFIED
    implements ClassifierNamePolicy {
        public static final FULLY_QUALIFIED INSTANCE;

        @Override
        @NotNull
        public String renderClassifier(@NotNull ClassifierDescriptor classifier2, @NotNull DescriptorRenderer renderer) {
            Intrinsics.checkNotNullParameter(classifier2, "classifier");
            Intrinsics.checkNotNullParameter(renderer, "renderer");
            if (classifier2 instanceof TypeParameterDescriptor) {
                Name name = ((TypeParameterDescriptor)classifier2).getName();
                Intrinsics.checkNotNullExpressionValue(name, "classifier.name");
                return renderer.renderName(name, false);
            }
            FqNameUnsafe fqNameUnsafe = DescriptorUtils.getFqName(classifier2);
            Intrinsics.checkNotNullExpressionValue(fqNameUnsafe, "DescriptorUtils.getFqName(classifier)");
            return renderer.renderFqName(fqNameUnsafe);
        }

        private FULLY_QUALIFIED() {
        }

        static {
            FULLY_QUALIFIED fULLY_QUALIFIED;
            INSTANCE = fULLY_QUALIFIED = new FULLY_QUALIFIED();
        }
    }

    public static final class SOURCE_CODE_QUALIFIED
    implements ClassifierNamePolicy {
        public static final SOURCE_CODE_QUALIFIED INSTANCE;

        @Override
        @NotNull
        public String renderClassifier(@NotNull ClassifierDescriptor classifier2, @NotNull DescriptorRenderer renderer) {
            Intrinsics.checkNotNullParameter(classifier2, "classifier");
            Intrinsics.checkNotNullParameter(renderer, "renderer");
            return this.qualifiedNameForSourceCode(classifier2);
        }

        private final String qualifiedNameForSourceCode(ClassifierDescriptor descriptor2) {
            Name name = descriptor2.getName();
            Intrinsics.checkNotNullExpressionValue(name, "descriptor.name");
            String nameString = RenderingUtilsKt.render(name);
            if (descriptor2 instanceof TypeParameterDescriptor) {
                return nameString;
            }
            DeclarationDescriptor declarationDescriptor = descriptor2.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "descriptor.containingDeclaration");
            String qualifier = this.qualifierName(declarationDescriptor);
            return qualifier != null && Intrinsics.areEqual(qualifier, "") ^ true ? qualifier + "." + nameString : nameString;
        }

        private final String qualifierName(DeclarationDescriptor descriptor2) {
            String string;
            DeclarationDescriptor declarationDescriptor = descriptor2;
            if (declarationDescriptor instanceof ClassDescriptor) {
                string = this.qualifiedNameForSourceCode((ClassifierDescriptor)descriptor2);
            } else if (declarationDescriptor instanceof PackageFragmentDescriptor) {
                FqNameUnsafe fqNameUnsafe = ((PackageFragmentDescriptor)descriptor2).getFqName().toUnsafe();
                Intrinsics.checkNotNullExpressionValue(fqNameUnsafe, "descriptor.fqName.toUnsafe()");
                string = RenderingUtilsKt.render(fqNameUnsafe);
            } else {
                string = null;
            }
            return string;
        }

        private SOURCE_CODE_QUALIFIED() {
        }

        static {
            SOURCE_CODE_QUALIFIED sOURCE_CODE_QUALIFIED;
            INSTANCE = sOURCE_CODE_QUALIFIED = new SOURCE_CODE_QUALIFIED();
        }
    }
}

