package today.sleek.base.value.value;

import today.sleek.base.value.Value;
import today.sleek.client.modules.impl.Module;

public final class BooleanValue extends Value<Boolean> {

    public BooleanValue(String name, Module owner, Boolean value) {
        super(name, owner, value);
    }

    public BooleanValue(String name, Module owner, Boolean value, Value parent) {
        super(name, owner, value);
        this.parent = parent;
    }

    public BooleanValue(String name, Module owner, Boolean value, ModeValue modeValue, String... mode) {
        super(name, owner, value, modeValue, mode);
    }

    @Override
    public Boolean getValue() {
        return super.getValue();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getValue() == obj || super.equals(obj);
    }
}