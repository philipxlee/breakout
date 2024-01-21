package breakout;

import java.util.Random;

import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Powerup {
    private static final int POWERUP_VELOCITY_X = 1;
    private static final int POWERUP_VELOCITY_Y = 100;
    private static final int POWERUP_RADIUS = 5;

    Circle myPowerup;
    Paddle myPaddle;
    Ball myBall;
    Random rand = new Random();

    int powerupVelocityX = POWERUP_VELOCITY_X * rand.nextInt(101) - 50;
    int powerupVelocityY = POWERUP_VELOCITY_Y;

    public Powerup(int x, int y, Paddle paddle, Ball ball) {
        myPowerup = new Circle(x, y, POWERUP_RADIUS);
        this.myPaddle = paddle;
        this.myBall = ball;
    }

    public void handlePowerupMovement(Scene myScene, Group root, Stage stage) {
        myPowerup.setCenterX(myPowerup.getCenterX() + powerupVelocityX);
        myPowerup.setCenterY(myPowerup.getCenterY() + powerupVelocityY);
        handlePowerupPanelCollision(myPaddle);
        handlePowerupWindowCollision();
    }


    public void handlePowerupPanelCollision(Paddle myPaddle) {
        if (myPowerup.intersects(myPaddle.getBoundsInLocal())) {
          //TODO: Implement activate powerup
            ;
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

    public void laserPaddle() {

    }











}
