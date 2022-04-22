package stellar.skid.events.events;

import stellar.skid.modules.configurations.property.AbstractProperty;

public class LoadConfigEvent implements Event {

    private final AbstractProperty property;
    private final String value;

    public LoadConfigEvent(AbstractProperty property, String value) {
        this.property = property;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public AbstractProperty getProperty() {
        return property;
    }
}
