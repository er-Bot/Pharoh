module Pharoh {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens core ;
    exports core.controllers;
}