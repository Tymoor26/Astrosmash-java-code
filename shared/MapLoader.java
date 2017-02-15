package shared;

import java.io.*;

/**
 * @author Thomas Sammons
 */
public class MapLoader {

	// X and Y range of the map
	public int xRange, yRange;

	// ID array
	int blockIDArray[][];

	// Map to play
	private File pickedMap;
	private String mapname;
	private BufferedReader br;

	/**
	 * MapLoader Constructor
	 * 
	 * @param userMap
	 *            The map to be played on
	 */
	public MapLoader(String userMap) {
		mapname = userMap;
		pickedMap = new File("Maps/" + userMap);
		xYGetter();
	}

	/**
	 * Gets the X and Y lengths of the Map being used
	 */
	private void xYGetter() {
		try {
			br = new BufferedReader(new FileReader(pickedMap));
			// X length
			String line = br.readLine();
			xRange = Integer.parseInt(line);

			// Y Length
			line = br.readLine();
			yRange = Integer.parseInt(line);
			mapImportSetup();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates an 2D array of the blockID's
	 */
	private void mapImportSetup() {
		// Set up array to cover the size of the map
		blockIDArray = new int[xRange][yRange];
		try {

			int y = 0;
			// Reads in text file line by line gathering the id number
			for (String line; (line = br.readLine()) != null;) {
				for (int x = 0; x < line.length(); x++) {
					blockIDArray[x][y] = Integer.parseInt(Character
							.toString(line.charAt(x)));
				}
				y++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the maps X range
	 * 
	 * @return The x range of the map
	 */
	public int getXRange() {
		return xRange;
	}

	/**
	 * Gets the maps Y range
	 * 
	 * @return The y range of the map
	 */
	public int getYRange() {
		return yRange;
	}

	/**
	 * Prints the blockID array ~VISUAL TESTING~
	 */
	public void printArray() {
		System.out.println("");
		for (int y = 0; y < yRange; y++) {
			for (int x = 0; x < xRange; x++) {
				System.out.print(blockIDArray[x][y]);
			}
			System.out.println("");
		}
	}

	/**
	 * Gets blockID at a certain point
	 * 
	 * @param x
	 *            The x coord
	 * @param y
	 *            The y coord
	 * @return The blockID at x and y
	 */
	public int getBlockID(int oldX, int oldY) {
		int x;
		int y;
		if (oldX < 0 && oldY < 0) {
			x = oldX * -1;
			y = oldY * -1;
		} else if (oldX < 0 && oldY >= 0) {
			x = oldX * -1;
			y = oldY;
		} else if (oldX >= 0 && oldY < 0) {
			x = oldX;
			y = oldY * -1;
		} else {
			x = oldX;
			y = oldY;
		}
		if (x >= xRange) {
			x = xRange - 1;
			return blockIDArray[x][y];
		} else if (y >= yRange) {
			y = yRange - 1;
			return blockIDArray[x][y];
		} else {
			return blockIDArray[x][y];
		}
	}

	/**
	 * Gets the Array of blockID
	 * 
	 * @return The blockID array
	 */
	public int[][] getBlockArray() {
		return blockIDArray;
	}

	/**
	 * Gets the maps name being used
	 * 
	 * @return The maps name
	 */
	public String getMapname() {
		return mapname;
	}
}
