package server;

import shared.MapLoader;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Francis Berti
 */
public class AI {
	private int currentPixelX; // The current x coordinate in pixels
	private int currentPixelY; // The current y coordinate in pixels
	private int currentBlockX; // The current x coordinate in blocks
	private int currentBlockY; // The current y coordinate in blocks
	private int direction; // The current direction the AI is facing

	private String team; // The team the AI is on
	private String charClass; // The character class the AI is (Assault, Heavy,
								// Sniper, Shotgunner)

	private int currentFrame;

	private int health; // The current health of the AI
	private int speed;
	private String username; // The username of the AI (used to determine which
								// to move, who killed who etc.)

	private int weaponAmmo; // Ammo for the selected weapon
	private int currentAmmo; // Current ammo in weapon
	private int reloadDelay; // Delay to reload each weapon
	private int currentReloadTime; // current time during reload

	private MapLoader loader; // To be changed when maploader is moved across
	private int[][] blockIDArray; // The 2D array storing block IDs - Used in
									// routing

	private int[] blockObjective; // The current objective, the block
	private int[] nextNode; // The next block to be visited
	private int[] nextNodePixel; // The next pixel to be visited
	private ArrayList<int[]> routeToObjective; // The route of block nodes to be
												// followed

	private boolean shootY; // Determines whether the player is shooting
							// left/right or up/down
	private int maxViewDist; // The maximum distance the AI can see
	private int shotTimer;
	private int currentShotTime;
	private String weapon;

	/**
	 * Constructor for the AI class Sets weapons, ammo, position etc.
	 * 
	 * @param charClass
	 *            The class for the AI (shotgunner, sniper, assault, heavy)
	 * @param team
	 *            The team for the AI (Red, Blue)
	 * @param map
	 *            The filename of the map
	 * @param username
	 *            The username of the AI
	 */
	public AI(String charClass, String team, String map, String username) {
		// TODO Auto-generated constructor stub
		currentFrame = 1;
		currentReloadTime = 0;
		direction = 1;
		this.team = team;
		this.charClass = charClass;
		this.username = username;
		routeToObjective = new ArrayList<int[]>();
		nextNode = new int[2];
		nextNodePixel = new int[2];
		blockObjective = new int[2];

		switch (charClass.toLowerCase()) {
		case "assault":
			weapon = "Assault Rifle";
			weaponAmmo = 20;
			reloadDelay = 60;
			speed = 1;
			shotTimer = 20;
			health = 150;
			maxViewDist = 700;
			break;

		case "shotgunner":
			weapon = "Shotgun";
			weaponAmmo = 6;
			reloadDelay = 70;
			speed = 2;
			shotTimer = 50;
			maxViewDist = 400;
			health = 175;
			break;

		case "sniper":
			weapon = "Sniper Rifle";
			weaponAmmo = 5;
			reloadDelay = 100;
			speed = 2;
			shotTimer = 200;
			maxViewDist = 1000;
			health = 125;
			break;

		case "heavy":
			weapon = "Rocket Launcher";
			reloadDelay = 110;
			weaponAmmo = 1;
			speed = 1;
			shotTimer = 300;
			maxViewDist = 800;
			health = 200;
			break;

		default:
			weapon = "Assault Rifle";
			weaponAmmo = 20;
			speed = 2;
			shotTimer = 20;
			maxViewDist = 700;
			health = 150;
			break;
		}

		// ***************************************************
		// *** ADD THIS BACK IN ONCE MAPLOADER IS IMPORTED ***
		// ********** I DON'T WANT NULL POINTER ERRORS *******
		loader = new MapLoader(map);

		blockIDArray = loader.getBlockArray();
		// ***************************************************
		currentAmmo = weaponAmmo;
		currentReloadTime = 0;
		getSpawn(team);

		blockObjective = null;
	}

	/**
	 * Sets the spawn of the AI depending on the team Finds the spawn block in
	 * any given map
	 * 
	 * @param team
	 *            The team of the AI (Red, Blue)
	 */
	private void getSpawn(String team) {
		for (int i = 0; i < blockIDArray.length; i++) {
			for (int j = 0; j < blockIDArray[0].length; j++) {
				if (team.equals("Red")) {
					if (blockIDArray[i][j] == 9) {
						currentBlockX = i;
						currentBlockY = j;
					}
				}

				if (team.equals("Blue")) {
					if (blockIDArray[i][j] == 8) {
						currentBlockX = i;
						currentBlockY = j;
					}
				}

				currentPixelX = currentBlockX * -32;
				currentPixelY = currentBlockY * -32;
			}
		}
	}

	/**
	 * Runs the AI, the step taken whenever the server wants the AI to change
	 * something Plots the route, shoots players
	 * 
	 * @param players
	 *            The list of players handled by the server.
	 * @param aiList
	 *            The list of ai handled by the server
	 * @return aiBull The bullet created by the AI if there is a player in range
	 *         to be shot
	 */
	public Bullet update(ConcurrentHashMap<String, Player> players,
			ConcurrentHashMap<String, AI> aiList) {

		currentFrame++;
		if (currentFrame > 12) {
			currentFrame = 1;
		}

		// is player in shootable area?
		// if so, shoot and route
		// if not, move about
		shootY = false;
		Player shootablePlayer = enemyShootable(players);
		AI shootableAI = aiShootable(aiList);
		Bullet aiBull = null;
		currentShotTime--;
		currentReloadTime--;
		try {
			if (shootablePlayer != null && currentShotTime <= 0) {
				currentShotTime = shotTimer;
				aiBull = shoot(shootablePlayer.getxPos(),
						shootablePlayer.getyPos());
				currentAmmo--;
				shootY = false;
				blockObjective[0] = shootablePlayer.getxPos() / -32;
				blockObjective[1] = shootablePlayer.getyPos() / -32;
			}

			if (shootableAI != null && currentShotTime <= 0) {
				currentShotTime = shotTimer;
				aiBull = shoot(shootableAI.getxPos(), shootableAI.getyPos());
				currentAmmo--;
				shootY = false;
				blockObjective[0] = shootableAI.getxPos() / -32;
				blockObjective[1] = shootableAI.getyPos() / -32;
			}
		} catch (NullPointerException e) {
			// e.printStackTrace();
		}
		// checks to see if there's an objective
		// checks to see if there's a route to that objective
		// knows where it is, knows which pixel it needs to be on
		// if is on pixel it needs to be on, checks to see if its at the end of
		// the route, if end of route... start again so far
		// if on goal pixel but not end of route, remove block from route, keep
		// going
		// otherwise...
		// if the goal pixel is to the right, go right etc
		boolean listEmpty = false;
		boolean nextNodeObstructed = false;

		if (blockObjective == null) {
			blockObjective = generateRandomObjective();
		}
		if (routeToObjective.isEmpty()) {
			listEmpty = true;
			// System.out.println("Empty list");
			blockObjective = generateRandomObjective();
			routeToObjective = plotRoute(blockObjective);
		}
		// System.out.println("Current pixel: " + currentPixelX + "," +
		// currentPixelY);
		// System.out.println("Current block: " + currentBlockX + "," +
		// currentBlockY);
		// System.out.println("objective: " + blockObjective[0] + "," +
		// blockObjective[1] + "\n");
		// for (int[] is : routeToObjective) {
		// System.out.println("node: " + is[0] + "," + is[1]);
		// }
		if (!listEmpty) {
			try {
				// System.out.println("Current pixel: " + currentPixelX + "," +
				// currentPixelY);
				// System.out.println("Next block: " +
				// routeToObjective.get(0)[0] + "," +
				// routeToObjective.get(0)[1]);
				nextNode[0] = routeToObjective.get(0)[0];
				nextNode[1] = routeToObjective.get(0)[1];
				nextNodePixel[0] = ((nextNode[0] * -32) - 16);
				nextNodePixel[1] = ((nextNode[1] * -32) - 16);

				if (blockIDArray[nextNode[0]][nextNode[1]] == 1) {
					nextNodeObstructed = true;
				}

				if (currentPixelX == nextNodePixel[0]
						&& currentPixelY == nextNodePixel[1]) {
					routeToObjective.remove(0);
					if (currentPixelX / 32 == blockObjective[0]
							&& currentPixelY / 32 == blockObjective[1]) {
						blockObjective = null;
					} else {
						nextNode[0] = routeToObjective.get(0)[0];
						nextNode[1] = routeToObjective.get(0)[1];
						nextNodePixel[0] = ((nextNode[0] * 32) - 16) * (-1);
						nextNodePixel[1] = ((nextNode[1] * 32) - 16) * (-1);
					}
				} else if (!nextNodeObstructed) {
					if (nextNodePixel[0] > currentPixelX) {
						direction = 4;
						currentPixelX += speed;
					} else if (nextNodePixel[0] < currentPixelX) {
						direction = 2;
						currentPixelX -= speed;
					} else if (nextNodePixel[1] > currentPixelY) {
						direction = 1;
						currentPixelY += speed;
					} else if (nextNodePixel[1] < currentPixelY) {
						direction = 3;
						currentPixelY -= speed;
					}
					// update current block
					if (currentPixelX / 32 != currentBlockX
							|| currentPixelY / 32 != currentBlockY) {
						currentBlockX = currentPixelX / -32;
						currentBlockY = currentPixelY / -32;
					}
				} else {
					blockObjective = null;
					routeToObjective.clear();
					currentBlockX = currentPixelX / -32;
					currentBlockY = currentPixelY / -32;
				}

			} catch (NullPointerException | IndexOutOfBoundsException e) {
				// System.out.println("something went wrong");
				// e.printStackTrace();
				blockObjective = null;
				routeToObjective.clear();
			}

		}
		return aiBull;
	}

	/**
	 * Checks through all the AI on the server and detects whether one can be
	 * shot.
	 * 
	 * @param aiList
	 *            The list of AI handled by the server
	 * @return ai The AI player in shootable range
	 */
	private AI aiShootable(ConcurrentHashMap<String, AI> aiList) {
		ConcurrentHashMap<String, AI> aiListing = aiList;

		for (Entry<String, AI> aiEntry : aiListing.entrySet()) {
			AI ai = aiEntry.getValue();
			// System.out.println("Ai in " + ai.getTeam() + " found");

			// if enemy then check if shootable (ignore allies)
			if (!ai.getTeam().equals(team)) {
				// System.out.println("found AI not on team");
				// if within 32 pixels in y value, and within maxViewDist in x
				// then shootable
				// otherwise
				// if within 32 pixels in X value, and within maxViewDist in Y
				// then shootable
				// otherwise not shootable

				int diffX = ai.getxPos() - currentPixelX;
				int diffY = ai.getyPos() - currentPixelY;
				int absDiffX = Math.abs(diffX);
				int absDiffY = Math.abs(diffY);

				if (absDiffX < maxViewDist && absDiffY < 32) {
					// System.out.println("AI in range X");
					return ai;
				} else if (absDiffY < maxViewDist && absDiffX < 32) {
					// System.out.println("AI in range Y");
					shootY = true;
					return ai;
				}

			}

		}

		return null;
	}

	/**
	 * Shoots at a player
	 * 
	 * @param playerPosX
	 *            The X position of the player to be shot at
	 * @param playerPosY
	 *            The Y position of the player to be shot at
	 * @return aiBul The bullet created when shooting
	 */
	private Bullet shoot(int playerPosX, int playerPosY) {
		// TODO Auto-generated method stub
		int diffX = playerPosX - currentPixelX;
		int diffY = playerPosY - currentPixelY;

		Bullet aiBul = null;

		if (diffY > 0 && shootY) {
			// up
			aiBul = new Bullet(1, currentPixelX, currentPixelY, weapon,
					username, team);
			// bulletArray.add(new Bullet(1, playerPosX, playerPosY,
			// "Assault Rifle", team, username));
		} else if (diffY < 0 && shootY) {
			// down
			aiBul = new Bullet(3, currentPixelX, currentPixelY, weapon,
					username, team);
			// bulletArray.add(new Bullet(3, playerPosX, playerPosY,
			// "Assault Rifle", team, username));
		} else if (diffX > 0 && !shootY) {
			// left
			aiBul = new Bullet(4, currentPixelX, currentPixelY, weapon,
					username, team);
			// bulletArray.add(new Bullet(4, playerPosX, playerPosY,
			// "Assault Rifle", team, username));
		} else if (diffX < 0 && !shootY) {
			// right
			aiBul = new Bullet(2, currentPixelX, currentPixelY, weapon,
					username, team);
			// bulletArray.add(new Bullet(2, playerPosX, playerPosY,
			// "Assault Rifle", team, username));
		}

		// System.out.println(username + " shooting " + aiBul.getDirection());
		return aiBul;

	}

	/**
	 * Generates a random objective for the AI to route to
	 * 
	 * @return randCood The randomly generated block that the AI will route to
	 */
	public int[] generateRandomObjective() {
		int xBound = blockIDArray.length;
		int yBound = blockIDArray[0].length;

		Random rand = new Random();
		int randX = rand.nextInt(xBound);
		int randY = rand.nextInt(yBound);

		while (blockIDArray[randX][randY] == 1)// If random location is a wall,
												// try again.
		{
			randX = rand.nextInt(xBound);
			randY = rand.nextInt(yBound);
		}

		int[] randCoord = { randX, randY };

		return randCoord;
	}

	/**
	 * The method used to plot the route for the AI to travel through to reach
	 * it's generated objective.
	 *
	 * @param objective
	 *            The coordinate of the objective the route should reach.
	 * @return visitedSet The set of nodes that the AI will route through to
	 *         reach the goal.
	 */
	public ArrayList<int[]> plotRoute(int[] objective) {

		int objectiveX = objective[0];
		int objectiveY = objective[1];
		int thisBlockX = currentBlockX;
		int thisBlockY = currentBlockY;
		int[] thisBlock = { thisBlockX, thisBlockY };

		int distSoFar = 0; // Gscore
		int distToGoal = distSoFar
				+ costEstimate(thisBlockX, thisBlockY, objectiveX, objectiveY); // fscore

		ArrayList<int[]> closedSet = new ArrayList<int[]>();
		ArrayList<int[]> visitedSet = new ArrayList<int[]>();
		ArrayList<int[]> openSet = new ArrayList<int[]>(); // The set of nodes
															// to explore

		openSet.add(thisBlock);

		// System.out.println("StartingCoords: " + thisBlock[0] + "," +
		// thisBlock[1]);
		// System.out.println("Goal coords: " + objectiveX + "," + objectiveY);

		// return visitedSet;

		while (openSet.size() > 0) // while there are still nodes to explore...
		{
			// System.out.println("Starting search!");
			openSet = sortOpenSet(openSet, objectiveX, objectiveY);// Sort the
																	// set, with
																	// the best
																	// at the
																	// front.
			int[] currentNode = openSet.get(0);// current node removed from
												// openset
			openSet.remove(0);
			if (isGoal(currentNode, objective)) {
				visitedSet.add(currentNode);// FINISHED WOOP
				return visitedSet;
			}
			if (isVisited(visitedSet, currentNode)) {
			} else {
				visitedSet.add(currentNode);
				openSet.clear();
				ArrayList<int[]> neighbours = addNeighbours(currentNode);// Get
																			// all
																			// neighbous
																			// for
																			// the
																			// current
																			// location,
																			// as
																			// long
																			// as
																			// we
																			// have't
																			// been
																			// there
																			// before

				while (!neighbours.isEmpty()) {

					openSet.add(neighbours.get(0));

					neighbours.remove(0);

				}

			}

		}

		return new ArrayList<int[]>();
	}

	/**
	 * Checks to see if a given node has been visited before Equivalent to using
	 * the .contains method, but works for int[]
	 *
	 * @param visitedSet
	 *            The set of visited nodes
	 * @param testCoord
	 *            The coordinate that may have been visited previously.
	 * @return isVisited The boolean value determining whether a node has been
	 *         visited before.
	 */
	private boolean isVisited(ArrayList<int[]> visitedSet, int[] testCoord) {
		// TODO Auto-generated method stub
		boolean isVisited = false;

		for (int[] coord : visitedSet) {
			if (testCoord[0] == coord[0] && testCoord[1] == coord[1]) {
				isVisited = true;// Checks to see if a given node is in the
									// openset
			}
		}

		return isVisited;
	}

	/**
	 * Method to generate all the neighbours around a given point.
	 *
	 * @param position
	 *            The given location you wish to get the neighbours of.
	 * @return neighbourSet The list of coordinates that surround the current
	 *         position.
	 */
	private ArrayList<int[]> addNeighbours(int[] position) {
		ArrayList<int[]> neighbourSet = new ArrayList<int[]>();

		int posX = position[0];
		int posY = position[1];
		int[] xUp = { posX + 1, posY };

		int[] xDn = { posX - 1, posY };

		int[] yUp = { posX, posY + 1 };

		int[] yDn = { posX, posY - 1 };

		if (xUp[0] >= 1 && xUp[1] >= 0 && xUp[0] < blockIDArray.length
				&& xUp[1] < blockIDArray[0].length) {
			if (blockIDArray[xUp[0]][xUp[1]] != 1) {
				// System.out.println("xUp " + xUp[0] + "," + xUp[1]);
				neighbourSet.add(xUp);
			}
		}

		if (xDn[0] >= 0 && xDn[1] >= 0 && xDn[0] < blockIDArray.length
				&& xDn[1] < blockIDArray[0].length) {
			// System.out.println(blockIDArray[xDn[0]][xDn[1]]);
			if (blockIDArray[xDn[0]][xDn[1]] != 1) {
				// System.out.println("xDn " + xDn[0] + "," + xDn[1]);
				neighbourSet.add(xDn);
			}
		}

		if (yUp[0] >= 0 && yUp[1] >= 1 && yUp[0] < blockIDArray.length
				&& yUp[1] < blockIDArray[0].length) {
			if (blockIDArray[yUp[0]][yUp[1]] != 1) {
				// System.out.println("yUp " + yUp[0] + "," + yUp[1]);
				neighbourSet.add(yUp);
			}
		}

		if (yDn[0] >= 0 && yDn[1] >= 0 && yDn[0] < blockIDArray.length
				&& yDn[1] < blockIDArray[0].length) {
			if (blockIDArray[yDn[0]][yDn[1]] != 1) {
				// System.out.println("yDn " + yDn[0] + "," + yDn[1]);
				neighbourSet.add(yDn);
			}
		}

		return neighbourSet;

	}

	/**
	 * Check to see if the current location is the goal
	 *
	 * @param currPos
	 *            The current location of the AI
	 * @param obj
	 *            The objective the AI plans to reach
	 * @return returnBool The boolean value dictating whether the AI has reached
	 *         its goal.
	 */
	public boolean isGoal(int[] currPos, int[] obj) {
		boolean returnBool = false;
		if (currPos[0] == obj[0] && currPos[1] == obj[1]) {
			returnBool = true;
		}

		return returnBool;
	}

	/**
	 * Sorts the openSet to make sure the most favourable node is at the front.
	 *
	 * @param openSet
	 *            The set to be sorted
	 * @param goalX
	 *            The X coordinate of the objective
	 * @param goalY
	 *            The y coordinate of the objective
	 * @return sortedSet The sorted set of possible nodes, most favourable first
	 */
	private ArrayList<int[]> sortOpenSet(ArrayList<int[]> openSet, int goalX,
			int goalY) {
		ArrayList<int[]> sortedSet = new ArrayList<int[]>();
		sortedSet = openSet;

		int n = sortedSet.size();

		do {
			int newn = 0;
			for (int i = 1; i < n; i++) {
				int[] currPos = sortedSet.get(i);
				int[] prevPos = sortedSet.get(i - 1);
				int currDisttoGoal = costEstimate(currPos[0], currPos[1],
						goalX, goalY);
				int prevDisttoGoal = costEstimate(prevPos[0], prevPos[1],
						goalX, goalY);
				// System.out.println("Dist to goal for (" + currPos[0] + "," +
				// currPos[1] + "): " + currDisttoGoal);
				// System.out.println("Dist to goal for (" + prevPos[0] + "," +
				// prevPos[1] + "): " + prevDisttoGoal);
				if (prevDisttoGoal > currDisttoGoal) {
					int[] temp = sortedSet.get(i);
					sortedSet.set(i, prevPos);
					sortedSet.set(i - 1, temp);
					newn = i;
				}
			}
			n = newn;
		} while (n != 0);

		return sortedSet;

	}

	/**
	 * Estimates how far away the goal node is from a given node
	 *
	 * @param posX
	 *            The current x coordinate of the AI
	 * @param posY
	 *            The current y coordinate of the AI
	 * @param goalX
	 *            The x coordinate of the objective node
	 * @param goalY
	 *            The y coordinate of the objective node
	 * @return
	 */
	private int costEstimate(int posX, int posY, int goalX, int goalY) {
		int distX = Math.abs(goalX - posX);
		int distY = Math.abs(goalY - posY);
		int estimateDist = distX + distY;

		return estimateDist;

	}

	/**
	 * Checks through all the players on the server and detects whether one can
	 * be shot.
	 * 
	 * @param playerSet
	 *            The list of AI handled by the server
	 * @return player The player in shootable range
	 */
	private Player enemyShootable(ConcurrentHashMap<String, Player> playerSet) {
		ConcurrentHashMap<String, Player> players = playerSet;

		for (Entry<String, Player> playerEntry : players.entrySet()) {
			Player player = playerEntry.getValue();
			// System.out.println("Player in " + player.getTeam() + " found at "
			// + player.getxPos() + "," + player.getyPos());

			// if enemy then check if shootable (ignore allies)
			if (!player.getTeam().equals(team)) {
				// if within 32 pixels in y value, and within maxViewDist in x
				// then shootable
				// otherwise
				// if within 32 pixels in X value, and within maxViewDist in Y
				// then shootable
				// otherwise not shootable
				// System.out.println("found enemy!");
				int diffX = player.getxPos() - currentPixelX;
				int diffY = player.getyPos() - currentPixelY;
				int absDiffX = Math.abs(diffX);
				int absDiffY = Math.abs(diffY);

				if (absDiffX < maxViewDist && absDiffY < 32) {
					// System.out.println("Player in range X");
					return player;
				} else if (absDiffY < maxViewDist && absDiffX < 32) {
					// System.out.println("Player in range Y");
					shootY = true;
					return player;
				}

			}

		}

		return null;

	}

	/**
	 * Gets the direction of the AI
	 * 
	 * @return direction The direction of the player
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Gets the x coordinate of the AI
	 * 
	 * @return currentPixelX The x coordinate of the AI
	 */
	public int getxPos() {
		return currentPixelX;
	}

	/**
	 * Gets the y coordinate of the AI
	 * 
	 * @return currentPixelY The y coordinate of the AI
	 */
	public int getyPos() {
		return currentPixelY;
	}

	/**
	 * Gets the current frame of the animation the AI is in
	 * 
	 * @return currentFrame The current frame of the animation the AI is in
	 */
	public int getCurrentFrame() {
		return currentFrame;
	}

	/**
	 * Gets the username of the AI
	 * 
	 * @return username The username of the AI
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the current team of the AI
	 * 
	 * @return team The team of the AI
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * Gets the class of the AI
	 * 
	 * @return charClass The character class of the AI
	 */
	public String getType() {
		return charClass;
	}

	/**
	 * Gets the health of the AI
	 * 
	 * @return health The health of the AI
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Sets the health of the AI
	 * 
	 * @param h
	 *            The new health of the AI
	 */
	public void setHealth(int h) {
		// TODO Auto-generated method stub
		health = h;
	}
}
