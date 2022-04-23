/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.types.model;

import java.util.ArrayList;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;

public final class ArgumentList
extends ArrayList<TypeArgumentMarker>
implements TypeArgumentListMarker {
    public ArgumentList(int initialSize) {
        super(initialSize);
    }
}

