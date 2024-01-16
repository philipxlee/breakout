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
        this.setStroke(Color.BLACK); // Outline color
        this.setStrokeWidth(1.0);
    }

    public void hit() {
        health--;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }
}

class NormalBlock extends Block {
    public NormalBlock(int x, int y, int width, int height, int health) {
        super(x, y, width, height, health);
        setFill(Color.GREEN);
    }
}
