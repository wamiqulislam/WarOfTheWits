package wamiq.nust.warofthewits;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Objects;

public class SceneChanger {

    public void changeScene(ActionEvent event, FXMLLoader fxmlLoader) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/button.css")).toExternalForm());
        stage.setScene(scene);

        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public void changeScene(Stage stage, FXMLLoader fxmlLoader) throws IOException {

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/button.css")).toExternalForm());
        stage.setScene(scene);

        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

}
