import java.util.concurrent.atomic.AtomicLong;

public final class Event {
    private static final AtomicLong SEQ = new AtomicLong(0);

    private final long id;
    private final EventType type;
    private final long time;

    public Event(EventType type, long time) {
        this.id = SEQ.incrementAndGet();
        this.type = type;
        this.time = time;
    }

    public long getId() { return id; }
    public EventType getType() { return type; }
    public long getTime() { return time; }

    @Override
    public String toString() {
        return "Event{id=" + id + ", type=" + type + ", time=" + time + "}";
    }
}
