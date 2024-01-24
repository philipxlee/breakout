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

    private int lives = 5;
    private int score = 0;
    private int powerupUsed = 0;
    private boolean isGameOver = false;
    private Scene myScene;
    private Ball myBall;
    private Paddle myPaddle;
    private Main main;
    private List<Powerup> activePowerups = new ArrayList<>();

    public void addScore(int add) { score += add; }
    public void toggleBallSpeed() { myBall.handleBallSpeed(); }
    public void setGameOver(boolean gameOver) { isGameOver = gameOver; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public boolean isGameOver() { return isGameOver; }


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
            case L -> main.updateLives(++lives);
            case S -> skipLevel(stage);
            case DIGIT1 -> myPaddle.extendPaddleWidth();
            case DIGIT2 -> myPaddle.speedUpPaddle();
            case DIGIT3 -> myBall.enlargeBallSize();
            case DIGIT4 -> toggleBallSpeed();
            case DIGIT5 -> {
                myPaddle.resetPaddleWidth();
                myPaddle.resetPaddleSpeed();
                myBall.resetBallSize();
            }
        }
    }

    public void checkLevelCompletion(Group root, Stage stage) {
        // Check if all destructible blocks are cleared
        boolean levelCleared = root.getChildren().stream()
                .filter(node -> node instanceof Block)
                .map(node -> (Block) node)
                .noneMatch(block -> !block.isDestroyed() && !(block instanceof UnbreakableBlock));
        if (levelCleared) {
            main.showNextLevelIntroduction(stage);
            myBall.resetBall();
            myPaddle.resetPaddlePosition();
        }
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

    public void createPowerup(Block block, Group root) {
        if (block instanceof NormalBlock && Math.random() < 0.2) { // 20% chance for powerup
            Powerup powerup = new Powerup((int) (block.getX() + block.getWidth() / 2), (int) block.getY(), myPaddle, myBall, this);
            root.getChildren().add(powerup.getPowerupCircle());
            activePowerups.add(powerup);
        }
    }

    public void handleBadBlockEffect(Group root) {
        // Create a list to store eligible blocks and add eligible to list
        List<Block> eligibleBlocks = new ArrayList<>();
        for (Node node : root.getChildren()) {
            if (node instanceof Block && !(node instanceof BadBlock) && !(node instanceof UnbreakableBlock)) {
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

    public int getPowerupUsed() { return powerupUsed; }

    private void handleBallAtBottomEdge(Stage stage, Group root) {
        if (myBall.getCenterY() + Ball.BALL_RADIUS >= Main.SIZE) {
            resetBall();
            main.clearPowerups(root);
            lives--;
            main.updateLives(lives);
            myPaddle.resetPaddlePosition();
        }
        if (lives == 0 && !isGameOver) {
            setGameOver(true);
            main.showGameOverScreen(stage, "Sorry :(\nYou lost...\nScore: " + getScore() + "\nLives Remaining: " + getLives() + "\nPowerups used: " + getPowerupUsed());
        }
    }

    private void skipLevel(Stage stage) {
        main.showNextLevelIntroduction(stage);
        myPaddle.resetPaddlePosition();
        resetBall();
    }

    private void resetBall() { myBall.resetBall(); }
    private void addPowerupUsed() { powerupUsed++; }
}