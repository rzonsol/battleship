package pl.rzonsol.battelship.ships;

/**
 * Created by rzonsol on 08.12.2016.
 */
public class BattleShip extends WarShip{

    public BattleShip(Orientation orientation) {
        super(orientation);
    }

    @Override
    public int getDecksCount() {
        return 4;
    }
}
