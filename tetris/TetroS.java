package tetris;

public class TetroS extends Tetromino{
    public TetroS(int defx, int defy){
        Piece p1 = new Piece(defx-1, defy); //pezzo centrale
        Piece p2 = new Piece(defx, defy);
        Piece p3 = new Piece(defx-1, defy-1);
        Piece p4 = new Piece(defx-2, defy-1);
        pieces.add(p1);
        pieces.add(p2);
        pieces.add(p3);
        pieces.add(p4);
    }
    public char getType(){
        return 'S';
    }
}
