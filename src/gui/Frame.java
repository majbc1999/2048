package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.UIManager;

import basic.Game;

@SuppressWarnings("serial")
public class Frame extends JFrame implements ActionListener {
	
	public Panel panel;

	private JMenuBar menuBar;
	private JMenu menu, menuAlgorithm, menuNewSettings;
	
	private JMenuItem menuClassic, menuEndless, menuPlayer, menuComputer;
	private JMenuItem menuRandomAlgorithm, menuSimulator, menuEmptySpacesAlgorithm, menuSimulatorDynamic, menuPositionEvalAlgorithm;

	private JMenuItem menuColorScheme, menuSize;
	
	private JMenuItem game3, game4, game5, game6, game8, classicScheme, darkScheme, CBScheme;

	private JButton undo;
	
	public Frame(Color[] colorScheme, Game game) {
		super();

		this.setTitle("2048");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		// create the menu bar.
		menuBar = new JMenuBar();

		// build the first menu.
		menu = new JMenu("New Game");
		menuBar.add(menu);

		menuNewSettings = new JMenu("Players");
		menuBar.add(menuNewSettings);

		// a group of JMenuItems
		menuClassic = new JMenuItem("Classic", new ImageIcon("static/rsz_finish_flag.png"));
		menu.add(menuClassic);

		menuEndless = new JMenuItem("Endless", new ImageIcon("static/rsz_infinity.png"));
		menu.add(menuEndless);
		
		menuPlayer = new JMenuItem("Player", new ImageIcon("static/rsz_player.png"));
		menuPlayer.setSelected(true);
		menuNewSettings.add(menuPlayer);

		menuComputer = new JMenuItem("Computer", new ImageIcon("static/rsz_ai.png"));
		menuNewSettings.add(menuComputer);

		// size and colors
		game3 = new JMenuItem("3x3");
		game4 = new JMenuItem("4x4");
		game5 = new JMenuItem("5x5");
		game6 = new JMenuItem("6x6");
		game8 = new JMenuItem("8x8");
		classicScheme = new JMenuItem("Classic 2048");
		darkScheme = new JMenuItem("Dark Blue Theme");
		CBScheme = new JMenuItem("Colorblind Mode");

		// submenu for algorithm picker
		menuAlgorithm = new JMenu("Computer Algorithm");

		menuRandomAlgorithm = new JMenuItem("Random Moves");
		menuRandomAlgorithm.setOpaque(true);
		menuRandomAlgorithm.setBackground(new Color(163,184,204));	
		menuAlgorithm.add(menuRandomAlgorithm);

		menuSimulator = new JMenuItem("Simulator 2000");
		menuAlgorithm.add(menuSimulator);

		menuEmptySpacesAlgorithm = new JMenuItem("Minimum Empty Spaces");
		menuAlgorithm.add(menuEmptySpacesAlgorithm);

		menuSimulatorDynamic = new JMenuItem("Dynamic Simulator");
		menuAlgorithm.add(menuSimulatorDynamic);

		menuPositionEvalAlgorithm = new JMenuItem("Position Evaluator");
		menuAlgorithm.add(menuPositionEvalAlgorithm);

		menuNewSettings.addSeparator();
		menuNewSettings.add(menuAlgorithm);

		// build second and third menu in the menu bar.
		menuSize = new JMenu("Size");
		menuSize.add(game3);
		menuSize.add(game4);
		menuSize.add(game5);
		menuSize.add(game6);
		menuSize.add(game8);
		menuBar.add(menuSize);

		menuColorScheme = new JMenu("Colors");
		menuColorScheme.add(classicScheme);
		menuColorScheme.add(darkScheme);
		menuColorScheme.add(CBScheme);
		menuBar.add(menuColorScheme);

		// undo button added
		menuBar.add(Box.createGlue());
		undo = new JButton("Undo");
		undo.setPreferredSize(new Dimension(75, 31));
		undo.setFocusable(false);
		menuBar.add(new JSeparator());
		menuBar.add(new JSeparator());
		menuBar.add(new JSeparator());
		menuBar.add(new JSeparator());
		menuBar.add(undo);
		menuBar.add(new JSeparator());

		setJMenuBar(menuBar);

		// action listeners for all menu items
		menuClassic.addActionListener(this); 
		menuEndless.addActionListener(this);
		menuPlayer.addActionListener(this);
		menuComputer.addActionListener(this);
		menuRandomAlgorithm.addActionListener(this); 
		menuSimulator.addActionListener(this);
		menuSimulatorDynamic.addActionListener(this);
		menuEmptySpacesAlgorithm.addActionListener(this);
		menuPositionEvalAlgorithm.addActionListener(this);

		menuSize.addActionListener(this);
		menuColorScheme.addActionListener(this);
		game3.addActionListener(this);
		game4.addActionListener(this);
		game5.addActionListener(this);
		game6.addActionListener(this);
		game8.addActionListener(this);
		classicScheme.addActionListener(this);
		darkScheme.addActionListener(this);
		CBScheme.addActionListener(this);
		undo.addActionListener(this);

		// panel and layouts
        panel = new Panel(colorScheme, game);
        
        GridBagConstraints panel_layout = new GridBagConstraints();
		panel_layout.gridx = 0;
		panel_layout.gridy = 0;
		panel_layout.fill = GridBagConstraints.BOTH;

		panel_layout.weightx = 1.0;
		panel_layout.weighty = 1.0;
		getContentPane().add(panel, panel_layout);
	}

    
    // action listener
    @Override
	public void actionPerformed(ActionEvent e) {
 		if (e.getSource() == menuRandomAlgorithm) {
			panel.lastComputer = "random";
			menuEmptySpacesAlgorithm.setOpaque(false);
			menuSimulatorDynamic.setOpaque(false);
			menuSimulator.setOpaque(false);
			menuPositionEvalAlgorithm.setOpaque(false);
			menuRandomAlgorithm.setOpaque(true);
			menuRandomAlgorithm.setBackground(new Color(163,184,204));
		} 

		if (e.getSource() == menuEmptySpacesAlgorithm) {
			panel.lastComputer = "emptyspaces";
			menuRandomAlgorithm.setOpaque(false);
			menuSimulator.setOpaque(false);
			menuSimulatorDynamic.setOpaque(false);
			menuPositionEvalAlgorithm.setOpaque(false);
			menuEmptySpacesAlgorithm.setOpaque(true);
			menuEmptySpacesAlgorithm.setBackground(new Color(163,184,204));
		} 
		
		if (e.getSource() == menuSimulator) {
			panel.lastComputer = "simulate";
			menuRandomAlgorithm.setOpaque(false);
			menuEmptySpacesAlgorithm.setOpaque(false);
			menuSimulatorDynamic.setOpaque(false);
			menuPositionEvalAlgorithm.setOpaque(false);
			menuSimulator.setOpaque(true);
			menuSimulator.setBackground(new Color(163,184,204));
		}

		if (e.getSource() == menuSimulatorDynamic) {
			panel.lastComputer = "simulateDyn";
			menuRandomAlgorithm.setOpaque(false);
			menuEmptySpacesAlgorithm.setOpaque(false);
			menuSimulator.setOpaque(false);
			menuPositionEvalAlgorithm.setOpaque(false);
			menuSimulatorDynamic.setOpaque(true);
			menuSimulatorDynamic.setBackground(new Color(163,184,204));
		}

		if (e.getSource() == menuPositionEvalAlgorithm) {
			panel.lastComputer = "positionEval";
			menuRandomAlgorithm.setOpaque(false);
			menuEmptySpacesAlgorithm.setOpaque(false);
			menuSimulator.setOpaque(false);
			menuSimulatorDynamic.setOpaque(false);
			menuPositionEvalAlgorithm.setOpaque(true);
			menuPositionEvalAlgorithm.setBackground(new Color(163,184,204));
		}

		if (e.getSource() == menuClassic) {		
			Game newGame = new Game(panel.game.N);
			newGame.gameMode = true;

			newGame.spawnRandomNumber();

			panel.game = newGame;
			panel.repaint();
		}

		if (e.getSource() == menuEndless) {
			Game newGame = new Game(panel.game.N);
			newGame.gameMode = false;

			newGame.spawnRandomNumber();

			panel.game = newGame;
			panel.repaint();
		}

		if (e.getSource() == classicScheme) {
			Color[] classicColorScheme = new Color[12];

			classicColorScheme[0] = new Color(255, 255, 255);  // color of    2
			classicColorScheme[1] = new Color(252, 248, 172);   // color of    4
			classicColorScheme[2] = new Color(255, 198, 25);   // color of    8
			classicColorScheme[3] = new Color(253, 163, 0);    // color of   16
			classicColorScheme[4] = new Color(250, 132, 26);   // color of   32
			classicColorScheme[5] = new Color(96, 214, 198);     // color of   64
			classicColorScheme[6] = new Color(103, 124, 245);   // color of  128
			classicColorScheme[7] = new Color(0, 77, 169);  // color of  256
			classicColorScheme[8] = new Color(2, 64, 137);     // color of  512
			classicColorScheme[9] = new Color(10,10,10);  // color of 1024
			classicColorScheme[10] = new Color(10,10,10);      // color of 2048
			classicColorScheme[11] = new Color(10,10,10);      // color of 4096

			panel.colors = new Hashtable<Integer,Color>();
			panel.colors.put(0, Color.WHITE);
			panel.colors.put(2, classicColorScheme[0]);
			panel.colors.put(4, classicColorScheme[1]);
			panel.colors.put(8, classicColorScheme[2]);
			panel.colors.put(16, classicColorScheme[3]);
			panel.colors.put(32, classicColorScheme[4]);
			panel.colors.put(64, classicColorScheme[5]);
			panel.colors.put(128, classicColorScheme[6]);
			panel.colors.put(256, classicColorScheme[7]);
			panel.colors.put(512, classicColorScheme[8]);
			panel.colors.put(1024, classicColorScheme[9]);
			panel.colors.put(2048, classicColorScheme[10]);
			panel.colors.put(4096, classicColorScheme[11]);
			panel.gridColor = Color.LIGHT_GRAY;
			panel.primaryFontColor = Color.BLACK;

			panel.secondaryFontColor = Color.WHITE;
			panel.setBackground(UIManager.getColor("Panel.background"));
			panel.theme = "classic";

			panel.repaint();
		}

		if (e.getSource() == CBScheme) {
			Color[] classicColorScheme = new Color[12];

			classicColorScheme[0] = new Color(242,242,242);  // color of    2
			classicColorScheme[1] = new Color(204,204,204);   // color of    4
			classicColorScheme[2] = new Color(153,153,153);   // color of    8
			classicColorScheme[3] = new Color(115,115,115);    // color of   16
			classicColorScheme[4] = new Color(77,77,77);   // color of   32
			classicColorScheme[5] = new Color(64,64,64);   // color of  64
			classicColorScheme[6] = new Color(38,38,38);     // color of   128
			classicColorScheme[7] = new Color(26,26,26);  // color of  256
			classicColorScheme[8] = new Color(18,18,18);     // color of  512
			classicColorScheme[9] = new Color(10,10,10);  // color of 1024
			classicColorScheme[10] = new Color(0, 0,0);      // color of 2048
			classicColorScheme[11] = new Color(10,10,10);      // color of 4096

			panel.colors = new Hashtable<Integer,Color>();
			panel.colors.put(0, Color.WHITE);
			panel.colors.put(2, classicColorScheme[0]);
			panel.colors.put(4, classicColorScheme[1]);
			panel.colors.put(8, classicColorScheme[2]);
			panel.colors.put(16, classicColorScheme[3]);
			panel.colors.put(32, classicColorScheme[4]);
			panel.colors.put(64, classicColorScheme[5]);
			panel.colors.put(128, classicColorScheme[6]);
			panel.colors.put(256, classicColorScheme[7]);
			panel.colors.put(512, classicColorScheme[8]);
			panel.colors.put(1024, classicColorScheme[9]);
			panel.colors.put(2048, classicColorScheme[10]);
			panel.colors.put(4096, classicColorScheme[11]);
			panel.gridColor = Color.LIGHT_GRAY;
			panel.primaryFontColor = Color.BLACK;

			panel.secondaryFontColor = Color.WHITE;
			panel.setBackground(new Color(255,255,255));
			panel.theme = "colorblind";

			panel.repaint();
		}

		if (e.getSource() == darkScheme) {
			Color[] darkColorScheme = new Color[12];

			darkColorScheme[0] = new Color(2, 7, 40);  // color of    2
			darkColorScheme[1] = new Color(8, 2, 60);   // color of    4
			darkColorScheme[2] = new Color(8, 18, 102);   // color of    8
			darkColorScheme[3] = new Color(15, 39, 163);    // color of   16
			darkColorScheme[4] = new Color(78, 47, 235);   // color of   32
			darkColorScheme[5] = new Color(102, 72, 250);     // color of   64
			darkColorScheme[6] = new Color(72, 137, 250);   // color of  128
			darkColorScheme[7] = new Color(109, 159, 247);  // color of  256
			darkColorScheme[8] = new Color(129, 165, 230);     // color of  512
			darkColorScheme[9] = new Color(161, 195, 255);  // color of 1024
		   	darkColorScheme[10] = new Color(77, 216, 255);      // color of 2048
	       	darkColorScheme[11] = new Color(128, 227, 255);      // color of 4096

			panel.colors = new Hashtable<Integer,Color>();
			panel.colors.put(0, Color.WHITE);
			panel.colors.put(2, darkColorScheme[0]);
			panel.colors.put(4, darkColorScheme[1]);
			panel.colors.put(8, darkColorScheme[2]);
			panel.colors.put(16, darkColorScheme[3]);
			panel.colors.put(32, darkColorScheme[4]);
			panel.colors.put(64, darkColorScheme[5]);
			panel.colors.put(128, darkColorScheme[6]);
			panel.colors.put(256, darkColorScheme[7]);
			panel.colors.put(512, darkColorScheme[8]);
			panel.colors.put(1024, darkColorScheme[9]);
			panel.colors.put(2048, darkColorScheme[10]);
			panel.colors.put(4096, darkColorScheme[11]);
			panel.gridColor = Color.DARK_GRAY;
			panel.primaryFontColor = Color.WHITE;

			panel.secondaryFontColor = Color.WHITE;
			panel.setBackground(Color.BLACK);
			panel.theme = "dark";
			panel.repaint();
		}

		if (e.getSource() == game3) {
			Game newGame = new Game(3);
			if (panel.game.gameMode) {
				newGame.gameMode = true;
			}
			else {
				newGame.gameMode = false;
			}
			newGame.spawnRandomNumber();
			panel.game = newGame;
			panel.repaint();
		}

		if (e.getSource() == game4) {
			Game newGame = new Game(4);
			if (panel.game.gameMode) {
				newGame.gameMode = true;
			}
			else {
				newGame.gameMode = false;
			}
			newGame.spawnRandomNumber();
			panel.game = newGame;
			panel.repaint();
		}

		if (e.getSource() == game5) {
			Game newGame = new Game(5);
			if (panel.game.gameMode) {
				newGame.gameMode = true;
			}
			else {
				newGame.gameMode = false;
			}
			newGame.spawnRandomNumber();
			panel.game = newGame;
			panel.repaint();
		}

		if (e.getSource() == game6) {
			Game newGame = new Game(6);
			if (panel.game.gameMode) {
				newGame.gameMode = true;
			}
			else {
				newGame.gameMode = false;
			}
			newGame.spawnRandomNumber();
			panel.game = newGame;
			panel.repaint();
		}

		if (e.getSource() == game8) {
			Game newGame = new Game(8);
			if (panel.game.gameMode) {
				newGame.gameMode = true;
			}
			else {
				newGame.gameMode = false;
			}
			newGame.spawnRandomNumber();
			panel.game = newGame;
			panel.repaint();
		}
	
		if (e.getSource() == menuPlayer) {
			panel.stopSwingworker = true;
			panel.repaint();
		}

		if (e.getSource() == menuComputer) {
			panel.stopSwingworker = false;
			panel.repaint();
			panel.play(panel.lastComputer);
		}

		if (e.getSource() == undo) {
			if (panel.stopSwingworker) {
				panel.game.undo();
				repaint();
			}
		}
	}
}
