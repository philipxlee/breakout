package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public abstract class Block extends Rectangle {

    protected int health;
    public Block(int x, int y, int width, int height, int health) {
        super(width, height);
        this.setX(x);
        this.setY(y);
        this.health = health;
        updateBlockColor();
    }

    public void hit() {
        health--;
        updateBlockColor();
    }

    private void updateBlockColor() {
        switch (health) {
            case 3 -> setFill(Color.RED);
            case 2 -> setFill(Color.ORANGE);
            case 1 -> setFill(Color.GREEN);
        }
    }

    public boolean isDestroyed() {
        return health <= 0;
    }
}

class NormalBlock extends Block {
    public NormalBlock(int x, int y, int width, int height, int health) {
        super(x, y, width, height, health);
    }
}

class BadBlock extends Block {
    public BadBlock(int x, int y, int width, int height, int health) {
        super(x, y, width, height, health);
        setFill(Color.BLUEVIOLET);
    }
}

class UnbreakableBlock extends Block {
    public UnbreakableBlock(int x, int y, int width, int height, int health) {
        super(x, y, width, height, health);
        health = Integer.MAX_VALUE;
        setStroke(Color.WHITE);
        setStrokeWidth(1.0);
        setFill(Color.BLACK);
    }
}
