package wamiq.nust.warofthewits;

public abstract class Soldier {
    protected int position;

    public Soldier() {
        this.position = 0;
    }

    public abstract int getTrainCost();
    public abstract int getMoveCost();

    public int getPosition() {return position;}
    public void setPosition(int position) {this.position = position;}


}

