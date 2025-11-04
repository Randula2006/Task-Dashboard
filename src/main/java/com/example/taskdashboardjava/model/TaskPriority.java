/**
 * Priority levels for tasks with associated colors and a tiny graphic helper.
 * Warning: choosing HIGH too often may cause stress.
 */
package com.example.taskdashboardjava.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.Node;

public enum TaskPriority {
    HIGH("High", Color.RED),
    MEDIUM("Medium", Color.ORANGE),
    LOW("Low", Color.GREEN);

    private final String name;
    private final Color color;

    TaskPriority(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    /** Display name of the priority (e.g., "High"). */
    public String getName() {
        return name;
    }

    /** Associated JavaFX color for UI accents. */
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Creates a tiny colored circle for use in list cells.
     * Because icons speak louder than words.
     */
    public Node createGraphic() {
        Circle circle = new Circle(5);
        circle.setFill(this.color);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(0.5);
        return circle;
    }
}