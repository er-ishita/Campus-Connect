import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class CampusConnectFrame extends Frame {

    // THEME COLORS 💜
    Color LILAC = new Color(200, 180, 255);
    Color LAVENDER = new Color(230, 220, 255);
    Color PURPLE_TEXT = new Color(70, 40, 120);

    // FONTS
    Font TITLE_FONT = new Font("Arial", Font.BOLD, 20);
    Font SUBTITLE_FONT = new Font("Arial", Font.BOLD, 16);
    Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);

    // CardLayout container
    CardLayout cards = new CardLayout();
    Panel cardPanel = new Panel(cards);

    // student data
    StudentData student = new StudentData();

    // GUI Managers
    TimetableManagerGUI timetable = new TimetableManagerGUI();
    AssignmentManagerGUI assignments = new AssignmentManagerGUI();
    ClubDirectoryGUI clubs = new ClubDirectoryGUI();

    Panel nav;

    public CampusConnectFrame() {
        super("CampusConnect – Lavender Edition");

        setSize(1000, 650);
        setLayout(new BorderLayout());
        setBackground(LAVENDER);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        nav = new Panel(new GridLayout(0,1,5,5));
        nav.setBackground(LILAC);
        nav.setPreferredSize(new Dimension(200, 0));

        addNavButton("Registration", "registration");
        addNavButton("Timetable", "timetable");
        addNavButton("Assignments", "assignments");
        addNavButton("Clubs", "clubs");
        addNavButton("Student Details", "details");

        Button exit = new Button("Exit");
        styleButton(exit);
        exit.addActionListener(e -> System.exit(0));
        nav.add(exit);

        // add panels
        cardPanel.add(new RegistrationPanel(), "registration");
        cardPanel.add(timetable.getPanel(), "timetable");
        cardPanel.add(assignments.getPanel(), "assignments");
        cardPanel.add(clubs.getPanel(), "clubs");
        cardPanel.add(new StudentDetailsPanel(), "details");

        add(nav, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // ---------------------- Styling Helpers ----------------------

    private void styleButton(Button b) {
        b.setBackground(LILAC);
        b.setForeground(PURPLE_TEXT);
        b.setFont(SUBTITLE_FONT);
    }

    private void styleLabel(Label l) {
        l.setForeground(PURPLE_TEXT);
        l.setFont(SUBTITLE_FONT);
    }

    private void styleField(TextField tf) {
        tf.setFont(NORMAL_FONT);
        tf.setBackground(Color.WHITE);
    }

    private void styleArea(TextArea area) {
        area.setFont(NORMAL_FONT);
        area.setBackground(new Color(245, 235, 255));
        area.setForeground(PURPLE_TEXT);
    }

    private void addNavButton(String title, String cardName) {
        Button b = new Button(title);
        styleButton(b);
        b.addActionListener(e -> cards.show(cardPanel, cardName));
        nav.add(b);
    }

    // ============================================================
    //                        PANELS
    // ============================================================

    // 1️⃣ REGISTRATION PANEL
    class RegistrationPanel extends Panel {

        TextField idF = new TextField(20);
        TextField nameF = new TextField(20);
        TextField ageF = new TextField(5);
        TextField courseF = new TextField(20);
        TextField semF = new TextField(5);
        TextArea out = new TextArea(10, 50);

        RegistrationPanel() {
            setLayout(new BorderLayout(10,10));
            setBackground(LAVENDER);

            Panel form = new Panel(new GridLayout(0,2,6,6));
            form.setBackground(LAVENDER);

            Label l1 = new Label("Enrollment ID:");
            Label l2 = new Label("Name:");
            Label l3 = new Label("Age:");
            Label l4 = new Label("Course:");
            Label l5 = new Label("Semester:");

            styleLabel(l1); styleLabel(l2); styleLabel(l3); styleLabel(l4); styleLabel(l5);
            styleField(idF); styleField(nameF); styleField(ageF); styleField(courseF); styleField(semF);

            form.add(l1); form.add(idF);
            form.add(l2); form.add(nameF);
            form.add(l3); form.add(ageF);
            form.add(l4); form.add(courseF);
            form.add(l5); form.add(semF);

            Button save = new Button("Save");
            Button clear = new Button("Clear");
            styleButton(save); styleButton(clear);

            save.addActionListener(e -> {
                try {
                    student.id = idF.getText().trim();
                    student.name = nameF.getText().trim();
                    student.course = courseF.getText().trim();
                    student.age = Integer.parseInt(ageF.getText().trim());
                    student.sem = Integer.parseInt(semF.getText().trim());

                    out.setText("Registration saved!\n\n" + student.displayString());
                    clubs.setStudent(student.id, student.name);

                } catch (Exception ex) {
                    out.setText("Please enter valid numeric values for age/semester.");
                }
            });

            clear.addActionListener(e -> {
                idF.setText(""); nameF.setText(""); ageF.setText(""); 
                courseF.setText(""); semF.setText("");
            });

            Panel btns = new Panel();
            btns.setBackground(LAVENDER);
            btns.add(save); btns.add(clear);

            styleArea(out);

            add(form, BorderLayout.NORTH);
            add(btns, BorderLayout.CENTER);
            add(out, BorderLayout.SOUTH);
        }
    }

    // 2️⃣ STUDENT DETAILS PANEL
    class StudentDetailsPanel extends Panel {
        TextArea out = new TextArea(20, 60);

        StudentDetailsPanel() {
            setBackground(LAVENDER);
            setLayout(new BorderLayout());
            styleArea(out);

            Button refresh = new Button("Refresh");
            styleButton(refresh);
            refresh.addActionListener(e -> out.setText(student.displayString()));

            add(refresh, BorderLayout.NORTH);
            add(out, BorderLayout.CENTER);
        }
    }

    // ============================================================
    //                     TIMETABLE MANAGER GUI
    // ============================================================

    class TimetableManagerGUI {
        Panel panel;
        Choice day;
        TextField courseTF, facultyTF, startTF, durationTF;
        TextArea out;
        String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        ArrayList<Entry>[] week = new ArrayList[6];

        TimetableManagerGUI() {
            for (int i=0;i<6;i++) week[i]=new ArrayList<>();
            build();
        }

        Panel getPanel() { return panel; }

        void build() {
            panel = new Panel(new BorderLayout(10,10));
            panel.setBackground(LAVENDER);

            Panel form = new Panel(new GridLayout(0,2,6,6));
            form.setBackground(LAVENDER);

            day = new Choice();
            for (String d: days) day.add(d);

            courseTF = new TextField(20);
            facultyTF = new TextField(20);
            startTF = new TextField(5);
            durationTF = new TextField(5);

            Label l1 = new Label("Day:");
            Label l2 = new Label("Course:");
            Label l3 = new Label("Faculty:");
            Label l4 = new Label("Start Hour (24h):");
            Label l5 = new Label("Duration (hours):");

            styleLabel(l1); styleLabel(l2); styleLabel(l3); styleLabel(l4); styleLabel(l5);
            styleField(courseTF); styleField(facultyTF); styleField(startTF); styleField(durationTF);

            form.add(l1); form.add(day);
            form.add(l2); form.add(courseTF);
            form.add(l3); form.add(facultyTF);
            form.add(l4); form.add(startTF);
            form.add(l5); form.add(durationTF);

            Button addBtn = new Button("Add Class");
            Button showBtn = new Button("Show Timetable");
            Button clearBtn = new Button("Clear Day");

            styleButton(addBtn); styleButton(showBtn); styleButton(clearBtn);

            Panel btns = new Panel();
            btns.setBackground(LAVENDER);
            btns.add(addBtn); btns.add(showBtn); btns.add(clearBtn);

            out = new TextArea(15,60);
            styleArea(out);

            addBtn.addActionListener(e -> addClass());
            showBtn.addActionListener(e -> displayAll());
            clearBtn.addActionListener(e -> {
                week[day.getSelectedIndex()].clear();
                out.append("Cleared " + day.getSelectedItem() + "\n");
            });

            panel.add(form, BorderLayout.NORTH);
            panel.add(btns, BorderLayout.CENTER);
            panel.add(out, BorderLayout.SOUTH);
        }

        void addClass() {
            try {
                int d = day.getSelectedIndex();
                String course = courseTF.getText().trim();
                String faculty = facultyTF.getText().trim();
                int start = Integer.parseInt(startTF.getText().trim());
                int dur = Integer.parseInt(durationTF.getText().trim());
                int end = start + dur;

                // weekday / saturday check
                if (d == 5) { // Saturday
                    if (start < 9 || end > 12) { out.append("Saturday allowed only 9–12.\n"); return; }
                } else {
                    if (start < 9 || end > 17) { out.append("Weekdays allowed 9–17.\n"); return; }
                }

                // lunch break
                if (start < 13 && end > 13) { out.append("Cannot overlap lunch (13–14).\n"); return; }

                // overlap
                for (Entry e : week[d]) {
                    if (start < e.end && e.start < end) { out.append("Time overlap!\n"); return; }
                }

                Entry en = new Entry(course, faculty, start, dur);

                insertSorted(week[d], en);
                out.append("Added: "+course+" ("+start+":00-"+end+":00)\n");

            } catch (Exception ex) {
                out.append("Invalid input. Enter numeric start/duration.\n");
            }
        }

        void displayAll() {
            out.append("\n=== TIMETABLE ===\n");
            for (int i=0;i<6;i++) {
                out.append("• "+days[i]+"\n");
                if (week[i].isEmpty()) out.append("   (No classes)\n");
                else
                    for (Entry e : week[i]) out.append("   "+e+"\n");
            }
        }

        class Entry {
            String c,f; int start,dur,end;
            Entry(String c,String f,int s,int d){this.c=c;this.f=f;this.start=s;this.dur=d;this.end=s+d;}
            public String toString(){return c+" | "+f+" | "+start+":00 - "+end+":00";}
        }

        void insertSorted(ArrayList<Entry> list, Entry e) {
            int i=0;
            while (i < list.size() && list.get(i).start <= e.start) i++;
            list.add(i, e);
        }
    }

    // ============================================================
    //                ASSIGNMENT MANAGER GUI
    // ============================================================

    class AssignmentManagerGUI {
        Panel panel;
        TextField courseTF, taskTF, deadlineTF, searchTF;
        TextArea out;

        ArrayList<Assignment> list = new ArrayList<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        AssignmentManagerGUI() { build(); }

        Panel getPanel(){ return panel; }

        void build() {
            panel = new Panel(new BorderLayout(10,10));
            panel.setBackground(LAVENDER);

            Panel form = new Panel(new GridLayout(0,2,6,6));
            form.setBackground(LAVENDER);

            Label l1 = new Label("Course:");
            Label l2 = new Label("Task Title:");
            Label l3 = new Label("Deadline (yyyy-MM-dd):");

            styleLabel(l1); styleLabel(l2); styleLabel(l3);

            courseTF = new TextField(20);
            taskTF = new TextField(20);
            deadlineTF = new TextField(10);

            styleField(courseTF); styleField(taskTF); styleField(deadlineTF);

            form.add(l1); form.add(courseTF);
            form.add(l2); form.add(taskTF);
            form.add(l3); form.add(deadlineTF);

            Panel btns = new Panel();
            btns.setBackground(LAVENDER);

            Button add = new Button("Add");
            Button view = new Button("View All");
            Button del = new Button("Delete");
            Button done = new Button("Mark Done");

            styleButton(add); styleButton(view); styleButton(del); styleButton(done);

            btns.add(add); btns.add(view); btns.add(done); btns.add(del);

            Panel searchP = new Panel(new FlowLayout(FlowLayout.LEFT));
            searchP.setBackground(LAVENDER);
            searchTF = new TextField(15);
            styleField(searchTF);
            Button search = new Button("Search Course");
            styleButton(search);

            searchP.add(new Label("Course:"));
            searchP.add(searchTF);
            searchP.add(search);

            out = new TextArea(16, 70);
            styleArea(out);

            add.addActionListener(e -> addAssignment());
            view.addActionListener(e -> showAll());
            del.addActionListener(e -> deleteAssignment());
            done.addActionListener(e -> markDone());
            search.addActionListener(e -> searchCourse());

            panel.add(form, BorderLayout.NORTH);
            panel.add(btns, BorderLayout.WEST);
            panel.add(searchP, BorderLayout.EAST);
            panel.add(out, BorderLayout.CENTER);
        }

        void addAssignment() {
            try {
                String c = courseTF.getText().trim();
                String t = taskTF.getText().trim();
                LocalDate d = LocalDate.parse(deadlineTF.getText().trim(), df);

                if (d.isBefore(LocalDate.now())) {
                    out.append("Deadline cannot be before today.\n");
                    return;
                }

                list.add(new Assignment(c,t,d));
                out.append("Added assignment.\n");

            } catch (Exception ex) {
                out.append("Invalid date. Use yyyy-MM-dd.\n");
            }
        }

        void showAll() {
            out.append("\n=== Assignments ===\n");
            if (list.isEmpty()) { out.append("No assignments.\n"); return; }
            for (Assignment a : list) out.append(a+"\n");
        }

        void deleteAssignment() {
            String t = taskTF.getText().trim();
            Iterator<Assignment> it = list.iterator();
            while (it.hasNext()) {
                Assignment a = it.next();
                if (a.title.equalsIgnoreCase(t)) {
                    it.remove();
                    out.append("Deleted.\n");
                    return;
                }
            }
            out.append("Task not found.\n");
        }

        void markDone() {
            String t = taskTF.getText().trim();
            for (Assignment a : list) {
                if (a.title.equalsIgnoreCase(t)) {
                    a.done = true;
                    out.append("Marked as done.\n");
                    return;
                }
            }
            out.append("Task not found.\n");
        }

        void searchCourse() {
            String c = searchTF.getText().trim();
            out.append("\nSearching for course: "+c+"\n");
            boolean found=false;
            for (Assignment a : list) {
                if (a.course.equalsIgnoreCase(c)) {
                    out.append(a+"\n");
                    found=true;
                }
            }
            if (!found) out.append("No assignments.\n");
        }

        class Assignment {
            String course, title;
            LocalDate deadline;
            boolean done=false;
            Assignment(String c,String t,LocalDate d){course=c;title=t;deadline=d;}
            public String toString(){
                return course+" | "+title+" | "+deadline+" | "+(done?"Done":"Pending");
            }
        }
    }

// ============================================================
//                   CLUB DIRECTORY GUI (UPDATED + LEAVE)
// ============================================================

class ClubDirectoryGUI {

    Panel panel;
    TextArea out;

    ArrayList<Club> clubs = new ArrayList<>();
    StudentStub studentStub = new StudentStub();

    ClubDirectoryGUI() {
        preloadClubs();
        build();
    }

    void setStudent(String id, String name) {
        studentStub.id = id;
        studentStub.name = name;
    }

    Panel getPanel(){ return panel; }

    void preloadClubs() {

        Club coding = new Club("Knuth Coding Club", "Competitive programming & contests");
        coding.addEvent("Codeathon", "2025-12-01 15:00", "2025-12-01 18:00", "3-hour coding event");
        coding.addEvent("Debug Duel", "2025-12-03 14:00", "2025-12-03 16:00", "Debugging challenge");

        Club art = new Club("Aakriti", "Art & Creativity");
        art.addEvent("Watercolor Workshop", "2025-12-04 14:00", "2025-12-04 16:00", "Painting session");

        Club robotics = new Club("Robotics Club", "Hardware & AI Robotics");
        robotics.addEvent("Robo Race", "2025-12-05 10:00", "2025-12-05 12:00", "Robot racing competition");

        Club dance = new Club("Dance Club", "Hip-hop & classical dance");
        dance.addEvent("Freestyle Night", "2025-12-06 17:00", "2025-12-06 19:00", "Open dance stage");

        Club singing = new Club("Singing Club", "Vocals, karaoke, performances");
        singing.addEvent("Karaoke Evening", "2025-12-09 18:00", "2025-12-09 20:00", "Fun singing event");

        clubs.add(coding);
        clubs.add(art);
        clubs.add(robotics);
        clubs.add(dance);
        clubs.add(singing);
    }

    void build() {
        panel = new Panel(new BorderLayout(10,10));
        panel.setBackground(LAVENDER);

        Panel top = new Panel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(LAVENDER);

        Choice clubChoice = new Choice();
        for (Club c : clubs) clubChoice.add(c.name);

        Button join = new Button("Join");
        Button myClubs = new Button("My Clubs");
        Button info = new Button("View Info");
        Button leave = new Button("Leave Club");

        styleButton(join); styleButton(myClubs); styleButton(info); styleButton(leave);

        top.add(new Label("Club:"));
        top.add(clubChoice);
        top.add(join);
        top.add(myClubs);
        top.add(info);
        top.add(leave);

        out = new TextArea(18,80);
        styleArea(out);

        // JOIN
        join.addActionListener(e -> {
            if (studentStub.id.equals("")) {
                out.append("Register first!\n");
                return;
            }
            Club c = findClub(clubChoice.getSelectedItem());
            if (studentStub.joinClub(c)) out.append("Joined "+c.name+"\n");
            else out.append("Already joined or error.\n");
        });

        // MY CLUBS
        myClubs.addActionListener(e -> {
            out.append("\nYour clubs:\n");
            if (studentStub.joinedClubs.isEmpty()) out.append("(None)\n");
            else for (String s : studentStub.joinedClubs) out.append(" - "+s+"\n");
        });

        // VIEW INFO + EVENTS
        info.addActionListener(e -> {
            Club c = findClub(clubChoice.getSelectedItem());
            out.append("\n====== " + c.name + " ======\n");
            out.append(c.desc + "\n\n");

            if (c.events.isEmpty()) {
                out.append("(No events listed)\n");
            } else {
                out.append("Events:\n");
                for (ClubEvent ev : c.events) {
                    out.append(" • " + ev + "\n");
                }
            }
        });

        // 💜 LEAVE CLUB OPTION
        leave.addActionListener(e -> {
            if (studentStub.joinedClubs.isEmpty()) {
                out.append("You haven't joined any clubs yet.\n");
                return;
            }

            String toLeave = clubChoice.getSelectedItem();

            if (!studentStub.joinedClubs.contains(toLeave)) {
                out.append("You are not a member of "+toLeave+"\n");
                return;
            }

            studentStub.joinedClubs.remove(toLeave);
            out.append("You left " + toLeave + "\n");
        });

        panel.add(top, BorderLayout.NORTH);
        panel.add(out, BorderLayout.CENTER);
    }

    Club findClub(String name) {
        for (Club c : clubs) if (c.name.equalsIgnoreCase(name)) return c;
        return null;
    }

    // ---------------- Club Classes ----------------

    class Club {
        String name, desc;
        ArrayList<ClubEvent> events = new ArrayList<>();

        Club(String n,String d){ name=n; desc=d;}

        void addEvent(String title, String start, String end, String desc) {
            events.add(new ClubEvent(title, start, end, desc));
        }
    }

    class ClubEvent {
        String title, start, end, desc;

        ClubEvent(String t,String s,String e,String d){
            title=t; start=s; end=e; desc=d;
        }

        public String toString() {
            return title+" ("+start+" → "+end+") - "+desc;
        }
    }

    class StudentStub {
        String id="",name="";
        ArrayList<String> joinedClubs = new ArrayList<>();

        boolean joinClub(Club c) {
            if (c == null) return false;
            if (joinedClubs.contains(c.name)) return false;
            joinedClubs.add(c.name);
            return true;
        }
    }
}


    // ============================================================
    //                     STUDENT DATA CLASS
    // ============================================================

    class StudentData {
        String id="", name="", course="";
        int age=0, sem=0;

        String displayString() {
            return "ID: "+id+
                   "\nName: "+name+
                   "\nAge: "+age+
                   "\nCourse: "+course+
                   "\nSemester: "+sem+"\n";
        }
    }

    // ============================================================
    // MAIN
    // ============================================================

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new CampusConnectFrame());
    }
}
