package clubdirectory;

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
    public List<String> getJoinedClubs() { return joinedClubs; }

    public boolean canJoinClub(Club club, ClubDirectory dir) {
        if (club == null) return false;
        if (joinedClubs.contains(club.getName())) return false;
        if (joinedClubs.size() >= 3) return false;

        for (String clubName : joinedClubs) {
            Club existing = dir.findClub(clubName);
            if (existing == null) continue;
            for (Event e1 : existing.getEvents()) {
                for (Event e2 : club.getEvents()) {
                    if (e1.clashesWith(e2)) return false;
                }
            }
        }
        return true;
    }

    public void joinClub(Club club, ClubDirectory dir) {
        if (!canJoinClub(club, dir)) return;
        joinedClubs.add(club.getName());
        club.addMember(id);
        System.out.println("Joined " + club.getName());
    }

    public void leaveClub(Club club) {
        if (!joinedClubs.contains(club.getName())) {
            System.out.println("You are not a member of " + club.getName());
            return;
        }
        joinedClubs.remove(club.getName());
        club.getMemberIds().remove(this.id);
        System.out.println("Left " + club.getName());
    }

    public void addTaskToToDoList(String title, String desc, LocalDateTime start, LocalDateTime end) {
        if (!end.isAfter(start)) {
            System.out.println("End must be after start.");
            return;
        }
        toDoList.add(new Event(title, start, end, desc));
        System.out.println("Task added.");
    }

    public void viewToDoList() {
        if (toDoList.isEmpty()) {
            System.out.println("No tasks.");
            return;
        }
        toDoList.sort(Comparator.comparing(Event::getStartTime));
        for (int i = 0; i < toDoList.size(); i++) {
            System.out.println((i + 1) + ". " + toDoList.get(i));
        }
    }
}
