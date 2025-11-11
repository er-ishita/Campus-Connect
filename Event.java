import java.io.Serializable;
import java.time.LocalDateTime;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    // Clash detection for overlapping time ranges
    public boolean clashesWith(Event other) {
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    @Override
    public String toString() {
        return name + " (" + startTime + " to " + endTime + ") - " + description;
    }
}
