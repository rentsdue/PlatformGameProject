package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.*;
import static utilz.HelpMethods.*;

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
    protected int pushBackDir;
	protected float pushDrawOffset;
	protected int pushBackOffsetDir = UP;

    //Attack box and hit box
    protected Rectangle2D.Float hitBox;
    protected Rectangle2D.Float attackBox;
    
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void updatePushBackDrawOffset() {
		float speed = 0.95f;
		float limit = -30f;

		if (pushBackOffsetDir == UP) {
			pushDrawOffset -= speed;
			if (pushDrawOffset <= limit)
				pushBackOffsetDir = DOWN;
		} else {
			pushDrawOffset += speed;
			if (pushDrawOffset >= 0)
				pushDrawOffset = 0;
		}
	}

    protected void pushBack(int pushBackDir, int[][] lvlData, float speedMulti) {
		float xSpeed = 0;
		if (pushBackDir == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;

		if (CanMoveHere(hitBox.x + xSpeed * speedMulti, hitBox.y, hitBox.width, hitBox.height, lvlData))
			hitBox.x += xSpeed * speedMulti;
	}

    protected void drawHitBox(Graphics g, int xLvlOffset) {
        //For debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x- xLvlOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    protected void initHitBox(int width, int height) {
        hitBox= new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    protected void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - xLvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
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
        this.state = state;
    }

    public int getCurrentHealth() {
        return this.currentHealth;
    }

    protected void newState(int state) {
		this.state = state;
		aniTick = 0;
		aniIndex = 0;
	}
    

}
