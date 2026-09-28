package helpdesk;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        HelpdeskSystem helpdeskSystem = new HelpdeskSystem();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice:", 1, 8);

            switch (choice) {
                case 1:
                    createTicket(helpdeskSystem);
                    break;
                case 2:
                    updateTicket(helpdeskSystem);
                    break;
                case 3:
                    changeTicketStatus(helpdeskSystem, "Start ticket processing", TicketStatus.IN_PROGRESS, "OPEN -> IN_PROGRESS");
                    break;
                case 4:
                    changeTicketStatus(helpdeskSystem, "Resolve ticket", TicketStatus.RESOLVED, "IN_PROGRESS -> RESOLVED");
                    break;
                case 5:
                    changeTicketStatus(helpdeskSystem, "Close ticket", TicketStatus.CLOSED, "RESOLVED -> CLOSED");
                    break;
                case 6:
                    viewTicket(helpdeskSystem);
                    break;
                case 7:
                    viewAllTickets(helpdeskSystem);
                    break;
                case 8:
                    System.out.println("Exiting Student Helpdesk System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid menu choice. Please try again.");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("       STUDENT HELPDESK SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Create Ticket");
        System.out.println("2. Update Ticket");
        System.out.println("3. Start Ticket Processing");
        System.out.println("4. Resolve Ticket");
        System.out.println("5. Close Ticket");
        System.out.println("6. View Ticket");
        System.out.println("7. View All Tickets");
        System.out.println("8. Exit");
    }

    private static void createTicket(HelpdeskSystem helpdeskSystem) {
        System.out.println("\nCreate a new ticket");

        int ticketId = readInt("Enter ticket ID:");
        String studentName = readRequiredString("Enter student name:");
        String email = readRequiredString("Enter student email:");
        String title = readRequiredString("Enter ticket title:");
        String description = readRequiredString("Enter ticket description:");
        Category category = readCategory();
        Priority priority = readPriority();

        Ticket existingTicket = helpdeskSystem.findTicketById(ticketId);
        if (existingTicket != null) {
            System.out.println("Ticket ID already exists. Please use a unique ID.");
            return;
        }

        User user = new User(ticketId, studentName, email);
        Ticket ticket = new Ticket(ticketId, title.trim(), description.trim(), user, category, priority);

        boolean added = helpdeskSystem.addTicket(ticket);
        if (added) {
            System.out.println("Ticket created successfully.");
            System.out.println("Status: " + ticket.getStatus());
        } else {
            System.out.println("Unable to create ticket.");
        }
    }

    private static void updateTicket(HelpdeskSystem helpdeskSystem) {
        int ticketId = readInt("Enter ticket ID to update:");
        Ticket ticket = helpdeskSystem.findTicketById(ticketId);

        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }

        if (ticket.isClosed()) {
            System.out.println("Ticket is closed and cannot be modified.");
            return;
        }

        String title = readRequiredString("Enter new title:");
        String description = readRequiredString("Enter new description:");
        Category category = readCategory();
        Priority priority = readPriority();

        boolean updated = helpdeskSystem.updateTicket(ticketId, title, description, category, priority);
        if (updated) {
            System.out.println("Ticket updated successfully.");
        } else {
            System.out.println("Ticket update failed. Please check the values and status.");
        }
    }

    private static void viewTicket(HelpdeskSystem helpdeskSystem) {
        int ticketId = readInt("Enter ticket ID to view:");
        Ticket ticket = helpdeskSystem.findTicketById(ticketId);

        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }

        printTicketDetails(ticket);
    }

    private static void viewAllTickets(HelpdeskSystem helpdeskSystem) {
        List<Ticket> tickets = helpdeskSystem.getAllTickets();
        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
            return;
        }

        System.out.println("\nAll tickets:");
        for (Ticket ticket : tickets) {
            printTicketDetails(ticket);
            System.out.println("----------------------------------------");
        }
    }

    private static void printTicketDetails(Ticket ticket) {
        System.out.println("Ticket ID: " + ticket.getTicketId());
        System.out.println("Student Name: " + ticket.getUser().getName());
        System.out.println("Email: " + ticket.getUser().getEmail());
        System.out.println("Title: " + ticket.getTitle());
        System.out.println("Description: " + ticket.getDescription());
        System.out.println("Category: " + ticket.getCategory());
        System.out.println("Priority: " + ticket.getPriority());
        System.out.println("Status: " + ticket.getStatus());
    }

    private static void changeTicketStatus(HelpdeskSystem helpdeskSystem, String actionMessage,
                                          TicketStatus targetStatus, String transitionText) {
        int ticketId = readInt("Enter ticket ID:");
        Ticket ticket = helpdeskSystem.findTicketById(ticketId);

        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }

        if (ticket.getStatus() == TicketStatus.CLOSED) {
            System.out.println("Closed tickets cannot be modified.");
            return;
        }

        if (targetStatus == TicketStatus.IN_PROGRESS && ticket.getStatus() != TicketStatus.OPEN) {
            System.out.println("Invalid transition: " + ticket.getStatus() + " -> " + targetStatus + ". Valid flow: OPEN -> IN_PROGRESS.");
            return;
        }

        if (targetStatus == TicketStatus.RESOLVED && ticket.getStatus() != TicketStatus.IN_PROGRESS) {
            System.out.println("Invalid transition: " + ticket.getStatus() + " -> " + targetStatus + ". Valid flow: IN_PROGRESS -> RESOLVED.");
            return;
        }

        if (targetStatus == TicketStatus.CLOSED && ticket.getStatus() != TicketStatus.RESOLVED) {
            System.out.println("Invalid transition: " + ticket.getStatus() + " -> " + targetStatus + ". Valid flow: RESOLVED -> CLOSED.");
            return;
        }

        boolean success = false;
        switch (targetStatus) {
            case IN_PROGRESS:
                success = helpdeskSystem.startProcessing(ticketId);
                break;
            case RESOLVED:
                success = helpdeskSystem.resolveTicket(ticketId);
                break;
            case CLOSED:
                success = helpdeskSystem.closeTicket(ticketId);
                break;
            default:
                System.out.println("Invalid status operation.");
                return;
        }

        if (success) {
            System.out.println(actionMessage + " successful.");
            System.out.println("Transition: " + transitionText);
            System.out.println("Current status: " + helpdeskSystem.findTicketById(ticketId).getStatus());
        } else {
            System.out.println("Invalid status transition attempted.");
        }
    }

    private static Category readCategory() {
        Category.printMenu();
        while (true) {
            int option = readInt("Choose category option:");
            Category category = Category.fromOption(option);
            if (category != null) {
                return category;
            }
            System.out.println("Invalid category selection. Please try again.");
        }
    }

    private static Priority readPriority() {
        Priority.printMenu();
        while (true) {
            int option = readInt("Choose priority option:");
            Priority priority = Priority.fromOption(option);
            if (priority != null) {
                return priority;
            }
            System.out.println("Invalid priority selection. Please try again.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " ");
                if (!SCANNER.hasNext()) {
                    System.out.println("Input stream closed. Exiting gracefully.");
                    System.exit(0);
                }
                int value = SCANNER.nextInt();
                SCANNER.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                if (SCANNER.hasNext()) {
                    SCANNER.nextLine();
                } else {
                    System.out.println("Input stream closed. Exiting gracefully.");
                    System.exit(0);
                }
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("Input stream closed. Exiting gracefully.");
                System.exit(0);
            }
        }
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Please enter a number between " + min + " and " + max + ".");
        }
    }

    private static String readRequiredString(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " ");
                if (!SCANNER.hasNextLine()) {
                    System.out.println("Input stream closed. Exiting gracefully.");
                    System.exit(0);
                }
                String value = SCANNER.nextLine().trim();
                if (!value.isEmpty()) {
                    return value;
                }
                System.out.println("This field cannot be empty.");
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("Input stream closed. Exiting gracefully.");
                System.exit(0);
            }
        }
    }
}
