package pl.rzonsol.battelship.ships;

import pl.rzonsol.battelship.board.Field;

/**
 * Created by rzonsol on 08.12.2016.
 *
 * fields:
 * orientation - set orientation on the bord HORIZONTAL/VERTICAL
 */
public abstract class WarShip implements Ship{

    private Orientation orientation;
    private int hits;
    private Field[] occupied;

    public enum Orientation {
        HORIZONTAL, VERTICAL
    }

    public WarShip(Orientation orientation){
        this.orientation=orientation;
        occupied = new Field[this.getDecksCount()];
    }


    /**
     * isSunk return true if all decks was hit and false otherwise
     */
    @Override
    public Boolean isSunk() {
        return hits==getDecksCount();
    }

    /**
     * setOnField(Field f , int i) - gets two parameters Field is class from  package pl.rzonsol.battelship.board.Field.
     * methods put deck one teh field from boards
     */
    public void setOnField(Field field, int deckNo){

        field.setShip(this);
        field.setState(State.SHIP);
        occupied[deckNo]=field;
    }

    /**
     * hit() - function counts number of hits on the ship
     * if hits is equal to the number of decks then
     * change the Status of field to SUNK
     */
    @Override
    public void hit() {
        hits++;
        if(isSunk()){
            for (int i = 0; i <occupied.length; i++) {
                occupied[i].setState(State.SUNK);
            }
        }
    }

    /**
     * gets ship orientation from board
     */
    public Orientation getOrientation() {
        return orientation;
    }
}

