package pl.rzonsol.battelship.iaplayer;

import org.junit.Before;
import org.junit.Test;
import pl.rzonsol.battelship.board.Board;
import pl.rzonsol.battelship.board.Field;
import pl.rzonsol.battelship.ships.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by rzonsol on 14.12.2016.
 */
public class HardIATest {

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board();
    }

    @Test
    public void shouldReturnSunk() throws Exception {

        board.addShip(4, 4, new Cruiser(WarShip.Orientation.HORIZONTAL));
        board.shoot(4, 4);
        board.shoot(5, 4);
        HardIA hardIA = new HardIA();
        Field field = hardIA.shootNear(board, 5, 4);

        assertEquals(State.SUNK, field.getState());
    }

    @Test
    public void shouldReturnSunkOnBoarder() throws Exception{
        board.addShip(5, 0, new Cruiser(WarShip.Orientation.HORIZONTAL));
        board.shoot(5, 0);
        board.shoot(6, 0);
        HardIA hardIA = new HardIA();
        Field field = hardIA.shootNear(board, 6, 0);
        assertEquals(State.SUNK, field.getState());
    }

    @Test
    public void shouldReturnSunkOnBoarderUp() throws Exception{
        board.addShip(5, 0, new Cruiser(WarShip.Orientation.HORIZONTAL));
        board.shoot(7, 0);
        board.shoot(6, 0);
        board.shoot(8,0);
        HardIA hardIA = new HardIA();
        Field field = hardIA.shootNear(board, 6, 0);
        assertEquals( State.SUNK, board.getField(5, 0).getState());
    }

    @Test
    public void shouldReturnSunkOnBoarderTop() throws Exception{
        board.addShip(0, 0, new Cruiser(WarShip.Orientation.HORIZONTAL));
        board.shoot(0, 0);
        board.shoot(1, 0);
        HardIA hardIA = new HardIA();
        Field field = hardIA.shootNear(board, 1, 0);
        assertEquals( State.SUNK, field.getState());
    }

    @Test
    public void shouldReturnSunkOnBoarderBottom() throws Exception{
        board.addShip(7, 0, new Cruiser(WarShip.Orientation.HORIZONTAL));
        board.shoot(8, 0);
        board.shoot(9, 0);
        HardIA hardIA = new HardIA();
        Field field = hardIA.shootNear(board, 9, 0);
        assertEquals( State.SUNK, field.getState());
    }

    @Test
    public void shouldReturnSunkVertical() throws Exception{
        board.addShip(5, 5, new Cruiser(WarShip.Orientation.VERTICAL));
        board.shoot(5, 5);
        board.shoot(5, 6);
        HardIA hardIA = new HardIA();
        Field field = hardIA.shootNear(board, 5, 6);
        assertEquals( State.SUNK, field.getState());
    }

    @Test
    public void shouldReturnSunkHorizontalLeftBorder() throws Exception{
        board.addShip(5, 0, new Cruiser(WarShip.Orientation.VERTICAL));
        board.shoot(5, 1);
        board.shoot(5, 2);
        board.shoot(5, 3);
        HardIA hardIA = new HardIA();
        Field field = hardIA.shootNear(board, 5, 1);
        assertEquals( State.SUNK, field.getState());
    }
    @Test
    public void shouldReturnSunkHorizontalCorner() throws Exception{
        board.addShip(0, 0, new BattleShip(WarShip.Orientation.HORIZONTAL));
        board.shoot(0, 0);
        board.shoot(1, 0);
        HardIA hardIA = new HardIA();
        Field field = hardIA.shootNear(board, 0, 0);
        assertEquals( State.HIT, field.getState());
    }

    @Test
    public void shouldReturnSunkHorizontalBatelship() throws Exception{
        board.addShip(2, 6, new BattleShip(WarShip.Orientation.HORIZONTAL));
        board.shoot(4, 6);
        board.shoot(5, 6);
        board.shoot(6, 6);
        HardIA hardIA = new HardIA();
        Field field = hardIA.shootNear(board, 5, 6);
        assertEquals( State.HIT, field.getState());
    }

    @Test
    public void ShouldReturnMissIAShoot() throws Exception {
        board.addShip(3, 1, new Destroyer(WarShip.Orientation.HORIZONTAL));
        board.shoot(2, 1);
        board.shoot(3, 1);
        board.shoot(3, 0);
        HardIA hardIA = new HardIA();
        Field field = hardIA.shootNear(board,3,1);
    }

    @Test
    public void shouldReturnSunkTopBorder() throws Exception{
        HardIA hardIA = new HardIA();
        board.addShip(0,6,new Cruiser(WarShip.Orientation.HORIZONTAL));
        board.shoot(0,6);
        board.shoot(1,6);
        board.shoot(3,6);
        Field field = hardIA.shootNear(board,1,6);
        assertEquals(State.SUNK, field.getState());
    }

    @Test
    public void shouldReturnSunkTopBorderCruiser() throws Exception{
        HardIA hardIA = new HardIA();
        board.addShip(0,9,new Cruiser(WarShip.Orientation.HORIZONTAL));
        board.shoot(0,9);
        board.shoot(1,9);
        for (int i = 0; i < 5; i++) {
            board.getField(i,8).setState(State.MISS);
        }
        Field field = hardIA.shootNear(board,1,9);
        board.printBord();
        assertEquals(State.SUNK, field.getState());
    }

    @Test
    public void shouldReturnMissAround()throws Exception{
        HardIA hardIA = new HardIA();
        board.addShip(5,5,new Submarine(WarShip.Orientation.HORIZONTAL));
        board.shoot(5,5);
        board = hardIA.ifSunkPutMissAround(board,5,5);
        board.printBord();
        assertEquals(State.MISS,board.getField(4,5).getState());
        assertEquals(State.MISS,board.getField(6,5).getState());
        assertEquals(State.MISS,board.getField(5,4).getState());
        assertEquals(State.MISS,board.getField(5,6).getState());
        assertEquals(State.MISS,board.getField(4,4).getState());
        assertEquals(State.MISS,board.getField(4,6).getState());
        assertEquals(State.MISS,board.getField(6,4).getState());
        assertEquals(State.MISS,board.getField(6,6).getState());
    }

    @Test
    public void shouldReturnMissAroundCruiser()throws Exception{
        HardIA hardIA = new HardIA();
        board.addShip(0,0,new Destroyer(WarShip.Orientation.HORIZONTAL));
        board.shoot(0,0);
        board.shoot(1,0);
        board = hardIA.ifSunkPutMissAround(board,1,0);
        board.printBord();
    }

    @Test
    public void shouldReturnMissAroundBattleShip()throws Exception{
        HardIA hardIA = new HardIA();
        board.addShip(0,0,new BattleShip(WarShip.Orientation.VERTICAL));
        board.shoot(0,0);
        board.shoot(0,1);
        board.shoot(0,2);
        board.shoot(0,3);
        board = hardIA.ifSunkPutMissAround(board,0,0);
        board.printBord();
    }

    @Test
    public void shouldReturnMissAroundBattleShipCenter()throws Exception{
        HardIA hardIA = new HardIA();
        board.addShip(3,3,new BattleShip(WarShip.Orientation.VERTICAL));
        board.shoot(3,3);
        board.shoot(3,4);
        board.shoot(3,5);
        board.shoot(3,6);
        board = hardIA.ifSunkPutMissAround(board,3,3);
        board.printBord();
    }

    @Test
    public void shouldReturnMissAroundDestroyer()throws Exception{
        HardIA hardIA = new HardIA();
        board.addShip(1,8,new Destroyer(WarShip.Orientation.VERTICAL));
        board.shoot(1,8);
        board.shoot(1,9);
        board = hardIA.ifSunkPutMissAround(board,1,8);
        board.printBord();
        assertEquals(State.MISS,board.getField(0,9).getState());
        assertEquals(State.MISS,board.getField(0,8).getState());
        assertEquals(State.MISS,board.getField(0,7).getState());
        assertEquals(State.MISS,board.getField(2,9).getState());
        assertEquals(State.MISS,board.getField(2,8).getState());
        assertEquals(State.MISS,board.getField(2,7).getState());
        assertEquals(State.MISS,board.getField(1,7).getState());

    }
}