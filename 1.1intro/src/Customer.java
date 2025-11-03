public class Customer {
    private static int nextId = 1;

    private final int id;
    private long startTime; // nanoseconds
    private long endTime;   // nanoseconds

    public Customer() {
        this.id = nextId++;
        this.startTime = System.nanoTime();
        this.endTime = -1;
    }

    public Customer(int id, long startTime, long endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getTimeSpent() {
        if (endTime < 0) return -1;
        return endTime - startTime; // nanoseconds
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", timeSpent(ns)=" + getTimeSpent() +
                '}';
    }
}