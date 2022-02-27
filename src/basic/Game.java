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

    // function that spawns a 2 or 4 in empty place on the board
    public void spawnRandomNumber() {
        int newNumber;
        int numberOfEmptySpaces = 0;
        
        Random random = new Random(System.currentTimeMillis());

        // we imply that 2 is spawned with 90% probability and 4 with 10% probability (as is in 2048 rulebook)
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
                // current element
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

                    // we move the number to the furthest empty spot
                    if (!moveDone) {
                        if (emptyIndexes.size() != 0) {
                            board[emptyIndexes.get(emptyIndexes.size() - 1)][i] = number;
                            board[j][i] = 0;
                            emptyIndexes.clear();
                        }
                    }
                    else {
                        moveDone = false;
                        emptyIndexes.clear();
                    }
                }
            }
        }

        // changed number get subtracted by one (we avoid merging multiple numbers in single step)
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] % 2 != 0) {
                    board[i][j] = board[i][j] - 1;
                }
            }
        }
    }

    // method that moves numbers down
    public void moveDown() {
        ArrayList<Integer> emptyIndexes = new ArrayList<Integer>();
        boolean moveDone = false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // current element
                int number = board[N - j - 1][i];
                if (number != 0) {
                    // we search for same number or empty space below
                    for (int k = N - j; k < N; k++) {
                        // same number below
                        if (board[k][i] == number) {
                            board[k][i] = 2 * number + 1;
                            board[N - j - 1][i] = 0;
                            moveDone = true;
                            break;
                        }
                        // other number below
                        else if (board[k][i] != number && board[k][i] != 0) {
                            break;
                        }
                        // empty space below
                        else if (board[k][i] == 0) {
                            emptyIndexes.add(k); 
                        }
                    }

                    // we move the number to the furthest empty spot
                    if (!moveDone) {
                        if (emptyIndexes.size() != 0) {
                            board[emptyIndexes.get(emptyIndexes.size() - 1)][i] = number;
                            board[N - j - 1][i] = 0;
                            emptyIndexes.clear();
                        }
                    }
                    else {
                        moveDone = false;
                        emptyIndexes.clear();
                    }
                }
            }
        }

        // changed number get subtracted by one (we avoid merging multiple numbers in single step)
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] % 2 != 0) {
                    board[i][j] = board[i][j] - 1;
                }
            }
        }
    }

    // method that moves numbers up
    public void moveLeft() {
        ArrayList<Integer> emptyIndexes = new ArrayList<Integer>();
        boolean moveDone = false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // current element
                int number = board[i][j];

                if (number != 0) {
                    // we search for same number or empty space in the left
                    for (int k = 1; k < j + 1; k++) {
                        // same number left
                        if (board[i][j - k] == number) {
                            board[i][j - k] = 2 * number + 1;
                            board[i][j] = 0;
                            moveDone = true;
                            break;
                        }
                        // other number in the left
                        else if (board[i][j - k] != number && board[i][j - k] != 0) {
                            break;
                        }
                        // empty space in the left
                        else if (board[i][j - k] == 0) {
                            emptyIndexes.add(j - k);
                        }
                    }

                    // we move the number to the furthest empty spot
                    if (!moveDone) {
                        if (emptyIndexes.size() != 0) {
                            board[i][emptyIndexes.get(emptyIndexes.size() - 1)] = number;
                            board[i][j] = 0;
                            emptyIndexes.clear();
                        }
                    }
                    else {
                        moveDone = false;
                        emptyIndexes.clear();
                    }
                }
            }
        }

        // changed number get subtracted by one (we avoid merging multiple numbers in single step)
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] % 2 != 0) {
                    board[i][j] = board[i][j] - 1;
                }
            }
        }
    }

    // method that moves numbers right
    public void moveRight() {
        ArrayList<Integer> emptyIndexes = new ArrayList<Integer>();
        boolean moveDone = false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // current element
                int number = board[i][N - j - 1];
                
                if (number != 0) {
                    // we search for same number or empty space in the right
                    for (int k = N - j; k < N; k++) {
                        // same number in the right
                        if (board[i][k] == number) {
                            board[i][k] = 2 * number + 1;
                            board[i][N - j - 1] = 0;
                            moveDone = true;
                            break;
                        }
                        // other number in the right
                        else if (board[i][k] != number && board[i][k] != 0) {
                            break;
                        }
                        // empty space in the right
                        else if (board[i][k] == 0) {
                            emptyIndexes.add(k); 
                        }
                    }

                    // we move the number to the furthest empty spot
                    if (!moveDone) {
                        if (emptyIndexes.size() != 0) {
                            board[i][emptyIndexes.get(emptyIndexes.size() - 1)] = number;
                            board[i][N - j - 1] = 0;
                            emptyIndexes.clear();
                        }
                    }
                    else {
                        moveDone = false;
                        emptyIndexes.clear();
                    }
                }
            }
        }

        // changed number get subtracted by one (we avoid merging multiple numbers in single step)
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] % 2 != 0) {
                    board[i][j] = board[i][j] - 1;
                }
            }
        }
    }


    // prints the board (for debugging)
    public void print() {
        for (int i=0; i < this.N; i++) {
            for (int j=0; j < this.N; j++) {
                System.out.print(this.board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
