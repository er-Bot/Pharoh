package core.controllers;

import core.db.DBConnection;
import core.db.Medicine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        protected Object call() throws SQLException, InterruptedException {
            Connection con = DBConnection.getConnection();
            assert con != null;

            Thread.sleep(THREAD_SLEEP_INTERVAL);
            this.updateMessage("Loading Medicines...");

            ObservableList<Medicine> medicineList = FXCollections.observableArrayList();
            ArrayList<String> medicineNames = new ArrayList<>();

            PreparedStatement getMedicineList = con.prepareStatement("select * from medicine order by medi_id");
            ResultSet medicineRS = getMedicineList.executeQuery();

            while(medicineRS.next()) {
                Medicine medicine = Medicine.getInstance(medicineRS);
                medicineList.add(medicine);
                medicineNames.add(medicine.getMedi_name());
            }
            System.out.println(medicineList);

            Inventory.medicineList = medicineList;
            Inventory.medicineNames = medicineNames;

            Thread.sleep(THREAD_SLEEP_INTERVAL);
            this.updateMessage("Loading Dashboard Contents...");

            PreparedStatement getTodaysSell = con.prepareStatement("select count(*), sum(CCMD_TOTAL) from CLIENT_COMMAND where trunc(CCMD_DATE) = trunc(sysdate)");
            ResultSet todaysSell = getTodaysSell.executeQuery();
            PreparedStatement getTotalSell = con.prepareStatement("select sum(CCMD_TOTAL) from CLIENT_COMMAND");
            ResultSet totalSells = getTotalSell.executeQuery();
            PreparedStatement getTodaysPurchase = con.prepareStatement("select count(*), sum(SCMD_TOTAL) from SUPPLIER_COMMAND where trunc(SCMD_DATE) = trunc(sysdate)");
            ResultSet todaysPurchace = getTodaysPurchase.executeQuery();
            PreparedStatement getTotalPurchase = con.prepareStatement("select sum(SCMD_TOTAL) from SUPPLIER_COMMAND");
            ResultSet totalPurchaces = getTotalPurchase.executeQuery();
            PreparedStatement getOutOfStock = con.prepareStatement("select count(*) from MEDICINE where MEDI_STOCK_QTE = 0");
            ResultSet stockOut = getOutOfStock.executeQuery();

            double todaySell = 0.0;
            double totalSell = 0;
            double todayPurchase = 0.0;
            double totalPurchase = 0;
            int purchaseCount = 0;
            int sellCount = 0;
            int  stockOutCtr = 0;

            while(todaysSell.next()) {
                sellCount += todaysSell.getInt(1);
                todaySell += todaysSell.getDouble(2);
            }
            while(totalSells.next()) {
                totalSell += totalSells.getDouble(1);
            }
            while (todaysPurchace.next()) {
                purchaseCount += todaysPurchace.getInt(1);
                todayPurchase += todaysPurchace.getDouble(2);
            }
            while (totalPurchaces.next()) {
                totalPurchase += totalPurchaces.getDouble(1);
            }
            while (stockOut.next())
                stockOutCtr += stockOut.getInt(1);

            Dashboard.todaySellCtr = sellCount;
            Dashboard.todaysTotalSell = todaySell;
            Dashboard.todayTotalBought = todayPurchase;
            Dashboard.todaysBuyCtr = purchaseCount;
            Dashboard.stockOut = stockOutCtr;
            Dashboard.totalBought = totalPurchase;
            Dashboard.totalSell = totalSell;

            //Updating Status of the Task
            this.updateMessage("Loading Finished!");
            Thread.sleep(THREAD_SLEEP_INTERVAL);

            return null;
        }
    }
}
