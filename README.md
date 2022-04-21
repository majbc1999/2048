# 2048

## Description 

2048 is a single-player sliding tile puzzle video game written by Italian web developer Gabriele Cirulli. The objective of the game is to slide numbered tiles on a grid to combine them to create a tile with the number 2048; however, one can continue to play the game after reaching the goal, creating tiles with larger numbers. (from [Wikipedia](https://en.wikipedia.org/wiki/2048_(video_game)))

## Goal of the project

Goals of the project are:
- [x] implementation of the game (in Java)
- [x] simple settings manager for the game (color scheme, grid size,...)
- [x] implementation of AI player (possibly more than one *computer* player)

## Implementation of the game

The game is represented in class `Game.java` in package `basic`. In Java, the game is represented as a simple matrix (`int[N][N] board`), with `N` representing game size (for example, `N=4` means our game will run on a 4x4 board). Class `Game` also contains methods `moveUp`, `moveDown`, `moveLeft` and `moveRight`: they serve to complete moves and change the board accordingly. Method `status` gives feedback on the current game status (whether it is game over or not). Method `spawnRandomNumber` spawns 2 (probability 90%) or 4 (10%) in a random empty space on the board. This class also contains methods for ai solving the problem. For example: method `playEmptySpaces` plays Empty spaces algorithm.

The `gui` package provides user interface for the game. Class `Frame.java` extends JFrame and is used as main window. The class `Panel.java` extends JPanel and is used to draw the game. Both JFrame and JPanel are components of Swing (a GUI widget toolkit for Java).

Controls of the game are keys on the keyboard: *w*- move up, *a*- move left, *s*- move down, *d*- move right. Unfortunatelly, there seems to be a problem with slovenian keyboard and Java KeyListener doesn't detect arrow keys.

## Variants
Although official game runs in the 4x4 grid, size is selectable. We can choose *3x3, 4x4, 5x5, 6x6* or *8x8* game

As per game modes, we will implement 2 similar game modes: *Classic* and *Endless*. *Classic* ends when user reaches 2048 with a win. *Endless* cannot be won as we chase biggest number possible. Note although that in spite of the name, the game cannot run forever: for example, biggest number that we can reach in 4x4 game is [131072](https://qph.fs.quoracdn.net/main-qimg-432256feb5c92d2a35549f120d03dbca).

## Game settings
We can control game size (choose from menubar). Game sizes available *3x3*,*4x4*,*5x5*,*6x6*,*8x8*. Note however, it is not possible to beat the game in 3x3 mode (we get maximum 4-8-16-32-64-128-256-512-1024 in the 9 squares).

Different color themes can be chosen: *Vintage 2048* (classic 2048 colors), *Dark Blue Night* (dark theme) and *Colorblind Mode* (black and white theme) can be chosen.

## Solving programs

### **AI 1**: Random moves
This AI just playes random moves until the game is over.

### **AI 2**: Simulating algorithm with random moves
Playes every possible move in the background and then for each simulates the whole game with playing random moves (until game over). For every possible move we repeat that for $k$ times and calculate the average score. Then we play the move with highest average score. Idea for this algorithm was found on [stack overflow](https://stackoverflow.com/questions/22342854/what-is-the-optimal-algorithm-for-the-game-2048#:~:text=AI%20Algorithm&text=The%20starting%20move%20with%20the,1%25%20for%20the%208192%20tile). For $n=500$ the results are very satisfying successfully beats the game with more then 95% probability. However this method of problem solving is a little bit time-wasteful: we have to wait several seconds for the move. Time per move is linearly increasing with increased depth. This method is officially called *Pure Monte Carlo game search*. An option would be to make depth (number of games for each move, $k$) selectable, but it seems as $k=500$ playes both very fast and very good. So selectable depth seems a bit unneeded.

### **AI 3**: Experimenting with empty spaces
Here, the goal is to make the move, that minimizes number of empty spaces on the board. If the same number of empty spaces appear, we prioritize certain moves. This works really fast, but it is not very efficient. It reaches 256 and 512 on a 4x4 board respectivelly, however, it seems unlikely to beat the game with this algorithm. It seems though, that structure of the game is really good in this algorithm. Human solving stategies often involve similar game structure.

### **AI 4**: Simulating algorithm with dynamic depth
After analysing performance of *AI 2* with differenth depth, this algorithm uses dynamic depth to increase in time performance. For example: Until score of the game is low, we can use depth 100 as it almost always reaches 1024, after that we increase depth to 500 to reach 2048 and so on. With this algorithm, time complexity is highly reduced. Reliability is of course minimally reduced as well. This AI takes a bit under 40 seconds to beat the game, meanwhile simulator of depth 500 needs around 210 seconds. So this algorithm is 5x faster then AI 2. It is 2x less reliable to beat the game, but with success a bit over 90%, it is quite good enough.

## Referencing papers (ideas)
- [Using Artificial Intelligence to solve the 2048 Game, Vasilis Vryniotis, 2014](https://blog.datumbox.com/using-artificial-intelligence-to-solve-the-2048-game-java-code/), idea for minimax algorithm. Won't be doing that, as *AI 2* and *AI 4* behaves well enough.
- [Composition of Basic Heuristics for the Game 2048](https://theresamigler.files.wordpress.com/2020/03/2048.pdf), for empty spaces idea

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
- [x] the user should be able to select depth of the *simulating* algorithm -> no need, for $k=500$ the algorithm is good and fast enough
- [x] arrow keys should also make moves, not only *a, w, s, d* -> maybe problem with slovenian keyboard? simple solution doesn't work
- [x] problem of slow simulator algorithm comes mainly from slow copying of the games (find a better way to copy a game)
- [x] when computer plays, human shall not interrupt it (also undo should not work then)
- [x] another algorithm: simulator with dynamic depth

## Instructions for installation
1. (Install java)[https://www.java.com/en/download/] for your operating system
2. Clone this repository and open it in VS Code or Eclipse
3. Run Main.java and a pop-out window will appear
4. Play the game 