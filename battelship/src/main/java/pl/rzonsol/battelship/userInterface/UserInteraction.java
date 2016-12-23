package pl.rzonsol.battelship.userInterface;

import pl.rzonsol.battelship.board.Board;
import pl.rzonsol.battelship.exeptions.IllegalMoveException;
import pl.rzonsol.battelship.ships.*;

import java.util.Scanner;

/**
 * Created by rzonsol on 22.12.2016.
 */
public class UserInteraction {

    public Board addShipsToBoard(Board board){
        board = addSubmarines(board);
        board = addDestroyers(board);
        board = addCruiser(board);
        board = addBattleShip(board);
        return board;
    }

    public Board userShoot(Board board){
        int[] xy = new int[2];
        boolean oneMore=true;

        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Where you want shoot (enter position, first later second number, ex A5 ) :");
            try {
                String shipPosition = scanner.nextLine();
                shipPosition=shipPosition.toUpperCase();
                xy[1] = shipPosition.charAt(0) -'A';
                xy[0] = shipPosition.charAt(1) -'0';
                oneMore= false;
            }catch (Exception e){
                System.out.println("Enter correct coordinate.");
            }
        }while (oneMore);
        try {
            board.shoot(xy[0],xy[1]);
        } catch (IllegalMoveException e) {
            System.out.println(e.getMessage());
        }
        return board;
    }


    private int[] getCoordinate(){

        int[] xy = new int[2];
        boolean oneMore =true;

        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.println("Set the ship on the board(enter position, first later second number, ex A5 ):");
                String shipPosition = scanner.nextLine();
                shipPosition=shipPosition.toUpperCase();
                xy[1] = shipPosition.charAt(0) -'A';
                xy[0] = shipPosition.charAt(1) -'0';
                oneMore=false;
            }catch (Exception e){
                System.out.println("Enter correct coordinate.");
            }
        }while (oneMore);
        return xy;
    }


    private WarShip.Orientation getOrientation(){

        WarShip.Orientation orientation=null;

        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Set the ship orientation on the board(enter h for horizontal and v for vertical):");
            String shipOrientation = scanner.nextLine();
            shipOrientation = shipOrientation.toUpperCase();

            if (shipOrientation.charAt(0)=='H'){
                return WarShip.Orientation.VERTICAL;
            }else if (shipOrientation.charAt(0)=='V'){
                return WarShip.Orientation.HORIZONTAL;
            }else{
                System.out.println("Enter correct value.");
            }
        }while (orientation!=null);

        return orientation;
    }


    private Board addSubmarines(Board board){
        int i=0;
        do {
            System.out.println("Set submarines");
            int[] xy=getCoordinate();
            Ship ship =new Submarine();
            try {
                board.addShip(xy[0],xy[1],ship);
                i++;
            } catch (IllegalMoveException e) {
                System.out.println(e.getMessage());
            }
            printUserBoard(board);
        }while(i<4);
        return board;
    }

    private   Board addDestroyers(Board board){
        int i=0;
        do {
            System.out.println("Set destroyers");
            int[] xy =getCoordinate();
            WarShip.Orientation orientation = getOrientation();
            Ship ship = new Destroyer(orientation);
            try {
                board.addShip(xy[0],xy[1],ship);
                i++;
            } catch (IllegalMoveException e) {
                System.out.println(e.getMessage());
            }
            printUserBoard(board);
        }while (i<3);
        return board;
    }

    private Board addCruiser(Board board){
        int i=0;
        do {
            System.out.println("Set cruisers");
            int[] xy = getCoordinate();
            WarShip.Orientation orientation= getOrientation();
            Ship ship = new Cruiser(orientation);
            try {
                board.addShip(xy[0],xy[1],ship);
                i++;
            } catch (IllegalMoveException e) {
                System.out.println(e.getMessage());
            }
            printUserBoard(board);
        }while (i<2);
        return board;
    }

    private Board addBattleShip(Board board){
        int i=0;
        System.out.println("Set BattleShip");
        do {
            int[] xy = getCoordinate();
            WarShip.Orientation orientation= getOrientation();
            Ship ship = new BattleShip(orientation);
            try {
                board.addShip(xy[0],xy[1],ship);
                i++;
            } catch (IllegalMoveException e) {
                System.out.println(e.getMessage());
            }
            printUserBoard(board);
        }while (i<1);
        return board;
    }

    public void printUserBoard(Board board){
        board.userShips();
        board.printBord();
        board.userShipsToNormal();
    }

}
