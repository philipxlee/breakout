package breakout;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import java.util.Iterator;

public class Ball {

    private static final int BALL_START_X = Game.SIZE / 2;
    private static final int BALL_START_Y = Game.SIZE / 2;
    private static final int BALL_RADIUS = 5;
    private static final int BALL_VELOCITY_X = 0;
    private static final int BALL_VELOCITY_Y = 150;

    private int ballVelocityX = BALL_VELOCITY_X;
    private int ballVelocityY = BALL_VELOCITY_Y;
    private final Circle myBall;
    private Game game;
    private Main main;
    private boolean slowed = false;

    public Ball() {
        myBall = new Circle(BALL_START_X, BALL_START_Y, BALL_RADIUS);
        myBall.setFill(Color.YELLOW);
    }

    public void setBallComponents(Game game, Main main) {
        this.game = game;
        this.main = main;
    }

    public void resetBall() {
        setCenterX(Game.SIZE / 2.0);
        setCenterY(Game.SIZE / 2.0);
        ballVelocityX = 0;
        ballVelocityY = 150;
    }

    public void handleBallSpeed() {
        if (slowed) {
            slowed = false;
            ballVelocityX = ballVelocityX * 2;
            ballVelocityY = ballVelocityY * 2;
        } else {
            slowed = true;
            ballVelocityX = ballVelocityX / 2;
            ballVelocityY = ballVelocityY / 2;
        }
    }

    public void handleBallMovement(double timestamp, Paddle myPaddle, Scene myScene) {
        // Move ball
        myBall.setCenterX(myBall.getCenterX() + ballVelocityX * timestamp);
        myBall.setCenterY(myBall.getCenterY() + ballVelocityY * timestamp);

        // Handle collisions
        handleBallPanelCollision(myPaddle);
        handleBallWindowCollision();
        handleBallBlockCollision(myScene);
    }

    private void handleBallWindowCollision() {
        if (myBall.getCenterX() - BALL_RADIUS <= 0 || myBall.getCenterX() + BALL_RADIUS >= Game.SIZE) {
            ballVelocityX *= -1;
        }
        if (myBall.getCenterY() - BALL_RADIUS <= 0 || myBall.getCenterY() + BALL_RADIUS >= Game.SIZE) {
            ballVelocityY *= -1;
        }
    }

    private void handleBallPanelCollision(Paddle myPaddle) {
        if (myBall.intersects(myPaddle.getBoundsInLocal())) {
            ballVelocityY *= -1;
            double paddleCenter = myPaddle.getX() + myPaddle.getWidth() / 2;
            double ballCenter = myBall.getCenterX();
            double difference = ballCenter - paddleCenter;
            ballVelocityX += (int) (difference / 5); // Adjust X velocity based on hit location
        }
    }

    private void handleBallBlockCollision(Scene myScene) {
        Iterator<Node> iter = myScene.getRoot().getChildrenUnmodifiable().iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node instanceof Block) {
                Block block = (Block) node;
                if (myBall.intersects(block.getBoundsInParent())) {
                    block.hit();
                    ballVelocityY *= -1;
                    if (!(block instanceof UnbreakableBlock)) {
                        game.addScore();
                        main.updateScore(game.getScore());
                        if (block.isDestroyed()) {
                            Platform.runLater(() -> ((Group) myScene.getRoot()).getChildren().remove(block));
                        }
                    }
                    break;
                }
            }
        }
    }

    public Circle getBall() { return myBall; }
    public double getCenterX() { return myBall.getCenterX(); }
    public double getCenterY() { return myBall.getCenterY(); }
    public void setCenterX(double x) { myBall.setCenterX(x); }
    public void setCenterY(double y) { myBall.setCenterY(y); }
}
