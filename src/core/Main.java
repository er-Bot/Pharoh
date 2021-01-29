package core;

import core.controllers.Login;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static double x, y;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Login.open(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
