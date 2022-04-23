/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.property;

import club.tifality.Tifality;
import club.tifality.property.ValueChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Property<T> {
    protected final String label;
    protected final Supplier<Boolean> dependency;
    private final List<ValueChangeListener<T>> valueChangeListeners = new ArrayList<ValueChangeListener<T>>();
    protected T value;

    public Property(String label, T value, Supplier<Boolean> dependency) {
        this.label = label;
        this.value = value;
        this.dependency = dependency;
    }

    public Property(String label, T value) {
        this(label, value, () -> Boolean.TRUE);
    }

    public void addValueChangeListener(ValueChangeListener<T> valueChangeListener) {
        this.valueChangeListeners.add(valueChangeListener);
    }

    public boolean isAvailable() {
        return this.dependency.get();
    }

    public String getLabel() {
        return this.label;
    }

    public T getValue() {
        return this.value;
    }

    public T get() {
        return this.value;
    }

    public void setValue(T value) {
        T oldValue = this.value;
        this.value = value;
        if (oldValue != value) {
            for (ValueChangeListener<T> valueChangeListener : this.valueChangeListeners) {
                valueChangeListener.onValueChange(oldValue, value);
            }
        }
        Tifality.getInstance().getConfigManager().saveConfig("Value");
    }

    public void callFirstTime() {
        for (ValueChangeListener<T> valueChangeListener : this.valueChangeListeners) {
            valueChangeListener.onValueChange(this.value, this.value);
        }
    }

    public Class<?> getType() {
        return this.value.getClass();
    }
}

