package core.controllers;

import core.db.DBConnection;
import core.db.Medicine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class Inventory implements Initializable {
    public Label lblMode;
    public TextField txtSearch;
    public Button btnSearch;
    public Label lblSearchResults;

    private static int recordIndex = 0;
    private static int recordSize = 0;
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
    public Button btnOutOfStock;
    public Button btnDelete;
    public AnchorPane medicinePane;
    private Medicine onView = null;
    private static boolean addFlag = false;
    private static boolean searchDone = false;

    public static TreeMap<String, Integer> medicineType = new TreeMap<>();
    public static ObservableList<Medicine> medicineList = FXCollections.observableArrayList(); //This field will auto set from InitializerController Class
    public static ObservableList<Medicine> tempList = FXCollections.observableArrayList(); //Will hold the main list while searching
    public static ArrayList<String> medicineNames = new ArrayList<>();
    public static ObservableList<String> medicineTypeNames = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(Login.loggerAccessLevel.equals("Admin")) {
            btnDelete.setDisable(false);
        }
        txtType.setItems(medicineTypeNames);
        TextFields.bindAutoCompletion(txtSearch, medicineNames);
        setView();
    }

    private void reloadRecords(){
        medicineList.clear();
        Connection connection = DBConnection.getConnection();
        try {
            assert connection != null;
            PreparedStatement getmedicineList = connection.prepareStatement("SELECT * FROM MEDICINE ORDER BY MEDI_ID");
            ResultSet medicineResultSet = getmedicineList.executeQuery();

            while(medicineResultSet.next()) {
                Medicine medicine = new Medicine(
                        medicineResultSet.getInt("medi_id"),
                        medicineResultSet.getString("medi_name"),
                        medicineResultSet.getDate("medi_expire_date"),
                        medicineResultSet.getDouble("medi_pu"),
                        medicineResultSet.getInt("medi_stock_qte"),
                        medicineResultSet.getString("medi_dci"),
                        medicineResultSet.getString("medi_form"),
                        medicineResultSet.getDouble("medi_dose"),
                        medicineResultSet.getInt("medi_grp")
                );

                medicineNames.add(medicineResultSet.getString("medi_name"));

                medicineList.add(medicine);
            }

            setView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setView() {
        recordIndex = 0; //Resetting index value
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

            if (recordSize > 1) {
                btnNextEntry.setDisable(false);
            }
        }

        btnPrevEntry.setDisable(true);
    }

    private void recordNavigator() {
        /*txtStock.setStyle("-fx-text-fill: #263238"); //Resetting stock color

        txtPrice.setText("0.0");

        medicineID.setText(Integer.toString(onView.getMedi_id()));
        txtmedicineName.setText(onView.getMedi_name());
        txtType.setValue(onView.get().toString());
        if(onView.isRent()) {
            chkRent.setSelected(true);
            txtRentRate.setText(Double.toString(onView.getRentRate()));
        }
        if(onView.isSale()) {
            chkSale.setSelected(true);
            txtPrice.setText(Double.toString(onView.getSalePrice()));
        }
        txtStock.setText(Integer.toString(onView.getStock()));

        if(onView.getStock() <= 5) //Setting stock color red if it's very limited
            txtStock.setStyle("-fx-text-fill: red");

        //Setting Image
        if (onView.getPhoto() == null) {
            ImagePattern img = new ImagePattern(new Image("/main/resources/icons/trolley.png"));
            imgCustomerPhoto.setFill(img);
        } else {
            try {
                imgPath = onView.getPhoto();

                File tmpPath = new File(imgPath.replace("file:", ""));

                if(tmpPath.exists()) {
                    ImagePattern img = new ImagePattern(new Image(imgPath));
                    imgCustomerPhoto.setFill(img);
                } else {
                    imgPath = null;
                    ImagePattern img = new ImagePattern(new Image("/main/resources/icons/trolley.png"));
                    imgCustomerPhoto.setFill(img);
                }

            } catch (Exception e) {
                //Fallback photo in case image not found
                ImagePattern img = new ImagePattern(new Image("/main/resources/icons/trolley.png"));
                imgCustomerPhoto.setFill(img);
            }
        }*/
    }

    public void listAllmedicines(ActionEvent event) {
        /*btnGoBack.setOnAction(e -> {
            medicineListPane.setVisible(false);  //Setting medicine list pane visible
            medicinePane.setVisible(true); //Setting medicine pane visible
        });
        tbl.setmedicines(medicineList);
        listView();*/
    }

    public void btnDelAction(ActionEvent event) {
        /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setGraphic(new ImageView(this.getClass().getResource("/main/resources/icons/x-button.png").toString()));

        alert.setHeaderText("Do you really want to delete this entry?");
        alert.setContentText("Press OK to confirm, Cancel to go back");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
            Connection connection = DBConnection.getConnection();
            try {
                PreparedStatement ps = connection.prepareStatement("DELETE FROM  medicine WHERE medicineID = "+Integer.valueOf(medicineID.getText()));
                ps.executeUpdate();

                new PromptDialogController("Operation Successful.", "medicine is deleted from the database. Restart or refresh to see effective result.");
                reloadRecords();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    public void outOfStockList(ActionEvent event) {
/*
        btnGoBack.setOnAction(e -> {
            medicineListPane.setVisible(false);  //Setting medicine list pane visible
            medicinePane.setVisible(true); //Setting medicine pane visible
        });

        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM medicine, medicinetype WHERE medicineTypeId = medicineType_medicineTypeId AND stock ="+0);
            ResultSet medicineResultSet = ps.executeQuery();

            ObservableList<medicine> outOfStk = FXCollections.observableArrayList();

            while(medicineResultSet.next()) {
                medicine medicine = new medicine(medicineResultSet.getInt("medicineID"),
                        medicineResultSet.getString("medicineName"),
                        medicineResultSet.getInt("stock"),
                        false,
                        false,
                        medicineResultSet.getDouble("salePrice"),
                        medicineResultSet.getDouble("rentRate"),
                        medicineResultSet.getString("photo"),
                        medicineResultSet.getString("typeName"));

                medicineNames.add(medicineResultSet.getString("medicineName"));

                if(medicineResultSet.getString("rentalOrSale").contains("Rental"))
                {
                    medicine.setRent(true);
                }
                if(medicineResultSet.getString("rentalOrSale").contains("Sale")) {
                }

                btnGoBack.setOnAction(e -> {
                    medicineListPane.setVisible(false);  //Setting medicine list pane visible
                    medicinePane.setVisible(true); //Setting medicine pane visible
                });

                outOfStk.add(medicine);

            }

            tbl.setmedicines(outOfStk);

            listView();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    private void listView() {
       /* medicinePane.setVisible(false); //Setting default medicine pane not visible
        medicineListPane.setVisible(true); //Setting medicine list visible

        columnmedicineID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnmedicineName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnmedicineType.setCellValueFactory(new PropertyValueFactory<>("medicineType"));
        columnForRent.setCellValueFactory(new PropertyValueFactory<>("rent"));
        columnForSale.setCellValueFactory(new PropertyValueFactory<>("sale"));
        columnRentalRate.setCellValueFactory(new PropertyValueFactory<>("rentRate"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("salePrice"));*/

    }

    public void btnAddMode(ActionEvent event) {
     /*   if(addFlag) {
            addFlag = false; //Resetting addFlag value.
            btnAddIcon.setGlyphName("PLUS");

            //Enabling other buttons
            btnPrevEntry.setDisable(false);
            btnNextEntry.setDisable(false);
            btnListAll.setDisable(false);
            btnSearch.setDisable(false);
            btnOutOfStock.setDisable(false);
            btnDelete.setDisable(false);

            String defColor = "#263238";

            //Changing Focus Color
            txtmedicineName.setUnFocusColor(Color.web(defColor));
            txtPrice.setUnFocusColor(Color.web(defColor));
            txtRentRate.setUnFocusColor(Color.web(defColor));
            txtType.setUnFocusColor(Color.web(defColor));
            txtSearch.setUnFocusColor(Color.web(defColor));
            txtStock.setUnFocusColor(Color.web(defColor));

            //Setting Label
            lblMode.setText("Navigation Mode");

            reloadRecords();


        } else {
            Connection con = DBConnection.getConnection();
            try {
                PreparedStatement ps = con.prepareStatement("SELECT max(medicineID) FROM medicine");
                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    medicineID.setText(Integer.valueOf(rs.getInt(1) + 1).toString());
                }

                addFlag = true; //Setting flag true to enable exit mode
                btnAddIcon.setGlyphName("UNDO"); //Changing glyph

                //Setting Label
                lblMode.setText("Entry Mode");

                ImagePattern img = new ImagePattern(new Image("/main/resources/icons/trolley.png"));
                imgCustomerPhoto.setFill(img);

                //Disabling other buttons
                btnPrevEntry.setDisable(true);
                btnNextEntry.setDisable(true);
                btnOutOfStock.setDisable(true);
                btnListAll.setDisable(true);
                btnSearch.setDisable(true);
                btnDelete.setDisable(true);

                //Cleaning fields
                txtmedicineName.setText("");
                txtType.setValue("");
                txtRentRate.setText("");
                txtPrice.setText("");
                imgPath = null;
                txtStock.setText("");
            } catch (SQLException e) {
                new PromptDialogController("SQL Error!", "Error occured while executing Query.\nSQL Error Code: " + e.getErrorCode());
            }
        }*/
    }

    private ObservableList<Medicine> searchWithID(Integer id) {
        return null;
     /*   Connection con = DBConnection.getConnection();

        String idSQL = "SELECT * FROM medicine, medicinetype WHERE medicineID = ? AND medicineTypeId = medicineType_medicineTypeId";

        ObservableList<medicine> searchResult = FXCollections.observableArrayList(); //list to hold search result

        try {
            PreparedStatement preparedStatement = con.prepareStatement(idSQL);
            preparedStatement.setInt(1, id);

            ResultSet medicineResultSet = preparedStatement.executeQuery();

            //Getting values from medicines result set
            while (medicineResultSet.next()) {
                medicine medicine = new medicine(medicineResultSet.getInt("medicineID"),
                        medicineResultSet.getString("medicineName"),
                        medicineResultSet.getInt("stock"),
                        false,
                        false,
                        medicineResultSet.getDouble("salePrice"),
                        medicineResultSet.getDouble("rentRate"),
                        medicineResultSet.getString("photo"),
                        medicineResultSet.getString("typeName"));

                if (medicineResultSet.getString("rentalOrSale").contains("Rental")) {
                    medicine.setRent(true);
                }
                if (medicineResultSet.getString("rentalOrSale").contains("Sale")) {
                    medicine.setSale(true);
                }

                searchResult.add(medicine);
            }

            con.close();

        } catch (SQLException e) {
            new PromptDialogController("SQL Error!",
                    "Error occured while executing Query.\nSQL Error Code: " + e.getErrorCode());
        }

        return searchResult;*/
    }

    private ObservableList<Medicine> searchWithName(String name) {
        return null;
     /*   Connection con = DBConnection.getConnection();

        String nameSQL = "SELECT * FROM medicine, medicinetype WHERE medicineName COLLATE UTF8_GENERAL_CI like ? AND medicineTypeId = medicineType_medicineTypeId";

        ObservableList<medicine> searchResult = FXCollections.observableArrayList(); //list to hold search result

        try {
            PreparedStatement preparedStatement2 = con.prepareStatement(nameSQL);
            preparedStatement2.setString(1, "%" + txtSearch.getText() + "%");

            ResultSet medicineResultSet = preparedStatement2.executeQuery();

            //Getting values from customers result set
            while (medicineResultSet.next()) {
                medicine medicine = new medicine(medicineResultSet.getInt("medicineID"),
                        medicineResultSet.getString("medicineName"),
                        medicineResultSet.getInt("stock"),
                        false,
                        false,
                        medicineResultSet.getDouble("salePrice"),
                        medicineResultSet.getDouble("rentRate"),
                        medicineResultSet.getString("photo"),
                        medicineResultSet.getString("typeName"));

                if(medicineResultSet.getString("rentalOrSale").contains("Rental")) {
                    medicine.setRent(true);
                }
                if(medicineResultSet.getString("rentalOrSale").contains("Sale")) {
                    medicine.setSale(true);
                }
                searchResult.add(medicine);
            }

            con.close();

        } catch (SQLException e) {
            new PromptDialogController("SQL Error!",
                    "Error occured while executing Query.\nSQL Error Code: " + e.getErrorCode());
        }

        return searchResult;*/
    }

    public void btnSearchAction(ActionEvent event) {
      /*  if (searchDone) {
            searchDone = false;
            lblSearchResults.setVisible(false);
            medicineList = tempList; //Reassigning customers List
            recordSize = medicineList.size();
            btnSearch.setTooltip(new Tooltip("Search with customers name or id"));
            btnSearchIcon.setGlyphName("SEARCH");
            setView();
        } else {
            ObservableList<medicine> searchResult = FXCollections.observableArrayList(); //list to hold search result
            try {
                // Checking if input field is a number then searching with ID
                // If parsing of Integer fails then we shall try to search
                // with name instead
                Integer id = Integer.valueOf(txtSearch.getText());
                searchResult = searchWithID(id);
            } catch (NumberFormatException e) {
                String name = txtSearch.getText();
                searchResult = searchWithName(name);
            } finally {
                if (searchResult.size() <= 0) {
                    lblSearchResults.setText("No Results Found!");
                    lblSearchResults.setVisible(true);
                } else {
                    tempList = FXCollections.observableArrayList(medicineList);
                    searchDone = true;
                    btnSearchIcon.setGlyphName("CLOSE");
                    btnSearch.setTooltip(new Tooltip("Reset Full List"));
                    medicineList = searchResult; //Assigning search result to customerList
                    recordSize = searchResult.size();
                    lblSearchResults.setText(recordSize + " results found!");
                    lblSearchResults.setVisible(true);
                    setView();
                }
            }

        }*/
    }

    private boolean checkFields() {
        return false;
       /* boolean entryFlag = true;
        if (txtmedicineName.getText().equals("")) {
            txtmedicineName.setUnFocusColor(Color.web("red"));
            entryFlag = false;
        }

        if(chkSale.isSelected() && txtPrice.getText().equals("")) {
            txtPrice.setUnFocusColor(Color.web("red"));
            entryFlag = false;
        }

        if(!chkRent.isSelected() && !chkSale.isSelected()) {
            entryFlag = false;
        }

        if(chkRent.isSelected() && txtRentRate.getText().equals("")) {
            txtRentRate.setUnFocusColor(Color.web("red"));
            entryFlag = false;
        }

        if(txtType.getValue().equals("")) {
            txtType.setUnFocusColor(Color.web("red"));
            entryFlag = false;;
        }

        if(txtStock.getText().equals("")) {
            txtStock.setUnFocusColor(Color.web("red"));
            entryFlag = false;;
        }

        return entryFlag;*/
    }

    private void addRecordToDatabase() {
      /*  Connection con = DBConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO medicine VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setInt(1, Integer.valueOf(medicineID.getText()));
            ps.setString(2, txtmedicineName.getText());
            ps.setInt(3, Integer.valueOf(txtStock.getText()));

            if(chkRent.isSelected() && chkSale.isSelected())
                ps.setString(4, "Rental,Sale");
            else if(chkSale.isSelected())
                ps.setString(4, "Sale");
            else if(chkRent.isSelected())
                ps.setString(4, "Rental");

            Double salePrice = 0.0;

            if(!txtPrice.getText().equals("")) {
                salePrice = Double.valueOf(txtPrice.getText());
            }

            Double rentPrice = 0.0;

            if(!txtRentRate.getText().equals("")) {
                rentPrice = Double.valueOf(txtRentRate.getText());
            }

            ps.setDouble(5, salePrice);
            ps.setDouble(6, rentPrice);
            ps.setString(7, imgPath);
            ps.setInt(8, medicineType.get(txtType.getValue()));

            ps.executeUpdate();

            new PromptDialogController("Operation Successful!", "New medicine Added!");


        } catch (SQLException e) {
            new PromptDialogController("SQL Error!", "Error occured while executing Query.\nSQL Error Code: " + e.getErrorCode());
        }*/
    }

    private void updateRecord () {
      /*  Connection con = DBConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE medicine SET medicineID = ?, medicineName = ?," +
                    "stock = ?, rentalOrSale = ?, salePrice = ?, rentRate = ?, photo = ?, medicineType_medicineTypeId = ? WHERE medicineID = "+Integer.valueOf(medicineID.getText()));
            ps.setInt(1, Integer.valueOf(medicineID.getText()));
            ps.setString(2, txtmedicineName.getText());
            ps.setInt(3, Integer.valueOf(txtStock.getText()));

            if(chkRent.isSelected() && chkSale.isSelected())
                ps.setString(4, "Rental,Sale");
            else if(chkSale.isSelected())
                ps.setString(4, "Sale");
            else if(chkRent.isSelected())
                ps.setString(4, "Rental");

            Double salePrice = 0.0;

            if(!txtPrice.getText().equals("")) {
                salePrice = Double.valueOf(txtPrice.getText());
            }

            Double rentPrice = 0.0;

            if(!txtRentRate.getText().equals("")) {
                rentPrice = Double.valueOf(txtRentRate.getText());
            }

            ps.setDouble(5, salePrice);
            ps.setDouble(6, rentPrice);
            ps.setString(7, imgPath);
            ps.setInt(8, medicineType.get(txtType.getValue()));

            ps.executeUpdate();

            new PromptDialogController("Operation Successful!", "Entry updated!");

            reloadRecords();

        } catch (SQLException e) {
            e.printStackTrace();
            new PromptDialogController("SQL Error!", "Error occured while executing Query.\nSQL Error Code: " + e.getErrorCode());
        }*/
    }

    public void btnSaveAction(ActionEvent event) {
    /*    if (addFlag) {
            boolean fieldsNotEmpty = checkFields();
            if(fieldsNotEmpty) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Entry");
                alert.setGraphic(new ImageView(this.getClass().getResource("/main/resources/icons/question (2).png").toString()));

                alert.setHeaderText("Do you really want to add this entry?");
                alert.setContentText("Press OK to confirm, Cancel to go back");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    addRecordToDatabase();
                }
            } else {
                JFXSnackbar snackbar = new JFXSnackbar(medicinePane);
                snackbar.show("One or more fields are empty!", 3000);
            }
        } else {
            boolean fieldsNotEmpty = checkFields();
            if(fieldsNotEmpty) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Edit");
                alert.setGraphic(new ImageView(this.getClass().getResource("/main/resources/icons/question (2).png").toString()));

                alert.setHeaderText("Do you really want to update this entry?");
                alert.setContentText("Press OK to confirm, Cancel to go back");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    updateRecord();
                }
            }

        }*/
    }
}
