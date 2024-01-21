package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


public class Game {

    public static final int SIZE = 600;

    private static final int BALL_RADIUS = 5;
    private int lives = 3;
    private int score = 0;
    private boolean isGameOver = false;
    private Scene myScene;
    private Ball myBall;
    private Paddle myPaddle;
    private Main main;

    public void addScore() { score++; }
    public void toggleBallSpeed() { myBall.handleBallSpeed(); }
    public void setGameOver(boolean gameOver) { isGameOver = gameOver; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public boolean isGameOver() { return isGameOver; }

    public void updateBall(double timestamp, Group root, Stage stage) {
        myBall.handleBallMovement(timestamp, myPaddle, myScene, root, stage);
        handleBallAtBottomEdge(stage);
    }

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

    private void handleBallAtBottomEdge(Stage stage) {
        if (myBall.getCenterY() + BALL_RADIUS >= SIZE) {
            resetBall();
            myPaddle.resetPaddlePosition();
            lives--;
            main.updateLives(lives);
        }
        if (lives == 0 && !isGameOver) {
            setGameOver(true);
            main.showGameOverScreen(stage, "Sorry :(\nYou lost...");
        }
    }

    private void skipLevel(Group root, Stage stage) {
        main.showNextLevelIntroduction(root, stage);
        myPaddle.resetPaddlePosition();
        resetBall();
    }
}
