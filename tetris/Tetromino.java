package tetris;

import java.util.*;

public abstract class Tetromino implements Drawable {

    protected List<Piece> pieces = new ArrayList<>();

    public abstract char getType();
    
    public boolean isEmpty() {
        for (Piece p : pieces) {
            if (!pieces.contains(p)) {
                return true;
            }
        }
        return false;
    }
    
    public List<Piece> getPieces(){
        return pieces;
    }
   
    @Override
    public void draw(DrawingCanvas d) {
        for (int i = 0; i < pieces.size(); i++) {
            Piece p = pieces.get(i);
            p.draw(d);
        }
    }

    public void down() {
        for (Piece p : pieces) {
            p.setY(p.getY() - 1);
        }
    }

    public void move(boolean left) {
        for (Piece p : pieces) {
            if (!left) {
                p.setX(p.getX() + 1);
            } else {
                p.setX(p.getX() - 1);
            }
        }
    }

    public int numPiecesOnLine(int y) {
        int count = 0;
        for (Piece p : pieces) {
            if (p.getY() == y) {
                count++;
            }
        }
        return count;
    }

    public void removePiecesOnLine(int y) {
        
        Iterator itr = pieces.iterator(); 
        while (itr.hasNext()){ 
            Piece p = (Piece)itr.next(); 
            if (p.getY() == y) {
                itr.remove();
            }
        } 
        
    }

    public boolean getMoving() {
        return true;
    }

    public void setMoving(boolean val) {
        val = true;
    }

    //la funzione controlla se nella posizione x,y il tetromino this ha un pezzo
    public boolean isTherePieceInPosition(int x, int y) {
        for (Piece p : pieces) {
            if (p.getX() == x && p.getY() == y) {
                return true;
            }
        }
        return false;
    }
    
    public void rotate(boolean r){
        int xCenter = pieces.get(0).getX();
        int yCenter = pieces.get(0).getY();
        
        for (int i=1; i<pieces.size(); i++){
            int xRelative = pieces.get(i).getX() - xCenter;
            int yRelative = pieces.get(i).getY() - yCenter;
            
            int coordinate[] = repositioning(xRelative, yRelative);
            
            int xAbsolute = coordinate[0]+xCenter;
            int yAbsolute = coordinate[1]+yCenter;
            pieces.get(i).setX(xAbsolute);
            pieces.get(i).setY(yAbsolute);
        }
    }
    
    public int[] repositioning(int x, int y){
        int value[] = new int[2];
        if (x==-1&&y==1){
            value[0] = -1;
            value[1]= -1;
            return value;
        }else if(x==-1&&y==-1){
            value[0] = 1;
            value[1] = -1;
            return value;
        }else if(x==1&&y==-1){
            value[0] = 1;
            value[1] = 1;
            return value;
        }else if(x==1&&y==1){
            value[0] = -1;
            value[1] = 1;
            return value;
        }else if(x==-1&&y==0){
            value[0] = 0;
            value[1] = -1;
            return value;
        }else if(x==0&&y==-1){
            value[0] = 1;
            value[1] = 0;
            return value;
        }else if(x==1&&y==0){
            value[0] = 0;
            value[1] = 1;
            return value;
        }else if(x==0&&y==1){
            value[0] = -1;
            value[1] = 0;
            return value;
        }
        return null;
    }
}
