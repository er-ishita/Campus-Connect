import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClubDirectory directory = ClubDirectory.loadData();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (true) {
            System.out.println("\n==== CLUB DIRECTORY MENU ====");
            System.out.println("1. Add Club");
            System.out.println("2. Add Event to Club");
            System.out.println("3. Add Student");
            System.out.println("4. Join Club");
            System.out.println("5. View All Clubs");
            System.out.println("6. Add Task to To-Do List");     // ✅ moved up
            System.out.println("7. View Student To-Do List");    // ✅ moved down
            System.out.println("8. Save & Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1:
                    System.out.print("Enter club name: ");
                    String cname = sc.nextLine();
                    System.out.print("Enter description: ");
                    String desc = sc.nextLine();
                    directory.addClub(new Club(cname, desc));
                    break;

                case 2:
                    System.out.print("Enter club name: ");
                    cname = sc.nextLine();
                    Club c = directory.findClub(cname);
                    if (c != null) {
                        System.out.print("Enter event name: ");
                        String ename = sc.nextLine();
                        System.out.print("Enter event description: ");
                        desc = sc.nextLine();
                        LocalDateTime start = null, end = null;
                        boolean valid = false;

                        while (!valid) {
                            try {
                                System.out.print("Enter start (YYYY-MM-DD HH:MM): ");
                                start = LocalDateTime.parse(sc.nextLine(), formatter);
                                System.out.print("Enter end (YYYY-MM-DD HH:MM): ");
                                end = LocalDateTime.parse(sc.nextLine(), formatter);
                                if (end.isBefore(start)) {
                                    System.out.println("⚠️ End time cannot be before start time!");
                                } else valid = true;
                            } catch (Exception e) {
                                System.out.println("❌ Invalid date format! Try again (YYYY-MM-DD HH:MM)");
                            }
                        }

                        c.addEvent(new Event(ename, start, end, desc));
                        System.out.println("✅ Event added successfully!");
                    } else System.out.println("Club not found.");
                    break;

                case 3:
                    System.out.print("Enter student ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter student name: ");
                    String sname = sc.nextLine();
                    directory.addStudent(new Student(id, sname));
                    break;

                case 4:
                    System.out.print("Enter student ID: ");
                    id = sc.nextLine();
                    System.out.print("Enter club name: ");
                    cname = sc.nextLine();
                    Student s = directory.findStudent(id);
                    c = directory.findClub(cname);
                    if (s != null && c != null) s.joinClub(c, directory);
                    else System.out.println("Invalid student or club.");
                    break;

                case 5:
                    directory.showAllClubs();
                    break;

                // ✅ CASE 6 — Add Task to To-Do List (moved up)
                case 6:
                    System.out.print("Enter student ID: ");
                    id = sc.nextLine();
                    s = directory.findStudent(id);
                    if (s == null) {
                        System.out.println("❌ Student not found.");
                        break;
                    }

                    System.out.print("Enter task title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter task description: ");
                    desc = sc.nextLine();

                    LocalDateTime tStart = null, tEnd = null;
                    boolean ok = false;
                    while (!ok) {
                        try {
                            System.out.print("Enter task start (YYYY-MM-DD HH:MM): ");
                            tStart = LocalDateTime.parse(sc.nextLine(), formatter);
                            System.out.print("Enter task end (YYYY-MM-DD HH:MM): ");
                            tEnd = LocalDateTime.parse(sc.nextLine(), formatter);

                            if (tEnd.isBefore(tStart)) {
                                System.out.println("⚠️ End time cannot be before start time!");
                            } else ok = true;
                        } catch (Exception e) {
                            System.out.println("❌ Invalid date format! Use YYYY-MM-DD HH:MM");
                        }
                    }

                    s.addTaskToToDoList(title, desc, tStart, tEnd);
                    break;

                // ✅ CASE 7 — View Student To-Do List (moved down)
                case 7:
                    System.out.print("Enter student ID: ");
                    id = sc.nextLine();
                    s = directory.findStudent(id);
                    if (s != null) s.viewToDoList();
                    else System.out.println("Student not found.");
                    break;

                case 8:
                    directory.saveData();
                    System.out.println("💾 Data saved. Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
