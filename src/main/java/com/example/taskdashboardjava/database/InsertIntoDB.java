package com.example.taskdashboardjava.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InsertIntoDB {

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
}
