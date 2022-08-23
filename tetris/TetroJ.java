/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;
import java.util.*;

/**
 *
 * @author Donatella Malta
 */
public class TetroJ extends Tetromino{
    public TetroJ(int defx, int defy){
        Piece p1 = new Piece(defx, defy-1); //pezzo centrale
        Piece p2 = new Piece(defx, defy);
        Piece p3 = new Piece(defx, defy-2);
        Piece p4 = new Piece(defx-1, defy-2);
        pieces.add(p1);
        pieces.add(p2);
        pieces.add(p3);
        pieces.add(p4);
    }
    public char getType(){
        return 'J';
    } 
}
