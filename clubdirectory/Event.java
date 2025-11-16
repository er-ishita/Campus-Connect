package clubdirectory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event {
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;

    public Event(String name, LocalDateTime startTime, LocalDateTime endTime, String description) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    public String getName() { return name; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public String getDescription() { return description; }

    public boolean clashesWith(Event other) {
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    @Override
    public String toString() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return name + " (" + startTime.format(f) + " to " + endTime.format(f) + ") - " + description;
    }
}
