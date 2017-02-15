package client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Thomas Sammons
 * @author Tymoor Rahman
 * @author Ahmed Qureshi
 */
public class InputHandler implements KeyListener {

	private Player player;
	private Boolean held = false;
	private ClientNet clientNet;

	// main timer delay

	/**
	 * Constructor
	 *
	 * @param player
	 */
	public InputHandler(Player player, ClientNet clientNet) {
		this.player = player;
		this.clientNet = clientNet;

	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * set boolean of which way player is facing and which way bullet is shot
	 * also checks if user is changing weapons or reloading and runs the
	 * specific function for when necessary keys are pressed. If keys are held,
	 * then held becomes true meaning it doesn't run the necessary function.
	 */
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (player.getPlayerHealth() > 0) {
			if (keyCode == KeyEvent.VK_A) {
				player.left = true;
			}
			if (keyCode == KeyEvent.VK_D) {
				player.right = true;
			}
			if (keyCode == KeyEvent.VK_W) {
				player.up = true;
			}
			if (keyCode == KeyEvent.VK_S) {
				player.down = true;
			}
			if (keyCode == KeyEvent.VK_UP) {
				player.shotup = true;
				player.shotdn = false;
				player.shotlft = false;
				player.shotrgt = false;
				player.makeBullet(clientNet);
			}
			if (keyCode == KeyEvent.VK_DOWN) {
				player.shotdn = true;
				player.shotup = false;
				player.shotlft = false;
				player.shotrgt = false;
				player.makeBullet(clientNet);
			}
			if (keyCode == KeyEvent.VK_LEFT) {
				player.shotlft = true;
				player.shotdn = false;
				player.shotrgt = false;
				player.shotup = false;
				player.makeBullet(clientNet);
			}
			if (keyCode == KeyEvent.VK_RIGHT) {
				player.shotrgt = true;
				player.shotup = false;
				player.shotlft = false;
				player.shotdn = false;
				player.makeBullet(clientNet);
			}
			if (keyCode == KeyEvent.VK_E) {
				if (!held) {
					player.changeWeapon();
				}
				held = true;
			}
			if (keyCode == KeyEvent.VK_R) {
				if (!held) {
					player.reload();
				}
				held = true;
			}
		}
	}

	/**
	 * set direction back to default after key is released and sets held back to
	 * false when necessary keys for changing weapon and reloading are released.
	 */
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_A) {
			player.left = false;
		}
		if (keyCode == KeyEvent.VK_D) {
			player.right = false;
		}
		if (keyCode == KeyEvent.VK_W) {
			player.up = false;
		}
		if (keyCode == KeyEvent.VK_S) {
			player.down = false;
		}
		if (keyCode == KeyEvent.VK_UP) {
			player.shotup = false;
		}
		if (keyCode == KeyEvent.VK_DOWN) {
			player.shotdn = false;
		}
		if (keyCode == KeyEvent.VK_LEFT) {
			player.shotlft = false;
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			player.shotrgt = false;
		}
		if (keyCode == KeyEvent.VK_E) {
			held = false;
		}
		if (keyCode == KeyEvent.VK_R) {
			held = false;
		}
	}

	/**
	 * Due to implementation, this needs to be here. It does nothing.
	 */
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

}
