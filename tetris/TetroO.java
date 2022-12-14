package tetris;

public class TetroO extends Tetromino{
    public TetroO(int defx, int defy){
        Piece p1 = new Piece(defx, defy); //pezzo centrale
        Piece p2 = new Piece(defx+1, defy);
        Piece p3 = new Piece(defx, defy-1);
        Piece p4 = new Piece(defx+1, defy-1);
        pieces.add(p1);
        pieces.add(p2);
        pieces.add(p3);
        pieces.add(p4);
    }
    public char getType(){
        return 'O';
    }
    
    @Override 
    public void rotate(boolean r){
    }
            
}
