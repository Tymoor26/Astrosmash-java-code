package client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import shared.GameState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexis Lowe
 */
public class ClientNet {
	private Client client;
	private Kryo kryo;
	private String ipaddress;
	private ConcurrentHashMap<String, OtherPlayers> playersHash;
	private ArrayList<Bullet> bulletArray;
	private ConcurrentHashMap<Integer, Items> itemsHash;
	private String mapname;
	private boolean gameover;
	private String winner;
	private int redScore;
	private int blueScore;

	/**
	 * Creates a NetworkClient
	 */
	public ClientNet(String ip) {
		client = new Client(65536, 65536);

		kryo = client.getKryo();

		new Thread(client).start();

		ipaddress = ip;

		playersHash = new ConcurrentHashMap<>();
		itemsHash = new ConcurrentHashMap<>();

	}

	/**
	 * Connects to a give server
	 *
	 * @param ipaddress
	 *            Servers Ip Address
	 * @param port
	 *            Servers communication port
	 */
	public void connectServer(String ipaddress, int port) {
		try {
			client.connect(5000, ipaddress, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Registers classes with kryo for serialization
	 *
	 * @param aclass
	 */
	public void regClasses(Class aclass) {
		kryo.register(aclass);
	}

	/**
	 * Initialise the network client
	 */
	public void initNetworkClient() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				regClasses(GameState.class);
				regClasses(shared.Bullet.class);
				regClasses(shared.Items.class);
				regClasses(shared.Player.class);
				regClasses(ConcurrentHashMap.class);
				regClasses(ArrayList.class);
				connectServer(ipaddress, 1337);

				client.addListener(new Listener() {
					@Override
					public void received(Connection connection, Object o) {
						super.received(connection, o);
						// Checks if the the object receive is a GameState.
						// Updates the clientNet Hashes and Array to the new
						// received data.
						if (o instanceof GameState) {
							GameState gameState = (GameState) o;
							mapname = gameState.map;
							gameover = gameState.gameover;
							winner = gameState.winner;
							redScore = gameState.red;
							blueScore = gameState.blue;

							if (gameState.players != null && !gameover) {
								if (gameState.players.size() > 0) {
									for (OtherPlayers players : playersHash
											.values()) {
										if (gameState.players.get(players
												.getUsername()) == null) {
											playersHash.remove(players
													.getUsername());
										}
									}
									for (shared.Player player : gameState.players
											.values()) {
										if (playersHash
												.containsKey(player.username)) {
											playersHash.get(player.username)
													.setUpdate(player);
										} else {
											OtherPlayers otherPlayers = new OtherPlayers(
													player);
											playersHash.put(player.username,
													otherPlayers);
										}
									}
								}
								bulletArray = new ArrayList<>();
								for (shared.Bullet bullet : gameState.bullets) {
									Bullet bullet1 = new Bullet(bullet);
									bulletArray.add(bullet1);
								}

								for (shared.Items items : gameState.itemses) {
									if (!itemsHash.containsKey(items.id)) {
										Items items1 = new Items(items);
										itemsHash.put(items.id, items1);
									} else {
										itemsHash.get(items.id).setX(items.x);
										itemsHash.get(items.id).setY(items.y);
									}

								}

							}

						}
					}
				});
			}
		}, "ClientNetwork");
		thread.start();

	}

	/**
	 * Get Winner
	 *
	 * @return winner
	 */
	public String getWinner() {
		return winner;
	}

	/**
	 * Get RedScore
	 *
	 * @return redscore
	 */
	public int getRedScore() {
		return redScore;
	}

	/**
	 * Get BlueScore
	 *
	 * @return blueScore
	 */
	public int getBlueScore() {
		return blueScore;
	}

	/**
	 * Get Gameover
	 *
	 * @return gameover
	 */
	public boolean isGameover() {
		return gameover;
	}

	/**
	 * Get ItemHash
	 *
	 * @return itemHash
	 */
	public ConcurrentHashMap<Integer, Items> getItemsHash() {
		return itemsHash;
	}

	/**
	 * Get BulletArray
	 *
	 * @return bulletArray
	 */
	public ArrayList<Bullet> getBulletArray() {
		return bulletArray;
	}

	/**
	 * Get PlayerHash
	 *
	 * @return playerHash
	 */
	public ConcurrentHashMap<String, OtherPlayers> getPlayersHash() {
		return playersHash;
	}

	/**
	 * Sends a Shared player object to the Server
	 *
	 * @param player
	 *            Shared Player Object
	 */
	public void sendUpdate(final shared.Player player) {
		Thread threadsend = new Thread(new Runnable() {
			@Override
			public void run() {
				client.sendTCP(player);
			}
		});
		threadsend.start();
	}

	/**
	 * Get the Map name
	 *
	 * @return mapname
	 */
	public String getMapname() {
		return mapname;
	}

	/**
	 * Sends a Shared bullet to the server
	 *
	 * @param bullet
	 *            Shared Bullet
	 */
	public void sendUpdateb(final shared.Bullet bullet) {
		Thread threadsend = new Thread(new Runnable() {
			@Override
			public void run() {
				client.sendTCP(bullet);
			}
		});
		threadsend.start();
	}
}
