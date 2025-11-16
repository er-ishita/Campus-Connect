import java.util.*;

public class Timetable {
    //subclass concept
    static class Entry {
        String courseName;
        String facultyName;
        int startHour;
        int duration;
        int endHour;
        Entry next;

        Entry(String courseName, String facultyName, int startHour, int duration) {
            this.courseName = courseName;
            this.facultyName = facultyName;
            this.startHour = startHour;
            this.duration = duration;
            this.endHour = startHour + duration;
            this.next = null;
        }

        void display() {
            System.out.println(courseName + " |" + facultyName +" | " + startHour + ":00 - " + endHour + ":00");
        }
    }

    static class TimeOverlapException extends Exception {
        public TimeOverlapException(String msg) { super(msg); }
    }

    static class LunchBreakException extends Exception {
        public LunchBreakException(String msg) { super(msg); }
    }

    static class ClassTimeException extends Exception {
        public ClassTimeException(String msg) { super(msg); }
    }

    private Entry[] week = new Entry[6];
    private Scanner sc = new Scanner(System.in);
    private final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public void openTimetableMenu() {
        while (true) {
            System.out.println("\nTimetable Manager");
            System.out.println("1. Add Class");
            System.out.println("2. Display Timetable");
            System.out.println("3. Search by Course Name");
            System.out.println("4. Remove Entry by Day");
            System.out.println("5. Exit Timetable Menu");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            try {
                switch (ch) {
                    case 1 -> addClass();
                    case 2 -> displayAll();
                    case 3 -> searchByCourse();
                    case 4 -> removeEntryByDay();
                    case 5 -> {
                        System.out.println("Returning to Main Menu...");
                        return;
                    }
                    default -> System.out.println("Invalid choice! Try again.");
                }
            } catch (LunchBreakException | TimeOverlapException | ClassTimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addClass() throws LunchBreakException, TimeOverlapException, ClassTimeException {
        System.out.println("\nSelect Day:");
        for (int i = 0; i < days.length; i++) {
            System.out.println((i + 1) + ". " + days[i]);
        }

        int dayIndex = sc.nextInt() - 1;
        sc.nextLine();

        if (dayIndex < 0 || dayIndex > 5) {
            System.out.println("Invalid day!");
            return;
        }

        System.out.print("Enter Course Name: ");
        String course = sc.nextLine();
        System.out.print("Enter Faculty Name: ");
        String faculty = sc.nextLine();
        System.out.print("Enter Start Time (24h format, e.g., 9 for 9AM): ");
        int start = sc.nextInt();
        System.out.print("Enter Duration (in hours): ");
        int duration = sc.nextInt();
        int end = start + duration;

        if (dayIndex == 5) {
            if (start < 9 || end > 12)
                throw new ClassTimeException("Saturday classes allowed only between 9 AM - 12 PM!");
        } else {
            if (start < 9 || end > 17)
                throw new ClassTimeException("Classes allowed only between 9 AM - 5 PM on weekdays!");
        }

        if (start <= 13 && end > 13)
            throw new LunchBreakException("Class cannot overlap lunch break (1 PM - 2 PM)!");

        Entry newClass = new Entry(course, faculty, start, duration);
        week[dayIndex] = insertSorted(week[dayIndex], newClass);
        System.out.println("Class added successfully!");
    }

    private Entry insertSorted(Entry head, Entry newNode) throws TimeOverlapException {
        if (head == null || newNode.startHour < head.startHour) {
            if (head != null && newNode.endHour > head.startHour)
                throw new TimeOverlapException("Class overlaps with " + head.courseName + "!");
            newNode.next = head;
            return newNode;
        }

        Entry curr = head;
        while (curr.next != null && curr.next.startHour < newNode.startHour) {
            curr = curr.next;
        }

        if (curr.endHour > newNode.startHour)
            throw new TimeOverlapException("Class overlaps with " + curr.courseName + "!");

        if (curr.next != null && newNode.endHour > curr.next.startHour)
            throw new TimeOverlapException("Class overlaps with " + curr.next.courseName + "!");

        newNode.next = curr.next;
        curr.next = newNode;
        return head;
    }

    private void displayAll() {
        for (int i = 0; i < days.length; i++) {
            System.out.println("\n------ " + days[i] + " ------");
            Entry temp = week[i];
            if (temp == null) {
                System.out.println("No classes scheduled.");
                continue;
            }
            while (temp != null) {
                temp.display();
                temp = temp.next;
            }
        }
    }

    private void searchByCourse() {
        System.out.print("\nEnter Course Name to search: ");
        String searchCourse = sc.nextLine();
        boolean found = false;

        for (int i = 0; i < week.length; i++) {
            Entry temp = week[i];
            while (temp != null) {
                if (temp.courseName.equalsIgnoreCase(searchCourse)) {
                    System.out.println("Found on " + days[i] + ":");
                    temp.display();
                    found = true;
                }
                temp = temp.next;
            }
        }

        if (!found)
            System.out.println("No class found with course name: " + searchCourse);
    }

    private void removeEntryByDay() {
        System.out.println("\nSelect Day to Remove:");
        for (int i = 0; i < days.length; i++) {
            System.out.println((i + 1) + ". " + days[i]);
        }

        int dayIndex = sc.nextInt() - 1;
        sc.nextLine();

        if (dayIndex < 0 || dayIndex > 5) {
            System.out.println("Invalid day!");
            return;
        }

        if (week[dayIndex] == null) {
            System.out.println("No classes scheduled for that day.");
            return;
        }

        System.out.print("Enter Course Name to remove: ");
        String removeCourse = sc.nextLine();

        Entry curr = week[dayIndex];
        Entry prev = null;
        boolean removed = false;

        while (curr != null) {
            if (curr.courseName.equalsIgnoreCase(removeCourse)) {
                if (prev == null)
                    week[dayIndex] = curr.next;
                else
                    prev.next = curr.next;

                System.out.println("Entry for " + removeCourse + " removed successfully from " + days[dayIndex] + "!");
                removed = true;
                break;
            }
            prev = curr;
            curr = curr.next;
        }

        if (!removed)
            System.out.println("No class found with that course name on selected day.");
    }
}
