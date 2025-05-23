package wamiq.nust.warofthewits;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.File;
import java.util.List;

public class InstructionsWindowController {

    @FXML private ComboBox<String> quizComboBox;
    @FXML private Label feedbackLabel;
    @FXML private Label instructionLabel;
    @FXML private Label instructionIndexLabel;
    @FXML private Button prevButton;
    @FXML private Button nextButton;


    private List<String> instructionPages;
    private List<String> instructionHeadings;

    private int currentPageIndex = 0;

    @FXML
    public void initialize(){
        File quizesFolder = new File("src/main/resources/quizes");
        if (quizesFolder.exists() && quizesFolder.isDirectory()) {
            File[] quizFile = quizesFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
            if (quizFile != null) {
                for (File file : quizFile) {
                    String name = file.getName();
                    if (name.toLowerCase().endsWith(".csv")) {
                        name = name.substring(0, name.length() - 4);
                    }
                    quizComboBox.getItems().add(name);
                }
            }
        } else {
            System.err.println("Quizes folder not found: " + quizesFolder.getAbsolutePath());
        }

        if(quizComboBox.getItems().isEmpty()){
            feedbackLabel.setText("No Quizes Found! Please go to Options to add Quizes.");
        }


        instructionHeadings = List.of(
                "Introduction",
                "Loading Quizzes",
                "Selecting a Quiz",
                "Gameplay Overview",
                "Earning & Spending Gold",
                "Battlefield Mechanics",
                "Winning the Game",
                "Controls: Player 1",
                "Controls: Player 2"
        );

        instructionPages = List.of(
                // Introduction
                """
                        \t\t\tIntroduction
                        
                        Welcome to War of the Wits! In this two-player real-time game, you’ll answer questions to earn gold and then deploy soldiers on three lanes to push toward the enemy fort. \
                        There are no turns—both quiz and battle happen simultaneously. Read on to learn how to load quizzes, play, and control your forces.""",

                // Loading Quizzes
                """
                        \t\t\tLoading Quizzes
                        
                        To load a new quiz, open the Options menu and either drag-and-drop a CSV file into the designated area or click Browse to select it. \
                        Your file must be comma-separated with each line in the format:
                        question, choice1, choice2, choice3, choice4, correct choice number""",

                // Selecting a Quiz
                """
                        \t\t\tSelecting a Quiz
                        
                        After loading quizzes, use the “Choose your Quiz” dropdown below to pick one. Once selected, click Start to launch the game.""",

                // Gameplay Overview
                """
                        \t\t\tGameplay Overview
                        
                        The game runs two parts simultaneously: Quiz and Battle. There are no turns—both players play at once. \
                        Answer correctly to earn 30 gold, answer incorrectly to lose 10 gold, and you have 5 skips to bypass questions without penalty.""",

                // Earning & Spending Gold
                """
                        \t\t\tEarning & Spending Gold
                        
                        Gold you earn in the quiz can be spent on soldiers. Attackers cost more to train but move cheaply; defenders train for less gold but cost more to move. \
                        Decide whether you need a fast advance or a strong defense each round.""",

                // Battlefield Mechanics
                """
                        \t\t\tBattlefield Mechanics
                        
                        There are three lanes where you can deploy soldiers. Only one soldier per lane per side. \
                        If you move into a lane occupied by an enemy, you’ll eliminate that soldier instead of advancing. \
                        Control lanes to push toward the enemy fort.""",

                // Winning the Game
                """
                        \t\t\tWinning the Game
                        
                        The first player to get any single soldier to the opponent’s fort wins instantly. \
                        Balance your quiz performance with smart battlefield tactics to secure victory!""",

                // Controls
                """
                        \t\t\tControls
                        
                        Player 1 Keys:
                          • 1–4: Select answer choice 1, 2, 3, or 4
                          • 5: Skip question (up to 5 skips total)
                          • 6: Train an Attacker (higher train cost, lower move cost)
                          • 7: Train a Defender (lower train cost, higher move cost)
                          • 8, 9, 0: Place or move soldier on lane 1, 2, or 3""",

                """
                        Player 2 Keys:
                          • Z, X, C, V: Select answer choice 1, 2, 3, or 4
                          • B: Skip question (up to 5 skips total)
                          • N: Train an Attacker (higher train cost, lower move cost)
                          • M: Train a Defender (lower train cost, higher move cost)
                          • <, >, ?: Place or move soldier on lane 1, 2, or 3
                        
                        Tip: Toggle “Show Controls” in the game menu to overlay this legend at any time."""
        );




        instructionLabel.setText(instructionPages.get(currentPageIndex));
        instructionIndexLabel.setText(buildInstructionIndex(currentPageIndex));

        prevButton.setOnAction(e -> {
            if (currentPageIndex > 0) {
                currentPageIndex--;
                instructionLabel.setText(instructionPages.get(currentPageIndex));
                instructionIndexLabel.setText(buildInstructionIndex(currentPageIndex));

            }
        });

        nextButton.setOnAction(e -> {
            if (currentPageIndex < instructionPages.size() - 1) {
                currentPageIndex++;
                instructionLabel.setText(instructionPages.get(currentPageIndex));
                instructionIndexLabel.setText(buildInstructionIndex(currentPageIndex));

            }
        });

    }

    private String buildInstructionIndex(int currentIndex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < instructionHeadings.size(); i++) {
            if (i == currentIndex) {
                sb.append("→ ").append(instructionHeadings.get(i)).append("\n");
            } else {
                sb.append(instructionHeadings.get(i)).append("\n");
            }
        }
        return sb.toString().trim();
    }


    @FXML
    protected void instructionsToGame(ActionEvent event) throws Exception{

        //gets file name entered
        String quizTitle = quizComboBox.getValue();
        //finds file
        File quizFile = new File("src/main/resources/quizes/"+quizTitle+".csv");

        //checks if file is present
        if(!quizFile.exists()){
            feedbackLabel.setText("Quiz Not Found: "+quizTitle);
            return;
        }

        //loads quiz from file
        Quiz quiz = QuizLoaderCSV.loadQuizFromCSV(quizFile.getPath(), quizTitle);
        System.out.println("............."+quiz.getTitle());

        //loads Scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameWindowUI.fxml"));
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event,fxmlLoader);

        //starting quiz
        GameWindowController controller = fxmlLoader.getController();
        controller.startGame(quiz);

        MusicPlayer.getInstance().changeTrack(1);

    }

    @FXML
    protected void instructionsToStart(ActionEvent event) throws Exception {
        System.out.println("start button pressed");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartWindowUI.fxml"));
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event,fxmlLoader);

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
