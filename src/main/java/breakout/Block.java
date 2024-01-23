package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public abstract class Block extends Rectangle {

    private int health;

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


    public void addHealth(int amount) {
        this.health += amount;
        updateBlockColor();
    }


    public boolean isDestroyed() {
        return health <= 0;
    }

    private void updateBlockColor() {
        switch (health) {
            case 5 -> setFill(Color.RED);
            case 4 -> setFill(Color.DEEPPINK);
            case 3 -> setFill(Color.DARKSLATEBLUE);
            case 2 -> setFill(Color.ORANGE);
            case 1 -> setFill(Color.GREEN);
        }
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
        setStroke(Color.WHITE);
        setStrokeWidth(0.8);
        setFill(Color.BLACK);
    }
}