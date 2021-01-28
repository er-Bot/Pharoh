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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Base implements Initializable {
    public AnchorPane paneLeft;
    public AnchorPane paneMenuHolder;
    public Button btnDashboard;
    public Button btnInventoryItem;
    public Button btnCustomers;
    public Button btnSells;
    public Button btnRentals;
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
    private AnchorPane newRightPane = null;
    private Button temp = null;
    private Button recover = null;
    private static boolean anchorFlag = false;
    /** store locations of core.view files and it will be used to navigate between different menus */
    private HashMap<String, String> FXML_URL = new HashMap<>();

    /**
     * This method will resize right pane size
     * relative to it's parent whenever window is resized
     */
    private void autoResizePane() {
        newRightPane.setPrefWidth(paneRight.getWidth());
        newRightPane.setPrefHeight(paneRight.getHeight());
    }

    /**
     * This method will help to set appropriate right pane contents
     * respective to the left pane selection and will make it responsive if
     * any window resize occurs
     * @param URL: main.resources.view file path; scene
     */

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

    /**
     * The method here is universal method for all the navigators from left
     * pane which will identify the selection by user and
     * set the respective right pane FXML
     */

    public void btnNavigators(ActionEvent event) {
        borderSelector(event); //Marking selected navigator button

        Button btn = (Button)event.getSource();

        // Getting navigation button label
        String btnText = btn.getText();

        // Checking which button is clicked from the map
        // and navigating to respective menu
        ctrlRightPane(FXML_URL.get(btnText));
    }

    private void loadFXMLMap() {
        // Adding URLS in the FXML_URL ArrayList field
        // to avoid code redundancy in ctrlRightPane() method
        FXML_URL.put("Login", "/core/view/login.fxml");
        FXML_URL.put("Items", "/core/view/inventory.fxml");
        FXML_URL.put("Customers", "/core/view/customer.fxml");
        FXML_URL.put("Dashboard", "/core/view/dashboard.fxml");
        FXML_URL.put("Sells", "/core/view/sells.fxml");
        FXML_URL.put("Rentals", "/core/view/rentals.fxml");
        FXML_URL.put("Accounts", "/core/view/accounts.fxml");
        FXML_URL.put("Administrative", "/core/view/administrator.fxml");
        FXML_URL.put("Update Due", "/core/view/dueupdate.fxml");
    }

    /**
     * This method will mark selected navigator
     * from left navigation pane and will remove it if another
     * navition button is clicked.
     */
    private void borderSelector(ActionEvent event) {
        Button btn = (Button)event.getSource();

        if(temp == null) {
            temp = btn; //Saving current button properties
        } else {
            temp.setStyle(""); //Resetting previous selected button properties
            temp = btn; //Saving current button properties
        }

        btn.setStyle("-fx-background-color: #455A64");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFXMLMap();
        username = Login.loggerUsername;
        accessLevel = Login.loggerAccessLevel;
        lblUsername.setText(username.toUpperCase());
        lblAccessLevel.setText(accessLevel);

        //Controling access by checking access level of user
        if(accessLevel.equals("Employee")) {
            btnAdmin.setDisable(true);
        }

        //Setting Clock within a new Thread
        Runnable clock = this::runClock;

        Thread newClock = new Thread(clock); //Creating new thread
        newClock.setDaemon(true); //Thread will automatically close on applications closing
        newClock.start(); //Starting Thread

        //Setting DashboardController on RightPane
        ctrlRightPane("/core/view/dashboard.fxml");
    }

    private void runClock() {
        while (true) {
            Platform.runLater(() -> {
                // Getting the system time in a string
                String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
                // Setting the time in a label
                lblClock.setText(time);
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Upon logging out this method will set log in prompt on
     * screen by closing main application
     */
    public void logOut() {
        Stage current = (Stage)lblUsername.getScene().getWindow();
        current.close();

        try {
            // Setting login window
            Parent root = FXMLLoader.load(getClass().getResource("/core/view/login.fxml"));
            root.getStylesheets().add("/core/css/login.css");
            Scene scene = new Scene(root);
            Stage logInPrompt = new Stage();
            logInPrompt.setScene(scene);
            logInPrompt.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
