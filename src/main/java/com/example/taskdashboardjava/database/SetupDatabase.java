/**
 * Database bootstrap that creates necessary tables on application startup.
 * Safe to call multiple times.
 */
package com.example.taskdashboardjava.database;

import java.sql.Connection;
import java.sql.Statement;

public class SetupDatabase {

    /**
     * Creates the schema if it doesn't exist.
     * Columns are intentionally simple strings for flexibility.
     */
    public static void createTables(){

        String createTableTasks = """
                    CREATE TABLE IF NOT EXISTS tasks (
                        ID INTEGER PRIMARY KEY AUTOINCREMENT,
                        title TEXT ,
                        description TEXT,
                        due_date TEXT,
                        priority TEXT,
                        priority_color TEXT,
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
