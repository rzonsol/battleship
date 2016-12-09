package pl.rzonsol.battelship.board;


import pl.rzonsol.battelship.exeptions.IllegalMoveExeption;
import pl.rzonsol.battelship.ships.*;
import pl.rzonsol.battelship.ships.Submarine;
import pl.rzonsol.battelship.ships.WarShip;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by rzonsol on 09.12.2016.
 */
public class BoardTest {

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board();
    }

    @Test
    public void shouldAddSubmarine() throws Exception {
        // arrange

        //act
        board.addShip(0,0, new Submarine(WarShip.Orientation.HORIZONTAL));
        //assert
        assertEquals(1,board.getShipsCount());
    }

    @Test(expected = IllegalMoveExeption.class)
    public void ShouldNoBeAbleAddFiveSubmarine() throws Exception {
        //arrange
        board.addShip(0,0, new Submarine());
        board.addShip(1,2, new Submarine());
        board.addShip(3,3, new Submarine());
        board.addShip(5,5, new Submarine());
        //act
        board.addShip(9,9, new Submarine());
    }

    @Test
    public void shouldAddSubmarineOnField() throws Exception {
        // arrange
        //act
        board.addShip(0,0, new Submarine());
        //assert
        Field field = board.getField(0,0);
        assertEquals(State.SHIP, field.getState());
    }

    @Test(expected = IllegalMoveExeption.class)
    public void shouldNotBeAbleToGetOutside() throws Exception {
        //act
        board.addShip(9,1,new Destroyer(WarShip.Orientation.HORIZONTAL));
    }

    @Test(expected = IllegalMoveExeption.class)
    public void shouldNotBeAbleToBeClose() throws Exception {
        //arrange
        board.addShip(0,0,new Destroyer(WarShip.Orientation.HORIZONTAL));
        //act
        board.addShip(2,0,new Destroyer(WarShip.Orientation.HORIZONTAL));
    }

    @Test
    public void shouldAddDestroyerOnFields() throws Exception {
        // arrange
        //act
        board.addShip(0,0, new Destroyer(WarShip.Orientation.HORIZONTAL));
        //assert
        Field field = board.getField(1,0);
        assertEquals(State.SHIP, field.getState());
    }

    @Test(expected = IllegalMoveExeption.class)
    public void shouldNotAbleAdeTwoBattleship() throws Exception {
        //arrange
        board.addShip(0,0, new BattleShip(WarShip.Orientation.HORIZONTAL));
        //act
        board.addShip(6,0, new BattleShip(WarShip.Orientation.HORIZONTAL));
        //
    }

    @Test(expected = IllegalMoveExeption.class)
    public void shouldFailToAddOutsideX() throws Exception {
        // arrange
        //act
        board.addShip(-1,0, new Submarine());
    }

    @Test(expected = IllegalMoveExeption.class)
    public void shouldFailToAddOutsideY() throws Exception {
        // arrange
        //act
        board.addShip(0,-1, new Submarine());
    }

    @Test
    public void shouldMarkMiss() throws Exception {
        //arrange
        //act
        board.shoot(0,0);
        //assert
        assertEquals(State.MISS,board.getField(0,0).getState());
    }

    @Test
    public void shouldMarkAsHit() throws Exception {
        //arrange
        board.addShip(0,0,new Destroyer(WarShip.Orientation.HORIZONTAL));
        //act
        board.shoot(0,0);
        //assert
        assertEquals(State.HIT,board.getField(0,0).getState());
    }

    @Test
    public void shouldMarkAsSunk() throws Exception {
        //arrange
        board.addShip(0,0,new Destroyer(WarShip.Orientation.HORIZONTAL));
        board.shoot(1,0);
        //act
        board.shoot(0,0);
        //assert
        assertEquals(State.SUNK,board.getField(0,0).getState());
        assertEquals(State.SUNK,board.getField(1,0).getState());
    }

    @Test
    public void  shouldDecreaseShipsOnBoard() throws Exception {
        //arrange
        board.addShip(0,0,new Submarine());
        //act
        board.shoot(0,0);
        //asserts
        assertEquals(0,board.getShipsCount());
    }

    @Test(expected = IllegalMoveExeption.class)
    public void shouldNoBeAbleShootTwice() throws Exception {
        //arrange
        board.shoot(0,0);
        //act
        board.shoot(0,0);
        //asserts
    }

    @Test
    public void shouldHaveAllShipsGenerated() throws Exception {
        //arrange
        //act
        board.fillBoard();
        //asserts
        assertEquals(10,board.getShipsCount());
    }

}