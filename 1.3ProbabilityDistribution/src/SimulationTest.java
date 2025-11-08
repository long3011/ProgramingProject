import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleSupplier;

public class SimulationTest {
    public static void main(String[] args) {
        // Create 10 arrival events
        Clock.getInstance().reset();

        DoubleSupplier expMean5 = () -> {
            double u = 1.0 - ThreadLocalRandom.current().nextDouble();
            return -Math.log(u) * 5.0;
        };

        ArrivalProcess ap = new ArrivalProcess("ARRIVAL", expMean5);
        List<ArrivalProcess.Event> apEvents = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ap.scheduleNext(apEvents);
        }
        long lastTime = Clock.getInstance().now();
        System.out.println("Last event time (clock): " + lastTime);

        // Convert to our Event list and customers
        EventList list = new EventList();
        for (ArrivalProcess.Event e : apEvents) {
            list.add(new Event(EventType.ARRIVAL, e.getTime()));
        }
        System.out.println("Events created: " + list.size());

        ServicePoint sp = new ServicePoint();
        for (Event e : list.getAll()) {
            sp.enqueue(new Customer(e.getTime()));
        }
        System.out.println("Queued customers: " + sp.queueSize());

        // Move time forward slightly and then process all customers
        Clock.getInstance().advance(5L);
        long departureTime = Clock.getInstance().now();

        List<Customer> done = sp.processAll(departureTime);
        long total = 0;
        for (Customer c : done) {
            long tSys = c.timeInSystem();
            total += tSys;
            System.out.println("Customer " + c.getId() +
                    " arrival=" + c.getArrivalTime() +
                    " departure=" + c.getDepartureTime() +
                    " timeInSystem=" + tSys);
        }
        System.out.println("Average time in system: " + (done.isEmpty() ? 0.0 : (total / (double) done.size())));
    }
}
