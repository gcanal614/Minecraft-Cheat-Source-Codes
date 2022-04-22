package non.asset.event.cancelable;

import non.asset.event.Event;

public class CancelableEvent extends Event {
    private boolean canceled;

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
