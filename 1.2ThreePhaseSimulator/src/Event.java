import java.util.PriorityQueue;

public class Event implements Comparable<Event> {
    private int time;
    private EventType type;

    public Event(int time, EventType type) {
        this.time = time;
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public EventType getType() {
        return type;
    }

    @Override
    public int compareTo(Event other) {
        return this.time - other.time;
    }

    @Override
    public String toString() {
        return "Event{" + "time=" + time + ", type=" + type + '}';
    }
}
