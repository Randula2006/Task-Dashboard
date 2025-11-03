
# Task Management System

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![GitHub last commit](https://img.shields.io/github/last-commit/Randula2006/Task-Dashboard?style=for-the-badge)
![GitHub commit activity](https://img.shields.io/github/commit-activity/w/Randula2006/Task-Dashboard?style=for-the-badge)

## 📋 Overview

A comprehensive JavaFX-based Task Management System featuring a modern desktop interface for creating, organizing, and tracking tasks. Built with Java 25, JavaFX 17, and SQLite, this application provides an intuitive dashboard for managing personal, work, and other task categories with real-time filtering and persistence.

**Key Highlights:**
- Clean Model-View-Controller (MVC) architecture
- Embedded SQLite database for lightweight data persistence
- Category-based task organization with dynamic filtering
- Priority and status tracking with visual indicators
- Real-time UI updates and smooth transitions

## ✨ Features

### Task Management
- **Create Tasks**: Add new tasks with title, description, due date, priority, and category
- **Edit Tasks**: Modify existing task details through a dedicated edit dialog
- **Delete Tasks**: Remove completed or unwanted tasks
- **Priority Levels**: Three-tier priority system (High, Medium, Low) with color-coded indicators
  - 🔴 High (Red)
  - 🟠 Medium (Orange)
  - 🟢 Low (Green)

### Organization & Filtering
- **Dynamic Categories**: Automatically populated sidebar based on existing tasks
- **Category Filtering**: Click any category to view filtered tasks
- **Multiple Views**: 
  - All Tasks (default view)
  - Personal Tasks
  - Work Tasks
  - Other Tasks (custom categories)

### Task Status Tracking
- **Pending** (Gray) - Newly created tasks
- **In Progress** (Orange) - Active tasks
- **Completed** (Green) - Finished tasks

### User Interface
- **Modern Dashboard**: Clean, responsive JavaFX interface
- **Card-based Layout**: Tasks displayed as organized cards in a 2-column grid
- **Smooth Transitions**: Fade animations for view changes
- **Modal Dialogs**: Dedicated popup windows for adding and editing tasks

### Data Persistence
- **SQLite Database**: Embedded database stored in `data/task_dashboard.db`
- **Auto-initialization**: Database and tables created automatically on first launch
- **CRUD Operations**: Full Create, Read, Update functionality

## 🚀 Quick start

### Prerequisites

- **Java Development Kit (JDK) 17 or higher** (configured for Java 25)
- **Apache Maven 3.6 or higher**
- **Git**

### Installation & Running

1. **Clone the repository:**

```bash
git clone https://github.com/Randula2006/Task-Dashboard.git
cd Task-DashBoard-Java
```

2. **Build the project:**

```bash
mvn clean package
```

3. **Run the application:**

Using Maven:
```bash
mvn javafx:run
```

Or using the Java command (after building):
```bash
java -jar target/Task-DashBoard-Java-1.0-SNAPSHOT.jar
```

Or on Windows, use the Maven wrapper:
```bash
./mvnw.cmd clean javafx:run
```

On Unix/Linux/Mac:
```bash
./mvnw clean javafx:run
```

## 🏗️ Architecture

### Project Structure

```
task-dashboard-java/
├── src/
│   ├── main/
│   │   ├── java/com/example/taskdashboardjava/
│   │   │   ├── Main.java                    # Application entry point
│   │   │   ├── Dashboard.java               # JavaFX Application class
│   │   │   ├── module-info.java             # Java module descriptor
│   │   │   ├── controller/
│   │   │   │   ├── Controller.java          # Main dashboard controller
│   │   │   │   ├── AddTaskPopupWindowsController.java
│   │   │   │   ├── AllTasksController.java  # Task list view controller
│   │   │   │   ├── editTaskPopupWindow.java # Edit dialog controller
│   │   │   │   ├── CardController.java      # Individual task card controller
│   │   │   │   └── Task.java                # Task data model
│   │   │   ├── database/
│   │   │   │   ├── DatabaseConnection.java  # SQLite connection manager
│   │   │   │   ├── SetupDatabase.java       # Database initialization
│   │   │   │   └── DatabaseHandler.java     # CRUD operations
│   │   │   └── model/
│   │   │       ├── TaskPriority.java        # Priority enum (HIGH, MEDIUM, LOW)
│   │   │       └── TaskStatus.java          # Status enum (PENDING, IN_PROGRESS, COMPLETED)
│   │   └── resources/com/example/taskdashboardjava/
│   │       ├── FXML/
│   │       │   ├── Main.fxml                # Main dashboard layout
│   │       │   ├── AddTaskPopupWindows.fxml # Add task dialog
│   │       │   ├── EditPopupWindow.fxml     # Edit task dialog
│   │       │   ├── AllTasks.fxml            # Task list view
│   │       │   └── UI/
│   │       │       ├── Card.fxml            # Task card component
│   │       │       └── CategoryItems.fxml   # Category sidebar item
│   │       ├── CSS/
│   │       │   └── Application.css          # Application styles
│   │       └── Images/
│   │           └── Icon.png                 # Application icon
│   └── test/                                # Unit tests
├── data/                                    # SQLite database storage (created at runtime)
├── pom.xml                                  # Maven configuration
└── README.md                                # This file
```

### Design Pattern

**Model-View-Controller (MVC)**
- **Model**: `Task.java`, `TaskPriority.java`, `TaskStatus.java`
- **View**: FXML files defining UI structure and layout
- **Controller**: Controller classes handling user interactions and business logic

### Database Schema

**tasks** table:
```sql
CREATE TABLE tasks (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT,
    description TEXT,
    due_date TEXT,
    priority TEXT,
    category TEXT,
    status TEXT
);
```

## 🛠️ Technologies & Dependencies

### Core Technologies
- **Java 25** - Programming language
- **JavaFX 17.0.6** - Desktop UI framework
- **SQLite 3.46.0.0** - Embedded database
- **Maven** - Build automation and dependency management

### JavaFX Modules
- `javafx-controls` - UI controls
- `javafx-fxml` - FXML layout support
- `javafx-web` - Web view component
- `javafx-swing` - Swing interoperability
- `javafx-media` - Media playback

### Additional Libraries
- **ControlsFX 11.2.1** - Extended JavaFX controls
- **FormsFX 11.6.0** - Form building framework
- **ValidatorFX 0.5.0** - Input validation
- **Ikonli 12.3.1** - Icon library for JavaFX
- **BootstrapFX 0.4.0** - Bootstrap-like styling
- **TilesFX 21.0.3** - Tile-based dashboard components
- **FXGL 17.3** - Game library (optional features)
- **SLF4J 2.0.9** - Logging facade

### Testing
- **JUnit Jupiter 5.10.2** - Unit testing framework

## 📝 Usage Guide

### Adding a Task
1. Click the **"Add Task"** button in the top-right corner
2. Fill in the task details:
   - Title (required)
   - Description
   - Due Date (optional)
   - Priority (High/Medium/Low)
3. Click **"Create"** to save or **"Cancel"** to discard

### Viewing Tasks
- Click **"All"** in the sidebar to view all tasks
- Click any category name to filter tasks by that category
- Tasks are displayed as cards in a responsive 2-column grid

### Editing a Task
1. Click on a task card
2. Modify any field (title, description, date, priority, category, status)
3. Click **"Save"** to update or **"Cancel"** to discard changes

### Managing Categories
- Categories are automatically created when you add tasks
- Switch between categories using the sidebar
- The category list updates dynamically as you add/edit tasks

## 🧪 Development

### Running Tests
```bash
mvn test
```

### Building for Production
```bash
mvn clean package
```

### Modifying the UI
- FXML files are located in `src/main/resources/com/example/taskdashboardjava/FXML/`
- CSS styles are in `src/main/resources/com/example/taskdashboardjava/CSS/Application.css`
- Use Scene Builder for visual FXML editing

### Database Location
- Development: `data/task_dashboard.db` (created automatically)
- To reset the database, simply delete this file and restart the application

## 🤝 Contributing

Contributions, bug reports, and feature requests are welcome! To contribute:

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/your-feature-name`
3. **Make your changes**:
   - Follow the existing code style and conventions
   - Add JavaDoc comments for public methods
   - Test your changes thoroughly
4. **Commit your changes**: `git commit -m "Add: descriptive commit message"`
5. **Push to your branch**: `git push origin feature/your-feature-name`
6. **Open a Pull Request** with a clear description of your changes

### Code Style Guidelines
- Use meaningful variable and method names
- Follow JavaFX best practices for controller design
- Maintain the MVC pattern
- Add comments for complex logic
- Update documentation for new features

## 📄 License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## 👤 Author

**Randula2006**

- GitHub: [@Randula2006](https://github.com/Randula2006)
- Repository: [Task-Dashboard](https://github.com/Randula2006/Task-Dashboard)

## 🐛 Known Issues

- None currently reported

## 🔮 Future Enhancements

Potential features for future releases:
- Task search and filtering
- Task sorting (by date, priority, status)
- Data export (CSV, JSON)
- Task reminders and notifications
- Dark mode theme
- Multi-user support
- Cloud synchronization
- Recurring tasks
- Task attachments

## 📞 Support

If you encounter any issues or have questions:
1. Check the [Issues](https://github.com/Randula2006/Task-Dashboard/issues) page for existing reports
2. Open a new issue with detailed information:
   - Steps to reproduce the problem
   - Expected vs actual behavior
   - Screenshots (if applicable)
   - Your environment (OS, Java version)

---

⭐ **Star this repository if you find it helpful!** Thank you for using Task Dashboard.