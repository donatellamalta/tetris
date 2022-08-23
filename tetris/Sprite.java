package tetris;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * 
 *
 */
public class Sprite {
    
    
	private static Map<String, Sprite> sprites = new HashMap<String, Sprite>();
        public static Sprite getSprite(String ref) {
                // Check for cache
                if (sprites.get(ref) != null)
                        return (Sprite) sprites.get(ref);
                // If cache miss load new
                BufferedImage sourceImage = null;
                try {
                        // Check file existence
                        URL url = Sprite.class.getClassLoader().getResource(ref);
                        if (url == null)
                                throw new RuntimeException("Can't find ref: " + ref);
                        // Read the image
                        sourceImage = ImageIO.read(url);
                } catch (IOException e) {
                        throw new RuntimeException("Failed to load: " + ref);
                }
                // Create an accelerated image of the right size to store the sprite in
                GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
                                .getDefaultConfiguration();
                Image image = gc.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), Transparency.BITMASK);
                // Draw the image into the accelerated image
                image.getGraphics().drawImage(sourceImage, 0, 0, null);
                // Finally create the sprite, add it the cache then return it
                Sprite sprite = new Sprite(image);
                sprites.put(ref, sprite);
                return sprite;
        }
    
    
	/**
	 * The image that represents the sprite
	 */
	private Image image;
	
	/**
	 * Construct a sprite with an image.
	 * @param image the image of the sprite.
	 */
	public Sprite(Image image) {
		this.image = image;
	}
	
	/**
	 * Getter of height.
	 * @return the height of the sprite.
	 */
	public int getHeight() {
		return image.getHeight(null);
	}
	
	/**
	 * Getter of width.
	 * @return the width of the sprite.
	 */
	public int getWidth() {
		return image.getWidth(null);
	}
	
	/**
	 * Draws the sprite at the given x, y position
	 * The positions are referred to ScreenManager.REFERENCE_SCREEN_WIDTH and HEIGHT and are reported
	 * to real size of the window with the ScreenManager.getAbsoluteX and Y methods.
	 * @param g		The canvas where the sprite is to be drawn
	 * @param x		The horizontal position where the sprite is to be drawn
	 * @param y		The vertical position where the sprite is to be drawn
	 * @param angle	The drawn sprite is rotated of angle radians
	 */
	public void draw(Graphics2D g, int x, int y, int w, int h){
		AffineTransform transf = new AffineTransform();
		transf.translate(x, y);
		/*transf.rotate(angle, ScreenManager.getInstance().getAbsoluteX(image.getWidth(null)/2),
							 ScreenManager.getInstance().getAbsoluteY(image.getHeight(null)/2));*/
                //transf.scale(scale, scale);
	    
		g.drawImage(image.getScaledInstance(w, h, 0), transf, null);
	}
	
	
	
}
