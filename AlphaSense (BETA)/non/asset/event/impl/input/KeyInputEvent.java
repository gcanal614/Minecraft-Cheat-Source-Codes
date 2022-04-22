package non.asset.event.impl.input;

import non.asset.event.Event;

public class KeyInputEvent extends Event {
    private int key;

    public KeyInputEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
