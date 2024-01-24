# Breakout Design
## PhilipLee


## Design Goals

- I wanted to create different classes that manage different key properties of the game, such as the paddle, the ball, as well as blocks.
- The goals of this program was to incoporate class hierarchy in order to facilitate the adding of new levels with ease. 
- Constants and objects should be easy to create as well as used and called from other classes.
- The block class also features an abstract class that allows for further manipulation of the different types of blocks if needed for future 
updates or developers to alter its properties. The use of the abstract class is used in order to facilitate the ease of increasing difficulty 
if new levels are added, by introducing new types of custom blocks.
- Lastly, the code should be readable, and easy to understand. The design should make it so a developer can follow the flow of the program reasonably 
well, guided by concise, unrepeated, and articulately commented code.

## High-Level Design

- The Main Class will be the main class that handles all the operations/logistics. This class manages the flow of the game,
controlling when scenes should be set up, and when to flow to a splash screen.
- The Ball class focuses on managing the properties and methods of the game ball.
- The Paddle class focuses on managing the properties and methods of the paddle.
- The Game class handles the interactions between the paddle and the ball. It also manages the interactions between these two objects
with powerups,controlling the spawning of powerups. This class also contains information about the Game, like your current score, lives remaining, 
and also handles inputs from the keyboard to play the game or activate cheat keys.

## Assumptions or Simplifications
- Game mechanics are self-explanatory: a user could understand the game when given a paddle that can "reflect" the ball to break blocks.
- Graphics and visuals can be ran by an old computer, given that they have the correct version of Java and JavaFX installed. 
- The coloring of the objects from light to dark signify their health, with darker colors meaning the higher the health of the block.
- JavaFX shapes can also be used instead of images to represent a GUI for users to play the game.
- Text files are used for ease of creating a level, with the program reading in the text files to automatically load the level.


## Changes from the Plan
- Initially, I thought about creating a laser, but felt that the laser was too overpowered, and very implementation heavy.
- Instead of an abstract class for powerups, I realized that it was simpler to create methods that could imitate a powerup.
- Instead of a control class, I chose to control the flow of the came in Main and use Game to run typical game functions.
- While in the process of implementing tilting, I realized that it used heavy trignometry to maintain the physics of the ball bouncing,
and that the hitbox of JavaFX rectangles would make the implementation very complex. In addition, rotating would be too non-user friendly as
there would be too many things to control which may make the game too difficult.
- For the "Obstacle block" in the plan, I chose to implement it as a "BadBlock", but instead of adding health to blocks surrounding it,
I chose to do it randomly in order to make the game more interesting and randomized, making the game more difficult and unpredictable.


## How to Add New Levels
- In order to add new levels, simply create a text file with the block layout, and add the level's text file into the levels array in Main.
- The program will automatically load the level, as well as create the splash screens with updates score and texts. This is due to the fact that the program
implements automatic changes to the next level and generates a new splash screen according to the incoming level. This is done through the communication of different methods,
similar to a "looping" of the levels array.

