package pl.rzonsol.battelship.iaplayer;

import org.junit.Before;
import org.junit.Test;
import pl.rzonsol.battelship.board.Board;
import pl.rzonsol.battelship.board.Field;
import pl.rzonsol.battelship.ships.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by rzonsol on 09.12.2016.
 */
public class MediumIATest {


    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board();
    }

    @Test
    public void shouldReturnNotNull() throws Exception {
        //arrange
        MediumIA iaLavel =new MediumIA();
        //act
        assertNotNull(iaLavel.iaShoot(board));
    }

    @Test
    public void shouldReturnMiss() throws Exception {
        //arrange
        MediumIA iaLavel =new MediumIA();
        // act
        Field field =iaLavel.shootNear(board, 1,1);

        board.setFields(field);
        Field[] fieldArrary = new Field[8];
        for (int i = 0; i < 3; i++) {
            fieldArrary[i]=board.getField(i+0,0);
            fieldArrary[i+3]=board.getField(i+0,2);
        }
        fieldArrary[6]=board.getField(0,1);
        fieldArrary[7]=board.getField(2,1);
        //assert
        Boolean testMiss=false;
        for (int i = 0; i < 8; i++) {
            testMiss = testMiss ||(fieldArrary[i].getState()== State.MISS)? true:false;
        }
        assertTrue(testMiss);
    }

    @Test
    public void shouldreturnMissCornerShootNear() throws Exception {
        //arrange
        MediumIA iaLavel =new MediumIA();
        board.addShip(0,0,new Submarine());
        Boolean testMiss=false;
        //act
        Field field=iaLavel.shootNear(board,0,0);
        board.setFields(field);
        //assert
        testMiss = testMiss ||(board.getField(0,1).getState()== State.MISS)? true:false;
        testMiss = testMiss ||(board.getField(1,0).getState()== State.MISS)? true:false;
        testMiss = testMiss ||(board.getField(1,1).getState()== State.MISS)? true:false;
        assertTrue(testMiss);
    }

    @Test
    public void shouldReturnMissBorderShootNear() throws Exception {
        //arrange
        MediumIA iaLavel =new MediumIA();
        board.addShip(0,5,new Submarine());
        Boolean testMiss= false;
        //act
        Field field = iaLavel.shootNear(board,0,5);
        //assert
        board.setFields(field);
        for (int i = 4; i <7; i++) {
            testMiss = testMiss ||(board.getField(1,i).getState()== State.MISS)? true:false;
        }
        testMiss = testMiss ||(board.getField(0,4).getState()== State.MISS)? true:false;
        testMiss = testMiss ||(board.getField(0,6).getState()== State.MISS)? true:false;
        assertTrue(testMiss);
    }

    @Test
    public void shouldReturnMissCenterShootNear() throws Exception {
        //arrange
        MediumIA iaLavel =new MediumIA();
        board.addShip(5,5,new Submarine());
        //act
        Field field = iaLavel.shootNear(board,5,5);
        //assert
        assertEquals(State.MISS,field.getState());

    }


    @Test
    public void shouldReturnSunkBattleShip() throws Exception {
        //arrange
        MediumIA iaLavel =new MediumIA();
        board.addShip(9,5,new BattleShip(WarShip.Orientation.VERTICAL));

        board.shoot(9,5);
        int x=9;int y=5;
        do{
            Field field = iaLavel.shootNear(board,x,y);
            if (field.getState()==State.HIT){
                x=field.getX();
                y=field.getY();
            }
        }while (board.getField(9,5).getState()!=State.SUNK);

        assertEquals(State.SUNK,board.getField(9,5).getState());

    }



}