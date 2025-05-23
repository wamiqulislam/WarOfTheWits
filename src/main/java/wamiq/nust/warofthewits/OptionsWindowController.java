package wamiq.nust.warofthewits;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class OptionsWindowController {
    @FXML private Slider volumeSlider;
    @FXML private CheckBox muteCheckBox;
    @FXML private Label dropArea;

    // Adjust this path to your actual quiz resources folder
    private final Path QUIZ_DEST_FOLDER = Paths.get(
            "D:/WAMIQ-UL-ISLAM/.Wamiq new/Wamiq's VSC Code/WarOfTheWits/src/main/resources/quizes"
    );

    public void initialize() {
        // --- Music init ---
        MusicPlayer player = MusicPlayer.getInstance();
        volumeSlider.setValue(player.getVolume() * 100);
        muteCheckBox.setSelected(player.isMuted());

        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            player.setVolume(newVal.doubleValue() / 100);
        });
        muteCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            player.setMute(newVal);
        });

        // --- Ensure quiz folder exists ---
        try {
            if (!Files.exists(QUIZ_DEST_FOLDER)) {
                Files.createDirectories(QUIZ_DEST_FOLDER);
            }
        } catch (IOException e) {
            showError("Failed to create quiz folder: " + e.getMessage());
        }

        // --- Drag-and-drop setup ---
        setupDragAndDrop();
    }

    @FXML
    protected void optionsToStart(ActionEvent event) throws Exception {
        System.out.println("start button pressed");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartWindowUI.fxml"));
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event,fxmlLoader);
    }

    // --- Quiz file chooser button ---
    @FXML private void onChooseQuizClicked() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Quiz CSV File");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            copyFileToQuizzes(file);
        }
    }

    private void setupDragAndDrop() {
        dropArea.setOnDragOver(event -> {
            if (event.getGestureSource() != dropArea && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        dropArea.setOnDragDropped(this::handleDrop);
    }

    private void handleDrop(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            for (File file : db.getFiles()) {
                if (file.getName().toLowerCase().endsWith(".csv")) {
                    copyFileToQuizzes(file);
                } else {
                    showError("Only .csv files are allowed: " + file.getName());
                }
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private void copyFileToQuizzes(File file) {
        Path destination = QUIZ_DEST_FOLDER.resolve(file.getName());
        try {
            if (Files.exists(destination)) {
                showError("File already exists: " + file.getName());
                return;
            }
            Files.copy(file.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            showInfo("Quiz file added: " + file.getName());
        } catch (IOException e) {
            showError("Failed to copy file: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
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