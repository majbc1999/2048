package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ai.Computer;
import basic.Game;

@SuppressWarnings("serial")
public class Frame extends JFrame implements ActionListener{
	
	private Panel panel;
	
	private JLabel status;
	private JLabel kocka;
	
	private JMenuItem menuClassic, menuEndless, menuComputer, menuTraining;
	private JMenuItem menuGameSettings, menuInterface;
	private JButton razveljavi;
	
	public int velikost;
	
	public Frame(Color[] colorScheme, Game game) {
		super();

		this.setTitle("2048");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		// menu bar
		JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        
        JMenu menuNewGame = addMenu(menubar, "New Game");
		JMenu menuSettings = addMenu(menubar, "Settings");

		// submenus
        menuClassic = addMenuItem(menuNewGame, "Classic");
		menuEndless = addMenuItem(menuNewGame, "Endless");
		menuComputer = addMenuItem(menuNewGame, "Computer");
		menuTraining = addMenuItem(menuNewGame, "Training");
		menuGameSettings = addMenuItem(menuSettings, "Game Settings");
		menuInterface = addMenuItem(menuSettings, "Interface");

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
	
	// method that adds a new main menu
	public JMenu addMenu(JMenuBar menubar, String title) {
        JMenu menu = new JMenu(title);
        menubar.add(menu);
        return menu;
    }

	// method that adds a submenu
    public JMenuItem addMenuItem(JMenu menu, String title) {
        JMenuItem menuitem = new JMenuItem(title);
        menu.add(menuitem);
        menuitem.addActionListener(this);
        return menuitem;
    }

	
	// automatically playes moves of the computer
	public void play() {
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
		
		this.panel = new Panel(colorSchemeInit, newGame);
		panel.setVisible(true);
		panel.repaint();

		while (this.panel.game.status()) {
			Computer comp = new Computer(this.panel.game);
			comp.playRandomMove(); 
			this.panel.game.board = comp.game.board;
			this.panel.game.print();
			this.panel.game.spawnRandomNumber();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				System.out.println("Napaka");
			}
			this.panel.repaint();
		}
	}
    
    // action listener
    @Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuComputer) {
			play();
		}
	}
}
