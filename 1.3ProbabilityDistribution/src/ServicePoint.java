import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

public final class ServicePoint {
    private final Queue<Customer> queue = new LinkedList<>();

    public void enqueue(Customer c) { queue.add(c); }

    public int queueSize() { return queue.size(); }

    public List<Customer> processAll(long departureTime) {
        List<Customer> processed = new ArrayList<>();
        while (!queue.isEmpty()) {
            Customer c = queue.poll();
            c.depart(departureTime);
            processed.add(c);
        }
        return processed;
    }
}
