package core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private static double x, y;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        primaryStage.setTitle("Pharoh");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        String css = this.getClass().getResource("css/login.css").toExternalForm();

        scene.getStylesheets().add(css); // Adding stylesheet
        primaryStage.setTitle("Log In Prompt");
        primaryStage.setScene(scene);
        //primaryStage.getIcons().add(new Image("img/Accounts_main.png"));
        primaryStage.setResizable(false);


        // drag ability
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y =event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
