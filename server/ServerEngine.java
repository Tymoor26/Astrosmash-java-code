package server;

import gui.Block;
import shared.GameState;
import shared.MapLoader;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexis Lowe
 * @author Thomas Sammons
 * @author Tymoor Rahman
 * @author Francis Berti
 */
public class ServerEngine {
	private ConcurrentHashMap<String, Player> playersHash;
	private ConcurrentHashMap<String, AI> aiHash;
	private ArrayList<Bullet> bulletsArray;
	private ConcurrentHashMap<Integer, Items> itemses;
	private ServerNet serverNet;
	private MapLoader map;
	private Random rand = new Random();
	private int aiNumber;
	private int numAi;
	private int targetscore;
	private GameMode gameMode;

	/**
	 * Creates a server engine requires a number of ai a targetscore for the
	 * gamemode and a Map Name. After creating the object it calls a The
	 * gameloop Which is Threaded.
	 *
	 * @param numAi
	 * @param targeScore
	 * @param mapname
	 */
	public ServerEngine(int numAi, int targeScore, String mapname) {
		map = new MapLoader(mapname);
		serverNet = new ServerNet(map.getMapname());
		bulletsArray = new ArrayList<>();
		aiHash = new ConcurrentHashMap<>();
		playersHash = new ConcurrentHashMap<>();
		itemses = new ConcurrentHashMap<>();
		gameMode = new GameMode(targeScore);
		this.numAi = numAi;
		this.targetscore = targeScore;
		initItems();
		initAi(numAi);
		gameLoop();
	}

	/**
	 * GameLoop Never stop until the server is halted.
	 */
	private void gameLoop() {
		Thread server = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					update();
					sendGameState();
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		server.start();
	}

	/**
	 * Spawns a AI
	 *
	 * @param team
	 */
	public void spawnAI(String team) {
		aiNumber++;
		Random rand = new Random();
		int randClass = rand.nextInt(4);
		String charClass = "Assault";
		switch (randClass) {
		case 0:
			charClass = "Heavy";
			break;
		case 1:
			charClass = "Assault";
			break;
		case 2:
			charClass = "Shotgunner";
			break;
		case 3:
			charClass = "Sniper";
			break;
		default:
			charClass = "Assault";
		}
		AI ai = new AI(charClass, team, map.getMapname(), "ai" + team
				+ aiNumber);
		aiHash.put(ai.getUsername(), ai);
	}

	/**
	 * Initialise an equal amount of AI on each team given the total number of
	 * AI
	 *
	 * @param numAi
	 */
	public void initAi(int numAi) {
		int teamsplit = numAi / 2;
		for (int i = teamsplit; i > 0; i--) {
			spawnAI("Red");
		}
		for (int i = teamsplit; i > 0; i--) {
			spawnAI("Blue");
		}

	}

	/**
	 * Initialise the ItemPickups on the map places 2 on specific areas floor
	 * only
	 */
	public void initItems() {
		int count = 0;
		while (count < 2) {
			int x = (rand.nextInt(map.xRange));
			int y = (rand.nextInt(map.yRange));
			while (map.getBlockID(x, y) == 0 || map.getBlockID(x, y) == 1
					|| map.getBlockID(x, y) == 7 || map.getBlockID(x, y) == 6
					|| map.getBlockID(x, y) == 5 || map.getBlockID(x, y) == 4) {
				x = (rand.nextInt(map.xRange));
				y = (rand.nextInt(map.yRange));
			}
			if (count == 0) {
				itemses.put(2, new server.Items(-1 * x * Block.blockSize, -1
						* y * Block.blockSize, "Speed", 1));
			} else {
				itemses.put(1, new server.Items(-1 * x * Block.blockSize, -1
						* y * Block.blockSize, "Health", 2));
			}
			count++;
		}
	}

	/**
	 * Creates a GameState from all the info gathered and sends it
	 */
	public void sendGameState() {
		GameState gameState = new GameState();
		gameState.map = map.getMapname();
		gameState.bullets = new ArrayList<>();
		gameState.players = new ConcurrentHashMap<>();
		gameState.itemses = new ArrayList<>();
		for (Items item : itemses.values()) {
			shared.Items items = new shared.Items();
			items.x = item.getX();
			items.y = item.getY();
			items.type = item.getType();
			items.id = item.getId();
			gameState.itemses.add(items);
		}
		for (Bullet bullet : bulletsArray) {
			if (bullet.getLifeSpan() > 0) {
				shared.Bullet bullet1 = new shared.Bullet();
				bullet1.weapon = bullet.getWeapon();
				bullet1.xPos = bullet.getxPos();
				bullet1.yPos = bullet.getyPos();
				bullet1.lifeSpan = bullet.getLifeSpan();
				gameState.bullets.add(bullet1);
			}
		}
		if (playersHash.size() > 0) {
			for (Player player : playersHash.values()) {
				shared.Player player1 = new shared.Player();
				player1.direction = player.getDirection();
				player1.team = player.getTeam();
				player1.type = player.getType();
				player1.xPos = player.getxPos();
				player1.yPos = player.getyPos();
				player1.username = player.getUsername();
				player1.health = player.getHealth();
				player1.speed = player.getSpeed();
				player1.dead = player.isDead();
				player1.counterimage = player.getImageCounter();
				gameState.players.put(player1.username, player1);
			}
		}
		for (AI ai : aiHash.values()) {
			shared.Player player1 = new shared.Player();
			player1.direction = ai.getDirection();
			player1.team = ai.getTeam();
			player1.type = ai.getType();
			player1.xPos = ai.getxPos();
			player1.yPos = ai.getyPos();
			player1.username = ai.getUsername();
			player1.health = ai.getHealth();
			player1.counterimage = ai.getCurrentFrame();
			gameState.players.put(player1.username, player1);
		}
		gameState.blue = gameMode.getScore("Blue");
		gameState.red = gameMode.getScore("Red");
		gameState.gameover = gameMode.gameOver();
		gameState.winner = gameMode.getWinner();
		serverNet.sendUpdate(gameState);
	}

	/**
	 * Resets the Server to 0
	 */
	public void restart() {
		playersHash.clear();
		aiHash.clear();
		gameMode = new GameMode(targetscore);
		aiNumber = 0;
		initAi(numAi);
	}

	/**
	 * Get the latest bullets from ServerNet and then clear the ServerNets Array
	 *
	 * @throws Exception
	 */
	private void updateBullets() throws Exception {
		for (shared.Bullet bullet : serverNet.getBullets().values()) {
			Bullet bullet1 = new Bullet(bullet.direction, bullet.xPos,
					bullet.yPos, bullet.weapon, bullet.owner, bullet.team);
			bulletsArray.add(bullet1);
		}
		serverNet.getBullets().clear();
	}

	/**
	 * Updates the AI and adds their shots to the bullet Array
	 *
	 * @throws Exception
	 */
	private void updateAI() throws Exception {
		for (AI ai : aiHash.values()) {
			if (ai.getHealth() > 0) {
				Bullet aibul = ai.update(playersHash, aiHash);
				if (aibul != null) {
					bulletsArray.add(aibul);
				}
			}
		}
	}

	/**
	 * Updates the players and get the latest info from Server
	 *
	 * @throws Exception
	 */
	private void updatePlayers() throws Exception {
		for (Player player : playersHash.values()) {
			if (serverNet.getPlayers().get(player.getUsername()) == null) {
				playersHash.remove(player.getUsername());
			}
		}
		for (shared.Player player : serverNet.getPlayers().values()) {
			if (playersHash.containsKey(player.username)) {
				playersHash.get(player.username).setxPos(player.xPos);
				playersHash.get(player.username).setyPos(player.yPos);
				playersHash.get(player.username).setDirection(player.direction);
				playersHash.get(player.username).setImageCounter(
						player.counterimage);
				playersHash.get(player.username).setHealth(player.health);
				playersHash.get(player.username).setSpeed(player.speed);
				playersHash.get(player.username).setDead(player.dead);
			} else {
				Player player1 = new Player(player);
				playersHash.put(player.username, player1);
			}
		}
	}

	/**
	 * Checks if the Items collide with players if they do it recreates the item
	 * in a new place.
	 */
	private void updateItems() {
		for (Items item : itemses.values()) {
			for (Player player : playersHash.values()) {
				int check = item.collisionWithPlayer(player);
				if (check == 1) {
					int x = (rand.nextInt(map.xRange));
					int y = (rand.nextInt(map.yRange));
					while (map.getBlockID(x, y) == 0
							|| map.getBlockID(x, y) == 1
							|| map.getBlockID(x, y) == 7
							|| map.getBlockID(x, y) == 6
							|| map.getBlockID(x, y) == 5
							|| map.getBlockID(x, y) == 4) {
						x = (rand.nextInt(map.xRange));
						y = (rand.nextInt(map.yRange));
					}
					itemses.get(1).setY(-1 * y * Block.blockSize);
					itemses.get(1).setX(-1 * x * Block.blockSize);
				} else if (check == 2) {
					int x = (rand.nextInt(map.xRange));
					int y = (rand.nextInt(map.yRange));
					while (map.getBlockID(x, y) == 0
							|| map.getBlockID(x, y) == 1
							|| map.getBlockID(x, y) == 7
							|| map.getBlockID(x, y) == 6
							|| map.getBlockID(x, y) == 5
							|| map.getBlockID(x, y) == 4) {
						x = (rand.nextInt(map.xRange));
						y = (rand.nextInt(map.yRange));
					}
					itemses.get(2).setX(-1 * x * Block.blockSize);
					itemses.get(2).setY(-1 * y * Block.blockSize);
				}
			}
		}
	}

	/**
	 * Updates the score of the game.
	 */
	private void updateScore() {
		for (Player player : playersHash.values()) {
			if (player.isDead()) {
				gameMode.incrementScore(player.getTeam());
				player.setDead(false);
			}
		}
		for (AI ai : aiHash.values()) {
			if (ai.getHealth() <= 0) {
				String aiTeam = ai.getTeam();
				aiHash.remove(ai.getUsername());
				gameMode.incrementScore(aiTeam);
				spawnAI(aiTeam);
			}
		}
	}

	/**
	 * Moves the bullets and check if they collide with players or AI
	 */
	private void updateBulletColMov() {
		ArrayList<Bullet> tmp = (ArrayList<Bullet>) bulletsArray.clone();
		for (Bullet bullet : tmp) {
			bullet.moveBullet(map);
			if (bullet.getLifeSpan() <= 0) {
				bulletsArray.remove(bullet);
			}

			for (Player player : playersHash.values()) {
				if (bullet.bulletCollisionToPlayer(player)) {
					if (bullet.isExplosive() && bullet.getLifeSpan() > 10) {
						bullet.setLifeSpan(10);
					} else if (!bullet.isExplosive()
							|| bullet.getLifeSpan() > 0) {
						bulletsArray.remove(bullet);
					}
				}
				if (bullet.isExploding()) {
					bullet.explosionProx(player);
				}
			}
			for (AI ai : aiHash.values()) {
				if (bullet.bulletCollisionToAI(ai)) {
					if (bullet.isExplosive() && bullet.getLifeSpan() > 10) {
						bullet.setLifeSpan(10);
					} else if (!bullet.isExplosive()
							|| bullet.getLifeSpan() > 0) {
						bulletsArray.remove(bullet);
					}
				}
				if (bullet.isExploding()) {
					bullet.explosionProxAI(ai);
				}
			}
			if (bullet.isExploding()) {
				bullet.setExplosionSpan(bullet.getExplosionSpan() - 1);
				if (bullet.getExplosionSpan() <= 0) {
					bulletsArray.remove(bullet);
				}
			}
			bullet.moveBullet(map);
		}
	}

	/**
	 * Updates the GameState and gets all the info from the Clients by accessing
	 * ServerNet
	 */
	public void update() {
		try {
			if (gameMode.gameOver()) {
				restart();
			}
			updateAI();
			updateBullets();
			updatePlayers();
			updateItems();
			updateScore();
			updateBulletColMov();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ServerEngine(0, 2, "map2.txt");
	}
}
