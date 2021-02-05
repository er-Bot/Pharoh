package core.controllers;

import core.db.DBConnection;
import core.db.Medicine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class Inventory implements Initializable {
    public Label lblMode;
    public TextField txtSearch;
    public Button btnSearch;
    public Label lblSearchResults;
    public ComboBox<String> txtType;
    public TextField txtStock;
    public TextField txtPrice;
    public DatePicker date;
    public TextField txtDCI;
    public TextField txtDose;
    public TextField txtForm;
    public TextField txtmedicineName;
    public Label medicineID;
    public Button btnPrevEntry;
    public Button btnAddNew;
    public Button btnNextEntry;
    public Button btnSave;
    public Label lblPageIndex;
    public Button btnListAll;
    public Button btnDelete;
    public AnchorPane itemPane;
    public Button btnEdit;

    private Medicine onView = null;
    private static boolean addFlag = false;
    private static boolean editFlag = false;
    private static boolean searchDone = false;
    private static int recordIndex = 0;
    private static int recordSize = 0;

    public static TreeMap<String, Integer> MedGrpStrInt = new TreeMap<>();
    public static TreeMap<Integer, String> MedGrpIntStr = new TreeMap<>();
    public static ObservableList<Medicine> medicineList = FXCollections.observableArrayList();
    public static ObservableList<Medicine> tempList = FXCollections.observableArrayList(); //Will hold the main list while searching
    // for autocomplete
    public static ArrayList<String> medicineNames = new ArrayList<>();
    public static ObservableList<String> medicineGroupNames = FXCollections.observableArrayList();

    public static void load(){
        Connection connection = DBConnection.getConnection();
        try {
            assert connection != null;
            PreparedStatement medListPS = connection.prepareStatement("select * from MEDICINE order by MEDI_GRP, MEDI_NAME");
            PreparedStatement medGrpListPS = connection.prepareStatement("select * from MEDICINE_GROUP order by MGRP_ID");
            ResultSet medList = medListPS.executeQuery();
            ResultSet medGrpList = medGrpListPS.executeQuery();

            while (medGrpList.next()){
                MedGrpStrInt.put(medGrpList.getString("mgrp_name"), medGrpList.getInt("mgrp_id"));
                MedGrpIntStr.put(medGrpList.getInt("mgrp_id"), medGrpList.getString("mgrp_name"));
                medicineGroupNames.add(medGrpList.getString("mgrp_name"));
            }

            while (medList.next()) {
                medicineList.add(new Medicine(
                        medList.getInt("medi_id"),
                        medList.getString("medi_name"),
                        medList.getDate("medi_expire_date"),
                        medList.getDouble("medi_pu"),
                        medList.getInt("medi_stock_qte"),
                        medList.getString("medi_dci"),
                        medList.getString("medi_form"),
                        medList.getDouble("medi_dose"),
                        medList.getInt("medi_grp")
                ));

                medicineNames.add(medList.getString("medi_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(Login.loggerAccessLevel.equals("ADMIN")) {
            btnDelete.setDisable(false);
        }
        txtType.setItems(medicineGroupNames);
        setView();
    }

    private void setView() {
        recordIndex = 0;
        recordSize = medicineList.size();

        //Setting Fields
        btnNextEntry.setOnAction(event -> {
            onView = medicineList.get(++recordIndex);
            recordNavigator();
            lblPageIndex.setText("Showing " + (recordIndex + 1) + " of " + recordSize + " results.");
            if (recordIndex == recordSize - 1)
                btnNextEntry.setDisable(true);
            btnPrevEntry.setDisable(false);
        });

        //Setting previous entry if any on previous button action
        btnPrevEntry.setOnAction(event -> {
            onView = medicineList.get(--recordIndex);
            recordNavigator();
            lblPageIndex.setText("Showing " + (recordIndex + 1) + " of " + recordSize + " results.");
            btnNextEntry.setDisable(false);
            if (recordIndex == 0)
                btnPrevEntry.setDisable(true);

        });

        if (recordSize > 0) {
            onView = medicineList.get(recordIndex); //Setting value for current record

            recordNavigator();
            lblPageIndex.setText("Showing " + (recordIndex + 1) + " of " + recordSize + " results.");

            if (recordSize > 1)
                btnNextEntry.setDisable(false);
            if(recordIndex + 1 == recordSize)
                btnNextEntry.setDisable(true);
        }

        btnPrevEntry.setDisable(true);
        btnSave.setDisable(true);
    }

    private void recordNavigator() {
        txtStock.setStyle("-fx-text-fill: #263238"); //Resetting stock color
        txtPrice.setText("0 $");

        medicineID.setText(onView.getMedi_id()+"");
        txtmedicineName.setText(onView.getMedi_name());
        txtType.setValue(MedGrpIntStr.get(onView.getMedi_grp()));
        date.setValue(onView.getMedi_expire_date().toLocalDate());
        date.setDisable(!Login.loggerAccessLevel.equals("ADMIN"));
        txtPrice.setText(onView.getMedi_pu()+" $");
        txtStock.setText(((int) onView.getMedi_stock_qte())+"");
        txtDCI.setText(onView.getMedi_dci());
        txtForm.setText(onView.getMedi_form());
        txtDose.setText(onView.getMedi_dose()+"");

        if(onView.getMedi_stock_qte() <= 5) //Setting stock color red if it's very limited
            txtStock.setStyle("-fx-text-fill: #f16666");
    }

    private void activateFields(boolean b){
        activateFields(b, false);
    }
    private void activateFields(boolean b, boolean e){
        //Enabling other buttons
        btnPrevEntry.setDisable(b);
        btnNextEntry.setDisable(b);
        btnListAll.setDisable(b);
        btnSearch.setDisable(b);
        btnDelete.setDisable(b);
        if (!e)
            btnEdit.setDisable(b);
        else btnAddNew.setDisable(b);
        btnSave.setDisable(!b);

        txtmedicineName.setEditable(b);
        date.setDisable(!b);
        txtType.setDisable(!b);
        txtPrice.setEditable(b);
        txtStock.setEditable(b);
        txtDCI.setEditable(b);
        txtDose.setEditable(b);
        txtForm.setEditable(b);

        if(!e){
            txtmedicineName.setText("");
            date.setValue(LocalDate.now());
            txtPrice.setText("");
            txtStock.setText("");
            txtDCI.setText("");
            txtDose.setText("");
            txtForm.setText("");
        }
    }

    public void btnAddMode() {
        if(addFlag) {
            addFlag = false; //Resetting addFlag value.
            activateFields(false);
            lblMode.setText("Navigation Mode");
            reloadRecords();
        }
        else {
            Connection con = DBConnection.getConnection();
            try {
                assert con != null;
                PreparedStatement ps = con.prepareStatement("SELECT max(MEDI_ID) FROM MEDICINE");
                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    medicineID.setText(Integer.valueOf(rs.getInt(1) + 1).toString());
                }

                addFlag = true; //Setting flag true to enable exit mode
                lblMode.setText("Entry Mode");
                activateFields(true);
            } catch (SQLException e) {
                PromptDialog.dialog("SQL Error", "Error occurred while executing Query.\nSQL Error Code: " + e.getErrorCode());
            }
        }
    }

    private void reloadRecords(){
        if(!medicineList.isEmpty())
            medicineList.clear();
        if(!medicineGroupNames.isEmpty())
            medicineGroupNames.clear();
        if(!medicineNames.isEmpty())
            medicineNames.clear();
        if(!MedGrpIntStr.isEmpty())
            MedGrpIntStr.clear();
        if(!MedGrpStrInt.isEmpty())
            MedGrpStrInt.clear();
        load();
        if (!medicineList.isEmpty())
        onView = medicineList.get(0);
        setView();
    }

    public void btnSaveAction(ActionEvent event) {
        boolean fieldsNotEmpty = !checkFields();
        if(fieldsNotEmpty)
            PromptDialog.dialog("Empty Fields", "One or more fields are empty!");
        else {
            if (addFlag) {
                ConfirmTask tsk = () -> {
                    addRecordToDatabase();
                    activateFields(false);
                    btnAddMode();
                };
                PromptDialog.confirm("Confirm Entry", "Press Confirm to add this entry, Cancel to go back", tsk);
            }
            if (editFlag) {
                ConfirmTask tsk = () -> {
                    updateRecord();
                    activateFields(false, true);
                    btnEditMode();
                };
                PromptDialog.confirm("Confirm Edit", "Press Confirm to update this entry, Cancel to go back", tsk);
            }
        }
    }

    private boolean checkFields() {
        boolean entryFlag = true;
        if (txtmedicineName.getText().equals("")) {
            txtmedicineName.setStyle("-fx-background-color: #f16666");
            entryFlag = false;
        } else txtmedicineName.setStyle("-fx-background-color: white");

        if(txtPrice.getText().equals("")) {
            txtPrice.setStyle("-fx-background-color: #f16666");;
            entryFlag = false;;
        } else txtPrice.setStyle("-fx-background-color: white");

        if(txtStock.getText().equals("")) {
            txtStock.setStyle("-fx-background-color: #f16666");;
            entryFlag = false;;
        } else txtPrice.setStyle("-fx-background-color: white");

        return entryFlag;
    }

    private void populatePS(PreparedStatement ps) throws SQLException {
        ps.setString(1, txtmedicineName.getText());
        ps.setInt(2, MedGrpStrInt.get(txtType.getValue()));
        ps.setDate(3, Date.valueOf(date.getValue()));
        String pu = txtPrice.getText();
        if(pu.endsWith("$"))
            pu = pu.split("[\\s$]+")[0];
        ps.setDouble(4, Double.parseDouble(pu));
        ps.setInt(5, Integer.parseInt(txtStock.getText()));
        if(txtDCI.getText() == null || txtDCI.getText().equals(""))
            ps.setNull(6, Types.NULL);
        else ps.setString(6, txtDCI.getText());
        if(txtForm.getText() == null || txtForm.getText().equals(""))
            ps.setNull(7, Types.NULL);
        else ps.setString(7, txtForm.getText());
        if(txtDose.getText() == null || txtDose.getText().equals(""))
            ps.setNull(8, Types.NULL);
        else ps.setDouble(8, Double.parseDouble(txtDose.getText()));
    }

    private void addRecordToDatabase() {
        Connection con = DBConnection.getConnection();
        try {
            assert con != null;
            PreparedStatement ps = con.prepareStatement("insert into MEDICINE (MEDI_NAME, MEDI_GRP, MEDI_EXPIRE_DATE, MEDI_PU, MEDI_STOCK_QTE, MEDI_DCI, MEDI_FORM, MEDI_DOSE) values (?, ?, ?, ?, ?, ?, ?, ?)");
            populatePS(ps);
            ps.executeUpdate();
            PromptDialog.dialog("Success", "New medicine Added!");
            reloadRecords();
        } catch (SQLException e) {
            PromptDialog.dialog("SQL Error", "Error occurred while executing Query.\nSQL Error Code: " + e.getErrorCode());
        }
    }

    public void btnDelAction() {
        ConfirmTask tsk = () -> {
            Connection connection = DBConnection.getConnection();
            try {
                assert connection != null;
                PreparedStatement ps = connection.prepareStatement("DELETE FROM  MEDICINE WHERE MEDI_ID = "+Integer.valueOf(medicineID.getText()));
                PreparedStatement psscl = connection.prepareStatement("DELETE FROM  SUPPLIER_COMMAND_LINE WHERE MEDI_ID = "+Integer.valueOf(medicineID.getText()));
                PreparedStatement psccl = connection.prepareStatement("DELETE FROM  CLIENT_COMMAND_LINE WHERE MEDI_ID = "+Integer.valueOf(medicineID.getText()));

                psccl.executeUpdate();
                psscl.executeUpdate();
                ps.executeUpdate();

                reloadRecords();

                PromptDialog.dialog("Success", "entry is deleted from the database.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };

        PromptDialog.confirm("Confirm Delete", "Press Confirm to confirm, Cancel to go back\nIn case of confirmation all elements that depends on this entry will get removed too!!", tsk);
    }

    public void btnEditMode() {
        if(editFlag) {
            editFlag = false; //Resetting addFlag value.
            activateFields(false, true);
            lblMode.setText("Navigation Mode");
            reloadRecords();
        }
        else {
            editFlag = true; //Setting flag true to enable exit mode
            lblMode.setText("Edit Mode");
            activateFields(true, true);
        }
    }

    private void updateRecord () {
        Connection con = DBConnection.getConnection();
        try {
            assert con != null;
            PreparedStatement ps = con.prepareStatement("UPDATE MEDICINE SET MEDI_NAME = ?, MEDI_GRP = ?," +
                    "MEDI_EXPIRE_DATE = ?, MEDI_PU = ?, MEDI_STOCK_QTE = ?, MEDI_DCI = ?, MEDI_FORM = ?, MEDI_DOSE = ? WHERE MEDI_ID = "+Integer.valueOf(medicineID.getText()));
            populatePS(ps);
            ps.executeUpdate();
            PromptDialog.dialog("Success", "Entry updated!");
            reloadRecords();
        } catch (SQLException e) {
            PromptDialog.dialog("SQL Error", "Error occurred while executing Query.\nSQL Error Code: " + e.getErrorCode());
        }
    }

    public void btnSearchAction(ActionEvent event) {
        if (searchDone) {
            searchDone = false;
            btnSearch.setTooltip(new Tooltip("Search by id or name"));
            lblSearchResults.setVisible(false);
            medicineList = tempList; //Reassigning customers List
            recordSize = medicineList.size();
            setView();
        }
        else {
            ObservableList<Medicine> searchResult = FXCollections.observableArrayList(); //list to hold search result
            try {
                Integer id = Integer.valueOf(txtSearch.getText());
                searchResult = searchWithID(id);
            }
            catch (NumberFormatException e) {
                String name = txtSearch.getText();
                searchResult = searchWithName(name);
            }
            finally {
                assert searchResult != null;
                if (searchResult.size() <= 0) {
                    lblSearchResults.setText("No Results Found!");
                    lblSearchResults.setVisible(true);
                }
                else {
                    tempList = FXCollections.observableArrayList(medicineList);
                    searchDone = true;
                    btnSearch.setTooltip(new Tooltip("Reset Full List"));
                    medicineList = searchResult; //Assigning search result to customerList
                    recordSize = searchResult.size();
                    lblSearchResults.setText(recordSize + " results found!");
                    lblSearchResults.setVisible(true);
                    setView();
                }
            }
        }
    }

    private ObservableList<Medicine> searchWithID(Integer id) {
        Connection con = DBConnection.getConnection();
        ObservableList<Medicine> searchResult = FXCollections.observableArrayList(); //list to hold search result

        try {
            assert con != null;
            PreparedStatement ps = con.prepareStatement("SELECT * FROM MEDICINE WHERE MEDI_ID = ?");
            ps.setInt(1, id);
            searchResult = afterSearch(ps);
        } catch (SQLException e) {
            PromptDialog.dialog("SQL Error", "Error occurred while executing Query.\nSQL Error Code: " + e.getErrorCode());
        }

        return searchResult;
    }

    private ObservableList<Medicine> searchWithName(String name) {
        Connection con = DBConnection.getConnection();
        ObservableList<Medicine> searchResult = FXCollections.observableArrayList(); //list to hold search result
        try {
            assert con != null;
            PreparedStatement ps = con.prepareStatement("SELECT * FROM MEDICINE WHERE MEDI_NAME like '%"+name+"%'");
            searchResult = afterSearch(ps);
        } catch (SQLException e) {
            PromptDialog.dialog("SQL Error", "Error occurred while executing Query.\nSQL Error Code: " + e.getErrorCode());
        }

        return searchResult;
    }

    private ObservableList<Medicine> afterSearch(PreparedStatement ps) throws SQLException {
        ObservableList<Medicine> searchResult = FXCollections.observableArrayList(); //list to hold search result
        ResultSet medRS = ps.executeQuery();

        while (medRS.next()) {
            searchResult.add(
                    new Medicine(
                            medRS.getInt("medi_id"),
                            medRS.getString("medi_name"),
                            medRS.getDate("medi_expire_date"),
                            medRS.getDouble("medi_pu"),
                            medRS.getInt("medi_stock_qte"),
                            medRS.getString("medi_dci"),
                            medRS.getString("medi_form"),
                            medRS.getDouble("medi_dose"),
                            medRS.getInt("medi_grp")
                    )
            );
        }

        return searchResult;
    }

    public void listAllItems(ActionEvent actionEvent) {
        Base.loadTable("/core/view/medicinelist.fxml");
    }

}
