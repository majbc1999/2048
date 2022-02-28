import java.awt.Color;
import java.io.File;

import javax.imageio.ImageIO;

import basic.Game;
import gui.Frame;

public class Main {
    public static void main(String[] args) throws Exception {
        Game game = new Game(4);
        
        for (int i = 0; i < 11; i++) {
            game.spawnRandomNumber();
            Thread.sleep(10);
        }

        game.print();

        game.moveRight();
        System.out.println("-----------");
        System.out.println("Moving right");
        System.out.println("-----------");
        game.print();

        Color[] colorSchemeInit = new Color[12];
        colorSchemeInit[0] = Color.BLUE;
        colorSchemeInit[1] = Color.RED;
        colorSchemeInit[2] = Color.YELLOW;
        colorSchemeInit[3] = Color.BLUE;
        colorSchemeInit[4] = Color.BLUE;
        colorSchemeInit[5] = Color.BLUE;
        colorSchemeInit[6] = Color.BLUE;
        colorSchemeInit[7] = Color.BLUE;
        colorSchemeInit[8] = Color.BLUE;
        colorSchemeInit[9] = Color.BLUE;
        colorSchemeInit[10] = Color.BLUE;
        colorSchemeInit[11] = Color.BLUE;

        // new frame
        Frame frame = new Frame(colorSchemeInit, game);
        frame.setIconImage(ImageIO.read(new File("static/2048.png")));
        frame.pack();
        frame.setVisible(true);

    }
}
