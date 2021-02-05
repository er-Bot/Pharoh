package core.controllers;

import core.db.ClientCommand;
import core.db.DBConnection;
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

public class SellList implements Initializable {

    public TableView<ClientCommand> tblRecent;
    public TableColumn<ClientCommand, Integer> ccmd_id;
    public TableColumn<ClientCommand, Integer> clt_id;
    public TableColumn<ClientCommand, Integer> medi_id;
    public TableColumn<ClientCommand, Date> ccmd_date;
    public TableColumn<ClientCommand, Integer> ccl_qty;
    public TableColumn<ClientCommand, Double> medi_pu;
    public TableColumn<ClientCommand, Double> ccmd_total;
    public Label lblSellCount;
    public Label lblAmount;
    public Label lblHeader;
    public Label today;

    public static boolean todayFlag = false;
    public Button backBtn;
    PreparedStatement getSellsList;
    private int ctr;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection con = DBConnection.getConnection();

        if(todayFlag) {
            lblHeader.setText("Today's Sells Report");
            try {
                assert con != null;
                getSellsList = con.prepareStatement("select CLIENT_COMMAND.CCMD_ID as \"command\", CLT_ID as \"client\", MEDICINE.MEDI_ID as \"medicine\", CCMD_DATE as \"date\", CCL_QTE as \"quantity\", MEDI_PU as \"price\", CCL_QTE * MEDI_PU as \"amount\"\n" +
                        "from CLIENT_COMMAND, CLIENT_COMMAND_LINE, MEDICINE\n" +
                        "where CLIENT_COMMAND.CCMD_ID = CLIENT_COMMAND_LINE.CCMD_ID and trunc(CCMD_DATE) = trunc(sysdate) and MEDICINE.MEDI_ID = CLIENT_COMMAND_LINE.MEDI_ID");
                PreparedStatement ctrps = con.prepareStatement("select count(*) from CLIENT_COMMAND where trunc(CCMD_DATE) = trunc(sysdate)");
                ResultSet ctrs = ctrps.executeQuery();
                while (ctrs.next())
                    ctr += ctrs.getInt(1);
                showReport();
                todayFlag = false;
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                assert con != null;
                getSellsList = con.prepareStatement("select CLIENT_COMMAND.CCMD_ID as \"command\", CLT_ID as \"client\", MEDICINE.MEDI_ID as \"medicine\", CCMD_DATE as \"date\", CCL_QTE as \"quantity\", MEDI_PU as \"price\", CCL_QTE * MEDI_PU as \"amount\"\n" +
                        "from CLIENT_COMMAND, CLIENT_COMMAND_LINE, MEDICINE\n" +
                        "where CLIENT_COMMAND.CCMD_ID = CLIENT_COMMAND_LINE.CCMD_ID and MEDICINE.MEDI_ID = CLIENT_COMMAND_LINE.MEDI_ID");
                PreparedStatement ctrps = con.prepareStatement("select count(*) from CLIENT_COMMAND");
                ResultSet ctrs = ctrps.executeQuery();
                while (ctrs.next())
                    ctr += ctrs.getInt(1);
                showReport();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void showReport() {
        today.setText(LocalDate.now().toString());
        ccmd_id.setCellValueFactory(new PropertyValueFactory<>("command"));
        clt_id.setCellValueFactory(new PropertyValueFactory<>("client"));
        medi_id.setCellValueFactory(new PropertyValueFactory<>("medicine"));
        ccmd_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        ccl_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        medi_pu.setCellValueFactory(new PropertyValueFactory<>("price"));
        ccmd_total.setCellValueFactory(new PropertyValueFactory<>("amount"));

        try {
            ResultSet sellsList = getSellsList.executeQuery();

            ObservableList<ClientCommand> list = FXCollections.observableArrayList();

            double total = 0.0;

            while(sellsList.next()) {
                list.add(ClientCommand.getInstance(sellsList));
                total += sellsList.getDouble("amount");
            }

            lblAmount.setText(total + " $");
            lblSellCount.setText(Integer.toString(ctr));

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
