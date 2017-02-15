/**
 *
 */
package server;

/**
 * @author Francis Berti
 */
public class GameMode {
	private int redScore;
	private int blueScore;
	private int targetScore;

	/**
	 * Constructor
	 */
	public GameMode(int targetScore) {
		setScore("Red", 0);
		setScore("Blue", 0);
		setTargetScore(targetScore);
	}

	/**
	 * Checks to see if its game over
	 * 
	 * @return
	 */
	public boolean gameOver() {
		boolean gameOver = false;
		if (redScore >= targetScore || blueScore >= targetScore) {
			gameOver = true;
		}
		return gameOver;
	}

	/**
	 * Sets the score
	 * 
	 * @param teamNo
	 *            The team
	 * @param score
	 *            The score
	 */
	public void setScore(String teamNo, int score) {
		switch (teamNo) {
		case "Red":
			redScore = score;
			break;
		case "Blue":
			blueScore = score;
			break;
		default:
			System.out.println("Error, team numbers must be 1 or 2");
		}
	}

	/**
	 * Gets the score
	 * 
	 * @param team
	 *            The Team
	 * @return The score
	 */
	public int getScore(String team) {
		switch (team) {
		case "Red":
			return redScore;
		case "Blue":
			return blueScore;
		default:
			System.out.println("Error, team numbers must be 1 or 2");
		}
		return 0;
	}

	/**
	 * Checks who is the winner when gameOver() becomes true
	 * 
	 * @return int value representing either winning team or a draw
	 */
	public String getWinner() {
		String winningTeam = "";
		if (gameOver()) {
			if (redScore == blueScore) {
				winningTeam = "Draw";
			} else if (redScore > blueScore) {
				winningTeam = "Blue";
			} else if (blueScore > redScore) {
				winningTeam = "Red";
			} else {
				System.out
						.println("Error: something went wrong with the team score");
			}
		}
		return winningTeam;
	}

	/**
	 * Increase the score of a team when they score a point
	 * 
	 * @param team
	 *            The team who scored the point
	 */
	public void incrementScore(String team) {
		switch (team) {
		case "Red":
			redScore++;
			break;
		case "Blue":
			blueScore++;
			break;
		default:
			System.out.println("Error, team numbers must be 1 or 2");
		}
	}

	/**
	 * Method to get the score needed to win the game
	 *
	 * @return the score needed to win the game
	 */
	public int getTargetScore() {
		return targetScore;
	}

	/**
	 * Method to set the score needed to win the game
	 * 
	 * @param target
	 */
	public void setTargetScore(int target) {
		targetScore = target;
	}
}
