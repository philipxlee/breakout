package breakout;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle {

    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;
    private static final int SIZE = 600;
    private static final int NORMAL_MOVEMENT = 20;
    private static final int FAST_MOVEMENT = 50;
    private int currentMovement = NORMAL_MOVEMENT;

    private final Rectangle myPaddle;

    public Paddle() {
        myPaddle = new Rectangle((Game.SIZE / 2.0) - (PADDLE_WIDTH / 2.0), Game.SIZE - 100, PADDLE_WIDTH, PADDLE_HEIGHT);
        myPaddle.setArcHeight(10);
        myPaddle.setArcWidth(10);
        myPaddle.setFill(Color.RED);
    }

    public void resetPaddlePosition() {
        myPaddle.setX((SIZE / 2.0) - (PADDLE_WIDTH / 2.0));
        myPaddle.setY(SIZE - 100);
    }

    public void handlePaddleLeftMovement() {
        if (myPaddle.getX() <= 0) {
            myPaddle.setX(SIZE - myPaddle.getWidth());
        } else {
            myPaddle.setX(Math.max(myPaddle.getX() - currentMovement, 0));
        }
    }

    public void handlePaddleRightMovement() {
        if (myPaddle.getX() + myPaddle.getWidth() >= SIZE) {
            myPaddle.setX(0);
        } else {
            myPaddle.setX(Math.min(myPaddle.getX() + currentMovement, SIZE - myPaddle.getWidth()));
        }
    }

    public void handlePaddleDownMovement() { myPaddle.setY(Math.min(myPaddle.getY() + 10, SIZE - myPaddle.getHeight())); }
    public void handlePaddleUpMovement() { myPaddle.setY(Math.max(myPaddle.getY() - 10, 0)); }
    public void extendPaddleWidth() { myPaddle.setWidth(PADDLE_WIDTH * 1.5); }
    public void resetPaddleWidth() { myPaddle.setWidth(PADDLE_WIDTH); }
    public void speedUpPaddle() { currentMovement = FAST_MOVEMENT; }
    public void resetPaddleSpeed() { currentMovement = NORMAL_MOVEMENT; }
    public Rectangle getPaddle() { return myPaddle; }
    public Bounds getBoundsInLocal() { return myPaddle.getBoundsInLocal(); }
    public double getX() { return myPaddle.getX(); }
    public double getY() { return myPaddle.getY(); }
    public void setX(double x) { myPaddle.setX(x); }
    public void setY(double y) { myPaddle.setY(y); }
    public double getWidth() { return myPaddle.getWidth(); }
    public double getHeight() { return myPaddle.getHeight(); }

}
