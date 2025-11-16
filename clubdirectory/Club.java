package clubdirectory;

import java.util.ArrayList;
import java.util.List;

public class Club {
    private String name;
    private String description;
    private List<Event> events;
    private List<Event> meetings;
    private List<String> memberIds;

    public Club(String name, String description) {
        this.name = name;
        this.description = description;
        this.events = new ArrayList<>();
        this.meetings = new ArrayList<>();
        this.memberIds = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Event> getEvents() { return events; }
    public List<Event> getMeetings() { return meetings; }
    public List<String> getMemberIds() { return memberIds; }

    public void addEvent(Event e) {
        if (e != null && !events.contains(e)) events.add(e);
    }

    public void addMeeting(Event e) {
        if (e != null && !meetings.contains(e)) meetings.add(e);
    }

    public void addMember(String studentId) {
        if (studentId != null && !studentId.isBlank() && !memberIds.contains(studentId)) {
            memberIds.add(studentId);
        }
    }
}
