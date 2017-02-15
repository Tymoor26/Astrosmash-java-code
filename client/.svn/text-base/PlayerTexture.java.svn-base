package client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Ahmed Qureshi
 */
public class PlayerTexture {
	private BufferedImage originalImage;
	private BufferedImage image;
	private Image sprite;
	private String type;
	private String team;

	/**
	 * Constructor
	 * 
	 * @throws IOException
	 */
	public PlayerTexture(String type, String team) {
		this.type = type;
		this.team = team;
		init();
		try {
			originalImage = ImageIO.read(new File("Textures/Characters/" + type
					+ "_" + team + "_" + "Frames" + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Rotates an image
	 * 
	 * @param degree
	 *            The degree of rotation
	 * @param image
	 *            The image to be rotated
	 * @return The rotated image to be drawn
	 */
	private void init() {
		sprite = new ImageIcon("Textures/Characters/" + type + "_" + team
				+ ".png").getImage();
	}

	/**
	 * Rotates an image
	 * 
	 * @param degree
	 *            The degrees to rotate
	 * @param image
	 *            The image to rotate
	 * @return The rotated image
	 */
	public BufferedImage rotateImage(int degree, Image image) {
		ImageIcon icon = new ImageIcon(image);
		BufferedImage bi = new BufferedImage(icon.getIconWidth(),
				icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		g2.rotate(Math.toRadians(degree), icon.getIconWidth() / 2,
				icon.getIconHeight() / 2);
		g2.drawImage(image, 0, 0, null);
		return bi;
	}

	/**
	 * Method to get the animation for the image by getting the frames for the
	 * animation
	 * 
	 * @return
	 * @throws IOException
	 */
	public BufferedImage getAnimatedImage(int count, int direction) {
		image = originalImage.getSubimage((count / 10) * 32, 0, 32, 32);
		switch (direction) {
		case 1:
			image = rotateImage(270, image);
			break;
		case 3:
			image = rotateImage(90, image);
			break;
		case 4:
			image = rotateImage(180, image);
			break;
		case 2:
			image = rotateImage(0, image);
			break;
		}
		return image;
	}

	/**
	 * gets the sprite based on the current facing direction
	 * 
	 * @param direction
	 * @return rotated sprite image
	 */
	public Image getSprite(int direction) {
		init();
		switch (direction) {
		case 1:
			sprite = rotateImage(270, sprite);
			break;
		case 3:
			sprite = rotateImage(90, sprite);
			break;
		case 4:
			sprite = rotateImage(180, sprite);
			break;
		case 2:
			sprite = rotateImage(0, sprite);
			break;
		}
		return sprite;
	}
}
