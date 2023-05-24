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

    // constructor with random
    public Game(int N, Random rand) {
        this.board = new int[N][N];
        this.N = N;
        this.score = 0;
        this.movesHistory = new ArrayList<String>();
        this.spawnPositions = new ArrayList<Coordinates>();
        this.numbersSpawned = new ArrayList<Integer>();
        this.random = rand;
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
            System.out.println("Can't spawn a number. There are insufficient empty spaces.");
        }
    }

    // counts empty spaces on the board
    public int countEmptySpaces() {
        int emptySpaces = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0) {
                    emptySpaces++;
                }
            }
        }
        return emptySpaces;
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

    // method that plays sequence of given moves
    public void playMoves(ArrayList<String> moves, ArrayList<Integer> numbers, ArrayList<Coordinates> coords) {
        // very time-wasteful
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

    // method for faster game copy
    public void copyGame(Game otherGame) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.board[i][j] = otherGame.board[i][j];
            }
        }
    }

    // method that plays sequence of given moves until last move
    public void playMovesUntilLast(ArrayList<String> moves, ArrayList<Integer> numbers, ArrayList<Coordinates> coords) {
        if (moves.size() > 0) {
            for (int i = 0; i < moves.size() - 1; i++) {
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
            spawnNumber(numbers.get(numbers.size() - 2), coords.get(coords.size() - 2));
        }
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

    // returns array of all possible moves
    public ArrayList<String> possibleMoves() {
        ArrayList<String> moves = new ArrayList<String>();
        
        Game newGame = new Game(N);
		if (this.gameMode) {
			newGame.gameMode = true;
		}
		else {
			newGame.gameMode = false;
		}
        newGame.copyGame(this);
        newGame.moveDown();
        if (!newGame.compareOtherGame(this)) {
            moves.add("down");
        }

        newGame = new Game(N);
		if (this.gameMode) {
			newGame.gameMode = true;
		}
		else {
			newGame.gameMode = false;
		}
        newGame.copyGame(this);
        newGame.moveUp();
        if (!newGame.compareOtherGame(this)) {
            moves.add("up");
        }

        newGame = new Game(N);
		if (this.gameMode) {
			newGame.gameMode = true;
		}
		else {
			newGame.gameMode = false;
		}
        newGame.copyGame(this);
        newGame.moveLeft();
        if (!newGame.compareOtherGame(this)) {
            moves.add("left");
        }

        newGame = new Game(N);
		if (this.gameMode) {
			newGame.gameMode = true;
		}
		else {
			newGame.gameMode = false;
		}
        newGame.copyGame(this);
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
    public void simulateMove(int n, Random rand) {
        
        // simulate moveUp
        int scoreMoveUp = 0;

        if (possibleMoves().contains("up")) {
            for (int i = 0; i < n; i++) {
                Game newGame = new Game(N, rand);
                if (this.gameMode) {
                    newGame.gameMode = true;
                }
                else {
                    newGame.gameMode = false;
                }
                
                newGame.copyGame(this);
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
                Game newGame = new Game(N, rand);
                if (this.gameMode) {
                    newGame.gameMode = true;
                }
                else {
                    newGame.gameMode = false;
                }
                
                newGame.copyGame(this);
                newGame.moveDown();
                newGame.spawnRandomNumber();

                while (newGame.status() && !newGame.win()) {
                    newGame.playRandomMove();
                }
                scoreMoveDown += newGame.score;
            }
        }

        // simulate moveLeft
        int scoreMoveLeft = 0;

        if (possibleMoves().contains("left")) {
            for (int i = 0; i < n; i++) {
                Game newGame = new Game(N, rand);
                if (this.gameMode) {
                    newGame.gameMode = true;
                }
                else {
                    newGame.gameMode = false;
                }
                
                newGame.copyGame(this);
                newGame.moveLeft();
                newGame.spawnRandomNumber();

                while (newGame.status() && !newGame.win()) {
                    newGame.playRandomMove();
                }
                scoreMoveLeft += newGame.score;
            }
        }

        // simulate moveRight
        int scoreMoveRight = 0;

        if (possibleMoves().contains("right")) {
            for (int i = 0; i < n; i++) {
                Game newGame = new Game(N, rand);
                if (this.gameMode) {
                    newGame.gameMode = true;
                }
                else {
                    newGame.gameMode = false;
                }
                
                newGame.copyGame(this);
                newGame.moveRight();
                newGame.spawnRandomNumber();

                while (newGame.status() && !newGame.win()) {
                    newGame.playRandomMove();
                }
                scoreMoveRight += newGame.score;
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

    // third ai: prioritizes least amount of empty spaces
    public void playEmptySpaces() {
        Game newGame = new Game(N);
        newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
        int upSpaces = -1;
        if (possibleMoves().contains("up")) {
            newGame.moveUp();
            upSpaces = newGame.countEmptySpaces();
        }

        newGame = new Game(N);
        newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
        int downSpaces = -1;
        if (possibleMoves().contains("down")) {
            newGame.moveDown();
            downSpaces = newGame.countEmptySpaces();
        }

        newGame = new Game(N);
        newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
        int leftSpaces = -1;
        if (possibleMoves().contains("left")) {
            newGame.moveLeft();
            leftSpaces = newGame.countEmptySpaces();
        }

        newGame = new Game(N);
        newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
        int rightSpaces = -1;
        if (possibleMoves().contains("right")) {
            newGame.moveRight();
            rightSpaces = newGame.countEmptySpaces();
        }

        ArrayList<String> bestPossibleMoves = new ArrayList<String>();
        
        // left spaces
        if (leftSpaces >= rightSpaces && leftSpaces >= upSpaces && leftSpaces >= downSpaces) {
            bestPossibleMoves.add("left");
        }

        // right
        if (rightSpaces >= leftSpaces && rightSpaces >= upSpaces && rightSpaces >= downSpaces) {
            bestPossibleMoves.add("right");
        }

        // down
        if (downSpaces >= rightSpaces && downSpaces >= upSpaces && downSpaces >= leftSpaces) {
            bestPossibleMoves.add("down");
        }

        // up
        if (upSpaces >= rightSpaces && upSpaces >= leftSpaces && upSpaces >= downSpaces) {
            bestPossibleMoves.add("up");
        }
        
        // we prioritize 1. up 2. right 3. left 4. down
        if (bestPossibleMoves.contains("up")) {
            this.moveUp();
            this.spawnRandomNumber();
        }
        else if (bestPossibleMoves.contains("right")) {
            this.moveRight();
            this.spawnRandomNumber();
        }
        else if (bestPossibleMoves.contains("left")) {
            this.moveLeft();
            this.spawnRandomNumber();
        }
        else if (bestPossibleMoves.contains("down")) {
            this.moveDown();
            this.spawnRandomNumber();
        }
    }

    // fourth ai: simulator with dynamic depth
    public void simulateMoveDynamic(Random rand) {
        if (this.score < 6000) {
            simulateMove(50, rand);
        }
        else if (this.score < 7500) {
            simulateMove(100, rand);
        }
        else if (this.score < 13500) {
            simulateMove(50, rand);
        }
        else if (this.score < 14800) {
            simulateMove(500, rand);
        }
        else if (this.score < 15000) {
            simulateMove(50, rand);
        }
        else if (this.score < 16500) {
            simulateMove(500, rand);
        }
        else if (this.score < 22000) {
            simulateMove(100, rand);
        }
        else if (this.score < 23600) {
            simulateMove(500, rand);
        }
        else if (this.score < 24700) {
            simulateMove(100, rand);
        }
        else if (this.score < 25400) {
            simulateMove(500, rand);
        }
        else if (this.score < 26000) {
            simulateMove(100, rand);
        }
        else if (this.score < 27600) {
            simulateMove(500, rand);
        }
        else if (this.score < 29500) {
            simulateMove(100, rand);
        }
        else if (this.score < 30700) {
            simulateMove(500, rand);
        }
        else if (this.score < 32000) {
            simulateMove(100, rand);
        }
        else if (this.score < 32700) {
            simulateMove(500, rand);
        }
        else if (this.score < 33500) {
            simulateMove(100, rand);
        }
        else if (this.score < 34700) {
            simulateMove(1000, rand);
        }
        else if (this.score < 35000) {
            simulateMove(100, rand);
        }
        else if (this.score < 36500) {
            simulateMove(1000, rand);
        }
        else if (this.score < 49000) {
            simulateMove(100, rand);
        }
        else if (this.score < 51600) {
            simulateMove(500, rand);
        }
        else if (this.score < 58000) {
            simulateMove(100, rand);
        }
        else if (this.score < 69000) {
            simulateMove(500, rand);
        }
        else if (this.score < 72000) {
            simulateMove(1000, rand);
        }
        else if (this.score < 76000) {
            simulateMove(500, rand);
        }
        else {
            simulateMove(1000, rand);
        }
    }

    // fifth ai: simulator with position eval
    public void playPositionEvalAlgorithm(int depth) {
        
        float bestEval = -1000000000;
        String bestMove = "";

        if (possibleMoves().contains("up")) {
            // simulate up
            Game newGame = new Game(N);
            newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
            newGame.moveUp();
            
            float upEval = newGame.moveEval(depth);
            if (upEval > bestEval) {
                bestEval = upEval;
                bestMove = "up";
            }
        }

        if (possibleMoves().contains("right")) {
            Game newGame = new Game(N);
            newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
            newGame.moveRight();
            
            float rightEval = newGame.moveEval(depth);
            
            if (rightEval > bestEval) {
                bestEval = rightEval;
                bestMove = "right";
            }
        }
        
        if (possibleMoves().contains("down")) {
            Game newGame = new Game(N);
            newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
            newGame.moveDown();
            
            float downEval = newGame.moveEval(depth);
            
            if (downEval > bestEval) {
                bestEval = downEval;
                bestMove = "down";
            }
        }
        
        if (possibleMoves().contains("left")) {
            Game newGame = new Game(N);
            newGame.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
            newGame.moveLeft();
            
            float leftEval = newGame.moveEval(depth);
            
            if (leftEval > bestEval) {
                bestEval = leftEval;
                bestMove = "left";
            }
        }

        switch (bestMove) {
            case "up":
                this.moveUp();
                break;
            case "right":
                this.moveRight();
                break;
            case "down":
                this.moveDown();
                break;
            case "left":
                this.moveLeft();
                break;
            }
        
        if (this.emptySpaces().size() > 0) {
            this.spawnRandomNumber();
        }
    }

    // weighted position eval calculated
    public float moveEval(int depth) {
        // check all empty spaces
        ArrayList<Coordinates> emptySpaces = this.emptySpaces();
        float numberOfEmptySpaces = emptySpaces.size();
        float expectedScore = 0;

        // check if game is over
        if (!this.status()) {
            return -10000f;
        }

        // for each empty space, we spawn a 2 or a 4
        if (depth == 1) {
            for (Coordinates coord: emptySpaces) {
                Game newGame2 = new Game(N);
                newGame2.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
                newGame2.spawnNumber(2, coord);
                float eval2 = newGame2.positionEval();
                expectedScore += 1 / numberOfEmptySpaces * eval2 * 0.9;

                Game newGame3 = new Game(N);
                newGame3.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
                newGame3.spawnNumber(4, coord);
                float eval4 = newGame3.positionEval();
                expectedScore += 1 / numberOfEmptySpaces * eval4 * 0.1;
            }
            return expectedScore;
        }
        else {
            for (Coordinates coord: emptySpaces) {
                Game newGame2 = new Game(N);
                newGame2.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
                newGame2.spawnNumber(2, coord);
                newGame2.playPositionEvalAlgorithm(depth - 1);
                float eval2 = newGame2.positionEval();
                expectedScore += 1 / numberOfEmptySpaces * eval2 * 0.9;

                Game newGame3 = new Game(N);
                newGame3.playMoves(this.movesHistory, this.numbersSpawned, this.spawnPositions);
                newGame3.spawnNumber(4, coord);
                newGame3.playPositionEvalAlgorithm(depth - 1);
                float eval4 = newGame3.positionEval();
                expectedScore += 1 / numberOfEmptySpaces * eval4 * 0.1;
            }
            return expectedScore;
        }
    }

    // position eval
    public float positionEval() {
        return positionEval(
            1f, // scoreMultiplier
            10f, // emptyTilesMultiplier
            200f, // maxTileMultiplier
            50f, // smoothnessMultiplierTopRow
            30f  // smoothnessMultiplierCols
        );
    }

    // position eval with weights
    public float positionEval(float scoreMultiplier,
                              float emptyTilesMultiplier,
                              float maxTileMultiplier,
                              float smoothnessMultiplierTopRow,
                              float smoothnessMultiplierCols
                              ) {
        
        float eval = 0;

        // score
        eval += this.score * scoreMultiplier;

        // empty tiles
        eval += this.countEmptySpaces() * emptyTilesMultiplier;
        
        // corner tiles
        int maxTile = 0;

        for (int i=0; i < this.N; i++) {
            for (int j=0; j < this.N; j++) {
                if (this.board[i][j] > maxTile) {
                    maxTile = this.board[i][j];
                }
            }
        }
        
        if (board[0][this.N - 1] == maxTile) {
                eval += maxTileMultiplier;
        }

        // smoothness: check if every row and column is sorted
        // most important: first row

        ArrayList<Integer> firstRow = new ArrayList<Integer>();
        for (int i=0; i < this.N; i++) {
            firstRow.add(this.board[0][i]);
        }
        

        // check if firstRow is sorted
        boolean sorted = true;
        for (int i=0; i < firstRow.size() - 1; i++) {
            if (firstRow.get(i) > firstRow.get(i + 1)) {
                sorted = false;
                break;
            }
        }

        if (sorted) {
            eval += smoothnessMultiplierTopRow;
        }

        // check if colums are sorted
        for (int i=0; i < this.N; i++) {
            ArrayList<Integer> column = new ArrayList<Integer>();
            for (int j=0; j < this.N; j++) {
                column.add(this.board[j][i]);
            }

            sorted = true;
            for (int j=0; j < column.size() - 1; j++) {
                if (column.get(j) < column.get(j + 1)) {
                    sorted = false;
                    break;
                }
            }
            if (sorted) {
                eval += smoothnessMultiplierCols;
            }
        }

        return eval;
    }

    // returns the empty spaces on the board
    public ArrayList<Coordinates> emptySpaces() {
        ArrayList<Coordinates> emptySpaces = new ArrayList<Coordinates>();
        for (int i=0; i < this.N; i++) {
            for (int j=0; j < this.N; j++) {
                if (this.board[i][j] == 0) {
                    emptySpaces.add(new Coordinates(i, j));
                }
            }
        }
        return emptySpaces;
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

    // biggest number reached
    public int maxNumber() {
        int biggest = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] > biggest) {
                    biggest = board[i][j];
                }
            }
        }
        return biggest;
    }

    // undo
    public void undo() {
        if (this.movesHistory.size() >= 1) {
            Game newGame = new Game(N);
            
            if (gameMode) {newGame.gameMode = true;}
            else {newGame.gameMode = false;}

            newGame.playMovesUntilLast(this.movesHistory, this.numbersSpawned, this.spawnPositions);
            this.board = new int[N][N];
            this.score = 0;
            this.movesHistory = new ArrayList<String>();
            this.spawnPositions = new ArrayList<Coordinates>();
            this.numbersSpawned = new ArrayList<Integer>();
            this.playMoves(newGame.movesHistory, newGame.numbersSpawned, newGame.spawnPositions);
        }
    }

}
