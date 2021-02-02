package core.controllers;

import core.db.DBConnection;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    public Label lblOutOfStock;
    public Label lblTotalBought;
    public Label lblTotalSold;
    public Label lblTotalNet;
    public Button btnTodaySell;
    public Label lblTodaySellCtr;
    public Label lblTodaysSellAmount;
    public Button btnTodayBuy;
    public Label lblTodaysBuyCtr;
    public Label lblTodaysBuyAmount;
    public Button loadAgain;

    public static Integer todaysBuyCtr = 0;
    public static Integer todaySellCtr = 0;
    public static Double todaysTotalSell = 0.0;
    public static Double todayTotalBought = 0.0;
    public static Double totalSell = 0.0;
    public static Double totalBought = 0.0;
    public static Integer stockOut = 0;

    public static Base base;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setFields();
    }

    private void setFields() {
        lblTotalBought.setText(totalBought.toString() + " $");
        lblTotalSold.setText(totalSell.toString() + " $");
        lblTodaySellCtr.setText(todaySellCtr.toString());
        lblTodaysSellAmount.setText(todaysTotalSell.toString() + " $");
        lblTodaysBuyCtr.setText(todaysBuyCtr.toString());
        lblTodaysBuyAmount.setText(todayTotalBought.toString() + " $");
        lblTotalNet.setText((totalSell - totalBought) + " $");
        lblOutOfStock.setText(stockOut.toString());
    }

    public void loadAgain() {
        Connection connection = DBConnection.getConnection();
        try {
            assert connection != null;

            PreparedStatement getTodaysSell = connection.prepareStatement("SELECT COUNT(*), SUM(ccmd_total) FROM client_command WHERE ccmd_date = '"+ Date.valueOf(LocalDate.now()) + "'");
            PreparedStatement getTodaysPurchase = connection.prepareStatement("SELECT COUNT(*), SUM(scmd_total) FROM supplier_command WHERE scmd_date = '"+ Date.valueOf(LocalDate.now()) + "'");
            PreparedStatement getOutOfStock = connection.prepareStatement("SELECT * FROM medicine WHERE medi_stock_qte =" + 0);

            PreparedStatement getTotalSell = connection.prepareStatement("SELECT SUM(ccmd_total) FROM client_command");
            PreparedStatement getTotalPurchase = connection.prepareStatement("SELECT SUM(scmd_total) FROM supplier_command");

            ResultSet todaysSell = getTodaysSell.executeQuery();
            ResultSet todaysPurchase = getTodaysPurchase.executeQuery();
            ResultSet TotalSell = getTotalSell.executeQuery();
            ResultSet TtalPurchase = getTotalPurchase.executeQuery();
            ResultSet stockOutRs = getOutOfStock.executeQuery();

            double todSell = 0.0;
            double todBuy = 0.0;
            double totSell = 0.0;
            double totBuy = 0.0;
            int pCount = 0;
            int sCount = 0;

            while (todaysSell.next()) {
                sCount += todaysSell.getInt(1);
                todSell += todaysSell.getDouble(2);
            }

            while (todaysPurchase.next()) {
                pCount += todaysPurchase.getInt(1);
                todBuy += todaysPurchase.getDouble(2);
            }

            while (TotalSell.next()) {
                totSell += TotalSell.getDouble(1);
            }

            while (TtalPurchase.next()) {
                totBuy += TtalPurchase.getDouble(1);
            }

            int sOCtr = 0;

            while (stockOutRs.next()) {
                sOCtr += 1;
            }

            todaySellCtr = sCount;
            todaysTotalSell = todSell;
            todaysBuyCtr = pCount;
            todayTotalBought = todBuy;
            totalSell = totSell;
            totalBought = totBuy;
            stockOut = sOCtr;

            //Setting values on the fields
            setFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showRent() {
        loadTable("/core/view/commandlist.fxml");
    }

    public void showSell() {
        SellList.todayFlag = true;
        loadTable("/core/view/selllist.fxml");
    }

    private void loadTable(String url){
        try {
            Scene scn = btnTodaySell.getScene();
            AnchorPane anch = (AnchorPane) scn.lookup("#paneRight");
            anch.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(getClass().getResource(url));
            pane.setPrefHeight(anch.getHeight());
            pane.setPrefWidth(anch.getWidth());

            anch.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
