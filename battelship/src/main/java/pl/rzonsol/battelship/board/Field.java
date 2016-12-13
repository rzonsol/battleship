package pl.rzonsol.battelship.board;

import pl.rzonsol.battelship.ships.Ship;
import pl.rzonsol.battelship.ships.State;


/**
 * Created by rzonsol on 08.12.2016.
 */
public class Field {
    /**
     *  x - is the number from 0 to 9
     *  y - is the number from 0 to 9 (respectively from A to J)
     */

    private final int x;
    private final int y;
    private State state;
    private Ship ship;

    public Field(int x, int y, State state){
        this.x=x;
        this.y=y;
        this.state = state;
    }

    public void setShip(Ship ship){
        this.ship=ship;
    }

    public Ship getShip() {
        return ship;
    }

    public void setState(State state){
        this.state=state;
    }

    public State getState() {
        return state;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    /**
     * stateToChars() - change state of field to char
     */

    public char stateToChar(){
        switch (state){
            case HIT:
                return 'O';
            case EMPTY:
                return ' ';
            case SHIP:
                return '?';
            case MISS:
                return '!';
            case SUNK:
                return 'X';
            case USER_SHIP:
                return '0';
            default:
                return '?';
        }
    }


}

