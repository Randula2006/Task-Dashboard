/**
 * Traditional Java entry point that delegates to JavaFX's launcher.
 * Because main methods are forever.
 */
package com.example.taskdashboardjava;

import javafx.application.Application;

public class Main {
    /**
     * Entrypoint for the JVM. Launches the JavaFX application class.
     * @param args command-line arguments (mostly ignored, like New Year resolutions)
     */
    public static void main(String[] args) {
        Application.launch(Dashboard.class, args);
    }



}
