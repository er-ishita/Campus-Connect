import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ClubMain {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void clubMenu() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your student ID: ");
        String id = sc.nextLine().trim();

        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();

        Student student = new Student(id, name);
        ClubDirectory directory = new ClubDirectory(student);

        while (true) {
            printMenu();
            int choice = safeInt(sc);

            switch (choice) {

                case 1 -> viewAllClubs(directory);

                case 2 -> viewEventsOfClub(sc, directory);

                case 3 -> joinClub(sc, directory);

                case 4 -> viewJoinedClubs(directory);

                case 5 -> addTask(sc, directory);

                case 6 -> student.viewToDoList();

                case 7 -> leaveClubFlow(sc, directory);   

                case 8 -> {
                    System.out.println("Exiting Student Portal. Goodbye!");
                    return;
                }

                default -> System.out.println("Invalid choice. Enter 1–8.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n==== STUDENT PORTAL ====");
        System.out.println("1. View All Clubs");
        System.out.println("2. View Events of a Club");
        System.out.println("3. Join a Club");
        System.out.println("4. View Joined Clubs");
        System.out.println("5. Add Task to To-Do List");
        System.out.println("6. View To-Do List");
        System.out.println("7. Leave a Club");      
        System.out.println("8. Exit");
        System.out.print("Enter choice: ");
    }

    private static void viewAllClubs(ClubDirectory dir) {
        System.out.println("\nAvailable Clubs:");
        for (Club c : dir.getClubs()) {
            System.out.println(" - " + c.getName() + ": " + c.getDescription());
        }
    }

    private static void viewEventsOfClub(Scanner sc, ClubDirectory dir) {
        System.out.print("Enter club name: ");
        String cname = sc.nextLine().trim();
        Club club = dir.findClub(cname);

        if (club == null) {
            System.out.println("Club not found.");
            return;
        }

        System.out.println("\nEvents for " + club.getName() + ":");
        if (club.getEvents().isEmpty()) {
            System.out.println("No events yet.");
            return;
        }
        for (Event e : club.getEvents()) System.out.println(" - " + e);
    }

    private static void joinClub(Scanner sc, ClubDirectory dir) {
        System.out.print("Enter club name to join: ");
        String cname = sc.nextLine().trim();

        Club club = dir.findClub(cname);
        if (club == null) {
            System.out.println(" Club not found.");
            return;
        }

        dir.getStudent().joinClub(club, dir);
    }

    private static void viewJoinedClubs(ClubDirectory dir) {
        Student s = dir.getStudent();
        System.out.println("\nJoined Clubs:");
        if (s.getJoinedClubs().isEmpty()) {
            System.out.println("You have not joined any clubs.");
            return;
        }
        for (String c : s.getJoinedClubs()) {
            System.out.println(" - " + c);
        }
    }

    private static void addTask(Scanner sc, ClubDirectory dir) {
        Student s = dir.getStudent();

        System.out.print("Task title: ");
        String title = sc.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        System.out.print("Task description: ");
        String desc = sc.nextLine().trim();

        LocalDateTime start = safeDate(sc, "Start time (YYYY-MM-DD HH:MM): ");
        LocalDateTime end = safeDate(sc, "End time   (YYYY-MM-DD HH:MM): ");

        if (!end.isAfter(start)) {
            System.out.println("End must be after start.");
            return;
        }

        s.addTaskToToDoList(title, desc, start, end);
    }

    private static void leaveClubFlow(Scanner sc, ClubDirectory dir) {
        Student s = dir.getStudent();

        if (s.getJoinedClubs().isEmpty()) {
            System.out.println("You are not in any club yet.");
            return;
        }

        System.out.println("\n You are currently in:");
        for (String clubName : s.getJoinedClubs()) {
            System.out.println(" - " + clubName);
        }

        System.out.print("\nEnter the name of the club you want to leave: ");
        String cname = sc.nextLine().trim();

        Club club = dir.findClub(cname);
        if (club == null) {
            System.out.println(" Club not found.");
            return;
        }

        s.leaveClub(club);
    }


    private static int safeInt(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Enter a number: ");
            }
        }
    }

    private static LocalDateTime safeDate(Scanner sc, String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return LocalDateTime.parse(sc.nextLine().trim(), FORMATTER);
            } catch (Exception e) {
                System.out.println("Invalid date format. Try again.");
            }
        }
    }
}
