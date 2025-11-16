import java.util.ArrayList;
import java.util.List;

public class Club {
    private String name;
    private String description;
    private List<Event> events;
    private List<String> memberIds;

    public Club(String name, String description) {
        this.name = name;
        this.description = description;
        this.events = new ArrayList<>();
        this.memberIds = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Event> getEvents() { return events; }
    public List<String> getMemberIds() { return memberIds; }

    public void addEvent(Event e) {
        if (e == null) return;
       if (!events.contains(e)) events.add(e);
        else System.out.println("This event already exists in the club.");
    }

    public void addMember(String studentId) {
        if (studentId == null || studentId.isBlank()) return;
        if (!memberIds.contains(studentId)) memberIds.add(studentId);
    }

    @Override
    public String toString() {
        return name + " - " + description + " (events: " + events.size() + ", members: " + memberIds.size() + ")";
    }
}
