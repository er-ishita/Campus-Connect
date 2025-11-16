import java.util.*;

class ProjectMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Timetable timetable = new Timetable();
        ClubMain club=new ClubMain();
        AssignmentManager assignment= new AssignmentManager();

        System.out.println("Hello user! Quick registration before we start!");
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter your course: ");
        String course = sc.nextLine();
        System.out.print("Enter your semester: ");
        int sem = sc.nextInt();

        System.out.println("\nRegistration Complete! ");
        System.out.println("| Name: " + name + " \n| Age: " + age + " \n| Course: " + course + " \n| Semester: " + sem);

        while (true) {
            System.out.println("\nCampus Connect Main Menu");
            System.out.println("1. Modify Timetable");
            System.out.println("2. Assignment Manager");
            System.out.println("3. Club Directory");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> timetable.openTimetableMenu();
                case 2 -> assignment.openAssignmentMenu();
                case 3 -> club.clubMenu();
                case 4 -> {
                    System.out.println("Thank you for using Campus-Connect!");
                    return;
                }
                default -> System.out.println("Invalid choice!Please use appropriate choices only...");
            }
        }
    }
}