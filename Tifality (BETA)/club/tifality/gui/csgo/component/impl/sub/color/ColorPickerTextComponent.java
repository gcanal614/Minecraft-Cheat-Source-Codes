/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.csgo.component.impl.sub.color;

import club.tifality.gui.csgo.component.Component;
import club.tifality.gui.csgo.component.ExpandableComponent;
import club.tifality.gui.csgo.component.PredicateComponent;
import club.tifality.gui.csgo.component.impl.sub.color.ColorPickerComponent;
import club.tifality.gui.csgo.component.impl.sub.text.TextComponent;
import club.tifality.property.ValueChangeListener;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ColorPickerTextComponent
extends Component
implements PredicateComponent,
ExpandableComponent {
    private final ColorPickerComponent colorPicker;
    private final TextComponent textComponent;

    public ColorPickerTextComponent(Component parent, String text, final Supplier<Integer> getColor, final Consumer<Integer> setColor, final Consumer<ValueChangeListener<Integer>> addValueChangeListener, final Supplier<Boolean> isVisible, float x, float y) {
        super(parent, x, y, 0.0f, 5.0f);
        this.textComponent = new TextComponent(this, text, 1.0f, 1.0f);
        this.colorPicker = new ColorPickerComponent(this, 29.166668f, 0.0f, 11.0f, 5.0f){

            @Override
            public int getColor() {
                return (Integer)getColor.get();
            }

            @Override
            public void setColor(int color) {
                setColor.accept(color);
            }

            @Override
            public void addValueChangeListener(ValueChangeListener<Integer> onValueChange) {
                addValueChangeListener.accept(onValueChange);
            }

            @Override
            public boolean isVisible() {
                return (Boolean)isVisible.get();
            }
        };
        this.addChild(this.colorPicker);
        this.addChild(this.textComponent);
    }

    public ColorPickerTextComponent(Component parent, String text, Supplier<Integer> getColor, Consumer<Integer> setColor, Consumer<ValueChangeListener<Integer>> addValueChangeListener, Supplier<Boolean> isVisible) {
        this(parent, text, getColor, setColor, addValueChangeListener, isVisible, 0.0f, 0.0f);
    }

    public ColorPickerTextComponent(Component parent, String text, Supplier<Integer> getColor, Consumer<Integer> setColor, Consumer<ValueChangeListener<Integer>> addValueChangeListener) {
        this(parent, text, getColor, setColor, addValueChangeListener, () -> true);
    }

    @Override
    public float getWidth() {
        return 13.0f + this.textComponent.getWidth();
    }

    @Override
    public boolean isVisible() {
        return this.colorPicker.isVisible();
    }

    @Override
    public float getExpandedX() {
        return this.colorPicker.getExpandedX();
    }

    @Override
    public float getExpandedY() {
        return this.colorPicker.getY() + this.colorPicker.getHeight();
    }

    @Override
    public float getExpandedWidth() {
        return this.colorPicker.getExpandedWidth();
    }

    @Override
    public float getExpandedHeight() {
        return this.colorPicker.getExpandedHeight();
    }

    @Override
    public void setExpanded(boolean expanded) {
        this.colorPicker.setExpanded(expanded);
    }

    @Override
    public boolean isExpanded() {
        return this.colorPicker.isExpanded();
    }
}

