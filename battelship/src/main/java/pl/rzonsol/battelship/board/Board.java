package pl.rzonsol.battelship.board;

import pl.rzonsol.battelship.exeptions.IllegalMoveException;
import pl.rzonsol.battelship.ships.*;

import java.util.Random;

/**
 * Created by rzonsol on 08.12.2016.
 */
public class Board {

    public static final int BOARD_SIZE=10;
    public static final int SHIPS_TYPE_COUNT = 4;


    private Field[][] fields =new Field[BOARD_SIZE][BOARD_SIZE];
    private int shipsCount;
    private int[] numberOfShipsByDeck = new int[SHIPS_TYPE_COUNT];

    /**
     * Board() - creating game boards and puts on it Empty state
     * x - represents integers from 0 to BOARD_SIZE
     * y - represents characters from A to BOARD_SIZE - letters in alphabet
     */
    public Board(){
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                fields[x][y]= new Field(x,y,State.EMPTY);
            }
        }
    }

    /**
     *  userShips() - change status of SHIP to USER_SHIP - it is using to display ships on user board.
     */
    public void userShips(){
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if(getField(x,y).getState()==State.SHIP){
                    fields[x][y].setState(State.USER_SHIP);
                }
            }

        }
    }

    public void userShipsToNormal(){
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if(getField(x,y).getState()==State.USER_SHIP){
                    fields[x][y].setState(State.SHIP);
                }
            }

        }

    }

    /**
     * getShip() - return ship depends on number of decks and orientation (horizontal/vertical)
    */
    public Ship getShip(int decks, WarShip.Orientation orientation) {
        switch (decks){
            case 1:
                return new Submarine();
            case 2:
                return new Destroyer(orientation);
            case 3:
                return new Cruiser(orientation);
            case 4:
                return new BattleShip(orientation);
            default:
                throw new IllegalArgumentException(String.format("Unknown ship with %d ddcks",decks));
        }
    }

    /**
     * fileBoard() - generate random ships on the board
     * 1 - BattleShip
     * 2 - Cruiser
     * 3 - Destroyer
     * 4 - Submarine
     */
    public  void fillBoard(){
        Random random =new Random();

        for (int decks = 1; decks <= SHIPS_TYPE_COUNT; decks++) {
            for (int i = 0; i < getTotalCountOfShips(decks); i++) {
                boolean tryAgain;
                do{
                    int x = random.nextInt(BOARD_SIZE);
                    int y = random.nextInt(BOARD_SIZE);

                    WarShip.Orientation orientation = random.nextBoolean() ? WarShip.Orientation.HORIZONTAL
                            : WarShip.Orientation.VERTICAL;
                    Ship ship = getShip(decks, orientation);

                    try {
                        addShip(x,y,ship);
                        tryAgain=false;
                    } catch (IllegalMoveException illegalMoveExeption) {
                        tryAgain=true;
                    }

                }while(tryAgain);
            }
        }
    }

    /**
     * printUserBoard() - printing board with ships on it. If it is computer board the ships are hidden.
     */
    public void printBord(){
        System.out.print("  |");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((char)('A'+i)+"|");
        }

        System.out.print('\n');
        for (int i = 0; i < 3*BOARD_SIZE; i++) {
            System.out.print('-');
        }

        System.out.print('\n');
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(' ');
            System.out.print(i+"|");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(fields[i][j].stateToChar()+"|");
            }
            System.out.print('\n');
        }
    }

    /**
     * addShip() - put ship on the board, method take 3 parameters (x,y)- coordinate of the first deck and ship with orientation
     */
    public void addShip(int x,int y, Ship ship)  throws IllegalMoveException {

        int count = ship.getDecksCount();
        Field[] field = new Field[count];
        int xToSet=x,yToSet=y;

        if(numberOfShipsByDeck[count -1]==getTotalCountOfShips(count)){
            throw new IllegalMoveException("You have all submarine set!");
        }

        for (int i = 0; i < count; i++) {
            // read orientation of ships and chose suitable field coordinate
            if(ship.getOrientation() == WarShip.Orientation.HORIZONTAL){
                xToSet = x+i;
            }else{
                yToSet = y+i;
            }

            if (isOutside(xToSet, yToSet)) {
                throw new IllegalMoveException("Ship set outside board!");
            }

            field[i] = fields[xToSet][yToSet];

            if(isFillOccupied(field[i])) {
                throw new IllegalMoveException("Field is occupited!");
            }
        }

        for (int i = 0; i <count; i++) {
            ship.setOnField(field[i],i);
        }

        shipsCount++;
        ship.setOnField(fields[x][y],0);
        numberOfShipsByDeck[count -1]++;
        return ;
    }

    /**
    * isFillOccupied(Field f) get one parameter of Field type and checks if it is empty
    */
    private boolean isFillOccupied(Field field) {

        for (int y = field.getY()-1; y <= field.getY()+1; y++) {
            for (int x = field.getX()-1; x <= field.getX()+1; x++) {

                if(isOutside(x,y)){
                    continue;
                }

                if(fields[x][y].getState() != State.EMPTY){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * isOutside() checks if the field with cordinate (x,y) is outside the board, if it is outside its return true
     */
    public boolean isOutside(int x, int y) {
        return x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE;
    }

    /**
     * shoot(int x,int y) - method is responds for shooting on the board, its changing state of the field on the board
     */

    public void shoot(int x, int y) throws IllegalMoveException {

        if(isOutside(x,y)){
            throw new IllegalMoveException("Don't shoot outside board!");
        }

        Field field = getField(x, y);

        if(field.getState()==State.MISS || field.getState() == State.HIT || field.getState() == State.SUNK){
            throw new IllegalMoveException("You have already shot here!");
        }

        if(field.getState()==State.EMPTY){
            field.setState(State.MISS);
        }else if(field.getState()== State.SHIP || field.getState() == State.USER_SHIP){
            field.setState(State.HIT);
            field.getShip().hit();
            if(field.getShip().isSunk()){
                shipsCount--;
            }
        }
    }

    /**
     * getTotalCountOfShips returns number of ships on board
     */
    public int getTotalCountOfShips(int decksCount) {
        return SHIPS_TYPE_COUNT - decksCount+1;
    }

    public int getShipsCount() {
        return shipsCount;
    }

    public Field getField(int x, int y) {
        return fields[x][y];
    }

    public Field[][] getFields() {
        return fields;
    }

    public void setFields(Field field) {
        this.fields[field.getX()][field.getX()] = field;
    }
}

