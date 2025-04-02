# Event Participant Manager

A simple **Java Swing** application to **Add, Update, Delete, Search** and **Generate Reports** (CSV or TXT) for event participants. Demonstrates fundamental OOP principles, a GUI with Swing, and file output without external libraries.

---

## Features

1. **CRUD Operations**
   - **Add** participants (name, email, event).
   - **View** all participants in a text area.
   - **Update** participant details by matching email.
   - **Delete** participant by matching email.
   - **Search** for participants by a substring in their name.

2. **Report Generation**
   - Choose **CSV** (comma-separated values) or **TXT** (plain text) as output format.
   - A `JFileChooser` dialog allows saving the report to a specified location on your system.

3. **Minimal Dependencies**
   - Uses only **core Java** libraries: `java.awt`, `javax.swing`, `java.io`, `java.util`.
   - No extra libraries or frameworks needed.
   - In Java 9 and above, you can optionally create a `module-info.java` with the following:

```java
module EventParticipantManagerProject {
    requires java.desktop;
}
```

This ensures the GUI-related classes (`java.desktop`) are accessible in a modular environment.

---

## Project Structure

```plaintext
EventParticipantManagerProject
└── src
    └── com
        └── eventmanager
            ├── model
            │     ├── Participant.java
            │     └── EventManager.java
            ├── service (or "report")
            │     ├── IReportGenerator.java
            │     ├── CSVReportGenerator.java
            │     └── TextReportGenerator.java
            └── ui
                  └── EventRegistrationForm.java
```

### Packages

1. **com.eventmanager.model**  
   - **Participant.java**  
     - Encapsulates participant data (name, email, event name).  
   - **EventManager.java**  
     - Manages participants in-memory with a `List<Participant>` and methods for CRUD + search.

2. **com.eventmanager.service** (sometimes named `com.eventmanager.report`)  
   - **IReportGenerator.java** (Interface)  
     - Declares the abstract `generateReport(...)` method.  
   - **CSVReportGenerator.java**  
     - Implements `IReportGenerator` for CSV file output.  
   - **TextReportGenerator.java**  
     - Implements `IReportGenerator` for TXT (plain text) file output.

3. **com.eventmanager.ui**  
   - **EventRegistrationForm.java**  
     - Main Swing GUI (with a `main()` method).  
     - Two tabs: **Registration** (for adding participants) and **Management** (for update, delete, search, reporting).

---

## OOP Principles Demonstrated

1. **Encapsulation**  
   - `Participant` has private fields with getters/setters.

2. **Abstraction**  
   - `IReportGenerator` interface for generating reports (hides details of CSV vs. TXT).

3. **Polymorphism**  
   - Multiple implementations (`CSVReportGenerator`, `TextReportGenerator`) chosen at runtime.

4. **Class & Object**  
   - Instantiating `EventManager`, `Participant`, and the concrete report generators.

*(If you also use Inheritance or an abstract `BaseManager` in your project, it would further illustrate that pillar.)*

---

## How to Run

1. **Open/Import in Eclipse** (or any Java IDE).
2. Ensure your package structure matches the `com.eventmanager.*` hierarchy in `src/`.
3. **Compile** the project (Eclipse will do this automatically).
4. **Run** `com.eventmanager.ui.EventRegistrationForm` as a Java application.
5. The GUI will launch with two tabs:
   - **Registration Tab**: Enter a participant’s Name, Email, and Event, then click **"Add Participant"**.
   - **Management Tab**:
     - **Update** by entering the existing Email + new Name + new Event.
     - **Delete** by Email.
     - **Search** by a partial Name match.
     - **Generate Report** by choosing **CSV** or **TXT**, then clicking the button. A file chooser will appear for saving the report.

---

## Usage Guide

1. **Adding Participants**  
   - In the “Registration” tab, fill in the text fields and click **"Add Participant"**. You’ll see them immediately appear in the **Participants List**.

2. **Updating Participants**  
   - Switch to “Management” tab. Enter the **Email** of an existing participant, plus a **New Name** and **New Event**. Click **Update Participant**.

3. **Deleting Participants**  
   - Provide the **Email** of the participant you want to remove, then click **Delete Participant**.

4. **Searching Participants**  
   - Type a **Name** (or partial name) to look for and see matching results in a dialog.

5. **Report Generation**  
   - Use the drop-down menu to select **CSV** or **TXT**.
   - Click **Generate Report**.
   - A **JFileChooser** will let you name your file and select a directory.
   - The resulting file (`.csv` or `.txt`) will contain the current list of participants.

---

## Notes / Customization

- **Default File Names**: The UI defaults to `report.csv` or `report.txt` depending on the format.
- **Data Persistence**: Currently, participants are stored in-memory. If you need persistence across runs, consider saving to a database or local file.
- **Dependencies**: No external libraries are required for CSV or TXT generation.

---

## Contributing

1. Fork or clone the repository.
2. Make your changes.
3. Submit a pull request or patch.

---

## License

This project is for **educational** and **demonstration** purposes. You can use it, modify it, or integrate it into your own projects as needed.

