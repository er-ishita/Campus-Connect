# Campus-Connect — Java Project (Terminal + GUI)

A complete student portal system built using Core Java, featuring:
🕒 Timetable Manager (Linked List + Exception Handling)
📚 Assignment Manager (Deadlines, Search, Task Completion)
🎭 Club Directory (Events, Join/Leave, To-Do Tasks)
👤 Student Profile Management
🪟 AWT GUI using Frame (optional)
🖥️ Full Terminal Version using ProjectMain
This project demonstrates OOP, Data Structures, Exception Handling, Java Time API, and AWT GUI.

📌 Features Overview

🎓 Student Profile
Register with ID, Name, Age, Course, Semester
Edit profile
View details anytime

🕒 Timetable Manager
Built using a custom LinkedList implementation with strict time validation:
Add class with:
No overlapping hours
No lunch-time classes (1–2 PM)
Weekday limit (9 AM–5 PM)
Saturday limit (9 AM–12 PM)
Display whole timetable
Search class by course name
Remove class by day

📚 Assignment Manager
Uses ArrayList, LocalDate, and custom exceptions:
Add assignment (with deadline validation)
View all assignments
Mark completed
Delete assignment
Search by course name

🎭 Club Directory
View all clubs
View events for each club
Join a club (limit + event-clash checks in full version)
Leave club
Add tasks to club To-Do
View joined clubs
View personal To-Do List

🪟 GUI Version (CampusConnectFrame.java)
Lavender-themed AWT GUI using:
Frame
CardLayout
Panel
Button
TextField
TextArea
Provides a button-based navigation panel to access:
Registration
Timetable
Assignments
Clubs
Student Details

🧩 Project Structure
Campus-Connect/
│── ProjectMain.java # Terminal version entry point
│── Timetable.java # Linked List timetable system
│── AssignmentManager.java # Deadlines, search, task manager
│── ClubMain.java # Club interaction console menu
│── ClubDirectory.java # Preloaded clubs + events
│── Club.java # Club data structure
│── Student.java # Student & To-Do list logic
│── Event.java # Event data structure
│── CampusConnectFrame.java # Full GUI version (AWT)
│── README.md

🚀 How to Run (Terminal Version)
✔ Step 1 — Compile
javac ProjectMain.java
✔ Step 2 — Run
java ProjectMain
This launches the interactive console-based menu with all features (timetable, assignments, clubs, etc.)

🪟 How to Run the GUI Version
✔ Step 1 — Compile
javac CampusConnectFrame.java
✔ Step 2 — Run
java CampusConnectFrame
This launches the lavender-themed AWT GUI with button-based interactive panels.

🛠️ Requirements
✔ Terminal Version
Java 17 or above
Works perfectly on modern JDKs.

✔ GUI Version
Java 8 or above
AWT Frames work universally — no additional setup required.

📚 Concepts Demonstrated
Object-Oriented Programming
Linked Lists
Collection Framework
Exception Handling
Java AWT
Java Time API
Multi-file modular design
Interactive menus

🤝 Contributing
Feel free to open issues or submit pull requests
to improve UI, add new features, or enhance performance.
