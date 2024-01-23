package breakout;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;



public class Game {

    public static final int SIZE = 600;

    private static final int BALL_RADIUS = Ball.BALL_RADIUS;
    private int lives = 3;
    private int score = 0;
    private int powerupUsed = 0;
    private boolean isGameOver = false;
    private Scene myScene;
    private Ball myBall;
    private Paddle myPaddle;
    private Main main;

    public void addScore(int add) { score += add; }
    public void toggleBallSpeed() { myBall.handleBallSpeed(); }
    public void setGameOver(boolean gameOver) { isGameOver = gameOver; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public boolean isGameOver() { return isGameOver; }


    private List<Powerup> activePowerups = new ArrayList<>();

    public void setGameComponents(Scene scene, Ball ball, Paddle paddle, Main main) {
        this.myScene = scene;
        this.myBall = ball;
        this.myPaddle = paddle;
        this.main = main;
    }

    public void handleKeyInput(KeyCode button, Group root, Stage stage) {
        switch(button) {
            case LEFT -> myPaddle.handlePaddleLeftMovement();
            case RIGHT -> myPaddle.handlePaddleRightMovement();
            case UP -> myPaddle.handlePaddleUpMovement();
            case DOWN -> myPaddle.handlePaddleDownMovement();
            case R -> myPaddle.resetPaddlePosition();
            case SHIFT -> toggleBallSpeed();
            case L -> main.updateLives(++lives);
            case S -> skipLevel(root, stage);
        }
    }

    public void checkLevelCompletion(Group root, Stage stage) {
        // Check if all destructible blocks are cleared
        boolean levelCleared = root.getChildren().stream()
                .filter(node -> node instanceof Block)
                .map(node -> (Block) node)
                .noneMatch(block -> !block.isDestroyed() && !(block instanceof UnbreakableBlock));
        if (levelCleared) {
            main.showNextLevelIntroduction(root, stage);
            myBall.resetBall();
            myPaddle.resetPaddlePosition();
        }
    }

    private void resetBall() { myBall.resetBall(); }

    private void handleBallAtBottomEdge(Stage stage, Group root) {
        if (myBall.getCenterY() + BALL_RADIUS >= SIZE) {
            resetBall();
            main.clearPowerups(root);
            myPaddle.resetPaddlePosition();
            lives--;
            main.updateLives(lives);
        }
        if (lives == 0 && !isGameOver) {
            setGameOver(true);
            main.showGameOverScreen(stage, "Sorry :(\nYou lost...\nScore: " + getScore() + "\nLives Remaining: " + getLives() + "\nPowerups used: " + getPowerupUsed());
        }
    }

    private void skipLevel(Group root, Stage stage) {
        main.showNextLevelIntroduction(root, stage);
        myPaddle.resetPaddlePosition();
        resetBall();
    }

    public void updateBall(double timestamp, Group root, Stage stage) {
        myBall.handleBallMovement(timestamp, myPaddle, myScene, root, stage);
        handleBallAtBottomEdge(stage, root);

        // Update each active powerup
        Iterator<Powerup> iterator = activePowerups.iterator();
        while (iterator.hasNext()) {
            Powerup powerup = iterator.next();
            powerup.handlePowerupMovement(myScene, root, stage);
            if (powerup.isCollected()) {
                System.out.println("Collected");
                addPowerupUsed();
                addScore(5);
                main.updatePowerupsUsed(getPowerupUsed());
                main.updateScore(getScore());
                iterator.remove();
            }
            if (powerup.isOffScreen()) {
                iterator.remove();
            }
        }
    }

    public int getPowerupUsed() { return powerupUsed; }

    public void createPowerup(Block block, Group root) {
        if (block instanceof NormalBlock && Math.random() < 0.2) { // 20% chance for powerup
            Powerup powerup = new Powerup((int) (block.getX() + block.getWidth() / 2), (int) block.getY(), myPaddle, myBall, this);
            root.getChildren().add(powerup.getPowerupCircle());
            activePowerups.add(powerup);
        }
    }

    private void addPowerupUsed() { powerupUsed++; }

    public void handleBadBlockEffect(Group root) {
        // Create a list to store eligible blocks
        List<Block> eligibleBlocks = new ArrayList<>();

        // Add all blocks that are not BadBlock to the list
        for (Node node : root.getChildren()) {
            if (node instanceof Block && !(node instanceof BadBlock)) {
                eligibleBlocks.add((Block) node);
            }
        }

        // Shuffle the list to randomize the order and choose blocks
        Collections.shuffle(eligibleBlocks);
        int blocksToAffect = Math.min(eligibleBlocks.size(), 1 + (int)(Math.random() * 2)); // Randomly 1 or 2

        // Increase the health of the first 1 or 2 blocks in the shuffled list
        for (int i = 0; i < blocksToAffect; i++) {
            Block block = eligibleBlocks.get(i);
            block.addHealth(1);
        }
    }

    public Game getGame() { return this; }
    public Paddle getPaddle() { return myPaddle; }
    public Ball getBall() { return myBall; }
}
