/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.ProtocolStringList;

public interface LazyStringList
extends ProtocolStringList {
    public ByteString getByteString(int var1);

    public void add(ByteString var1);

    public List<?> getUnderlyingElements();

    public LazyStringList getUnmodifiableView();
}

