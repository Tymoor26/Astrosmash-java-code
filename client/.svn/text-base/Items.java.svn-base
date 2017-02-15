package client;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Alexis Lowe
 * @author Tymoor Rahman
 * @author Ahmed Qureshi
 */
public class Items {
	private int x;
	private int y;
	private boolean onMap;
	private BufferedImage image;
	private BufferedImage subImage;
	private String type;
	private int itemImageCounter;

	/**
	 * Builds a Item from a Shared Item
	 * 
	 * @param items
	 */
	public Items(shared.Items items) {
		this.type = items.type;
		this.x = items.x;
		this.y = items.y;
		itemImageCounter = 0;
		try {
			image = ImageIO.read(new File("Textures/" + type + "_" + "Frames"
					+ ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the Y coordinate of the item
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Sets the X coordinate of the item
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Renders the item requires the Canvas and the Player
	 * 
	 * @param g
	 *            Graphics
	 * @param player
	 *            Player
	 */
	public void render(Graphics g, Player player) {
		int mapX = -1 * (x - player.xOffset);
		int mapY = -1 * (y - player.yOffset);
		subImage = image.getSubimage((itemImageCounter / 20) * 32, 0, 32, 32);
		g.drawImage(subImage, mapX, mapY, null);
		itemImageCounter++;
		if (itemImageCounter > 180) {
			itemImageCounter = 0;
		}
	}
}
