// TaskPriority.java
package com.example.taskdashboardjava.model; // Make sure this matches your package

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

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name; // Important for basic display if cellFactory fails or is not used
    }

    public Node createGraphic() {
        Circle circle = new Circle(5); // Radius 5
        circle.setFill(this.color);
        circle.setStroke(Color.BLACK); // Optional: add a border
        circle.setStrokeWidth(0.5);
        return circle;
    }
}