package com.example.taskdashboardjava.model; // Ensure this package matches your project structure

import javafx.scene.Node; // For optional graphic
import javafx.scene.shape.Circle; // For optional graphic
import javafx.scene.paint.Color; // For optional graphic

public enum TaskStatus {
    PENDING("Pending", Color.GRAY),
    IN_PROGRESS("In Progress", Color.ORANGE), // Using ORANGE as in your task cards
    COMPLETED("Completed", Color.GREEN);

    private final String name;
    private final Color color; // Added a color field for consistency and potential future use

    TaskStatus(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name;
    }

    // Optional: Method to create a graphic (e.g., a colored circle)
    // You might use this in your task card display or ComboBox if desired
    public Node createGraphic() {
        Circle circle = new Circle(5); // Radius 5
        circle.setFill(this.color);
        return circle;
    }

    // You might also want a static method to get a TaskStatus from its name (String)
    public static TaskStatus fromName(String statusName) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.name.equalsIgnoreCase(statusName)) {
                return status;
            }
        }
        return null; // Or throw an IllegalArgumentException
    }
}