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
        board.addShip(0, 0, new Cruiser(WarShip.Orientation.VERTICAL));
        board.shoot(0, 0);
        board.shoot(0, 1);
        HardIA hardIA = new HardIA();
        Field field = hardIA.shootNear(board, 0, 0);
        assertEquals( State.SUNK, field.getState());
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


}