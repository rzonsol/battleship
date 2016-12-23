package pl.rzonsol.battelship;

import pl.rzonsol.battelship.board.Board;
import pl.rzonsol.battelship.iaplayer.HardIA;
import pl.rzonsol.battelship.iaplayer.Ia;
import pl.rzonsol.battelship.iaplayer.MediumIA;
import pl.rzonsol.battelship.userInterface.UserInteraction;

import java.util.Scanner;

/**
 * Created by rzonsol on 08.12.2016.
 */
public class game {



    public static void main(String[] args){
        boolean oneMore=true;
        Byte level = 0;
        int gameRound =0;
        String levelString;


        Scanner scanner = new Scanner(System.in);
        UserInteraction userInteraction= new UserInteraction();

        Board userBoard = new Board();
        Board pcBoard = new Board();

        Ia pcLevel = null;

        /*Set level of game*/
        do {
            System.out.println("choose level: e - easy, n - normal");
            try {
                levelString = scanner.nextLine();
                levelString = levelString.toUpperCase();
                if (levelString.charAt(0)=='E'){
                    System.out.println("You choose easy level");
                    level = 0;
                    oneMore=false;
                }else if(levelString.charAt(0)=='N'){
                    System.out.println("You choose normal level");
                    level = 1;
                    oneMore=false;
                }else{
                    System.out.println("Enter correct level");
                    oneMore=true;
                }
            }catch (Exception e){
                System.out.println("Enter correct level");
                oneMore=true;
            }
        }while(oneMore);


        switch (level){
            case 0:
                pcLevel = new MediumIA();
            break;
            case 1:
                pcLevel = new HardIA();
            break;
        }

        /*filling boards*/
        pcBoard.fillBoard();
        userBoard=userInteraction.addShipsToBoard(userBoard);


        /*game*/
        do{
                System.out.println("Round: " + gameRound);
                System.out.println("Ships left on pcBoard: "+ pcBoard.getShipsCount());

                pcBoard=userInteraction.userShoot(pcBoard);
                userBoard = pcLevel.iaShoot(userBoard);

                System.out.println("User board");
                userInteraction.printUserBoard(userBoard);
                userBoard.printBord();
                System.out.println("PC board");
                gameRound++;
            }while ((pcBoard.getShipsCount() >0)&& userBoard.getShipsCount() >0);


        /*results*/
        if(userBoard.getShipsCount()==0){
            System.out.println("PC Win!!!!");
        }else{
            System.out.println("YOU Win!!!!");
        }
        System.out.println("Game over!!");
            userBoard.printBord();
    }
}
