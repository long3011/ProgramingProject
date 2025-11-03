import java.util.LinkedList;
import java.util.Random;

class ServicePoint {
    private final LinkedList<Customer> queue = new LinkedList<>();
    private final Random rnd = new Random();

    // NOTE: method names follow the assignment's intended meaning but use a readable spelling
    public void addToQueue(Customer a) {
        queue.addFirst(a); // incoming customers added to front
    }

    public Customer removeFromQueue() {
        if (queue.isEmpty()) return null;
        return queue.removeLast();
    }

    /**
     * Serve all customers in the queue. For each customer:
     *  - compute a random service time (ms)
     *  - sleep to simulate service
     *  - set endTime and print response time and service time
     * Returns an array where [0] = totalServiceTime (ns), [1] = count processed
     */
    public long[] serve() {
        long totalServiceTimeNs = 0;
        int processed = 0;

        while (!queue.isEmpty()) {
            Customer c = removeFromQueue();
            if (c == null) break;
            // simulate service time in milliseconds
            long serviceMs = (long) (50 + rnd.nextDouble() * 200); // e.g., 50-250 ms random
            long serviceNs = serviceMs * 1_000_000L;

            // waiting time = now - startTime
            long waitNs = System.nanoTime() - c.getStartTime();

            // simulate service
            try {
                Thread.sleep(serviceMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long finishNs = System.nanoTime();
            c.setEndTime(finishNs);

            long actualServiceNs = serviceNs; // we used sleep so approximate service time
            totalServiceTimeNs += actualServiceNs;
            processed++;

            long responseTimeNs = (c.getTimeSpent() == -1) ? (finishNs - c.getStartTime()) : c.getTimeSpent();
            System.out.printf("Customer %d served. Wait=%.3f ms, Service=%.3f ms, Response=%.3f ms\n",
                    c.getId(),
                    waitNs / 1_000_000.0,
                    actualServiceNs / 1_000_000.0,
                    responseTimeNs / 1_000_000.0);
        }

        return new long[]{totalServiceTimeNs, processed};
    }
}

class CustomerGenerator {
    private final ServicePoint sp;

    public CustomerGenerator(ServicePoint sp) {
        this.sp = sp;
    }

    public void createCustomers(int n) {
        for (int i = 0; i < n; i++) {
            // create customer with start time now
            Customer c = new Customer();
            sp.addToQueue(c);
            // No delay here — creating all at program start as requested
        }
    }
}

public class ServiceSimulation {
    public static void main(String[] args) {
        ServicePoint sp = new ServicePoint();
        CustomerGenerator gen = new CustomerGenerator(sp);

        int numberOfCustomers = 10; // change to test different loads
        gen.createCustomers(numberOfCustomers);
        System.out.println("Created " + numberOfCustomers + " customers in the queue.");

        long[] res = sp.serve();
        long totalServiceNs = res[0];
        int processed = (int) res[1];

        if (processed > 0) {
            double averageServiceMs = (totalServiceNs / (double) processed) / 1_000_000.0;
            System.out.printf("Average service time (ms): %.3f (calculated from %d customers)\n",
                    averageServiceMs, processed);
        } else {
            System.out.println("No customers processed.");
        }
    }
}
