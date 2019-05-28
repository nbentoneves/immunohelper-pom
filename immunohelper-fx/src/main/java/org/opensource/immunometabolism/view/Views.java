package org.opensource.immunometabolism.view;

import java.util.ResourceBundle;

public enum Views {

    MAIN {
        @Override
        String getTitle() {
            return getStringFromResourceBundle("main.app.title");
        }

        @Override
        String getFxmlFile() {
            return "/view/main.fxml";
        }
    },
    ABOUT {
        @Override
        String getTitle() {
            return getStringFromResourceBundle("about.title");
        }

        @Override
        String getFxmlFile() {
            return "/view/about.fxml";
        }
    };

    abstract String getTitle();

    abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("bundle").getString(key);
    }

}
