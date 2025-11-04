import java.util.PriorityQueue;

public class EventList {
    private PriorityQueue<Event> queue = new PriorityQueue<>();

    public void addEvent(Event e) {
        queue.add(e);
    }

    public Event getNextEvent() {
        return queue.poll();
    }

    public void printEvents() {
        PriorityQueue<Event> copy = new PriorityQueue<>(queue);
        while (!copy.isEmpty()) {
            System.out.println(copy.poll());
        }
    }
}
