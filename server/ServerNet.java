package server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import shared.Bullet;
import shared.GameState;
import shared.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexis Lowe
 */
public class ServerNet {
	private Server server;
	private Kryo kryo;
	private ConcurrentHashMap<Integer, shared.Bullet> bullets;
	private ConcurrentHashMap<String, shared.Player> players;
	private String mapname;

	/**
	 * Creates a Network server
	 * 
	 * @param map
	 *            The map being used
	 */
	public ServerNet(String map) {
		server = new Server(65536, 65536); // Creates a Kryonet server
		players = new ConcurrentHashMap<>();
		bullets = new ConcurrentHashMap<>();
		mapname = map;
		kryo = server.getKryo(); // Gets kryo instance from the server
		kryo.register(GameState.class); // reg GameState Class
		kryo.register(shared.Bullet.class);
		kryo.register(shared.Items.class);
		kryo.register(shared.Player.class); // reg PlayerPosition Class
		kryo.register(ConcurrentHashMap.class); // reg HashMap Class
		kryo.register(ArrayList.class);
		server.start();
		try {
			initNetworkServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets The bullets
	 * 
	 * @return bullets
	 */
	public ConcurrentHashMap<Integer, Bullet> getBullets() {
		return bullets;
	}

	/**
	 * Gets The players
	 * 
	 * @return players
	 */
	public ConcurrentHashMap<String, Player> getPlayers() {
		return players;
	}

	/**
	 * Sends the GameState to all the Clients
	 * 
	 * @param gameState
	 */
	public void sendUpdate(GameState gameState) {
		server.sendToAllTCP(gameState);
	}

	/**
	 * Initialise the Server and sets the listeners
	 * 
	 * @throws IOException
	 */
	public void initNetworkServer() throws IOException {
		server.bind(1337); // set server port to 1337
		server.addListener(new Listener() {
			@Override
			public void connected(Connection connection) {
				super.connected(connection);
				GameState tmp = new GameState();
				tmp.map = mapname;
				server.sendToTCP(connection.getID(), tmp);
			}

			@Override
			public void disconnected(Connection connection) {
				super.disconnected(connection);
				players.clear();
			}

			@Override
			public void received(Connection connection, Object o) {
				super.received(connection, o);
				while (o == null) {
				}
				if (o instanceof shared.Bullet) {
					shared.Bullet bullet = (shared.Bullet) o;
					Random gen = new Random();
					int i = gen.nextInt(999999999);
					while (bullets.containsKey(i)) {
						i = gen.nextInt();
					}
					bullets.put(i, bullet);
				} else if (o instanceof shared.Player) {
					shared.Player player = (shared.Player) o;
					players.put(player.username, player);
				}
			}
		});
	}
}
