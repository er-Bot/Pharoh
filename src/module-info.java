module Pharoh {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires controlsfx;

    opens core ;
    opens core.db;
    exports core.controllers;
}