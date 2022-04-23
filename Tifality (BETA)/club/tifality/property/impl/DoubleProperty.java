/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.property.impl;

import club.tifality.property.Property;
import club.tifality.property.impl.Representation;
import java.util.function.Supplier;

public class DoubleProperty
extends Property<Double> {
    private final double min;
    private final double max;
    private final double increment;
    private final Representation representation;

    public DoubleProperty(String label, double value, Supplier<Boolean> dependency, double min, double max, double increment, Representation representation) {
        super(label, value, dependency);
        this.min = min;
        this.max = max;
        this.increment = increment;
        this.representation = representation;
    }

    public DoubleProperty(String label, double value, Supplier<Boolean> dependency, double min, double max, double increment) {
        this(label, value, dependency, min, max, increment, Representation.DOUBLE);
    }

    public DoubleProperty(String label, double value, double min, double max, double increment, Representation representation) {
        this(label, value, () -> Boolean.TRUE, min, max, increment, representation);
    }

    public DoubleProperty(String label, double value, double min, double max, double increment) {
        this(label, value, () -> Boolean.TRUE, min, max, increment, Representation.DOUBLE);
    }

    public Representation getRepresentation() {
        return this.representation;
    }

    @Override
    public void setValue(Double value) {
        if (this.value != null && ((Double)this.value).doubleValue() != value.doubleValue()) {
            if (value < this.min) {
                value = this.min;
            } else if (value > this.max) {
                value = this.max;
            }
        }
        super.setValue(value);
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public double getIncrement() {
        return this.increment;
    }
}

