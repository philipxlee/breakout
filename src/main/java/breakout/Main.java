package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Game of Breakout with Variations
 * @author Philip Lee
 */

public class Main extends Application {

    public static final int SIZE = 600;
    public static final Color DUKE_BLUE = new Color(0, 0.188, 0.529, 1);
    public static final int FRAMES_PER_SECOND = 60;
    public static final double DELAY = 1.0 / FRAMES_PER_SECOND;

    private Scene myScene;
    private Rectangle myPaddle;
    private List<Level> levels;
    private Game game;
    private Ball ball;
    private Paddle paddle;
    private int currentLevel = 0;

    @Override
    public void start(Stage stage) {

        // Display starting splash screen
        Scene myStartScreen = setGameIntroductionScene(SIZE, SIZE);
        stage.setScene(myStartScreen);
        stage.setTitle("Breakout is Breaking Me");
        stage.show();

        // Create game instances
        game = new Game();
        ball = new Ball();
        paddle = new Paddle();

        // Start game
        myStartScreen.setOnMouseClicked(e -> startGame(stage));

        // Create levels
        levels = new ArrayList<>();
        levels.add(new Level1(1));
        levels.add(new Level2(2));
        levels.add(new Level3(3));
    }

    public Scene setGameIntroductionScene(int width, int height) {
        Group root = new Group();

        Text gameRules = new Text("Welcome!");
        gameRules.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        gameRules.setFill(Color.WHITE);
        gameRules.setX(SIZE / 6.0);
        gameRules.setY(SIZE / 4.0);

        root.getChildren().add(gameRules);

        return new Scene(root, width, height, DUKE_BLUE);
    }

    private void startGame(Stage stage) {

        // Create scene and make game control correct scenes
        myScene = setGameScene(SIZE, SIZE, DUKE_BLUE);
        game.setGameComponents(myScene, ball, paddle, this);

        // Set the scene
        stage.setScene(myScene);
        Group root = (Group) myScene.getRoot();

        // Start level splash screen
        showNextLevelIntroduction(root, stage);

        // Event handler
        myScene.setOnKeyPressed(e -> game.handleKeyInput(e.getCode(), root, stage));

        // Set timeline
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(DELAY), e -> game.updateBall(DELAY)));
        animation.play();
    }

    public Scene setGameScene(int width, int height, Color backgroundColor) {
        // Create group
        Group root = new Group();
        root.getChildren().add(ball.getBall());
        root.getChildren().add(paddle.getPaddle());

        // Create and return scene
        myScene = new Scene(root, width, height, backgroundColor);
        return myScene;
    }

    public void setLevelIntroductionScreen(int level, Stage stage) {
        Group root = new Group();

        Text levelText = new Text("Level " + level + " is Starting!");
        levelText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        levelText.setFill(Color.WHITE);
        levelText.setX(SIZE / 6.0);
        levelText.setY(SIZE / 4.0);

        root.getChildren().add(levelText);
        Scene levelSplashScene = new Scene(root, SIZE, SIZE, DUKE_BLUE);
        stage.setScene(levelSplashScene);

        levelSplashScene.setOnMouseClicked(e -> {
            nextLevel((Group) myScene.getRoot());
            stage.setScene(myScene); // Set back the game scene
        });
    }

    public void nextLevel(Group root) {

        // Clear remaining blocks and move to next level
        clearBlocks(root);

        // Move to next level if game is not finished
        Level level = levels.get(currentLevel - 1);
        level.setUpBlocks(root);
    }

    public void showNextLevelIntroduction(Group root, Stage stage) {
        if (currentLevel < levels.size()) {
            currentLevel++;
            setLevelIntroductionScreen(currentLevel, stage);
        }
        else {
            // TODO: Implement handleGameEnd
            Platform.exit(); // Quit game
            return;
        }
    }

    private void clearBlocks(Group root) { root.getChildren().removeIf(node -> node instanceof Block); }

}
