package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.Iterator;
import javafx.scene.text.Text;

public class Game {

    public static final String TITLE = "Breakout will Break Me";
    public static final Color DUKE_BLUE = new Color(0, 0.188, 0.529, 1); // add 0. infront
    public static final int SIZE = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double DELAY = 1.0 / FRAMES_PER_SECOND;

    private static final int BALL_RADIUS = 5;
    private static final int BALL_START_X = SIZE / 2;
    private static final int BALL_START_Y = SIZE / 2;
    private static final int BALL_VELOCITY_X = 0;
    private static final int BALL_VELOCITY_Y = 150;
    private static final int PADDLE_WIDTH = 50;
    private static final int PADDLE_HEIGHT = 6;

    private Scene mySplashScene;
    private Scene myScene;
    private Circle myBall;
    private Rectangle myPaddle;
    private Main main;
    private int ballVelocityX = BALL_VELOCITY_X;
    private int ballVelocityY = BALL_VELOCITY_Y;

    public void init(Stage stage, Main main) {
        mySplashScene = setupSplashScene(SIZE, SIZE);
        stage.setScene(mySplashScene);
        stage.setTitle(TITLE);
        stage.show();
        this.main = main;

        mySplashScene.setOnMouseClicked(e -> startGame(stage));
    }

    public Scene setupSplashScene(int width, int height) {
        Group root = new Group();

        Text gameRules = new Text("Game Rules:\n\n- Rule 1\n- Rule 2\n- Rule 3\n\nClick to start.");
        gameRules.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        gameRules.setFill(Color.WHITE);
        gameRules.setX(SIZE / 4);
        gameRules.setY(SIZE /4);

        root.getChildren().add(gameRules);

        Scene scene = new Scene(root, width, height, DUKE_BLUE);
        return scene;
    }

    private void startGame(Stage stage) {

        myScene = setupScene(SIZE, SIZE, DUKE_BLUE);
        stage.setScene(myScene);

        // Start level
        main.goToNextLevel((Group) myScene.getRoot());

        // Event handler
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

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

    public Scene getMyScene() {
        return mySplashScene != null ? mySplashScene : myScene;
    }

    private void handleKeyInput(KeyCode button) {
        // Check which key was pressed
        Group root = (Group) myScene.getRoot();

        switch(button) {
            case LEFT -> {
                if (myPaddle.getX() <= 0) {
                    myPaddle.setX(SIZE - myPaddle.getWidth());
                }
                else {
                    myPaddle.setX(Math.max(myPaddle.getX() - 20, 0));
                }
            }
            case RIGHT -> {
                if (myPaddle.getX() + myPaddle.getWidth() >= SIZE) {
                    myPaddle.setX(0);
                }
                else {
                    myPaddle.setX(Math.min(myPaddle.getX() + 20, SIZE - myPaddle.getWidth()));
                }
            }
            case UP -> myPaddle.setY(Math.max(myPaddle.getY() - 10, 0));
            case DOWN -> myPaddle.setY(Math.min(myPaddle.getY() + 10, SIZE - myPaddle.getHeight()));
            case R -> {
                myPaddle.setX((SIZE / 2) - (PADDLE_WIDTH / 2));
                myPaddle.setY(SIZE - 100);
            }
            case S -> main.goToNextLevel(root);
        }
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
            ballVelocityX += difference / 5; // Adjust X velocity based on hit location
        }

        // Collision with the window boundaries
        if (myBall.getCenterX() - BALL_RADIUS <= 0 || myBall.getCenterX() + BALL_RADIUS >= SIZE) {
            ballVelocityX *= -1;
        }
        if (myBall.getCenterY() - BALL_RADIUS <= 0 || myBall.getCenterY() + BALL_RADIUS >= SIZE) {
            ballVelocityY *= -1;
        }

        // Collisions with a block
        Iterator<Node> iter = myScene.getRoot().getChildrenUnmodifiable().iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node instanceof Block) {
                Block block = (Block) node;
                if (myBall.intersects(block.getBoundsInParent())) {
                    block.hit();
                    ballVelocityY *= -1;
                    if (!(block instanceof UnbreakableBlock) && block.isDestroyed()) {
                        // Remove block
                        Platform.runLater(() -> ((Group)myScene.getRoot()).getChildren().remove(block));
                    }
                    break;
                }
            }
        }

        // Ball hitting the bottom edge
        if (myBall.getCenterY() + BALL_RADIUS >= SIZE) {
            ballVelocityY *= -1;
            myBall.setCenterX(SIZE / 2);
            myBall.setCenterY(SIZE / 2);
//            Platform.exit(); // Quit game
        }
    }
}
