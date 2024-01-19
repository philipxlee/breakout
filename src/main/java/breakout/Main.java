package breakout;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

/**
 * Game of Breakout with Variations
 * @author Philip Lee
 */

public class Main extends Application {

    private int currentLevel = 0;
    private List<Level> levels;

    @Override
    public void start(Stage stage) {
        Game game = new Game();
        game.init(stage, this);
        Scene myScene = game.getMyScene();
        Group root = (Group) myScene.getRoot();

        // Create levels
        levels = new ArrayList<>();
        levels.add(new Level1(1));
        levels.add(new Level2(2));
        levels.add(new Level3(3));
    }

    public void nextLevel(Group root) {

        // Clear remaining blocks
        clearBlocks(root);

        currentLevel++;
        if (currentLevel > levels.size()) {
            //TODO: Implement handleGameEnd
            return;
        }

        Level level = levels.get(currentLevel - 1);
        level.setUpBlocks(root);
    }

    public void goToNextLevel(Group root) {
        nextLevel(root);
    }

    private void clearBlocks(Group root) {
        root.getChildren().removeIf(node -> node instanceof Block);
    }
}
