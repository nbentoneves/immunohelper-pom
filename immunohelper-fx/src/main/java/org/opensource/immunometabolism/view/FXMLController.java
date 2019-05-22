package org.opensource.immunometabolism.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.immunometabolism.excel.ExcelExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FXMLController {

    @Autowired
    private ExcelExtractor excelExtractor;

    @FXML
    private Label label;

    public void initialize() {
        label.setText("Nuno");
        excelExtractor.readExcel();
    }
}