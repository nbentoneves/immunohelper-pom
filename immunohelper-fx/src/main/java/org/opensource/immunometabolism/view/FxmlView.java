package org.opensource.immunometabolism.view;

import java.util.ResourceBundle;

public enum FxmlView {

    MAIN {
        @Override
        String getTitle() {
            return getStringFromResourceBundle("main.app.title");
        }

        @Override
        String getFxmlFile() {
            return "/scene.fxml";
        }
    }, ABOUT {
        @Override
        String getTitle() {
            return getStringFromResourceBundle("about.title");
        }

        @Override
        String getFxmlFile() {
            return "/about.fxml";
        }
    };

    abstract String getTitle();

    abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
