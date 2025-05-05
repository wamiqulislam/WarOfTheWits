package wamiq.nust.warofthewits;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Player {
    private final Quiz quiz;
    private final List<Question> questionList; // working list of questions
    private Question currentQuestion;
    private int skips;

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
    private final Label battleLabel;

    private final ImageView barracks;
    private final Image attackerImage;
    private final Image defenderImage;
    private Image trainedSoldierImage;

    private final Random rand;

    public Player(Quiz quiz, Path[] paths, Label feedbackLabel, Label pq, Label pa, Label pb, Label pc, Label pd, Label goldLabel, Label battleLabel, ImageView barracks) {
        this.quiz = quiz;
        this.feedbackLabel = feedbackLabel;
        this.pa = pa;
        this.pb = pb;
        this.pc = pc;
        this.pd = pd;
        this.pq = pq;
        this.goldLabel = goldLabel;
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
        System.out.println(question.getQuestionText() + "xxxxxxxxxx");
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

        System.out.println("processing answer player1");

        if(ans == 5){
            if(skips<=0){
                feedbackLabel.setText("No More Skips Left!");
                feedbackLabel.setTextFill(Color.RED);
                return;
            }
            feedbackLabel.setText("Skipped!");
            feedbackLabel.setTextFill(Color.LIGHTBLUE);
            skips--;
            loadNextQuestion();
            return;
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
        goldLabel.setText("Gold: "+ gold);
        // Load the next question (if any)
        loadNextQuestion();
    }

    public void trainSoldier(String type) {
        if(barracksFull){
            battleLabel.setText("soldier already trained!");
            return;
        }
        if(type.equals("Attacker")){
            if(gold < Attacker.getStaticTrainCost()){
                battleLabel.setText("not enough gold!");
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
            if(gold < Defender.getStaticTrainCost()){
                battleLabel.setText("not enough gold!");
                return;
            }
            battleLabel.setText("Defender trained!");
            trainedSoldier = new Defender();
            barracksFull = true;
            transaction(-trainedSoldier.getTrainCost());
            trainedSoldierImage = defenderImage;
            barracks.setImage(trainedSoldierImage);
        }

        goldLabel.setText("Gold: "+ gold);

    }

    public void placeSoldier(int pathIndex) {
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
                battleLabel.setText("killed enemy on path "+pathIndex+1);
            }

            path.setSoldier(trainedSoldier, playerId);
            battleLabel.setText("soldier placed on path" + pathIndex+1);

            barracks.setImage(null); // barracks image cleared

            //x set to starting position of path depending on player
            int x = playerId==0?140:1040;
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
            battleLabel.setText("Not enough gold to move soldier");
            return;
        }

        if(enemySoldier != null &&  mySoldierPosition + enemySoldier.getPosition() == 3){
            path.setSoldier(null, enemyPlayerId);
            path.setImage(null, enemyPlayerId);
            battleLabel.setText("killed enemy on path "+pathIndex+1);
            transaction(-mySoldier.getMoveCost());
            return;
        }
        else if (mySoldierPosition >= 4){
            battleLabel.setText("P"+playerId+" has won");
            transaction(-mySoldier.getMoveCost());
            //endGame();
            return;
        }
        else{
            battleLabel.setText("soldier moved on path "+pathIndex+1);
            mySoldier.setPosition(mySoldier.getPosition()+1);
            transaction(-mySoldier.getMoveCost());

            int deltaX = playerId == 0? 225: -225;
            double x = path.getImageView(playerId).getLayoutX() + deltaX;
            path.getImageView(playerId).setLayoutX(x);
        }
        goldLabel.setText("Gold: "+ gold);

    }

    private void transaction(int cost){
        if(cost > 0){
            gold += cost;
        }
        else{
            cost*=-1;
            if(gold <= cost){
                gold = 0;
            }
            else{
                gold -= cost;
            }
        }
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }
}
