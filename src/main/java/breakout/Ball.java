package breakout;

import java.util.Iterator;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Ball {
    public static final int BALL_RADIUS = 8;
    private static final int BALL_START_X = Main.SIZE / 2;
    private static final int BALL_START_Y = Main.SIZE / 2;
    private static final int BALL_VELOCITY_X = 0;
    private static final int BALL_VELOCITY_Y = 150;
    private static final int VELOCITY_CHANGE_STEP = 40;

    private int ballVelocityX = BALL_VELOCITY_X;
    private int ballVelocityY = BALL_VELOCITY_Y;
    private boolean slowed = false;
    private Circle myBall;
    private Game game;
    private Main main;

    public Ball() {
        myBall = new Circle(BALL_START_X, BALL_START_Y, BALL_RADIUS);
        myBall.setFill(Color.YELLOW);
        myBall.setStroke(Color.BLACK);
        myBall.setStrokeWidth(2.5);
    }

    public void setBallComponents(Game game, Main main) {
        this.game = game;
        this.main = main;
    }

    public void resetBall() {
        setCenterX(Main.SIZE / 2.0);
        setCenterY(Main.SIZE / 2.0);
        ballVelocityX = BALL_VELOCITY_X;
        ballVelocityY = BALL_VELOCITY_Y;
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

    public void handleBallMovement(double timestamp, Paddle myPaddle, Scene myScene, Group root, Stage stage) {
        myBall.setCenterX(myBall.getCenterX() + ballVelocityX * timestamp);
        myBall.setCenterY(myBall.getCenterY() + ballVelocityY * timestamp);
        handleBallPanelCollision(myPaddle);
        handleBallWindowCollision();
        handleBallBlockCollision(myScene, root, stage);
    }

    public void enlargeBallSize() { myBall.setRadius(BALL_RADIUS * 2); }
    public void resetBallSize() { myBall.setRadius(BALL_RADIUS); }
    public double getCenterY() { return myBall.getCenterY(); }
    public void setCenterX(double x) { myBall.setCenterX(x); }
    public void setCenterY(double y) { myBall.setCenterY(y); }
    public Circle getBall() { return myBall; }

    private void handleBallWindowCollision() {
        if (myBall.getCenterX() - BALL_RADIUS <= 0 || myBall.getCenterX() + BALL_RADIUS >= Main.SIZE) {
            ballVelocityX *= -1;
        }
        if (myBall.getCenterY() - BALL_RADIUS <= 0 || myBall.getCenterY() + BALL_RADIUS >= Main.SIZE) {
            ballVelocityY *= -1;
        }
    }

    private void handleBallPanelCollision(Paddle myPaddle) {
        if (myBall.intersects(myPaddle.getBoundsInLocal())) {
            ballVelocityY *= -1;
            double paddleCenter = myPaddle.getX() + myPaddle.getWidth() / 2;
            double ballCenter = myBall.getCenterX();
            double difference = ballCenter - paddleCenter;
            double sixthPaddleWidth = myPaddle.getWidth() / 6;

            for (int i = 1; i <= 6; i++) {
                if (myBall.getCenterX() <= myPaddle.getX() + (i * sixthPaddleWidth)) {
                    int velocityChange = calculateVelocityChange(i, difference);
                    ballVelocityX = velocityChange;
                    break;
                }
            }
        }
    }

    private int calculateVelocityChange(int segment, double difference) {
        // Adjust the velocity change based on the segment hit
        int velocityChange = (segment - 3) * VELOCITY_CHANGE_STEP;
        if (segment > 3) {
            velocityChange += (int) difference;
        } else {
            velocityChange -= (int) difference;
        }
        return velocityChange;
    }

    private void handleBallBlockCollision(Scene myScene, Group root, Stage stage) {
        Iterator<Node> iter = myScene.getRoot().getChildrenUnmodifiable().iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node instanceof Block && updateBlockHealthAndVelocity(myScene, root, stage, (Block) node)) {
                break;
            }
        }
    }


    private boolean updateBlockHealthAndVelocity(Scene myScene, Group root, Stage stage, Block block) {
        if (myBall.intersects(block.getBoundsInParent())) {
            block.hit();
            ballVelocityX *= -1;
            ballVelocityY *= -1;
            checkForBadBlock(myScene, root, block);
            checkForUnbreakableBlock(myScene, root, stage, block);
            return true;
        }
        return false;
    }

    private void checkForBadBlock(Scene myScene, Group root, Block block) {
        if (block instanceof BadBlock) {
            game.handleBadBlockEffect(root);
            Platform.runLater(() -> ((Group) myScene.getRoot()).getChildren().remove(block));
        }
    }


    private void checkForUnbreakableBlock(Scene myScene, Group root, Stage stage, Block block) {
        if (!(block instanceof UnbreakableBlock)) {
            game.addScore(1);
            main.updateScore(game.getScore());
            if (block.isDestroyed()) {
                game.createPowerup(block, root);
                Platform.runLater(() -> ((Group) myScene.getRoot()).getChildren().remove(block));
            }
            game.checkLevelCompletion(root, stage);
        }
    }
}