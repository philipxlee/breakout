package breakout;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle {

    private static final int PADDLE_WIDTH = 50;
    private static final int PADDLE_HEIGHT = 6;
    private static final int SIZE = 600;

    private Rectangle myPaddle;

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
            myPaddle.setX(Math.max(myPaddle.getX() - 20, 0));
        }
    }

    public void handlePaddleRightMovement() {
        if (myPaddle.getX() + myPaddle.getWidth() >= SIZE) {
            myPaddle.setX(0);
        } else {
            myPaddle.setX(Math.min(myPaddle.getX() + 20, SIZE - myPaddle.getWidth()));
        }
    }

    public void handlePaddleDownMovement() { myPaddle.setY(Math.min(myPaddle.getY() + 10, SIZE - myPaddle.getHeight())); }
    public void handlePaddleUpMovement() { myPaddle.setY(Math.max(myPaddle.getY() - 10, 0)); }
    public Rectangle getPaddle() { return myPaddle; }
    public double getX() { return myPaddle.getX(); }
    public double getY() { return myPaddle.getY(); }
    public void setX(double x) { myPaddle.setX(x); }
    public void setY(double y) { myPaddle.setY(y); }
    public double getWidth() { return myPaddle.getWidth(); }
    public double getHeight() { return myPaddle.getHeight(); }
    public Bounds getBoundsInLocal() { return myPaddle.getBoundsInLocal(); }
}
