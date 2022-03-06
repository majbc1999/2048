# 2048

## Description 

2048 is a single-player sliding tile puzzle video game written by Italian web developer Gabriele Cirulli. The objective of the game is to slide numbered tiles on a grid to combine them to create a tile with the number 2048; however, one can continue to play the game after reaching the goal, creating tiles with larger numbers. (from [Wikipedia](https://en.wikipedia.org/wiki/2048_(video_game)))

## Goal of the project

Goals of the project are:
- [x] implementation of the game (in Java)
- [ ] simple settings manager for the game (color scheme, grid size,...)
- [ ] implementation of AI player (possibly more than one *computer* player)

## Implementation of the game

The game is represented in class `Game.java` in package `basic`. In Java, the game is represented as a simple matrix (`int[N][N] board`), with `N` representing game size (for example, `N=4` means our game will run on a 4x4 board). Class `Game` also contains methods `moveUp`, `moveDown`, `moveLeft` and `moveRight`: they serve to complete moves and change the board accordingly. Method `status` gives feedback on the current game status (whether it is game over or not). Method `spawnRandomNumber` spawns 2 (probability 90%) or 4 (10%) in a random empty space on the board.

The `gui` package provides user interface for the game. Class `Frame.java` extends JFrame and is used as main window. The class `Panel.java` extends JPanel and is used to draw the game. Both JFrame and JPanel are components of Swing (a GUI widget toolkit for Java).

In the `ai` package we will have different classes, each representing a different computer method, that tries to solve the game. 

Controls of the game are keys on the keyboard: *w*- move up, *a*- move left, *s*- move down, *d*- move right.

## Variants
Although official game runs in the 4x4 grid, size will be selectable. Interesting aspect of the final analysis will be how different solving methods behave in different sizes.

As per game modes, we will implement 2 similar game modes: *Classic* and *Endless*. *Classic* ends when user reaches 2048 with a win. *Endless* cannot be won as we chase biggest number possible. Note although that in spite of the name, the game cannot run forever: for example, biggest number that we can reach in 4x4 game is [131072](https://qph.fs.quoracdn.net/main-qimg-432256feb5c92d2a35549f120d03dbca).

## Game settings
(to be added when implemented)

## Solving programs

(to be added when implemented)

## Current bugs
- [ ] if the move does nothing to the board, a new number should not be spawned
- [ ] computer play works, it just doesn't show on panel