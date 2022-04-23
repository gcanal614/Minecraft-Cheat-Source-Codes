/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import org.jetbrains.annotations.NotNull;

public interface DeserializedContainerSource
extends SourceElement {
    @NotNull
    public String getPresentableString();
}

