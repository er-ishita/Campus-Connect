# Campus-Connect — Java Project (Terminal + GUI)

A complete student-portal system built using **Core Java**.

> **Note:**  
> This project currently has **no backend** and runs fully as a **terminal-based application**.  
> A backend/database may be added in future versions.

---

## 🚀 Overview

Campus-Connect implements a modular student system featuring:

- 🕒 **Timetable Manager** (Custom Linked List + Exception Handling)
- 📚 **Assignment Manager** (Deadlines, Search, Completion Tracking)
- 🎭 **Club Directory** (Events, Join/Leave, To-Do Tasks)
- 👤 **Student Profile Management**
- 🪟 **Optional AWT GUI** using Frame
- 🖥️ **Full Terminal Version** using `ProjectMain`

This project demonstrates **OOP**, **Data Structures**, **Exception Handling**, **Java Time API**, and **AWT GUI**.

---

## 📌 Features

### 🎓 Student Profile

- Register with ID, Name, Age, Course, Semester
- Edit profile
- View stored details

---

### 🕒 Timetable Manager

Implemented with a **custom LinkedList** and strict validation rules:

- Add class with:
  - No overlapping hours
  - No lunch-time classes (1–2 PM)
  - Weekday timing limit (9 AM–5 PM)
  - Saturday timing limit (9 AM–12 PM)
- Display full timetable
- Search class by course name
- Remove class by day

---

### 📚 Assignment Manager

Uses `ArrayList`, `LocalDate`, and custom exceptions.

- Add assignment (deadline validation)
- View all assignments
- Mark assignments as completed
- Delete assignments
- Search by course name

---

### 🎭 Club Directory

- View all clubs
- View their events
- Join a club
- Leave a club
- Add tasks to club To-Do
- View joined clubs
- View personal To-Do list

---

## 🪟 GUI Version (`CampusConnectFrame.java`)

Lavender-themed GUI built using **Java AWT**, using:

- `Frame`
- `CardLayout`
- `Panel`
- `Button`
- `TextField`
- `TextArea`

Buttons allow navigation to:

- Registration
- Timetable
- Assignments
- Clubs
- Student Details

---

## 🧩 Project Structure

Campus-Connect/
│── ProjectMain.java # Terminal version entry point
│── Timetable.java # Linked List timetable system
│── AssignmentManager.java # Deadlines, search, task manager
│── ClubMain.java # Club interaction console menu
│── ClubDirectory.java # Preloaded clubs + events
│── Club.java # Club data structure
│── Student.java # Student & To-Do list logic
│── Event.java # Event data structure
│── CampusConnectFrame.java # GUI version (AWT)
│── README.md

---

## 🚀 How to Run (Terminal Version)

### ✔ Step 1 — Compile

```bash
javac ProjectMain.java
```

### ✔ Step 2 — Run

```bash
java ProjectMain
```

---

## 🛠 Requirements

### ✔ Terminal Version

- Java **17+** recommended

### ✔ GUI Version

- Java **8+**
- AWT works natively — no extra setup needed

---

## 📚 Concepts Demonstrated

- Object-Oriented Programming
- Linked Lists
- Collections (ArrayList)
- Exception Handling
- Java AWT
- Java Time API
- Modular Java Architecture
- Interactive Menus
