# Breakout Plan
### Philip Lee

## Interesting Breakout Variants

 * Idea #1: Super Breakout - Power up that can cause the paddle to shoot lasers
that damage everything in its path. Multi ball triggers are also interesting.

 * Idea #2: Obstacle Breakout - A variant that imposes extra walls or obstacles
that are caused when a player hits a "bad" block. 

## Paddle Ideas

 * Idea #1: Warping - moves from one side to the other side when the paddle reaches
the end of the window.
 * Idea #2: Tilting - allows a user to tilt the paddle to redirect the ball.


## Block Ideas

 * Idea #1: Rectangular blocks with different health.

 * Idea #2: Power up blocks.

 * Idea #3: Obstacle blocks -  A "bad" block that, when hit, will increase the 
health of the normal blocks that surrounds it.

## Power-up Ideas

 * Idea #1: Speed boost: Paddle moves quicker for a certain amount of time.

 * Idea #2: Laser paddle: Shoots a laser that can damage/break everything in its path.

 * Idea #3: Increase length: Paddle increases length for a certain amount of time.


## Cheat Key Ideas

 * Idea #1: Extra lives.

 * Idea #2: Skip a level.

 * Idea #3: Slow-mo. Make the ball move slower indefinitely until a button is pressed.

 * Idea #4: Free power-up. Gives a free power-up to the player.


## Level Descriptions

 * Level 1:
   * Consists of basic structures such as regular blocks.
   * Some blocks may have more health.
   * For instance, in the text grid below, 0 represents whitespace and elements with
   a integer bigger than 0 represents a block with a health of that integer.
   * Note that this may not fully represent the implementation of the actual application.
 
          [1, 2, 3, 3, 3, 3, 3, 2, 1]
          [1, 1, 2, 3, 3, 3, 2, 1, 1]
          [1, 1, 1, 2, 3, 2, 1, 1, 1]
          [0, 0, 1, 1, 1, 1, 1, 0, 0]
          [0, 0, 0, 1, 1, 1, 0, 0, 0]
          [0, 0, 0, 0, 1, 0, 0, 0, 0]
          [0, 0, 0, 0, 0, 0, 0, 0, 0]
   
             ...Rest of display...


 * Level 2:
   * Introduces bad blocks and power ups.
   * Bad blocks may increase surrounding block's health.
   * Power ups drops abilities in a FIFO system, which can be used.
   * Display power-ups on the screen in a list.
   * For instance, let power-ups be P and bad blocks be B.
   * A user should be able to pass a level without breaking the bad blocks.
   * They can also choose to break the bad block but suffer the penalty.
   
          [1, 2, 3, 3, B, 3, 3, 2, 1]
          [0, 1, P, 3, 3, 3, P, 1, 0]
          [0, 1, 1, 2, 3, 2, 1, 1, 0]
          [0, 0, B, 1, 1, 1, B, 0, 0]
          [0, 0, 0, 1, B, 1, 0, 0, 0]
          [0, 0, 0, 0, 0, 0, 0, 0, 0]
          [0, 0, 0, 0, 0, 0, 0, 0, 0]

             ...Rest of display...

 * Level 3:
   * Introduces all the previous elements, but with things like smaller
   paddles, faster ball speed, and blocks.
   * There will also be impenetrable obstacle blocks.
   * For instance, these blocks will be marked with X.
     
         [1, X, 3, 3, B, 3, 3, X, 1]
         [0, X, P, X, 3, X, P, X, 0]
         [0, 1, 1, 2, X, 2, 1, 1, 0]
         [0, 0, B, 1, 1, 1, B, 0, 0]
         [0, 0, 0, 0, 0, 0, 0, 0, 0]
         [0, 0, 0, 0, 0, 0, 0, 0, 0]
         [0, 0, 0, 0, 0, 0, 0, 0, 0]

            ...Rest of display...

## Class Ideas

 * Idea #1: Blocks - an abstract class for basic block functionality and 
subclasses for different blocks. Blocks include NormalBlock, BadBlock, 
UnbreakableBlock, PowerUpBlock.


 * Idea #2: Power-ups - an abstract class for the basic features of a power up.
When a PowerUpBlock is broken, randomly choose to create an object of one of the subclasses
of this base class, which would represent a power-up. The power-up would then affect the ball 
speed or other things.


 * Idea #3: Control - an abstract class to manage the creation of each level. When to switch to
the next level.


 * Idea #4: Levels - an abstract class that creates the basic levels. The subclasses will include
more sophisticated levels, calling the Blocks class to add more blocks with effects.