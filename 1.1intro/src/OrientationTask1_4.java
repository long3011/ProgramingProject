import java.util.LinkedList;
import java.util.Scanner;

public class OrientationTask1_4 {
    private final LinkedList<Customer> queue = new LinkedList<>();

    public void addFirstToQueue(Customer c) {
        queue.addFirst(c);
    }

    public Customer removeLastFromQueue() {
        if (queue.isEmpty()) return null;
        return queue.removeLast();
    }

    public void printQueue() {
        System.out.println("Queue (front -> back): ");
        queue.forEach(c -> System.out.print(c.getId() + " "));
        System.out.println();
    }

    public static void main(String[] args) {
        OrientationTask1_4 app = new OrientationTask1_4();
        Scanner sc = new Scanner(System.in);
        System.out.println("Simple queue interface. Commands: q (queue new), d (dequeue), p (print), x (exit)");
        while (true) {
            System.out.print("> ");
            String cmd = sc.nextLine().trim().toLowerCase();
            if (cmd.equals("q")) {
                Customer c = new Customer();
                app.addFirstToQueue(c);
                System.out.println("Enqueued customer id=" + c.getId() + " (startTime recorded).");
            } else if (cmd.equals("d")) {
                Customer c = app.removeLastFromQueue();
                if (c == null) {
                    System.out.println("Queue empty.");
                } else {
                    c.setEndTime(System.nanoTime());
                    System.out.printf("Dequeued id=%d, time spent in queue: %.3f ms\n",
                            c.getId(), c.getTimeSpent() / 1_000_000.0);
                }
            } else if (cmd.equals("p")) {
                app.printQueue();
            } else if (cmd.equals("x")) {
                System.out.println("Exiting.");
                break;
            } else {
                System.out.println("Unknown command.");
            }
        }
        sc.close();
    }
}
