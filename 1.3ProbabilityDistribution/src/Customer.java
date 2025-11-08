// filepath: d:\Users\minhl\ProgrammingProject\1.3ProbabilityDistribution\src\Customer.java
import java.util.concurrent.atomic.AtomicLong;

public final class Customer {
    private static final AtomicLong SEQ = new AtomicLong(0);

    private final long id;
    private final long arrivalTime;
    private long departureTime = -1L;

    public Customer(long arrivalTime) {
        this.id = SEQ.incrementAndGet();
        this.arrivalTime = arrivalTime;
    }

    public long getId() { return id; }
    public long getArrivalTime() { return arrivalTime; }
    public long getDepartureTime() { return departureTime; }

    public void depart(long time) { this.departureTime = time; }

    public long timeInSystem() {
        return (departureTime >= 0) ? (departureTime - arrivalTime) : -1L;
    }

    @Override
    public String toString() {
        return "Customer{id=" + id + ", arrival=" + arrivalTime + ", departure=" + departureTime + "}";
    }
}

