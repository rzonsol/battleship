package pl.rzonsol.battelship.iaplayer;

import pl.rzonsol.battelship.board.Board;
import pl.rzonsol.battelship.board.Field;
import pl.rzonsol.battelship.exeptions.IllegalMoveException;
import pl.rzonsol.battelship.ships.State;

import java.util.Random;

/**
 * Created by rzonsol on 09.12.2016.
 */
public class MediumIA extends Ia{

    private Boolean hitNotSunk=false;
    private static Field field =new Field(0,0,State.EMPTY);

    public Board iaShoot(Board board) {
        Random random = new Random();
        Field shoot=null;
        int randomX = random.nextInt(board.BOARD_SIZE);
        int randomY = random.nextInt(board.BOARD_SIZE);
        Field fieldNearShot;

        if (field.getState()==State.HIT){
            fieldNearShot=shootNear(board,field.getX(),field.getY());
            shoot = fieldNearShot;
            if (fieldNearShot.getState()==State.HIT || fieldNearShot.getState()==State.SUNK){
                field=fieldNearShot;
            }
        }else{
            try {
                board.shoot(randomX,randomY);
                field=board.getField(randomX,randomY);
                shoot = field;
            } catch (IllegalMoveException e) {
                System.out.println(e.getMessage());
            }

        }
        if(shoot!=null){System.out.println("New shoot at "+(char)('A'+shoot.getY()) + shoot.getX());}
        return board;
    }

    /**
     * shoot near last hit not sunk ship
     */
    protected Field shootNear(Board board,int x, int y){
        Random random = new Random();
        Boolean oneAgana =true;
        Field field = null;
        do{
            int xCoordinateStart=random.nextInt(3)-1+x;
            int yCoordinateStart=random.nextInt(3)-1+y;

            if((!board.isOutside(xCoordinateStart,yCoordinateStart))&&(xCoordinateStart!=x ||yCoordinateStart!=y)) {
                try {
                    board.shoot(xCoordinateStart, yCoordinateStart);
                    field = board.getField(xCoordinateStart, yCoordinateStart);
                    if(field.getState()!=null){oneAgana=false;}
                } catch (IllegalMoveException e) {
                    if (board.getField(xCoordinateStart, yCoordinateStart).getState()==State.HIT){x=xCoordinateStart;y=yCoordinateStart;}
                    System.out.println(e.getMessage());
                }
            }
        }while (oneAgana);

        return field;
    }

}
