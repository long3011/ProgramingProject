public class TestEventList {
    public static void main(String[] args) {
        EventList list = new EventList();
        list.addEvent(new Event(10, EventType.ARRIVAL));
        list.addEvent(new Event(5, EventType.EXIT));
        list.addEvent(new Event(15, EventType.ARRIVAL));

        System.out.println("Next event: " + list.getNextEvent());
        System.out.println("Remaining events:");
        list.printEvents();
    }
}
