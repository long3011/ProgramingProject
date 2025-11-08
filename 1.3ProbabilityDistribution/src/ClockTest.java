/**
 * Simple test program for the Simulator Clock Singleton.
 */
public class ClockTest {
    public static void main(String[] args) {
        Clock c1 = Clock.getInstance();
        Clock c2 = Clock.getInstance();

        System.out.println("Same instance: " + (c1 == c2));

        System.out.println("t0 = " + c1.now());
        c1.set(1_000L);
        System.out.println("t1 = " + c2.now()); // same instance reflects the set

        long t2 = c1.advance(250L);
        System.out.println("t2 = " + t2);

        for (int i = 1; i <= 5; i++) {
            long ti = c1.advance(100L);
            System.out.println("tick " + i + " -> " + ti);
        }

        c1.reset();
        System.out.println("reset -> " + c1.now());
    }
}
