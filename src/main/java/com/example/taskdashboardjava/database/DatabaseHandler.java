package com.example.taskdashboardjava.database;

import com.example.taskdashboardjava.controller.Task;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {

    public static void InsertTask(String title, String description, String due_date, String priority){

        String InsertTaskSQL = """
                    INSERT INTO tasks (title, description, due_date, priority, category, status)
                    VALUES (?, ?, ?, ?, "All Tasks", "Pending");
                """; // <-- FIXED: Was "Not started", now "Pending"

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

    public static void updateTask(Task task) {
        String updateTaskSQL = """
                UPDATE tasks
                SET title = ?,
                    description = ?,
                    due_date = ?,
                    priority = ?,
                    status = ?
                WHERE ID = ?;
                """;

        try (var conn = DriverManager.getConnection(DatabaseConnection.URL);
             var pstmt = conn.prepareStatement(updateTaskSQL)) {

            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setString(3, (task.getDueDate() != null) ? task.getDueDate().toString() : "");
            pstmt.setString(4, (task.getPriority() != null) ? task.getPriority().getName() : "Low");
            pstmt.setString(5, (task.getStatus() != null) ? task.getStatus().getName() : "Pending");
            pstmt.setInt(6, task.getId());

            pstmt.executeUpdate();
            System.out.println("Task ID: " + task.getId() + " updated successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to update task ID: " + task.getId());
        }
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

    public static Map<String, List<Task>> getAllTasksByCategory() {
        Map<String, List<Task>> categorized = new HashMap<>();
        List<Task> allTasks = getAllTask();

        if (allTasks == null) {
            System.err.println("Database returned null tasks. Returning empty map.");
            return categorized;
        }

        for (Task task : allTasks) {
            String category = task.getCategory();
            // This is a safety check in case the category is null in the database
            if (category == null) {
                category = "All Tasks";
            }
            categorized.computeIfAbsent(category, k -> new ArrayList<>()).add(task);
        }

        return categorized;
    }
}