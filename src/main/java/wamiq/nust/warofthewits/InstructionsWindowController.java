package wamiq.nust.warofthewits;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

public class InstructionsWindowController {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TextField textInput;
    @FXML private Label feedbackLabel;

    @FXML
    protected void instructionsToGame(ActionEvent event) throws Exception{

        //gets file name entered
        String quizTitle = textInput.getText();
        //finds file
        File quizFile = new File("src/main/resources/quizes/"+quizTitle.toUpperCase()+".csv");

        //checks if file is present
        if(!quizFile.exists()){
            feedbackLabel.setText("Quiz Not Found!");
            return;
        }

        //loads quiz from file
        Quiz quiz = QuizLoaderCSV.loadQuizFromCSV(quizFile.getPath(), quizTitle);

        //loads Scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameWindowUI.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        root = fxmlLoader.load();
        //scene = SceneScaler.createScaledScene(root, 1280, 720);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);

        //starting quiz
        GameWindowController controller = fxmlLoader.getController();
        controller.startGame(quiz);

        stage.show();

    }
}
