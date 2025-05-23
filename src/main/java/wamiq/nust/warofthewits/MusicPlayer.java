package wamiq.nust.warofthewits;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

public class MusicPlayer {
    private static MusicPlayer instance;
    private MediaPlayer mediaPlayer;
    private final List<String> trackPaths;
    private int currentTrackIndex = 0;
    private double volume = 0.5;
    private boolean isMuted = false;

    private MusicPlayer() {
        // Add your music file paths here
        trackPaths = List.of(
                "src/main/resources/music/MiiTheme.mp3",
                "src/main/resources/music/TerrariaDay.mp3",
                "src/main/resources/music/PokémonBattle.mp3"
        );
        playTrack(currentTrackIndex);
    }

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    private void playTrack(int index) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Media media = new Media(new File(trackPaths.get(index)).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(volume);
        mediaPlayer.setMute(isMuted);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO)); // loop
        mediaPlayer.play();
    }

    public void setVolume(double volume) {
        this.volume = volume;
        if (mediaPlayer != null && !isMuted) {
            mediaPlayer.setVolume(volume);
        }
    }

    public void setMute(boolean mute) {
        this.isMuted = mute;
        if (mediaPlayer != null) {
            mediaPlayer.setMute(mute);
        }
    }

    public boolean isMuted() {
        return isMuted;
    }

    public double getVolume() {
        return volume;
    }

    public void changeTrack(int index) {
        if (index >= 0 && index < trackPaths.size()) {
            currentTrackIndex = index;
            playTrack(index);
        }
    }
}

