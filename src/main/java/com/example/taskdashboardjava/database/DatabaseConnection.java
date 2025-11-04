/**
 * Thin SQLite connection utility.
 * Ensures the data directory exists and returns a JDBC connection.
 */
package com.example.taskdashboardjava.database;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class DatabaseConnection{

    private static final String DB_PATH = "data/task_dashboard.db";
    public static final String URL = "jdbc:sqlite:" + DB_PATH;

    /**
     * Opens a connection to the SQLite database. Creates the data folder if needed.
     * @return an open {@link Connection} or null if connection fails
     */
    public static Connection connect(){
        Connection conn = null;
        try{
            File DB_Dir = new File("data");
            if(!DB_Dir.exists()){
                DB_Dir.mkdir();
            }

            conn = java.sql.DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            return conn;

        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Connection to SQLite has failed.");
            return null;
        }

    }


}