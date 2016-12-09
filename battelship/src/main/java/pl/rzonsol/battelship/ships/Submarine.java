package pl.rzonsol.battelship.ships;

/**
 * Created by rzonsol on 08.12.2016.
 */
public class Submarine extends WarShip{

    public Submarine(WarShip.Orientation orientation) {
        super(orientation);
    }

    public Submarine(){
        this(WarShip.Orientation.HORIZONTAL);
    }

    @Override
    public int getDecksCount() {
        return 1;
    }
}
