package core.controllers;

import core.db.Client;
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
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

public class Initializer implements Initializable {

    private static final int THREAD_SLEEP_INTERVAL = 100;
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

            //Creating OLs to save values from result set
            ObservableList<Client> clientsList = FXCollections.observableArrayList();
            ObservableList<Medicine> medicineList = FXCollections.observableArrayList();

            assert con != null;
            // General tables
            PreparedStatement getClientList = con.prepareStatement("select * from client");
            PreparedStatement getMedicineList = con.prepareStatement("select * from medicine order by medi_id");
            PreparedStatement getCCmdList = con.prepareStatement("select * from client_command order by(ccmd_id) desc");
            PreparedStatement getSCmdList = con.prepareStatement("select * from supplier_command order by(scmd_id) desc");

            ResultSet medicineRS = getMedicineList.executeQuery();
            ResultSet clientRS = getClientList.executeQuery();
            ResultSet ccmdRS = getCCmdList.executeQuery();
            ResultSet scmdRS = getSCmdList.executeQuery();
            
            //Dashboard Statements
            PreparedStatement getTodaysSell = con.prepareStatement("select count(*), sum(ccmd_total) from client_command where ccmd_date = '"+ Date.valueOf(LocalDate.now()) + "'");
            PreparedStatement getTodaysPurchase = con.prepareStatement("select count(*), sum(scmd_total) from supplier_command where scmd_date = '"+ Date.valueOf(LocalDate.now()) + "'");
            PreparedStatement getOutOfStock = con.prepareStatement("select * from medicine where medi_stock_qte = " + 0);
            ResultSet todaysSell = getTodaysSell.executeQuery();
            ResultSet todaysPurchace = getTodaysPurchase.executeQuery();
            ResultSet outOfStock = getOutOfStock.executeQuery();

            //Updating task message
            this.updateMessage("Loading Clients...");
            Thread.sleep(THREAD_SLEEP_INTERVAL);

            ArrayList<String> clientIDNameHolder = new ArrayList<>(); //Will store ID and Name from ResultSet
            ArrayList<String> clientName = new ArrayList<>();
            ArrayList<Integer> clientID = new ArrayList<>();
            ArrayList<String> medicineIDNameForSale = new ArrayList<>(); //Will hold medicine id name for sell
            ArrayList<Integer> medicineIDForSale = new ArrayList<>();
            ArrayList<String> medicineIDNameForRentals = new ArrayList<>(); //Will hold medicine id name for rent
            ArrayList<Integer> medicineIDForRent = new ArrayList<>();
            ArrayList<String> medicineNames = new ArrayList<>();
            TreeMap<String, Integer> medicineTypeTree = new TreeMap<>();

            // Getting values from clients result set
            while(clientRS.next()) {
                clientIDNameHolder.add(clientRS.getInt(1) + " | "
                        + clientRS.getString(2) + "  "
                        + clientRS.getString(3));

                clientName.add(clientRS.getString(2)); //Adding first Name
                clientName.add(clientRS.getString(3)); //Adding last name

                Client newClient = new Client(
                        clientRS.getInt("clt_id"),
                        clientRS.getString("clt_fname"),
                        clientRS.getString("clt_lname"),
                        clientRS.getString("clt_sex"),
                        clientRS.getString("clt_address"),
                        clientRS.getString("clt_tel"),
                        clientRS.getString("clt_order_doct"),
                        clientRS.getString("clt_order_num")
                );

                clientsList.add(newClient);

                clientID.add(clientRS.getInt(1));
            }

            //Setting fields in Clients List
            ClientController.clientsList = clientsList;
            ClientController.clientNames = clientName;

            //Setting Id and Name to SellsController
            SellsController.clientIDName = clientIDNameHolder;
            SellsController.clientID = clientID;

            Thread.sleep(THREAD_SLEEP_INTERVAL);
            //Updating Task status
            this.updateMessage("Loading Medicines...");

            while(medicineRS.next()) {
                Medicine medicine = new Medicine(medicineRS.getInt("medi_id"),
                        medicineRS.getString("medi_name"),
                        medicineRS.getDate("medi_expire_date"),
                        medicineRS.getDouble("medi_pu"),
                        medicineRS.getDouble("medi_stock_ste"),
                        medicineRS.getString("medi_desc"),
                        medicineRS.getString("medi_dci"),
                        medicineRS.getString("medi_form"),
                        medicineRS.getDouble("medi_dose")
                );

                medicineNames.add(medicineRS.getString("medi_name"));

                medicineList.add(medicine);

            }

            //Setting Observable Lists to the static field of StockController
            StockController.medicineList = medicineList;
            StockController.medicineNames = medicineNames;
            SellsController.inventoryMedicine = medicineIDNameForSale; //Setting medicine id and name for sale & RentalsController
            SellsController.medicineIDForSale = medicineIDForSale;
            PurchaseController.inventoryMedicine = medicineIDNameForRentals;
            PurchaseController.medicineIDForRent = medicineIDForRent;

            Thread.sleep(THREAD_SLEEP_INTERVAL);
/*
            //Updating task status
            this.updateMessage("Loading Sells...");
            ObservableList<Purchase> sellsListByUser = FXCollections.observableArrayList();

            while(sellsList.next()) {
                sellsListByUser.add(new Purchase(sellsList.getInt("purchaseID"),
                        sellsList.getInt("Clients_clientID"),
                        sellsList.getInt("Medicine_medicineID"),
                        sellsList.getString("purchaseDate"),
                        sellsList.getInt("purchaseQuantity"),
                        sellsList.getDouble("payAmount"),
                        sellsList.getDouble("amountDue")));

            }

            //Setting Purchases on Sell Class
            SellsController.purchaseList = sellsListByUser;

            Thread.sleep(THREAD_SLEEP_INTERVAL);

            //Updating Task Status
            this.updateMessage("Loading Rentals...");
            ObservableList<Rent> rentsListByUser = FXCollections.observableArrayList();

            while (rentList.next()) {
                rentsListByUser.add(new Rent(rentList.getInt("rentalID"),
                        rentList.getInt("Medicine_medicineID"),
                        rentList.getInt("Clients_clientID"),
                        rentList.getString("rentalDate"),
                        rentList.getString("returnDate"),
                        rentList.getDouble("paid"),
                        rentList.getDouble("amountDue")));
            }

            //Setting Rents on Rental Class
            RentalsController.rentalList = rentsListByUser;

            Thread.sleep(THREAD_SLEEP_INTERVAL);

            //Updating task status
            this.updateMessage("Loading Accounts...");

            ObservableList<Account> accountListByUser = FXCollections.observableArrayList();
            ArrayList<String> accountNames = new ArrayList<>();

            while(accountResultSet.next()) {
                accountListByUser.add(new Account(accountResultSet.getInt(3),
                        accountResultSet.getString(1) + " " + accountResultSet.getString(2),
                        accountResultSet.getString(4),
                        accountResultSet.getString(5)));
                accountNames.add(accountResultSet.getString(4));
            }

            //Setting Accounts on AccountController Class
            AccountController.accountList = accountListByUser;
            AccountController.accountNames = accountNames;

            Thread.sleep(THREAD_SLEEP_INTERVAL);

            //Updating Task Message
            //DashboardController contents
            this.updateMessage("Loading Dashboard Contents...");

            double totalDueAmount = 0.0;
            int totalDueCtr = 0;

            while (rentalDue.next()) {
                totalDueCtr += rentalDue.getInt(1);
                totalDueAmount += rentalDue.getDouble(2);
            }

            while (purchaseDue.next()) {
                totalDueCtr += purchaseDue.getInt(1);
                totalDueAmount += purchaseDue.getDouble(2);
            }

            double todaySell = 0.0;
            double todayRent = 0.0;
            int rentCount = 0;
            int sellCount = 0;

            while (todaysSell.next()) {
                sellCount += todaysSell.getInt(1);
                todaySell += todaysSell.getDouble(2);
            }

            while (todaysRent.next()) {
                rentCount += todaysRent.getInt(1);
                todayRent += todaysRent.getDouble(2);
            }

            double todaysDueAmount = 0.0;
            int dueCtr = 0;

            while (todaysRentDue.next()) {
                dueCtr += todaysRentDue.getInt(1);
                todaysDueAmount += todaysRentDue.getDouble(2);
            }

            while (todysPurchaseDue.next()) {
                dueCtr += todysPurchaseDue.getInt(1);
                todaysDueAmount += todysPurchaseDue.getDouble(2);
            }

            int stockOutCtr = 0;

            while (stockOut.next()) {
                stockOutCtr += 1;
            }

            //Setting on DashboardController
            DashboardController.totalDueCtr = totalDueCtr;
            DashboardController.totalDueAmount = totalDueAmount;
            DashboardController.todaySellCtr = sellCount;
            DashboardController.todaysTotalSell = todaySell;
            DashboardController.todaysRentalCtr = rentCount;
            DashboardController.todayTotalRental = todayRent;
            DashboardController.todaysTotalDue = todaysDueAmount;
            DashboardController.stockOut = stockOutCtr;

            Thread.sleep(THREAD_SLEEP_INTERVAL);
            this.updateMessage("Loading Medicine Types....");

            while (medicineType.next()) {
                medicineTypeTree.put(medicineType.getString(2), medicineType.getInt(1));
                medicineTypeName.add(medicineType.getString(2));
            }

            StockController.medicineType = medicineTypeTree;
            StockController.medicineTypeNames = medicineTypeName;*/

            //Updating Status of the Task
            this.updateMessage("Loading Finished!");
            Thread.sleep(THREAD_SLEEP_INTERVAL);

            return null;
        }
    }
}
