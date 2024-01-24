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
import javafx.scene.shape.Circle;
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

    private int currentLevel = 0;
    private Scene myScene;
    private List<Level> levels;
    private Game game;
    private Ball ball;
    private Paddle paddle;
    private Text scoreText;
    private Text livesText;
    private Text powerUpsText;

    @Override
    public void start(Stage stage) {

        // Display starting splash screen
        Scene myStartScreen = setGameIntroductionScene();
        stage.setScene(myStartScreen);
        stage.setTitle("Breakout by Philip Lee");
        stage.show();

        // Create game instances
        game = new Game();
        ball = new Ball();
        paddle = new Paddle();

        // Start game
        myStartScreen.setOnMouseClicked(e -> startGame(stage));

        // Create levels
        levels = new ArrayList<>();
        levels.add(new Level("src/main/resources/Level1Layout"));
        levels.add(new Level("src/main/resources/Level2Layout"));
        levels.add(new Level("src/main/resources/Level3Layout"));
    }

    public void showNextLevelIntroduction(Stage stage) {
        if (currentLevel < levels.size()) {
            currentLevel++;
            setLevelIntroductionScreen(currentLevel, stage);
        }
        else {
            showGameOverScreen(stage, "Congratulations! :)\nYou won!\nScore: " + game.getScore() + "\nLives Remaining: " + game.getLives() + "\nPowerups used: " + game.getPowerupUsed());
        }
    }

    public void showGameOverScreen(Stage stage, String message) {
        Group root = new Group();
        Text gameOverText = createText(message, Color.WHITE, 30);
        gameOverText.setX(SIZE / 4.0);
        gameOverText.setY(SIZE / 3.0);
        root.getChildren().add(gameOverText);
        Scene gameOverScene = new Scene(root, SIZE, SIZE, DUKE_BLUE);
        stage.setScene(gameOverScene);
    }

    public void updateScore(int newScore) { scoreText.setText("Score: " + newScore); }
    public void updateLives(int newRemainingLives) { livesText.setText("Lives: " + newRemainingLives); }
    public void updatePowerupsUsed(int newPowerupUsed) { powerUpsText.setText("Power-ups: " + newPowerupUsed); }
    public void clearPowerups(Group root) { root.getChildren().removeIf(node -> node instanceof Circle && "Powerup".equals(node.getId()));}

    private void startGame(Stage stage) {
        myScene = setGameScene();
        game.setGameComponents(myScene, ball, paddle, this);
        ball.setBallComponents(game, this);

        stage.setScene(myScene);
        Group root = (Group) myScene.getRoot();
        showNextLevelIntroduction(stage);
        myScene.setOnKeyPressed(e -> game.handleKeyInput(e.getCode(), root, stage));

        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(DELAY), e -> {
            if (!game.isGameOver()) {
                game.updateBall(DELAY, root, stage);
            }
        }));
        animation.play();
    }

    private Scene setGameScene() {
        // Create group
        Group root = new Group();
        root.getChildren().add(ball.getBall());
        root.getChildren().add(paddle.getPaddle());

        // Create scoreboard showing score and lives
        VBox overlay = createScoreboard();
        root.getChildren().add(overlay);

        // Create and return scene
        myScene = new Scene(root, SIZE, SIZE, DUKE_BLUE);
        return myScene;
    }

    private Text createText(String text, Color color, int size) {
        Text newText = new Text(text);
        newText.setFont(Font.font("Verdana", FontWeight.BOLD, size));
        newText.setFill(color);
        return newText;
    }


    private Scene setGameIntroductionScene() {
        Group root = new Group();
        Text welcomeText = createText("Welcome to Breakout!", Color.WHITE, 20);
        Text rule1 = createText("Rule 1: Hit all the blocks (besides black ones)", Color.WHITE, 15);
        Text rule2 = createText("Rule 2: Don't let the ball fall", Color.WHITE, 15);
        Text rule3 = createText("Rule 3: Have fun! :)", Color.WHITE, 15);
        Text keys = createText("Cheat keys:\n - 1: Extend Paddle\n - 2: Increase paddle horizontal speed\n" +
                " - 3: Increase size of game ball\n - 4: Toggle button to lower speed\n - 5: Resets 1, 2, 3\n" +
                " - S: Skips level\n - R: Resets paddle to original position\n - L: Add a life\n", Color.WHITE, 15);

        VBox rulesBox = new VBox(10);
        rulesBox.getChildren().addAll(welcomeText, rule1, rule2, rule3, keys);
        rulesBox.setAlignment(Pos.TOP_LEFT);
        rulesBox.setLayoutX(SIZE / 5.0);
        rulesBox.setLayoutY(SIZE / 5.0);
        root.getChildren().add(rulesBox);
        return new Scene(root, SIZE, SIZE, DUKE_BLUE);
    }

    private void setLevelIntroductionScreen(int level, Stage stage) {
        Group root = new Group();
        Text levelText = createText("Level " + level + " is starting!\n\nCurrent score: " + game.getScore() + "\n\nLives remaining: " + game.getLives() + "\n\nPowerups used: " + game.getPowerupUsed(), Color.WHITE, 20);
        levelText.setX(SIZE / 6.0);
        levelText.setY(SIZE / 4.0);

        root.getChildren().add(levelText);
        Scene levelSplashScene = new Scene(root, SIZE, SIZE, DUKE_BLUE);
        stage.setScene(levelSplashScene);

        levelSplashScene.setOnMouseClicked(e -> {
            nextLevel((Group) myScene.getRoot());
            stage.setScene(myScene);
        });
    }

    private VBox createScoreboard() {
        scoreText = createText("Score: " + game.getScore(), Color.WHITE, 12);
        livesText = createText("Lives: " + game.getLives(), Color.WHITE, 12);
        powerUpsText = createText("Power-ups: " + game.getPowerupUsed(), Color.WHITE, 12);

        VBox overlay = new VBox(5);
        overlay.setAlignment(Pos.TOP_LEFT);
        overlay.setPadding(new Insets(10));
        overlay.getChildren().addAll(scoreText, livesText, powerUpsText);
        overlay.setPickOnBounds(false);
        return overlay;
    }

    private void nextLevel(Group root) {
        clearBlocks(root);
        clearPowerups(root);
        ball.resetBall();
        Level level = levels.get(currentLevel - 1);
        level.showBlockLayout(root);
    }

    private void clearBlocks(Group root) { root.getChildren().removeIf(node -> node instanceof Block); }
}