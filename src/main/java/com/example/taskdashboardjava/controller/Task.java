/**
 * Domain model representing a task entity as used in the UI layer.
 * Contains parsing helpers for mapping DB strings to enums.
 */
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
    public Task(int id, String title, String description, LocalDate dueDate, TaskPriority priority, TaskStatus status, String priorityColor) {
        this.id = id;
        this.title = title;
        this.description = description;
            /** Database row ID. */
        this.dueDate = dueDate;
            /** Short, human-friendly title. */
        this.priority = priority;
            /** Optional longer description; because context matters. */
        this.status = status;
        this.priorityColor = priorityColor;
        this.category = "All";
    }
            /** Web color for card accent (e.g., #1e5ae0). */
    public Task(int id, String title, String description, String dueDateString, String priorityString, String category, String statusString, String priorityColorString) {
            /** Category name used for grouping/filtering. */
        this.id = id;
        this.title = title;
            /**
             * Constructs a task with typed fields (typically from UI code).
             */
        this.description = description;
        this.category = category;
        try {
            if (dueDateString != null && !dueDateString.isEmpty()) {
                this.dueDate = LocalDate.parse(dueDateString);
            }
        } catch (DateTimeParseException e) {
            System.err.println("Could not parse date: " + dueDateString);
            this.dueDate = null;
        }
        this.priority = findPriorityByName(priorityString);
            /**
             * Constructs a task from database string fields.
             * Handles safe parsing to enums and dates.
             */
        this.status = findStatusByName(statusString);
        if (priorityColorString != null && !priorityColorString.isEmpty()) {
            this.priorityColor = priorityColorString;
        } else {
            this.priorityColor = "#1e5ae0";
        }
    }
    private TaskPriority findPriorityByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return TaskPriority.LOW;
        }
        for (TaskPriority p : TaskPriority.values()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return TaskPriority.LOW;
    }
    private TaskStatus findStatusByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return TaskStatus.PENDING;
        }
            /**
             * Resolves a priority enum by its display name.
             */
        for (TaskStatus s : TaskStatus.values()) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return TaskStatus.PENDING;
    }


    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
            /**
             * Resolves a status enum by its display name.
             */
    public LocalDate getDueDate() { return dueDate; }
    public TaskPriority getPriority() { return priority; }
    public TaskStatus getStatus() { return status; }
    public String getPriorityColor() { return priorityColor; }
    public String getCategory() { return category; }
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setPriority(TaskPriority priority) { this.priority = priority; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public void setPriorityColor(String priorityColor) { this.priorityColor = priorityColor; }
    public void setCategory(String category) { this.category = category; }
}