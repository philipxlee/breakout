package breakout;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle {

    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 10;
    private static final int NORMAL_MOVEMENT = 20;
    private static final int FAST_MOVEMENT = 50;
    private final Rectangle myPaddle;
    private int currentMovement = NORMAL_MOVEMENT;

    public Paddle() {
        myPaddle = new Rectangle((Main.SIZE / 2.0) - (PADDLE_WIDTH / 2.0), Main.SIZE - 100, PADDLE_WIDTH, PADDLE_HEIGHT);
        myPaddle.setArcHeight(10);
        myPaddle.setArcWidth(10);
        myPaddle.setFill(Color.RED);
    }

    public void resetPaddlePosition() {
        myPaddle.setX((Main.SIZE / 2.0) - (PADDLE_WIDTH / 2.0));
        myPaddle.setY(Main.SIZE - 100);
    }

    public void handlePaddleLeftMovement() {
        if (myPaddle.getX() <= 0) {
            myPaddle.setX(Main.SIZE - myPaddle.getWidth());
        } else {
            myPaddle.setX(Math.max(myPaddle.getX() - currentMovement, 0));
        }
    }

    public void handlePaddleRightMovement() {
        if (myPaddle.getX() + myPaddle.getWidth() >= Main.SIZE) {
            myPaddle.setX(0);
        } else {
            myPaddle.setX(Math.min(myPaddle.getX() + currentMovement, Main.SIZE - myPaddle.getWidth()));
        }
    }

    public void handlePaddleDownMovement() { myPaddle.setY(Math.min(myPaddle.getY() + 10, Main.SIZE - myPaddle.getHeight())); }
    public void handlePaddleUpMovement() { myPaddle.setY(Math.max(myPaddle.getY() - 10, 0)); }
    public void extendPaddleWidth() { myPaddle.setWidth(PADDLE_WIDTH * 1.5); }
    public void resetPaddleWidth() { myPaddle.setWidth(PADDLE_WIDTH); }
    public void speedUpPaddle() { currentMovement = FAST_MOVEMENT; }
    public void resetPaddleSpeed() { currentMovement = NORMAL_MOVEMENT; }
    public double getX() { return myPaddle.getX(); }
    public double getWidth() { return myPaddle.getWidth(); }
    public Rectangle getPaddle() { return myPaddle; }
    public Bounds getBoundsInLocal() { return myPaddle.getBoundsInLocal(); }
}