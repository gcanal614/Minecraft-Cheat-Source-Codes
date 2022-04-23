/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.KotlinMetadataFinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface KotlinClassFinder
extends KotlinMetadataFinder {
    @Nullable
    public Result findKotlinClassOrContent(@NotNull ClassId var1);

    @Nullable
    public Result findKotlinClassOrContent(@NotNull JavaClass var1);

    public static abstract class Result {
        @Nullable
        public final KotlinJvmBinaryClass toKotlinJvmBinaryClass() {
            Result result2 = this;
            if (!(result2 instanceof KotlinClass)) {
                result2 = null;
            }
            KotlinClass kotlinClass2 = (KotlinClass)result2;
            return kotlinClass2 != null ? kotlinClass2.getKotlinJvmBinaryClass() : null;
        }

        private Result() {
        }

        public /* synthetic */ Result(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public static final class KotlinClass
        extends Result {
            @NotNull
            private final KotlinJvmBinaryClass kotlinJvmBinaryClass;

            @NotNull
            public final KotlinJvmBinaryClass getKotlinJvmBinaryClass() {
                return this.kotlinJvmBinaryClass;
            }

            public KotlinClass(@NotNull KotlinJvmBinaryClass kotlinJvmBinaryClass) {
                Intrinsics.checkNotNullParameter(kotlinJvmBinaryClass, "kotlinJvmBinaryClass");
                super(null);
                this.kotlinJvmBinaryClass = kotlinJvmBinaryClass;
            }

            @NotNull
            public String toString() {
                return "KotlinClass(kotlinJvmBinaryClass=" + this.kotlinJvmBinaryClass + ")";
            }

            public int hashCode() {
                KotlinJvmBinaryClass kotlinJvmBinaryClass = this.kotlinJvmBinaryClass;
                return kotlinJvmBinaryClass != null ? kotlinJvmBinaryClass.hashCode() : 0;
            }

            public boolean equals(@Nullable Object object) {
                block3: {
                    block2: {
                        if (this == object) break block2;
                        if (!(object instanceof KotlinClass)) break block3;
                        KotlinClass kotlinClass2 = (KotlinClass)object;
                        if (!Intrinsics.areEqual(this.kotlinJvmBinaryClass, kotlinClass2.kotlinJvmBinaryClass)) break block3;
                    }
                    return true;
                }
                return false;
            }
        }

        public static final class ClassFileContent
        extends Result {
            @NotNull
            private final byte[] content;

            @NotNull
            public final byte[] getContent() {
                return this.content;
            }

            @NotNull
            public String toString() {
                return "ClassFileContent(content=" + Arrays.toString(this.content) + ")";
            }

            public int hashCode() {
                return this.content != null ? Arrays.hashCode(this.content) : 0;
            }

            public boolean equals(@Nullable Object object) {
                block3: {
                    block2: {
                        if (this == object) break block2;
                        if (!(object instanceof ClassFileContent)) break block3;
                        ClassFileContent classFileContent = (ClassFileContent)object;
                        if (!Intrinsics.areEqual(this.content, classFileContent.content)) break block3;
                    }
                    return true;
                }
                return false;
            }
        }
    }
}

