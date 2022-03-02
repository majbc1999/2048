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
    
    // action listener
    @Override
	public void actionPerformed(ActionEvent e) {

	}

}
