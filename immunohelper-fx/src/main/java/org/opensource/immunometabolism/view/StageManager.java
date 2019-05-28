package org.opensource.immunometabolism.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opensource.immunometabolism.config.SpringFXMLLoader;

import java.io.IOException;
import java.util.Objects;

public class StageManager {

    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }

    public void switchScene(final Views view) throws IOException {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        render(viewRootNodeHierarchy, view.getTitle(), true);
    }

    public void openScene(final Views view) throws IOException {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        render(viewRootNodeHierarchy, view.getTitle(), false);
    }

    private void render(final Parent rootnode, String title, boolean switchScene) {

        Stage stage = !switchScene ? new Stage() : this.primaryStage;

        Scene scene = prepareScene(rootnode, stage);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    private Scene prepareScene(Parent rootNode, Stage stage) {
        Scene scene = stage.getScene();

        if (scene == null) {
            scene = new Scene(rootNode);
        }
        scene.setRoot(rootNode);
        return scene;
    }

    /**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */
    private Parent loadViewNodeHierarchy(String fxmlFilePath) throws IOException {
        Parent rootNode = springFXMLLoader.load(fxmlFilePath);
        Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        return rootNode;
    }


}
