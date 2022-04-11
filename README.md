# 2048

## Description 

2048 is a single-player sliding tile puzzle video game written by Italian web developer Gabriele Cirulli. The objective of the game is to slide numbered tiles on a grid to combine them to create a tile with the number 2048; however, one can continue to play the game after reaching the goal, creating tiles with larger numbers. (from [Wikipedia](https://en.wikipedia.org/wiki/2048_(video_game)))

## Goal of the project

Goals of the project are:
- [x] implementation of the game (in Java)
- [x] simple settings manager for the game (color scheme, grid size,...)
- [x] implementation of AI player (possibly more than one *computer* player)

## Implementation of the game

The game is represented in class `Game.java` in package `basic`. In Java, the game is represented as a simple matrix (`int[N][N] board`), with `N` representing game size (for example, `N=4` means our game will run on a 4x4 board). Class `Game` also contains methods `moveUp`, `moveDown`, `moveLeft` and `moveRight`: they serve to complete moves and change the board accordingly. Method `status` gives feedback on the current game status (whether it is game over or not). Method `spawnRandomNumber` spawns 2 (probability 90%) or 4 (10%) in a random empty space on the board. This class also contains methods for ai solving the problem.

The `gui` package provides user interface for the game. Class `Frame.java` extends JFrame and is used as main window. The class `Panel.java` extends JPanel and is used to draw the game. Both JFrame and JPanel are components of Swing (a GUI widget toolkit for Java).

Controls of the game are keys on the keyboard: *w*- move up, *a*- move left, *s*- move down, *d*- move right.

## Variants
Although official game runs in the 4x4 grid, size will be selectable. Interesting aspect of the final analysis will be how different solving methods behave in different sizes.

As per game modes, we will implement 2 similar game modes: *Classic* and *Endless*. *Classic* ends when user reaches 2048 with a win. *Endless* cannot be won as we chase biggest number possible. Note although that in spite of the name, the game cannot run forever: for example, biggest number that we can reach in 4x4 game is [131072](https://qph.fs.quoracdn.net/main-qimg-432256feb5c92d2a35549f120d03dbca).

## Game settings
We can control game size (choose from menubar). Game sizes available *3x3*,*4x4*,*5x5*,*6x6*,*8x8*. Note however, it is not possible to beat the game in 3x3 mode (we get maximum 4-8-16-32-64-128-256-512-1024 in the 9 squares).

Different color themes can be chosen. For now only *Vintage 2048* and *Dark Blue Night* can be chosen.

## Solving programs

### **AI 1**: Random moves
This AI just playes random moves until the game is over.

### **AI 2**: Simulating algorithm with random moves
Playes every possible move in the background and then for each simulates the whole game with playing random moves (until game over). For every possible move we repeat that for $n$ times and calculate the average score. Then we play the move with highest average score. Idea for this algorithm was found on [stack overflow](https://stackoverflow.com/questions/22342854/what-is-the-optimal-algorithm-for-the-game-2048#:~:text=AI%20Algorithm&text=The%20starting%20move%20with%20the,1%25%20for%20the%208192%20tile). For $n=25$ the results are already satisfying and the program has successfully beaten the game. Even for $n=5$ algorithm successfully beats the game in first try. However this method of problem solving is a little bit time-wasteful: we have to wait several seconds for the move. Time per move is linearly increasing with increased depth.

### **AI 3**: Experimenting with empty spaces
Here, the goal is to make the move, that minimizes number of empty spaces on the board. If the same number of empty spaces appear, we prioritize certain moves. This works really fast and is quite efficient. It reaches 256 and 512 on a 4x4 board respectivelly, however seems unlikely to beat the game with this algorithm. It seems though, that structure of the game is really good in this algorithm. Note: *AI 2* would probably play better if instead of random moves, we would use this algorithm. However, even with $n=5$, algorithm takes too long to simulate.

## Referencing papers (ideas)
- [Using Artificial Intelligence to solve the 2048 Game, Vasilis Vryniotis, 2014](https://blog.datumbox.com/using-artificial-intelligence-to-solve-the-2048-game-java-code/)
- [Composition of Basic Heuristics for the Game 2048](https://theresamigler.files.wordpress.com/2020/03/2048.pdf)

## Current bugs
- [x] if the move does nothing to the board, a new number should not be spawned
- [x] compareOtherGame method on class Game (both these methods have trouble with deep copy)
- [x] computer play works, it just doesn't show on panel
- [x] simulating algorithm loops don't work (maybe put loops in Swingworker)
- [x] problem with spawning random numbers
- [x] ugly colors in dark mode
- [x] what to do with *Player* and *Computer* buttons in *New game* menu: solution is that if computer plays, *Player* stops him and lets human to continue. Similarly *Computer* button works to start playing this game. 
- [x] classic mode icon not seen in dark mode
- [x] the user has to see which computer algorithm is playing
- [ ] the user should be able to select depth of the *simulating* algorithm
- [ ] arrow keys should also make moves, not only *a, w, s, d* (not everybody is a gamer)
- [ ] problem of slow simulator algorithm comes mainly from slow copying of the games (find a better way to copy a game)
- [ ] when computer plays, human shall not interrupt it (also undo should not work then)
- [ ] changing computer algorithm should instantly change it 
- [ ] another algorithm: **better simulating algorithm**, which simulates and decides based on score, empty fields and max number (try to find best such function)
- [ ] another algorithm: based on data changes depths depending on score and minimizing time (big depth is only needed right before new highest number)