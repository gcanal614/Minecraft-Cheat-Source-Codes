/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Modifier;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.load.java.JavaVisibilities;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaModifierListOwner;
import org.jetbrains.annotations.NotNull;

public interface ReflectJavaModifierListOwner
extends JavaModifierListOwner {
    public int getModifiers();

    public static final class DefaultImpls {
        public static boolean isAbstract(@NotNull ReflectJavaModifierListOwner $this) {
            return Modifier.isAbstract($this.getModifiers());
        }

        public static boolean isStatic(@NotNull ReflectJavaModifierListOwner $this) {
            return Modifier.isStatic($this.getModifiers());
        }

        public static boolean isFinal(@NotNull ReflectJavaModifierListOwner $this) {
            return Modifier.isFinal($this.getModifiers());
        }

        @NotNull
        public static Visibility getVisibility(@NotNull ReflectJavaModifierListOwner $this) {
            int n = $this.getModifiers();
            boolean bl = false;
            boolean bl2 = false;
            int modifiers = n;
            boolean bl3 = false;
            Visibility visibility = Modifier.isPublic(modifiers) ? Visibilities.PUBLIC : (Modifier.isPrivate(modifiers) ? Visibilities.PRIVATE : (Modifier.isProtected(modifiers) ? (Modifier.isStatic(modifiers) ? JavaVisibilities.PROTECTED_STATIC_VISIBILITY : JavaVisibilities.PROTECTED_AND_PACKAGE) : JavaVisibilities.PACKAGE_VISIBILITY));
            Intrinsics.checkNotNullExpressionValue(visibility, "modifiers.let { modifier\u2026Y\n            }\n        }");
            return visibility;
        }
    }
}

