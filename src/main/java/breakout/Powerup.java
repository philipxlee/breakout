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

    int powerupVelocityX = POWERUP_VELOCITY_X + (rand.nextInt(5) - 3);
    int powerupVelocityY = POWERUP_VELOCITY_Y;

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
        handlePowerupPaddleCollision(myPaddle);
        handlePowerupWindowCollision();
        if (isCollected() || isOffScreen()) {
            root.getChildren().remove(myPowerup);
        }
    }


    public void handlePowerupPaddleCollision(Paddle myPaddle) {
        if (myPowerup.intersects(myPaddle.getBoundsInLocal())) {
            switch(rand.nextInt(1,4)) {
                case 1 -> extendPaddle();
                case 2 -> speedPaddle();
                case 3-> enlargeBall();
            }
        }
    }

    public void handlePowerupWindowCollision() {
        if (myPowerup.getCenterX() - POWERUP_RADIUS <= 0 || myPowerup.getCenterX() + POWERUP_RADIUS >= Game.SIZE) {
            powerupVelocityX *= -1;
        }
        if (myPowerup.getCenterY() - POWERUP_RADIUS <= 0 || myPowerup.getCenterY() + POWERUP_RADIUS >= Game.SIZE) {
            powerupVelocityY *= -1;
        }
    }

    public void extendPaddle() {
        myPaddle.extendPaddleWidth();
        PauseTransition wait = new PauseTransition(Duration.seconds(15));
        wait.setOnFinished(event -> myPaddle.resetPaddleWidth());
        wait.play();
    }

    public void speedPaddle() {
        myPaddle.speedUpPaddle();
        PauseTransition wait = new PauseTransition(Duration.seconds(15));
        wait.setOnFinished(event-> myPaddle.resetPaddleSpeed());
        wait.play();
    }

    public void enlargeBall() {
        myBall.enlargeBallSize();
        PauseTransition wait = new PauseTransition(Duration.seconds(15));
        wait.setOnFinished(event-> myBall.resetBallSize());
        wait.play();
    }

    public Circle getPowerupCircle() { return myPowerup; }
    public boolean isCollected() { return myPowerup.intersects(myPaddle.getBoundsInLocal()); }
    public boolean isOffScreen() { return myPowerup.getCenterY() + POWERUP_RADIUS >= Game.SIZE;}

}
