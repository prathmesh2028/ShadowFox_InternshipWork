package helpdesk;

import java.util.ArrayList;
import java.util.List;

public class HelpdeskSystem {
    private final List<Ticket> tickets;

    public HelpdeskSystem() {
        this.tickets = new ArrayList<>();
    }

    public boolean addTicket(Ticket ticket) {
        if (ticket == null) {
            return false;
        }

        for (Ticket existingTicket : tickets) {
            if (existingTicket.getTicketId() == ticket.getTicketId()) {
                return false;
            }
        }

        return tickets.add(ticket);
    }

    public Ticket findTicketById(int ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == ticketId) {
                return ticket;
            }
        }
        return null;
    }

    public List<Ticket> getAllTickets() {
        return new ArrayList<>(tickets);
    }

    public boolean updateTicket(int ticketId, String newTitle, String newDescription,
                                Category newCategory, Priority newPriority) {
        Ticket ticket = findTicketById(ticketId);
        if (ticket == null || ticket.isClosed()) {
            return false;
        }

        if (newTitle == null || newTitle.trim().isEmpty()) {
            return false;
        }

        if (newDescription == null || newDescription.trim().isEmpty()) {
            return false;
        }

        ticket.setTitle(newTitle.trim());
        ticket.setDescription(newDescription.trim());
        ticket.setCategory(newCategory);
        ticket.setPriority(newPriority);
        return true;
    }

    public boolean startProcessing(int ticketId) {
        return changeStatus(ticketId, TicketStatus.IN_PROGRESS);
    }

    public boolean resolveTicket(int ticketId) {
        return changeStatus(ticketId, TicketStatus.RESOLVED);
    }

    public boolean closeTicket(int ticketId) {
        return changeStatus(ticketId, TicketStatus.CLOSED);
    }

    private boolean changeStatus(int ticketId, TicketStatus targetStatus) {
        Ticket ticket = findTicketById(ticketId);
        if (ticket == null) {
            return false;
        }

        if (!ticket.getStatus().canTransitionTo(targetStatus)) {
            return false;
        }

        ticket.setStatus(targetStatus);
        return true;
    }
}
