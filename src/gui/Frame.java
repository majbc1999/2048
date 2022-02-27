package gui;

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

@SuppressWarnings("serial")
public class Frame extends JFrame implements ActionListener{
	
	private Panel polje;
	
	private JLabel status;
	private JLabel kocka;
	
	private JMenuItem menuClassic, menuEndless, menuComputer, menuTraining;
	private JMenuItem menuGameSettings, menuInterface;
	private JButton razveljavi;
	
	public int velikost;
	
	public Frame() {
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

        polje = new Panel();
        
        GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;

		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
		
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
	
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		status.setText("Izberite igro!");
		
		kocka = new JLabel();
		kocka.setFont(new Font(kocka.getFont().getName(),
							    kocka.getFont().getStyle(),
							    20));
		
		GridBagConstraints kocka_layout = new GridBagConstraints();
		kocka_layout.gridx = 0;
		kocka_layout.gridy = 2;
		kocka_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(kocka, kocka_layout);
		
		kocka.setText("");
		
	}
	
	public JMenu addMenu(JMenuBar menubar, String title) {
        JMenu menu = new JMenu(title);
        menubar.add(menu);
        return menu;
    }

    public JMenuItem addMenuItem(JMenu menu, String title) {
        JMenuItem menuitem = new JMenuItem(title);
        menu.add(menuitem);
        menuitem.addActionListener(this);
        return menuitem;
    }
    
    
    @Override
	public void actionPerformed(ActionEvent e) {

	}

}
