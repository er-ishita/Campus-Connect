import java.io.*;
import java.util.*;

public class ClubDirectory implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Club> clubs;
    private Map<String, Student> students;

    public ClubDirectory() {
        clubs = new ArrayList<>();
        students = new HashMap<>();
    }

    public void addClub(Club club) { clubs.add(club); }
    public void addStudent(Student s) { students.put(s.getId(), s); }
    public Club findClub(String name) {
        for (Club c : clubs)
            if (c.getName().equalsIgnoreCase(name))
                return c;
        return null;
    }
    public Student findStudent(String id) { return students.get(id); }

    public void showAllClubs() {
        if (clubs.isEmpty()) System.out.println("No clubs available.");
        else clubs.forEach(System.out::println);
    }

    // Save data
    public void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("club_data.dat"))) {
            out.writeObject(this);
            System.out.println("✅ Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load data
    public static ClubDirectory loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("club_data.dat"))) {
            return (ClubDirectory) in.readObject();
        } catch (Exception e) {
            System.out.println("⚠️ No saved data found, starting fresh.");
            return new ClubDirectory();
        }
    }
}
