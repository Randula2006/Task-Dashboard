module com.example.taskdashboardjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires javafx.graphics;

    opens com.example.taskdashboardjava to javafx.fxml;
    exports com.example.taskdashboardjava;
    exports com.example.taskdashboardjava.controller;
    opens com.example.taskdashboardjava.controller to javafx.fxml;
    exports com.example.taskdashboardjava.model;
    opens com.example.taskdashboardjava.model to javafx.fxml;
}