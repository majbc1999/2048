package ai;

import java.util.Random;

import basic.Game;

// random algorithm; plays random move 
public class Computer {
    
    public Game game;

    public Computer(Game game) {
        this.game = game;
    }

    // first ai: plays completely random (possible move)
    public Game playRandomMove() {
        Random random = new Random(System.currentTimeMillis());

        double randomDbl = Math.pow(10, 4) * random.nextDouble();
        double floor =  Math.floor(randomDbl);

        double randomDouble = randomDbl - floor;

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
