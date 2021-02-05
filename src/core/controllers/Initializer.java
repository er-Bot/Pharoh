package core.controllers;

import core.db.DBConnection;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class Initializer implements Initializable {

    private static final int THREAD_SLEEP_INTERVAL = 50;
    @FXML
    public ProgressBar progressIndicator;
    @FXML
    public Label taskName;
    public String sessionUser = Login.loggerUsername; //This field will hold userName who's currently using the system
    //The field is initiated from LogInController Class

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoadRecords initializerTask = new LoadRecords(sessionUser);
        progressIndicator.progressProperty().unbind();
        taskName.textProperty().unbind();
        taskName.textProperty().bind(initializerTask.messageProperty());

        new Thread(initializerTask).start();

        //Loading Main Application upon initializer task's succession
        initializerTask.setOnSucceeded(e -> {
            //Closing Current Stage
            Stage currentStage = (Stage) taskName.getScene().getWindow();
            currentStage.close();
            Base.initStage(new Stage());
        });
    }

    public static void initStage(Stage stage){
        try {
            AtomicReference<Double> x = new AtomicReference<>((double) 0);
            AtomicReference<Double> y = new AtomicReference<>((double) 0);

            Parent root = FXMLLoader.load(Initializer.class.getResource("/core/view/initializer.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Pharmacy Name");
            stage.setScene(scene);

            // drag ability
            root.setOnMousePressed(event -> {
                x.set(event.getSceneX());
                y.set(event.getSceneY());
            });
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - x.get());
                stage.setY(event.getScreenY() - y.get());
            });

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class LoadRecords extends Task {

        String sessionUser;
        public LoadRecords(String su){
            sessionUser = su;
        }

        @Override
        protected Object call() throws InterruptedException {
            Connection con = DBConnection.getConnection();
            assert con != null;

            Thread.sleep(THREAD_SLEEP_INTERVAL);
            this.updateMessage("Loading Medicines...");
            (new Thread(Inventory::load)).start();

            Thread.sleep(THREAD_SLEEP_INTERVAL);
            this.updateMessage("Loading Dashboard Contents...");
            (new Thread(Dashboard::load)).start();

            //Updating Status of the Task
            this.updateMessage("Loading Finished!");
            Thread.sleep(THREAD_SLEEP_INTERVAL);

            return null;
        }
    }
}
