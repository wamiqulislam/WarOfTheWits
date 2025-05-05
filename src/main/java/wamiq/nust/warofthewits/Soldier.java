package wamiq.nust.warofthewits;

public abstract class Soldier {
    protected int position; // 0 to 5 (points on path)

    public Soldier() {
        this.position = 0; // Start at beginning of path
    }

    public abstract int getTrainCost();
    public abstract int getMoveCost();

    public int getPosition() {return position;}
    public void setPosition(int position) {this.position = position;}


}

class Attacker extends Soldier {
    public static final int trainCost = 20;
    public static final int moveCost = 10;

    public Attacker() {
        super();
    }
    @Override
    public int getMoveCost() {
        return moveCost;
    }
    @Override
    public int getTrainCost() {
        return trainCost;
    }

    public static int getStaticMoveCost() {
        return moveCost;
    }
    public static int getStaticTrainCost() {
        return trainCost;
    }

}

class Defender extends Soldier {
    public static final int trainCost = 10;
    public static final int moveCost = 20;

    public Defender() {
        super();
    }

    @Override
    public int getMoveCost() {
        return moveCost;
    }
    @Override
    public int getTrainCost() {
        return trainCost;
    }

    public static int getStaticMoveCost() {
        return moveCost;
    }
    public static int getStaticTrainCost() {
        return trainCost;
    }
}
