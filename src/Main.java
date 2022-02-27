import basic.Game;

public class Main {
    public static void main(String[] args) throws Exception {
        Game igra = new Game(4);
        
        for (int i = 0; i < 11; i++) {
            igra.spawnRandomNumber();
            Thread.sleep(200);
        }

        igra.print();

        igra.moveRight();
        System.out.println("-----------");
        System.out.println("Moving right");
        System.out.println("-----------");
        igra.print();
    }
}
