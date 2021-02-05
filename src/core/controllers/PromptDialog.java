package core.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PromptDialog {

    private static double x, y;

    public Label lblHeader;
    public TextArea txtError;
    public Button btnClose;
    public Button btnOK;

    public static void dialog(String header, String error) {
        try {
            Stage stg = new Stage();
            Parent root = FXMLLoader.load(PromptDialog.class.getResource("/core/view/prompt.fxml"));

            Label lblHeader = (Label) root.lookup("#lblHeader");
            TextArea txtError = (TextArea) root.lookup("#txtError");
            Button btnClose = (Button) root.lookup("#btnClose");

            lblHeader.setText(header);
            txtError.setText(error);

            commun(root, stg, btnClose);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void confirm(String header, String error, ConfirmTask tsk){
        try {
            Stage stg = new Stage();
            Parent root = FXMLLoader.load(PromptDialog.class.getResource("/core/view/prompt_error.fxml"));

            Label lblHeader = (Label) root.lookup("#lblHeader");
            TextArea txtError = (TextArea) root.lookup("#txtError");
            Button btnClose = (Button) root.lookup("#btnClose");
            Button btnConfirm = (Button) root.lookup("#btnOK");

            lblHeader.setText(header);
            txtError.setText(error);
            btnConfirm.setOnAction(event -> {
                tsk.fire();
                stg.close();
            });

            commun(root, stg, btnClose);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void commun(Parent root, Stage stg, Button btnClose){
        stg.setAlwaysOnTop(true);

        stg.initModality(Modality.APPLICATION_MODAL);
        stg.initStyle(StageStyle.UNDECORATED);
        stg.setResizable(false);

        Scene s = new Scene(root);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y =event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stg.setX(event.getScreenX() - x);
            stg.setY(event.getScreenY() - y);
        });

        btnClose.setOnAction(event -> stg.close());

        stg.setScene(s);
        stg.show();
    }
}
