package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public abstract class Level {

    protected int levelNumber;
    protected int blockWidth = Game.SIZE / 10;
    protected int blockHeight = 30;
    protected final int X_OFFSET = 30;
    protected final int Y_OFFSET = 50;
    protected String pathName = "";
    protected HashMap<Integer, Color> colorMap = new HashMap<>() {{
        put(3, Color.RED);
        put(2, Color.ORANGE);
        put(1, Color.GREEN);
    }};;

    public Level(int levelNumber) { this.levelNumber = levelNumber; }
    public void showBlockLayout(Group root) {}
    public void setBlockLayout(Group root, String pathname) {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathName))) {
            String line;
            int row = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Trim the line
                if (line.isEmpty()) continue; // Skip empty lines

                String[] values = line.split("\\s+"); // Split on one or more spaces

                for (int i = 0; i < values.length; i++) {
                    if (values[i].isEmpty()) continue; // Skip empty values

                    int x = (i * blockWidth) + X_OFFSET;
                    int y = (row * blockHeight) + Y_OFFSET;

                    if (!values[i].equals("0")) {
                        if (values[i].equals("X")) {
                            // Create unbreakable block
                            Block block = new UnbreakableBlock(x, y, blockWidth, blockHeight, 999);
                            root.getChildren().add(block);
                        } else {
                            // Create normal block
                            int health = Integer.parseInt(values[i]);
                            Block block = new NormalBlock(x, y, blockWidth, blockHeight, health);
                            Color color = colorMap.get(health);
                            block.setFill(color);
                            root.getChildren().add(block);
                        }
                    }
                }
                row++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}


class Level1 extends Level {
    public Level1(int levelNumber) { super(levelNumber); }

    @Override
    public void showBlockLayout(Group root) {
        this.pathName = "src/main/resources/Level1Layout";
        setBlockLayout(root, pathName);
    }
}

class Level2 extends Level {
    public Level2(int levelNumber) {
        super(levelNumber);
    }

    @Override
    public void showBlockLayout(Group root) {
        this.pathName = "src/main/resources/Level2Layout";
        setBlockLayout(root, pathName);
    }
}

class Level3 extends Level {
    public Level3(int levelNumber) {
        super(levelNumber);
    }

    @Override
    public void showBlockLayout(Group root) {
        this.pathName = "src/main/resources/Level3Layout";
        setBlockLayout(root, pathName);
    }
}