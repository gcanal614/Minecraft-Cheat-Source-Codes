/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectClassStructure;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.ReadKotlinClassHeaderAnnotationVisitor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectKotlinClass
implements KotlinJvmBinaryClass {
    @NotNull
    private final Class<?> klass;
    @NotNull
    private final KotlinClassHeader classHeader;
    public static final Factory Factory = new Factory(null);

    @Override
    @NotNull
    public String getLocation() {
        StringBuilder stringBuilder = new StringBuilder();
        String string = this.klass.getName();
        Intrinsics.checkNotNullExpressionValue(string, "klass.name");
        return stringBuilder.append(StringsKt.replace$default(string, '.', '/', false, 4, null)).append(".class").toString();
    }

    @Override
    @NotNull
    public ClassId getClassId() {
        return ReflectClassUtilKt.getClassId(this.klass);
    }

    @Override
    public void loadClassAnnotations(@NotNull KotlinJvmBinaryClass.AnnotationVisitor visitor2, @Nullable byte[] cachedContents) {
        Intrinsics.checkNotNullParameter(visitor2, "visitor");
        ReflectClassStructure.INSTANCE.loadClassAnnotations(this.klass, visitor2);
    }

    @Override
    public void visitMembers(@NotNull KotlinJvmBinaryClass.MemberVisitor visitor2, @Nullable byte[] cachedContents) {
        Intrinsics.checkNotNullParameter(visitor2, "visitor");
        ReflectClassStructure.INSTANCE.visitMembers(this.klass, visitor2);
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof ReflectKotlinClass && Intrinsics.areEqual(this.klass, ((ReflectKotlinClass)other).klass);
    }

    public int hashCode() {
        return this.klass.hashCode();
    }

    @NotNull
    public String toString() {
        return this.getClass().getName() + ": " + this.klass;
    }

    @NotNull
    public final Class<?> getKlass() {
        return this.klass;
    }

    @Override
    @NotNull
    public KotlinClassHeader getClassHeader() {
        return this.classHeader;
    }

    private ReflectKotlinClass(Class<?> klass, KotlinClassHeader classHeader) {
        this.klass = klass;
        this.classHeader = classHeader;
    }

    public /* synthetic */ ReflectKotlinClass(Class klass, KotlinClassHeader classHeader, DefaultConstructorMarker $constructor_marker) {
        this(klass, classHeader);
    }

    public static final class Factory {
        @Nullable
        public final ReflectKotlinClass create(@NotNull Class<?> klass) {
            Intrinsics.checkNotNullParameter(klass, "klass");
            ReadKotlinClassHeaderAnnotationVisitor headerReader = new ReadKotlinClassHeaderAnnotationVisitor();
            ReflectClassStructure.INSTANCE.loadClassAnnotations(klass, headerReader);
            KotlinClassHeader kotlinClassHeader = headerReader.createHeader();
            if (kotlinClassHeader == null) {
                return null;
            }
            Intrinsics.checkNotNullExpressionValue(kotlinClassHeader, "headerReader.createHeader() ?: return null");
            return new ReflectKotlinClass(klass, kotlinClassHeader, null);
        }

        private Factory() {
        }

        public /* synthetic */ Factory(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

