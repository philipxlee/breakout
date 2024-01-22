package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Level {
    private final int INFINITE_HEALTH = 1_000_000;
    protected final int X_OFFSET = 30;
    protected final int Y_OFFSET = 50;
    protected int levelNumber;
    protected String pathName;
    protected int blockWidth = Game.SIZE / 10;
    protected int blockHeight = 30;
    protected HashMap<Integer, Color> colorMap = new HashMap<>() {{
        put(3, Color.RED);
        put(2, Color.ORANGE);
        put(1, Color.GREEN);
    }};;

    public Level(int levelNumber, String pathName) {
        this.levelNumber = levelNumber;
        this.pathName = pathName;
    }

    public void showBlockLayout(Group root) { setBlockLayout(root, this.pathName); }

    public void setBlockLayout(Group root, String pathname) {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathName))) {
            String line;
            int row = 0;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split("\\s+"); // Split on one or more spaces
                for (int i = 0; i < values.length; i++) {
                    if (values[i].isEmpty() || values[i].equals("0")) continue;
                    int x = (i * blockWidth) + X_OFFSET;
                    int y = (row * blockHeight) + Y_OFFSET;
                    generateBlocks(root, values, i, x, y);
                }
                row++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateBlocks(Group root, String[] values, int i, int x, int y) {
        if (values[i].equals("X")) {
            Block block = new UnbreakableBlock(x, y, blockWidth, blockHeight, INFINITE_HEALTH);
            root.getChildren().add(block);
        } else if (values[i].equals("B")) {
            Block block = new BadBlock(x, y, blockWidth, blockHeight, 1);
            root.getChildren().add(block);
        } else {
            int health = Integer.parseInt(values[i]);
            Block block = new NormalBlock(x, y, blockWidth, blockHeight, health);
            Color color = colorMap.get(health);
            block.setFill(color);
            root.getChildren().add(block);
        }
    }
}