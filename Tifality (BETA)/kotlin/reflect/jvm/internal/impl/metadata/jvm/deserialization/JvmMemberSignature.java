/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class JvmMemberSignature {
    @NotNull
    public abstract String getName();

    @NotNull
    public abstract String getDesc();

    @NotNull
    public final String toString() {
        return this.asString();
    }

    @NotNull
    public abstract String asString();

    private JvmMemberSignature() {
    }

    public /* synthetic */ JvmMemberSignature(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    public static final class Method
    extends JvmMemberSignature {
        @NotNull
        private final String name;
        @NotNull
        private final String desc;

        @Override
        @NotNull
        public String asString() {
            return this.getName() + this.getDesc();
        }

        @Override
        @NotNull
        public String getName() {
            return this.name;
        }

        @Override
        @NotNull
        public String getDesc() {
            return this.desc;
        }

        public Method(@NotNull String name, @NotNull String desc) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            super(null);
            this.name = name;
            this.desc = desc;
        }

        public int hashCode() {
            String string = this.getName();
            String string2 = this.getDesc();
            return (string != null ? string.hashCode() : 0) * 31 + (string2 != null ? string2.hashCode() : 0);
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof Method)) break block3;
                    Method method = (Method)object;
                    if (!Intrinsics.areEqual(this.getName(), method.getName()) || !Intrinsics.areEqual(this.getDesc(), method.getDesc())) break block3;
                }
                return true;
            }
            return false;
        }
    }

    public static final class Field
    extends JvmMemberSignature {
        @NotNull
        private final String name;
        @NotNull
        private final String desc;

        @Override
        @NotNull
        public String asString() {
            return this.getName() + ':' + this.getDesc();
        }

        @Override
        @NotNull
        public String getName() {
            return this.name;
        }

        @Override
        @NotNull
        public String getDesc() {
            return this.desc;
        }

        public Field(@NotNull String name, @NotNull String desc) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            super(null);
            this.name = name;
            this.desc = desc;
        }

        @NotNull
        public final String component1() {
            return this.getName();
        }

        @NotNull
        public final String component2() {
            return this.getDesc();
        }

        public int hashCode() {
            String string = this.getName();
            String string2 = this.getDesc();
            return (string != null ? string.hashCode() : 0) * 31 + (string2 != null ? string2.hashCode() : 0);
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof Field)) break block3;
                    Field field = (Field)object;
                    if (!Intrinsics.areEqual(this.getName(), field.getName()) || !Intrinsics.areEqual(this.getDesc(), field.getDesc())) break block3;
                }
                return true;
            }
            return false;
        }
    }
}

