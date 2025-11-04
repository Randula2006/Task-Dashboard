/**
 * Lifecycle status of a task, with colors for quick visual scanning.
 */
package com.example.taskdashboardjava.model;

import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public enum TaskStatus {
    PENDING("Pending", Color.GRAY),
    IN_PROGRESS("In Progress", Color.ORANGE),
    COMPLETED("Completed", Color.GREEN);

    private final String name;
    private final Color color;

    TaskStatus(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    /** Human-friendly display name. */
    public String getName() {
        return name;
    }

    /** Associated JavaFX color. */
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name;
    }

    /** Small colored circle for use in UI controls. */
    public Node createGraphic() {
        Circle circle = new Circle(5);
        circle.setFill(this.color);
        return circle;
    }
    /** Finds a status by its display name; returns null if unknown. */
    public static TaskStatus fromName(String statusName) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.name.equalsIgnoreCase(statusName)) {
                return status;
            }
        }
        return null;
    }
}