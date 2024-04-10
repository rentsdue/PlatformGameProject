package objects;

import java.awt.geom.Rectangle2D;

import main.java.com.example.Game;

public class Cannon extends GameObject {

    private int tileY;
    
    public Cannon(int x, int y, int objType) {
        super(x, y, objType);
        tileY = y / Game.TILES_SIZE;
        initHitbox(40, 26);
        // hitBox.x -= (int) (4 * Game.SCALE);
        hitBox.y += (int) (6 * Game.SCALE);
    }

    public void update() {
        if (doAnimation) {
            updateAnimationTick();
        }
    }

    //Getters and setters
    public int getTileY() {
        return this.tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public Rectangle2D.Float getHitBox() {
        return this.hitBox;
    }
    
}
