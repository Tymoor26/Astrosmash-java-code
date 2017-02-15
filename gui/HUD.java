package gui;

import client.Player;
import javax.swing.*;
import java.awt.*;

/**
 * @author Ahmed Qureshi
 */
public class HUD extends JPanel {
	private static final long serialVersionUID = 1L;
	static JLabel healthLabel = new JLabel("Health:");
	static JLabel ammoLabel = new JLabel("Ammo:");
	static JLabel armourLabel = new JLabel("Armour: ");
	static JLabel weaponLabel = new JLabel("Weapon: ");
	JLabel nullLabel = new JLabel();
	static JLabel currentScore = new JLabel("");
	static JLabel powerUpLabel = new JLabel("Power up");
	private Player player;

	/**
	 * Creates JPanel to be put on the Game Frame. Contains information about
	 * the user's player: health, ammo, armour, weapon, power up and score
	 * 
	 * @param player
	 */
	public HUD(Player player) {
		super();
		this.player = player;
		init();
	}

	/**
	 * Sets the ammo information
	 * 
	 * @param ammo
	 *            the latest ammo of the current equipped gun
	 */
	public static void setAmmo(String ammo) {
		ammoLabel.setText("Ammo: " + ammo);
		ammoLabel.repaint();
	}

	/**
	 * Sets the weapon information
	 * 
	 * @param weapon
	 *            the latest equipped gun
	 */
	public static void setWeapon(String weapon) {
		weaponLabel.setText("Weapon: " + weapon);
		weaponLabel.repaint();
	}

	/**
	 * Sets the health information
	 * 
	 * @param health
	 *            the latest health
	 */
	public static void setHealth(int health) {
		healthLabel.setText("Health: " + health);
		healthLabel.repaint();
	}

	/**
	 * Sets the armour information
	 * 
	 * @param armour
	 *            the latest armour informaiton
	 */
	public static void setArmour(int armour) {
		armourLabel.setText("Armour: " + armour);
		armourLabel.repaint();
	}

	/**
	 * 
	 * @param powerUp
	 */
	public static void setPowerUp(String powerUp) {
		powerUpLabel.setText(powerUp);
		powerUpLabel.repaint();
	}

	/**
	 * Sets the score information
	 * 
	 * @param score
	 *            the latest score - changed when a death will occur
	 */
	public static void setScore(String score) {
		currentScore.setText(score);
	}

	/**
	 * Places all the labels on the JPanel in the beginning
	 */
	private void init() {
		GridLayout layout = new GridLayout(2, 3);
		this.setLayout(layout);
		healthLabel.setText("Health: " + player.getPlayerHealth());
		ammoLabel.setText("Ammo: " + player.getCurrentEquippedAmmo());
		weaponLabel.setText("Weapon: " + player.getCurrentEquipped());
		armourLabel.setText("Armour: " + player.getPlayerArmour());
		powerUpLabel.setText("");
		add(healthLabel);
		add(weaponLabel);
		add(powerUpLabel);
		add(armourLabel);
		add(ammoLabel);
		add(currentScore);
	}
}
