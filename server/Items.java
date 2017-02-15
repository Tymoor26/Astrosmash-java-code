package server;

/**
 * @author Alexis Lowe
 * @author Tymoor Rahman.
 */
public class Items {
	private int x, y;
	private String type;
	private int id;

	/**
	 * Creates a Item
	 * 
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @param type
	 *            String
	 * @param id
	 *            int
	 */
	public Items(int x, int y, String type, int id) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.type = type;
	}

	/**
	 * Checks if a player has pick up the item
	 *
	 * @param player
	 * @return 1 or 2 depending on type of item that collided or 0 if no pickup
	 */
	public int collisionWithPlayer(Player player) {
		if ((type.equals("Health") && player.getHealth() < 100)
				|| (type.equals("Speed"))) {
			int itemX = x * -1;
			int itemY = y * -1;
			int pX = player.getxPos() * -1;
			int pY = player.getyPos() * -1;
			int leftMostX = pX - 16;
			int rightMostX = pX + 16;
			int upMostY = pY - 16;
			int downMostY = pY + 16;
			for (int x = leftMostX; x < rightMostX; x++) {
				for (int y = upMostY; y < downMostY; y++) {
					if (x == itemX && y == itemY) {
						if (type.equals("Health")) {
							if ((player.getHealth() + 10) > 100) {
								player.setHealth(100);
							} else {
								player.setHealth(player.getHealth() + 10);
							}
							return 1;
						} else {
							player.setSpeed(player.getSpeed() + 1);
							return 2;
						}
					}
				}
			}
		}
		return 0;
	}

	/**
	 * Get Id
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id
	 * 
	 * @param id
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Get X
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Set X
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Get Y
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set Y
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Get the type of Item
	 * 
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of the item
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
}
