package clubdirectory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("==== LOGIN ====");
            System.out.println("1. Enter as Student");
            System.out.println("2. Enter as Club Head");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = safeInt(sc);

            if (choice == 1) {
                studentPortal(sc);
            } else if (choice == 2) {
                headLogin(sc);
            } else if (choice == 3) {
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void studentPortal(Scanner sc) {

        System.out.print("Enter your student ID: ");
        String id = sc.nextLine().trim();

        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();

        Student student = new Student(id, name);
        ClubDirectory directory = new ClubDirectory(student);

        while (true) {
            printStudentMenu();
            int ch = safeInt(sc);

            switch (ch) {
                case 1 -> viewAllClubs(directory);
                case 2 -> viewEvents(sc, directory);
                case 3 -> joinClub(sc, directory);
                case 4 -> viewJoinedClubs(directory);
                case 5 -> addTask(sc, directory);
                case 6 -> student.viewToDoList();
                case 7 -> leaveClub(sc, directory);
                case 8 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void printStudentMenu() {
        System.out.println("==== STUDENT PORTAL ====");
        System.out.println("1. View All Clubs");
        System.out.println("2. View Events of a Club");
        System.out.println("3. Join a Club");
        System.out.println("4. View Joined Clubs");
        System.out.println("5. Add Task");
        System.out.println("6. View Tasks");
        System.out.println("7. Leave a Club");
        System.out.println("8. Back");
        System.out.print("Enter choice: ");
    }

    private static void viewAllClubs(ClubDirectory dir) {
        for (Club c : dir.getClubs()) {
            System.out.println(" - " + c.getName());
        }
    }

    private static void viewEvents(Scanner sc, ClubDirectory dir) {
        System.out.print("Enter club name: ");
        Club c = dir.findClub(sc.nextLine().trim());
        if (c == null) {
            System.out.println("Club not found.");
            return;
        }
        for (Event e : c.getEvents()) System.out.println(" - " + e);
    }

    private static void joinClub(Scanner sc, ClubDirectory dir) {
        System.out.print("Enter club name: ");
        Club c = dir.findClub(sc.nextLine().trim());
        if (c == null) {
            System.out.println("Club not found.");
            return;
        }
        dir.getStudent().joinClub(c, dir);
    }

    private static void leaveClub(Scanner sc, ClubDirectory dir) {
        Student s = dir.getStudent();
        if (s.getJoinedClubs().isEmpty()) {
            System.out.println("No clubs joined.");
            return;
        }
        System.out.println("Your clubs:");
        for (String c : s.getJoinedClubs()) System.out.println(" - " + c);
        System.out.print("Enter club name to leave: ");
        Club club = dir.findClub(sc.nextLine().trim());
        if (club == null) {
            System.out.println("Not found.");
            return;
        }
        s.leaveClub(club);
    }

    private static void viewJoinedClubs(ClubDirectory dir) {
        Student s = dir.getStudent();
        if (s.getJoinedClubs().isEmpty()) {
            System.out.println("No clubs joined.");
            return;
        }
        for (String c : s.getJoinedClubs()) System.out.println(" - " + c);
    }

    private static void addTask(Scanner sc, ClubDirectory dir) {
        Student s = dir.getStudent();
        System.out.print("Task title: ");
        String title = sc.nextLine().trim();
        System.out.print("Task description: ");
        String desc = sc.nextLine().trim();
        LocalDateTime start = safeDate(sc, "Start (yyyy-MM-dd HH:mm): ");
        LocalDateTime end = safeDate(sc, "End (yyyy-MM-dd HH:mm): ");
        if (!end.isAfter(start)) {
            System.out.println("Invalid times.");
            return;
        }
        s.addTaskToToDoList(title, desc, start, end);
    }

    private static void headLogin(Scanner sc) {

        ClubDirectory dummy = new ClubDirectory(new Student("x", "temp"));

        System.out.print("Enter club name: ");
        String cname = sc.nextLine().trim();

        Club club = dummy.findClub(cname);
        if (club == null) {
            System.out.println("Club not found.");
            return;
        }

        System.out.print("Enter password: ");
        String pass = sc.nextLine().trim();

        if (!dummy.verifyHeadPassword(cname, pass)) {
            System.out.println("Incorrect password.");
            return;
        }

        headPortal(sc, dummy, club);
    }

    private static void headPortal(Scanner sc, ClubDirectory dir, Club club) {
        while (true) {
            System.out.println("==== HEAD PORTAL: " + club.getName() + " ====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Event");
            System.out.println("3. Add Meeting");
            System.out.println("4. Remove Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int ch = safeInt(sc);

            switch (ch) {
                case 1 -> addStudentToClub(sc, club);
                case 2 -> addEventToClub(sc, club);
                case 3 -> addMeeting(sc, club);
                case 4 -> removeStudent(sc, club);
                case 5 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void addStudentToClub(Scanner sc, Club club) {
        System.out.print("Enter student ID: ");
        club.addMember(sc.nextLine().trim());
    }

    private static void addEventToClub(Scanner sc, Club club) {
        System.out.print("Event title: ");
        String name = sc.nextLine().trim();
        System.out.print("Description: ");
        String desc = sc.nextLine().trim();
        LocalDateTime start = safeDate(sc, "Start: ");
        LocalDateTime end = safeDate(sc, "End: ");
        club.addEvent(new Event(name, start, end, desc));
    }

    private static void addMeeting(Scanner sc, Club club) {
        System.out.print("Meeting title: ");
        String name = sc.nextLine().trim();
        System.out.print("Description: ");
        String desc = sc.nextLine().trim();
        LocalDateTime start = safeDate(sc, "Start: ");
        LocalDateTime end = safeDate(sc, "End: ");
        club.addMeeting(new Event(name, start, end, desc));
    }

    private static void removeStudent(Scanner sc, Club club) {
        System.out.print("Enter student ID: ");
        String id = sc.nextLine().trim();
        club.getMemberIds().remove(id);
    }

    private static int safeInt(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Enter number: ");
            }
        }
    }

    private static LocalDateTime safeDate(Scanner sc, String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return LocalDateTime.parse(sc.nextLine().trim(), FORMATTER);
            } catch (Exception e) {
                System.out.println("Invalid date format.");
            }
        }
    }
}
