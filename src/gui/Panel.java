package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Panel extends JPanel implements MouseListener{
	
	
	Color barvaA;
	Color barvaB;
	Color barvaGol;
	Color barvaKazenski;
	Color barvaSpawnA;
	Color barvaSpawnB;
	public int dolzina;
	public int sirina;
	public int gol;
	
	boolean siPodaja;
	
	
	
	public Panel() {
		setBackground(new Color(0, 204, 0));
		this.addMouseListener(this);
		barvaA = new Color(46, 168, 217);
		barvaB = new Color(201, 9, 2);
		dolzina = 20;
		sirina = 15;
		gol = 7;
		barvaGol = new Color(255, 255, 196);
		barvaKazenski = new Color(0, 240, 50);
		siPodaja = false;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	// Relativna širina črte
	private final static double LINE_WIDTH = 0.05;
		
	// Širina enega kvadratka
	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / (double) (dolzina + 2) - 0.03 * (Math.min(getWidth(), getHeight()) / (double) (dolzina + 2));
	}
	
	// Relativni prostor okoli X in O
	private final static double PADDING = 0.12;
		
	private void paintA(Graphics2D g2, int i, int j) { // Pobarvaj igralca 1
		double w = squareWidth();
		double dx = (getWidth()/2.0)- ((sirina /2.0) * w);
		double dy = (getHeight()/2.0)- ((dolzina /2.0) * w);
		
		double d = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING) + dx;
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING) + dy;
		g2.setColor(barvaA);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.fillOval((int)x, (int)y, (int)d , (int)d);
		
		g2.setColor(Color.BLACK);
		g2.drawOval((int)x, (int)y, (int) d, (int) d);
	}
	
	
	private void paintB(Graphics2D g2, int i, int j) { // Pobarvaj igralca 2
		double w = squareWidth();
		double dx = (getWidth()/2.0)- ((sirina /2.0) * w);
		double dy = (getHeight()/2.0)- ((dolzina /2.0) * w);
		
		double d = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING) + dx;
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING) + dy;
		g2.setColor(barvaB);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.fillOval((int)x, (int)y, (int)d , (int)d);
		
		g2.setColor(Color.BLACK);
		g2.drawOval((int)x, (int)y, (int) d, (int) d);
	}
	
	private void paintgolmanA(Graphics2D g2, int i, int j) { // Pobarvaj igralca 2
		double w = squareWidth();
		double dx = (getWidth()/2.0)- ((sirina /2.0) * w);
		double dy = (getHeight()/2.0)- ((dolzina /2.0) * w);
		
		double d = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING) + dx;
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING) + dy;
		g2.setColor(Color.DARK_GRAY);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.fillOval((int)x, (int)y, (int)d , (int)d);
		
		g2.setColor(Color.BLACK);
		g2.drawOval((int)x, (int)y, (int) d, (int) d);
	}
	
	private void paintgolmanB(Graphics2D g2, int i, int j) { // Pobarvaj igralca 2
		double w = squareWidth();
		double dx = (getWidth()/2.0)- ((sirina /2.0) * w);
		double dy = (getHeight()/2.0)- ((dolzina /2.0) * w);
		
		double d = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING) + dx;
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING) + dy;
		g2.setColor(Color.BLACK);
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
		double dx = (getWidth()/2.0)- ((sirina/2.0) * w);
		double dy = (getHeight()/2.0)- ((dolzina/2.0) * w);

		
		// pobarvajmo polja
		
			// goli
		g2.setColor(barvaGol);
		for (int i = 0; i < 7; i++) {
			g2.fillRect((int)(4 * w + i * w + dx), (int)(-w + dy), (int)w, (int)w);
			g2.fillRect((int)(4 * w + i * w + dx), (int)((dolzina) * w + dy), (int)w, (int)w);
		}

			// kazenski golmana
		g2.setColor(barvaKazenski);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 4; j++) {
				g2.fillRect((int)(3 * w + i * w + dx), (int)(j * w + dy), (int)w, (int)w);
				g2.fillRect((int)(3 * w + i * w + dx), (int)((dolzina - 4) * w + j * w + dy), (int)w, (int)w);
			}
		}
		
		
			// spawni
		
		g2.setColor(Color.CYAN);
			g2.fillRect((int)(7 * w + dx), (int)(0 * w + dy), (int)w, (int)w);	
			g2.fillRect((int)(5 * w + dx), (int)(5 * w + dy), (int)w, (int)w);	
			g2.fillRect((int)(9 * w + dx), (int)(5 * w + dy), (int)w, (int)w);	
			g2.fillRect((int)(3 * w + dx), (int)(8 * w + dy), (int)w, (int)w);	
			g2.fillRect((int)(11 * w + dx), (int)(8 * w + dy), (int)w, (int)w);	
		g2.setColor(Color.ORANGE);
			g2.fillRect((int)(3 * w + dx), (int)(11 * w + dy), (int)w, (int)w);	
			g2.fillRect((int)(11 * w + dx), (int)(11 * w + dy), (int)w, (int)w);	
			g2.fillRect((int)(5 * w + dx), (int)(14 * w + dy), (int)w, (int)w);	
			g2.fillRect((int)(9 * w + dx), (int)(14 * w + dy), (int)w, (int)w);	
			g2.fillRect((int)(7 * w + dx), (int)(19 * w + dy), (int)w, (int)w);	


			
		
		// črte
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		for (int i = 0; i <= sirina; i++) {
			g2.drawLine((int)(i * w + dx),
					    (int)(0 + dy),
					    (int)(i * w + dx),
					    (int)(dolzina * w + dy));
		}
		for (int i = 0; i <= dolzina; i++) {
			g2.drawLine((int)(0 + dx),
					    (int)(i * w + dy),
					    (int)(sirina * w + dx),
					    (int)(i * w + dy));
		}
		
		// gol vertik
		for (int i = 4; i <= 11; i++) {
			g2.drawLine((int)(i * w + dx),
					    (int)(-w + dy),
					    (int)(i * w + dx),
					    (int)((dolzina + 1) * w + dy));
		}
		
		// gol horiz
		g2.drawLine((int)(4 * w + dx),
			    (int)((dolzina + 1) * w + dy),
			    (int)(11 * w + dx),
			    (int)((dolzina + 1) * w + dy));
		
		g2.drawLine((int)(4 * w + dx),
			    (int)(-w + dy),
			    (int)(11 * w + dx),
			    (int)(-w + dy));
		
		// sredinska črta
		g2.setColor(Color.WHITE);
		
		g2.drawLine((int)(0 * w + dx),
			    (int)(dolzina / 2 * w + dy),
			    (int)(sirina * w + dx),
			    (int)(dolzina / 2 * w + dy));		
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