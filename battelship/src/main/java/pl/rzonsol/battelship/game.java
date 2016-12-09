package pl.rzonsol.battelship;

import pl.rzonsol.battelship.board.Board;
import pl.rzonsol.battelship.exeptions.IllegalMoveExeption;
import pl.rzonsol.battelship.ships.Submarine;

/**
 * Created by rzonsol on 08.12.2016.
 */
public class game {

    public static void main(String[] args){
        Board userBoard = new Board();

        try {
            userBoard.addShip(1,0,new Submarine());
            userBoard.addShip(1,2,new Submarine());
            userBoard.addShip(2,4,new Submarine());
            userBoard.addShip(5,0,new Submarine());
        } catch (IllegalMoveExeption illegalMoveExeption) {
            illegalMoveExeption.printStackTrace();
        }
        userBoard.userShips();
        userBoard.printBord();


//        Integer gameRound =0;
//        Board board = new Board();
//        board.fillBoard();
//        Scanner scanner = new Scanner(System.in);
//        while (board.getShipsCount() >0){
//            System.out.println("Round: " + gameRound);
//            System.out.println("Ships left: "+ board.getShipsCount());
//            board.printBord();
//            String move = scanner.nextLine();
//            move = move.toUpperCase();
//            int y=move.charAt(0)-'A';
//            int x=move.charAt(1) - '0';
//            gameRound++;
//            try {
//                board.shoot(x,y);
//            } catch (IllegalMoveExeption illegalMoveExeption) {
//                System.out.println("Error: " + illegalMoveExeption.getMessage());
//            }
//        }

        System.out.println("Game over!!");

    }
}
