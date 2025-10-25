
# Task Management System

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![GitHub last commit](https://img.shields.io/github/last-commit/Randula2006/Task-Dashboard?style=for-the-badge)
![GitHub commit activity](https://img.shields.io/github/commit-activity/w/Randula2006/Task-Dashboard?style=for-the-badge)

## 📋 Overview

A Java/Maven implementation of a Task Management System — a lightweight desktop application for creating, viewing, and organizing tasks.

This README gives a concise orientation for developers who want to build, run, or contribute to the project.

## 🚀 Quick start

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Apache Maven 3.6 or higher
- Git

### Build and run

1. Clone the repository (replace the URL with your fork if applicable):

```bash
git clone https://github.com/Randula2006/Task-Dashboard.git
cd "Task-DashBoard-Java"
```

2. Build with Maven:

```bash
mvn clean package
```

3. Run the application (the artifact is a JavaFX desktop app; adapt the command to your environment):

```bash
mvn exec:java -Dexec.mainClass="com.example.taskdashboardjava.Main"
```

If you prefer to run the packaged JAR (after packaging):

```bash
java -jar target/your-artifact-name.jar
```

## ✅ Features (high level)

- Create, edit and delete tasks
- Categorize tasks (Personal, Work, Others)
- Task priority levels
- Persistent storage using an embedded database (project-local)
- Simple, responsive JavaFX UI

## 🛠️ Technologies

- Java 11+
- Maven
- JavaFX for UI

## 📁 Project layout

```
task-dashboard-java/
├── src/
│   ├── main/
│   │   ├── java/          # Application source code (com.example.taskdashboardjava)
│   │   └── resources/     # FXML, CSS and images
│   └── test/              # Unit tests
├── pom.xml                # Maven configuration
└── README.md              # Project documentation
```

## Contributing

Contributions, bug reports and feature requests are welcome. To contribute:

1. Fork the repository
2. Create a topic branch (e.g. `feature/add-search`)
3. Add tests for new behavior when applicable
4. Commit and push your changes
5. Open a pull request describing the change

Please follow common best practices (small commits, clear messages, and one logical change per PR).

## License

This project is licensed under the MIT License — see the `LICENSE` file for details.

## Author

**Randula2006** — Maintainer and original author

## Support

If you need help or want to report issues, open an issue in the repository and provide as much detail as possible (steps to reproduce, screenshots, logs).

---

Thank you for checking out this project — star the repo if you find it useful!