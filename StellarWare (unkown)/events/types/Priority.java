package stellar.skid.events.types;

/**
 * The priority for the dispatcher to determine what method should be invoked first.
 * Ram was talking about the memory usage of the way I store the data so I decided
 * to just use bytes for the priority because they take up only 8 bits of memory
 * per value compared to the 32 bits per value of an enum (Same as an integer).
 *
 * @author DarkMagician6
 * @since August 3, 2013
 */
public final class Priority {

    /**
     * Highest priority, called first.
     */
    public static final byte HIGHEST = 0, /**
     * High priority, called after the highest priority.
     */
    HIGH = 1, /**
     * Medium priority, called after the high priority.
     */
    MEDIUM = 2, /**
     * Low priority, called after the medium priority.
     */
    LOW = 3, /**
     * Lowest priority, called after all the other priorities.
     */
    LOWEST = 4;

    /**
     * Array containing all the priority values.
     */
    public static final byte[] VALUE_ARRAY;

    /* Sets up the VALUE_ARRAY the first time anything in this class is called */
    static {
        VALUE_ARRAY = new byte[]{HIGHEST, HIGH, MEDIUM, LOW, LOWEST};
    }

}
