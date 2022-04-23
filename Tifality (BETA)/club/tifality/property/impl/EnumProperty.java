/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.property.impl;

import club.tifality.property.Property;
import java.util.function.Supplier;

public class EnumProperty<T extends Enum<T>>
extends Property<T> {
    private final T[] values = this.getEnumConstants();

    public EnumProperty(String label, T value, Supplier<Boolean> dependency) {
        super(label, value, dependency);
    }

    public EnumProperty(String label, T value) {
        this(label, value, () -> true);
    }

    private T[] getEnumConstants() {
        return (Enum[])((Enum)this.value).getClass().getEnumConstants();
    }

    public boolean isSelected(T value) {
        return this.value == value;
    }

    public T[] getValues() {
        return this.values;
    }

    @Override
    public void setValue(int index) {
        this.setValue(this.values[index]);
    }
}

