# breakout
## Philip Lee


### Timeline

* Start Date: 15 January, 2024

* Finish Date: 23 January, 2024

* Hours Spent: 30 hours



### Attributions

* Oracle JavaFX Documentation -- https://docs.oracle.com/javase/8/javafx/api/toc.htm
* OpenAI ChatGPT -- https://chat.openai.com
* GeeksForGeeks JavaFX Tutorial -- https://www.geeksforgeeks.org/javafx-tutorial/


### Running the Program

* Main class:
   * Main.java


* Data files needed:
   * In the resource directory:
      * Level1Layout.txt
      * Level2Layout.txt
      * Level3Layout.txt


* Key/Mouse inputs:
   * Arrow keys to control up, down, left, right movement of the paddle.
   * Mouse click to transition from splash screens to the game, or the next level.


* Cheat keys:
   * __R__: Resets the paddle back to the starting position.
   * __S__: Skips to the next level.
   * __L__: Add a life.
   * __1__: Extends the size of the paddle.
   * __2__: Increases the horizontal speed of the paddle.
   * __3__: Increases the size of the game ball.
   * __4__: Toggle button to lower the ball speed by half or to increase it back to the original speed.
   * __5__: Resets 1, 2, 3 - effectively changing the paddle and ball back to its default properties (i.e. speed and size)



### Notes/Assumptions

* Assumptions or Simplifications:
   * Players can deduce that blocks of the same color have the same health.
   * Players can deduce that black blocks are unbreakable.
   * Players can deduce that purple blocks may affect the health of other blocks.
   * 3 lives is reasonable to beat all 3 levels.


* Known Bugs:
   * No known bugs.


* Features implemented:
   * Paddle:
      * Warping - ability for the paddle to teleport to the other edge upon reaching an edge.
      * Positional Bounces - the paddle is split into thirds. The left third would always cause the ball to bounce
        to the left, and the right third will always cause the ball to bounce to the right. The middle third, however,
        will depend on the direction the ball is making contact with the paddle. It follows the normal
        logic of how a ball, with its momentum in the X axis, is carried over when it makes contact with a physical object.

   * Block:
      * NormalBlock - a regular block with varying colors, with each color corresponding to a certain health. When a NormalBlock is broken, there is a chance that a powerup will spawn, and its powers will be randomly selected.
      * BadBlock - a purple block that will randomly increase the health of two NormalBlocks in the screen by 1, thus changing their color, when broken.
      * UnbreakableBlock - a black, indestructible block with a twist. When the game ball hits any corner of the UnbreakableBlock, the ball
        will "stick" to that edge and traverse it. When it reaches the other corner, the direction is reversed.

   * Power Ups:
      * Extend Paddle: The paddle will extend in width for 15 seconds.
      * Speed Paddle: The paddle will move quicker horizontally for 15 seconds.
      * Enlarge Ball: The game ball will enlarge for 15 seconds.


* Noteworthy Features:
   * BadBlock: Collects all the current NormalBlocks in the scene and shuffles it. Then, it takes the first two blocks in the array and increases its health,
     thus changing its color.



### Assignment Impressions

It was a challenging assignment in the sense that there had to be a learning curve to learn JavaFX. I initially struggled with grouping too many things together. For instance, 
at the beginning, I didn't have separate classes for Ball and Paddle, but instead had all of these methods that altered their states within one class, which led to messy code.
Refactoring and following best practices was also challenging, as I feel that a lot of coders have an idiosyncratic style of code. The assignment, however, was really fun and challeging,
albeit implementation heavy.