package com.example.taskdashboardjava.controller;

import com.example.taskdashboardjava.model.TaskPriority;
import com.example.taskdashboardjava.model.TaskStatus;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskPriority priority;
    private TaskStatus status;
    private String priorityColor;
    private String category;

    // This constructor is fine for creating new tasks from code
    public Task(int id, String title, String description, LocalDate dueDate, TaskPriority priority, TaskStatus status, String priorityColor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.priorityColor = priorityColor;
        this.category = "All"; // Default category
    }


    public Task(int id, String title, String description, String dueDateString, String priorityString, String category, String statusString) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;

        // 1. Parse Due Date String into LocalDate
        try {
            if (dueDateString != null && !dueDateString.isEmpty()) {
                this.dueDate = LocalDate.parse(dueDateString);
            }
        } catch (DateTimeParseException e) {
            System.err.println("Could not parse date: " + dueDateString);
            this.dueDate = null; // Set to null if parsing fails
        }

        // 2. Parse Priority String into TaskPriority Enum
        this.priority = findPriorityByName(priorityString);

        // 3. Parse Status String into TaskStatus Enum
        this.status = findStatusByName(statusString);

        // 4. Set a default color based on priority
        if (this.priority != null) {
            this.priorityColor = this.priority.getColor().toString();
        } else {
            this.priorityColor = "#1e5ae0"; // Default blue
        }
    }

    // Helper method to safely convert a String to a TaskPriority
    private TaskPriority findPriorityByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return TaskPriority.LOW; // Default value
        }
        for (TaskPriority p : TaskPriority.values()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return TaskPriority.LOW; // Default if no match is found
    }

    // Helper method to safely convert a String to a TaskStatus
    private TaskStatus findStatusByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return TaskStatus.PENDING; // Default value
        }
        for (TaskStatus s : TaskStatus.values()) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return TaskStatus.PENDING; // Default if no match is found
    }


    // --- Getters ---
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getDueDate() { return dueDate; }
    public TaskPriority getPriority() { return priority; }
    public TaskStatus getStatus() { return status; }
    public String getPriorityColor() { return priorityColor; }
    public String getCategory() { return category; }

    // --- Setters ---
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    // CORRECTED: This setter no longer calls itself in a loop
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setPriority(TaskPriority priority) { this.priority = priority; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public void setPriorityColor(String priorityColor) { this.priorityColor = priorityColor; }
    public void setCategory(String category) { this.category = category; }
}