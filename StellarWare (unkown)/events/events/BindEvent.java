package stellar.skid.events.events;

import stellar.skid.modules.AbstractModule;

public class BindEvent implements Event {

    private final int keyCode;
    private final String keyName;
    private final AbstractModule module;

    public BindEvent(AbstractModule module, int keyCode, String keyName) {
        this.module = module;
        this.keyCode = keyCode;
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public AbstractModule getModule() {
        return module;
    }
}
