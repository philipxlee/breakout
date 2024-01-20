package breakout;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


public class Game {

    public static final int SIZE = 600;
    private static final int BALL_RADIUS = 5;
    private Scene myScene;
    private Ball myBall;
    private Paddle myPaddle;
    private Main main;
    private int lives = 3;


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
            case L -> lives++;
            case S -> skipLevel(root, stage);
        }
    }

    private void resetBall() { myBall.resetBall(); }
    private void skipLevel(Group root, Stage stage) {
        main.showNextLevelIntroduction(root, stage);
        myPaddle.resetPaddlePosition();
        resetBall();
    }

    private void handleBallAtBottomEdge() {
        if (myBall.getCenterY() + BALL_RADIUS >= SIZE) {
            resetBall();
            lives--;
        }
        if (lives == 0) {
            Platform.exit(); // Quit game
        }
    }

    public void toggleBallSpeed() { myBall.handleBallSpeed(); }
    public void updateBall(double timestamp) {
        myBall.handleBallMovement(timestamp, myPaddle, myScene);
        handleBallAtBottomEdge();
    }
}
