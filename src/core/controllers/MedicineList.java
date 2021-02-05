package core.controllers;

import core.db.DBConnection;
import core.db.Medicine;
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
import java.util.ResourceBundle;

public class MedicineList implements Initializable {

    public TableView<Medicine> tblRecent;
    public TableColumn<Medicine, Integer> id;
    public TableColumn<Medicine, String> name;
    public TableColumn<Medicine, Date> date;
    public TableColumn<Medicine, Double> pu;
    public TableColumn<Medicine, Integer> qty;
    public TableColumn<Medicine, String> dci;
    public TableColumn<Medicine, String> form;
    public TableColumn<Medicine, Double> dose;
    public TableColumn<Medicine, String> group;

    public static boolean outOfStockFlag = false;
    public Button backBtn;
    public Button outstkBtn;
    public Label lblHeader;
    private URL url;
    private ResourceBundle rb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.url = location;
        rb = resources;

        Connection con = DBConnection.getConnection();
        try {
            assert con != null;

            PreparedStatement ps;
            if(outOfStockFlag) {
                lblHeader.setText("Out of stock List");
                ps = con.prepareStatement("select MEDI_ID, MEDI_NAME, MEDI_EXPIRE_DATE, MEDI_PU, MEDI_STOCK_QTE, MEDI_DCI, MEDI_FORM, MEDI_DOSE, MGRP_NAME\n" +
                        "from MEDICINE, MEDICINE_GROUP where MEDI_GRP = MGRP_ID and MEDI_STOCK_QTE < 5 order by MEDI_STOCK_QTE");
            }
            else {
                lblHeader.setText("Inventory List");
                ps = con.prepareStatement("select MEDI_ID, MEDI_NAME, MEDI_EXPIRE_DATE, MEDI_PU, MEDI_STOCK_QTE, MEDI_DCI, MEDI_FORM, MEDI_DOSE, MGRP_NAME\n" +
                        "from MEDICINE, MEDICINE_GROUP where MEDI_GRP = MGRP_ID order by MGRP_NAME, MEDI_NAME");
            }
            loadUp(ps);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadUp(PreparedStatement ps) throws SQLException {
        ResultSet meds = ps.executeQuery();

        id.setCellValueFactory(new PropertyValueFactory<>("medi_id"));
        name.setCellValueFactory(new PropertyValueFactory<>("medi_name"));
        date.setCellValueFactory(new PropertyValueFactory<>("medi_expire_date"));
        pu.setCellValueFactory(new PropertyValueFactory<>("medi_pu"));
        qty.setCellValueFactory(new PropertyValueFactory<>("medi_stock_qte"));
        dci.setCellValueFactory(new PropertyValueFactory<>("medi_dci"));
        form.setCellValueFactory(new PropertyValueFactory<>("medi_form"));
        dose.setCellValueFactory(new PropertyValueFactory<>("medi_dose"));
        group.setCellValueFactory(new PropertyValueFactory<>("mgrp_name"));

        ObservableList<Medicine> list = FXCollections.observableArrayList();

        while(meds.next()) {
            list.add(new Medicine(
                    meds.getInt("medi_id"),
                    meds.getString("medi_name"),
                    meds.getDate("medi_expire_date"),
                    meds.getDouble("medi_pu"),
                    meds.getInt("medi_stock_qte"),
                    meds.getString("medi_dci"),
                    meds.getString("medi_form"),
                    meds.getDouble("medi_dose"),
                    meds.getString("mgrp_name")
            ));
        }

        tblRecent.setItems(list);
    }

    public void backToInventory() {
        Base b = Base.currentBase;
        b.ctrlRightPane(b.FXML_URL.get("Inventory"));
    }

    public void alterOutOfStock() {
        outOfStockFlag = !outOfStockFlag;
        initialize(url, rb);
    }
}
