package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Level {

    private final int blockWidth = Main.SIZE / 10;
    private final int blockHeight = 30;
    private final String pathName;
    private final HashMap<Integer, Color> colorMap = new HashMap<>() {{
        put(3, Color.RED);
        put(2, Color.ORANGE);
        put(1, Color.GREEN);
    }};;

    public Level(String pathName) { this.pathName = pathName; }
    public void showBlockLayout(Group root) { setBlockLayout(root); }

    private void setBlockLayout(Group root) {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathName))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("\\s+"); // Split on one or more spaces
                for (int i = 0; i < values.length; i++) {
                    if (values[i].isEmpty() || values[i].equals("0")) continue;
                    int x_OFFSET = 30;
                    int x = (i * blockWidth) + x_OFFSET;
                    int y_OFFSET = 50;
                    int y = (row * blockHeight) + y_OFFSET;
                    generateBlocks(root, values, i, x, y);
                }
                row++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateBlocks(Group root, String[] values, int i, int x, int y) {
        Block block;
        if (values[i].equals("X")) {
            int infiniteHealth = 1_000_000;
            block = new UnbreakableBlock(x, y, blockWidth, blockHeight, infiniteHealth);
        } else if (values[i].equals("B")) {
            int oneHealth = 1;
            block = new BadBlock(x, y, blockWidth, blockHeight, oneHealth);
        } else {
            int determinedHealth = Integer.parseInt(values[i]);
            block = new NormalBlock(x, y, blockWidth, blockHeight, determinedHealth);
            block.setFill(colorMap.get(determinedHealth));
        }
        root.getChildren().add(block);
    }
}