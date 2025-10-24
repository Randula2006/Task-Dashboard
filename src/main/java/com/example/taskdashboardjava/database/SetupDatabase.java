package com.example.taskdashboardjava.database;

import java.sql.Connection;
import java.sql.Statement;

public class SetupDatabase {

    public static void createTables(){

        String createTableTasks = """
                    CREATE TABLE IF NOT EXISTS tasks (
                        ID INTEGER PRIMARY KEY AUTOINCREMENT,
                        title TEXT NOT NULL,
                        description TEXT,
                        due_date TEXT,
                        category TEXT,
                        status TEXT
                    );
                """;

        try(Connection conn = DatabaseConnection.connect()){
            Statement statement = conn.createStatement();
            statement.execute(createTableTasks);
            System.out.println("Tables created successfully.");

        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to create tables.");
        }
    }
}
