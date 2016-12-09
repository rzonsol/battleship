package pl.rzonsol.battelship.ships;

import pl.rzonsol.battelship.board.Field;

/**
 * Created by rzonsol on 08.12.2016.
 */
public interface Ship {

    int getDecksCount();

    void hit();

    Boolean isSunk();

    void setOnField(Field field, int deckNo);

    WarShip.Orientation getOrientation();
}
