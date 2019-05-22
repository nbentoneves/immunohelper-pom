package org.opensource.immunometabolism.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import org.opensource.immunometabolism.view.FxmlView;
import org.opensource.immunometabolism.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class FXMLController implements Initializable {

    @FXML
    private MenuBar menuBar;

    private StageManager stageManager;

    @Lazy
    @Autowired
    public FXMLController(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void menuExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    public void openFile(ActionEvent actionEvent) {

        final FileChooser fileChooser = new FileChooser();
        File uploadFile = fileChooser.showOpenDialog(null);

        if (uploadFile != null) {
            System.out.println("File upload:" + uploadFile.getAbsolutePath());
        }

    }

    public void about(ActionEvent actionEvent) {
        stageManager.openScene(FxmlView.ABOUT);
    }
}