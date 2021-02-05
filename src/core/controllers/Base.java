package core.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class Base implements Initializable {
    public AnchorPane paneLeft;
    public AnchorPane paneMenuHolder;
    public Button btnDashboard;
    public Button btnInventoryItem;
    public Button btnCustomers;
    public Button btnSells;
    public Button btnAccounts;
    public Button btnAdmin;
    public AnchorPane paneAccountSection;
    public Label lblAccessLevel;
    public Label lblUsername;
    public Label lblClock;
    public Button btnDueUpdate;
    public AnchorPane paneRight;

    private static String username = "";
    private static String accessLevel = "";
    public Button closebtn;
    public Label lblDate;
    private BorderPane newRightPane = null;
    private Button temp = null;
    private Button recover = null;
    private static boolean anchorFlag = false;

    public static Base currentBase;

    public final HashMap<String, String> FXML_URL = new HashMap<>();
    private void loadFXMLMap() {
        FXML_URL.put("Login", "/core/view/login.fxml");
        FXML_URL.put("Inventory", "/core/view/inventory.fxml");
        FXML_URL.put("Customers", "/core/view/customer.fxml");
        FXML_URL.put("Dashboard", "/core/view/dashboard.fxml");
        FXML_URL.put("SellList", "/core/view/selllist.fxml");
        FXML_URL.put("Sells", "/core/view/sells.fxml");
        FXML_URL.put("Rentals", "/core/view/rentals.fxml");
        FXML_URL.put("Accounts", "/core/view/accounts.fxml");
        FXML_URL.put("Administrative", "/core/view/administrator.fxml");
        FXML_URL.put("Update Due", "/core/view/dueupdate.fxml");
    }

    private void autoResizePane() {
        newRightPane.setPrefWidth(paneRight.getWidth());
        newRightPane.setPrefHeight(paneRight.getHeight());
    }

    public void ctrlRightPane(String URL) {
        try {
            paneRight.getChildren().clear(); //Removing previous nodes
            newRightPane = FXMLLoader.load(getClass().getResource(URL));

            newRightPane.setPrefHeight(paneRight.getHeight());
            newRightPane.setPrefWidth(paneRight.getWidth());

            paneRight.getChildren().add(newRightPane);

            //Listener to monitor any window size change
            paneRight.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
                // Some components of the scene will be resized automatically
                autoResizePane();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnNavigators(ActionEvent event) {
        Button btn = (Button)event.getSource();

        // Getting navigation button label
        String btnText = btn.getText();

        // Checking which button is clicked from the map
        // and navigating to respective menu
        ctrlRightPane(FXML_URL.get(btnText));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFXMLMap();
        username = Login.loggerUsername;
        accessLevel = Login.loggerAccessLevel;
        lblUsername.setText(username.toUpperCase());
        lblAccessLevel.setText(accessLevel);

        //Controling access by checking access level of user
        if(accessLevel.equals("EMPLOYEE")) {
            btnAdmin.setDisable(true);
        }

        //Setting Clock within a new Thread
        Runnable clock = this::runClock;
        Thread newClock = new Thread(clock); //Creating new thread
        newClock.setDaemon(true); //Thread will automatically close on applications closing
        newClock.start(); //Starting Thread

        //Setting DashboardController on RightPane
        ctrlRightPane(FXML_URL.get("Dashboard"));

        currentBase = this;
    }

    private void runClock() {
        while (true) {
            Platform.runLater(() -> {
                // Getting the system time in a string
                String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
                // Setting the time in a label
                lblClock.setText(time);
                lblDate.setText(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void logOut() {
        Stage current = (Stage)lblUsername.getScene().getWindow();
        current.close();

        try {
            // Setting login window
            Stage logInPrompt = new Stage();
            Login.open(logInPrompt);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initStage(Stage stage) {
        try {
            AtomicReference<Double> x = new AtomicReference<>((double) 0);
            AtomicReference<Double> y = new AtomicReference<>((double) 0);

            Parent root = FXMLLoader.load(Initializer.class.getResource("/core/view/base.fxml"));
            Scene scene = new Scene(root);
            String css = Initializer.class.getResource("/core/css/base.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("Pharmacy Name");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(true);

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

    public void quit() {
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

    static void loadTable(String url){
        try {
            AnchorPane anch = currentBase.paneRight;
            anch.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(Base.class.getResource(url));
            pane.setPrefHeight(anch.getHeight());
            pane.setPrefWidth(anch.getWidth());

            anch.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
