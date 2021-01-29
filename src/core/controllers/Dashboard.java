package core.controllers;

import core.db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    public Button btnTodaySell;
    public Label lblTodaySellCtr;
    public Label lblTodaysSellAmount;
    public Button btnTodayRental;
    public Label lblTodaysRentalCtr;
    public Label lblTodaysRentalAmount;
    public Button loadAgain;
    public Label lblOutOfStock;
    public Label lblTotalDueAmount;
    public Label lblTodaysDueAmount;

    public static Integer todaysRentalCtr = 0;
    public static Integer totalDueCtr = 0;
    public static Integer todaySellCtr = 0;
    public static Double todaysTotalDue = 0.0;
    public static Double todaysTotalSell = 0.0;
    public static Double todayTotalRental = 0.0;
    public static Double totalDueAmount = 0.0;
    public static Integer stockOut = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setFields();
    }

    private void setFields() {
        //Setting total due amount
        lblTotalDueAmount.setText(totalDueAmount.toString() + " $");

        //Setting todays sell amount
        lblTodaySellCtr.setText(todaySellCtr.toString());
        lblTodaysSellAmount.setText(todaysTotalSell.toString() + " $");

        //Setting todays rent amount
        lblTodaysRentalAmount.setText(todayTotalRental.toString() + " $");
        lblTodaysRentalCtr.setText(todaysRentalCtr.toString());

        //Setting todays due
        lblTodaysDueAmount.setText(todaysTotalDue.toString() + " $");

        //Setting out of stock
        lblOutOfStock.setText(stockOut.toString());
    }

    public void loadAgain() {
        Connection connection = DBConnection.getConnection();
        try {
            assert connection != null;

            PreparedStatement getTodaysSell = connection.prepareStatement("SELECT COUNT(*), SUM(ccmd_total) FROM client_command WHERE ccmd_date = '"+ Date.valueOf(LocalDate.now()) + "'");
            PreparedStatement getTodaysPurchase = connection.prepareStatement("SELECT COUNT(*), SUM(scmd_total) FROM supplier_command WHERE scmd_date = '"+ Date.valueOf(LocalDate.now()) + "'");
            PreparedStatement getOutOfStock = connection.prepareStatement("SELECT * FROM medicine WHERE medi_stock_qte =" + 0);

            ResultSet todaysSell = getTodaysSell.executeQuery();
            ResultSet todaysRent = getTodaysPurchase.executeQuery();
            ResultSet stockOutRs = getOutOfStock.executeQuery();

            double tDAmount = 0.0; //Total Due Amount


            double tSell = 0.0; //Today's sell
            double tRent = 0.0; //Today's rent
            int rCount = 0;
            int sCount = 0;

            while (todaysSell.next()) {
                sCount += todaysSell.getInt(1);
                tSell += todaysSell.getDouble(2);
            }

            while (todaysRent.next()) {
                rCount += todaysRent.getInt(1);
                tRent += todaysRent.getDouble(2);
            }

            double todayDAmount = 0.0;
            Integer dCtr = 0;

            int sOCtr = 0;

            while (stockOutRs.next()) {
                sOCtr += 1;
            }

            totalDueAmount = tDAmount;
            todaySellCtr = sCount;
            todaysTotalSell = tSell;
            todaysRentalCtr = rCount;
            todayTotalRental = tRent;
            todaysTotalDue = todayDAmount;
            stockOut = sOCtr;

            //Setting values on the fields
            setFields();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showRent() {
        try {
            //RentalListController.todayFlag = true;
            Parent rentList = FXMLLoader.load(getClass().getResource("/main/resources/view/rentallist.fxml"));
            Scene s = new Scene(rentList);
            Stage stg = new Stage();
            stg.setScene(s);
            stg.setResizable(false);
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSell() {
        SellList.todayFlag = true;
        SellList.initStage(new Stage());
    }

}
