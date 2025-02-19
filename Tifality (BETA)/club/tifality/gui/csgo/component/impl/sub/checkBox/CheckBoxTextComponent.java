/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.csgo.component.impl.sub.checkBox;

import club.tifality.gui.csgo.component.Component;
import club.tifality.gui.csgo.component.PredicateComponent;
import club.tifality.gui.csgo.component.impl.sub.checkBox.CheckBoxComponent;
import club.tifality.gui.csgo.component.impl.sub.text.TextComponent;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class CheckBoxTextComponent
extends Component
implements PredicateComponent {
    private final CheckBoxComponent checkBox;
    private final TextComponent textComponent;

    public CheckBoxTextComponent(Component parent, String text, final Supplier<Boolean> isChecked, final Consumer<Boolean> onChecked, final Supplier<Boolean> isVisible, float x, float y) {
        super(parent, x, y, 0.0f, 5.0f);
        this.checkBox = new CheckBoxComponent(this, 0.0f, 0.0f, 5.0f, 5.0f){

            @Override
            public boolean isChecked() {
                return (Boolean)isChecked.get();
            }

            @Override
            public void setChecked(boolean checked) {
                onChecked.accept(checked);
            }

            @Override
            public boolean isVisible() {
                return (Boolean)isVisible.get();
            }
        };
        this.textComponent = new TextComponent(this, text, 8.0f, 1.0f);
        this.addChild(this.checkBox);
        this.addChild(this.textComponent);
    }

    public CheckBoxTextComponent(Component parent, String text, Supplier<Boolean> isChecked, Consumer<Boolean> onChecked, Supplier<Boolean> isVisible) {
        this(parent, text, isChecked, onChecked, isVisible, 0.0f, 0.0f);
    }

    public CheckBoxTextComponent(Component parent, String text, Supplier<Boolean> isChecked, Consumer<Boolean> onChecked) {
        this(parent, text, isChecked, onChecked, () -> true);
    }

    @Override
    public float getWidth() {
        return 8.0f + this.textComponent.getWidth();
    }

    @Override
    public boolean isVisible() {
        return this.checkBox.isVisible();
    }
}

