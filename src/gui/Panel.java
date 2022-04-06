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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import basic.Game;


@SuppressWarnings("serial")
public class Panel extends JPanel implements MouseListener, KeyListener {
	
	// labels for numbers
	JLabel[][] labels;

	// current game
	Game game;
	
	// hashtable for colors
	Hashtable<Integer,Color> colors = new Hashtable<Integer,Color>();

	// images for game mode
	BufferedImage imageClassic;
	BufferedImage imageEndless;
	BufferedImage imageClassicInv;
	JLabel imageLabel;

	// label for score
	JLabel scoreLabel;

	// label for game over and game won
	JLabel gameOverLabel;
	JLabel gameWonLabel;

	// label for colors
	Color backgroundColor;
	Color gridColor;
	Color primaryFontColor;
	Color secondaryFontColor;
	Color zerosColor;

	String theme;

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

		this.gridColor = Color.LIGHT_GRAY;
		this.primaryFontColor = Color.BLACK;
		this.secondaryFontColor	= Color.WHITE;
		this.theme = "classic";

		// labels list
		this.labels = new JLabel[8][8];

		this.scoreLabel = new JLabel("Score: " + Integer.toString(game.score));
		
		// load all 3 pictures
		try {                
			imageClassic = ImageIO.read(new File("static/rsz_finish_flag.png"));
			imageEndless = ImageIO.read(new File("static/rsz_infinity.png"));
			imageClassicInv = ImageIO.read(new File("static/rsz_finish_flag_inverted.png"));
		} catch (IOException ex) {
		}
		
		this.imageLabel = new JLabel(new ImageIcon(imageClassic));

		// enable mouse and key listeners
		addMouseListener(this); 
		addKeyListener(this);
		setFocusable(true);	
	}

	// swingworker takes care of background;
	private static SwingWorker<Game, Void> worker = null;

	// automatically playes moves of the computer
	public void play(String alg) {					
		worker = new SwingWorker<Game, Void>() {
			
			@Override
			protected Game doInBackground() {
				
				if (alg.equals("random")) {
					game.playRandomMove();
				}
				else if (alg.equals("simulate")) {
					game.simulateMove(25);
				}
				else if (alg.equals("emptyspaces")) {
					game.playEmptySpaces();
				}
				return game;
			}

			@Override
			protected void done() {
				if (game.status() && !game.win()) {
					repaint();
					if (alg.equals("random")) {
						play("random");
					}
					else if (alg.equals("simulate")) {
						play("simulate");
					}
					else if (alg.equals("emptyspaces")) {
						play("emptyspaces");
					}
				}
				repaint();
			}
		};
		worker.execute();
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
		return Math.min(getWidth(), getHeight()) / (double) (game.N + 1) - 0.03 * (Math.min(getWidth(), getHeight()) / (double) (game.N + 1));
	}
	
	// space between lines and number squared
	private final static double PADDING = 0.05;
			
	// method that draws and fills rounded square, where the numbers are
	private void paintNumbers(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double dx = (getWidth()/2.0)- ((game.N/2.0) * w);
		double dy = (getHeight()/2.0)- ((game.N/2.0) * w);

		double d = w * (1.0 - LINE_WIDTH - 2.0 * PADDING);
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
		
		// game-over rectangle
		try {
			remove(gameOverLabel);
		}
		catch(Exception e) {
		}
		if (!game.status()) {
			gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
			gameOverLabel.setBounds(0, 10, getWidth() - 10, getHeight() - 10);
			gameOverLabel.setFont(new Font("Arial", 0, 50));
			gameOverLabel.setForeground(Color.YELLOW);
			add(gameOverLabel);
		}

		// game-won rectangle
		try {
			remove(gameWonLabel);
		}
		catch(Exception e) {
		}
		if (game.win()) {
			gameWonLabel = new JLabel("Game Won", SwingConstants.CENTER);
			gameWonLabel.setBounds(0, 10, getWidth() - 10, getHeight() - 10);
			gameWonLabel.setFont(new Font("Arial", 0, 50));
			gameWonLabel.setForeground(new Color(0,102,0));
			add(gameWonLabel);
		}

		// game grid lines
		g2.setColor(gridColor);
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
		// removing old labels
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
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

				if (game.board[i][j] >= 256) {
					tempLabel.setForeground(secondaryFontColor);
				}
				else {
					tempLabel.setForeground(primaryFontColor);
				}
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

		// label for scored
		try {
			remove(scoreLabel);
		}
		catch(Exception e) {
		}
		
		// label for gamemode
		try {
			remove(imageLabel);
		}
		catch(Exception e) {
		}


		// relative score size (based on window size)
		if (Math.min((int) getHeight(), (int) getWidth()) < 350) {
			scoreLabel = new JLabel("Score: " + Integer.toString(game.score), SwingConstants.RIGHT);
			scoreLabel.setBounds(0, 5, getWidth() - 9, 16);
			scoreLabel.setFont(new Font("Arial", 0, 15));
			scoreLabel.setForeground(primaryFontColor);
			add(scoreLabel);
			if (this.game.gameMode) {
				if (this.theme.equals("dark")) {
					imageLabel = new JLabel(new ImageIcon(imageClassicInv));
				}
				else {
					imageLabel = new JLabel(new ImageIcon(imageClassic));
				}
				imageLabel.setBounds(5, 5, 25, 25);
			}
			else {
				imageLabel = new JLabel(new ImageIcon(imageEndless));
				imageLabel.setBounds(5, 5, 25, 25);
			}
			add(imageLabel);
		}

		else if (Math.min((int) getHeight(), (int) getWidth()) < 600) {
			scoreLabel = new JLabel("Score: " + Integer.toString(game.score), SwingConstants.RIGHT);
			scoreLabel.setBounds(0, 5, getWidth() - 30, 21);
			scoreLabel.setFont(new Font("Arial", 0, 20));
			scoreLabel.setForeground(primaryFontColor);
			add(scoreLabel);	
			if (this.game.gameMode)	 {
				if (this.theme.equals("dark")) {
					imageLabel = new JLabel(new ImageIcon(imageClassicInv));
				}
				else {
					imageLabel = new JLabel(new ImageIcon(imageClassic));
				}
				imageLabel.setBounds(15, 8, 25, 25);
			}
			else {
				imageLabel = new JLabel(new ImageIcon(imageEndless));
				imageLabel.setBounds(15, 8, 25, 25);
			}
			add(imageLabel);
		}

		else {
			scoreLabel = new JLabel("Score: " + Integer.toString(game.score), SwingConstants.RIGHT);
			scoreLabel.setBounds(0, 10, getWidth() - 30, 31);
			scoreLabel.setFont(new Font("Arial", 0, 25));
			scoreLabel.setForeground(primaryFontColor);
			add(scoreLabel);
			if (this.game.gameMode)	 {
				if (this.theme.equals("dark")) {
					imageLabel = new JLabel(new ImageIcon(imageClassicInv));
				}
				else {
					imageLabel = new JLabel(new ImageIcon(imageClassic));
				}
				imageLabel.setBounds(15, 20, 25, 25);
			}
			else {
				imageLabel = new JLabel(new ImageIcon(imageEndless));
				imageLabel.setBounds(15, 20, 25, 25);
			}
			add(imageLabel);
		}

		if (!game.status()) {
			g2.setColor(new Color(1f,0f,0f,.6f));
			g2.fillRect(0, 0, getWidth(), getHeight());
		}
		if (game.win()) {
			g2.setColor(new Color(0f,1f,0f,.6f));
			g2.fillRect(0, 0, getWidth(), getHeight());
		}
	}
	
	// typing method
	@Override
	public void keyTyped(KeyEvent e) {

		char key = e.getKeyChar();

		// key up
		if (key == 'w') {
			if (game.possibleMoves().contains("up")) {
				game.moveUp();
				if (game.status() && !game.win()) {
					game.spawnRandomNumber();
				}
				repaint();
			}
		}

		// keys down
		if (key == 's') {
			if (game.possibleMoves().contains("down")) {
				game.moveDown();
				if (game.status() && !game.win()) {
					game.spawnRandomNumber();
				}
				repaint();
			}
		}

		// keys left
		if (key == 'a') {
			if (game.possibleMoves().contains("left")) {
				game.moveLeft();
				if (game.status() && !game.win()) {
					game.spawnRandomNumber();
				}
				repaint();
			}
		}

		// key right
		if (key == 'd') {
			if (game.possibleMoves().contains("right")) {
				game.moveRight();
				if (game.status() && !game.win()) {
					game.spawnRandomNumber();
				}
				repaint();
			}
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