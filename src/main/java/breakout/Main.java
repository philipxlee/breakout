package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    private List<Level> levels;
    private Game game;
    private Ball ball;
    private Paddle paddle;
    private Text scoreText;
    private Text livesText;
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
        levels.add(new Level(1, "src/main/resources/Level1Layout"));
        levels.add(new Level(2, "src/main/resources/Level2Layout"));
        levels.add(new Level(3, "src/main/resources/Level3Layout"));
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
        ball.setBallComponents(game, this);

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
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(DELAY), e -> { if (!game.isGameOver()) game.updateBall(DELAY, root, stage);}));
        animation.play();
    }

    public Scene setGameScene(int width, int height, Color backgroundColor) {
        // Create group
        Group root = new Group();
        root.getChildren().add(ball.getBall());
        root.getChildren().add(paddle.getPaddle());

        // Create scoreboard showing score and lives
        VBox overlay = createScoreboard();
        root.getChildren().add(overlay);

        // Create and return scene
        myScene = new Scene(root, width, height, backgroundColor);
        return myScene;
    }

    private VBox createScoreboard() {
        // Initialize score and lives text
        scoreText = new Text("Score: 0");
        scoreText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        scoreText.setFill(Color.WHITE);
        livesText = new Text("Lives: " + game.getLives()); // Assuming getLives() method in Game class
        livesText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        livesText.setFill(Color.WHITE);

        // Create a VBox for score and lives
        VBox overlay = new VBox(5);
        overlay.setAlignment(Pos.TOP_LEFT);
        overlay.setPadding(new Insets(10));
        overlay.getChildren().addAll(scoreText, livesText);
        overlay.setPickOnBounds(false);
        return overlay;
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
        clearBlocks(root);
        ball.resetBall();
        Level level = levels.get(currentLevel - 1);
        level.showBlockLayout(root);
    }

    public void showNextLevelIntroduction(Group root, Stage stage) {
        if (currentLevel < levels.size()) {
            currentLevel++;
            setLevelIntroductionScreen(currentLevel, stage);
        }
        else {
            showGameOverScreen(stage, "Congratulations! :)\nYou won!");
        }
    }

    public void showGameOverScreen(Stage stage, String message) {
        Group root = new Group();

        Text gameOverText = new Text(message);
        gameOverText.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        gameOverText.setFill(Color.WHITE);
        gameOverText.setX(SIZE / 4.0);
        gameOverText.setY(SIZE / 3.0);

        root.getChildren().add(gameOverText);
        Scene gameOverScene = new Scene(root, SIZE, SIZE, DUKE_BLUE);

        stage.setScene(gameOverScene);
    }

    public void updateScore(int newScore) { scoreText.setText("Score: " + newScore); }
    public void updateLives(int newRemainingLives) { livesText.setText("Lives: " + newRemainingLives); }
    private void clearBlocks(Group root) { root.getChildren().removeIf(node -> node instanceof Block); }

}
