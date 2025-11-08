import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleSupplier;

/**
 * Test: create 10 arrival events and print the list.
 */
public class ArrivalProcessTest {
    public static void main(String[] args) {
        // Reset logical time
        Clock.getInstance().reset();

        // Exponential interarrival generator with mean = 5 ticks
        DoubleSupplier expMean5 = () -> {
            double u = 1.0 - ThreadLocalRandom.current().nextDouble(); // (0,1]
            return -Math.log(u) * 5.0;
        };

        ArrivalProcess process = new ArrivalProcess("ARRIVAL", expMean5);
        List<ArrivalProcess.Event> events = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            process.scheduleNext(events);
        }

        System.out.println("Clock time after scheduling: " + Clock.getInstance().now());
        System.out.println("Events (" + events.size() + "):");
        for (ArrivalProcess.Event e : events) {
            System.out.println(e);
        }
    }
}
