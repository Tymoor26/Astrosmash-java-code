package client;

import gui.Block;
import gui.GameGui;
import gui.HUD;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author Thomas Sammons
 * @author Alexis Lowe
 * @author Tymoor Rahman
 * @author Ahmed Qureshi
 * @author Francis Berti
 */
public class Player {
	public boolean left, right, up, down;
	public boolean shotup, shotdn, shotlft, shotrgt;
	public int xOffset = 0;
	public int yOffset = 0;
	private int currSpeed;
	private int classSpeed;
	private int spawnX;
	private int spawnY;
	private int monitorScreenCenterX;
	private int monitorScreenCenterY;
	private int topLeftOfScreenX;
	private int topLeftOfScreenY;
	private String type;
	private String team;
	private Image spritesheet;
	private int bullets;
	private GameGui game;
	private int direction;// 1 = up, 3 = down, 4 = left , 2 = right
	private int playerHealth;
	private int playerArmour;
	private Weapon weapon;
	private String primary;
	private String secondary;
	private String currentEquipped;
	private int primaryAmmoCap;
	private int secondaryAmmoCap;
	private int primaryAmmoCurr;
	private int secondaryAmmoCurr;
	private int currentEquippedAmmo;
	private int primaryShotRate;
	private int secondaryShotRate;
	private int currentEquippedShotRate;
	private int primaryReloadDelay;
	private int secondaryReloadDelay;
	private int currentEquippedReloadDelay;
	private String username;
	private int shotDelayCounter;
	private int reloadCounter;
	private boolean reloading;
	private String killedBy = "";
	private PlayerTexture texture;
	private int imageCounter;
	private Items item;
	private boolean dead;

	/**
	 * Constructor of the player. Takes in the character class, team colour and
	 * username. This will set up everything based on inputs.
	 * 
	 * @param type
	 * @param team
	 * @param username
	 */
	public Player(String type, String team, String username) {
		this.type = type;
		this.team = team;
		this.bullets = 100;
		texture = new PlayerTexture(type, team); // set the animation based on
													// character class
		setStats(this.type); // set the speed and armour
		currSpeed = classSpeed; // currspeed changes when a speed boost is
								// picked up
		weapon = new Weapon(type); // set the object of type Weapon to select
									// primary and secondary weapons based on
									// character class
		primary = weapon.getPrimary(); // set primary weapon
		secondary = weapon.getSecondary(); // set secondary weapon
		currentEquipped = primary; // set primary weapon as the currently
									// equipped weapon by default
		primaryAmmoCap = weapon.getPrimaryAmmoCount(); // initialise ammo counts
														// for both primary
		secondaryAmmoCap = weapon.getSecondaryAmmoCount(); // and secondary
															// weapons
		primaryShotRate = weapon.getShotRatePrimary(); // initialise shot rate
														// for both primary
		secondaryShotRate = weapon.getShotRateSecondary();// and secondary
															// weapons
		primaryReloadDelay = weapon.getReloadDelayPrimary(); // set reload delay
																// for both
																// primary
		secondaryReloadDelay = weapon.getReloadDelaySecondary(); // and
																	// secondary
																	// weapons
		primaryAmmoCurr = primaryAmmoCap; // initialise currentprimary
		secondaryAmmoCurr = secondaryAmmoCap;// and secondary ammo counts
		currentEquippedAmmo = primaryAmmoCap; // set the ammo count
		currentEquippedShotRate = primaryShotRate;// and shot rate
		currentEquippedReloadDelay = primaryReloadDelay; // and reload delay
															// stats of
															// currently
															// equipped weapon,
															// which is the
															// primary weapon.
		shotDelayCounter = primaryShotRate; // set the shot rate counter to the
											// currently equipped shot rate
											// counter
		reloadCounter = currentEquippedReloadDelay + 1; // initialise the reload
														// counter
		playerHealth = 100; // initialise player health
		reloading = false; // set reloading boolean to false
		Random gen = new Random();
		this.username = username;
		HUD.setAmmo("" + currentEquippedAmmo); // set up HUD with various data.
		HUD.setArmour(playerArmour);
		HUD.setHealth(playerHealth);
		HUD.setWeapon(currentEquipped);
		imageCounter = 0; // set image counter so it starts with player sprite
							// in default position
		init(); // initialises sprite
	}

	/**
	 * get the current image counter
	 * 
	 * @return imageCounter
	 */
	public int getCounterimage() {
		return imageCounter;
	}

	/**
	 * get the current equipped weapon
	 * 
	 * @return currentEquipped
	 */
	public String getCurrentEquipped() {
		return currentEquipped;
	}

	/**
	 * get the x offset of the player
	 * 
	 * @return xOffset
	 */
	public int getxOffset() {
		return xOffset;
	}

	/**
	 * get the y offset of the player
	 * 
	 * @return yOffset
	 */
	public int getyOffset() {
		return yOffset;
	}

	/**
	 * return whether or not the player is dead based on the dead variable
	 * 
	 * @return dead
	 */
	public boolean isDead() {

		return dead;
	}

	/**
	 * set the dead variable.
	 * 
	 * @param dead
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}

	/**
	 * update the player if weapon is a mine then the mine stays on the map and
	 * isn't deleted from the array until someone steps on it.
	 *
	 * @param game
	 *            The game being played
	 */
	public void update(GameGui game) {
		if (playerArmour > 0 && playerHealth < 100) {
			int diff = 100 - playerHealth;
			playerArmour -= diff;
			playerHealth = 100;
			if (playerArmour < 0) {
				playerHealth += playerArmour;
				playerArmour = 0;
			}
		}

		HUD.setHealth(playerHealth);
		HUD.setArmour(playerArmour);
		this.game = game;
		monitorScreenCenterX = (game.getWidth() / 2) - Block.blockSize;
		monitorScreenCenterY = (game.getHeight() / 2) - Block.blockSize;
		moveMap();
		shotDelayCounter++;

		if (reloading) {
			reloadCounter++;
		}

		if (reloadCounter == currentEquippedReloadDelay) {
			reloading = false;
			resetAmmo(currentEquipped);
			HUD.setAmmo("" + currentEquippedAmmo);
			reloadCounter++;
		}

		// System.out.println(getposX());

	}

	/**
	 * Get the team colour that the player is representing
	 * 
	 * @return team
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * get the players current health
	 * 
	 * @return playerHealth
	 */
	public int getPlayerHealth() {
		return playerHealth;
	}

	/**
	 * set the players current health
	 * 
	 * @param playerHealth
	 */
	public void setPlayerHealth(int playerHealth) {
		this.playerHealth = playerHealth;
	}

	/**
	 * get the players current speed
	 * 
	 * @return currSpeed
	 */
	public int getPlayerCurrSpeed() {
		return currSpeed;
	}

	/**
	 * set the players current speed
	 * 
	 * @param playerSpeed
	 */
	public void setPlayerCurrSpeed(int playerSpeed) {
		this.currSpeed = playerSpeed;
	}

	/**
	 * get the players character class speed
	 * 
	 * @return classSpeed
	 */
	public int getPlayerClassSpeed() {
		return classSpeed;
	}

	/**
	 * get the players current armour value
	 * 
	 * @return playerArmour
	 */
	public int getPlayerArmour() {
		return playerArmour;
	}

	/**
	 * set the players current armour value
	 * 
	 * @param playerArmour
	 */
	public void setPlayerArmour(int playerArmour) {
		this.playerArmour = playerArmour;
	}

	/**
	 * get the players current equipped weapon ammo count
	 * 
	 * @return
	 */
	public int getCurrentEquippedAmmo() {
		return currentEquippedAmmo;
	}

	/**
	 * Gets the y position of the player
	 *
	 * @return The y position of the player
	 */
	public int getposY() {
		return topLeftOfScreenY - monitorScreenCenterY - Block.blockSize;
	}

	/**
	 * get the players username
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * get the players character class
	 * 
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * get the character class of player
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * get the current facing direction of the player
	 * 
	 * @return direction (1 = up, 3 = down, 4 = left , 2 = right)
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * get the x position of the spawn point
	 * 
	 * @return spawnX
	 */
	public int getSpawnX() {
		return spawnX;
	}

	/**
	 * get the y position of the spawn point
	 * 
	 * @return spawnY
	 */
	public int getSpawnY() {
		return spawnY;
	}

	/**
	 * Sets the spawn location of the player
	 *
	 * @param x
	 *            The xCoord of the spawn
	 * @param y
	 *            The yCoord of the spawn
	 */
	public void setSpawn(int x, int y) {
		this.spawnX = x;
		this.spawnY = y;
		// System.out.println("XSPAWN: "+ spawnX + " YSPAWN: "+spawnY);
		xOffset = x;
		yOffset = y;

		this.topLeftOfScreenX += xOffset;
		this.topLeftOfScreenY += yOffset;
	}

	/**
	 * creates object of Bullet type and it to the list with different direction
	 * according to the boolean from keypressed. also decrease ammo count of
	 * current equipped weapon param clientNet
	 */
	public void makeBullet(ClientNet clientNet) {
		if (bullets > -1 && shotDelayCounter > currentEquippedShotRate
				&& currentEquippedAmmo > 0 && !reloading && playerHealth > 0) {
			if (shotup) {
				shared.Bullet bullet = new shared.Bullet();
				bullet.xPos = getposX();
				bullet.yPos = getposY();
				bullet.weapon = currentEquipped;
				bullet.direction = 1;
				bullet.owner = username;
				bullet.team = team;

				clientNet.sendUpdateb(bullet);

				shotDelayCounter = 0;
				this.direction = 1;
			}
			if (shotdn) {
				shared.Bullet bullet = new shared.Bullet();
				bullet.xPos = getposX();
				bullet.yPos = getposY();
				bullet.direction = 3;
				bullet.weapon = currentEquipped;
				bullet.owner = username;
				bullet.team = team;

				clientNet.sendUpdateb(bullet);

				shotDelayCounter = 0;
				this.direction = 3;
			}
			if (shotlft) {
				shared.Bullet bullet = new shared.Bullet();
				bullet.xPos = getposX();
				bullet.yPos = getposY();
				bullet.direction = 4;
				bullet.weapon = currentEquipped;
				bullet.owner = username;
				bullet.team = team;

				clientNet.sendUpdateb(bullet);

				shotDelayCounter = 0;
				this.direction = 4;
			}
			if (shotrgt) {
				shared.Bullet bullet = new shared.Bullet();
				bullet.xPos = getposX();
				bullet.yPos = getposY();
				bullet.direction = 2;
				bullet.weapon = currentEquipped;
				bullet.owner = username;
				bullet.team = team;

				clientNet.sendUpdateb(bullet);

				shotDelayCounter = 0;
				this.direction = 2;
			}
			if (currentEquipped.equals(primary)) {
				primaryAmmoCurr--;
				currentEquippedAmmo--;
				// System.out.println(primaryAmmoCurr);
			} else {
				secondaryAmmoCurr--;
				currentEquippedAmmo--;
				// System.out.println(secondaryAmmoCurr);
			}
			HUD.setAmmo("" + currentEquippedAmmo);
		} else if (shotDelayCounter > currentEquippedShotRate) {
			// System.out.println("RELOAD WEAPON NOW!!!!");
			HUD.setAmmo("" + "RELOAD NOW!!!");
		}
	}

	/**
	 * Gets the x position of the player
	 *
	 * @return The x position of the player
	 */
	public int getposX() {
		return topLeftOfScreenX - monitorScreenCenterX - Block.blockSize;
	}

	/**
	 * This method takes in the players current moving direction (based on the
	 * keys they are pressing) and the speed they are running at (which will be
	 * different for each character class) and depending on the direction, it
	 * will check for every pixel from its current position to the position it
	 * will go to, whether or not there is a wall. This is done so that 1. The
	 * player can't move through walls and 2. The player doesn't move into a
	 * wall and possibly get stuck inside it.
	 *
	 * @param direction
	 * @param speed
	 * @return true or false depending on if the player will collide with wall.
	 */
	public Boolean collisionToWalls(Boolean direction, int speed) {
		if (direction == this.left) {
			for (int i = 1; i <= speed; i++) {
				int newX = (getposX() + speed) / Block.blockSize; // Since
																	// everything
																	// is in a
																	// grid
																	// format
																	// the
																	// /Block.blocksize
				int newY = getposY() / Block.blockSize; // is done in order to
														// represent the double
														// array grid.
				if (game.getImportedMap().getBlockID(newX * -1, newY * -1) == 1
						|| (team.equals("Red") && (game.getImportedMap()
								.getBlockID(newX, newY) == 7 || game
								.getImportedMap().getBlockID(newX, newY) == 6))
						| (team.equals("Blue") && (game.getImportedMap()
								.getBlockID(newX, newY) == 5 || game
								.getImportedMap().getBlockID(newX, newY) == 4))) { // realistically
																					// you
																					// can
																					// have
																					// a
																					// situation
																					// where
					return true; // you dont move enough to go to a new grid
									// location, which results in no collisions.
				}
			}
		}
		if (direction == this.right) { // All if statements work the same way,
										// just changing the values of newX and
			for (int i = 1; i <= speed; i++) { // newY to accommodate for the
												// direction.
				int newX = (getposX() - speed) / Block.blockSize;
				int newY = getposY() / Block.blockSize;
				if (game.getImportedMap().getBlockID(newX * -1, newY * -1) == 1
						|| (team.equals("Red") && (game.getImportedMap()
								.getBlockID(newX, newY) == 7 || game
								.getImportedMap().getBlockID(newX, newY) == 6))
						| (team.equals("Blue") && (game.getImportedMap()
								.getBlockID(newX, newY) == 5 || game
								.getImportedMap().getBlockID(newX, newY) == 4))) {
					return true;
				}
			}
		}
		if (direction == this.up) {
			for (int i = 1; i <= speed; i++) {
				int newX = getposX() / Block.blockSize;
				int newY = (getposY() + speed) / Block.blockSize;
				if (game.getImportedMap().getBlockID(newX * -1, newY * -1) == 1
						|| (team.equals("Red") && (game.getImportedMap()
								.getBlockID(newX, newY) == 7 || game
								.getImportedMap().getBlockID(newX, newY) == 6))
						| (team.equals("Blue") && (game.getImportedMap()
								.getBlockID(newX, newY) == 5 || game
								.getImportedMap().getBlockID(newX, newY) == 4))) {
					return true;
				}
			}
		}
		if (direction == this.down) {
			for (int i = 1; i <= speed; i++) {
				int newX = getposX() / Block.blockSize;
				int newY = (getposY() - speed) / Block.blockSize;
				if (game.getImportedMap().getBlockID(newX * -1, newY * -1) == 1
						|| (team.equals("Red") && (game.getImportedMap()
								.getBlockID(newX, newY) == 7 || game
								.getImportedMap().getBlockID(newX, newY) == 6))
						| (team.equals("Blue") && (game.getImportedMap()
								.getBlockID(newX, newY) == 5 || game
								.getImportedMap().getBlockID(newX, newY) == 4))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * change the weapon from primary to secondary and vice versa as well as the
	 * values of the variables that start with currentEquipped
	 */
	public void changeWeapon() {
		if (currentEquipped.equals(primary)) {
			currentEquipped = secondary;
			currentEquippedAmmo = secondaryAmmoCurr;
			currentEquippedShotRate = secondaryShotRate;
			currentEquippedReloadDelay = secondaryReloadDelay;
			reloadCounter = currentEquippedReloadDelay + 1;
			reloading = false;
		} else {
			currentEquipped = primary;
			currentEquippedAmmo = primaryAmmoCurr;
			currentEquippedShotRate = primaryShotRate;
			currentEquippedReloadDelay = primaryReloadDelay;
			reloadCounter = currentEquippedReloadDelay + 1;
			reloading = false;
		}
		HUD.setWeapon(currentEquipped);
		HUD.setAmmo("" + currentEquippedAmmo);
	}

	/**
	 * reset the ammo count of currently equipped weapon
	 */
	public void reload() {
		if (reloadCounter > currentEquippedReloadDelay && !checkIfAmmoAtMax()) {
			reloadCounter = 0;
			reloading = true;
			HUD.setAmmo("" + "RELOADING!");
		}
	}

	/**
	 * checks if the ammo of the current equipped weapon is at max so that if
	 * the user pressed reload at this point, the reload doesnt happen
	 * 
	 * @return whether or not the ammo is at max
	 */
	public boolean checkIfAmmoAtMax() {
		if (currentEquipped.equals(primary)) {
			return primaryAmmoCurr == primaryAmmoCap;
		} else {
			return secondaryAmmoCurr == secondaryAmmoCap;
		}
	}

	/**
	 * After reload delay is done, reset the ammo counter of current weapon
	 *
	 * @param currentEquipped
	 */
	public void resetAmmo(String currentEquipped) {
		switch (currentEquipped) {
		case "Assault Rifle":
			primaryAmmoCurr = 20;
			currentEquippedAmmo = primaryAmmoCurr;
			break;
		case "Shotgun":
			primaryAmmoCurr = 6;
			currentEquippedAmmo = primaryAmmoCurr;
			break;
		case "Sniper Rifle":
			primaryAmmoCurr = 5;
			currentEquippedAmmo = primaryAmmoCurr;
			break;
		case "Rocket Launcher":
			primaryAmmoCurr = 1;
			currentEquippedAmmo = primaryAmmoCurr;
			break;
		case "Grenade":
			secondaryAmmoCurr = 1;
			currentEquippedAmmo = secondaryAmmoCurr;
			break;
		case "Mine":
			secondaryAmmoCurr = 2;
			currentEquippedAmmo = secondaryAmmoCurr;
			break;
		case "Flamethrower":
			secondaryAmmoCurr = 30;
			currentEquippedAmmo = secondaryAmmoCurr;
			break;
		case "Pistol":
			secondaryAmmoCurr = 10;
			currentEquippedAmmo = secondaryAmmoCurr;
			break;
		default:
			primaryAmmoCurr = 20;
			secondaryAmmoCurr = 1;
			currentEquippedAmmo = primaryAmmoCurr;
			break;
		}
	}

	/**
	 * Moves the player based on which key is being pressed.
	 */
	private void moveMap() {
		if (left && !collisionToWalls(left, currSpeed) && (getposX() * -1) > 0) {
			xOffset += currSpeed;
			topLeftOfScreenX = xOffset;
			this.direction = 4;
		}
		if (right && !collisionToWalls(right, currSpeed)
				&& (getposX() * -1) < (game.mapXSize * Block.blockSize) - 5) {
			xOffset -= currSpeed;
			this.direction = 2;
			topLeftOfScreenX = xOffset;

		}
		if (up && !collisionToWalls(up, currSpeed) && (getposY() * -1) > 0) {
			yOffset += currSpeed;
			this.direction = 1;
			topLeftOfScreenY = yOffset;

		}
		if (down && !collisionToWalls(down, currSpeed)
				&& (getposY() * -1) < (game.mapYSize * Block.blockSize) - 5) {
			yOffset -= currSpeed;
			this.direction = 3;
			topLeftOfScreenY = yOffset;

		}

	}

	/**
	 * Render the player
	 *
	 * @param g
	 *            The graphics to render
	 */
	public void render(Graphics g) {
		// drawing the bullets method
		// drawing all the method using a loop
		BufferedImage rot = null;
		if (left == true || right == true || up == true || down == true) {
			rot = texture.getAnimatedImage(imageCounter, direction);
			imageCounter++;
			if (imageCounter > 110) {
				imageCounter = 0;
			}
		}
		if (left == false && right == false && up == false && down == false) {
			imageCounter = 0;
			rot = texture.getAnimatedImage(imageCounter, direction);
		}

		g.drawImage(rot, monitorScreenCenterX, monitorScreenCenterY,
				Block.blockSize * 2, Block.blockSize * 2, null);

	}

	/**
	 * set the classSpeed and the playerArmour variables based on the character
	 * class of the player
	 * 
	 * @param type
	 */
	public void setStats(String type) {
		switch (type) {
		case "Assault":
			classSpeed = 2;
			playerArmour = 50;
			break;
		case "Sniper":
			classSpeed = 2;
			playerArmour = 25;
			break;
		case "Shotgunner":
			classSpeed = 3;
			playerArmour = 75;
			break;
		case "Heavy":
			classSpeed = 1;
			playerArmour = 100;
			break;
		default:
			classSpeed = 2;
			playerArmour = 50;
			break;
		}
	}

	/**
	 * Initialise the player by setting the sprite image.
	 */
	private void init() {
		spritesheet = new ImageIcon("Textures/Characters/" + type + "_" + team
				+ ".png").getImage();
	}
}
