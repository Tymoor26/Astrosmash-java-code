package client;

import gui.Block;

import java.awt.*;

/**
 * @author Thomas Sammons
 * @author Alexis Lowe
 * @author Tymoor Rahman
 * @author Ahmed Qureshi
 * @author Francis Berti
 */
public class Bullet {
	private String weapon;
	private int x, y;
	private String owner;
	private int direction;
	private String team;
	private int lifeSpan;

	/**
	 * Builds a bullet from shared.bullet
	 * 
	 * @param bullet
	 */
	public Bullet(shared.Bullet bullet) {
		weapon = bullet.weapon;
		x = bullet.xPos;
		y = bullet.yPos;
		lifeSpan = bullet.lifeSpan;
	}

	/**
	 * Get weapon that shot the bullet
	 * 
	 * @return weapon
	 */
	public String getWeapon() {
		return weapon;
	}

	/**
	 * Render bullet on screen. If bullet is an explosive and its time for it to
	 * explode, then render explosion.
	 * 
	 * @param g
	 * @param player
	 */
	public void render(Graphics g, Player player) {
		int xMove = -1 * (x - player.xOffset);
		int yMove = -1 * (y - player.yOffset);
		if (lifeSpan > 15) {
			switch (weapon) {
			case "Rocket Launcher":
				g.setColor(Color.WHITE);
				g.fillRect(xMove, yMove, 15, 15);
				break;
			case "Mine":
				g.setColor(Color.BLACK);
				g.fillRect(xMove, yMove, 9, 9);
				break;
			case "Flamethrower":
				g.setColor(Color.RED);
				g.fillRect(xMove, yMove, 30, 30);
				break;
			case "Shotgun":
				g.setColor(Color.YELLOW);
				g.fillRect(xMove, yMove, 10, 10);
				break;
			case "Sniper Rifle":
				g.setColor(Color.YELLOW);
				g.fillRect(xMove, yMove, 3, 3);
			default:
				g.setColor(Color.YELLOW);
				g.fillRect(xMove, yMove, 6, 6);
				break;
			}
		} else {
			int xpos;
			int ypos;
			int r;
			switch (weapon) {
			case "Rocket Launcher":
				g.setColor(Color.RED);
				r = Block.blockSize + 50;
				xpos = xMove - (r / 2);
				ypos = yMove - (r / 2);
				g.fillOval(xpos, ypos, r, r);
				break;
			case "Mine":
				g.setColor(Color.RED);
				r = Block.blockSize + 40;
				xpos = xMove - (r / 2);
				ypos = yMove - (r / 2);
				g.fillOval(xpos, ypos, r, r);
				break;
			case "Grenade":
				g.setColor(Color.RED);
				r = Block.blockSize + 30;
				xpos = xMove - (r / 2);
				ypos = yMove - (r / 2);
				g.fillOval(xpos, ypos, r, r);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Gets the x position of the Bullet
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y position of the Bullet
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}
}
