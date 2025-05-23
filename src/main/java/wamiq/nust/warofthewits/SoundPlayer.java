package wamiq.nust.warofthewits;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class SoundPlayer {

    public static void play(String fileName) {
        try {
            if(fileName.equals("press.mp3")) return;
            Media sound = new Media(Objects.requireNonNull(SoundPlayer.class.getResource("/sounds/" + fileName)).toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("--abc---Could not play sound: " + fileName);
            e.printStackTrace();
        }
    }
}
