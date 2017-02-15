package client;

import gui.Block;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Alexis Lowe
 * @author Thomas Sammons
 * @author Francis Berti
 */
public class OtherPlayers {
	private int xPos, yPos;
	private String team;
	private String type;
	private String username;
	private int direction;
	private int health;
	private int counterimage;
	private PlayerTexture texture;
	private BufferedImage rot;
	private int speed;
	private boolean dead;

	/**
	 * Creates a OtherPlayer from a Shared Player
	 * 
	 * @param player
	 */
	public OtherPlayers(shared.Player player) {
		this.xPos = player.xPos;
		this.yPos = player.yPos;
		this.team = player.team;
		this.type = player.type;
		this.direction = player.direction;
		this.health = player.health;
		this.counterimage = player.counterimage;
		this.username = player.username;
		this.speed = player.speed;
		this.dead = player.dead;
		rot = null;
		texture = new PlayerTexture(this.type, this.team);
	}

	/**
	 * Get dead
	 * 
	 * @return dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Updates from a Shared Player
	 * 
	 * @param player
	 *            Shared player
	 */
	public void setUpdate(shared.Player player) {
		this.xPos = player.xPos;
		this.yPos = player.yPos;
		this.health = player.health;
		this.direction = player.direction;
		this.counterimage = player.counterimage;
		this.speed = player.speed;
		this.dead = player.dead;
	}

	/**
	 * Get Health
	 * 
	 * @return health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Get Speed
	 * 
	 * @return speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Get Username
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Get X position
	 * 
	 * @return xPos
	 */
	public int getxPos() {
		return xPos;
	}

	/**
	 * Get Y position
	 * 
	 * @return yPos
	 */
	public int getyPos() {
		return yPos;
	}

	/**
	 * Get Direction
	 * 
	 * @return direction
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Get Counter Image
	 * 
	 * @return counterimage
	 */
	public int getCounterImage() {
		return counterimage;
	}

	/**
	 * Renders the OtherPlayer
	 * 
	 * @param g
	 *            The Graphics to draw
	 * @param player
	 *            Current Player
	 */
	public void render(Graphics g, Player player) {
		rot = texture.getAnimatedImage(counterimage, direction);
		int mapX = xPos - player.xOffset + Block.blockSize;
		int mapY = yPos - player.yOffset + Block.blockSize;
		g.drawImage(rot, -1 * mapX, -1 * mapY, Block.blockSize * 2,
				Block.blockSize * 2, null);

	}
}
