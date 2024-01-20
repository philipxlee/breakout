package breakout;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Iterator;

public class Game {

    public static final String TITLE = "Breakout will Break Me";
    public static final Color DUKE_BLUE = new Color(0, 0.188, 0.529, 1); // add 0. infront
    public static final int SIZE = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double DELAY = 1.0 / FRAMES_PER_SECOND;

    private static final int BALL_RADIUS = 5;
    private static final int BALL_VELOCITY_X = 0;
    private static final int BALL_VELOCITY_Y = 150;
    private static final int PADDLE_WIDTH = 50;
    private static final int PADDLE_HEIGHT = 6;


    private Scene myScene;
    private Circle myBall;
    private Rectangle myPaddle;
    private Main main;
    private int ballVelocityX = BALL_VELOCITY_X;
    private int ballVelocityY = BALL_VELOCITY_Y;
    private int myHealth = 3;
    private boolean slowed = false;

    public void setGameComponents(Scene scene, Circle ball, Rectangle paddle, Main main) {
        this.myScene = scene;
        this.myBall = ball;
        this.myPaddle = paddle;
        this.main = main;
    }

    public void handleKeyInput(KeyCode button, Group root, Stage stage) {

        // Check which key was pressed
        switch(button) {
            case LEFT -> handlePaddleLeftMovement();
            case RIGHT -> handlePaddleRightMovement();
            case UP -> myPaddle.setY(Math.max(myPaddle.getY() - 10, 0));
            case DOWN -> myPaddle.setY(Math.min(myPaddle.getY() + 10, SIZE - myPaddle.getHeight()));
            case R -> resetPaddlePosition();
            case SHIFT -> toggleBallSpeed();
            case L -> myHealth++;
            case S -> skipLevel(root, stage);
        }
    }
    public void toggleBallSpeed() {
        if (!slowed) {
            ballVelocityX = ballVelocityX / 2;
            ballVelocityY = ballVelocityY / 2;
            slowed = true;
        } else {
            slowed = false;
            ballVelocityX = ballVelocityX * 2;
            ballVelocityY = ballVelocityY * 2;
        }
    }

    private void skipLevel(Group root, Stage stage) {
        main.showNextLevelIntroduction(root, stage);
        resetPaddlePosition();
        resetBall();
    }
    private void handlePaddleLeftMovement() {
        if (myPaddle.getX() <= 0) {
            myPaddle.setX(SIZE - myPaddle.getWidth());
        } else {
            myPaddle.setX(Math.max(myPaddle.getX() - 20, 0));
        }
    }

    private void handlePaddleRightMovement() {
        if (myPaddle.getX() + myPaddle.getWidth() >= SIZE) {
            myPaddle.setX(0);
        } else {
            myPaddle.setX(Math.min(myPaddle.getX() + 20, SIZE - myPaddle.getWidth()));
        }
    }

    private void resetPaddlePosition() {
        myPaddle.setX((SIZE / 2) - (PADDLE_WIDTH / 2));
        myPaddle.setY(SIZE - 100);
    }


    private void resetBall() {
        myBall.setCenterX(SIZE / 2);
        myBall.setCenterY(SIZE / 2);
        ballVelocityX = BALL_VELOCITY_X;
        ballVelocityY = BALL_VELOCITY_Y;
    }
    public void updateBall(double timestamp) {

        // Move ball
        myBall.setCenterX(myBall.getCenterX() + ballVelocityX * timestamp);
        myBall.setCenterY(myBall.getCenterY() + ballVelocityY * timestamp);

        // Collisions with panel
        if (myBall.intersects(myPaddle.getBoundsInLocal())) {
            ballVelocityY *= -1;
            double paddleCenter = myPaddle.getX() + myPaddle.getWidth() / 2;
            double ballCenter = myBall.getCenterX();
            double difference = ballCenter - paddleCenter;
            ballVelocityX += difference / 5; // Adjust X velocity based on hit location
        }

        // Collision with the window boundaries
        if (myBall.getCenterX() - BALL_RADIUS <= 0 || myBall.getCenterX() + BALL_RADIUS >= Game.SIZE) {
            ballVelocityX *= -1;
        }
        if (myBall.getCenterY() - BALL_RADIUS <= 0 || myBall.getCenterY() + BALL_RADIUS >= Game.SIZE) {
            ballVelocityY *= -1;
        }

        // Collisions with a block
        Iterator<Node> iter = myScene.getRoot().getChildrenUnmodifiable().iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node instanceof Block) {
                Block block = (Block) node;
                if (myBall.intersects(block.getBoundsInParent())) {
                    block.hit();
                    ballVelocityY *= -1;
                    if (!(block instanceof UnbreakableBlock) && block.isDestroyed()) {
                        // Remove block
                        Platform.runLater(() -> ((Group)myScene.getRoot()).getChildren().remove(block));
                    }
                    break;
                }
            }
        }

        // Ball hitting the bottom edge
        if (myBall.getCenterY() + BALL_RADIUS >= Game.SIZE) {
            resetBall();
            myHealth--;
            if (myHealth == 0) {
                Platform.exit(); // Quit game
            }
        }
    }
}
