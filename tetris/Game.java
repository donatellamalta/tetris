package tetris;

public class Game {

    private static final long FPS_LIMIT = 90;
    private static final long MIN_LOOP_DURATION_MS = 1000 / FPS_LIMIT;
    DrawingCanvas c;
    GameState state;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game g = new Game();
        g.run();
    }

    private Game() {
        c = new DrawingCanvas();
    }

    private boolean gameRunning = true;

    private void run() {
        long lastLoopTime = System.currentTimeMillis() - 1;
        //state = new GameOverState(this, c); //to test only the game over display
        state = new PlayingState(this, c);

        while (gameRunning) {
            long curTime = System.currentTimeMillis();
            long delta = curTime - lastLoopTime;
            lastLoopTime = curTime;
            c.clear();
            
            state.gameLoop(delta);
            c.update();
            // Apply frame limit
            long loopDuration = System.currentTimeMillis() - curTime;
            if (loopDuration < MIN_LOOP_DURATION_MS) {
                try {
                    Thread.sleep((MIN_LOOP_DURATION_MS - loopDuration) / 1000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void over(){
        state = new GameOverState(this, c);
    }

    
}
