package stellar.skid.events.events;

import stellar.skid.events.events.callables.CancellableEvent;

public class KeyPressEvent extends CancellableEvent {

    private int key;

    public KeyPressEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

}
