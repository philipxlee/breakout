package breakout;

import java.util.Random;
import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Powerup {
    private static final int POWERUP_VELOCITY_X = 1;
    private static final int POWERUP_VELOCITY_Y = 1;
    private static final int POWERUP_RADIUS = 8;

    Circle myPowerup;
    Paddle myPaddle;
    Ball myBall;
    Game myGame;
    Random rand = new Random();

    private int powerupVelocityX = POWERUP_VELOCITY_X + (rand.nextInt(5) - 3);
    private int powerupVelocityY = POWERUP_VELOCITY_Y;

    public Powerup(int x, int y, Paddle paddle, Ball ball, Game game) {
        myPowerup = new Circle(x, y, POWERUP_RADIUS);
        myPowerup.setFill(Color.ALICEBLUE);
        myPowerup.setStroke(Color.BLACK);
        myPowerup.setStrokeWidth(1.5);
        myPowerup.setId("Powerup");
        this.myPaddle = paddle;
        this.myBall = ball;
        this.myGame = game;
    }

    public void handlePowerupMovement(Scene myScene, Group root, Stage stage) {
        myPowerup.setCenterX(myPowerup.getCenterX() + powerupVelocityX);
        myPowerup.setCenterY(myPowerup.getCenterY() + powerupVelocityY);
        handlePowerupCollection(myPaddle);
        handlePowerupWindowCollision();
        if (isCollected() || isOffScreen()) {
            root.getChildren().remove(myPowerup);
        }
    }


    public void handlePowerupCollection(Paddle myPaddle) {
        if (myPowerup.intersects(myPaddle.getBoundsInLocal())) {
            switch(rand.nextInt(1,4)) {
                case 1 -> extendPaddle();
                case 2 -> speedPaddle();
                case 3-> enlargeBall();
            }
        }
    }

    public void handlePowerupWindowCollision() {
        if (myPowerup.getCenterX() - POWERUP_RADIUS <= 0 || myPowerup.getCenterX() + POWERUP_RADIUS >= Main.SIZE) {
            powerupVelocityX *= -1;
        }
        if (myPowerup.getCenterY() - POWERUP_RADIUS <= 0 || myPowerup.getCenterY() + POWERUP_RADIUS >= Main.SIZE) {
            powerupVelocityY *= -1;
        }
    }

    public void extendPaddle() {
        myPaddle.extendPaddleWidth();
        applyEffectWithDelay(() -> myPaddle.resetPaddleWidth());
    }

    public void speedPaddle() {
        myPaddle.speedUpPaddle();
        applyEffectWithDelay(() -> myPaddle.resetPaddleSpeed());
    }

    public void enlargeBall() {
        myBall.enlargeBallSize();
        applyEffectWithDelay(() -> myBall.resetBallSize());
    }

    public Circle getPowerupCircle() { return myPowerup; }
    public boolean isCollected() { return myPowerup.intersects(myPaddle.getBoundsInLocal()); }
    public boolean isOffScreen() { return myPowerup.getCenterY() + POWERUP_RADIUS >= Main.SIZE;}

    private void applyEffectWithDelay(Runnable action) {
        PauseTransition wait = new PauseTransition(Duration.seconds(15));
        wait.setOnFinished(event -> action.run());
        wait.play();
    }
}