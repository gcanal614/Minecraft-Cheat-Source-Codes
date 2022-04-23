/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.Arrays;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface JavaClassFinder {
    @Nullable
    public JavaClass findClass(@NotNull Request var1);

    @Nullable
    public JavaPackage findPackage(@NotNull FqName var1);

    @Nullable
    public Set<String> knownClassNamesInPackage(@NotNull FqName var1);

    public static final class Request {
        @NotNull
        private final ClassId classId;
        @Nullable
        private final byte[] previouslyFoundClassFileContent;
        @Nullable
        private final JavaClass outerClass;

        @NotNull
        public final ClassId getClassId() {
            return this.classId;
        }

        public Request(@NotNull ClassId classId, @Nullable byte[] previouslyFoundClassFileContent, @Nullable JavaClass outerClass) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            this.classId = classId;
            this.previouslyFoundClassFileContent = previouslyFoundClassFileContent;
            this.outerClass = outerClass;
        }

        public /* synthetic */ Request(ClassId classId, byte[] byArray, JavaClass javaClass, int n, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n & 2) != 0) {
                byArray = null;
            }
            if ((n & 4) != 0) {
                javaClass = null;
            }
            this(classId, byArray, javaClass);
        }

        @NotNull
        public String toString() {
            return "Request(classId=" + this.classId + ", previouslyFoundClassFileContent=" + Arrays.toString(this.previouslyFoundClassFileContent) + ", outerClass=" + this.outerClass + ")";
        }

        public int hashCode() {
            ClassId classId = this.classId;
            JavaClass javaClass = this.outerClass;
            return ((classId != null ? ((Object)classId).hashCode() : 0) * 31 + (this.previouslyFoundClassFileContent != null ? Arrays.hashCode(this.previouslyFoundClassFileContent) : 0)) * 31 + (javaClass != null ? javaClass.hashCode() : 0);
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof Request)) break block3;
                    Request request = (Request)object;
                    if (!Intrinsics.areEqual(this.classId, request.classId) || !Intrinsics.areEqual(this.previouslyFoundClassFileContent, request.previouslyFoundClassFileContent) || !Intrinsics.areEqual(this.outerClass, request.outerClass)) break block3;
                }
                return true;
            }
            return false;
        }
    }
}

