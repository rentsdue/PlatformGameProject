package objects;

import static utilz.Constants.Projectiles.*;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.java.com.example.Game;
import utilz.LoadSave;

public class Projectile {

    private Rectangle2D.Float hitBox;
    private int dir;
    private boolean active = true;
    private BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PROJECTILE);;

    public Projectile(int x, int y, int dir) {
        int xOffset = (int) (-3 * Game.SCALE);
        int yOffset = (int) (5 * Game.SCALE);

        if (dir == 1) {
            xOffset = (int) (29* Game.SCALE);
        }

        hitBox = new Rectangle2D.Float(x + xOffset, y + yOffset, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
        this.dir = dir;
    }

    public void updatePosition() {
        hitBox.x += dir * SPEED;
    }

    public void setPosition(int x, int y) {
        hitBox.x = x;
        hitBox.y = y;
    }

    //Getters and setters
    public Rectangle2D.Float getHitBox() {
        return this.hitBox;
    }

    public void setHitBox(Rectangle2D.Float hitBox) {
        this.hitBox = hitBox;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BufferedImage getImage() {
        return this.img;
    }
    
}
