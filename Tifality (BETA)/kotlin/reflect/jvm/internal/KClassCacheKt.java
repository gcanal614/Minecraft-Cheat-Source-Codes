/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal;

import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.KClassImpl;
import kotlin.reflect.jvm.internal.pcollections.HashPMap;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\b\u0010\u0005\u001a\u00020\u0006H\u0000\u001a&\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\t0\b\"\b\b\u0000\u0010\t*\u00020\u00042\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u000bH\u0000\"*\u0010\u0000\u001a\u001e\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u0002\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00040\u00040\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"K_CLASS_CACHE", "Lkotlin/reflect/jvm/internal/pcollections/HashPMap;", "", "kotlin.jvm.PlatformType", "", "clearKClassCache", "", "getOrCreateKotlinClass", "Lkotlin/reflect/jvm/internal/KClassImpl;", "T", "jClass", "Ljava/lang/Class;", "kotlin-reflection"})
public final class KClassCacheKt {
    private static HashPMap<String, Object> K_CLASS_CACHE;

    @NotNull
    public static final <T> KClassImpl<T> getOrCreateKotlinClass(@NotNull Class<T> jClass) {
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        String name = jClass.getName();
        Object cached = K_CLASS_CACHE.get(name);
        if (cached instanceof WeakReference) {
            KClassImpl kClass;
            KClassImpl kClassImpl = kClass = (KClassImpl)((WeakReference)cached).get();
            if (Intrinsics.areEqual(kClassImpl != null ? kClassImpl.getJClass() : null, jClass)) {
                return kClass;
            }
        } else if (cached != null) {
            WeakReference[] cfr_ignored_0 = (WeakReference[])cached;
            for (WeakReference ref : (WeakReference[])cached) {
                KClassImpl kClass;
                KClassImpl kClassImpl = kClass = (KClassImpl)ref.get();
                if (!Intrinsics.areEqual(kClassImpl != null ? kClassImpl.getJClass() : null, jClass)) continue;
                return kClass;
            }
            int size = ((Object[])cached).length;
            WeakReference[] newArray = new WeakReference[size + 1];
            System.arraycopy(cached, 0, newArray, 0, size);
            KClassImpl<T> newKClass = new KClassImpl<T>(jClass);
            newArray[size] = new WeakReference<KClassImpl<T>>(newKClass);
            HashPMap<String, Object> hashPMap = K_CLASS_CACHE.plus(name, newArray);
            Intrinsics.checkNotNullExpressionValue(hashPMap, "K_CLASS_CACHE.plus(name, newArray)");
            K_CLASS_CACHE = hashPMap;
            return newKClass;
        }
        KClassImpl<T> newKClass = new KClassImpl<T>(jClass);
        HashPMap<String, WeakReference<KClassImpl<T>>> hashPMap = K_CLASS_CACHE.plus(name, new WeakReference<KClassImpl<T>>(newKClass));
        Intrinsics.checkNotNullExpressionValue(hashPMap, "K_CLASS_CACHE.plus(name, WeakReference(newKClass))");
        K_CLASS_CACHE = hashPMap;
        return newKClass;
    }

    public static final void clearKClassCache() {
        HashPMap hashPMap = HashPMap.empty();
        Intrinsics.checkNotNullExpressionValue(hashPMap, "HashPMap.empty()");
        K_CLASS_CACHE = hashPMap;
    }

    static {
        HashPMap hashPMap = HashPMap.empty();
        Intrinsics.checkNotNullExpressionValue(hashPMap, "HashPMap.empty<String, Any>()");
        K_CLASS_CACHE = hashPMap;
    }
}

