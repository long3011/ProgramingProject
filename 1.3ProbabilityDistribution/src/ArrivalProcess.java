import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.DoubleSupplier;

/**
 * Generates arrival events using a provided interarrival-time generator.
 * The event type and generator are properties of this process.
 */
public final class ArrivalProcess {
    private final String eventType;
    private final DoubleSupplier interarrival;

    public ArrivalProcess(String eventType, DoubleSupplier interarrival) {
        if (eventType == null || eventType.isEmpty()) {
            throw new IllegalArgumentException("eventType must not be null or empty");
        }
        if (interarrival == null) {
            throw new IllegalArgumentException("interarrival must not be null");
        }
        this.eventType = eventType;
        this.interarrival = interarrival;
    }

    /**
     * Samples an interarrival, advances the global Clock, creates an Event,
     * appends it to the given list, and returns it.
     */
    public Event scheduleNext(List<Event> list) {
        if (list == null) throw new IllegalArgumentException("list must not be null");

        double dt = interarrival.getAsDouble();
        if (!(dt > 0.0)) dt = 1.0; // ensure strictly positive
        long deltaTicks = Math.max(1L, (long) Math.ceil(dt));

        long t = Clock.getInstance().advance(deltaTicks);
        Event e = new Event(eventType, t);
        list.add(e);
        return e;
    }

    /** Simple event record used by this process. */
    public static final class Event {
        private static final AtomicLong SEQ = new AtomicLong(0);

        private final long id;
        private final String type;
        private final long time;

        public Event(String type, long time) {
            this.id = SEQ.incrementAndGet();
            this.type = type;
            this.time = time;
        }

        public long getId() { return id; }
        public String getType() { return type; }
        public long getTime() { return time; }

        @Override
        public String toString() {
            return "Event{id=" + id + ", type=" + type + ", time=" + time + "}";
        }
    }
}
