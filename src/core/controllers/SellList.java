package core.controllers;

import core.db.Command;
import core.db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SellList implements Initializable {

    public TableView<Command> tblRecent;
    public TableColumn<Command, Integer> ccmd_id;
    public TableColumn<Command, Integer> clt_id;
    public TableColumn<Command, Integer> medi_id;
    public TableColumn<Command, Date> ccmd_date;
    public TableColumn<Command, Integer> ccl_qty;
    public TableColumn<Command, Double> ccmd_total;
    public TableColumn<Command, String> empl_name;
    public Label lblSellCount;
    public Label lblDue;
    public Label lblAmount;
    public Label lblHeader;
    public Label today;

    public static boolean todayFlag = false;
    PreparedStatement getSellsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection con = DBConnection.getConnection();

        if(todayFlag) {
            lblHeader.setText("Today's Sells Report");
            try {
                assert con != null;
                getSellsList = con.prepareStatement("SELECT * FROM client_command WHERE ccmd_date ='"+ Date.valueOf(LocalDate.now()) +"'");
                showReport();
                todayFlag = false;
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                assert con != null;
                getSellsList = con.prepareStatement("SELECT * FROM client_command");
                showReport();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void showReport() {
        today.setText(LocalDate.now().toString());
        ccmd_id.setCellValueFactory(new PropertyValueFactory<>("purID"));
        clt_id.setCellValueFactory(new PropertyValueFactory<>("cusID"));
        medi_id.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        ccmd_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        ccl_qty.setCellValueFactory(new PropertyValueFactory<>("qte"));
        ccmd_total.setCellValueFactory(new PropertyValueFactory<>("paid"));
        empl_name.setCellValueFactory(new PropertyValueFactory<>("user"));

        try {
            ResultSet sellsList = getSellsList.executeQuery();

            ObservableList<Command> list = FXCollections.observableArrayList();

            int ctr = 0;
            double total = 0.0;

            while(sellsList.next()) {
                list.add(new Command(sellsList.getInt("ccmd_id"),
                        sellsList.getInt("clt_id"),
                        sellsList.getInt("medi_id"),
                        sellsList.getString("ccmd_date"),
                        sellsList.getInt("ccl_qte"),
                        sellsList.getDouble("ccmd_total"),
                        sellsList.getString("empl_name"))
                );

                ctr++;
                total += sellsList.getDouble("ccmd_total");

            }

            lblAmount.setText(total + " $");
            lblSellCount.setText(Integer.toString(ctr));

            tblRecent.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
