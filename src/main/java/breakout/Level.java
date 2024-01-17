package breakout;

import javafx.scene.Group;

public abstract class Level {

    public static final int SIZE = 800;

    protected int levelNumber;
    protected int rows = 5;
    protected int cols = 10;
    protected int blockWidth = SIZE / 10;
    protected int blockHeight = 20;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public void setUpBlocks(Group root) {
        // Create and add blocks
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * blockWidth;
                int y = row * blockHeight;
                int health = 1;
                Block block = new NormalBlock(x, y, blockWidth, blockHeight, health);
                root.getChildren().add(block);
            }
        }
    }
}

class Level1 extends Level {
    public Level1(int levelNumber) {
        super(levelNumber);
    }
}

class Level2 extends Level {
    public Level2(int levelNumber) {
        super(levelNumber);
    }

    @Override
    public void setUpBlocks(Group root) {
        // Create and add blocks
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * blockWidth;
                int y = row * blockHeight;
                int health = 1;
                Block block = new BadBlock(x, y, blockWidth, blockHeight, health);

                root.getChildren().add(block);
            }
        }
    }
}

class Level3 extends Level {
    public Level3(int levelNumber) {
        super(levelNumber);
    }

    @Override
    public void setUpBlocks(Group root) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * blockWidth;
                int y = row * blockHeight;
                int health = 1;
                Block block = new UnbreakableBlock(x, y, blockWidth, blockHeight, health);

                root.getChildren().add(block);
            }
        }
    }
}