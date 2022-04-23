/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.WeakClassLoaderBox;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.RuntimeModuleData;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\b\u0010\u0005\u001a\u00020\u0006H\u0000\u001a\u0010\u0010\u0007\u001a\u00020\u0004*\u0006\u0012\u0002\b\u00030\bH\u0000\" \u0010\u0000\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"moduleByClassLoader", "Ljava/util/concurrent/ConcurrentMap;", "Lkotlin/reflect/jvm/internal/WeakClassLoaderBox;", "Ljava/lang/ref/WeakReference;", "Lkotlin/reflect/jvm/internal/impl/descriptors/runtime/components/RuntimeModuleData;", "clearModuleByClassLoaderCache", "", "getOrCreateModule", "Ljava/lang/Class;", "kotlin-reflection"})
public final class ModuleByClassLoaderKt {
    private static final ConcurrentMap<WeakClassLoaderBox, WeakReference<RuntimeModuleData>> moduleByClassLoader = new ConcurrentHashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public static final RuntimeModuleData getOrCreateModule(@NotNull Class<?> $this$getOrCreateModule) {
        Intrinsics.checkNotNullParameter($this$getOrCreateModule, "$this$getOrCreateModule");
        ClassLoader classLoader = ReflectClassUtilKt.getSafeClassLoader($this$getOrCreateModule);
        WeakClassLoaderBox key = new WeakClassLoaderBox(classLoader);
        WeakReference cached = (WeakReference)moduleByClassLoader.get(key);
        if (cached != null) {
            RuntimeModuleData runtimeModuleData = (RuntimeModuleData)cached.get();
            if (runtimeModuleData != null) {
                RuntimeModuleData runtimeModuleData2 = runtimeModuleData;
                boolean bl = false;
                boolean bl2 = false;
                RuntimeModuleData it = runtimeModuleData2;
                boolean bl3 = false;
                RuntimeModuleData runtimeModuleData3 = it;
                Intrinsics.checkNotNullExpressionValue(runtimeModuleData3, "it");
                return runtimeModuleData3;
            }
            moduleByClassLoader.remove(key, cached);
        }
        RuntimeModuleData module = RuntimeModuleData.Companion.create(classLoader);
        try {
            while (true) {
                WeakReference<RuntimeModuleData> weakReference;
                if (moduleByClassLoader.putIfAbsent(key, new WeakReference<RuntimeModuleData>(module)) == null) {
                    RuntimeModuleData runtimeModuleData = module;
                    return runtimeModuleData;
                }
                WeakReference<RuntimeModuleData> ref = weakReference;
                RuntimeModuleData result2 = (RuntimeModuleData)ref.get();
                if (result2 != null) {
                    RuntimeModuleData runtimeModuleData = result2;
                    return runtimeModuleData;
                }
                moduleByClassLoader.remove(key, ref);
            }
        }
        finally {
            key.setTemporaryStrongRef(null);
        }
    }

    public static final void clearModuleByClassLoaderCache() {
        moduleByClassLoader.clear();
    }
}

