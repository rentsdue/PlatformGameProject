package objects;
import java.awt.geom.Rectangle2D;

import main.java.com.example.Game;

public class Spike extends GameObject {

    public Spike(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(32, 16);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * 16);
        hitBox.y += yDrawOffset;
    }

    public Rectangle2D.Float getHitBox() {
        return this.hitBox;
    }
}
