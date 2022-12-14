package tetris;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Donatella Malta
 */
public class DrawingCanvas extends Canvas {
    
    private static final int GRID_RESOLUTION = 30;
    
    private static final int DEFAULT_SCREEN_WIDTH = GRID_RESOLUTION*14;
    private static final int DEFAULT_SCREEN_HEIGHT = GRID_RESOLUTION*16;
    private static final String DEFAULT_WINDOW_NAME = "Tetris";

    private int screenWidth;
    private int screenHeight;
    /**
     * 
     */
    private BufferStrategy buffer;
    private static JFrame container;

    public DrawingCanvas() {
        this(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT, DEFAULT_WINDOW_NAME);
    }
    public DrawingCanvas(int screenWidth, int screenHeight, String windowName) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        // Initialize window
        if(container == null){
        container = new JFrame(windowName);}
        else{
                container.validate();
                container.repaint();
        }
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        panel.setLayout(null);

        setBounds(0, 0, screenWidth, screenHeight);
        panel.add(this);
        setIgnoreRepaint(true);
        container.setResizable(false);
        container.setFocusable(true);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int displayWidth = gd.getDisplayMode().getWidth();
        int displayHeight = gd.getDisplayMode().getHeight();

        container.setLocation((displayWidth-screenWidth)/2, (displayHeight-screenHeight)/2);
        container.pack();
        //container.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/icon.png")).getImage());

        container.setVisible(true);
        container.setFocusable(true);

        requestFocus();
        // Initialize buffer strategy
        createBufferStrategy(2);
        buffer = getBufferStrategy();
        // Draw the background
        buffer.show();
        
        
        container.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
        });
    }

    public int getScreenWidth(){
        return DEFAULT_SCREEN_WIDTH/GRID_RESOLUTION;
    }
    public int getScreenHeight(){
        return DEFAULT_SCREEN_HEIGHT/GRID_RESOLUTION;
    }
    
    public void draw(Sprite s, int x, int y) {
        Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
        y += 1;
        y *= GRID_RESOLUTION;
        x *= GRID_RESOLUTION;
        s.draw(g, x, screenHeight - y, GRID_RESOLUTION, GRID_RESOLUTION);
    }

    public void drawText(String text, Color color, int size, int x, int y) {
        Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
        g.setColor(Color.red);
        g.setFont(new Font("Staatliches", Font.BOLD, 75));
        FontMetrics metrics_game = getFontMetrics(g.getFont());
        g.drawString(text, (x - metrics_game.stringWidth(text)) / 2, y / 2);
    }

    public void clear(){
        Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);
    }
    public void update(){
        buffer.show();
    }
}
