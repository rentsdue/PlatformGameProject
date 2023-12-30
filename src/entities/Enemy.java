package entities;
import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.Directions.*;

import main.java.com.example.Game;

public abstract class Enemy extends Entity {
    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed=25;
    protected boolean firstUpdate=true, inAir;
    protected float fallSpeed, gravity=0.04f*Game.SCALE;
    protected float walkSpeed=0.35f*Game.SCALE;
    protected int walkDir=LEFT;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType=enemyType;
        initHitBox(x, y, width, height);
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >=aniSpeed) {
            aniTick=0;
            aniIndex++;
            if (aniIndex>=GetSpriteAmount(enemyType, enemyState)) {
                aniIndex=0;
            }
        } 
    }
    
    
   protected void changeWalkDir() {
        if (walkDir==LEFT) {
            walkDir=RIGHT;
        } else {
            walkDir=LEFT;
        }
   }

    //Getters and setters
    public int getAniIndex() {
        return this.aniIndex;
    }

    public void setAniIndex(int aniIndex) {
        this.aniIndex = aniIndex;
    }

    public int getEnemyState() {
        return this.enemyState;
    }

    public void setEnemyState(int enemyState) {
        this.enemyState = enemyState;
    }

    public int getEnemyType() {
        return this.enemyType;
    }

    public void setEnemyType(int enemyType) {
        this.enemyType = enemyType;
    }

    public int getAniTick() {
        return this.aniTick;
    }

    public void setAniTick(int aniTick) {
        this.aniTick = aniTick;
    }

    public int getAniSpeed() {
        return this.aniSpeed;
    }

    public void setAniSpeed(int aniSpeed) {
        this.aniSpeed = aniSpeed;
    }
}
