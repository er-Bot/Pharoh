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
    public PromptDialog(String header, String error) {

        Stage stg = new Stage();
        stg.setAlwaysOnTop(true);

        //Modality is so that this window must be interacted before others
        stg.initModality(Modality.APPLICATION_MODAL);
        stg.initStyle(StageStyle.UNDECORATED);
        stg.setResizable(false);

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/core/view/prompt.fxml"));
            root.setOnMousePressed(event -> {
                x = event.getSceneX();
                y =event.getSceneY();
            });
            root.setOnMouseDragged(event -> {
                stg.setX(event.getScreenX() - x);
                stg.setY(event.getScreenY() - y);
            });


            Scene s = new Scene(root);

            //Getting useful nodes from FXML to set error report
            Label lblHeader = (Label) root.lookup("#lblHeader");
            TextArea txtError = (TextArea) root.lookup("#txtError");
            Button btnClose = (Button) root.lookup("#btnClose");

            lblHeader.setText(header);
            txtError.setText(error);

            //Setting close button event
            btnClose.setOnAction(event -> stg.hide());

            stg.setScene(s);
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
