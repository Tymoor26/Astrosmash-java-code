package client;

/**
 * @author Tymoor Rahman
 * @author Francis Berti
 */
public class Weapon {

	private String primary;
	private String secondary;

	/**
	 * Sets the primary and secondary weapons based on chosen character class
	 * 
	 * @param type
	 */
	public Weapon(String type) {
		switch (type) {
		case "Assault":
			this.primary = "Assault Rifle";
			this.secondary = "Grenade";
			break;
		case "Sniper":
			this.primary = "Sniper Rifle";
			this.secondary = "Mine";
			break;
		case "Shotgunner":
			this.primary = "Shotgun";
			this.secondary = "Flamethrower";
			break;
		case "Heavy":
			this.primary = "Rocket Launcher";
			this.secondary = "Pistol";
			break;
		default:
			this.primary = "ERROR";
			this.secondary = "ERROR";
			System.out.println("ERROR: INVALID INPUT");
			break;
		}
	}

	/**
	 * gets primary weapon
	 * 
	 * @return primary weapon
	 */
	public String getPrimary() {
		return primary;
	}

	/**
	 * gets secondary weapon
	 * 
	 * @return secondary weapon
	 */
	public String getSecondary() {
		return secondary;
	}

	/**
	 * gets the ammo count for the primary weapon
	 * 
	 * @return ammo count for primary weapon
	 */
	public int getPrimaryAmmoCount() {
		switch (primary) {
		case "Assault Rifle":
			return 20;
		case "Sniper Rifle":
			return 5;
		case "Shotgun":
			return 6;
		case "Rocket Launcher":
			return 1;
		default:
			return 50;
		}
	}

	/**
	 * gets the ammo count for the secondary weapon
	 * 
	 * @return the ammo count for secondary weapon
	 */
	public int getSecondaryAmmoCount() {
		switch (secondary) {
		case "Grenade":
			return 1;
		case "Mine":
			return 2;
		case "Flamethrower":
			return 30;
		case "Pistol":
			return 10;
		default:
			return 50;
		}
	}

	/**
	 * get the shot rate of the currently equipped weapon
	 * 
	 * @return shot rate of primary weapon
	 */
	public int getShotRatePrimary() {
		switch (primary) {
		case "Assault Rifle":
			return 20;
		case "Sniper Rifle":
			return 100;
		case "Shotgun":
			return 50;
		case "Rocket Launcher":
			return 100;
		default:
			return 20;
		}
	}

	/**
	 * get the shot rate of the secondary weapon
	 * 
	 * @return shot rate of primary weapon
	 */
	public int getShotRateSecondary() {
		switch (secondary) {
		case "Grenade":
			return 50;
		case "Mine":
			return 75;
		case "Flamethrower":
			return 3;
		case "Pistol":
			return 40;
		default:
			return 20;
		}
	}

	/**
	 * get the reload delay of the primary weapon
	 * 
	 * @return reload delay of primary weapon
	 */
	public int getReloadDelayPrimary() {
		switch (primary) {
		case "Assault Rifle":
			return 60;
		case "Sniper Rifle":
			return 100;
		case "Shotgun":
			return 70;
		case "Rocket Launcher":
			return 110;
		default:
			return 20;
		}
	}

	/**
	 * get the reload delay of the secondary weapon
	 * 
	 * @return reload delay of secondary weapon
	 */
	public int getReloadDelaySecondary() {
		switch (secondary) {
		case "Grenade":
			return 10;
		case "Mine":
			return 10;
		case "Flamethrower":
			return 120;
		case "Pistol":
			return 30;
		default:
			return 20;
		}
	}
}
