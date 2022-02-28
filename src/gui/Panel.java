package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import basic.Game;


@SuppressWarnings("serial")
public class Panel extends JPanel implements MouseListener{
	
	// color scheme
	Color color2;     
	Color color4;     
	Color color8;     
	Color color16;    
	Color color32;   
	Color color64;  
	Color color128; 
	Color color256; 
	Color color512; 
	Color color1024;
	Color color2048;
	Color color4096; // all numbers bigger then 4096 are black color

	// current game
	Game game;
	
	// square width (and length)


	// main constuctor
	public Panel(Color[] colorScheme, Game game) {

		// game
		this.game = game;

		// color scheme
		this.color2 = colorScheme[0];   
		this.color4 = colorScheme[0];   
		this.color8 = colorScheme[0];   
		this.color16 = colorScheme[0];  
		this.color32 = colorScheme[0];  
		this.color64 = colorScheme[0];  
		this.color128 = colorScheme[0]; 
		this.color256 = colorScheme[0]; 
		this.color512 = colorScheme[0]; 
		this.color1024 = colorScheme[0];
		this.color2048 = colorScheme[0];
		this.color4096 = colorScheme[0];

	}

	// preffered dimension of our panel
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	// line relative width
	private final static double LINE_WIDTH = 0.05;
		
	// Širina enega kvadratka
	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / (double) (game.N + 2) - 0.03 * (Math.min(getWidth(), getHeight()) / (double) (game.N + 2));
	}
	
	// Relativni prostor okoli X in O
	private final static double PADDING = 0.12;
		
	private void paintA(Graphics2D g2, int i, int j) { // Pobarvaj igralca 1
		double w = squareWidth();
		double dx = (getWidth()/2.0)- ((game.N /2.0) * w);
		double dy = (getHeight()/2.0)- ((game.N /2.0) * w);
		
		double d = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING) + dx;
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING) + dy;
		g2.setColor(Color.YELLOW);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.fillOval((int)x, (int)y, (int)d , (int)d);
		
		g2.setColor(Color.BLACK);
		g2.drawOval((int)x, (int)y, (int) d, (int) d);
	}
		
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		double w = squareWidth();
		double dx = (getWidth()/2.0)- ((game.N/2.0) * w);
		double dy = (getHeight()/2.0)- ((game.N/2.0) * w);
		
		// črte
		g2.setColor(Color.BLACK);
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
}