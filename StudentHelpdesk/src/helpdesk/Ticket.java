package helpdesk;

public class Ticket {
    private int ticketId;
    private String title;
    private String description;
    private User user;
    private Category category;
    private Priority priority;
    private TicketStatus status;

    public Ticket(int ticketId, String title, String description, User user,
                 Category category, Priority priority) {
        this.ticketId = ticketId;
        this.title = title;
        this.description = description;
        this.user = user;
        this.category = category;
        this.priority = priority;
        this.status = TicketStatus.OPEN;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public boolean isClosed() {
        return status == TicketStatus.CLOSED;
    }
}
