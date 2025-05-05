package wamiq.nust.warofthewits;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartWindowUI.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = SceneScaler.createScaledScene(root, 1280, 720);
        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        stage.show();

        // for fullscreen when maximised
        stage.maximizedProperty().addListener((_, _, isNowMaximized) -> {
            if (isNowMaximized) {
                stage.setFullScreen(true);
            }
        });



    }
}