package wamiq.nust.warofthewits;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;


public class StartWindowController {

    @FXML
    protected void startToInstructions(ActionEvent event) throws Exception {
        System.out.println("start button pressed");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InstructionsWindowUI.fxml"));
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event,fxmlLoader);


    }

    @FXML
    protected void startToOptions(ActionEvent event) throws Exception {
        System.out.println("options button pressed");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OptionsWindowUI.fxml"));
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event,fxmlLoader);
    }

    @FXML
    protected void Exit(){
        System.exit(0);
    }

    @FXML
    public void playHoverSound() {
        SoundPlayer.play("hover.wav");
    }
    @FXML
    public void playPressSound() {
        SoundPlayer.play("press.mp3");
    }
}
