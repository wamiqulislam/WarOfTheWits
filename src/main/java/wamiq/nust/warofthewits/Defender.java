package wamiq.nust.warofthewits;

public class Defender extends Soldier {
    public static final int TRAIN_COST = 10;
    public static final int MOVE_COST = 20;

    public Defender() {super();}

    @Override
    public int getMoveCost() {return MOVE_COST;}
    @Override
    public int getTrainCost() {return TRAIN_COST;}
}
