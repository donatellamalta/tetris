package tetris;

public class TetroI extends Tetromino {
    public TetroI(int defx, int defy){
        Piece p1 = new Piece(defx, defy-1);//pezzo centrale
        Piece p2 = new Piece(defx, defy);
        Piece p3 = new Piece(defx, defy-2);
        Piece p4 = new Piece(defx, defy-3);
        pieces.add(p1);
        pieces.add(p2);
        pieces.add(p3);
        pieces.add(p4);
    }
    
    public char getType(){
        return 'I';
    }
    
   
    @Override 
    public int[] repositioning (int x, int y){
       
        int value[] = new int[2];
        
        if (x==0 && y==-2){
            value[0] = 2;
            value[1] = 0;
        }else if (x==2 && y==0){
            value[0] = 0;
            value[1] = -2;
        }
         else{
            value = super.repositioning(x, y);
        }
        return value;
    }
}
