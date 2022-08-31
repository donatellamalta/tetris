package tetris;

import java.awt.Color;

public class GameOverState implements GameState{
    Game game;
    DrawingCanvas c;
    public GameOverState(Game game, DrawingCanvas c) {
        this.game = game;
        this.c = c;
    }
   
    public void gameLoop(long delta) {

        c.drawText("GAME OVER", Color.red, 20, c.getWidth(), c.getHeight());


        
    }

}
