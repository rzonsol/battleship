package pl.rzonsol.battelship.iaplayer;

import pl.rzonsol.battelship.board.Board;
import pl.rzonsol.battelship.exeptions.IllegalMoveException;

import java.util.Random;

/**
 * Created by rzonsol on 09.12.2016.
 */
public class EasyIA {

    public Board IaShoot(Board board){
        Random random = new Random();
        int y=random.nextInt(10);
        int x=random.nextInt(10);
        try {
            board.shoot(x,y);
        } catch (IllegalMoveException illegalMoveExeption) {
            System.out.println("Message: " + illegalMoveExeption.getMessage());
        } finally {
            System.out.println("You Shot at : "+ (char)('A'+y) +x);
        }
        return board;
    }
}
