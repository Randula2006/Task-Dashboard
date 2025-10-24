package com.example.taskdashboardjava.database;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class DatabaseConnection{

    private static final String DB_PATH = "data/task_dashboard.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    public static Connection connect(){
        Connection conn = null;
        try{
            //checking if the directory is available and if not make the directory
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