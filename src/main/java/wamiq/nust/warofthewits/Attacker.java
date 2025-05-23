package wamiq.nust.warofthewits;

public class Attacker extends Soldier {
    public static final int TRAIN_COST = 20;
    public static final int MOVE_COST = 10;

    public Attacker() {super();}

    @Override
    public int getMoveCost() {return MOVE_COST;}
    @Override
    public int getTrainCost() {return TRAIN_COST;}
}
