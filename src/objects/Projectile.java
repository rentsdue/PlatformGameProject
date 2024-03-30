package objects;

import static utilz.Constants.Projectiles.*;

import java.awt.geom.Rectangle2D;

public class Projectile {

    private Rectangle2D.Float hitBox;
    private int dir;
    private boolean active = true;

    public Projectile(int x, int y, int dir) {
        hitBox = new Rectangle2D.Float(x, y, BALL_WIDTH, BALL_HEIGHT);
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
    
}
