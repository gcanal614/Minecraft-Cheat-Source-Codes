/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.renderer;

public final class PropertyAccessorRenderingPolicy
extends Enum<PropertyAccessorRenderingPolicy> {
    public static final /* enum */ PropertyAccessorRenderingPolicy PRETTY;
    public static final /* enum */ PropertyAccessorRenderingPolicy DEBUG;
    public static final /* enum */ PropertyAccessorRenderingPolicy NONE;
    private static final /* synthetic */ PropertyAccessorRenderingPolicy[] $VALUES;

    static {
        PropertyAccessorRenderingPolicy[] propertyAccessorRenderingPolicyArray = new PropertyAccessorRenderingPolicy[3];
        PropertyAccessorRenderingPolicy[] propertyAccessorRenderingPolicyArray2 = propertyAccessorRenderingPolicyArray;
        propertyAccessorRenderingPolicyArray[0] = PRETTY = new PropertyAccessorRenderingPolicy();
        propertyAccessorRenderingPolicyArray[1] = DEBUG = new PropertyAccessorRenderingPolicy();
        propertyAccessorRenderingPolicyArray[2] = NONE = new PropertyAccessorRenderingPolicy();
        $VALUES = propertyAccessorRenderingPolicyArray;
    }

    public static PropertyAccessorRenderingPolicy[] values() {
        return (PropertyAccessorRenderingPolicy[])$VALUES.clone();
    }

    public static PropertyAccessorRenderingPolicy valueOf(String string) {
        return Enum.valueOf(PropertyAccessorRenderingPolicy.class, string);
    }
}

