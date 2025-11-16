import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents an event or task with start and end times.
 */
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

    /**
     * True if two events' time ranges overlap.
     */
    public boolean clashesWith(Event other) {
        if (other == null) return false;
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    @Override
    public String toString() {
        // Friendly ISO-like formatting
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("%s (%s to %s) - %s",
                name,
                startTime.format(f),
                endTime.format(f),
                description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;
        return Objects.equals(name, event.name)
                && Objects.equals(startTime, event.startTime)
                && Objects.equals(endTime, event.endTime)
                && Objects.equals(description, event.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startTime, endTime, description);
    }
}
