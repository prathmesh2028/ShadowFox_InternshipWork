package helpdesk;

public enum Category {
    ACADEMIC,
    TECHNICAL,
    HOSTEL,
    FEES,
    LIBRARY,
    OTHER;

    public static void printMenu() {
        System.out.println("Select category:");
        int index = 1;
        for (Category category : values()) {
            System.out.println(index + ". " + category);
            index++;
        }
    }

    public static Category fromOption(int option) {
        Category[] categories = values();
        if (option >= 1 && option <= categories.length) {
            return categories[option - 1];
        }
        return null;
    }
}
