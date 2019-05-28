package org.opensource.immunometabolism.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.opensource.immunometabolism.tools.NotifyLog;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class AboutController implements ControllerInitializable {

    @FXML
    private TextArea textAbout;

    private NotifyLog notifyLog;

    @Lazy
    public AboutController(NotifyLog notifyLog){
        this.notifyLog = notifyLog;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textAbout.setText("Hello...please write a text!");
        notifyLog.update("Open about scene");
    }
}