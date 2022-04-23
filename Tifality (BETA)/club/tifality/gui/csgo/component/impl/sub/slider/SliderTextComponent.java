/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.csgo.component.impl.sub.slider;

import club.tifality.gui.csgo.component.Component;
import club.tifality.gui.csgo.component.PredicateComponent;
import club.tifality.gui.csgo.component.impl.sub.slider.SliderComponent;
import club.tifality.gui.csgo.component.impl.sub.text.TextComponent;
import club.tifality.property.impl.Representation;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class SliderTextComponent
extends Component
implements PredicateComponent {
    private static final float SLIDER_THICKNESS = 4.0f;
    private static final int SLIDER_Y_OFFSET = 1;
    private final SliderComponent sliderComponent;

    public SliderTextComponent(Component parent, String text, final Supplier<Double> getValue, final Consumer<Double> setValue, final Supplier<Double> getMin, final Supplier<Double> getMax, final Supplier<Double> getIncrement, final Supplier<Representation> getRepresentation, final Supplier<Boolean> isVisible, float x, float y) {
        super(parent, x, y, 40.166668f, 4.0f);
        this.sliderComponent = new SliderComponent(this, 0.0f, 6.0f, this.getWidth(), 4.0f){

            @Override
            public double getValue() {
                return (Double)getValue.get();
            }

            @Override
            public void setValue(double value) {
                setValue.accept(value);
            }

            @Override
            public Representation getRepresentation() {
                return (Representation)((Object)getRepresentation.get());
            }

            @Override
            public double getMin() {
                return (Double)getMin.get();
            }

            @Override
            public double getMax() {
                return (Double)getMax.get();
            }

            @Override
            public double getIncrement() {
                return (Double)getIncrement.get();
            }

            @Override
            public boolean isVisible() {
                return (Boolean)isVisible.get();
            }
        };
        this.addChild(this.sliderComponent);
        this.addChild(new TextComponent(this, text, 1.0f, 0.0f));
    }

    public SliderTextComponent(Component parent, String text, Supplier<Double> getValue, Consumer<Double> setValue, Supplier<Double> getMin, Supplier<Double> getMax, Supplier<Double> getIncrement, Supplier<Representation> getRepresentation, Supplier<Boolean> isVisible) {
        this(parent, text, getValue, setValue, getMin, getMax, getIncrement, getRepresentation, isVisible, 0.0f, 0.0f);
    }

    public SliderTextComponent(Component parent, String text, Supplier<Double> getValue, Consumer<Double> setValue, Supplier<Double> getMin, Supplier<Double> getMax, Supplier<Double> getIncrement, Supplier<Representation> getRepresentation) {
        this(parent, text, getValue, setValue, getMin, getMax, getIncrement, getRepresentation, () -> true);
    }

    @Override
    public float getHeight() {
        return 6.0f + super.getHeight();
    }

    @Override
    public boolean isVisible() {
        return this.sliderComponent.isVisible();
    }
}

