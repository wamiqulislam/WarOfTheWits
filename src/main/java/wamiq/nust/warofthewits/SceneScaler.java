package wamiq.nust.warofthewits;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;

public class SceneScaler {

    public static Scene createScaledScene(Parent fxmlRoot, double baseWidth, double baseHeight) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double scaleX = screenBounds.getWidth() / baseWidth;
        double scaleY = screenBounds.getHeight() / baseHeight;
        double scale = Math.min(scaleX, scaleY);

        Group scaledGroup = new Group(fxmlRoot);
        scaledGroup.setScaleX(scale);
        scaledGroup.setScaleY(scale);

        StackPane container = new StackPane(scaledGroup);
        container.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());

        return new Scene(container);
    }
}

