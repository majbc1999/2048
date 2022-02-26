// game logic class
package basic;

import java.util.ArrayList;
import java.util.Random;

public class Game {
	
	// size of the game (default 4)
	public int N;

    // matrix of size N x N, that represents current state on board, 0 means that field is empty
    public int[][] board;

    // constructor
    public Game(int N) {
        this.board = new int[N][N];
        this.N = N;
    }

    // function that spawns a 2 or 4 in empty place on 
    public void spawnRandomNumber() {
        int newNumber;
        int numberOfEmptySpaces = 0;
        
        Random random = new Random(System.currentTimeMillis());

        // we imply that 2 is spawned with 90% probability and 4 with 10% probability
        double randomDouble = random.nextDouble();
        if (randomDouble <= 0.1) {
            newNumber = 4;
        }
        else {
            newNumber = 2;
        }

        // find a random empty place on board
        for (int i=0; i < this.N; i++) {
            for (int j=0; j < this.N; j++) {
                if (board[i][j]==0) {numberOfEmptySpaces++;}
            }
        }

        int randomInt = random.nextInt(numberOfEmptySpaces);

        numberOfEmptySpaces = 0; 


        for (int i=0; i < this.N; i++) {
            for (int j=0; j < this.N; j++) {
                if (board[i][j] == 0) {
                    if (numberOfEmptySpaces == randomInt) {
                        this.board[i][j] = newNumber;
                    }
                    numberOfEmptySpaces++;
                }
            }
        }
    }

    // method that moves numbers up
    public void moveUp() {
        ArrayList<Integer> emptyIndexes = new ArrayList<Integer>();
        boolean moveDone = false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // empty element in the grid
                int number = board[j][i];
                if (number != 0) {
                    // we search for same number or empty space above
                    for (int k = 1; k < j + 1; k++) {
                        // same number above
                        if (board[j - k][i] == number) {
                            board[j - k][i] = 2 * number + 1;
                            board[j][i] = 0;
                            moveDone = true;
                            break;
                        }
                        // other number above
                        else if (board[j - k][i] != number && board[j - k][i] != 0) {
                            break;
                        }
                        // empty space above
                        else if (board[j - k][i] == 0) {
                            emptyIndexes.add(j - k); 
                        }
                    }

                    // we move the number in the furthest empty spot
                    if (!moveDone) {
                        if (emptyIndexes.size() != 0) {
                            board[emptyIndexes.get(emptyIndexes.size() - 1)][i] = number;
                            board[j][i] = 0;
                        }
                    }
                    else {
                        moveDone = false;
                        emptyIndexes.clear();
                    }
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] % 2 != 0) {
                    board[i][j] = board[i][j] - 1;
                }
            }
        }
    }

    // prints the board (debugging)
    public void print() {
        for (int i=0; i < this.N; i++) {
            for (int j=0; j < this.N; j++) {
                System.out.print(this.board[i][j]);
            }
            System.out.println();
        }
    }
}
