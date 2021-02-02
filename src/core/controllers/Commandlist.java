package core.controllers;

import core.db.ClientCommand;
import core.db.DBConnection;
import core.db.SupplierCommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Commandlist implements Initializable {
    public Label lblHeader;
    public Button backBtn;
    public Label today;
    public TableView<SupplierCommand> tblRecent;
    public TableColumn<ClientCommand, Integer> scmd_id;
    public TableColumn<ClientCommand, Integer> sup_id;
    public TableColumn<ClientCommand, Integer> medi_id;
    public TableColumn<ClientCommand, Date> scmd_date;
    public TableColumn<ClientCommand, Integer> scl_qty;
    public TableColumn<ClientCommand, Double> scmd_total;
    public TableColumn<ClientCommand, String> empl_name;

    public static boolean todayFlag = false;
    public Label lblPurchaseCount;
    public Label lblAmount;
    PreparedStatement getPurchasesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection con = DBConnection.getConnection();

        if(todayFlag) {
            lblHeader.setText("Today's Purchases Report");
            try {
                assert con != null;
                getPurchasesList = con.prepareStatement("SELECT * FROM SUPPLIER_COMMAND WHERE SCMD_DATE ='"+ Date.valueOf(LocalDate.now()) +"'");
                showReport();
                todayFlag = false;
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                assert con != null;
                getPurchasesList = con.prepareStatement("SELECT * FROM SUPPLIER_COMMAND");
                showReport();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void showReport() {
        today.setText(LocalDate.now().toString());
        scmd_id.setCellValueFactory(new PropertyValueFactory<>("purID"));
        sup_id.setCellValueFactory(new PropertyValueFactory<>("cusID"));
        medi_id.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        scmd_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        scl_qty.setCellValueFactory(new PropertyValueFactory<>("qte"));
        scmd_total.setCellValueFactory(new PropertyValueFactory<>("paid"));
        empl_name.setCellValueFactory(new PropertyValueFactory<>("user"));

        try {
            ResultSet sellsList = getPurchasesList.executeQuery();

            ObservableList<SupplierCommand> list = FXCollections.observableArrayList();

            int ctr = 0;
            double total = 0.0;

            while(sellsList.next()) {
                list.add(new SupplierCommand(
                        sellsList.getInt("scmd_id"),
                        sellsList.getInt("sup_id"),
                        sellsList.getInt("medi_id"),
                        sellsList.getString("scmd_date"),
                        sellsList.getInt("scl_qte"),
                        sellsList.getDouble("scmd_total"),
                        sellsList.getString("empl_name"))
                );

                ctr++;
                total += sellsList.getDouble("scmd_total");

            }

            lblAmount.setText(total + " $");
            lblPurchaseCount.setText(Integer.toString(ctr));

            tblRecent.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void backToDashboard() {
        Base b = Base.currentBase;
        b.ctrlRightPane(b.FXML_URL.get("Dashboard"));
    }
}
