/**
 * Data access utilities for tasks. Provides insert, update, delete and queries.
 * If this class misbehaves, blame SQL, not Java.
 */
package com.example.taskdashboardjava.database;

import com.example.taskdashboardjava.controller.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {

    /**
     * Inserts a new task with default category and status.
     * @param title task title
     * @param description task description
     * @param due_date ISO-8601 date string (yyyy-MM-dd)
     * @param priority display name of the priority level
     */
    public static void InsertTask(String title, String description, String due_date, String priority){

        String InsertTaskSQL = """
                    INSERT INTO tasks (title, description, due_date, priority, category, status)
                    VALUES (?, ?, ?, ?, 'Personal', 'Pending');
                """;

        try(var conn = DriverManager.getConnection(DatabaseConnection.URL);
            var pstmt= conn.prepareStatement(InsertTaskSQL)){

            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, due_date);
            pstmt.setString(4, priority);
            pstmt.executeUpdate();
            System.out.println("Task inserted successfully.");

        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Failed to insert task.");
        }
    }

    /**
     * Fetches all distinct category names from stored tasks.
     * @return ordered list of categories
     */
    public static List<String> getAllCategories() {
        String sql = "SELECT DISTINCT category FROM tasks WHERE category IS NOT NULL AND category != '' ORDER BY category";
        List<String> categories = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DatabaseConnection.URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }

    /**
     * Updates a task to match the provided model fields.
     * @param task task with new values
     */
    public static void updateTask(Task task) {
        String updateTaskSQL = """
                UPDATE tasks
                SET title = ?,
                    description = ?,
                    due_date = ?,
                    priority = ?,
                    category = ?,
                    status = ?,
                    priority_color = ?
                WHERE ID = ?;
                """;

        try (var conn = DriverManager.getConnection(DatabaseConnection.URL);
             var pstmt = conn.prepareStatement(updateTaskSQL)) {

            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setString(3, (task.getDueDate() != null) ? task.getDueDate().toString() : "");
            pstmt.setString(4, (task.getPriority() != null) ? task.getPriority().getName() : "Low");

            pstmt.setString(5, task.getCategory());

            pstmt.setString(6, (task.getStatus() != null) ? task.getStatus().getName() : "Pending");
            pstmt.setString(7, task.getPriorityColor());
            pstmt.setInt(8, task.getId());

            pstmt.executeUpdate();
            System.out.println("Task ID: " + task.getId() + " updated successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to update task ID: " + task.getId());
        }
    }

    /**
     * Queries tasks by category; passing "All" returns every task.
     * @param category category filter
     * @return tasks matching the filter
     */
    public static List<Task> getTasksByCategory(String category) {
        if ("All".equalsIgnoreCase(category)) {
            return getAllTask();
        }

        String sql = "SELECT * FROM tasks WHERE category = ?";
        List<Task> tasks = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DatabaseConnection.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Task task = new Task(
                        rs.getInt("ID"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("due_date"),
                        rs.getString("priority"),
                        rs.getString("category"),
                        rs.getString("status"),
                        rs.getString("priority_color")
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    /**
     * Retrieves all tasks in the database.
     * @return list of all tasks
     */
    public static List<Task> getAllTask(){

        String GetAllTasks = """ 
                SELECT * FROM tasks;
                """;
        List<Task> tasks = new ArrayList<>();

        try(var conn = DriverManager.getConnection(DatabaseConnection.URL);
            var stmt = conn.prepareStatement(GetAllTasks);){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Task task = new Task(
                        rs.getInt("ID"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("due_date"),
                        rs.getString("priority"),
                        rs.getString("category"),
                        rs.getString("status"),
                        rs.getString("priority_color")
                );
                tasks.add(task);
            }
            System.out.println("Tasks retrieved successfully.");
            System.out.println(tasks.size() + " tasks found.");


        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Failed to retrieve tasks.");
        }
        return tasks;
    }

    /**
     * Deletes the task with the given identifier.
     * @param id primary key
     */
    public static void deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConnection.URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Task ID: " + id + " deleted successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to delete task ID: " + id);
        }
    }
}