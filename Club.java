import java.io.Serializable;
import java.util.*;

public class Club implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private List<Event> events;
    private List<String> memberIds; // store only student IDs to avoid circular reference

    public Club(String name, String description) {
        this.name = name;
        this.description = description;
        this.events = new ArrayList<>();
        this.memberIds = new ArrayList<>();
    }

    public String getName() { return name; }
    public List<Event> getEvents() { return events; }
    public List<String> getMemberIds() { return memberIds; }

    public void addEvent(Event e) { events.add(e); }

    public void addMember(String studentId) {
        if (!memberIds.contains(studentId)) memberIds.add(studentId);
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }
}
