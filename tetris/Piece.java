/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

/**
 *
 * @author Donatella Malta
 */
public class Piece implements Drawable {
    private int x;
    private int y;
    
    public Piece (int x, int y){
        this.x=x;
        this.y=y;
    }
    public void draw (DrawingCanvas d){
        Sprite s = Sprite.getSprite("images/piece.png");
        d.draw(s, x, y);
    }
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    
    
}
