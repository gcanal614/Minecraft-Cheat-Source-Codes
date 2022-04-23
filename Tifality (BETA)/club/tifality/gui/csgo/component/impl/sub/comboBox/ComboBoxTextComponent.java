/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.csgo.component.impl.sub.comboBox;

import club.tifality.gui.csgo.component.Component;
import club.tifality.gui.csgo.component.ExpandableComponent;
import club.tifality.gui.csgo.component.PredicateComponent;
import club.tifality.gui.csgo.component.impl.sub.comboBox.ComboBoxComponent;
import club.tifality.gui.csgo.component.impl.sub.text.TextComponent;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class ComboBoxTextComponent
extends Component
implements PredicateComponent,
ExpandableComponent {
    private final ComboBoxComponent comboBoxComponent;
    private final TextComponent textComponent;

    public ComboBoxTextComponent(Component parent, String name, final Supplier<Enum<?>[]> getValues, final Consumer<Integer> setValueByIndex, final Supplier<Enum<?>> getValue, final Supplier<List<Enum<?>>> getValueMultiSelect, final Supplier<Boolean> isVisible, final boolean multiSelect, float x, float y) {
        super(parent, x, y, 40.166668f, 16.0f);
        this.comboBoxComponent = new ComboBoxComponent(this, 0.0f, 6.0f, this.getWidth(), 10.0f){

            @Override
            public boolean isVisible() {
                return (Boolean)isVisible.get();
            }

            @Override
            public Enum<?> getValue() {
                return (Enum)getValue.get();
            }

            @Override
            public void setValue(int index) {
                setValueByIndex.accept(index);
            }

            @Override
            public List<Enum<?>> getMultiSelectValues() {
                return (List)getValueMultiSelect.get();
            }

            @Override
            public boolean isMultiSelectable() {
                return multiSelect;
            }

            @Override
            public Enum<?>[] getValues() {
                return (Enum[])getValues.get();
            }
        };
        this.textComponent = new TextComponent(this, name, 1.0f, 0.0f);
        this.addChild(this.comboBoxComponent);
        this.addChild(this.textComponent);
    }

    public ComboBoxTextComponent(Component parent, String name, Supplier<Enum<?>[]> getValues, Consumer<Integer> setValueByIndex, Supplier<Enum<?>> getValue, Supplier<List<Enum<?>>> getValueMultiSelect, Supplier<Boolean> isVisible, boolean multiSelect) {
        this(parent, name, getValues, setValueByIndex, getValue, getValueMultiSelect, isVisible, multiSelect, 0.0f, 0.0f);
    }

    public ComboBoxTextComponent(Component parent, String name, Supplier<Enum<?>[]> getValues, Consumer<Integer> setValueByIndex, Supplier<Enum<?>> getValue, Supplier<List<Enum<?>>> getValueMultiSelect, boolean multiSelect) {
        this(parent, name, getValues, setValueByIndex, getValue, getValueMultiSelect, () -> true, multiSelect, 0.0f, 0.0f);
    }

    @Override
    public boolean isVisible() {
        return this.comboBoxComponent.isVisible();
    }

    public ComboBoxComponent getComboBoxComponent() {
        return this.comboBoxComponent;
    }

    @Override
    public float getExpandedX() {
        return this.comboBoxComponent.getExpandedX();
    }

    @Override
    public float getExpandedY() {
        return this.getY() + this.textComponent.getHeight();
    }

    @Override
    public float getExpandedWidth() {
        return this.comboBoxComponent.getExpandedWidth();
    }

    @Override
    public float getExpandedHeight() {
        return this.comboBoxComponent.getExpandedHeight();
    }

    @Override
    public void setExpanded(boolean expanded) {
        this.comboBoxComponent.setExpanded(expanded);
    }

    @Override
    public boolean isExpanded() {
        return this.comboBoxComponent.isExpanded();
    }
}

