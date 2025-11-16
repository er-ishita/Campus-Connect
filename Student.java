import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Student {
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
    public List<String> getJoinedClubs() { return joinedClubs; }

    
    public boolean canJoinClub(Club club, ClubDirectory dir) {
        if (club == null) {
            System.out.println(" Club reference is null.");
            return false;
        }
        if (joinedClubs.contains(club.getName())) {
            System.out.println("You are already a member of " + club.getName());
            return false;
        }
        if (joinedClubs.size() >= 3) {
            System.out.println("Cannot join more than 3 clubs.");
            return false;
        }

        // Check clashes: every event of the new club vs every event of existing clubs
        for (String joinedClubName : joinedClubs) {
            Club existing = dir.findClub(joinedClubName);
            if (existing == null) continue;
            for (Event e1 : existing.getEvents()) {
                for (Event e2 : club.getEvents()) {
                    if (e1.clashesWith(e2)) {
                        System.out.println(" Event clash detected:");
                        System.out.println("   " + e1);
                        System.out.println("   " + e2);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void joinClub(Club club, ClubDirectory dir) {
        if (club == null) {
            System.out.println(" Club cannot be null.");
            return;
        }
        if (canJoinClub(club, dir)) {
            joinedClubs.add(club.getName());
            club.addMember(id);
            System.out.println(" " + name + " joined " + club.getName());
        } else {
            System.out.println("Could not join " + club.getName());
        }
    }

     public void leaveClub(Club club) {
    if (club == null) {
        System.out.println(" Club does not exist.");
        return;
    }

    if (!joinedClubs.contains(club.getName())) {
        System.out.println(" You are not a member of " + club.getName());
        return;
    }

    joinedClubs.remove(club.getName());
    club.getMemberIds().remove(this.id);

    System.out.println("You have left the club: " + club.getName());
}


    /**
     * Add a custom task to the student's to-do list with basic validation.
     */
    public void addTaskToToDoList(String title, String description, LocalDateTime start, LocalDateTime end) {
        if (title == null || title.isBlank()) {
            System.out.println("Task title cannot be empty.");
            return;
        }
        if (description == null) description = "";
        if (start == null || end == null) {
            System.out.println("Start and end times are required.");
            return;
        }
        if (end.isBefore(start) || end.isEqual(start)) {
            System.out.println("End time must be after start time.");
            return;
        }

        Event task = new Event(title.trim(), start, end, description.trim());
        toDoList.add(task);
        System.out.println("Task added to To-Do List.");
    }

   
    /**
     * View tasks, sorted by start time (ascending).
     */
    public void viewToDoList() {
        System.out.println("\nTo-Do List for " + name + ":");

        if (toDoList.isEmpty()) {
            System.out.println("No tasks.");
            return;
        }

        toDoList.sort(Comparator.comparing(Event::getStartTime));
        int i = 1;
        for (Event e : toDoList) {
            System.out.println(i++ + ". " + e);
        }
    }
}
