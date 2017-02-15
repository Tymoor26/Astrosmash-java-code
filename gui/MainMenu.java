package gui;

import server.ServerEngine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * The main menu for the game
 * 
 * @author Thomas Sammons
 */
public class MainMenu {
	// Frame Size
	private final int HEIGHT = 600;
	private final int WIDTH = 800;
	private JFrame frame;

	// Panels to use
	private JPanel MainMenu;
	private JPanel teamPickPanel;
	private JPanel playerPickPanel;
	private JPanel multiplayerPanel;

	// Global elements to change the colour of
	private JButton sniper;
	private JButton assult;
	private JButton heavy;
	private JButton shotgun;
	private JLabel playerPickBG;
	private JLabel returnButton;

	// Information to be filled in to play the game
	private boolean multiplayerActive = false;
	private boolean hosting = false;
	private String team = "";
	private String user = "";
	private String type = "";
	private String ip = "";
	private String map = "Random";
	private String scoreLimit = "25";
	private String numOfAi = "0";

	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialise();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialise() {
		// Basic Frame setup
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		// Different panels to load
		mainMenuPanel();
		teamPickPanel();
		charPickPanel();
		multiPanel();
		optionsPanel();
	}

	/**
	 * Main Menu panel To pick which option to go for
	 */
	private void mainMenuPanel() {
		MainMenu = new JPanel();
		MainMenu.setBounds(0, 0, 794, 571);
		MainMenu.setBackground(Color.BLACK);
		frame.getContentPane().add(MainMenu);
		MainMenu.setLayout(null);

		// Title on page
		JLabel titleMain = new JLabel();
		titleMain.setIcon(new ImageIcon("Textures/Menu/MainTitle.png"));
		titleMain.setBounds(20, 31, 764, 89);
		MainMenu.add(titleMain);

		// ##############\\
		// Singleplayer \\
		// ##############\\
		final JLabel singleplayer = new JLabel();
		singleplayer.setBounds(0, 227, 320, 60);
		singleplayer.setBackground(Color.GRAY);
		singleplayer.setForeground(Color.WHITE);
		singleplayer.setFont(new Font("Helvetica Inserat LT Std", Font.PLAIN,
				50));
		singleplayer.setIcon(new ImageIcon(
				"Textures/Menu/SingleplayerTitleBG.png"));
		singleplayer.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				singleplayer.setForeground(Color.LIGHT_GRAY);
			}

			public void mouseExited(MouseEvent evt) {
				singleplayer.setForeground(Color.WHITE);
			}

			public void mouseClicked(MouseEvent event) {
				multiplayerActive = false;
				MainMenu.setVisible(false);
				teamPickPanel.setVisible(true);
			}
		});
		MainMenu.add(singleplayer);

		// ##############\\
		// Multiplayer \\
		// ##############\\
		final JLabel multiplayer = new JLabel();
		multiplayer.setForeground(Color.WHITE);
		multiplayer
				.setFont(new Font("Helvetica Inserat LT Std", Font.PLAIN, 50));
		multiplayer.setIcon(new ImageIcon(
				"Textures/Menu/MultiplayerTitleBG.png"));
		multiplayer.setBounds(0, 326, 320, 60);
		multiplayer.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				multiplayer.setForeground(Color.LIGHT_GRAY);
			}

			public void mouseExited(MouseEvent evt) {
				multiplayer.setForeground(Color.WHITE);
			}

			public void mouseClicked(MouseEvent event) {
				multiplayerActive = true;
				MainMenu.setVisible(false);
				multiplayerPanel.setVisible(true);
			}
		});
		MainMenu.add(multiplayer);

		// ##############\\
		// Options \\
		// ##############\\
		final JLabel options = new JLabel();
		options.setForeground(Color.WHITE);
		options.setFont(new Font("Helvetica Inserat LT Std", Font.PLAIN, 50));
		options.setIcon(new ImageIcon("Textures/Menu/OptionsTitleBG.png"));
		options.setBounds(0, 425, 320, 60);
		options.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				options.setForeground(Color.LIGHT_GRAY);
			}

			public void mouseExited(MouseEvent evt) {
				options.setForeground(Color.WHITE);
			}

			public void mouseClicked(MouseEvent event) {
				System.out.println("Options TODO");

			}
		});
		MainMenu.add(options);

		// ##############\\
		// BACKGROUND \\
		// ##############\\
		JLabel menuBackground = new JLabel();
		menuBackground.setIcon(new ImageIcon("Textures/Menu/Test_BG.jpg"));
		menuBackground.setBounds(0, 0, 794, 571);
		MainMenu.add(menuBackground);
	}

	/**
	 * Team pick panel To pick a team to play as (Red/Blue)
	 */
	private void teamPickPanel() {
		teamPickPanel = new JPanel();
		teamPickPanel.setBounds(0, 0, 794, 571);
		teamPickPanel.setBackground(Color.BLACK);
		frame.getContentPane().add(teamPickPanel);
		teamPickPanel.setLayout(null);
		teamPickPanel.setVisible(false);

		// Title on page
		JLabel titleSingle = new JLabel();
		titleSingle.setIcon(new ImageIcon("Textures/Menu/PickATeam.png"));
		titleSingle.setBounds(20, 31, 764, 89);
		teamPickPanel.add(titleSingle);

		// ##############\\
		// Blue Team \\
		// ##############\\
		final JLabel blueTeam = new JLabel();
		blueTeam.setBounds(0, 420, 320, 60);
		blueTeam.setBackground(Color.GRAY);
		blueTeam.setForeground(Color.WHITE);
		blueTeam.setFont(new Font("Helvetica Inserat LT Std", Font.PLAIN, 50));
		blueTeam.setIcon(new ImageIcon("Textures/Menu/BlueTeamTitleBG.png"));
		blueTeam.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				blueTeam.setForeground(Color.LIGHT_GRAY);
			}

			public void mouseExited(MouseEvent evt) {
				blueTeam.setForeground(Color.WHITE);
			}

			public void mouseClicked(MouseEvent event) {
				setPlayerPickBG("Blue");
				team = "Blue";
				playerPickPanel.setVisible(true);
				teamPickPanel.setVisible(false);
			}
		});
		teamPickPanel.add(blueTeam);

		// ##############\\
		// Red Team \\
		// ##############\\
		final JLabel redTeam = new JLabel();
		redTeam.setBounds(474, 420, 320, 60);
		redTeam.setIcon(new ImageIcon("Textures/Menu/RedTeamTitleBG.png"));
		redTeam.setBackground(Color.GRAY);
		redTeam.setForeground(Color.WHITE);
		redTeam.setFont(new Font("Helvetica Inserat LT Std", Font.PLAIN, 50));
		redTeam.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				redTeam.setForeground(Color.LIGHT_GRAY);
			}

			public void mouseExited(MouseEvent evt) {
				redTeam.setForeground(Color.WHITE);
			}

			public void mouseClicked(MouseEvent event) {
				setPlayerPickBG("Red");
				team = "Red";
				playerPickPanel.setVisible(true);
				teamPickPanel.setVisible(false);
			}
		});
		teamPickPanel.add(redTeam);

		// ##############\\
		// Return \\
		// ##############\\
		returnButton = new JLabel();
		returnButton.setBounds(0, 491, 320, 60);
		returnButton.setIcon(new ImageIcon("Textures/Menu/BackTitleBG.png"));
		returnButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				MainMenu.setVisible(true);
				teamPickPanel.setVisible(false);
			}
		});
		teamPickPanel.add(returnButton);

		// ##############\\
		// Background \\
		// ##############\\
		JLabel singleBackground = new JLabel();
		singleBackground.setBounds(0, 0, 794, 571);
		singleBackground.setIcon(new ImageIcon("Textures/Menu/Team_Pick.jpg"));
		teamPickPanel.add(singleBackground);

	}

	/**
	 * Character pick panel To pick which character to play as
	 */
	private void charPickPanel() {

		playerPickPanel = new JPanel();
		playerPickPanel.setBounds(0, 0, 794, 571);
		frame.getContentPane().add(playerPickPanel);
		playerPickPanel.setLayout(null);
		playerPickPanel.setVisible(false);

		JLabel sniperTitle = new JLabel("");
		sniperTitle.setBounds(75, 180, 125, 50);
		sniperTitle.setIcon(new ImageIcon("Textures/Menu/SniperTitleBG.png"));
		playerPickPanel.add(sniperTitle);

		sniper = new JButton();
		sniper.setBounds(75, 230, 125, 100);
		sniper.setBackground(Color.BLACK);
		sniper.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
			}

			public void mouseExited(MouseEvent evt) {
			}

			public void mouseClicked(MouseEvent event) {
				type = "Sniper";
				sniper.setBackground(Color.WHITE);
				heavy.setBackground(Color.BLACK);
				assult.setBackground(Color.BLACK);
				shotgun.setBackground(Color.BLACK);
			}
		});
		playerPickPanel.add(sniper);

		JLabel assaultTitle = new JLabel("");
		assaultTitle.setBounds(240, 180, 125, 50);
		assaultTitle.setIcon(new ImageIcon("Textures/Menu/AssaultTitleBG.png"));
		playerPickPanel.add(assaultTitle);

		assult = new JButton();
		assult.setBounds(240, 230, 125, 100);
		assult.setBackground(Color.BLACK);
		assult.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
			}

			public void mouseExited(MouseEvent evt) {
			}

			public void mouseClicked(MouseEvent event) {
				type = "Assault";
				sniper.setBackground(Color.BLACK);
				heavy.setBackground(Color.BLACK);
				assult.setBackground(Color.WHITE);
				shotgun.setBackground(Color.BLACK);
			}
		});
		playerPickPanel.add(assult);

		JLabel heavyTitle = new JLabel("");
		heavyTitle.setBounds(415, 180, 125, 50);
		heavyTitle.setIcon(new ImageIcon("Textures/Menu/HeavyTitleBG.png"));
		playerPickPanel.add(heavyTitle);

		heavy = new JButton();
		heavy.setBounds(415, 230, 125, 100);
		heavy.setBackground(Color.BLACK);
		heavy.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
			}

			public void mouseExited(MouseEvent evt) {
			}

			public void mouseClicked(MouseEvent event) {
				type = "Heavy";
				System.out.println(type);
				sniper.setBackground(Color.BLACK);
				heavy.setBackground(Color.WHITE);
				assult.setBackground(Color.BLACK);
				shotgun.setBackground(Color.BLACK);
			}
		});
		playerPickPanel.add(heavy);

		JLabel shotgunTitle = new JLabel("");
		shotgunTitle.setBounds(575, 180, 125, 50);
		shotgunTitle.setIcon(new ImageIcon(
				"Textures/Menu/ShotgunnerTitleBG.png"));
		playerPickPanel.add(shotgunTitle);

		shotgun = new JButton();
		shotgun.setBounds(575, 230, 125, 100);
		shotgun.setBackground(Color.BLACK);
		shotgun.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
			}

			public void mouseExited(MouseEvent evt) {
			}

			public void mouseClicked(MouseEvent event) {
				type = "Shotgunner";
				System.out.println(type);
				sniper.setBackground(Color.BLACK);
				heavy.setBackground(Color.BLACK);
				assult.setBackground(Color.BLACK);
				shotgun.setBackground(Color.WHITE);
			}
		});
		playerPickPanel.add(shotgun);

		JLabel playerPickTitle = new JLabel();
		playerPickTitle.setIcon(new ImageIcon("Textures/Menu/PickAClass.png"));
		playerPickTitle.setBounds(20, 31, 764, 89);
		playerPickPanel.add(playerPickTitle);

		JLabel returnTeam = new JLabel("");
		returnTeam.setBounds(0, 491, 320, 60);
		returnTeam.setIcon(new ImageIcon("Textures/Menu/BackTitleBG.png"));
		returnTeam.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				teamPickPanel.setVisible(true);
				playerPickPanel.setVisible(false);
				sniper.setBackground(Color.BLACK);
				heavy.setBackground(Color.BLACK);
				assult.setBackground(Color.BLACK);
				shotgun.setBackground(Color.BLACK);
				type = "";
			}
		});
		playerPickPanel.add(returnTeam);

		JLabel play = new JLabel("");
		play.setBounds(474, 491, 320, 60);
		play.setIcon(new ImageIcon("Textures/Menu/PlayTitleBG.png"));
		play.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (!type.equals("") && multiplayerActive) {
					new GameGui(type, team, user, ip).start();
					frame.dispose();
				} else if (!type.equals("") && !multiplayerActive) {
					new ServerEngine(20, 25, "BloodVally.txt");
					new GameGui(type, team, "localHost", "127.0.0.1").start();
				}
			}
		});
		playerPickPanel.add(play);
		playerPickBG = new JLabel();
		playerPickBG.setBounds(0, 0, 794, 571);
		playerPickPanel.add(playerPickBG);
	}

	/**
	 * Multiplayer panel Contains all the set up for hosting and joining games
	 */
	private void multiPanel() {
		final JLabel join = new JLabel();
		final JLabel host = new JLabel();
		multiplayerPanel = new JPanel();
		multiplayerPanel.setBounds(0, 0, 794, 571);
		frame.getContentPane().add(multiplayerPanel);
		multiplayerPanel.setLayout(null);
		multiplayerPanel.setVisible(false);

		// TITLE OF PAGE
		JLabel multiTitle = new JLabel();
		multiTitle.setIcon(new ImageIcon("Textures/Menu/MultiplayerTitle.png"));
		multiTitle.setBounds(20, 31, 764, 89);
		multiplayerPanel.add(multiTitle);

		// ###############
		// JOIN PAGE
		// ###############

		// USER NAME
		final JTextField username = new JTextField();
		username.setBounds(150, 180, 450, 60);
		username.setFont(new Font("Verdana", Font.PLAIN, 45));
		username.setVisible(false);
		username.setText("Enter Username");
		username.setBackground(Color.BLACK);
		username.setForeground(Color.WHITE);
		username.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (username.getText().equals("Enter Username")
						|| username.getText().equals("Need Username")) {
					username.setText("");
				}
			}

			public void focusLost(FocusEvent e) {
				if (username.getText().matches("")) {
					username.setText("Enter Username");
				}
			}
		});
		multiplayerPanel.add(username);

		// IP ADDRESS FIELD
		final JTextField ipAddress = new JTextField();
		ipAddress.setBounds(150, 280, 450, 60);
		ipAddress.setFont(new Font("Verdana", Font.PLAIN, 45));
		ipAddress.setVisible(false);
		ipAddress.setText("Enter IP Address");
		ipAddress.setBackground(Color.BLACK);
		ipAddress.setForeground(Color.WHITE);
		ipAddress.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (ipAddress.getText().equals("Enter IP Address")
						|| ipAddress.getText().equals("Incorrect IP")) {
					ipAddress.setText("");
					ipAddress.setBackground(Color.BLACK);
				}
			}

			public void focusLost(FocusEvent e) {
				if (ipAddress.getText().equals("")) {
					ipAddress.setText("Enter IP Address");
				}
			}
		});
		multiplayerPanel.add(ipAddress);

		// CONNECT BUTTON
		final JLabel connect = new JLabel();
		connect.setIcon(new ImageIcon("Textures/Menu/ConnectTitleBG.png"));
		connect.setBounds(474, 491, 320, 60);
		connect.setVisible(false);
		connect.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				System.out.println(username.getText());
				if (ipAddress.getText().matches(
						"[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")
						&& !username.getText().equals("")
						&& !username.getText().equals("Enter Username")
						&& !username.getText().equals("Need Username")) {
					user = username.getText();
					ip = ipAddress.getText();
					join.setVisible(true);
					host.setVisible(true);
					ipAddress.setVisible(false);
					ipAddress.setText("");
					connect.setVisible(false);
					username.setVisible(false);
					username.setText("");
					multiplayerPanel.setVisible(false);
					teamPickPanel.setVisible(true);
					returnButton.setVisible(false);
				}
				if (!ipAddress.getText().matches(
						"[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
					ipAddress.setBackground(Color.RED);
					ipAddress.setText("Incorrect IP");
				}
				if (username.getText().equals("")
						|| username.getText().equals("Enter Username")
						|| username.getText().equals("Need Username")) {
					username.setBackground(Color.RED);
					username.setText("Need Username");
				}
			}
		});
		multiplayerPanel.add(connect);

		// ##########
		// HOST PAGE
		// ##########
		final JLabel multiReturn = new JLabel();
		// bots picked
		final JLabel botsLable = new JLabel();
		botsLable.setIcon(new ImageIcon("Textures/Menu/BotsTitleBG.png"));
		botsLable.setBounds(0, 180, 320, 60);
		botsLable.setVisible(false);
		multiplayerPanel.add(botsLable);

		final JLabel mapPick = new JLabel();
		mapPick.setIcon(new ImageIcon("Textures/Menu/MapTitleBG.png"));
		mapPick.setBounds(0, 280, 320, 60);
		mapPick.setVisible(false);
		multiplayerPanel.add(mapPick);

		final JLabel gameModePick = new JLabel();
		gameModePick.setIcon(new ImageIcon("Textures/Menu/ScoreTitleBG.png"));
		gameModePick.setBounds(0, 380, 320, 60);
		gameModePick.setVisible(false);
		multiplayerPanel.add(gameModePick);

		String[] botNum = new String[101];
		for (int i = 0; i < 101; i++) {
			botNum[i] = Integer.toString(i);
		}
		final JComboBox<String> bots = new JComboBox<String>(botNum);
		bots.setBounds(320, 180, 280, 60);
		bots.setFont(new Font("Helvetica Inserat LT Std", Font.PLAIN, 18));
		bots.setVisible(false);
		bots.setBackground(Color.BLACK);
		bots.setForeground(Color.WHITE);
		bots.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				numOfAi = bots.getSelectedItem().toString();
			}
		});
		multiplayerPanel.add(bots);

		File folder = new File("Maps");
		File[] listOfFiles = folder.listFiles();
		String[] mapoption = new String[listOfFiles.length];
		mapoption[0] = "Random";
		for (int i = 1; i < listOfFiles.length; i++) {
			if (!listOfFiles[i].toString()
					.substring(5, listOfFiles[i].toString().length() - 4)
					.equals(".svn")) {
				mapoption[i] = listOfFiles[i].toString().substring(5,
						listOfFiles[i].toString().length() - 4);
			}
		}
		final JComboBox<String> maps = new JComboBox<String>(mapoption);
		maps.setBounds(320, 280, 280, 60);
		maps.setVisible(false);
		maps.setFont(new Font("Helvetica Inserat LT Std", Font.PLAIN, 18));
		maps.setBackground(Color.BLACK);
		maps.setForeground(Color.WHITE);
		maps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				map = maps.getSelectedItem().toString();
			}
		});
		multiplayerPanel.add(maps);

		String[] scoreArray = new String[25];
		for (int i = 0; i < 25; i++) {
			scoreArray[i] = Integer.toString((i + 1) * 20);
		}
		final JComboBox<String> scorePicker = new JComboBox<String>(scoreArray);
		scorePicker.setBounds(320, 380, 280, 60);
		scorePicker.setVisible(false);
		scorePicker
				.setFont(new Font("Helvetica Inserat LT Std", Font.PLAIN, 18));
		scorePicker.setBackground(Color.BLACK);
		scorePicker.setForeground(Color.WHITE);
		scorePicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				scoreLimit = scorePicker.getSelectedItem().toString();
			}
		});
		multiplayerPanel.add(scorePicker);

		// CONNECT BUTTON
		final JLabel hostButton = new JLabel();
		hostButton.setIcon(new ImageIcon("Textures/Menu/HostTitleBG.png"));
		hostButton.setBounds(474, 491, 320, 60);
		hostButton.setVisible(false);
		hostButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				multiReturn.setVisible(false);
				ip = "127.0.0.1";
				ipAddress.setVisible(true);
				ipAddress.setText("127.0.0.1");
				ipAddress.setEnabled(false);
				botsLable.setVisible(false);
				bots.setVisible(false);
				mapPick.setVisible(false);
				gameModePick.setVisible(false);
				maps.setVisible(false);
				scorePicker.setVisible(false);
				connect.setVisible(true);
				username.setVisible(true);
				username.setText("Enter Username");
				hostButton.setVisible(false);

				if (map.equals("Random")) {
					boolean svnFileRemoval = true;
					while (svnFileRemoval) {
						File output;
						File file = new File("Maps/");
						File[] ranFile = file.listFiles();
						output = new File(
								ranFile[(int) (Math.random() * ranFile.length)]
										.toString());
						map = output.getName();
						System.out.println(map);
						if (!map.equals(".svn")) {
							svnFileRemoval = false;
						}
					}
				} else {
					map += ".txt";
				}
				new ServerEngine(Integer.parseInt(numOfAi) * 2, Integer
						.parseInt(scoreLimit), map);
			}
		});
		multiplayerPanel.add(hostButton);

		// ###########
		// MAIN PAGE
		// ###########

		// Host Button
		host.setFont(new Font("Helvetica Inserat LT Std", Font.PLAIN, 50));
		host.setIcon(new ImageIcon("Textures/Menu/HostTitleBG.png"));
		host.setBounds(0, 227, 320, 60);
		host.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				join.setVisible(false);
				host.setVisible(false);
				hostButton.setVisible(true);
				botsLable.setVisible(true);
				bots.setVisible(true);
				mapPick.setVisible(true);
				maps.setVisible(true);
				scorePicker.setVisible(true);
				gameModePick.setVisible(true);
				hosting = true;
			}
		});
		multiplayerPanel.add(host);

		// Join Button
		join.setForeground(Color.WHITE);
		join.setFont(new Font("Helvetica Inserat LT Std", Font.PLAIN, 50));
		join.setIcon(new ImageIcon("Textures/Menu/JoinTitleBG.png"));
		join.setBounds(0, 326, 320, 60);
		join.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				join.setVisible(false);
				host.setVisible(false);
				ipAddress.setVisible(true);
				connect.setVisible(true);
				username.setVisible(true);
			}
		});
		multiplayerPanel.add(join);

		// Return Button

		multiReturn.setBounds(0, 491, 320, 60);
		multiReturn.setIcon(new ImageIcon("Textures/Menu/BackTitleBG.png"));
		multiReturn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (connect.isVisible() || hostButton.isVisible()) {
					join.setVisible(true);
					host.setVisible(true);
					ipAddress.setVisible(false);
					ipAddress.setText("Enter IP Address");
					username.setText("Enter Username");
					ipAddress.setBackground(Color.BLACK);
					username.setBackground(Color.BLACK);
					connect.setVisible(false);
					username.setVisible(false);
					hostButton.setVisible(false);
					botsLable.setVisible(false);
					bots.setVisible(false);
					mapPick.setVisible(false);
					maps.setVisible(false);
					scorePicker.setVisible(false);
					gameModePick.setVisible(false);
					ipAddress.setEnabled(true);
					hosting = false;
					user = "";
					ip = "";
				} else {
					multiplayerActive = false;
					MainMenu.setVisible(true);
					multiplayerPanel.setVisible(false);
				}
			}
		});
		multiplayerPanel.add(multiReturn);

		// BACKGROUND
		JLabel multiplayerBG = new JLabel();
		multiplayerBG.setBounds(0, 0, 794, 571);
		multiplayerBG.setIcon(new ImageIcon("Textures/Menu/MultiPlayerBG.jpg"));
		multiplayerPanel.add(multiplayerBG);

	}

	/**
	 * Options panel To change game options
	 */
	private void optionsPanel() {

	}

	/**
	 * Sets the colour of the characters to pick based of team choice, and also
	 * the background image
	 * 
	 * @param team
	 *            The team picked
	 */
	private void setPlayerPickBG(String team) {
		playerPickBG.setIcon(new ImageIcon("Textures/Menu/" + team
				+ "_Team.jpg"));

		ImageIcon sniperTemp = new ImageIcon("Textures/Characters/Sniper_"
				+ team + ".png");
		Image stuff = sniperTemp.getImage();
		Image newImage = stuff.getScaledInstance(150, 150,
				java.awt.Image.SCALE_SMOOTH);
		sniper.setIcon(new ImageIcon(newImage));

		ImageIcon assualtTemp = new ImageIcon("Textures/Characters/Assault_"
				+ team + ".png");
		Image stuff2 = assualtTemp.getImage();
		Image newImage2 = stuff2.getScaledInstance(150, 150,
				java.awt.Image.SCALE_SMOOTH);
		assult.setIcon(new ImageIcon(newImage2));

		ImageIcon heavyTemp = new ImageIcon("Textures/Characters/Heavy_" + team
				+ ".png");
		Image stuff3 = heavyTemp.getImage();
		Image newImage3 = stuff3.getScaledInstance(150, 150,
				java.awt.Image.SCALE_SMOOTH);
		heavy.setIcon(new ImageIcon(newImage3));

		ImageIcon shotgunTemp = new ImageIcon("Textures/Characters/Shotgunner_"
				+ team + ".png");
		Image stuff4 = shotgunTemp.getImage();
		Image newImage4 = stuff4.getScaledInstance(150, 150,
				java.awt.Image.SCALE_SMOOTH);
		shotgun.setIcon(new ImageIcon(newImage4));
	}
}
