package com.example.taskdashboardjava.controller;

/**
 * This class is not a controller. It's a simple data holder.
 * Its only job is to store the name of the category that the
 * user has clicked on, so the AllTasksController knows what to display.
 */
public class CurrentView {

    /**
     * This is a static variable, which means it's shared across all classes.
     * 1. Controller (sidebar) will SET this value (e.g., "Work").
     * 2. AllTasksController will READ this value to filter the tasks.
     */
    public static String selectedCategory = "All";
}