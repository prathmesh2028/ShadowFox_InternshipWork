package helpdesk;

public enum TicketStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED;

    public boolean canTransitionTo(TicketStatus nextStatus) {
        switch (this) {
            case OPEN:
                return nextStatus == IN_PROGRESS;
            case IN_PROGRESS:
                return nextStatus == RESOLVED;
            case RESOLVED:
                return nextStatus == CLOSED;
            default:
                return false;
        }
    }
}
