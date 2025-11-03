package com.example.taskdashboardjava.database;

import com.example.taskdashboardjava.controller.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {

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

    public static List<String> getAllCategories() {
        // This query gets every unique category name from your tasks
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

    public static void updateTask(Task task) {
        String updateTaskSQL = """
                UPDATE tasks
                SET title = ?,
                    description = ?,
                    due_date = ?,
                    priority = ?,
                    category = ?,
                    status = ?
                WHERE ID = ?;
                """;

        try (var conn = DriverManager.getConnection(DatabaseConnection.URL);
             var pstmt = conn.prepareStatement(updateTaskSQL)) {

            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setString(3, (task.getDueDate() != null) ? task.getDueDate().toString() : "");
            pstmt.setString(4, (task.getPriority() != null) ? task.getPriority().getName() : "Low");

            // This is the important part for saving the category
            pstmt.setString(5, task.getCategory());

            pstmt.setString(6, (task.getStatus() != null) ? task.getStatus().getName() : "Pending");
            pstmt.setInt(7, task.getId());

            pstmt.executeUpdate();
            System.out.println("Task ID: " + task.getId() + " updated successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to update task ID: " + task.getId());
        }
    }

    public static List<Task> getTasksByCategory(String category) {
        // If the category is "All", we use the existing getAllTask()
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
                        rs.getString("status")
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

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
                        rs.getString("status")
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

}