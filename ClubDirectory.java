import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ClubDirectory {
    private List<Club> clubs;
    private Student student; 
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ClubDirectory(Student student) {
        this.student = student;
        this.clubs = new ArrayList<>();
        preloadClubs();
    }

    public Student getStudent() {
        return student;
    }

    public List<Club> getClubs() {
        return clubs;
    }

    public Club findClub(String name) {
        for (Club c : clubs) {
            if (c.getName().equalsIgnoreCase(name.trim())) return c;
        }
        return null;
    }

    private void preloadClubs() {
        Club coding = new Club("Knuth Coding Club", "Competitive programming & contests");
        Club art = new Club("Aakriti", "Sketching, painting, creativity");
        Club robotics = new Club("UCR Robotics", "Hardware + AI robots");
        Club dance = new Club("Dance Club", "Hip-hop, classical, freestyle dance activities");
        Club singing = new Club("Singing Club", "Vocal training, karaoke, performances");
     
        coding.addEvent(new Event("Codeathon",
                LocalDateTime.parse("2025-12-01 15:00", FORMATTER),
                LocalDateTime.parse("2025-12-01 18:00", FORMATTER),
                "3 hour coding contest"));

        art.addEvent(new Event("Watercolor Workshop",
                LocalDateTime.parse("2025-12-03 14:00", FORMATTER),
                LocalDateTime.parse("2025-12-03 16:00", FORMATTER),
                "Painting workshop"));

        robotics.addEvent(new Event("Robo Race",
                LocalDateTime.parse("2025-12-05 10:00", FORMATTER),
                LocalDateTime.parse("2025-12-05 12:00", FORMATTER),
                "Robot racing competition"));

         dance.addEvent(new Event("Freestyle Dance Night",
            LocalDateTime.parse("2025-12-06 17:00", FORMATTER),
            LocalDateTime.parse("2025-12-06 19:00", FORMATTER),
            "Open dance floor event"));

        dance.addEvent(new Event("Classical Dance Practice",
            LocalDateTime.parse("2025-12-08 15:00", FORMATTER),
            LocalDateTime.parse("2025-12-08 17:00", FORMATTER),
            "Classical dance training session"));

        singing.addEvent(new Event("Karaoke Evening",
            LocalDateTime.parse("2025-12-09 18:00", FORMATTER),
            LocalDateTime.parse("2025-12-09 20:00", FORMATTER),
            "Fun karaoke night"));

         singing.addEvent(new Event("Vocal Warmup Workshop",
            LocalDateTime.parse("2025-12-11 13:00", FORMATTER),
            LocalDateTime.parse("2025-12-11 15:00", FORMATTER),
            "Learn vocal techniques"));

        clubs.add(coding);
        clubs.add(art);
        clubs.add(robotics);
        clubs.add(dance);
        clubs.add(singing);
    }
}
