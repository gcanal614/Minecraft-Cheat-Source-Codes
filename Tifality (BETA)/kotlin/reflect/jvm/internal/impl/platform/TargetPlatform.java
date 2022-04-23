/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.platform;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.reflect.jvm.internal.impl.platform.PlatformUtilKt;
import kotlin.reflect.jvm.internal.impl.platform.SimplePlatform;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TargetPlatform
implements Collection<SimplePlatform>,
KMappedMarker {
    @NotNull
    private final Set<SimplePlatform> componentPlatforms;

    @NotNull
    public String toString() {
        return PlatformUtilKt.getPresentableDescription(this);
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TargetPlatform)) {
            return false;
        }
        return !(Intrinsics.areEqual(this.componentPlatforms, ((TargetPlatform)other).componentPlatforms) ^ true);
    }

    @Override
    public int hashCode() {
        return ((Object)this.componentPlatforms).hashCode();
    }

    @NotNull
    public final Set<SimplePlatform> getComponentPlatforms() {
        return this.componentPlatforms;
    }

    public int getSize() {
        return this.componentPlatforms.size();
    }

    public boolean contains(@NotNull SimplePlatform element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return this.componentPlatforms.contains(element);
    }

    @Override
    public boolean containsAll(@NotNull Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return this.componentPlatforms.containsAll(elements);
    }

    @Override
    public boolean isEmpty() {
        return this.componentPlatforms.isEmpty();
    }

    @Override
    @NotNull
    public Iterator<SimplePlatform> iterator() {
        return this.componentPlatforms.iterator();
    }

    @Override
    public boolean addAll(Collection<? extends SimplePlatform> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public /* synthetic */ boolean add(Object object) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    @Override
    public <T> T[] toArray(T[] TArray) {
        return CollectionToArray.toArray(this, TArray);
    }
}

