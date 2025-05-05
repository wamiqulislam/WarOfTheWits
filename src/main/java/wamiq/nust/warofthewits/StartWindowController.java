package wamiq.nust.warofthewits;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class StartWindowController {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label label1;

    @FXML
    protected void startToInstructions(ActionEvent event) throws Exception {
        System.out.println("start button pressed");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InstructionsWindowUI.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        root = fxmlLoader.load();

        //scene = SceneScaler.createScaledScene(root, 1280, 720);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);

        stage.show();

    }

    @FXML
    protected void startToOptions(ActionEvent event) throws Exception {
        System.out.println("options button pressed");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OptionsWindowUI.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        root = fxmlLoader.load();

        //scene = SceneScaler.createScaledScene(root, 1280, 720);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        //stage.setFullScreen(true);

        stage.show();

    }

    @FXML
    protected void Exit(){
        label1.setText("Welcome to JavaFX Application!");
    }

}
