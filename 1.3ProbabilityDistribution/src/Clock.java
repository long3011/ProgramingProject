import java.util.concurrent.atomic.AtomicLong;

/**
 * Singleton logical clock for a simulator.
 * Time is represented as ticks (long) and can be read and modified.
 */
public final class Clock {
    // Lazy-loaded, thread-safe Singleton holder
    private static final class Holder {
        private static final Clock INSTANCE = new Clock();
    }

    private final AtomicLong time = new AtomicLong(0L);

    private Clock() {
        // Prevent external instantiation
    }

    public static Clock getInstance() {
        return Holder.INSTANCE;
    }

    /** Returns current clock time (ticks). */
    public long now() {
        return time.get();
    }

    /** Sets the clock time to a specific value (ticks). */
    public void set(long newTime) {
        time.set(newTime);
    }

    /** Advances the clock by delta ticks and returns the new time. */
    public long advance(long delta) {
        return time.addAndGet(delta);
    }

    /** Resets the clock to zero. */
    public void reset() {
        time.set(0L);
    }

    @Override
    public String toString() {
        return "Clock{time=" + now() + "}";
    }
}
