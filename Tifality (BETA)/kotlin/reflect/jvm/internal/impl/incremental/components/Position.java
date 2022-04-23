/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.incremental.components;

import java.io.Serializable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Position
implements Serializable {
    private final int line;
    private final int column;
    @NotNull
    private static final Position NO_POSITION;
    public static final Companion Companion;

    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    static {
        Companion = new Companion(null);
        NO_POSITION = new Position(-1, -1);
    }

    @NotNull
    public String toString() {
        return "Position(line=" + this.line + ", column=" + this.column + ")";
    }

    public int hashCode() {
        return this.line * 31 + this.column;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof Position)) break block3;
                Position position = (Position)object;
                if (this.line != position.line || this.column != position.column) break block3;
            }
            return true;
        }
        return false;
    }

    public static final class Companion {
        @NotNull
        public final Position getNO_POSITION() {
            return NO_POSITION;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

