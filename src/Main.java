import basic.Game;

public class Main {
    public static void main(String[] args) throws Exception {
        Game igra = new Game(3);
        igra.SpawnRandomNumber();
        igra.print();
    }
}
