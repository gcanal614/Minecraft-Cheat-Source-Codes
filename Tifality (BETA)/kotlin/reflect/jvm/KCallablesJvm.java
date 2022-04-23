/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.ReflectJvmMapping;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.UtilKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\",\u0010\u0002\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0000\u001a\u00020\u00018F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0002\u0010\u0004\"\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"value", "", "isAccessible", "Lkotlin/reflect/KCallable;", "(Lkotlin/reflect/KCallable;)Z", "setAccessible", "(Lkotlin/reflect/KCallable;Z)V", "kotlin-reflection"})
@JvmName(name="KCallablesJvm")
public final class KCallablesJvm {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static final boolean isAccessible(@NotNull KCallable<?> $this$isAccessible) {
        Object v15;
        Intrinsics.checkNotNullParameter($this$isAccessible, "$this$isAccessible");
        KCallable<?> kCallable = $this$isAccessible;
        if (kCallable instanceof KMutableProperty) {
            Field field = ReflectJvmMapping.getJavaField((KProperty)$this$isAccessible);
            if (!(field != null ? field.isAccessible() : true)) return false;
            Method method = ReflectJvmMapping.getJavaGetter((KProperty)$this$isAccessible);
            if (!(method != null ? method.isAccessible() : true)) return false;
            Method method2 = ReflectJvmMapping.getJavaSetter((KMutableProperty)$this$isAccessible);
            if (method2 == null) return true;
            boolean bl = method2.isAccessible();
            if (!bl) return false;
            return true;
        }
        if (kCallable instanceof KProperty) {
            Field field = ReflectJvmMapping.getJavaField((KProperty)$this$isAccessible);
            if (!(field != null ? field.isAccessible() : true)) return false;
            Method method = ReflectJvmMapping.getJavaGetter((KProperty)$this$isAccessible);
            if (method == null) return true;
            boolean bl = method.isAccessible();
            if (!bl) return false;
            return true;
        }
        if (kCallable instanceof KProperty.Getter) {
            Field field = ReflectJvmMapping.getJavaField(((KProperty.Getter)$this$isAccessible).getProperty());
            if (!(field != null ? field.isAccessible() : true)) return false;
            Method method = ReflectJvmMapping.getJavaMethod((KFunction)$this$isAccessible);
            if (method == null) return true;
            boolean bl = method.isAccessible();
            if (!bl) return false;
            return true;
        }
        if (kCallable instanceof KMutableProperty.Setter) {
            Field field = ReflectJvmMapping.getJavaField(((KMutableProperty.Setter)$this$isAccessible).getProperty());
            if (!(field != null ? field.isAccessible() : true)) return false;
            Method method = ReflectJvmMapping.getJavaMethod((KFunction)$this$isAccessible);
            if (method == null) return true;
            boolean bl = method.isAccessible();
            if (!bl) return false;
            return true;
        }
        if (!(kCallable instanceof KFunction)) throw (Throwable)new UnsupportedOperationException("Unknown callable: " + $this$isAccessible + " (" + $this$isAccessible.getClass() + ')');
        Method method = ReflectJvmMapping.getJavaMethod((KFunction)$this$isAccessible);
        if (!(method != null ? method.isAccessible() : true)) return false;
        KCallableImpl<?> kCallableImpl = UtilKt.asKCallableImpl($this$isAccessible);
        if (!((kCallableImpl != null && (kCallableImpl = kCallableImpl.getDefaultCaller()) != null ? kCallableImpl.getMember() : (v15 = null)) instanceof AccessibleObject)) {
            v15 = null;
        }
        AccessibleObject accessibleObject = v15;
        if (!(accessibleObject != null ? accessibleObject.isAccessible() : true)) return false;
        Constructor constructor = ReflectJvmMapping.getJavaConstructor((KFunction)$this$isAccessible);
        if (constructor == null) return true;
        boolean bl = constructor.isAccessible();
        if (!bl) return false;
        return true;
    }

    public static final void setAccessible(@NotNull KCallable<?> $this$isAccessible, boolean value) {
        Intrinsics.checkNotNullParameter($this$isAccessible, "$this$isAccessible");
        KCallable<?> kCallable = $this$isAccessible;
        if (kCallable instanceof KMutableProperty) {
            Field field = ReflectJvmMapping.getJavaField((KProperty)$this$isAccessible);
            if (field != null) {
                field.setAccessible(value);
            }
            Method method = ReflectJvmMapping.getJavaGetter((KProperty)$this$isAccessible);
            if (method != null) {
                method.setAccessible(value);
            }
            Method method2 = ReflectJvmMapping.getJavaSetter((KMutableProperty)$this$isAccessible);
            if (method2 != null) {
                method2.setAccessible(value);
            }
        } else if (kCallable instanceof KProperty) {
            Field field = ReflectJvmMapping.getJavaField((KProperty)$this$isAccessible);
            if (field != null) {
                field.setAccessible(value);
            }
            Method method = ReflectJvmMapping.getJavaGetter((KProperty)$this$isAccessible);
            if (method != null) {
                method.setAccessible(value);
            }
        } else if (kCallable instanceof KProperty.Getter) {
            Field field = ReflectJvmMapping.getJavaField(((KProperty.Getter)$this$isAccessible).getProperty());
            if (field != null) {
                field.setAccessible(value);
            }
            Method method = ReflectJvmMapping.getJavaMethod((KFunction)$this$isAccessible);
            if (method != null) {
                method.setAccessible(value);
            }
        } else if (kCallable instanceof KMutableProperty.Setter) {
            Field field = ReflectJvmMapping.getJavaField(((KMutableProperty.Setter)$this$isAccessible).getProperty());
            if (field != null) {
                field.setAccessible(value);
            }
            Method method = ReflectJvmMapping.getJavaMethod((KFunction)$this$isAccessible);
            if (method != null) {
                method.setAccessible(value);
            }
        } else if (kCallable instanceof KFunction) {
            Object v11;
            Object object;
            Method method = ReflectJvmMapping.getJavaMethod((KFunction)$this$isAccessible);
            if (method != null) {
                method.setAccessible(value);
            }
            if (!(((object = UtilKt.asKCallableImpl($this$isAccessible)) != null && (object = ((KCallableImpl)object).getDefaultCaller()) != null ? object.getMember() : (v11 = null)) instanceof AccessibleObject)) {
                v11 = null;
            }
            AccessibleObject accessibleObject = v11;
            if (accessibleObject != null) {
                accessibleObject.setAccessible(true);
            }
            Constructor constructor = ReflectJvmMapping.getJavaConstructor((KFunction)$this$isAccessible);
            if (constructor != null) {
                constructor.setAccessible(value);
            }
        } else {
            throw (Throwable)new UnsupportedOperationException("Unknown callable: " + $this$isAccessible + " (" + $this$isAccessible.getClass() + ')');
        }
    }
}

