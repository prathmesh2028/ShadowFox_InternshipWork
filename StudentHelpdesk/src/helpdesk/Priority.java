package helpdesk;

public enum Priority {
    LOW,
    MEDIUM,
    HIGH;

    public static void printMenu() {
        System.out.println("Select priority:");
        int index = 1;
        for (Priority priority : values()) {
            System.out.println(index + ". " + priority);
            index++;
        }
    }

    public static Priority fromOption(int option) {
        Priority[] priorities = values();
        if (option >= 1 && option <= priorities.length) {
            return priorities[option - 1];
        }
        return null;
    }
}
