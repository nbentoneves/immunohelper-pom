package org.opensource.immunometabolism.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.opensource.immunometabolism.tools.ConsoleComponentImpl;
import org.opensource.immunometabolism.tools.NotifyLog;
import org.opensource.immunometabolism.view.StageManager;
import org.opensource.immunometabolism.view.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MainController implements ControllerInitializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private static final String NEWLINE = "\n";

    @FXML
    public MenuItem menuItemAbout;
    public MenuItem menuItemExit;
    public MenuItem menuItemOpenFile;

    @FXML
    public TextArea consoleInfo;

    @FXML
    private ListView<CheckBox> listView;

    @FXML
    private Button buttonClean;

    @FXML
    private Button buttonSubmit;

    private StageManager stageManager;
    private NotifyLog notifyLog;

    private ObservableList<CheckBox> checkBoxes;

    @Lazy //Stage only created after Spring context bootstap
    @Autowired
    public MainController(StageManager stageManager, ConsoleComponentImpl notifyLog) {
        this.stageManager = stageManager;
        this.notifyLog = notifyLog;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        checkBoxes = FXCollections.observableArrayList();
        for (int index = 0; index < 20; index++) {
            checkBoxes.add(new CheckBox("Hello - " + index));
        }

        buttonClean.setOnAction(actionEvent -> checkBoxes.forEach(checkBox -> checkBox.setSelected(false)));
        buttonSubmit.setOnAction(actionEvent -> processFile());

        menuItemAbout.setOnAction(actionEvent -> processMenuItemAbout());
        menuItemExit.setOnAction(actionEvent -> processMenuItemExit());
        menuItemOpenFile.setOnAction(actionEvent -> processMenuItemOpenFile());

        listView.getItems().addAll(checkBoxes);

        ((ConsoleComponentImpl) notifyLog).setConsoleInfo(consoleInfo);

    }

    private void processMenuItemExit() {
        Platform.exit();
    }


    private void processMenuItemOpenFile() {

        final FileChooser fileChooser = new FileChooser();
        File uploadFile = fileChooser.showOpenDialog(null);

        if (uploadFile != null) {
            notifyLog.update("operation='processMenuItemOpenFile', msg='File uploaded: " + uploadFile.getAbsolutePath());
        }

    }

    private void processMenuItemAbout() {
        try {
            stageManager.openScene(Views.ABOUT);
        } catch (IOException ex) {
            LOGGER.error("operation='processMenuItemAbout', msg='Problem when try to load the main view!", ex);
            notifyLog.update("operation='processMenuItemAbout', msg='Problem when try to load the main view!");
        }
    }

    private void processFile() {
        LOGGER.info("operation='processFile', msg='Processing the file...'");
        notifyLog.update("operation='processFile', msg='Processing the file...'");
    }
}