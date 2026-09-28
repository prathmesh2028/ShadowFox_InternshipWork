# Student Helpdesk & Support Ticket System

## Overview

This application is a beginner-friendly Java console program designed to manage student support tickets for an internal helpdesk. It allows staff to create, update, search, and close tickets while tracking their lifecycle and enforcing validation rules.

## Features

- Create new student support tickets
- Update ticket details while the ticket is still active
- Start processing a ticket
- Resolve and close tickets
- View a single ticket by ID
- View all tickets in the system
- Prevent duplicate ticket IDs
- Validate empty or invalid input
- Enforce proper ticket status transitions
- Keep all business logic organized in separate classes

## Technologies Used

- Java
- OOP
- Collections
- Enums
- Exception Handling
- Scanner

## Project Structure

- Main: Handles menu navigation and user interaction
- HelpdeskSystem: Stores tickets and manages ticket operations and business rules
- Ticket: Represents each support ticket and holds its data and status
- User: Represents the student who raised the ticket
- Category: Enum for different ticket categories
- Priority: Enum for ticket priorities
- TicketStatus: Enum for the ticket lifecycle

## Ticket Lifecycle

OPEN
↓
IN_PROGRESS
↓
RESOLVED
↓
CLOSED

## Validation

The system validates the following conditions:

- Duplicate ticket ID prevention
- Empty title and description prevention
- Invalid menu input handling
- Invalid status transition handling
- Empty student name and email prevention
- Invalid category and priority selection prevention

## How to Run

Open a terminal in the project folder and run:

```bash
javac -d out src/helpdesk/*.java
java -cp out helpdesk.Main
```

## Example Workflow

Create ticket
→ Start processing
→ Resolve
→ Close

## Notes

This project uses a simple console interface and does not require any external libraries or frameworks.
