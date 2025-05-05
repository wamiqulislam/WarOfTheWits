package wamiq.nust.warofthewits;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameWindowController {
    @FXML private Stage stage;
    @FXML private Scene scene;
    @FXML private Parent root;

    @FXML private ImageView backgroundImage;
    @FXML private AnchorPane rootPane1;

    @FXML private Label feedbackLabel1;
    @FXML private Label p1q;
    @FXML private Label p1a;
    @FXML private Label p1b;
    @FXML private Label p1c;
    @FXML private Label p1d;
    @FXML private Label goldLabel1;
    @FXML private Label battleLabel1;
    @FXML private ImageView barracks1;
    @FXML private ImageView p1path0;
    @FXML private ImageView p1path1;
    @FXML private ImageView p1path2;


    @FXML private Label feedbackLabel2;
    @FXML private Label p2q;
    @FXML private Label p2a;
    @FXML private Label p2b;
    @FXML private Label p2c;
    @FXML private Label p2d;
    @FXML private Label goldLabel2;
    @FXML private Label battleLabel2;
    @FXML private ImageView barracks2;
    @FXML private ImageView p2path0;
    @FXML private ImageView p2path1;
    @FXML private ImageView p2path2;

    Player player1;
    Player player2;


    @FXML
    public void initialize() {
        backgroundImage.fitWidthProperty().bind(rootPane1.widthProperty());
        backgroundImage.fitHeightProperty().bind(rootPane1.heightProperty());
        rootPane1.requestFocus();
    }


    public void startGame(Quiz quiz) {

        //images
        final ImageView[][] pathImageViews = {{p1path0, p2path0}, {p1path1, p2path1}, {p1path2, p2path2}};

        //making paths for the battleground
        Path[] paths = new Path[3];
        for (int i = 0; i < 3; i++)
            paths[i] = new Path(pathImageViews[i]);



        //Making players
        player1 = new Player(quiz, paths, feedbackLabel1,p1q,p1a,p1b,p1c,p1d,goldLabel1,battleLabel1, barracks1);
        player2 = new Player(quiz, paths, feedbackLabel2,p2q,p2a,p2b,p2c,p2d,goldLabel2,battleLabel2, barracks2);

        //Starting quiz for the players
        player1.loadNextQuestion();
        player2.loadNextQuestion();
    }



    @FXML
    public void handleKeyPressed(KeyEvent event) {

        System.out.println("Key pressed: " + event.getCode());

        //player 1
        switch (event.getCode()) {
            case DIGIT1 -> player1.processAnswer(1);
            case DIGIT2 -> player1.processAnswer(2);
            case DIGIT3 -> player1.processAnswer(3);
            case DIGIT4 -> player1.processAnswer(4);
            case DIGIT5 -> player1.processAnswer(5);
            case DIGIT6 -> player1.trainSoldier("Attacker");
            case DIGIT7 -> player1.trainSoldier("Defender");
            case DIGIT8 -> player1.placeSoldier(0);
            case DIGIT9 -> player1.placeSoldier(1);
            case DIGIT0 -> player1.placeSoldier(2);
        }

        //player 2
        switch (event.getCode()) {
            case NUMPAD1, Z -> player2.processAnswer(1);
            case NUMPAD2, X -> player2.processAnswer(2);
            case NUMPAD3, C -> player2.processAnswer(3);
            case NUMPAD4, V -> player2.processAnswer(4);
            case NUMPAD5, B -> player2.processAnswer(5);
            case NUMPAD6, N -> player2.trainSoldier("Attacker");
            case NUMPAD7, M -> player2.trainSoldier("Defender");
            case NUMPAD8, COMMA -> player2.placeSoldier(0);
            case NUMPAD9, PERIOD -> player2.placeSoldier(1);
            case NUMPAD0, SLASH -> player2.placeSoldier(2);
        }

    }
}
