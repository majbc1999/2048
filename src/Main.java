import java.awt.Color;
import java.io.File;

import javax.imageio.ImageIO;

import basic.Game;
import gui.Frame;

public class Main {
    public static void main(String[] args) throws Exception {
        Game game = new Game(3);
    
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
        
        // spawn first number before starting the game
        game.spawnRandomNumber();

        // frame and panel initialization
        Frame frame = new Frame(colorSchemeInit, game);
        frame.setIconImage(ImageIO.read(new File("static/2048.png")));
        frame.pack();
        frame.setVisible(true);
    }
}
