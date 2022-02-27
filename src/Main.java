import basic.Game;
import gui.Frame;

public class Main {
    public static void main(String[] args) throws Exception {
        Game igra = new Game(4);
        
        for (int i = 0; i < 11; i++) {
            igra.spawnRandomNumber();
            Thread.sleep(10);
        }

        igra.print();

        igra.moveRight();
        System.out.println("-----------");
        System.out.println("Moving right");
        System.out.println("-----------");
        igra.print();

        // new frame
        Frame frame = new Frame();
        frame.pack();
        frame.setVisible(true);

    }
}
