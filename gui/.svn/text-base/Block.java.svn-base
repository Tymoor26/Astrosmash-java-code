package gui;

import client.Player;
import shared.MapLoader;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Used for drawing out the map blocks
 * 
 * @author Thomas Sammons
 */
public class Block {
	public static int blockSize = 32;
	public int x, y; // current x and y
	public int oX, oY; // Original x and y
	int blockID; // Block ID
	GameGui game;
	MapLoader map;
	Player player;

	/**
	 * Constructor for a block
	 * 
	 * @param oX
	 *            The original x position
	 * @param oY
	 *            The original y position
	 * @param game
	 *            The game the block is being used in
	 * @param blockID
	 *            The id of the block
	 * @param map
	 *            The map from which the block came from
	 * @param player
	 *            The player
	 */
	public Block(int oX, int oY, GameGui game, int blockID, MapLoader map,
			Player player) {
		this.oX = oX;
		this.oY = oY;
		this.game = game;
		this.blockID = blockID;
		this.map = map;
		this.player = player;
	}

	/**
	 * The update method for blocks
	 * 
	 * @param game
	 *            The game being used to update from
	 */
	public void update(GameGui game) {
		this.game = game;
		x = oX + player.getxOffset();
		y = oY + player.getyOffset();
	}

	/**
	 * Rotates an image
	 * 
	 * @param degree
	 *            The degree of rotation
	 * @param image
	 *            The image to be rotated
	 * @return The rotated image to be drawn
	 */
	public BufferedImage rotateImage(int degree, Image image) {
		ImageIcon icon = new ImageIcon(image);
		BufferedImage bi = new BufferedImage(icon.getIconWidth(),
				icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		g2.rotate(Math.toRadians(degree), icon.getIconWidth() / 2,
				icon.getIconHeight() / 2);
		g2.drawImage(image, 0, 0, null);
		return bi;
	}

	/**
	 * Render method for the blocks
	 * 
	 * @param g
	 *            The graphics to draw with
	 */
	public void render(Graphics g) {
		// Render a block depending on its ID
		switch (blockID) {
		case 0:
			break;
		case 1:
			Image WALL = ImageSelecter(1, "Wall");
			g.drawImage(WALL, x, y, blockSize, blockSize, null);
			break;
		case 2:
			Image FLOOR = new ImageIcon("Textures/Floor/Floor_Metal.jpg")
					.getImage();
			g.drawImage(FLOOR, x, y, blockSize, blockSize, null);
			break;
		case 3:
			break;
		case 4:
			Image shield_H_R = new ImageIcon("Textures/Floor/Floor_S_H_Red.jpg")
					.getImage();
			g.drawImage(shield_H_R, x, y, blockSize, blockSize, null);
			break;
		case 5:
			Image shield_V_R = new ImageIcon("Textures/Floor/Floor_S_V_Red.jpg")
					.getImage();
			g.drawImage(shield_V_R, x, y, blockSize, blockSize, null);
			break;
		case 6:
			Image shield_H_B = new ImageIcon(
					"Textures/Floor/Floor_S_H_Blue.jpg").getImage();
			g.drawImage(shield_H_B, x, y, blockSize, blockSize, null);
			break;
		case 7:
			Image shield_V_B = new ImageIcon(
					"Textures/Floor/Floor_S_V_Blue.jpg").getImage();
			g.drawImage(shield_V_B, x, y, blockSize, blockSize, null);
			break;
		case 8:
			Image SPAWN_BLUE = new ImageIcon("Textures/Spawn/Spawn_Blue.jpg")
					.getImage();
			g.drawImage(SPAWN_BLUE, x, y, blockSize, blockSize, null);
			break;
		case 9:
			Image SPAWN_RED = new ImageIcon("Textures/Spawn/Spawn_Red.jpg")
					.getImage();
			g.drawImage(SPAWN_RED, x, y, blockSize, blockSize, null);
			break;
		default:
			g.setColor(Color.BLUE);
			g.fillRect(x, y, blockSize, blockSize);
			break;
		}
	}

	/**
	 * Used to pick the correct image for a certain x y position
	 * 
	 * @param blockCode
	 *            The codeID of the block
	 * @param fileName
	 *            The name of the Folder and start of the file
	 * @return The correct image for the x y position
	 */
	private Image ImageSelecter(int blockCode, String fileName) {
		// Turning pixels into blocks
		int unitX = oX / blockSize;
		int unitY = oY / blockSize;

		// Default output block
		Image output = new ImageIcon("Textures/Space/Space_Blank.jpg")
				.getImage();

		// Set Boolean conditions for all corners
		boolean mainBodyBlocks = unitX > 0 && unitY > 0
				&& unitX < map.getXRange() - 1 && unitY < map.getYRange() - 1;
		boolean outlineBlocksW = unitX == 0;
		boolean outlineBlocksN = unitY == 0;
		boolean outlineBlocksE = unitX == map.xRange - 1;
		boolean outlineBlocksS = unitY == map.yRange - 1;
		boolean tileUp = false;
		boolean tileDown = false;
		boolean tileLeft = false;
		boolean tileRight = false;
		if (mainBodyBlocks) {
			// If in main body (not on the edge of map)
			tileUp = map.getBlockID(unitX, unitY - 1) == blockCode;
			tileDown = map.getBlockID(unitX, unitY + 1) == blockCode;
			tileLeft = map.getBlockID(unitX - 1, unitY) == blockCode;
			tileRight = map.getBlockID(unitX + 1, unitY) == blockCode;

			// Picks the correct block for the positions selected
			if (tileUp && tileDown && tileRight && tileLeft) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_X_Junction.jpg").getImage();
			} else if (!tileLeft && !tileUp && !tileRight && !tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_NC.jpg").getImage();
			} else if (!tileLeft && tileUp && tileRight && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_R.jpg").getImage();
			} else if (tileLeft && !tileDown && tileRight && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_U.jpg").getImage();
			} else if (!tileRight && tileUp && tileLeft && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_L.jpg").getImage();
			} else if (tileRight && tileDown && tileLeft && !tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_D.jpg").getImage();
			} else if (!tileLeft && !tileUp && tileRight && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_RD.jpg").getImage();
			} else if (!tileLeft && !tileDown && tileRight && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_RU.jpg").getImage();
			} else if (!tileRight && !tileUp && tileLeft && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_LD.jpg").getImage();
			} else if (!tileRight && !tileDown && tileLeft && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_LU.jpg").getImage();
			} else if (!tileLeft && !tileUp && !tileRight && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_D.jpg").getImage();
			} else if (!tileLeft && !tileDown && !tileRight && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_U.jpg").getImage();
			} else if (!tileRight && !tileUp && tileLeft && !tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_L.jpg").getImage();
			} else if (tileRight && !tileDown && !tileLeft && !tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_R.jpg").getImage();
			} else if (!tileLeft && tileUp && !tileRight && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Straight_V.jpg").getImage();
			} else {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Straight_H.jpg").getImage();
			}
		} else if (outlineBlocksW) {
			// If Block is located on the Left edge
			tileUp = map.getBlockID(unitX, unitY - 1) == blockCode;
			tileDown = map.getBlockID(unitX, unitY + 1) == blockCode;
			tileRight = map.getBlockID(unitX + 1, unitY) == blockCode;

			// Picks the correct block for the positions selected
			if (!tileUp && !tileRight && !tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_NC.jpg").getImage();
			} else if (!tileUp && tileRight && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_RD.jpg").getImage();
			} else if (!tileDown && tileRight && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_RU.jpg").getImage();
			} else if (!tileRight && !tileUp && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_LD.jpg").getImage();
			} else if (!tileRight && !tileDown && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_LU.jpg").getImage();
			} else if (tileUp && tileRight && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_R.jpg").getImage();
			} else if (!tileDown && tileRight && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_U.jpg").getImage();
			}
			// else if(!tileRight && tileUp && tileDown){output = new
			// ImageIcon("Textures/"+fileName+"/" +fileName
			// +"_T_Junction_L.jpg").getImage();}
			else if (tileRight && tileDown && !tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_D.jpg").getImage();
			} else if (!tileUp && !tileRight && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_D.jpg").getImage();
			} else if (!tileDown && !tileRight && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_U.jpg").getImage();
			} else if (!tileRight && !tileUp && !tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_L.jpg").getImage();
			} else if (tileRight && !tileDown && !tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_R.jpg").getImage();
			} else if (tileUp && !tileRight && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Straight_V.jpg").getImage();
			} else {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Straight_H.jpg").getImage();
			}
		} else if (outlineBlocksN) {
			// If Block is located on the Top edge
			tileDown = map.getBlockID(unitX, unitY + 1) == blockCode;
			tileLeft = map.getBlockID(unitX - 1, unitY) == blockCode;
			tileRight = map.getBlockID(unitX + 1, unitY) == blockCode;

			// Picks the correct block for the positions selected
			if (!tileLeft && !tileRight && !tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_NC.jpg").getImage();
			} else if (!tileLeft && tileRight && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_RD.jpg").getImage();
			} else if (!tileLeft && !tileDown && tileRight) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_RU.jpg").getImage();
			} else if (!tileRight && tileLeft && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_LD.jpg").getImage();
			} else if (!tileRight && !tileDown && tileLeft) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_LU.jpg").getImage();
			} else if (!tileLeft && tileRight && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_R.jpg").getImage();
			}
			// else if(tileLeft && !tileDown && tileRight){output = new
			// ImageIcon("Textures/"+fileName+"/" +fileName
			// +"_T_Junction_U.jpg").getImage();}
			else if (!tileRight && tileLeft && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_L.jpg").getImage();
			} else if (tileRight && tileDown && tileLeft) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_D.jpg").getImage();
			} else if (!tileLeft && !tileRight && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_D.jpg").getImage();
			} else if (!tileLeft && !tileDown && !tileRight) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_U.jpg").getImage();
			} else if (!tileRight && tileLeft && !tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_L.jpg").getImage();
			} else if (tileRight && !tileDown && !tileLeft) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_R.jpg").getImage();
			} else if (!tileLeft && !tileRight && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Straight_V.jpg").getImage();
			} else {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Straight_H.jpg").getImage();
			}
		} else if (outlineBlocksE) {
			// If Block is located on the Right edge
			tileUp = map.getBlockID(unitX, unitY - 1) == blockCode;
			tileDown = map.getBlockID(unitX, unitY + 1) == blockCode;
			tileLeft = map.getBlockID(unitX - 1, unitY) == blockCode;

			// Picks the correct block for the positions selected
			if (!tileLeft && !tileUp && !tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_NC.jpg").getImage();
			} else if (!tileLeft && !tileUp && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_RD.jpg").getImage();
			} else if (!tileLeft && !tileDown && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_RU.jpg").getImage();
			} else if (!tileUp && tileLeft && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_LD.jpg").getImage();
			} else if (!tileDown && tileLeft && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_LU.jpg").getImage();
			}
			// else if(!tileLeft && tileUp && tileDown){output = new
			// ImageIcon("Textures/"+fileName+"/" +fileName
			// +"_T_Junction_R.jpg").getImage();}
			else if (tileLeft && !tileDown && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_U.jpg").getImage();
			} else if (tileUp && tileLeft && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_L.jpg").getImage();
			} else if (tileDown && tileLeft && !tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_D.jpg").getImage();
			} else if (!tileLeft && !tileUp && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_D.jpg").getImage();
			} else if (!tileLeft && !tileDown && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_U.jpg").getImage();
			} else if (!tileUp && tileLeft && !tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_L.jpg").getImage();
			} else if (!tileDown && !tileLeft && !tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_R.jpg").getImage();
			} else if (!tileLeft && tileUp && tileDown) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Straight_V.jpg").getImage();
			} else {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Straight_H.jpg").getImage();
			}
		} else if (outlineBlocksS) {
			// If Block is located on the Bottom edge
			tileUp = map.getBlockID(unitX, unitY - 1) == blockCode;
			tileLeft = map.getBlockID(unitX - 1, unitY) == blockCode;
			tileRight = map.getBlockID(unitX + 1, unitY) == blockCode;

			// Picks the correct block for the positions selected
			if (!tileLeft && !tileUp && !tileRight) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_NC.jpg").getImage();
			} else if (!tileLeft && !tileUp && tileRight) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_RD.jpg").getImage();
			} else if (!tileLeft && tileRight && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_RU.jpg").getImage();
			} else if (!tileRight && !tileUp && tileLeft) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_LD.jpg").getImage();
			} else if (!tileRight && tileLeft && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Corner_LU.jpg").getImage();
			} else if (!tileLeft && tileUp && tileRight) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_R.jpg").getImage();
			} else if (tileLeft && tileRight && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_U.jpg").getImage();
			} else if (!tileRight && tileUp && tileLeft) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_T_Junction_L.jpg").getImage();
			}
			// else if(tileRight && tileLeft && !tileUp){output = new
			// ImageIcon("Textures/"+fileName+"/" +fileName
			// +"_T_Junction_D.jpg").getImage();}
			else if (!tileLeft && !tileUp && !tileRight) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_D.jpg").getImage();
			} else if (!tileLeft && !tileRight && tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_U.jpg").getImage();
			} else if (!tileRight && !tileUp && tileLeft) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_L.jpg").getImage();
			} else if (tileRight && !tileLeft && !tileUp) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_End_R.jpg").getImage();
			} else if (!tileLeft && tileUp && !tileRight) {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Straight_V.jpg").getImage();
			} else {
				output = new ImageIcon("Textures/" + fileName + "/" + fileName
						+ "_Straight_H.jpg").getImage();
			}
		} else {
			return output;
		}
		return output;
	}
}
