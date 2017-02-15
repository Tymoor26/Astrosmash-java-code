package gui;

import client.ClientEngine;
import client.ClientNet;
import client.InputHandler;
import client.Player;
import shared.MapLoader;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

/**
 * The Game GUI
 * 
 * @author Thomas Sammons
 * @author Alexis Lowe
 * @author Tymoor Rahman
 * @author Ahmed Qureshi
 * @author Francis Berti
 */
public class GameGui extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public final int HEIGHT = 600;
	public final int WIDTH = 1024;
	public final Dimension gameDim = new Dimension(WIDTH, HEIGHT);
	private final String gameName = "Astro Smash";
	public int mapXSize;
	public int mapYSize;
	Block blockArray[][];
	private MapLoader importedMap;
	private client.Player player;
	private ClientEngine engine;
	private JFrame gameFrame;
	private boolean isRunning = true;
	private Thread thread;
	private String ip;
	private ClientNet clientNet;
	private HUD hud;
	private InputHandler inputHandler;

	/**
	 * GameGui Constructor
	 * 
	 * @throws IOException
	 */
	public GameGui(String userClass, String userTeam, String username, String ip) {

		// basic frame setup
		gameFrame = new JFrame();
		this.ip = ip;
		setMaximumSize(gameDim);
		setMaximumSize(gameDim);
		setPreferredSize(gameDim);
		gameFrame.setTitle(gameName);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setLayout(new BorderLayout());
		gameFrame.add(this, BorderLayout.CENTER);
		gameFrame.pack();
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);
		clientNet = new ClientNet(this.ip);

		player = new client.Player(userClass, userTeam, username);// , this);
		hud = new HUD(player);
		gameFrame.add(hud, BorderLayout.SOUTH);

		gameFrame.setVisible(true);

		inputHandler = new InputHandler(player, clientNet);
		this.addKeyListener(inputHandler);

		engine = new ClientEngine(clientNet, player, this);
		importedMap = engine.getMap();
		mapXSize = importedMap.getXRange();
		mapYSize = importedMap.getYRange();
	}

	public InputHandler getInputHandler() {
		return inputHandler;
	}

	public void setHUD(Player player) {
		gameFrame.remove(hud);
		this.hud = new HUD(player);
		gameFrame.add(hud, BorderLayout.SOUTH);
	}

	/**
	 * Starts the game loop
	 */
	public void run() {
		// working out FPS And setting UPS
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0.0;
		int frames = 0;
		int updates = 0;

		// Game Loop
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update(); // Updates The Game
				updates++;
				delta--;
			}
			render(); // Renders The Game
			frames++;

			// Shows FPS/ UPD and Blocks rendered in game title
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				gameFrame.setTitle(gameName + " | " + updates + " ups, "
						+ frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		// if Game loop is exited stops thread
		stop();
	}

	/**
	 * Starts the thread
	 */
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Stops the game loop and closes the thread
	 */
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new MainMenu();
		gameFrame.dispose();
	}

	/**
	 * Updates the game
	 */
	public void update() {
		engine.update(this);

	}

	/**
	 * renders the game
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		// Create Graphics to draw
		Graphics g = bs.getDrawGraphics();

		// Colour frame background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		// Render the Game Engine
		engine.render(g);

		g.dispose();
		bs.show();
	}

	/**
	 * Get the current map being used
	 * 
	 * @return The map being used
	 */
	public MapLoader getImportedMap() {
		return importedMap;
	}

	/**
	 * Gets the current player
	 * 
	 * @return The current player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Get the client engine
	 * 
	 * @return The client engine
	 */
	public ClientEngine getEngine() {
		return engine;
	}

	/**
	 * Gets the client Network
	 * 
	 * @return The client network
	 */
	public ClientNet getNet() {
		return clientNet;
	}
}
