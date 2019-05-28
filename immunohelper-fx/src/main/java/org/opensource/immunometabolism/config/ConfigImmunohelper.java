/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensource.immunometabolism.config;

import javafx.stage.Stage;
import org.opensource.immunometabolism.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ResourceBundle;

@Configuration
public class ConfigImmunohelper {

    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @Bean
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("bundle");
    }

    @Lazy //Stage only created after Spring context bootstap
    @Bean
    public StageManager stageManager(Stage stage) {
        return new StageManager(springFXMLLoader, stage);
    }

}
