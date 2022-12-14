package tetris;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.KeyAdapter;

public class PlayingState extends KeyAdapter implements GameState {
    Game game;
    DrawingCanvas c;

    // The whole game logic is down here
    private static final int MAX_X = 13;
    private static final int MAX_Y = 14;
    private static final int INITIAL_PIECE_X = 6;
    private static final int INITIAL_PIECE_Y = 14;

    private static final int STEP_DOWN_INTERVAL_MS = 1000;

    long last_step_down_time_ms;
    Tetromino current = null;
    List<Tetromino> tetrominos = null;

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                if (hasDown(current)) {
                    current.down();
                }
                break;
            case KeyEvent.VK_UP:
                current.rotate(true);
                break;
            case KeyEvent.VK_LEFT:
                if (hasLeft(current)) {
                    current.move(true);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (hasRight(current)) {
                    current.move(false);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public PlayingState(Game game, DrawingCanvas c) {
        this.game = game;
        this.c = c;
        c.addKeyListener(this);
        tetrominos = new ArrayList<>();
        current = newRandomTetromino();
        last_step_down_time_ms = 0;
    }

    public void gameLoop(long elapsedMs) {
        last_step_down_time_ms += elapsedMs;
        if (last_step_down_time_ms > STEP_DOWN_INTERVAL_MS) {

            if (hasDown(current)) {
                current.down();

            } else {
                current.setMoving(false);
                tetrominos.add(current);
                current = newRandomTetromino();

                if (fullFrame(tetrominos.get(tetrominos.size() - 1))) {
                    
                    game.over();

                } else {
                    deleteLine();
                }

            }
            last_step_down_time_ms = 0;
        }
        for (Tetromino t : tetrominos) {
            t.draw(c);
        }
        current.draw(c);
    }

    private Tetromino newTetromino(char type, int x, int y) {
        switch (type) {
            case 'I':
                return new TetroI(x, y);
            case 'J':
                return new TetroJ(x, y);
            case 'L':
                return new TetroL(x, y);
            case 'O':
                return new TetroO(x, y);
            case 'S':
                return new TetroS(x, y);
            case 'Z':
                return new TetroZ(x, y);
            case 'T':
                return new TetroT(x, y);
        }
        return null;
    }

    private Tetromino newRandomTetromino() {
        Random r = new Random();
        final char ttypes[] = { 'I', 'J', 'L', 'O', 'S', 'Z', 'T' };
        char type = ttypes[r.nextInt(ttypes.length)];

        Tetromino t = newTetromino(type, INITIAL_PIECE_X, INITIAL_PIECE_Y);
        t.setMoving(true);
        return t;
    }

    private boolean hasDown(Tetromino t) {
        // Se ha un pezzo sull'ultima riga, allora non pu?? scendere (?? nella parte pi??
        // in basso)
        if (t.numPiecesOnLine(0) > 0) {
            return false;
        }
        // Altrimenti verifico che non ci siano altri pezzi sotto
        return hasSide(t, 'D');
    }

    private boolean hasLeft(Tetromino t) {
        List<Piece> mypieces = t.getPieces();
        for (Piece p : mypieces) {
            if (p.getX() <= 0) {
                return false;
            }
        }
        return hasSide(t, 'L');
    }

    private boolean hasRight(Tetromino t) {
        List<Piece> mypieces = t.getPieces();
        for (Piece p : mypieces) {
            if (p.getX() >= MAX_X) {
                return false;
            }
        }
        return hasSide(t, 'R');
    }

    private boolean hasSide(Tetromino t, char side) {
        List<Piece> mypieces = t.getPieces();
        for (Tetromino other : tetrominos) {
            for (Piece p : mypieces) {
                int px = p.getX();
                int py = p.getY();
                switch (side) {
                    case 'L':
                        px -= 1;
                        break;
                    case 'R':
                        px += 1;
                        break;
                    case 'D':
                        py -= 1;
                        break;
                    default:
                        break;
                }
                if (other.isTherePieceInPosition(px, py)) {
                    return false;
                }
            }
        }
        return true;
    }

    // la funzione restituisce SE una posizione x,y ?? occupata
    public boolean isTherePieceInPosition(int x, int y) {
        for (Tetromino t : tetrominos) {
            if (t.isTherePieceInPosition(x, y)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLineFull(int lineIndex) {
        int numPiecesOnLine = 0;
        for (int i = 0; i < (c.getScreenWidth()); i++) {
            if (isTherePieceInPosition(i, lineIndex)) {
                numPiecesOnLine++; // controlla che ci sta gi?? un metodo fatto in tetromino
                if (numPiecesOnLine == c.getScreenWidth()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void deleteLine() {
        for (int i = 0; i < c.getScreenHeight(); i++) {
            if (isLineFull(i)) {
                for (int j = 0; j < tetrominos.size(); j++) {
                    tetrominos.get(j).removePiecesOnLine(i);
                }
                downLines(i);
            }
        }
    }

    // il parametro ?? la linea che ?? stata cancellata
    private void downLines(int ydeletedLine) {
        for (int i = 0; i < tetrominos.size(); i++) {
            List<Piece> pList = tetrominos.get(i).getPieces();
            for (int j = 0; j < pList.size(); j++) {
                Piece currentPiece = pList.get(j);
                int yCurrentPiece = currentPiece.getY();
                if (yCurrentPiece > ydeletedLine) {
                    currentPiece.setY(yCurrentPiece - 1);
                }
            }
        }
    }

    private boolean fullFrame(Tetromino t) {
        List<Piece> mypieces = t.getPieces();
        for (Piece p : mypieces) {
            if (p.getY() >= MAX_Y) {
                System.out.println("Full");
                return true;
            }
        }
        return false;
    }
    
}
