package wamiq.nust.warofthewits;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartWindowUI.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/button.css")).toExternalForm());
        stage.setScene(scene);

        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/WOTWLogo.png"))));
        stage.setTitle("War Of The Wits");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

        MusicPlayer.getInstance();
    }
}