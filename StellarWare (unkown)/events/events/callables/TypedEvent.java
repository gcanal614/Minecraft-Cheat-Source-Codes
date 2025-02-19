package stellar.skid.events.events.callables;


import stellar.skid.events.events.Event;
import stellar.skid.events.events.Typed;

/**
 * Abstract example implementation of the Typed interface.
 *
 * @author DarkMagician6
 * @since August 27, 2013
 */
public abstract class TypedEvent implements Event, Typed {

    private final byte type;

    /**
     * Sets the type of the event when it's called.
     *
     * @param eventType The type ID of the event.
     */
    protected TypedEvent(byte eventType) {
        this.type = eventType;
    }

    /**
     * @see Typed#getType()
     */
    @Override
    public byte getType() {
        return this.type;
    }

}
