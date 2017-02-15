package server;

import gui.Block;
import shared.MapLoader;

/**
 * @author Alexis Lowe
 * @author Tymoor Rahman
 * @author Francis Berti
 */
public class Bullet {
	private int direction;
	private int xPos, yPos;
	private String weapon;
	private String owner;
	private int bulletSpeed;
	private int lifeSpan;
	private String team;
	private boolean isRocket = false;
	private boolean isGrenade = false;
	private boolean isMine = false;
	private boolean isFlame = false;
	private boolean isDead = false;
	private int damage = 0;
	private boolean exploding = false;
	private int explosionSpan;

	/**
	 * Constructor
	 * 
	 * @param direction
	 *            The direction shooting
	 * @param xPos
	 *            The x position
	 * @param yPos
	 *            The y position
	 * @param weapon
	 *            The weapon used
	 * @param owner
	 *            The player who shot it
	 * @param team
	 *            The team of the owner
	 */
	public Bullet(int direction, int xPos, int yPos, String weapon,
			String owner, String team) {
		this.direction = direction;
		this.xPos = xPos;
		this.yPos = yPos;
		this.weapon = weapon;
		this.owner = owner;
		this.team = team;
		switch (weapon) {
		case "Assault Rifle":
			explosionSpan = 0;
			bulletSpeed = 8;
			lifeSpan = 300;
			damage = 25; // previous 10 - needed buffing
			break;
		case "Shotgun":
			explosionSpan = 0;
			bulletSpeed = 10;
			lifeSpan = 40;
			damage = 35; // previous 25
			break;
		case "Sniper Rifle":
			explosionSpan = 0;
			bulletSpeed = 12;
			lifeSpan = 500;
			damage = 80; // previous 100 - needed nerfing
			break;
		case "Rocket Launcher":
			explosionSpan = 30;
			bulletSpeed = 6;
			isRocket = true;
			lifeSpan = 500;
			damage = 70;
			break;
		case "Grenade":
			explosionSpan = 20;
			bulletSpeed = 4;
			isGrenade = true;
			lifeSpan = 60;
			damage = 50;
			break;
		case "Mine":
			explosionSpan = 15;
			bulletSpeed = 3;
			isMine = true;
			lifeSpan = 40;
			damage = 60;
			break;
		case "Flamethrower":
			explosionSpan = 0;
			bulletSpeed = 5;
			isFlame = true;
			lifeSpan = 30;
			damage = 30;
			break;
		case "Pistol":
			explosionSpan = 0;
			bulletSpeed = 10;
			lifeSpan = 50;
			damage = 15; // previous 10
			break;
		default:
			explosionSpan = 0;
			bulletSpeed = 4;
			isRocket = false;
			lifeSpan = 300;
		}
	}

	/**
	 * Gets the life span of the bullet
	 * 
	 * @return The life span
	 */
	public int getLifeSpan() {
		return lifeSpan;
	}

	/**
	 * Sets the life span of the bullet
	 * 
	 * @param x
	 *            The value of the life span
	 */
	public void setLifeSpan(int x) {
		lifeSpan = x;
	}

	/**
	 * Move the bullet
	 * 
	 * @param map
	 *            The map moving on
	 */
	public void moveBullet(MapLoader map) {
		switch (direction) {
		case 1: // up
			if (!bulletDead()) {
				if (lifeSpan > 20 || (lifeSpan > 15 && (!isGrenade && !isMine))) {
					this.yPos += bulletSpeed;
				}
			}
			break;
		case 2: // right
			if (!bulletDead()) {
				if (lifeSpan > 20 || (lifeSpan > 15 && (!isGrenade && !isMine))) {
					this.xPos -= bulletSpeed;
				}
			}
			break;
		case 3: // down
			if (!bulletDead()) {
				if (lifeSpan > 20 || (lifeSpan > 15 && (!isGrenade && !isMine))) {
					this.yPos -= bulletSpeed;
				}
			}
			break;
		case 4: // left
			if (!bulletDead()) {
				if (lifeSpan > 20 || (lifeSpan > 15 && (!isGrenade && !isMine))) {
					this.xPos += bulletSpeed;
				}
			}
			break;
		default:
			System.out.println("No Bullets");
			break;
		}

		if (isMine) {
			if (lifeSpan > 20) {
				lifeSpan--;
			}
		} else if (isGrenade) {
			if (lifeSpan > 15) {
				lifeSpan--;
			} else {
				exploding = true;
			}
		} else {
			lifeSpan--;
		}

		if (this.bulletCollisionToWalls(map)) {
			if (isExplosive() && !exploding && !isMine) {
				lifeSpan = 10;
				exploding = true;
			} else if (!exploding && !isMine) {
				lifeSpan = 0;
			}
		}
	}

	/**
	 * Check to see if the bullet is dead
	 * 
	 * @return if the bullet is dead
	 */
	public boolean bulletDead() {
		if (lifeSpan <= 0) {
			isDead = true;
			return true;
		} else {
			isDead = false;
			return false;
		}
	}

	/**
	 * Check to see if the bullet has hit a wall
	 * 
	 * @param bullet
	 * @return boolean if bullet is on wall texture or out of map
	 */
	public boolean bulletCollisionToWalls(MapLoader map) {
		int x = -1 * getxPos();
		int y = -1 * getyPos();
		int newX = x / Block.blockSize;
		int newY = y / Block.blockSize;
		if (map.getBlockID(newX, newY) == 1
				|| (team.equals("Red") && (map.getBlockID(newX, newY) == 7 || map
						.getBlockID(newX, newY) == 6))
				| (team.equals("Blue") && (map.getBlockID(newX, newY) == 5 || map
						.getBlockID(newX, newY) == 4))
				|| (x > (map.xRange * Block.blockSize) - 5) || (x < 1)
				|| (y < 1) || (y > (map.yRange * Block.blockSize) - 5)) {
			return true;
		} else
			return false;
	}

	/**
	 * Check to see if the bullet has hit a player
	 * 
	 * @param bullet
	 * @param playerX
	 * @param playerY
	 * @return boolean if bullet has hit player
	 */
	public boolean bulletCollisionToPlayer(Player player) {
		if (!(team.equals(player.getTeam()))) {
			int bulletX = getxPos() * -1;
			int bulletY = getyPos() * -1;
			int pX = player.getxPos() * -1;
			int pY = player.getyPos() * -1;
			int leftMostX = pX - 16;
			int rightMostX = pX + 16;
			int upMostY = pY - 16;
			int downMostY = pY + 16;
			for (int x = leftMostX; x < rightMostX; x++) {
				for (int y = upMostY; y < downMostY; y++) {
					if (x == bulletX && y == bulletY) {
						player.setHealth(player.getHealth() - damage);
						if (isExplosive()) {
							exploding = true;
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Check to see if the bullet hits an Ai
	 * 
	 * @param bullet
	 * @param playerX
	 * @param playerY
	 * @return boolean if bullet has hit player
	 */
	public boolean bulletCollisionToAI(AI ai) {
		if (!(team.equals(ai.getTeam()))) {
			int bulletX = getxPos() * -1;
			int bulletY = getyPos() * -1;
			int pX = ai.getxPos() * -1;
			int pY = ai.getyPos() * -1;
			int leftMostX = pX - 16;
			int rightMostX = pX + 16;
			int upMostY = pY - 16;
			int downMostY = pY + 16;
			for (int x = leftMostX; x < rightMostX; x++) {
				for (int y = upMostY; y < downMostY; y++) {
					if (x == bulletX && y == bulletY) {
						ai.setHealth(ai.getHealth() - damage);
						if (isExplosive()) {
							exploding = true;
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Proximity of an explosion
	 * 
	 * @param player
	 */
	public void explosionProx(Player player) {
		int r;
		int x = player.getxPos() - xPos;
		int y = player.getyPos() - yPos;
		// System.out.println("player x: " + player.getxPos());
		// System.out.println("player y: " + player.getyPos());
		// System.out.println("bullet x: " + xPos);
		// System.out.println("bullet y: " + yPos);
		switch (weapon) {
		case "Rocket Launcher":
			r = 50;
			break;
		case "Grenade":
			r = 30;
			break;
		case "Mine":
			r = 40;
			break;
		default:
			x = 0;
			y = 0;
			r = 0;
			break;
		}
		if (x < 0) {
			x = x * -1;
		}
		if (y < 0) {
			y = y * -1;
		}
		// System.out.println("x:" + x);
		// System.out.println("y:" + y);
		// System.out.println("r: " + r);

		if (x < r && y < r) {
			// System.out.println("in");
			player.setHealth(player.getHealth() - damage);
		}

	}

	/**
	 * Proximity of an explosion in AI
	 * 
	 * @param ai
	 */
	public void explosionProxAI(AI ai) {
		int r;
		int x = ai.getxPos() - xPos;
		int y = ai.getyPos() - yPos;
		// System.out.println("player x: " + player.getxPos());
		// System.out.println("player y: " + player.getyPos());
		// System.out.println("bullet x: " + xPos);
		// System.out.println("bullet y: " + yPos);
		switch (weapon) {
		case "Rocket Launcher":
			r = 50;
			break;
		case "Grenade":
			r = 30;
			break;
		case "Mine":
			r = 40;
			break;
		default:
			x = 0;
			y = 0;
			r = 0;
			break;
		}
		if (x < 0) {
			x = x * -1;
		}
		if (y < 0) {
			y = y * -1;
		}
		// System.out.println("x:" + x);
		// System.out.println("y:" + y);
		// System.out.println("r: " + r);

		if (x < r && y < r) {
			// System.out.println("in");
			ai.setHealth(ai.getHealth() - damage);
		}

	}

	/**
	 * Gets the direction of the bullet
	 * 
	 * @return The direction
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Gets the x position
	 * 
	 * @return x
	 */
	public int getxPos() {
		return xPos;
	}

	/**
	 * Gets the y position
	 * 
	 * @return y
	 */
	public int getyPos() {
		return yPos;
	}

	/**
	 * Gets the weapon used
	 * 
	 * @return the weapon
	 */
	public String getWeapon() {
		return weapon;
	}

	/**
	 * Gets the owner of the bullet
	 * 
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Check to see if the bullet will explode
	 * 
	 * @return
	 */
	public boolean isExplosive() {
		return isMine || isGrenade || isRocket;
	}

	/**
	 * Checks to see if the bullet is exploding
	 * 
	 * @return
	 */
	public boolean isExploding() {
		return exploding;
	}

	/**
	 * Checks to see if bullet is a mine
	 * 
	 * @return
	 */
	public boolean isMine() {
		return isMine;
	}

	/**
	 * Gets the explosive radius
	 * 
	 * @return
	 */
	public int getExplosionSpan() {
		// TODO Auto-generated method stub
		return explosionSpan;
	}

	/**
	 * Sets the explosive radius
	 * 
	 * @param explosionSpan2
	 */
	public void setExplosionSpan(int explosionSpan2) {
		// TODO Auto-generated method stub
		explosionSpan = explosionSpan2;
	}
}
