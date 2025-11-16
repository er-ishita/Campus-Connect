package clubdirectory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ClubDirectory {
    private List<Club> clubs;
    private Student student;
    private Map<String, String> headPasswords;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ClubDirectory(Student student) {
        this.student = student;
        this.clubs = new ArrayList<>();
        this.headPasswords = new HashMap<>();
        preloadClubs();
    }

    public Student getStudent() { return student; }
    public List<Club> getClubs() { return clubs; }

    public Club findClub(String name) {
        for (Club c : clubs) if (c.getName().equalsIgnoreCase(name)) return c;
        return null;
    }

    public boolean verifyHeadPassword(String clubName, String password) {
        String key = clubName.toLowerCase();
        return headPasswords.containsKey(key) && headPasswords.get(key).equals(password);
    }

    private void preloadClubs() {
        Club c1 = new Club("Coding Club", "Programming and contests");
        Club c2 = new Club("Art Club", "Painting and creativity");
        Club c3 = new Club("Robotics Club", "Robots and engineering");
        Club c4 = new Club("Dance Club", "Dance practice");
        Club c5 = new Club("Singing Club", "Vocal training");

        c1.addEvent(new Event("Codeathon",
                LocalDateTime.parse("2025-12-01 15:00", FORMATTER),
                LocalDateTime.parse("2025-12-01 18:00", FORMATTER),
                "Coding contest"));

        c2.addEvent(new Event("Watercolor Workshop",
                LocalDateTime.parse("2025-12-03 14:00", FORMATTER),
                LocalDateTime.parse("2025-12-03 16:00", FORMATTER),
                "Painting"));

        clubs.add(c1);
        clubs.add(c2);
        clubs.add(c3);
        clubs.add(c4);
        clubs.add(c5);

        headPasswords.put("coding club", "coding123");
        headPasswords.put("art club", "art123");
        headPasswords.put("robotics club", "robo123");
        headPasswords.put("dance club", "dance123");
        headPasswords.put("singing club", "sing123");
    }
}
