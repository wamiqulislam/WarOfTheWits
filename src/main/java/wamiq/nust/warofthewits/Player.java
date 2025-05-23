package wamiq.nust.warofthewits;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Player {
    private final Quiz quiz;
    private final List<Question> questionList; // working list of questions
    private final Stage stage;
    private Question currentQuestion;
    private int skips;
    private int lastPath;

    private static int nextPlayerId = 0;
    private final int playerId;
    private final int enemyPlayerId;
    private int gold;
    private final Path[] paths;
    private boolean barracksFull;
    private Soldier trainedSoldier;

    private final Label feedbackLabel;
    private final Label pq;
    private final Label pa;
    private final Label pb;
    private final Label pc;
    private final Label pd;
    private final Label goldLabel;
    private final Label skipLabel;
    private final Label battleLabel;

    private final ImageView barracks;
    private final Image attackerImage;
    private final Image defenderImage;
    private Image trainedSoldierImage;

    private final Random rand;

    public Player(Stage stage, Quiz quiz, Path[] paths, Label feedbackLabel, Label pq, Label pa, Label pb, Label pc, Label pd, Label goldLabel, Label skipLabel, Label battleLabel, ImageView barracks) {
        this.stage = stage;
        this.quiz = quiz;
        this.feedbackLabel = feedbackLabel;
        this.pa = pa;
        this.pb = pb;
        this.pc = pc;
        this.pd = pd;
        this.pq = pq;
        this.goldLabel = goldLabel;
        this.skipLabel = skipLabel;
        this.battleLabel = battleLabel;
        this.barracks = barracks; // {barracks, path0, path1, path2}

        this.questionList = new ArrayList<>(quiz.getQuestionList());
        this.gold = 0;
        this.skips = 5;
        this.barracksFull = false;
        this.playerId = nextPlayerId++;
        this.enemyPlayerId = playerId == 0?1:0;
        this.paths = paths;
        this.rand = new Random();

        attackerImage = new Image(Objects.requireNonNull(getClass().getResource("/images/ATTACKER" + (playerId + 1) + ".png")).toExternalForm());
        defenderImage = new Image(Objects.requireNonNull(getClass().getResource("/images/DEFENDER" + (playerId + 1) + ".png")).toExternalForm());
    }

    public void loadNextQuestion() {
        if (questionList.isEmpty()) {
            // No more questions left; quiz complete.
            pq.setText("Quiz Complete!");
            pa.setText("");
            pb.setText("");
            pc.setText("");
            pd.setText("");
            feedbackLabel.setText("");
            return;
        }
        // Get a random question from the remaining list.
        int index = rand.nextInt(questionList.size());
        currentQuestion = questionList.get(index);
        displayQuestion(currentQuestion);
    }

    private void displayQuestion(Question question) {
        System.out.println(question.getQuestionText());
        pq.setText("Q: " + question.getQuestionText());
        List<String> choices = question.getAnswers();
        // Assuming there are always 4 choices.
        pa.setText("1. " + choices.get(0));
        pb.setText("2. " + choices.get(1));
        pc.setText("3. " + choices.get(2));
        pd.setText("4. " + choices.get(3));
        //feedbackLabel1.setText("");  // Clear previous feedback.
    }

    public void processAnswer(int ans) {
        if (questionList.isEmpty()) {
            System.out.println("quiz complete");
            return;
        }


        if(ans == 5){
            if(skips<=0){
                feedbackLabel.setText("No More Skips Left!");
                feedbackLabel.setTextFill(Color.RED);
                return;
            }
            feedbackLabel.setText("Skipped!");
            feedbackLabel.setTextFill(Color.LIGHTBLUE);
            skips--;
            skipLabel.setText("Skips: "+ skips);
        }
        else if (currentQuestion.isCorrect(ans)) {
            // If question is answered, remove it from the list.
            questionList.remove(currentQuestion);
            feedbackLabel.setText("Correct!");
            feedbackLabel.setTextFill(Color.GREEN);
            transaction(30);
        } else {
            questionList.remove(currentQuestion);
            feedbackLabel.setText("Incorrect!");
            feedbackLabel.setTextFill(Color.RED);
            transaction(-10);
        }
        //updates gold label
        goldLabel.setText(""+gold);
        // Load the next question (if any)
        loadNextQuestion();
    }

    public void trainSoldier(String type) {
        if(barracksFull){
            battleLabel.setText("Soldier already trained!");
            return;
        }
        if(type.equals("Attacker")){
            if(gold < Attacker.TRAIN_COST){
                battleLabel.setText("Not enough Gold to train!");
                return;
            }
            battleLabel.setText("Attacker trained!");
            trainedSoldier = new Attacker();
            barracksFull = true;
            transaction(-trainedSoldier.getTrainCost());
            trainedSoldierImage = attackerImage;
            barracks.setImage(trainedSoldierImage);

        }
        else if(type.equals("Defender")){
            if(gold < Defender.TRAIN_COST){
                battleLabel.setText("Not enough Gold to train!");
                return;
            }
            battleLabel.setText("Defender trained!");
            trainedSoldier = new Defender();
            barracksFull = true;
            transaction(-trainedSoldier.getTrainCost());
            trainedSoldierImage = defenderImage;
            barracks.setImage(trainedSoldierImage);
        }

        goldLabel.setText(""+ gold);

    }

    public void placeSoldier(int pathIndex) {
        this.lastPath = pathIndex + 1;
        Path path = paths[pathIndex];

        if(path.getSoldier(playerId) != null) {
            moveSoldier(pathIndex);
        }
        else if(trainedSoldier == null){
            battleLabel.setText("No soldier on this path");
        }
        else{
            if(path.getSoldier(enemyPlayerId) != null &&  path.getSoldier(enemyPlayerId).getPosition() >= 4){
                path.setSoldier(null, enemyPlayerId);
                path.setImage(null, enemyPlayerId);
                battleLabel.setText("Killed enemy on path "+(pathIndex+1));
            }

            path.setSoldier(trainedSoldier, playerId);
            battleLabel.setText("Soldier placed on path" + (pathIndex+1));

            barracks.setImage(null); // barracks image cleared

            //x set to starting position of path depending on player
            int x = playerId==0?165:1059;
            paths[pathIndex].getImageView(playerId).setLayoutX(x);

            paths[pathIndex].setImage(trainedSoldierImage, playerId);

            trainedSoldier = null;
            barracksFull = false;
        }
    }

    private void moveSoldier(int pathIndex){
        Path path = paths[pathIndex];
        Soldier mySoldier = path.getSoldier(playerId);
        Soldier enemySoldier = path.getSoldier(enemyPlayerId);
        int mySoldierPosition = mySoldier.getPosition();

        if(gold < mySoldier.getMoveCost()){
            battleLabel.setText("Not enough gold to move");
            return;
        }

        if(enemySoldier != null &&  mySoldierPosition + enemySoldier.getPosition() == 3){
            path.setSoldier(null, enemyPlayerId);
            path.setImage(null, enemyPlayerId);
            battleLabel.setText("Killed enemy on path "+(pathIndex+1));
            transaction(-mySoldier.getMoveCost());
            return;
        }
        else if (mySoldierPosition >= 4){
            battleLabel.setText("Player "+(playerId+1)+" has won");
            transaction(-mySoldier.getMoveCost());
            endGame();
            return;
        }
        else{
            battleLabel.setText("Soldier moved on path "+ (pathIndex+1));
            mySoldier.setPosition(mySoldier.getPosition()+1);
            transaction(-mySoldier.getMoveCost());

            int deltaX = playerId == 0? 225: -225;
            double x = path.getImageView(playerId).getLayoutX() + deltaX;
            path.getImageView(playerId).setLayoutX(x);
        }
        goldLabel.setText(""+ gold);

    }

    private void transaction(int cost){
            gold += cost;
    }


    private void endGame(){

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/button.css")).toExternalForm());


        alert.setTitle("Game Ended");
        alert.setHeaderText("Player "+(playerId+1)+" won");
        alert.setContentText("Congratulations Player "+(playerId+1)+", you have won this Battle on Path "+lastPath+".");

        alert.showAndWait();

        resetPlayerIds();

        try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartWindowUI.fxml"));
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(stage ,fxmlLoader);

        MusicPlayer.getInstance().changeTrack(0);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void resetPlayerIds(){
        nextPlayerId =0;
    }
}
