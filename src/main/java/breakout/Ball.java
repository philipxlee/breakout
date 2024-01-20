package breakout;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Iterator;



public class Ball {

    private static final int BALL_START_X = Game.SIZE / 2;
    private static final int BALL_START_Y = Game.SIZE / 2;
    private static final int BALL_RADIUS = 5;
    public boolean slowed = false;

    private static final int BALL_VELOCITY_X = 0;
    private static final int BALL_VELOCITY_Y = 150;
    private int ballVelocityX = BALL_VELOCITY_X;
    private int ballVelocityY = BALL_VELOCITY_Y;
    public Circle createBall() {
        Circle newBall = new Circle(BALL_START_X, BALL_START_Y, BALL_RADIUS);
        newBall.setFill(Color.YELLOW);
        return newBall;
    }



}
