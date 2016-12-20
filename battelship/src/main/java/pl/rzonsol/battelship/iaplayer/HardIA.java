package pl.rzonsol.battelship.iaplayer;

import pl.rzonsol.battelship.board.Board;
import pl.rzonsol.battelship.board.Field;
import pl.rzonsol.battelship.exeptions.IllegalMoveException;
import pl.rzonsol.battelship.ships.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by rzonsol on 14.12.2016.
 */
public class HardIA {
    private static Field field =new Field(0,0,State.EMPTY);
    private int listCordynate=0;
    private List<Field> listOfFields = new ArrayList<Field>();
    private Board board;


    private Boolean hitNotSunk=false;
    private static Field lastField =new Field(0,0,State.EMPTY);


    public Board IaShoot(Board board) {
        Random random = new Random();
        int randomX=-1, randomY=-1;
        Field fieldNearShot;


//        if (lastField.getState()==State.EMPTY || lastField.getState()==State.SUNK){
//            boolean newShot=true;
//            do {
//                randomX = random.nextInt(board.BOARD_SIZE);
//                randomY = random.nextInt(board.BOARD_SIZE);
//                newShot = (board.getField(randomX,randomY).getState()!=State.HIT && board.getField(randomX,randomY).getState()!=State.SUNK && board.getField(randomX,randomY).getState()!=State.MISS )?false:true;
//            }while(newShot);
//            try {
//                board.shoot(randomX,randomY);
//                if (board.getField(randomX,randomY).getState()==State.HIT){
//                    lastField=board.getField(randomX,randomY);
//                }
//            } catch (IllegalMoveException e) {
//                System.out.println(e.getMessage());
//            }
//            System.out.println("x="+randomX+",y="+randomY);
//        }else{
//            Field hitOrNot = shootNear(board,lastField.getX(),lastField.getY());
//            if(hitOrNot.getState()==State.HIT || hitOrNot.getState()==State.SUNK){
//                lastField=hitOrNot;
//                System.out.println("x="+lastField.getX()+",y="+lastField.getY());
//            }
//        }
        return board;
    }



    public Field shootNear(Board board, int x, int y){
        this.board=board;
        Field shoot;

        if(firstDeckHit(x,y)){
            Random random = new Random();
            boolean stop=true;
            do {
                if(random.nextInt(2)==0){
                    do {
                        x+=Math.pow(-1,random.nextInt(2));
                    }while(board.isOutside(x,y));
                }else{
                    do {
                        y+=Math.pow(-1,random.nextInt(2));
                    }while (board.isOutside(x,y));
                }

                if(board.getField(x,y).getState()!=State.MISS){
                    try {
                        board.shoot(x,y);
                        stop=false;
                    } catch (IllegalMoveException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }while (stop);
        }


        if((!board.isOutside(x-1,y)&&board.getField(x-1,y).getState()==State.HIT)||(!board.isOutside(x+1,y)&& board.getField(x+1,y).getState()==State.HIT)) {
            return shootVertical(x, y);
        }else if((!board.isOutside(x,y-1)&&board.getField(x,y-1).getState()==State.HIT) || (!board.isOutside(x,y+1)&&board.getField(x,y+1).getState()==State.HIT)){
            return shootHorizontal(x,y);
        }
        return null;


    }





    private Field shootVertical(int x, int y){
        Field shootOrNot;

        for (int i = 0; i < 8; i++) {
            if(!this.board.isOutside(x-3+i,y)){
                listOfFields.add(this.board.getField(x-3+i,y));
                if (i==2){listCordynate = listOfFields.size();
                }
            }
        }

        if(listCordynate>0 && listCordynate<listOfFields.size()-1 && (listOfFields.get(listCordynate-1).getState()==State.HIT || listOfFields.get(listCordynate+1).getState()==State.HIT) ){
            shootOrNot= shootIfPossibleVertical(1);
            if(shootOrNot!=null){
                return shootOrNot;
            }
            shootOrNot= shootIfPossibleVertical(-1);
            if(shootOrNot!=null){
                return shootOrNot;
            }
        }else if (listCordynate==0){

            shootOrNot= shootIfPossibleVertical(1);
            if(shootOrNot!=null){
                return shootOrNot;
            }
        }else if (listCordynate == listOfFields.size()-1){
            shootOrNot= shootIfPossibleVertical(-1);
            if(shootOrNot!=null){
                return shootOrNot;
            }
        }
        return null;
    }


    public Field shootHorizontal(int x, int y){
        Field shootOrNot;

        for (int i = 0; i < 8; i++) {
            if(!this.board.isOutside(x,y-3+i)){
                listOfFields.add(this.board.getField(x,y-3+i));
                if (i==2){listCordynate = listOfFields.size();
                }
            }
        }

        if(listCordynate>0 && listCordynate<listOfFields.size()-1 && (listOfFields.get(listCordynate-1).getState()==State.HIT || listOfFields.get(listCordynate+1).getState()==State.HIT) ){
            shootOrNot= shootIfPossibleVertical(1);
            if(shootOrNot!=null){
                return shootOrNot;
            }
            shootOrNot= shootIfPossibleVertical(-1);
            if(shootOrNot!=null){
                return shootOrNot;
            }
        }else if (listCordynate==0){

            shootOrNot= shootIfPossibleVertical(1);
            if(shootOrNot!=null){
                return shootOrNot;
            }
        }else if (listCordynate == listOfFields.size()-1){
            shootOrNot= shootIfPossibleVertical(-1);
            if(shootOrNot!=null){
                return shootOrNot;
            }
        }
        return null;
    }



    private Field shootIfPossibleVertical(int plusMinus){
        int count = listCordynate+plusMinus;
        boolean stop=true;
        do{
            if(listOfFields.get(count).getState()!=State.HIT && listOfFields.get(count).getState()!=State.MISS){
                try {
                    board.shoot(listOfFields.get(count).getX(),listOfFields.get(count).getY());
                } catch (IllegalMoveException e) {
                    e.printStackTrace();
                }
                return listOfFields.get(count);
            }else if (listOfFields.get(count).getState()==State.MISS){
                count+=board.BOARD_SIZE;
            }
            count=count+plusMinus;
            if (plusMinus>0 && !(count < listOfFields.size())){
                stop=false;
            }else if((plusMinus<0 && !(count >=0)) || count>listOfFields.size()){
                stop=false;
            }
        }while(stop);
        return null;
    }

    private boolean firstDeckHit(int x, int y){
        boolean firstHit=true;
        if(!board.isOutside(x-1,y)){
            firstHit =(this.board.getField(x-1,y).getState()==State.HIT)?false:true;
        }
        if(!board.isOutside(x+1,y)){
            firstHit =(firstHit && ((this.board.getField(x+1,y).getState()==State.HIT)?false:true));
        }
        if(!board.isOutside(x,y-1)){
            firstHit =(firstHit &&((this.board.getField(x,y-1).getState()==State.HIT)?false:true));
        }
        if(!board.isOutside(x,y+1)){
            firstHit =(firstHit && ((this.board.getField(x,y+1).getState()==State.HIT)?false:true));
        }
        return firstHit;
    }

}
