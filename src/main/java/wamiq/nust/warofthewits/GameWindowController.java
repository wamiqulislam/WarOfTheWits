package wamiq.nust.warofthewits;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;
import java.util.Optional;

public class GameWindowController {

    @FXML private AnchorPane rootPane1;
    @FXML private ImageView backgroundImage;
    @FXML private CheckBox showHelp;


    @FXML private Label feedbackLabel1;
    @FXML private Label p1q;
    @FXML private Label p1a;
    @FXML private Label p1b;
    @FXML private Label p1c;
    @FXML private Label p1d;
    @FXML private Label goldLabel1;
    @FXML private Label skipLabel1;
    @FXML private Label battleLabel1;
    @FXML private ImageView barracks1;
    @FXML private ImageView p1path0;
    @FXML private ImageView p1path1;
    @FXML private ImageView p1path2;
    @FXML private ImageView goldImage1;


    @FXML private Label feedbackLabel2;
    @FXML private Label p2q;
    @FXML private Label p2a;
    @FXML private Label p2b;
    @FXML private Label p2c;
    @FXML private Label p2d;
    @FXML private Label goldLabel2;
    @FXML private Label skipLabel2;
    @FXML private Label battleLabel2;
    @FXML private ImageView barracks2;
    @FXML private ImageView p2path0;
    @FXML private ImageView p2path1;
    @FXML private ImageView p2path2;
    @FXML private ImageView goldImage2;

    //for coin animation
    private Image[] coinFrames;
    private int currentFrame = 0;
    private Timeline animation;

    //background imgs
    private Image background;
    private Image helpBackground;


    Player player1;
    Player player2;


    @FXML
    public void initialize() {

        backgroundImage.fitWidthProperty().bind(rootPane1.widthProperty());
        backgroundImage.fitHeightProperty().bind(rootPane1.heightProperty());
        rootPane1.requestFocus();

        background = new Image(Objects.requireNonNull(getClass().getResource("/images/WOTWBackground.png")).toExternalForm());
        helpBackground = new Image(Objects.requireNonNull(getClass().getResource("/images/WOTWBackgroundHelp.png")).toExternalForm());
        backgroundImage.setImage(background);

        showHelp.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                backgroundImage.setImage(helpBackground);
            } else {
                backgroundImage.setImage(background);
            }
        });

        loadCoinImages();
        setupAnimation();
        animation.play();
    }


    public void startGame(Quiz quiz) {

        Stage stage = (Stage) rootPane1.getScene().getWindow();

        //images
        final ImageView[][] pathImageViews = {{p1path0, p2path0}, {p1path1, p2path1}, {p1path2, p2path2}};

        //making paths for the battleground
        Path[] paths = new Path[3];
        for (int i = 0; i < 3; i++)
            paths[i] = new Path(pathImageViews[i]);



        //Making players
        player1 = new Player(stage, quiz, paths, feedbackLabel1,p1q,p1a,p1b,p1c,p1d,goldLabel1,skipLabel1,battleLabel1, barracks1);
        player2 = new Player(stage, quiz, paths, feedbackLabel2,p2q,p2a,p2b,p2c,p2d,goldLabel2,skipLabel2,battleLabel2, barracks2);

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

    @FXML
    protected void gameToStart(ActionEvent event) throws Exception {
        System.out.println("main menu button pressed");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit to Main Menu");
        alert.setHeaderText("Quit to Main Menu?");
        alert.setContentText("The Battle will end and all of your progress will be lost.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get()==ButtonType.OK) {
            System.out.println("Confirmed!");

            Player.resetPlayerIds();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartWindowUI.fxml"));
            SceneChanger sceneChanger = new SceneChanger();
            sceneChanger.changeScene(event,fxmlLoader);

            MusicPlayer.getInstance().changeTrack(0);

        } else {
            System.out.println("Cancelled!");
        }
    }

    @FXML
    public void playHoverSound() {
        SoundPlayer.play("hover.wav");
    }
    @FXML
    public void playPressSound() {
        SoundPlayer.play("press.mp3");
    }

    private void loadCoinImages() {
        coinFrames = new Image[9];
        for (int i = 0; i < 9; i++) {
            String imageName = String.format("/images/goldCoin%d.png", i + 1);
            coinFrames[i] = new Image(Objects.requireNonNull(getClass().getResource(imageName)).toExternalForm());
        }
    }


    private void setupAnimation() {
        animation = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            goldImage1.setImage(coinFrames[currentFrame]);
            goldImage2.setImage(coinFrames[currentFrame]);
            currentFrame = (currentFrame + 1) % coinFrames.length;
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
    }

}
