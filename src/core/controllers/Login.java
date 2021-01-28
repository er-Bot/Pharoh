package core.controllers;

import core.db.DBConnection;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login implements Initializable  {
    public Button quitButton;
    public TextField txtUsername;
    public PasswordField txtPassword;
    public CheckBox chkPasswordMask;
    public TextField txtPasswordShown;
    public Label lblWarnUsername;
    public Label lblWarnPassword;
    public CheckBox chkSaveCredentials;
    public Button btnLogIn;
    public AnchorPane topPane;

    public static String loggerUsername = "";
    public static String loggerAccessLevel = "";

    public void onEnterKey(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            userLogger();
        }
    }

    public void chkPasswordMaskAction() {
        if (chkPasswordMask.isSelected())
        {
            txtPasswordShown.setText(txtPassword.getText());
            txtPasswordShown.setVisible(true);
            txtPassword.setVisible(false);
        } else {
            txtPassword.setText(txtPasswordShown.getText());
            txtPassword.setVisible(true);
            txtPasswordShown.setVisible(false);
        }
    }

    public void ctrlLogInCheck() {
        userLogger();
    }

    public void quitLogin() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * set the previous saved username password if any.
     * password visibility toggling
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection connection = DBConnection.getConnection();

        try {
            assert connection != null;
            PreparedStatement getCredents = connection.prepareStatement("select * from credential");
            ResultSet resultSet = getCredents.executeQuery();

            if(resultSet.next()) {
                txtUsername.setText(resultSet.getString(1)); //Getting Saved Username
                txtPassword.setText(resultSet.getString(2)); //Getting Saved Password
            }
            txtPasswordShown.setVisible(false);

            txtUsername.setOnMouseClicked(event -> lblWarnUsername.setVisible(false));
            txtPasswordShown.setOnMouseClicked(event -> lblWarnPassword.setVisible(false));
            txtPassword.setOnMouseClicked(event -> lblWarnPassword.setVisible(false));

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void userLogger() {
        //Taking input from the username & password fields
        String username = txtUsername.getText();
        String password;

        //Getting input from the field in which
        //user inputted password.
        //Note: We have two password field.
        //One for visible password and another for hidden.
        if(chkPasswordMask.isSelected())
            password = txtPasswordShown.getText();
        else
            password = txtPassword.getText();

        //Checking if any fields were blank
        //If not then we're attempting to connect to DB

        if (username.equals("")) {
            lblWarnUsername.setVisible(true);
        } else if(password.equals("")) {
            lblWarnPassword.setVisible(true);
        } else {
            try {
                Connection con = DBConnection.getConnection();
                String sql = "SELECT * FROM users WHERE usr_name = ? AND usr_pass = ?";
                assert con != null;
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    //Setting user credentials for further processing
                    loggerUsername = rs.getString("usr_name");
                    loggerAccessLevel = rs.getString("usr_type");

                    //Checking for Save Credential CheckBox
                    //Upon true value saving new credents in DataBase
                    if(chkSaveCredentials.isSelected()) {
                        PreparedStatement delPrevCredents = con.prepareStatement("DELETE FROM credential");
                        delPrevCredents.executeUpdate();

                        PreparedStatement saveCredents = con.prepareStatement("INSERT INTO credential VALUES ('"+username+"',"+"'"+password+"')");
                        saveCredents.executeUpdate();
                    }

                    Stage logIn = (Stage) btnLogIn.getScene().getWindow(); //Getting current window

                    Stage base = new Stage();
                    Parent root;

                    //Moving to InitializerController Class to load all required main.resources
                    try {
                        root = FXMLLoader.load(getClass().getResource("/core/view/initializer.fxml"));
                        Scene s = new Scene(root);
                        logIn.setScene(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    new PromptDialog("Authentication Error!", "Either username or password did not match!");
                }

                con.close();
            } catch (SQLException e) {
                System.out.println(e.getErrorCode());
                e.printStackTrace();
            }
        }
    }
}
