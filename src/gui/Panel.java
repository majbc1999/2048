package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import basic.Game;


@SuppressWarnings("serial")
public class Panel extends JPanel implements MouseListener, KeyListener {
	
	// labels for numbers
	JLabel[][] labels;

	// dictionary for color scheme

	// current game
	Game game;
	
	// square width (and length)
	Hashtable<Integer,Color> colors = new Hashtable<Integer,Color>();

	// main constuctor
	public Panel(Color[] colorScheme, Game game) {

		// game
		this.game = game;

		// color scheme
		
		colors.put(0, Color.WHITE);
		colors.put(2, colorScheme[0]);
		colors.put(4, colorScheme[1]);
		colors.put(8, colorScheme[2]);
		colors.put(16, colorScheme[3]);
		colors.put(32, colorScheme[4]);
		colors.put(64, colorScheme[5]);
		colors.put(128, colorScheme[6]);
		colors.put(256, colorScheme[7]);
		colors.put(512, colorScheme[8]);
		colors.put(1024, colorScheme[9]);
		colors.put(2048, colorScheme[10]);
		colors.put(4096, colorScheme[11]);

		// labels
		this.labels = new JLabel[game.N][game.N];
		
		// enable mouse and key listeners
		addMouseListener(this); 
		addKeyListener(this);
		setFocusable(true);	
	}

	// preffered dimension of our panel
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	// line relative width
	private final static double LINE_WIDTH = 0.05;
		
	// width of a square
	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / (double) (game.N + 2) - 0.03 * (Math.min(getWidth(), getHeight()) / (double) (game.N + 2));
	}
	
	// space between lines and number squared
	private final static double PADDING = 0.05;
			
	private void paintNumbers(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double dx = (getWidth()/2.0)- ((game.N/2.0) * w);
		double dy = (getHeight()/2.0)- ((game.N/2.0) * w);

		double d = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING) + dx;
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING) + dy;
		
		Color clr = colors.getOrDefault(Integer.valueOf(game.board[j][i]), Color.BLACK);
		
		g2.setColor(clr);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.fillRoundRect((int)x, (int)y, (int)d , (int)d, 5, 5);
		g2.setColor(clr);
		g2.drawRoundRect((int)x, (int)y, (int)d , (int)d, 5, 5);
	}

	// painting
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		double w = squareWidth();
		double dx = (getWidth()/2.0)- ((game.N/2.0) * w);
		double dy = (getHeight()/2.0)- ((game.N/2.0) * w);
		
		// game grid
		g2.setColor(Color.LIGHT_GRAY);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		for (int i = 0; i <= game.N; i++) {
			g2.drawLine((int)(i * w + dx),
					    (int)(0 + dy),
					    (int)(i * w + dx),
					    (int)(game.N * w + dy));
		}
		for (int i = 0; i <= game.N; i++) {
			g2.drawLine((int)(0 + dx),
					    (int)(i * w + dy),
					    (int)(game.N * w + dx),
					    (int)(i * w + dy));
		}

		// painting numbers
		for (int i = 0; i < game.N; i++) {
			for (int j = 0; j < game.N; j++) {
				if (game.board[i][j] != 0) {
					paintNumbers(g2, j, i);
				}
			}
		}

		// setting and drawing labels

		// firstly remove all labels
		for (int i = 0; i < game.N; i++) {
			for (int j = 0; j < game.N; j++) {
				try {
					remove(labels[i][j]);
				}
				catch(Exception e) {
					continue;
				}
			}
		}

		// create all labels
		for (int i = 0; i < game.N; i++) {
			for (int j = 0; j < game.N; j++) {
				JLabel tempLabel = new JLabel(String.valueOf(game.board[i][j]), SwingConstants.CENTER);

				double d = w * (1.0 - LINE_WIDTH - 2.0 * PADDING);
				double x = w * (j + 0.5 * LINE_WIDTH + PADDING) + dx;
				double y = w * (i + 0.5 * LINE_WIDTH + PADDING) + dy;

				tempLabel.setBounds((int) x, (int) y, (int) d, (int) d);
				tempLabel.setFont(new Font("Arial", 0, 30));

				labels[i][j] = tempLabel;
			}
		}

		// spawn the labels on field
		for (int i = 0; i < game.N; i++) {
			for (int j = 0; j < game.N; j++) {
				if (Integer.valueOf(labels[i][j].getText()) != 0) {
					add(labels[i][j]);
				}
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		char key = e.getKeyChar();
		// key up
		if (key == 'w') {
			System.out.print("x");
			game.moveUp();
			game.spawnRandomNumber();
			repaint();
		}

		// keys down
		if (key == 's') {
			game.moveDown();
			game.spawnRandomNumber();
			repaint();
		}

		// keys left
		if (key == 'a') {
			game.moveLeft();
			game.spawnRandomNumber();
			repaint();
		}

		// key right
		if (key == 'd') {
			game.moveRight();
			game.spawnRandomNumber();
			repaint();
		}
    }
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
 
	@Override
	public void mousePressed(MouseEvent e) {		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
    }

	@Override
	public void keyReleased(KeyEvent e) {
    }
}