package com.example.taskdashboardjava.controller;

public class Task {
    private int ID;
    private String title;
    private String description;
    private String dueDate;
    private String priority;
    private String category;
    private String status;

    public Task(int ID , String title, String description, String dueDate, String priority, String category, String status) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.category = category;
        this.status = status;
    }

    //setters
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    //getters
    public int getID() {
        return ID;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getDueDate() {
        return dueDate;
    }
    public String getPriority() {
        return priority;
    }
    public String getCategory() {
        return category;
    }
    public String getStatus() {
        return status;
    }

}
