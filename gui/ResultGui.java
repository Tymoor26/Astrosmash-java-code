package gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Thomas Sammons
 * @author Alexis Lowe
 */
public class ResultGui {
	public JFrame frame = new JFrame();
	private int redScore;
	private int blueScore;
	private String winner;

	/**
	 * Constructor
	 * 
	 * @param redScore
	 *            The red teams score
	 * @param blueScore
	 *            The blue team score
	 * @param winner
	 *            The winning team
	 */
	public ResultGui(int redScore, int blueScore, String winner) {
		this.redScore = redScore;
		this.blueScore = blueScore;
		this.winner = winner;
		// Makes the frame
		makeFrame();
	}

	/**
	 * Makes the frame to show
	 */
	private void makeFrame() {
		// basic frame setup
		frame.setBounds(100, 100, 400, 200);
		frame.setTitle("Game Results");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setVisible(true);
		panel.setBounds(0, 0, 294, 160);
		panel.setLayout(null);
		frame.add(panel);
		// Header
		JLabel winning = new JLabel("Winning Team: " + winner);
		// Changes colour depending on who won
		if (winner.equals("Blue")) {
			winning.setForeground(Color.BLUE);
		} else {
			winning.setForeground(Color.RED);
		}
		winning.setBounds(20, 0, 384, 60);
		winning.setFont(new Font("Helvetica Inserat LT Std", Font.PLAIN, 30));
		panel.add(winning);
		// Red score
		JLabel red = new JLabel("Red Score: " + redScore);
		// Changes colour depending on who won
		if (winner.equals("Blue")) {
			red.setForeground(Color.BLUE);
		} else {
			red.setForeground(Color.RED);
		}
		red.setBounds(20, 55, 284, 60);
		red.setFont(new Font("Calibri", Font.PLAIN, 25));
		panel.add(red);
		// Blue score
		JLabel blue = new JLabel("Blue Score: " + blueScore);
		// Changes colour depending on who won
		if (winner.equals("Blue")) {
			blue.setForeground(Color.BLUE);
		} else {
			blue.setForeground(Color.RED);
		}
		blue.setBounds(20, 90, 284, 60);
		blue.setFont(new Font("Calibri", Font.PLAIN, 25));
		panel.add(blue);
	}
}
