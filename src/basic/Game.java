// game logic class
package basic;

import java.util.ArrayList;
import java.util.Random;

public class Game {
	
	// size of the game (default 4)
	public int N;
    public int score;

    // matrix of size N x N, that represents current state on board, 0 means that field is empty
    public int[][] board;

    // history of moves and spawns 
    public ArrayList<String> movesHistory;
    public ArrayList<Coordinates> spawnPositions;
    public ArrayList<Integer> numbersSpawned;

    // random seed
    public Random random;

    // classic or endless (true or false)
    public Boolean gameMode;

    // constructor
    public Game(int N) {
        this.board = new int[N][N];
        this.N = N;
        this.score = 0;
        this.movesHistory = new ArrayList<String>();
        this.spawnPositions = new ArrayList<Coordinates>();
        this.numbersSpawned = new ArrayList<Integer>();
        this.random = new Random(System.currentTimeMillis());
        this.gameMode = true;
    }

    // function that spawns a 2 or 4 in empty place on the board (if no empty place it does nothing)
    public void spawnRandomNumber() {
        int newNumber;
        int numberOfEmptySpaces = 0;

        // we imply that 2 is spawned with 90% probability and 4 with 10% probability (as is in 2048 rulebook)

        double randomDbl = Math.pow(10, 4) * random.nextDouble();
        double floor =  Math.floor(randomDbl);

        double randomDouble = randomDbl - floor;

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

        if (numberOfEmptySpaces > 0) {
            int randomInt = random.nextInt(numberOfEmptySpaces);

            numberOfEmptySpaces = 0; 
    
            for (int i=0; i < this.N; i++) {
                for (int j=0; j < this.N; j++) {
                    if (board[i][j] == 0) {
                        if (numberOfEmptySpaces == randomInt) {
                            this.board[i][j] = newNumber;
                            numbersSpawned.add(newNumber);
                            spawnPositions.add(new Coordinates(i,j));
                        }
                        numberOfEmptySpaces++;
                    }
                }
            }
        }
        else {
            System.out.print("Can't spawn a number. There are insufficient empty spaces.");
        }
    }

    // method that moves numbers up
    public void moveUp() {
        ArrayList<Integer> emptyIndexes = new ArrayList<Integer>();
        boolean moveDone = false;
        if (!win()) {    
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
                                score += 2 * number;
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

            movesHistory.add("up");
        }
    }

    // method that moves numbers down
    public void moveDown() {
        ArrayList<Integer> emptyIndexes = new ArrayList<Integer>();
        boolean moveDone = false;
        if (!win()) {
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
                                score += 2 * number;
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

            movesHistory.add("down");
        }
    }

    // method that moves numbers up
    public void moveLeft() {
        ArrayList<Integer> emptyIndexes = new ArrayList<Integer>();
        boolean moveDone = false;
        if (!win()) {
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
                                score += 2 * number;
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

            movesHistory.add("left");
        }
    }

    // method that moves numbers right
    public void moveRight() {
        ArrayList<Integer> emptyIndexes = new ArrayList<Integer>();
        boolean moveDone = false;
        if (!win()) {
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
                                score += 2 * number;
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

            movesHistory.add("right");
        }
    }

    // method that spawns a custom number on a custom coorinate
    public void spawnNumber(int number, Coordinates coords) {
        board[coords.x][coords.y] = number;
        spawnPositions.add(coords);
        numbersSpawned.add(number);
    }

    // method that playes sequence of given moves
    public void playMoves(ArrayList<String> moves, ArrayList<Integer> numbers, ArrayList<Coordinates> coords) {
        for (int i = 0; i < moves.size(); i++) {
            spawnNumber(numbers.get(i), coords.get(i));
            if (moves.get(i) == "up") {
                moveUp();
            }
            else if (moves.get(i) == "down") {
                moveDown();
            }
            else if (moves.get(i) == "left") {
                moveLeft();
            }
            else if (moves.get(i) == "right") {
                moveRight();    
            }
        }
        spawnNumber(numbers.get(numbers.size() - 1), coords.get(coords.size() - 1));
    }

    // method that returns True if the game is still in progress and False if it is over
    public Boolean status() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0) {
                    return true;
                }
            }
        }

        int tempInt = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == tempInt) {
                    return true;
                }
                else {
                    tempInt = board[i][j];
                }
            }
            tempInt = 0;
        }

        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                if (board[i][j] == tempInt) {
                    return true;
                }
                else {
                    tempInt = board[i][j];
                }
            }
            tempInt = 0;
        }

        return false;
    }

    // method that returns True if game won and False else
    public Boolean win() {
        if (!gameMode) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] >= 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    // returns array of all possible moves (doesn't work yet)
    public ArrayList<String> possibleMoves() {
        ArrayList<String> moves = new ArrayList<String>();
        
        Game newGame = new Game(N);
        newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
        newGame.moveDown();
        if (!newGame.compareOtherGame(this)) {
            moves.add("down");
        }

        newGame = new Game(N);
        newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
        newGame.moveUp();
        if (!newGame.compareOtherGame(this)) {
            moves.add("up");
        }

        newGame = new Game(N);
        newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
        newGame.moveLeft();
        if (!newGame.compareOtherGame(this)) {
            moves.add("left");
        }

        newGame = new Game(N);
        newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
        newGame.moveRight();
        if (!newGame.compareOtherGame(this)) {
            moves.add("right");
        }

        return moves;
    }

    // compares two games if they are the same (doesn't work yet)
    public Boolean compareOtherGame(Game otherGame) {

        if (otherGame.N != N) {
            return false;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.board[i][j] != otherGame.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // first ai: plays completely random (possible move)
    public void playRandomMove() {
        double randomDbl = Math.pow(10, 4) * random.nextDouble();
        double floor =  Math.floor(randomDbl);

        double randomDouble = randomDbl - floor;
        
        if (randomDouble < 0.25) {
            if (possibleMoves().contains("up")) {
                moveUp();
                spawnRandomNumber();
            }
            else {
                playRandomMove();
            }
        }

        else if (randomDouble >= 0.25 && randomDouble < 0.5) {
            if (possibleMoves().contains("down")) {
                moveDown();
                spawnRandomNumber();
            }
            else {
                playRandomMove();
            }
        }

        else if (randomDouble >= 0.5 &&  randomDouble < 0.75) {
            if (possibleMoves().contains("left")) {
                moveLeft();
                spawnRandomNumber();
            }
            else {
                playRandomMove();
            }
        }

        else if (randomDouble >= 0.75) {
            if (possibleMoves().contains("right")) {
                moveRight();
                spawnRandomNumber();
            }
            else {
                playRandomMove();
            }
        }
    }

    // second ai: simulates only once for each move
    public void simulateMove(int n) {
        
        // simulate moveUp
        int scoreMoveUp = 0;

        if (possibleMoves().contains("up")) {
            for (int i = 0; i < n; i++) {
                Game newGame = new Game(N);
                newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
                newGame.moveUp();
                newGame.spawnRandomNumber();

                while (newGame.status() && !newGame.win()) {
                    newGame.playRandomMove();
                }

                scoreMoveUp += newGame.score;                
            }
        }

        // simulate moveDown
        int scoreMoveDown = 0;
        
        if (possibleMoves().contains("down")) {
            for (int i = 0; i < n; i++) {
                Game newGame = new Game(N);
                newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
                newGame.moveDown();
                newGame.spawnRandomNumber();

                while (newGame.status() && !newGame.win()) {
                    newGame.playRandomMove();
                }

                scoreMoveDown += newGame.score;

                System.out.println(n + i);
            }
        }

        // simulate moveLeft
        int scoreMoveLeft = 0;

        if (possibleMoves().contains("left")) {
            for (int i = 0; i < n; i++) {
                Game newGame = new Game(N);
                newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
                newGame.moveLeft();
                newGame.spawnRandomNumber();

                while (newGame.status() && !newGame.win()) {
                    newGame.playRandomMove();
                }

                scoreMoveLeft += newGame.score;

                System.out.println(2*n + i);
            }
        }

        // simulate moveRight
        int scoreMoveRight = 0;

        if (possibleMoves().contains("right")) {
            for (int i = 0; i < n; i++) {
                Game newGame = new Game(N);
                newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
                newGame.moveRight();
                newGame.spawnRandomNumber();

                while (newGame.status() && !newGame.win()) {
                    newGame.playRandomMove();
                }

                scoreMoveRight += newGame.score;

                System.out.println(3*n + i);
            }
        }

        if (scoreMoveDown >= scoreMoveUp && scoreMoveDown >= scoreMoveLeft && scoreMoveDown >= scoreMoveRight) {
            moveDown();
            spawnRandomNumber();
        }
        else if (scoreMoveUp >= scoreMoveDown && scoreMoveUp >= scoreMoveLeft && scoreMoveUp >= scoreMoveRight) {
            moveUp();
            spawnRandomNumber();
        }
        else if (scoreMoveLeft >= scoreMoveUp && scoreMoveLeft >= scoreMoveDown && scoreMoveLeft >= scoreMoveRight) {
            moveLeft();
            spawnRandomNumber();
        }
        else if (scoreMoveRight >= scoreMoveUp && scoreMoveRight >= scoreMoveLeft && scoreMoveRight >= scoreMoveDown) {
            moveRight();
            spawnRandomNumber();
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
        System.out.println("-------------");
    }
}
