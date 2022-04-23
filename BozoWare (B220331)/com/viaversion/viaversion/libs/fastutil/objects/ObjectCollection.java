// 
// Decompiled by Procyon v0.5.36
// 

package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Iterator;
import java.util.Collection;

public interface ObjectCollection<K> extends Collection<K>, ObjectIterable<K>
{
    ObjectIterator<K> iterator();
}
