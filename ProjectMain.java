import java.util.*;

class ProjectMain {
    Scanner sc = new Scanner(System.in);
    String id,name,course;
    int age, sem;

    Timetable timetable = new Timetable();
    AssignmentManager assignment= new AssignmentManager();

    ClubMain club;

    public static void main(String[] args) {
        ProjectMain obj=new ProjectMain();
        obj.start();
    }

    public void start(){
        System.out.println("Hello user! Quick registration before we start!");
        System.out.print("Enter your id: ");
        id=sc.nextLine();
        System.out.print("Enter your name: ");
        name = sc.nextLine();
        System.out.print("Enter your age: ");
        age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter your course: ");
        course = sc.nextLine();
        System.out.print("Enter your semester: ");
        sem = sc.nextInt();

        System.out.println("\nRegistration Complete! ");
        System.out.println("| Enrollment: "+id+"\n| Name: " + name + " \n| Age: " + age + " \n| Course: " + course + " \n| Semester: " + sem);
        while (true) {
            System.out.println("\nCampus Connect Main Menu");
            System.out.println("1. Modify Timetable");
            System.out.println("2. Assignment Manager");
            System.out.println("3. Club Directory");
            System.out.println("4. Edit your details");
            System.out.println("5. View your details");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            club = new ClubMain(id, name);

            switch (ch) {
                case 1 -> timetable.openTimetableMenu();
                case 2 -> assignment.openAssignmentMenu();
                case 3 -> club.clubMenu();
                case 4 -> editDetails();
                case 5 -> display();
                case 6 -> {
                    System.out.println("Thank you for using Campus-Connect!");
                    return;
                }
                default -> System.out.println("Invalid choice!Please use appropriate choices only...");
            }
        }
    }

    public void editDetails(){
        System.out.println("\n\n==========EDITING DETAILS============");
        System.out.println("Enter your id: ");
        id=sc.nextLine();
        System.out.print("Enter your name: ");
        name = sc.nextLine();
        System.out.print("Enter your age: ");
        age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter your course: ");
        course = sc.nextLine();
        System.out.print("Enter your semester: ");
        sem = sc.nextInt();

        club=new ClubMain(id,name);

    }

    public void display(){
        System.out.println("\n\n===========STUDENT DETAILS============");
        System.out.println("| Enrollment: "+id+"\n| Name: " + name + " \n| Age: " + age + " \n| Course: " + course + " \n| Semester: " + sem);
    }
}