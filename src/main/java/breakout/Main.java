package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author YOUR NAME HERE
 */
public class Main extends Application {
    // useful names for constant values used
    public static final String TITLE = "Breakout will Break Me";
    public static final Color DUKE_BLUE = new Color(0, 0.188, 0.529, 1);
    public static final Color YELLOW = new Color(0.236, 0.192, 0.67, 1); // add a 0. infront
    public static final int SIZE = 800;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double DELAY = 1.0 / FRAMES_PER_SECOND;

    private static final int BALL_RADIUS = 5;
    private static final int BALL_START_X = SIZE / 2;
    private static final int BALL_START_Y = SIZE / 2;
    private static final int BALL_VELOCITY_X = 0;
    private static final int BALL_VELOCITY_Y = 100;
    private static final int PADDLE_WIDTH = 50;
    private static final int PADDLE_HEIGHT = 6;


    private Scene myScene;
    private Circle myBall;
    private Rectangle myPaddle;
    private int ballVelocityX = BALL_VELOCITY_X;
    private int ballVelocityY = BALL_VELOCITY_Y;
    /**
     * Initialize what will be displayed.
     */
    @Override
    public void start (Stage stage) {

        // Show scene
        myScene = setupScene(SIZE, SIZE, DUKE_BLUE);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();

        // Set timeline
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(DELAY), e -> updateBall(DELAY)));
        animation.play();
    }

    public Scene setupScene (int width, int height, Color backgroundColor) {
        // Create ball
        myBall = new Circle(BALL_START_X, BALL_START_Y, BALL_RADIUS);
        myBall.setFill(Color.YELLOW);

        // Create paddle
        myPaddle = new Rectangle((width / 2) - (PADDLE_WIDTH / 2), height - 100, PADDLE_WIDTH, PADDLE_HEIGHT);
        myPaddle.setArcHeight(10);
        myPaddle.setArcWidth(10);
        myPaddle.setFill(Color.RED);

        // Create group
        Group root = new Group();
        root.getChildren().add(myBall);
        root.getChildren().add(myPaddle);

        // Create and return scene
        myScene = new Scene(root, width, height, backgroundColor);
        return myScene;
    }

    private void updateBall(double timestamp) {

        // Move ball
        myBall.setCenterX(myBall.getCenterX() + ballVelocityX * timestamp);
        myBall.setCenterY(myBall.getCenterY() + ballVelocityY * timestamp);

        // Collisions with panel
        if (myBall.intersects(myPaddle.getBoundsInLocal())) {
            ballVelocityY *= -1;
            double paddleCenter = myPaddle.getX() + myPaddle.getWidth() / 2;
            double ballCenter = myBall.getCenterX();
            double difference = ballCenter - paddleCenter;
            ballVelocityX += difference / 10; // Adjust X velocity based on hit location
        }

        // Collision with the window boundaries
        if (myBall.getCenterX() - BALL_RADIUS <= 0 || myBall.getCenterX() + BALL_RADIUS >= SIZE) {
            ballVelocityX *= -1; // Reverse X direction
        }
        if (myBall.getCenterY() - BALL_RADIUS <= 0 || myBall.getCenterY() + BALL_RADIUS >= SIZE) {
            ballVelocityY *= -1; // Reverse Y direction
        }
    }
}
