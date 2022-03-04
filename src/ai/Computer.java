package ai;

import java.util.Random;

import basic.Game;

// random algorithm; plays random move 
public class Computer {
    
    public Game game;

    public Computer(Game game) {
        this.game = game;
    }

    public Game playRandomMove() {
        Random random = new Random(System.currentTimeMillis());

        double randomDouble = random.nextDouble();

        if (randomDouble < 0.25) {
            game.moveUp();
        }

        if (randomDouble >= 0.25 && randomDouble < 0.5) {
            game.moveDown();
        }

        if (randomDouble >= 0.5 &&  randomDouble < 0.75) {
            game.moveLeft();
        }

        else {
            game.moveRight();
        }
        return game;
    }

}
