package core.controllers;

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
import java.util.ResourceBundle;

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
        LoadRecords initializerTask = new LoadRecords();
        progressIndicator.progressProperty().unbind();
        taskName.textProperty().unbind();
        taskName.textProperty().bind(initializerTask.messageProperty());

        new Thread(initializerTask).start();

        //Loading Main Application upon initializer task's succession
        initializerTask.setOnSucceeded(e -> {
            //Closing Current Stage
            Stage currentSatge = (Stage) taskName.getScene().getWindow();
            currentSatge.close();
            loadApplication();
        });
    }

    private void loadApplication() {
        //Creating a new stage for main application
        Parent root;
        Stage base = new Stage();

        try {
            root = FXMLLoader.load(getClass().getResource("/core/view/base.fxml"));
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/core/css/base.css").toExternalForm();
            scene.getStylesheets().add(css);
            base.setTitle("Inventory System");
            base.setScene(scene);
            base.setMaximized(true);
            base.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static class LoadRecords extends Task {
        @Override
        protected Object call()  {
            /*Connection connection = DBConnection.getConnection();

            //Creating OLs to save values from result set
            ObservableList<Client> clientsList = FXCollections.observableArrayList();
            ObservableList<Medicament> medicamentList = FXCollections.observableArrayList();
            ObservableList<String> medicamentTypeName = FXCollections.observableArrayList();

            PreparedStatement getClientList = connection.prepareStatement("SELECT * FROM clients");
            PreparedStatement getMedicamentList = connection.prepareStatement("SELECT *" +
                    "FROM medicament, medicamenttype WHERE medicament.MedicamentType_medicamentTypeId = medicamenttype.medicamentTypeId ORDER BY medicamentID");
            PreparedStatement getSellsList = connection.prepareStatement("SELECT * FROM purchases WHERE User_username ='"
                    +sessionUser+"'" + " ORDER BY(purchaseID) DESC");
            PreparedStatement getRentalList = connection.prepareStatement("SELECT * FROM rentals WHERE User_username ='"
                    +sessionUser+"'" + " ORDER BY(rentalID) DESC");
            PreparedStatement getAccountList = connection.prepareStatement("SELECT  clients.firstName, clients.lastName, accounts.acccountID, accounts.accountName, accounts.paymethod " +
                    "FROM accounts, clients WHERE User_username ='"
                    +sessionUser+"' AND Clients_clientID = clientID");
            PreparedStatement getMedicamentType = connection.prepareStatement("SELECT * FROM medicamenttype");
            PreparedStatement getOutOfStock = connection.prepareStatement("SELECT * FROM medicament, medicamenttype WHERE medicamentTypeId = MedicamentType_medicamentTypeId AND stock ="+0);

            //DashboardController stmts
            PreparedStatement getRentalDue = connection.prepareStatement("SELECT COUNT(*), SUM(amountDue) FROM rentals WHERE amountDue <> 0");
            PreparedStatement getPurchaseDue = connection.prepareStatement("SELECT COUNT(*), SUM(amountDue) FROM purchases WHERE amountDue <> 0");
            PreparedStatement getTodaysSell = connection.prepareStatement("SELECT COUNT(*), SUM(payAmount) FROM purchases WHERE purchaseDate = '"+ Date.valueOf(LocalDate.now()) + "'");
            PreparedStatement getTodaysRent = connection.prepareStatement("SELECT COUNT(*), SUM(paid) FROM rentals WHERE rentalDate = '"+ Date.valueOf(LocalDate.now()) + "'");
            PreparedStatement getTodaysRentalDue = connection.prepareStatement("SELECT COUNT(*), SUM(amountDue) FROM rentals WHERE amountDue <> 0 AND rentalDate = '"+ Date.valueOf(LocalDate.now()) + "'");
            PreparedStatement getTodaysPurchaseDue = connection.prepareStatement("SELECT COUNT(*), SUM(amountDue) FROM purchases WHERE amountDue <> 0 AND purchaseDate = '"+ Date.valueOf(LocalDate.now()) + "'");

            ResultSet medicamentResultSet = getMedicamentList.executeQuery();
            ResultSet clientResultSet = getClientList.executeQuery();
            ResultSet sellsList = getSellsList.executeQuery();
            ResultSet rentList = getRentalList.executeQuery();
            ResultSet accountResultSet = getAccountList.executeQuery();
            ResultSet medicamentType = getMedicamentType.executeQuery();
            ResultSet stockOut = getOutOfStock.executeQuery();

            //DashboardController rs
            ResultSet rentalDue = getRentalDue.executeQuery();
            ResultSet purchaseDue = getPurchaseDue.executeQuery();
            ResultSet todaysSell = getTodaysSell.executeQuery();
            ResultSet todaysRent = getTodaysRent.executeQuery();
            ResultSet todaysRentDue = getTodaysRentalDue.executeQuery();
            ResultSet todysPurchaseDue = getTodaysPurchaseDue.executeQuery();

            //Updating task message
            this.updateMessage("Loading Clients...");
            Thread.sleep(THREAD_SLEEP_INTERVAL);

            ArrayList<String> clientIDNameHolder = new ArrayList<>(); //Will store ID and Name from ResultSet
            ArrayList<String> medicamentIDNameForSale = new ArrayList<>(); //Will hold medicament id name for sell
            ArrayList<String> clientName = new ArrayList<>();
            ArrayList<Integer> medicamentIDForSale = new ArrayList<>();
            ArrayList<String> medicamentIDNameForRentals = new ArrayList<>(); //Will hold medicament id name for rent
            ArrayList<Integer> medicamentIDForRent = new ArrayList<>();
            ArrayList<String> medicamentNames = new ArrayList<>();
            ArrayList<Integer> clientID = new ArrayList<>();
            TreeMap<String, Integer> medicamentTypeTree = new TreeMap<>();

            // Getting values from clients result set
            while(clientResultSet.next()) {
                clientIDNameHolder.add(clientResultSet.getInt(1) + " | "
                        + clientResultSet.getString(2) + "  "
                        + clientResultSet.getString(3));

                clientName.add(clientResultSet.getString(2)); //Adding first Name
                clientName.add(clientResultSet.getString(3)); //Adding last name

                Client newClient = new Client(
                        clientResultSet.getInt("clientID"),
                        clientResultSet.getString("firstName"),
                        clientResultSet.getString("lastName"),
                        clientResultSet.getString("address"),
                        clientResultSet.getString("phone"),
                        clientResultSet.getString("email"),
                        clientResultSet.getString("photo"),
                        clientResultSet.getString("gender"),
                        clientResultSet.getDate("memberSince"));

                clientsList.add(newClient);

                clientID.add(clientResultSet.getInt(1));
            }

            //Setting fields in Clients List
            ClientController.clientsList = clientsList;
            ClientController.clientNames = clientName;

            //Setting Id and Name to SellsController, RentalsController, Accounts
            SellsController.clientIDName = clientIDNameHolder;
            SellsController.clientID = clientID;
            RentalsController.clientIDName = clientIDNameHolder;
            RentalsController.clientID = clientID;
            AccountController.clientIDName = clientIDNameHolder;

            Thread.sleep(THREAD_SLEEP_INTERVAL);
            //Updating Task status
            this.updateMessage("Loading Medicaments...");

            while(medicamentResultSet.next()) {
                Medicament medicament = new Medicament(medicamentResultSet.getInt("medicamentID"),
                        medicamentResultSet.getString("medicamentName"),
                        medicamentResultSet.getInt("stock"),
                        false,
                        false,
                        medicamentResultSet.getDouble("salePrice"),
                        medicamentResultSet.getDouble("rentRate"),
                        medicamentResultSet.getString("photo"),
                        medicamentResultSet.getString("typeName"));

                medicamentNames.add(medicamentResultSet.getString("medicamentName"));

                if(medicamentResultSet.getString("rentalOrSale").contains("Rental"))
                {
                    medicament.setRent(true);
                    medicamentIDNameForRentals.add(medicamentResultSet.getInt("medicamentID") + " | " +
                            medicamentResultSet.getString("medicamentName"));
                    medicamentIDForRent.add(medicamentResultSet.getInt("medicamentID"));
                }
                if(medicamentResultSet.getString("rentalOrSale").contains("Sale")) {
                    medicamentIDNameForSale.add(medicamentResultSet.getInt("medicamentID") + " | " + medicamentResultSet.getString("medicamentName"));
                    medicamentIDForSale.add(medicamentResultSet.getInt("medicamentID"));
                    medicament.setSale(true);
                }

                medicamentList.add(medicament);

            }

            //Setting Observable Lists to the static field of StockController
            StockController.medicamentList = medicamentList;
            StockController.medicamentNames = medicamentNames;
            SellsController.inventoryMedicament = medicamentIDNameForSale; //Setting medicament id and name for sale & RentalsController
            SellsController.medicamentIDForSale = medicamentIDForSale;
            RentalsController.inventoryMedicament = medicamentIDNameForRentals;
            RentalsController.medicamentIDForRent = medicamentIDForRent;

            Thread.sleep(THREAD_SLEEP_INTERVAL);

            //Updating task status
            this.updateMessage("Loading Sells...");
            ObservableList<Purchase> sellsListByUser = FXCollections.observableArrayList();

            while(sellsList.next()) {
                sellsListByUser.add(new Purchase(sellsList.getInt("purchaseID"),
                        sellsList.getInt("Clients_clientID"),
                        sellsList.getInt("Medicament_medicamentID"),
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
                        rentList.getInt("Medicament_medicamentID"),
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

            Double totalDueAmount = 0.0;
            Integer totalDueCtr = 0;

            while (rentalDue.next()) {
                totalDueCtr += rentalDue.getInt(1);
                totalDueAmount += rentalDue.getDouble(2);
            }

            while (purchaseDue.next()) {
                totalDueCtr += purchaseDue.getInt(1);
                totalDueAmount += purchaseDue.getDouble(2);
            }

            Double todaySell = 0.0;
            Double todayRent = 0.0;
            Integer rentCount = 0;
            Integer sellCount = 0;

            while (todaysSell.next()) {
                sellCount += todaysSell.getInt(1);
                todaySell += todaysSell.getDouble(2);
            }

            while (todaysRent.next()) {
                rentCount += todaysRent.getInt(1);
                todayRent += todaysRent.getDouble(2);
            }

            Double todaysDueAmount = 0.0;
            Integer dueCtr = 0;

            while (todaysRentDue.next()) {
                dueCtr += todaysRentDue.getInt(1);
                todaysDueAmount += todaysRentDue.getDouble(2);
            }

            while (todysPurchaseDue.next()) {
                dueCtr += todysPurchaseDue.getInt(1);
                todaysDueAmount += todysPurchaseDue.getDouble(2);
            }

            Integer stockOutCtr = 0;

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
            this.updateMessage("Loading Medicament Types....");

            while (medicamentType.next()) {
                medicamentTypeTree.put(medicamentType.getString(2), medicamentType.getInt(1));
                medicamentTypeName.add(medicamentType.getString(2));
            }

            StockController.medicamentType = medicamentTypeTree;
            StockController.medicamentTypeNames = medicamentTypeName;

            //Updating Status of the Task
            this.updateMessage("Loading Finished!");
            Thread.sleep(THREAD_SLEEP_INTERVAL);*/

            return null;
        }
    }

}
