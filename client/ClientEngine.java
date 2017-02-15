package client;

import gui.Block;
import gui.GameGui;
import gui.HUD;
import gui.ResultGui;
import shared.MapLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexis Lowe
 * @author Tymoor Rahman
 */
public class ClientEngine {
	private ConcurrentHashMap<String, OtherPlayers> playersHash;
	private ArrayList<Bullet> bulletsArray;
	private ConcurrentHashMap<Integer, Items> itemsHash;
	private Player player;
	private ClientNet clientNet;
	private MapLoader map;
	private Block[][] blockArray;
	private GameGui gui;
	private int speedBoostCounter = 0;
	private boolean gameover;
	private String winner;
	private int redScore;
	private int blueScore;

	/**
	 * Constructs the ClientEngine the client engine is in charge of connecting
	 * the received packets from the Server and the GameGui. It also handles the
	 * players positions and movement and sends these to the Server. It requires
	 * a ClientNet to send and receive the data from the Server.
	 * 
	 * @param clientNet
	 *            ClientNet for Sending and Receiving info.
	 * @param player
	 *            The player that is handled by the Client.
	 * @param gui
	 *            The gui to Render the changes caused by the ClientEngine.
	 */
	public ClientEngine(ClientNet clientNet, Player player, GameGui gui) {
		this.player = player;
		this.clientNet = clientNet;
		clientNet.initNetworkClient();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.map = new MapLoader(clientNet.getMapname());
		this.gui = gui;
		this.playersHash = new ConcurrentHashMap<>();
		this.bulletsArray = new ArrayList<>();
		this.itemsHash = new ConcurrentHashMap<>();
		init();

	}

	/**
	 * Initialise the game
	 */
	private void init() {
		blockArray = new Block[map.getXRange()][map.getYRange()];
		for (int y = 0; y < map.getYRange(); y++) {
			for (int x = 0; x < map.getXRange(); x++) {
				int blockID = map.getBlockID(x, y);
				blockArray[x][y] = new Block(x * Block.blockSize, y
						* Block.blockSize, gui, blockID, map, player);
				// set spawn location for players
				if (blockID == 8 && player.getTeam() == "Blue") {
					int x1 = -(x * Block.blockSize) + (gui.getWidth() / 2)
							- Block.blockSize / 2;
					int y1 = -(y * Block.blockSize) + (gui.getHeight() / 2)
							- Block.blockSize / 2;
					player.setSpawn(x1, y1);
				}
				if (blockID == 9 && player.getTeam() == "Red") {
					int x1 = -(x * Block.blockSize) + (gui.getWidth() / 2)
							- Block.blockSize / 2;
					int y1 = -(y * Block.blockSize) + (gui.getHeight() / 2)
							- Block.blockSize / 2;
					player.setSpawn(x1, y1);
				}
			}
		}
	}

	/**
	 * This is the main method that does all the work and calculations. It is
	 * called within the game loop and updates all of the data required for the
	 * game.
	 *
	 * @param gui
	 *            GameGui for map update.
	 */
	public void update(GameGui gui) {
		// This updates the map to make sure there is no changes.
		for (int y = 0; y < map.getYRange(); y++) {
			for (int x = 0; x < map.getXRange(); x++) {
				blockArray[x][y].update(gui);
			}
		}

		try {
			// This updates the players health and positions.
			updatePlayers();
			// This updates the bullets by getting them from the clientNet.
			updateBullets();
			// This updates the items by getting them from the clientNet.
			updateItems();
			// This updates the Currents Game State.
			updateGameState();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		// Checks if the player is dead and if yes respawns him.
		respawnPlayer();
		// Checks if the Current game has ended if yes respawns the player.
		endGame();
		// Runs the players update method and then send the current state of the
		// player to the Server.
		player.update(gui);
		sendPlayer();

	}

	/**
	 * Renders the game on the given canvas
	 * 
	 * @param g
	 */
	public void render(Graphics g) {
		renderMap(g);
		renderItems(g);
		renderBullets(g);
		renderOtherPlayers(g);
		player.render(g);
	}

	/**
	 * Checks the state of the game if it is GameOver then it respawns the
	 * player and show the winner using a ResultGui.
	 */
	private void endGame() {
		if (gameover) {
			player = new Player(player.getType(), player.getTeam(),
					player.getUsername());
			gui.getInputHandler().setPlayer(player);
			init();
			new ResultGui(blueScore, redScore, winner);
			gameover = false;
		}

	}

	/**
	 * Respawn the player if his health is below or equal to 0 and set him to
	 * dead.
	 */
	public void respawnPlayer() {
		if (player.getPlayerHealth() <= 0) {
			player = new Player(player.getType(), player.getTeam(),
					player.getUsername());
			if (!player.isDead()) {
				player.setDead(true);
			}
			gui.getInputHandler().setPlayer(player);
			init();
		}
	}

	/**
	 * Gets the latest GameState from clientNet
	 * 
	 * @throws Exception
	 */
	private void updateGameState() throws Exception {
		gameover = clientNet.isGameover();
		redScore = clientNet.getRedScore();
		blueScore = clientNet.getBlueScore();
		winner = clientNet.getWinner();
		HUD.setScore("Red Score: " + blueScore + " | Blue Score: " + redScore);
	}

	/**
	 * Gets the latest Items Hash from the clientNet
	 * 
	 * @throws Exception
	 */
	private void updateItems() throws Exception {
		itemsHash = clientNet.getItemsHash();
	}

	/**
	 * Gets the latest Bullets Array from the clientNet
	 * 
	 * @throws Exception
	 */
	private void updateBullets() throws Exception {
		bulletsArray = clientNet.getBulletArray();
	}

	/**
	 * Updates the current player with any new info gained from clientNet and
	 * gets the latest OtherPlayers Hash.
	 * 
	 * @throws Exception
	 */
	private void updatePlayers() throws Exception {
		playersHash = clientNet.getPlayersHash();
		// Since players info is dealt by Server we must update the currents
		// Player
		OtherPlayers players = playersHash.get(player.getUsername());
		player.setDead(players.isDead());
		if (players.getHealth() > 0) {
			player.setPlayerHealth(players.getHealth());
		} else {
			player.setPlayerHealth(0);
		}
		if (players.getSpeed() > player.getPlayerClassSpeed()) {
			int diff = players.getSpeed() - player.getPlayerClassSpeed();
			if (diff > 1) {
				speedBoostCounter = 0;
			}
			speedBoostCounter++;
			if (speedBoostCounter < 300) {
				player.setPlayerCurrSpeed(player.getPlayerClassSpeed() + 1);
				HUD.setPowerUp("Speed Boost");
			} else {
				speedBoostCounter = 0;
				player.setPlayerCurrSpeed(player.getPlayerClassSpeed());
				HUD.setPowerUp("");
			}
		} else {
			player.setPlayerCurrSpeed(player.getPlayerClassSpeed());
		}
	}

	/**
	 * Sends the current state of the player to the Server
	 */
	public void sendPlayer() {
		shared.Player player1 = new shared.Player();
		player1.username = player.getUsername();
		player1.xPos = player.getposX();
		player1.yPos = player.getposY();
		player1.type = player.getType();
		player1.direction = player.getDirection();
		player1.counterimage = player.getCounterimage();
		player1.team = player.getTeam();
		player1.health = player.getPlayerHealth();
		player1.dead = player.isDead();
		player1.speed = player.getPlayerCurrSpeed();
		clientNet.sendUpdate(player1);
	}

	/**
	 * Renders the map blocks
	 * 
	 * @param g
	 *            Graphics The current game canvas
	 */
	private void renderMap(Graphics g) {
		for (int y = 0; y < map.getYRange(); y++) {
			for (int x = 0; x < map.getXRange(); x++) {
				// Only renders what in the window
				if (blockArray[x][y].x >= (0 - Block.blockSize)
						&& blockArray[x][y].x <= (gui.getWidth() + Block.blockSize)
						&& blockArray[x][y].y >= (0 - Block.blockSize)
						&& blockArray[x][y].y <= (gui.getHeight() + Block.blockSize)) {
					blockArray[x][y].render(g);
				}
			}
		}
	}

	/**
	 * Goes through all the Items in the Hash and renders each of them.
	 * 
	 * @param g
	 *            Graphics The current game canvas
	 */
	private void renderItems(Graphics g) {
		for (Items items : itemsHash.values()) {
			items.render(g, player);
		}
	}

	/**
	 * Goes through all the OtherPlayers in the Hash and renders each of them
	 * except for the current player.
	 * 
	 * @param g
	 *            Graphics The current game canvas
	 */
	private void renderOtherPlayers(Graphics g) {
		for (OtherPlayers otherPlayers : playersHash.values()) {
			if (!otherPlayers.getUsername().equals(player.getUsername())) {
				if (otherPlayers.getHealth() > 0) {
					otherPlayers.render(g, player);
				}
			}
		}
	}

	/**
	 * Goes through all the Bullets in the Array and render each of them.
	 * 
	 * @param g
	 *            Graphics The current game canvas
	 */
	private void renderBullets(Graphics g) {
		ArrayList<Bullet> tmp = (ArrayList<Bullet>) bulletsArray.clone();
		for (Bullet bullet : tmp) {
			bullet.render(g, player);
		}
	}

	/**
	 * Get Map
	 * 
	 * @return Maploader
	 */
	public MapLoader getMap() {
		return map;
	}

	/**
	 * Get the player
	 * 
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Get BulletArray
	 *
	 * @return bulletsArray
	 */
	public ArrayList<Bullet> getBulletArray() {
		return bulletsArray;
	}
}