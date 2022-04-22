package non.asset.event.impl.player;

import non.asset.event.cancelable.CancelableEvent;

public class PushEvent extends CancelableEvent {
   private boolean pre;
    public PushEvent(boolean pre) {
        this.pre = pre;
    }
    public boolean isPre() {
        return pre;
    }
}
