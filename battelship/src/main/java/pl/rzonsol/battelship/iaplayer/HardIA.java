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
public class HardIA extends Ia{
    private Board board;
    private static Field lastField =new Field(0,0,State.EMPTY);


    public Board iaShoot(Board board) {
        this.board=board;

        Random random = new Random();
        int randomX=-1, randomY=-1;


        if (lastField.getState()==State.EMPTY || lastField.getState()==State.SUNK){

           boolean shootIntoEmpty=true;

           do {
                randomX = random.nextInt(board.BOARD_SIZE);
                randomY = random.nextInt(board.BOARD_SIZE);
                if(this.board.getField(randomX,randomY).getState()==State.SHIP ||this.board.getField(randomX,randomY).getState()==State.EMPTY){
                    shootIntoEmpty=false;
                }
           }while(shootIntoEmpty);

           try {
                this.board.shoot(randomX,randomY);
            } catch (IllegalMoveException e) {
                System.out.println(e.getMessage());
            }

            if (this.board.getField(randomX,randomY).getState()==State.SUNK){
                this.board=ifSunkPutMissAround(board,randomX,randomY);
            }

            if (this.board.getField(randomX,randomY).getState()==State.HIT){
                lastField=board.getField(randomX,randomY);
            }

        }else if(lastField.getState()==State.HIT){

            Field nearShot = shootNear(this.board,lastField.getX(),lastField.getY());

            if (nearShot.getState()==State.HIT){
                lastField=nearShot;
            }else if(nearShot.getState()==State.SUNK){
                lastField=nearShot;
                this.board=ifSunkPutMissAround(this.board,nearShot.getX(),nearShot.getY());
            }
        }

        return this.board;
    }

    public Field shootNear(Board board, int x, int y){
        this.board=board;


        /*shoot if we hit ship first time but not sunk */
        if(firstDeckHit(x,y)){

            Random random = new Random();
            boolean stop=true;
            int randomX,randomY;

            do {
                randomX=x;randomY=y;
                if(random.nextInt(2)==0){
                    do {
                        randomX+=Math.pow(-1,random.nextInt(2));
                    }while(this.board.isOutside(randomX,randomY));
                }else{
                    do {
                        randomY+=Math.pow(-1,random.nextInt(2));
                    }while (this.board.isOutside(randomX,randomY));
                }

                if(this.board.getField(randomX,randomY).getState()!=State.MISS){
                    try {
                        this.board.shoot(randomX,randomY);
                        stop=false;
                    } catch (IllegalMoveException e) {
                        System.out.println(e.getMessage());
                    }
                }

            }while (stop);

            return this.board.getField(randomX,randomY);
        }

        /*shoot near if we hits ship more than 1*/
        if((!board.isOutside(x-1,y)&&this.board.getField(x-1,y).getState()==State.HIT)||(!board.isOutside(x+1,y)&& this.board.getField(x+1,y).getState()==State.HIT)) {
            return shootVertical(x, y);
        }else if((!board.isOutside(x,y-1)&&this.board.getField(x,y-1).getState()==State.HIT) || (!board.isOutside(x,y+1)&&this.board.getField(x,y+1).getState()==State.HIT)){
            return shootHorizontal(x,y);
        }
        return null;


    }

    private Field shootVertical(int x, int y){
        Field shootOrNot;
        int listCordynate=0;
        List<Field> listOfFields = new ArrayList<Field>();
        for (int i = 0; i < 8; i++) {
            if(!this.board.isOutside(x-3+i,y)){
                listOfFields.add(this.board.getField(x-3+i,y));
                if (i==2){listCordynate = listOfFields.size();
                }
            }
        }

        if(listCordynate>0 && listCordynate<listOfFields.size()-1 && (listOfFields.get(listCordynate-1).getState()==State.HIT || listOfFields.get(listCordynate+1).getState()==State.HIT) ){
            shootOrNot= shootIfPossible(1,listCordynate, listOfFields);
            if(shootOrNot!=null){
                return shootOrNot;
            }
            shootOrNot= shootIfPossible(-1, listCordynate, listOfFields);
            if(shootOrNot!=null){
                return shootOrNot;
            }
        }else if (listCordynate==0){
            shootOrNot= shootIfPossible(1, listCordynate, listOfFields);
            if(shootOrNot!=null){
                return shootOrNot;
            }
        }else if (listCordynate == listOfFields.size()-1){
            shootOrNot= shootIfPossible(-1, listCordynate, listOfFields);
            if(shootOrNot!=null){
                return shootOrNot;
            }
        }
        return null;
    }

    public Field shootHorizontal(int x, int y){
        Field shootOrNot;
        int listCordynate=0; // dodałem
        List<Field> listOfFields = new ArrayList<Field>(); //dodałem


        for (int i = 0; i < 8; i++) {
            if(!this.board.isOutside(x,y-3+i)){
                listOfFields.add(this.board.getField(x,y-3+i));
                if (i==2){listCordynate = listOfFields.size();
                }
            }
        }

        if(listCordynate>0 && listCordynate<listOfFields.size()-1 && (listOfFields.get(listCordynate-1).getState()==State.HIT || listOfFields.get(listCordynate+1).getState()==State.HIT) ){
            shootOrNot= shootIfPossible(1, listCordynate, listOfFields);
            if(shootOrNot!=null){
                return shootOrNot;
            }
            shootOrNot= shootIfPossible(-1, listCordynate, listOfFields);
            if(shootOrNot!=null){
                return shootOrNot;
            }
        }else if (listCordynate==0){

            shootOrNot= shootIfPossible(1, listCordynate, listOfFields);
            if(shootOrNot!=null){
                return shootOrNot;
            }
        }else if (listCordynate == listOfFields.size()-1){
            shootOrNot= shootIfPossible(-1, listCordynate, listOfFields);
            if(shootOrNot!=null){
                return shootOrNot;
            }
        }
        return null;
    }

    private Field shootIfPossible(int plusMinus, int listCoordinate, List<Field> listOfFields ){
        int count = listCoordinate+plusMinus;
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

    public Board ifSunkPutMissAround(Board board,int x, int y){

        List<Field> listOfSunks = new ArrayList<Field>();

        listOfSunks.add(board.getField(x,y));
        int i=1;
        boolean stop=true;
        do {
            if (!board.isOutside(x+i,y)&& board.getField(x+i,y).getState()==State.SUNK){
                listOfSunks.add(board.getField(x+i,y));
                i++;
            }else{
                stop=false;
            }
        }while(stop);
        i=1;
        stop=true;
        do {
            if (!board.isOutside(x-i,y)&& board.getField(x-i,y).getState()==State.SUNK){
                listOfSunks.add(board.getField(x-i,y));
                i++;
            }else{
                stop=false;
            }
        }while(stop);
        i=1;
        stop=true;
        do {
            if (!board.isOutside(x,y+i)&& board.getField(x,y+i).getState()==State.SUNK){
                listOfSunks.add(board.getField(x,y+i));
                i++;
            }else{
                stop=false;
            }
        }while(stop);
        i=1;
        stop=true;
        do {
            if (!board.isOutside(x,y-i)&& board.getField(x,y-i).getState()==State.SUNK){
                listOfSunks.add(board.getField(x,y-i));
                i++;
            }else{
                stop=false;
            }
        }while(stop);

        for (Field field: listOfSunks) {
            if (!board.isOutside(field.getX()-1,field.getY())&& board.getField(field.getX()-1,field.getY()).getState()!=State.SUNK){
                board.getField(field.getX()-1,field.getY()).setState(State.MISS);
            }
            if (!board.isOutside(field.getX()+1,field.getY())&& board.getField(field.getX()+1,field.getY()).getState()!=State.SUNK){
                board.getField(field.getX()+1,field.getY()).setState(State.MISS);
            }
            if (!board.isOutside(field.getX(),field.getY()-1)&& board.getField(field.getX(),field.getY()-1).getState()!=State.SUNK){
                board.getField(field.getX(),field.getY()-1).setState(State.MISS);
            }
            if (!board.isOutside(field.getX(),field.getY()+1)&&  board.getField(field.getX(),field.getY()+1).getState()!=State.SUNK){
                board.getField(field.getX(),field.getY()+1).setState(State.MISS);
            }
            if (!board.isOutside(field.getX()-1,field.getY()+1)&&  board.getField(field.getX()-1,field.getY()+1).getState()!=State.SUNK){
                board.getField(field.getX()-1,field.getY()+1).setState(State.MISS);
            }
            if (!board.isOutside(field.getX()+1,field.getY()+1)&&  board.getField(field.getX()+1,field.getY()+1).getState()!=State.SUNK){
                board.getField(field.getX()+1,field.getY()+1).setState(State.MISS);
            }
            if (!board.isOutside(field.getX()-1,field.getY()-1)&&  board.getField(field.getX()-1,field.getY()-1).getState()!=State.SUNK){
                board.getField(field.getX()-1,field.getY()-1).setState(State.MISS);
            }
            if (!board.isOutside(field.getX()+1,field.getY()-1)&&  board.getField(field.getX()+1,field.getY()-1).getState()!=State.SUNK){
                board.getField(field.getX()+1,field.getY()-1).setState(State.MISS);
            }

        }
        return board;
    }

}
