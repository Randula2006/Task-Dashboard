package com.example.taskdashboardjava.database;

import com.example.taskdashboardjava.controller.Task;

import java.sql.DriverManager;
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
                    VALUES (@title, @description, @date, @priority , "All Tasks", "Not started");
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

    public static List<Task> getAllTask(){

        String GetAllTasks = """ 
                SELECT * FROM tasks;
                """;

        try(var conn = DriverManager.getConnection(DatabaseConnection.URL);
            var stmt = conn.prepareStatement(GetAllTasks);){

            ResultSet rs = stmt.executeQuery();
            List<Task> tasks = new ArrayList<>();

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
            System.out.println(tasks);
            return tasks;

        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Failed to retrieve tasks.");
        }
        return null;
    }

    public static Map<String, List<Task>> getAllTasksByCategory() {
        Map<String, List<Task>> categorized = new HashMap<>();
        List<Task> allTasks = getAllTask();

        for (Task task : allTasks) {
            String category = task.getCategory();
            categorized.computeIfAbsent(category, k -> new ArrayList<>()).add(task);
        }

        return categorized;
    }
}
