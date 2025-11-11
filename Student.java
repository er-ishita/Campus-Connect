import java.io.Serializable;
import java.util.*;
import java.time.LocalDateTime;


public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private List<String> joinedClubs;
    private List<Event> toDoList;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.joinedClubs = new ArrayList<>();
        this.toDoList = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }

    public boolean canJoinClub(Club club, ClubDirectory dir) {
        if (joinedClubs.size() >= 3) return false;

        // Check for event clash across existing clubs
        for (String clubName : joinedClubs) {
            Club existing = dir.findClub(clubName);
            if (existing != null) {
                for (Event e1 : existing.getEvents()) {
                    for (Event e2 : club.getEvents()) {
                        if (e1.clashesWith(e2)) {
                            System.out.println("⚠️ Event clash detected between:");
                            System.out.println(" - " + e1);
                            System.out.println(" - " + e2);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void joinClub(Club club, ClubDirectory dir) {
        if (canJoinClub(club, dir)) {
            joinedClubs.add(club.getName());
            club.addMember(id);
            System.out.println("✅ " + name + " joined " + club.getName());
        } else {
            System.out.println("❌ Cannot join " + club.getName() + " (limit exceeded or clash found).");
        }
    }

    public void addTaskToToDoList(String title, String description, LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            System.out.println("⚠️ End time cannot be before start time!");
            return;
        }
        Event task = new Event(title, start, end, description);
        toDoList.add(task);
        System.out.println("✅ Task added to To-Do List successfully!");
    }

    public void viewToDoList() {
        System.out.println("\n📅 To-Do List for " + name + ":");
        if (toDoList.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (Event e : toDoList) {
                System.out.println(" - " + e);
            }
        }
    }
}
