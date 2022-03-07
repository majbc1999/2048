package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import basic.Game;

@SuppressWarnings("serial")
public class Frame extends JFrame implements ActionListener{
	
	private Panel panel;
	
	private JLabel status;

	private JMenuBar menuBar;
	private JMenu menu, menuAlgorithm;
	
	private JMenuItem menuClassic, menuEndless, menuPlayer, menuComputer;
	private JMenuItem menuRandomAlgorithm, menuSimulator;
	private JMenuItem menuSettingsUI, menuSettingsGame;
	
	public int velikost;
	
	public Frame(Color[] colorScheme, Game game) {
		super();

		this.setTitle("2048");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("New Game");
		menuBar.add(menu);

		//a group of JMenuItems
		menuClassic = new JMenuItem("Classic", new ImageIcon("static/rsz_finish_flag.png"));
		menu.add(menuClassic);

		menuEndless = new JMenuItem("Endless", new ImageIcon("static/rsz_infinity.png"));
		menu.add(menuEndless);

		//a group of radio button menu items
		menu.addSeparator();
		
		menuPlayer = new JMenuItem("Player", new ImageIcon("static/rsz_player.png"));
		menuPlayer.setSelected(true);
		menu.add(menuPlayer);

		menuComputer = new JMenuItem("Computer", new ImageIcon("static/rsz_ai.png"));
		menu.add(menuComputer);

		//a submenu for algorithm picker
		menu.addSeparator();
		menuAlgorithm = new JMenu("Computer Algorithm");

		menuRandomAlgorithm = new JMenuItem("Random Moves");
		menuAlgorithm.add(menuRandomAlgorithm);

		menuSimulator = new JMenuItem("Simulator");
		menuAlgorithm.add(menuSimulator);

		menu.add(menuAlgorithm);

		//Build second menu in the menu bar.
		menu = new JMenu("Settings");
		menuSettingsUI = new JMenuItem("Interface Settings");
		menu.add(menuSettingsUI);

		menuSettingsGame = new JMenuItem("Game Settings");
		menu.add(menuSettingsGame);

		menuBar.add(menu);

		setJMenuBar(menuBar);



		// panel and layouts
        panel = new Panel(colorScheme, game);
        
        GridBagConstraints panel_layout = new GridBagConstraints();
		panel_layout.gridx = 0;
		panel_layout.gridy = 0;
		panel_layout.fill = GridBagConstraints.BOTH;

		panel_layout.weightx = 1.0;
		panel_layout.weighty = 1.0;
		getContentPane().add(panel, panel_layout);
		
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
	
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		status.setText("Start a new game!");		
	}
    
    // action listener
    @Override
	public void actionPerformed(ActionEvent e) {
/* 		if (e.getSource() == menuComputer) {

			Game newGame = new Game(panel.game.N);
			newGame.player = "random";
			newGame.spawnRandomNumber();
	
			// basic color palette (missing label colors)
			Color[] colorSchemeInit = new Color[12];
			colorSchemeInit[0] = new Color(255, 255, 255);  // color of    2
			colorSchemeInit[1] = new Color(252, 248, 172);   // color of    4
			colorSchemeInit[2] = new Color(255, 198, 25);   // color of    8
			colorSchemeInit[3] = new Color(253, 163, 0);    // color of   16
			colorSchemeInit[4] = new Color(250, 132, 26);   // color of   32
			colorSchemeInit[5] = new Color(96, 214, 198);     // color of   64
			colorSchemeInit[6] = new Color(103, 124, 245);   // color of  128
			colorSchemeInit[7] = new Color(0, 77, 169);  // color of  256
			colorSchemeInit[8] = new Color(2, 64, 137);     // color of  512
			colorSchemeInit[9] = new Color(10,10,10);  // color of 1024
			colorSchemeInit[10] = new Color(10,10,10);      // color of 2048
			colorSchemeInit[11] = new Color(10,10,10);      // color of 4096
			
			panel.game = newGame;
			panel.repaint();
			
			panel.play();
		} */
	}
}
