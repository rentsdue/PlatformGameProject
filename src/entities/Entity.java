package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.java.com.example.Game;

public abstract class Entity {
    protected float x, y;
    protected int aniTick, aniIndex;
    protected int width, height;
    protected int state;
    protected float airSpeed;
    protected boolean inAir = false;
    protected float walkSpeed = 1.0f * Game.SCALE;
    protected boolean attackChecked;

    protected int maxHealth;
	protected int currentHealth;

    //Attack box and hit box
    protected Rectangle2D.Float hitBox;
    protected Rectangle2D.Float attackBox;
    
    public Entity(float x, float y, int width, int height) {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }

    protected void drawHitBox(Graphics g, int xLvlOffset) {
        //For debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x- xLvlOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    protected void initHitBox(int width, int height) {
        hitBox= new Rectangle2D.Float(x, y, (int) (width*Game.SCALE), (int) (height*Game.SCALE));
    }

    protected void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x-xLvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    //Getters and setters
    public Rectangle2D.Float getHitBox() {
        return this.hitBox;
    }

    public int getState() {
        return this.state;
    }

    public int getAniIndex() {
        return this.aniIndex;
    }

    public void setState(int state){
        this.state=state;
    }

}
