package pl.rzonsol.battelship;

import pl.rzonsol.battelship.board.Board;
import pl.rzonsol.battelship.iaplayer.*;

/**
 * Created by rzonsol on 08.12.2016.
 */
public class game {

    public static void main(String[] args){

            Board userBoard = new Board();

            Integer gameRound =0;
            Board board = new Board();
            board.fillBoard();
            HardIA pcLevel = new HardIA();

            do{
                System.out.println("Round: " + gameRound);
                System.out.println("Ships left: "+ board.getShipsCount());

                pcLevel.iaShoot(board);
                board.printBord();
                gameRound++;
            }while (board.getShipsCount() >0);

            System.out.println("Game over!!");
            board.printBord();


    }
}
