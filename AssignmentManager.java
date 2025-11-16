import java.time.*;
import java.time.format.*;
import java.util.*;

public class AssignmentManager {
    private static class Assignment {
        String courseName;
        String task;
        LocalDate deadline;
        boolean completed;

        Assignment(String courseName, String task, LocalDate deadline) {
            this.courseName = courseName;
            this.task = task;
            this.deadline = deadline;
            this.completed = false;
        }

        void markCompleted() {
            this.completed = true;
        }

        void display() {
            System.out.println("Course: " + courseName);
            System.out.println("Task: " + task);
            System.out.println("Deadline: " + deadline);
            System.out.println("Status: " + (completed ? "Completed" : "Pending"));
            System.out.println("---------------------------------");
        }
    }

    static class InvalidDeadlineException extends Exception {
        public InvalidDeadlineException(String msg) {
            super(msg);
        }
    }

    private final ArrayList<Assignment> assignments = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);

    public void openAssignmentMenu() {
        while (true) {
            System.out.println("\nStudent Task & Assignment Manager");
            System.out.println("1. Add Assignment");
            System.out.println("2. View All Assignments");
            System.out.println("3. Mark Assignment as Completed");
            System.out.println("4. Delete Assignment");
            System.out.println("5. Search course Assignment");
            System.out.println("6. Exit Assignment Menu");
            System.out.print("Enter your choice: ");

            int ch;
            try {
                ch = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input! Enter a number between 1–6.");
                continue;
            }

            try {
                switch (ch) {
                    case 1 -> addAssignment();
                    case 2 -> viewAssignments();
                    case 3 -> markCompleted();
                    case 4 -> deleteAssignment();
                    case 5 -> searchByCourseName();
                    case 6 -> {
                        System.out.println("Returning to Main Menu...");
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            } catch (InvalidDeadlineException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }

    private void addAssignment() throws InvalidDeadlineException {
        System.out.print("\nEnter Course Name: ");
        String course = sc.nextLine().trim();
        System.out.print("Enter Task/Assignment Title: ");
        String title = sc.nextLine().trim();
        System.out.print("Enter Deadline (yyyy-MM-dd): ");
        String dateStr = sc.nextLine().trim();

        if (course.isEmpty() || title.isEmpty())
            throw new InvalidDeadlineException("Course name and task title cannot be empty!");

        LocalDate deadline;
        try {
            deadline = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new InvalidDeadlineException("Invalid date format! Use yyyy-MM-dd.");
        }

        if (deadline.isBefore(LocalDate.now()))
            throw new InvalidDeadlineException("Deadline cannot be before today!");

        assignments.add(new Assignment(course, title, deadline));
        System.out.println("Assignment added successfully!");
    }

    private void viewAssignments() {
        if (assignments.isEmpty()) {
            System.out.println("No assignments found!");
            return;
        }

        System.out.println("\n------Your Assignments ------");
        for (Assignment a : assignments) {
            a.display();
        }
    }

    private void markCompleted() {
        if (assignments.isEmpty()) {
            System.out.println("No assignments available to mark!");
            return;
        }

        System.out.print("Enter Task Title to mark as completed: ");
        String title = sc.nextLine().trim();
        boolean found = false;

        for (Assignment a : assignments) {
            if (a.task.equalsIgnoreCase(title)) {
                if (a.completed) {
                    System.out.println("This task is already marked as completed!");
                } else {
                    a.markCompleted();
                    System.out.println("Task marked as completed!");
                }
                found = true;
                break;
            }
        }

        if (!found)
            System.out.println("No assignment found with that title.");
    }

    private void deleteAssignment() {
        if (assignments.isEmpty()) {
            System.out.println("No assignments to delete!");
            return;
        }

        System.out.print("Enter Task Title to delete: ");
        String title = sc.nextLine().trim();

        Iterator<Assignment> it = assignments.iterator();
        boolean deleted = false;

        while (it.hasNext()) {
            Assignment a = it.next();
            if (a.task.equalsIgnoreCase(title)) {
                it.remove();
                System.out.println("Assignment '" + title + "' deleted successfully!");
                deleted = true;
                break;
            }
        }

        if (!deleted) System.out.println("No assignment found with that title.");
    }

    private void searchByCourseName() {
    System.out.print("\nEnter Course Name to search assignments: ");
    String course = sc.nextLine().trim();

    boolean found = false;

    System.out.println("\n------ Assignments for Course: " + course + " ------");

    for (Assignment a : assignments) {
        if (a.courseName.equalsIgnoreCase(course)) {
            a.display();
            found = true;
        }
    }

    if (!found) {
        System.out.println("No assignments found for this course.");
    }
    }


}

