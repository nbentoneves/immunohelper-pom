package org.opensource.immunometabolism.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.springframework.stereotype.Controller;

@Controller
public class AboutController {

    @FXML
    private TextArea textArea;

    @FXML
    private void initialize() {

        textArea.setText("Hello...please write a text!");

    }

}