module Pharoh {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires controlsfx;

    opens core ;
    opens core.db;
    exports core.controllers;
}