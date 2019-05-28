package org.opensource.immunometabolism;

import javafx.application.Application;
import javafx.stage.Stage;
import org.opensource.immunometabolism.controller.MainController;
import org.opensource.immunometabolism.view.StageManager;
import org.opensource.immunometabolism.view.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class MainApp extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private ConfigurableApplicationContext springContext;
    private StageManager stageManager;

    @Override
    public void init() {
        springContext = bootstrapSpringApplicationContext();
    }

    @Override
    public void start(Stage stage) {
        stageManager = springContext.getBean(StageManager.class, stage);
        try {
            stageManager.switchScene(Views.MAIN);
        } catch (IOException ex) {
            LOGGER.error("operation='start', msg='Problem when try to load the main view!", ex);
        }
    }

    @Override
    public void stop() {
        springContext.close();
    }

    public static void main(String[] args) {
        launch(MainApp.class, args);
    }

    //TODO: Review this code
    /////////////////////////// PRIVATE ///////////////////////////////////////
    private ConfigurableApplicationContext bootstrapSpringApplicationContext() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(MainApp.class);
        String[] args = getParameters().getRaw().stream().toArray(String[]::new);
        builder.headless(false); //needed for TestFX integration testing or eles will get a java.awt.HeadlessException during tests
        return builder.run(args);
    }

}