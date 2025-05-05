package wamiq.nust.warofthewits;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Path {
    private Soldier[] soldiers = {null, null};
    private ImageView[] images = new ImageView[2];

    Path(ImageView[] images){
        this.images[0] = images[0];
        this.images[1] = images[1];
    }

    public Soldier getSoldier(int playerId) {
        return soldiers[playerId];
    }

    public void setSoldier(Soldier trainedSoldier, int playerId) {
        this.soldiers[playerId] = trainedSoldier;
    }

    public void setImage(Image image, int playerId) {
        this.images[playerId].setImage(image);
    }

    public ImageView getImageView(int playerId) {
        return images[playerId];
    }
}

