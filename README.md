# Java RPG

In this project, a top-down 2D RPG (role playing game) game was developed using Java programming language. Few points to note:
* The game was _not_ developed from scratch.
* Slick library was used as the underlying game engine.
* Basic game assets were provided, so we did not have to develop the character artwork, map tiles and all the other compulsory components.

Project Outcome:
* Developed a working RPG game using Java programming language.
* Learnt and utilised UML (unified modelling language) block diagram for the development process.
* Worked closely with tutor and colleagues, asked for feedback and utilised them to improve the game.

## Setting up the Slick Library

Here I will briefly go over how to setup the Slick Library used for game development in Eclipse. It uses LWJGL for graphics and inputs.

1. Locate the appropriate libraries for the system in `\lib` folder.
2. We would need to have all the `.jar` and `.dll` files imported for Windows systems. On macOS, we would need `.dylib` files instead of `.dll` files. Similarly, Linux needs `.so` files. These files are all located within the `\lib` folder.
3. Right click on each of the `.jar` files, and press `Build Path -> Configure Build Path...` and add all the `.jar` files.
4. Now if you go to `RPG.java` under `/src` and click run, the application should start running.

## Controls

The game is controlled entirely using the arrow keys on the keyboard. The `left, right, up and down keys` move the player. Each frame, the player moves by a tiny amount in the direction of the keys being pressed, if any. It is possible to move diagonally by holding down two keys at a time (for example, move north-east by holding the up and right keys).

* You can attack a monster simply by moving close to it (within 50 pixels) and holding `A`.
* Similarly, you can talk to a villager by moving close (within 50 pixels) and pressing `T`.
