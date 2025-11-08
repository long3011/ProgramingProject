import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EventList {
    private final List<Event> events = new ArrayList<>();

    public void add(Event e) { events.add(e); }

    public List<Event> getAll() { return Collections.unmodifiableList(events); }

    public int size() { return events.size(); }

    public void print() { for (Event e : events) System.out.println(e); }
}
